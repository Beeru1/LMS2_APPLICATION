package com.ibm.lms.services;

import java.util.ArrayList;

import com.ibm.lms.dto.LogsDTO;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.DownloadLogsFormBean;

public interface LogsService {
	
	 public ArrayList<LogsDTO> getSMSLogs(DownloadLogsFormBean formBean) throws LMSException; 
	 
	 public ArrayList<LogsDTO> getEmailLogs(DownloadLogsFormBean formBean) throws LMSException;  //added by sudhanshu

	 public ArrayList<LogsDTO> getDownloadFilesLogs(DownloadLogsFormBean formBean ) throws LMSException;
	 public ArrayList<LogsDTO> getUserMstrHistLogs(DownloadLogsFormBean formBean) throws LMSException;
	 public ArrayList<LogsDTO> getUserMstrLoginLogout(DownloadLogsFormBean formBean) throws LMSException;
	 public String getLogsData(int logsId,DownloadLogsFormBean formBean)throws LMSException;
}
