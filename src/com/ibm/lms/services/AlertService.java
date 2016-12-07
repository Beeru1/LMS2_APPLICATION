package com.ibm.lms.services;

import java.util.ArrayList;

import com.ibm.lms.dto.AlertDTO;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.AlertsFormBean;

public interface AlertService
{
	public int insertAlert(AlertDTO alertsDto) throws LMSException, DAOException;
	public boolean checkDuplicateAlert(AlertDTO alertsDto) throws LMSException, DAOException;
	public  ArrayList<AlertDTO> getAlertDetails(int alertId) throws LMSException,DAOException;
	

}
