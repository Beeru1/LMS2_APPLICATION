package com.ibm.lms.dao;

import java.util.ArrayList;


import com.ibm.lms.dto.LogsDTO;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.forms.DownloadLogsFormBean;

public interface LogsDao {

 public ArrayList<LogsDTO> getSMSLogs(DownloadLogsFormBean formBean) throws DAOException; 
	 
	 public ArrayList<LogsDTO> getEmailLogs(DownloadLogsFormBean formBean) throws DAOException;  //added by sudhanshu

	 public ArrayList<LogsDTO> getDownloadFilesLogs(DownloadLogsFormBean formBean ) throws DAOException;
	 public ArrayList<LogsDTO> getUserMstrHistLogs(DownloadLogsFormBean formBean) throws DAOException;

	public ArrayList<LogsDTO> getUserMstrLoginLogout(
			DownloadLogsFormBean formBean) throws DAOException;
	public String getLogsData(String logsName,DownloadLogsFormBean formBean)throws DAOException;
	 
}
