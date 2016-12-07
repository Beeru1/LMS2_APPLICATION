package com.ibm.km.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.km.dao.KmViewDthAegingDao;
import com.ibm.km.dto.KmViewDthAegingDto;
import com.ibm.km.dto.OfferDetailsDTO;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.exception.LMSException;

public class KmViewDthAegingDaoImpl implements KmViewDthAegingDao{

	private static final Logger logger;
	static {
		logger = Logger.getLogger(KmViewDthAegingDaoImpl.class);
	}

	
	public ArrayList<KmViewDthAegingDto> getBucketId(String days) throws Exception{
		ArrayList<KmViewDthAegingDto> bucketDetails = new ArrayList<KmViewDthAegingDto>();
		Connection conn = null;
	    ResultSet rs = null;
	    PreparedStatement pstmt = null;
	    String query = "Select BUCKET_ID,FROM_DAY,TO_DAY from LMS.KM_BUCKET_MSTR where ?>=FROM_DAY and ?<=TO_DAY and STATUS='A'";
	    try
	    {
	    
	    conn = DBConnection.getDBConnection();
	    pstmt = conn.prepareStatement(query);
	    pstmt.setInt(1,Integer.parseInt(days));
	    pstmt.setInt(2,Integer.parseInt(days));
	    rs = pstmt.executeQuery();
	    while(rs.next()){
	    	KmViewDthAegingDto dto = new KmViewDthAegingDto();
	    	dto.setBucketId(rs.getInt("BUCKET_ID")+"");
	    	dto.setFromDay(rs.getInt("FROM_DAY")+"");
	    	dto.setToDay(rs.getInt("TO_DAY")+"");
	    	bucketDetails.add(dto);
	    }
	    
	    logger.info((new StringBuilder("List is returned :")).append(bucketDetails.size()).toString());
	    }
	    catch (SQLException e) {
			   logger.info(e);
			  
			   logger.error(
			    "SQL Exception occured while getBucketId."
			     + "Exception Message: "
			     + e.getMessage());
			   throw new LMSException("SQLException: " + e.getMessage(), e);
			  } catch (Exception e) {
			   logger.info(e);
			   
			   logger.error(
			    "Exception occured while getBucketId."
			     + "Exception Message: "
			     + e.getMessage());
			   throw new LMSException("Exception: " + e.getMessage(), e);
			  } finally {
			   try {

			    if (conn != null) {
			     conn.setAutoCommit(true);
			     conn.close();
			    }
			    DBConnection.releaseResources(conn, pstmt, rs);
			   } catch (Exception e) {
			  
			   }
			  }
	    
		return bucketDetails;
	}
	public ArrayList<OfferDetailsDTO> getOfferDetails(String bucketId) throws Exception{
		ArrayList<OfferDetailsDTO> offerList = new ArrayList<OfferDetailsDTO>();
		Connection conn = null;
	    ResultSet rs = null;
	    PreparedStatement pstmt = null;
	    String query = "Select OFFER_TITLE,OFFER_DESC from LMS.KM_OFFER_UPLOAD where BUCKET_ID=? and END_DATE>=sysdate";
	    
	    try{
	    
	    conn = DBConnection.getDBConnection();
	    pstmt = conn.prepareStatement(query);
	    pstmt.setInt(1,Integer.parseInt(bucketId));
	    rs = pstmt.executeQuery();
	    
	    while(rs.next()){
	    	OfferDetailsDTO dto = new OfferDetailsDTO();
	    	dto.setOfferTitle(rs.getString("OFFER_TITLE"));
	    	dto.setOfferDesc(rs.getString("OFFER_DESC"));
	    	offerList.add(dto);
	    }
	    }
	    catch (SQLException e) {
			   logger.info(e);
			  
			   logger.error(
			    "SQL Exception occured while getOfferDetails."
			     + "Exception Message: "
			     + e.getMessage());
			   throw new LMSException("SQLException: " + e.getMessage(), e);
			  } catch (Exception e) {
			   logger.info(e);
			   
			   logger.error(
			    "Exception occured while getOfferDetails."
			     + "Exception Message: "
			     + e.getMessage());
			   throw new LMSException("Exception: " + e.getMessage(), e);
			  } finally {
			   try {

			    if (conn != null) {
			     conn.setAutoCommit(true);
			     conn.close();
			    }
			    DBConnection.releaseResources(conn, pstmt, rs);
			   } catch (Exception e) {
			  
			   }
			  }
	    
		
		return offerList;
	}
	
	
}
