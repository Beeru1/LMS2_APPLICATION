
package com.ibm.lms.services.impl;

/**
 * @author Amandeep Singh 
 */

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.ibm.lms.common.DataObject;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dao.CircleManagementDao;
import com.ibm.lms.dao.impl.CircleManagementDaoImpl;
import com.ibm.lms.dto.CircleMstrDto;
import com.ibm.lms.dto.PushMstrWebserviceDTO;
import com.ibm.lms.dto.webservice.RetrieveLeadDataDTO;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.CircleManagementService;

public class CircleManagementServiceImpl implements CircleManagementService 
{
	private static final Logger logger = Logger.getLogger(CircleManagementServiceImpl.class);
	
	public ArrayList<CircleMstrDto> getLobList() throws LMSException 
	{
		CircleManagementDao dao= CircleManagementDaoImpl.circleManagementDaoInstance();//changed by srikant new CircleManagementDaoImpl();
		try
		{
			return dao.getLobList();
		}
		catch (Exception e) 
		{
			logger.error("Exception occurred while getting circle list : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	
	public  ArrayList<CircleMstrDto> getCircleList(CircleMstrDto circleMstrDto) throws LMSException
	{
		CircleManagementDao dao= CircleManagementDaoImpl.circleManagementDaoInstance();//changed by srikant new CircleManagementDaoImpl();
		try
		{
			return dao.getCircleList(circleMstrDto);
		}
		catch (Exception e) 
		{
			logger.error("Exception occurred while getting circle list : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	
	public int createCircle(CircleMstrDto circleMstrDto) throws LMSException 
	{
		CircleManagementDao dao= CircleManagementDaoImpl.circleManagementDaoInstance();//changed by srikant new CircleManagementDaoImpl();
		try
		{
			return dao.createCircle(circleMstrDto);
		}
		catch (Exception e) 
		{
			logger.error("Exception occurred while creating circle : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	
	public int deleteCircle(CircleMstrDto circleMstrDto) throws LMSException 
	{
		CircleManagementDao dao= CircleManagementDaoImpl.circleManagementDaoInstance();//changed by srikant new CircleManagementDaoImpl();
		try
		{
			return dao.deleteCircle(circleMstrDto);
		}
		catch (Exception e) 
		{
			logger.error("Exception occurred while getting circle list : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public int editCircle(CircleMstrDto circleMstrDto) throws LMSException 
	{
		CircleManagementDao dao= CircleManagementDaoImpl.circleManagementDaoInstance();//changed by srikant new CircleManagementDaoImpl();
		try
		{
			return dao.editCircle(circleMstrDto);
		}
		catch (Exception e) 
		{
			logger.error("Exception occurred while editting circle : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param dao
	 * @param product
	 * @return
	 * @throws DAOException
	 */
	private PushMstrWebserviceDTO [] getAllCityDetails(CircleManagementDao dao, String product) throws DAOException
	{
		List<DataObject> aCircleDataObjList = dao.getCityUsingProductName(product);
		if(aCircleDataObjList == null)
			return null;
		DataObject[] arr = Utility.convertListToArray(aCircleDataObjList);
		PushMstrWebserviceDTO[] aDTO = new PushMstrWebserviceDTO[1];
		aDTO[0] = new PushMstrWebserviceDTO();
		aDTO[0].setCityData(arr);
		
		return aDTO;
	}
	
	/**
	 * 
	 * @param dao
	 * @param product
	 * @return
	 * @throws DAOException
	 */
	private PushMstrWebserviceDTO [] getAllPincodeDetails(CircleManagementDao dao, String product) throws DAOException
	{
		List<String> pincodeList = dao.getPincodeUsingProductName(product);
		if(pincodeList == null)
			return null;
		String[] arr = Utility.convertListToArray(pincodeList);
		
		PushMstrWebserviceDTO [] array = new PushMstrWebserviceDTO[1];
		PushMstrWebserviceDTO aDTO = new PushMstrWebserviceDTO();
		array[0] = aDTO;
		
		aDTO.setPincodeList(arr);
		
		return array;
	}
	
	/**
	 * 
	 * @param dao
	 * @param product
	 * @return
	 * @throws DAOException
	 */
	private PushMstrWebserviceDTO [] getAllCirleDetails(CircleManagementDao dao, String product) throws DAOException
	{
		List<DataObject> aCircleDataObjList = dao.getCircleDetailsUsingProductName(product);
		if(aCircleDataObjList == null)
			return null;
		DataObject[] arr = Utility.convertListToArray(aCircleDataObjList);
		PushMstrWebserviceDTO[] aDTO = new PushMstrWebserviceDTO[1];
		aDTO[0] = new PushMstrWebserviceDTO();
		aDTO[0].setCircleData(arr);

		return aDTO;
	}
	
	
	/**
	 * 
	 * @param product
	 * @param flag
	 * @return
	 * @throws LMSException 
	 */
	public PushMstrWebserviceDTO[] getMstrListByProduct(String product, char flag) throws LMSException 
	{
		CircleManagementDao dao= CircleManagementDaoImpl.circleManagementDaoInstance();//changed by srikant new CircleManagementDaoImpl();
		flag = Character.toUpperCase(flag);
		logger.info("Executing for Product = " + product + " with flag = " + flag );
		try
		{
			if(dao.isValidProduct(product))
			{
				if(flag == 'S' || flag == 'D') 
				{
					// Fill circle details only
					return getAllCirleDetails(dao, product);
				}
				else if(flag == 'P') 
				{
					// Fill Pin code details only
					return getAllPincodeDetails(dao, product);
				}
				else if(flag == 'C') 
				{
					// Fill City details only
					return getAllCityDetails(dao, product);
				}
				else if(flag == 'A') 
				{
					// Fill circle id, circle name, pincode, city name and city id
					return dao.getFindAllDetailsUsingProductName(product);
				}
				
				// if wrong flag is given input
				return null;
			}
			else
			{
				PushMstrWebserviceDTO [] array = new PushMstrWebserviceDTO[1];
				PushMstrWebserviceDTO anObj = new PushMstrWebserviceDTO();
				array[0] = anObj;
				return array;
			}
		}
		catch (Exception e) 
		{
			logger.error("Exception occurred while executing getMstrListByProduct : " + e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	public RetrieveLeadDataDTO[] getLeadList(String leadId,String prospectMobileNumber, String transactionId, String flag)  {

		logger.info("getLeadList******************************Starting process >>>>>>>");
		CircleManagementDao dao= CircleManagementDaoImpl.circleManagementDaoInstance();//changed by srikant new CircleManagementDaoImpl();
		try{
			return dao.getLeadList(leadId,prospectMobileNumber,transactionId,flag);
		}
		catch (Exception e) 
		{
			logger.error("Exception occurred while executing getMstrListByProduct : " + e.getMessage());
			//throw new LMSException(e.getMessage(), e);
		}
		return null;

	}


	
	
}