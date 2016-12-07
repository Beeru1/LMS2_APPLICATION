package com.ibm.lms.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;


import com.ibm.lms.common.CommonMstrUtil;
import com.ibm.lms.common.Constants;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.common.SendMail;
import com.ibm.lms.dao.BulkAssigmentDao;
import com.ibm.lms.dto.AssignmentReportDTO;
import com.ibm.lms.dto.BulkAssignmentDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.engine.dao.UpdateLeadDataDAO;
import com.ibm.lms.engine.dao.impl.UpdateLeadDataDAOImpl;
import com.ibm.lms.engine.dataobjects.UpdateLeadDataDO;
import com.ibm.lms.engine.exception.LmsException;
import com.ibm.lms.engine.util.ServerPropertyReader;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.wf.actions.AssignmentMatrixAction;

public class BulkAssigmentDaoImpl implements BulkAssigmentDao {
	private static Logger logger = Logger.getLogger(BulkAssigmentDaoImpl.class.getName());
	
	protected static final String SQL_INSERT_ASSIGNMENT ="INSERT INTO ASSIGNMENT_MATRIX(OLM_ID, " +
	" ASSIGNMENT_KEY, PRODUCT_LOB_ID, CIRCLE_ID, CITY_ID, ZONE_ID, PINCODE, RSU_ID, PRIMARY_AUTH, " +
	" STATUS, CREATED, CREATED_BY, UPDATED, UPDATED_BY, USER_TYPE, LEVEL_ID,PRODUCT_ID,CITY_ZONE_CODE,CHANNEL_PARTNER_ID,LEVEL1_CC,LEVEL2_CC,LEVEL3_CC,LEVEL4_CC,REQUEST_CATEGORY_ID,IPADDRESS) " +
	" VALUES(?,?,?,?,?,?,?,?,?,'A',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) WITH UR ";	
	
	
    protected static final String APPROVED_MATRIX="SELECT OLM_ID,ASSIGNMENT_KEY, PRODUCT_LOB_ID, CIRCLE_ID, CITY_ID, ZONE_ID, PINCODE, RSU_ID, PRIMARY_AUTH, "+ 
	 "STATUS, CREATED, CREATED_BY, UPDATED, UPDATED_BY, USER_TYPE, LEVEL_ID,PRODUCT_ID,CITY_ZONE_CODE,CHANNEL_PARTNER_ID,LEVEL1_CC,LEVEL2_CC,LEVEL3_CC,LEVEL4_CC,REQUEST_CATEGORY_ID,UPLOAD_TYPE,APPROVER_L1,APPROVER_L2,IPADDRESS "+
	 "FROM ASSIGNMENT_MATRIX_TEMP WHERE STATUS_L1='2' AND STATUS_L2='2' AND STATUS='A' WITH UR";

    protected static final String APPROVED_MATRIX_DELETION="UPDATE ASSIGNMENT_MATRIX_TEMP SET STATUS='D' WHERE STATUS_L1='2' AND STATUS_L2='2' AND STATUS='A' WITH UR";	
    
	protected static final String SQL_INSERT_ASSIGNMENT_TEMP ="INSERT INTO ASSIGNMENT_MATRIX_TEMP(OLM_ID, " +
	" ASSIGNMENT_KEY, PRODUCT_LOB_ID, CIRCLE_ID, CITY_ID, ZONE_ID, PINCODE, RSU_ID, PRIMARY_AUTH, " +
	" STATUS, CREATED, CREATED_BY, UPDATED, UPDATED_BY, USER_TYPE, LEVEL_ID,PRODUCT_ID,CITY_ZONE_CODE,CHANNEL_PARTNER_ID,LEVEL1_CC,LEVEL2_CC,LEVEL3_CC,LEVEL4_CC,REQUEST_CATEGORY_ID,UPLOAD_TYPE,APPROVER_L1,APPROVER_L2,STATUS_L1,STATUS_L2,IPADDRESS) " +
	" VALUES(?,?,?,?,?,?,?,?,?,'A',CURRENT TIMESTAMP,?,CURRENT TIMESTAMP,'',?,?,?,?,?,?,?,?,?,?,?,?,?,0,0,?) WITH UR ";	

	protected static final String SQL_SOFT_DELETE_ASSIGNMENT = "UPDATE ASSIGNMENT_MATRIX SET STATUS='D'," +
	" UPDATED= CURRENT TIMESTAMP, UPDATED_BY= ? WHERE OLM_ID = ? AND ASSIGNMENT_KEY = ? " +
	" AND PRIMARY_AUTH= ?  AND STATUS = 'A' WITH UR ";
	
	protected static final String SQL_SOFT_DELETE_ASSIGNMENT_TEMP = "UPDATE ASSIGNMENT_MATRIX_TEMP SET STATUS=?," +
	" UPDATED= CURRENT TIMESTAMP ,UPDATED_BY= ? ,STATUS_L1=? , COMMENTS_L1=? WHERE OLM_ID = ? AND ASSIGNMENT_KEY = ? " +
	" AND PRIMARY_AUTH= ? WITH UR ";
	
	protected static final String SQL_SOFT_DELETE_ASSIGNMENT_TEMP_L2 = "UPDATE ASSIGNMENT_MATRIX_TEMP SET STATUS=?," +
	" UPDATED= CURRENT TIMESTAMP ,UPDATED_BY= ? ,STATUS_L2=? , COMMENTS_L2=? WHERE OLM_ID = ? AND ASSIGNMENT_KEY = ? " +
	" AND PRIMARY_AUTH= ? WITH UR ";
	
	protected static final String SQL_SOFT_DELETE_ASSIGNMENT_NEW="UPDATE ASSIGNMENT_MATRIX SET STATUS='D'," +
	" UPDATED= CURRENT TIMESTAMP, UPDATED_BY= ? WHERE  CHANNEL_PARTNER_ID = ? " +
	" AND STATUS = 'A' AND PRODUCT_LOB_ID= ? AND CIRCLE_ID=? AND PRODUCT_ID =? ";
	
	protected static final String SQL_SOFT_DELETE_ASSIGNMENT_TEMP_NEW="UPDATE ASSIGNMENT_MATRIX_TEMP SET STATUS='D'," +
	" UPDATED= CURRENT TIMESTAMP, UPDATED_BY= ? WHERE  CHANNEL_PARTNER_ID = ? " +
	" AND STATUS = 'A' AND PRODUCT_LOB_ID= ? AND CIRCLE_ID=? AND PRODUCT_ID =? ";
	
	
	/*protected static final String SQL_SOFT_DELETE_ASSIGNMENT_NEW = "UPDATE ASSIGNMENT_MATRIX SET STATUS='D'," +
	" UPDATED= CURRENT TIMESTAMP, UPDATED_BY= ? WHERE OLM_ID = ? AND ASSIGNMENT_KEY = ? " +
	" AND PRIMARY_AUTH= ?  AND USER_TYPE= ? AND STATUS = 'A' WITH UR ";
	 */

	protected static final String SQL_REASSIGN_ASSIGNMENT = "UPDATE ASSIGNMENT_MATRIX SET STATUS='A'," +
	" UPDATED= CURRENT TIMESTAMP, UPDATED_BY= ? WHERE OLM_ID = ? AND ASSIGNMENT_KEY = ? " +
	" AND PRIMARY_AUTH= ? AND STATUS = 'D' WITH UR ";

	protected static final String SQL_REASSIGN_ASSIGNMENT_TEMP = "UPDATE ASSIGNMENT_MATRIX_TEMP SET STATUS='A'," +
	" UPDATED= CURRENT TIMESTAMP, UPDATED_BY= ? WHERE OLM_ID = ? AND ASSIGNMENT_KEY = ? " +
	" AND PRIMARY_AUTH= ? AND STATUS = 'D' WITH UR ";

	protected static final String SQL_SELECT_ASSIGNMENT_KEY = "SELECT ASSIGNMENT_KEY FROM ASSIGNMENT_MATRIX " +
	" WHERE OLM_ID = ? AND ASSIGNMENT_KEY = ? AND STATUS = ? WITH UR ";
	
	
	protected static final String SQL_SELECT_ASSIGNMENT__TEMP_KEY = "SELECT ASSIGNMENT_KEY FROM ASSIGNMENT_MATRIX_TEMP " +
	" WHERE OLM_ID = ? AND ASSIGNMENT_KEY = ? AND STATUS = ? WITH UR ";
	
	protected static final String SQL_CHECK_ASSIGNMENT_KEY_AS_PRIMARY = "SELECT ASSIGNMENT_KEY FROM " +
			" ASSIGNMENT_MATRIX WHERE ASSIGNMENT_KEY = ? AND STATUS = ? AND PRIMARY_AUTH= 1 WITH UR ";

	/*protected static final String SQL_DELETE_ASSIGNMENT = "DELETE FROM ASSIGNMENT_MATRIX WHERE   " +
			" OLM_ID = ? AND ASSIGNMENT_KEY = ? AND PRIMARY_AUTH= ? AND STATUS = 'D' WITH UR ";
	 */
	
	/* Commented by Parnika for LMS Phase 2 
	protected static final String SQL_SELECT_ASSIGNMENT_MATRIX="SELECT a.OLM_ID,a.ASSIGNMENT_KEY,a.STATUS,a.PINCODE,a.PRIMARY_AUTH,(SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = a.PRODUCT_LOB_ID) AS PRODUCT_LOB," +
			"(SELECT CIRCLE_NAME FROM CIRCLE_MSTR WHERE CIRCLE_ID = a.CIRCLE_ID) AS CIRCLE,(SELECT CITY_NAME FROM CITY_MSTR WHERE CITY_CODE = a.CITY_ID) AS CITY," +
			"(SELECT ZONE_NAME FROM ZONE_MSTR WHERE ZONE_CODE = a.ZONE_ID) AS ZONE,(SELECT USER_FNAME FROM USER_MSTR WHERE USER_LOGIN_ID=a.OLM_ID)AS FNAME," +
			"(SELECT USER_LNAME  FROM USER_MSTR WHERE USER_LOGIN_ID=a.OLM_ID)AS LNAME FROM ASSIGNMENT_MATRIX a WHERE STATUS = 'A'";

	 */
	
	/* Added By Parnika for LMS Phase 2 */
	
	protected static final String SQL_SELECT_ASSIGNMENT_MATRIX = "SELECT a.OLM_ID,a.ASSIGNMENT_KEY,a.STATUS,a.PINCODE,a.PRIMARY_AUTH,(SELECT PRODUCT_NAME FROM PRODUCT_MSTR WHERE PRODUCT_ID = a.PRODUCT_ID)," +
			"(SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = a.PRODUCT_LOB_ID) AS PRODUCT_LOB," +
			"(SELECT CIRCLE_NAME FROM CIRCLE_MSTR WHERE CIRCLE_ID = a.CIRCLE_ID and LOB_ID = a.PRODUCT_LOB_ID ) AS CIRCLE," +
			"(SELECT ZONE_NAME FROM ZONE_MSTR WHERE ZONE_CODE = a.ZONE_ID) AS ZONE," +
			"(SELECT CITY_NAME FROM CITY_MSTR WHERE CITY_CODE = a.CITY_ID) AS CITY,(SELECT CITY_ZONE_NAME FROM CITY_ZONE_MSTR " +
			"WHERE CITY_ZONE_CODE = a.CITY_ZONE_CODE) AS CITY_ZONE,(SELECT USER_FNAME FROM USER_MSTR WHERE USER_LOGIN_ID=a.OLM_ID)AS FNAME," +
			"(SELECT USER_LNAME  FROM USER_MSTR WHERE USER_LOGIN_ID=a.OLM_ID)AS LNAME FROM ASSIGNMENT_MATRIX a WHERE STATUS = 'A' ";
	
	
	
	protected static final String SQL_SOFT_DEACTIVATE_ASSIGNMENT_TEMP="UPDATE ASSIGNMENT_MATRIX_TEMP SET STATUS='D'," +
	" UPDATED= CURRENT TIMESTAMP, UPDATED_BY= ? WHERE OLM_ID = ? AND ASSIGNMENT_KEY = ? " +
	" AND PRIMARY_AUTH= ?  AND STATUS = 'A' WITH UR ";
	
	protected static final String VIEW_ASSIGNMENTS="SELECT a.OLM_ID,(SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = a.PRODUCT_LOB_ID) AS PRODUCT_LOB,"+
"(SELECT PRODUCT_NAME FROM PRODUCT_MSTR WHERE PRODUCT_ID = a.PRODUCT_ID) as PRODUCT,a.PRIMARY_AUTH,a.PINCODE,a.RSU_ID,"+
"(SELECT CIRCLE_NAME FROM CIRCLE_MSTR WHERE CIRCLE_ID = a.CIRCLE_ID and LOB_ID = a.PRODUCT_LOB_ID ) AS CIRCLE,(SELECT ZONE_NAME FROM ZONE_MSTR WHERE ZONE_CODE = a.ZONE_ID) AS ZONE, (SELECT CITY_NAME FROM CITY_MSTR WHERE CITY_CODE = a.CITY_ID) AS CITY,(SELECT CITY_ZONE_NAME FROM CITY_ZONE_MSTR "+ 
"WHERE CITY_ZONE_CODE = a.CITY_ZONE_CODE) AS CITY_ZONE,a.CREATED,a.USER_TYPE,a.CHANNEL_PARTNER_ID,a.APPROVER_L1,a.APPROVER_L2,a.STATUS_L1,a.STATUS_L2,a.COMMENTS_L1,a.COMMENTS_L2 "+
"FROM ASSIGNMENT_MATRIX_TEMP a where ((a.STATUS_L1=1 and a.STATUS='D') or (a.STATUS_L2=1 and a.STATUS='D') or (a.STATUS='A' and a.STATUS_L1=0) or (a.STATUS='A' and a.STATUS_L2=0 and a.STATUS_L1!=0)) and a.CREATED_BY=? with ur";

	
		/* End of changes By parnika */
	
	//Bulk Assignment Matrix Upload method
	
	/*public String insertAssignment(ArrayList<BulkAssignmentDto> validListInsert,UserMstr userBean) 
	throws DAOException
	{	
		Connection con = null;
		PreparedStatement ps = null;
		BulkAssignmentDto dto = null;
		String msg = "success";
		int i=0;
	
		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_INSERT_ASSIGNMENT);

			for (Iterator<BulkAssignmentDto> itr = validListInsert.iterator();itr.hasNext();) 
			{
				try
				{
				
				dto = (BulkAssignmentDto) itr.next();
				boolean booleanValue=dto.isAssignment();
				//logger.info("boolean value>>>>>>>>>>>>>>>"+booleanValue);
				
				if(dto.getLevelType().equalsIgnoreCase("a")){
				ps.setString(1,dto.getOlmId().toUpperCase());
				ps.setString(2,dto.getAssignmentKey().toUpperCase());
				ps.setInt(3,Integer.parseInt(dto.getProductLobId()));
				ps.setInt(4,Integer.parseInt(dto.getCircle()));
				ps.setString(5,dto.getCity());
				ps.setString(6,dto.getCityZone());
				ps.setString(7,dto.getPincode());
				ps.setString(8,dto.getRsu());
				ps.setInt(9,Constants.PRIMARY_ASSIGNMENT);
				ps.setString(10,userBean.getUserLoginId());
				ps.setString(11,dto.getUserType());
				ps.setString(12,"0");
				ps.setInt(13,Integer.parseInt(dto.getProductId()));
				ps.setString(14,dto.getCityZoneCode());
				ps.setString(15,dto.getOlmId());
				ps.setString(16,"");
				ps.setString(17,"");
				ps.setString(18,"");
				ps.setString(19,"");
				
				
			
				if(isAssignmentKeyExist(dto.getOlmId(),dto.getAssignmentKey(),Constants.PRIMARY_ASSIGNMENT,
						Constants.DEACTIVE_ASSIGNMENT))
					reAssignment(dto.getOlmId(),dto.getAssignmentKey(),
							Constants.PRIMARY_ASSIGNMENT,userBean.getUserLoginId(),con);
				else  
				i=ps.executeUpdate();
				}
				if(dto.getSecondaryOlmId().length() > 0)
				insertSecondaryOlmId(dto,userBean,con);
				
			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
				
			}	

		}catch (Exception e) {
			e.printStackTrace();
			msg = "error";
			return msg;
		} finally {
			try {
				DBConnection.releaseResources(con, ps, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		return msg;

	}

	//secondary OlmId Method
	
	public void insertSecondaryOlmId(BulkAssignmentDto dto,UserMstr userBean,Connection con) 
	throws DAOException
	{	
		PreparedStatement ps = null;
		int j=1;

		try {
			ps = con.prepareStatement(SQL_INSERT_ASSIGNMENT);

			String secOlmIds[] = dto.getSecondaryOlmId().split(",");

			for(int i = 0 ; i < secOlmIds.length ;i++)
			{
				try
				{
						ps.setString(1,secOlmIds[i].toUpperCase());
						ps.setString(2,dto.getAssignmentKey().toUpperCase());
						ps.setInt(3,Integer.parseInt(dto.getProductLobId()));
						ps.setInt(4,Integer.parseInt(dto.getCircle()));
						ps.setString(5,dto.getCity());
						ps.setString(6,dto.getCityZone());
						ps.setString(7,dto.getPincode());
						ps.setString(8,dto.getRsu());
						ps.setInt(9,Constants.SECONDARY_ASSIGNMENT);
						ps.setString(10,userBean.getUserLoginId());
						ps.setString(11,dto.getUserType());
						if(dto.getLevelType().equalsIgnoreCase("a"))
						{
							//logger.info("if loop");
							ps.setString(12,"0");
							ps.setString(16,"");
							ps.setString(17,"");
							ps.setString(18,"");
							ps.setString(19,"");
						}
						else
						{	
							//logger.info("else loop");
							ps.setString(12,Integer.toString(j));
							j++;
							ps.setString(16,dto.getLevellCC());
							ps.setString(17,dto.getLevel2CC());
							ps.setString(18,dto.getLevel3CC());
							ps.setString(19,dto.getLevel4CC());
						}
						
						ps.setInt(13,Integer.parseInt(dto.getProductId()));
						ps.setString(14,dto.getCityZoneCode());
						ps.setString(15,dto.getOlmId());	
					
				if(isAssignmentKeyExist(secOlmIds[i],dto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,	Constants.DEACTIVE_ASSIGNMENT)){
					reAssignment(secOlmIds[i],dto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,userBean.getUserLoginId(),con);
				}
				else 
				if(!isAssignmentKeyExist(secOlmIds[i],dto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,Constants.ACTIVE_ASSIGNMENT))
					ps.executeUpdate();
					//ps.addBatch();
				
				}
			
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
			
		//ps.executeBatch();

		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in insertSecondaryOlmId method :  "+ e.getMessage(),e);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}

	}
	
	*/
	// Assignment Matrix Creation for Single and Bulk
	
private static BulkAssigmentDaoImpl bulkAssigmentDaoImpl=null;
	
	public BulkAssigmentDaoImpl(){
		
	}
	
	
	
	public static BulkAssigmentDaoImpl bulkAssigmentDaoInstance()
	{
		if(bulkAssigmentDaoImpl==null)
		{
			bulkAssigmentDaoImpl = new BulkAssigmentDaoImpl();
		}
		return bulkAssigmentDaoImpl;
		
	}
	
	
	
	public String insertAssignment(ArrayList<BulkAssignmentDto> validListInsert,UserMstr userBean,String flag)throws DAOException
	{	
		logger.info("inside insert assignment for main table*************************");
		Connection con = null;
		PreparedStatement ps = null;
		BulkAssignmentDto dto = null;
		String msg = "success";
		int i=0;
		java.util.Date date= new java.util.Date();
		MasterService mstrServvice= new MasterServiceImpl();
		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_INSERT_ASSIGNMENT);
			for (Iterator<BulkAssignmentDto> itr = validListInsert.iterator();itr.hasNext();) 
			{
				try
				{
				
				dto = (BulkAssignmentDto) itr.next();
				if(dto.isAssignment() == false){
				ps.setString(1,dto.getOlmId().toUpperCase());
				ps.setInt(3,Integer.parseInt(dto.getProductLobId()));
				ps.setInt(4,Integer.parseInt(dto.getCircle()));
				ps.setString(5,dto.getCity());
				ps.setString(6,dto.getCityZone());
				ps.setString(7,dto.getPincode());
				ps.setString(8,dto.getRsu());
				ps.setString(14,dto.getUserType());//user type
				
                if(dto.getProductId()!=null & !("").equalsIgnoreCase(dto.getProductId()))
                {
				ps.setInt(16,Integer.parseInt(dto.getProductId()));
                }
                else
                {
                ps.setInt(16,-1);	
                }
				ps.setString(17,dto.getCityZoneCode());
				ps.setString(23,dto.getRequestCategoryId());
				
				
				if("Y".equalsIgnoreCase(mstrServvice.getParameterName(PropertyReader.getAppValue("Assignment.approval.activate.flag"))) || flag.equalsIgnoreCase("temp"))
				{ //temp block
				ps.setString(2,dto.getAssignmentKey());
				ps.setInt(9, dto.getPrimaryAuth());
				ps.setString(10,dto.getCreateTime());
				ps.setString(11,dto.getCreatedBy());
				ps.setString(12,dto.getUpdateTime());
				ps.setString(13,dto.getUpdateBy());
				ps.setString(15,dto.getLevelId());
				ps.setString(18,dto.getChannelPartnerId());//channel partner id
				ps.setString(19,dto.getLevellCC());
				ps.setString(20,dto.getLevel2CC());
				ps.setString(21,dto.getLevel3CC());
				ps.setString(22,dto.getLevel4CC());
				ps.setString(24,dto.getIpAddress());
			
				}
				else
				{// else main block
					String assignmentKey = dto.getAssignmentKey().toUpperCase() + "A~" +"0" +"~"+ dto.getUserType() +"~"+ dto.getOlmId();
					
					ps.setString(2,assignmentKey);
					ps.setInt(9,Constants.PRIMARY_ASSIGNMENT);
					ps.setTimestamp(10,new Timestamp(date.getTime()));
					ps.setString(11,userBean.getUserLoginId());
					ps.setTimestamp(12,new Timestamp(date.getTime()));
					ps.setString(13,""); //updated by
					ps.setString(15,"0");
					ps.setString(18,dto.getOlmId());//channel partner id
					ps.setString(19,"");
					ps.setString(20,"");
					ps.setString(21,"");
					ps.setString(22,"");
					ps.setString(24,userBean.getIpaddress());
					
					
				}
				
			
				if(isAssignmentKeyExist(dto.getOlmId(),dto.getAssignmentKey(),Constants.PRIMARY_ASSIGNMENT,Constants.DEACTIVE_ASSIGNMENT, 0, dto.getUserType(), dto.getOlmId(),0) && !(flag.equalsIgnoreCase("temp")))
				   reAssignment(dto.getOlmId(),dto.getAssignmentKey(),Constants.PRIMARY_ASSIGNMENT,dto.getCreatedBy(),con, 0, dto.getUserType(), dto.getOlmId(),0);
				
				else if(isAssignmentKeyExist(dto.getOlmId(),dto.getAssignmentKey(),Constants.PRIMARY_ASSIGNMENT,Constants.ACTIVE_ASSIGNMENT, 0, dto.getUserType(), dto.getOlmId(),0) && !(flag.equalsIgnoreCase("temp")))
				{}
				
				else 
					
				i=ps.executeUpdate();
				
				logger.info("Assignment Matrix updated value"+i);
				
				}
				
				if(dto.getSecondaryOlmId().length() > 0 && !("Y").equalsIgnoreCase(mstrServvice.getParameterName(PropertyReader.getAppValue("Assignment.approval.activate.flag"))) && !(flag.equalsIgnoreCase("temp")))
				{
				insertSecondaryOlmId1(dto,userBean,con);
				insertSecondaryOlmId2(dto,userBean,con);
				}
			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
				
			}	

		}
		catch (Exception e) {
			e.printStackTrace();
			msg = "error";
			return msg;
		} finally {
			try {
				DBConnection.releaseResources(con, ps, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		return msg;

	}

	
	public String insertTempAssignment(ArrayList<BulkAssignmentDto> validListInsert,UserMstr userBean) 
	throws DAOException
	{	
		Connection con = null;
		PreparedStatement ps = null;
		BulkAssignmentDto dto = null;
		String msg = "";
		int i=0;
		String resul="";
		String l1app="";
		String l2app="";
		
		try {
			
			con = DBConnection.getDBConnection();
			
    		
			ps = con.prepareStatement(SQL_INSERT_ASSIGNMENT_TEMP);
			
		//	logger.info("validListInsert.size^^^^^^^^^^^^^^^^^^^^^^^^^^^^6"+validListInsert.size());
			
			for (Iterator<BulkAssignmentDto> itr = validListInsert.iterator();itr.hasNext();) 
			{
				try
				{
				
				dto = (BulkAssignmentDto) itr.next();
			//	logger.info("dto.isAssignment()&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+dto.isAssignment());	
				if(dto.isAssignment() == false)
				{
				ps.setString(1,dto.getOlmId().toUpperCase());
				String assignmentKey = dto.getAssignmentKey().toUpperCase() + "A~" +"0" +"~"+ dto.getUserType() +"~"+ dto.getOlmId();
				ps.setString(2,assignmentKey);
			//	logger.info("assignmentKey&&&&&&&&&&&&&&&&&&&&&&&"+assignmentKey);
				ps.setInt(3,Integer.parseInt(dto.getProductLobId()));
				ps.setInt(4,Integer.parseInt(dto.getCircle()));
				ps.setString(5,dto.getCity());
				ps.setString(6,dto.getCityZone());
				ps.setString(7,dto.getPincode());
				ps.setString(8,dto.getRsu());
				ps.setInt(9,Constants.PRIMARY_ASSIGNMENT);
				ps.setString(10,userBean.getUserLoginId());
				ps.setString(11,dto.getUserType());
				ps.setString(12,"0");
				ps.setInt(13,Integer.parseInt(dto.getProductId()));
				ps.setString(14,dto.getCityZoneCode());
				ps.setString(15,dto.getOlmId()); //channel partner id
				
				ps.setString(16,"");
				ps.setString(17,"");
				ps.setString(18,"");
				ps.setString(19,"");
					
				ps.setString(20,dto.getRequestCategoryId());
				ps.setString(21,dto.getUploadType());
				resul=getApproversList(userBean.getUserLoginId().toUpperCase());
				if(resul !=null && resul.contains(",")) 
				{
					l1app=resul.split(",")[0];
					l2app=resul.split(",")[1];
					
				}
				
				ps.setString(22,l1app);  //L1 approver
				ps.setString(23,l2app);  //L2 approver
				ps.setString(24,userBean.getIpaddress());
				
		
				if(isAssignmentTempKeyExist(dto.getOlmId(),dto.getAssignmentKey(),Constants.PRIMARY_ASSIGNMENT,Constants.DEACTIVE_ASSIGNMENT, 0, dto.getUserType(), dto.getOlmId(),0))
				{
				
				reAssignmentTemp(dto.getOlmId(),dto.getAssignmentKey(),Constants.PRIMARY_ASSIGNMENT,userBean.getUserLoginId(),con, 0, dto.getUserType(), dto.getOlmId(),0);
				msg="Assignment matrix uploaded successfully!";
				
				}
				else
				{
				i=ps.executeUpdate();
				logger.info("Assignment Matrix updated value"+i);
				msg="Assignment matrix uploaded successfully!";
				}
				}
				
				if(dto.getSecondaryOlmId().length() > 0)
				{
				insertSecondaryOlmId1Temp(dto,userBean,con);
				insertSecondaryOlmId2Temp(dto,userBean,con);
				}
			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
				
			}	

		}
		catch (Exception e) {
			e.printStackTrace();
			msg = "error";
			return msg;
		} finally {
			try {
				DBConnection.releaseResources(con, ps, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		return msg;

	}

	
	//secondary OlmId first Method
	
	public void insertSecondaryOlmId1(BulkAssignmentDto dto,UserMstr userBean,Connection con) 
	throws DAOException
	{	java.util.Date date= new java.util.Date();
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(SQL_INSERT_ASSIGNMENT);

			String secOlmIds[] = dto.getSecondaryOlmId().split(",");

			for(int i = 0 ; i < secOlmIds.length ;i++)
			{
				try
				{
					  	ps.setString(1,secOlmIds[i].toUpperCase());
						String assignmentKey = dto.getAssignmentKey().toUpperCase() + "A~" +"0" +"~"+ dto.getUserType() +"~"+ dto.getOlmId();
						ps.setString(2,assignmentKey);
						ps.setInt(3,Integer.parseInt(dto.getProductLobId()));
						ps.setInt(4,Integer.parseInt(dto.getCircle()));
						ps.setString(5,dto.getCity());
						ps.setString(6,dto.getCityZone());
						ps.setString(7,dto.getPincode());
						ps.setString(8,dto.getRsu());
						ps.setInt(9,Constants.SECONDARY_ASSIGNMENT);
						ps.setTimestamp(10,new Timestamp(date.getTime()));
						ps.setString(11,userBean.getUserLoginId());
						ps.setTimestamp(12,new Timestamp(date.getTime()));
						ps.setString(13,""); //updated by
						ps.setString(14,dto.getUserType());
						if(dto.isAssignment() == false)
						{
							ps.setString(15,"0");
							ps.setString(19,"");
							ps.setString(20,"");
							ps.setString(21,"");
							ps.setString(22,"");
						}
						ps.setInt(16,Integer.parseInt(dto.getProductId()));
						ps.setString(17,dto.getCityZoneCode());
						ps.setString(18,dto.getOlmId());
						ps.setString(23, dto.getRequestCategoryId());
						ps.setString(24, dto.getIpAddress());
						
				if(isAssignmentKeyExist(secOlmIds[i],dto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,	Constants.DEACTIVE_ASSIGNMENT, 0, dto.getUserType(), dto.getOlmId(),0))
				reAssignment(secOlmIds[i],dto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,userBean.getUserLoginId(),con,0,dto.getUserType(),dto.getOlmId(),0);
				
				/*else if(isAssignmentKeyExist(secOlmIds[i],dto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,Constants.ACTIVE_ASSIGNMENT, 0, dto.getUserType(), dto.getOlmId(), "0"))
				{}
				*/	
				
				else 
				ps.executeUpdate();
								
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
		}
			}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in insertSecondaryOlmId method :  "+ e.getMessage(),e);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}

	}
	
	
	
//secondary OlmId second Method
	
	public void insertSecondaryOlmId2(BulkAssignmentDto dto,UserMstr userBean,Connection con) 
	throws DAOException
	{	java.util.Date date= new java.util.Date();
		PreparedStatement ps = null;
		int j=1;
		
		try {
			ps = con.prepareStatement(SQL_INSERT_ASSIGNMENT);

			String secOlmIds[] = dto.getSecondaryOlmId().split(",");

			for(int k = 0 ; k < secOlmIds.length ;k++)
							{
								try
								{
								
										ps.setString(1,secOlmIds[k].toUpperCase());
										String assignmentKey = dto.getAssignmentKey().toUpperCase() + "E~" +j +"~"+ dto.getUserType() +"~" +dto.getOlmId();
										ps.setString(2,assignmentKey);
										ps.setInt(3,Integer.parseInt(dto.getProductLobId()));
										ps.setInt(4,Integer.parseInt(dto.getCircle()));
										ps.setString(5,dto.getCity());
										ps.setString(6,dto.getCityZone());
										ps.setString(7,dto.getPincode());
										ps.setString(8,dto.getRsu());
										ps.setInt(9,Constants.SECONDARY_ASSIGNMENT);
										ps.setTimestamp(10,new Timestamp(date.getTime()));
										ps.setString(11,userBean.getUserLoginId());
										ps.setTimestamp(12,new Timestamp(date.getTime()));
										ps.setString(13,""); //updated by
     									ps.setString(14,dto.getUserType());
										
     									ps.setString(15,Integer.toString(j));
										
										if(dto.getLevellCC() == null)
										{
											ps.setString(19,"");
										}
										else
										{
											ps.setString(19,dto.getLevellCC());
										}
										if(dto.getLevel2CC() == null)
										{
											ps.setString(20,"");
										}
										else
										{
											ps.setString(20,dto.getLevel2CC());
										}
										if(dto.getLevel3CC() == null)
										{
											ps.setString(21,"");
										}
										else
										{
											ps.setString(21,dto.getLevel3CC());
										}
										if(dto.getLevel4CC() == null)
										{
											ps.setString(22,"");
										}
										else
										{
											ps.setString(22,dto.getLevel4CC());
										}
										
										ps.setInt(16,Integer.parseInt(dto.getProductId()));
										ps.setString(17,dto.getCityZoneCode());
										ps.setString(18,dto.getOlmId());
										ps.setString(23,dto.getRequestCategoryId());
										ps.setString(24, dto.getIpAddress());
										
										
								
								//if(isAssignmentKeyExist2(secOlmIds[k],dto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,	Constants.DEACTIVE_ASSIGNMENT, j, dto.getUserType(), dto.getOlmId()))
								if(isAssignmentKeyExist(secOlmIds[k],dto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,	Constants.DEACTIVE_ASSIGNMENT, j, dto.getUserType(), dto.getOlmId(),2))
								reAssignment(secOlmIds[k],dto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,userBean.getUserLoginId(),con,j,dto.getUserType(),dto.getOlmId(),1);
								
								else if(isAssignmentKeyExist(secOlmIds[k],dto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,Constants.ACTIVE_ASSIGNMENT, j, dto.getUserType(), dto.getOlmId(),2))
								{}
								else
								ps.executeUpdate();
								
								j++;
								}
							
								catch(Exception e)
								{
									e.printStackTrace();
								}
								
			}
			}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in insertSecondaryOlmId method :  "+ e.getMessage(),e);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}

	}
	
	
	public void insertSecondaryOlmId1Temp(BulkAssignmentDto dto,UserMstr userBean,Connection con)throws DAOException
	{	
		PreparedStatement ps = null;
		String resul="";
		String l1app="";
		String l2app="";
		
		try {
			ps = con.prepareStatement(SQL_INSERT_ASSIGNMENT_TEMP);
        //logger.info("insertSecondaryOlmId1Temp********************");
			String secOlmIds[] = dto.getSecondaryOlmId().split(",");

			for(int i = 0 ; i < secOlmIds.length ;i++)
			{
				try
				{
						ps.setString(1,secOlmIds[i].toUpperCase());
						String assignmentKey = dto.getAssignmentKey().toUpperCase() + "A~" +"0" +"~"+ dto.getUserType() +"~"+ dto.getOlmId();
						ps.setString(2,assignmentKey);
						ps.setInt(3,Integer.parseInt(dto.getProductLobId()));
						ps.setInt(4,Integer.parseInt(dto.getCircle()));
						ps.setString(5,dto.getCity());
						ps.setString(6,dto.getCityZone());
						ps.setString(7,dto.getPincode());
						ps.setString(8,dto.getRsu());
						ps.setInt(9,Constants.SECONDARY_ASSIGNMENT);
						ps.setString(10,userBean.getUserLoginId());
						ps.setString(11,dto.getUserType());
						
						if(dto.isAssignment() == false)
						{
						
							ps.setString(12,"0");
							ps.setString(16,"");
							ps.setString(17,"");
							ps.setString(18,"");
							ps.setString(19,"");
						}
						
						
						ps.setInt(13,Integer.parseInt(dto.getProductId()));
						ps.setString(14,dto.getCityZoneCode());
						ps.setString(15,dto.getOlmId()); //channel partner id
						ps.setString(20, dto.getRequestCategoryId());
						ps.setString(21, dto.getUploadType());
						
						resul=getApproversList(dto.getOlmId().toUpperCase());
						if(resul !=null && resul.contains(",")) 
						{
							l1app=resul.split(",")[0];
							l2app=resul.split(",")[1];
							
						}
						
						ps.setString(22,l1app);  //L1 approver
						ps.setString(23,l2app);  //L2 approver
						ps.setString(24,userBean.getIpaddress());
						
						if(isAssignmentTempKeyExist(secOlmIds[i],dto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,	Constants.DEACTIVE_ASSIGNMENT, 0, dto.getUserType(), dto.getOlmId(),0))
							reAssignmentTemp(secOlmIds[i],dto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,userBean.getUserLoginId(),con,0,dto.getUserType(),dto.getOlmId(),0);
							
						else if(isAssignmentTempKeyExist(secOlmIds[i],dto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,Constants.ACTIVE_ASSIGNMENT, 0, dto.getUserType(), dto.getOlmId(),0))
						{}
									
						else 
						ps.executeUpdate();
						
				}
					
				
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in insertSecondaryOlmId method :  "+ e.getMessage(),e);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}

	}
	
	
	
//secondary OlmId second Method
	
	public void insertSecondaryOlmId2Temp(BulkAssignmentDto dto,UserMstr userBean,Connection con)throws DAOException
	{	
		PreparedStatement ps = null;
		int j=1;
		String resul="";
		String l1app="";
		String l2app="";
		
		
		try {
			ps = con.prepareStatement(SQL_INSERT_ASSIGNMENT_TEMP);

			String secOlmIds[] = dto.getSecondaryOlmId().split(",");

			for(int k = 0 ; k < secOlmIds.length ;k++)
							{
								try
								{
									
										ps.setString(1,secOlmIds[k].toUpperCase());
										String assignmentKey = dto.getAssignmentKey().toUpperCase() + "E~" +j +"~"+ dto.getUserType() +"~" +dto.getOlmId();
										ps.setString(2,assignmentKey);
										ps.setInt(3,Integer.parseInt(dto.getProductLobId()));
										ps.setInt(4,Integer.parseInt(dto.getCircle()));
										ps.setString(5,dto.getCity());
										ps.setString(6,dto.getCityZone());
										ps.setString(7,dto.getPincode());
										ps.setString(8,dto.getRsu());
										ps.setInt(9,Constants.SECONDARY_ASSIGNMENT);
										ps.setString(10,userBean.getUserLoginId());
										ps.setString(11,dto.getUserType());
										
										ps.setString(12,Integer.toString(j));
										
										if(dto.getLevellCC() == null)
										{
											ps.setString(16,"");
										}
										else
										{
											ps.setString(16,dto.getLevellCC());
										}
										if(dto.getLevel2CC() == null)
										{
											ps.setString(17,"");
										}
										else
										{
											ps.setString(17,dto.getLevel2CC());
										}
										if(dto.getLevel3CC() == null)
										{
											ps.setString(18,"");
										}
										else
										{
											ps.setString(18,dto.getLevel3CC());
										}
										if(dto.getLevel4CC() == null)
										{
											ps.setString(19,"");
										}
										else
										{
											ps.setString(19,dto.getLevel4CC());
										}
										
										ps.setInt(13,Integer.parseInt(dto.getProductId()));
										ps.setString(14,dto.getCityZoneCode());
										ps.setString(15,dto.getOlmId());
										ps.setString(20,dto.getRequestCategoryId());
										ps.setString(21, dto.getUploadType());
										
										resul=getApproversList(dto.getOlmId().toUpperCase());
										if(resul !=null && resul.contains(",")) 
										{
											l1app=resul.split(",")[0];
											l2app=resul.split(",")[1];
											
										}
										
										ps.setString(22,l1app);  //L1 approver
										ps.setString(23,l2app);//l2 approver
										ps.setString(24, userBean.getIpaddress());
										
																		
									if(isAssignmentTempKeyExist(secOlmIds[k],dto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT, Constants.DEACTIVE_ASSIGNMENT, j, dto.getUserType(), dto.getOlmId(),1))
										reAssignmentTemp(secOlmIds[k],dto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,userBean.getUserLoginId(),con,j,dto.getUserType(),dto.getOlmId(),1);
									
									else if(isAssignmentTempKeyExist(secOlmIds[k],dto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,Constants.ACTIVE_ASSIGNMENT, j, dto.getUserType(), dto.getOlmId(),1))
									{}
									
									else
									ps.executeUpdate();
									j++;
									
									
								}
							
								catch(Exception e)
								{
									e.printStackTrace();
								}
								
			}
			}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in insertSecondaryOlmId method :  "+ e.getMessage(),e);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}

	}
	//Delete Assignment matrix Method for Single and Bulk
	
	
	public String softDeleteAssignment(ArrayList<BulkAssignmentDto> validListDelete,UserMstr userBean)
	throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		BulkAssignmentDto dto;
		String msg = "success";
		int i=0;

		try {
			con = DBConnection.getDBConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SQL_SOFT_DELETE_ASSIGNMENT);

			for (Iterator<BulkAssignmentDto> itr = validListDelete.iterator();itr.hasNext();) 
			{
				dto = (BulkAssignmentDto) itr.next();	
				
				ps.setString(1,userBean.getUserLoginId());
				ps.setString(2,dto.getOlmId().toUpperCase());
				
				String assignmentKey = dto.getAssignmentKey();
				ps.setString(3,assignmentKey);
				ps.setInt(4,dto.getPrimaryAuth());
				i=ps.executeUpdate();
				logger.info("updated value for Assignment matrix Delete"+i);

				if(dto.getSecondaryOlmId().length() > 0)
					softDeleteSecondaryAssignment(dto,userBean,con);
					softDeleteSecondaryAssignment1(dto,userBean,con);
				
			}
			con.commit();


		}catch (Exception e) {
			e.printStackTrace();
			msg = "error";
			return msg;
		} finally {
			try {
				DBConnection.releaseResources(con, ps, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		return msg;

	}

	
	
	public String softDeleteAssignmentTemp(ArrayList<BulkAssignmentDto> validListDelete,UserMstr userBean)
	throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		BulkAssignmentDto dto;
		String msg = "success";
		int i=0;

		try {
			con = DBConnection.getDBConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SQL_SOFT_DEACTIVATE_ASSIGNMENT_TEMP);

			for (Iterator<BulkAssignmentDto> itr = validListDelete.iterator();itr.hasNext();) 
			{
				dto = (BulkAssignmentDto) itr.next();	
				ps.setString(1,userBean.getCreatedBy());
				ps.setString(2,dto.getOlmId().toUpperCase());
				ps.setString(3,dto.getAssignmentKey());
				ps.setInt(4,dto.getPrimaryAuth());
				i=ps.executeUpdate();
				logger.info("updated value for Assignment matrix temp Delete"+i);

				if(dto.getSecondaryOlmId().length() > 0)
					softDeleteSecondaryAssignment(dto,userBean,con);
					softDeleteSecondaryAssignment1(dto,userBean,con);
				
			}
			con.commit();


		}catch (Exception e) {
			e.printStackTrace();
			msg = "error";
			return msg;
		} finally {
			try {
				DBConnection.releaseResources(con, ps, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		return msg;

	}

	
	
	public void softDeleteSecondaryAssignment(BulkAssignmentDto dto,UserMstr userBean,Connection con)throws DAOException 
	{

		PreparedStatement ps = null;
		int j=0;
	
		String secOlmIds[] = dto.getSecondaryOlmId().split(",");

		try {
			ps = con.prepareStatement(SQL_SOFT_DELETE_ASSIGNMENT);
			
			for(int i = 0 ; i < secOlmIds.length ;i++)
			{				
				ps.setString(1,userBean.getUserLoginId());
				ps.setString(2,secOlmIds[i].toUpperCase());
				
				/* Added by Parnika adding Level # User Type # Channel Partner in Key*/
				String assignmentKey = dto.getAssignmentKey();
				ps.setString(3,assignmentKey);
				/* End of changes by Parnika */	
				
				//ps.setString(3,dto.getAssignmentKey().toUpperCase()+"A");
				ps.setInt(4,Constants.SECONDARY_ASSIGNMENT);
				//ps.setString(5, dto.getUserType());
				j=ps.executeUpdate();
				logger.info("Secondary olmid's deleted value"+j);

			}	
			

		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in softDeleteSecondaryAssignment method :  "+ e.getMessage(),e);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}

	}
	
	public void softDeleteSecondaryAssignment1(BulkAssignmentDto dto,
			UserMstr userBean,Connection con)
	throws DAOException {

		PreparedStatement ps = null;
		int j=0;
		
		String secOlmIds[] = dto.getSecondaryOlmId().split(",");

		try {
			ps = con.prepareStatement(SQL_SOFT_DELETE_ASSIGNMENT);
			
			for(int i = 0 ; i < secOlmIds.length ;i++)
			{				
				ps.setString(1,userBean.getUserLoginId());
				ps.setString(2,secOlmIds[i].toUpperCase());
				
				/* Added by Parnika adding Level # User Type # Channel Partner in Key*/
				int level = i+1;
				String assignmentKey = dto.getAssignmentKey();
				ps.setString(3,assignmentKey);
				/* End of changes by Parnika */	
				
				//ps.setString(3,dto.getAssignmentKey().toUpperCase()+"E");
				ps.setInt(4,Constants.SECONDARY_ASSIGNMENT);
				//ps.setString(5, dto.getUserType());
				j=ps.executeUpdate();
				logger.info("Secondary olmid's deleted value"+j);

			}	
			

		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in softDeleteSecondaryAssignment method :  "+ e.getMessage(),e);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}

	}
	
	
	public boolean isAssignmentKeyExist(String olmId, String assignmentKey,int primaryAuth,String status, int level, String userType, String channelPartnerId,int flag)
	throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		String key="";

		try {
	
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_SELECT_ASSIGNMENT_KEY);
			ps.setString(1,olmId.toUpperCase());
			ps.setString(3,status);
			
			if(flag==0)
			{
				key = assignmentKey.toUpperCase() + "A~" +0 +"~"+ userType +"~" +channelPartnerId;
				ps.setString(2,key);
			}
			else if(flag==1)
			{
				ps.setString(2,assignmentKey);
			}
			else if(flag==2)
			{
				key = assignmentKey.toUpperCase()+ "E~" +level +"~"+ userType +"~" +channelPartnerId;
				ps.setString(2,key);
			}
			
			logger.info(ps);
			rs = ps.executeQuery();

			if(rs.next())
			{
				
				isExist = true;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in isAssignmentKeyExist method :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		return isExist;

	}

	
	/*public boolean isAssignmentKeyExist1(String olmId, String assignmentKey,int primaryAuth,String status, int level, String userType, String channelPartnerId)
	throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		String key="";

		try {
	
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_SELECT_ASSIGNMENT_KEY);
			
			ps.setString(1,olmId.toUpperCase());
		    logger.info("case 1**************"+assignmentKey+"********"+status+"***********"+olmId);
			ps.setString(2,assignmentKey);
			ps.setString(3,status);
			rs = ps.executeQuery();

			if(rs.next())
			{
				isExist = true;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in isAssignmentKeyExist method :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		logger.info("exist value***************"+isExist);
		return isExist;

	}
	
	public boolean isAssignmentKeyExist2(String olmId, String assignmentKey,int primaryAuth,String status, int level, String userType, String channelPartnerId)
	throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		String key="";

		try {
	
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_SELECT_ASSIGNMENT_KEY);
			
			ps.setString(1,olmId.toUpperCase());
			key = assignmentKey.toUpperCase()+ "E~" +level +"~"+ userType +"~" +channelPartnerId;
		
		    logger.info("case 1**************"+assignmentKey+"********"+status+"***********"+olmId);
			ps.setString(2,key);
			ps.setString(3,status);
			rs = ps.executeQuery();

			if(rs.next())
			{
				isExist = true;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in isAssignmentKeyExist method :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		logger.info("exist value***************"+isExist);
		return isExist;

	}
	
	public boolean isAssignmentTempKeyExist2(String olmId, String assignmentKey,int primaryAuth,String status, int level, String userType, String channelPartnerId)
	throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		String key="";

		try {
	
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_SELECT_ASSIGNMENT__TEMP_KEY);
			
			ps.setString(1,olmId.toUpperCase());
			key = assignmentKey.toUpperCase()+ "E~" +level +"~"+ userType +"~" +channelPartnerId;
			ps.setString(2,key);
			ps.setString(3,status);
			rs = ps.executeQuery();

			if(rs.next())
			{
				isExist = true;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in isAssignmentKeyExist method :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		logger.info("exist value***************"+isExist);
		return isExist;

	}*/
	public boolean isAssignmentTempKeyExist(String olmId, String assignmentKey,int primaryAuth,String status, int level, String userType, String channelPartnerId,int flag)
	throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		String key="";

         try {
	
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_SELECT_ASSIGNMENT__TEMP_KEY);
			
			ps.setString(1,olmId.toUpperCase());
			ps.setString(3,status);
			
			if(flag==0)
			{
			 key = assignmentKey.toUpperCase() + "A~" +"0" +"~"+ userType +"~" +channelPartnerId;	
				
			}
			else if(flag==1)
			{
			 key = assignmentKey.toUpperCase()+ "E~" +level +"~"+ userType +"~" +channelPartnerId;
			}
			
			ps.setString(2,key);
			rs = ps.executeQuery();

			if(rs.next()) 
				isExist = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in isAssignmentTempKeyExist method :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return isExist;

	}
	
	public void reAssignment(String olmId,String assignmentKey,int primaryAuth,String updatedBy	,Connection con, int level, String userType, String channelPartnerId,int flag)
	throws DAOException {

		PreparedStatement ps = null;
		String key="";
		try {
	
			ps = con.prepareStatement(SQL_REASSIGN_ASSIGNMENT);

				ps.setString(1,updatedBy);
				ps.setString(2,olmId.toUpperCase());
		
				if(flag==0)
				{
				  key = assignmentKey.toUpperCase() + "A~" +level +"~"+ userType +"~" +channelPartnerId;	
				}
				else if(flag==1)
				{
				 key = assignmentKey.toUpperCase() + "E~" +level +"~"+ userType +"~" +channelPartnerId;
				}
				else if(flag==2)
				{
				 key= assignmentKey.toUpperCase();
				}
				ps.setString(3,key);
				ps.setInt(4,primaryAuth);
				ps.executeUpdate();
					

		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in updateSecondaryOlmId method :  "+ e.getMessage(),e);
		} finally {
			try {
				ps = null;
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}

	}

	public void reAssignmentFromTemp(String olmId,String assignmentKey,int primaryAuth,String updatedBy	, int level, String userType, String channelPartnerId)
	throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_REASSIGN_ASSIGNMENT);

				ps.setString(1,updatedBy);
				ps.setString(2,olmId.toUpperCase());
				ps.setString(3,assignmentKey);
				ps.setInt(4,primaryAuth);
				ps.executeUpdate();
					

		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in updateSecondaryOlmId method :  "+ e.getMessage(),e);
		} finally {
			try {
				ps = null;
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}

	}
	
	/*public void reAssignmentSecOlmId2(String olmId,String assignmentKey,int primaryAuth,String updatedBy ,Connection con, int level, String userType, String channelPartnerId)
	throws DAOException {

		PreparedStatement ps = null;
		
		try {
	
			ps = con.prepareStatement(SQL_REASSIGN_ASSIGNMENT);

				ps.setString(1,updatedBy);
				ps.setString(2,olmId.toUpperCase());
				String key = assignmentKey.toUpperCase() + "E~" +level +"~"+ userType +"~" +channelPartnerId;
				ps.setString(3,key);
				ps.setInt(4,primaryAuth);
				ps.executeUpdate();
					

		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in updateSecondaryOlmId method :  "+ e.getMessage(),e);
		} finally {
			try {
				ps = null;
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}

	}
	
	public void reAssignmentSecOlmId2Temp(String olmId,String assignmentKey,int primaryAuth,String updatedBy,Connection con, int level, String userType, String channelPartnerId)
	throws DAOException {

		PreparedStatement ps = null;
		
		try {
	
			ps = con.prepareStatement(SQL_REASSIGN_ASSIGNMENT_TEMP);

				ps.setString(1,updatedBy);
				ps.setString(2,olmId.toUpperCase());
				String key = assignmentKey.toUpperCase() + "E~" +level +"~"+ userType +"~" +channelPartnerId;
				ps.setString(3,key);
				ps.setInt(4,primaryAuth);
				ps.executeUpdate();
					

		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in updateSecondaryOlmId method :  "+ e.getMessage(),e);
		} finally {
			try {
				ps = null;
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}

	}*/
	
	public void reAssignmentTemp(String olmId,String assignmentKey,int primaryAuth,String updatedBy,Connection con, int level, String userType, String channelPartnerId,int flag)
	throws DAOException {

		PreparedStatement ps = null;
		String key="";
		
		try {
	
			ps = con.prepareStatement(SQL_REASSIGN_ASSIGNMENT_TEMP);

				ps.setString(1,updatedBy);
				ps.setString(2,olmId.toUpperCase());
				if(flag==0)
				{
				  key = assignmentKey.toUpperCase() + "A~" +level +"~"+ userType +"~" +channelPartnerId;
				}
				else if(flag==1)
				{
				  key = assignmentKey.toUpperCase() + "E~" +level +"~"+ userType +"~" +channelPartnerId;	
				}
				
				ps.setString(3,key);
				ps.setInt(4,primaryAuth);
				ps.executeUpdate();
					

		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in updateSecondaryOlmId method :  "+ e.getMessage(),e);
		} finally {
			try {
				ps = null;
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}

	}
	
	/*public boolean isAssignmentKeyAssignedAsPrimary(String assignmentKey,
			String status) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;

		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_CHECK_ASSIGNMENT_KEY_AS_PRIMARY);
			ps.setString(1,assignmentKey.toUpperCase());
			ps.setString(2,status);
			
			rs = ps.executeQuery();

			if(rs.next()) 
				isExist = true;

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in isAssignmentKeyExist method :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return isExist;
	}*/


	public ArrayList<BulkAssignmentDto> getAssignmentMatrixList(String olmID) throws DAOException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		BulkAssignmentDto dto;
		int counter = 1;
		try {
			StringBuffer query=new StringBuffer(SQL_SELECT_ASSIGNMENT_MATRIX);
			ArrayList<BulkAssignmentDto> ActionList = new ArrayList<BulkAssignmentDto>();
			con = DBConnection.getDBConnection();
			if(olmID !=null && !"".equalsIgnoreCase(olmID))
				query.append(" AND OLM_ID = ? ");
			ps = con.prepareStatement(query.toString());
			if(olmID !=null && !"".equalsIgnoreCase(olmID))
				ps.setString(counter++, olmID);
			rs = ps.executeQuery();
			logger.info(query);
			/* Added By Parnika for LMS Phase 2 */
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
				ActionList.add(dto);
			/* End of changes by Parnika */
			}
			return ActionList;
		} catch (SQLException e) {
			e.printStackTrace();
			//logger.error("SQL Exception occured while Viewing assigned Leads."	+ "SQL Exception Message: "	+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			//logger.error(	" Exception occured while Viewing assigned Leads."+ " Exception Message: "+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				//logger.error("DAO Exception occured while Viewing assigned Leads."	+ "DAO Exception Message: "	+ e.getMessage());
				throw new LMSException("Exception: " + e.getMessage(), e);
			}
		}

	}

	public String softDeleteAssignmentNew(ArrayList<BulkAssignmentDto> validListDelete,UserMstr userBean)
	throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		BulkAssignmentDto dto;
		String msg = "success";
		StringBuffer bufferAssignSoftD = null;
		int i=0;

		try {
			con = DBConnection.getDBConnection();
			con.setAutoCommit(false);
			
			
			for (Iterator<BulkAssignmentDto> itr = validListDelete.iterator();itr.hasNext();) 
			{
				dto = (BulkAssignmentDto) itr.next();	
				
				bufferAssignSoftD  =  new StringBuffer(SQL_SOFT_DELETE_ASSIGNMENT_NEW);
				
				if(dto.getCity()!=null && !dto.getCity().equalsIgnoreCase("")){
					bufferAssignSoftD.append(" AND CITY_ID ='"+dto.getCity()+"'");
				}if(dto.getCityZone() !=null && !dto.getCityZone().equalsIgnoreCase("")){
					bufferAssignSoftD.append(" AND ZONE_ID ='"+dto.getCityZone()+"'");
				}if(dto.getCityZoneCode() !=null && !dto.getCityZoneCode().equalsIgnoreCase("")){
					bufferAssignSoftD.append(" AND CITY_ZONE_CODE='"+dto.getCityZoneCode()+"'");
				}if(dto.getRsu() !=null && !dto.getRsu().equalsIgnoreCase("")){
					bufferAssignSoftD.append(" AND RSU_ID ='"+dto.getRsu()+"'");
				}if(dto.getPincode() !=null && !dto.getPincode().equalsIgnoreCase("")){
					bufferAssignSoftD.append(" AND PINCODE ='"+dto.getPincode()+"'");
				}
				bufferAssignSoftD.append("  WITH UR");
				//logger.info("query================================="+bufferAssignSoftD.toString());
				ps = con.prepareStatement(bufferAssignSoftD.toString());
				ps.setString(1,userBean.getUserLoginId());
				ps.setString(2,dto.getOlmId().toUpperCase());
				ps.setString(3,dto.getProductLobId());
				ps.setString(4,dto.getCircle());
				ps.setString(5,dto.getProductId());
				
				/* Added by Parnika adding Level # User Type # Channel Partner in Key*/
			//String assignmentKey = dto.getAssignmentKey();
				
				//String assignmentKey = dto.getAssignmentKey().toUpperCase() + "A~" +"0" +"~"+ dto.getUserType() +"~"+ dto.getOlmId();
				//ps.setString(3,dto.getAssignmentKey().toUpperCase() + "A~" +"0" +"~"+ dto.getUserType() +"~"+ dto.getOlmId());
				//ps.setString(3, dto.getOlmId().toUpperCase());
				/* End of changes by Parnika */		
				//ps.setInt(4,dto.getPrimaryAuth());
				//ps.setString(5, dto.getUserType());
				i=ps.executeUpdate();
				
				logger.info("updated value for Assignment matrix Delete"+i);

				/*if(dto.getSecondaryOlmId().length() > 0)
					softDeleteSecondaryAssignmentNew(dto,userBean,con);
				//	softDeleteSecondaryAssignmentNew1(dto,userBean,con);*/
				
				
			}
			con.commit();


		}catch (Exception e) {
			e.printStackTrace();
			msg = "error";
			return msg;
		} finally {
			try {
				DBConnection.releaseResources(con, ps, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		return msg;

	}
	
	
	/*public String softDeleteAssignmentTempNew(ArrayList<BulkAssignmentDto> validListDelete,UserMstr userBean)
	throws DAOException {
		logger.info("delete new method**********************************");

		Connection con = null;
		PreparedStatement ps = null;
		BulkAssignmentDto dto;
		String msg = "success";
		int i=0;

		try {
			con = DBConnection.getDBConnection();
			con.setAutoCommit(false);
			
			for (Iterator<BulkAssignmentDto> itr = validListDelete.iterator();itr.hasNext();) 
			{
				dto = (BulkAssignmentDto) itr.next();	
				if(CommonMstrUtil.ProductLobIdNotPending(dto.getProductLobId()))
				{
				StringBuffer bufferAssignSoftD  =  new StringBuffer(SQL_SOFT_DELETE_ASSIGNMENT_TEMP_NEW);
				if(dto.getCity()!=null && !dto.getCity().equalsIgnoreCase("")){
					bufferAssignSoftD.append(" AND CITY_ID ='"+dto.getCity()+"'");
				}if(dto.getCityZone() !=null && !dto.getCityZone().equalsIgnoreCase("")){
					bufferAssignSoftD.append(" AND ZONE_ID ='"+dto.getCityZone()+"'");
				}if(dto.getCityZoneCode() !=null && !dto.getCityZoneCode().equalsIgnoreCase("")){
					bufferAssignSoftD.append(" AND CITY_ZONE_CODE='"+dto.getCityZoneCode()+"'");
				}if(dto.getRsu() !=null && !dto.getRsu().equalsIgnoreCase("")){
					bufferAssignSoftD.append(" AND RSU_ID ='"+dto.getRsu()+"'");
				}if(dto.getPincode() !=null && !dto.getPincode().equalsIgnoreCase("")){
					bufferAssignSoftD.append(" AND PINCODE ='"+dto.getPincode()+"'");
				}
				bufferAssignSoftD.append("  WITH UR");
				logger.info("query================================="+bufferAssignSoftD.toString());
				ps = con.prepareStatement(bufferAssignSoftD.toString());
				ps.setString(1,dto.getUpdateBy());
				ps.setString(2,dto.getOlmId().toUpperCase());
				ps.setInt(3,Integer.parseInt(dto.getProductLobId()));
				ps.setString(4,dto.getCircle());
				if(dto.getProductId()!=null && !("").equalsIgnoreCase(dto.getProductId()))
				{
				ps.setInt(5,Integer.parseInt(dto.getProductId()));
				}
				else
				{
					ps.setInt(5,-1);
				}
				
				
				i=ps.executeUpdate();
				
				logger.info("updated value for Assignment matrix temp Delete"+i);

				
			
			}
			con.commit();

			
		}catch (Exception e) {
			e.printStackTrace();
			msg = "error";
			return msg;
		} finally {
			try {
				DBConnection.releaseResources(con, ps, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		return msg;

	}*/
//Delete method for secondary assignment
	

	/*public void softDeleteSecondaryAssignmentNew(BulkAssignmentDto dto,
			UserMstr userBean,Connection con)
	throws DAOException {

		PreparedStatement ps = null;
		int j=0;
	
		String secOlmIds[] = dto.getSecondaryOlmId().split(",");

		try {
			ps = con.prepareStatement(SQL_SOFT_DELETE_ASSIGNMENT_NEW);
			
			for(int i = 0 ; i < secOlmIds.length ;i++)
			{				
				ps.setString(1,userBean.getUserLoginId());
				ps.setString(2,secOlmIds[i].toUpperCase());
				ps.setString(3,dto.getOlmId().toUpperCase());
				ps.setInt(4,Constants.SECONDARY_ASSIGNMENT);
			
				j=ps.executeUpdate();
			}	
			

		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in softDeleteSecondaryAssignment method :  "+ e.getMessage(),e);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}

	}*/
	
	/*public void softDeleteSecondaryAssignmentNew1(BulkAssignmentDto dto,
			UserMstr userBean,Connection con)
	throws DAOException {

		PreparedStatement ps = null;
		int j=0;
		//logger.info("secondary olmid method place");
		String secOlmIds[] = dto.getSecondaryOlmId().split(",");

		try {
			ps = con.prepareStatement(SQL_SOFT_DELETE_ASSIGNMENT_NEW);
			
			for(int i = 0 ; i < secOlmIds.length ;i++)
			{				
				ps.setString(1,userBean.getUserLoginId());
				ps.setString(2,secOlmIds[i].toUpperCase());
				
				 Added by Parnika adding Level # User Type # Channel Partner in Key
				int level = i+1;
				//String assignmentKey = dto.getAssignmentKey().toUpperCase() + "E~" +level+"~"+ dto.getUserType() +"~"+ dto.getOlmId();
				ps.setString(3,dto.getOlmId().toUpperCase());
				 End of changes by Parnika 	
				
				//ps.setString(3,dto.getAssignmentKey().toUpperCase()+"E");
				ps.setInt(4,Constants.SECONDARY_ASSIGNMENT);
				//ps.setString(5, dto.getUserType());
				j=ps.executeUpdate();
				logger.info("Secondary olmid's deleted value"+j);

			}	
			

		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in softDeleteSecondaryAssignment method :  "+ e.getMessage(),e);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}

	}*/
	
	
	
	/*public void softDeleteAssignmentNew(ArrayList<BulkAssignmentDto> validListDelete, UserMstr userBean) throws DAOException {
		
		Connection con = null;
		PreparedStatement ps = null;
		BulkAssignmentDto dto;
		String msg = "success";

		try {
			con = DBConnection.getDBConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SQL_SOFT_DELETE_ASSIGNMENT);

			for (Iterator<BulkAssignmentDto> itr = validListDelete.iterator();itr.hasNext();) 
			{
				dto = (BulkAssignmentDto) itr.next();					

				ps.setString(1,userBean.getUserLoginId());
				ps.setString(2,dto.getOlmId().toUpperCase());
				ps.setString(3,dto.getAssignmentKey().toUpperCase());
				ps.setInt(4,dto.getPrimaryAuth());
				
				ps.executeUpdate();

				if(dto.getSecondaryOlmId().length() > 0)
					softDeleteSecondaryAssignment(dto,userBean,con);
				
			}
			con.commit();


		}catch (Exception e) {
			e.printStackTrace();
			msg = "error";
			//return msg;
		} finally {
			try {
				DBConnection.releaseResources(con, ps, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		*/
		//return msg;
		//return msg;

	
	//}

	/*public void deleteAssignment(String olmId,String assignmentKey,int primaryAuth )
	throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_DELETE_ASSIGNMENT);

			ps.setString(1,olmId.toUpperCase());
			ps.setString(2,assignmentKey.toUpperCase());
			ps.setInt(3,primaryAuth);
			ps.executeUpdate();

		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Exception occured in deleteAssignment method :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}

	}*/

	private static final String GET_APPROVERS_AND_DR_LIST ="SELECT distinct APPROVER_L1,APPROVER_L2 FROM OLM_APPROVER WHERE LOB_ID=(SELECT b.LOB_ID FROM USER_MSTR a, USER_MAPPING b WHERE a.USER_LOGIN_ID=b.USER_LOGIN_ID and a.USER_LOGIN_ID=?)";
		//"SELECT distinct APPROVER_L1,APPROVER_L2 FROM OLM_APPROVER WHERE OLM_ID=?";

	
	public String getApproversList(String olmId) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String appl1="";
		String appl2="";
		String result="";
		
		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(GET_APPROVERS_AND_DR_LIST);
			ps.setString(1,olmId);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				appl1=rs.getString("APPROVER_L1");
				appl2=rs.getString("APPROVER_L2");
				result=appl1+","+appl2;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public String rejectAssignmentMatrix(ArrayList<BulkAssignmentDto> validListDelete,UserMstr userBean,String flag)
	throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		BulkAssignmentDto dto;
		String msg = "success";
		int i=0;

		try {
			con = DBConnection.getDBConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SQL_SOFT_DELETE_ASSIGNMENT_TEMP);

			
			for (Iterator<BulkAssignmentDto> itr = validListDelete.iterator();itr.hasNext();) 
			{
				dto = (BulkAssignmentDto) itr.next();	
				
				if(flag.equalsIgnoreCase("true"))
				{
				ps.setString(1,"A"); //approve
				
				}
				else
				{
				 ps.setString(1,"D");  //reject
				 
				}
				ps.setString(2,userBean.getUserLoginId());
				if(flag.equalsIgnoreCase("true"))
				{
				ps.setString(3,"2"); //approve
				if(dto.getRemarks().length()<=0 && ("").equalsIgnoreCase(dto.getRemarks()))
				{
				ps.setString(4,"approved");
				}
				else
				{
				 ps.setString(4,dto.getRemarks());	
				}
				}
				else
				{
				 ps.setString(3,"1");  //reject
				 ps.setString(4,dto.getRemarks());
				}
				
				
				ps.setString(5,dto.getOlmId().toUpperCase());
				String assignmentKey = dto.getAssignmentKey();
				ps.setString(6,assignmentKey);
				ps.setInt(7,dto.getPrimaryAuth());
				
				i=ps.executeUpdate();
				logger.info("updated value for Assignment matrix Delete"+i);

				if(dto.getSecondaryOlmId().length() > 0 && !flag.equalsIgnoreCase("true"))
					softDeleteSecondaryAssignment(dto,userBean,con);
					softDeleteSecondaryAssignment1(dto,userBean,con);
				
			}
			con.commit();


		}catch (Exception e) {
			e.printStackTrace();
			msg = "error";
			return msg;
		} finally {
			try {
				DBConnection.releaseResources(con, ps, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		return msg;

	}
	
	public String rejectAssignmentMatrixL2(ArrayList<BulkAssignmentDto> validListDelete,UserMstr userBean,String flag)
	throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		BulkAssignmentDto dto;
		String msg = "success";
		int i=0;

		try {
			con = DBConnection.getDBConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SQL_SOFT_DELETE_ASSIGNMENT_TEMP_L2);

			
			for (Iterator<BulkAssignmentDto> itr = validListDelete.iterator();itr.hasNext();) 
			{
				dto = (BulkAssignmentDto) itr.next();	
				
				if(flag.equalsIgnoreCase("true"))
				{
				ps.setString(1,"A"); //approve
				
				}
				else
				{
				 ps.setString(1,"D");  //reject
				 
				}
				ps.setString(2,userBean.getUserLoginId());
				if(flag.equalsIgnoreCase("true"))
				{
				ps.setString(3,"2"); //approve by L2
				if(dto.getRemarks().length()<=0 && ("").equalsIgnoreCase(dto.getRemarks()))
				{
				ps.setString(4,"approved");
				}
				else
				{
				ps.setString(4,dto.getRemarks());	
				}
				}
				else
				{
				 ps.setString(3,"1");  //reject
				 ps.setString(4,dto.getRemarks());
				}
				
				
				ps.setString(5,dto.getOlmId().toUpperCase());
				String assignmentKey = dto.getAssignmentKey();
				ps.setString(6,assignmentKey);
				ps.setInt(7,dto.getPrimaryAuth());
				
				i=ps.executeUpdate();
				logger.info("updated value for Assignment matrix Delete"+i);

				if(dto.getSecondaryOlmId().length() > 0 && !flag.equalsIgnoreCase("true"))
					softDeleteSecondaryAssignment(dto,userBean,con);
					softDeleteSecondaryAssignment1(dto,userBean,con);
				
			}
			con.commit();


		}catch (Exception e) {
			e.printStackTrace();
			msg = "error";
			return msg;
		} finally {
			try {
				DBConnection.releaseResources(con, ps, null);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		return msg;

	}
	        //both level approved matrices
			public ArrayList<BulkAssignmentDto> insertintoMainTable(UserMstr userBean) throws DAOException {
			Connection con = null;
			ResultSet rs = null;
			PreparedStatement ps = null;
			ResultSet rs1 = null;
			PreparedStatement ps1 = null;
			int counter = 1;
			ArrayList<BulkAssignmentDto> actionList= new ArrayList<BulkAssignmentDto>();
			ArrayList<BulkAssignmentDto> actionListInsert= new ArrayList<BulkAssignmentDto>();
			BulkAssignmentDto dto=null;
			
			try {
				
				StringBuffer query=new StringBuffer(APPROVED_MATRIX);
				ArrayList<BulkAssignmentDto> ActionList = new ArrayList<BulkAssignmentDto>();
				con = DBConnection.getDBConnection();
				ps = con.prepareStatement(query.toString());
				rs = ps.executeQuery();
				while (rs.next()) 
				{
					dto = new BulkAssignmentDto();
					dto.setOlmId(rs.getString("OLM_ID"));
					dto.setCircle(rs.getString("CIRCLE_ID"));
					dto.setCityZone(rs.getString("ZONE_ID"));
					dto.setCity(rs.getString("CITY_ID"));
					dto.setCityZoneCode(rs.getString("CITY_ZONE_CODE"));
					dto.setProductName(rs.getString("PRODUCT_ID"));
					dto.setProductLobId(rs.getString("PRODUCT_LOB_ID"));
					dto.setPincode(rs.getString("PINCODE"));
					dto.setRsu(rs.getString("RSU_ID"));
					dto.setAssignmentKey(rs.getString("ASSIGNMENT_KEY"));
					dto.setPrimaryAuth(rs.getInt("PRIMARY_AUTH"));
					dto.setCreateTime(rs.getString("CREATED"));
					dto.setCreatedBy(rs.getString("CREATED_BY"));
					dto.setUpdateTime(rs.getString("UPDATED"));
					dto.setUpdateBy(rs.getString("UPDATED_BY"));
					dto.setUserType(rs.getString("USER_TYPE")); //updated by
					dto.setRequestCategoryId(rs.getString("REQUEST_CATEGORY_ID"));
					dto.setChannelPartnerId(rs.getString("CHANNEL_PARTNER_ID"));
					dto.setLevellCC(rs.getString("LEVEL1_CC"));
					dto.setLevel2CC(rs.getString("LEVEL2_CC"));
					dto.setLevel3CC(rs.getString("LEVEL3_CC"));
					dto.setLevel4CC(rs.getString("LEVEL4_CC"));
					dto.setLevelId(rs.getString("LEVEL_ID"));
					dto.setIpAddress(rs.getString("IPADDRESS"));
					actionList.add(dto);
					
					//if(isAssignmentKeyExist1(dto.getOlmId(),dto.getAssignmentKey(),dto.getPrimaryAuth(),Constants.DEACTIVE_ASSIGNMENT, 0, dto.getUserType(), dto.getOlmId()))
					if(isAssignmentKeyExist(dto.getOlmId(),dto.getAssignmentKey(),dto.getPrimaryAuth(),Constants.DEACTIVE_ASSIGNMENT, 0, dto.getUserType(), dto.getOlmId(),1))
					{   
					reAssignmentFromTemp(dto.getOlmId(), dto.getAssignmentKey(),dto.getPrimaryAuth() ,dto.getUpdateBy(), Integer.parseInt(dto.getLevelId()), dto.getUserType(), dto.getOlmId());
					}else
					actionListInsert.add(dto);
					}
				
					String msg1=insertAssignment(actionListInsert,userBean,"temp");  
					StringBuffer query1=new StringBuffer(APPROVED_MATRIX_DELETION);
					ps1 = con.prepareStatement(query1.toString());
					ps1.executeUpdate();
							
		
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

			
			public ArrayList<BulkAssignmentDto> viewAssignmentMatrixStatus(UserMstr userBean)throws DAOException {

				Connection con = null;
				PreparedStatement ps = null;
				ResultSet rs =  null;
				ArrayList<BulkAssignmentDto> bulkList= new ArrayList<BulkAssignmentDto>();
				BulkAssignmentDto dto = null;
				
				try {
					con = DBConnection.getDBConnection();
					con.setAutoCommit(false);
					ps = con.prepareStatement(VIEW_ASSIGNMENTS);
					ps.setString(1,userBean.getUserLoginId());
					rs=ps.executeQuery();
					
					while (rs.next())
					{
					  dto = new BulkAssignmentDto();
					  dto.setOlmId(rs.getString("OLM_ID"));
					  dto.setProductLobId(rs.getString("PRODUCT_LOB"));
					  dto.setProductName(rs.getString("PRODUCT"));
					  dto.setPrimaryAuth(Integer.parseInt(rs.getString("PRIMARY_AUTH")));
					  dto.setCircle(rs.getString("CIRCLE"));
					  dto.setCity(rs.getString("CITY"));
					  dto.setCityZone(rs.getString("ZONE"));
					  dto.setPincode(rs.getString("PINCODE"));
					  dto.setUserType(rs.getString("USER_TYPE"));
					  dto.setChannelPartnerId(rs.getString("CHANNEL_PARTNER_ID"));
					  dto.setApproverL1(rs.getString("APPROVER_L1"));
					  dto.setApproverL2(rs.getString("APPROVER_L2"));
					  dto.setStatusL1(rs.getString("STATUS_L1"));
					  dto.setCommentsL1(rs.getString("COMMENTS_L1"));
					  dto.setCommentsL2(rs.getString("COMMENTS_L2"));
					  dto.setStatusL2(rs.getString("STATUS_L2"));
					  dto.setRsu(rs.getString("RSU_ID"));
					  dto.setCityZone(rs.getString("CITY_ZONE"));
					  dto.setCreateTime(rs.getString("CREATED"));
					  					  
					  if(dto.getStatusL1().equalsIgnoreCase("1"))
					  {
						  dto.setRemarks(dto.getCommentsL1());
						  dto.setRejectedBy(dto.getApproverL1());
					  }
					  else if(dto.getStatusL2().equalsIgnoreCase("1")) 
					  {

						  dto.setRemarks(dto.getCommentsL2()); 
						  dto.setRejectedBy(dto.getApproverL2());
					  }
					  else if(dto.getStatusL1().equalsIgnoreCase("0"))
					  {
						  dto.setPendingwith(dto.getApproverL1());
					  }
					  else if(!dto.getStatusL1().equalsIgnoreCase("0") && dto.getStatusL2().equalsIgnoreCase("0"))
					  {
						  dto.setPendingwith(dto.getApproverL2());  
					  }
					  bulkList.add(dto); 
					}
			  
				}catch (Exception e) {
					e.printStackTrace();
				} finally 
				{
					try {
						DBConnection.releaseResources(con, ps, null);
					} catch (Exception e) {				
						throw new DAOException(e.getMessage(), e);
					}
				}
				
				return bulkList;

			}
			
			private static final String INSERT_EMAIL_HISTORY = "INSERT INTO EMAIL_SENT_HISTORY(EMAIL_MSG, SUBJECT, EMAIL_ID, SENT_ON, RESPONSE) VALUES(?,?,?, current timestamp,?)";
			private static final String SQL_GET_ALERT_CONFIG = "select SUBJECT_TEMPLATE,MSG_TEMPLATE from ALERT_EMAIL_CONFIG where status='A' and ALERT_ID=? with ur";
			private static final String GET_EMAILS="SELECT USER_EMAILID FROM USER_MSTR WHERE USER_LOGIN_ID in (SELECT distinct APPROVER_L1 FROM OLM_APPROVER WHERE OLM_ID=? union all SELECT distinct APPROVER_L2 FROM OLM_APPROVER WHERE OLM_ID=? union all SELECT distinct olm_id FROM OLM_APPROVER WHERE OLM_ID = ?) WITH UR";
			private static String fromEmail = PropertyReader.getAppValue("email.from");
			private static String strHost = PropertyReader.getAppValue("mail.smtp.host");	
			
						
			
			public BulkAssignmentDto logs(UserMstr userBean,String errLogFileName) throws DAOException 
		    {
			PreparedStatement inspstmthistory=null;
			PreparedStatement ps=null;
			ResultSet rs= null;
			PreparedStatement ps1=null;
			ResultSet rs1= null;
			Connection con=null;
			String EmailSub="";
			String messageBody="";
			String emails="";
			boolean alertFlag=false;
			String msg="success";
			ArrayList<String> emailids = new ArrayList<String>();
			BulkAssignmentDto bulkAssignmentDto = new BulkAssignmentDto();
			
			
			try
			{
				con = DBConnection.getDBConnection();
				ps = con.prepareStatement(SQL_GET_ALERT_CONFIG);
				ps.setInt(1, Constants.ASSIGNMENTMATRIX_ALERTID);
						
				ps1 = con.prepareStatement(GET_EMAILS);
				ps1.setString(1, userBean.getUserLoginId());
				ps1.setString(2, userBean.getUserLoginId());
				ps1.setString(3, userBean.getUserLoginId());
				
				inspstmthistory = con.prepareStatement(INSERT_EMAIL_HISTORY);
				
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					alertFlag=true;
					EmailSub=rs.getString("SUBJECT_TEMPLATE");
					bulkAssignmentDto.setEmailsub(EmailSub);
					messageBody=rs.getString("MSG_TEMPLATE");
					messageBody=messageBody.replaceAll(Constants.olmid,""+userBean.getUserLoginId());
					
				}	
				
				rs1=ps1.executeQuery();
				
				while(rs1.next())
				{
					if(rs1.getString("USER_EMAILID")!=null && !("").equalsIgnoreCase(rs1.getString("USER_EMAILID")) && rs1.getString("USER_EMAILID").length()>0)
					{
						emailids.add(rs1.getString("USER_EMAILID"));
					}
					bulkAssignmentDto.setEmails(emailids);
					
				}
		        String formatedMsg = SendMail.getGeneralMessage(messageBody);
		        bulkAssignmentDto.setMessageBody(formatedMsg);
		        bulkAssignmentDto.setFlagSmsEmail(Constants.EMAILFLAG);
		        bulkAssignmentDto.setFilePath(errLogFileName);
		        
				/*for(Object emailObj: emailids.toArray())
				{
				 	
				String messsg = SendMail.sendingMail(errLogFileName,emailObj.toString() ,null , formatedMsg, EmailSub , fromEmail , strHost);
				try{
					
					inspstmthistory.setString(1, ((formatedMsg.length()<1000)? formatedMsg:formatedMsg.substring(0,999)));
					inspstmthistory.setString(2, EmailSub);				
					inspstmthistory.setString(3,emailObj.toString());
					inspstmthistory.setString(4,messsg);
				    int i=inspstmthistory.executeUpdate();
				    logger.info("not sent , rows inserted in table is="+i);
			  	}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				}*/
			}
			catch (Exception e) {
				throw new DAOException("Exception occured while getting Mobility Product list :  "+ e.getMessage(),e);
			} finally {
				try {
					DBConnection.releaseResources(con, ps, rs);
				} catch (Exception e) {				
					throw new DAOException(e.getMessage(), e);
				}
			}
			return bulkAssignmentDto;
		}



		


			
			
}//daoImpl