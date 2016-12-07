package com.ibm.lms.webservice;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.lms.dto.AuthorizationData;
import com.ibm.lms.dto.UpdateLeadResponseMessage;
import com.ibm.lms.engine.AsyncTaskManager;
import com.ibm.lms.engine.dataobjects.UpdateLeadDataDO;
import com.ibm.lms.engine.handlers.UpdateLeadDataHandler;

public class UpdateLeadWebService {

	private static final Logger logger =  Logger.getLogger(UpdateLeadWebService.class);
	
	public UpdateLeadResponseMessage updateLeadData(UpdateLeadDataDO[] leadData , AuthorizationData authData ) throws Exception
	{
		UpdateLeadResponseMessage rseponseMsg=null;
		
		try
		{
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			String userName = bundle.getString("lms.dialer.ws.username");
			String password = bundle.getString("lms.dialer.ws.password");
			if(leadData == null || leadData.length==0) {
				rseponseMsg = new UpdateLeadResponseMessage();
				rseponseMsg.setResponseCode("1");
				rseponseMsg.setResponseMsg("Invalid Lead Data");
				return rseponseMsg;
			}
			if(authData != null &&(authData.getPassword()).equals(password) && authData.getUserName().equals(userName))
			{
				logger.info("UpdateLeadData start process >>>>>>>");
				UpdateLeadDataHandler obj = new UpdateLeadDataHandler(leadData);
				new AsyncTaskManager().processUpdateLeads(obj);
				
				rseponseMsg = new UpdateLeadResponseMessage();
				rseponseMsg.setResponseCode("0");
				rseponseMsg.setResponseMsg("SUCCESS");
				logger.info("UpdateLeadData Process  End >>>>>>>");
			}else {
				rseponseMsg = new UpdateLeadResponseMessage();
				rseponseMsg.setResponseCode("1");
				rseponseMsg.setResponseMsg("Invalid Userid/Password");
				logger.info("UpdateLeadData Process  End >>>>>>>");
			}
		}catch(Exception e) {
			rseponseMsg = new UpdateLeadResponseMessage();
			rseponseMsg.setResponseCode("1");
			rseponseMsg.setResponseMsg("Internal Error Occured");
			logger.info("UpdateLeadData Process  End >>>>>>>");
		}
		return  rseponseMsg;
	}
}
