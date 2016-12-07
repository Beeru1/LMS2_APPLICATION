package com.ibm.lms.dao;

import java.util.ArrayList;
import java.util.List;

import com.ibm.lms.dto.AlertDTO;
import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;

public interface AlertDao {
	
	public boolean checkDuplicateAlert(AlertDTO alertsDto)throws DAOException;
	public int insertAlert(AlertDTO alertsDto) throws DAOException;
	public boolean updateAlert(AlertDTO alertsDto)throws DAOException;
	public  ArrayList<AlertDTO> getAlertDetails(int leadId) throws DAOException;
}
