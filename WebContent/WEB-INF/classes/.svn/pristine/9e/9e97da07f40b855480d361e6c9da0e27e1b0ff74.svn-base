package com.ibm.lms.webservice;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.lms.dto.webservice.AuthorizationData;
import com.ibm.lms.dto.webservice.RetrieveLeadDataDTO;
import com.ibm.lms.dto.webservice.RetrieveLeadDataResponse;
import com.ibm.lms.services.CircleManagementService;
import com.ibm.lms.services.impl.CircleManagementServiceImpl;


public class RetrieveLeadDataService {
	
	private static final Logger logger = Logger.getLogger(RetrieveLeadDataService.class);
	
	private static final int SUCCESS_CODE = 0;
	private static final int NO_DATA_FOUND_CODE = 1;
	private static final int NOT_AUTHORIZED_CODE = 2;
	private static final int INTERNAL_FATAL_ERROR_CODE = 5;

	private static final String SUCCESS_MSG = "Success";
	private static final String NO_DATA_FOUND_MSG = "No Data Found";
	private static final String NOT_AUTHORIZED_MSG = "Not Authorized";
	private static final String INTERNAL_FATAL_ERROR_MSG = "Internal Fatal Error";
	
	public RetrieveLeadDataResponse retrieveLeadData(AuthorizationData authData ,String leadId, String prospectMobileNumber,
			String transactionId,String flag) 
			throws Exception {

		RetrieveLeadDataResponse response=null;
		CircleManagementService circleManagement = new CircleManagementServiceImpl();
		try{

			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			String userNames = bundle.getString("lms.dialer.ws.username");
			String passwords = bundle.getString("lms.dialer.ws.password");
			response = new RetrieveLeadDataResponse(); 
			if (authData !=null && authData.getPassword().equals(passwords) && authData.getUserName().equals(userNames)) 
			{
					logger.info(leadId+"===leadId  retrieveLeadData******************************Starting process >>>>>>>transactionId=="+transactionId);
					RetrieveLeadDataDTO[] resp = circleManagement.getLeadList(leadId,prospectMobileNumber,transactionId,flag);

						if(resp == null || resp.length<=0)
						{
							response = new RetrieveLeadDataResponse(); 
							response.setResponseMsg(NO_DATA_FOUND_MSG);
							response.setResponseCode(NO_DATA_FOUND_CODE);
							response.setWebServiceResponse(null);
						}
						else {
							response = new RetrieveLeadDataResponse(); 
							response.setResponseMsg(SUCCESS_MSG);
							response.setResponseCode(SUCCESS_CODE);
							response.setWebServiceResponse(resp);
						}
						logger.info("retrieveLeadData******************************End process >>>>>>>");
			}
			else {
				response = new RetrieveLeadDataResponse(); 
				response.setResponseMsg(NOT_AUTHORIZED_MSG);
				response.setResponseCode(NOT_AUTHORIZED_CODE);
				response.setWebServiceResponse(null);
			}
			return response;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("error in retrieved lead data============"+e.getMessage());
			response.setResponseMsg(INTERNAL_FATAL_ERROR_MSG);
			response.setResponseCode(INTERNAL_FATAL_ERROR_CODE);
		}

		return response;
	}
	
	
	
}
		
	


