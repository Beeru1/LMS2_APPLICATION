package com.ibm.lms.dao.impl;

/**
 * @author Parnika Sharma 
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.lms.dao.DBConnection;

import com.ibm.lms.dao.AgencyMappingDao;
import com.ibm.lms.dto.AgencyDTO;
import com.ibm.lms.dto.CircleDTO;


import com.ibm.lms.exception.DAOException;


public class AgencyMappingDaoImpl implements AgencyMappingDao {

	Logger logger = Logger.getLogger(AgencyMappingDaoImpl.class);
	// Selecting circles which are not mapped to any agency
	
	
	//Added by srikant
private static AgencyMappingDaoImpl agencyMappingDaoImpl=null;
	
	private AgencyMappingDaoImpl(){
		
	}
	
	public static AgencyMappingDaoImpl agencyMappingDaoInstance()
	{
		if(agencyMappingDaoImpl==null)
		{
			agencyMappingDaoImpl=new AgencyMappingDaoImpl();
		}
		return agencyMappingDaoImpl;
		
	}
	
	public  ArrayList<CircleDTO> getCircleList() throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CircleDTO> circletList = new ArrayList<CircleDTO>();
		CircleDTO dto = null;
		try {
			con = DBConnection.getDBConnection();
			
			ps = con.prepareStatement("SELECT CIRCLE_ID, CIRCLE_NAME FROM CIRCLE_MSTR WHERE STATUS = 'A' AND CIRCLE_ID not in (SELECT DISTINCT(CIRCLE_ID) FROM AGENCY_CIRCLE) WITH UR");
			rs = ps.executeQuery();
			while(rs.next()) {
				dto = new CircleDTO();
				dto.setCircleId(rs.getInt("CIRCLE_ID"));
				dto.setCircleName(rs.getString("CIRCLE_NAME"));
				circletList.add(dto);
			}
		} catch (Exception e) {
			throw new DAOException("Exception occured while getting circle list :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return circletList;
	}
	
	public  String createAgency(AgencyDTO agencyDto) throws DAOException{

		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		ResultSet rs = null;
		String[] multipleCircles = null;
		int agencyId = 0;
		String result="";
		int rowInserted = 0;
		try {
			con = DBConnection.getDBConnection();
			con.setAutoCommit(false);
			
			ps5 = con.prepareStatement("SELECT AGENCY_NAME FROM AGENCY_MSTR WHERE AGENCY_NAME = ?");	
			ps5.setString(1, agencyDto.getAgencyName().trim());
			rs=ps5.executeQuery();
			
			if(rs.next()){
				result = "Duplicate";
				return result;
			}
			
			ps = con.prepareStatement("INSERT INTO AGENCY_MSTR(AGENCY_NAME,AGENCY_DESC,STATUS,IS_DEFAULT,AGENCY_PATH,CLASS_NAME,USERNAME,PASSWORD) VALUES(?,?,'A','N',?,?,?,?) WITH UR");			
			ps.setString(1, agencyDto.getAgencyName());
			ps.setString(2, agencyDto.getAgencyDescription());
			ps.setString(3, agencyDto.getAgencyPath());
			ps.setString(4, agencyDto.getAgencyClass());
			ps.setString(5, agencyDto.getUsername());
			ps.setString(6, agencyDto.getPassword());
			ps.executeUpdate();
			
			ps1 = con.prepareStatement("SELECT AGENCY_ID FROM AGENCY_MSTR WHERE AGENCY_NAME = ? WITH UR");
			ps1.setString(1, agencyDto.getAgencyName());
			rs = ps1.executeQuery();
			if(rs.next()){
				agencyId= rs.getInt("AGENCY_ID");
			}
			
			
			multipleCircles = agencyDto.getCreateMultiple();
			
			ps2 = con.prepareStatement("INSERT INTO AGENCY_CIRCLE(AGENCY_ID,CIRCLE_ID) VALUES(?,?) WITH UR");
			
			if(multipleCircles != null)
			{
				for(int i =0 ; i< multipleCircles.length ; i++)
				{
					if(i==0 && multipleCircles.length > 1)
					{
						continue;
					}
					ps2.setInt(1, agencyId);
					ps2.setInt(2, Integer.parseInt(multipleCircles[i]));
					rowInserted = ps2.executeUpdate();
					if(rowInserted == 1){
						result = "Success";
					}
					else{
						result = "Failure";
						break;
					}
				}
			}
			
			// To make the agency default
			
			if((agencyDto.getDefaultCheck()).equalsIgnoreCase("Yes")){
				ps3 = con.prepareStatement("UPDATE AGENCY_MSTR SET IS_DEFAULT = 'N' WITH UR");
				ps3.executeUpdate();
				ps4 = con.prepareStatement("UPDATE AGENCY_MSTR SET IS_DEFAULT = 'Y' WHERE AGENCY_ID = ? WITH UR");
				ps4.setInt(1, agencyId);
				ps4.executeUpdate();
			}
			
			
			
			con.commit();
			
		} catch (Exception e) {
			try {
				result = "Failure";
				e.printStackTrace();
			con.rollback();
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
				throw new DAOException("Exception occured while createAgency() :  "+ e.getMessage(),e);
			}
			
		} finally {
			try {
				con.setAutoCommit(true);
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				result = "Failure";
				throw new DAOException(e.getMessage(), e);
			}
		}
	
		return result;
		
	}
	
	public  ArrayList<AgencyDTO> getAgencyList() throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<AgencyDTO> agencyList = new ArrayList<AgencyDTO>();
		AgencyDTO dto = null;
		try {
			con = DBConnection.getDBConnection();
			// Selecting Agencies which are active
			ps = con.prepareStatement("SELECT AGENCY_ID,AGENCY_NAME,AGENCY_DESC FROM AGENCY_MSTR WHERE STATUS = 'A' WITH UR");
			rs = ps.executeQuery();
			while(rs.next()) {
				dto = new AgencyDTO();
				dto.setAgencyId(rs.getInt("AGENCY_ID"));
				dto.setAgencyName(rs.getString("AGENCY_NAME"));
				dto.setAgencyDescription(rs.getString("AGENCY_DESC"));
				agencyList.add(dto);
			}
		} catch (Exception e) {
			throw new DAOException("Exception occured while getting agency list :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return agencyList;
	}
	
	public AgencyDTO getAgencyDetails(int agencyId) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		AgencyDTO dto = new AgencyDTO();
		try {
			con = DBConnection.getDBConnection();
			// Getting details of the selected agency
			ps = con.prepareStatement("SELECT AGENCY_DESC, AGENCY_PATH, CLASS_NAME, USERNAME, PASSWORD, IS_DEFAULT FROM AGENCY_MSTR WHERE AGENCY_ID = ? WITH UR");
			ps.setInt(1, agencyId);
			rs = ps.executeQuery();
			if(rs.next()){
				dto.setAgencyDescription(rs.getString("AGENCY_DESC"));
				dto.setAgencyPath(rs.getString("AGENCY_PATH"));
				dto.setAgencyClass(rs.getString("CLASS_NAME"));
				dto.setUsername(rs.getString("USERNAME"));
				dto.setPassword(rs.getString("PASSWORD"));
				dto.setDefaultCheck(rs.getString("IS_DEFAULT"));
			}
			
		} catch (Exception e) {
			throw new DAOException("Exception occured while getting agency Details :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return dto;
	}
	
	public  ArrayList<AgencyDTO> getCircleMappedList(int agencyId) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<AgencyDTO> agencyList = new ArrayList<AgencyDTO>();
		AgencyDTO dto = null;
		try {
			con = DBConnection.getDBConnection();
			// Selecting Agencies which are active
			ps = con.prepareStatement("SELECT a.CIRCLE_ID CIRCLE_ID, a.CIRCLE_NAME CIRCLE_NAME FROM CIRCLE_MSTR a, AGENCY_CIRCLE b WHERE a.CIRCLE_ID = b.CIRCLE_ID AND b.AGENCY_ID = ? WITH UR");
			ps.setInt(1, agencyId);
			rs = ps.executeQuery();
			while(rs.next()) {
				dto = new AgencyDTO();
				dto.setMappedCircleId(rs.getInt("CIRCLE_ID"));
				dto.setMappedCircleName(rs.getString("CIRCLE_NAME"));
				agencyList.add(dto);
			}
		} catch (Exception e) {
			throw new DAOException("Exception occured while getCircleMappedList :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return agencyList;
	}

	public  ArrayList<CircleDTO> removeCircleAgencyMapping(int agencyId, String[] circleList) throws DAOException{

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CircleDTO> otherCircleList = null;

		try {
			con = DBConnection.getDBConnection();
			con.setAutoCommit(false);
			
			for(int i=0; i < circleList.length ; i++){
			ps = con.prepareStatement("DELETE FROM AGENCY_CIRCLE WHERE AGENCY_ID = ? AND CIRCLE_ID = ? WITH UR");	
			ps.setInt(1, agencyId);
			ps.setInt(2,Integer.parseInt(circleList[i]));
			ps.executeUpdate();
		}			
			con.commit();
			otherCircleList = getCircleList();
			
		} catch (Exception e) {
			try {
			con.rollback();
			}
			catch(Exception e1)
			{
				throw new DAOException("Exception occured while removeCircleAgencyMapping() :  "+ e.getMessage(),e);
			}
			
		} finally {
			try {
				con.setAutoCommit(true);
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
	
		return otherCircleList;
		
	}
	
	public  ArrayList<AgencyDTO> addCircleAgencyMapping(int agencyId, String[] circleList) throws DAOException{

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<AgencyDTO> mappedCircleList = null;

		try {
			con = DBConnection.getDBConnection();
			con.setAutoCommit(false);
			
			for(int i=0; i < circleList.length ; i++){
				
			ps = con.prepareStatement("INSERT INTO AGENCY_CIRCLE(AGENCY_ID,CIRCLE_ID) VALUES(?,?) WITH UR");	
			ps.setInt(1, agencyId);
			ps.setInt(2,Integer.parseInt(circleList[i]));
			ps.executeUpdate();
		}			
			con.commit();
			mappedCircleList = getCircleMappedList(agencyId);
			
		} catch (Exception e) {
			try {
			con.rollback();
			}
			catch(Exception e1)
			{
				throw new DAOException("Exception occured while addCircleAgencyMapping() :  "+ e.getMessage(),e);
			}
			
		} finally {
			try {
				con.setAutoCommit(true);
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
	
		return mappedCircleList;
		
	}
	
	public  boolean updateAgency(AgencyDTO agencyDto) throws DAOException{

		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		boolean rows = false;
		int rowUpdated = 0;
		try {
			con = DBConnection.getDBConnection();
			con.setAutoCommit(false);
			
			ps = con.prepareStatement("UPDATE AGENCY_MSTR SET AGENCY_DESC = ?, AGENCY_PATH = ?, CLASS_NAME = ?, USERNAME = ?, PASSWORD = ? WHERE AGENCY_ID = ? WITH UR");			
			ps.setString(1, agencyDto.getAgencyDescription());
			ps.setString(2, agencyDto.getAgencyPath());
			ps.setString(3, agencyDto.getAgencyClass());
			ps.setString(4, agencyDto.getUsername());
			ps.setString(5, agencyDto.getPassword());
			ps.setInt(6, agencyDto.getAgencyId());
			rowUpdated = ps.executeUpdate();
			
			// To make the agency default
			
			if((agencyDto.getDefaultCheck()).equalsIgnoreCase("Yes")){
				ps1 = con.prepareStatement("UPDATE AGENCY_MSTR SET IS_DEFAULT = 'N' WITH UR");
				ps1.executeUpdate();
				ps2 = con.prepareStatement("UPDATE AGENCY_MSTR SET IS_DEFAULT = 'Y' WHERE AGENCY_ID = ? WITH UR");
				ps2.setInt(1, agencyDto.getAgencyId());
				ps2.executeUpdate();
			}
		
			con.commit();
			
			if(rowUpdated == 1){
				rows = true;
			}
			else{
				rows = false;
			}
			
		} catch (Exception e) {
			try {
			con.rollback();
			}
			catch(Exception e1)
			{
				throw new DAOException("Exception occured while updateAgency() :  "+ e.getMessage(),e);
			}
			
		} finally {
			try {
				con.setAutoCommit(true);
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
	
		return rows;
		
	}
	
}
