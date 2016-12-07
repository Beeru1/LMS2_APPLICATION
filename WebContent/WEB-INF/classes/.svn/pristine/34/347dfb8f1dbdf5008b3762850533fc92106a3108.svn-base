/*
 * Created on Apr 29, 2008
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

import com.ibm.km.dao.KmWhatsNewDao;
import com.ibm.km.dto.DocumentPath;
import com.ibm.km.dto.KmWhatsNew;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.exception.UserMstrDaoException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KmWhatsNewDaoImpl implements KmWhatsNewDao{
	
	
	Logger logger = Logger.getLogger(AddFileDaoImpl.class.toString());
	/* (non-Javadoc)
	 * @see com.ibm.km.dao.KmWhatsNewDao#checkFile(com.ibm.km.dto.DocumentPath, java.lang.String)
	 */
	public static final String SQL_INSERT_FILE_INFO = "INSERT INTO LMS.KM_WHATS_NEW_MSTR(DOCUMENT_ID, DOCUMENT_NAME,DOCUMENT_DISPLAY_NAME, DOCUMENT_DESC,CIRCLE_ID, UPLOADED_BY, UPLOADED_DT) VALUES(NEXTVAL FOR LMS.KM_WHATS_NEW_ID, ?, ?, ?, ?, ?, current timestamp )";
												
	public static final String SQL_CHECK_DUPLCATE_FILE = "SELECT DOCUMENT_NAME FROM LMS.KM_WHATS_NEW_MSTR WHERE DOCUMENT_DISPLAY_NAME = ? AND  CIRCLE_ID=?  ";
		
	public static final String SQL_SELECT_CSR_DOCUMENTS = "SELECT * FROM LMS.KM_WHATS_NEW_MSTR WHERE ( CIRCLE_ID=?  ";
	
	protected static final String SQL_GET_DOCUMENT_ID = "SELECT NEXTVAL FOR LMS.KM_DOCUMENT_ID FROM SYSIBM.SYSDUMMY1";
	
	protected static final String SQL_CATEGORIES=" SELECT ele.ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR ele,LMS.KM_WHATS_NEW_MSTR whats WHERE ele.ELEMENT_ID=whats.CIRCLE_ID AND ele.PARENT_ID = ? AND ele.ELEMENT_LEVEL_ID=4 ";
	
	public static final String SQL_UPDATE_FILE = "UPDATE LMS.KM_WHATS_NEW_MSTR SET DOCUMENT_NAME = ?,UPDATED_BY = ?,UPDATED_DT = current timestamp, DOCUMENT_DESC=? WHERE DOCUMENT_NAME = ? ";
	
	public String checkFile(DocumentPath path, String fileName) throws LMSException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String docName = "";
			
		try
		{
			con = getConnection();
			StringBuffer query=new StringBuffer(SQL_CHECK_DUPLCATE_FILE);
			pstmt = con.prepareStatement(query.append(" with ur").toString());
			pstmt.setString(1,fileName);
			pstmt.setString(2,path.getCircleId());
			rs = pstmt.executeQuery();
				
			while(rs.next())
			{
				docName = rs.getString(1);
				return docName;
			}
				
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
		return docName;
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
	/* (non-Javadoc)
	 * @see com.ibm.km.dao.KmWhatsNewDao#saveFileInfoInDB(com.ibm.km.dto.KmWhatsNew)
	 */
	public void saveFileInfoInDB(KmWhatsNew dto) throws LMSException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		
		try
		{
			con = getConnection();

		/*	ps=con.prepareStatement(SQL_GET_DOCUMENT_ID);
			rs=ps.executeQuery();
			rs.next();
			int documentId=Integer.parseInt(rs.getString(1)); */
			StringBuffer query=new StringBuffer(SQL_INSERT_FILE_INFO);
			pstmt = con.prepareStatement(query.append(" with ur").toString());
			
			pstmt.setString(1,dto.getDocumentName());
			pstmt.setString(2,(dto.getDocumentDisplayName().trim()));
			pstmt.setString(3,dto.getDocumentDesc());
			pstmt.setString(4,dto.getCircleId());
			pstmt.setString(5,dto.getUploadedBy());
			
			
			pstmt.executeUpdate();
			
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
	/* (non-Javadoc)
	 * @see com.ibm.km.dao.KmWhatsNewDao#updateFileInfoInDB(java.lang.String, java.lang.String, int)
	 */
	public void updateFileInfoInDB(String oldFile, String fileName, int userId, String docDesc) throws LMSException {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String docName = "";
		try
		{
			con = getConnection();
			StringBuffer query=new StringBuffer(SQL_INSERT_FILE_INFO);
			pstmt = con.prepareStatement(query.append(" with ur").toString());
			pstmt.setString(1,fileName);
			pstmt.setInt(2,userId);
			pstmt.setString(3, docDesc);
			pstmt.setString(4,oldFile);
			pstmt.executeUpdate();
			logger.info(SQL_UPDATE_FILE);
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
	/* (non-Javadoc)
	 * @see com.ibm.km.dao.KmWhatsNewDao#viewFiles(java.lang.String)
	 */
	public ArrayList viewFiles(String[] circleIds,String actorId) throws LMSException {
		Connection con=null;
		ResultSet rs = null;
		ArrayList fileList = new ArrayList();
		PreparedStatement ps = null;
		KmWhatsNew dto;
		StringBuffer query=new StringBuffer(SQL_SELECT_CSR_DOCUMENTS);
		//String sql=SQL_SELECT_CSR_DOCUMENTS;
		for(int i=1;i<circleIds.length;i++){
				//sql+=" OR CIRCLE_ID=? ";
				query.append("OR CIRCLE_ID=?").toString();		
			}
			//sql+=" ) ORDER BY  UPLOADED_DT DESC ";
			query.append(" ) ORDER BY  UPLOADED_DT DESC").toString();
		try {
		
		con=DBConnection.getDBConnection();
		ps=con.prepareStatement(query.append(" with ur").toString());
		for(int i=0;i<circleIds.length;i++){
			ps.setInt(i+1, Integer.parseInt(circleIds[i]));	
		}	
		rs = ps.executeQuery();
		logger.info(query);
		while(rs.next()){
			dto=new KmWhatsNew();
			
			
			String docPath = rs.getString("CIRCLE_ID") + "/" + rs.getString("DOCUMENT_NAME");
			dto.setDocumentPath(docPath);
			dto.setDocumentId(rs.getString("DOCUMENT_ID"));
			dto.setDocumentName(rs.getString("DOCUMENT_NAME"));
			dto.setDocumentDisplayName(rs.getString("DOCUMENT_DISPLAY_NAME"));
			dto.setDocumentDesc(rs.getString("DOCUMENT_DESC"));
			dto.setCircleId(rs.getString("CIRCLE_ID"));
			dto.setUploadedBy(rs.getString("UPLOADED_BY"));
			if(rs.getString("UPLOADED_DT")!=null){
//				Remove seconds and milliseconds from ever page wherever displayed : defect no. MASDB00060756
				
				dto.setUploadedDate(rs.getString("UPLOADED_DT").substring(0,16));
			}
			
			
			dto.setUpdatedBy(rs.getString("UPDATED_BY"));
			if(rs.getString("UPDATED_DT")!=null){
				dto.setUpdatedDate(rs.getString("UPDATED_DT").substring(0,16));
			}
			
			

			fileList.add(dto);	
		}
		return fileList;
         
	} catch (SQLException e) {
		logger.error(e);
//	logger.severe("SQL Exception occured while find." + "Exception Message: " + e.getMessage());
		throw new LMSException("SQLException: " + e.getMessage(), e);
	} catch (Exception e) {

//	logger.severe("Exception occured while find." + "Exception Message: " + e.getMessage());
		logger.error(e);
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
	 * @see com.ibm.km.dao.KmWhatsNewDao#getCategories(java.lang.String)
	 */
	public String[] getCategories(String elementId) throws LMSException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList list=new ArrayList();	
		try
		{
			con = getConnection();
            StringBuffer query=new StringBuffer(SQL_CATEGORIES);
			pstmt = con.prepareStatement(query.append(" with ur").toString());
			pstmt.setString(1,elementId);
			
			rs = pstmt.executeQuery();
			int i=0;	
			while(rs.next())
			{
				
				list.add(rs.getString("ELEMENT_ID"));
			}
			String[]categoryID=(String[])list.toArray(new String[list.size()]);
			return categoryID;	
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

}
