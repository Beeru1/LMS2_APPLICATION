package com.ibm.lms.dao.impl;

import antlr.collections.Enumerator;

import com.ibm.lms.common.DBConnection;
import com.ibm.lms.dao.SMSSendReceiveDao;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.wf.dao.AssignedLeadsDAO;
import com.ibm.lms.wf.dao.impl.AssignedLeadsDAOImpl;
import com.ibm.lms.wf.dto.Leads;
//import com.tivoli.pd.jutil.bo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Pattern;


public class SMSSendReceiveDaoImpl implements SMSSendReceiveDao {

	private final static String IS_VALID_LEADID = "SELECT LD.LEAD_ID FROM LEAD_DATA LD ,LEAD_DETAILS LDT WHERE LD.LEAD_ID=LDT.LEAD_ID AND LD.LEAD_ID=? WITH UR";
	private final static String IS_LEAD_UPDATED="SELECT LEAD_STATUS_ID FROM LEAD_DATA WHERE LEAD_ID =? WITH UR";
	private final static String UPDATE_LOST_LEAD="UPDATE LEAD_DATA SET LEAD_STATUS_ID=600 where LEAD_ID=? ";
	private final static String CHECK_ALT_MOB_NO="SELECT ALTERNATE_CONTACT_NUMBER FROM LEAD_PROSPECT_DETAIL WHERE LEAD_ID=? with ur";
	private final static String UPDATE_ALT_NO="UPDATE LEAD_PROSPECT_DETAIL SET ALTERNATE_CONTACT_NUMBER=? where LEAD_ID=? ";
	private final static String LOG_SMS="INSERT INTO LOG_SMS_REPORT(HEADER_REC,BODY_REC,TIME_REC_SMS) VALUES (?,?,current timestamp) ";
	private final static String UPDATE_SMS="UPDATE LOG_SMS_REPORT set MSISDN=? where ID=? ";
	@Override
	public Long isValidLead(String leadId) throws Exception {
		Connection conn=null;
		try{
		conn=DBConnection.getDBConnection();
		}catch(DAOException daoEx){
			daoEx.printStackTrace();
			throw new DAOException(daoEx.getMessage());
			
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long lead_Id = null ;
		Pattern pattern = Pattern.compile(".*[^0-9].*");
		try 
		{
			if(leadId !=null && leadId.length() >0 && !pattern.matcher(leadId).matches())
			{
				
				ps = conn.prepareStatement(IS_VALID_LEADID);
				ps.setLong(1, Long.parseLong(leadId.toString()));
				rs = ps.executeQuery();
				if(rs.next())
				{
					return rs.getLong(1);
				}
			}
		}catch(Exception expx){
			expx.printStackTrace();
			throw new DAOException(expx.getMessage());
		}finally{
			DBConnection.releaseResources(conn, ps, rs);
		}
		return lead_Id;
	}
	@Override
	public boolean updateWonLostLead(String leadId) throws Exception {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
	
		conn=DBConnection.getDBConnection();
		ps=conn.prepareStatement(IS_LEAD_UPDATED);
		ps.setLong(1, Long.parseLong(leadId));
		rs=ps.executeQuery();
		
		while(rs.next()){
			int status=rs.getInt("LEAD_STATUS_ID");
			if(status==500 || status==600)
				 return false;
		}
		}catch(Exception expx){
			expx.printStackTrace();
			throw new DAOException(expx.getMessage());
		}finally{
			DBConnection.releaseResources(conn, ps, rs);
		}
		
		return true;
	}
	@Override
	public void updateLost(String leadId, String status,String msIsdn) throws Exception {
		// TODO Auto-generated method stub
		AssignedLeadsDAO assignedLeadsDAO = new AssignedLeadsDAOImpl();
		Leads leads=new Leads();
		leads.setLeadID(leadId);
		leads.setStatus("600");
		leads.setSubStatus("600");
		leads.setRemarks("OK");
		leads.setCafNumber("");
		leads.setUpdatedBy(msIsdn);
		ArrayList<Leads> leadList=new ArrayList<Leads>();
		leadList.add(leads);
		assignedLeadsDAO.closeTheLead(leadList);
		
		
	}
	@Override
	public void updateLeadWon(Long leadId, String Satus, String msIsdn,
			String cafNo) throws Exception {
		AssignedLeadsDAO assignedLeadsDAO = new AssignedLeadsDAOImpl();
		Leads leads=new Leads();
		leads.setLeadID(leadId.toString());
		leads.setStatus("500");
		leads.setSubStatus("500");
		leads.setRemarks("OK");
		leads.setCafNumber(cafNo);
		leads.setUpdatedBy(msIsdn);
		ArrayList<Leads> leadList=new ArrayList<Leads>();
		leadList.add(leads);
		assignedLeadsDAO.closeTheLead(leadList);
		
	}
	@Override
	public void updateLeadMobNo(Long leadId, String mobNo) throws Exception {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
	
		conn=DBConnection.getDBConnection();
		ps=conn.prepareStatement(CHECK_ALT_MOB_NO);
		ps.setLong(1, leadId);
		rs=ps.executeQuery();
		
		if(rs.next()){
			if(rs.getString("ALTERNATE_CONTACT_NUMBER")==null){
				ps=conn.prepareStatement(UPDATE_ALT_NO);
				ps.setString(1, mobNo);
				ps.setLong(2, leadId);
				ps.executeUpdate();
			}
		}
		}catch(Exception expx){
			expx.printStackTrace();
			throw new DAOException(expx.getMessage());
		}finally{
			DBConnection.releaseResources(conn, ps, rs);
		}
	}
	@Override
	public long logSMS(String header, String body) throws Exception {
		Connection conn=null;
		try{
		conn=DBConnection.getDBConnection();
		}catch(DAOException daoEx){
			daoEx.printStackTrace();
			throw new DAOException(daoEx.getMessage());
			
		}
		conn.setAutoCommit(false);
		long smsID=-1;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String autoGen[]={"ID"};
		try{
		ps=conn.prepareStatement(LOG_SMS, autoGen);
		ps.setString(1, header);
		ps.setString(2, body);
		int recNo=ps.executeUpdate();
		if(recNo!=0){
			ResultSet keys=ps.getGeneratedKeys();
			while(keys.next()){
				smsID=keys.getLong(1);
			}
			return smsID;
			
		}
		
		}catch(Exception expc){
			expc.printStackTrace();
			throw new DAOException(expc.getMessage());
		}
		finally{
			conn.commit();
			DBConnection.releaseResources(conn, ps, rs);
		}
		return smsID;
	}
	@Override
	public void updateSMSLog(long Id, String msisdn) throws Exception {
		// TODO Auto-generated method stub
		Connection conn=null;
		try{
		conn=DBConnection.getDBConnection();
		}catch(DAOException daoEx){
			daoEx.printStackTrace();
			throw new DAOException(daoEx.getMessage());
			
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps=conn.prepareStatement(UPDATE_SMS);
			ps.setString(1, msisdn);
			ps.setLong(2, Id);
			ps.executeUpdate();
			}catch(Exception expc){
				expc.printStackTrace();
				throw new DAOException(expc.getMessage());
			}
			finally{
				DBConnection.releaseResources(conn, ps, rs);
			}
		
	}

}
