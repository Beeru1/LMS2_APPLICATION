package com.ibm.lms.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import com.ibm.lms.common.ColumnKeys;
import com.ibm.lms.common.Constants;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.common.DataObject;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dao.CircleManagementDaoNew;
import com.ibm.lms.dto.CaptureLeadDetailsDTO;
import com.ibm.lms.dto.CircleMstrDto;
import com.ibm.lms.dto.PushMstrWebserviceDTO;
import com.ibm.lms.dto.webservice.RetrieveLeadDataNewDTO;
import com.ibm.lms.engine.dao.impl.GetLeadCaptureData;
import com.ibm.lms.exception.DAOException;

public class CircleManagementDaoImplNew  implements CircleManagementDaoNew
{

	Logger logger = Logger.getLogger(CircleManagementDaoImplNew.class);
	
	
	private final static String GET_CIRCLE_STATE_DETAILS = "Select cm.circle_id, sm.State_name from STATE_MSTR sm ,CIRCLE_MSTR cm where cm.STATUS ='A' AND sm.STATUS ='A' AND sm.CIRCLE_MSTR_ID = cm.CIRCLE_MSTR_ID and cm.LOB_ID = " +
	"(SELECT pm.PRODUCT_LOB_ID from PRODUCT_MSTR pm , PRODUCT_SYNONYM ps where pm.PRODUCT_ID = ps.PRODUCT_ID and pm.STATUS = 'A' and ucase(PRODUCT_SYNONYM_NAME) = ucase(?) FETCH FIRST ROW ONLY) with ur";
	
	private final static String GET_CITY_DETAILS = "select city_code, city_name from city_mstr where STATUS ='A' AND zone_code in (select zone_code from ZONE_MSTR where STATUS ='A' AND CIRCLE_MSTR_ID in" + 
	   "(Select CIRCLE_MSTR_ID from CIRCLE_MSTR where STATUS ='A' AND LOB_ID = (SELECT pm.PRODUCT_LOB_ID from PRODUCT_MSTR pm , PRODUCT_SYNONYM ps where pm.PRODUCT_ID = ps.PRODUCT_ID and pm.STATUS = 'A' and ucase(PRODUCT_SYNONYM_NAME) = ucase(?) FETCH FIRST ROW ONLY))) with ur";

	private final static String GET_PINCODE_DETAILS = "select pincode from pincode_mstr where STATUS ='A' AND CITY_ZONE_CODE in (select city_zone_code " +
		" from CITY_ZONE_MSTR where STATUS ='A' AND city_code in" +  
		"(select city_code from city_mstr where STATUS ='A' AND zone_code in (select zone_code from ZONE_MSTR " +
		" where STATUS ='A' AND CIRCLE_MSTR_ID in (Select CIRCLE_MSTR_ID from CIRCLE_MSTR where STATUS ='A' AND LOB_ID = " +
		"(SELECT pm.PRODUCT_LOB_ID from PRODUCT_MSTR pm , PRODUCT_SYNONYM ps where pm.PRODUCT_ID = ps.PRODUCT_ID and pm.STATUS = 'A' and ucase(PRODUCT_SYNONYM_NAME) = ucase(?) FETCH FIRST ROW ONLY))))) with ur"; 
	
	private final static String IS_VALID_PROJECT = "SELECT count(1) from PRODUCT_MSTR pm , PRODUCT_SYNONYM ps where pm.PRODUCT_ID = ps.PRODUCT_ID and pm.STATUS = 'A' and ucase(PRODUCT_SYNONYM_NAME) = ucase(?)  with ur";
	
	
	//Added By Beeru
	private final static String IS_VALID_LEADID = "SELECT LD.LEAD_ID FROM LEAD_DATA LD ,LEAD_DETAILS LDT WHERE LD.LEAD_ID=LDT.LEAD_ID AND LD.LEAD_ID=? WITH UR";
	private final static String IS_VALID_TXNID = "SELECT LD.LEAD_ID FROM LEAD_DATA LD ,LEAD_DETAILS LDT WHERE LD.LEAD_ID=LDT.LEAD_ID AND LDT.LEAD_CAPTURED_DATA_ID=? WITH UR";
	
 private final static String TRANS_ID_EXIST="SELECT LEAD_CAPTURED_DATA_ID FROM LEAD_CAPTURE WHERE LEAD_CAPTURED_DATA_ID=? AND LC_ISDONE=1 WITH UR";
 private final static String GET_HISTORY="(select '' as LEAD_PROSPECT_ID,'null' as CITY_ZONE_CODE,'null' as CITY,'null' as CITY_CODE, 'null' as STATE, 'null' as ZONE ,'' as CIRCLE_ID, '' as PINCODE,'null' as RSU_CODE,'null' as ADDRESS1,'null' as ADDRESS2,'null' as PRIMARY_LANGUAGE,'null' as COMPANY,'null' as APPOINTMENT_TIME,'null' as IS_CUSTOMER,'null' as MARITAL_STATUS,'null' as SUB_ZONE,'null' as RENTAL,'null' as PYT_AMT,'null' as GEO_IP_CITY,'null' as AD_PARAMETER,'null' as QUAL_LEAD_PARAM,'null' as TAG,'null' as TRAN_REFNO,'null' as PYT_AMT_DUE,'' as LEAD_ID,'null' as REQUEST_CATEGORY, 'null' as OPPORTUNITY_TIME,'null' as UTM_LABELS,'null' as AGENCY_ID,'null' as  NDNC_DISCLAIMER,'null' as UD_ID,'null' as FEASIBILITY_PARAM,'null' as PLAN_ID,'null' as CUSTOMER_INFO_ID,'null' as RENTAL_TYPE,'null' as FREEBIE_TAKEN,'null' as FREEBIE_COUNT,'null' as BOOSTER_COUNT,'null' as BOOSTER_TAKEN,'null' as FLAG,'null' as PREPAID_NUMBER,'null' as OFFER,'null' as DOWNLOAD_LIMIT,'null' as DEVICE_MRP,'null' as VOICE_BENEFIT,'null' as  DATA_QUOTA,'null' as USER_TYPE,'null' as DEVICE_TAKEN,'null' as BENEFIT,'null' as PKG_DURATION,'null' as HLR_NO,'null' as DOB,'null' as EXTRA_PARAM1,"+
"'null' as EXTRA_PARAM3,'null' as EXTRA_PARAM4,'null' as EXTRA_PARAM5,'null' as EXTRA_PARAM6,'null' as EXTRA_PARAM7,'null' as EXTRA_PARAM8,'null' as COMPETITOR_CHOSEN, 'null' as SENTBY,'null' as PRODUCT_BOUGHT,'null' as PAYMENT_COLLECTED,'null' as PAYMENT_TYPE,'null' as TASK_START_TIME,'null' as TASK_END_TIME,'null' as ASSIGNED_REMARKS,cast(LPCH.PROSPECT_ID as varchar(40))as PROSPECT_ID,LPCH.CUSTOMER_NAME AS CUSTOMER_NAME,LPCH.EMAIL AS EMAIL,LPCH.ALTERNATE_CONTACT_NUMBER,LPCH.LANDLINE_NUMBER,'null' as APPOINMENT_ENDTIME FROM LEAD_PROSPECT_CUSTOMER_HIST LPCH WHERE LPCH.PROSPECT_ID =?) union all"+
"(select cast(LPDH.LEAD_PROSPECT_ID as varchar(40))as LEAD_PROSPECT_ID, LPDH.CITY_ZONE_CODE,(SELECT CITY_NAME FROM CITY_MSTR CM WHERE CM.CITY_CODE=LPDH.CITY_CODE) AS CITY,LPDH.CITY_CODE,LPDH.STATE,(SELECT ZONE_NAME FROM ZONE_MSTR ZM WHERE ZM.ZONE_CODE=LPDH.ZONE_CODE) AS ZONE,cast(LPDH.CIRCLE_ID as varchar(40))as CIRCLE_ID,cast(LPDH.PINCODE as varchar(40))as PINCODE,LPDH.RSU_CODE,LPDH.ADDRESS1,LPDH.ADDRESS2,LPDH.PRIMARY_LANGUAGE,LPDH.COMPANY,LPDH.APPOINTMENT_TIME,LPDH.IS_CUSTOMER,LPDH.MARITAL_STATUS,LPDH.SUB_ZONE,LPDH.RENTAL,LPDH.PYT_AMT,LPDH.GEO_IP_CITY,LPDH.AD_PARAMETER,LPDH.QUAL_LEAD_PARAM,LPDH.TAG,LPDH.TRAN_REFNO,LPDH.PYT_AMT_DUE,'' as LEAD_ID,'null' as REQUEST_CATEGORY,'null' as OPPORTUNITY_TIME,'null' as UTM_LABELS,'null' as AGENCY_ID,'null' as  NDNC_DISCLAIMER,'null' as UD_ID,'null' as FEASIBILITY_PARAM,'null' as PLAN_ID,'null' as CUSTOMER_INFO_ID,'null' as RENTAL_TYPE,'null' as FREEBIE_TAKEN,'null' as FREEBIE_COUNT,'null' as BOOSTER_COUNT,'null' as BOOSTER_TAKEN,'null' as FLAG,'null' as PREPAID_NUMBER,'null' as OFFER,'null' as DOWNLOAD_LIMIT,'null' as DEVICE_MRP,'null' as VOICE_BENEFIT,'null' as  DATA_QUOTA,'null' as USER_TYPE,'null' as DEVICE_TAKEN,'null' as BENEFIT,'null' as PKG_DURATION,'null' as HLR_NO,'null' as DOB,'null' as EXTRA_PARAM1,'null' as EXTRA_PARAM3,'null' as EXTRA_PARAM4,'null' as EXTRA_PARAM5,'null' as EXTRA_PARAM6,'null' as EXTRA_PARAM7,'null' as EXTRA_PARAM8,"+
"'null' as COMPETITOR_CHOSEN,'null' as SENTBY,'null' as PRODUCT_BOUGHT,'null' as PAYMENT_COLLECTED,'null' as PAYMENT_TYPE,'null' as TASK_START_TIME,'null' as TASK_END_TIME,'null' as ASSIGNED_REMARKS,'' as PROSPECT_ID,'null' as CUSTOMER_NAME ,'null' as EMAIL,'null' as ALTERNATE_CONTACT_NUMBER,'null' as LANDLINE_NUMBER,'null' as APPOINMENT_ENDTIME from LEAD_PROSPECT_DETAIL_HIST LPDH WHERE LPDH.LEAD_PROSPECT_ID = ?) union all "+
"(SELECT '' as LEAD_PROSPECT_ID,'null' as CITY_ZONE_CODE,'null' as CITY,'null' as CITY_CODE,'null' as STATE,'null' as ZONE, '' as CIRCLE_ID,'' as PINCODE ,'null' as RSU_CODE,'null' as ADDRESS1,'null' as ADDRESS2,'null' as PRIMARY_LANGUAGE,'null' as COMPANY,'null' as APPOINTMENT_TIME, 'null' as IS_CUSTOMER,'null' as MARITAL_STATUS,'null' as SUB_ZONE,'null' as RENTAL,'null' as PYT_AMT,'null' as GEO_IP_CITY,'null' as AD_PARAMETER,'null' as QUAL_LEAD_PARAM,'null' as TAG,'null' as TRAN_REFNO,'null' as PYT_AMT_DUE,cast(LDH.LEAD_ID as varchar(40))as LEAD_ID,LDH.REQUEST_CATEGORY, LDH.OPPORTUNITY_TIME,LDH.UTM_LABELS,LDH.AGENCY_ID,LDH.NDNC_DISCLAIMER, LDH.UD_ID,LDH.FEASIBILITY_PARAM,LDH.PLAN_ID,LDH.CUSTOMER_INFO_ID,LDH.RENTAL_TYPE,LDH.FREEBIE_TAKEN,LDH.FREEBIE_COUNT,LDH.BOOSTER_COUNT,LDH.BOOSTER_TAKEN,LDH.FLAG,LDH.PREPAID_NUMBER,LDH.OFFER,LDH.DOWNLOAD_LIMIT,LDH.DEVICE_MRP,LDH.VOICE_BENEFIT,LDH.DATA_QUOTA,LDH.USER_TYPE,LDH.DEVICE_TAKEN,LDH.BENEFIT,LDH.PKG_DURATION,LDH.HLR_NO,LDH.DOB,LDH.EXTRA_PARAM1,LDH.EXTRA_PARAM3,LDH.EXTRA_PARAM4,LDH.EXTRA_PARAM5,LDH.EXTRA_PARAM6,LDH.EXTRA_PARAM7,LDH.EXTRA_PARAM8,LDH.COMPETITOR_CHOSEN,LDH.SENTBY,LDH.PRODUCT_BOUGHT,LDH.PAYMENT_COLLECTED,LDH.PAYMENT_TYPE,LDH.TASK_START_TIME,LDH.TASK_END_TIME,LDH.ASSIGNED_REMARKS,'' as PROSPECT_ID,'null' as CUSTOMER_NAME,'null' as EMAIL,'null' as ALTERNATE_CONTACT_NUMBER,'null' as LANDLINE_NUMBER,'null' as APPOINMENT_ENDTIME FROM LEAD_DETAILS_HISTORY  LDH WHERE LDH.LEAD_ID =?) FETCH FIRST 20 ROW ONLY ";

	private static final String GET_LEAD_DATA_NEW ="SELECT LD.LEAD_ID,LDT.LEAD_CAPTURED_DATA_ID,LPC.PROSPECT_ID, LD.LEAD_PROSPECT_ID,LPC.CUSTOMER_NAME,"+
                                                     "LPC.PROSPECT_MOBILE_NUMBER,LPD.ALTERNATE_CONTACT_NUMBER,(SELECT SOURCE_NAME FROM SOURCE_MSTR SM WHERE SM.SOURCE_ID=LD.SOURCE) AS SOURCE,(SELECT SUBSOURCE_NAME FROM SUB_SOURCE_MSTR SSM WHERE SSM.SUBSOURCE_ID=LD.SUB_SOURCE) "+
                                                      "AS SUB_SOURCE,LD.CID,LD.FID,LPD.LANDLINE_NUMBER,LPD.ADDRESS1,LPD.ADDRESS2,(SELECT CITY_NAME FROM CITY_MSTR CM WHERE CM.CITY_CODE=LPD.CITY_CODE) AS CITY,LPD.PINCODE,LDT.STATE,(SELECT CIRCLE_NAME FROM CIRCLE_MSTR CM WHERE CM.CIRCLE_ID=LPD.CIRCLE_ID "+
                                                      "AND CM.LOB_ID=LPD.PRODUCT_LOB_ID)AS CIRCLE,LPC.MARITAL_STATUS,(SELECT REQUEST_CATEGORY FROM REQUEST_CATEGORY_MSTR RCM WHERE RCM.REQUEST_CATEGORY_ID=LDT.REQUEST_CATEGORY_ID) AS REQUEST_CATEGORY,LPD.APPOINTMENT_TIME,LPD.APPOINMENT_ENDTIME, LDT.OPPORTUNITY_TIME,(SELECT PRODUCT_NAME "+
                                                      "FROM PRODUCT_MSTR PM WHERE PM.PRODUCT_ID=LD.PRODUCT_ID) AS PRODUCT,(SELECT PRODUCT_ID FROM PRODUCT_MSTR PM WHERE PM.PRODUCT_ID=LD.PRODUCT_ID) AS PRODUCT_ID, LPC.EMAIL,LPD.PRIMARY_LANGUAGE,LPD.SUB_ZONE,(SELECT ZONE_NAME FROM ZONE_MSTR ZM WHERE ZM.ZONE_CODE=LPD.ZONE_CODE) AS ZONE,LD.REMARKS,LD.CAMPAIGN,LD.REFERER_PAGE,LPD.QUAL_LEAD_PARAM,LPD.GEO_IP_CITY,"+
                                                       "LD.FROM_PAGE,LD.SERVICE,LPD.TAG,LPD.IS_CUSTOMER,LPD.AD_PARAMETER,LDT.UTM_LABELS,LD.REFERER_URL,LPC.COMPANY,(SELECT CITY_ZONE_NAME FROM CITY_ZONE_MSTR CZM WHERE CZM.CITY_ZONE_CODE=LPD.CITY_ZONE_CODE) AS CITY_ZONE_CODE,LD.PLAN,LPD.RENTAL,LD.ALLOCATED_NO,LD.ONLINE_CAF_NO,"+
                                                        "LPD.PYT_AMT,LPD.TRAN_REFNO,LDT.AGENCY_ID,LDT.NDNC_DISCLAIMER,LDT.UD_ID,LPD.CITY_CODE,LPD.CIRCLE_ID,LDT.FEASIBILITY_PARAM,LDT.PLAN_ID,LDT.CUSTOMER_INFO_ID,LDT.RENTAL_TYPE,LDT.FREEBIE_TAKEN,LDT.FREEBIE_COUNT,LDT.BOOSTER_COUNT,LDT.BOOSTER_TAKEN,LDT.FLAG,LDT.PREPAID_NUMBER,"+
                                                        "LDT.OFFER,LDT.DOWNLOAD_LIMIT,LDT.DEVICE_MRP,LDT.VOICE_BENEFIT,LDT.DATA_QUOTA,LDT.USER_TYPE,LDT.DEVICE_TAKEN,LDT.BENEFIT,LDT.PKG_DURATION,LDT.HLR_NO,LDT.DOB,LPD.RSU_CODE,LDT.EXTRA_PARAM1 AS SIM_NUM,LDT.EXTRA_PARAM2 AS CUSTOMER_SEGMENT,LDT.EXTRA_PARAM3,LDT.EXTRA_PARAM4,"+
                                                         "LDT.EXTRA_PARAM7 AS GENDER,LDT.EXTRA_PARAM6,LDT.EXTRA_PARAM5 AS NATIONALITY,LDT.EXTRA_PARAM8 AS IDENTITYPROOFTYPE,(SELECT LEAD_STATUS from LEAD_STATUS LS WHERE LS.LEAD_STATUS_ID=LD.LEAD_STATUS_ID)AS LEAD_STATUS,(SELECT SUB_STATUS from LEAD_SUB_STATUS LSS WHERE "+
                                                         "LSS.LEAD_STATUS_ID=LD.LEAD_SUB_STATUS_ID AND  LPD.PRODUCT_LOB_ID=LSS.PRODUCT_LOB_ID fetch first row only) AS LEAD_SUB_STATUS,LD.CAF,LDT.PRODUCT_BOUGHT,LDT.PAYMENT_COLLECTED,LD.PLAN,LDT.PAYMENT_TYPE,LPD.PYT_AMT,LDT.COMPETITOR_CHOSEN,LDT.SENTBY,LDT.ASSIGNED_REMARKS AS "+
                                                         "ASSIGNED_REMARKS,LPD.PYT_AMT_DUE,LDT.TASK_START_TIME,LDT.TASK_END_TIME,LD.LEAD_SUBMIT_TIME,LD.KEYWORD,LUD.EXT3 as EXTRA_PARAM9,LUD.IDENTITYPROOFID, " +
                                                         "LUD.RELATION_NAME ,LUD.PAYMENT_DATE ,LUD.UPC , LUD.UPC_GEN_DATE ,"+
                                                         "LUD.PREVIOUS_OPERATOR,LUD.RENTAL_PLAN, LUD.PREVIOUS_CIRCLE , LUD.EXISTING_PART,LUD.MNP_STATUS,LUD.DOC_COLLECTED_FLAG,"+
                                                         "LUD.PLAN_TYPE FROM LEAD_DATA LD,LEAD_PROSPECT_CUSTOMER LPC,LEAD_PROSPECT_DETAIL LPD,LEAD_DETAILS LDT, LEAD_UPDATE_DATA LUD WHERE LD.LEAD_PROSPECT_ID=LPD.LEAD_PROSPECT_ID AND LD.PROSPECT_ID=LPC.PROSPECT_ID AND LD.LEAD_ID=LDT.LEAD_ID AND LD.LEAD_ID=LUD.LEAD_ID";

//Added by srikant 
	
	 private static CircleManagementDaoImplNew circleManagementDaoImplNew=null;
		
		private CircleManagementDaoImplNew(){
			
		}
		
		public static CircleManagementDaoImplNew circleManagementDaoInstance()
		{
			if(circleManagementDaoImplNew==null)
			{
				circleManagementDaoImplNew=new CircleManagementDaoImplNew();
			}
			return circleManagementDaoImplNew;
			
		}
	public ArrayList<CircleMstrDto> getCircleList(CircleMstrDto circleMstrDto) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CircleMstrDto> circleList = new ArrayList<CircleMstrDto>();
		CircleMstrDto dto = null;
		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement("SELECT CIRCLE_MSTR_ID,CIRCLE_NAME,CIRCLE_DESC FROM CIRCLE_MSTR WHERE LOB_ID=? AND STATUS='A' WITH UR");

			ps.setInt(1,circleMstrDto.getLobId());
			
			rs = ps.executeQuery();
			while(rs.next()) {
				dto = new CircleMstrDto();
				dto.setCircleId(rs.getString("CIRCLE_MSTR_ID"));
				dto.setCircleName((rs.getString("CIRCLE_NAME")));
				dto.setCircleDesc((rs.getString("CIRCLE_DESC")));
				circleList.add(dto);
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
		return circleList;
	}

	public ArrayList<CircleMstrDto> getLobList() throws DAOException 
		{
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<CircleMstrDto> lobList = new ArrayList<CircleMstrDto>();
			CircleMstrDto dto = null;
			try {
				con = DBConnection.getDBConnection();
				ps = con.prepareStatement("SELECT PRODUCT_LOB_ID,PRODUCT_LOB FROM PRODUCT_LOB WHERE STATUS='A' WITH UR");
				rs = ps.executeQuery();
				while(rs.next()) {
					dto = new CircleMstrDto();
					dto.setLobId(rs.getInt("PRODUCT_LOB_ID"));
					dto.setLobName(rs.getString("PRODUCT_LOB"));
					lobList.add(dto);
				}
			} catch (Exception e) {
				throw new DAOException("Exception occured while getting lob list :  "+ e.getMessage(),e);
			} finally {
				try {
					DBConnection.releaseResources(con, ps, rs);
				} catch (Exception e) {				
					throw new DAOException(e.getMessage(), e);
				}
			}
			return lobList;
		}

	public int createCircle(CircleMstrDto circleMstrDto)throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CircleMstrDto> lobList = new ArrayList<CircleMstrDto>();
		int rows=0;
		CircleMstrDto dto = null;
		try 
		{	
			con = DBConnection.getDBConnection();
			
			if(checkCircle(circleMstrDto))
			{
				rows=Constants.CIRCLE_EXISTS;
			}
			else if(!checkCircle(circleMstrDto))
			{	
				ps = con.prepareStatement("INSERT INTO CIRCLE_MSTR(CIRCLE_ID,CIRCLE_NAME,CIRCLE_DESC,STATUS,LOB_ID,TRANSACTION_TIME,UPDATED_BY,CIRCLE_MSTR_ID)"+
										"(SELECT max(CIRCLE_ID)+1,?,?,'A',?,current timestamp,?,(select max(CIRCLE_MSTR_ID)+1  from CIRCLE_MSTR) from CIRCLE_MSTR) WITH UR");
				ps.setString(1,circleMstrDto.getCircleName());
				ps.setString(2,circleMstrDto.getCircleDesc());
				ps.setInt(3,circleMstrDto.getLobId());
				ps.setString(4,circleMstrDto.getUserLoginId());
				rows = ps.executeUpdate();
	
			}
		} catch (Exception e) {
			throw new DAOException("Exception occured while creating circle :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return rows;
	}
	
	public int editCircle(CircleMstrDto circleMstrDto)throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CircleMstrDto> lobList = new ArrayList<CircleMstrDto>();
		int rows=0;
		CircleMstrDto dto = null;
		try {
			con = DBConnection.getDBConnection();
			
			ps = con.prepareStatement("UPDATE CIRCLE_MSTR SET  CIRCLE_NAME = ?, CIRCLE_DESC = ?,TRANSACTION_TIME= current timestamp, UPDATED_BY = ? where CIRCLE_MSTR_ID=? WITH UR");
			
			ps.setString(1,circleMstrDto.getCircleName());
			ps.setString(2,circleMstrDto.getCircleDesc());
			ps.setString(3,circleMstrDto.getUserLoginId());
			ps.setInt(4,Integer.parseInt(circleMstrDto.getCircleId()));
			
			rows = ps.executeUpdate();
		} catch (Exception e) {
			throw new DAOException("Exception occured while editing circle :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return rows;
	}
	
	public int deleteCircle(CircleMstrDto circleMstrDto)throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CircleMstrDto> lobList = new ArrayList<CircleMstrDto>();
		int rows=0;
		CircleMstrDto dto = null;
		try {
			con = DBConnection.getDBConnection();
			
			
			/*if(!checkCircle(circleMstrDto))
			{
				rows=Constants.CIRCLE_EXISTS;     // Circle to delete doesnt exists
				
			}*/
			//else if(checkCircle(circleMstrDto))
			//{	
			ps = con.prepareStatement("UPDATE CIRCLE_MSTR SET STATUS='D',TRANSACTION_TIME=current timestamp,UPDATED_BY=?   WHERE CIRCLE_MSTR_ID=? AND STATUS='A' WITH UR");
			ps.setString(1,circleMstrDto.getUserLoginId());
			ps.setString(2,circleMstrDto.getCircleId());
			
			rows = ps.executeUpdate();
			//}
		} catch (Exception e) {
			throw new DAOException("Exception occured while deleting circle :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return rows;
	}
	
	public boolean checkCircle(CircleMstrDto circleMstrDto) throws DAOException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist=false;
		
		String circleExists="SELECT LOB_ID FROM CIRCLE_MSTR WHERE LOB_ID=? and CIRCLE_NAME=? and STATUS='A' WITH UR";

		try {
			con = DBConnection.getDBConnection();
			
			ps = con.prepareStatement(circleExists);
			
			ps.setInt(1,circleMstrDto.getLobId());
			ps.setString(2, circleMstrDto.getCircleName());
			
			rs=ps.executeQuery();
			

			
			if(rs==null)
			{
				logger.info("Circle Doesnt Exist");
				isExist=false;
			}
			
			else if(rs.next())
			{	
				logger.info("Circle exists");
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
	
	/**
	 * 
	 * @param productName
	 * @return
	 * @throws DAOException
	 */
	public List<DataObject> getCircleDetailsUsingProductName(String productName) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DataObject> circleStateList = new ArrayList<DataObject>();
		
		try 
		{
			con = DBConnection.getDBConnection();

//			String query = "select CIRCLE_MSTR_ID, State_name from STATE_MSTR where CIRCLE_MSTR_ID in (Select circle_id from CIRCLE_MSTR where LOB_ID = "+
//				"(Select product_lob_id from PRODUCT_MSTR where PRODUCT_NAME = '"+ productName +"')) with ur";
//			
			//logger.info(GET_CIRCLE_STATE_DETAILS);
			
			ps = con.prepareStatement(GET_CIRCLE_STATE_DETAILS);
			ps.setString(1, productName);
			rs = ps.executeQuery();
			boolean isRecordExist = false;
			while(rs.next())
			{
				isRecordExist = true;
				String circleId = rs.getString(1);
				String stateName = rs.getString(2);
				
				DataObject obj = new DataObject();
				obj.setCode(circleId);
				obj.setName(stateName);
				circleStateList.add(obj);
			}
			if(!isRecordExist)
				return null;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException("Exception occured while getting circle details :  "+ e.getMessage(),e);
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
		return circleStateList;
	}

	/**
	 * 
	 */
	public List<String> getPincodeUsingProductName(String productName) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> pincodeList = new ArrayList<String>();
		try 
		{
			con = DBConnection.getDBConnection();
//			String query = "select pincode from pincode_mstr where CITY_ZONE_CODE in (select city_zone_code " +
//					" from CITY_ZONE_MSTR where city_code in" +  
//					"(select city_code from city_mstr where zone_code in (select zone_code from ZONE_MSTR " +
//					"where CIRCLE_MSTR_ID in (Select CIRCLE_MSTR_ID from CIRCLE_MSTR where LOB_ID = " +
//					"(Select product_lob_id from PRODUCT_MSTR where PRODUCT_NAME = '"+productName+"'))))) with ur";
			
			//logger.info(GET_PINCODE_DETAILS);
			
			ps = con.prepareStatement(GET_PINCODE_DETAILS);
			ps.setString(1, productName);
			rs = ps.executeQuery();
			
			boolean isRecordExist = false; 
			while(rs.next())
			{
				isRecordExist = true;
				String pincode = rs.getString(1);
				pincodeList.add(pincode);
			}
			
			if(!isRecordExist)
				return null;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException("Exception occured while getting pincode details :  "+ e.getMessage(),e);
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
		//logger.info(pincodeList);
		return pincodeList;
	}

	/**
	 * 
	 */
	public List<DataObject> getCityUsingProductName(String product) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DataObject> cityList = new ArrayList<DataObject>();
		try 
		{
			con = DBConnection.getDBConnection();
//			String query = "select city_code, city_name from city_mstr where zone_code in (select zone_code from ZONE_MSTR where CIRCLE_MSTR_ID in" + 
//						   "(Select CIRCLE_MSTR_ID from CIRCLE_MSTR where LOB_ID = (Select product_lob_id from PRODUCT_MSTR where PRODUCT_NAME = '"+product+"'))) with ur";
//			
			//logger.info(GET_CITY_DETAILS);
			
			ps = con.prepareStatement(GET_CITY_DETAILS);
			ps.setString(1, product);
			rs = ps.executeQuery();
			
			boolean isRecordExist = false;
			while(rs.next())
			{
				isRecordExist = true;
				String cityCode = rs.getString(1);
				String circleName = rs.getString(2);
				
				DataObject obj = new DataObject();
				obj.setCode(cityCode);
				obj.setName(circleName);
				
				cityList.add(obj);
			}
			
			if(!isRecordExist)
				return null;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException("Exception occured while getting city details :  "+ e.getMessage(),e);
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
		return cityList;
	}
	
	/**
	 * 
	 * @param circleList
	 * @param cityList
	 * @param pincodeList
	 * @param product
	 * @return
	 */
	private PushMstrWebserviceDTO[] createArrayFromGivenList(List<DataObject> circleList, List<DataObject> cityList, List<String> pincodeList, String product)
	{
		PushMstrWebserviceDTO[] array = new PushMstrWebserviceDTO[1];
		array[0] = new PushMstrWebserviceDTO();
		array[0].setCircleData(Utility.convertListToArray(circleList));
		array[0].setCityData(Utility.convertListToArray(cityList));
		array[0].setPincodeList(Utility.convertListToArray(pincodeList));
		return array;
	}
	
	
	/**
	 * 
	 */
	public PushMstrWebserviceDTO[] getFindAllDetailsUsingProductName(String product) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		
		List<DataObject> cityList = new ArrayList<DataObject>();
		List<DataObject> circleList = new ArrayList<DataObject>();
		List<String> pincodeList = new ArrayList<String>();
		
		try 
		{
			con = DBConnection.getDBConnection();

			logger.info(GET_CITY_DETAILS);
			
			ps1 = con.prepareStatement(GET_CITY_DETAILS);
			ps1.setString(1, product);
			rs1 = ps1.executeQuery();
			
			boolean isRecordExist = false;
			
			while(rs1.next())
			{
				isRecordExist = true;
				String cityCode = rs1.getString(1);
				String circleName = rs1.getString(2);
				
				DataObject obj = new DataObject();
				obj.setCode(cityCode);
				obj.setName(circleName);
				
				cityList.add(obj);
			}
			
			//logger.info(GET_CIRCLE_STATE_DETAILS);
			
			ps2 = con.prepareStatement(GET_CIRCLE_STATE_DETAILS);
			ps2.setString(1, product);
			rs2 = ps2.executeQuery();
			
			while(rs2.next())
			{
				isRecordExist = true;
				String circleCode = rs2.getString(1);
				String stateName = rs2.getString(2);
				
				DataObject obj = new DataObject();
				obj.setCode(circleCode);
				obj.setName(stateName);
				
				circleList.add(obj);
			}
			
			//logger.info(GET_PINCODE_DETAILS);
			
			ps3 = con.prepareStatement(GET_PINCODE_DETAILS);
			ps3.setString(1, product);
			rs3 = ps3.executeQuery();
			
			while(rs3.next())
			{
				isRecordExist = true;
				pincodeList.add(rs3.getString(1));
			}
			
			return isRecordExist? createArrayFromGivenList(circleList, cityList, pincodeList, product): null;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException("Exception occured while getting city details :  "+ e.getMessage(),e);
		}
		finally 
		{
			try 
			{
				if(rs2 != null)
					rs2.close();
				if(rs3 != null)
					rs3.close();
				
				if(ps2 != null)
					ps2.close();
				if(ps3 != null)
					ps3.close();
				
				DBConnection.releaseResources(con, ps1, rs1);
			} 
			catch (Exception e) 
			{				
				throw new DAOException(e.getMessage(), e);
			}
		}
	}


	/**
	 * 
	 * @param product
	 * @return
	 */
	public boolean isValidProduct(String product) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(IS_VALID_PROJECT);
			ps.setString(1, product);
			//logger.info(query);

			rs = ps.executeQuery();
			if(rs.next() && rs.getInt(1) >0)
			{
				return true;
			}
			return false;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException("Exception occured while getting city details :  "+ e.getMessage(),e);
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

	//Added By Bhaskar
	
	private Long isValidLeadId(String leadId , Connection con ,String trxnId)  throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long lead_Id = null ;
		String [] tempTrxId =null;
		Pattern pattern = Pattern.compile(".*[^0-9].*");
		try 
		{
			if(leadId !=null && leadId.length() >0 && !pattern.matcher(leadId).matches())
			{
				
				ps = con.prepareStatement(IS_VALID_LEADID);
				ps.setLong(1, Long.parseLong(leadId.toString()));
				rs = ps.executeQuery();
				if(rs.next())
				{
					return rs.getLong(1);
				}
			}
		 if(trxnId !=null && trxnId.length() >0 && lead_Id ==null)
			{
				//logger.info("transaction id block");
				if(trxnId.contains("-")) {
				 tempTrxId = trxnId.split("-");
				 trxnId = tempTrxId[1];
				}
				if(!pattern.matcher(trxnId.trim()).matches()) {
				ps = con.prepareStatement(IS_VALID_TXNID);
				//logger.info("transction"+trxnId);
				ps.setLong(1, Long.parseLong(trxnId));
				rs = ps.executeQuery();
				if(rs.next())
				{
					return rs.getLong(1);
				}
				}
			}
			
				
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("error"+e);
			//throw new DAOException("Exception occured while getting city details :  "+ e.getMessage(),e);
		}
		finally 
		{
			try 
			{
				DBConnection.releaseResources(null, ps, rs);
			} 
			catch (Exception e) 
			{				
				//throw new DAOException(e.getMessage(), e);
			}
		}
		
		return lead_Id;
	}

	//TxnId Validation
		private long isValidTxnId(String transactionId)  throws DAOException {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Long transaction_Id = 0L;
			try 
			{
				con = DBConnection.getDBConnection();
				ps = con.prepareStatement(IS_VALID_TXNID);
				ps.setInt(1, Integer.parseInt(transactionId));
				rs = ps.executeQuery();
				
				if(rs.next()) 
					transaction_Id=rs.getLong("LEAD_ID");	
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DAOException("Exception occured while getting city details :  "+ e.getMessage(),e);
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
			return transaction_Id;
		}
		
		
	
	
	private ArrayList<RetrieveLeadDataNewDTO> resultHistorySetDataVersion1(Connection con ,RetrieveLeadDataNewDTO dtoMain) throws DAOException
	{
		
		PreparedStatement psLeadProspectdetailH=null;
		ResultSet rsLeadProspectdetailH = null;
		RetrieveLeadDataNewDTO dto  = null;
		ArrayList<RetrieveLeadDataNewDTO> leadHstDetailsList = new ArrayList<RetrieveLeadDataNewDTO>();
		
		
	  try {
		  
			String leadId = dtoMain.getLeadId();
			psLeadProspectdetailH=con.prepareStatement(GET_HISTORY);
			psLeadProspectdetailH.setString(1,dtoMain.getProspectId());
			
			psLeadProspectdetailH.setString(2,dtoMain.getLeadProspectId());
			
			psLeadProspectdetailH.setString(3,dtoMain.getLeadId());
			
			
			rsLeadProspectdetailH = psLeadProspectdetailH.executeQuery();
			
			
			while(rsLeadProspectdetailH.next()) 
			{
				
				dto=new RetrieveLeadDataNewDTO();
				//dto.setLeadId(leadId);
				dto.setRequestCategory(rsLeadProspectdetailH.getString(ColumnKeys.REQUEST_CATEGORY));
				
				dto.setOpportunityTime(rsLeadProspectdetailH.getString(ColumnKeys.OPPORTUNITY_TIME));
				dto.setUtmLAbels(rsLeadProspectdetailH.getString(ColumnKeys.UTM_LABELS));
				dto.setAgencyId(rsLeadProspectdetailH.getString(ColumnKeys.AGENCY_ID));
				dto.setNdncDisclaimer(rsLeadProspectdetailH.getString(ColumnKeys.NDNC_DISCLAIMER));
				dto.setUdId(rsLeadProspectdetailH.getString(ColumnKeys.UD_ID));
				dto.setFeasibilityparam(rsLeadProspectdetailH.getString(ColumnKeys.FEASIBILITY_PARAM));
				dto.setPlanId(rsLeadProspectdetailH.getString(ColumnKeys.PLAN_ID));
				dto.setCustomerInfoId(rsLeadProspectdetailH.getString(ColumnKeys.CUSTOMER_INFO_ID));
				dto.setRentaltype(rsLeadProspectdetailH.getString(ColumnKeys.RENTAL_TYPE));
				dto.setFreebietaken(rsLeadProspectdetailH.getString(ColumnKeys.FREEBIE_TAKEN));
				dto.setFreebiecount(rsLeadProspectdetailH.getString(ColumnKeys.FREEBIE_COUNT));
				dto.setBoostercount(rsLeadProspectdetailH.getString(ColumnKeys.BOOSTER_COUNT));
				dto.setBoostertaken(rsLeadProspectdetailH.getString(ColumnKeys.BOOSTER_TAKEN));
				dto.setFlag(rsLeadProspectdetailH.getString(ColumnKeys.FLAG));
				dto.setPrepaidnumber(rsLeadProspectdetailH.getString(ColumnKeys.PREPAID_NUMBER));
				dto.setOffer(rsLeadProspectdetailH.getString(ColumnKeys.OFFER));
				dto.setDownloadlimit(rsLeadProspectdetailH.getString(ColumnKeys.DOWNLOAD_LIMIT));
				dto.setDevicemrp(rsLeadProspectdetailH.getString(ColumnKeys.DEVICE_MRP));
				dto.setVoicebenefit(rsLeadProspectdetailH.getString(ColumnKeys.VOICE_BENEFIT));
				dto.setDataquota(rsLeadProspectdetailH.getString(ColumnKeys.DATA_QUOTA));
				dto.setUsertype(rsLeadProspectdetailH.getString(ColumnKeys.USER_TYPE));
				dto.setDevicetaken(rsLeadProspectdetailH.getString(ColumnKeys.DEVICE_TAKEN));
				dto.setBenefit(rsLeadProspectdetailH.getString(ColumnKeys.BENEFIT));
				dto.setHlrno(rsLeadProspectdetailH.getString(ColumnKeys.HLR_NO));
				dto.setDob(rsLeadProspectdetailH.getString(ColumnKeys.DOB));
				dto.setPkgduration(rsLeadProspectdetailH.getString(ColumnKeys.PKG_DURATION));
				dto.setSimNo(rsLeadProspectdetailH.getString(ColumnKeys.EXTRA_PARAM1));//SIM
				dto.setExtraParam3(rsLeadProspectdetailH.getString(ColumnKeys.EXTRA_PARAM3));//LATITUDE as EXTRA_PARAM3 in lead details
				dto.setExtraParam4(rsLeadProspectdetailH.getString(ColumnKeys.EXTRA_PARAM4)); //LONGITUDE as EXTRA_PARAM4 in lead details
				dto.setNationality(rsLeadProspectdetailH.getString(ColumnKeys.EXTRA_PARAM5)); //NATIONALITY as EXTRA_PARAM5 in lead details
				dto.setPaymentStatus(rsLeadProspectdetailH.getString(ColumnKeys.EXTRA_PARAM6)); //payment status in EXTRA_PARAM6 of lead details
				dto.setGender(rsLeadProspectdetailH.getString(ColumnKeys.EXTRA_PARAM7)); // gender in EXTRA_PARAM7 in lead details
				dto.setIdProofType(rsLeadProspectdetailH.getString(ColumnKeys.EXTRA_PARAM8)); // identity proof type in EXTRA_PARAM8 in lead details
				dto.setCompetitorChosen(rsLeadProspectdetailH.getString(ColumnKeys.COMPETITOR_CHOSEN));
				dto.setSentBy(rsLeadProspectdetailH.getString(ColumnKeys.SENTBY));
				dto.setProductBought(rsLeadProspectdetailH.getString(ColumnKeys.PRODUCT_BOUGHT));
				dto.setPaymentType(rsLeadProspectdetailH.getString(ColumnKeys.PAYMENT_TYPE));
				dto.setTaskStartTime(rsLeadProspectdetailH.getString(ColumnKeys.TASK_START_TIME));
				dto.setTaskEndTime(rsLeadProspectdetailH.getString(ColumnKeys.TASK_END_TIME));
				dto.setAssignedremarks(rsLeadProspectdetailH.getString(ColumnKeys.ASSIGNED_REMARKS));
				dto.setProspectId(rsLeadProspectdetailH.getString(ColumnKeys.PROSPECT_ID));
				dto.setProspectsName(rsLeadProspectdetailH.getString(ColumnKeys.CUSTOMER_NAME));
				dto.setEmail(rsLeadProspectdetailH.getString(ColumnKeys.EMAIL)); 
				dto.setAlternateContactNumber(rsLeadProspectdetailH.getString(ColumnKeys.ALTERNATE_CONTACT_NUMBER));
				dto.setLeadProspectId(rsLeadProspectdetailH.getString(ColumnKeys.LEAD_PROSPECT_ID));
				dto.setCityZoneCode(rsLeadProspectdetailH.getString(ColumnKeys.CITY_ZONE_CODE));
				dto.setCity(rsLeadProspectdetailH.getString("CITY"));
				dto.setCityId(rsLeadProspectdetailH.getString(ColumnKeys.CITY_CODE));   
				dto.setState(rsLeadProspectdetailH.getString(ColumnKeys.STATE));    
				dto.setZone(rsLeadProspectdetailH.getString("ZONE"));
				//dto.setCircle(rsLeadProspectdetailH.getString("CIRCLE"));
				dto.setCircleId(rsLeadProspectdetailH.getString(ColumnKeys.CIRCLE_ID));
				dto.setPincode(rsLeadProspectdetailH.getString(ColumnKeys.PINCODE));
				dto.setRsuCode(rsLeadProspectdetailH.getString(ColumnKeys.RSU_CODE));
				dto.setAddress(rsLeadProspectdetailH.getString(ColumnKeys.ADDRESS1));
				dto.setAddress2(rsLeadProspectdetailH.getString(ColumnKeys.ADDRESS2));
			    dto.setPrimaryLanguage(rsLeadProspectdetailH.getString(ColumnKeys.PRIMARY_LANGUAGE));
			    dto.setCompany(rsLeadProspectdetailH.getString(ColumnKeys.COMPANY));
			    dto.setAppointmentTime(rsLeadProspectdetailH.getString(ColumnKeys.APPOINTMENT_TIME));
			    dto.setAppointmentEndTime(rsLeadProspectdetailH.getString(ColumnKeys.APPOINMENT_ENDTIME));  
			    dto.setIsCustomer(rsLeadProspectdetailH.getString(ColumnKeys.IS_CUSTOMER));
			    dto.setMaritalStatus(rsLeadProspectdetailH.getString(ColumnKeys.MARITAL_STATUS));
				dto.setSubZone(rsLeadProspectdetailH.getString(ColumnKeys.SUB_ZONE));
				dto.setRental(rsLeadProspectdetailH.getString(ColumnKeys.RENTAL));
				dto.setPytAmt(rsLeadProspectdetailH.getString(ColumnKeys.PYT_AMT));	
				dto.setGeoIpCity(rsLeadProspectdetailH.getString(ColumnKeys.GEO_IP_CITY));
				dto.setAdParameter(rsLeadProspectdetailH.getString(ColumnKeys.AD_PARAMETER));
				dto.setQualLeadParam(rsLeadProspectdetailH.getString(ColumnKeys.QUAL_LEAD_PARAM));
				dto.setTag(rsLeadProspectdetailH.getString(ColumnKeys.TAG));
				dto.setTranRefno(rsLeadProspectdetailH.getString(ColumnKeys.TRAN_REFNO));
				
				leadHstDetailsList.add(dto);
				}
		
	  } catch (Exception e) {
			throw new DAOException("Exception occured while getting Master Types list :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, psLeadProspectdetailH, rsLeadProspectdetailH);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		return leadHstDetailsList;
	}
	
	
	private List<RetrieveLeadDataNewDTO> resultSetDataNew(ResultSet rs) {
		RetrieveLeadDataNewDTO dto =null;
		List<RetrieveLeadDataNewDTO> leadList=null;
		
		try {

			leadList=new ArrayList<RetrieveLeadDataNewDTO>();
			while(rs.next())
			{				
				dto=new RetrieveLeadDataNewDTO();
				dto.setLeadProspectId(rs.getString("LEAD_PROSPECT_ID"));
				dto.setProspectId(rs.getString("PROSPECT_ID"));
				dto.setLeadId(String.valueOf(rs.getLong("LEAD_ID")));
				dto.setTxnId((String.valueOf(rs.getInt("LEAD_CAPTURED_DATA_ID"))));
				dto.setProspectsMobileNumber(rs.getString("PROSPECT_MOBILE_NUMBER"));
				dto.setSource(rs.getString("SOURCE"));
				dto.setSubSource(rs.getString("SUB_SOURCE"));
				dto.setTid(rs.getString("CID"));
				dto.setFid(rs.getString("FID"));
				dto.setProductLob((rs.getString("PRODUCT")));
				dto.setProductID((rs.getString("PRODUCT_ID")));
				//dto.setRemarks(rs.getString("REMARKS"));
				dto.setCampaign(rs.getString("CAMPAIGN"));
				dto.setReferPage(rs.getString("REFERER_PAGE"));
				dto.setFromPage(rs.getString("FROM_PAGE"));
				dto.setService(rs.getString("SERVICE"));
				dto.setReferUrl(rs.getString("REFERER_URL"));
				dto.setPlan(rs.getString("PLAN"));
				dto.setOnlineCafNo(rs.getString("ONLINE_CAF_NO"));
				dto.setStatus(rs.getString("LEAD_STATUS"));
				dto.setSubStatus(rs.getString("LEAD_SUB_STATUS"));
				dto.setCafNumber(rs.getString("CAF"));
				dto.setRentalPlan(rs.getString("RENTAL_PLAN"));
				dto.setKeyWord(rs.getString("KEYWORD"));
				dto.setLeadSubmitTime(rs.getString("LEAD_SUBMIT_TIME"));
				dto.setProspectsName(rs.getString("CUSTOMER_NAME"));
				dto.setEmail(rs.getString("EMAIL"));
				dto.setAlternateContactNumber(rs.getString("ALTERNATE_CONTACT_NUMBER"));
				dto.setLandlineNumber(rs.getString("LANDLINE_NUMBER"));
				dto.setRequestCategory(rs.getString("REQUEST_CATEGORY"));
				dto.setOpportunityTime(rs.getString("OPPORTUNITY_TIME"));
				dto.setUtmLAbels(rs.getString("UTM_LABELS"));
				dto.setAgencyId(rs.getString("AGENCY_ID"));
				dto.setNdncDisclaimer(rs.getString("NDNC_DISCLAIMER"));
				dto.setUdId(rs.getString("UD_ID"));
				dto.setFeasibilityparam(rs.getString("FEASIBILITY_PARAM"));
				dto.setPlanId(rs.getString("PLAN_ID"));
				dto.setCustomerInfoId(rs.getString("CUSTOMER_INFO_ID"));
				dto.setRentaltype(rs.getString("RENTAL_TYPE"));
				dto.setFreebiecount(rs.getString("FREEBIE_COUNT"));
				dto.setFreebietaken(rs.getString("FREEBIE_TAKEN"));
				dto.setBoostercount(rs.getString("BOOSTER_COUNT"));
				dto.setBoostertaken(rs.getString("BOOSTER_TAKEN"));
				dto.setFlag(rs.getString("FLAG"));
				dto.setPrepaidnumber(rs.getString("PREPAID_NUMBER"));
				dto.setOffer(rs.getString("OFFER"));
				dto.setDownloadlimit(rs.getString("DOWNLOAD_LIMIT"));
				dto.setDevicemrp(rs.getString("DEVICE_MRP"));
				dto.setVoicebenefit(rs.getString("VOICE_BENEFIT"));
				dto.setDataquota(rs.getString("DATA_QUOTA"));
				dto.setUsertype(rs.getString("USER_TYPE"));
				dto.setDevicetaken(rs.getString("DEVICE_TAKEN"));
				dto.setBenefit(rs.getString("BENEFIT"));
				dto.setPkgduration(rs.getString("PKG_DURATION"));
				dto.setHlrno(rs.getString("HLR_NO"));
				dto.setDob(rs.getString("DOB"));
				//dto.setExtraParam1(rs.getString("EXTRA_PARAM1"));
				//dto.setExtraParam2(rs.getString("EXTRA_PARAM2"));
				dto.setLatitude(rs.getString("EXTRA_PARAM3"));//Latitude
				dto.setLongitude(rs.getString("EXTRA_PARAM4"));//Longitude
				//dto.setExtraParam5(rs.getString("EXTRA_PARAM5"));
			//	dto.setExtraParam6(rs.getString("EXTRA_PARAM6"));
			//	dto.setExtraParam7(rs.getString(ColumnKeys.EXTRA_PARAM7));
				//dto.setExtraParam8(rs.getString("EXTRA_PARAM8"));
				dto.setCompetitorChosen(rs.getString(ColumnKeys.COMPETITOR_CHOSEN));
				dto.setSentBy(rs.getString("SENTBY"));
				dto.setProductBought(rs.getString("PRODUCT_BOUGHT"));
				dto.setPaymentCollected(rs.getString("PAYMENT_COLLECTED"));
				dto.setPaymentType(rs.getString("PAYMENT_TYPE"));					
				dto.setPaymentStatus(rs.getString("EXTRA_PARAM6"));
				dto.setAssignedremarks(rs.getString("ASSIGNED_REMARKS"));
				dto.setAmtdue(rs.getString("PYT_AMT_DUE"));
				dto.setTaskStartTime(rs.getString("TASK_START_TIME"));
				dto.setTaskEndTime(rs.getString("TASK_END_TIME"));
				dto.setAllocatedNo(rs.getString(ColumnKeys.ALLOCATED_NO));
				dto.setRemarks(rs.getString(ColumnKeys.REMARKS));
				dto.setAddress(rs.getString(ColumnKeys.ADDRESS1));
				dto.setAddress2(rs.getString(ColumnKeys.ADDRESS2));
				dto.setCity(rs.getString("CITY"));	
				dto.setPincode(String.valueOf(rs.getInt("PINCODE")));
				dto.setState(rs.getString(ColumnKeys.STATE));	
				dto.setCircle(rs.getString("CIRCLE"));
				dto.setMaritalStatus(rs.getString(ColumnKeys.MARITAL_STATUS));
				dto.setAppointmentTime(rs.getString(ColumnKeys.APPOINTMENT_TIME));
				dto.setAppointmentEndTime(rs.getString(ColumnKeys.APPOINMENT_ENDTIME));
				dto.setPrimaryLanguage(rs.getString(ColumnKeys.PRIMARY_LANGUAGE));	
				dto.setSubZone(rs.getString(ColumnKeys.SUB_ZONE));
				dto.setZone(rs.getString("ZONE"));
				dto.setIsCustomer(rs.getString(ColumnKeys.IS_CUSTOMER));
				dto.setAdParameter(rs.getString(ColumnKeys.AD_PARAMETER));
				dto.setCompany(rs.getString(ColumnKeys.COMPANY));
				dto.setCityZoneCode(rs.getString(ColumnKeys.CITY_ZONE_CODE));
				dto.setPytAmt(rs.getString(ColumnKeys.PYT_AMT));	
				dto.setCityId(rs.getString(ColumnKeys.CITY_CODE));	
				dto.setRsuCode(rs.getString(ColumnKeys.RSU_CODE));
				dto.setPaymentAmount(rs.getString(ColumnKeys.PYT_AMT));
				dto.setCircleId(String.valueOf(rs.getInt(ColumnKeys.CIRCLE_ID)));
				dto.setQualLeadParam(rs.getString(ColumnKeys.QUAL_LEAD_PARAM));
				dto.setGeoIpCity(rs.getString(ColumnKeys.GEO_IP_CITY));
				dto.setRental(rs.getString(ColumnKeys.RENTAL));
				dto.setTag(rs.getString(ColumnKeys.TAG));
				dto.setTranRefno(rs.getString(ColumnKeys.TRAN_REFNO));
				
				dto.setExtraParam3(rs.getString("EXTRA_PARAM9"));
				dto.setSimNo(rs.getString(ColumnKeys.SIMNO));
				dto.setCustSegVal(rs.getString(ColumnKeys.SEGMENT));
				dto.setGender(rs.getString(ColumnKeys.GENDER));
				dto.setNationality(rs.getString(ColumnKeys.NATIONALITY));
				dto.setIdProofType(rs.getString(ColumnKeys.IDPROOFTYPE));
				dto.setIdProofID(rs.getString(ColumnKeys.IDENTITYPROOF));
				dto.setFathMothHus(rs.getString(ColumnKeys.RELATION));
				dto.setPaymDT(rs.getString(ColumnKeys.PAYMENTDATE));
				dto.setUpc(rs.getString(ColumnKeys.UPC));
				dto.setUpcGenDT(rs.getString(ColumnKeys.UPCGENDT));
				dto.setPrevOperName(rs.getString(ColumnKeys.PREVOP));
				dto.setPrevCircleName(rs.getString(ColumnKeys.PREVCIR));
				dto.setPrePost(rs.getString(ColumnKeys.EXISTPART));
				dto.setMnpStatus(rs.getString(ColumnKeys.MNPSTATUS));
				dto.setDocCollFlag(rs.getString(ColumnKeys.DOC_COLLECTED_FLAG));  
				dto.setPlanType(rs.getString(ColumnKeys.PLAN_TYPE));
				
				
				
				//dto.setExtraParam10(rs.getString("EXTRA_PARAM10"));
				
				leadList.add(dto);
		}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		//if(leadList !=null && leadList.size() >0 )
		//dtos  = leadList.toArray(new RetrieveLeadDataDTO [leadList.size()]);
		return leadList; 
		
	}
	/*private long isValidMobile(String mobile)  throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long mobile = 0L;
		try 
		{
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(IS_VALID_TXNID);
			ps.setInt(1, Integer.parseInt(mobile));
			rs = ps.executeQuery();
			
			if(rs.next()) 
				mobile=rs.getLong("MOBILE");	
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException("Exception occured while getting city details :  "+ e.getMessage(),e);
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
		return transaction_Id;
	}*/
	
	private long transIdExist(String transactionId)  throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long transaction_Id = 0L;
		try 
		{
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(TRANS_ID_EXIST);
			ps.setInt(1, Integer.parseInt(transactionId));
			rs = ps.executeQuery();
			
			if(rs.next()) 
				transaction_Id=rs.getLong("LEAD_CAPTURED_DATA_ID");	
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException("Exception occured while getting city details :  "+ e.getMessage(),e);
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
		return transaction_Id;
	}

	@Override
	public RetrieveLeadDataNewDTO[] getLeadListNew(String leadId,
			String prospectMobileNumber, String transactionId, String flag,
			String product, String source) throws DAOException {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement psLeadData = null;
		ResultSet rsLeadData = null;
		List<RetrieveLeadDataNewDTO>  retrieveLeadDataDTOs =null;
		RetrieveLeadDataNewDTO[] dtos =null;
		boolean mobilFlag = false;
		Pattern pattern = Pattern.compile(".*[^0-9].*");
		 logger.info("Circle Mgmt dao impl product->>>>>>>>"+"leadId--"+leadId+"prospectMobileNumber-"+prospectMobileNumber+"transactionId"+transactionId+"product"+product+"source----"+source);
		GetLeadCaptureData getLeadCaptureData=new GetLeadCaptureData();
		try {
			
			con = DBConnection.getDBConnection();
			StringBuffer query=new StringBuffer(GET_LEAD_DATA_NEW);
			logger.info(leadId+"===lead retrieved lead data start  for transactionId===="+transactionId);
			Long lead =null;
			/*changes by Nancy*/
			if(transactionId !=null && transactionId.contains("-")) {
				String tempTrxId [] = transactionId.split("-");
				String  Trxn = tempTrxId[1];
				transactionId = Trxn.trim(); 
				 
				}
			//lead id is valid or transaction id id valid:
			lead  =isValidLeadId(leadId, con, transactionId);
			if(lead == null && transactionId !=null && transactionId.length() > 0 && !pattern.matcher(transactionId.trim()).matches() && !getLeadCaptureData.getLcIsDoneStatus(transactionId) ){
				List<CaptureLeadDetailsDTO> captureList= GetLeadCaptureData.captureLeadData(Integer.parseInt(transactionId.trim()));
			 	if(captureList.size() >0)
			 	getLeadCaptureData.insertLeadData(captureList);
			}
			logger.info("valid--+"+lead);
			lead = isValidLeadId(leadId, con, transactionId);
			logger.info("lead---"+lead);
			if(lead !=null && lead >0) {
				query.append(" AND LD.LEAD_ID = ?");
				psLeadData = con.prepareStatement(query.toString() +" WITH UR");
				logger.info("lead"+lead);
				psLeadData.setLong(1, lead);
				logger.info("query-only lead \n"+query);
				rsLeadData = psLeadData.executeQuery();
				
				retrieveLeadDataDTOs = resultSetDataNew(rsLeadData);
			}else if((source.trim().length()<0 ||source.trim().equalsIgnoreCase(""))&&prospectMobileNumber !=null && prospectMobileNumber.trim().length() >0 && (leadId.trim().length()>0||transactionId.trim().length()>0) ){
				query.append(" AND LPC.PROSPECT_MOBILE_NUMBER = '"+prospectMobileNumber+"' FETCH FIRST 5 ROW ONLY");
				mobilFlag  = true;
				logger.info("query only mobile"+query);
				psLeadData = con.prepareStatement(query.toString() +" WITH UR");
				rsLeadData = psLeadData.executeQuery();
				retrieveLeadDataDTOs = resultSetDataNew(rsLeadData);
			}
			
				
		//for History:
		if(!mobilFlag && flag !=null && flag.equalsIgnoreCase("H") && retrieveLeadDataDTOs != null && retrieveLeadDataDTOs.size() >0) 
		 {
				 
			RetrieveLeadDataNewDTO dtoMain = retrieveLeadDataDTOs.get(0);
			 ArrayList<RetrieveLeadDataNewDTO> list=  resultHistorySetDataVersion1(con, dtoMain);
			 
			 if(list ==null || list.size()<=0)
			 {
				 RetrieveLeadDataNewDTO dataDTO  = new RetrieveLeadDataNewDTO();
				 dataDTO = dtoMain;
				 retrieveLeadDataDTOs.add(dataDTO);
			 }else {
				 retrieveLeadDataDTOs.addAll(list);
			 }
		 }
		logger.info("leadid length"+leadId.trim().length());
		logger.info("transactionId length"+transactionId.trim().length());
		if((leadId.trim().length()<=0 || "".equalsIgnoreCase(leadId.trim())) && (transactionId.trim().length()<=0 || "".equalsIgnoreCase(transactionId.trim())))
		{
			logger.info("product-----------"+product.trim().length());
			logger.info("source==="+source.trim().length());
			 if((product.trim().length()>0 || !"".equalsIgnoreCase(product.trim()))&&(source.trim().length()>0 ))
			 {
				 query.append("\nAND LD.PRODUCT_ID =(select PRODUCT_ID from PRODUCT_SYNONYM where ucase(PRODUCT_SYNONYM_NAME)= ucase(?) fetch first row only)");
				 logger.info("query->>>>>-----------"+query);
			 }
		 if(prospectMobileNumber.trim().length() >0 && source.trim().length()>0 )
		 {
			 query.append("\nAND Ld.SOURCE =(select SOURCE_ID  from SOURCE_MSTR where ucase(SOURCE_NAME) = ucase(?) fetch first row only)");
			 query.append("\nAND LPC.PROSPECT_MOBILE_NUMBER=?");
			 psLeadData=con.prepareStatement(query.toString() +" WITH UR");
			 logger.info("query with source"+query);
			 if((product.trim().length()>0 || !"".equalsIgnoreCase(product.trim()))&&(source.trim().length()>0 ))
			 {
			 psLeadData.setString(1, product);
			 psLeadData.setString(2, source);
			 psLeadData.setString(3, prospectMobileNumber);
			 }
			 else{
				 psLeadData.setString(1, source);
				 psLeadData.setString(2, prospectMobileNumber);
			 }
			 rsLeadData = psLeadData.executeQuery();
			retrieveLeadDataDTOs = resultSetDataNew(rsLeadData);
			logger.info("Circle Mgmt dao impl product->>>>>>>>"+product+"source----"+source);
			 
		 }
		}
		
			  if(retrieveLeadDataDTOs != null && retrieveLeadDataDTOs.size() >0) {
		 dtos  = retrieveLeadDataDTOs.toArray(new RetrieveLeadDataNewDTO [retrieveLeadDataDTOs.size()]); //history list to array
			  }
			
			  
			  
			  logger.info("===END Retrieved Lead data END");
		
			  
			  return dtos;
		 
		} catch (Exception e) {
			e.printStackTrace();
		}finally 
		{
			try 
			{
				DBConnection.releaseResources(con, psLeadData, null);
			} 
			catch (Exception e) 
			{		
				e.printStackTrace();
				//throw new DAOException(e.getMessage(), e);
			}
		}
		dtos  = retrieveLeadDataDTOs.toArray(new RetrieveLeadDataNewDTO [retrieveLeadDataDTOs.size()]); //history list to array
		 
		return dtos;
	}
	
	}
