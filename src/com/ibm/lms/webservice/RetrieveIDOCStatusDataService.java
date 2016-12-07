package com.ibm.lms.webservice;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.lms.dto.webservice.AuthorizationData;
import com.ibm.lms.dto.webservice.RetrieveIDOCLeadDataDTO;
import com.ibm.lms.dto.webservice.RetrieveIDOCStatusDataResponse;
import com.ibm.lms.services.WebServiceIdocServices;
import com.ibm.lms.services.impl.WebServiceIdocServiceImpl;

public class RetrieveIDOCStatusDataService {
	private static final int SUCCESS_CODE = 0;
	private static final int NO_DATA_FOUND_CODE = 1;
	private static final int NOT_AUTHORIZED_CODE = 2;
	private static final int INTERNAL_FATAL_ERROR_CODE = 5;

	private static final String SUCCESS_MSG = "Success";
	private static final String NO_DATA_FOUND_MSG = "No Data Found";
	private static final String NOT_AUTHORIZED_MSG = "Not Authorized";
	private static final String INTERNAL_FATAL_ERROR_MSG = "Internal Fatal Error";
	private static final String PRODCUT_SOURCE_MANDETORY = "Source mandetory for ProspectMobileno";
	private static final int PRODCUT_SOURCE_MANDETORY_CODE = 3;
	private static final Logger logger =  Logger.getLogger(RetrieveIDOCStatusDataService.class);
	
	public RetrieveIDOCStatusDataResponse retrieveIDOCStatusData(AuthorizationData authorizationData ,String leadid,String prospectMobileNumber,String product,String source,String transactionId){
		RetrieveIDOCStatusDataResponse idocResponse = null;
		WebServiceIdocServices idocService = new WebServiceIdocServiceImpl();
		//Pattern pattern = Pattern.compile(".*[^0-9].*");
		RetrieveIDOCLeadDataDTO[] idocDataRes=null;
	 try{
		 
		 logger.info("Inside retrieveIDOCData method of RetrieveIDOCDataService class");
		 idocResponse = new RetrieveIDOCStatusDataResponse(); 
		 ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			String userNames = bundle.getString("lms.dialer.ws.username");
			String passwords = bundle.getString("lms.dialer.ws.password");
			if(authorizationData!=null && authorizationData.getUserName().equalsIgnoreCase(userNames)&& authorizationData.getPassword().equals(passwords))
			{
				//logger.info("cond"+((leadid==null || leadid.trim().length()<=0)|| (transactionId==null || transactionId.trim().length()<=0)));
				if(((((leadid.trim().length()<=0 && transactionId.trim().length()<=0)) || (leadid==null&&transactionId==null)) && (prospectMobileNumber!=null && prospectMobileNumber.trim().length()>0))&&((source==null || source.trim().length()<=0)))
				{
					//logger.info("pro"+pattern.matcher(product.trim()).matches());
					    //idocResponse = new RetrieveIDOCDataResponseDto(); 
						idocResponse.setResponseMsg(PRODCUT_SOURCE_MANDETORY);
						idocResponse.setResponseCode(PRODCUT_SOURCE_MANDETORY_CODE);
				}
				else{
					//calling method to fetch data
					 idocDataRes= idocService.getIDOCDataList(leadid, prospectMobileNumber, product, source, transactionId);
				if(idocDataRes == null || idocDataRes.length<=0)
				{
					//idocResponse = new RetrieveIDOCDataResponseDto(); 
					idocResponse.setResponseMsg(NO_DATA_FOUND_MSG);
					idocResponse.setResponseCode(NO_DATA_FOUND_CODE);
					idocResponse.setWebServiceResponse(null);
				}
				else
				{
					 //idocResponse = new RetrieveIDOCDataResponseDto(); 
					//logger.info("TRUEEEEEEEE");
					idocResponse.setResponseMsg(SUCCESS_MSG);
					idocResponse.setResponseCode(SUCCESS_CODE);
					idocResponse.setWebServiceResponse(idocDataRes);
				}
			}
			}
			else
			{
				// idocResponse = new RetrieveIDOCDataResponseDto(); 
				idocResponse.setResponseMsg(NOT_AUTHORIZED_MSG);
				idocResponse.setResponseCode(NOT_AUTHORIZED_CODE);
				idocResponse.setWebServiceResponse(null);
			}
	 }
		catch(Exception e)
		{
			e.printStackTrace();
			idocResponse.setResponseMsg(INTERNAL_FATAL_ERROR_MSG);
			idocResponse.setResponseCode(INTERNAL_FATAL_ERROR_CODE);
			logger.error("Error exist inside retrieveIDOCData mthod ", e);
		}
		
		return idocResponse;
		 
	}
}
