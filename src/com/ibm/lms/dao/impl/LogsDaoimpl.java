package com.ibm.lms.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.dao.LogsDao;
import com.ibm.lms.dto.LogsDTO;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.forms.DownloadLogsFormBean;

public class LogsDaoimpl implements LogsDao {
	
	
	private String SQL_SELECT_DOWNLOADFILE_LOGS = "SELECT USER_LOGIN_ID AS USER_ID,DOWNLOAD_TIME,MESSAGE,CLIENT_IP AS IP_ADDRESS,FILENAME FROM BULK_DATA_TRANSACTION_LOGS WHERE";
	private String SQL_SELECT_SMS_LOGS = "SELECT MESSAGE AS MESSAGE_CONTENT,MOBILE_NUMBER,SENT_ON AS SENT_DATE,RESPONSE_MSG AS SENT_STATUS FROM CUSTOMER_SEND_SMS_DETAILS WHERE";
	private String SQL_SELECT_EMAIL_LOGS = "SELECT 	EMAIL_ID,EMAIL_MSG,SUBJECT AS EMAIL_SUBJECT,SENT_ON,RESPONSE FROM EMAIL_SENT_HISTORY WHERE";
	private String SQL_SELECT_USERMSTRHIST_LOGS = "SELECT * FROM USER_MSTR_HIST WHERE";
	private String SQL_SELECT_LOGIN_LOGOUT = "SELECT USER_LOGIN_ID, LOGIN_TIME, ELEMENT_ID, FAV_CATEGORY_ID, USER_FNAME, USER_LNAME, LOGIN_DATE, LOGOUT_TIME, SESSION_ID, CLIENT_IP FROM KM_LOGIN_DATA WHERE";
	private static final Logger logger;
	static {
		logger = Logger.getLogger(LogsDaoimpl.class);
	}
	
	
	private static LogsDaoimpl logsDaoImpl=null;
	
	private LogsDaoimpl(){
		
	}
	
	public static LogsDaoimpl logsDaoInstance()
	{
		if(logsDaoImpl==null)
		{
			logsDaoImpl=new LogsDaoimpl();
		}
		return logsDaoImpl;
		
	}

	@Override
	public ArrayList<LogsDTO> getDownloadFilesLogs(DownloadLogsFormBean formBean)
			throws DAOException {
		// TODO Auto-generated method stub
		 logger.info("Inside getDownloadFilesLogs of LogsDaoImpl");
		
		 ArrayList<LogsDTO> logsList = new ArrayList<LogsDTO>();
		 LogsDTO dto = null;
		 Connection con = null;
			PreparedStatement ps = null;			
			ResultSet rs = null;
			String startDate = null;
			String endDate =null;
			try{
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");			
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
				if(formBean.getStartDate()!=null && !formBean.getStartDate().equalsIgnoreCase(""))
				{
					//start = (Date) sdf.parse(formBean.getStartDate());
					startDate = dateFormat1.format((Date) sdf.parse(formBean.getStartDate()));
				}
				if(formBean.getEndDate()!=null && !formBean.getEndDate().equalsIgnoreCase(""))
				{
					endDate = dateFormat1.format((Date) sdf.parse(formBean.getEndDate()));
				}
				
				con = DBConnection.getDBConnection();	
				StringBuffer query = new StringBuffer(SQL_SELECT_DOWNLOADFILE_LOGS);
				if(startDate!=null && !startDate.equalsIgnoreCase("") && endDate!=null && !endDate.equalsIgnoreCase(""))
				{
					query.append(" DATE(DOWNLOAD_TIME) BETWEEN '"+startDate+"' and  '"+endDate+"' ");
				}
				
				
				query.append(" WITH UR");
				ps = con.prepareStatement(query.toString());
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					dto = new LogsDTO();
					dto.setUserLoginId(rs.getString("USER_ID"));
					dto.setDownloadTime(rs.getString("DOWNLOAD_TIME"));
					dto.setMsg(rs.getString("MESSAGE"));
					dto.setClientIp(rs.getString("IP_ADDRESS"));
					dto.setFileName(rs.getString("FILENAME"));
					logsList.add(dto);
				}
			}
			catch(Exception e)
			{
				e.getMessage();
			}
			finally
			{

				try {
					DBConnection.releaseResources(con,ps, rs);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					logger.error(ex);
				}
				
			
			}
			return logsList;
	}

	@Override
	public ArrayList<LogsDTO> getEmailLogs(DownloadLogsFormBean formBean)
			throws DAOException {
		// TODO Auto-generated method stub
		 ArrayList<LogsDTO> logsList = new ArrayList<LogsDTO>();
		 LogsDTO dto = null;
		 Connection con = null;
			PreparedStatement ps = null;			
			ResultSet rs = null;
			String startDate = null;
			String endDate = null;
			try{
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");			
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
				if(formBean.getStartDate()!=null && !formBean.getStartDate().equalsIgnoreCase(""))
				{
					//start = (Date) sdf.parse(formBean.getStartDate());
					startDate = dateFormat1.format((Date) sdf.parse(formBean.getStartDate()));
				}
				if(formBean.getEndDate()!=null && !formBean.getEndDate().equalsIgnoreCase(""))
				{
					//start = (Date) sdf.parse(formBean.getStartDate());
					endDate = dateFormat1.format((Date) sdf.parse(formBean.getEndDate()));
				}
				con = DBConnection.getDBConnection();	
				StringBuffer query = new StringBuffer(SQL_SELECT_EMAIL_LOGS);
				if(startDate!=null && !startDate.equalsIgnoreCase("") && endDate!=null && !endDate.equalsIgnoreCase(""))
				{
					query.append(" DATE(SENT_ON) BETWEEN '"+startDate+"' and  '"+endDate+"' ");
				}
				
				
				query.append(" WITH UR");
				ps = con.prepareStatement(query.toString());
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					dto = new LogsDTO();
					dto.setEmailId(rs.getString("EMAIL_ID"));
					//dto.setEmailMessage(rs.getString("EMAIL_MSG"));
					//String emailContent = rs.getString("EMAIL_MSG");
					//StringBuffer sb = new StringBuffer(data);
					/*StringBuffer sb = new StringBuffer(data);*/
					dto.setEmailMessage(rs.getString("EMAIL_MSG").replaceAll("<.*?>", ""));
					dto.setEmailSubject(rs.getString("EMAIL_SUBJECT"));
					dto.setSentDate(rs.getString("SENT_ON"));
					dto.setResponseStatus(rs.getString("RESPONSE"));
					logsList.add(dto);
				}
			}
			catch(Exception e)
			{
				e.getMessage();
			}
			finally
			{

				try {
					DBConnection.releaseResources(con,ps, rs);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					logger.error(ex);
				}
				
			
			}
			return logsList;
	}

	@Override
	public ArrayList<LogsDTO> getSMSLogs(DownloadLogsFormBean formBean)
			throws DAOException {
		// TODO Auto-generated method stub
		 ArrayList<LogsDTO> logsList = new ArrayList<LogsDTO>();
		 LogsDTO dto = null;
		 Connection con = null;
			PreparedStatement ps = null;			
			ResultSet rs = null;
			String startDate = null;
			String endDate =null;
			try{
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");			
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
				if(formBean.getStartDate()!=null && !formBean.getStartDate().equalsIgnoreCase(""))
				{
					//start = (Date) sdf.parse(formBean.getStartDate());
					startDate = dateFormat1.format((Date) sdf.parse(formBean.getStartDate()));
				}
				if(formBean.getEndDate()!=null && !formBean.getEndDate().equalsIgnoreCase(""))
				{
					//start = (Date) sdf.parse(formBean.getStartDate());
					endDate = dateFormat1.format((Date) sdf.parse(formBean.getEndDate()));
				}
				con = DBConnection.getDBConnection();	
				StringBuffer query = new StringBuffer(SQL_SELECT_SMS_LOGS);
				if(startDate!=null && !startDate.equalsIgnoreCase("") && endDate!=null && !endDate.equalsIgnoreCase(""))
				{
					query.append(" DATE(SENT_ON) BETWEEN '"+startDate+"' and  '"+endDate+"' ");
				}
				
				
				query.append(" WITH UR");
				ps = con.prepareStatement(query.toString());
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					dto = new LogsDTO();
					dto.setMessageContent(rs.getString("MESSAGE_CONTENT"));
					dto.setMobileNo(rs.getString("MOBILE_NUMBER"));
					//logger.info("herhhhhhhhhhhh"+rs.getTimestamp("SENT_DATE"));
					dto.setSentDate(rs.getTimestamp("SENT_DATE").toString());
					//logger.info("Sent date==========="+dto.getSentDate());
					dto.setResponseStatus(rs.getString("SENT_STATUS"));
					logsList.add(dto);
				}
			}
			catch(Exception e)
			{
				e.getMessage();
			}
			finally
			{

				try {
					DBConnection.releaseResources(con,ps, rs);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					logger.error(ex);
				}
				
			
			}
			return logsList;
	}

	@Override
	public ArrayList<LogsDTO> getUserMstrHistLogs(DownloadLogsFormBean formBean)
			throws DAOException {
		// TODO Auto-generated method stub
		 ArrayList<LogsDTO> logsList = new ArrayList<LogsDTO>();
		 LogsDTO dto = null;
		 Connection con = null;
			PreparedStatement ps = null;			
			ResultSet rs = null;
			String startDate = null;
			String endDate = null;
			try{
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");			
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
				if(formBean.getStartDate()!=null && !formBean.getStartDate().equalsIgnoreCase(""))
				{
					//start = (Date) sdf.parse(formBean.getStartDate());
					startDate = dateFormat1.format((Date) sdf.parse(formBean.getStartDate()));
				}
				if(formBean.getEndDate()!=null && !formBean.getEndDate().equalsIgnoreCase(""))
				{
					//start = (Date) sdf.parse(formBean.getStartDate());
					endDate = dateFormat1.format((Date) sdf.parse(formBean.getEndDate()));
				}
				con = DBConnection.getDBConnection();	
				StringBuffer query = new StringBuffer(SQL_SELECT_USERMSTRHIST_LOGS);
				if(startDate!=null && !startDate.equalsIgnoreCase("") && endDate!=null && !endDate.equalsIgnoreCase(""))
				{
					query.append(" DATE(HISTORY_DT) BETWEEN '"+startDate+"' and  '"+endDate+"' ");
				}
				
				
				query.append(" WITH UR");
				ps = con.prepareStatement(query.toString());
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					dto = new LogsDTO();
					dto.setUserId(rs.getString("USER_ID"));
					dto.setUserLoginId(rs.getString("USER_LOGIN_ID"));
					dto.setFirstName(rs.getString("USER_FNAME"));
					dto.setMiddleName(rs.getString("USER_MNAME"));
					dto.setLastName(rs.getString("USER_LNAME"));
					dto.setMobileNo(rs.getString("USER_MOBILE_NUMBER"));
					dto.setEmailId(rs.getString("USER_EMAILID"));
					dto.setUserPassword(rs.getString("USER_PASSWORD"));
					dto.setUserPasswordExpDate(rs.getString("USER_PSSWRD_EXPRY_DT"));
					dto.setCreatedDate(rs.getString("CREATED_DT"));
					dto.setCreatedBy(String.valueOf(rs.getInt("CREATED_BY")));
					dto.setUpdatedDate(rs.getString("UPDATED_DT"));
					dto.setUpdatedBy(String.valueOf(rs.getInt("UPDATED_BY")));
					dto.setStatus(rs.getString("STATUS"));
					dto.setGroupId(rs.getString("GROUP_ID"));
					dto.setActorId(rs.getString("KM_ACTOR_ID"));
					dto.setOwnerId(rs.getString("KM_OWNER_ID"));
					dto.setLoginAttempt(rs.getString("LOGIN_ATTEMPTED"));
					dto.setLastLoginTime(rs.getString("LAST_LOGIN_TIME"));
					dto.setLogin_status(rs.getString("USER_LOGIN_STATUS"));
					dto.setCatogoryId(rs.getString("FAV_CATEGORY_ID"));
					dto.setElementID(rs.getString("ELEMENT_ID"));
					dto.setUser_alert(rs.getString("USER_ALERTS"));
					dto.setAlertId(rs.getString("ALERT_ID"));
					dto.setPartnerName(rs.getString("PARTNER_NAME"));
					dto.setPasswordResetTime(rs.getString("PASSWORD_RESET_TIME"));
					dto.setOlmId(rs.getString("OLM_ID"));
					dto.setRole(rs.getString("ROLE"));
					dto.setBussinessSegment(rs.getString("BUSINESS_SEGEMENT"));
					dto.setProcess(rs.getString("PROCESS"));
					dto.setActivity(rs.getString("ACTIVITY"));
					dto.setPartner(rs.getString("PARTNER"));
					dto.setLocation(rs.getString("LOCATION"));
					dto.setEndLoginTime(rs.getString("END_LOGIN_TIME"));
					dto.setTotalLogintime(rs.getString("TOTAL_LOGIN_TIME"));
					dto.setPbxId(rs.getString("PBX_ID"));
					//dto.setLastLoginTime(rs.getString("LAST_LOGIN_TIME"));
					dto.setHistoryDate(rs.getString("HISTORY_DT"));
					dto.setHistoryAction(rs.getString("HISTORY_ACTION"));
					logsList.add(dto);
				}
			}
			catch(Exception e)
			{
				e.getMessage();
			}
			finally
			{

				try {
					DBConnection.releaseResources(con,ps, rs);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					logger.error(ex);
				}
				
			
			}
			return logsList;
	}

	@Override
	public ArrayList<LogsDTO> getUserMstrLoginLogout(
			DownloadLogsFormBean formBean) throws DAOException {
		// TODO Auto-generated method stub
		 ArrayList<LogsDTO> logsList = new ArrayList<LogsDTO>();
		 LogsDTO dto = null;
		 Connection con = null;
			PreparedStatement ps = null;			
			ResultSet rs = null;
			String startDate = null;
			String endDate = null;
			try{
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");			
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
				if(formBean.getStartDate()!=null && !formBean.getStartDate().equalsIgnoreCase(""))
				{
					//start = (Date) sdf.parse(formBean.getStartDate());
					startDate = dateFormat1.format((Date) sdf.parse(formBean.getStartDate()));
				}
				if(formBean.getEndDate()!=null && !formBean.getEndDate().equalsIgnoreCase(""))
				{
					//start = (Date) sdf.parse(formBean.getStartDate());
					endDate = dateFormat1.format((Date) sdf.parse(formBean.getEndDate()));
				}
				con = DBConnection.getDBConnection();	
				StringBuffer query = new StringBuffer(SQL_SELECT_LOGIN_LOGOUT);
				if(startDate!=null && !startDate.equalsIgnoreCase("") && endDate!=null && !endDate.equalsIgnoreCase(""))
				{
					query.append(" DATE(LOGIN_DATE) BETWEEN '"+startDate+"' and  '"+endDate+"' ");
				}
				
				
				query.append(" WITH UR");
				ps = con.prepareStatement(query.toString());
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					dto = new LogsDTO();
					dto.setUserLoginId(rs.getString("USER_LOGIN_ID"));
					dto.setLastLoginTime(rs.getString("LOGIN_TIME"));
					dto.setElementID(String.valueOf(rs.getInt("ELEMENT_ID")));
					dto.setFavID(String.valueOf(rs.getInt("FAV_CATEGORY_ID")));
					dto.setFirstName(rs.getString("USER_FNAME"));
					dto.setLastName(rs.getString("USER_LNAME"));
					dto.setLogDate(rs.getString("LOGIN_DATE"));
					dto.setLogoutTime(rs.getString("LOGOUT_TIME"));
					dto.setSessionID(rs.getString("SESSION_ID"));
					dto.setClientIp(rs.getString("CLIENT_IP"));
					logsList.add(dto);
				}
			}
			catch(Exception e)
			{
				e.getMessage();
			}
			finally
			{

				try {
					DBConnection.releaseResources(con,ps, rs);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					logger.error(ex);
				}
				
			
			}
			return logsList;

	}
	
	
	public String getLogsData(String logsTableName,DownloadLogsFormBean formBean)throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1=null;
		ResultSet rs1=null;
		ResourceBundle rb=ResourceBundle.getBundle("ApplicationResources");
		String schemaName=rb.getString("lms.schema.bulk.download");
		
		StringBuffer query1 = new StringBuffer("select distinct(colname) from syscat.columns where TABSCHEMA='"+schemaName+"' and ");
		if(logsTableName.contains(","))
		{
			query1.append(" tabname in(");
			String tabnames[]=logsTableName.split(",");
			for(int i=0;i<tabnames.length;i++)
			{
				query1.append("'"+tabnames[i]+"'");
				if(i!=tabnames.length-1)
					query1.append(",");
				else
					query1.append(")");
					
				}
		
			}
		else
		{
			query1.append(" tabname =?");
		}
		
		StringBuffer query2 = null;
		String logsData="";
		String startDate = null;
		String endDate = null;
		logger.info("logsTableName"+logsTableName);
		try{
			if(logsTableName.equalsIgnoreCase("CUSTOMER_SEND_SMS_DETAILS"))
			{
				query2=new StringBuffer("SELECT * FROM CUSTOMER_SEND_SMS_DETAILS WHERE DATE(SENT_ON)");
			}
			else if(logsTableName.equalsIgnoreCase("EMAIL_SENT_HISTORY"))
			{
				query2=new StringBuffer("SELECT * FROM EMAIL_SENT_HISTORY WHERE DATE(SENT_ON)");
			}
			else if(logsTableName.equalsIgnoreCase("BULK_DATA_TRANSACTION_LOGS"))
			{
				query2=new StringBuffer("SELECT * FROM BULK_DATA_TRANSACTION_LOGS WHERE DATE(DOWNLOAD_TIME)");
			}
			else if(logsTableName.equalsIgnoreCase("USER_MSTR_HIST"))
			{
				query2=new StringBuffer("SELECT * FROM USER_MSTR_HIST WHERE DATE(HISTORY_DT)");
			}
			else if(logsTableName.equalsIgnoreCase("KM_LOGIN_DATA"))
			{
				if(formBean.getFlagStatus()!=null && !"".equalsIgnoreCase(formBean.getFlagStatus())&& "count".equalsIgnoreCase(formBean.getFlagStatus()))
				{
					query2=new StringBuffer("SELECT count(LOGIN_TIME) as LOGIN_COUNT ,count(LOGOUT_TIME) as LOGOUT_COUNT , USER_LOGIN_ID FROM KM_LOGIN_DATA  where date(LOGIN_DATE)");
				}
				else
				query2=new StringBuffer("SELECT * FROM KM_LOGIN_DATA WHERE DATE(LOGIN_DATE)");
			}
			else if(logsTableName.equalsIgnoreCase("ASSIGNMENT_MATRIX_HIST"))
			{
				query2=new StringBuffer("SELECT * FROM ASSIGNMENT_MATRIX_HIST WHERE DATE(HISTORY_DT)");
			}
			else if(logsTableName.equalsIgnoreCase("LEAD_SEARCH_TRANSACTION"))
			{
				query2=new StringBuffer("SELECT * FROM LEAD_SEARCH_TRANSACTION WHERE DATE(SEARCH_TIME)");
			}
			
			else if(logsTableName.equalsIgnoreCase("LEAD_CAPTURE,LEAD_CAPTURE_DATA"))
			{
				query2=new StringBuffer("SELECT * FROM LEAD_CAPTURE lc,LEAD_CAPTURE_DATA lcd WHERE lc.LEAD_CAPTURED_DATA_ID = lcd. LEAD_CAPTURED_DATA_ID and DATE(lc.CREATE_TIME) ");
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");			
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
			if(formBean.getStartDate()!=null && !formBean.getStartDate().equalsIgnoreCase(""))
			{
				startDate = dateFormat1.format((Date) sdf.parse(formBean.getStartDate()));
			}
			if(formBean.getEndDate()!=null && !formBean.getEndDate().equalsIgnoreCase(""))
			{
				endDate = dateFormat1.format((Date) sdf.parse(formBean.getEndDate()));
			}
			if(startDate!=null && !startDate.equalsIgnoreCase("") && endDate!=null && !endDate.equalsIgnoreCase(""))
			{
				query2.append(" BETWEEN '"+startDate+"' and  '"+endDate+"' ");
			}
			if(!logsTableName.equalsIgnoreCase("CUSTOMER_SEND_SMS_DETAILS")&&!logsTableName.equalsIgnoreCase("EMAIL_SENT_HISTORY")&& !logsTableName.equalsIgnoreCase("LEAD_CAPTURE,LEAD_CAPTURE_DATA") && formBean.getUserId()!=null && !"".equalsIgnoreCase(formBean.getUserId()))
			{
					if(formBean.getUserId().contains(","))
			{
				
				if(logsTableName.equalsIgnoreCase("ASSIGNMENT_MATRIX_HIST")||logsTableName.equalsIgnoreCase("LEAD_SEARCH_TRANSACTION"))
					query2.append(" AND OLM_ID in(");
				else
				query2.append(" AND USER_LOGIN_ID in(");
				String userId[]=formBean.getUserId().split(",");
				for(int i=0;i<userId.length;i++)
				{
					query2.append("'"+userId[i]+"'");
					if(i!=userId.length-1)
						query2.append(",");
					else
						query2.append(")");
						
					}
				}
			
			else
			{
				if(logsTableName.equalsIgnoreCase("ASSIGNMENT_MATRIX_HIST")||logsTableName.equalsIgnoreCase("LEAD_SEARCH_TRANSACTION"))
				{
					
					query2.append("AND OLM_ID='"+formBean.getUserId()+"'");
				}	
				else
				query2.append("AND USER_LOGIN_ID='"+formBean.getUserId()+"'");
		
			}
			
		}
			else if(logsTableName.equalsIgnoreCase("LEAD_CAPTURE,LEAD_CAPTURE_DATA") && formBean.getLeadCaptureId()!=null && !"".equalsIgnoreCase(formBean.getLeadCaptureId()))
				{
					if(formBean.getLeadCaptureId().contains(","))
					{
						query2.append(" and lc.LEAD_CAPTURED_DATA_ID in(");
						String leadCaptureDataId[]=formBean.getLeadCaptureId().split(",");
						for(int i=0;i<leadCaptureDataId.length;i++)
						{
							query2.append("'"+leadCaptureDataId[i]+"'");
							if(i!=leadCaptureDataId.length-1)
								query2.append(",");
							else
								query2.append(")");
								
							}
					
						
										
				}
					else
					{
						query2.append(" and lc.LEAD_CAPTURED_DATA_ID = '"+formBean.getLeadCaptureId()+"' ");
					}
				}
			
			
			String callValue="";
			
			con = DBConnection.getDBConnection();
			
			if(formBean.getFlagStatus()!=null && !"".equalsIgnoreCase(formBean.getFlagStatus())&& "count".equalsIgnoreCase(formBean.getFlagStatus()))
			{
				query2.append(" GROUP BY USER_LOGIN_ID");
				
				int i = 0;
				ArrayList<String> columns = new ArrayList<String>();
				columns.add("USER_LOGIN_ID");
				columns.add("LOGIN_COUNT");
				columns.add("LOGOUT_COUNT");
				//columns.add("START_DATE");
				//columns.add("END_DATE");
				logsData += "USER_LOGIN_ID,LOGIN_COUNT,LOGOUT_COUNT";
				ps1=con.prepareStatement(query2.toString());
				rs1=ps1.executeQuery();
				while(rs1.next())
				{ 
					
					logsData += "\n";
					i = 0;
					for(int j = 0; j < columns.size(); j++) {
						callValue=rs1.getString(columns.get(j));
						if(callValue!=null && !"".equalsIgnoreCase(callValue))
						{
							//logger.info("iutuitiu"+callValue);
						callValue=callValue.replaceAll("<.*?>", "");
						//logger.info("Call Value-------------"+callValue);
						callValue=callValue.replaceAll(",", ".");
						callValue=callValue.replaceAll("\\s+", " ");
						//logger.info("callllllll"+callValue);
						}
						if(i != 0) 
							logsData += ",";
						i++;
						if(callValue==null)callValue="";
						logsData += callValue; // code chanded by pratap as null is not a valid for user on 10-Dec 2013
						
						/*if(j==columns.size()-1)
						{
							logsData +=","+startDate+","+endDate;
						}*/
						//data +=rs.getString(columns.get(j));
					}
					//logger.info("trrrrrrrrrrrety"+logsData);
					//logs.add(logsData);
				}
			}
			else{
				query2.append(" WITH UR");
			ps = con.prepareStatement(query1.toString());
			if(!logsTableName.contains(","))
			{
			ps.setString(1,logsTableName);
			}
			rs = ps.executeQuery();
			int i = 0;
			//ArrayList<String> logs = new ArrayList<String>();
			ArrayList<String> columns = new ArrayList<String>();
			while(rs.next()) { 
				if(i != 0) 
					logsData += ",";
				i++;
				String value = rs.getString(1);
				logsData += value;
				columns.add(value);
			}
			//logger.info("trrrrrrrrrrrety"+logsData);
			//logs.add(logsData);
			ps = null;
			rs = null;
			
			ps = con.prepareStatement(query2.toString());
			rs = ps.executeQuery();
			while(rs.next()) { 
				
				logsData += "\n";
				i = 0;
				for(int j = 0; j < columns.size(); j++) {
					callValue=rs.getString(columns.get(j));
					if(callValue!=null && !"".equalsIgnoreCase(callValue))
					{
						//logger.info("iutuitiu"+callValue);
					callValue=callValue.replaceAll("<.*?>", "");
					//logger.info("Call Value-------------"+callValue);
					callValue=callValue.replaceAll(",", ".");
					callValue=callValue.replaceAll("\\s+", " ");
					//logger.info("callllllll"+callValue);
					}
					if(i != 0) 
						logsData += ",";
					i++;
					if(callValue==null)callValue="";
					logsData += callValue; // code chanded by pratap as null is not a valid for user on 10-Dec 2013
					//data +=rs.getString(columns.get(j));
				}
				//logger.info("trrrrrrrrrrrety"+logsData);
				//logs.add(logsData);
			}
		  }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new DAOException("Exception occured while get Report Data :  "+ e.getMessage(),e);
		
		}
		finally
		{

			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		
		}
		return logsData;
	}
}
