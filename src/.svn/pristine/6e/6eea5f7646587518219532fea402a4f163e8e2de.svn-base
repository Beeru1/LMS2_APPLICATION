package com.ibm.lms.webservice;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.lms.dto.webservice.AuthorizationData;
import com.ibm.lms.dto.webservice.LeadCaptureServiceFirstVersDO;
import com.ibm.lms.dto.webservice.LeadCaptureServiceFirstVersRespsMsg;
import com.ibm.lms.engine.dao.CaptureLeadDataDAO;
import com.ibm.lms.engine.dao.impl.CaptureLeadDataDAOImpl;

public class LeadCaptureServiceFirstVers {

	private static final Logger logger =  Logger.getLogger(LeadCaptureServiceFirstVers.class);
	public LeadCaptureServiceFirstVersRespsMsg leadCaptureData( AuthorizationData authorizationData ,LeadCaptureServiceFirstVersDO leadCaptureData) throws Exception
	{
		
		LeadCaptureServiceFirstVersRespsMsg rseponseMsg=null;
		try
		{
			//DBConnection.getInstance();
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			String userName = bundle.getString("lms.dialer.ws.username");
			String password = bundle.getString("lms.dialer.ws.password");
	
			rseponseMsg = new LeadCaptureServiceFirstVersRespsMsg();
			if(authorizationData !=null && (authorizationData.getPassword()).equals(password) && authorizationData.getUserName().equals(userName))
			{
				if(leadCaptureData !=null) {
				CaptureLeadDataDAO daoObj = new CaptureLeadDataDAOImpl();
				rseponseMsg.setResponseCode("Txn-"+daoObj.getLeadData(leadCaptureData));
				rseponseMsg.setResponseMsg("SUCCESS"); 
				}else {
					rseponseMsg.setResponseCode("0");
					rseponseMsg.setResponseMsg("Invalid data");
				}
				
				
			}
			else
			{
				rseponseMsg.setResponseCode("0");
				rseponseMsg.setResponseMsg("Invalid Userid/Password");
				logger.error("Invalid Userid/Password:" );
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured in createLeadData():" + e.getMessage());
			rseponseMsg.setResponseCode("1");
			rseponseMsg.setResponseMsg("Internal Error Occured ::"+e);
			
		}
		return rseponseMsg;
	}
		
		
	}
	

