package com.ibm.lms.webservice;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.AuthorizationData;
import com.ibm.lms.dto.PushMstrWebserviceDTO;
import com.ibm.lms.dto.PushMstrWebserviceResponse;
import com.ibm.lms.services.impl.CircleManagementServiceImpl;


public class PushMstrWebService 
{
	private static final Logger logger = Logger.getLogger(PushMstrWebService.class);
	
	private static final int SUCCESS_CODE = 0;
	private static final int NO_DATA_FOUND_CODE = 1;
	private static final int NOT_AUTHORIZED_CODE = 2;
	private static final int INCORRECT_PRODUCT_CODE = 3;
	private static final int INCORRECT_FLAG_CODE = 4;
	private static final int INTERNAL_FATAL_ERROR_CODE = 5;

	private static final String SUCCESS_MSG = "Success";
	private static final String NO_DATA_FOUND_MSG = "No Data Found";
	private static final String NOT_AUTHORIZED_MSG = "Not Authorized";
	private static final String INCORRECT_PRODUCT_MSG = "Incorrect Product name.";
	private static final String INCORRECT_FLAG_MSG = "Allowed flags are: S,P,C or A";
	private static final String INTERNAL_FATAL_ERROR_MSG = "Internal Fatal Error";

	
	/**
	 * error codes:
	 * 0  -  success.
	 * 1  -  No data found.
	 * 2  -  user authentication failure.
	 * 3  -  product name incorrect.
	 * 4  -  flag is not correct.
	 * 5  -  internal fatal error
	 * 
	 * @param authData
	 * @param product
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public PushMstrWebserviceResponse getMstrByProduct(AuthorizationData authData, String product, String flag) 
					throws Exception 
	{

		logger.info("*******************inside Web service of getMstrByProduct()**************");
		CircleManagementServiceImpl circleManagementServiceImpl = new CircleManagementServiceImpl();

		PushMstrWebserviceResponse response = null;
		try 
		{
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			String userNames = bundle.getString("lms.dialer.ws.username");
			String passwords = bundle.getString("lms.dialer.ws.password");
			response = new PushMstrWebserviceResponse(); 
			if (authData.getPassword().equals(passwords) && authData.getUserName().equals(userNames)) 
			{
				if(Utility.isValidFlag(flag))
				{
					if (product == null || product.length() <= 0) 
					{
						
						response.setResponseMessage(INCORRECT_PRODUCT_MSG);
						response.setProductName(product);
						response.setResponseCode(INCORRECT_PRODUCT_CODE);
						response.setWebserviceResponse(null);
					}
					else
					{
						PushMstrWebserviceDTO[] resp = circleManagementServiceImpl.getMstrListByProduct(product, flag.charAt(0));
						if(resp == null)
						{
							response.setResponseMessage(NO_DATA_FOUND_MSG);
							response.setProductName(product);
							response.setResponseCode(NO_DATA_FOUND_CODE);
							response.setWebserviceResponse(null);
						}
						else
						{
							response.setProductName(product);
							if(resp[0].getCircleData() == null && resp[0].getCityData() == null && resp[0].getPincodeList() == null)
							{
								response.setResponseMessage(INCORRECT_PRODUCT_MSG);
								response.setResponseCode(INCORRECT_PRODUCT_CODE);	
							}
							else
							{
								response.setResponseMessage(SUCCESS_MSG);
								response.setResponseCode(SUCCESS_CODE);
							}
							
							response.setWebserviceResponse(resp[0]);
							return response;
						}
					}
				}
				else
				{
					response.setResponseMessage(flag + " is not correct input." + INCORRECT_FLAG_MSG);
					response.setProductName(product);
					response.setResponseCode(INCORRECT_FLAG_CODE);
					response.setWebserviceResponse(null);
				}
			}
			else 
			{
				response.setResponseMessage(NOT_AUTHORIZED_MSG);
				response.setProductName(product);
				response.setResponseCode(NOT_AUTHORIZED_CODE);
				response.setWebserviceResponse(null);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in updateLeadDisposition():"
					+ e.getMessage());

			response.setResponseMessage(INTERNAL_FATAL_ERROR_MSG);
			response.setProductName(product);
			response.setResponseCode(INTERNAL_FATAL_ERROR_CODE);
			response.setWebserviceResponse(null);
		
		}
		logger.info("*******************out Web service of getMstrByProduct()**************");
		return response;
	}
}
