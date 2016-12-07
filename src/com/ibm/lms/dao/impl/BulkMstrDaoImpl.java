package com.ibm.lms.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.ibm.lms.common.CommonMstrUtil;
import com.ibm.lms.engine.util.Constants;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.common.LMSStatusCodes;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dao.BulkAssigmentDao;
import com.ibm.lms.dao.BulkMstrDao;
import com.ibm.lms.dto.BulkMstrDTO;
import com.ibm.lms.dto.CircleDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.LeadRegistrationFormBean;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.impl.BulkMstrServiceImpl;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.websphere.product.history.xml.includedEFix;
import com.tivoli.pd.jasn1.boolean32;

public class BulkMstrDaoImpl implements BulkMstrDao {

	
	private static final Logger logger;
	static {
		logger = Logger.getLogger(BulkMstrDao.class);
	}

private static BulkMstrDaoImpl bulkMstrDaoImpl=null;
	
	private BulkMstrDaoImpl(){
		
	}
	
	public static BulkMstrDaoImpl bulkMstrDaoInstance()
	{
		if(bulkMstrDaoImpl==null)
		{
			bulkMstrDaoImpl=new BulkMstrDaoImpl();
		}
		return bulkMstrDaoImpl;
		
	}
	//@Override
	public ArrayList<BulkMstrDTO> uploadZone(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) throws DAOException
	{
		
		//insert zone starts here
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		//PreparedStatement ps1 = null;
		BulkMstrDTO dto = null;
		ResultSet rs1=null;
		ResultSet rs2=null;
		int circleId=0;
		int lobId=0;
		String zoneCode="";
		ArrayList<BulkMstrDTO> returnBulkMstrDTO = new ArrayList<BulkMstrDTO>();
		
		
		try {
			BulkMstrDTO bulkMstrDTO = new BulkMstrDTO();
			CircleDTO circleDTO= new CircleDTO();
			con = DBConnection.getDBConnection();
			
			ps1 =con.prepareStatement("select ZONE_ID,ZONE_CODE from ZONE_MSTR order by ZONE_CODE desc FETCH FIRST 1 ROW ONLY");
			//fetching last zone code and zone id
			
			
			ps = con.prepareStatement("INSERT INTO ZONE_MSTR(ZONE_ID, ZONE_CODE, ZONE_NAME, STATUS, TRANSACTION_TIME, UPDATED_BY,CIRCLE_MSTR_ID) "+
					 " (select max(ZONE_ID)+1,?,?,'A',current timestamp,?,(select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID= ? and LOB_ID=? and STATUS='A')from ZONE_MSTR) WITH UR");
			

			ps3=con.prepareStatement("SELECT * FROM ZONE_MSTR WHERE ZONE_NAME =?  AND CIRCLE_MSTR_ID="+
				"(select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID=? and LOB_ID=? AND STATUS = 'A') AND STATUS = 'A' WITH UR ");
			
			ps2 = con.prepareStatement("UPDATE ZONE_MSTR SET STATUS='D',"+
					 "TRANSACTION_TIME= CURRENT TIMESTAMP, UPDATED_BY= ? "+
					 "WHERE ZONE_NAME = ?  AND CIRCLE_MSTR_ID=(select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID=? and LOB_ID=?)   AND STATUS = 'A' WITH UR");
			
					
			for (Iterator<BulkMstrDTO> itr = listBulkDto.iterator();itr.hasNext();) 
			{
				try
				{
					BulkMstrDTO dto1;
					dto1= (BulkMstrDTO)itr.next();
					
						if(dto1.isErrFlag()){
								if(!(dto1.getActionType().equalsIgnoreCase("c") || dto1.getActionType().equalsIgnoreCase("d") ) )
								{
									dto1.setMessage("Enter either c or d for CREATION/DELETION | ");
									returnBulkMstrDTO.add(dto1);
									continue;
								}
								
								if(dto1.getProductLobId().equals("") || dto1.getCircle().equals("") || dto1.getZoneName().equals(""))
								{
									dto1.setMessage("PRODUCT_LOB_ID CIRCLE_ID and ZONE are mandatory fields |");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}
								if(dto1.getProductLobId().length()>Constants.MAX_LENGTH)
								{
									dto1.setMessage("Invalid Product Lob Id Length.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}
								if(dto1.getCircle().length()> Constants.MAX_LENGTH)
								{
									dto1.setMessage("Invalid Circle Id Length.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}/*
								if(dto1.getZoneName().length()>Constants.MAX_ZONE_NAME_LENGTH)//MAX_ZONE_NAME_LENGTH=30
								{
									dto1.setMessage("Zone Name should be of 30 Characters or less.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;	
								}*/
						}
					
						else if(!dto1.isErrFlag() && dto1.getActionType().equalsIgnoreCase("c") )
						{
									if(!isValidCircle(dto1))
									{	
										dto1.setMessage("Invalid Circle Id and Product Lob id combination.");
										dto1.setErrFlag(true);
										returnBulkMstrDTO.add(dto1);
										continue;
									}
										
									if(isDuplicateZone(dto1))
									{
										dto1.setMessage("Zone Name Already Exists for given Circle Id and Product Lob id combination.");
										dto1.setErrFlag(true);
										returnBulkMstrDTO.add(dto1);
										continue;
									}
									
									rs1=ps1.executeQuery();
									//fetching last zone code and zone id
									if(rs1.next())
									{	
										circleDTO.setZoneId(rs1.getInt("ZONE_ID"));
										circleDTO.setZoneName(rs1.getString("ZONE_CODE"));
										
									}
									
									String initialzonechar="Z";
																		
									/* Added by Parnika */
									String StringZoneChar = "";
									int intZoneCode = 0;
									for(int i = 1; i< circleDTO.getZoneName().length(); i++ ){
										try{
											intZoneCode=Integer.parseInt((circleDTO.getZoneName()).substring(i,circleDTO.getZoneName().length() ));

											logger.info("intZoneCode:::::::::::"+intZoneCode);
											logger.info("asa:::::i"+i);

											StringZoneChar = circleDTO.getZoneName().substring(0 , i);
											logger.info("StringZoneChar:::::"+StringZoneChar);
											logger.info("circleDTO.getZoneName()::::::"+circleDTO.getZoneName());
											break;
										}
										catch(Exception e){
											continue;
										}
									}
									if(!StringZoneChar.equals("")){
										initialzonechar = StringZoneChar;
									}
									/* End of changes by parnika */
									
									int maxZonenumber=intZoneCode;
																
									if(maxZonenumber<9)
									{
										maxZonenumber++;
										zoneCode=initialzonechar+"0000"+maxZonenumber;
									}
									else if(maxZonenumber >=9 && maxZonenumber <99  )
									{
										maxZonenumber++;
										zoneCode=initialzonechar+"000"+maxZonenumber;
									}
									else if(maxZonenumber >=99 && maxZonenumber <999 )
									{
										maxZonenumber++;
										zoneCode=initialzonechar+"00"+maxZonenumber;
									}
									else if(maxZonenumber >=999 && maxZonenumber <9999 )
									{
										maxZonenumber++;
										zoneCode=initialzonechar+"0"+maxZonenumber;
									}
									else if(maxZonenumber >=9999 && maxZonenumber <99999)
									{
										maxZonenumber++;
										zoneCode=initialzonechar+maxZonenumber;
									}
									logger.info("zoneCode::::::::::::::::::::::::::::"+zoneCode);
									// end of genrating zone code 
								 
								circleId=	Integer.parseInt(dto1.getCircle());
								lobId=Integer.parseInt(dto1.getProductLobId());
								ps.setString(1,zoneCode);
								ps.setString(2,dto1.getZoneName().toUpperCase());
								ps.setString(3,userBean.getUserLoginId());
								ps.setInt(4,circleId);
								ps.setInt(5,lobId);
								
								ps.executeUpdate();
								dto1.setZoneCode(zoneCode);
								dto1.setMessage("Zone Created Successfully.");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
			
					else if(!dto1.isErrFlag() && dto1.getActionType().equalsIgnoreCase("d"))
					{
						
						circleId=	Integer.parseInt(dto1.getCircle());
						lobId=Integer.parseInt(dto1.getProductLobId());

						ps3.setString(1,dto1.getZoneName().toUpperCase());
						ps3.setInt(2,circleId);
						ps3.setInt(3,lobId);
						
						rs2=ps3.executeQuery();
						
						
						if(rs2.next())
						{

							ps2.setString(1,userBean.getUserLoginId());
							ps2.setString(2,dto1.getZoneName().toUpperCase());
							ps2.setInt(3,circleId);
							ps2.setInt(4,lobId);
								
							ps2.executeUpdate();
								
				
								bulkMstrDTO = new BulkMstrDTO();
								
								dto1.setMessage("Zone deleted successfully");
								dto1.setZoneCode("Zone Code has been deleted");
								
								returnBulkMstrDTO.add(dto1);
								continue;
								
								}
						else
						{
							dto1.setMessage("Zone not deleted.");
							returnBulkMstrDTO.add(dto1);
							continue;
						}
					}
					//}
			}	
			
			catch(Exception e)
			{
				BulkMstrDTO bulkDto= new BulkMstrDTO();
				bulkDto.setMessage("Zone not created");
				bulkDto.setZoneCode("Zone Code not generated.");
				returnBulkMstrDTO.add(bulkDto);
				e.printStackTrace();
			}

			} // END Iterator

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs1);
				DBConnection.releaseResources(con, ps1, rs2);
				DBConnection.releaseResources(con, ps2, null);
				DBConnection.releaseResources(con, ps3, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return returnBulkMstrDTO;
		
		//insert zone ends here
	}
	
	public ArrayList<BulkMstrDTO> uploadCity(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) 
	throws DAOException
	{	
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		BulkMstrDTO dto = null;
		ResultSet rs1=null;
		ResultSet rs2=null;
		String zoneCode="";
		String cityName="";
		//int circleId=0;
		//int lobId=0;
		String cityCode="";
		ArrayList<BulkMstrDTO> arr= new ArrayList<BulkMstrDTO>();
		ArrayList<BulkMstrDTO> returnBulkMstrDTO = new ArrayList<BulkMstrDTO>();
		try {
			
			
			
			
			//ArrayList<BulkZoneMsgDTO> arr = new ArrayList<BulkZoneMsgDTO>();
			BulkMstrDTO bulkMstrDTO = new BulkMstrDTO();
			CircleDTO circleDTO= new CircleDTO();
			con = DBConnection.getDBConnection();

			
			ps1 =con.prepareStatement("SELECT CITY_ID,CITY_CODE FROM CITY_MSTR ORDER BY CITY_CODE DESC FETCH FIRST 1 ROW ONLY");
			logger.info("fetching last city code and city id");
			
			
			ps = con.prepareStatement("INSERT INTO CITY_MSTR(CITY_ID, CITY_CODE, CITY_NAME, STATUS,ZONE_CODE, TRANSACTION_TIME, UPDATED_BY) "+
					   				  "select max(CITY_ID)+1,?,?,'A',?,current timestamp,? from CITY_MSTR WITH UR");
			
			
			
			ps3=con.prepareStatement("SELECT * FROM CITY_MSTR WHERE CITY_NAME=? and ZONE_CODE=? and STATUS='A' WITH UR ");
			
			ps2 = con.prepareStatement("UPDATE CITY_MSTR SET STATUS='D', TRANSACTION_TIME= CURRENT TIMESTAMP , UPDATED_BY=?"+
                                      "WHERE CITY_NAME=? AND ZONE_CODE=? AND STATUS='A' WITH UR");
			
		
					
			for (Iterator<BulkMstrDTO> itr = listBulkDto.iterator();itr.hasNext();) 
			{
				try
				{
					
					dto = (BulkMstrDTO) itr.next();	
					
					if(dto.isErrFlag()){
						
							if(!(dto.getActionType().equalsIgnoreCase("c") || dto.getActionType().equalsIgnoreCase("d")))
							{
								dto.setMessage("Enter either c or d for CREATION/DELETION | ");
								dto.setErrFlag(true);
								returnBulkMstrDTO.add(dto);
								continue;
							}
							
							if(dto.getZoneCode().equals("") || dto.getCityName().equals("") )
							{
								dto.setMessage("ZONE CODE and CITY NAME are mandatory fields |");
								dto.setErrFlag(true);
								returnBulkMstrDTO.add(dto);
								continue;
							}
					
					}
					
					
					else if(!dto.isErrFlag() && dto.getActionType().equalsIgnoreCase("c") ){
							
							
						
						if(!isValidZoneCode(dto))
						{
							dto.setMessage("Invalid ZoneCode.");
							dto.setErrFlag(true);
							returnBulkMstrDTO.add(dto);
							continue;
						}
							
						if(isDuplicateCity(dto))
						{
							dto.setMessage("City Name Already Exists for Given Circle");
							dto.setErrFlag(true);
							returnBulkMstrDTO.add(dto);
							continue;
						}
						
						
							rs1=ps1.executeQuery();
							while(rs1.next())
							{
								circleDTO.setCityId(rs1.getInt("CITY_ID"));
								circleDTO.setCityCode(rs1.getString("CITY_CODE"));
							}
							
							//int intCityCode=Integer.parseInt((circleDTO.getCityCode()).substring(1,circleDTO.getCityCode().length() ));
							
						
							
							
							//Added by Parnika 
							String initialzonechar="C";
							String StringCityChar = "";
							
							int intCityCode = 0;
							for(int i = 1; i< circleDTO.getCityCode().length(); i++ ){
								try{
									intCityCode=Integer.parseInt((circleDTO.getCityCode()).substring(i,circleDTO.getCityCode().length() ));
									logger.info("intCityCode:::::::::::"+intCityCode);
									StringCityChar = circleDTO.getCityCode().substring(0 , i);
									
									break;
								}
								catch(Exception e){
									continue;
								}
							}
							
							if(!StringCityChar.equals("")){
								initialzonechar = StringCityChar;
							}

							// End of changes by parnika 
							
							
							
							int maxCityNumber=intCityCode;
							
							if(maxCityNumber<9)
							{
								maxCityNumber++;
								cityCode=initialzonechar+"0000"+maxCityNumber;
							}
							else if(maxCityNumber >=9 && maxCityNumber <99  )
							{
								maxCityNumber++;
								cityCode=initialzonechar+"000"+maxCityNumber;
							}
							else if(maxCityNumber >=99 && maxCityNumber <999 )
							{
								maxCityNumber++;
								cityCode=initialzonechar+"00"+maxCityNumber;
							}
							else if(maxCityNumber >=999 && maxCityNumber <9999 )
							{
								maxCityNumber++;
								cityCode=initialzonechar+"0"+maxCityNumber;
							}
							else if(maxCityNumber >=9999 && maxCityNumber <99999)
							{
								maxCityNumber++;
								cityCode=initialzonechar+maxCityNumber;
							}
							logger.info("cityCode::::::::::::::::::::::::::::"+cityCode);
							// end of genrating city code 
						
						zoneCode=dto.getZoneCode();
						cityName=dto.getCityName();
						
						logger.info("cityCode:::::::::::::::"+cityCode);
						logger.info("cityName:::::::::::::::"+dto.getCityName());
						ps.setString(1,cityCode);
						ps.setString(2,dto.getCityName().toUpperCase());
						ps.setString(3,dto.getZoneCode().toUpperCase());
						ps.setString(4,userBean.getUserLoginId());

						ps.executeUpdate();
						
						bulkMstrDTO = new BulkMstrDTO();
						
						dto.setMessage("City inserted successfully");
						dto.setCitycode(cityCode.toUpperCase());
						
						returnBulkMstrDTO.add(dto);
				}
					
					
				else if(!dto.isErrFlag() && dto.getActionType().equalsIgnoreCase("d"))
				{
					cityName=dto.getCityName();
					zoneCode=dto.getZoneCode();
					
					ps3.setString(1,dto.getCityName().toUpperCase());
					ps3.setString(2,dto.getZoneCode().toUpperCase());
					
					rs2=ps3.executeQuery();
					
					
					if(rs2.next())
					{

							ps2.setString(1,userBean.getUserLoginId());
							ps2.setString(2,dto.getCityName().toUpperCase());
							ps2.setString(3,dto.getZoneCode().toUpperCase());
							
							ps2.executeUpdate();
			
							dto.setMessage("City deleted successfully.");
							
							returnBulkMstrDTO.add(dto);
							
							}
					else
					{
						
						dto.setMessage("City Not Deleted.");
						returnBulkMstrDTO.add(dto);
					}
				}
			}
			
			catch(Exception e)
				{
					dto.setMessage("City not inserted.");
					dto.setCitycode("City Code not generated");
	
					returnBulkMstrDTO.add(dto);
					e.printStackTrace();
				}
			
			}

		}catch (Exception e) {
			e.printStackTrace();
			dto.setMessage("City not inserted.");
			dto.setCitycode("City Code not Generated.");

			returnBulkMstrDTO.add(dto);
			
		} finally {
			try {
				DBConnection.releaseResources(con, ps, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return returnBulkMstrDTO;

	}
	

	public ArrayList<BulkMstrDTO> uploadCityZone(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) throws DAOException
	{
		
		//insert cityZone starts here
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		BulkMstrDTO dto = null;
		ResultSet rs1=null;
		ResultSet rs2=null;
		String cityCode="";
		String cityZoneName="";
		String cityZoneCode="";
		
		ArrayList<BulkMstrDTO> arr = new ArrayList<BulkMstrDTO>();
		ArrayList<BulkMstrDTO> returnBulkMstrDTO = new ArrayList<BulkMstrDTO>();
		
		try {
			BulkMstrDTO bulkMstrDTO = new BulkMstrDTO();
			CircleDTO circleDTO= new CircleDTO();
			con = DBConnection.getDBConnection();
			
			ps1 =con.prepareStatement("Select CITY_ZONE_ID,CITY_ZONE_CODE from CITY_ZONE_MSTR order by CITY_ZONE_CODE desc FETCH FIRST 1 ROW ONLY");
			//fetching last cityzone code and cityzone id
			
		
			
			ps = con.prepareStatement("INSERT INTO CITY_ZONE_MSTR(CITY_ZONE_ID, CITY_ZONE_CODE, CITY_ZONE_NAME, STATUS,CITY_CODE, TRANSACTION_TIME, UPDATED_BY) "+
									"select max(CITY_ZONE_ID)+1,?,?,'A',?,current timestamp,? from CITY_ZONE_MSTR WITH UR");
			
			
			ps3=con.prepareStatement("SELECT * FROM CITY_ZONE_MSTR WHERE CITY_CODE=? and CITY_ZONE_NAME=? and STATUS='A' WITH UR ");
			
			ps2 = con.prepareStatement("UPDATE CITY_ZONE_MSTR SET STATUS='D', TRANSACTION_TIME= CURRENT TIMESTAMP , UPDATED_BY=?"+
									 "WHERE CITY_CODE=? AND CITY_ZONE_NAME=? AND STATUS='A' WITH UR");
			
					
			
			for (Iterator<BulkMstrDTO> itr = listBulkDto.iterator();itr.hasNext();) 
			{
				try
				{
					
					dto = (BulkMstrDTO) itr.next();	
					
					
					
					
					if(dto.isErrFlag()){
						
						
						if(!(dto.getActionType().equalsIgnoreCase("c") || dto.getActionType().equalsIgnoreCase("d")))
						{
							//errMsg.append("Enter either c or d for CREATION/DELETION | ");
							dto.setMessage("Enter either c or d for CREATION/DELETION | ");
							returnBulkMstrDTO.add(dto);
						}
						

						if(dto.getCitycode().equals("") || dto.getCityZoneName().equals(""))
						{
							dto.setMessage("CITY CODE and CITYZONE NAME are mandatory fields |");
							dto.setErrFlag(true);
							returnBulkMstrDTO.add(dto);
						}
				
					}
					
					
					else if(!dto.isErrFlag() && dto.getActionType().equalsIgnoreCase("c") )
					{
						
						
						if(!isValidCityCode(dto))
						{
							dto.setMessage("Invalid City Code.");
							dto.setErrFlag(true);
							returnBulkMstrDTO.add(dto);
							continue;
						}
							
						if(isDuplicateCityZone(dto))
						{
							dto.setMessage("CityZone Name Already Exists.");
							dto.setErrFlag(true);
							returnBulkMstrDTO.add(dto);
							continue;
						}
						

						rs1=ps1.executeQuery();
						while(rs1.next())
						{
							circleDTO.setCityZoneId(rs1.getInt("CITY_ZONE_ID"));
							circleDTO.setCityZoneCode(rs1.getString("CITY_ZONE_CODE"));
						}

						//int intcityZoneCode=Integer.parseInt(circleDTO.getCityZoneCode().substring(2, circleDTO.getCityZoneCode().length()));
						
						
						
						//Added by Parnika 
						
						String StringCityZoneChar = "";
						String initialzonechar="CZ";
						int intcityZoneCode = 0;
						for(int i = 1; i< circleDTO.getCityZoneCode().length(); i++ ){
							try{
								intcityZoneCode=Integer.parseInt((circleDTO.getCityZoneCode()).substring(i,circleDTO.getCityZoneCode().length() ));
								logger.info("intcityZoneCode:::::::::::"+intcityZoneCode);
								StringCityZoneChar = circleDTO.getCityZoneCode().substring(0 , i);
								break;
							}
							catch(Exception e){
								continue;
							}
						}

						// End of changes by parnika 
						
						

						if(!StringCityZoneChar.equals("")){
							initialzonechar = StringCityZoneChar;
						}
						
						int maxcityZoneCode=intcityZoneCode;
						
						
						if(maxcityZoneCode<9)
						{
							maxcityZoneCode++;
							cityZoneCode=initialzonechar+"0000"+maxcityZoneCode;
						}
						else if(maxcityZoneCode >=9 && maxcityZoneCode <99  )
						{
							maxcityZoneCode++;
							cityZoneCode=initialzonechar+"000"+maxcityZoneCode;
						}
						else if(maxcityZoneCode >=99 && maxcityZoneCode <999 )
						{
							maxcityZoneCode++;
							cityZoneCode=initialzonechar+"00"+maxcityZoneCode;
						}
						else if(maxcityZoneCode >=999 && maxcityZoneCode <9999 )
						{
							maxcityZoneCode++;
							cityZoneCode=initialzonechar+"0"+maxcityZoneCode;
						}
						else if(maxcityZoneCode >=9999 && maxcityZoneCode <99999)
						{
							maxcityZoneCode++;
							cityZoneCode=initialzonechar+maxcityZoneCode;
						}
						// end of genrating zone code 
					 
					ps.setString(1,cityZoneCode);
					ps.setString(2,dto.getCityZoneName().toUpperCase());
					ps.setString(3,dto.getCitycode().toUpperCase());
					ps.setString(4,userBean.getUserLoginId());
					
					ps.executeUpdate();
						 
					dto.setMessage("CityZone created successfully");
					dto.setCityZoneCode(cityZoneCode);
					
					returnBulkMstrDTO.add(dto);
					}
					
					else if(!dto.isErrFlag() && dto.getActionType().equalsIgnoreCase("d"))
					{
						
						
						
						ps3.setString(1,dto.getCitycode().toUpperCase());
						ps3.setString(2,dto.getCityZoneName().toUpperCase());
						
						rs2=ps3.executeQuery();
						
						
						if(rs2.next())
						{

								ps2.setString(1,userBean.getUserLoginId());
								ps2.setString(2,dto.getCitycode().toUpperCase());
								ps2.setString(3,dto.getCityZoneName().toUpperCase());
								
								ps2.executeUpdate();
				
								dto.setMessage("CityZone deleted successfully.");

								returnBulkMstrDTO.add(dto);
								
								}
						else
						{
							
							dto.setMessage("City Not Deleted.");

							returnBulkMstrDTO.add(dto);
						}
						
						
						
						
					}
					
					
			}
			
			catch(Exception e)
			{
				dto.setMessage("CityZone not created");
				dto.setZoneCode("Not Generated");
				returnBulkMstrDTO.add(dto);
				e.printStackTrace();
			}
			
			
			//con.commit();
			}	
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				DBConnection.releaseResources(con, ps, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return returnBulkMstrDTO;
		
		
		//insert cityzone ends here
	
	}
	
	
	
	
	
	
	
	
	
	
	
	public ArrayList<BulkMstrDTO> uploadPinCode(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) throws DAOException
	{
		
		//insert zone starts here
		Connection con = null;
		PreparedStatement psInsertPinCode = null;
		PreparedStatement psDeletePinCode = null;
		//BulkMstrDTO dto = null;
		ArrayList<BulkMstrDTO> returnBulkMstrDTO = new ArrayList<BulkMstrDTO>();
		
		
		try {
			BulkMstrDTO bulkMstrDTO = new BulkMstrDTO();
			con = DBConnection.getDBConnection();
			
			psInsertPinCode = con.prepareStatement("INSERT INTO PINCODE_MSTR(PINCODE, CITY_ZONE_CODE, STATUS, TRANSACTION_TIME, UPDATED_BY) values "+
					 " (?,?,'A',current timestamp,?) WITH UR");
			
			psDeletePinCode = con.prepareStatement("DELETE FROM PINCODE_MSTR where PINCODE=? and STATUS='A' WITH UR");
			
					
			for (Iterator<BulkMstrDTO> itr = listBulkDto.iterator();itr.hasNext();) 
			{
				try
				{
					BulkMstrDTO dto1;
					dto1= (BulkMstrDTO)itr.next();
					
						if(dto1.isErrFlag()){
								if(!(dto1.getActionType().equalsIgnoreCase("c") || dto1.getActionType().equalsIgnoreCase("d") ) )
								{
									dto1.setMessage("Enter either c or d for CREATION/DELETION | ");
									returnBulkMstrDTO.add(dto1);
									continue;
								}
								
								if(dto1.getPinCode().equals("") || dto1.getCityZoneCode().equals(""))
								{
									dto1.setMessage("PinCode and CityZone Code are mandatory fields |");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}
								
								if(dto1.getPinCode().length()!=6)
								{
									dto1.setMessage("Invalid PinCode.It should be Of 6 Digits");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}
								
						}
					
						else if(!dto1.isErrFlag() && dto1.getActionType().equalsIgnoreCase("c") )
						{
									if(dto1.getPinCode().length()!=6)
									{
										dto1.setMessage("Invalid PinCode.");
										dto1.setErrFlag(true);
										returnBulkMstrDTO.add(dto1);
										continue;
									}
									
									if(!isValidCityZoneCode(dto1))
									{	
										dto1.setMessage("Invalid CityZone Code.");
										dto1.setErrFlag(true);
										returnBulkMstrDTO.add(dto1);
										continue;
									}
										
									if(isDuplicatePinCode(dto1))
									{
										dto1.setMessage("Pin Code Already Exists.");
										dto1.setErrFlag(true);
										returnBulkMstrDTO.add(dto1);
										continue;
									}
									
									psInsertPinCode.setString(1,dto1.getPinCode());
									psInsertPinCode.setString(2,dto1.getCityZoneCode().toUpperCase());
									psInsertPinCode.setString(3,userBean.getUserLoginId());
								
								psInsertPinCode.executeUpdate();
								
								dto1.setMessage("PinCode Inserted Successfully.");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
			
					else if(!dto1.isErrFlag() && dto1.getActionType().equalsIgnoreCase("d"))
					{
						

						if(dto1.getPinCode().length()!=6)
						{
							dto1.setMessage("Invalid PinCode.");
							dto1.setErrFlag(true);
							returnBulkMstrDTO.add(dto1);
							continue;
						}
						
						if(!isDuplicatePinCode(dto1))
						{
							dto1.setMessage("Pin Code Doesnt Exists.");
							dto1.setErrFlag(true);
							returnBulkMstrDTO.add(dto1);
							continue;
						}
						
						else if(isDuplicatePinCode(dto1))
						{

							psDeletePinCode.setString(1,dto1.getPinCode());
								
							psDeletePinCode.executeUpdate();
				
								bulkMstrDTO = new BulkMstrDTO();
								
								dto1.setMessage("PinCode deleted successfully");
								
								returnBulkMstrDTO.add(dto1);
								continue;
								
						}
						else
						{
							dto1.setMessage("Pin Code could not be deleted.");
							returnBulkMstrDTO.add(dto1);
							continue;
						}
					}
						
					//}
			}	
			
			catch(Exception e)
			{
				// dto1 to be updated in returnBulkMstrDTO
				BulkMstrDTO bulkDto= new BulkMstrDTO();
				bulkDto.setMessage("Pin Code not inserted.");
				returnBulkMstrDTO.add(bulkDto);
				e.printStackTrace();
			}

			} // END Iterator

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				DBConnection.releaseResources(con, psInsertPinCode, null);
				DBConnection.releaseResources(con, psDeletePinCode, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		return returnBulkMstrDTO;
		//insert pincode ends here
	}
	
	
	
	
	
	
	public ArrayList<BulkMstrDTO> uploadRSUCode(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) throws DAOException
	{
		
		//insert zone starts here
		Connection con = null;
		PreparedStatement psInsertRSU = null;
		PreparedStatement psDeleteRSU = null;
		ArrayList<BulkMstrDTO> returnBulkMstrDTO = new ArrayList<BulkMstrDTO>();
		
		
		try {
			BulkMstrDTO bulkMstrDTO = new BulkMstrDTO();
			con = DBConnection.getDBConnection();
			
			psInsertRSU = con.prepareStatement("INSERT INTO RSU_MSTR(RSU_ID, RSU_CODE, STATUS, CITY_ZONE_CODE,TRANSACTION_TIME, UPDATED_BY) "+
					 " (SELECT MAX(RSU_ID)+1,?,'A',?,current timestamp,? from RSU_MSTR) WITH UR");
			
			psDeleteRSU = con.prepareStatement("UPDATE RSU_MSTR SET STATUS='D',TRANSACTION_TIME= CURRENT TIMESTAMP, UPDATED_BY=? "+
					 "WHERE RSU_CODE =?  AND CITY_ZONE_CODE=? AND STATUS = 'A' WITH UR");
			
					
			for (Iterator<BulkMstrDTO> itr = listBulkDto.iterator();itr.hasNext();) 
			{
				try
				{
					BulkMstrDTO dto1;
					dto1= (BulkMstrDTO)itr.next();
					
						if(dto1.isErrFlag()){
								if(!(dto1.getActionType().equalsIgnoreCase("c") || dto1.getActionType().equalsIgnoreCase("d") ) )
								{
									dto1.setMessage("Enter either c or d for CREATION/DELETION | ");
									returnBulkMstrDTO.add(dto1);
									continue;
								}
								
								if(dto1.getRsuCode().equals("") || dto1.getCityZoneCode().equals(""))
								{
									dto1.setMessage("RSU Code and CityZone Code are mandatory fields |");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}
						}
					
						else if(!dto1.isErrFlag() && dto1.getActionType().equalsIgnoreCase("c") )
						{
									if(!isValidCityZoneCode(dto1))
									{
										dto1.setMessage("Invalid CityZone Code.");
										dto1.setErrFlag(true);
										returnBulkMstrDTO.add(dto1);
										continue;
									}
										
									if(isDuplicateRSUCode(dto1))
									{
										logger.info("in dupliacte RSU Code check::::::::::::::::::::::::::::");
										dto1.setMessage("RSU Code Already Exists.");
										dto1.setErrFlag(true);
										returnBulkMstrDTO.add(dto1);
										continue;
									}
									
									psInsertRSU.setString(1,dto1.getRsuCode());
									psInsertRSU.setString(2,dto1.getCityZoneCode().toUpperCase());
									psInsertRSU.setString(3,userBean.getUserLoginId());
								
								psInsertRSU.executeUpdate();
								dto1.setMessage("RSU Code Inserted Successfully.");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
			
					else if(!dto1.isErrFlag() && dto1.getActionType().equalsIgnoreCase("d"))
					{
						
						
						if(!isDuplicateRSUCode(dto1))
						{
							dto1.setMessage("RSU Code Doesnt Exists.");
							dto1.setErrFlag(true);
							returnBulkMstrDTO.add(dto1);
							continue;
						}
						
						else if(isDuplicateRSUCode(dto1))
						{

							psDeleteRSU.setString(1,userBean.getUserLoginId());
							psDeleteRSU.setString(2,dto1.getRsuCode());
							psDeleteRSU.setString(3,dto1.getCityZoneCode());
								
							psDeleteRSU.executeUpdate();
				
								bulkMstrDTO = new BulkMstrDTO();
								
								dto1.setMessage("RSU Code deleted successfully");
								
								returnBulkMstrDTO.add(dto1);
								continue;
								
						}
						else
						{
							dto1.setMessage("RSU Code could not be deleted.");
							returnBulkMstrDTO.add(dto1);
							continue;
						}
					}
						
					//}
			}	
			
			catch(Exception e)
			{
				// dto1 to be updated in returnBulkMstrDTO
				BulkMstrDTO bulkDto= new BulkMstrDTO();
				bulkDto.setMessage("RSU Code not uploaded.");
				returnBulkMstrDTO.add(bulkDto);
				e.printStackTrace();
			}

			} // END Iterator

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				DBConnection.releaseResources(con, psInsertRSU, null);
				DBConnection.releaseResources(con, psDeleteRSU, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		return returnBulkMstrDTO;
		//insert pincode ends here
	}
	
	
	
	public ArrayList<BulkMstrDTO> uploadAutoAssignmentMatrix(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) throws DAOException
	{logger.info("inside uploadAutoAssignmentMatrix:::::::::");
	//insert zone starts here
	Connection con = null;
	PreparedStatement psInsertMatrix = null;
	//PreparedStatement psInsertMatrix1 = null;
	PreparedStatement psDeleteMatrix = null;
	PreparedStatement psGetZoneFromCity = null;
	PreparedStatement psGetDataFromPin = null;
	PreparedStatement psGetZoneFromCityZone = null;
	ResultSet rs=null;
	ResultSet rs1=null;
	int prodLobId=-1;
	int circleId=-1;
	int prodId=-1;
	ArrayList<BulkMstrDTO> returnBulkMstrDTO = new ArrayList<BulkMstrDTO>();
	
	
	try {
		BulkMstrDTO bulkMstrDTO = new BulkMstrDTO();
		con = DBConnection.getDBConnection();
		
		psInsertMatrix = con.prepareStatement ("INSERT INTO AUTO_ASSIGNMENT_MATRIX(LOB_ID, PRODUCT_ID, CIRCLE_ID,ZONE_CODE, CITY_CODE,CITY_ZONE_CODE, PINCODE,STATUS, UPDATED_DT, UPDATED_BY,REQUEST_CATEGORY_ID,AUTO_ASSIGNMENT_TYPE) values(?,?,?,?,?,?,?,'A',current timestamp,?,?,?)");
		
		
		//psInsertMatrix1 = con.prepareStatement("INSERT INTO AUTO_ASSIGNMENT_MATRIX(LOB_ID, PRODUCT_ID, CIRCLE_ID,ZONE_CODE, CITY_CODE,STATUS, UPDATED_DT, UPDATED_BY) values(?,?,?,?,?,'A',current timestamp,?)");
		
		/*psDeleteMatrix = con.prepareStatement("UPDATE AUTO_ASSIGNMENT_MATRIX SET STATUS='D',UPDATED_DT= CURRENT TIMESTAMP, UPDATED_BY=? "+
											  "WHERE LOB_ID =?  AND PRODUCT_ID=? AND CIRCLE_ID=? AND CITY_CODE=? AND STATUS = 'A' WITH UR");
		*/
		psDeleteMatrix = con.prepareStatement("UPDATE AUTO_ASSIGNMENT_MATRIX SET STATUS='D',UPDATED_DT= CURRENT TIMESTAMP, UPDATED_BY=? WHERE LOB_ID =?  AND PRODUCT_ID=? AND CIRCLE_ID=? AND ZONE_CODE=? AND CITY_CODE=? AND CITY_ZONE_CODE=? AND PINCODE=? AND STATUS = 'A' and AUTO_ASSIGNMENT_TYPE=? WITH UR");
		
		psGetZoneFromCity= con.prepareStatement("SELECT ZONE_CODE FROM CITY_MSTR WHERE CITY_CODE=? AND STATUS='A' WITH UR");
		
		psGetDataFromPin= con.prepareStatement("SELECT CM.ZONE_CODE, CZM.CITY_CODE, CZM.CITY_ZONE_CODE, PM.PINCODE "+
							"FROM CITY_ZONE_MSTR CZM ,PINCODE_MSTR PM , CITY_MSTR CM "+
							"WHERE CZM.CITY_ZONE_CODE = PM.CITY_ZONE_CODE and PM.PINCODE=? and CM.CITY_CODE = CZM.CITY_CODE "+
							"AND PM.STATUS='A' AND CM.STATUS='A' AND CZM.STATUS='A' WITH UR");
		
		
		psGetZoneFromCityZone= con.prepareStatement("SELECT CM.ZONE_CODE, CZM.CITY_CODE,CZM.CITY_ZONE_CODE FROM CITY_ZONE_MSTR CZM , CITY_MSTR CM WHERE  CM.CITY_CODE=CZM.CITY_CODE and CZM.CITY_ZONE_CODE=? AND  CM.STATUS='A' AND CZM.STATUS='A' WITH UR");
									
		for (Iterator<BulkMstrDTO> itr = listBulkDto.iterator();itr.hasNext();) 
		{
			try
			{
				BulkMstrDTO dto1;
				dto1= (BulkMstrDTO)itr.next();
				
					if(dto1.isErrFlag()){
							if(!(dto1.getActionType().equalsIgnoreCase("c") || dto1.getActionType().equalsIgnoreCase("d") ) )
							{
								dto1.setMessage("Enter either c or d for CREATION/DELETION | ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							
							//if(dto1.getProductLobId().equals("") || dto1.getProductId().equals("") ||  dto1.getCircle().equals("")||  dto1.getZoneCode().equals("")||  dto1.getCitycode().equals("")||  dto1.getCityZoneCode().equals("")||  dto1.getPinCode().equals(""))
								if(dto1.getProductLobId().equals("") || dto1.getProductId().equals("") ||  dto1.getCircle().equals("")||	 dto1.getCitycode().equals(""))
							{
								dto1.setMessage("Product LOB ID and Product ID and Circle ID  and CityCode and CityZoneCode and PinCode are mandatory fields |");
								dto1.setErrFlag(true);
								returnBulkMstrDTO.add(dto1);
								continue;
							}
								
								
							if(dto1.getProductLobId().equals("") || dto1.getCircle().equals(""))
								
							{
								//lob id and circle id cant be blank 
								dto1.setMessage("Product LOB ID  and Circle ID  are mandatory fields |");
								dto1.setErrFlag(true);
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							
							if(dto1.getCitycode().equals("") && dto1.getReqCategory().equals("") && dto1.getCityZoneCode().equals("") && dto1.getPinCode().equals(""))
							 	{
									dto1.setMessage("Atleast one of the fields CityCode , CityZoneCode , PinCode and Request Category  should be entered.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}
					
							
							if(dto1.getProductLobId().length()>5)
							{
								dto1.setMessage("Invalid Product LOB ID length ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							if(dto1.getProductId().length()>5)
							{
								dto1.setMessage("Invalid Product ID length");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							if(dto1.getCircle().length()>20)
							{
								dto1.setMessage("Invalid Circle ID length ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
						/*	if(dto1.getZoneCode().length()>20)
							{
								dto1.setMessage("Invalid Zone Code length ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}*/
							if(dto1.getCitycode().length()>20)
							{
								dto1.setMessage("Invalid CityCode length");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							if(dto1.getCityZoneCode().length()>20)
							{
								dto1.setMessage("Invalid CityZoneCode length ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							if(dto1.getPinCode().length()>6)
							{
								dto1.setMessage("Invalid PinCode length ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							
							
					}
				
					else if(!dto1.isErrFlag() && dto1.getActionType().equalsIgnoreCase("c") )
					{
						logger.info("creating uploadAutoAssignmentMatrix :::::::::::::::::::");
								if(!isValidProdLobId(dto1))
								{
									dto1.setMessage("Invalid Product LOB ID.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}
									
								/*if(!isValidProdId(dto1))
								{
									dto1.setMessage("Invalid Product ID.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}*/
								//if prod id is empty
								if(dto1.getProductId().equals(""))	
									{
									if(!isValidCircle(dto1))
									
										{
											dto1.setMessage("Invalid Circle ID.");
											dto1.setErrFlag(true);
											returnBulkMstrDTO.add(dto1);
											continue;
										}
									}
								else if (!dto1.getProductId().equals(""))	
									{
									//add validation for prod id,lob and circle
										if(!isValidProdLobCircleComb(dto1))
										{
											dto1.setMessage("Invalid Circle, product and lob combination.");
											dto1.setErrFlag(true);
											returnBulkMstrDTO.add(dto1);
											continue;
										}
									}
								logger.info("asa::::::::dto1.getReqCategory()::"+dto1.getReqCategory());
								if(dto1.getReqCategory()!=null)
								//if(!dto1.getReqCategory().equals(""))
								{
									if(!isValidReqCat(dto1))
									{
										logger.info("asa:;bbbb");
										dto1.setMessage("Invalid Request Category and Product Id combination.");
										dto1.setErrFlag(true);
										returnBulkMstrDTO.add(dto1);
										continue;
									}
								}
								if(!chkPin(dto1))
								{
									dto1.setMessage("Invalid combination.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}
								/*
								if(!isValidCityCode(dto1))
								{
									dto1.setMessage("Invalid City Code.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}*/
								
								/*if(!isValidZoneCodeforAsgnmtMtrx(dto1))
								{
									dto1.setMessage("Invalid Zone Code.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}*/
								
								/*if(!isValidCityCodeforAsgnmtMtrx(dto1))
								{
									dto1.setMessage("Invalid City Code.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}*/
								
							/*	if(!isValidCityZoneCodeforAsgnmtMtrx(dto1))
								{
									dto1.setMessage("Invalid CityZone Code.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}*/
								/*if(dto1.getPinCode()!="")
								{
									if(!isValidPinCodeforAsgnmtMtrx(dto1))
									{
										dto1.setMessage("Invalid Pincode.");
										dto1.setErrFlag(true);
										returnBulkMstrDTO.add(dto1);
										continue;
									}
									
									if(!dto1.getCityZoneCode().equals(""))
									{
										if(!isValidCityZoneCodeforAsgnmtMtrx(dto1))
										{
											dto1.setMessage("Invalid City Zone Code and Pin Code Combination.");
											dto1.setErrFlag(true);
											returnBulkMstrDTO.add(dto1);
											continue;
										}
										
										if((!dto1.getCitycode().equals("")) && (!dto1.getCityZoneCode().equals("")))
										{	
											if(!isValidCityCodeforAsgnmtMtrx(dto1))
												{
													dto1.setMessage("Invalid City Code and City Zone Code Combination.");
													dto1.setErrFlag(true);
													returnBulkMstrDTO.add(dto1);
													continue;
												}
										}
										
									}
									
									
								}
								*/
								/*else if(dto1.getPinCode()=="")
								{
									
									if(dto1.getCityZoneCode()!="")
									{
										if(dto1.getCitycode()!="")											
										{	
											if(!isValidCityCodeforAsgnmtMtrx(dto1))
												{
													dto1.setMessage("Invalid City Code and City Zone Code Combination.");
													dto1.setErrFlag(true);
													returnBulkMstrDTO.add(dto1);
													continue;
												}
										
										}
									}
								}
								*/
								
								
								
								
								
							/*	if(isValidAsgnmtMtrxforDelete(dto1))//checking for duplicate entry while inserting
								{
									dto1.setMessage("Record Already Exists.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}*/
								
								//check for duplicate--to be corrected
								
								
								
								
								
								
								
								
								//If pincode is entered , fetching details from the pincode
								if(dto1.getPinCode()!="" && dto1.getCityZoneCode()=="")
									
								{
									psGetDataFromPin.setString(1,dto1.getPinCode());
									
									
									rs=psGetDataFromPin.executeQuery();
									
									while(rs.next())
									{	
									dto1.setZoneCode(rs.getString("ZONE_CODE"));
									dto1.setCitycode(rs.getString("CITY_CODE"));
									dto1.setCityZoneCode(rs.getString("CITY_ZONE_CODE"));
									}
								}
								
								else if(dto1.getPinCode()=="" && dto1.getCityZoneCode()!="")              
									
								{
									
									psGetZoneFromCityZone.setString(1,dto1.getCityZoneCode());
									
									
									rs=psGetZoneFromCityZone.executeQuery();
									
									
									while (rs.next())
									{
									dto1.setZoneCode(rs.getString("ZONE_CODE"));
									dto1.setCitycode(rs.getString("CITY_CODE"));
									dto1.setCityZoneCode(rs.getString("CITY_ZONE_CODE"));
									}
									
								}
								
								
								else if(dto1.getPinCode()!="" && dto1.getCityZoneCode()!="")
								{
									
									if(!isValidCityZoneCodeforPinCode(dto1))									
									{
										dto1.setMessage("City Zone Code Doesnt Exist for Entered PinCode.");
										dto1.setErrFlag(true);
										returnBulkMstrDTO.add(dto1);
										continue;
									}
									else if(isValidCityZoneCodeforPinCode(dto1))
									{
									psGetDataFromPin.setString(1,dto1.getPinCode());
									
									rs=psGetDataFromPin.executeQuery();
									
									while (rs.next())
									{
									dto1.setZoneCode(rs.getString("ZONE_CODE"));
									dto1.setCitycode(rs.getString("CITY_CODE"));
									dto1.setCityZoneCode(rs.getString("CITY_ZONE_CODE"));
									}
									}
									
								}
								
								else if(dto1.getPinCode()=="" && dto1.getCityZoneCode()=="")
								{
									dto1.setPinCode("");
									dto1.setCityZoneCode("");
									

									psGetZoneFromCity.setString(1,dto1.getCitycode());
									rs=psGetZoneFromCity.executeQuery();
									
									while(rs.next())
									{
										dto1.setZoneCode(rs.getString("ZONE_CODE"));
										logger.info("zone code:::::::;"+dto1.getZoneCode());
									}
									
								}
								
								
								
								if(isValidAsgnmtMtrxforDelete(dto1))						//checking if assignment matrix already exists
								{
									dto1.setMessage("Auto Assignment Matrix Already Exists.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}
								
								else if(!isValidAsgnmtMtrxforDelete(dto1))					
								{
								/*prodLobId=Integer.parseInt(dto1.getProductLobId());
								circleId=Integer.parseInt(dto1.getCircle());
								prodId=Integer.parseInt(dto1.getProductId());
								*/
									
									psInsertMatrix.setInt(1,Integer.parseInt(dto1.getProductLobId()));
									//changed by Nancy
									psInsertMatrix.setInt(3,Integer.parseInt(dto1.getCircle()));
									if(dto1.getProductId() !=null && dto1.getProductId().length() >0){
									psInsertMatrix.setInt(2,Integer.parseInt(dto1.getProductId()));
									}else {
										psInsertMatrix.setInt(2,-1);
									}
									//end of changes by Nancy
									psInsertMatrix.setString(4,dto1.getZoneCode());
									psInsertMatrix.setString(5,dto1.getCitycode());
									if(dto1.getCityZoneCode()!="")
									{
									psInsertMatrix.setString(6,dto1.getCityZoneCode());
									}
									else
									{
										psInsertMatrix.setString(6,"");	
									}
									if(dto1.getPinCode()!="")
									{	
										psInsertMatrix.setString(7,dto1.getPinCode());
									}
									else
									{
										psInsertMatrix.setString(7,"");
									}
									psInsertMatrix.setString(8,userBean.getUserLoginId());
									if(dto1.getReqCategory()!="")
									{	
									psInsertMatrix.setString(9,dto1.getReqCategory());
									}
									else
									{
										psInsertMatrix.setString(9,"");	
									}
									psInsertMatrix.setString(10,Constants.AUTO_QUALIFIED_ASSIGNMENT);
									psInsertMatrix.executeUpdate();
								
							dto1.setMessage("Auto Assignment matrix Inserted Successfully.");
							returnBulkMstrDTO.add(dto1);
							continue;
							}
						}
		
				else if(!dto1.isErrFlag() && dto1.getActionType().equalsIgnoreCase("d"))
				{
					

					psGetZoneFromCity.setString(1,dto1.getCitycode());		//fetching zone code from city code entered
					rs=psGetZoneFromCity.executeQuery();
					
					while(rs.next())
					{
						dto1.setZoneCode(rs.getString("ZONE_CODE"));
					}
					
					if(dto1.getPinCode()!="")								//fetching city zone code from entered pin code
					{
						psGetDataFromPin.setString(1,dto1.getPinCode());
						
						rs1=psGetDataFromPin.executeQuery();
						
						while(rs1.next())
						{	
							dto1.setCityZoneCode(rs1.getString("CITY_ZONE_CODE"));
						}
						
					}
					
					if(!isValidAsgnmtMtrxforDelete(dto1))
					{
						dto1.setMessage(" Auto Assignment Matrix Doesnt Exists.");
						dto1.setErrFlag(true);
						returnBulkMstrDTO.add(dto1);
						continue;
					}
					
					else if(isValidAsgnmtMtrxforDelete(dto1))
					{

						prodLobId=Integer.parseInt(dto1.getProductLobId());
						circleId=Integer.parseInt(dto1.getCircle());
						prodId=Integer.parseInt(dto1.getProductId());
						
						
						psDeleteMatrix.setString(1,userBean.getUserLoginId());
						psDeleteMatrix.setInt(2, prodLobId);
						psDeleteMatrix.setInt(3, prodId);
						psDeleteMatrix.setInt(4, circleId);
						psDeleteMatrix.setString(5, dto1.getZoneCode().toUpperCase());
						psDeleteMatrix.setString(6,dto1.getCitycode().toUpperCase());
						if(dto1.getCitycode()!="")
						{	
						psDeleteMatrix.setString(7,dto1.getCityZoneCode().toUpperCase());
						}
						else
						{
							psDeleteMatrix.setString(7,"");
						}
						if(dto1.getPinCode()!="")
						{
						psDeleteMatrix.setString(8,dto1.getPinCode().toUpperCase());
						}
						else
						{
							psDeleteMatrix.setString(8,"");
						}
						
						psInsertMatrix.setString(9,Constants.AUTO_QUALIFIED_ASSIGNMENT);
						
						psDeleteMatrix.executeUpdate();
			
							bulkMstrDTO = new BulkMstrDTO();
							
							dto1.setMessage("Assignment Matrix deleted successfully");
							
							returnBulkMstrDTO.add(dto1);
							continue;
							
					}
					else
					{
						dto1.setMessage("Assignment Matrix could not be deleted.");
						returnBulkMstrDTO.add(dto1);
						continue;
					}
				}
					
				//}
		}	
		
		catch(Exception e)
		{
			BulkMstrDTO bulkDto= new BulkMstrDTO();
			bulkDto.setMessage("Assignment Matrix cannot be uploaded.");
			returnBulkMstrDTO.add(bulkDto);
			e.printStackTrace();
		}

		} // END Iterator

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, psInsertMatrix, null);
			DBConnection.releaseResources(con, psDeleteMatrix, null);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	return returnBulkMstrDTO;}
	
	
	public ArrayList<BulkMstrDTO> uploadState(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) throws DAOException
	{
	
	//insert state starts here
	Connection con = null;
	PreparedStatement ps = null;
	PreparedStatement ps1 = null;
	PreparedStatement ps2 = null;
	PreparedStatement ps3 = null;
	//PreparedStatement ps1 = null;
	BulkMstrDTO dto = null;
	ResultSet rs1=null;
	ResultSet rs2=null;
	int circleId=0;
	int lobId=0;
	String stateCode="";
	ArrayList<BulkMstrDTO> returnBulkMstrDTO = new ArrayList<BulkMstrDTO>();
	
	
	try {
		BulkMstrDTO bulkMstrDTO = new BulkMstrDTO();
		CircleDTO circleDTO= new CircleDTO();
		con = DBConnection.getDBConnection();
		
		ps1 =con.prepareStatement("select STATE_ID,STATE_CODE from STATE_MSTR order by STATE_CODE desc FETCH FIRST 1 ROW ONLY with ur");
		//fetching last state code and state id
		
		
		ps = con.prepareStatement("INSERT INTO STATE_MSTR(STATE_ID, STATE_CODE, STATE_NAME,STATE_DESC,STATUS, UPDATED_TIME, UPDATED_BY,CIRCLE_MSTR_ID) "+
				 " (select max(STATE_ID)+1,?,?,?,'A',current timestamp,?,(select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID= ? and LOB_ID=? and STATUS='A')from STATE_MSTR) WITH UR");
		

		ps3=con.prepareStatement("SELECT * FROM STATE_MSTR WHERE STATE_NAME =?  AND CIRCLE_MSTR_ID="+
			"(select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID=? and LOB_ID=? AND STATUS = 'A') AND STATUS = 'A' WITH UR ");
		
		ps2 = con.prepareStatement("UPDATE STATE_MSTR SET STATUS='D',"+
				 "UPDATED_TIME= CURRENT TIMESTAMP, UPDATED_BY= ? "+
				 "WHERE STATE_NAME = ?  AND CIRCLE_MSTR_ID=(select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID=? and LOB_ID=?)   AND STATUS = 'A' WITH UR");
		
				
		for (Iterator<BulkMstrDTO> itr = listBulkDto.iterator();itr.hasNext();) 
		{
			try
			{
				BulkMstrDTO dto1;
				dto1= (BulkMstrDTO)itr.next();
				
					if(dto1.isErrFlag()){
							if(!(dto1.getActionType().equalsIgnoreCase("c") || dto1.getActionType().equalsIgnoreCase("d") ) )
							{
								dto1.setMessage("Enter either c or d for CREATION/DELETION | ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							
							if(dto1.getProductLobId().equals("") || dto1.getCircle().equals("") || dto1.getState().equals(""))
							{
								dto1.setMessage("PRODUCT_LOB_ID CIRCLE_ID and STATE are mandatory fields |");
								dto1.setErrFlag(true);
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							if(dto1.getProductLobId().length()>Constants.MAX_LENGTH)
							{
								dto1.setMessage("Invalid Product Lob Id Length.");
								dto1.setErrFlag(true);
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							if(dto1.getCircle().length()> Constants.MAX_LENGTH)
							{
								dto1.setMessage("Invalid Circle Id Length.");
								dto1.setErrFlag(true);
								returnBulkMstrDTO.add(dto1);
								continue;
							}/*
							if(dto1.getZoneName().length()>Constants.MAX_ZONE_NAME_LENGTH)//MAX_ZONE_NAME_LENGTH=30
							{
								dto1.setMessage("Zone Name should be of 30 Characters or less.");
								dto1.setErrFlag(true);
								returnBulkMstrDTO.add(dto1);
								continue;	
							}*/
					}
				
					else if(!dto1.isErrFlag() && dto1.getActionType().equalsIgnoreCase("c") )
					{
								if(!isValidCircle(dto1))
								{	
									dto1.setMessage("Invalid Circle Id and Product Lob id combination.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}
									
								if(isDuplicateState(dto1))
								{
									dto1.setMessage("State Name Already Exists for given Circle Id and Product Lob id combination.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}
								
								rs1=ps1.executeQuery();
								//fetching last zone code and zone id
								if(rs1.next())
								{	
									circleDTO.setStateId(rs1.getInt("STATE_ID"));
									circleDTO.setStateCode(rs1.getString("STATE_CODE"));
									
								}
								
								String initialStateChar="S";
																	
								
								String StringStateChar = "";
								int intStateCode = 0;
								for(int i = 1; i< circleDTO.getStateCode().length(); i++ ){
									try{
										intStateCode=Integer.parseInt((circleDTO.getStateCode()).substring(i,circleDTO.getStateCode().length() ));

										logger.info("intStateCode:::::::::::"+intStateCode);
										logger.info("asa:::::i"+i);

										StringStateChar = circleDTO.getStateCode().substring(0 , i);
										logger.info("StringStateChar:::::"+StringStateChar);
										logger.info("circleDTO.getStateCode()::::::"+circleDTO.getStateCode());
										break;
									}
									catch(Exception e){
										continue;
									}
								}
								if(!StringStateChar.equals("")){
									initialStateChar = StringStateChar;
								}
								/* End of changes by parnika */
								
								int maxStateNumber=intStateCode;
															
								if(maxStateNumber<9)
								{
									maxStateNumber++;
									stateCode=initialStateChar+"0000"+maxStateNumber;
								}
								else if(maxStateNumber >=9 && maxStateNumber <99  )
								{
									maxStateNumber++;
									stateCode=initialStateChar+"000"+maxStateNumber;
								}
								else if(maxStateNumber >=99 && maxStateNumber <999 )
								{
									maxStateNumber++;
									stateCode=initialStateChar+"00"+maxStateNumber;
								}
								else if(maxStateNumber >=999 && maxStateNumber <9999 )
								{
									maxStateNumber++;
									stateCode=initialStateChar+"0"+maxStateNumber;
								}
								else if(maxStateNumber >=9999 && maxStateNumber <99999)
								{
									maxStateNumber++;
									stateCode=initialStateChar+maxStateNumber;
								}
								logger.info("StateCode::::::::::::::::::::::::::::"+stateCode);
								// end of genrating state code 
							 
							circleId=	Integer.parseInt(dto1.getCircle());
							lobId=Integer.parseInt(dto1.getProductLobId());
							ps.setString(1,stateCode);
							ps.setString(2,dto1.getState().toUpperCase());
							ps.setString(3,dto1.getState().toUpperCase());
							ps.setString(4,userBean.getUserLoginId());
							ps.setInt(5,circleId);
							ps.setInt(6,lobId);
							
							ps.executeUpdate();
							dto1.setStateCode(stateCode);
							dto1.setMessage("State Created Successfully.");
							returnBulkMstrDTO.add(dto1);
							continue;
						}
		
				else if(!dto1.isErrFlag() && dto1.getActionType().equalsIgnoreCase("d"))
				{
					
					circleId=	Integer.parseInt(dto1.getCircle());
					lobId=Integer.parseInt(dto1.getProductLobId());

					ps3.setString(1,dto1.getState().toUpperCase());
					ps3.setInt(2,circleId);
					ps3.setInt(3,lobId);
					
					rs2=ps3.executeQuery();
					
					
					if(rs2.next())
					{

						ps2.setString(1,userBean.getUserLoginId());
						ps2.setString(2,dto1.getState().toUpperCase());
						ps2.setInt(3,circleId);
						ps2.setInt(4,lobId);
							
						ps2.executeUpdate();
							
			
							bulkMstrDTO = new BulkMstrDTO();
							
							dto1.setMessage("State deleted successfully");
							dto1.setZoneCode("State Code has been deleted");
							
							returnBulkMstrDTO.add(dto1);
							continue;
							
							}
					else
					{
						dto1.setMessage("State not deleted.");
						returnBulkMstrDTO.add(dto1);
						continue;
					}
				}
				//}
		}	
		
		catch(Exception e)
		{
			BulkMstrDTO bulkDto= new BulkMstrDTO();
			bulkDto.setMessage("State not created");
			bulkDto.setZoneCode("State Code not generated.");
			returnBulkMstrDTO.add(bulkDto);
			e.printStackTrace();
		}

		} // END Iterator

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs1);
			DBConnection.releaseResources(con, ps1, rs2);
			DBConnection.releaseResources(con, ps2, null);
			DBConnection.releaseResources(con, ps3, null);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return returnBulkMstrDTO;
	
	//insert State ends here
}
	
	
	
	public boolean isValidCircle(BulkMstrDTO bulkDto) throws DAOException {
		//for checking if circle exists while uploading zone
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		
		String validProductLob="";
		String validCircle="";
		int circleId;
		int lobId;
		String validCircleLob="SELECT * FROM CIRCLE_MSTR where CIRCLE_ID=? and LOB_ID=? and STATUS='A'";

		try {
			con = DBConnection.getDBConnection();
			
			circleId=	Integer.parseInt(bulkDto.getCircle());
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
			e.printStackTrace();
//			throw new DAOException("Exception occured in validate method :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return isExist;
	}
	
	public boolean isValidZoneCode(BulkMstrDTO bulkDto) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		
		String zoneCode="";
		int circleId;
		int lobId;
		String validZoneCode="";

		try {
			con = DBConnection.getDBConnection();
			validZoneCode="SELECT * FROM ZONE_MSTR WHERE ZONE_CODE=? and STATUS='A'";
			zoneCode=bulkDto.getZoneCode().toUpperCase();
			
			logger.info("zoneCode:::::::::::::::"+zoneCode);
			
			ps = con.prepareStatement(validZoneCode);
			
			ps.setString(1, zoneCode);
			
			
			rs=ps.executeQuery();
			
			if(rs==null)
			{
				logger.info("Invalid Zone Code");
				return isExist;
			}
			
			else if(rs.next())
			{	
				isExist=true;
				return isExist;
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
			//throw new DAOException("Exception occured in validate method :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return isExist;
	
	}

	
	public boolean isDuplicateZone(BulkMstrDTO dto1) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		
		int circleId;
		int lobId;
		String zoneName;
		String duplicateZone="SELECT * FROM ZONE_MSTR WHERE ZONE_NAME=? and CIRCLE_MSTR_ID="+
							  "(SELECT CIRCLE_MSTR_ID FROM CIRCLE_MSTR WHERE  CIRCLE_ID=? AND LOB_ID=? AND STATUS='A') AND STATUS='A' WITH UR";

		try {
			con = DBConnection.getDBConnection();
			
			circleId=	Integer.parseInt(dto1.getCircle());
			lobId=Integer.parseInt(dto1.getProductLobId());
			zoneName=dto1.getZoneName().toUpperCase();
			ps = con.prepareStatement(duplicateZone);
			
			ps.setString(1,zoneName);
			ps.setInt(2, circleId);
			ps.setInt(3, lobId);
			
			rs=ps.executeQuery();
			

			
			if(rs==null)
			{
				logger.info("No duplicate zone");
				isExist=false;
			}
			
			else if(rs.next())
			{	
				logger.info("duplicate zone exists");
				isExist=true;
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			//throw new DAOException("Exception occured in duplicateZone method :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return isExist;
	}
	
	
	public boolean isDuplicateCity(BulkMstrDTO bulkDto)throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		
		String zoneCode;
		String cityName;
		String duplicateCity="";

		try {
			con = DBConnection.getDBConnection();
			//duplicateCity="SELECT * FROM CITY_MSTR WHERE upper(ZONE_CODE)=? and upper(CITY_NAME)=? and STATUS='A' with ur";
			
			
			duplicateCity="SELECT * FROM CITY_MSTR WHERE ZONE_CODE in (select ZONE_CODE from ZONE_MSTR where CIRCLE_MSTR_ID in (SELECT CIRCLE_MSTR_ID FROM ZONE_MSTR WHERE ZONE_CODE= ?)) "+
			" and CITY_NAME =? and STATUS='A' WITH UR";
			//validation changed from zone to circle
			zoneCode=bulkDto.getZoneCode().toUpperCase();
			cityName=bulkDto.getCityName().toUpperCase();

			ps = con.prepareStatement(duplicateCity);
			ps.setString(1,zoneCode);
			ps.setString(2,cityName);
			rs=ps.executeQuery();
			
			if(rs==null)
			{
				logger.info("No duplicate city");
				isExist=false;
			}
			
			else if(rs.next())
			{	
				logger.info("duplicate city exists");
				isExist=true;
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
	
	
	
	
	
	public boolean isDuplicatePinCode(BulkMstrDTO dto1) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		boolean isExist=false;
		
		String pinCode="";
		/*String duplicatePinCode="SELECT * FROM PINCODE_MSTR WHERE PINCODE=? and STATUS='A'" +
				" and CITY_ZONE_CODE in(SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE CITY_CODE in (SELECT CITY_CODE FROM CITY_MSTR WHERE ZONE_CODE in (SELECT ZONE_CODE FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in(SELECT CIRCLE_MSTR_ID FROM CIRCLE_MSTR WHERE LOB_ID=?)))) " +
				"WITH UR";*/
		
		String duplicatePinCode="select pm.pincode, cm.lob_id  from PINCODE_MSTR pm, CITY_ZONE_MSTR czm, CITY_MSTR cim, ZONE_MSTR zm, CIRCLE_MSTR cm"+
								" where pm.CITY_ZONE_CODE = czm.CITY_ZONE_CODE and czm.CITY_CODE = cim.city_code and cim.ZONE_CODE = zm.ZONE_CODE"+
								" and zm.CIRCLE_MSTR_ID = cm.CIRCLE_MSTR_ID and pm.PINCODE = ? WITH UR";
		
		
		String duplicatePinCode1="SELECT pm.PINCODE, clm.LOB_ID FROM PINCODE_MSTR pm, CITY_ZONE_MSTR czm, CITY_MSTR cm, ZONE_MSTR zm, CIRCLE_MSTR clm "+
								"WHERE pm.PINCODE=? and pm.CITY_ZONE_CODE=czm.CITY_ZONE_CODE and czm.CITY_CODE=cm.CITY_CODE "+ 
								"and cm.ZONE_CODE=zm.ZONE_CODE and zm.CIRCLE_MSTR_ID=clm.CIRCLE_MSTR_ID and clm.LOB_ID=? "+ 
								"and pm.CITY_ZONE_CODE=? and pm.STATUS='A' WITH UR";

		try {
			con = DBConnection.getDBConnection();
			
			pinCode=dto1.getPinCode();
			logger.info("pinCode"+pinCode);
			
			ps = con.prepareStatement(duplicatePinCode);
			
			ps.setString(1,pinCode);
		//	ps.setInt(2,Integer.parseInt(dto1.getProductLobId()));

			rs=ps.executeQuery();
			
/*			if(rs==null)
			{
				logger.info("asa1111111111133333");
				isExist=false;
			}
			
			else  //if(rs.next())
			{	
*/  
			//				logger.info("asa11111111111444444");
//				logger.info("duplicate PinCode exists");
				//isExist=true;
			
				 
				while(rs.next())
				{
					int lob;
					dto1.setProductLobId(rs.getString("LOB_ID"));
					lob=Integer.parseInt(dto1.getProductLobId());
					
			
					ps1 = con.prepareStatement(duplicatePinCode1);
					
					ps1.setString(1,pinCode);
					//ps1.setInt(2,dto1.getProductLobId());
					ps1.setString(2,dto1.getProductLobId());
					ps1.setString(3,dto1.getCityZoneCode());
					
					rs1=ps1.executeQuery();
					/*
					if(rs1==null)
					{
						logger.info("asa11111111111666666");
						isExist=false;
						//continue;
					}
					
					else */
					if(rs1.next())
						//while(rs1.next())
					{
						logger.info("duplicate PinCode exists");
						isExist=true;
						//continue;
					}
					rs1.close();
					rs1=null;
					if(isExist)
						break;
					
				}

			//}
				
			

		} catch (Exception e) {
			e.printStackTrace();
			//throw new DAOException("Exception occured in duplicateZone method :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps1, rs1);
				DBConnection.releaseResources(null, ps, rs);
				
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return isExist;
	}
	
	
	

	public boolean isDuplicateRSUCode(BulkMstrDTO dto1) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		
		String rsuCode="";
		String duplicatePinCode="SELECT * FROM RSU_MSTR RM, CITY_ZONE_MSTR CZM, CITY_MSTR CIM, ZONE_MSTR ZM, CIRCLE_MSTR CM " +
				"WHERE RM.CITY_ZONE_CODE = CZM.CITY_ZONE_CODE AND CZM.CITY_CODE = CIM.CITY_CODE AND " +
				"CIM.ZONE_CODE = ZM.ZONE_CODE AND ZM.CIRCLE_MSTR_ID = CM.CIRCLE_MSTR_ID AND RM.RSU_CODE = ? " +
				"AND CM.CIRCLE_MSTR_ID = (SELECT CIRCLE_MSTR_ID FROM ZONE_MSTR WHERE ZONE_CODE = (SELECT ZONE_CODE " +
				"FROM CITY_MSTR WHERE CITY_CODE = (SELECT CITY_CODE FROM CITY_ZONE_MSTR WHERE CITY_ZONE_CODE = ? ))) AND RM.STATUS = 'A' WITH UR";

		try {
			con = DBConnection.getDBConnection();
			
			ps = con.prepareStatement(duplicatePinCode);
			
			ps.setString(1,dto1.getRsuCode());
			ps.setString(2,dto1.getCityZoneCode());
			
			logger.info("RSUCode"+dto1.getRsuCode());
			logger.info("RSUCode"+dto1.getRsuCode());
			
			rs=ps.executeQuery();
			
			if(rs==null)
			{
				logger.info("No duplicate RSU Code");
				isExist=false;
			}
			
			else if(rs.next())
			{	
				logger.info("duplicate RSU Code exists");
				isExist=true;
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
	
	
	
	public boolean isValidCityCode(BulkMstrDTO bulkDto) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		
		String validCityCode="";
		String cityCode="";
		String cityZoneName="";

		try {
			
			con = DBConnection.getDBConnection();
			
			cityCode=bulkDto.getCitycode();
			cityZoneName=bulkDto.getCityZoneName();
			
			validCityCode="SELECT * FROM CITY_MSTR WHERE CITY_CODE=? and STATUS='A'";
			
			ps = con.prepareStatement(validCityCode);
			
			ps.setString(1, cityCode);
			
			
			rs=ps.executeQuery();
			

			
			if(rs==null)
			{
				logger.info("Invalid Zone Code");
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


	
	public boolean isDuplicateCityZone(BulkMstrDTO bulkDto)throws DAOException
	{
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		
		int circleId;
		int lobId;
		String cityCode;
		String cityZoneName;
		String duplicateCityZone="";
		duplicateCityZone="SELECT * FROM CITY_ZONE_MSTR WHERE upper(CITY_CODE)=? and upper(CITY_ZONE_NAME)=? and STATUS='A' ";

		try {
			con = DBConnection.getDBConnection();
			
			
			
			cityCode=bulkDto.getCitycode().toUpperCase();
			cityZoneName=bulkDto.getCityZoneName().toUpperCase();
			
			ps = con.prepareStatement(duplicateCityZone);
			
			ps.setString(1,cityCode);
			ps.setString(2,cityZoneName);
			
			
			rs=ps.executeQuery();
			

			if(rs==null)
			{
				logger.info("No duplicate CityName");
				isExist=false;
				return isExist;
				
			}
			
			else if(rs.next())
			{	
				logger.info("duplicate City exists");
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
	
	
	
	public boolean isValidCityZoneCode(BulkMstrDTO bulkDto) throws DAOException {
		
		//checking if cityCode entered exists or not for PinCode and RSUCode upload.
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		
		String validcityZoneCode="";
		
		String checkCityZoneCode="SELECT * FROM CITY_ZONE_MSTR WHERE CITY_ZONE_CODE=? and STATUS='A'";

		try {
			con = DBConnection.getDBConnection();
			
			
			
			validcityZoneCode=bulkDto.getCityZoneCode();
			
			ps = con.prepareStatement(checkCityZoneCode);
			
			ps.setString(1, validcityZoneCode);
			
			
			rs=ps.executeQuery();
			

			
			if(rs==null)
			{
				logger.info("CityZone Code doesnt exist");
				return isExist;
			}
			
			else if(rs.next())
			{	
				logger.info("CityZone Code exists");
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

	
public boolean isValidProdLobId(BulkMstrDTO bulkDto) throws DAOException {
		
		//checking if cityCode entered exists or not for PinCode and RSUCode upload.
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		
		String validProdLobId="";
		
		String checkProdLobId="SELECT * FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID=? and STATUS='A' ";

		try {
			con = DBConnection.getDBConnection();
			
			
			
			validProdLobId=bulkDto.getProductLobId();
			logger.info("Product Lob Id::::"+validProdLobId);
			
			ps = con.prepareStatement(checkProdLobId);
			
			ps.setString(1, validProdLobId);
			
			
			rs=ps.executeQuery();
			

			
			if(rs==null)
			{
				logger.info("Product Lob ID doesnt exist");
				return isExist;
			}
			
			else if(rs.next())
			{	
				logger.info("Product Lob ID exists");
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

public boolean isValidProdAndRequestId(BulkMstrDTO bulkDto)
{
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String query = "Select * from REQUEST_CATEGORY_MSTR where PRODUCT_ID = ? and REQUEST_CATEGORY_ID = ? with ur";
	try
	{
		con = DBConnection.getDBConnection();
		ps = con.prepareStatement(query);
		
		ps.setString(1, bulkDto.getProductId());
		ps.setString(2, bulkDto.getReqCategory());
		
		rs=ps.executeQuery();

		while(rs.next())
		{
			isExist = true;
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	finally
	{
		try
		{
			DBConnection.releaseResources(con, ps, rs);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	return isExist;
}

public boolean isValidProdId(BulkMstrDTO bulkDto) throws DAOException {
		
		//checking if cityCode entered exists or not for PinCode and RSUCode upload.
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		
		int validProdLobId=0;
		int validProdId=0;
		
		String checkProdLobId="SELECT * FROM PRODUCT_MSTR WHERE PRODUCT_ID=? and PRODUCT_LOB_ID=? and STATUS='A' ";

		try {
			con = DBConnection.getDBConnection();
			/*
			
			
			validProdLobId=Integer.parseInt(bulkDto.getProductLobId());
			validProdId=Integer.parseInt(bulkDto.getProductId());
			logger.info("Product Lob Id::::"+validProdLobId);
			logger.info("Product Id::::"+validProdId);
			*/
			ps = con.prepareStatement(checkProdLobId);
			
			
			if(!bulkDto.getProductId().equals(""))
						ps.setInt(1, Integer.parseInt(bulkDto.getProductId()));
			else
						ps.setInt(1,0);
			
			if(!bulkDto.getProductLobId().equals(""))
				ps.setInt(2, Integer.parseInt(bulkDto.getProductLobId()));
			else
				ps.setInt(2,0);
			
			
			rs=ps.executeQuery();
			

			
			if(rs==null)
			{
				logger.info("Product ID doesnt exist");
				return isExist;
			}
			
			else if(rs.next())
			{	
				logger.info("Product ID exists");
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


public boolean isValidZoneCodeforAsgnmtMtrx(BulkMstrDTO bulkDto) throws DAOException {
	
	//checking if cityCode entered exists or not for PinCode and RSUCode upload.
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
		logger.info("validZoneCode::::"+validZoneCode);
		logger.info("circleId::::"+circleId);
		logger.info("lobId::::"+lobId);
		
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

/*
public boolean isValidCityCodeforAsgnmtMtrx(BulkMstrDTO bulkDto) throws DAOException {
	
	//checking if cityCode entered exists or not for PinCode and RSUCode upload.
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String validCityCode="";
	String ZoneCode="";
	
	String checkZoneCode="SELECT * FROM CITY_MSTR WHERE CITY_CODE=? and ZONE_CODE=? and STATUS='A' WITH UR";

	try {
		con = DBConnection.getDBConnection();
		
		
		validCityCode=bulkDto.getCitycode().toUpperCase();
		ZoneCode=bulkDto.getZoneCode().toUpperCase();
		logger.info("validCityCode::::"+validCityCode);
		logger.info("ZoneCode::::"+ZoneCode);
		
		ps = con.prepareStatement(checkZoneCode);
		
		ps.setString(1, validCityCode);
		ps.setString(2, ZoneCode);
		
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
}*/






public boolean isValidCityCodeforAsgnmtMtrx(BulkMstrDTO bulkDto) throws DAOException {
	
	//checking if cityCode entered exists or not for PinCode and RSUCode upload.
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String checkCityZoneCode="SELECT * FROM CITY_ZONE_MSTR WHERE CITY_ZONE_CODE=? and CITY_CODE=? and STATUS='A' WITH UR";

	try {
		con = DBConnection.getDBConnection();
		
		
		ps = con.prepareStatement(checkCityZoneCode);
		
		ps.setString(1, bulkDto.getCityZoneCode().toUpperCase());
		ps.setString(2, bulkDto.getCitycode().toUpperCase());
		
		rs=ps.executeQuery();
		

		
		if(rs==null)
		{
			logger.info("City Zone Code doesnt exist");
			return isExist;
		}
		
		else if(rs.next())
		{	
			logger.info("City Zone Code exists");
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


/*
public boolean isValidCityZoneCodeforAsgnmtMtrx(BulkMstrDTO bulkDto) throws DAOException {
	
	//checking if cityCode entered exists or not for PinCode and RSUCode upload.
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String validCityZoneCode="";
	String CityCode="";
	
	String checkCityZoneCode="SELECT * FROM CITY_ZONE_MSTR WHERE CITY_ZONE_CODE=? and CITY_CODE=? and STATUS='A' WITH UR";

	try {
		con = DBConnection.getDBConnection();
		
		
		validCityZoneCode=bulkDto.getCityZoneCode().toUpperCase();
		CityCode=bulkDto.getCitycode().toUpperCase();
		logger.info("validCityZoneCode::::"+validCityZoneCode);
		logger.info("CityCode::::"+CityCode);
		
		ps = con.prepareStatement(checkCityZoneCode);
		
		ps.setString(1, validCityZoneCode);
		ps.setString(2, CityCode);
		
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
}*/


public boolean isValidCityZoneCodeforAsgnmtMtrx(BulkMstrDTO bulkDto) throws DAOException {
	
	//checking if cityCode entered exists or not for PinCode and RSUCode upload.
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String cityZoneCode="";
	String CityCode="";
	
	String checkCityZoneCode="SELECT * FROM PINCODE_MSTR WHERE CITY_ZONE_CODE=? and PINCODE=? and STATUS='A' WITH UR";

	try {
		con = DBConnection.getDBConnection();
		
		
		cityZoneCode=bulkDto.getCityZoneCode().toUpperCase();
		logger.info("cityZoneCode::::"+cityZoneCode);
		
		ps = con.prepareStatement(checkCityZoneCode);
		
		ps.setString(1, cityZoneCode);
		ps.setString(2, bulkDto.getPinCode());
		
		rs=ps.executeQuery();
		

		
		if(rs==null)
		{
			logger.info("PinCode doesnt exist");
			return isExist;
		}
		
		else if(rs.next())
		{	
			logger.info("PinCode Code exists");
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


public boolean isValidPinCodeforAsgnmtMtrx(BulkMstrDTO bulkDto) throws DAOException {
	
	//checking if cityCode entered exists or not for PinCode and RSUCode upload.
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String pinCode="";
	String CityCode="";
	
	String checkPinCode="SELECT * FROM PINCODE_MSTR WHERE PINCODE=? and STATUS='A' WITH UR";

	try {
		con = DBConnection.getDBConnection();
		
		
		pinCode=bulkDto.getPinCode();
		logger.info("cityZoneCode::::"+pinCode);
		
		ps = con.prepareStatement(checkPinCode);
		
		ps.setString(1, pinCode);
		
		rs=ps.executeQuery();
		

		
		if(rs==null)
		{
			logger.info("PinCode doesnt exist");
			return isExist;
		}
		
		else if(rs.next())
		{	
			logger.info("PinCode Code exists");
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
public boolean isValidAsgnmtMtrxforDelete(BulkMstrDTO bulkDto) throws DAOException {
	
	logger.info("inside isValidAsgnmtMtrxforDelete");
	
	//checking if cityCode entered exists or not for PinCode and RSUCode upload.
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
	int pinCode;
	
	String checkCityZoneCode="SELECT * FROM AUTO_ASSIGNMENT_MATRIX WHERE LOB_ID=? and PRODUCT_ID=? and CIRCLE_ID=? and ZONE_CODE=? and CITY_CODE=? and CITY_ZONE_CODE=? and "+
							 "PINCODE=? and  STATUS='A' and AUTO_ASSIGNMENT_TYPE=? WITH UR";

	String checkCityZoneCode1="SELECT * FROM AUTO_ASSIGNMENT_MATRIX WHERE LOB_ID=?  and CIRCLE_ID=? and ZONE_CODE=? and CITY_CODE=? and CITY_ZONE_CODE=? and "+
	 "PINCODE=? and  STATUS='A' and AUTO_ASSIGNMENT_TYPE=? WITH UR";

	
	try {
		con = DBConnection.getDBConnection();
		/*
		lobId=Integer.parseInt(bulkDto.getProductLobId());
		prodId=Integer.parseInt(bulkDto.getProductId());
		circleId=Integer.parseInt(bulkDto.getCircle());
		zoneCode=bulkDto.getZoneCode().toUpperCase();
		cityCode=bulkDto.getCitycode().toUpperCase();
		cityZoneCode=bulkDto.getCityZoneCode().toUpperCase();
		//pinCode=Integer.parseInt(bulkDto.getPinCode());
		logger.info("checking if assignmnt matrix exists for deletion::::::::::::::::::");
		logger.info("lobId::::"+lobId);
		logger.info("prodId::::"+prodId);
		logger.info("circleId::::"+circleId);
		logger.info("zoneCode::::"+zoneCode);
		logger.info("cityCode::::"+cityCode);
		logger.info("cityZoneCode::::"+cityZoneCode);
		//logger.info("pinCode::::"+pinCode);
*/		
		
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
					//ps.setInt(7, pinCode);
					if(bulkDto.getPinCode()!="")
					{	
						ps.setString(7, bulkDto.getPinCode());
					}
					else
					{
						ps.setString(7,"");
					}
					ps.setString(8, Constants.AUTO_QUALIFIED_ASSIGNMENT);
					
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
			//ps.setInt(7, pinCode);
			if(bulkDto.getPinCode()!="")
			{	
				ps.setString(6, bulkDto.getPinCode());
			}
			else
			{
				ps.setString(6,"");
			}
			ps.setString(7, Constants.AUTO_QUALIFIED_ASSIGNMENT);
			
		}
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





public boolean isValidCityZoneCodeforPinCode(BulkMstrDTO bulkDto) throws DAOException {
	
	//checking if cityCode entered exists or not for PinCode and RSUCode upload.
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String pinCode="";
	String cityZoneCode="";
	
	String checkCityZoneCode="SELECT * FROM PINCODE_MSTR WHERE PINCODE=? and  CITY_ZONE_CODE=? and STATUS='A' WITH UR";

	try {
		con = DBConnection.getDBConnection();
		
		
		pinCode=bulkDto.getPinCode();
		cityZoneCode=bulkDto.getCityZoneCode().toUpperCase();
		logger.info("pinCode::::"+pinCode);
		logger.info("cityZoneCode::::"+cityZoneCode);
		
		ps = con.prepareStatement(checkCityZoneCode);
		
		ps.setString(1, pinCode);
		ps.setString(2, cityZoneCode);
		
		rs=ps.executeQuery();
		

		
		if(rs==null)
		{
			logger.info("City Zone Code and pincode doesnt exist");
			return isExist;
		}
		
		else if(rs.next())
		{	
			logger.info("City Zone  Code and pincode exists");
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
//Added By Bhaskar

public ArrayList<BulkMstrDTO> uploadChannelPartner(ArrayList<BulkMstrDTO> listBulkDto, UserMstr userBean) throws DAOException {
	
	Connection con = null;
	PreparedStatement ps = null;
	PreparedStatement ps2 = null;
	PreparedStatement ps3 = null;
	BulkMstrDTO dto = null;
	
	ResultSet rs2=null;
	String channelPartnerId="";
	String mobileNumber="";

	ArrayList<BulkMstrDTO> returnBulkMstrDTO = new ArrayList<BulkMstrDTO>();
	
	try {
		BulkMstrDTO bulkMstrDTO = new BulkMstrDTO();
		
		con = DBConnection.getDBConnection();
		
		
		ps = con.prepareStatement("INSERT INTO USER_MSTR_ADDITIONAL(USER_LOGIN_ID, USER_MOBILE_NUMBER, STATUS, UPDATE_TIME, UPDATED_BY) values(?,?,'A',CURRENT TIMESTAMP,?)");
		ps3=con.prepareStatement("SELECT * FROM USER_MSTR_ADDITIONAL WHERE USER_LOGIN_ID=? and USER_MOBILE_NUMBER=? and STATUS='A' WITH UR ");
		ps2 = con.prepareStatement("UPDATE USER_MSTR_ADDITIONAL SET STATUS='D', UPDATE_TIME= CURRENT TIMESTAMP , UPDATED_BY=?"+
								 "WHERE USER_LOGIN_ID=? AND USER_MOBILE_NUMBER=? AND STATUS='A' WITH UR");
		
				
		
		for (Iterator<BulkMstrDTO> itr = listBulkDto.iterator();itr.hasNext();) 
		{
			try
			{
				dto = (BulkMstrDTO) itr.next();	
				channelPartnerId=dto.getChannelPartnerId();
				mobileNumber=dto.getMobileNumber();
				
				
				if(dto.isErrFlag()){
					
					
					if(!(dto.getActionType().equalsIgnoreCase("c") || dto.getActionType().equalsIgnoreCase("d")))
					{
						//errMsg.append("Enter either c or d for CREATION/DELETION | ");
						dto.setMessage("Enter either c or d for CREATION/DELETION | ");
						returnBulkMstrDTO.add(dto);
					}
					

					if(dto.getChannelPartnerId().equals("") || dto.getMobileNumber().equals(""))
					{
						dto.setMessage("OLM_ID  and MOBILE NUMBER are mandatory fields |");
						dto.setErrFlag(true);
						returnBulkMstrDTO.add(dto);
					}
			
				}
				
				
				else if(!dto.isErrFlag() && dto.getActionType().equalsIgnoreCase("c") )
				{
					
					if(!isValidUserLoginId(dto))
					{
						dto.setMessage("Invalid Channel Partner Id.");
						dto.setErrFlag(true);
						returnBulkMstrDTO.add(dto);
						continue;
					}
						
					if(isDuplicateChannelPartner(dto))
					{
						dto.setMessage("Already Exists.");
						dto.setErrFlag(true);
						returnBulkMstrDTO.add(dto);
						continue;
					}
					if(!dto.getMobileNumber().equals(""))
					{
						if(dto.getMobileNumber().length() != 10)
						{
							dto.setMessage("MOBILE_NO should be of 10 digits only |");
							dto.setErrFlag(true);
							returnBulkMstrDTO.add(dto);
							continue;
						}
						else
						{
							if(!Utility.validateNumber(dto.getMobileNumber()))
							{
								dto.setMessage("MOBILE_NO should be numeric only |");
								dto.setErrFlag(true);
								returnBulkMstrDTO.add(dto);
								continue;	
							}
						}
					}
					
				ps.setString(1,dto.getChannelPartnerId());
				ps.setString(2,dto.getMobileNumber());
				ps.setString(3,userBean.getUserLoginId());
				ps.executeUpdate();
					 
				dto.setMessage("Channel Partner Uploaded Successfully");
				returnBulkMstrDTO.add(dto);
				}
	
				
				else if(!dto.isErrFlag() && dto.getActionType().equalsIgnoreCase("d"))
				{
					
					ps3.setString(1,dto.getChannelPartnerId().toUpperCase());
					ps3.setString(2,dto.getMobileNumber());
					
					rs2=ps3.executeQuery();
					
					if(rs2.next())
					{
							ps2.setString(1,userBean.getUserLoginId());
							ps2.setString(2,dto.getChannelPartnerId().toUpperCase());
							ps2.setString(3,dto.getMobileNumber());
							ps2.executeUpdate();
			
							dto.setMessage("Channel Partner Deleted Successfully.");
							returnBulkMstrDTO.add(dto);
							
							}
					else
					{
						dto.setMessage("Channel Partner Not Deleted.");
						returnBulkMstrDTO.add(dto);
					}
					}
					}
		
		catch(Exception e)
		{
			dto.setMessage("ChannelPartner not created");
			returnBulkMstrDTO.add(dto);
			e.printStackTrace();
		}
		}	
	} 
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, null);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return returnBulkMstrDTO;
	
}

//Channel Partner validation

private boolean isValidUserLoginId(BulkMstrDTO dto) {
	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isValid=false;
	String validChannelPartnerId="";
		//validChannelPartnerId = "SELECT USER_LOGIN_ID FROM USER_MSTR WHERE USER_LOGIN_ID = ? WITH UR";
		validChannelPartnerId="select USER_LOGIN_ID from user_mstr where KM_ACTOR_ID="+Constants.CHANNEL_PARTNER_ID+" and USER_LOGIN_ID=? with ur";
		
	try {
		
		con = DBConnection.getDBConnection();
		ps = con.prepareStatement(validChannelPartnerId);
		ps.setString(1,dto.getChannelPartnerId());
		rs = ps.executeQuery();
		
		if(rs.next()) 
			isValid = true;
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			e.printStackTrace();
		}
	}
	return isValid;
}

//ChannelPartner Duplicate Validation

private boolean isDuplicateChannelPartner(BulkMstrDTO dto) throws DAOException {

	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String channelPartner;
	String mobileNumber;
	String duplicateChannelPartner="";

	duplicateChannelPartner="SELECT * FROM USER_MSTR_ADDITIONAL WHERE USER_LOGIN_ID=? and USER_MOBILE_NUMBER=? and STATUS='A' WITH UR ";

	try {
		con = DBConnection.getDBConnection();
		
		ps = con.prepareStatement(duplicateChannelPartner);
		
		ps.setString(1,dto.getChannelPartnerId());
		ps.setString(2,dto.getMobileNumber());
		rs=ps.executeQuery();
		
		if(rs==null)
		{
			isExist=false;
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

}//ended


public boolean isDuplicateState(BulkMstrDTO dto1) throws DAOException {
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	int circleId;
	int lobId;
	String stateName;
	String duplicateState="SELECT * FROM STATE_MSTR WHERE STATE_NAME=? and CIRCLE_MSTR_ID="+
						  "(SELECT CIRCLE_MSTR_ID FROM CIRCLE_MSTR WHERE  CIRCLE_ID=? AND LOB_ID=? AND STATUS='A') AND STATUS='A' WITH UR";

	try {
		con = DBConnection.getDBConnection();
		
		circleId=	Integer.parseInt(dto1.getCircle());
		lobId=Integer.parseInt(dto1.getProductLobId());
		stateName=dto1.getState().toUpperCase();
		ps = con.prepareStatement(duplicateState);
		
		ps.setString(1,stateName);
		ps.setInt(2, circleId);
		ps.setInt(3, lobId);
		
		rs=ps.executeQuery();
		

		
		if(rs==null)
		{
			logger.info("No duplicate state");
			isExist=false;
		}
		
		else if(rs.next())
		{	
			logger.info("duplicate state exists");
			isExist=true;
		}
		

	} catch (Exception e) {
		e.printStackTrace();
		//throw new DAOException("Exception occured in duplicateZone method :  "+ e.getMessage(),e);
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	return isExist;
}

public boolean isValidProdLobCircleComb(BulkMstrDTO bulkDto) throws DAOException {
	
	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String validProdLobId="";
	String validProdId="";
	String validCircle="";
	
	String checkProdLobId="SELECT * FROM CIRCLE_MSTR cm,PRODUCT_MSTR pm,PRODUCT_LOB pl  WHERE cm.LOB_ID=pl.PRODUCT_LOB_ID and pl.PRODUCT_LOB_ID=pm.PRODUCT_LOB_ID and cm.STATUS='A' and pl.STATUS='A' and pm.STATUS='A' and cm.CIRCLE_ID=?  and cm.LOB_ID=? with ur ";

	try {
		con = DBConnection.getDBConnection();
		
		
		
		validProdLobId=bulkDto.getProductLobId();
		validProdId=bulkDto.getProductId();
		validCircle=bulkDto.getCircle();
		
		logger.info("Product Lob Id::::"+validProdLobId);
		logger.info("Product Id::::"+validProdId);
		logger.info("Circle Id::::"+validCircle);
		
		ps = con.prepareStatement(checkProdLobId);
		
		ps.setString(1, validCircle);
		ps.setString(2, validProdId);
		ps.setString(3, validProdLobId);
		
		
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


public boolean chkPin(BulkMstrDTO bulkDto) throws DAOException {
	logger.info("asa::::::::::chkPin:::");
		
		//checking if cityCode entered exists or not for PinCode and RSUCode upload.
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		
		String validProdLobId="";
		String validProdId="";
		String validCircle="";
		String check="";
		
		
		try {
			con = DBConnection.getDBConnection();
			
			
			
			validProdLobId=bulkDto.getPinCode();
			validProdId=bulkDto.getProductId();
			validCircle=bulkDto.getCircle();
			
			if(!bulkDto.getPinCode().equals(""))
			{
				if(bulkDto.getCitycode().equals("") && bulkDto.getCityZoneCode().equals(""))
				{
					logger.info("asa::11");
					 check="SELECT PINCODE FROM PINCODE_MSTR pm,CIRCLE_MSTR clm,ZONE_MSTR zm,CITY_MSTR cm,CITY_ZONE_MSTR czm WHERE PINCODE=? and pm.CITY_ZONE_CODE in (SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE CITY_CODE in  (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in  (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?)))) with ur";
					
					 ps = con.prepareStatement(check); 
					 
					ps.setString(1, bulkDto.getPinCode());
					ps.setString(2, bulkDto.getCircle());
					ps.setString(3, bulkDto.getProductLobId());
				}
				
				else if(!bulkDto.getCitycode().equals("") && bulkDto.getCityZoneCode().equals(""))
				{
					logger.info("asa::22");
					 check="SELECT PINCODE FROM PINCODE_MSTR pm,CIRCLE_MSTR clm,ZONE_MSTR zm,CITY_MSTR cm,CITY_ZONE_MSTR czm WHERE PINCODE=? and pm.CITY_ZONE_CODE in (SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE CITY_CODE =? and  CITY_CODE in  (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?	)))) with ur";
					
					 ps = con.prepareStatement(check); 
					 
					ps.setString(1, bulkDto.getPinCode());
					ps.setString(2, bulkDto.getCitycode());
					ps.setString(3, bulkDto.getCircle());
					ps.setString(4, bulkDto.getProductLobId());
				}
				else if (bulkDto.getCitycode().equals("") && (!bulkDto.getCityZoneCode().equals("")))
				{
					logger.info("asa::33");
					 check="SELECT PINCODE FROM PINCODE_MSTR pm,CIRCLE_MSTR clm,ZONE_MSTR zm,CITY_MSTR cm,CITY_ZONE_MSTR czm WHERE PINCODE=? and  pm.CITY_ZONE_CODE=? and pm.CITY_ZONE_CODE in (SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE  CITY_CODE in  (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?)))) with ur";
					

					 ps = con.prepareStatement(check); 
					 
					ps.setString(1, bulkDto.getPinCode());
					ps.setString(2, bulkDto.getCityZoneCode());
					ps.setString(3, bulkDto.getCircle());
					ps.setString(4, bulkDto.getProductLobId());
				}
				
				else if((!bulkDto.getCitycode().equals("")) && (!bulkDto.getCityZoneCode().equals("")))
				{
					logger.info("asa::44");
					 check="SELECT PINCODE FROM PINCODE_MSTR pm,CIRCLE_MSTR clm,ZONE_MSTR zm,CITY_MSTR cm,CITY_ZONE_MSTR czm WHERE PINCODE=? and  pm.CITY_ZONE_CODE=? and pm.CITY_ZONE_CODE in (SELECT CITY_ZONE_CODE FROM CITY_ZONE_MSTR WHERE CITY_CODE=? and CITY_CODE in (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?)))) with ur";
					
					 ps = con.prepareStatement(check); 
					 
					ps.setString(1, bulkDto.getPinCode());
					ps.setString(2, bulkDto.getCityZoneCode());
					ps.setString(3, bulkDto.getCitycode());
					ps.setString(4, bulkDto.getCircle());
					ps.setString(5, bulkDto.getProductLobId());
					
					logger.info("asa:: bulkDto.getPinCode()::"+ bulkDto.getPinCode());
					logger.info("asa:: bulkDto.getPinCode()::"+ bulkDto.getCityZoneCode());
					logger.info("asa:: bulkDto.getPinCode()::"+ bulkDto.getCitycode());
					logger.info("asa:: bulkDto.getPinCode()::"+ bulkDto.getCircle());
					logger.info("asa:: bulkDto.getPinCode()::"+ bulkDto.getProductLobId());
				}
						
			}
			
			else if(bulkDto.getPinCode().equals(""))
				
			{
				
				if(bulkDto.getCitycode().equals("") && bulkDto.getCityZoneCode().equals(""))
				{
					logger.info("asa:::55");
					 check="select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?  with ur";
					
					 ps = con.prepareStatement(check); 
					 
					ps.setString(1, bulkDto.getCircle());
					ps.setString(2, bulkDto.getProductLobId());
				}
				
				else if(!bulkDto.getCitycode().equals("") && bulkDto.getCityZoneCode().equals(""))
				{
					logger.info("asa::::::66");
					 check="SELECT CIRCLE_ID FROM CIRCLE_MSTR clm,ZONE_MSTR zm, CITY_MSTR cm, CITY_ZONE_MSTR czm WHERE cm.CITY_CODE=? and cm.ZONE_CODE in (SELECT ZONE_CODE FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (SELECT CIRCLE_MSTR_ID FROM CIRCLE_MSTR WHERE CIRCLE_ID=? and LOB_ID=?)) with ur";
					
					 ps = con.prepareStatement(check); 
					 
					ps.setString(1, bulkDto.getCitycode());
					ps.setString(2, bulkDto.getCircle());
					ps.setString(3, bulkDto.getProductLobId());
				}
				
				else if (bulkDto.getCitycode().equals("") && (!bulkDto.getCityZoneCode().equals("")))
				{
					logger.info("asa:::77");
					 check="SELECT CIRCLE_ID FROM  CIRCLE_MSTR clm,ZONE_MSTR zm, CITY_MSTR cm, CITY_ZONE_MSTR czm WHERE czm.CITY_ZONE_CODE=? and czm.CITY_CODE in (SELECT CITY_CODE FROM CITY_MSTR WHERE zone_code in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=?))) with ur";
					
					 ps = con.prepareStatement(check); 
					 
					ps.setString(1, bulkDto.getCityZoneCode());
					ps.setString(2, bulkDto.getCircle());
					ps.setString(3, bulkDto.getProductLobId());
				}
				
				else if((!bulkDto.getCitycode().equals("")) && (!bulkDto.getCityZoneCode().equals("")))
				{
					logger.info("asa:::88");
					 check="SELECT CIRCLE_ID FROM  CIRCLE_MSTR clm,ZONE_MSTR zm, CITY_MSTR cm, CITY_ZONE_MSTR czm WHERE czm.CITY_ZONE_CODE=? and czm.CITY_CODE=cm.CITY_CODE	and cm.ZONE_CODE in (SELECT zone_code FROM ZONE_MSTR WHERE CIRCLE_MSTR_ID in (select CIRCLE_MSTR_ID from CIRCLE_MSTR where CIRCLE_ID =? and LOB_ID=? )) with ur	";
					
					 ps = con.prepareStatement(check); 
					 
					ps.setString(1, bulkDto.getCityZoneCode());
					//ps.setString(2, bulkDto.getCitycode());
					ps.setString(2, bulkDto.getCircle());
					ps.setString(3, bulkDto.getProductLobId());
				}
				
			}
			
			logger.info("Product Lob Id::::"+validProdLobId);
			logger.info("Product Id::::"+validProdId);
			logger.info("Circle Id::::"+validCircle);
			
			//ps = con.prepareStatement(check);
			
			/*ps.setString(1, validCircle);
			ps.setString(2, validProdId);
			ps.setString(3, validProdLobId);
			*/
			
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


public boolean isValidReqCat(BulkMstrDTO bulkDto) throws DAOException {
	
	//checking if req category entered exists or not for product id.
	Connection con = null;
	
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean isExist=false;
	
	String validReqCat="";
	String validProdId="";
	
	String checkProdLobId="SELECT REQUEST_CATEGORY FROM REQUEST_CATEGORY_MSTR WHERE REQUEST_CATEGORY_ID=? and PRODUCT_ID=? and STATUS='A' with ur ";

	String checkProdLobId1="SELECT REQUEST_CATEGORY FROM REQUEST_CATEGORY_MSTR WHERE REQUEST_CATEGORY_ID=? and STATUS='A' with ur ";
	
	try {
		con = DBConnection.getDBConnection();
		
		
		if (!bulkDto.getProductId().equals(""))
		{
		ps = con.prepareStatement(checkProdLobId);
		
		ps.setInt(1, Integer.parseInt(bulkDto.getReqCategory()));
		ps.setInt(2, Integer.parseInt(bulkDto.getProductId()));
		
		}
		
		else
		{
			ps = con.prepareStatement(checkProdLobId1);
			
			ps.setInt(1, Integer.parseInt(bulkDto.getReqCategory()));
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

//added by Nancy Agrawal.
	public ArrayList<BulkMstrDTO> uploadAgencyAssignmentMatrix(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) throws DAOException
		{
			logger.info("inside uploadAgencyAssignmentMatrix:::::::::");
		
		Connection con = null;
		PreparedStatement psInsertMatrix = null;
		PreparedStatement psGetLobId=null;
		PreparedStatement psDeleteMatrix = null;
		PreparedStatement psGetZoneFromCity = null;
		PreparedStatement psGetDataFromPinRsu = null;
		PreparedStatement psGetZoneFromCityZone = null;
		PreparedStatement psGetDataFromPinRsuAndCity=null;
		PreparedStatement psGetDataFromPinRsuAndZone=null;
		PreparedStatement psGetDataFromPinRsuOnly=null;
		ResultSet rs=null;
		ResultSet rs1=null;
		int prodLobId=-1;
		int circleId=-1;
		int prodId=-1;
		int agencyId=-1;
		String status="";
		int product_lob_id = 0;
		ArrayList<BulkMstrDTO> returnBulkMstrDTO = new ArrayList<BulkMstrDTO>();
		
		
		try {
			con = DBConnection.getDBConnection();
			
			psInsertMatrix = con.prepareStatement ("INSERT INTO AGENCY_ASSIGNMENT(AGENCY_ID, PRODUCT_LOB_ID, PRODUCT_ID, CIRCLE_ID,CITY_ID,ZONE_ID,PINCODE_RSU_ID, CITY_ZONE_CODE,STATUS, CREATED, CREATED_BY,CHANNEL_PARTNER_ID) values(?,?,?,?,?,?,?,?,'A',current timestamp,?,?)");
			
			psDeleteMatrix = con.prepareStatement("UPDATE AGENCY_ASSIGNMENT SET STATUS='D'," +
					"CREATED= CURRENT TIMESTAMP, CREATED_BY=? WHERE AGENCY_ID=? AND PRODUCT_LOB_ID =?" +
					"  AND PRODUCT_ID=? AND CIRCLE_ID =? AND ZONE_ID =? AND CITY_ID = ? AND CITY_ZONE_CODE =? AND PINCODE_RSU_ID =? AND CHANNEL_PARTNER_ID = ? AND STATUS= 'A' WITH UR");
			
			psGetZoneFromCity= con.prepareStatement("SELECT ZONE_CODE FROM CITY_MSTR WHERE CITY_CODE=? AND STATUS='A' WITH UR");
			
			psGetZoneFromCityZone= con.prepareStatement("SELECT CM.ZONE_CODE, CZM.CITY_CODE,CZM.CITY_ZONE_CODE FROM CITY_ZONE_MSTR CZM , CITY_MSTR CM WHERE  CM.CITY_CODE=CZM.CITY_CODE and CZM.CITY_ZONE_CODE=? AND  CM.STATUS='A' AND CZM.STATUS='A' WITH UR");
			
			//psGetCityZoneCode=con.prepareStatement("select * from CITY_ZONE_MSTR where CITY_CODE=? ");
			for (Iterator<BulkMstrDTO> itr = listBulkDto.iterator();itr.hasNext();) 
			{
				try
				{
					BulkMstrDTO dto1;
					dto1= (BulkMstrDTO)itr.next();
					
					 if(dto1.getAgencyId().equals("") || dto1.getProductLobId().equals("") ||  dto1.getCircle().equals("")|| dto1.getStatus().equals(""))
					   {
						dto1.setMessage("Agency Id and Product LOB ID  and Circle ID  and status are mandatory fields |");
						dto1.setErrFlag(true);
						returnBulkMstrDTO.add(dto1);
						continue;
					} else if(dto1.getProductLobId().equals("") || dto1.getCircle().equals(""))
						{
							dto1.setMessage("Product LOB ID  and Circle ID  are mandatory fields |");
							dto1.setErrFlag(true);
							returnBulkMstrDTO.add(dto1);
							continue;
						}
					else if(!(dto1.getProductLobId().equals("")) && dto1.getProductLobId().length()>5)
						{
							dto1.setMessage("Invalid Product LOB ID length ");
							returnBulkMstrDTO.add(dto1);
							continue;
						}
					else if(!(dto1.getProductId().equals("")) && dto1.getProductId().length()>5)
						{
							dto1.setMessage("Invalid Product ID length");
							returnBulkMstrDTO.add(dto1);
							continue;
						}
					else if(!(dto1.getCircle().equals("")) && dto1.getCircle().length()>20)
						{
							dto1.setMessage("Invalid Circle ID length ");
							returnBulkMstrDTO.add(dto1);
							continue;
						}
					
					else if(!(dto1.getCitycode().equals("")) && dto1.getCitycode().length()>20)
						{
							dto1.setMessage("Invalid CityCode length");
							returnBulkMstrDTO.add(dto1);
							continue;
						}
					else if(!(dto1.getCityZoneCode().equals("")) && dto1.getCityZoneCode().length()>20)
						{
							dto1.setMessage("Invalid CityZoneCode length ");
							returnBulkMstrDTO.add(dto1);
							continue;
						}
					else if(!(dto1.getPincodersu().equals("")) && !(dto1.getPincodersu().length()>=3 && dto1.getPincodersu().length()<7))
						{
							dto1.setMessage("Invalid PinCode/RSU length");
							returnBulkMstrDTO.add(dto1);
							continue;
						}
						
					/*else if(!("".equalsIgnoreCase(dto1.getChannelPartnerId())) && (dto1.getChannelPartnerId().length()>= 20))
						{
							dto1.setMessage("Invalid channel partner id length");
							returnBulkMstrDTO.add(dto1);
							continue;
						}*/
								
								
								boolean Lobflag  =CommonMstrUtil.isValidProdLobId(dto1);
								if(!Lobflag)
								{
									dto1.setMessage("Invalid Product LOB ID.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}
								
								else if(Lobflag&& dto1.getProductLobId().length()<=5)//valid product lobId
								{
								if(dto1.getProductLobId().equals("2"))	
								{
									psGetDataFromPinRsuOnly=con.prepareStatement("SELECT CM.ZONE_CODE, CZM.CITY_CODE, CZM.CITY_ZONE_CODE, PM.RSU_CODE "+
											"FROM CITY_ZONE_MSTR CZM ,RSU_MSTR PM , CITY_MSTR CM "+
											"WHERE CZM.CITY_ZONE_CODE = PM.CITY_ZONE_CODE and PM.RSU_CODE=? and CM.CITY_CODE = CZM.CITY_CODE "+
											"AND PM.STATUS='A' AND CM.STATUS='A' AND CZM.STATUS='A' WITH UR");
									
									psGetDataFromPinRsu=con.prepareStatement("SELECT CM.ZONE_CODE, CZM.CITY_CODE, CZM.CITY_ZONE_CODE, PM.RSU_CODE "+
											"FROM CITY_ZONE_MSTR CZM ,RSU_MSTR PM , CITY_MSTR CM "+
											"WHERE CZM.CITY_ZONE_CODE = PM.CITY_ZONE_CODE and PM.RSU_CODE=? and CZM.CITY_ZONE_CODE=? and CM.CITY_CODE = CZM.CITY_CODE "+
											"AND PM.STATUS='A' AND CM.STATUS='A' AND CZM.STATUS='A' WITH UR");
						
									
									psGetDataFromPinRsuAndCity=con.prepareStatement("SELECT CM.ZONE_CODE, CZM.CITY_CODE, CZM.CITY_ZONE_CODE, PM.RSU_CODE "+
											"FROM CITY_ZONE_MSTR CZM ,RSU_MSTR PM , CITY_MSTR CM ,ZONE_MSTR ZM "+
											"WHERE CZM.CITY_ZONE_CODE = PM.CITY_ZONE_CODE and PM.RSU_CODE=? and CM.CITY_CODE = CZM.CITY_CODE AND CM.CITY_CODE=?"+
											"AND PM.STATUS='A' AND CM.STATUS='A' AND CZM.STATUS='A' WITH UR");
									
									psGetDataFromPinRsuAndZone=con.prepareStatement("SELECT CM.ZONE_CODE, CZM.CITY_CODE, CZM.CITY_ZONE_CODE, PM.RSU_CODE "+
											"FROM CITY_ZONE_MSTR CZM ,RSU_MSTR PM , CITY_MSTR CM,ZONE_MSTR ZM "+
											"WHERE CZM.CITY_ZONE_CODE = PM.CITY_ZONE_CODE and PM.RSU_CODE=? and CM.CITY_CODE = CZM.CITY_CODE "+
											"AND ZM.ZONE_CODE= CM.ZONE_CODE AND ZM.ZONE_CODE=? AND PM.STATUS='A' AND CM.STATUS='A' AND CZM.STATUS='A' WITH UR");
								
								}
								else
								{
									psGetDataFromPinRsuOnly=con.prepareStatement("SELECT CM.ZONE_CODE, CZM.CITY_CODE, CZM.CITY_ZONE_CODE, PM.PINCODE "+
											"FROM CITY_ZONE_MSTR CZM ,PINCODE_MSTR PM , CITY_MSTR CM "+
											"WHERE CZM.CITY_ZONE_CODE = PM.CITY_ZONE_CODE and PM.PINCODE=? and CM.CITY_CODE = CZM.CITY_CODE "+
											"AND PM.STATUS='A' AND CM.STATUS='A' AND CZM.STATUS='A' WITH UR");
									
									psGetDataFromPinRsu=con.prepareStatement("SELECT CM.ZONE_CODE, CZM.CITY_CODE, CZM.CITY_ZONE_CODE, PM.PINCODE "+
											"FROM CITY_ZONE_MSTR CZM ,PINCODE_MSTR PM , CITY_MSTR CM "+
											"WHERE CZM.CITY_ZONE_CODE = PM.CITY_ZONE_CODE and PM.PINCODE=? and CZM.CITY_ZONE_CODE=? and CM.CITY_CODE = CZM.CITY_CODE "+
											"AND PM.STATUS='A' AND CM.STATUS='A' AND CZM.STATUS='A' WITH UR");
									
									psGetDataFromPinRsuAndCity= con.prepareStatement("SELECT CM.ZONE_CODE, CZM.CITY_CODE, CZM.CITY_ZONE_CODE, PM.PINCODE "+
													"FROM CITY_ZONE_MSTR CZM ,PINCODE_MSTR PM , CITY_MSTR CM,ZONE_MSTR ZM "+
													"WHERE CZM.CITY_ZONE_CODE = PM.CITY_ZONE_CODE and PM.PINCODE=? and CM.CITY_CODE = CZM.CITY_CODE AND CM.CITY_CODE=?"+
													"AND PM.STATUS='A' AND CM.STATUS='A' AND CZM.STATUS='A' WITH UR");
									
									psGetDataFromPinRsuAndZone=con.prepareStatement("SELECT CM.ZONE_CODE, CZM.CITY_CODE, CZM.CITY_ZONE_CODE, PM.PINCODE "+
											"FROM CITY_ZONE_MSTR CZM ,PINCODE_MSTR PM , CITY_MSTR CM,ZONE_MSTR ZM "+
											"WHERE CZM.CITY_ZONE_CODE = PM.CITY_ZONE_CODE and PM.PINCODE=? and CM.CITY_CODE = CZM.CITY_CODE "+
											"AND ZM.ZONE_CODE= CM.ZONE_CODE AND ZM.ZONE_CODE=? AND PM.STATUS='A' AND CM.STATUS='A' AND CZM.STATUS='A' WITH UR");
								
								
								}//end of first else
						}//end of second else 
								
								       
									//if prod id is empty
									if(dto1.getProductId().equals(""))	
										{
										if(!CommonMstrUtil.isValidCircle(dto1))
										
											{
												dto1.setMessage("Invalid Circle ID.");
												dto1.setErrFlag(true);
												returnBulkMstrDTO.add(dto1);
												continue;
											}
										}
									
									else if(dto1.getChannelPartnerId()!=null && !"".equalsIgnoreCase(dto1.getChannelPartnerId()) && !CommonMstrUtil.isValidChannelPartnerId(dto1,2))  //added for invalid channel partner id.
									{
										dto1.setMessage("Invalid Channel Partner ID.");
										dto1.setErrFlag(true);
										returnBulkMstrDTO.add(dto1);
										continue;
								     }
									
									else if (!dto1.getProductId().equals(""))	
										{
										//add validation for prod id,lob and circle
											if(!CommonMstrUtil.isValidProdLobCircleComb(dto1))
											{
												dto1.setMessage("Invalid Circle, product and lob combination.");
												dto1.setErrFlag(true);
												returnBulkMstrDTO.add(dto1);
												continue;
											}
										}
									
									if(!CommonMstrUtil.chkPinRsu(dto1))
									{
										dto1.setMessage("Invalid combination of pincode_rsu and city zone code.");
										dto1.setErrFlag(true);
										returnBulkMstrDTO.add(dto1);
										continue;
									}
									
									//If pincode or rsu is entered , fetching details from the pincode:
									if(dto1.getPincodersu()!="" && dto1.getCityZoneCode()=="")
										
									{
										if(dto1.getCitycode()!="" && dto1.getZoneCode()=="" && CommonMstrUtil.isValidCityCode(dto1))
										{
										psGetDataFromPinRsuAndCity.setString(1,dto1.getPincodersu());
										psGetDataFromPinRsuAndCity.setString(2,dto1.getCity());
										
										rs=psGetDataFromPinRsuAndCity.executeQuery();
										
										while(rs.next()&& rs!=null)
										{	
										dto1.setCitycode(rs.getString("CITY_CODE"));
										dto1.setCityZoneCode("");
										dto1.setZoneCode("");
										}}
										else if (dto1.getCitycode()=="" && dto1.getZoneCode()!="" && CommonMstrUtil.isValidZoneCode(dto1)){
											psGetDataFromPinRsuAndZone.setString(1,dto1.getPincodersu());
											psGetDataFromPinRsuAndZone.setString(2,dto1.getZone());
											
											rs=psGetDataFromPinRsuAndZone.executeQuery();
											
											while(rs.next()&& rs!=null)
											{	
											dto1.setZoneCode(rs.getString("ZONE_CODE"));
											dto1.setCitycode("");
											dto1.setCityZoneCode("");
										}
									}
										else if(dto1.getCitycode()!="" && dto1.getZoneCode()!="" && CommonMstrUtil.isValidZoneCodeAndCityCode(dto1)){
											psGetDataFromPinRsuAndZone.setString(1,dto1.getPincodersu());
											psGetDataFromPinRsuAndZone.setString(2,dto1.getZone());
											
											rs=psGetDataFromPinRsuAndZone.executeQuery();
											
											while(rs.next()&& rs!=null)
											{	
											dto1.setZoneCode(rs.getString("ZONE_CODE"));
											dto1.setCitycode(rs.getString("CITY_CODE"));
											dto1.setCityZoneCode("");
											}
										}
										else if(dto1.getCitycode()=="" && dto1.getZoneCode()=="")
										{
											psGetDataFromPinRsuOnly.setString(1,dto1.getPincodersu());
											rs=psGetDataFromPinRsuOnly.executeQuery();
											while(rs.next()&& rs!=null)
											{	
											dto1.setZoneCode("");
											dto1.setCitycode("");
											dto1.setCityZoneCode("");
											}
											
										}
									}
									//if pincode/rsu is not entered:
									else if(dto1.getPincodersu()=="" && dto1.getCityZoneCode()!="")              
										
									{
										
										psGetZoneFromCityZone.setString(1,dto1.getCityZoneCode());
										
										
										rs=psGetZoneFromCityZone.executeQuery();
										while(rs.next()&& rs!=null)
										{
										if(dto1.getCitycode()!="" && dto1.getZoneCode()=="" && CommonMstrUtil.isValidCityCode(dto1))
										{
											dto1.setCitycode(rs.getString("CITY_CODE"));
										}	
										else if (dto1.getCitycode()=="" && dto1.getZoneCode()!="" && CommonMstrUtil.isValidZoneCode(dto1)){
											
											dto1.setZoneCode(rs.getString("ZONE_CODE"));
											
											
									       }
										else if(dto1.getCitycode()!="" && dto1.getZoneCode()!="" && CommonMstrUtil.isValidZoneCodeAndCityCode(dto1)){
											
											dto1.setZoneCode(rs.getString("ZONE_CODE"));
											dto1.setCitycode(rs.getString("CITY_CODE"));
																				
										}
										else if(dto1.getCitycode()=="" && dto1.getZoneCode()=="")
										{
											dto1.setZoneCode("");
											dto1.setCitycode("");
										}	
										}
									}
									
									//channel partner validation:
									else if(dto1.getCitycode()!=null && dto1.getCitycode().length() >0 )
										
									{
										if(!CommonMstrUtil.isValidLobCircleCP(dto1,2))
										{
											
											dto1.setMessage("Invalid Circle,ProductLob,Channel Partner and City Combination.");
											dto1.setErrFlag(true);
											returnBulkMstrDTO.add(dto1);
											continue;
										}
										
									
									else 
									{
										if(!CommonMstrUtil.isValidLobCircleCP(dto1,2))
										{
											
											dto1.setMessage("Invalid Circle,ProductLob and Channel Partner Combination.");
											dto1.setErrFlag(true);
											returnBulkMstrDTO.add(dto1);
											continue;
										}
									}
									}
									
									//pincode_rsu and cityzonecode both are entered.
									else if(dto1.getPincodersu()!="" && dto1.getCityZoneCode()!="")
									{
										
										if(!CommonMstrUtil.isValidCityZoneCodeforPinCodeRsu(dto1))									
										{
											dto1.setMessage("City Zone Code Doesnt Exist for Entered PinCode/RSU.");
											dto1.setErrFlag(true);
											returnBulkMstrDTO.add(dto1);
											continue;
										}
										else if(CommonMstrUtil.isValidCityZoneCodeforPinCodeRsu(dto1))
										{
											psGetDataFromPinRsu.setString(1,dto1.getPincodersu());
											psGetDataFromPinRsu.setString(2,dto1.getCityZoneCode());
											
										
										rs=psGetDataFromPinRsu.executeQuery();
										
										while(rs.next())
											{
											if(dto1.getCitycode()!="" && dto1.getZoneCode()=="" && CommonMstrUtil.isValidCityCode(dto1))
											{
												dto1.setCitycode(rs.getString("CITY_CODE"));
												//dto1.setCityZoneCode(rs.getString("CITY_ZONE_CODE"));
											}	
											else if (dto1.getCitycode()=="" && dto1.getZoneCode()!="" && CommonMstrUtil.isValidZoneCode(dto1)){
												
												dto1.setZoneCode(rs.getString("ZONE_CODE"));
												//dto1.setCityZoneCode(rs.getString("CITY_ZONE_CODE"));
												
										       }
											else if(dto1.getCitycode()!="" && dto1.getZoneCode()!="" && CommonMstrUtil.isValidZoneCodeAndCityCode(dto1)){
												
												dto1.setZoneCode(rs.getString("ZONE_CODE"));
												dto1.setCitycode(rs.getString("CITY_CODE"));
												//dto1.setCityZoneCode(rs.getString("CITY_ZONE_CODE"));
												
											}
											
											}
											}
										}
										
									
									
									//pincode_rsu and city zone code both are not entered.
									else if(dto1.getPincodersu()=="" && dto1.getCityZoneCode()=="")
									{
										dto1.setPincodersu("");
										dto1.setCityZoneCode("");
										
										if(dto1.getCitycode()!="" && dto1.getZoneCode()=="" && CommonMstrUtil.isValidCityCode(dto1))
										{
											psGetZoneFromCity.setString(1,dto1.getCitycode());
											rs=psGetZoneFromCity.executeQuery();
											
											while(rs.next())
											{
												dto1.setCitycode(rs.getString("CITY_CODE"));
											}
										}
										else if (dto1.getCitycode()=="" && dto1.getZoneCode()!="" && CommonMstrUtil.isValidZoneCode(dto1)){
											
											dto1.setZoneCode(rs.getString("ZONE_CODE"));
											
											
									       }
										else if(dto1.getCitycode()!="" && dto1.getZoneCode()!="" && CommonMstrUtil.isValidZoneCodeAndCityCode(dto1)){
											
											dto1.setZoneCode(rs.getString("ZONE_CODE"));
											dto1.setCitycode(rs.getString("CITY_CODE"));
											
											
										}
										else if(dto1.getCitycode()=="" && dto1.getZoneCode()=="" && CommonMstrUtil.isValidCircle(dto1))
										{
											dto1.setZoneCode("");
											dto1.setCitycode("");	
										}
										}
									
									
									boolean agencyFlag = CommonMstrUtil.isValidAgencyAssignmentMtrx(dto1);
									
									if(dto1.getStatus().equalsIgnoreCase("C"))
									{
											if(agencyFlag)						
												{
													dto1.setMessage("Agency Assignment Matrix Already Exists.");
													dto1.setErrFlag(true);
													returnBulkMstrDTO.add(dto1);
													continue;
												}
												
												else if(!agencyFlag)					
												{
												    psInsertMatrix.setInt(1,Integer.parseInt(dto1.getAgencyId()));
													psInsertMatrix.setInt(2,Integer.parseInt(dto1.getProductLobId()));
													psInsertMatrix.setInt(4,Integer.parseInt(dto1.getCircle()));
													if(dto1.getProductId() !=null && dto1.getProductId().length() >0)
													{
													psInsertMatrix.setInt(3,Integer.parseInt(dto1.getProductId()));
													}
													else 
													{
														psInsertMatrix.setInt(3,-1);
													}
													if(dto1.getZoneCode() !=null && dto1.getZoneCode().length() >0) {
														psInsertMatrix.setString(6,dto1.getZoneCode());
													}else {
														psInsertMatrix.setString(6,"");
													}
													if(dto1.getCitycode() !=null && dto1.getCitycode().length() >0) {
														psInsertMatrix.setString(5,dto1.getCitycode());
													}else {
														psInsertMatrix.setString(5,"");
													}
													
													if(dto1.getCityZoneCode()!=null && dto1.getCityZoneCode().length() >0) {
														psInsertMatrix.setString(8,dto1.getCityZoneCode());
													}else {
														psInsertMatrix.setString(8,"");	
													}
													
													if(dto1.getPincodersu()!= null && dto1.getPincodersu().length() >0)
													{	
														psInsertMatrix.setString(7,dto1.getPincodersu());
													}
													else
													{
														psInsertMatrix.setString(7,"");
													}
													
													psInsertMatrix.setString(9, userBean.getUserLoginId());
												
													if(dto1.getChannelPartnerId() !=null && dto1.getChannelPartnerId().length() >0) {
														psInsertMatrix.setString(10,dto1.getChannelPartnerId());
													}else {
														psInsertMatrix.setString(10,"");	
													}
													psInsertMatrix.executeUpdate();
												
											        dto1.setMessage("Agency Assignment matrix Inserted Successfully.");
											        returnBulkMstrDTO.add(dto1);
											        continue;
								               }
									}
									//if status is entered as D:
									else if(dto1.getStatus().equalsIgnoreCase("D"))
									{
										if(!agencyFlag)						
										{
											dto1.setMessage("Agency Assignment Matrix Already Deleted.");
											dto1.setErrFlag(true);
											returnBulkMstrDTO.add(dto1);
											continue;
										}
										
										else if(agencyFlag)					
										{
											
											psDeleteMatrix.setString(1, userBean.getUserLoginId());
										    psDeleteMatrix.setInt(2,Integer.parseInt(dto1.getAgencyId()));
										    psDeleteMatrix.setInt(3,Integer.parseInt(dto1.getProductLobId()));
											if(dto1.getProductId() !=null && dto1.getProductId().length() >0){
												psDeleteMatrix.setInt(4,Integer.parseInt(dto1.getProductId()));
											}else {
												psDeleteMatrix.setInt(4,-1);
											}
											psDeleteMatrix.setInt(5,Integer.parseInt(dto1.getCircle()));
											
											if(dto1.getZoneCode() !=null && dto1.getZoneCode().length() >0) {
												psDeleteMatrix.setString(6,dto1.getZoneCode());	
											}else {
												psDeleteMatrix.setString(6,"");
											}
											if(dto1.getCitycode() !=null && dto1.getCitycode().length() >0) {
												psDeleteMatrix.setString(7,dto1.getCitycode());
											}else {
												psDeleteMatrix.setString(7,"");
											}
											
											if(dto1.getCityZoneCode() != null && dto1.getCityZoneCode().length() >0)
											{
												psDeleteMatrix.setString(8,dto1.getCityZoneCode());
											}else
											{
												psDeleteMatrix.setString(8,"");	
											}
											
											if(dto1.getPincodersu()!=null && dto1.getPincodersu().length() >0)
											{	
												psDeleteMatrix.setString(9,dto1.getPincodersu());
											}
											else
											{
												psDeleteMatrix.setString(9,"");
											}
											
											if(dto1.getChannelPartnerId() !=null && dto1.getChannelPartnerId().length() >0) {
												psDeleteMatrix.setString(10,dto1.getChannelPartnerId());
												
											}else {
												psDeleteMatrix.setString(10,"");	
											}
											
										int  i =psDeleteMatrix.executeUpdate();
										logger.info("**row updated*******==="+i);
										
									dto1.setMessage("Agency Assignment matrix Deleted Successfully.");
									returnBulkMstrDTO.add(dto1);
									continue;
									}
										}	
									
				
			}	
			
						catch(Exception e)
						{

						
							BulkMstrDTO bulkDto= new BulkMstrDTO();
							bulkDto.setMessage("Agency Assignment Matrix cannot be uploaded.");
							returnBulkMstrDTO.add(bulkDto);
							e.printStackTrace();
						}
		
			} // END Iterator
		
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						finally 
							    {
							try {
								DBConnection.releaseResources(con, psInsertMatrix, null);
								DBConnection.releaseResources(con, psDeleteMatrix, null);
							} catch (Exception e) {				
								throw new DAOException(e.getMessage(), e);
							}
		}
		
		return returnBulkMstrDTO;
		}


	
	public ArrayList<BulkMstrDTO> uploadWorkFlowAutoAssignmentMatrix(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) throws DAOException
	{
		logger.info("inside uploadWorkFlowAutoAssignmentMatrix:::::::::");

	Connection con = null;
	PreparedStatement psInsertMatrix = null;
	PreparedStatement psDeleteMatrix = null;
	
	ResultSet rs=null;
	ResultSet rs1=null;
	int prodLobId=-1;
	int circleId=-1;
	int prodId=-1;
	ArrayList<BulkMstrDTO> returnBulkMstrDTO = new ArrayList<BulkMstrDTO>();
	
	
	try {
		BulkMstrDTO bulkMstrDTO = new BulkMstrDTO();
		con = DBConnection.getDBConnection();
		
		psInsertMatrix = con.prepareStatement ("INSERT INTO AUTO_ASSIGNMENT_MATRIX(LOB_ID, PRODUCT_ID, CIRCLE_ID, CITY_CODE,PINCODE,RSU_CODE,STATUS, UPDATED_DT, UPDATED_BY,REQUEST_CATEGORY_ID,OLM_ID,AUTO_ASSIGNMENT_TYPE) " +
				"values(?,?,?,?,?,?,'A',current timestamp,?,?,?,?)");
		
		
		psDeleteMatrix = con.prepareStatement("UPDATE AUTO_ASSIGNMENT_MATRIX SET STATUS='D',UPDATED_DT= CURRENT TIMESTAMP, UPDATED_BY=? WHERE LOB_ID =? AND CIRCLE_ID=? AND OLM_ID=? AND CITY_CODE=? AND PINCODE=? AND RSU_CODE=? AND REQUEST_CATEGORY_ID=? AND STATUS = 'A' AND PRODUCT_ID=? AND AUTO_ASSIGNMENT_TYPE=? WITH UR");
		
								
		for (Iterator<BulkMstrDTO> itr = listBulkDto.iterator();itr.hasNext();) 
		{
			try
			{
				BulkMstrDTO dto1;
				dto1= (BulkMstrDTO)itr.next();
				if(dto1.isErrFlag())
					{
							if(!(dto1.getActionType().equalsIgnoreCase("c") || dto1.getActionType().equalsIgnoreCase("d") ) )
							{
								dto1.setMessage("Enter either c or d for CREATION/DELETION | ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							
							//all fields mandatory checked.
								if(dto1.getProductLobId().equals("") || dto1.getProductId().equals("") || dto1.getChannelPartnerId().equals("") ||  dto1.getCircle().equals(""))
							{
								dto1.setMessage("Product LOB ID,product id,Circle ID and OLm ID are mandatory fields |");
								dto1.setErrFlag(true);
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							
								if(dto1.getProductId().length()>5)
								{
									logger.info("Invalid Product  ID length");
									dto1.setMessage("Invalid Product  ID length ");
									returnBulkMstrDTO.add(dto1);
									continue;
								}
							//checking if product lob id is valid
								
							if(dto1.getProductLobId().length()>5)
							{
								dto1.setMessage("Invalid Product LOB ID length ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							
							//checking if product circle id is valid
							
						
							if(dto1.getCircle().length()>20)
							{
								dto1.setMessage("Invalid Circle ID length ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
						
							if(dto1.getChannelPartnerId().length()>10)
							{
								dto1.setMessage("Invalid Channel Partner ID length ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							
							//checking if city code is valid:
							
							if(dto1.getCitycode()!=null && dto1.getCitycode().length() >20 )
							{
								dto1.setMessage("Invalid city code length ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							
					
							//CHECKING IF PINCODE/RSU IS VALID:
							if(dto1.getPinCode()!=null && dto1.getPinCode().length() >6 )
							{
								dto1.setMessage("Invalid Pincode length ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							
							if(dto1.getRsuCode()!=null && dto1.getRsuCode().length() >10 )
							{
								logger.info("Invalid RSU length  ");
								dto1.setMessage("Invalid RSU length ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							
							//CHECKING IF REQUEST CATEGORY IS VALID:
							
							if(dto1.getReqCategory()!=null && dto1.getReqCategory().length() >50 )
							{
								dto1.setMessage("Invalid Request Category length ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							
					}
							
				
					else if(!dto1.isErrFlag() && dto1.getActionType().equalsIgnoreCase("c") )
					{
						logger.info("creating WorkflowAutoAssignmentMatrix :::::::::::::::::::");
						
						boolean Lobflag=CommonMstrUtil.isValidProdLobId(dto1);
						if(!Lobflag)
						{
							dto1.setMessage("Invalid Product LOB ID.");
							dto1.setErrFlag(true);
							returnBulkMstrDTO.add(dto1);
							continue;
						}	
						
						if(!CommonMstrUtil.isValidProdLobCircleComb(dto1))
						{
							dto1.setMessage("Invalid Circle, product and lob combination.");
							dto1.setErrFlag(true);
							returnBulkMstrDTO.add(dto1);
							continue;
						}

						boolean ChannelPartnerFlag  =CommonMstrUtil.isValidChannelPartnerId(dto1,1);
						if(!ChannelPartnerFlag)
						{
							dto1.setMessage("Invalid Channel Partner ID.");
							dto1.setErrFlag(true);
							returnBulkMstrDTO.add(dto1);
							continue;
					     }
						
							//product and lob combination
									if(!CommonMstrUtil.isValidProdLobCircle(dto1))
										{
											dto1.setMessage("Invalid Circle and productlob combination.");
											dto1.setErrFlag(true);
											returnBulkMstrDTO.add(dto1);
											continue;
										}
									
									//circle lob channel partner and city combination
									
									if(dto1.getCitycode()!=null && dto1.getCitycode().length() >0 )
									
									{
										if(!CommonMstrUtil.isValidLobCircleCP(dto1,1))
										{
											
											dto1.setMessage("Invalid Circle,ProductLob,Channel Partner and City Combination.");
											dto1.setErrFlag(true);
											returnBulkMstrDTO.add(dto1);
											continue;
										}
										
									}
									else 
									{
										if(!CommonMstrUtil.isValidLobCircleCP(dto1,1))
										{
											
											dto1.setMessage("Invalid Circle,ProductLob and Channel Partner Combination.");
											dto1.setErrFlag(true);
											returnBulkMstrDTO.add(dto1);
											continue;
										}
									}
									
									if(dto1.getCitycode()!=null && dto1.getCitycode().length() >0 )
									{
										 if(!CommonMstrUtil.isValidCityCode(dto1))
										{
											dto1.setMessage("Invalid City code.");
											dto1.setErrFlag(true);
											returnBulkMstrDTO.add(dto1);
											continue;
										}
									}
									
									if(dto1.getPinCode()!=null && dto1.getPinCode().length() >0 )
									{
										 if(!CommonMstrUtil.chkPin(dto1))
										{
											dto1.setMessage("Invalid Pincode.");
											dto1.setErrFlag(true);
											returnBulkMstrDTO.add(dto1);
											continue;
										}
									}
									
									if(!dto1.getRsuCode().equals("") && dto1.getRsuCode()!=null && dto1.getProductLobId().equals("2"))
									{
										 if(!CommonMstrUtil.chkRsu(dto1))
										{
											 logger.info("Invalid RSU Code.");
											dto1.setMessage("Invalid RSU Code.");
											dto1.setErrFlag(true);
											returnBulkMstrDTO.add(dto1);
											continue;
										}
									}
									
									
									if(dto1.getReqCategory()!=null && dto1.getReqCategory().length() >0 )
									{
										 if(!CommonMstrUtil.isValidReqCategory(dto1))
										{
											dto1.setMessage("Invalid request Category.");
											dto1.setErrFlag(true);
											returnBulkMstrDTO.add(dto1);
											continue;
										}
									}
									
									//pincode is entered with city:
									   /*if(dto1.getPinCode()!=null && dto1.getPinCode().length() >0)
											{
											if(!CommonMstrUtil.chkPinRsu(dto1))
											
										{
											
											dto1.setMessage("Invalid Circle,ProductLob,ChannelPartner and PinCode combination.");
											dto1.setErrFlag(true);
											returnBulkMstrDTO.add(dto1);
											continue;
										}
											}*/
										
										
										if(dto1.getReqCategory()!=null && dto1.getReqCategory().length() >0)
											
										{
											if(!CommonMstrUtil.isValidReqCategoryCombination(dto1))
												{
													dto1.setMessage("Invalid Circle,ProductLob,ChannelPartner and Request Category Combination.");
													dto1.setErrFlag(true);
													returnBulkMstrDTO.add(dto1);
													continue;
												}	
											}
												
								if(CommonMstrUtil.isValidWorkFlowAsgnmtMtrxforDelete(dto1))						
								{
									
									dto1.setMessage("Workflow Auto Assignment Matrix Already Exists.");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
								}
								
								else if(!CommonMstrUtil.isValidWorkFlowAsgnmtMtrxforDelete(dto1))					
								{
									
									int id=CommonMstrUtil.getRequestCategoryId(dto1);
									psInsertMatrix.setInt(1,Integer.parseInt(dto1.getProductLobId()));
									psInsertMatrix.setInt(2, Integer.parseInt(dto1.getProductId()));
									psInsertMatrix.setInt(3,Integer.parseInt(dto1.getCircle()));
									if(dto1.getCitycode()!="")
									{
									psInsertMatrix.setString(4,dto1.getCitycode());
									}
									else
									{
										psInsertMatrix.setString(4,"");	
									}
									if(dto1.getPinCode()!="")
									{	
										psInsertMatrix.setString(5,dto1.getPinCode());
									}
									else
									{
										psInsertMatrix.setString(5,"");
									}
									if(dto1.getRsuCode()!="")
									{	
										psInsertMatrix.setString(6,dto1.getRsuCode());
									}
									else
									{
										psInsertMatrix.setString(6,"");
									}
									psInsertMatrix.setString(7,userBean.getUserLoginId());
									if(dto1.getReqCategory()!="")
									{
										
									psInsertMatrix.setInt(8,id);
									}
									else
									{
										psInsertMatrix.setInt(8,-1);	
									}
									psInsertMatrix.setString(9,dto1.getChannelPartnerId());
									psInsertMatrix.setString(10, Constants.AUTO_ASSIGNMENT_WORKFLOW);
									psInsertMatrix.executeUpdate();
										
							dto1.setMessage("Auto Assignment matrix Inserted Successfully.");
							returnBulkMstrDTO.add(dto1);
							continue;
							}
			}
		
		
				else if(!dto1.isErrFlag() && dto1.getActionType().equalsIgnoreCase("d"))
				{
					
					if(!CommonMstrUtil.isValidWorkFlowAsgnmtMtrxforDelete(dto1))
					{
						
						dto1.setMessage(" Auto Assignment Matrix Doesnt Exists.");
						dto1.setErrFlag(true);
						returnBulkMstrDTO.add(dto1);
						continue;
					}
					
					else if(CommonMstrUtil.isValidWorkFlowAsgnmtMtrxforDelete(dto1))
					{
								
						int id=CommonMstrUtil.getRequestCategoryId(dto1);
						prodLobId=Integer.parseInt(dto1.getProductLobId());
						circleId=Integer.parseInt(dto1.getCircle());
						psDeleteMatrix.setString(1,userBean.getUserLoginId());
						psDeleteMatrix.setInt(2, prodLobId);
						psDeleteMatrix.setInt(3, circleId);
						psDeleteMatrix.setString(4, dto1.getChannelPartnerId());
						
					
								
						if(dto1.getCitycode()!="")
						{	
						psDeleteMatrix.setString(5,dto1.getCitycode().toUpperCase());
						}
						else
						{
							psDeleteMatrix.setString(5,"");
						}
						if(dto1.getPinCode()!="")
						{
						psDeleteMatrix.setString(6,dto1.getPinCode());
						}
						else
						{
							psDeleteMatrix.setString(6,"");	
						}
						if(dto1.getReqCategory()!="")
						{
							
						psDeleteMatrix.setInt(8,id);
						}
						else
						{
							psDeleteMatrix.setInt(8,-1);
						}
						
						if(dto1.getRsuCode()!="")
						{
						psDeleteMatrix.setString(7,dto1.getRsuCode());
						}
						else
						{
							psDeleteMatrix.setString(7,"");	
						}
						psDeleteMatrix.setInt(9,Integer.parseInt(dto1.getProductId()));
						psDeleteMatrix.setString(10,Constants.AUTO_ASSIGNMENT_WORKFLOW);
						
						psDeleteMatrix.executeUpdate();
			
							bulkMstrDTO = new BulkMstrDTO();
							logger.info("Auto Assignment Matrix for deletion ends.");
							dto1.setMessage("Assignment Matrix deleted successfully");
							
							returnBulkMstrDTO.add(dto1);
							continue;
							
					}
					else
					{
						logger.info("Assignment Matrix could not be deleted.");
						dto1.setMessage("Assignment Matrix could not be deleted.");
						returnBulkMstrDTO.add(dto1);
						continue;
					}
				}
					
				
		}	
		
		catch(Exception e)
		{
			BulkMstrDTO bulkDto= new BulkMstrDTO();
			bulkDto.setMessage("WorkFlow Auto Assignment Matrix cannot be uploaded.");
			returnBulkMstrDTO.add(bulkDto);
			e.printStackTrace();
		}

		} // END Iterator

	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, psInsertMatrix, null);
			DBConnection.releaseResources(con, psDeleteMatrix, null);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	return returnBulkMstrDTO;
	}
	
	
	
	//-----------------Added by Srikant
	
	
	
	public ArrayList<BulkMstrDTO> uploadChannelCode(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) throws DAOException
	{
		logger.info("inside uploadChannelCode:::::::::");

	Connection con = null;
	PreparedStatement psInsertChannelCode = null;
	PreparedStatement psDeleteChannelCode = null;
	PreparedStatement ps=null;
	
	ResultSet rs=null;
	//ResultSet rs1=null;
	String olmId=null;
	int channelCode=-1;
	ArrayList<BulkMstrDTO> returnBulkMstrDTO = new ArrayList<BulkMstrDTO>();
	
	
	try {
		BulkMstrDTO bulkMstrDTO = null;
		con = DBConnection.getDBConnection();
		
		psInsertChannelCode = con.prepareStatement ("INSERT INTO UPLOAD_CHANNEL_CODE(OLM_ID,CHANNEL_CODE,STATUS,CREATION_DATE,CREATED_BY,UPDATED_BY) " +
				"values(?,?,'A',current timestamp,?,?)");
		
		logger.info("______________________________======");
		//logger.info(dto1.isErrFlag());
		//logger.info(dto1.getStatus());
		psDeleteChannelCode = con.prepareStatement("UPDATE UPLOAD_CHANNEL_CODE SET STATUS='D',UPDATED_DATE= CURRENT TIMESTAMP,UPDATED_BY=? WHERE OLM_ID =? AND CHANNEL_CODE=? AND STATUS='A' WITH UR");
		ps=con.prepareStatement("SELECT OLM_ID FROM ASSIGNMENT_MATRIX WHERE OLM_ID=? and STATUS='A' WITH UR");
								
		for (Iterator<BulkMstrDTO> itr = listBulkDto.iterator();itr.hasNext();) 
		{
			
			try
			{
				BulkMstrDTO dto1;
				dto1= (BulkMstrDTO)itr.next();
				if(dto1.isErrFlag())
					{
					logger.info("**********************"+dto1.getStatus());
					logger.info("************"+dto1.isErrFlag());
					
					
					//logger.info("**********"+rs.next());
					
							if(!(dto1.getActionType().equalsIgnoreCase("c") || dto1.getActionType().equalsIgnoreCase("d") ) )
							{
								dto1.setMessage("Enter either c or d for CREATION/DELETION | ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							/*if(rs.next()==false)
							{
								
								dto1.setMessage("OLM Id not valid");
								returnBulkMstrDTO.add(dto1);
								continue;
							}*/
							//all fields mandatory checked.
								if(dto1.getOlmId().equals("") || dto1.getChannelCode().equals("") || dto1.getActionType().equals(""))
							{
								dto1.setMessage("OLM ID,CHANNEL CODE,STATUS are mandatory fields |");
								dto1.setErrFlag(true);
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							//checking channell code
								if(dto1.getChannelCode().length()>15)
								{
									logger.info("Invalid Channel Code length");
									dto1.setMessage("Invalid Channel Code length ");
									returnBulkMstrDTO.add(dto1);
									continue;
								}
							
								
						
							//checking if product circle id is valid
							
						
							if(dto1.getActionType().length()>2)
							{
								dto1.setMessage("Invalid Status  Length ");
								returnBulkMstrDTO.add(dto1);
								continue;
							}
							
							//
							
					}
							
				
					else if(!dto1.isErrFlag()&&dto1.getActionType().equalsIgnoreCase("c") )
					{
						logger.info("creating Bulkuploadchannelcode :::::::::::::::::::");
						
						/*if(CommonMstrUtil.isValidChannelCodeInsert(dto1).equalsIgnoreCase("fail"))						
								{
							logger.info("%%%%%%%%%%%%%%%%%%"+CommonMstrUtil.isValidChannelCodeInsert(dto1));
									dto1.setMessage("OLM id not valid");
									dto1.setErrFlag(true);
									returnBulkMstrDTO.add(dto1);
									continue;
									
									
								}*/
						ps.setString(1,dto1.getOlmId());
						rs=ps.executeQuery();
						//String s=rs.getString("OLM_ID");
						//logger.info("00000000000000000000000000"+s);
						if(rs.next()==false)
						{
							
							dto1.setMessage("OLM Id not valid");
							dto1.setErrFlag(true);
							returnBulkMstrDTO.add(dto1);
							continue;
						}
						 if(CommonMstrUtil.isValidChannelCodeInsert(dto1).equalsIgnoreCase("notsuccess"))
						{
							dto1.setMessage("OLM id and ChannelCode pair already exists");
							dto1.setErrFlag(true);
							returnBulkMstrDTO.add(dto1);
							continue;
						}
						
						/*else if(CommonMstrUtil.isValidChannelCodeInsert(dto1).equals("problem"))
						{
							dto1.setMessage("Problem occured in Olm id");
							dto1.setErrFlag(true);
							returnBulkMstrDTO.add(dto1);
							continue;
						}*/
							else if(CommonMstrUtil.isValidChannelCodeInsert(dto1).equalsIgnoreCase("success"))					
								{
								logger.info("--%%%%%%%%%%%%%%"+CommonMstrUtil.isValidChannelCodeInsert(dto1));
									//int id=CommonMstrUtil.getRequestCategoryId(dto1);
									psInsertChannelCode.setString(1,dto1.getOlmId());
									psInsertChannelCode.setString(2,dto1.getChannelCode());
									psInsertChannelCode.setString(3,userBean.getUserLoginId());
									psInsertChannelCode.setString(4,userBean.getUserLoginId());
									//psInsertMatrix.setInt(3,Integer.parseInt(dto1.getCircle()));
									
									
									psInsertChannelCode.executeUpdate();
										
							dto1.setMessage("OLM id and Channelcode Inserted Successfully.");
							returnBulkMstrDTO.add(dto1);
							continue;
							}
					}
		
		
				else if(!dto1.isErrFlag() && dto1.getActionType().equalsIgnoreCase("d"))
				{
					
					if(CommonMstrUtil.isValidChannelCodeInsert(dto1).equalsIgnoreCase("success"))
					{
						
						dto1.setMessage(" Channelcode and olmid Doesnt Exists.");
						dto1.setErrFlag(true);
						returnBulkMstrDTO.add(dto1);
						continue;
					}
				
					else 
					{
								
						//int id=CommonMstrUtil.getRequestCategoryId(dto1);
						
						psDeleteChannelCode.setString(1,userBean.getUserLoginId());
						psDeleteChannelCode.setString(2,dto1.getOlmId());
						psDeleteChannelCode.setString(3,dto1.getChannelCode());
						//psDeleteChannelCode.setString(3, dto1.getStatus());
						
						
					
								
						psDeleteChannelCode.executeUpdate();
			
							bulkMstrDTO = new BulkMstrDTO();
							logger.info("Bulk channel code for deletion ends.");
							dto1.setMessage("Cahnnel code deleted successfully");
							
							returnBulkMstrDTO.add(dto1);
							continue;
							
					}
				}
					else
					{
						logger.info("Cahnnel code could not be deleted.");
						dto1.setMessage("Cahnnel code could not be deleted.");
						returnBulkMstrDTO.add(dto1);
						continue;
					}
					
				
		}	
		
		catch(Exception e)
		{
			BulkMstrDTO bulkDto= new BulkMstrDTO();
			bulkDto.setMessage("Channel code and Olm id  cannot be uploaded.");
			returnBulkMstrDTO.add(bulkDto);
			e.printStackTrace();
		}

		} // END Iterator

	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		try {
			DBConnection.releaseResources(con, psInsertChannelCode, null);
			DBConnection.releaseResources(con, psDeleteChannelCode, null);
		} catch (Exception e) {				
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	return returnBulkMstrDTO;
	}
	

	public ArrayList<BulkMstrDTO> uploadChannelPartnerCapability (ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean)throws DAOException
	{	
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		BulkMstrDTO dto = null;
		String olmid="";
		ResultSet rs1=null;
		ResultSet rs2=null;
		ResultSet rs3=null;
		ArrayList<BulkMstrDTO> arr= new ArrayList<BulkMstrDTO>();
		ArrayList<BulkMstrDTO> returnBulkMstrDTO = new ArrayList<BulkMstrDTO>();
		
		try {
			
			BulkMstrDTO bulkMstrDTO = new BulkMstrDTO();
			con = DBConnection.getDBConnection();

			ps1 =con.prepareStatement("SELECT A.USER_LOGIN_ID FROM USER_MSTR A,USER_MAPPING B WHERE A.USER_LOGIN_ID=B.USER_LOGIN_ID AND A.USER_LOGIN_ID=? WITH UR");
			ps2=con.prepareStatement("SELECT OLM_ID FROM ASSIGNMENT_CAPACITY_MSTR WHERE OLM_ID=? WITH UR");
			ps3 = con.prepareStatement("UPDATE ASSIGNMENT_CAPACITY_MSTR SET THRESHOLD_VALUE= ?,CRETAED_BY=?,CREATED_DATE=current timestamp,IP_ADDRESS=? WHERE OLM_ID=? WITH UR");
			ps4 = con.prepareStatement("DELETE FROM ASSIGNMENT_CAPACITY_MSTR WHERE OLM_ID=? WITH UR");
			ps5 = con.prepareStatement("INSERT INTO ASSIGNMENT_CAPACITY_MSTR(OLM_ID,THRESHOLD_VALUE,CRETAED_BY,CREATED_DATE,IP_ADDRESS) VALUES(?,?,?,current timestamp,?) WITH UR");
		
					
			for (Iterator<BulkMstrDTO> itr = listBulkDto.iterator();itr.hasNext();) 
			{
				try
				{
					
					dto = (BulkMstrDTO) itr.next();	
					
					if(dto.isErrFlag()){
						
						
							if(!(dto.getActionType().equalsIgnoreCase("c") || dto.getActionType().equalsIgnoreCase("d")))
							{
								dto.setMessage("Enter either c or d for CREATION/DELETION | ");
								dto.setErrFlag(true);
								returnBulkMstrDTO.add(dto);
								continue;
							}
							
							if(dto.getChannelPartnerId().equals("") || dto.getThreshold().equals(""))
							{
								dto.setMessage("Channel Partner and threshold values are mandatory fields |");
								dto.setErrFlag(true);
								returnBulkMstrDTO.add(dto);
								continue;
							}
							
							if(Integer.parseInt(dto.getThreshold())<=0) 
							{
								dto.setMessage("Threshold value must be a positive integer |");
								dto.setErrFlag(true);
								returnBulkMstrDTO.add(dto);
								continue;
							}
					
					}
					
					
					else if(!dto.isErrFlag() && dto.getActionType().equalsIgnoreCase("c") )
					{
						    
							
							ps1.setString(1,dto.getChannelPartnerId());
							rs1=ps1.executeQuery();
							while(rs1.next())
							{
							olmid = rs1.getString("USER_LOGIN_ID");
							}
							if(olmid == null || olmid == "")
							{
								
							dto.setMessage("Invalid Olm ID.");
							dto.setErrFlag(true);
							}
							else
							{
								
								ps2.setString(1,dto.getChannelPartnerId());  //checking for existing
								rs2=ps2.executeQuery();
								if(rs2.next())                               //update
								{
									ps3.setInt(1,Integer.parseInt(dto.getThreshold()));  
									ps3.setString(2,userBean.getUserLoginId());
									ps3.setString(3, userBean.getIpaddress());
									ps3.setString(4,dto.getChannelPartnerId());  
									ps3.executeUpdate();
									dto.setMessage("Capability updated successfully!");
									
								}
								else                                          //else insert
								{
									ps5.setString(1,dto.getChannelPartnerId()); 
									ps5.setInt(2,Integer.parseInt(dto.getThreshold()));  
									ps5.setString(3,userBean.getUserLoginId());
									ps5.setString(4, userBean.getIpaddress());
									ps5.executeUpdate();	
									dto.setMessage("Capability inserted successfully!");
								}
								
							}
							
							returnBulkMstrDTO.add(dto);
							continue;
						    }
						
				
					else if(!dto.isErrFlag() && dto.getActionType().equalsIgnoreCase("d"))    //deletion
				{
					ps4.setString(1,dto.getChannelPartnerId());
					ps4.executeUpdate();
				}
			}
			
			catch(Exception e)
				{
					dto.setMessage("Capability not inserted.");
					returnBulkMstrDTO.add(dto);
					e.printStackTrace();
				}
			
			}

		}catch (Exception e) {
			e.printStackTrace();
			dto.setMessage("Capability not inserted.");
			returnBulkMstrDTO.add(dto);
			
		} finally {
			try {
				DBConnection.releaseResources(con, ps, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return returnBulkMstrDTO;

	}
	
	

	public ArrayList<BulkMstrDTO> escalationUpload(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean)throws DAOException
	{	
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		BulkMstrDTO dto = null;
		String olmid="";
		ResultSet rs1=null;
		ArrayList<BulkMstrDTO> arr= new ArrayList<BulkMstrDTO>();
		ArrayList<BulkMstrDTO> returnBulkMstrDTO = new ArrayList<BulkMstrDTO>();
		
		try {
			
			BulkMstrDTO bulkMstrDTO = new BulkMstrDTO();
			con = DBConnection.getDBConnection();

			ps =con.prepareStatement("INSERT INTO ESCALATION_MSTR(STAGE, LEVEL1_ID, PARTNER_ID,LEVEL2_ID,UPDATED_DT ,UPDATED_BY,COMPOSITE_KEY) VALUES(?, ?, ?, ?,current_timestamp,?,?)");
			//ps1 = con.prepareStatement("SELECT * FROM ESCALATION_MSTR where Upper(STAGE) =Upper(?)and UPPER(LEVEL1_ID) = UPPER (?) and upper(PARTNER_ID) = Upper(?)and upper(LEVEL2_ID)=upper(?) with ur");
					
			for (Iterator<BulkMstrDTO> itr = listBulkDto.iterator();itr.hasNext();) 
			{
				try
				{
					
					dto = (BulkMstrDTO)itr.next();
					if(!dto.isErrFlag()){
					boolean validLevelId1 = CommonMstrUtil.isValidOlmId(dto.getLevelId1());
					boolean validLevelId2 = CommonMstrUtil.isValidOlmId(dto.getLevelId2());
					boolean validPartnerId = CommonMstrUtil.isValidOlmId(dto.getPartnerId());
					boolean validStage = CommonMstrUtil.isValidStage(dto);
					if(!validStage){
						dto.setMessage("Invalid Stage");
						dto.setErrFlag(true);
						returnBulkMstrDTO.add(dto);
						continue;
					}
					else if(!validLevelId1) {
						dto.setMessage("Invalid Level 1 Id");
						dto.setErrFlag(true);
						returnBulkMstrDTO.add(dto);
						continue;
					}
					else if(!validLevelId2){
						dto.setMessage("Invalid Level 2 Id");
						dto.setErrFlag(true);
						returnBulkMstrDTO.add(dto);
						continue;						
					}
					else if (!validPartnerId){
						dto.setMessage("Invalid Partner ID");
						dto.setErrFlag(true);
						returnBulkMstrDTO.add(dto);
						continue;
					}
					
					
					String compositeKey = dto.getStage()+"~"+dto.getPartnerId();
					ps.setString(1, dto.getStage());
					ps.setString(2, dto.getLevelId1());
					ps.setString(3, dto.getPartnerId());
					ps.setString(4, dto.getLevelId2());
					ps.setString(5, userBean.getUserLoginId());
					ps.setString(6, compositeKey);
					try{
					ps.executeUpdate();
					}
					catch(SQLException e){
						if(e.getErrorCode()==-803){
							dto.setMessage("Duplicate Record");
							dto.setErrFlag(true);
							returnBulkMstrDTO.add(dto);
							continue;
						}
					}
					dto.setMessage("Record Successfully Updated in Escalation Master");
					returnBulkMstrDTO.add(dto);
					continue;
				}
					returnBulkMstrDTO.add(dto);

				}
			
			catch(Exception e)
				{
					dto.setMessage("Uplaod in Escalation Master not successful");
					returnBulkMstrDTO.add(dto);
					e.printStackTrace();
				}
			
			}

		}catch (Exception e) {
			e.printStackTrace();
			dto.setMessage("Uplaod in Escalation Master not successful");
			returnBulkMstrDTO.add(dto);
			
		} finally {
			try {
				DBConnection.releaseResources(con, ps, null);
				//rs1.close();
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return returnBulkMstrDTO;

	}
}




