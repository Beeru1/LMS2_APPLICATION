package com.ibm.lms.webservice;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.lms.dto.webservice.AuthorizationData;
import com.ibm.lms.dto.webservice.GisInfoCaptureDO;
import com.ibm.lms.dto.webservice.GisResponseMessage;
import com.ibm.lms.engine.dao.impl.CaptureGisDataDaoImpl;


public class GisInfoCaptureWebService 
{

private static final Logger logger =  Logger.getLogger(GisInfoCaptureWebService.class);
private static final String SUCCESS_CODE = "0";
private static final String INVALID_DATA = "1";
private static final String INCOMPLETE_DATA = "2";
private static final String INTERNAL_FATAL_ERROR_CODE = "3";

private static final String SUCCESS_MSG = "SuccessFul Update";
private static final String INVALID_DATA_MSG = "Invalid Data";
private static final String INCOMPLETE_DATA_MSG = "Incomplete Data.";
private static final String NOT_AUTHORIZED_MSG = "Invalid UserId/Password.";
private static final String INTERNAL_FATAL_ERROR_MSG = "Internal Fatal Error";

	public GisResponseMessage captureGisData(GisInfoCaptureDO[] gisData , AuthorizationData authData ) throws Exception
	{
		logger.info("inside captureGisData*********************************");
		GisResponseMessage rseponseMsg=null;
		
		try
		{   
			rseponseMsg = new GisResponseMessage();
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			String userName = bundle.getString("lms.dialer.ws.username");
			String password = bundle.getString("lms.dialer.ws.password");
			
			
			if((authData.getPassword()).equals(password) && authData.getUserName().equals(userName)) {
				logger.info("Creating process >>>>>>>");
				CaptureGisDataDaoImpl daoObj = new CaptureGisDataDaoImpl();
				String responses =daoObj.captureGisDataResponse(gisData);
				if(responses.equalsIgnoreCase(SUCCESS_CODE)) {
					rseponseMsg.setResponseCode(SUCCESS_CODE);
					rseponseMsg.setResponseMsg(SUCCESS_MSG);
				} 
				else if(responses.equalsIgnoreCase(INVALID_DATA))	{
					rseponseMsg.setResponseCode(INVALID_DATA);
					rseponseMsg.setResponseMsg(INVALID_DATA_MSG);
					logger.error("Error.Invalid Data");
				}
				else if(responses.equalsIgnoreCase(INCOMPLETE_DATA))	{
					rseponseMsg.setResponseCode(INCOMPLETE_DATA);
					rseponseMsg.setResponseMsg(INCOMPLETE_DATA_MSG);
					logger.error("Data Incomplete");
				}
			}else {
				rseponseMsg.setResponseCode(INVALID_DATA);
				rseponseMsg.setResponseMsg(NOT_AUTHORIZED_MSG);
				logger.error("Invalid Userid/Password:" + authData.getUserName() );
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured in captureLeadData():" + e.getMessage());
			
			rseponseMsg.setResponseCode(INTERNAL_FATAL_ERROR_CODE);
			rseponseMsg.setResponseMsg(INTERNAL_FATAL_ERROR_MSG);
			//throw new Exception("Exception in captureLeadData method:: "+e);
		}
		return rseponseMsg;
	}
	
	/*private HttpSession getSession() {
        HttpServletRequest req = (HttpServletRequest) this.wsContext.getMessageContext().get(MessageContext.SERVLET_REQUEST);
        return req.getSession(true);
 	
}*/
}
