package com.ibm.lms.common;
//author: Nancy Agrawal
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.ibm.lms.engine.util.Constants;
import com.ibm.lms.dao.BulkMstrDao;
import com.ibm.lms.dto.AssignmentReportDTO;
import com.ibm.lms.dto.BulkAssignmentDto;
import com.ibm.lms.dto.BulkMstrDTO;
import com.ibm.lms.dto.Login;
import com.ibm.lms.dto.ProductDTO;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;

public class CommonMstrUtil 
{
	private static final Logger logger;
	static {
		logger = Logger.getLogger(BulkMstrDao.class);
	}
    
	public static boolean isValidCircle(BulkMstrDTO bulkDto) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		int circleId;
		int lobId;
		String validCircleLob="SELECT * FROM CIRCLE_MSTR where CIRCLE_ID=? and LOB_ID=? and STATUS='A'";

		try {
			con = DBConnection.getDBConnection();
			
			circleId=Integer.parseInt(bulkDto.getCircle());
			lobId=Integer.parseInt(bulkDto.getProductLobId());
			
			ps = con.prepareStatement(validCircleLob);
			
			ps.setInt(1, circleId);
			ps.setInt(2, lobId);
			
			rs=ps.executeQuery();
			
			if(rs==null)
			{
				logger.info("circle lob mapping doesnt exist");
				return isExist;
			}
			
			else if(rs.next())
			{	
				isExist=true;
				return isExist;
			}
			} catch (Exception e) {
			e.printStackTrace();} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return isExist;
	}
	


public static boolean isValidProdLobCircleComb(BulkMstrDTO bulkDto) throws DAOException {
	
	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String checkProdLobId="SELECT * FROM CIRCLE_MSTR cm,PRODUCT_MSTR pm,PRODUCT_LOB pl  WHERE cm.LOB_ID=pl.PRODUCT_LOB_ID and pl.PRODUCT_LOB_ID=pm.PRODUCT_LOB_ID and cm.STATUS='A' and pl.STATUS='A' and pm.STATUS='A' and cm.CIRCLE_ID=? and pm.PRODUCT_ID=? and cm.LOB_ID=? with ur ";

	try {
		con = DBConnection.getDBConnection();
		ps = con.prepareStatement(checkProdLobId);
		ps.setInt(1, Integer.parseInt(bulkDto.getCircle()));
		ps.setInt(2, Integer.parseInt(bulkDto.getProductId()));
		ps.setInt(3, Integer.parseInt(bulkDto.getProductLobId()));
		
		
		rs=ps.executeQuery();
		

		
		if(rs==null)
		{
			logger.info("Combination doesnt exist");
			return isExist;
		}
		
		else if(rs.next())
		{	
			logger.info("Combination exists");
			isExist=true;
			return isExist;
		}
		
		

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return isExist;
}

public static boolean chkPinRsu(BulkMstrDTO bulkDto) throws DAOException
{
	logger.info("asa::::::::::chkPin or Rsu:::");
		
		//checking if cityCode entered exists or not for PinCode and RSUCode upload.
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		
		int validProdLobId=-1;
		int validProdId=-1;
		int validCircle=-1;
		String check="";
		String validPinCode="";
		String validRsuCode="";
		
		try {
			con = DBConnection.getDBConnection();
			
			validProdLobId=Integer.parseInt(bulkDto.getProductLobId());
			validPinCode=bulkDto.getPinCode();
			validRsuCode=bulkDto.getRsuCode();
			validProdId=Integer.parseInt(bulkDto.getProductId());
			validCircle=Integer.parseInt(bulkDto.getCircle());
			
			if(validProdLobId==2)
			{
				//rsu is not null:
				if(!bulkDto.getPincodersu().equals(""))
				{
					if(bulkDto.getCitycode().equals("") && bulkDto.getCityZoneCode().equals(""))
					{
						 check="SELECT RSU_CODE FROM RSU_MSTR pm,CIRCLE_MSTR clm,ZONE_MSTR zm,CITY_MSTR cm,CITY_ZONE_MSTR czm WHERE RSU_CODE=? and pm.CITY_ZONE_CODE in (SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE CITY_CODE in  (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in  (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?))))FETCH FIRST 1 ROW ONLY with ur";
						 ps = con.prepareStatement(check); 
						ps.setString(1, bulkDto.getPincodersu());
						ps.setInt(2, Integer.parseInt(bulkDto.getCircle()));
						ps.setInt(3, Integer.parseInt(bulkDto.getProductLobId()));
					}
					
					 else if(!bulkDto.getCitycode().equals("") && bulkDto.getCityZoneCode().equals(""))
					{
						
						 check="SELECT RSU_CODE FROM RSU_MSTR pm,CIRCLE_MSTR clm,ZONE_MSTR zm,CITY_MSTR cm,CITY_ZONE_MSTR czm WHERE RSU_CODE=? and pm.CITY_ZONE_CODE in (SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE CITY_CODE =? and  CITY_CODE in  (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?	))))FETCH FIRST 1 ROW ONLY with ur";
						
						 ps = con.prepareStatement(check); 
						 
						ps.setString(1, bulkDto.getPincodersu());
						ps.setString(2, bulkDto.getCitycode());
						ps.setInt(3, Integer.parseInt(bulkDto.getCircle()));
						ps.setInt(4, Integer.parseInt(bulkDto.getProductLobId()));
					}
	 				else if (bulkDto.getCitycode().equals("") && (!bulkDto.getCityZoneCode().equals("")))
					{
						
						 check="SELECT RSU_CODE FROM RSU_MSTR pm,CIRCLE_MSTR clm,ZONE_MSTR zm,CITY_MSTR cm,CITY_ZONE_MSTR czm WHERE RSU_CODE=? and  pm.CITY_ZONE_CODE=? and pm.CITY_ZONE_CODE in (SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE  CITY_CODE in  (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?)))) FETCH FIRST 1 ROW ONLY with ur";
						

						 ps = con.prepareStatement(check); 
						 
						ps.setString(1, bulkDto.getPincodersu());
						ps.setString(2, bulkDto.getCityZoneCode());
						ps.setInt(3, validCircle);
						ps.setInt(4, validProdLobId);
					}
					
					else if((!bulkDto.getCitycode().equals("")) && (!bulkDto.getCityZoneCode().equals("")))
					{
						
						 check="SELECT RSU_CODE FROM RSU_MSTR pm,CIRCLE_MSTR clm,ZONE_MSTR zm,CITY_MSTR cm,CITY_ZONE_MSTR czm WHERE RSU_CODE=? and  pm.CITY_ZONE_CODE=? and pm.CITY_ZONE_CODE in (SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE CITY_CODE=? and CITY_CODE in (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?)))) FETCH FIRST 1 ROW ONLY with ur";
						
						 ps = con.prepareStatement(check); 
						 
						ps.setString(1, bulkDto.getPincodersu());
						ps.setString(2, bulkDto.getCityZoneCode());
						ps.setString(3, bulkDto.getCitycode());
						ps.setInt(4, validCircle);
						ps.setInt(5, validProdLobId);
						
					}
				}// end of cndtn 1
					else if(bulkDto.getPincodersu().equals(""))
						
					{
						
						if(bulkDto.getCitycode().equals("") && bulkDto.getCityZoneCode().equals(""))
						{
							
							 check="select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?  with ur";
							
							 ps = con.prepareStatement(check); 
							 
							ps.setInt(1, validCircle);
							ps.setInt(2, validProdLobId);
						}
						
						else if(!bulkDto.getCitycode().equals("") && bulkDto.getCityZoneCode().equals(""))
						{
							
							 check="SELECT CIRCLE_ID FROM CIRCLE_MSTR clm,ZONE_MSTR zm, CITY_MSTR cm, CITY_ZONE_MSTR czm WHERE cm.CITY_CODE=? and cm.ZONE_CODE in (SELECT ZONE_CODE FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (SELECT CIRCLE_MSTR_ID FROM CIRCLE_MSTR WHERE CIRCLE_ID=? and LOB_ID=?)) with ur";
							
							 ps = con.prepareStatement(check); 
							 
							ps.setString(1, bulkDto.getCitycode());
							ps.setInt(2, validCircle);
							ps.setInt(3, validProdLobId);
						}
						
						else if (bulkDto.getCitycode().equals("") && (!bulkDto.getCityZoneCode().equals("")))
						{
							
							 check="SELECT CIRCLE_ID FROM  CIRCLE_MSTR clm,ZONE_MSTR zm, CITY_MSTR cm, CITY_ZONE_MSTR czm WHERE czm.CITY_ZONE_CODE=? and czm.CITY_CODE in (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?))) with ur";
							
							 ps = con.prepareStatement(check); 
							 
							ps.setString(1, bulkDto.getCityZoneCode());
							ps.setInt(2, validCircle);
							ps.setInt(3, validProdLobId);
						}
						
						else if((!bulkDto.getCitycode().equals("")) && (!bulkDto.getCityZoneCode().equals("")))
						{
							
							 check="SELECT CIRCLE_ID FROM  CIRCLE_MSTR clm,ZONE_MSTR zm, CITY_MSTR cm, CITY_ZONE_MSTR czm WHERE czm.CITY_ZONE_CODE=? and czm.CITY_CODE=cm.CITY_CODE	and cm.ZONE_CODE in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=? )) with ur	";
							
							 ps = con.prepareStatement(check); 
							 
							ps.setString(1, bulkDto.getCityZoneCode());
							ps.setInt(2, validCircle);
							ps.setInt(3, validProdLobId);
						}
							
				}	
				}
			else if(validProdLobId!=2)
			{
			if(!bulkDto.getPincodersu().equals(""))
			{
				if(bulkDto.getCitycode().equals("") && bulkDto.getCityZoneCode().equals(""))
				{
					
					 check="SELECT PINCODE FROM PINCODE_MSTR pm,CIRCLE_MSTR clm,ZONE_MSTR zm,CITY_MSTR cm,CITY_ZONE_MSTR czm WHERE PINCODE=? and pm.CITY_ZONE_CODE in (SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE CITY_CODE in  (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in  (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?)))) with ur";
					
					 ps = con.prepareStatement(check); 
					 
					ps.setString(1, bulkDto.getPincodersu());
					ps.setInt(2, Integer.parseInt(bulkDto.getCircle()));
					ps.setInt(3, Integer.parseInt(bulkDto.getProductLobId()));
				}
				
				else if(!bulkDto.getCitycode().equals("") && bulkDto.getCityZoneCode().equals(""))
				{
					
					 check="SELECT PINCODE FROM PINCODE_MSTR pm,CIRCLE_MSTR clm,ZONE_MSTR zm,CITY_MSTR cm,CITY_ZONE_MSTR czm WHERE PINCODE=? and pm.CITY_ZONE_CODE in (SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE CITY_CODE =? and  CITY_CODE in  (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?	)))) with ur";
					
					 ps = con.prepareStatement(check); 
					 
					ps.setString(1, bulkDto.getPincodersu());
					ps.setString(2, bulkDto.getCitycode());
					ps.setInt(3, Integer.parseInt(bulkDto.getCircle()));
					ps.setInt(4, Integer.parseInt(bulkDto.getProductLobId()));
				}
				else if (bulkDto.getCitycode().equals("") && (!bulkDto.getCityZoneCode().equals("")))
				{
					
					 check="SELECT PINCODE FROM PINCODE_MSTR pm,CIRCLE_MSTR clm,ZONE_MSTR zm,CITY_MSTR cm,CITY_ZONE_MSTR czm WHERE PINCODE=? and  pm.CITY_ZONE_CODE=? and pm.CITY_ZONE_CODE in (SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE  CITY_CODE in  (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?)))) with ur";
					

					 ps = con.prepareStatement(check); 
					 
					ps.setString(1, bulkDto.getPincodersu());
					ps.setString(2, bulkDto.getCityZoneCode());
					ps.setInt(3, validCircle);
					ps.setInt(4, validProdLobId);
				}
				
				else if((!bulkDto.getCitycode().equals("")) && (!bulkDto.getCityZoneCode().equals("")))
				{
					
					 check="SELECT PINCODE FROM PINCODE_MSTR pm,CIRCLE_MSTR clm,ZONE_MSTR zm,CITY_MSTR cm,CITY_ZONE_MSTR czm WHERE PINCODE=? and  pm.CITY_ZONE_CODE=? and pm.CITY_ZONE_CODE in (SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE CITY_CODE=? and CITY_CODE in (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?)))) with ur";
					
					 ps = con.prepareStatement(check); 
					 
					ps.setString(1, bulkDto.getPincodersu());
					ps.setString(2, bulkDto.getCityZoneCode());
					ps.setString(3, bulkDto.getCitycode());
					ps.setInt(4, validCircle);
					ps.setInt(5, validProdLobId);
					
					
				}
						
			}
			else if(bulkDto.getPincodersu().equals(""))
				
			{
				
				if(bulkDto.getCitycode().equals("") && bulkDto.getCityZoneCode().equals(""))
				{
					
					 check="select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?  with ur";
					
					 ps = con.prepareStatement(check); 
					 
					ps.setInt(1, validCircle);
					ps.setInt(2, validProdLobId);
				}
				
				else if(!bulkDto.getCitycode().equals("") && bulkDto.getCityZoneCode().equals(""))
				{
				
					 check="SELECT CIRCLE_ID FROM CIRCLE_MSTR clm,ZONE_MSTR zm, CITY_MSTR cm, CITY_ZONE_MSTR czm WHERE cm.CITY_CODE=? and cm.ZONE_CODE in (SELECT ZONE_CODE FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (SELECT CIRCLE_MSTR_ID FROM CIRCLE_MSTR WHERE CIRCLE_ID=? and LOB_ID=?)) with ur";
					
					 ps = con.prepareStatement(check); 
					 
					ps.setString(1, bulkDto.getCitycode());
					ps.setInt(2, validCircle);
					ps.setInt(3, validProdLobId);
				}
				
				else if (bulkDto.getCitycode().equals("") && (!bulkDto.getCityZoneCode().equals("")))
				{
					
					 check="SELECT CIRCLE_ID FROM  CIRCLE_MSTR clm,ZONE_MSTR zm, CITY_MSTR cm, CITY_ZONE_MSTR czm WHERE czm.CITY_ZONE_CODE=? and czm.CITY_CODE in (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?))) with ur";
					
					 ps = con.prepareStatement(check); 
					 
					ps.setString(1, bulkDto.getCityZoneCode());
					ps.setInt(2, validCircle);
					ps.setInt(3, validProdLobId);
				}
				
				else if((!bulkDto.getCitycode().equals("")) && (!bulkDto.getCityZoneCode().equals("")))
				{
					
					 check="SELECT CIRCLE_ID FROM  CIRCLE_MSTR clm,ZONE_MSTR zm, CITY_MSTR cm, CITY_ZONE_MSTR czm WHERE czm.CITY_ZONE_CODE=? and czm.CITY_CODE=cm.CITY_CODE	and cm.ZONE_CODE in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=? )) with ur	";
					
					 ps = con.prepareStatement(check); 
					 
					ps.setString(1, bulkDto.getCityZoneCode());
					ps.setInt(2, validCircle);
					ps.setInt(3, validProdLobId);
				}
				
			}
			}
			rs=ps.executeQuery();
			if(rs==null)
			{
				logger.info("Combination doesnt exist");
				return isExist;
			}
			
			else if(rs.next())
			{	
				logger.info("Combination exists");
				isExist=true;
				return isExist;
			}
			
			} 
			    catch (Exception e)
			    {
				e.printStackTrace();
			    }
				    finally
				    {
					try 
					{
						DBConnection.releaseResources(con, ps, rs);
					} catch (Exception e) 
					{				
						throw new DAOException(e.getMessage(), e);
					}
				    }
		return isExist;
	}
public static boolean isValidCityZoneCodeforPinCodeRsu(BulkMstrDTO bulkDto) throws DAOException {
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	String checkCityZoneCode="";
	String pinCodeRsu="";
	String cityZoneCode="";
	
	
	if(bulkDto.getProductLobId().equals("2"))
			{
				checkCityZoneCode="SELECT * FROM RSU_MSTR WHERE RSU_CODE=? and  CITY_ZONE_CODE=? and STATUS='A' WITH UR";
			}
	else
			{
				checkCityZoneCode="SELECT * FROM PINCODE_MSTR WHERE PINCODE=? and  CITY_ZONE_CODE=? and STATUS='A' WITH UR";
		
			}

	try {
		con = DBConnection.getDBConnection();
		pinCodeRsu=bulkDto.getPincodersu();
		cityZoneCode=bulkDto.getCityZoneCode().toUpperCase();
		
		ps = con.prepareStatement(checkCityZoneCode);
		
		ps.setString(1, pinCodeRsu);
		ps.setString(2, cityZoneCode);
		
		rs=ps.executeQuery();
		

		
		if(rs==null)
		{
			logger.info("City Zone Code and pincode/rsu entered doesnt exist");
			return isExist;
		}
		
		else if(rs.next())
		{	
			logger.info("City Zone  Code and pincode/rsu entered exists");
			isExist=true;
			return isExist;
		}
		
		

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return isExist;
}
public static boolean isValidAgencyAssignmentMtrx(BulkMstrDTO bulkDto) throws DAOException {
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	int lobId=-1;
	int prodId=-1;
	int circleId=-1;
	String zoneCode="";
	String cityCode="";
	String cityZoneCode="";
	int pinCodersu;
	int agencyId=-1;
	
	String checkCityZoneCode="SELECT * FROM AGENCY_ASSIGNMENT WHERE PRODUCT_LOB_ID=? and PRODUCT_ID=? and CIRCLE_ID=? and ZONE_ID=? and CITY_ID=? and CITY_ZONE_CODE=? and "+ 
							"PINCODE_RSU_ID=? and CHANNEL_PARTNER_ID=? and  STATUS='A' WITH UR";

	String checkCityZoneCode1="SELECT * FROM AGENCY_ASSIGNMENT WHERE PRODUCT_LOB_ID=?  and CIRCLE_ID=? and ZONE_ID=? and CITY_ID=? and CITY_ZONE_CODE=? and "+
	 "PINCODE_RSU_ID=? and  CHANNEL_PARTNER_ID=? and STATUS='A' WITH UR";

	
	try {
		con = DBConnection.getDBConnection();
		
		if(!bulkDto.getProductId().equals(""))
		{
					ps = con.prepareStatement(checkCityZoneCode);
					
					ps.setInt(1, Integer.parseInt(bulkDto.getProductLobId()));
					ps.setInt(2, Integer.parseInt(bulkDto.getProductId()));
					ps.setInt(3, Integer.parseInt(bulkDto.getCircle()));
					ps.setString(4, bulkDto.getZoneCode().toUpperCase());
					if(bulkDto.getCitycode()!="")
					{	
					ps.setString(5, bulkDto.getCitycode().toUpperCase());
					}
					else
					{
						ps.setString(5,"");
					}
					if(bulkDto.getCityZoneCode()!="")
					{
						
						ps.setString(6, bulkDto.getCityZoneCode().toUpperCase());
					}
					else
					{
						ps.setString(6,"");
					}
					
					if(bulkDto.getPincodersu()!="")
					{	
						ps.setString(7, bulkDto.getPincodersu());
					}
					else
					{
						ps.setString(7,"");
					}
					
					if(bulkDto.getChannelPartnerId()!=null && bulkDto.getChannelPartnerId().length() >0)
					{	
						ps.setString(8, bulkDto.getChannelPartnerId());
					}
					else
					{
						ps.setString(8,"");
					}
					
		}
		
		else
			
		{
			ps = con.prepareStatement(checkCityZoneCode1);
			
			ps.setInt(1, Integer.parseInt(bulkDto.getProductLobId()));
			ps.setInt(2, Integer.parseInt(bulkDto.getCircle()));
			ps.setString(3, bulkDto.getZoneCode().toUpperCase());
			if(bulkDto.getCitycode()!="")
			{	
			ps.setString(4, bulkDto.getCitycode().toUpperCase());
			}
			else
			{
				ps.setString(4,"");
			}
			if(bulkDto.getCityZoneCode()!="")
			{
				ps.setString(5, bulkDto.getCityZoneCode().toUpperCase());
			}
			else
			{
				ps.setString(5,"");
			}
			
			if(bulkDto.getPincodersu()!="")
			{	
				ps.setString(6, bulkDto.getPincodersu());
			}
			else
			{
				ps.setString(6,"");
			}
			if(bulkDto.getChannelPartnerId()!=null && bulkDto.getChannelPartnerId().length() >0)
			{	
				ps.setString(7, bulkDto.getChannelPartnerId());
			}
			else
			{
				ps.setString(7,"");
			}
		}
		rs=ps.executeQuery();
		

		if(rs==null)
		{
			logger.info("Agency Assignment Matrix  doesnt exist");
			return isExist;
		}
		
		else if(rs.next())
		{	
			logger.info("Agency Assignment Matrix exists");
			isExist=true;
			return isExist;
		}
		
		

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return isExist;
}

public static boolean isValidProdLobId(BulkMstrDTO bulkDto) throws DAOException {
	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	int validProdLobId;
	
	String checkProdLobId="SELECT PRODUCT_LOB_ID FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID=? and STATUS='A' ";

	try {
		con = DBConnection.getDBConnection();
		validProdLobId=Integer.parseInt(bulkDto.getProductLobId());
		ps = con.prepareStatement(checkProdLobId);
		ps.setInt(1, validProdLobId);
		rs=ps.executeQuery();
		if(rs==null)
		{
			logger.info("Product Lob ID doesnt exist");
			return false;
		}
		else if(rs.next())
		{	
			logger.info("Product Lob ID exists");
			return true;
		}
		} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return isExist;
}
public static boolean isValidCityCode(BulkMstrDTO bulkDto) throws DAOException
{
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String validCityCode="";
	String cityCode="";
	
	int circleId=-1;
	int lobId=-1;
	
	try {
		
		con = DBConnection.getDBConnection();
		
		cityCode=bulkDto.getCitycode().toUpperCase();
		circleId=Integer.parseInt(bulkDto.getCircle());
		lobId=Integer.parseInt(bulkDto.getProductLobId());
		validCityCode="select CITY_CODE from CITY_MSTR where CITY_CODE=? AND ZONE_CODE=(select zone_code from  ZONE_MSTR where circle_mstr_id=(select circle_mstr_id from circle_mstr where circle_id=? and lob_id=?))";
		
		ps = con.prepareStatement(validCityCode);
		ps.setString(1, cityCode);
		ps.setInt(2, circleId);
		ps.setInt(3, lobId);
		
		rs=ps.executeQuery();
		if(rs==null)
		{
			logger.info("Invalid city Code");
			return isExist;
		}
		
		else if(rs.next())
		{	
			isExist=true;
			return isExist;
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return isExist;

}
public static boolean isValidZoneCode(BulkMstrDTO bulkDto) throws DAOException {
	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String validZoneCode="";
	int circleId=-1;
	int lobId=-1;
	
	String checkZoneCode="SELECT * FROM ZONE_MSTR WHERE ZONE_CODE=? and CIRCLE_MSTR_ID= "+
						  "(SELECT CIRCLE_MSTR_ID FROM CIRCLE_MSTR WHERE CIRCLE_ID=? and LOB_ID=? and STATUS='A' ) and STATUS='A' WITH UR";

	try {
		con = DBConnection.getDBConnection();
		
		
		
		validZoneCode=bulkDto.getZoneCode().toUpperCase();
		circleId=Integer.parseInt(bulkDto.getCircle());
		lobId=Integer.parseInt(bulkDto.getProductLobId());
		ps = con.prepareStatement(checkZoneCode);
		ps.setString(1, validZoneCode);
		ps.setInt(2, circleId);
		ps.setInt(3, lobId);
		rs=ps.executeQuery();
		if(rs==null)
		{
			logger.info("Zone Code doesnt exist");
			return isExist;
		}
		
		else if(rs.next())
		{	
			logger.info("Zone Code exists");
			isExist=true;
			return isExist;
		}
		
		

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return isExist;
}
public static boolean isValidZoneCodeAndCityCode(BulkMstrDTO bulkDto) throws DAOException {
	
	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String validCityCode="";
	String validZoneCode="";
	int circleId=-1;
	int lobId=-1;
	
	String checkCityCode="select CITY_CODE from CITY_MSTR where city_code =? and zone_code="+
						"(select ZONE_CODE from ZONE_MSTR where ZONE_CODE=? and CIRCLE_MSTR_ID= "+
						"(select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID=?  and LOB_ID=? ) )WITH UR";

	try {
		con = DBConnection.getDBConnection();
		
		
		
		validCityCode=bulkDto.getCitycode().toUpperCase();
		validZoneCode=bulkDto.getZoneCode().toUpperCase();
		circleId=Integer.parseInt(bulkDto.getCircle());
		lobId=Integer.parseInt(bulkDto.getProductLobId());
		
		ps = con.prepareStatement(checkCityCode);
		
		ps.setString(1, validCityCode);
		ps.setString(2,validZoneCode);
		ps.setInt(3, circleId);
		ps.setInt(4, lobId);
		
		
		rs=ps.executeQuery();
		

		
		if(rs==null)
		{
			logger.info("valid city code and Zone Code doesnt exist");
			return isExist;
		}
		
		else if(rs.next())
		{	
			logger.info("valid city code and Zone Code exists");
			isExist=true;
			return isExist;
		}
		
		

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return isExist;
}


public static boolean isValidLeadSubStatus(BulkMstrDTO bulkDto) throws DAOException 
{
Connection con = null;
PreparedStatement ps = null;
ResultSet rs = null;
boolean isExist=false;
String validLeadSubStatus="";


String checkLeadSubStatusId="select LSS.SUB_STATUS_ID from LEAD_SUB_STATUS LSS,LEAD_STATUS LS where LS.LEAD_STATUS_ID =LSS.LEAD_STATUS_ID  AND LSS.LEAD_STATUS_ID=? AND LSS.SUB_STATUS_ID=? and LSS.PRODUT_LOB_ID=? WITH UR";

try {
	con = DBConnection.getDBConnection();
	
	validLeadSubStatus=bulkDto.getLeadStatusId();
	ps = con.prepareStatement(checkLeadSubStatusId);
	ps.setInt(1, Integer.parseInt(bulkDto.getLeadStatusId()));
	ps.setInt(2, Integer.parseInt(bulkDto.getSubStatusId()));
	ps.setInt(3, Integer.parseInt(bulkDto.getProductLobId()));
	rs=ps.executeQuery();
	
	if(rs==null)
	{
		logger.info("valid lead SUB_STATUS_ID doesnt exist");
		return isExist;
	}
	
	else if(rs.next())
	{	
		logger.info("valid SUB_STATUS_ID id exists");
		isExist=true;
		return isExist;
	}
	
	

} catch (Exception e) {
	e.printStackTrace();
} finally {
	try {
		DBConnection.releaseResources(con, ps, rs);
	} catch (Exception e) {				
		throw new DAOException(e.getMessage(), e);
	}
}
return isExist;
}

public static boolean isValidLeadStatus(BulkMstrDTO bulkDto) throws DAOException 
{
Connection con = null;
PreparedStatement ps = null;
ResultSet rs = null;
boolean isExist=false;
String validLeadStatus="";


String checkLeadStatusId="select LEAD_STATUS_ID from LEAD_STATUS where LEAD_STATUS_ID =? WITH UR";

try {
	con = DBConnection.getDBConnection();
	
	validLeadStatus=bulkDto.getLeadStatusId();
	ps = con.prepareStatement(checkLeadStatusId);
	ps.setInt(1,Integer.parseInt(bulkDto.getLeadStatusId()));
	rs=ps.executeQuery();
	
	if(rs==null)
	{
		logger.info("valid lead status id doesnt exist");
		return isExist;
	}
	
	else if(rs.next())
	{	
		logger.info("valid lead status id exists");
		isExist=true;
		return isExist;
	}
	
	

} catch (Exception e) {
	e.printStackTrace();
} finally {
	try {
		DBConnection.releaseResources(con, ps, rs);
	} catch (Exception e) {				
		throw new DAOException(e.getMessage(), e);
	}
}
return isExist;
}

public static boolean isValidChannelPartnerId(BulkMstrDTO bulkDto,int flag) throws DAOException {

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String olmId;
	
	String validChannelPartner="SELECT * FROM USER_MSTR WHERE KM_ACTOR_ID=3 and USER_LOGIN_ID=? and STATUS='A' with ur";
	String validChannelPartner1="SELECT * FROM USER_MSTR WHERE USER_LOGIN_ID=? and STATUS='A' with ur";

	try {
		con = DBConnection.getDBConnection();
		
		olmId=bulkDto.getChannelPartnerId();
		if(flag==1)
		{
		ps = con.prepareStatement(validChannelPartner);
		}
		else
		{
		ps = con.prepareStatement(validChannelPartner1);	
		}
		
		ps.setString(1, olmId);
		
		
		rs=ps.executeQuery();
		
		if(rs==null)
		{
			logger.info("invalid Channel Partner Id exist");
			return isExist;
		}
		
		else if(rs.next())
		{	
			isExist=true;
			return isExist;
		}
		} catch (Exception e) {
		e.printStackTrace();} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return isExist;
}

public static boolean isValidProdLobCircle(BulkMstrDTO bulkDto) throws DAOException {
	
	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	int validProdLobId=-1;
	int validCircle=-1;
	
	String checkProdLobId="SELECT * FROM CIRCLE_MSTR cm,PRODUCT_LOB pl  WHERE cm.LOB_ID=pl.PRODUCT_LOB_ID  and cm.STATUS='A' and pl.STATUS='A' and cm.CIRCLE_ID=? and cm.LOB_ID=? with ur ";

	try {
		con = DBConnection.getDBConnection();
		
		
		
		validProdLobId=Integer.parseInt(bulkDto.getProductLobId());
		validCircle=Integer.parseInt(bulkDto.getCircle());
		ps = con.prepareStatement(checkProdLobId);
		
		ps.setInt(1, validCircle);
		ps.setInt(2, validProdLobId);
		
		
		rs=ps.executeQuery();
		

		
		if(rs==null)
		{
			logger.info("Combination doesnt exist");
			return isExist;
		}
		
		else if(rs.next())
		{	
			logger.info("Combination exists");
			isExist=true;
			return isExist;
		}
		
		

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return isExist;
}

public static boolean getProductListByLob(BulkMstrDTO bulkDto) throws DAOException 
{
	String SQL_SELECT_PRODUCTS = "SELECT PRODUCT_ID, PRODUCT_NAME  FROM PRODUCT_MSTR WHERE STATUS='A' and PRODUCT_LOB_ID=? WITH UR";
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String productId;
	boolean isExist=false;
	
	try 
	{
con = DBConnection.getDBConnection();
		
		productId=bulkDto.getProductId();
		ps = con.prepareStatement(SQL_SELECT_PRODUCTS);
		
		ps.setString(1, productId);
		
		
		rs=ps.executeQuery();
		
		if(rs==null)
		{
			logger.info("Product does not exist for this lob");
			return isExist;
		}
		
		else if(rs.next())
		{	
			isExist=true;
			return isExist;
		}
		} catch (Exception e) {
		e.printStackTrace();} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}

	return isExist;
}

public static boolean isValidReqCategory(BulkMstrDTO bulkDto) throws DAOException {
Connection con = null;
	
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String checkProdLobId1="SELECT REQUEST_CATEGORY FROM REQUEST_CATEGORY_MSTR WHERE REQUEST_CATEGORY=? and STATUS='A' with ur ";
	

	try {
		con = DBConnection.getDBConnection();
		
		
			ps = con.prepareStatement(checkProdLobId1);
			ps.setString(1,bulkDto.getReqCategory());
			
		rs=ps.executeQuery();
		

		
		if(rs==null)
		{
			logger.info("Request category  doesnt exist");
			return isExist;
		}
		
		else if(rs.next())
		{	
			logger.info("exists");
			isExist=true;
			return isExist;
		}
		
		

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return isExist;
}
public static boolean isValidReqCategoryCombination(BulkMstrDTO bulkDto) throws DAOException {
	
	
	Connection con = null;
	
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	
	String checkProdLobId="SELECT REQUEST_CATEGORY FROM REQUEST_CATEGORY_MSTR WHERE REQUEST_CATEGORY=? and PRODUCT_ID in(select product_id from product_mstr where product_lob_id=(select product_lob_id from CIRCLE_MSTR cm,PRODUCT_LOB pl  WHERE cm.LOB_ID=pl.PRODUCT_LOB_ID  and cm.STATUS='A' and pl.STATUS='A' and cm.CIRCLE_ID=? and cm.LOB_ID=?)) with ur";

	
	try {
		con = DBConnection.getDBConnection();
		
		
		ps = con.prepareStatement(checkProdLobId);
		
		ps.setString(1, bulkDto.getReqCategory());
		ps.setInt(2,Integer.parseInt(bulkDto.getCircle()));
		ps.setInt(3, Integer.parseInt(bulkDto.getProductLobId()));
		
		
		rs=ps.executeQuery();
		

		
		if(rs==null)
		{
			logger.info("Request category Combination doesnt exist");
			return isExist;
		}
		
		else if(rs.next())
		{	
			logger.info("Combination exists");
			isExist=true;
			return isExist;
		}
		
		

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return isExist;
}
public static boolean isValidCityLobCircleCPComb(BulkMstrDTO bulkDto) throws DAOException
{
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String validCityCode="";
	String cityCode="";
	int circleId=-1;
	int lobId=-1;
	
	try {
		
		con = DBConnection.getDBConnection();
		
		cityCode=bulkDto.getCitycode();
		
		validCityCode="SELECT CIM.city_code ,rm.REQUEST_CATEGORY FROM REQUEST_CATEGORY_MSTR rm,city_mstr CIM WHERE CIM.city_code=? and rm.REQUEST_CATEGORY=? and rm.PRODUCT_ID in(select product_id from product_mstr where product_lob_id=(select product_lob_id from CIRCLE_MSTR cm,PRODUCT_LOB pl  WHERE cm.LOB_ID=pl.PRODUCT_LOB_ID  and cm.STATUS='A' and pl.STATUS='A' and cm.CIRCLE_ID=? and cm.LOB_ID=?)) and CIM.ZONE_CODE in(select zone_code from  ZONE_MSTR where circle_mstr_id=(select circle_mstr_id from circle_mstr where circle_id=? and lob_id=?))";

		
		
		ps = con.prepareStatement(validCityCode);
		
		ps.setString(1, cityCode);
		validCityCode=bulkDto.getCitycode().toUpperCase();
		circleId=Integer.parseInt(bulkDto.getCircle());
		lobId=Integer.parseInt(bulkDto.getProductLobId());
		
		ps = con.prepareStatement(validCityCode);
		
		ps.setString(1, bulkDto.getCitycode());
		ps.setString(2,bulkDto.getReqCategory());
		ps.setInt(3,circleId);
		ps.setInt(4, lobId);
		ps.setInt(5,circleId);
		ps.setInt(6, lobId);
		
		rs=ps.executeQuery();
		

		
		if(rs==null)
		{
			logger.info("Invalid city Code");
			return isExist;
		}
		
		else if(rs.next())
		{	
			isExist=true;
			return isExist;
		}
		
		

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return isExist;

}
public static boolean isValidLobCircleCP(BulkMstrDTO bulkDto,int flag) throws DAOException {
	
	
	Connection con = null;
	
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	String checkProdLobId="SELECT * FROM user_mstr um,USER_MAPPING mp,CIRCLE_MSTR cm,PRODUCT_MSTR pm WHERE um.USER_LOGIN_ID=mp.USER_LOGIN_ID and um.KM_ACTOR_ID=3 and mp.CIRCLE_ID=cm.CIRCLE_ID and pm.PRODUCT_LOB_ID=mp.LOB_ID and pm.PRODUCT_LOB_ID=? and cm.CIRCLE_ID=? and um.USER_LOGIN_ID=? with ur";
     
	String checkProdLobIdAgency="SELECT * FROM user_mstr um,USER_MAPPING mp,CIRCLE_MSTR cm,PRODUCT_MSTR pm WHERE um.USER_LOGIN_ID=mp.USER_LOGIN_ID and mp.CIRCLE_ID=cm.CIRCLE_ID and pm.PRODUCT_LOB_ID=mp.LOB_ID and pm.PRODUCT_LOB_ID=? and cm.CIRCLE_ID=? and um.USER_LOGIN_ID=? with ur";
	
	String check3="select * from CITY_MSTR CIM,user_mstr um,USER_MAPPING mp,CIRCLE_MSTR cm,PRODUCT_MSTR pm where um.USER_LOGIN_ID=mp.USER_LOGIN_ID and mp.CIRCLE_ID=cm.CIRCLE_ID and pm.PRODUCT_LOB_ID=mp.LOB_ID and mp.LOB_ID=? and mp.CIRCLE_ID=? and mp.USER_LOGIN_ID=? and CIM.CITY_CODE in(select CITY_CODE from CITY_MSTR where ZONE_CODE in (select zone_code from  ZONE_MSTR where circle_mstr_id=(select circle_mstr_id from circle_mstr where circle_id=? and lob_id=?)))";
	try {
		con = DBConnection.getDBConnection();
		
		
		if(bulkDto.getCitycode()!=null && bulkDto.getCitycode().length() >0 )
		{
			ps = con.prepareStatement(check3);	
			ps.setInt(1, Integer.parseInt(bulkDto.getProductLobId()));
			ps.setInt(2,Integer.parseInt(bulkDto.getCircle()));
			ps.setString(3, bulkDto.getChannelPartnerId());
			ps.setInt(4,Integer.parseInt(bulkDto.getCircle()));
			ps.setInt(5, Integer.parseInt(bulkDto.getProductLobId()));
			
		
		}
		else
		{
		    
		    if(flag==1)
		    {
		    	ps = con.prepareStatement(checkProdLobId);
		    }
		    else
		    {
		    	ps = con.prepareStatement(checkProdLobIdAgency);
		    }
			ps.setInt(1, Integer.parseInt(bulkDto.getProductLobId()));
			ps.setInt(2,Integer.parseInt(bulkDto.getCircle()));
			ps.setString(3, bulkDto.getChannelPartnerId());
					
		}
		
		rs=ps.executeQuery();
		

		
		if(rs==null)
		{
			logger.info("Combination does not exist");
			return isExist;
		}
		
		else if(rs.next())
		{	
			logger.info("Combination exists");
			isExist=true;
			return isExist;
		}
		
		

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return isExist;
}
public static String isValidChannelCodeInsert(BulkMstrDTO bulkDto) throws DAOException {
	Connection con = null;
	PreparedStatement ps = null;
	String olmId=null;
	//String olmId1=null;
	String channelCode=null;
	ResultSet rs = null;
	PreparedStatement ps1 = null;
	//ResultSet rs2=null;
	//ResultSet rs1 = null;
	//boolean isExist=false;
	String flag=null;
	
	
	String checkChannelCode="SELECT OLM_ID FROM UPLOAD_CHANNEL_CODE WHERE OLM_ID=? and CHANNEL_CODE=? and STATUS='A' WITH UR";
	//String checkChannelCode1="SELECT OLM_ID FROM ASSIGNMENT_MATRIX WHERE OLM_ID=? and STATUS='A' WITH UR";
	//int id=getRequestCategoryId(bulkDto);
	try {
		con = DBConnection.getDBConnection();
		
				    ps = con.prepareStatement(checkChannelCode);
				    
				   // ps1=con.prepareStatement(checkChannelCode1);
				    
				   	ps.setString(1,bulkDto.getOlmId());
					ps.setString(2,bulkDto.getChannelCode());
					
					//ps1.setString(1,bulkDto.getOlmId());
					
		rs=ps.executeQuery();
		//rs2=ps1.executeQuery();
		
		//logger.info(rs1+"******************"+rs);
		if(rs.next()==false)
		{
			logger.info("Channel code and olm id doesnt exist");
			logger.info("rs null 7777777777");
			//isExist=false;
			flag="success";
			return flag;
			
		}
	
		
		if(rs.next()!=false)
		{	
			//rs.next();
			//rs1.next();
			olmId=rs.getString("OLM_ID");
			channelCode=rs.getString("CHANNEL_CODE");
			//olmId1=rs1.getString("OLM_ID");
			logger.info("rs not null 0000000000000000000");
			if(olmId.equalsIgnoreCase(bulkDto.getOlmId())&&channelCode.equalsIgnoreCase(bulkDto.getChannelCode())){
				//isExist=true;
				flag="notsuccess";
			return flag;
			
			}
			logger.info("olm id not exist in Assignment");
			
		}
		
		/*else{
			logger.info("problem occured in olm id");
			//isExist=true;
			//return isExist;
			
			flag="problem";
			return flag;
		}*/
		
		

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
			//DBConnection.releaseResources(con, ps1, rs1);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return flag="notSuccess";
}


//========================
public static boolean isValidWorkFlowAsgnmtMtrxforDelete(BulkMstrDTO bulkDto) throws DAOException {
	Connection con = null;
	PreparedStatement ps = null;
	
	ResultSet rs = null;
	
	boolean isExist=false;
	
	String checkMatrix="SELECT * FROM AUTO_ASSIGNMENT_MATRIX WHERE LOB_ID=?  and CIRCLE_ID=? and REQUEST_CATEGORY_ID=? and CITY_CODE=? and OLM_ID=?  and PINCODE=? and  RSU_CODE=? and STATUS='A' and AUTO_ASSIGNMENT_TYPE=? and PRODUCT_ID=? WITH UR";
	int id=getRequestCategoryId(bulkDto);
	try {
		con = DBConnection.getDBConnection();
				    ps = con.prepareStatement(checkMatrix);
				    
				    
				   	
					ps.setInt(1, Integer.parseInt(bulkDto.getProductLobId()));
					ps.setInt(2, Integer.parseInt(bulkDto.getCircle()));
					if(bulkDto.getReqCategory()!="" || bulkDto.getReqCategory()!=null)
					{
						ps.setInt(3, id);
					}
					else
					{
						ps.setInt(3,-1);
					}
					
					if(bulkDto.getCitycode()!="" || bulkDto.getCitycode()!=null)
					{	
					ps.setString(4, bulkDto.getCitycode().toUpperCase());
					}
					else
					{
						ps.setString(4,"");
					}
					
				    ps.setString(5, bulkDto.getChannelPartnerId());
					
				    if(bulkDto.getPinCode()!="" || bulkDto.getPinCode()!=null)
					{
						ps.setString(6, bulkDto.getPincodersu());
					}
					else
					{
						ps.setString(6,"");
					}
					if(bulkDto.getRsuCode()!="" || bulkDto.getRsuCode()!=null)
					{
						ps.setString(7, bulkDto.getRsuCode());
					}
					else
					{
						ps.setString(7,"");
					}
					ps.setString(8,Constants.AUTO_ASSIGNMENT_WORKFLOW);
					ps.setInt(9,Integer.parseInt(bulkDto.getProductId()));
		rs=ps.executeQuery();
		

		
		if(rs==null)
		{
			logger.info("Assignment Matrix  doesnt exist");
			return isExist;
		}
		
		else if(rs.next())
		{	
			logger.info("Assignment Matrix exists");
			isExist=true;
			return isExist;
		
		}
		
		

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return isExist;
}













 public static int getRequestCategoryId(BulkMstrDTO bulkDto) throws DAOException 
{
	String SQL_REQUEST_CATEGORY = "SELECT REQUEST_CATEGORY_ID FROM REQUEST_CATEGORY_MSTR WHERE REQUEST_CATEGORY=? WITH UR";
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String requestCategory="";
int category=-1;
	
	try 
	{
con = DBConnection.getDBConnection();
		
       requestCategory=bulkDto.getReqCategory();
		ps = con.prepareStatement(SQL_REQUEST_CATEGORY);
		
		ps.setString(1, requestCategory);
		
		
		rs=ps.executeQuery();
		while(rs.next())
		{
			category=rs.getInt("REQUEST_CATEGORY_ID");	
		}
		
		
		} catch (Exception e) {
		e.printStackTrace();} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}

	return category;
}
 
 //Pincode Validation 
 public static boolean chkPin(BulkMstrDTO dto1) throws DAOException  {
 	//checking if cityCode entered exists or not for PinCode and RSUCode upload.
 	Connection con = null;
 	PreparedStatement ps = null;
 	ResultSet rs = null;
 	boolean isExist=false;
 
 	String check="";
 		try {
 		con = DBConnection.getDBConnection();
 		
 			logger.info("pincode"+dto1.getPinCode());
 			if(dto1.getCitycode().equals(""))
 			{
 				
 				 check="SELECT PINCODE FROM PINCODE_MSTR WHERE PINCODE=? and CITY_ZONE_CODE in (SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE CITY_CODE in (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=(select product_lob_id from product_mstr where product_id=? ))))) with ur";				
 				
 				 ps = con.prepareStatement(check); 
 				 
 				ps.setString(1, dto1.getPinCode());
 				ps.setInt(2, Integer.parseInt(dto1.getCircle()));
 				ps.setInt(3,Integer.parseInt(dto1.getProductId()));
 			}
 			
 			
 			else if (!dto1.getCitycode().equals("") )
 			{
 				
 				 check="SELECT PINCODE FROM PINCODE_MSTR WHERE CITY_ZONE_CODE in(SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE CITY_CODE in (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in(SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=(select product_lob_id from product_mstr where product_id=? )))) and city_code=?)and PINCODE=? with ur";
 				 ps = con.prepareStatement(check); 
 				
 			
 				ps.setInt(1, Integer.parseInt(dto1.getCircle()));
				ps.setInt(2,Integer.parseInt(dto1.getProductId()));
 				ps.setString(3, dto1.getCitycode());
 				ps.setString(4, dto1.getPinCode());
 			}
 		
 		rs=ps.executeQuery();
 		if(rs==null)
 		{
 			logger.info("Combination doesnt exist");
 			return isExist;
 		}
 		
 		else if(rs.next())
 		{	
 			logger.info("Combination exists");
 			isExist=true;
 			return isExist;
 		}
 		
 		} 
 		    catch (Exception e)
 		    {
 			e.printStackTrace();
 		    }
 			    finally
 			    {
 				try 
 				{
 					DBConnection.releaseResources(con, ps, rs);
 				} catch (Exception e) 
 				{				
 					throw new DAOException(e.getMessage(), e);
 				}
 			    }
 	return isExist;
 }


 //Rsu Code VAlidation
 public static boolean chkRsu(BulkMstrDTO dto1)throws DAOException  {
 	//checking if cityCode entered exists or not for PinCode and RSUCode upload.
 	Connection con = null;
 	PreparedStatement ps = null;
 	ResultSet rs = null;
 	boolean isExist=false;

 	String check="";
 		try {
 		con = DBConnection.getDBConnection();
 		
 			logger.info("pincode"+dto1.getPinCode());
 			if(dto1.getCitycode().equals("") && dto1.getProductLobId().equals("2"))
 			{
 				
 				 check="SELECT RSU_CODE FROM RSU_MSTR WHERE RSU_CODE=? and CITY_ZONE_CODE in (SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE CITY_CODE in (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=(select product_lob_id from product_mstr where product_id=? ))))) with ur";				
 				
 				 ps = con.prepareStatement(check); 
 				 
 				ps.setString(1, dto1.getRsuCode());
 				ps.setInt(2, Integer.parseInt(dto1.getCircle()));
 				ps.setInt(3,Integer.parseInt(dto1.getProductId()));
 			}
 			
 			
 			else if (!dto1.getCitycode().equals("")  && dto1.getProductLobId().equals("2"))
 			{
 				
 				 check="SELECT RSU_CODE FROM RSU_MSTR WHERE CITY_ZONE_CODE in(SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE CITY_CODE in(SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in(SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=(select product_lob_id from product_mstr where product_id=? )))) and city_code=?)and rsu_code=? with ur";

             ps = con.prepareStatement(check); 

				ps.setInt(1, Integer.parseInt(dto1.getCircle()));
				ps.setInt(2,Integer.parseInt(dto1.getProductId()));
 				ps.setString(3, dto1.getCitycode());
 				ps.setString(4, dto1.getRsuCode());
 				
 			}
 		
 		rs=ps.executeQuery();
 		if(rs==null)
 		{
 			logger.info("Combination doesnt exist");
 			return isExist;
 		}
 		
 		else if(rs.next())
 		{	
 			logger.info("Combination exists");
 			isExist=true;
 			return isExist;
 		}
 		
 		} 
 		    catch (Exception e)
 		    {
 			e.printStackTrace();
 		    }
 			    finally
 			    {
 				try 
 				{
 					DBConnection.releaseResources(con, ps, rs);
 				} catch (Exception e) 
 				{				
 					throw new DAOException(e.getMessage(), e);
 				}
 			    }
 	return isExist;
 }

 
 public static String ifUserActive(String userLoginId) throws DAOException 
 {
	final String SELECT_SMS_USER_FLAG="SELECT km.SMS_FLAG,um.USER_MOBILE_NUMBER FROM KM_ACTORS km,USER_MSTR um WHERE km.KM_ACTOR_ID=um.KM_ACTOR_ID and um.STATUS='A' and um.USER_LOGIN_ID=?  WITH UR";
	Connection con = null;
 	PreparedStatement ps = null;
 	ResultSet rs = null;
 	String flagval="";
	String contact=null;
	boolean flag=false;
	
	
 	try 
 	{
       con = DBConnection.getDBConnection();
       ps = con.prepareStatement(SELECT_SMS_USER_FLAG);
	   ps.setString(1, userLoginId);
	   rs=ps.executeQuery();
	   while (rs.next()) {	
			flagval=rs.getString("SMS_FLAG");
			contact=rs.getString("USER_MOBILE_NUMBER");
			
			if(flagval!=null && !("").equalsIgnoreCase(rs.getString("SMS_FLAG")) && rs.getString("SMS_FLAG").equalsIgnoreCase("Y"))
			{
		    flag=true;
			}
		} 		
 		} catch (Exception e) {
 		e.printStackTrace();} finally {
 		try {
 			DBConnection.releaseResources(con, ps, rs);
 		} catch (Exception e) {				
 			throw new DAOException(e.getMessage(), e);
 		}
 	}
    if(flag==true)
    {
 	return contact;
    }
    else
    {
    	return "";
    }
 }
  
 
 	final static String SQL_SELECT_ASSIGNMENT_MATRIX="SELECT a.OLM_ID,a.ASSIGNMENT_KEY,a.STATUS,a.PINCODE,a.PRIMARY_AUTH,(SELECT PRODUCT_NAME FROM PRODUCT_MSTR WHERE PRODUCT_ID = a.PRODUCT_ID),"+
			"(SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = a.PRODUCT_LOB_ID) AS PRODUCT_LOB,(SELECT CIRCLE_NAME FROM CIRCLE_MSTR WHERE CIRCLE_ID = a.CIRCLE_ID and LOB_ID = a.PRODUCT_LOB_ID ) AS CIRCLE, "+
			"(SELECT ZONE_NAME FROM ZONE_MSTR WHERE ZONE_CODE = a.ZONE_ID) AS ZONE, (SELECT CITY_NAME FROM CITY_MSTR WHERE CITY_CODE = a.CITY_ID) AS CITY,(SELECT CITY_ZONE_NAME FROM CITY_ZONE_MSTR  "+
			"WHERE CITY_ZONE_CODE = a.CITY_ZONE_CODE) AS CITY_ZONE,(SELECT USER_FNAME FROM USER_MSTR WHERE USER_LOGIN_ID=a.OLM_ID)AS FNAME, (SELECT USER_LNAME  FROM USER_MSTR WHERE USER_LOGIN_ID=a.OLM_ID)AS LNAME FROM ASSIGNMENT_MATRIX_TEMP a WHERE STATUS = 'A' and STATUS_L1!='2' and APPROVER_L1=? ";
				
	public static ArrayList<BulkAssignmentDto> getAssignmentMatrixList(String l1approverID) throws DAOException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		BulkAssignmentDto dto;
		int counter = 1;
		try {
			StringBuffer query=new StringBuffer(SQL_SELECT_ASSIGNMENT_MATRIX);
			ArrayList<BulkAssignmentDto> ActionList = new ArrayList<BulkAssignmentDto>();
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query.toString());
			ps.setString(1, l1approverID);
			rs = ps.executeQuery();
			logger.info(query);
			while (rs.next()) {
				dto = new BulkAssignmentDto();
				dto.setOlmId(rs.getString("OLM_ID"));
				dto.setCircle(rs.getString("CIRCLE"));
				dto.setCityZone(rs.getString("ZONE"));
				dto.setCity(rs.getString("CITY"));
				dto.setCityZoneCode(rs.getString("CITY_ZONE"));
				dto.setProductName(rs.getString("PRODUCT_NAME"));
				dto.setProductLobId(rs.getString("PRODUCT_LOB"));
				dto.setPincode(rs.getString("PINCODE"));
				dto.setUserType((rs.getString("FNAME"))+" "+rs.getString("LNAME"));
				dto.setAssignmentKey(rs.getString("ASSIGNMENT_KEY"));
				dto.setPrimaryAuth(rs.getInt("PRIMARY_AUTH"));
				if(dto.getPrimaryAuth()==1)
				{
					dto.setAssignmentType("PRIMARY");
				}
				else
				{
					dto.setAssignmentType("SECONDARY");
				}
				ActionList.add(dto);
				}
			return ActionList;
		} catch (SQLException e) {
			e.printStackTrace();
			
			throw new LMSException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				
				throw new LMSException("Exception: " + e.getMessage(), e);
			}
		}

	}
	
	final static String SQL_SELECT_ASSIGNMENT_MATRIX_L2="SELECT a.OLM_ID,a.ASSIGNMENT_KEY,a.STATUS,a.PINCODE,a.PRIMARY_AUTH,(SELECT PRODUCT_NAME FROM PRODUCT_MSTR WHERE PRODUCT_ID = a.PRODUCT_ID),"+
	"(SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = a.PRODUCT_LOB_ID) AS PRODUCT_LOB,(SELECT CIRCLE_NAME FROM CIRCLE_MSTR WHERE CIRCLE_ID = a.CIRCLE_ID and LOB_ID = a.PRODUCT_LOB_ID ) AS CIRCLE, "+
	"(SELECT ZONE_NAME FROM ZONE_MSTR WHERE ZONE_CODE = a.ZONE_ID) AS ZONE, (SELECT CITY_NAME FROM CITY_MSTR WHERE CITY_CODE = a.CITY_ID) AS CITY,(SELECT CITY_ZONE_NAME FROM CITY_ZONE_MSTR  "+
	"WHERE CITY_ZONE_CODE = a.CITY_ZONE_CODE) AS CITY_ZONE,(SELECT USER_FNAME FROM USER_MSTR WHERE USER_LOGIN_ID=a.OLM_ID)AS FNAME, (SELECT USER_LNAME  FROM USER_MSTR WHERE USER_LOGIN_ID=a.OLM_ID)AS LNAME,COMMENTS_L1 FROM ASSIGNMENT_MATRIX_TEMP a WHERE STATUS = 'A' and STATUS_L2!='2' and STATUS_L1='2' and APPROVER_L1!='' and APPROVER_L2=? ";
		
public static ArrayList<BulkAssignmentDto> getSecondLevelApproval(String l2approverID) throws DAOException {
Connection con = null;
ResultSet rs = null;
PreparedStatement ps = null;
BulkAssignmentDto dto;
int counter = 1;
try {
	StringBuffer query=new StringBuffer(SQL_SELECT_ASSIGNMENT_MATRIX_L2);
	ArrayList<BulkAssignmentDto> ActionList = new ArrayList<BulkAssignmentDto>();
	con = DBConnection.getDBConnection();
	ps = con.prepareStatement(query.toString());
	ps.setString(1, l2approverID);
	rs = ps.executeQuery();
	logger.info(query);
	while (rs.next()) {
		dto = new BulkAssignmentDto();
		dto.setOlmId(rs.getString("OLM_ID"));
		dto.setCircle(rs.getString("CIRCLE"));
		dto.setCityZone(rs.getString("ZONE"));
		dto.setCity(rs.getString("CITY"));
		dto.setCityZoneCode(rs.getString("CITY_ZONE"));
		dto.setProductName(rs.getString("PRODUCT_NAME"));
		dto.setProductLobId(rs.getString("PRODUCT_LOB"));
		dto.setPincode(rs.getString("PINCODE"));
		dto.setUserType((rs.getString("FNAME"))+" "+rs.getString("LNAME"));
		dto.setAssignmentKey(rs.getString("ASSIGNMENT_KEY"));
		dto.setPrimaryAuth(rs.getInt("PRIMARY_AUTH"));
		if(dto.getPrimaryAuth()==1)
		{
			dto.setAssignmentType("PRIMARY");
		}
		else
		{
			dto.setAssignmentType("SECONDARY");
		}
		
		ActionList.add(dto);
		}
	return ActionList;
} catch (SQLException e) {
	e.printStackTrace();
	
	throw new LMSException("Exception: " + e.getMessage(), e);
} catch (Exception e) {
	e.printStackTrace();
	
	throw new LMSException("Exception: " + e.getMessage(), e);
} finally {
	try {
		DBConnection.releaseResources(con, ps, rs);
	} catch (Exception e) {
		
		throw new LMSException("Exception: " + e.getMessage(), e);
	}
}

}
final static String SQL_SELECT_PENDING_L1="SELECT COUNT(OLM_ID)AS PENDING_L1 FROM ASSIGNMENT_MATRIX_TEMP WHERE STATUS_L1='0' AND STATUS='A'and approver_L1=?  with ur";

final static String SQL_SELECT_PENDING_L2="SELECT COUNT(OLM_ID)AS PENDING_L2 FROM ASSIGNMENT_MATRIX_TEMP WHERE STATUS_L1!='0' AND STATUS_L2=0 AND STATUS='A' and approver_L2=? with ur";

public static String getPendingapprovalsCount(String userLoginId) throws DAOException {
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	ResultSet rs1 = null;
	PreparedStatement ps1 = null;
	BulkAssignmentDto dto;
	String l1count="";
	String l2count="";
	
	try {
		StringBuffer query=new StringBuffer(SQL_SELECT_PENDING_L1);
		StringBuffer query1=new StringBuffer(SQL_SELECT_PENDING_L2);
		ArrayList<BulkAssignmentDto> ActionList = new ArrayList<BulkAssignmentDto>();
		con = DBConnection.getDBConnection();
		ps = con.prepareStatement(query.toString());
		ps.setString(1, userLoginId);
		rs = ps.executeQuery();
		logger.info(query);
		while (rs.next())
		{
		l1count=rs.getString("PENDING_L1");
		}
		ps1 = con.prepareStatement(query1.toString());
		ps1.setString(1,userLoginId);
		rs1 = ps1.executeQuery();
		while (rs1.next())
		{
		l2count=rs1.getString("PENDING_L2");
		}

	} catch (SQLException e) {
		e.printStackTrace();
		
		throw new LMSException("Exception: " + e.getMessage(), e);
	} catch (Exception e) {
		e.printStackTrace();
		
		throw new LMSException("Exception: " + e.getMessage(), e);
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {
			
			throw new LMSException("Exception: " + e.getMessage(), e);
		}
	}
	return l1count+","+l2count;

	}

final static String APPROVALPENDINGFORLOB= "SELECT COUNT(assignment_key) AS PENDING_LOB_COUNT FROM ASSIGNMENT_MATRIX_TEMP where PRODUCT_LOB_ID = ? and ((STATUS_L1=0 and STATUS_L2=0) or (STATUS_L1=2 and STATUS_L2=0)) AND STATUS='A'";

public static boolean ProductLobIdNotPending(String productLobId) throws DAOException {
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	int l1count=0;
	boolean flag=true;
	
	try {
		StringBuffer query=new StringBuffer(APPROVALPENDINGFORLOB);
		con = DBConnection.getDBConnection();
		ps = con.prepareStatement(query.toString());
		ps.setInt(1, Integer.parseInt(productLobId));
		rs = ps.executeQuery();
		logger.info(query);
		while (rs.next())
		{
		l1count=rs.getInt("PENDING_LOB_COUNT");
		}
		if(l1count>0)
		{
			flag=false;
		}
	} catch (SQLException e) {
		e.printStackTrace();
		
		throw new LMSException("Exception: " + e.getMessage(), e);
	} catch (Exception e) {
		e.printStackTrace();
		
		throw new LMSException("Exception: " + e.getMessage(), e);
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {
			
			throw new LMSException("Exception: " + e.getMessage(), e);
		}
	}
	return flag;

	}

	private static String EasyAccessUrl="SELECT PARAM_NAME FROM PARAMETER_MASTER WHERE FORM_NAME=? AND STATUS='A' WITH UR";
	private static String URLReference="EasyAccessURL";
	public static String getRedirectedLink()
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String URL="";
		try {
			String query=new String(EasyAccessUrl);
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query);
			ps.setString(1,URLReference);
			rs = ps.executeQuery();
			logger.info(query);
			while (rs.next())
			{
				URL = rs.getString("PARAM_NAME");
			}
			}
		catch(Exception e)
		{
			
		}
		
		return URL;
	}


public static boolean isValidOlmId(String olmID) throws DAOException {
	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String checkOlmId="select * from lms2.USER_MSTR where upper(USER_LOGIN_ID) = upper(?) and STATUS = 'A' with ur";

	try {
		con = DBConnection.getDBConnection();
		ps = con.prepareStatement(checkOlmId);
		ps.setString(1,olmID );
		rs=ps.executeQuery();
		if(rs==null)
		{
			logger.info("OLM ID doesnt exist");
			return false;
		}
		else if(rs.next())
		{	
			logger.info("OLMID exists");
			return true;
		}
		} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
			rs.close();
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return isExist;
}


public static boolean isValidStage(BulkMstrDTO bulkDto) throws DAOException 
{
Connection con = null;
PreparedStatement ps = null;
ResultSet rs = null;
boolean isExist=false;
String validLeadStatus="";


String checkLeadStatusId="select LEAD_STATUS_ID from LEAD_STATUS where LEAD_STATUS_ID =? WITH UR";

try {
	con = DBConnection.getDBConnection();
	
	validLeadStatus=bulkDto.getStage();
	ps = con.prepareStatement(checkLeadStatusId);
	ps.setInt(1,Integer.parseInt(validLeadStatus));
	rs=ps.executeQuery();
	
	if(rs==null)
	{
		logger.info("valid lead status id doesnt exist");
		return isExist;
	}
	
	else if(rs.next())
	{	
		logger.info("valid lead status id exists");
		isExist=true;
		return isExist;
	}
	
	

} catch (Exception e) {
	e.printStackTrace();
} finally {
	try {
		DBConnection.releaseResources(con, ps, rs);
	} catch (Exception e) {				
		throw new DAOException(e.getMessage(), e);
	}
}
return isExist;
}
}


