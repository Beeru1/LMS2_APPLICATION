package com.ibm.lms.webservice;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.lms.dto.webservice.AuthorizationData;
import com.ibm.lms.dto.webservice.IDOCDataStatusDTO;
import com.ibm.lms.dto.webservice.IDOCStatusServiceResponse;
import com.ibm.lms.services.WebServiceIdocServices;
import com.ibm.lms.services.impl.WebServiceIdocServiceImpl;

public class IDOCDataStatusService {
	
	private static final Logger logger =  Logger.getLogger(IDOCDataStatusService.class);
	
	//private static final String SUCCESS_MSG = "Success";
	private static final String SUCCESS_CODE = "0";
	private static final String INTERNAL_FATAL_ERROR_MSG = "Internal Fatal Error";
	private static final String  FAILURE_CODE = "1";
	public IDOCStatusServiceResponse iDOCStatusUpdateData(AuthorizationData authorizationData ,IDOCDataStatusDTO [] dataStatusDTO) throws Exception
	
	{
		IDOCStatusServiceResponse statusServiceResponse  = new IDOCStatusServiceResponse();
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			String userName = bundle.getString("lms.dialer.ws.username");
			String password = bundle.getString("lms.dialer.ws.password");
			
			if(authorizationData !=null && password.equals(authorizationData.getPassword()) && userName.equals(authorizationData.getUserName()))
			{ 
				if(dataStatusDTO !=null && dataStatusDTO.length >0) {
					WebServiceIdocServices serviceIdocDao  = new WebServiceIdocServiceImpl();
					String responce  = serviceIdocDao.IDOCDataStatus(dataStatusDTO);
					statusServiceResponse.setResponseCode(SUCCESS_CODE);
					statusServiceResponse.setResponseMsg(responce);
				}else {
					statusServiceResponse.setResponseCode(FAILURE_CODE);
					statusServiceResponse.setResponseMsg("Invalid Data received");
				}
				 
			}else {
				statusServiceResponse.setResponseCode(FAILURE_CODE);
				statusServiceResponse.setResponseMsg("Invalid Userid/Password");
				logger.error("Invalid Userid/Password:=========" + authorizationData.getUserName() );
			}
			
		} catch (Exception e) {
			statusServiceResponse.setResponseCode(FAILURE_CODE);
			statusServiceResponse.setResponseMsg(INTERNAL_FATAL_ERROR_MSG);
			logger.error("Invalid Userid/Password:=========" + e.getMessage());
			return statusServiceResponse;
		}
		
		return statusServiceResponse;
	}
	

}
