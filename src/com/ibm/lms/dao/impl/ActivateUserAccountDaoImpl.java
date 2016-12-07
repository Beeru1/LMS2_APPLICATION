package com.ibm.lms.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.lms.common.DBConnection;
import com.ibm.lms.dao.ActivateUserAccountDao;
import com.ibm.lms.exception.ActivateUserAccountException;
import com.ibm.lms.exception.DAOException;


public class ActivateUserAccountDaoImpl implements ActivateUserAccountDao {

	/**
	 * Logger for this class. Use logger.log(message) for logging. Refer to
	 * 
	 * @link http://java.sun.com/j2se/1.4.2/docs/guide/util/logging/overview.html
	 *       for logging options and configuration.
	 */

	private static Logger logger = Logger.getLogger(ActivateUserAccountDaoImpl.class);
	//Added by srikant
 private static ActivateUserAccountDaoImpl activateUserAccountDaoImpl=null;
	
	private ActivateUserAccountDaoImpl(){
		
	}
	
	public static ActivateUserAccountDaoImpl activateUserAccountDaoInstance()
	{
		if(activateUserAccountDaoImpl==null)
		{
			activateUserAccountDaoImpl=new ActivateUserAccountDaoImpl();
		}
		return activateUserAccountDaoImpl;
		
	}

	public ArrayList getExpiredLocked(int actorId, int val, String flag)
			throws ActivateUserAccountException {

		logger.info("Retrieving List of Expired/Locked Users.");
		ArrayList list = new ArrayList();
		HashMap hm = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
		
		/*int superAdminId=Integer.parseInt(bundle.getString("Superadmin"));
		int lobAdminId=Integer.parseInt(bundle.getString("LOBAdmin"));
		int circleAdminId=Integer.parseInt(bundle.getString("CircleAdmin"));
		int circleUserId=Integer.parseInt(bundle.getString("CircleUser"));
		int circleCSRId=Integer.parseInt(bundle.getString("CircleCSR"));
		int categoryCSRId=Integer.parseInt(bundle.getString("CategoryCSR"));
		int tsgUserId=Integer.parseInt(bundle.getString("TSGUser"));*/
		
		String appendkmActorID="";
		try { 
			String sql = "";
			
/*			Superadmin=1
			LOBAdmin=5
			CircleAdmin=2
			CircleUser=3
			CircleCSR=4
			CategoryCSR=6
			ReportAdmin=7
			TSGUser=8
*/			
			
			  //Circle User
			  /* if(actorId==circleUserId) {
				appendkmActorID=categoryCSRId+","+circleCSRId+")";
				  //appendkmActorID=""+circleCSRId + categoryCSRId;
			  }
			  //Circle Admin
			  else if(actorId==circleAdminId) {
					appendkmActorID=circleUserId+","+categoryCSRId+","+tsgUserId+","+circleCSRId+")";
					  //appendkmActorID=""+circleUserId+circleCSRId+categoryCSRId;
		  }
			  //LOB admin
			  else if(actorId==lobAdminId) {
				appendkmActorID=circleUserId+","+categoryCSRId+","+tsgUserId+","+circleCSRId+","+circleAdminId+")";
				  //appendkmActorID=""+circleAdminId;
			  }
			  //Superadmin
			  if(actorId==superAdminId)  {
				appendkmActorID=circleUserId+","+categoryCSRId+","+tsgUserId+","+circleCSRId+","+circleAdminId+","+lobAdminId+")";
				  //appendkmActorID=lobAdminId+"";
			  } */
			  
/*			  //Superadmin
			  if(actorId==superAdminId)  {
				appendkmActorID=lobAdminId+")";
				  //appendkmActorID=lobAdminId+"";
			  } 
			  //LOB admin
			  else if(actorId==lobAdminId) {
				appendkmActorID=circleAdminId+")";
				  //appendkmActorID=""+circleAdminId;
			  }
			  //Circle Admin
				  else if(actorId==circleAdminId) {
						appendkmActorID=circleUserId+","+categoryCSRId+","+tsgUserId+","+circleCSRId+")";
						  //appendkmActorID=""+circleUserId+circleCSRId+categoryCSRId;
			  }
			  //Circle User
			  else if(actorId==circleUserId) {
				appendkmActorID=categoryCSRId+","+circleCSRId+")";
				  //appendkmActorID=""+circleCSRId + categoryCSRId;
			  }
			
*/			
			if ("Locked".equals(flag)) {
				sb.append("SELECT  USER_ID,USER_FNAME, USER_LOGIN_ID ");
				sb.append(" FROM  USER_MSTR WHERE LOGIN_ATTEMPTED >=? AND STATUS='A' "); // AND KM_ACTOR_ID in (");
				//sb.append(appendkmActorID);
				
				
			}
			if ("Expired".equals(flag)) {
				sb.append("SELECT  USER_ID,USER_FNAME, USER_LOGIN_ID");
				sb.append(" FROM USER_MSTR WHERE (USER_PSSWRD_EXPRY_DT ) < current timestamp and 0 = ? ");
				sb.append(" AND STATUS='A' "); //AND KM_ACTOR_ID in (");
				//sb.append(appendkmActorID);
			}
			if ("Forced-LogOff".equals(flag)) {
				sb.append("SELECT  USER_ID,USER_FNAME, USER_LOGIN_ID");
				sb.append(" FROM USER_MSTR where USER_LOGIN_STATUS='Y' and 0 = ? ");
				sb.append(" AND STATUS='A' "); // AND KM_ACTOR_ID in (");
				//sb.append(appendkmActorID);
			}
			con = getConnection();
			sql = sb.toString();
			ps = con.prepareStatement(sql+" with ur");
			ps.setInt(1, val);
			rs = ps.executeQuery();
			while (rs.next()) {
				hm = new HashMap();
				hm.put("USER_ID", new Integer(rs.getInt(1)));
				hm.put("USER_FNAME", rs.getString(2));
				hm.put("USER_LOGIN_ID", rs.getString(3));
				list.add(hm);
				hm = null;
			}
			logger.info("Exit from Retrieving List of Expired/Locked Users.");
			return list;
		} catch (SQLException e) {
			logger.error("SQL Exception occured while getting Lock Expired."
					+ "Exception Message: " + e.getMessage());
			throw new ActivateUserAccountException("SQLException: "
					+ e.getMessage(), e);
		} catch (Exception e) {
			logger.error("SQL Exception occured while getting Lock Expired."
					+ "Exception Message: " + e.getMessage());
			throw new ActivateUserAccountException("Exception: "
					+ e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con,ps,rs);
			} catch (Exception e) {
				logger.error("DAO Exception occured while getting Lock Expired."
					+ "DAO Exception Message: " + e.getMessage());
			throw new ActivateUserAccountException("DAO Exception: "
					+ e.getMessage(), e);
			}
		}

	}

	public int[] updateExpired(Integer userId[], String password, String flag)
			throws ActivateUserAccountException {
		int success[] = null;

		ArrayList list = new ArrayList();
		HashMap hm = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		logger.info("Entered in updateExpired.");
		try {

			String sql = "";
			if ("Locked".equals(flag)) {
				sb.append("UPDATE USER_MSTR SET UPDATED_DT =current timestamp, LOGIN_ATTEMPTED = 0 ");
				sb.append(" WHERE USER_ID=?");
			}
			if ("Expired".equals(flag)) {
				/*sb.append(" UPDATE USER_MSTR SET UPDATED_DT=current timestamp ");
				sb.append(" WHERE USER_ID=? ");*/
				// Unlock user functionality Updated
				sb.append(" UPDATE USER_MSTR SET UPDATED_DT=current timestamp ,USER_PSSWRD_EXPRY_DT = current timestamp + 45 days ");
				sb.append(" WHERE USER_ID=? ");
			}
			if ("Forced-LogOff".equals(flag)) {
				sb.append("UPDATE USER_MSTR SET USER_LOGIN_STATUS='N' ");
				sb.append("WHERE USER_ID=? ");
			}
			con = getConnection();
			sql = sb.toString();
			ps = con.prepareStatement(sql+" with ur");
			for (int i = 0; i < userId.length; i++) {
				if (userId[i] != null) {
					if ("Locked".equals(flag)) {
						//ps.setString(1, password);
						ps.setInt(1, userId[i].intValue());
					}
					if ("Expired".equals(flag)) {
						ps.setInt(1, userId[i].intValue());
					}
					if ("Forced-LogOff".equals(flag)) {
						ps.setInt(1, userId[i].intValue());
					}
					ps.addBatch();
				}
			}
			sb = null;
			success = ps.executeBatch();
			
		} catch (SQLException e) {
			logger.error("SQL Exception occured while find."
					+ "Exception Message: " + e.getMessage());
			throw new ActivateUserAccountException("SQLException: "
					+ e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Exception occured while find."
					+ "Exception Message: " + e.getMessage());
			throw new ActivateUserAccountException("Exception: "
					+ e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con,ps,null);
            } catch (Exception e) {
				logger.error("DAO Exception occured while find."
					+ "DAO Exception Message: " + e.getMessage());
			throw new ActivateUserAccountException("DAO Exception: "
					+ e.getMessage(), e);
			}
		}
		logger.info("Exit from updateExpired.");
		return success;
	}

	private Connection getConnection() throws ActivateUserAccountException {

		logger.info("Entered getConnection for operation ActivateUserAccount");

		try {
			return DBConnection.getDBConnection();
		} catch (DAOException e) {

			logger.info("Exception Occured while obtaining connection.");

			throw new ActivateUserAccountException(
					"Exception while trying to obtain a connection", e);
		}

	}

}
