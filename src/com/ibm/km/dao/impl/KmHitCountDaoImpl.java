/*
 * Created on Nov 28, 2008
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

import com.ibm.km.dao.KmHitCountDAO;
import com.ibm.km.dto.KmHitCountReportDto;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.exception.UserMstrDaoException;

/**
 * @author Atul
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KmHitCountDaoImpl implements KmHitCountDAO {

	/* (non-Javadoc)
	 * @see com.ibm.km.dao.KmHitCountDAO#hitCountReport(java.lang.String)
	 */
	private static final Logger logger;

		static {

			logger = Logger.getLogger(KmHitCountDaoImpl.class);
		}
	protected static final String SQL_HIT_COUNT_REPORT = "WITH nee(element_id,chain) AS ( SELECT  ELEMENT_ID, cast(element_name as VARCHAR(240)) FROM LMS.KM_ELEMENT_MSTR  WHERE element_id = ? 	UNION ALL  SELECT nplus1.element_id,nee.chain || '/' || nplus1.element_name	FROM LMS.KM_ELEMENT_MSTR as nplus1, nee WHERE nee.element_id=nplus1.PARENT_ID)SELECT a.document_id,b.DOCUMENT_DISPLAY_NAME, nee.chain, count(1) AS COUNT FROM LMS.KM_DOCUMENT_VIEWS a,LMS.KM_DOCUMENT_MSTR b, nee, LMS.KM_ELEMENT_MSTR c WHERE a.DOCUMENT_ID=b.document_id and nee.element_id= b.element_id	and b.element_id = c.element_id and b.status='A' and b.approval_status='A'	and  date(a.ACCESSED_ON) = ? GROUP BY b.DOCUMENT_DISPLAY_NAME,a.DOCUMENT_ID,nee.chain order by count desc "; 
		
	public ArrayList hitCountReport(String elementId, String date) throws LMSException {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		KmHitCountReportDto dto = null;
		ArrayList aList = null;
			
		//logger.info("elementId::::::::::::::::::::::"+elementId);
		//logger.info("date::::::::::::::::::::::"+date);
		
		
		logger.info("Entered in hitCountReport method");
		try
		{
			StringBuffer query=new StringBuffer(SQL_HIT_COUNT_REPORT);
			con =  DBConnection.getDBConnection();
			pstmt = con.prepareStatement(query.append(" with ur ").toString());
			pstmt.setString(1,elementId);
			pstmt.setString(2, date);
			rs = pstmt.executeQuery();
			
			aList = new ArrayList();	
			while(rs.next()){
				dto=new KmHitCountReportDto();
				dto.setDocumentName(rs.getString("DOCUMENT_DISPLAY_NAME"));
				dto.setDocumentPath(rs.getString("CHAIN"));
				dto.setNoHits(rs.getString("COUNT"));
				aList.add(dto);
			}
			logger.info("Exit from hitCountReport method");
		}
			
		catch(UserMstrDaoException km)
		{
			logger.error("KmUserMstrDaoException occured in hitCountReport." + "Exception Message: " + km.getMessage());
			throw new LMSException(km.getMessage(),km);
		}
		catch(SQLException e)
		{
			logger.error("SQLException occured in hitCountReport." + "Exception Message: " + e.getMessage());
			throw new LMSException(e.getMessage(),e);
		}
		catch(DAOException e)
		{
			logger.error("DAOException occured in hitCountReport." + "Exception Message: " + e.getMessage());
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
				logger.error("DAOException occured in hitCountReport." + "Exception Message: " + e.getMessage());
				throw new LMSException(e.getMessage(),e);
			}
		}
		return aList;
		
	}

}
