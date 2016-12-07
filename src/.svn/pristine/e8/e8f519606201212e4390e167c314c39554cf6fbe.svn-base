package com.ibm.lms.webservice;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.webservice.AuthorizationData;
import com.ibm.lms.dto.webservice.UpdateLeadResponseMsg;
import com.ibm.lms.dto.webservice.UpdatedLeadDataFirstVersDTO;
import com.ibm.lms.engine.dao.impl.UpdateLeadDataDAOImplNew;


public class UpdateLeadDataFirstVers {
	
	private static final Logger logger =  Logger.getLogger(UpdateLeadDataFirstVers.class);
	private static final String INVALID_LEADID = "2";
	private static final String INVALID_TXN_ID = "3";
    
	
	public UpdateLeadResponseMsg leadUpdatedDataCapture(AuthorizationData authorizationData ,UpdatedLeadDataFirstVersDTO updatedLeadDataDTO  )throws Exception {
		UpdateLeadResponseMsg message =null;
		UpdateLeadDataDAOImplNew updateLeadDataDAOImpl=new UpdateLeadDataDAOImplNew();
		
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			String userName = bundle.getString("lms.dialer.ws.username");
			String password = bundle.getString("lms.dialer.ws.password");
			message  = new UpdateLeadResponseMsg();
			logger.info("leadUpdatedDataCapture******************************Starting process >>>>>>>");
			if(authorizationData !=null && (authorizationData.getPassword()).equals(password) && authorizationData.getUserName().equals(userName))
			{
				message = updateLeadDataDAOImpl.isValidId(updatedLeadDataDTO);
				String code = message.getResponseCode();
				logger.info("code in leadUpdatedDataCapture is************"+code);
				if(code.equalsIgnoreCase(INVALID_LEADID) || code.equalsIgnoreCase(INVALID_TXN_ID) || code.equalsIgnoreCase("5") ||code.equalsIgnoreCase("7"))
				{
					return message;
				}
				else
					message = updateLeadDataDAOImpl.updateLeadDataCapture(updatedLeadDataDTO);
				return message;
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
			message.setResponseMsg("Internal Error Occured *******************=="+e.getMessage());
		}
		
		return message;
	}

	

}
