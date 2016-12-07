package com.ibm.lms.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ibm.km.dao.impl.KmDocumentMstrDaoImpl;
//import com.ibm.km.common.Constants;
//import com.ibm.km.common.KeyChar;
import com.ibm.lms.dto.PushMstrWebserviceArrayDTO;
import com.ibm.lms.dto.PushMstrWebserviceDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.ws.cache.config.ElementHandler;

public class Utility {
public static String getNetworkFaultIndexPath(){
	
	Calendar cal = GregorianCalendar.getInstance();
	StringBuffer path = new StringBuffer(PropertyReader.getAppValue("network.fault.index.path")).append("/").append(cal.get(cal.YEAR)).append("-").append(cal.get(cal.MONTH)+1).append("-").append(cal.get(cal.DATE));
	return path.toString();
}

public static String getCurrentTime(){
	
	Calendar cal = GregorianCalendar.getInstance();
	StringBuffer path = new StringBuffer("").append(cal.get(cal.DATE)).append("/").append(cal.get(cal.MONTH)+1).append("/").append(cal.get(cal.YEAR)).append(" ").append(cal.get(cal.HOUR_OF_DAY)).append(":").append(cal.get(cal.MINUTE)).append(":").append(cal.get(cal.SECOND));
	return path.toString();
}

public static String getCurrentTime_(){
	
	Calendar cal = GregorianCalendar.getInstance();
	String month=changeFormat(cal.get(cal.MONTH)+1);
	String day=changeFormat(cal.get(cal.DATE));
	String hr=changeFormat(cal.get(cal.HOUR_OF_DAY));
	String min=changeFormat(cal.get(cal.MINUTE));
	String sec=changeFormat(cal.get(cal.SECOND));
		
	StringBuffer path = new StringBuffer("").append(cal.get(cal.YEAR)).append("-").append(month).append("-").append(day).append(" ").append(hr).append(":").append(min).append(":").append(sec);
	return path.toString();
}
public static String changeFormat(int in){
	String  val="";
	if(in < 10)
		 val ="0"+in ;
	else
		val =""+in ;
	
return val;	
}

 public static java.sql.Date getSqlDateFromString(String strDate,String strFormat) {

	SimpleDateFormat sdf;
	try {
		sdf = new SimpleDateFormat(strFormat);  
		return new java.sql.Date(sdf.parse(strDate).getTime());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		//logger.info("Exception occured while parsign date"+e);
		return null;
	} 
}

 
 public static String getDocumentViewURL(String documentId, int docType) {
	 String docUrl = "";
	 switch(docType)
		{
		  case 0 : 							docUrl = "documentAction.do?methodName=displayDocument";
		  									break;	
		  case Constants.DOC_TYPE_FILE : 	docUrl = "documentAction.do?methodName=displayDocument";
											break;	
		  case Constants.DOC_TYPE_PRODUCT : docUrl = "productUpload.do?methodName=viewProductDetails";
		  									break;	
		  case Constants.DOC_TYPE_SOP : 	docUrl = "sopUpload.do?methodName=viewSopDetails";
		  									break;	
		  case Constants.DOC_TYPE_SOP_BD : 	docUrl = "sopBDUpload.do?methodName=viewSopBDDetails";
											break;	
		  case Constants.DOC_TYPE_RC : 		docUrl = "rcContentUpload.do?methodName=viewRCData";
		  									break;	
		  case Constants.DOC_TYPE_BP : 		docUrl = "bpUpload.do?methodName=viewBPDetails";
			 								break;	
		  case Constants.DOC_TYPE_DTH : 	docUrl = "offerUpload.do?methodName=viewDTHOffer";
		  									break;
		  default : 							docUrl = "documentAction.do?methodName=displayDocument";
		}
	 
	 docUrl += "&docID=" + documentId;
	 return docUrl;
 }
 
 public static String getDocumentViewURL(String documentId) {
	 int docType = 0;
	 try {
		docType = new KmDocumentMstrDaoImpl().getDocumentType(documentId);
	} catch (LMSException e) {
		e.printStackTrace();
	} catch (DAOException e) {
		e.printStackTrace();
	}
	 return  getDocumentViewURL(documentId , docType);
 } 
 public static String encodeContent(String content) {	
	 
	 content = content.replaceAll("<script>", "< script>").replaceAll("</script>", "< /script>").replaceAll("<SCRIPT>", "< SCRIPT>").replaceAll("</SCRIPT>", "< /SCRIPT>");
		
	 return  ((content.replaceAll(">","&gt;")).replaceAll("<","&lt;")).replaceAll("&","&amp;"); 
 }
 public static String decodeContent(String content) {	
	 return  ((content.replaceAll("&gt;",">")).replaceAll("&lt;","<")).replaceAll("&amp;","&"); 
 }

 /*public static ArrayList<String> getMatch(String value)throws LMSException{
	 ArrayList<String> arr = new ArrayList<String>();
	 ElementHandler handler = new ElementHandler();
	 KeyChar kc = KeyChar.getRoot();
	 if(kc != null) {
		 arr = kc.match(value);
			logger.info("OUT:" + arr);
	 return arr;
	 }
//	 handler.addRules((RuleHandler)(Object) value);
	 return arr;
 }

 public static void insertWords(ArrayList<String> value)throws LMSException{
	 if(value == null || value.size() == 0) return;
	 KeyChar kc = KeyChar.getRoot();
	 for(int i = 0; i < value.size(); i++) {
		 String temp = value.get(i);
		 if(kc == null) 
			 kc = new KeyChar(temp);
		 else
			 kc.addNode(temp);
	 }*/
//	 cacheWrapper.equals(value);
//	 handler.addRules((RuleHandler)(Object) cacheWrapper);
//	 handler.finished();
// }
 
 public static boolean validateName(String name) {
		boolean isValid = false;
		String expression ="^[a-zA-Z]*$";
		String str = name;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			isValid = true;
		}
	
		return isValid;
	}
 
 
	public static boolean validateEmail(String email) {
		
		Pattern pattern;
	    Matcher matcher;
	    final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		pattern = Pattern.compile(EMAIL_PATTERN);

		matcher = pattern.matcher(email);
		return matcher.matches();

	}
	
	public static boolean validateNumber(String number) {
		boolean isValid = false;
		String expression ="^[0-9]*$";
		String str = number;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			isValid = true;
		}
	
		return isValid;
	}
	
	public static boolean validateAlphaNumeric(String alphaNum) {
		boolean isValid = false;
		String expression ="^[a-zA-Z ]*$";
		String str = alphaNum;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			isValid = true;
		}
	
		return isValid;
	}
	
	public static boolean validatePhoneNo(String phoneNo) {
		boolean isValid = false;
		String expression ="^[\\d]{2,4}[\\s]{0,1}[-]{1}[\\s]{0,1}[\\d]{6,8}$";
		String str = phoneNo;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			isValid = true;
		}
	
		return isValid;
	}
	
	public static boolean validateAddress(String address) {
		boolean isValid = false;
		String expression ="^[a-zA-Z0-9 \\ / - : ( ) @ ,]*$";
		String str = address;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			isValid = true;
		}
	
		return isValid;
	}

	//added By Beeru
	public static String getStackTrace(Exception e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}

	/**
	 * 
	 * @param aStringList
	 * @return
	 */
	public static String[] convertListToArray(List<String> aStringList)
	{
		int size = aStringList.size();
		String[] arr = new String[size];
		for(int i=0; i<size; i++)
		{
			arr[i] = aStringList.get(i);
		}	
		return arr;
	}
	
	
	/**
	 * 
	 * @param aDataObjList
	 * @return
	 */
	public static DataObject[] convertListToArray(List<DataObject> aDataObjList)
	{
		DataObject[] aDataObjArray = new DataObject[aDataObjList.size()];
		int size = aDataObjList.size();
		for(int i=0; i<size; i++)
		{
			aDataObjArray[i] = aDataObjList.get(i);
		}	
		return aDataObjArray;
	}

	
	
	/**
	 * 
	 * @param flag
	 */
	public static boolean isValidFlag(String flag)
	{
		if(flag == null || flag.trim().equals("") || flag.length() > 1)
			return false;
		flag = flag.toUpperCase();
		return flag.equals("C") || flag.equals("P") || flag.equals("S") || flag.equals("A");
	}
	
	public static boolean isValidRequest(HttpServletRequest request) {
		
		try {
			String requestedURI = request.getRequestURI();
			HttpSession session =  request.getSession();
			UserMstr userBean = (UserMstr)session.getAttribute("USER_INFO");
			if(session !=null && session.getAttribute("GROUP_LINKS_LIST") !=null && !Constants.SUPER_ADMIN.equalsIgnoreCase(userBean.getKmActorId()) && !Constants.CIRCLE_ADMIN.equalsIgnoreCase(userBean.getKmActorId())) {
				List<String> list  = (List<String>)session.getAttribute("GROUP_LINKS_LIST");	
				if(list !=null && request.getQueryString() !=null && !list.contains(requestedURI.substring(requestedURI.lastIndexOf("/")+1)+"?"+request.getQueryString())) {
					return true;
				}else if(list !=null && request.getQueryString() ==null && !list.contains(requestedURI.substring(requestedURI.lastIndexOf("/")+1))) {
					return true;
				}
			}
			
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
