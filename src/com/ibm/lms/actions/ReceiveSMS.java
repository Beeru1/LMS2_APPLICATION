package com.ibm.lms.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;

import com.ibm.lms.dto.SMSDto;
import com.ibm.lms.dto.SmsObject;
import com.ibm.lms.dto.SmsState;
import com.ibm.lms.engine.util.ServerPropertyReader;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.SMSSendReceiveService;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.services.impl.SMSSendReceiveServiceImpl;
import com.ibm.misc.BASE64Encoder;


/**
 * Servlet implementation class ReceiveSMS
 */
public class ReceiveSMS extends HttpServlet {
	static PostMethod postMethod = null;
	static HttpClient client = null;
	private static final long serialVersionUID = 1L;
	public static Hashtable<SmsObject,SmsState> smsMap=new Hashtable<SmsObject, SmsState>();
	public static Hashtable<String,Long>leadMsisdnMap=new Hashtable<String, Long>();
	private static Logger logger = Logger.getLogger(ReceiveSMS.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReceiveSMS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @return 
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    public void init(){
    	
    	TimerTask timer=new RefreshSMSMaps();
    	Timer time=new Timer(false);
    	
    	time.schedule(timer, 0, 120*1000);
    	
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		logger.info("Hello SMS received");
		SMSDto smsDto=null;
		
		Enumeration <String>e =request.getHeaderNames();
		StringBuffer sbH=new StringBuffer();
		while(e.hasMoreElements()){
			String headerName=e.nextElement();
			sbH.append(headerName+":"+request.getHeader(headerName)+"  ");
			
		}
	
		String header=sbH.toString();
		
		BufferedReader br=request.getReader();
		StringBuffer sb=new StringBuffer();
		
		String line="";
		while((line=br.readLine())!= null){
			sb.append(line);
			
		}
		String body=sb.toString();
		
		
		
		
		SMSSendReceiveService smsService=new SMSSendReceiveServiceImpl();
		MasterService masterServc=new MasterServiceImpl();
		long smsID=-1;
		
		
		try {
			smsID=smsService.logSMS(header, body);
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		
		if(!body.trim().equals("")){
			smsDto=smsService.parseXml(body);
			
		}else{
			return ;
		}
		logger.info("sms_id="+smsID);
		if(smsID!=-1){
			try {
				smsService.updateSMSLog(smsID, smsDto.getMsIsdn());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		SmsObject smsObject;
		SMSSendReceiveService smsServ=new SMSSendReceiveServiceImpl();
		char state=' ';
		
		if(!leadMsisdnMap.containsKey(smsDto.getMsIsdn())){
			state='0';
			
		}else {
			smsObject=new SmsObject();
			smsObject.setLeadId(leadMsisdnMap.get(smsDto.getMsIsdn()));
			smsObject.setMsIsdn(smsDto.getMsIsdn());
			if(!smsMap.containsKey(smsObject)){
				leadMsisdnMap.remove(smsDto.getMsIsdn());
				 sensSMS(smsDto.getMsIsdn(),ServerPropertyReader.getString("REQ_TIME_OUT"),"","0");
				 return;
			}
			SmsState stateObjct=smsMap.get(smsObject);
			state=stateObjct.getState().charAt(0);
			
		}
		switch(state){
			
		case '0':
			
			
			     
			    	 String text[]=smsDto.getSmsText().split(" ");
			    	 if(text.length<2 || text.length>2){
			    		 try {
							sensSMS(smsDto.getMsIsdn(),masterServc.getParameterName("INVALID_INP"),"","0");
						} catch (LMSException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			    		 return;
			    	 }
			    	 else
			    	 {
			    		 try {
							Long lead=smsServ.isValidLead(text[1]);
							if(lead==null){
								sensSMS(smsDto.getMsIsdn(), masterServc.getParameterName("LEAD_INVALID"),"", "0");
								return;
							}else{
								smsDto.setHeaderLead(lead);
								smsDto.setHeaderStatus("1");
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			    		 
			    		 
			    	 }
			    	 logger.info(smsDto.getHeaderLead());
			    	 SmsObject smsObj=new SmsObject();
			    	 SmsState smsState=new SmsState();
			    	 smsObj.setLeadId(Long.parseLong(text[1]));
			    	 smsObj.setMsIsdn(smsDto.getMsIsdn());
			    	 smsState.setState("1");
			    	 smsState.setLasMsgtime(System.currentTimeMillis());
			    	 smsMap.put(smsObj, smsState);
			    	 leadMsisdnMap.put(smsDto.getMsIsdn(),Long.parseLong(text[1]));
			    	 logger.info(smsDto.getMsIsdn());
			    	 try {
					sensSMS(smsDto.getMsIsdn(), masterServc.getParameterName("LEAD_STATUS"), leadMsisdnMap.get(smsDto.getMsIsdn()).toString(), smsDto.getHeaderStatus());
				} catch (LMSException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
			    	 
			     
			     
			    
			   break;
	
		case '1':
			   
			      
				 smsObj=new SmsObject();
				 smsState=new SmsState();
				 smsObj.setLeadId(leadMsisdnMap.get(smsDto.getMsIsdn()));
				 smsObj.setMsIsdn(smsDto.getMsIsdn());
				 smsState.setState("1");
				 smsState.setLasMsgtime(System.currentTimeMillis());
				 
				 if(!smsMap.containsKey(smsObj)){
					 leadMsisdnMap.remove(smsDto.getMsIsdn());
					 try {
						sensSMS(smsDto.getMsIsdn(),masterServc.getParameterName("REQ_TIME_OUT"),"","0");
					} catch (LMSException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 return;
				 }
				 text=smsDto.getSmsText().split(" ");
				 if(text.length<2 || text.length>2 || !text[0].equalsIgnoreCase("LUM") || (!text[1].equalsIgnoreCase("WON") && !text[1].equalsIgnoreCase("LOST"))){
					 try {
						sensSMS(smsDto.getMsIsdn(), masterServc.getParameterName("LEAD_INVALID_STAT"), leadMsisdnMap.get(smsDto.getMsIsdn()).toString(), smsDto.getHeaderStatus());
					} catch (LMSException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 return ;
				 }
				 try {
					if(!smsServ.updateWonLostLead(leadMsisdnMap.get(smsDto.getMsIsdn()).toString())){
						
						 sensSMS(smsDto.getMsIsdn(),masterServc.getParameterName("LEAD_AL_UPDATED"),"","0");
						 leadMsisdnMap.remove(smsDto.getMsIsdn());
						 smsMap.remove(smsObj);
						 return;
					 }
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					return ;
				}
				smsState.setState("2");
				smsDto.setHeaderStatus("2");
				smsMap.put(smsObj, smsState);
				 if(text[1].equals("LOST")){
					 
					 try {
						smsServ.updateLeadLost(leadMsisdnMap.get(smsDto.getMsIsdn()), "600", smsDto.getMsIsdn());
						sensSMS(smsDto.getMsIsdn(), "Lead "+leadMsisdnMap.get(smsDto.getMsIsdn()).toString()+ServerPropertyReader.getString("LEAD_UPDATE"), "", "");
						smsMap.remove(smsObj);
						leadMsisdnMap.remove(smsDto.getMsIsdn());
						return;
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return ;
					}
				 }
				 else if(text[1].equals("WON")){
					 
					 try {
						sensSMS(smsDto.getMsIsdn(),masterServc.getParameterName("LEAD_CAF_NO"),leadMsisdnMap.get(smsDto.getMsIsdn()).toString(),smsDto.getHeaderStatus());
					} catch (LMSException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 return;
				 }
			break;
			
		case '2':

			
			 SmsObject smsObj2=new SmsObject();
			 SmsState smsState2=new SmsState();
			 smsObj2.setLeadId(leadMsisdnMap.get(smsDto.getMsIsdn()));
			 smsObj2.setMsIsdn(smsDto.getMsIsdn());
			 smsState2.setState("3");
			 smsState2.setLasMsgtime(System.currentTimeMillis());
			 
			 if(!smsMap.containsKey(smsObj2)){
				 leadMsisdnMap.remove(smsDto.getMsIsdn());
				 try {
					sensSMS(smsDto.getMsIsdn(),masterServc.getParameterName("REQ_TIME_OUT"),"","0");
				} catch (LMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 return;
			 }
			 String text2[]=smsDto.getSmsText().split(" ");
			 if(text2.length>2 || text2.length<2)
			 {
				 try {
					sensSMS(smsDto.getMsIsdn(), masterServc.getParameterName("INVALID_CAF"), "","");
				} catch (LMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 return;
			 }
			 
			 try {
					smsServ.updateLeadWon(leadMsisdnMap.get(smsDto.getMsIsdn()), "500", smsDto.getMsIsdn(), text2[1]);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					try {
						sensSMS(smsDto.getMsIsdn(), masterServc.getParameterName("LEAD_ERROR"), leadMsisdnMap.get(smsDto.getMsIsdn()).toString(), smsDto.getHeaderStatus());
					} catch (LMSException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e2.printStackTrace();
					return;
				}
			 
			 			 
			 try {
					sensSMS(smsDto.getMsIsdn(),masterServc.getParameterName("LEAD_MOB_NO"), leadMsisdnMap.get(smsDto.getMsIsdn()).toString(), smsDto.getHeaderStatus());
				} catch (LMSException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			 smsMap.put(smsObj2, smsState2);
			
			break;
			
		case '3':

			smsObj2=new SmsObject();
			smsState2=new SmsState();
			smsObj2.setLeadId(leadMsisdnMap.get(smsDto.getMsIsdn()));
			smsObj2.setMsIsdn(smsDto.getMsIsdn());
			smsState2.setState("4");
			smsState2.setLasMsgtime(System.currentTimeMillis());
			if (!smsMap.containsKey(smsObj2)) {
				
				 leadMsisdnMap.remove(smsDto.getMsIsdn());
				try {
					sensSMS(
							smsDto.getMsIsdn(),
							masterServc.getParameterName("REQ_TIME_OUT"),
							"", "0");
				} catch (LMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return;
			}
			String text3[] = smsDto.getSmsText().split(" ");
			if(text3.length>2 || text3.length<2 || text3[1]==null || text3[1].length()>10 || text3[1].length()<10 || !text3[1].matches("\\d+"))
			 {
				 try {
					sensSMS(smsDto.getMsIsdn(), masterServc.getParameterName("INVALID_MOB"), leadMsisdnMap.get(smsDto.getMsIsdn()).toString(),smsDto.getHeaderStatus());
				} catch (LMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 return;
			 }
			
			try {
					smsServ.leadMobNoUpdate(leadMsisdnMap.get(smsDto.getMsIsdn()), text3[1]);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			//code//
			
			try {
					sensSMS(smsDto.getMsIsdn(), masterServc.getParameterName("TRANS_COMPLETE"), leadMsisdnMap.get(smsDto.getMsIsdn()).toString(), "4");
				} catch (LMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			smsMap.remove(smsObj2);
			leadMsisdnMap.remove(smsDto.getMsIsdn());
			 
			break;
		
		
		default : 
			       try {
					sensSMS(smsDto.getMsIsdn(), masterServc.getParameterName("ACCESS_DENIED"),"", "");
				} catch (LMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		           
			
			break;
		
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private void sensSMS(String msisdn,String text,String headerLead,String status){
		
	
		String responseString="";
		try {
			String placeHolderString;
			
	      
	       	        
			String ip =ServerPropertyReader.getString("fhhttp.sms.ip");
			String port = ServerPropertyReader.getString("fhhttp.sms.port");
			String userId = ServerPropertyReader.getString("fhhttp.sms.user");
			String password = ServerPropertyReader.getString("fhhttp.sms.pass");
			String smsSender = ServerPropertyReader.getString("fhhttp.sms.sender");
						
			logger.info(ip);
			logger.info(port);
			logger.info("\n");
			StringBuffer sbXmlMsg = new StringBuffer("");
			
			sbXmlMsg.append("<?xml version=\"1.0\" encoding=\"US-ASCII\"?><message>");
			sbXmlMsg.append("<sms type=\"mt\"><header-lead>"+headerLead+"</header-lead><header-status>"+status+"</header-status><destination");
			sbXmlMsg.append("><address><number type=\"national\">");
			sbXmlMsg.append(msisdn);
			sbXmlMsg.append("</number></address></destination><source><address>");
			sbXmlMsg.append("<alphanumeric>"+smsSender+"</alphanumeric>");
			sbXmlMsg.append("</address></source>");
			sbXmlMsg.append("<rsr type=\"all\" />");
			sbXmlMsg.append("<ud type=\"text\" encoding=\"default\">");
			sbXmlMsg.append(new StringBuffer()).append((text));
			sbXmlMsg.append("</ud></sms></message>");
			placeHolderString = sbXmlMsg.toString();

				/* Obtaining connection from First Hope */
			getConnection(ip, Integer.parseInt(port), userId, password);

			/* Sending SMS using XML over HTTP */
			StringRequestEntity requestEntity =	new StringRequestEntity(placeHolderString);
			postMethod.setRequestEntity(requestEntity);
			client.executeMethod(postMethod);
			//Print Response Body
			responseString = postMethod.getResponseBodyAsString();
			
			logger.info("ACK : " + responseString);
			

		} catch (Exception e) {
			e.printStackTrace();
			//logger.info("Exception occured while Sending SMS : "+ e.getMessage());
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
			
		}
		return ;
	}
	private void getConnection(String mb_ip,int mb_port,String userId,String password)
	{
		client = new HttpClient();
		client.setHttpConnectionManager(new MultiThreadedHttpConnectionManager());
		client.getHostConfiguration();
		String url ="http"+"://"+mb_ip+ ":" +mb_port;
		

		try {
			postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Content-Type", "text/xml");
			postMethod.setRequestHeader("Authorization", "Basic " + encode(userId + ":" + password));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private String encode(String source) {
		BASE64Encoder enc = new BASE64Encoder();		
		return (enc.encode(source.getBytes()));
	}

	
	

}
