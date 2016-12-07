package com.ibm.lms.webservice;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.lms.dto.webservice.AuthorizationData;
import com.ibm.lms.dto.webservice.RetrieveLeadDataNewDTO;
import com.ibm.lms.dto.webservice.RetrieveLeadDataRespMsg;
import com.ibm.lms.services.CircleManagementServiceNew;
import com.ibm.lms.services.impl.CircleManagementServiceImplNew;


public class RetrieveLeadDataFirstVers {
	
	private static final Logger logger = Logger.getLogger(RetrieveLeadDataFirstVers.class);
	
	private static final int SUCCESS_CODE = 0;
	private static final int NO_DATA_FOUND_CODE = 1;
	private static final int NOT_AUTHORIZED_CODE = 2;
	private static final int INTERNAL_FATAL_ERROR_CODE = 5;

	private static final String SUCCESS_MSG = "Success";
	private static final String NO_DATA_FOUND_MSG = "No Data Found";
	private static final String NOT_AUTHORIZED_MSG = "Not Authorized";
	private static final String INTERNAL_FATAL_ERROR_MSG = "Internal Fatal Error";
	
	public RetrieveLeadDataRespMsg retrieveLeadDetailData(AuthorizationData authorizationData ,String leadId, String prospectMobileNumber,String transactionId,String flag,String product,String source) throws Exception {

		RetrieveLeadDataRespMsg response=null;
		CircleManagementServiceNew circleManagement = new CircleManagementServiceImplNew();
		try{

			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			String userNames = bundle.getString("lms.dialer.ws.username");
			String passwords = bundle.getString("lms.dialer.ws.password");
			response = new RetrieveLeadDataRespMsg(); 
			if (authorizationData !=null && authorizationData.getPassword().equals(passwords) && authorizationData.getUserName().equals(userNames)) 
			{
					logger.info(leadId+"===leadId  retrieveLeadData******************************Starting process >>>>>>>transactionId=="+transactionId);
					logger.info("product"+product+"source----"+source);
				RetrieveLeadDataNewDTO[] resp = circleManagement.getLeadListNew(leadId,prospectMobileNumber,transactionId,flag,product,source);

						if(resp == null || resp.length<=0)
						{
							response.setResponseMsg(NO_DATA_FOUND_MSG);
							response.setResponseCode(NO_DATA_FOUND_CODE);
							response.setWebServiceResponse(null);
						}
						else {
							response.setResponseMsg(SUCCESS_MSG);
							response.setResponseCode(SUCCESS_CODE);
							response.setWebServiceResponse(resp);
						}
						logger.info("retrieveLeadData******************************End process >>>>>>>");
			}
			else {
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
		
	


