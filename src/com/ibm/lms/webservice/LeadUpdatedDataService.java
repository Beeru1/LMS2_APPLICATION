package com.ibm.lms.webservice;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.webservice.AuthorizationData;
import com.ibm.lms.dto.webservice.UpdatedLeadDataDTO;
import com.ibm.lms.dto.webservice.ServiceResponseMsg;
import com.ibm.lms.engine.AsyncTaskManager;
import com.ibm.lms.engine.dao.impl.UpdateLeadDataDAOImpl;
import com.ibm.lms.engine.handlers.LeadCaptureDataProcessLeadHandler;

public class LeadUpdatedDataService {
	
	private static final Logger logger =  Logger.getLogger(LeadUpdatedDataService.class);
	private static final String INVALID_LEADID = "2";
	private static final String INVALID_TXN_ID = "3";
    private static final String INVALIDLEADID = "Invalid Lead ID";
	private static final String INVALID_TXNID = "Invalid Transaction ID";
	
	
	public ServiceResponseMsg leadUpdatedDataCapture(UpdatedLeadDataDTO updatedLeadDataDTO [] ,AuthorizationData authorizationData)throws Exception {
		ServiceResponseMsg message =null;
		AsyncTaskManager asyncTaskManager =null;
		UpdateLeadDataDAOImpl updateLeadDataDAOImpl=new UpdateLeadDataDAOImpl();
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			String userName = bundle.getString("lms.dialer.ws.username");
			String password = bundle.getString("lms.dialer.ws.password");
			message  = new ServiceResponseMsg();
			logger.info("leadUpdatedDataCapture******************************Starting process >>>>>>>");
			if(authorizationData !=null && (authorizationData.getPassword()).equals(password) && authorizationData.getUserName().equals(userName))
			{
				
				String resp = updateLeadDataDAOImpl.isValidId(updatedLeadDataDTO);
				
				if(resp!=null && resp.length()>0 && resp.equalsIgnoreCase(INVALID_LEADID))
				{
					
					message.setResponseCode(INVALID_LEADID);
					message.setResponseMsg(INVALIDLEADID);
					return message;
				}
				else if(resp!=null && resp.length()>0 && resp.equalsIgnoreCase("5"))
				{
					message.setResponseCode("5");
					message.setResponseMsg("Invalid Lead/txn id");
					return message;
				}
				else if(resp!=null && resp.length()>0 && resp.equalsIgnoreCase("3"))
				{
					
					message.setResponseCode(INVALID_TXN_ID);
					message.setResponseMsg(INVALID_TXNID);
					return message;
				}
				else if(resp!=null && resp.length()>0 && resp.equalsIgnoreCase("7"))
				{
					
					message.setResponseCode("7");
					message.setResponseMsg("Invalid data");
					return message;
				}
				
				else
				{
					
					LeadCaptureDataProcessLeadHandler obj = new LeadCaptureDataProcessLeadHandler(updatedLeadDataDTO);
					asyncTaskManager = new AsyncTaskManager();
					asyncTaskManager.processLeadCapture(obj);
					message.setResponseCode("0");
					message.setResponseMsg("SUCCESS");
				}
					
			}
			else 
			{
				message.setResponseCode("1");
				message.setResponseMsg("Invalid Userid/Password");
			}
			logger.info("leadUpdatedDataCapture************************end  process >>>>>>>");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("error msg  in leadUpdatedDataCapture() ====="+Utility.getStackTrace(e));
			message.setResponseCode("1");
			message.setResponseMsg("Internal Error Occured *******************"+e.getMessage());
		}
		//logger.info("********************"+asyncTaskManager);
		//asyncTaskManager.shutDownAll();
		
		return message;
	}

	

}