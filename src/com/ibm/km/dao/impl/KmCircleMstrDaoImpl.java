package com.ibm.km.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.km.dao.KmCircleMstrDao;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.dto.CircleMstr;
import com.ibm.lms.exception.LMSException;
public class KmCircleMstrDaoImpl implements KmCircleMstrDao {

	/*
		 * Logger for the class.
		 */
	private static final Logger logger;

	static {

		logger = Logger.getLogger(KmCircleMstrDaoImpl.class);
	}

	protected static final String SQL_INSERT_WITH_ID =
		"INSERT INTO LMS.KM_CIRCLE_MSTR (CIRCLE_NAME, CIRCLE_ID, CREATED_DT, CREATED_BY, STATUS, SINGLE_SIGN_IN) VALUES (?, NEXTVAL FOR LMS.KM_CIRCLE_ID, current timestamp, ?, 'A', 'N')";

	protected static final String SQL_SELECT =
		"SELECT * FROM LMS.KM_CIRCLE_MSTR WHERE  STATUS = 'A' ";

	protected static final String SQL_SELECT_CIRCLE =
		"SELECT CIRCLE_NAME,CIRCLE_ID,CREATED_DT,CREATED_BY,STATUS, SINGLE_SIGN_IN FROM LMS.KM_CIRCLE_MSTR WHERE  STATUS = 'A' AND CIRCLE_ID!=0 ORDER BY CIRCLE_NAME ";

	protected static final String SQL_SELECT_ALL_CIRCLE =
		"SELECT CIRCLE_NAME,CIRCLE_ID,CREATED_DT,CREATED_BY,STATUS, SINGLE_SIGN_IN FROM LMS.KM_CIRCLE_MSTR WHERE  STATUS = 'A' ORDER BY CIRCLE_NAME ";

	protected static final String SQL_UPDATE =
		"UPDATE LMS.KM_CIRCLE_MSTR SET CIRCLE_NAME = ?, CIRCLE_ID = ?,CREATED_BY = ?, STATUS = ? WHERE CIRCLE_ID = ?";

	protected static final String SQL_DELETE = "DELETE FROM LMS.KM_CIRCLE_MSTR WHERE CIRCLE_ID = ?";

	//Inserting to the table KM_CIRCLE_MSTR
	
	public int insert(CircleMstr dto) throws LMSException {

		logger.info("Entered insert for table KM_CIRCLE_MSTR");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int rowsUpdated = 0;
		try {
			String sql = SQL_INSERT_WITH_ID;
			con = DBConnection.getDBConnection();
			
			ps = con.prepareStatement(sql);
			int paramCount = 1;
			
			/*Preparing the statement for insertion */
			
			ps.setString(paramCount++, dto.getCircleName().trim());
			ps.setInt(paramCount++, Integer.parseInt(dto.getCreatedBy()));
			
			rowsUpdated = ps.executeUpdate();
			

			logger.info(
				"Row insertion successful on table:KM_CIRCLE_MSTR. Inserted:"
					+ rowsUpdated
					+ " rows");

		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while inserting." + "Exception Message: " + e.getMessage());
			throw new LMSException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error(
				"Exception occured while inserting." + "Exception Message: " + e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.setAutoCommit(true);
					con.close();
				}
			} catch (Exception e) {
			}
		}

		return rowsUpdated;
	}



	public int updateCircle(CircleMstr dto) throws LMSException {

		logger.info("Entered update for table KM_CIRCLE_MSTR");

		Connection con = null;
		PreparedStatement ps = null;
		int numRows = -1;

		try {
			String sql = SQL_UPDATE;
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql);
			if (dto.getCircleName() == null)
				ps.setNull(1, Types.VARCHAR);
			else
				ps.setString(1, dto.getCircleName());
			if (dto.getCircleId() == null)
				ps.setNull(2, Types.DECIMAL);
			else
				ps.setString(2, dto.getCircleId());			
			if (dto.getCreatedBy() == null)
				ps.setNull(3, Types.DECIMAL);
			else
				ps.setString(3, dto.getCreatedBy());
			if (dto.getStatus() == null)
				ps.setNull(4, Types.CHAR);
			else
				ps.setString(4, dto.getStatus());
			ps.setString(5, dto.getCircleId());
			numRows = ps.executeUpdate();

			logger.info("Update successful on table:KM_CIRCLE_MSTR. Updated:" + numRows + " rows");

		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while update." + "Exception Message: " + e.getMessage());
			throw new LMSException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error(
				"Exception occured while update." + "Exception Message: " + e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return numRows;
	}

	

	//Populate the DTO object with the details of all the circles
	
	protected CircleMstr[] fetchMultipleResults(ResultSet rs) throws SQLException, LMSException {
		ArrayList results = new ArrayList();
		while (rs.next()) {
			CircleMstr dto = new CircleMstr();
			populateDto(dto, rs);
			results.add(dto);
		}
		CircleMstr retValue[] = new CircleMstr[results.size()];
		results.toArray(retValue);
		return retValue;
	}

	protected CircleMstr fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()) {
			CircleMstr dto = new CircleMstr();
			populateDto(dto, rs);
			return dto;
		} else
			return null;
	}

	protected static void populateDto(CircleMstr dto, ResultSet rs) throws SQLException {
		dto.setCircleName(rs.getString("CIRCLE_NAME"));

		dto.setCircleId(rs.getString("CIRCLE_ID"));

		dto.setCreatedDt(rs.getDate("CREATED_DT"));

		dto.setCreatedBy(rs.getString("CREATED_BY"));

		dto.setStatus(rs.getString("STATUS"));
		
		dto.setSingleSignInFlag(rs.getString("SINGLE_SIGN_IN"));

	}

	
	//Returns the details of all the circles in the database
	
	public CircleMstr[] getCircleData() throws LMSException {
		logger.info("Entered getCircleData for table:KM_CIRCLE_MSTR");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_SELECT+"ORDER BY CIRCLE_ID");
			rs = ps.executeQuery();
			return fetchMultipleResults(rs);
		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while getCircleData"
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error(
				"Exception occured while getCircleData" + "Exception Message: " + e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.ibm.km.dao.KmCircleMstrDao#checkCircleName(java.lang.String)
	 */
	public boolean checkCircleName(String circleName) throws LMSException {

		logger.info("Entered checkCircle for table:KM_CIRCLE_MSTR");
		logger.info("Circle Name ="+circleName);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isCircleExists = false;

		try {
			String sql = SQL_SELECT + "  AND UPPER(LMS.KM_CIRCLE_MSTR.CIRCLE_NAME) = ? ";
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, circleName.toUpperCase());
			rs = ps.executeQuery();
			
			/*Checking whether the circle name already exists in the table */
			
			while(rs.next()){
				isCircleExists = true;
			}
			return isCircleExists;
		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while checkCircle." + "Exception Message: " + e.getMessage());
			throw new LMSException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error("Exception occured while checking Circle on name." + "Exception Message: " + e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.ibm.km.dao.KmCircleMstrDao#getAllCircles()
	 */
	public ArrayList getAllCircles() throws LMSException{
		Connection con=null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		CircleMstr dto;
		try {
			ArrayList circleList=new ArrayList();
			String sql = SQL_SELECT_ALL_CIRCLE;
			con=DBConnection.getDBConnection();
			ps=con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()){
				dto=new CircleMstr();
				dto.setCircleName(rs.getString("CIRCLE_NAME"));
				dto.setCircleId(rs.getString("CIRCLE_ID"));
				circleList.add(dto); 	
 
			}
			
			
			return circleList;
         
		} catch (SQLException e) {

			//	logger.severe("SQL Exception occured while find." + "Exception Message: " + e.getMessage());
			throw new LMSException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			//	logger.severe("Exception occured while find." + "Exception Message: " + e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.km.dao.KmCircleMstrDao#getCircles()
	 */
	public ArrayList getCircles() throws LMSException{
		Connection con=null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		CircleMstr dto;
		try {
			ArrayList circleList=new ArrayList();
			String sql = SQL_SELECT_CIRCLE;
			con=DBConnection.getDBConnection();
			ps=con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()){
				dto=new CircleMstr();
				dto.setCircleName(rs.getString("CIRCLE_NAME"));
				dto.setCircleId(rs.getString("CIRCLE_ID"));
				circleList.add(dto); 	
 
			}
			
			
			return circleList;
         
		} catch (SQLException e) {

			//	logger.severe("SQL Exception occured while find." + "Exception Message: " + e.getMessage());
			throw new LMSException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			//	logger.severe("Exception occured while find." + "Exception Message: " + e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
	}
}