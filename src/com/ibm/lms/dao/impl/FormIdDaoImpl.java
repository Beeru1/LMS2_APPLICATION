/**
 * 
 */
package com.ibm.lms.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.lms.common.DBConnection;
import com.ibm.lms.dao.FormIdDao;
import com.ibm.lms.dto.FormIdDto;
import com.ibm.lms.exception.DAOException;

/**
 * @author Nehil Parashar
 *
 */
public class FormIdDaoImpl implements FormIdDao 
{
	private static Logger logger =  Logger.getLogger(FormIdDaoImpl.class);
	
	private static final String CREATE_FID = "INSERT INTO FORM_DETAILS(PAGE_URL, STATUS, UPDATED_BY)" 
											+ " VALUES(?, ?, ?)";
	
	private static final String GET_AUTOGENERATED_FID = "SELECT Max(Form_Id) AS FORM_ID from FORM_DETAILS with ur";
	
	private static final String DELETE_FID = "UPDATE FORM_DETAILS SET STATUS='D', UPDATED_BY=? WHERE FORM_ID=?";
	
	private static final String UPDATE_FID = "UPDATE FORM_DETAILS SET PAGE_URL=?, STATUS=?, UPDATED_BY=? WHERE FORM_ID=?"; 
	
	private static final String GET_FID_DATA = "SELECT PAGE_URL, STATUS FROM FORM_DETAILS WHERE FORM_ID=? and Status != 'D' with ur";

	private static final String GET_ALL_FID_DATA = "SELECT FORM_ID, PAGE_URL, STATUS FROM FORM_DETAILS WHERE STATUS != 'D'";
	
	/**
	 * 
	 */
	
	//Added by srikant 
private static FormIdDaoImpl formIdDaoImpl=null;
	
	private FormIdDaoImpl(){
		
	}
	
	public static FormIdDaoImpl formIdDaoInstance()
	{
		if(formIdDaoImpl==null)
		{
			formIdDaoImpl=new FormIdDaoImpl();
		}
		return formIdDaoImpl;
		
	}
	public long createFid(FormIdDto fiddto) throws DAOException 
	{	
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		long autoGeneratedFid = -1;
		try
		{
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(CREATE_FID);
			
			logger.info(CREATE_FID);
			
			ps.setString(1, fiddto.getPageUrl());
			ps.setString(2, fiddto.getStatus());
			ps.setString(3, fiddto.getUpdatedBy());
			
			int rowsUpdated = ps.executeUpdate();
			
			logger.info("query executed successfully and rows updated = " + rowsUpdated);
			
			// ######## Now get FID #############
			if(rowsUpdated == 1)
			{
				ps2 = con.prepareStatement(GET_AUTOGENERATED_FID);
			
				logger.info(GET_AUTOGENERATED_FID);

				rs = ps2.executeQuery();
				while(rs.next())
				{
					autoGeneratedFid = rs.getInt("FORM_ID");
				}
			}
			fiddto.setFid(autoGeneratedFid);
			return autoGeneratedFid;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException("Exception occured in insertFidData method :  "+ e.getMessage(),e);
		} 
		finally 
		{
			try 
			{
				if(ps2!=null)
					ps2.close();
				DBConnection.releaseResources(con, ps, rs);
			} 
			catch (Exception e) 
			{				
				throw new DAOException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 
	 * @param fiddto
	 */
	public long deleteFidData(FormIdDto fiddto) throws DAOException
	{	
		logger.info("inside deleteFidData of FormIdDaoImpl");
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long fidToDelete = -1;
		try
		{
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(DELETE_FID);
			
			logger.info(DELETE_FID);
			
			fidToDelete = fiddto.getFid();
			ps.setString(1, fiddto.getUpdatedBy());
			ps.setLong(2, fidToDelete);
			
			logger.info(fidToDelete);
			
			int rowsDeleted = ps.executeUpdate();
			
			logger.info("query executed successfully and rows updated = " + rowsDeleted);
			// ######## Now get FID Generated #############
			
			return fidToDelete;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException("Exception occured in insertFidData method :  "+ e.getMessage(),e);
		} 
		finally 
		{
			try 
			{
				DBConnection.releaseResources(con, ps, rs);
			} 
			catch (Exception e) 
			{				
				throw new DAOException(e.getMessage(), e);
			}
		}
	}


	/**
	 * 
	 * @param fid
	 * @return
	 */
	public FormIdDto getFidData(long fid) throws DAOException
	{	
		logger.info("Inside getFidData of FormIdDaoImpl");
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(GET_FID_DATA);
			
			logger.info(GET_FID_DATA);
			logger.info("input fid = " + fid);
			ps.setLong(1, fid);
			rs = ps.executeQuery();
			
			FormIdDto aDto = new FormIdDto();
			
			while(rs.next())
			{
				aDto.setFid(fid);
				aDto.setStatus(rs.getString("STATUS").equals("A")?"Active":"Inactive");
				aDto.setPageUrl(rs.getString("PAGE_URL"));
			}
			
			logger.info("returning with values PageURL = " + aDto.getPageUrl() + " and Status = " + aDto.getStatus());
			return aDto;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException("Exception occured in insertFidData method :  "+ e.getMessage(),e);
		} 
		finally 
		{
			try 
			{
				DBConnection.releaseResources(con, ps, rs);
			} 
			catch (Exception e) 
			{				
				throw new DAOException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 
	 */
	public long updateFidData(FormIdDto fiddto) throws DAOException 
	{	
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(UPDATE_FID);
			
			logger.info(UPDATE_FID);
				
			ps.setString(1, fiddto.getPageUrl().trim());
			ps.setString(2, fiddto.getStatus());
			ps.setString(3, fiddto.getUpdatedBy());
			ps.setLong(4, fiddto.getFid());
			
			long rowsUpdated = ps.executeUpdate();
					
			logger.info("Page URL = " + fiddto.getPageUrl() + " Status = " +fiddto.getStatus());
			logger.info("Updated by = " + fiddto.getUpdatedBy() + " Fid = " +fiddto.getFid());
			logger.info("query executed successfully and rows inserted = " + rowsUpdated);
			
			return rowsUpdated;
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException("Exception occured in updateFidData method :  "+ e.getMessage(),e);
		} 
		finally 
		{
			try 
			{
				DBConnection.releaseResources(con, ps, rs);
			} 
			catch (Exception e) 
			{				
				throw new DAOException(e.getMessage(), e);
			}
		}
	}


	/**
	 * 
	 */
	public List<FormIdDto> downloadExcel() throws DAOException
	{	
		logger.info("Inside getFidData of FormIdDaoImpl");
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(GET_ALL_FID_DATA);
			
			logger.info(GET_ALL_FID_DATA);

			rs = ps.executeQuery();
			
			List<FormIdDto> aDtoList = new ArrayList<FormIdDto>();
			
			while(rs.next())
			{
				FormIdDto aDto = new FormIdDto();
				aDto.setFid(rs.getInt("Form_Id"));
				aDto.setStatus(rs.getString("STATUS").equals("A")?"Active":"Inactive");
				aDto.setPageUrl(rs.getString("PAGE_URL"));
				
				aDtoList.add(aDto);
			}
			
			return aDtoList;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException("Exception occured in insertFidData method :  "+ e.getMessage(),e);
		} 
		finally 
		{
			try 
			{
				DBConnection.releaseResources(con, ps, rs);
			} 
			catch (Exception e) 
			{				
				throw new DAOException(e.getMessage(), e);
			}
		}
	}
}