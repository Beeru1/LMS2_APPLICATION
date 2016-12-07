/*
 * Created on Feb 13, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.km.dao.SubmitFileDao;
import com.ibm.km.dto.SubmitFileDto;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.exception.UserMstrDaoException;

/**
 * @author Pramod
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SubmitFileDaoImpl implements SubmitFileDao{
	
	
	Logger logger = Logger.getLogger(SubmitFileDaoImpl.class.toString());
	 
	public static final String SQL_GET_FILE_LIST = "SELECT DOCUMENT_ID, DOCUMENT_DISPLAY_NAME, CATEGORY_NAME, SUB_CATEGORY_NAME"
	+ " FROM LMS.KM_DOCUMENT_MSTR doc, LMS.KM_CATEGORY_MSTR cat, LMS.KM_SUB_CATEGORY_MSTR subcat, LMS.KM_CIRCLE_MSTR cir "
	+ " where doc.CIRCLE_ID = cir.CIRCLE_ID and doc.CATEGORY_ID = cat.CATEGORY_ID and doc.SUB_CATEGORY_ID = subcat.SUB_CATEGORY_ID"
	+ " and doc.UPLOADED_BY = ? and doc.APPROVAL_STATUS = 'S' and doc.STATUS = 'A' "
	+ " and cast(doc.UPLOADED_DT as date) between cast(? as date) and cast(? as date) ORDER BY doc.DOCUMENT_ID";
	
	public static final String SQL_UPDATE_STATUS = "UPDATE LMS.KM_DOCUMENT_MSTR SET APPROVAL_STATUS = 'P' WHERE DOCUMENT_ID = ?";
	
	/* (non-Javadoc)
	 * @see com.ibm.km.dao.SubmitFileDao#getFileList(java.lang.String, java.lang.String)
	 */
	public ArrayList getFileList(String userId, String fromDate,String toDate)
		throws LMSException {
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			SubmitFileDto dto;
			ArrayList fileList = new ArrayList();
			
			try
			{
				con = getConnection();
			//	pstmt = con.prepareStatement(SQL_GET_FILE_LIST + " and cast(doc.UPLOADED_DT as date) = cast('" + onDate +"' as date)");
				pstmt = con.prepareStatement(SQL_GET_FILE_LIST+" with ur");
			
				pstmt.setString(1,userId);
				pstmt.setString(2,fromDate);
				pstmt.setString(3,toDate);
			
				rs = pstmt.executeQuery();
				
				while(rs.next())
				{
					dto = new SubmitFileDto();
					dto.setDocName(rs.getString("DOCUMENT_DISPLAY_NAME"));
					dto.setCategoryName(rs.getString("CATEGORY_NAME"));
					dto.setSubCategoryName(rs.getString("SUB_CATEGORY_NAME"));
					dto.setDocumentId(rs.getString("DOCUMENT_ID"));
					fileList.add(dto);
				}
				return fileList;
				
			}
			catch(UserMstrDaoException km)
			{
				logger.error(km);
				throw new LMSException(km.getMessage(),km);
			}
			catch(SQLException e)
			{
				logger.error(e);
				throw new LMSException(e.getMessage(),e);
			}
			catch(Exception e)
			{
				logger.error(e);
				throw new LMSException(e.getMessage(),e);
			}
			finally
			{
				try
				{
					DBConnection.releaseResources(con,pstmt,rs);
				}
				catch(DAOException e)
				{
					logger.error(e);
					throw new LMSException(e.getMessage(),e);
				}
			}	
		
	}
	
	/* (non-Javadoc)
		 * @see com.ibm.km.dao.SubmitFileDao#submitFile(java.lang.String[])
		 */
		public void submitFile(String[] fileIds) throws LMSException {
			
			Connection con = null;
			PreparedStatement pstmt = null;
		
			SubmitFileDto dto;
			ArrayList fileList = new ArrayList();
			
			try
			{
				StringBuffer query=new StringBuffer(SQL_UPDATE_STATUS);
				con = getConnection();
				pstmt = con.prepareStatement(query.append(" with ur").toString());
				int i = 0;
				
				while( i < fileIds.length)
				{
					pstmt.setInt(1,Integer.parseInt(fileIds[i]));
					pstmt.addBatch();
					i++;
				}
				pstmt.executeBatch();
				con.commit();
			}
			catch(UserMstrDaoException km)
			{
				logger.error(km);
				throw new LMSException(km.getMessage(),km);
			}
			catch(SQLException e)
			{
				e.getNextException().printStackTrace();
				//e.printStackTrace();
				throw new LMSException(e.getMessage(),e);
			}
			finally
			{
				try
				{
					DBConnection.releaseResources(con,pstmt,null);
				}
				catch(DAOException e)
				{
					logger.error(e);
					throw new LMSException(e.getMessage(),e);
				}
			}	

		}

	private Connection getConnection() throws UserMstrDaoException {

				logger.info("Entered getConnection for operation on table:KM_USER_MSTR");

				try {
				return DBConnection.getDBConnection();
				}catch(DAOException e) {

				logger.info("Exception Occured while obtaining connection.");

				throw new UserMstrDaoException("Exception while trying to obtain a connection",e);
			}

		   }
	
	
}
