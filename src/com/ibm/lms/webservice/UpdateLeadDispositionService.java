package com.ibm.lms.webservice;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.lms.dao.UpdateLeadDispositionDAO;
import com.ibm.lms.dao.impl.UpdateLeadDispositionDAOImpl;
import com.ibm.lms.dto.AuthorizationData;
import com.ibm.lms.dto.LeadData;
import com.ibm.lms.dto.ResponseMessage;
import com.ibm.lms.dto.ResponseMessageDisposition;

public class UpdateLeadDispositionService {
	
	private static final Logger logger =  Logger.getLogger(UpdateLeadDispositionService.class);
	
	public ResponseMessageDisposition[] updateLeadDisposition(LeadData[] leadData , AuthorizationData authData )
	{
		logger.info("Calling UpdateLeadDispositionService webservice*******methodname::updateLeadDisposition");
		UpdateLeadDispositionDAO daoObj =  UpdateLeadDispositionDAOImpl.updateLeadDispositionDAOInstance();
		IEncryption crypt = new Encryption();
		ResponseMessageDisposition rseponseMsg=null;
		ResponseMessageDisposition[] arrRespMessage = null;
		try
		{
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			String userName = bundle.getString("lms.dialer.ws.username");
			String password = bundle.getString("lms.dialer.ws.password");
			
			logger.info("password::::::::::::::::::::::::"+password);
			
			if((authData.getPassword()).equals(password) && authData.getUserName().equals(userName))
			{
				arrRespMessage = daoObj.updateLeadDisposition(leadData);
			}
			else
			{
				rseponseMsg = new ResponseMessageDisposition();
				rseponseMsg.setResponseCode("1");
				rseponseMsg.setResponseMsg("Invalid Userid/Password");
				arrRespMessage = new ResponseMessageDisposition[1];
				arrRespMessage[0] = rseponseMsg;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured in updateLeadDisposition():" + e.getMessage());
			rseponseMsg = new ResponseMessageDisposition();
			rseponseMsg.setResponseCode("1");
			rseponseMsg.setResponseMsg("Internal Error Occured ");
			arrRespMessage = new ResponseMessageDisposition[1];
			arrRespMessage[0] = rseponseMsg;
		}
		return arrRespMessage;
	}
	
	
}
