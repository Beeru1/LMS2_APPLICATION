/*
 * Created on Apr 4, 2008
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

import com.ibm.km.dao.KmFileReportDao;
import com.ibm.km.dto.FileReportDto;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.exception.LMSException;

/**
 * @author jain
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KmFileReportDaoImpl implements KmFileReportDao {

	public static Logger logger = Logger.getLogger(KmFileReportDaoImpl.class);

	protected static final String SQL_NO_OF_APPROVALS1 ="SELECT APPROVAL_REJECTION_BY,USER_FNAME,USER_MNAME,USER_LNAME,COUNT(USER_LNAME) C FROM LMS.KM_DOCUMENT_MSTR ,LMS.KM_USER_MSTR WHERE LMS.KM_DOCUMENT_MSTR.CIRCLE_ID=? AND cast(UPLOADED_DT as date) between cast(? as date) and cast(? as date) AND APPROVAL_STATUS='A' AND APPROVAL_REJECTION_BY=USER_ID group by APPROVAL_REJECTION_BY,USER_FNAME,USER_MNAME,USER_LNAME";
	
	protected static final String SQL_NO_OF_APPROVALS2 ="SELECT APPROVAL_REJECTION_BY,USER_FNAME,USER_MNAME,USER_LNAME,COUNT(USER_LNAME) C FROM LMS.KM_DOCUMENT_MSTR ,LMS.KM_USER_MSTR WHERE LMS.KM_DOCUMENT_MSTR.CATEGORY_ID=? AND cast(UPLOADED_DT as date) between cast(? as date) and cast(? as date) AND APPROVAL_STATUS='A' AND APPROVAL_REJECTION_BY=USER_ID group by APPROVAL_REJECTION_BY,USER_FNAME,USER_MNAME,USER_LNAME";
	
	protected static final String SQL_NO_OF_APPROVALS3 ="SELECT APPROVAL_REJECTION_BY,USER_FNAME,USER_MNAME,USER_LNAME,COUNT(USER_LNAME) C FROM LMS.KM_DOCUMENT_MSTR ,LMS.KM_USER_MSTR WHERE LMS.KM_DOCUMENT_MSTR.SUB_CATEGORY_ID=? AND cast(UPLOADED_DT as date) between cast(? as date) and cast(? as date) AND APPROVAL_STATUS='A' AND APPROVAL_REJECTION_BY=USER_ID group by APPROVAL_REJECTION_BY,USER_FNAME,USER_MNAME,USER_LNAME";
	
	protected static final String SQL_NO_OF_HITS1 ="SELECT DOCUMENT_DISPLAY_NAME,NUMBER_OF_HITS FROM LMS.KM_DOCUMENT_MSTR WHERE CIRCLE_ID=? AND cast(UPLOADED_DT as date) between cast(? as date) and cast(? as date)";
	
	protected static final String SQL_NO_OF_HITS2 ="SELECT DOCUMENT_DISPLAY_NAME,NUMBER_OF_HITS FROM LMS.KM_DOCUMENT_MSTR WHERE CATEGORY_ID=? AND cast(UPLOADED_DT as date) between cast(? as date) and cast(? as date)";
	
	protected static final String SQL_NO_OF_HITS3 ="SELECT DOCUMENT_DISPLAY_NAME,NUMBER_OF_HITS FROM LMS.KM_DOCUMENT_MSTR WHERE SUB_CATEGORY_ID=? AND cast(UPLOADED_DT as date) between cast(? as date) and cast(? as date)";
	
	protected static final String SQL_FILES_COUNT ="WITH nee(element_id,chain, ELEMENT_LEVEL_ID) AS ( SELECT  ELEMENT_ID, cast(element_name as VARCHAR(240)), ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR WHERE element_id =? UNION ALL SELECT nplus1.element_id,nee.chain || '/' || nplus1.element_name , NPLUS1.ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR as nplus1, nee WHERE nee.element_id=nplus1.PARENT_ID) SELECT  COUNT(*) AS DOCUMENT_COUNT, doc.APPROVAL_STATUS,doc.STATUS FROM LMS.KM_DOCUMENT_MSTR doc, LMS.KM_ELEMENT_MSTR ele, nee WHERE  ele.parent_id=nee.element_id 	AND doc.element_id=ele.element_id 	 group by doc.APPROVAL_STATUS,doc.STATUS ";

	protected static final String SQL_FILES_APPROVED ="WITH nee(element_id,chain, ELEMENT_LEVEL_ID) AS ( SELECT  ELEMENT_ID, cast(element_name as VARCHAR(240)), ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR WHERE element_id =? UNION ALL  SELECT nplus1.element_id,nee.chain || '/' || nplus1.element_name , NPLUS1.ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR as nplus1, nee  WHERE nee.element_id=nplus1.PARENT_ID) SELECT  COUNT(*) AS DOCUMENT_COUNT FROM LMS.KM_DOCUMENT_MSTR doc, LMS.KM_ELEMENT_MSTR ele, nee  WHERE doc.STATUS ='A' AND doc.APPROVAL_STATUS='A' AND ele.parent_id=nee.element_id AND doc.element_id=ele.element_id ";

	protected static final String SQL_FILES_ADDED ="WITH nee(element_id,chain, ELEMENT_LEVEL_ID) AS ( SELECT  ELEMENT_ID, cast(element_name as VARCHAR(240)), ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR WHERE element_id =? UNION ALL SELECT nplus1.element_id,nee.chain || '/' || nplus1.element_name , NPLUS1.ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR as nplus1, nee  WHERE nee.element_id=nplus1.PARENT_ID) SELECT  COUNT(*) AS DOCUMENT_COUNT FROM LMS.KM_DOCUMENT_MSTR doc, nee  WHERE doc.STATUS ='A' AND doc.element_id=nee.element_id and cast(UPLOADED_DT as date)=cast(? as date) AND NEE.ELEMENT_LEVEL_ID=0";

	protected static final String SQL_FILES_DELETED ="WITH nee(element_id,chain, ELEMENT_LEVEL_ID) AS ( SELECT  ELEMENT_ID, cast(element_name as VARCHAR(240)), ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR WHERE element_id =? UNION ALL  SELECT nplus1.element_id,nee.chain || '/' || nplus1.element_name , NPLUS1.ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR as nplus1, nee  WHERE nee.element_id=nplus1.PARENT_ID) SELECT  COUNT(*) AS DOCUMENT_COUNT FROM LMS.KM_DOCUMENT_MSTR doc, nee WHERE doc.STATUS ='D' AND doc.element_id=nee.element_id and cast(UPDATED_DT as date)=cast(? as date) AND NEE.ELEMENT_LEVEL_ID=0";

	protected static final String SQL_FILES_CIRCLE_ADDED ="WITH nee(element_id, ELEMENT_LEVEL_ID) AS ( SELECT  ELEMENT_ID, ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR  WHERE element_id =? UNION ALL SELECT nplus1.element_id, NPLUS1.ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR as nplus1, nee WHERE nee.element_id=nplus1.PARENT_ID)  SELECT  count(*)AS DOCUMENT_COUNT, doc.uploaded_by,usm.USER_FNAME,usm.USER_MNAME, usm.USER_LNAME,usm.USER_LOGIN_ID, ele.element_name FROM LMS.KM_DOCUMENT_MSTR doc, nee,LMS.KM_USER_MSTR usm ,LMS.KM_ELEMENT_MSTR ele WHERE doc.STATUS ='A' AND NEE.ELEMENT_LEVEL_ID=0 and nee.element_id=doc.element_id AND doc.uploaded_by=usm.user_id AND ele.element_id=usm.element_id and cast(UPLOADED_DT as date)=cast(? as date) group by  doc.uploaded_by,usm.user_fname,usm.USER_MNAME,usm.USER_LNAME,usm.USER_LOGIN_ID,	ele.element_name ";

	protected static final String SQL_FILES_APPROVAL ="WITH nee(element_id,chain, ELEMENT_LEVEL_ID) AS ( SELECT  ELEMENT_ID, cast(element_name as VARCHAR(240)), ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR  WHERE element_id =? UNION ALL SELECT nplus1.element_id,nee.chain || '/' || nplus1.element_name , NPLUS1.ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR as nplus1, nee WHERE nee.element_id=nplus1.PARENT_ID) SELECT   doc.uploaded_by, COUNT(*) AS DOCUMENT_COUNT,usm.USER_FNAME,usm.USER_MNAME,usm.USER_LNAME FROM LMS.KM_DOCUMENT_MSTR doc, nee,LMS.KM_USER_MSTR usm  WHERE doc.STATUS ='A' AND doc.element_id=nee.element_id  AND doc.uploaded_by=usm.user_id and usm.KM_ACTOR_ID=2 and cast(APPROVAL_REJECTION_DT as date)=cast(? as date) AND NEE.ELEMENT_LEVEL_ID=0 group by  doc.uploaded_by,usm.user_fname,usm.USER_MNAME,usm.USER_LNAME  ";

	protected static final String SQL_FILES_TOTAL_COUNT ="WITH nee(element_id,chain, ELEMENT_LEVEL_ID) AS ( SELECT  ELEMENT_ID, cast(element_name as VARCHAR(240)), ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR WHERE element_id =? UNION ALL SELECT nplus1.element_id,nee.chain || '/' || nplus1.element_name , NPLUS1.ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR as nplus1, nee WHERE nee.element_id=nplus1.PARENT_ID) SELECT    COUNT(*) AS DOCUMENT_COUNT FROM nee  WHERE  NEE.ELEMENT_LEVEL_ID=0";

	protected static final String SQL_FILES_ALL_ADDED ="WITH nee(element_id,chain, ELEMENT_LEVEL_ID) AS ( SELECT  ELEMENT_ID, cast(element_name as VARCHAR(240)), ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR  WHERE element_id =?  UNION ALL  SELECT nplus1.element_id,nee.chain || '/' || nplus1.element_name , NPLUS1.ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR as nplus1, nee  WHERE nee.element_id=nplus1.PARENT_ID)  SELECT   COUNT(*) AS DOCUMENT_COUNT FROM LMS.KM_DOCUMENT_MSTR doc, nee,LMS.KM_ELEMENT_MSTR ele WHERE doc.STATUS ='A' AND doc.element_id=ele.element_id AND nee.element_id=ele.element_id and cast(UPLOADED_DT as date)=cast(? as date) AND NEE.ELEMENT_LEVEL_ID=0 ";

	public ArrayList getApproverList(
		String circleId,
		String categoryId,
		String subCategoryId,
		String fromDate,
		String toDate)
		throws LMSException {

		Connection con = null;
		ResultSet rs = null;
		FileReportDto dto;
		ArrayList fileList = new ArrayList();
		PreparedStatement ps = null;
		int c = 0;
		logger.info("Entered in getApproverList method");
		try {
			ArrayList approverList = new ArrayList();
			//String sql = null,
			StringBuffer query=null;
			String	val = null,
				fname = null,
				mname = null,
				lname = null;

			con = DBConnection.getDBConnection();

			if (!circleId.equals("-1")) {
				query =  new StringBuffer(SQL_NO_OF_APPROVALS1);
				val = circleId;
				logger.info("inside dao.circleid !=-1  " + query + "  " + val);
			}
			if (!categoryId.equals("-1")) {
				query = new StringBuffer(SQL_NO_OF_APPROVALS2);
				val = categoryId;
				logger.info("inside dao.catid !=-1  " + query + "  " + val);
			}
			if (!subCategoryId.equals("-1")) {
				query= new StringBuffer(SQL_NO_OF_APPROVALS3);
				val = subCategoryId;
				logger.info("inside dao.subcatid !=-1  " + query + "  " + val);
			}
			logger.info(val + fromDate + toDate);

			ps = con.prepareStatement(query.append(" with ur").toString());

			ps.setInt(1, Integer.parseInt(val));
			ps.setString(2, fromDate);
			ps.setString(3, toDate);

			rs = ps.executeQuery();
			while (rs.next()) {

				dto = new FileReportDto();
				dto.setApprovalId(rs.getString("APPROVAL_REJECTION_BY"));
				dto.setNoOfApproved(rs.getInt("C"));
				c += dto.getNoOfApproved();
				dto.setTotal(c);
				fname = rs.getString("USER_FNAME");
				mname = rs.getString("USER_MNAME");
				lname = rs.getString("USER_LNAME");

				dto.setApproverName(fname + " " + mname + " " + lname);

				approverList.add(dto);
				logger.info(
					dto.getApprovalId()
						+ dto.getApproverName()
						+ dto.getNoOfApproved()
						+ "\n");

			}
			logger.info("Exit from getApproverList method");
			return approverList;

		} catch (SQLException e) {
			logger.error(
				"SQL Exception occured while getting Approver List."
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException("SQL Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error(
				"Exception occured while getting Approver List."
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error(
					"DAOException occured while getting Approver List."
						+ "Exception Message: "
						+ e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}
		}

	}
	// Added by Atul
	public String getAddedFileCount(String elementId, String fromDate)
		throws LMSException {

		Connection con = null;
		ResultSet rs = null;
		FileReportDto dto;
		ArrayList fileList = new ArrayList();
		PreparedStatement ps = null;
		//KmFileReportFormBean formBean= new KmFileReportFormBean();
		int count = 0;

		logger.info("Entered in getAddedFileCount method");
		try {
			ArrayList countList = new ArrayList();
			String val = null; 
			
			StringBuffer query=new StringBuffer(SQL_FILES_ADDED);

			con = DBConnection.getDBConnection();

			ps = con.prepareStatement(query.append(" with ur ").toString());

			ps.setInt(1, Integer.parseInt(elementId));
			ps.setString(2, fromDate);
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt("DOCUMENT_COUNT");
			}

			logger.info("Exit from getAddedFileCount method");
			return count + "";

		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while getting Added File Count"
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException("SQL Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error(
				"Exception occured while getting Added File Count"
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error(
					"DAOException occured while getting Added File Count"
						+ "Exception Message: "
						+ e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}
		}

	}

	/*	public int getAddedFileCount(String elementId,String fromDate)throws KmException
				{	
			
					Connection con=null;
					ResultSet rs = null;
					FileReportDto dto;
					ArrayList fileList = new ArrayList();
					PreparedStatement ps = null;
					//KmFileReportFormBean formBean= new KmFileReportFormBean();
					int count=0;
					String fname="";
					String mname="";
					String lname="";
			
					try {
						ArrayList countList=new ArrayList();
						String val=null,sql=null;
				
						con=DBConnection.getDBConnection();
					
						ps=con.prepareStatement(SQL_FILES_ADDED);
					
						ps.setInt(1,Integer.parseInt(elementId));
						ps.setString(2,fromDate);
						rs = ps.executeQuery();
						while(rs.next()) {
							count=rs.getInt("DOCUMENT_COUNT");
						}
	
						return count;
			        
					} catch (SQLException e) {
				
					e.printStackTrace();
			
				//	logger.severe("SQL Exception occured while find." + "Exception Message: " + e.getMessage());
						throw new KmException("SQLException: " + e.getMessage(), e);
					} catch (Exception e) {
						   e.printStackTrace();
				//	logger.severe("Exception occured while find." + "Exception Message: " + e.getMessage());
						throw new KmException("Exception: " + e.getMessage(), e);
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
						e.printStackTrace();
						}
					}
			
				}	
			*/
	public int getDeletedFileList(String elementId, String fromDate)
		throws LMSException {

		Connection con = null;
		ResultSet rs = null;
		FileReportDto dto;
		ArrayList fileList = new ArrayList();
		PreparedStatement ps = null;
		int count = 0;
		logger.info("Entered in getDeletedFileList method");
		try {
			ArrayList countList = new ArrayList();
			String val = null;
			StringBuffer query=new StringBuffer(SQL_FILES_DELETED);
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query.append(" with ur ").toString());
			ps.setInt(1, Integer.parseInt(elementId));
			ps.setString(2, fromDate);
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt("DOCUMENT_COUNT");
			}

			logger.info("Exit from getDeletedFileList method");
			return count;

		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while getting Deleted File Count"
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException("SQL Exception: " + e.getMessage(), e);

		} catch (Exception e) {
			logger.error(
				"Exception occured while getting Deleted File Count"
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);

		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error(
					"DAOException occured while getting Deleted File Count"
						+ "Exception Message: "
						+ e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}
		}
	}

	//ended by Atul

	public ArrayList getHitList(
		String circleId,
		String categoryId,
		String subCategoryId,
		String fromDate,
		String toDate)
		throws LMSException {

		Connection con = null;
		ResultSet rs = null;
		FileReportDto dto;
		ArrayList fileList = new ArrayList();
		PreparedStatement ps = null;
		logger.info("Entered in getHitList method");
		try {
			ArrayList hitList = new ArrayList();
			String val = null;
			StringBuffer sql=null;

			con = DBConnection.getDBConnection();

			if (!circleId.equals("-1")) {
				sql = new StringBuffer(SQL_NO_OF_HITS1);
				val = circleId;
				logger.info("inside dao.circleid !=-1  " + sql + "  " + val);
			}
			if (!categoryId.equals("-1")) {
				sql =new StringBuffer(SQL_NO_OF_HITS2);
				val = categoryId;
				logger.info("inside dao.catid !=-1  " + sql + "  " + val);
			}
			if (!subCategoryId.equals("-1")) {
				sql = new StringBuffer(SQL_NO_OF_HITS3);
				val = subCategoryId;
				logger.info("inside dao.subcatid !=-1  " + sql + "  " + val);
			}
			logger.info(val + fromDate + toDate);

			ps = con.prepareStatement(sql.append(" with ur").toString());

			ps.setInt(1, Integer.parseInt(val));
			ps.setString(2, fromDate);
			ps.setString(3, toDate);

			rs = ps.executeQuery();
			while (rs.next()) {

				dto = new FileReportDto();
				dto.setDocName(rs.getString("DOCUMENT_DISPLAY_NAME"));
				dto.setNoOfHits(rs.getInt("NUMBER_OF_HITS"));

				hitList.add(dto);
				//logger.info(dto.getApprovalId()+dto.getApproverName()+dto.getNoOfApproved()+"\n");
			}
			return hitList;
		} catch (SQLException e) {
			//logger.info(e.p);

			//	logger.severe("SQL Exception occured while find." + "Exception Message: " + e.getMessage());
			throw new LMSException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

			//	logger.severe("Exception occured while find." + "Exception Message: " + e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {

			}
		}

	}
	/* (non-Javadoc)
	 * @see com.ibm.km.dao.KmFileReportDao#getFileCircleList(java.lang.String, java.lang.String)
	 */
	public ArrayList getFileCircleList(String elementId, String selectedDate)
		throws LMSException {

		Connection con = null;
		ResultSet rs = null;
		FileReportDto dto;
		ArrayList fileList = new ArrayList();
		PreparedStatement ps = null;
		int count = 0;
		String fname = "";
		String mname = "";
		String lname = "";

		logger.info("Entered in getFileCircleList method");

		try {
			ArrayList countList = new ArrayList();
			String val = null;
			StringBuffer query=new StringBuffer(SQL_FILES_CIRCLE_ADDED);
			con = DBConnection.getDBConnection();

			ps = con.prepareStatement(query.append(" with ur ").toString());
			logger.info("ELEMENT  IDDD :" + elementId);
			ps.setInt(1, Integer.parseInt(elementId));
			ps.setString(2, selectedDate);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new FileReportDto();
				dto.setNoOfDocuments(rs.getInt("DOCUMENT_COUNT"));
				//count=rs.getInt("DOCUMENT_COUNT");
				fname = rs.getString("USER_FNAME");
				mname = rs.getString("USER_MNAME");
				lname = rs.getString("USER_LNAME");
				dto.setUploadedByName(fname + " " + lname);
				dto.setUploadedByLoginId(rs.getString("USER_LOGIN_ID"));
				dto.setCircleName(rs.getString("ELEMENT_NAME"));
				countList.add(dto);
			}
			logger.info("Exit from getFileCircleList method");
			return countList;

		} catch (SQLException e) {
			logger.error(
				"SQL Exception occured while getting Circle File List"
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException("SQL Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error(
				"Exception occured while getting Circle File List"
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error(
					"DAOException occured while getting Circle File List"
						+ "Exception Message: "
						+ e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}

		}

	}

	public ArrayList getFileApprovedList(String elementId, String selectedDate)
		throws LMSException {

		Connection con = null;
		ResultSet rs = null;
		FileReportDto dto;
		ArrayList fileList = new ArrayList();
		PreparedStatement ps = null;
		int count = 0;
		String fname = "";
		String mname = "";
		String lname = "";
		logger.info("Entered in getFileApprovedList method");
		try {
			ArrayList countList = new ArrayList();
			String val = null;
			StringBuffer query=new StringBuffer(SQL_FILES_APPROVAL);
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query.append(" with ur ").toString());
			ps.setInt(1, Integer.parseInt(elementId));
			ps.setString(2, selectedDate);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new FileReportDto();
				dto.setNoOfDocuments(rs.getInt("DOCUMENT_COUNT"));
				//count=rs.getInt("DOCUMENT_COUNT");
				fname = rs.getString("USER_FNAME");
				mname = rs.getString("USER_MNAME");
				lname = rs.getString("USER_LNAME");
				dto.setApproverName(fname + " " + mname + " " + lname);
				countList.add(dto);
			}
			logger.info("Exit from getFileApprovedList method");
			return countList;

		} catch (SQLException e) {
			logger.error(
				"SQL Exception occured while getting FileApprovedList "
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException("SQL Exception: " + e.getMessage(), e);

		} catch (Exception e) {
			logger.error(
				"Exception occured while getting FileApprovedList "
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);

		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error(
					"DAOException occured while getting FileApprovedList "
						+ "Exception Message: "
						+ e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}
		}
	}

	public int getTotalFileCount(String elementId) throws LMSException {
		Connection con = null;
		ResultSet rs = null;
		FileReportDto dto;
		ArrayList fileList = new ArrayList();
		PreparedStatement ps = null;
		int count = 0;
		logger.info("Entered in getTotalFileCount method");
		try {
			ArrayList countList = new ArrayList();
			String val = null;
			StringBuffer query=new StringBuffer(SQL_FILES_TOTAL_COUNT);
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query.append(" with ur ").toString());
			ps.setInt(1, Integer.parseInt(elementId));
			//	ps.setString(2,fromDate);
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt("DOCUMENT_COUNT");
			}
			logger.info("Exit from getTotalFileCount method");
			return count;
		} catch (SQLException e) {
			logger.error(
				"SQL Exception occured while getting Total File Count "
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException("SQL Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error(
				"Exception occured while getting Total File Count "
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error(
					"DAOException occured while getting Total File Count "
						+ "Exception Message: "
						+ e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.ibm.km.dao.KmFileReportDao#getDeletedFileCount(java.lang.String, java.lang.String)
	 */
	public String getDeletedFileCount(String elementId, String selectedDate)
		throws LMSException {
		Connection con = null;
		ResultSet rs = null;
		FileReportDto dto;
		ArrayList fileList = new ArrayList();
		PreparedStatement ps = null;
		//KmFileReportFormBean formBean= new KmFileReportFormBean();
		int count = 0;
		logger.info("Entered in getDeletedFileCount method");

		try {
			ArrayList countList = new ArrayList();
			String val = null;
			StringBuffer query=new StringBuffer(SQL_FILES_DELETED);
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query.append(" with ur ").toString());
			ps.setInt(1, Integer.parseInt(elementId));
			ps.setString(2, selectedDate);
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt("DOCUMENT_COUNT");
			}
			logger.info("Exit from getDeletedFileCount method");
			return count + "";
		} catch (SQLException e) {
			logger.error(
				"SQL Exception occured while getting Deleted File Count "
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException("SQL Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error(
				"Exception occured while getting Deleted File Count "
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error(
					"DAOException occured while getting Deleted File Count "
						+ "Exception Message: "
						+ e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.ibm.km.dao.KmFileReportDao#getFileCount(java.lang.String)
	 */
	public FileReportDto getFileCount(String elementId) throws LMSException {
		Connection con = null;
		ResultSet rs = null;
		FileReportDto dto = new FileReportDto();
		ArrayList fileList = new ArrayList();
		PreparedStatement ps = null;
		int deletedFiles = 0;
		int  totalFileCount=0;
		//KmFileReportFormBean formBean= new KmFileReportFormBean();
		logger.info("Entered in getFileCount method");
		try {
			ArrayList countList = new ArrayList();
			String val = null;
			StringBuffer query=new StringBuffer(SQL_FILES_COUNT);
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query.append(" with ur ").toString());
			ps.setInt(1, Integer.parseInt(elementId));
			rs = ps.executeQuery();
			while (rs.next()) {
				logger.info("Values" + rs.getInt("DOCUMENT_COUNT"));
				if (rs.getString("STATUS").equals("D")) {
					deletedFiles += rs.getInt("DOCUMENT_COUNT");
					totalFileCount +=rs.getInt("DOCUMENT_COUNT");
				} else {
					if (rs.getString("APPROVAL_STATUS").equals("A")) {
						dto.setApprovedFileCount(
							rs.getString("DOCUMENT_COUNT"));
						totalFileCount +=rs.getInt("DOCUMENT_COUNT");
					} else if (rs.getString("APPROVAL_STATUS").equals("R")) {
						dto.setRejectedFileCount(
							rs.getString("DOCUMENT_COUNT"));
						totalFileCount +=rs.getInt("DOCUMENT_COUNT");
					} else if (rs.getString("APPROVAL_STATUS").equals("O")) {
						dto.setOldFileCount(rs.getString("DOCUMENT_COUNT"));
						totalFileCount +=rs.getInt("DOCUMENT_COUNT");
					} else if (rs.getString("APPROVAL_STATUS").equals("P")) {
						dto.setPendingFileCount(rs.getString("DOCUMENT_COUNT"));
						totalFileCount +=rs.getInt("DOCUMENT_COUNT");
					}
					
					dto.setTotal(totalFileCount);
				}
			}
			dto.setDeletedFileCount(deletedFiles + "");
			logger.info("Exit from getFileCount method");
			return dto;
		} catch (SQLException e) {
			logger.error(
				"SQL Exception occured while getting File Count "
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException("SQL  Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error(
				"Exception occured while getting File Count "
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException("" + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error(
					"DAOException occured while getting File Count "
						+ "Exception Message: "
						+ e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}
		}
	}
}