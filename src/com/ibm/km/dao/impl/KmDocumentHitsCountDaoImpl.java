package com.ibm.km.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.km.dao.KmDocumentHitsCountDao;
import com.ibm.km.forms.KmDocumentHitsCountFormBean;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;

public class KmDocumentHitsCountDaoImpl implements KmDocumentHitsCountDao{

	private static java.util.logging.Logger logger = java.util.logging.Logger
	.getLogger(KmDocumentHitsCountDaoImpl.class.toString());
	
	public ArrayList getTopBarLinks(KmDocumentHitsCountFormBean bean) throws DAOException{
		ArrayList links = new ArrayList();
		//logger.info("Inside daoimpl gettopBarLinks!!!!"+bean.getLobId());
		String sql = "Select Document_id, Document_name,DOC_TYPE from LMS.KM_DOCUMENT_HITS_COUNT where Lob_Id=? and Bar_type=1 order by Document_count desc fetch first 5 rows only  with ur";
		Connection conn = DBConnection.getDBConnection(); 
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1,bean.getLobId());
		rs = pstmt.executeQuery();
		
		while(rs.next()){
			KmDocumentHitsCountFormBean bean1 = new KmDocumentHitsCountFormBean();
			bean1.setDocumentId(rs.getInt("Document_Id"));
			bean1.setDocumentName(rs.getString("Document_name"));
			bean1.setDocType(rs.getInt("DOC_TYPE"));
			links.add(bean1);
		}
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
		finally
		{
			try
			{
				DBConnection.releaseResources(conn,pstmt,rs);
			}
			catch(DAOException e)
			{
				logger.severe("DAOException occured in getTopBarLinks" + "Exception Message: " + e.getMessage());
				throw new LMSException(e.getMessage(),e);
			}
		}	
		return links;
	}

	public ArrayList getBottomBarLinks(KmDocumentHitsCountFormBean bean) throws DAOException{
		ArrayList links = new ArrayList();
		//logger.info("Inside daoimpl getbottomBarLinks!!!!"+bean.getLobId());
		String sql = "Select Document_id, Document_name,DOC_TYPE from LMS.KM_DOCUMENT_HITS_COUNT where Lob_Id=? and Bar_type=2 order by Document_count desc fetch first 5 rows only  with ur";
		Connection conn = DBConnection.getDBConnection(); 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1,bean.getLobId());
		rs = pstmt.executeQuery();
		
		while(rs.next()){
			KmDocumentHitsCountFormBean bean1 = new KmDocumentHitsCountFormBean();
			bean1.setDocumentId(rs.getInt("Document_Id"));
			bean1.setDocumentName(rs.getString("Document_name"));
			bean1.setDocType(rs.getInt("DOC_TYPE"));
		    links.add(bean1);
		}
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
		finally
		{
			try
			{
				DBConnection.releaseResources(conn,pstmt,rs);
			}
			catch(DAOException e)
			{
				logger.severe("DAOException occured in getBottomBarLinks" + "Exception Message: " + e.getMessage());
				throw new LMSException(e.getMessage(),e);
			}
		}	
		
		return links;
	}

	
}
