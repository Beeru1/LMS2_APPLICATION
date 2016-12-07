package com.ibm.lms.webservice;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.lms.engine.dataobjects.CaptureLeadDO;
import com.ibm.lms.dto.AuthorizationData;
import com.ibm.lms.dto.ResponseMessage;
import com.ibm.lms.engine.AsyncTaskManager;
import com.ibm.lms.engine.handlers.ProcessCapturedLeadHandler;

public class LeadCaptureWebService {
	
	private static final Logger logger =  Logger.getLogger(LeadCaptureWebService.class);
	
	public ResponseMessage captureLeadData(CaptureLeadDO[] leadData , AuthorizationData authData ) 
							throws Exception
	{
		//logger.info("Calling captureLeadData webservice*******methodname::LeadCaptureWebService");
		//CaptureLeadDataDAO daoObj = new CaptureLeadDataDAOImpl();
		
		ResponseMessage rseponseMsg=null;
		try
		{
			//DBConnection.getInstance();
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			String userName = bundle.getString("lms.dialer.ws.username");
			String password = bundle.getString("lms.dialer.ws.password");
			
		//logger.info("User from Onzee::"+authData.getUserName()+"<<------");
		//logger.info("password from Onzee::"+authData.getPassword()+"<<------");
			
		//logger.info("User from ApplicationResources::"+userName);
		//logger.info("password from ApplicationResources::"+password);
			
			if((authData.getPassword()).equals(password) && authData.getUserName().equals(userName))
			{
				logger.info("Creating process >>>>>>>8738738783");
				ProcessCapturedLeadHandler obj = new ProcessCapturedLeadHandler(leadData);
				//logger.info("Starting process >>>>>>>");
				new AsyncTaskManager().processCapturedLeads(obj);
				//logger.info("Process Started >>>>>>>");
				//rseponseMsg = daoObj.captureLeadData(leadData);
				rseponseMsg = new ResponseMessage();
				rseponseMsg.setResponseCode("0");
				rseponseMsg.setResponseMsg("SUCCESS");
				logger.info("Process  End >>>>>>>");
			}
			else
			{
				rseponseMsg = new ResponseMessage();
				rseponseMsg.setResponseCode("1");
				rseponseMsg.setResponseMsg("Invalid Userid/Password");
				logger.error("Invalid Userid/Password:" + authData.getUserName() );
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured in captureLeadData():" + e.getMessage());
			rseponseMsg = new ResponseMessage();
			rseponseMsg.setResponseCode("1");
			rseponseMsg.setResponseMsg("Internal Error Occured ");
			throw new Exception("Exception in captureLeadData method:: "+e);
		}
		return rseponseMsg;
	}
	
	
}
