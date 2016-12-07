package com.ibm.km.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.km.dao.KmCsrLatestUpdatesDao;
import com.ibm.km.dto.KmCsrLatestUpdatesDto;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.common.Utility;
import com.ibm.lms.exception.DAOException;


public class KmCsrLatestUpdatesDaoImpl implements KmCsrLatestUpdatesDao{
	 
	private static Logger logger = Logger.getLogger(KmCsrLatestUpdatesDaoImpl.class.toString());
	
	protected static final String SQL_INSERT_LATEST_UPDATES = "INSERT INTO LMS.KM_LATEST_UPDATES( DOCUMENT_ID, DOC_TITLE, DOC_DETAIL, DOC_ACTIVATION_DT, DOC_EXPIRY_DT, CATEGORY, LOB_ID, CIRCLE_ID, DOC_TYPE) VALUES(?,?,?,?,?,?,?,?,?)  with ur";
	protected static final String SQL_SELECT_LATEST_UPDATES = "select DOCUMENT_ID,DOC_TITLE,DOC_DETAIL,DOC_TYPE from LMS.KM_LATEST_UPDATES where DOC_ACTIVATION_DT >=(current timestamp - ? ) and DOC_EXPIRY_DT>=current timestamp order by DOC_ACTIVATION_DT desc, REC_ID desc with ur";
	
	public ArrayList<KmCsrLatestUpdatesDto> initGetUpdates() throws DAOException{
		ArrayList<KmCsrLatestUpdatesDto> list = new ArrayList<KmCsrLatestUpdatesDto>();
		ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
		String timeDuration = bundle.getString("latestUpdates.timeDuration");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn= DBConnection.getDBConnection();
		try{
		pstmt = conn.prepareStatement(SQL_SELECT_LATEST_UPDATES.replace("?", timeDuration));
		rs = pstmt.executeQuery();
		while(rs.next()){
			KmCsrLatestUpdatesDto dto = new KmCsrLatestUpdatesDto();
			dto.setDocType(rs.getInt("DOC_TYPE"));
			dto.setDocumentId(rs.getInt("DOCUMENT_ID")+"");
			dto.setUpdateTitle(rs.getString("DOC_TITLE"));
			if(rs.getString("DOC_DETAIL").length() >= 40){
				dto.setUpdateBrief(rs.getString("DOC_DETAIL").substring(0,39)+"...");
			}else{
				dto.setUpdateBrief(rs.getString("DOC_DETAIL"));
			}
			dto.setUpdateDesc(rs.getString("DOC_DETAIL"));
			dto.setDocumentViewUrl(Utility.getDocumentViewURL(dto.getDocumentId(), dto.getDocType()));
			list.add(dto);
		}
		
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException(sqle.getMessage());
		}
		
		return list;
	}

	/**
	 * Method to insert Latest Updates into db.
	 */
	public  int insertLatestUpdates(KmCsrLatestUpdatesDto dto) throws DAOException {

		logger.info("Entered insert for table KM_LATEST_UPDATES");

		Connection con = null;
		PreparedStatement ps = null;
		int rowsUpdated = 0;
		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_INSERT_LATEST_UPDATES);
			
			
			String updateTitle = dto.getUpdateTitle();
			int updateTitleLength = updateTitle.length();	
			
			if (updateTitleLength > 200)
			{
				updateTitleLength = 200;
			}
			if (updateTitleLength > 0)
			{
				updateTitle = updateTitle.substring(0, updateTitleLength);
			}
			
			String updateDesc = dto.getUpdateDesc();
			int updateDescLength = updateDesc.length();			
			if (updateDescLength > 200)
			{
				updateDescLength = 200;
			}
			if (updateDescLength > 0)
			{
				updateDesc = updateDesc.substring(0, updateDescLength);
			}
			
			int paramCount = 1;
			ps.setString(paramCount++,  dto.getDocumentId());
			ps.setString(paramCount++,  updateTitle);
			ps.setString(paramCount++,  updateDesc);
			ps.setString(paramCount++,  dto.getActivationDate()+ " 00:00:00");
			ps.setString(paramCount++,  dto.getExpiryDate()+ " 23:59:59");
			ps.setString(paramCount++,  dto.getCategory());
			ps.setInt(paramCount++,  Integer.parseInt(dto.getLob()));
			ps.setInt(paramCount++,  Integer.parseInt(dto.getCircleId()));
			ps.setInt(paramCount++, dto.getDocType());
			rowsUpdated=ps.executeUpdate();

		logger.info("Latest update inserted.");

		} catch (SQLException e) {
			
		logger.error("SQL Exception occured while inserting Latest update." + "Exception Message: " + e.getMessage());
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			logger.error("SQL Exception occured while inserting Latest update." + "Exception Message: " + e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null) {
					con.setAutoCommit(true);
					con.close();
				}
			} catch (Exception e) {
				logger.error("SQL Exception occured while closing connection for Latest update." + "Exception Message: " + e.getMessage());
			}
		}

		return rowsUpdated;
	}
}
