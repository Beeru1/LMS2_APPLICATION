package com.ibm.lms.services.impl;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.ibm.lms.dto.LogsDTO;
import com.ibm.lms.dao.LogsDao;
import com.ibm.lms.dao.impl.LogsDaoimpl;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.DownloadLogsFormBean;
import com.ibm.lms.services.LogsService;

public class LogsServiceImpl implements LogsService {
	private static int SMSlogsId = 12;
	private static int EmaillogsId = 13;
	private static int uploadFilelogsId = 14;
	private static int userMstrHistlogsId = 15;
	private static int loginlogoutid = 16;
	private static int AssignmentmatrixlogsId=17;
	private static int LeadsearchTransactionlogs=18;
	private static int LeadCaptureDownloadLogs = 26;
	private static final Logger logger;
	static {
		logger = Logger.getLogger(LogsServiceImpl.class);
	}
	@Override
	public ArrayList<LogsDTO> getDownloadFilesLogs(DownloadLogsFormBean formBean)
			throws LMSException {
		// TODO Auto-generated method stub
		
		logger.info("Inside DownloadFilesLogs");
		 logger.info("Inside getDownloadFilesLogs");
		 LogsDao logsDao = LogsDaoimpl.logsDaoInstance();//chnaged by srikant new ReportDaoImpl();
		 try{
			 
			 return logsDao.getDownloadFilesLogs(formBean);
		 }
		 catch (Exception e) {
			 e.printStackTrace();
				logger.error("Exception occurred while getDownloadFilesLogs: "+e.getMessage());
				throw new LMSException(e.getMessage(), e);
			}
		
		
	}

	@Override
	public ArrayList<LogsDTO> getEmailLogs(DownloadLogsFormBean formBean)
			throws LMSException {
		// TODO Auto-generated method stub
		logger.info("Inside EmailLogs");
		 logger.info("Inside getEmailLogs");
		 LogsDao logsDao = LogsDaoimpl.logsDaoInstance();//chnaged by srikant new ReportDaoImpl();
		 try{
			 
			 return logsDao.getEmailLogs(formBean);
		 }
		 catch (Exception e) {
			 e.printStackTrace();
				logger.error("Exception occurred while getEmailLogs: "+e.getMessage());
				throw new LMSException(e.getMessage(), e);
			}
		
	}

	@Override
	public ArrayList<LogsDTO> getSMSLogs(DownloadLogsFormBean formBean)
			throws LMSException {
		// TODO Auto-generated method stub
		
		logger.info("Inside SMSLogs");
		 logger.info("Inside getSMSLogs");
		 LogsDao logsDao = LogsDaoimpl.logsDaoInstance();//chnaged by srikant new ReportDaoImpl();
		 try{
			 
			 return logsDao.getSMSLogs(formBean);
		 }
		 catch (Exception e) {
			 e.printStackTrace();
				logger.error("Exception occurred while getSMSLogs: "+e.getMessage());
				throw new LMSException(e.getMessage(), e);
			}
		
	}

	@Override
	public ArrayList<LogsDTO> getUserMstrHistLogs(DownloadLogsFormBean formBean)
			throws LMSException {
		// TODO Auto-generated method stub
		logger.info("Inside UserMstrHistLogs");
		 logger.info("Inside getUserMstrHistLogs");
		 LogsDao logsDao = LogsDaoimpl.logsDaoInstance();//chnaged by srikant new ReportDaoImpl();
		 try{
			 
			 return logsDao.getUserMstrHistLogs(formBean);
		 }
		 catch (Exception e) {
			 e.printStackTrace();
				logger.error("Exception occurred while getUserMstrHistLogs: "+e.getMessage());
				throw new LMSException(e.getMessage(), e);
			}
	}

	@Override
	public ArrayList<LogsDTO> getUserMstrLoginLogout(
			DownloadLogsFormBean formBean) throws LMSException {
		// TODO Auto-generated method stub
		logger.info("Inside UserMstrHistLogs");
		 logger.info("Inside getUserMstrHistLogs");
		 LogsDao logsDao = LogsDaoimpl.logsDaoInstance();//chnaged by srikant new ReportDaoImpl();
		 try{
			 
			 return logsDao.getUserMstrLoginLogout(formBean);
		 }
		 catch (Exception e) {
			 e.printStackTrace();
				logger.error("Exception occurred while getUserMstrHistLogs: "+e.getMessage());
				throw new LMSException(e.getMessage(), e);
			}
	}

	public String getLogsData(int logsId,DownloadLogsFormBean formBean)throws LMSException
	{
		String logsData="";
		logger.info("Inside getLogsData method in LogsServiceImpl");
		 LogsDao logsDao = LogsDaoimpl.logsDaoInstance();//chnaged by srikant new ReportDaoImpl();
		 try
		 {
			if(logsId==SMSlogsId) 
			{
				logsData=logsDao.getLogsData("CUSTOMER_SEND_SMS_DETAILS",formBean);
			}
			else if(logsId==EmaillogsId)
			{
				logsData=logsDao.getLogsData("EMAIL_SENT_HISTORY",formBean);
			}
			else if(logsId==uploadFilelogsId)
			{
				logsData=logsDao.getLogsData("BULK_DATA_TRANSACTION_LOGS",formBean);
			}
			else if(logsId==userMstrHistlogsId)
			{
				logsData=logsDao.getLogsData("USER_MSTR_HIST",formBean);
			}
			else if(logsId==loginlogoutid)
			{
				logsData=logsDao.getLogsData("KM_LOGIN_DATA", formBean);
			}
			else if(logsId==AssignmentmatrixlogsId)
			{
				logsData=logsDao.getLogsData("ASSIGNMENT_MATRIX_HIST", formBean);
			}
			else if(logsId==LeadsearchTransactionlogs)
			{
				logsData=logsDao.getLogsData("LEAD_SEARCH_TRANSACTION", formBean);
			}
			
			else if(logsId==LeadCaptureDownloadLogs)
			{
				logsData=logsDao.getLogsData("LEAD_CAPTURE,LEAD_CAPTURE_DATA", formBean);
			}
		 }
		 catch(Exception e)
		 {
			 logger.error("Exception occurred while getting Report Name: "+e.getMessage());
				throw new LMSException(e.getMessage(), e);
			 
		 }
		return logsData;
	}
}
