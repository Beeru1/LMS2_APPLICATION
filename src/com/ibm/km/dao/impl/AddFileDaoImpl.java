/*
 * Created on Feb 11, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibm.km.dao.AddFileDAO;
import com.ibm.km.dto.AddFileDTO;
import com.ibm.km.dto.KmDocumentMstr;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.exception.UserMstrDaoException;

/**
 * @author Vipin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AddFileDaoImpl implements AddFileDAO {

	
	// Changes after UAT
	
	public static final String SQL_INSERT_FILE_INFO ="INSERT INTO LMS.KM_DOCUMENT_MSTR(DOCUMENT_ID, DOCUMENT_GROUP_ID, DOCUMENT_NAME,DOC_NAME,DOCUMENT_DISPLAY_NAME, DOCUMENT_DESC,DOCUMENT_KEYWORD,NUMBER_OF_HITS, STATUS, APPROVAL_STATUS, UPLOADED_BY, UPLOADED_DT, UPDATED_BY, UPDATED_DT, APPROVAL_REJECTION_BY, PUBLISHING_START_DT, PUBLISHING_END_DT, DOCUMENT_PATH, CATEGORY_ID, SUB_CATEGORY_ID, CIRCLE_ID,ELEMENT_ID, DOC_TYPE) VALUES(?, 0, ?, ?, ?, ?, ?, 0, 'A', ?, ?, current timestamp, ?, current timestamp, 0, timestamp_format(?, 'YYYY-MM-DD HH24:MI:SS'), timestamp_format(?, 'YYYY-MM-DD HH24:MI:SS') , ?, 1, 1, 1,?, ?)";
	
	public static final String SQL_INSERT_ELEMENT_DOCUMENT= "INSERT INTO LMS.KM_ELEMENT_MSTR(ELEMENT_ID, ELEMENT_NAME, ELEMENT_DESC, PARENT_ID, ELEMENT_LEVEL_ID, PAN_STATUS,STATUS, CREATED_BY, CREATED_DT) VALUES(?, ?, ?, ?, 0, 'N', 'A', ?, CURRENT TIMESTAMP)";
								
	//Changed	
	public static final String SQL_CHECK_DUPLCATE_FILE = "select a.APPROVAL_STATUS,a.ELEMENT_ID, a.DOCUMENT_NAME from LMS.KM_DOCUMENT_MSTR a inner join LMS.KM_ELEMENT_MSTR b on  a.element_id=b.ELEMENT_ID where b.ELEMENT_LEVEL_ID=0 and b.PARENT_ID=? and a.doc_name=? and a.status='A' and b.status='A' AND (DATE(CURRENT TIMESTAMP) BETWEEN DATE(a.PUBLISHING_START_DT) AND DATE(a.PUBLISHING_END_DT))";
								
	//KM Phase2
	public static final String SQL_UPDATE_FILE = "UPDATE LMS.KM_DOCUMENT_MSTR SET DOCUMENT_NAME = ?,UPDATED_BY = ?,DOCUMENT_KEYWORD = ?, DOCUMENT_DESC= ? ,PUBLISHING_START_DT = timestamp_format(? ,'YYYY-MM-DD HH24:MI:SS'), PUBLISHING_END_DT = timestamp_format(?,'YYYY-MM-DD HH24:MI:SS'),UPDATED_DT = current timestamp WHERE DOCUMENT_NAME = ? ";
													
	protected static final String SQL_GET_DOCUMENT_ID = "SELECT NEXTVAL FOR LMS.KM_DOCUMENT_ID_SEQ FROM SYSIBM.SYSDUMMY1";
	
	protected static final String SQL_GET_ELEMENT_ID = "SELECT NEXTVAL FOR LMS.KM_ELEMENT_ID_SEQ FROM SYSIBM.SYSDUMMY1";
	
	Logger logger = Logger.getLogger(AddFileDaoImpl.class.toString());
	

	/* (non-Javadoc)
	 * @see com.ibm.km.dao.AddFileDAO#saveFileInfoInDB(com.ibm.km.dto.AddFileDTO)
	 */
	public String saveFileInfoInDB(KmDocumentMstr dto) throws LMSException {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		logger.info("Entered in saveFileInfoInDB method");
		try
		{
			StringBuffer query=new StringBuffer(SQL_GET_DOCUMENT_ID);
			//String sql1=SQL_GET_DOCUMENT_ID;
			con=getConnection();
			pstmt=con.prepareStatement(query.append(" with ur").toString());
			rs=pstmt.executeQuery();
			rs.next();
			String documentId=rs.getString(1);
			pstmt = con.prepareStatement(SQL_INSERT_FILE_INFO);
			pstmt.setString(1,documentId);
			pstmt.setString(2,dto.getDocumentName()); //DOCUMENT_NAME,DOC_NAME,DOCUMENT_DISPLAY_NAME
			pstmt.setString(3,dto.getDocName().trim());
			pstmt.setString(4,(dto.getDocumentDisplayName().trim()));
			pstmt.setString(5,dto.getDocumentDesc());
			pstmt.setString(6,dto.getKeyword());
			pstmt.setString(7,dto.getApprovalStatus());
			pstmt.setString(8,dto.getUploadedBy());
			pstmt.setString(9,dto.getUpdatedBy());
			// added publish date and publish end date of a new	
			////logger.info("DT:" + dto.getPublishingStartDate());
			pstmt.setString(10,dto.getPublishingStartDate()+ " 00:00:00");
			pstmt.setString(11,dto.getPublishingEndDate()+" 23:59:59");
			////logger.info("DT end:" + dto.getPublishingEndDate());
			
			pstmt.setString(12,dto.getDocumentPath());
			pstmt.setInt(13,Integer.parseInt(dto.getElementId()));
			pstmt.setInt(14, dto.getDocType());
			pstmt.executeUpdate();
			logger.info("Exit from saveFileInfoInDB method");
			return documentId;
		}
		catch(UserMstrDaoException km){
			km.printStackTrace();
			logger.error("KmUserMstrDaoException occured while saving FileInfoInDB." + "Exception Message: " + km.getMessage());
			throw new LMSException(km.getMessage(),km);
		}
		catch(SQLException e){
			e.printStackTrace();
			logger.error("SQLException occured while saving FileInfoInDB." + "Exception Message: " + e.getMessage());
			throw new LMSException(e.getMessage(),e);
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("Exception occured while saving FileInfoInDB." + "Exception Message: " + e.getMessage());
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
				logger.error("DAOException occured while saving FileInfoInDB." + "Exception Message: " + e.getMessage());
				throw new LMSException(e.getMessage(),e);
			}
		}
		
	}
	
	public int saveDocumentAsElement(AddFileDTO dto) throws LMSException {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		
		logger.info("Entered in saveDocumentAsElement method");
		try
		{
			con = getConnection();
			StringBuffer query=new StringBuffer(SQL_GET_ELEMENT_ID);
			pstmt=con.prepareStatement(query.append(" with ur").toString());
			rs=pstmt.executeQuery();
			rs.next();
			int documentId=Integer.parseInt(rs.getString(1));
			pstmt = con.prepareStatement(SQL_INSERT_ELEMENT_DOCUMENT);
			pstmt.setString(1,documentId+"");
			pstmt.setString(2,(dto.getDocDisplayName().trim()));
			pstmt.setString(3,dto.getDocName());
			pstmt.setString(4,dto.getParentId());
			pstmt.setString(5,dto.getUserId());
			pstmt.executeUpdate();
			logger.info("Exit in saveDocumentAsElement method");
			return documentId;
		}
		catch(UserMstrDaoException km)
		{
			logger.error("KmUserMstrDaoException occured while saving DocumentAsElement." + "Exception Message: " + km.getMessage());
			throw new LMSException(km.getMessage(),km);
		}
		catch(SQLException e)
		{
			logger.error("SQLException occured while saving DocumentAsElement." + "Exception Message: " + e.getMessage());
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
				logger.error("DAOException occured while saving DocumentAsElement." + "Exception Message: " + e.getMessage());
				throw new LMSException(e.getMessage(),e);
			}
		}
		
	}	
	
	/* (non-Javadoc)
		 * @see com.ibm.km.dao.AddFileDAO#checkFile(java.lang.String)
		 */
	public KmDocumentMstr checkFile(String parentId, String fileName) throws LMSException 
		{
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String status = null;
			KmDocumentMstr dto = null;
			
			logger.info("Entered in checkFile method");
			try
			{
				con = getConnection();
				StringBuffer query=new StringBuffer(SQL_CHECK_DUPLCATE_FILE);
				pstmt = con.prepareStatement(query.append(" with ur ").toString());
				pstmt.setString(1,parentId);
				pstmt.setString(2,fileName);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					dto=new KmDocumentMstr();
					dto.setStatus(rs.getString("APPROVAL_STATUS"));
					dto.setElementId(rs.getString("ELEMENT_ID"));
					dto.setDocumentName(rs.getString("DOCUMENT_NAME"));
				}
				logger.info("Exit from checkFile method");
			}
			
			catch(UserMstrDaoException km)
			{
				logger.error("KmUserMstrDaoException occured in checkFile." + "Exception Message: " + km.getMessage());
				throw new LMSException(km.getMessage(),km);
			}
			catch(SQLException e)
			{
				logger.error("SQLException occured in checkFile." + "Exception Message: " + e.getMessage());
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
					logger.error("DAOException occured in checkFile." + "Exception Message: " + e.getMessage());
					throw new LMSException(e.getMessage(),e);
				}
			}
			return dto;
		}

	/* (non-Javadoc)
		 * @see com.ibm.km.dao.AddFileDAO#updateFileInfoInDB(java.lang.String)
		 */
	public void updateDocumentName(KmDocumentMstr dto) throws LMSException
	 {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String docName = "";
		logger.info("Entered in updateDocumentName method");
		try
		{
			con = getConnection();
			StringBuffer query=new StringBuffer(SQL_UPDATE_FILE);
			pstmt = con.prepareStatement(query.append(" with ur").toString());
			pstmt.setString(1,dto.getFileName());
			pstmt.setInt(2,Integer.parseInt(dto.getUserId()));
			pstmt.setString(3, dto.getKeyword());
			pstmt.setString(4, dto.getDocumentDesc());
			pstmt.setString(5, dto.getPublishDt()+" 00:00:00");
			pstmt.setString(6, dto.getPublishEndDt()+" 00:00:00");
			pstmt.setString(7,dto.getOldFileName());
			pstmt.executeUpdate();
			
			logger.info("Exit from updateDocumentName method");
		}
		catch(UserMstrDaoException km)
		{
			logger.error("KmUserMstrDaoException occured while updating DocumentName." + "Exception Message: " + km.getMessage());
			throw new LMSException(km.getMessage(),km);
		}
		catch(SQLException e)
		{
			logger.error("SQLException occured while updating DocumentName." + "Exception Message: " + e.getMessage());
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
				logger.error("DAOException occured while updating DocumentName." + "Exception Message: " + e.getMessage());
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