/*
 * Created on Feb 20, 2008
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

import com.ibm.km.dao.KmFileDao;
import com.ibm.km.dto.KmDocumentMstr;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.exception.LMSException;
/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KmFileDaoImpl implements KmFileDao {

	public static Logger logger = Logger.getLogger(KmFileDaoImpl.class);

	protected static final String SQL_SELECT_FILES ="SELECT DOCUMENT_ID, DOCUMENT_NAME, DOCUMENT_DISPLAY_NAME, doc.CIRCLE_ID,doc.CATEGORY_ID,doc.SUB_CATEGORY_ID, APPROVAL_STATUS, UPLOADED_DT,APPROVAL_REJECTION_DT, CATEGORY_NAME,SUB_CATEGORY_NAME FROM LMS.KM_DOCUMENT_MSTR doc,LMS.KM_CATEGORY_MSTR cat,LMS.KM_SUB_CATEGORY_MSTR subcat WHERE doc.STATUS ='A' AND doc.CATEGORY_ID=cat.CATEGORY_ID AND doc.SUB_CATEGORY_ID=subcat.SUB_CATEGORY_ID ";

	protected static final String SQL_SELECT_DOCUMENT ="SELECT DOCUMENT_ID, DOCUMENT_GROUP_ID, DOCUMENT_NAME, DOCUMENT_DISPLAY_NAME, DOCUMENT_DESC, SUB_CATEGORY_ID, CATEGORY_ID, CIRCLE_ID, NUMBER_OF_HITS, STATUS, APPROVAL_STATUS, UPLOADED_BY, UPLOADED_DT, UPDATED_BY, UPDATED_DT, APPROVAL_REJECTION_DT, APPROVAL_REJECTION_BY, PUBLISHING_START_DT, PUBLISHING_END_DT FROM LMS.KM_DOCUMENT_MSTR  WHERE LMS.KM_DOCUMENT_MSTR.STATUS ='A' AND DOCUMENT_ID = ? ";

	protected static final String SQL_KEYWORD_SEARCH ="SELECT DOCUMENT_ID, DOCUMENT_NAME, DOCUMENT_DISPLAY_NAME, doc.CIRCLE_ID,doc.CATEGORY_ID,doc.SUB_CATEGORY_ID, APPROVAL_STATUS, UPLOADED_DT,APPROVAL_REJECTION_DT, CATEGORY_NAME,SUB_CATEGORY_NAME FROM LMS.KM_DOCUMENT_MSTR doc,LMS.KM_CATEGORY_MSTR cat,LMS.KM_SUB_CATEGORY_MSTR subcat WHERE doc.STATUS ='A' AND doc.CATEGORY_ID=cat.CATEGORY_ID AND doc.SUB_CATEGORY_ID=subcat.SUB_CATEGORY_ID AND UPPER(doc.DOCUMENT_KEYWORD) LIKE ";

	protected static final String SQL_CSR_KEYWORD_SEARCH ="SELECT DOCUMENT_ID, DOCUMENT_NAME, DOCUMENT_DISPLAY_NAME, doc.CIRCLE_ID,doc.CATEGORY_ID,doc.SUB_CATEGORY_ID, APPROVAL_STATUS, UPLOADED_DT,APPROVAL_REJECTION_DT, CATEGORY_NAME,SUB_CATEGORY_NAME FROM LMS.KM_DOCUMENT_MSTR doc,LMS.KM_CATEGORY_MSTR cat,LMS.KM_SUB_CATEGORY_MSTR subcat WHERE doc.STATUS ='A' AND doc.CATEGORY_ID=cat.CATEGORY_ID AND doc.SUB_CATEGORY_ID=subcat.SUB_CATEGORY_ID AND doc.APPROVAL_STATUS = 'A' AND UPPER(doc.DOCUMENT_KEYWORD) LIKE ";

	protected static final String SQL_DELETE_DOCUMENT ="UPDATE LMS.KM_DOCUMENT_MSTR SET STATUS= 'D', UPDATED_BY = ?, UPDATED_DT= current timestamp WHERE DOCUMENT_ID = ? ";

	/* (non-Javadoc)
	 * @see com.ibm.km.dao.KmFileDao#deleteDocument(java.lang.String)
	 * @delete the file details from the database
	 **/
	// Update the document status to D in LMS.KM_DOCUMENT_MSTR.
	public void deleteDocument(String fileId, String updatedBy)
		throws LMSException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		logger.info("Entered in delete document method");
		try {
			StringBuffer query=new StringBuffer(SQL_DELETE_DOCUMENT);
			//String sql = SQL_DELETE_DOCUMENT;
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query.append("with ur").toString());
			ps.setInt(1, Integer.parseInt(updatedBy));
			ps.setInt(2, Integer.parseInt(fileId));
			//Executing the querry to delete the file
			boolean deleted = ps.execute();
			logger.info("Exit from delete document method");

		} catch (SQLException e) {

			logger.error(
				"SQL Exception occured while deleting document."
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException("SQL Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error(
				"Exception occured while deleting document."
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, null);
			} catch (Exception e) {
				logger.error(
					"DAOException occured while deleting document."
						+ "Exception Message: "
						+ e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.ibm.km.dao.KmFileDao#viewFiles(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public ArrayList viewFiles(
		String circleId,
		String categoryId,
		String subCategoryId,
		String userId)
		throws LMSException {
/*		Connection con = null;
		ResultSet rs = null;
		ArrayList fileList = new ArrayList();
		PreparedStatement ps = null;
		KmDocumentMstr dto;
		logger.info("Entered in viewFiles method");
		try {

			//String sql = "";
			StringBuffer query=new StringBuffer(SQL_SELECT_FILES);
			if (userId.equals("")
				&& subCategoryId.equals("")
				&& categoryId.equals(""))
				//sql =
					query.append(" AND doc.CIRCLE_ID = ").append(circleId).append("  ORDER BY doc.DOCUMENT_ID").toString();
			if (userId.equals("")
				&& subCategoryId.equals("")
				&& !categoryId.equals(""))
				sql =
					SQL_SELECT_FILES
						+ " AND doc.CIRCLE_ID = "
						+ circleId
						+ " AND doc.CATEGORY_ID = "
						+ categoryId
						+ "  ORDER BY doc.DOCUMENT_ID";
			if (userId.equals("") && !subCategoryId.equals(""))
				sql =
					SQL_SELECT_FILES
						+ " AND doc.CIRCLE_ID = "
						+ circleId
						+ " AND doc.CATEGORY_ID = "
						+ categoryId
						+ " AND doc.SUB_CATEGORY_ID = "
						+ subCategoryId
						+ " ORDER BY doc.DOCUMENT_ID";
			if (!userId.equals("")
				&& !categoryId.equals("")
				&& subCategoryId.equals(""))
				sql =
					SQL_SELECT_FILES
						+ " AND doc.CIRCLE_ID = "
						+ circleId
						+ " AND doc.CATEGORY_ID = "
						+ categoryId
						+ "  AND doc.UPLOADED_BY = "
						+ userId
						+ " ORDER BY doc.DOCUMENT_ID";
			if (!userId.equals("") && !subCategoryId.equals(""))
				sql =
					SQL_SELECT_FILES
						+ " AND doc.CIRCLE_ID = "
						+ circleId
						+ " AND doc.CATEGORY_ID = "
						+ categoryId
						+ " AND doc.SUB_CATEGORY_ID = "
						+ subCategoryId
						+ " AND doc.UPLOADED_BY = "
						+ userId
						+ " ORDER BY doc.DOCUMENT_ID";
			if (!circleId.equals("")
				&& categoryId.equals("")
				&& subCategoryId.equals("")
				&& !userId.equals("")) {
				logger.info("Last querry");
				sql =
					SQL_SELECT_FILES
						+ " AND doc.CIRCLE_ID = "
						+ circleId
						+ "   ORDER BY doc.DOCUMENT_ID";
			}

			con = DBConnection.getDBConnection();

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				dto = new KmDocumentMstr();

				String docPath =
					rs.getString("CIRCLE_ID")
						+ "/"
						+ rs.getString("CATEGORY_ID")
						+ "/"
						+ rs.getString("SUB_CATEGORY_ID")
						+ "/"
						+ rs.getString("DOCUMENT_NAME");
				dto.setDocumentPath(docPath);
				dto.setDocumentId(rs.getString("DOCUMENT_ID"));
				dto.setDocumentName(rs.getString("DOCUMENT_NAME"));
				dto.setDocumentDisplayName(
					rs.getString("DOCUMENT_DISPLAY_NAME"));
				//	logger.info("APPROVAL_STATUS   "+rs.getString("APPROVAL_STATUS"));
				if (("A").equals(rs.getString("APPROVAL_STATUS"))) {
					dto.setApprovalStatus("Approved");

				} else if (("S").equals(rs.getString("APPROVAL_STATUS"))) {
					dto.setApprovalStatus("Submitted");

				} else if (("R").equals(rs.getString("APPROVAL_STATUS"))) {
					dto.setApprovalStatus("Rejected");

				} else if (("P").equals(rs.getString("APPROVAL_STATUS"))) {
					dto.setApprovalStatus("Pending");

				} else if (("O").equals(rs.getString("APPROVAL_STATUS"))) {
					dto.setApprovalStatus("Versioned");

				}
				//			Remove seconds and milliseconds from ever page wherever displayed : defect no. MASDB00060756

				if (rs.getString("UPLOADED_DT") != null) {
					dto.setUploadedDate(
						rs.getString("UPLOADED_DT").substring(0, 16));
				}

				if (rs.getString("APPROVAL_REJECTION_DT") != null) {
					dto.setApprovalRejectionDate(
						rs.getString("APPROVAL_REJECTION_DT").substring(0, 16));
				}

				fileList.add(dto);
			}

			logger.info("Exit from viewFiles method");
			return fileList;

		} catch (SQLException e) {
			logger.error(
				"SQL Exception occured while deleting document."
					+ "Exception Message: "
					+ e.getMessage());
			throw new KmException("SQL Exception: " + e.getMessage(), e);
		} catch (Exception e) {

			logger.error(
				"Exception occured while deleting document."
					+ "Exception Message: "
					+ e.getMessage());
			throw new KmException(" Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error(
					"DAOException occured while deleting document."
						+ "Exception Message: "
						+ e.getMessage());
				throw new KmException("DAO Exception: " + e.getMessage(), e);
			}
		}  */
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ibm.km.dao.KmFileDao#keywordFileSearch(java.lang.String, java.lang.String, java.lang.String)
	 */
	public ArrayList keywordFileSearch(
		String keyword,
		String circleId,
		String uploadedBy)
		throws LMSException {
		Connection con = null;
		ResultSet rs = null;
		ArrayList fileList = new ArrayList();
		PreparedStatement ps = null;
		KmDocumentMstr dto;
		logger.info("Entered in keywordFileSearch method");
		try {
			//String sql = "";
			StringBuffer query=new StringBuffer(SQL_KEYWORD_SEARCH);
			String Keyword = keyword.toUpperCase();

			if (!uploadedBy.equals("") && circleId.equals(""))
				//sql =
				query.append("'%").append(Keyword).append("%' AND doc.UPLOADED_BY = ").append(uploadedBy).toString();
			if (uploadedBy.equals("")
				&& !circleId.equals("")
				&& !keyword.equals(""))
				//sql =
				query.append("'%").append(Keyword).append("%' AND doc.CIRCLE_ID = ").append(circleId).toString();
			if (circleId.equals("") && uploadedBy.equals(""))
				query.append("'%").append(Keyword).append("%'").toString();
			if (!uploadedBy.equals("")
				&& !circleId.equals("")
				&& !keyword.equals("")) {
				uploadedBy = "";
				//sql =
					query.append("'%").append(Keyword).append("%' AND doc.CIRCLE_ID = ").append(circleId).toString();
			}
			query.append(" ORDER BY doc.DOCUMENT_ID").toString();
			logger.info(query);
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new KmDocumentMstr();

				String docPath =
					rs.getString("CIRCLE_ID")
						+ "/"
						+ rs.getString("CATEGORY_ID")
						+ "/"
						+ rs.getString("SUB_CATEGORY_ID")
						+ "/"
						+ rs.getString("DOCUMENT_NAME");
				dto.setDocumentPath(docPath);
				dto.setDocumentId(rs.getString("DOCUMENT_ID"));
				dto.setDocumentName(rs.getString("DOCUMENT_NAME"));
				dto.setDocumentDisplayName(
					rs.getString("DOCUMENT_DISPLAY_NAME"));

				if (("A").equals(rs.getString("APPROVAL_STATUS"))) {
					dto.setApprovalStatus("Approved");

				} else if (("S").equals(rs.getString("APPROVAL_STATUS"))) {
					dto.setApprovalStatus("Submitted");

				} else if (("R").equals(rs.getString("APPROVAL_STATUS"))) {
					dto.setApprovalStatus("Rejected");

				} else if (("P").equals(rs.getString("APPROVAL_STATUS"))) {
					dto.setApprovalStatus("Pending");

				} else if (("O").equals(rs.getString("APPROVAL_STATUS"))) {
					dto.setApprovalStatus("Versioned");

				}

				if (rs.getString("UPLOADED_DT") != null) {
					dto.setUploadedDate(
						rs.getString("UPLOADED_DT").substring(0, 16));
				}

				if (rs.getString("APPROVAL_REJECTION_DT") != null) {
					dto.setApprovalRejectionDate(
						rs.getString("APPROVAL_REJECTION_DT").substring(0, 16));
				}

				dto.setCategoryName(rs.getString("CATEGORY_NAME"));
				dto.setSubCategoryName(rs.getString("SUB_CATEGORY_NAME"));

				fileList.add(dto);

			}

			logger.info("Exit from keywordFileSearch method");
			return fileList;

		} catch (SQLException e) {
			logger.error(
				"SQL Exception occured while searching keyword."
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException("SQL Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error(
				"Exception occured while searching keyword."
					+ "Exception Message: "
					+ e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error(
					"DAOException occured while searching keyword."
						+ "Exception Message: "
						+ e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}
		}
	}
}