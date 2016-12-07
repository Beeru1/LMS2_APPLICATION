package com.ibm.km.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ibm.km.dao.KmRcContentUploadDao;
import com.ibm.km.dto.KmRcDataDTO;
import com.ibm.km.dto.KmRcHeaderDTO;
import com.ibm.km.dto.KmRcTypeDTO;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.exception.LMSException;

public class KmRcContentUploadDaoImpl implements KmRcContentUploadDao  {
	
	private static final Logger logger;
	
	static {
		  logger = Logger.getLogger(KmRcContentUploadDaoImpl.class);
		 }
	
	protected static final String GET_COMBO ="SELECT * FROM LMS.KM_RC_TYPE_MSTR";
	protected static final String GET_HEADERS = "SELECT * FROM LMS.KM_RC_MSTR";
	protected static final String INSERT_RC_DATA = "INSERT INTO LMS.KM_RC_DATA(TOPIC, RC_VALUE, RC_TYPE, RC_HEADER_ID, VALUE, PATH, START_DT, END_DT, DOCUMENT_ID, CIRCLE_ID) VALUES(?,?,?,?,?,?,?,?,?,?)";
	protected static final String GET_RC_DATA = "SELECT RD.*,RM.RC_HEADER AS HEADER FROM LMS.KM_RC_DATA RD,LMS.KM_RC_MSTR RM WHERE DOCUMENT_ID = ? AND RD.RC_HEADER_ID = RM.RC_ID"; 
	protected static final String GET_RC_TYPE_NAME = "SELECT * FROM LMS.KM_RC_TYPE_MSTR WHERE RC_TYPE_ID = ?";
	protected static final String DELETE_RC_DATA = "DELETE FROM LMS.KM_RC_DATA WHERE DOCUMENT_ID = ? ";
	protected static final String GET_DOC_IDS = "SELECT RD.DOCUMENT_ID FROM LMS.KM_RC_DATA RD,KM_DOCUMENT_MSTR DM WHERE RD.RC_VALUE = ? AND RD.RC_TYPE IN ( ";
	protected static final String GET_RC_DATA_CSR = "SELECT RD.TOPIC,RM.RC_HEADER AS HEADER,RD.VALUE FROM LMS.KM_RC_DATA RD,LMS.KM_RC_MSTR RM WHERE DOCUMENT_ID = ? AND RD.RC_HEADER_ID = RM.RC_ID";
	protected static final String GET_RC_HEADER_ID = "SELECT RC_ID FROM LMS.KM_RC_MSTR WHERE RC_HEADER = ?";
	
	public ArrayList<KmRcTypeDTO> getCombo() throws LMSException {
		  KmRcTypeDTO dto;
		  Connection con = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;

		  ArrayList<KmRcTypeDTO> comboList = new ArrayList<KmRcTypeDTO>();

		  try {
		   StringBuffer query =new StringBuffer(GET_COMBO);
		   con = DBConnection.getDBConnection();
		   ps = con.prepareStatement(query.append(" with ur").toString());
		  
		   rs = ps.executeQuery();
		
		   while (rs.next()) { 
		    dto = new KmRcTypeDTO();
		    dto.setRCTypeId(rs.getInt("RC_TYPE_ID"));
		    dto.setRCTypeValue(rs.getString("RC_TYPE_NAME"));
		    comboList.add(dto);
		   }
		  
		  } catch (SQLException e) {
		   logger.info(e);
		  
		   logger.error(
		    "SQL Exception occured while getting combo items."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("SQLException: " + e.getMessage(), e);
		  } catch (Exception e) {
		   logger.info(e);
		   
		   logger.error(
		    "Exception occured while getting combo items."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("Exception: " + e.getMessage(), e);
		  } finally {
		   try {

		    if (con != null) {
		     con.setAutoCommit(true);
		     con.close();
		    }
		    DBConnection.releaseResources(null, ps, rs);
		   } catch (Exception e) {
		  
		   }
		  }
		  return comboList;
		 }//getCombo


	public ArrayList<KmRcHeaderDTO> getHeaders() throws LMSException {
		
		KmRcHeaderDTO dto;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<KmRcHeaderDTO> headerList = new ArrayList<KmRcHeaderDTO>();

		  try {
		   StringBuffer query =new StringBuffer(GET_HEADERS);
		   con = DBConnection.getDBConnection();
		   ps = con.prepareStatement(query.append(" with ur").toString());
		  
		   rs = ps.executeQuery();
		
		   while (rs.next()) { 
			   dto = new KmRcHeaderDTO();
			   dto.setRCId(rs.getInt("RC_ID"));
			   dto.setRCHeader(rs.getString("RC_HEADER"));
			   headerList.add(dto);
		   }
		  
		  } catch (SQLException e) {
		   logger.info(e);
		  
		   logger.error(
		    "SQL Exception occured while getting Headers."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("SQLException: " + e.getMessage(), e);
		  } catch (Exception e) {
		   logger.info(e);
		   
		   logger.error(
		    "Exception occured while getting Headers."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("Exception: " + e.getMessage(), e);
		  } finally {
		   try {

		    if (con != null) {
		     con.setAutoCommit(true);
		     con.close();
		    }
		    DBConnection.releaseResources(null, ps, rs);
		   } catch (Exception e) {
		  
		   }
		  }
		  return headerList;
	}
	
	
	public int insertRcData(KmRcDataDTO dto) throws LMSException {
		  logger.info("Entered Insert for Table KM_RC_DATA");
		  Connection con = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
		  int rowsUpdated = 0;
		  try {
		   con = DBConnection.getDBConnection();

		   ps = con.prepareStatement(INSERT_RC_DATA);
		   
		 ArrayList<String> headerList = dto.getHeaderList();  
		 ArrayList<String> valueList  = dto.getValueList(); 
		   
		 for(int i=0 ;i<dto.getTotalHeaders();i++)
		 { 
		   ps.setString(1, dto.getRcTopic().trim());
		   ps.setString(2, dto.getRcValue().trim());
		   ps.setString(3, dto.getRcType());
		   ps.setInt(4,getRcHeaderId(headerList.get(i)));
		   ps.setString(5, valueList.get(i).trim());
		   ps.setString(6, dto.getContentPath());
		   ps.setDate(7,(Date) dto.getStartDt());
		   ps.setDate(8,(Date) dto.getEndDt());
		   ps.setInt(9,dto.getDocumentId());
		   ps.setInt(10,dto.getCircleId());
		   ps.addBatch();
		 }  
		
		  ps.executeBatch();
		  
		  } catch (SQLException e) {
			  e.printStackTrace();
		   logger.info(e);
		   e.printStackTrace();
		   logger.error(
		    "SQL Exception occured while inserting."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("SQLException: " + e.getMessage(), e);
		  } catch (Exception e) {
			  e.printStackTrace();
		   logger.info(e);
		   logger.error(
		    "Exception occured while inserting."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("Exception: " + e.getMessage(), e);
		  } finally {
		   try {

		    if (con != null) {
		     con.setAutoCommit(true);
		     con.close();
		    }
		    DBConnection.releaseResources(null, ps, rs);
		   } catch (Exception e) {
		   
		   }
		  }
		  return rowsUpdated;
		 }//insertRcData


	public KmRcDataDTO getRcData(String documentId) throws LMSException {
		  
		  KmRcDataDTO dto = new KmRcDataDTO();
		  ArrayList<String> headerList = new ArrayList<String>();
		  ArrayList<String> valueList  = new ArrayList<String>();
		  Connection con = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
		  
		  try {
		   StringBuffer query =new StringBuffer(GET_RC_DATA);
		   con = DBConnection.getDBConnection();
		   ps = con.prepareStatement(query.append(" with ur").toString());
		   ps.setInt(1,Integer.parseInt(documentId));
		   
		   //logger.info("documentId "+documentId);
		  
		   rs = ps.executeQuery();
		
		   while (rs.next()) { 
			   headerList.add(rs.getString("HEADER"));
			   valueList.add(rs.getString("VALUE"));
			   dto.setRcTopic(rs.getString("TOPIC"));
			   dto.setRcValue(rs.getString("RC_VALUE"));
			   dto.setStartDt(rs.getDate("START_DT"));
			   dto.setEndDt(rs.getDate("END_DT"));
			   dto.setContentPath(rs.getString("PATH"));
			   dto.setRcType(rs.getString("RC_TYPE"));
			   dto.setRcTypeId(rs.getString("RC_TYPE"));
			   dto.setCircleId(rs.getInt("CIRCLE_ID"));
		   }
		   
		   dto.setHeaderList(headerList);
		   dto.setValueList(valueList);
		   
		   if("" != dto.getRcType() || null != dto.getRcType());
		    dto.setRcType(getRcType(Integer.parseInt(dto.getRcType())));
		   
		   
		   
		  
		  } catch (SQLException e) {
		   logger.info(e);
		   e.printStackTrace();
		   logger.error(
		    "SQL Exception occured while getting RC Data."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("SQLException: " + e.getMessage(), e);
		  } catch (Exception e) {
		   logger.info(e);
		   e.printStackTrace();
		   logger.error(
		    "Exception occured while getting RC Data."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("Exception: " + e.getMessage(), e);
		  } finally {
		   try {

		    if (con != null) {
		     con.setAutoCommit(true);
		     con.close();
		    }
		    DBConnection.releaseResources(null, ps, rs);
		   } catch (Exception e) {
		  
		   }
		  }
		  return dto;
	}//getRcData
	
	public int getRcHeaderId(String rcHeader) throws LMSException {
		  Connection con = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;

		  int rcHeaderId = 0;

		  try {
		   StringBuffer query =new StringBuffer(GET_RC_HEADER_ID);
		   con = DBConnection.getDBConnection();
		   ps = con.prepareStatement(query.append(" with ur").toString());
		  
		   ps.setString(1,rcHeader.trim());
		   rs = ps.executeQuery();
		
		   while (rs.next()) { 
			   rcHeaderId = rs.getInt("RC_ID");
		   }
		  
		  } catch (SQLException e) {
			 e.printStackTrace(); 
		   logger.info(e);
		  
		   logger.error(
		    "SQL Exception occured while getting RC Type."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("SQLException: " + e.getMessage(), e);
		  } catch (Exception e) {
			e.printStackTrace();
		   logger.info(e);
		   
		   logger.error(
		    "Exception occured while getting RC Type."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("Exception: " + e.getMessage(), e);
		  } finally {
		   try {

		    if (con != null) {
		     con.setAutoCommit(true);
		     con.close();
		    }
		    DBConnection.releaseResources(null, ps, rs);
		   } catch (Exception e) {
		  
		   }
		  }
		  return rcHeaderId;
		 }//getRcType
	
	public String getRcType(int rcTypeId) throws LMSException {
		  Connection con = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;

		  String rcTypeName="";

		  try {
		   StringBuffer query =new StringBuffer(GET_RC_TYPE_NAME);
		   con = DBConnection.getDBConnection();
		   ps = con.prepareStatement(query.append(" with ur").toString());
		  
		   ps.setInt(1,rcTypeId);
		   rs = ps.executeQuery();
		
		   while (rs.next()) { 
			   rcTypeName = rs.getString("RC_TYPE_NAME");
		   }
		  
		  } catch (SQLException e) {
			 e.printStackTrace(); 
		   logger.info(e);
		  
		   logger.error(
		    "SQL Exception occured while getting RC Type."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("SQLException: " + e.getMessage(), e);
		  } catch (Exception e) {
			e.printStackTrace();
		   logger.info(e);
		   
		   logger.error(
		    "Exception occured while getting RC Type."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("Exception: " + e.getMessage(), e);
		  } finally {
		   try {

		    if (con != null) {
		     con.setAutoCommit(true);
		     con.close();
		    }
		    DBConnection.releaseResources(null, ps, rs);
		   } catch (Exception e) {
		  
		   }
		  }
		  return rcTypeName;
		 }//getRcType
	
	public String deleteRcData(String documentId) throws LMSException {
		logger.info("Entered delete RC Data for table LMS.KM_RC_DATA");
		  Connection con = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;

		  String message = "failure";
		  try {
		   con = DBConnection.getDBConnection();
		   StringBuffer query =new StringBuffer(DELETE_RC_DATA);
		   ps = con.prepareStatement(query.append(" with ur").toString());
		   ps.setInt(1, Integer.parseInt(documentId));
		   ps.executeUpdate();
		   message = "success";
		   logger.info("RC Data Deleted");

		  } catch (SQLException e) {
		   message = "failure";
		   logger.info(e);
		   
		   logger.error(
		    "SQL Exception occured while RC Data Delete."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("SQLException: " + e.getMessage(), e);
		  } catch (Exception e) {
		   message = "failure";
		   logger.info(e);
		  
		   logger.error(
		    "Exception occured while getting RC Data Delete."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("Exception: " + e.getMessage(), e);
		  } finally {
		   try {

		    if (con != null) {
		     con.setAutoCommit(true);
		     con.close();
		    }
		    DBConnection.releaseResources(null, ps, rs);
		   } catch (Exception e) {
		   
		   }
		  }
		  return message;
	
	
	}//deleteRcData
	
	public ArrayList<Integer> getDocIds(String rcValue,String rcType) throws LMSException {
		  Connection con = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;

		  ArrayList<Integer> docList = new ArrayList<Integer>();

		  try {
		   StringBuffer query =new StringBuffer(GET_DOC_IDS);
		   con = DBConnection.getDBConnection();
		   query.append(rcType);
		   ps = con.prepareStatement(query.append(" ) AND RD.DOCUMENT_ID = DM.DOCUMENT_ID AND DM.STATUS = 'A' GROUP BY RD.DOCUMENT_ID with ur").toString());
		   ps.setString(1,rcValue.trim());
		  
		  
		   rs = ps.executeQuery();
		
		   while (rs.next()) { 
		    docList.add(rs.getInt("DOCUMENT_ID"));
		   }
		  
		  } catch (SQLException e) {
		   e.printStackTrace();
		   logger.error(
		    "SQL Exception occured while getting documents."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("SQLException: " + e.getMessage(), e);
		  } catch (Exception e) {
		   e.printStackTrace();
		   logger.error(
		    "Exception occured while getting documents."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("Exception: " + e.getMessage(), e);
		  } finally {
		   try {

		    if (con != null) {
		     con.setAutoCommit(true);
		     con.close();
		    }
		    DBConnection.releaseResources(null, ps, rs);
		   } catch (Exception e) {
		  
		   }
		  }
		  return docList;
		 }//getDocIds
	
	
	
	public ArrayList<HashMap<String,String>> getRcDataCsr(String rcValue,String rcType) throws LMSException {
		  
		  HashMap<String, String>  valueMap;
		  ArrayList<HashMap<String,String>> valueList = new ArrayList<HashMap<String,String>>();
		  Connection con = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
		  
		  try {
			  
		   ArrayList<Integer> docList = getDocIds(rcValue,rcType);  
		   
		   StringBuffer query =new StringBuffer(GET_RC_DATA_CSR);
		   con = DBConnection.getDBConnection();
		   ps = con.prepareStatement(query.append(" with ur").toString());
		   
		   for(int i=0;i<docList.size();i++)
		   {   
			   valueMap = new HashMap<String,String>();
			   
			   ps.setInt(1,docList.get(i));
			   rs = ps.executeQuery();
			   
			if(rs.next()) 
			{
				valueMap.put("-1",rs.getString("TOPIC"));
				valueMap.put(rs.getString("HEADER"),rs.getString("VALUE"));
			}
			else
			{
				valueList = null;
			}
		
		   while (rs.next()) 
		     { 
			   valueMap.put(rs.getString("HEADER"),rs.getString("VALUE"));
		     }
		   valueList.add(valueMap);
		  
		   } 
		  
		  } catch (SQLException e) {
		   logger.info(e);
		   e.printStackTrace();
		   logger.error(
		    "SQL Exception occured while getting RC Data for CSR."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("SQLException: " + e.getMessage(), e);
		  } catch (Exception e) {
		   logger.info(e);
		   e.printStackTrace();
		   logger.error(
		    "Exception occured while getting RC Data for CSR."
		     + "Exception Message: "
		     + e.getMessage());
		   throw new LMSException("Exception: " + e.getMessage(), e);
		  } finally {
		   try {

		    if (con != null) {
		     con.setAutoCommit(true);
		     con.close();
		    }
		    DBConnection.releaseResources(null, ps, rs);
		   } catch (Exception e) {
		  
		   }
		  }
		  return valueList;
	}//getRcData


}
