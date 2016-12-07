/*
 * Created on Jun 11, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.lms.dao.impl;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

//import com.ibm.km.common.Constants;
import com.ibm.lms.common.Constants;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.dao.LoginDao;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.sms.SendSMSXML;

/**
 * @author Atul
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LoginDaoImpl implements LoginDao {

	 
	public static Logger logger = Logger.getLogger(LoginDaoImpl.class);

	protected static final String SQL_GET_EMAIL_ID ="SELECT USER_LOGIN_ID,USER_EMAILID,KM_ACTOR_ID,STATUS,TIMESTAMPDIFF(4, CAST(CURRENT TIMESTAMP-PASSWORD_RESET_TIME AS CHAR(22))) AS MINUTES FROM USER_MSTR WHERE USER_LOGIN_ID=?";
	//Changes made by Anil
	protected static final String SQL_UPDATE_PASSWORD ="UPDATE  USER_MSTR SET USER_PASSWORD=?, LAST_LOGIN_TIME=null,LOGIN_ATTEMPTED=0,USER_PSSWRD_EXPRY_DT= current timestamp + 45 days,PASSWORD_RESET_TIME = current timestamp  WHERE USER_LOGIN_ID=?";
	
	protected static final String SQL_UPDATE_PASSWORD_EXPIRY_DATE="UPDATE  USER_MSTR SET USER_PSSWRD_EXPRY_DT = current timestamp + 45 days, LOGIN_ATTEMPTED=0  WHERE USER_LOGIN_ID=?";
	
	protected static final String SQL_GET_OLM_ID ="SELECT OLM_ID FROM USER_MSTR WHERE USER_LOGIN_ID= ? with ur";
	protected static final String SQL_GET_OLM_ID_STATUS ="SELECT STATUS FROM apps.BTVL_HRMS_L5D_EMP_V  WHERE USER_NAME=?";
	
	protected static final String SQL_GET_DOCUMENT_COUNT_SUPER_ADMIN ="SELECT count(*) as docCount FROM KM_DOCUMENT_MSTR where PUBLISHING_END_DT >= current date and PUBLISHING_END_DT <= (current date + 7 days) with UR";
	
	protected static final String SQL_GET_DOCUMENT_COUNT_ADMIN ="SELECT count(*) as docCount FROM KM_DOCUMENT_MSTR where PUBLISHING_END_DT >= current date and PUBLISHING_END_DT <= (current date + 7 days) and (DOCUMENT_PATH like '%/";
	
	
	protected static final String SQL_GET_FAVORITES = "select DOCUMENT_ID from KM_FAVORITE_DOCUMENTS where USER_ID=? ";
	
	protected static final String SQL_GET_LAST_LOGIN = "SELECT max(LOGIN_TIME) as LOGIN_TIME " +
			" FROM KM_LOGIN_DATA where upper(USER_LOGIN_ID) = upper(?) with ur";
	
	/* Added by Parnika for LMS Phase 2 */
	
	protected static final String SQL_GET_USER_CIRCLE_LIST = "SELECT DISTINCT(CM.CIRCLE_MSTR_ID) FROM USER_MAPPING UM, CIRCLE_MSTR CM WHERE UM.CIRCLE_ID = CM.CIRCLE_ID AND UM.LOB_ID = CM.LOB_ID AND UM.USER_LOGIN_ID = ? WITH UR";
	protected static final String SQL_GET_USER_LOB_LIST = "SELECT DISTINCT(UM.LOB_ID) FROM USER_MAPPING UM WHERE UM.USER_LOGIN_ID = ? WITH UR";

	
	/* End of changes by Parnika */
	
	
	//Added by srikant
private static LoginDaoImpl loginDaoImpl=null;
	
	private LoginDaoImpl(){
		
	}
	
	public static LoginDaoImpl loginDaoInstance()
	{
		if(loginDaoImpl==null)
		{
			loginDaoImpl=new LoginDaoImpl();
		}
		return loginDaoImpl;
		
	}
	
	public boolean isValidUser(String userLoginId) throws LMSException {	

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String olmId = "";
		boolean isValidUser = false;
		logger.info("Entered in isValidUser method for LDAP validation.");
		try {
			
			// get OLM_ID from table
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_GET_OLM_ID);
			ps.setString(1, userLoginId);
			rs = ps.executeQuery();
			if (rs.next()) {
				
				olmId = rs.getString("OLM_ID");
			}
			logger.info("olmId : "+olmId);
			// validate from HRMS
			if(!"".equals(olmId) && null != olmId)
			{
				con = DBConnection.getOLMSConnection();
				ps = con.prepareStatement(SQL_GET_OLM_ID_STATUS);
				ps.setString(1, olmId);
				rs = ps.executeQuery();
				if (rs.next()) {
					String status = rs.getString("STATUS");
					logger.info("OLM ID status : "+status);
					if(status.equalsIgnoreCase("ACTIVE"))
					{
						isValidUser = true;
					}
				}
			}		
		} catch (SQLException e) {
			
			throw new LMSException(e.getMessage());
		} catch (Exception e) {
			throw new LMSException(e.getMessage());
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				//logger.info("Finally called.");
				throw new LMSException(e.getMessage());
			}
		}
		logger.info("Exit from isValidUser method");
		return isValidUser;
		}
		
		public boolean isValidOlmId(String userOLMId) throws LMSException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;	
		boolean isValidUser = false;
		logger.info("Validated user for OLM ID : "+userOLMId);
		try {
			
			
			// validate from HRMS
			if(!"".equals(userOLMId))
			{
				con = DBConnection.getOLMSConnection();
				ps = con.prepareStatement(SQL_GET_OLM_ID_STATUS);
				ps.setString(1, userOLMId);
				rs = ps.executeQuery();
				if (rs.next()) {
					String status = rs.getString("STATUS");
					if(status.equalsIgnoreCase("ACTIVE"))
					{
						isValidUser = true;
					}
				}
			}		
		} catch (SQLException e) {
			
			throw new LMSException(e.getMessage());
		} catch (Exception e) {
			throw new LMSException(e.getMessage());
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				throw new LMSException(e.getMessage());
			}
		}
		logger.info("Exit from isValidOlmId method");
		return isValidUser;
	}

		
	public ArrayList getEmailId(String userLoginId) throws LMSException {
		Connection con = null;
		ResultSet rs = null;
		ArrayList alList = null;
		PreparedStatement ps = null;
		logger.info("Entered in getEmailId method");
		try {
			StringBuffer query=new StringBuffer(SQL_GET_EMAIL_ID);
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query.append(" with ur ").toString());
			ps.setString(1, userLoginId);
			rs = ps.executeQuery();
			while (rs.next()) {
				alList = new ArrayList();
				alList.add(rs.getString("USER_LOGIN_ID"));
				alList.add(rs.getString("USER_EMAILID"));
				alList.add(rs.getString("KM_ACTOR_ID"));
				alList.add(rs.getString("STATUS"));
				if(rs.getString("MINUTES")!=null)
					alList.add(rs.getString("MINUTES"));
				/*else
					alList.add(Constants.CATEGORY_CSR);*/
			}
		} catch (SQLException e) {
			logger.error(
				"SQL Exception occured while getting Email Id."
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException("SQL Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error(
				"Exception occured while getting Email Id."
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error(
					"DAOException occured while getting Email Id."
						+ "Exception Message: "
						+ e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}
		}
		logger.info("Exit from getEmailId method");
		return alList;
	}

	 
	public void updatePassword(String userLoginId, String password)
		throws LMSException {
		Connection con = null;
		ResultSet rs = null;
		ArrayList alList = null;
		PreparedStatement ps = null;
		logger.info("Entered in updatePassword method");
		try {
			StringBuffer query=new StringBuffer(SQL_UPDATE_PASSWORD);
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query.append(" with ur").toString());
			ps.setString(1, password);
			ps.setString(2, userLoginId);
			ps.executeUpdate();
			logger.info("Exit from updatePassword method");
		} catch (SQLException e) {
			logger.error(
				"SQL Exception occured while updating password."
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException("SQL Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error(
				"Exception occured while updating password."
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error(
					"DAOException occured while updating password."
						+ "Exception Message: "
						+ e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}
		}
	}

	/**
	 * @param userLoginId
	 */
	public void updatePasswordExpiryDate(String userLoginId)throws LMSException {
		Connection con = null;
		ResultSet rs = null;
		ArrayList alList = null;
		PreparedStatement ps = null;
		logger.info("Entered in updatePassword method");
		try {
			StringBuffer query=new StringBuffer(SQL_UPDATE_PASSWORD_EXPIRY_DATE);
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query.append(" with ur").toString());
			ps.setString(1, userLoginId);
			ps.executeUpdate();
			logger.info("Exit from updatePassword method");
		} catch (SQLException e) {
			logger.error(
				"SQL Exception occured while updating password."
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException("SQL Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error(
				"Exception occured while updating password."
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error(
					"DAOException occured while updating password."
						+ "Exception Message: "
						+ e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}
		}
	}	 
	
   public String getConfigValue(String key) throws LMSException {
	   Connection con = null;
	   ResultSet rs = null;
	   PreparedStatement ps = null;
	   String val="";
	   logger.info("Entered in getConfigValue method");
	   try {
		   con = DBConnection.getDBConnection();
		   ps = con.prepareStatement("SELECT * from KM_document_mstr  with ur");
		   
		   rs=ps.executeQuery();
		   while(rs.next()){
			  
			  
			   System.out.print(rs.getString("DOCUMENT_DISPLAY_NAME")+"    :   "+rs.getString("DOCUMENT_PATH")); 
			   //logger.info("       CircleId : "+extractCircleId(rs.getString("DOCUMENT_PATH"), 3));
		   }
			
	   } catch (SQLException e) {
		   logger.error(
			   "SQL Exception occured while getting configuration value "
				   + e.getMessage());
		   throw new LMSException("SQL Exception: " + e.getMessage(), e);
	   } catch (Exception e) {
		   logger.error(
			   "Exception occured wwhile getting configuration value."
				   + "Exception Message: "
				   + e.getMessage());
		   throw new LMSException(" Exception: " + e.getMessage(), e);
	   } finally {
		   try {
			   DBConnection.releaseResources(con, ps, rs);
		   } catch (Exception e) {
			   logger.error(
				   "DAOException occured while getting configuration value."
					   + "Exception Message: "
					   + e.getMessage());
			   throw new LMSException("DAO Exception: " + e.getMessage(), e);
		   }
	   }
	   return val;
   }
   public String extractCircleId(String path, int level) throws LMSException {
		
		String circleId = null;
		StringTokenizer token= new StringTokenizer(path,"/");
		int i=0;
	try{	
		while(token.hasMoreTokens()){
			i++;
			String circle=token.nextToken();
			
			if(i==level){
				circleId=circle;
				break;
			}
			
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	return circleId;
		
		
}
   public void getConfigValue(String elementId,String newElementName ) throws LMSException {
	   Connection con = null;
	   ResultSet rs = null;
	   PreparedStatement ps = null;
	   String val="";
	   logger.info("Entered in getConfigValue method");
	   try {
		   con = DBConnection.getDBConnection();
		   ps = con.prepareStatement("UPDATE KM_ELEMENT_MSTR SET ELEMENT_NAME = ? WHERE ELEMENT_ID  = ? with  ur");
		   ps.setString(1, newElementName);
		   ps.setString(2, elementId);
		   ps.executeUpdate();
		   
			
	   } catch (SQLException e) {
		   logger.error(
			   "SQL Exception occured while getting configuration value "
				   + e.getMessage());
		   throw new LMSException("SQL Exception: " + e.getMessage(), e);
	   } catch (Exception e) {
		   logger.error(
			   "Exception occured wwhile getting configuration value."
				   + "Exception Message: "
				   + e.getMessage());
		   throw new LMSException(" Exception: " + e.getMessage(), e);
	   } finally {
		   try {
			   DBConnection.releaseResources(con, ps, null);
		   } catch (Exception e) {
			   logger.error(
				   "DAOException occured while getting configuration value."
					   + "Exception Message: "
					   + e.getMessage());
			   throw new LMSException("DAO Exception: " + e.getMessage(), e);
		   }
	   }
	  
   }
   
  /* public int getExpiredDocumentCount(UserMstr userBean) throws LMSException {
	   Connection con = null;
	   ResultSet rs = null;
	   PreparedStatement ps = null;
	   String val="";
	   int docCount = 0;
	   StringBuffer sb = new StringBuffer(SQL_GET_DOCUMENT_COUNT_ADMIN);
	   logger.info("Entered in getExpiredDocumentCount method");
	   try {
		   con = DBConnection.getDBConnection();
		   if(userBean.getKmActorId().equals(Constants.SUPER_ADMIN))
		   {
			   ps = con.prepareStatement(SQL_GET_DOCUMENT_COUNT_SUPER_ADMIN);   
		   } 
		   else{
			   sb.append(userBean.getElementId());
			   sb.append("/%' or DOCUMENT_PATH like '%/");
			   sb.append(userBean.getElementId());
			   sb.append("' )with UR");
			   ps = con.prepareStatement(sb.toString());   
		   }
		   rs=ps.executeQuery();
		   if(rs.next()){
			   docCount = rs.getInt("docCount");
			  
			  System.out.print("Count " + docCount); 
			   
		   }
			
	   } catch (SQLException e) {
		   e.printStackTrace();
		   logger.error(
			   "SQL Exception occured while getExpiredDocumentCount "
				   + e.getMessage());
		   throw new LMSException("SQL Exception: " + e.getMessage(), e);
	   } catch (Exception e) {
		   e.printStackTrace();
		   logger.error(
			   "Exception occured while getExpiredDocumentCount."
				   + "Exception Message: "
				   + e.getMessage());
		   throw new LMSException(" Exception: " + e.getMessage(), e);
	   } finally {
		   try {
			   DBConnection.releaseResources(con, ps, rs);
		   } catch (Exception e) {
			   logger.error(
				   "DAOException occured while getExpiredDocumentCount."
					   + "Exception Message: "
					   + e.getMessage());
			   throw new LMSException("DAO Exception: " + e.getMessage(), e);
		   }
	   }
	   return docCount;
   }*/
   
  // get favorites of loged in user 
   
   public List getFavorites(int userId) throws LMSException {
		Connection con = null;
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		List favoritesList = new ArrayList();
		List favListId = new ArrayList();
		Map hm = null;
		logger.info("Entered in getFavorites method to get favorits documentd of a user");
		try {
			//String sql = SQL_GET_FAVORITES;
			StringBuffer query =new StringBuffer(SQL_GET_FAVORITES);
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query.append(" with ur ").toString());			
			ps.setInt(1,userId);
			rs=ps.executeQuery();
			while (rs.next()) {			
				favListId.add(rs.getInt("DOCUMENT_ID"));	
							
			}
			
		}
		catch (SQLException e) {
			logger.error("SQL Exception occured while getting Favorites." + "Exception Message: " + e.getMessage());
			throw new LMSException("SQL Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Exception occured while getting Favorites." + "Exception Message: " + e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		} finally {
			try {
				if(rs!=null)
				rs.close();
				DBConnection.releaseResources(con,ps,rs);
			} catch (Exception e) {
				logger.error("DAOException occured while getting Favorites." + "Exception Message: " + e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}
		}
		
		logger.info("Exit from  getFavorites method");
		return favListId;
	}
   
   //method added by Karan to get the last login time on 29 Jan 2013
   
   public Timestamp getLastLogin(String userLoginId) throws LMSException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Timestamp lastLoginTime = null;
		logger.info("Entered in getLastLogin method");
		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_GET_LAST_LOGIN);
			ps.setString(1, userLoginId);
			rs = ps.executeQuery();
			while(rs.next()) {
				lastLoginTime= rs.getTimestamp(1);	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while getting Last Login Time."
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException("SQL Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while getting Last Login Time"
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error(
					"DAOException occured while getting Last Login Time."
						+ "Exception Message: "
						+ e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}
		}
		logger.info("Exit from getLastLogin method");
		return lastLoginTime;
	}


public int getExpiredDocumentCount(UserMstr userBean) throws LMSException {
	// TODO Auto-generated method stub
	return 0;
}

	/* Added by Parnika for LMS Phase 2 */

	public ArrayList getUserCircleList(String userLoginId) throws LMSException {
	   Connection con = null;
	   ResultSet rs = null;
	   PreparedStatement ps = null;
	   ArrayList circleList = new ArrayList();
	   logger.info("Entered in getUserCircleList method");
	   try {
		   con = DBConnection.getDBConnection();
		   ps = con.prepareStatement(SQL_GET_USER_CIRCLE_LIST);
		   ps.setString(1, userLoginId);
		   rs=ps.executeQuery();
			while (rs.next()) {			
				circleList.add(rs.getInt("CIRCLE_MSTR_ID"));
			}
			
	   } catch (SQLException e) {
		   logger.error(
			   "SQL Exception occured while getting getUserCircleList value "
				   + e.getMessage());
		   throw new LMSException("SQL Exception: " + e.getMessage(), e);
	   } catch (Exception e) {
		   logger.error(
			   "Exception occured wwhile getting getUserCircleList value."
				   + "Exception Message: "
				   + e.getMessage());
		   throw new LMSException(" Exception: " + e.getMessage(), e);
	   } finally {
		   try {
			   DBConnection.releaseResources(con, ps, null);
		   } catch (Exception e) {
			   logger.error(
				   "DAOException occured while getting getUserCircleList value."
					   + "Exception Message: "
					   + e.getMessage());
			   throw new LMSException("DAO Exception: " + e.getMessage(), e);
		   }
	   }
	   
	   return circleList;
	  
}
	
	public ArrayList getUserLobList(String userLoginId) throws LMSException {
		   Connection con = null;
		   ResultSet rs = null;
		   PreparedStatement ps = null;
		   ArrayList lobList = new ArrayList();
		   logger.info("Entered in getUserLobList method");
		   try {
			   con = DBConnection.getDBConnection();
			   ps = con.prepareStatement(SQL_GET_USER_LOB_LIST);
			   ps.setString(1, userLoginId);
			   rs=ps.executeQuery();
				while (rs.next()) {			
					lobList.add(rs.getInt("LOB_ID"));
				}
				
		   } catch (SQLException e) {
			   logger.error(
				   "SQL Exception occured while getting getUserLobList value "
					   + e.getMessage());
			   throw new LMSException("SQL Exception: " + e.getMessage(), e);
		   } catch (Exception e) {
			   logger.error(
				   "Exception occured wwhile getting getUserLobList value."
					   + "Exception Message: "
					   + e.getMessage());
			   throw new LMSException(" Exception: " + e.getMessage(), e);
		   } finally {
			   try {
				   DBConnection.releaseResources(con, ps, null);
			   } catch (Exception e) {
				   logger.error(
					   "DAOException occured while getting getUserLobList value."
						   + "Exception Message: "
						   + e.getMessage());
				   throw new LMSException("DAO Exception: " + e.getMessage(), e);
			   }
		   }
		   
		   return lobList;
		  
	}
	/* End of changes by Parnika */

	String SELECT_SMS_TEMPLATE = "SELECT MESSAGE_TEMPLATE FROM ALERT_MSTR  WHERE ALERT_ID = '"+Constants.OTP_alertID+"' AND ACTIVE = 'A' WITH UR";
	final String INSERT_SMS_TRANSACTIONS = " INSERT INTO CUSTOMER_SEND_SMS_DETAILS( MOBILE_NUMBER, MESSAGE, STATUS, SENT_ON, CREATED_ON, RESPONSE_MSG) VALUES( ?, ?, ?, CURRENT TIMESTAMP, CURRENT TIMESTAMP, ?)";
		
	
	@SuppressWarnings("finally")
	public String smsUserList(String userLoginId,String contact) throws LMSException {
		   Connection con = null;
		   PreparedStatement ps1 = null;
		   PreparedStatement ps2 = null;
		   ResultSet rs1 = null;
	       String message="";
		   String OTPgen="";
		   
		   logger.info("Entered in smsuserlist method");
		   try {
			    OTPgen=GenerateOTPSendSMS();
			    con = DBConnection.getDBConnection();
			    
			    ps1 = con.prepareStatement(SELECT_SMS_TEMPLATE);
			    logger.info("ps1->"+ps1);
				rs1 = ps1.executeQuery();
					
				while(rs1.next())
				{   
					message = rs1.getString("MESSAGE_TEMPLATE");
					logger.info("message is****************"+message);
					message=message.replaceAll(Constants.OTP_VAR, ""+OTPgen);
					new SendSMSXML().sendSms(contact,message.toString());
					ps2=con.prepareStatement(INSERT_SMS_TRANSACTIONS);
					ps2.setString(1, contact+"");
					ps2.setString(2, message.toString());
					ps2.setInt(3, 1);
					ps2.setString(4, "");
					ps2.executeUpdate();
					 
				}
		
		   }
		   
		   catch (SQLException e) {
			   logger.error("SQL Exception occured while getting sms list flag1 " + e.getMessage());
			   throw new LMSException("SQL Exception: " + e.getMessage(), e);
		   } catch (Exception e) {
			   logger.error(
				   "Exception occured wwhile getting sms list flag2 ." + "Exception Message: " + e.getMessage());
			   throw new LMSException(" Exception: " + e.getMessage(), e);
		   } finally {
			   /*try {
				   DBConnection.releaseResources(con, ps1, );
			   } catch (Exception e) {
				   logger.error(
					   "DAOException occured while getting sms list flag3 ."
						   + "Exception Message: "
						   + e.getMessage());
				   throw new LMSException("DAO Exception: " + e.getMessage(), e);
			   }*/
			   logger.info("otpgen is********************"+OTPgen); 
			   return OTPgen;
		   }
		  
		  
		
}
   
	public String GenerateOTPSendSMS() throws LMSException
	{
		String chars="0123456789";
		final int OTP_LENGTH =8;
        Random rnd = new SecureRandom();
        StringBuilder OTP = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++)
        OTP.append(chars.charAt(rnd.nextInt(chars.length())));
        logger.info("otp generated is****************"+OTP.toString());
        return OTP.toString();
       
	}
   
}