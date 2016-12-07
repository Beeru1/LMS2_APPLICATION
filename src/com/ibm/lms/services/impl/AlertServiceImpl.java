package com.ibm.lms.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.lms.common.Constants;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.dao.AlertDao;
import com.ibm.lms.dao.LeadRegistrationDao;
import com.ibm.lms.dao.UserMstrDao;
import com.ibm.lms.dao.impl.AlertDaoImpl;
import com.ibm.lms.dao.impl.LeadRegistrationDaoImpl;
import com.ibm.lms.dao.impl.UserMstrDaoImpl;
import com.ibm.lms.dto.AlertDTO;
//import com.ibm.lms.dto.AlertMsgDto;
import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.dto.LeadDetailsDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.AlertsFormBean;
import com.ibm.lms.services.AlertService;
import com.ibm.lms.services.MasterService;

public class AlertServiceImpl implements AlertService
{
	private static final Logger logger;

	static {

		logger = Logger.getLogger(LeadRegistrationServiceImpl.class);
	}
	
	public int insertAlert(AlertDTO alertsDto) throws LMSException, DAOException
	{
		AlertDao dao = AlertDaoImpl.alertDaoInstance();//changed by srikant new AlertDaoImpl();
		return dao.insertAlert(alertsDto);
	}
	
	public boolean checkDuplicateAlert(AlertDTO alertsDto)
	throws LMSException, DAOException
	{
		boolean exist = false;
		logger.info("checkDuplicateAlert");
		try {
			AlertDao alertDao = AlertDaoImpl.alertDaoInstance();//changed by srikant new AlertDaoImpl();
			if (alertDao.checkDuplicateAlert(alertsDto))
			{
				exist = true;
			} else {
				exist = false;
			}
		}
		catch (DAOException e) {
 
			   logger.error(
				   "SQL Exception occured while CheckUserId."
					   + "Exception Message: "
					   + e.getMessage());
			   e.printStackTrace();
			   throw new DAOException("SQLException: " + e.getMessage(), e); 
		}
		catch (Exception e) {
			e.printStackTrace();
			
				logger.error("DAOException occured in checkDuplicateAlert():" + e.getMessage());
				throw new LMSException(e.getMessage());
			
		}
		return exist;

	}
	public  ArrayList<AlertDTO> getAlertDetails(int alertId) throws LMSException
	{
		AlertDao dao= AlertDaoImpl.alertDaoInstance();//changed by srikant new AlertDaoImpl();
		try
		{
		return dao.getAlertDetails(alertId);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while getting Alert details : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	}
