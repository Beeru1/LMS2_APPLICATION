package com.ibm.lms.engine.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import com.ibm.core.exception.DAOException;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.dao.MasterDao;
import com.ibm.lms.dao.impl.MasterDaoImpl;
import com.ibm.lms.dto.CaptureLeadDetailsDTO;
import com.ibm.lms.dto.LeadAutoQualifiedDTO;
import com.ibm.lms.engine.dataobjects.UpdateLeadDataDO;
import com.ibm.lms.engine.util.Constants;
import com.ibm.lms.engine.util.ServerPropertyReader;

import com.ibm.lms.sms.SendSMSXML;

public class GetLeadCaptureData 

{

	private static final String SEQ_NAME_PROSPECTS_ID = "SEQ_PROSPECTS_ID";
	public static Map<String, String> statusMap = new HashMap<String, String>();
	boolean duplicateleadFlag = false;
	boolean rsuCodeFlag =  false;
	protected static final String SQL_GET_LEAD_DATA_CAPTURE = "SELECT * FROM LEAD_CAPTURE_DATA WHERE LEAD_CAPTURED_DATA_ID =? WITH UR";
	protected static final String INSERT_OTHER_LEAD_DETAILS = "INSERT INTO LEAD_DETAILS(CUSTOMER_INFO_ID, LEAD_ID, DOB, USER_TYPE, PLAN_ID, RENTAL_TYPE, STATE, UTM_LABELS, UD_ID, AGENCY_ID,BOOSTER_COUNT, FREEBIE_TAKEN,BOOSTER_TAKEN, FLAG,PREPAID_NUMBER,OFFER,DOWNLOAD_LIMIT,DEVICE_MRP,VOICE_BENEFIT,DATA_QUOTA, NDNC_DISCLAIMER, FEASIBILITY_PARAM,DEVICE_TAKEN,FREEBIE_COUNT, BENEFIT, PKG_DURATION, REQUEST_CATEGORY_ID,OPPORTUNITY_TIME, HLR_NO ,UTM_CID ,UTM_CONTENT,EXTRA_PARAM6,EXTRA_PARAM7,EXTRA_PARAM8,PAYMENT_TYPE,PAYMENT_COLLECTED,EXTRA_PARAM3,EXTRA_PARAM4,EXTRA_PARAM1,EXTRA_PARAM2,EXTRA_PARAM5,LEAD_CAPTURED_DATA_ID ,AUTO_ASSIGN)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?, ?, ?, ?, ?,?,?, ?,0)";
	protected static final String INSERT_LEAD_UPDATE_DATA = "INSERT INTO LEAD_UPDATE_DATA(LEAD_ID,IDENTITYPROOFID, RELATION_NAME,PAYMENT_DATE,UPC,UPC_GEN_DATE,PREVIOUS_OPERATOR,PREVIOUS_CIRCLE,EXISTING_PART,MNP_STATUS,DOC_COLLECTED_FLAG,PLAN_TYPE,RENTAL_PLAN,UPDATED_DT) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,current timestamp) ";

	protected static final String INSERT_OTHER_LEAD_DETAILS_HISTORY = "INSERT INTO LEAD_DETAILS_HISTORY (CUSTOMER_INFO_ID, LEAD_ID, DOB, USER_TYPE, PLAN_ID, RENTAL_TYPE, STATE, UTM_LABELS, UD_ID, AGENCY_ID,BOOSTER_COUNT, FREEBIE_TAKEN,BOOSTER_TAKEN, FLAG,PREPAID_NUMBER,OFFER,DOWNLOAD_LIMIT,DEVICE_MRP,VOICE_BENEFIT,DATA_QUOTA, NDNC_DISCLAIMER, FEASIBILITY_PARAM,DEVICE_TAKEN,FREEBIE_COUNT, BENEFIT, PKG_DURATION, REQUEST_CATEGORY,OPPORTUNITY_TIME, HLR_NO ,UTM_CID ,UTM_CONTENT,EXTRA_PARAM6,EXTRA_PARAM7,EXTRA_PARAM8,PAYMENT_TYPE,PAYMENT_COLLECTED,EXTRA_PARAM3,EXTRA_PARAM4,EXTRA_PARAM1,EXTRA_PARAM2,EXTRA_PARAM5,LEAD_CAPTURED_DATA_ID,WS_FLAG)VALUES(? ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?, ?, ?, ?, ?, ?,?,?, ?)";
	
	Logger logger = Logger.getLogger(GetLeadCaptureData.class);
	protected static final String SQL_GET_LEAD_DATA_DUMP = "SELECT * FROM LEAD_CAPTURE WHERE LC_ISDONE ="+Constants.WSFLAG+" and LEAD_CAPTURED_DATA_ID=? WITH UR ";
	protected static final String SQL_CHECK_PRODUCT = "SELECT pm.PRODUCT_ID from PRODUCT_MSTR pm , PRODUCT_SYNONYM ps where pm.PRODUCT_ID = ps.PRODUCT_ID and pm.STATUS = 'A' and ucase(PRODUCT_SYNONYM_NAME) = ? with ur"; 
	protected static final String SQL_CHECK_FID = " SELECT FORM_ID  FROM FORM_DETAILS WHERE FORM_ID = ? AND STATUS = 'A' WITH UR";
	protected static final String SQL_GET_MULTIPLE_LEAD = "SELECT PROSPECT_ID from LEAD_PROSPECT_CUSTOMER where PROSPECT_MOBILE_NUMBER = ? with ur ";
	protected static final String UPDATE_LEAD_PROSPECT_CUSTOMER_EMAIL ="UPDATE LEAD_PROSPECT_CUSTOMER SET EMAIL =?  WHERE PROSPECT_ID=? WITH UR ";
	protected static final String GET_PRODUCT_ID = "SELECT b.PRODUCT_ID as  PRODUCT_ID,c.PRODUCT_LOB_ID as PRODUCT_LOB_ID  "
		+" from PRODUCT_SYNONYM a,PRODUCT_MSTR b,PRODUCT_LOB c "
		+" where ucase(PRODUCT_SYNONYM_NAME)=? "
		+" and a.PRODUCT_ID=b.PRODUCT_ID "
		+" and b.PRODUCT_LOB_ID=c.PRODUCT_LOB_ID "
		+" with ur";
	protected static final String INSERT_LEAD_PROSPECT_DETAIL =" INSERT INTO LEAD_PROSPECT_DETAIL (RSU_CODE, CITY_CODE, CIRCLE_ID, ADDRESS2,PINCODE, ALTERNATE_CONTACT_NUMBER, ADDRESS1, PROSPECT_ID, IS_CUSTOMER, UPDATED_DT, UPDATED_BY," +
	"PRODUCT_LOB_ID, ZONE_CODE, CITY_ZONE_CODE, RENTAL, MYOP_ID, PYT_AMT, TRAN_REFNO, QUAL_LEAD_PARAM, AD_PARAMETER, EXTRA_PARAMS_2, EXTRA_PARAMS_3, GEO_IP_CITY, TAG, LEAD_ID,PYT_AMT_DUE,APPOINTMENT_TIME,PRIMARY_LANGUAGE,LANDLINE_NUMBER,MARITAL_STATUS,APPOINMENT_ENDTIME)"+
	" VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?,  current timestamp, ?,?,?,?,?,?,?,?,?,?,?,?,?,? ,?,?,?,?,?,?,?)";
	protected static final String SELECT_LEAD_PROSPECT_ID = "SELECT LEAD_PROSPECT_ID from LEAD_PROSPECT_DETAIL where LEAD_ID = ? with ur ";
	protected static final String SQL_INSERT_LEAD_DATA = "INSERT INTO LEAD_DATA(PRODUCT_ID, LEAD_STATUS_ID, " +
	" LEAD_ID, PROSPECT_ID, CREATE_TIME,  FID, CID, FROM_PAGE, REFERER_PAGE, " +
	" REFERER_URL, SERVICE, PLAN , KEYWORD , CAMPAIGN , SUB_SOURCE,SOURCE ,LEAD_PROSPECT_ID,UPDATED_DT,UPDATED_BY, ONLINE_CAF_NO, ALLOCATED_NO, LEAD_SUBMIT_TIME, LEAD_CATEGORY, UTM_SOURCE, UTM_TERM, UTM_MEDIUM, UTM_CAMPAIGN,REMARKS) " +
	" VALUES(?,?,?,?,current timestamp,?,?,?,?,?,?,? , ? , ? ,? , ? ,?,current timestamp,?, ?, ?, ?, ?, ?, ?, ?, ? , ?)" ;
	protected static final String SQL_INSERT_LEAD_TRANSACTION="INSERT INTO LEAD_TRANSACTION(PRODUCT_ID, LEAD_ASSIGNMENT_TIME, LEAD_STATUS_ID, LEAD_ID,TRANSACTION_TIME , SUB_STATUS_ID , UPDATED_BY, LEAD_PRODUCT_ID ) " 
	    +" VALUES( ? , current_timestamp , ? , ? , current_timestamp , ? , ?,? )";
	protected static final String SQL_INSERT_PROSPECTS_CUST="INSERT INTO LEAD_PROSPECT_CUSTOMER(CUSTOMER_NAME, EMAIL, PROSPECT_MOBILE_NUMBER,ALTERNATE_CONTACT_NUMBER,  PROSPECT_ID, CUSTOMER_FNAME,CUSTOMER_LNAME, COMPANY, UPDATED_BY,UPDATED_DT,MARITAL_STATUS,LANDLINE_NUMBER,PRIMARY_LANGUAGE)  VALUES(?,?,?,?,?,?,?,?,?,current timestamp,?,?,?)"; 
	//protected static final String INSERT_OTHER_LEAD_DETAILS = "INSERT INTO LEAD_DETAILS(CUSTOMER_INFO_ID, LEAD_ID, DOB, USER_TYPE, PLAN_ID, RENTAL_TYPE, STATE, UTM_LABELS, UD_ID, AGENCY_ID,BOOSTER_COUNT, FREEBIE_TAKEN,BOOSTER_TAKEN, FLAG,PREPAID_NUMBER,OFFER,DOWNLOAD_LIMIT,DEVICE_MRP,VOICE_BENEFIT,DATA_QUOTA, NDNC_DISCLAIMER, FEASIBILITY_PARAM,DEVICE_TAKEN,FREEBIE_COUNT, BENEFIT, PKG_DURATION, REQUEST_CATEGORY_ID,OPPORTUNITY_TIME, HLR_NO ,UTM_CID ,UTM_CONTENT,LEAD_CAPTURED_DATA_ID ,AUTO_ASSIGN)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?, ?, ?, ?, ?, ?,?,?, ?,0)";
	protected static final String UPDATE_LEAD_CAPTUER_DATA = " UPDATE LEAD_CAPTURE SET LC_ISDONE = 0 WHERE LEAD_CAPTURED_DATA_ID =? WITH UR ";
	protected static final String GET_CIRCLE_FROM_CITY ="SELECT cim.CIRCLE_ID from CITY_MSTR cm , ZONE_MSTR zm, CIRCLE_MSTR cim where  cm.zone_code = zm.zone_code and zm.circle_mstr_id = cim.circle_mstr_id and cim.lob_id = ? AND ucase(cm.CITY_NAME)= ? with ur ";
	protected static final String GET_REQUEST_CATEGORY_ID = "SELECT REQUEST_CATEGORY_ID from REQUEST_CATEGORY_MSTR  where ucase(REQUEST_CATEGORY)=? AND PRODUCT_ID =? AND STATUS = 'A' WITH UR";
	protected static final String SQL_INSERT_DIRTY_LEAD = "INSERT INTO DIRTY_LEAD(PROSPECT_NAME, PROSPECT_MOBILE_NUMBER, ALTERNATE_CONTACT_NUMBER, "
		+" ADDRESS1, CITY, PINCODE, CIRCLE, " 
		+" PRODUCT, EMAIL, CREATE_TIME , ERROR_MESSAGE , FID , CID, SUB_SOURCE, SOURCE,CAMPAIGN, REFERER_URL, REFERER_PAGE,LEAD_DETAILS_HISTORY_ID ) " 
		+" VALUES( ? ,? ,? ,? ,? ,?,? ,? ,?,current_timestamp , ? , ? , ?, ?, ?, ?, ? ,?,?)";
	//protected static final String INSERT_OTHER_LEAD_DETAILS_HISTORY = "INSERT INTO LEAD_DETAILS_HISTORY (CUSTOMER_INFO_ID, LEAD_ID, DOB, USER_TYPE, PLAN_ID, RENTAL_TYPE, STATE, UTM_LABELS, UD_ID, AGENCY_ID,BOOSTER_COUNT, FREEBIE_TAKEN,BOOSTER_TAKEN, FLAG,PREPAID_NUMBER,OFFER,DOWNLOAD_LIMIT,DEVICE_MRP,VOICE_BENEFIT,DATA_QUOTA, NDNC_DISCLAIMER, FEASIBILITY_PARAM,DEVICE_TAKEN,FREEBIE_COUNT, BENEFIT, PKG_DURATION, REQUEST_CATEGORY,OPPORTUNITY_TIME, HLR_NO ,UTM_CID ,UTM_CONTENT,WS_FLAG ,LEAD_CAPTURED_DATA_ID)VALUES(? ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?, ?, ?, ?, ?, ?,?,?, ?)";
	protected static final String GET_MAX_ID_OTHER_LEAD_DETAILS_HISTORY = "SELECT MAX(LEAD_DETAILS_HISTORY_ID) FROM LEAD_DETAILS_HISTORY WITH UR ";
	protected static final String SQL_INSERT_DUPLICATE_LEAD = "INSERT INTO DUPLICATE_LEAD(PROSPECT_NAME, PROSPECT_MOBILE_NUMBER, ALTERNATE_CONTACT_NUMBER, "
		+" ADDRESS1, CITY, PINCODE, CIRCLE, " 
		+" PRODUCT, EMAIL, CREATE_TIME , FID , CID ,SUB_SOURCE, SOURCE, CAMPAIGN, REFERER_URL, REFERER_PAGE ,LEAD_DETAILS_HISTORY_ID ) " 
		+" VALUES( ? ,? ,? ,? ,? ,? ,? ,? ,?,current_timestamp , ? , ? , ?, ?, ?, ?,?,? )";
	protected static final String GET_CIRCLE_ID_FROM_ID = "SELECT CIRCLE_ID from CIRCLE_MSTR  where CIRCLE_ID = ? AND LOB_ID=?  AND STATUS ='A'  with ur";
	protected static final String GET_CITY_ID = "SELECT cm.CITY_CODE ,cm.ZONE_CODE from CITY_MSTR cm , ZONE_MSTR zm, CIRCLE_MSTR cim where ucase(cm.CITY_NAME)= ? and cm.zone_code = zm.zone_code and zm.circle_mstr_id = cim.circle_mstr_id and cim.lob_id = ? AND CIM.CIRCLE_ID = ? with ur ";
	protected static final String GET_CITY_ID_FROMWS_ID = "SELECT cm.CITY_CODE ,cm.ZONE_CODE from CITY_MSTR cm , ZONE_MSTR zm, CIRCLE_MSTR cim where cm.CITY_CODE = ? and cm.zone_code = zm.zone_code and zm.circle_mstr_id = cim.circle_mstr_id and cim.lob_id = ? AND CIM.CIRCLE_ID = ? with ur ";
	protected static final String GET_CIRCLE_ID = "SELECT CIRCLE_ID from CIRCLE_MSTR  where ucase(CIRCLE_NAME)=? AND LOB_ID=?  AND STATUS ='A' with ur"; 
	protected static final String GET_CIRCLE_ID_FROM_STATE = " SELECT c.CIRCLE_ID  FROM STATE_MSTR s,CIRCLE_MSTR c WHERE c.CIRCLE_MSTR_ID = s.CIRCLE_MSTR_ID AND UCASE(s.STATE_NAME) = ?  AND c.LOB_ID =?  AND s.STATUS ='A' with ur"; 
	protected static final String GET_ALL_FROM_RSUCODE= "SELECT rsu.RSU_CODE, rsu.CITY_ZONE_CODE,  czm.CITY_CODE, cim.ZONE_CODE, zm.CIRCLE_MSTR_ID  FROM RSU_MSTR rsu, CITY_ZONE_MSTR czm, CITY_MSTR cim, ZONE_MSTR zm, CIRCLE_MSTR cm WHERE rsu.CITY_ZONE_CODE = czm.CITY_ZONE_CODE AND czm.CITY_CODE = cim.CITY_CODE AND cim.ZONE_CODE = zm.ZONE_CODE AND zm.CIRCLE_MSTR_ID = cm.CIRCLE_MSTR_ID  AND rsu.RSU_CODE = ? AND cm.CIRCLE_ID = ? AND cm.LOB_ID = ? WITH UR";
	protected static final String GET_ALL_FROM_PINCODE= "SELECT pm.PINCODE, pm.CITY_ZONE_CODE,  czm.CITY_CODE, cim.ZONE_CODE, zm.CIRCLE_MSTR_ID FROM PINCODE_MSTR pm, CITY_ZONE_MSTR czm, CITY_MSTR cim, ZONE_MSTR zm, CIRCLE_MSTR cm WHERE pm.CITY_ZONE_CODE = czm.CITY_ZONE_CODE AND czm.CITY_CODE = cim.CITY_CODE AND cim.ZONE_CODE = zm.ZONE_CODE AND zm.CIRCLE_MSTR_ID = cm.CIRCLE_MSTR_ID AND pm.PINCODE = ? AND cm.CIRCLE_ID = ? AND cm.LOB_ID = ? WITH UR"; 
	protected static final String GET_CITY_ZONE_CODE_FROM_CITY="select CZM.CITY_ZONE_CODE  from CITY_ZONE_MSTR CZM , CITY_MSTR CM where ucase(CZM.CITY_ZONE_NAME)= ? and CZM.CITY_CODE = CM.CITY_CODE and czm.CITY_CODE = ? with ur"; 
	protected static final String GET_CITY_ZONE_CODE_FROM_CIRCLE="SELECT CZM.CITY_ZONE_CODE, CZM.CITY_CODE, CM.ZONE_CODE FROM CITY_ZONE_MSTR CZM, CITY_MSTR CM, ZONE_MSTR ZM , CIRCLE_MSTR CIM WHERE ucase(CZM.CITY_ZONE_NAME)= ? AND CZM.CITY_CODE = CM.CITY_CODE AND CM.ZONE_CODE = ZM.ZONE_CODE AND ZM.CIRCLE_MSTR_ID = CIM.CIRCLE_MSTR_ID AND CIM.LOB_ID = ? AND CIM.CIRCLE_ID = ? WITH UR"; 
	protected static final String GET_ZONE_CODE_FROM_CIRCLE="SELECT ZM.ZONE_CODE FROM  ZONE_MSTR ZM ,CIRCLE_MSTR CM WHERE ucase(ZM.ZONE_NAME)= ? AND  ZM.CIRCLE_MSTR_ID = CM.CIRCLE_MSTR_ID AND CM.LOB_ID = ? AND CM.CIRCLE_ID = ? WITH UR"; 
	protected static final String SELECT_AUTO_QUALIFIED_LEAD = "SELECT MATRIX_ID ,ZONE_CODE,CITY_CODE,CITY_ZONE_CODE,PINCODE,REQUEST_CATEGORY_ID FROM AUTO_ASSIGNMENT_MATRIX WHERE AUTO_ASSIGNMENT_TYPE = '"+Constants.AUTO_QUALIFIED_ASSIGNMENT+"' AND LOB_ID = ? AND PRODUCT_ID = ? AND CIRCLE_ID = ? AND STATUS = 'A' ";
	protected static final String GET_SOURCE_ID = "SELECT SOURCE_ID from SOURCE_MSTR  where ucase(SOURCE_NAME)=?  AND STATUS = 'A' WITH UR";
	protected static final String GET_SUB_SOURCE_ID = "SELECT SUBSOURCE_ID FROM SUB_SOURCE_MSTR  WHERE ucase(SUBSOURCE_NAME)=?  AND STATUS = 'A' WITH UR";
	protected  static final String INSERT_SUB_SOURCE ="INSERT INTO SUB_SOURCE_MSTR (SUBSOURCE_ID, SUBSOURCE_NAME,STATUS) VALUES(? ,?,'A') WITH UR";
	protected  static final String INSERT_SOURCE ="INSERT INTO SOURCE_MSTR (SOURCE_ID ,SOURCE_NAME, STATUS) VALUES(?,?,'A') WITH UR";
	protected static final String CHECK_LEAD_CAPTURED_DATA_ID="SELECT LEAD_CAPTURED_DATA_ID FROM LEAD_DETAILS WHERE LEAD_ID=? WITH UR";
	protected static final String CHECK_LEAD_ID="SELECT LEAD_ID FROM LEAD_DETAILS WHERE LEAD_CAPTURED_DATA_ID=? WITH UR";
	protected static final String GET_DATA="SELECT D.PLAN,D.LEAD_STATUS_ID,D.REMARKS,D.LEAD_SUB_STATUS_ID,D.CAF,S.PRODUCT_BOUGHT,S.PAYMENT_COLLECTED,S.PAYMENT_TYPE,S,COMPETITOR_CHOSEN,S.SENTBY FROM LEAD_DATA D,LEAD_DETAILS S WHERE D.LEAD_ID=S.LEAD_ID AND D.LEAD_ID=? WITH UR";
	protected static final String CHECK_LC_ISDONE="SELECT LEAD_CAPTURED_DATA_ID FROM LEAD_CAPTURE WHERE LEAD_CAPTURED_DATA_ID=?  AND  LC_ISDONE = 0 WITH UR";
	protected static final String GET_MAP = "SELECT LEAD_STATUS_ID,LEAD_STATUS from LEAD_STATUS WITH UR";
	protected static final String CHECK_TRANSACTION_ID="SELECT LEAD_CAPTURED_DATA_ID FROM LEAD_CAPTURE WHERE LEAD_CAPTURED_DATA_ID=? WITH UR";
	protected final static String IS_VALID_LEADID = "SELECT LEAD_ID FROM LEAD_DATA where LEAD_ID=? WITH UR";
	protected final static String CHECK_TRANS_ID = "SELECT LD.LEAD_ID FROM LEAD_DATA LD ,LEAD_DETAILS LDT WHERE LD.LEAD_ID=LDT.LEAD_ID AND LDT.LEAD_CAPTURED_DATA_ID=? with ur";
	

	public static List<CaptureLeadDetailsDTO> captureLeadData(int captureId) throws DAOException {
		
		List<CaptureLeadDetailsDTO> list  = new LinkedList<CaptureLeadDetailsDTO>();
		Connection con  = null;
		PreparedStatement ps = null;
		PreparedStatement psleadDumpCapture = null;
		ResultSet rsleadDumpDataCapture  = null;
		
		ResultSet rsleadDumpData  = null;
		try {
			
				con = DBConnection.getDBConnection();
				ps = con.prepareStatement(SQL_GET_LEAD_DATA_DUMP);
				ps.setInt(1,captureId);
				rsleadDumpData = ps.executeQuery();
			
			if(rsleadDumpData.next()) {
				CaptureLeadDetailsDTO captureLeadDO  = new CaptureLeadDetailsDTO();
				captureLeadDO.setAddress(rsleadDumpData.getString("ADDRESS1"));
				captureLeadDO.setAddress2(rsleadDumpData.getString("ADDRESS2"));
				captureLeadDO.setAdParameter(rsleadDumpData.getString("AD_PARAMETER"));
				captureLeadDO.setAgencyId(rsleadDumpData.getString("AGENCY_ID"));
				captureLeadDO.setAllocatedNo(rsleadDumpData.getString("ALLOCATED_NO"));
				captureLeadDO.setAlternateContactNumber(rsleadDumpData.getString("ALTERNATE_CONTACT_NUMBER"));
				captureLeadDO.setBenefit(rsleadDumpData.getString("BENEFIT"));
				captureLeadDO.setBoostercount(rsleadDumpData.getString("BOOSTER_COUNT"));
				captureLeadDO.setBoostertaken(rsleadDumpData.getString("BOOSTER_TAKEN"));
				captureLeadDO.setCampaign(rsleadDumpData.getString("CAMPAIGN"));
				captureLeadDO.setCircle(rsleadDumpData.getString("CIRCLE"));
				captureLeadDO.setCircleId(rsleadDumpData.getString("CIRCLE_ID"));
				captureLeadDO.setCity(rsleadDumpData.getString("CITY"));
				captureLeadDO.setCityId(rsleadDumpData.getString("CITY_ID"));
				captureLeadDO.setCityZoneCode(rsleadDumpData.getString("CITY_ZONE_CODE"));
				captureLeadDO.setCompany(rsleadDumpData.getString("COMPANY"));
				captureLeadDO.setCustomerInfoId(rsleadDumpData.getString("CUSTOMER_INFO_ID"));
				captureLeadDO.setDataquota(rsleadDumpData.getString("DATA_QUOTA"));
				captureLeadDO.setDevicemrp(rsleadDumpData.getString("DEVICE_MRP"));
				captureLeadDO.setDevicetaken(rsleadDumpData.getString("DEVICE_TAKEN"));
				captureLeadDO.setDob(rsleadDumpData.getString("DOB"));
				captureLeadDO.setDownloadlimit(rsleadDumpData.getString("DOWNLOAD_LIMIT"));
				captureLeadDO.setEmail(rsleadDumpData.getString("EMAIL"));
				captureLeadDO.setFeasibilityparam(rsleadDumpData.getString("FEASIBILITY_PARAM"));
				captureLeadDO.setFid((rsleadDumpData.getString("F_ID")==null) ? "0":rsleadDumpData.getString("F_ID"));
				captureLeadDO.setFlag(rsleadDumpData.getString("FLAG"));
				captureLeadDO.setFreebiecount(rsleadDumpData.getString("FREEBIE_COUNT"));
				captureLeadDO.setFreebietaken(rsleadDumpData.getString("FREEBIE_TAKEN"));
				captureLeadDO.setFromPage(rsleadDumpData.getString("FROM_PAGE"));
				captureLeadDO.setGeoIpCity(rsleadDumpData.getString("GEO_IP_CITY"));
				captureLeadDO.setHlrno(rsleadDumpData.getString("HLR_NO"));
				captureLeadDO.setIpAddress(rsleadDumpData.getString("IP_ADDRESS"));
				captureLeadDO.setIsCustomer(rsleadDumpData.getString("IS_CUSTOMER"));
				captureLeadDO.setKeyWord(rsleadDumpData.getString("KEYWORD"));
				captureLeadDO.setMaritalStatus(rsleadDumpData.getString("MARITAL_STATUS"));
				captureLeadDO.setLandlineNumber(rsleadDumpData.getString("LANDLINE_NUMBER"));
				captureLeadDO.setLeadSubmitTime(rsleadDumpData.getString("LEAD_SUBMIT_TIME"));
				captureLeadDO.setNdncDisclaimer(rsleadDumpData.getString("NDNC_DISCLAIMER"));
				captureLeadDO.setOffer(rsleadDumpData.getString("OFFER"));
				captureLeadDO.setOnlineCafNo(rsleadDumpData.getString("ONLINE_CAF_NO"));
				captureLeadDO.setAppointmentTime(rsleadDumpData.getString("APPOINTMENT_TIME"));
				captureLeadDO.setOpportunityTime(rsleadDumpData.getString("OPPORTUNITY_TIME"));
				captureLeadDO.setPincode(rsleadDumpData.getString("PINCODE"));
				captureLeadDO.setPkgduration(rsleadDumpData.getString("PKG_DURATION"));
				captureLeadDO.setPlan(rsleadDumpData.getString("PLAN"));
				captureLeadDO.setPlanId(rsleadDumpData.getString("PLAN_ID"));
				captureLeadDO.setPrepaidnumber(rsleadDumpData.getString("PREPAID_NUMBER"));
				captureLeadDO.setPrimaryLanguage(rsleadDumpData.getString("PRIMARY_LANGUAGE"));
				captureLeadDO.setProduct(rsleadDumpData.getString("PRODUCT"));
				captureLeadDO.setProspectsMobileNumber(rsleadDumpData.getString("PROSPECT_MOBILE_NUMBER"));
				captureLeadDO.setProspectsName(rsleadDumpData.getString("PROSPECT_NAME"));
				captureLeadDO.setPytAmt(rsleadDumpData.getString("PYT_AMT"));
				captureLeadDO.setQualLeadParam(rsleadDumpData.getString("QUAL_LEAD_PARAM"));
				captureLeadDO.setReferPage(rsleadDumpData.getString("REFER_PAGE"));
				captureLeadDO.setReferUrl(rsleadDumpData.getString("REFER_URL"));
				captureLeadDO.setRemarks(rsleadDumpData.getString("REMARKS"));
				captureLeadDO.setRental(rsleadDumpData.getString("RENTAL"));
				captureLeadDO.setRentaltype(rsleadDumpData.getString("RENTAL_TYPE"));
				captureLeadDO.setRequestCategory(rsleadDumpData.getString("REQUEST_CATEGORY"));
				captureLeadDO.setRsuCode(rsleadDumpData.getString("RSU_CODE"));
				captureLeadDO.setService(rsleadDumpData.getString("SERVICE"));
				captureLeadDO.setSource(rsleadDumpData.getString("SOURCE"));
				captureLeadDO.setState(rsleadDumpData.getString("STATE"));
				captureLeadDO.setSubSource(rsleadDumpData.getString("SUB_SOURCE"));
				captureLeadDO.setSubZone(rsleadDumpData.getString("SUB_ZONE"));
				captureLeadDO.setTag(rsleadDumpData.getString("TAG"));
				captureLeadDO.setTid(rsleadDumpData.getString("T_ID"));
				captureLeadDO.setTranRefno(rsleadDumpData.getString("TRAN_REF_NO"));
				captureLeadDO.setUdId(rsleadDumpData.getString("UD_ID"));
				captureLeadDO.setUsertype(rsleadDumpData.getString("USER_TYPE"));
				captureLeadDO.setUtmLAbels(rsleadDumpData.getString("UTM_LABELS"));
				captureLeadDO.setVoicebenefit(rsleadDumpData.getString("VOICE_BENEFIT"));
				captureLeadDO.setZone(rsleadDumpData.getString("ZONE"));
				captureLeadDO.setLeadCaptureId(rsleadDumpData.getInt("LEAD_CAPTURED_DATA_ID"));
				captureLeadDO.setPaymentAmountDue(rsleadDumpData.getString("EXTRA_PARAM1"));  			//paymentAmountDue
				captureLeadDO.setPaymentCollected(rsleadDumpData.getString("EXTRA_PARAM2"));   			//paymentCollected
				captureLeadDO.setExtraParam3(rsleadDumpData.getString("EXTRA_PARAM3")); 				//latitude
				captureLeadDO.setExtraParam4(rsleadDumpData.getString("EXTRA_PARAM4")); 				//longitude
				captureLeadDO.setPaymentType(rsleadDumpData.getString("EXTRA_PARAM5")); 				//payment type
				captureLeadDO.setPaymentStatus(rsleadDumpData.getString("EXTRA_PARAM6")); 				//payment status
				captureLeadDO.setExtraParam7(rsleadDumpData.getString("EXTRA_PARAM7")); 				//source
				
				
				
				psleadDumpCapture = con.prepareStatement(SQL_GET_LEAD_DATA_CAPTURE);
				psleadDumpCapture.setInt(1, rsleadDumpData.getInt("LEAD_CAPTURED_DATA_ID"));
				rsleadDumpDataCapture  = psleadDumpCapture.executeQuery();
				while(rsleadDumpDataCapture.next())
				 {
					      captureLeadDO.setSimNo(rsleadDumpDataCapture.getString(2));
					        captureLeadDO.setCustomerSegment(rsleadDumpDataCapture.getString(3));
					        captureLeadDO.setRelationName(rsleadDumpDataCapture.getString(4));
							captureLeadDO.setNationality(rsleadDumpDataCapture.getString(5));
							captureLeadDO.setIdentityProofType(rsleadDumpDataCapture.getString(6));
							captureLeadDO.setIdentityProofID(rsleadDumpDataCapture.getString(7));
							captureLeadDO.setGender(rsleadDumpDataCapture.getString(8));
							captureLeadDO.setPayment_date(rsleadDumpDataCapture.getString(9));
							captureLeadDO.setUpc(rsleadDumpDataCapture.getString(10));
						    captureLeadDO.setUpc_gen_date(rsleadDumpDataCapture.getString(11));
							captureLeadDO.setPreviousOerator(rsleadDumpDataCapture.getString(12));
							captureLeadDO.setPreviousCircle(rsleadDumpDataCapture.getString(13));
							captureLeadDO.setExistingPart(rsleadDumpDataCapture.getString(14));
							captureLeadDO.setMnpStatus(rsleadDumpDataCapture.getString(15));
							captureLeadDO.setDocumentCollectedFlag(rsleadDumpDataCapture.getString(16));
							captureLeadDO.setPlanType(rsleadDumpDataCapture.getString(17));
							captureLeadDO.setRentalPlan(rsleadDumpDataCapture.getString(19));
							captureLeadDO.setAppointmentEndTime(rsleadDumpDataCapture.getString(20));
							
							 /*captureLeadDO.setSimNo(rsleadDumpDataCapture.getString("SIM_NUM"));
							captureLeadDO.setCustomerSegment(rsleadDumpDataCapture.getString("CUSTOMER_SEGMENT"));
							captureLeadDO.setGender(rsleadDumpDataCapture.getString("GENDER"));
							captureLeadDO.setNationality(rsleadDumpDataCapture.getString("NATIONALITY"));
							captureLeadDO.setIdentityProofType(rsleadDumpDataCapture.getString("IDENTITYPROOFTYPE"));
							captureLeadDO.setIdentityProofID(rsleadDumpDataCapture.getString("IDENTITYPROOFID"));
							captureLeadDO.setRelationName(rsleadDumpDataCapture.getString("RELATION_NAME"));
							captureLeadDO.setPayment_date(rsleadDumpDataCapture.getString("PAYMENT_DATE"));
							captureLeadDO.setUpc(rsleadDumpDataCapture.getString("UPC"));
						    captureLeadDO.setUpc_gen_date(rsleadDumpDataCapture.getString("UPC_GEN_DATE"));
							captureLeadDO.setPreviousOerator(rsleadDumpDataCapture.getString("PREVIOUS_OPERATOR"));
							captureLeadDO.setPreviousCircle(rsleadDumpDataCapture.getString("PREVIOUS_CIRCLE"));
							captureLeadDO.setExistingPart(rsleadDumpDataCapture.getString("EXISTING_PART"));
							captureLeadDO.setMnpStatus(rsleadDumpDataCapture.getString("MNP_STATUS"));
							captureLeadDO.setDocumentCollectedFlag(rsleadDumpDataCapture.getString("DOC_COLLECTED_FLAG"));
							captureLeadDO.setPlanType(rsleadDumpDataCapture.getString("PLAN_TYPE"));
							captureLeadDO.setRentalPlan(rsleadDumpDataCapture.getString("RENTAL_PLAN"));
							captureLeadDO.setAppointmentEndTime(rsleadDumpDataCapture.getString("APPOINMENT_ENDTIME"));
					*/
				 }
					list.add(captureLeadDO); 
			
			}
					
				
		} catch (Exception e) {
			throw new DAOException("Exception occured while getting lob list :  "+ e.getMessage(),e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rsleadDumpData);
			} catch (Exception e) {				
				throw new DAOException(e.getMessage(), e);
			}
		}
		return list;
}
	 
		public  String insertLeadData(List<CaptureLeadDetailsDTO>  captureLeadDOs) throws DAOException 
		{
			Connection con  = null;
			String message="";
			PreparedStatement ps = null;
			PreparedStatement psFid= null;
			PreparedStatement psLeadDetails= null;
			PreparedStatement psMultiple= null;
			PreparedStatement psLeadProspectdt= null;
			PreparedStatement psProduct= null;
			PreparedStatement pstmtdetails= null;
			PreparedStatement pstmtselect= null;
			PreparedStatement psLeadData= null;
			PreparedStatement psTrans= null;
			PreparedStatement psProspectsData= null;
			PreparedStatement psLeadCaputur= null;
			PreparedStatement psCicle= null;
			MasterDao mastrDao=MasterDaoImpl.masterDaoInstance();
			
			try {
				
			String sourceName=mastrDao.getParameterName("Source");
			logger.info("SOURCE_NAME="+sourceName);
			 con = DBConnection.getDBConnection();
			 if(statusMap==null || statusMap.size()==0)
					statusMap = getStatusMap(con);
			 ps = con.prepareStatement(SQL_CHECK_PRODUCT);
			 psFid = con.prepareStatement(SQL_CHECK_FID);
			 psMultiple = con.prepareStatement(SQL_GET_MULTIPLE_LEAD);
			 psLeadProspectdt = con.prepareStatement(UPDATE_LEAD_PROSPECT_CUSTOMER_EMAIL);
			 psProduct = con.prepareStatement(GET_PRODUCT_ID);
			 pstmtdetails = con.prepareStatement(INSERT_LEAD_PROSPECT_DETAIL);
			 pstmtselect = con.prepareStatement(SELECT_LEAD_PROSPECT_ID);
			 psLeadData= con.prepareStatement(SQL_INSERT_LEAD_DATA);
		     psTrans = con.prepareStatement(SQL_INSERT_LEAD_TRANSACTION);
			 psProspectsData = con.prepareStatement(SQL_INSERT_PROSPECTS_CUST);
			 psLeadDetails = con.prepareStatement(INSERT_OTHER_LEAD_DETAILS);
			 psLeadCaputur = con.prepareStatement(UPDATE_LEAD_CAPTUER_DATA);
			 psCicle = con.prepareStatement(GET_CIRCLE_FROM_CITY);
			 CallableStatement cs = null;
			 PreparedStatement leadUpdateData = con.prepareStatement(INSERT_LEAD_UPDATE_DATA);
				
			ResultSet rs = null;
			ResultSet rsFid = null;
			ResultSet rsMultiple= null;
			ResultSet rsProduct = null;

			if(captureLeadDOs !=null && captureLeadDOs.size() >0) {
			for(int i = 0; i < captureLeadDOs.size(); i++) {
				CaptureLeadDetailsDTO leadDtoObj  = captureLeadDOs.get(i);
			
				
				boolean dirtyleadFlag = false;
				boolean multipleLeadFlag = false;
				boolean autoAssign = false;
				boolean autoQualified  = false;
				String mobileNumber="";
				long imobileNumber =0;
				String productId="";
				String prospectsId="";
				String leadId = "";
				String PRODUCT_LOB_ID="";
				int circleId = -1;
				String leadStatus="";
				String cityId ="" ;
				String zoneCode="";
				String pinCode ="" ;
				String cityZoneCode="";
				StringBuilder sb = new StringBuilder();
				con.setAutoCommit(false);
				logger.info("lead creation start");
				try{
				mobileNumber   =  leadDtoObj.getProspectsMobileNumber();
				if(mobileNumber != null && mobileNumber.startsWith("0") ) {
					mobileNumber = mobileNumber.substring(1, mobileNumber.length());
				}
				if(mobileNumber == null || mobileNumber.length() != 10)	{
					dirtyleadFlag = true;
					message = Constants.MSG_INVALID_MOBILE;
				}else if(mobileNumber != null && mobileNumber.length() == 10) {
					try {
						imobileNumber =  Long.parseLong(mobileNumber+"");
					}
					catch(Exception ex)
					{
						logger.error("mobileNumber is not valid::" + ex);
						dirtyleadFlag = true;
						message = Constants.MSG_INVALID_MOBILE;
					}
				}
				
				if(!dirtyleadFlag)
				{	//check for product
					try {
						ps.setString(1, leadDtoObj.getProduct().toUpperCase());
						rs = ps.executeQuery();
						if(rs.next())
						{
							productId = rs.getString("PRODUCT_ID");
							leadDtoObj.setRequestCategory(getRequestCategory(con, leadDtoObj,productId));
						}
						else
						{
							/*** validation failed for Product. so this is dirty lead*****/
							dirtyleadFlag = true;
							message = Constants.MSG_INVALID_PODUCT;
						}
					} catch (Exception e) {
						dirtyleadFlag = true;
						message = Constants.MSG_INVALID_PODUCT;
						logger.error("Product is not valid::");
						//e.printStackTrace();
					}
					DBConnection.releaseResources(null, null, rs);
					
				}
				
				String FIdFlag =Constants.FID_FLAG;
				if(!dirtyleadFlag && FIdFlag.trim().equalsIgnoreCase("Y")) { // for  FID validate check should be check or not
					try {
						psFid.setString(1, leadDtoObj.getFid());
						rsFid = psFid.executeQuery();
						if(rsFid.next())
						{
							 rsFid.getString("FORM_ID");
						}
						else
						{
							/*** validation failed for fId. so this is dirty lead*****/
							dirtyleadFlag = true;
							message = Constants.MSG_INVALID_FID;
						}
					} catch (Exception e) {
						dirtyleadFlag = true;
						message = Constants.MSG_INVALID_FID;
						
					}
					
					DBConnection.releaseResources(null, null, rsFid);
				}
				
				UTMDataSource(leadDtoObj);
				
				if(dirtyleadFlag) {
					//check again if the lead has been created:
					if(isValidTxnsId(String.valueOf(leadDtoObj.getLeadCaptureId())) != null || !("").equalsIgnoreCase(isValidTxnsId(String.valueOf(leadDtoObj.getLeadCaptureId()))))
						logger.info("dirty lead*************"+isValidTxnsId(String.valueOf(leadDtoObj.getLeadCaptureId())));	
						logger.info("dirty lead*************"+isValidTxnsId(String.valueOf(leadDtoObj.getLeadCaptureId())));
					insertDirtyLead(leadDtoObj, con,message ,mobileNumber);
				}else {
					/*
					 * Check for multiple lead
					 */
					logger.error("Check for multiple lead");
					psMultiple.setString(1, mobileNumber);
					rsMultiple=psMultiple.executeQuery();
					if(rsMultiple.next())	{
						prospectsId = rsMultiple.getString("PROSPECT_ID");
						multipleLeadFlag = true;
					}
				

				DBConnection.releaseResources(null, null, rsMultiple);
				boolean prodCheck=true;
				List<String> prodListExc=Arrays.asList(ServerPropertyReader.getString("DUPLICATE_PRODUCT_ID_LOGIC").trim().split(","));
				prodCheck=prodListExc.contains(productId);
				
				if(ServerPropertyReader.getString("DUPLICATE_ONZEE_LOGIC").trim().equalsIgnoreCase("Y") &&  !prodCheck)
				{
				if(isValidTxnsId(String.valueOf(leadDtoObj.getLeadCaptureId())) != null || !("").equalsIgnoreCase(isValidTxnsId(String.valueOf(leadDtoObj.getLeadCaptureId()))))
				logger.info("duplicate lead*************"+isValidTxnsId(String.valueOf(leadDtoObj.getLeadCaptureId())));
				logger.info("duplicate lead*************"+isValidTxnsId(String.valueOf(leadDtoObj.getLeadCaptureId())));
				insertDuplicate(con,leadDtoObj, message, mobileNumber);
				if(duplicateleadFlag)
					message = "Duplicate Lead";
				}
				
				if(!duplicateleadFlag) {
					logger.error("This is valid lead so inserting into lead data tables");
				leadId = getLeadId(imobileNumber,con)+"";
				psProduct.setString(1, leadDtoObj.getProduct().toUpperCase());
				rsProduct = psProduct.executeQuery();
				if(rsProduct.next())
				{
					productId = rsProduct.getString("PRODUCT_ID");
					PRODUCT_LOB_ID = rsProduct.getString("PRODUCT_LOB_ID");
				}
				
				DBConnection.releaseResources(null, null, rsProduct);
				//*******************************************Lead Prospect Customer**************
				if(!multipleLeadFlag)  {
					/* New Prospect Customer Entry */
					prospectsId = getNextVal(SEQ_NAME_PROSPECTS_ID,con);
				} else {
					try { //for updation latest email ID of Lead Prospect Customer
						if(multipleLeadFlag && leadDtoObj.getEmail() !=null && leadDtoObj.getEmail().length() >0) {
							psLeadProspectdt.setString(1, leadDtoObj.getEmail());
							psLeadProspectdt.setInt(2, Integer.parseInt(prospectsId));
							psLeadProspectdt.executeUpdate();
						}
					} catch (Exception e) {
						logger.error(prospectsId+"*******Prospect email Id  Updatetion fail due to :"+e.getMessage());
					}
				
				
				}
				
				circleId  = getCircleID(con ,leadDtoObj ,PRODUCT_LOB_ID);
				if(circleId == -1 && leadDtoObj.getCity() != null && leadDtoObj.getCity()!="" ) {
					psCicle.setString(1, PRODUCT_LOB_ID);
					psCicle.setString(2, leadDtoObj.getCity().toUpperCase());
					ResultSet rsCircle = psCicle.executeQuery();
					if(rsCircle.next()){
						circleId = rsCircle.getInt("CIRCLE_ID");
					}
					
					DBConnection.releaseResources(null, null, rsCircle);
				}
				
				String otherdata = validateLeadOtherData(con, leadDtoObj, PRODUCT_LOB_ID, circleId); // for getting cityId ,cityZoneCode and zoneCode
				try {
					
					String [] cIdCzCodeDataz  = otherdata.split("#");
					if(cIdCzCodeDataz.length >0) {
					cityId  = cIdCzCodeDataz[0];
					cityZoneCode  = cIdCzCodeDataz[1];
					zoneCode  = cIdCzCodeDataz[2];
					pinCode = cIdCzCodeDataz[3];
					}
				} catch (Exception e) {
					//e.printStackTrace();
				}
				
				if(ServerPropertyReader.getString("LMSONLINE_LEADQUALIFIED").trim().equalsIgnoreCase("Y"))
					{
					otherdata = otherdata+"#"+leadDtoObj.getRequestCategory();
					autoAssign = isValidAutoQualified(PRODUCT_LOB_ID, productId, circleId, leadDtoObj, con ,otherdata);						
					}
				if(ServerPropertyReader.getString("LEADQUALIFIED_LOGIC_FLAG").trim().equalsIgnoreCase("Y") && leadDtoObj.getQualLeadParam() !=null && "1".equalsIgnoreCase(leadDtoObj.getQualLeadParam().trim())){
					autoQualified = true;
				}
				
				int parameterIndex = 1;
				if(rsuCodeFlag && leadDtoObj.getRsuCode() ==null && !leadDtoObj.getRsuCode().equals("")) {
					pstmtdetails.setString(parameterIndex++, leadDtoObj.getRsuCode());
				}else {
					pstmtdetails.setString(parameterIndex++, null);
				}
				pstmtdetails.setString(parameterIndex++,(cityId.length() >0)?cityId:null);						
				pstmtdetails.setInt(parameterIndex++,circleId);
				pstmtdetails.setString(parameterIndex++, leadDtoObj.getAddress2());
				if(leadDtoObj.getPincode() ==null || leadDtoObj.getPincode().equals("") || leadDtoObj.getPincode().equalsIgnoreCase("nil"))
					pstmtdetails.setString(parameterIndex++, null);
				else
					pstmtdetails.setString(parameterIndex++, (pinCode.length()>0)?pinCode:null);
				
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getAlternateContactNumber());	
	          
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getAddress());
	            pstmtdetails.setString(parameterIndex++, prospectsId);
	            if(leadDtoObj.getIsCustomer() != null && leadDtoObj.getIsCustomer().equalsIgnoreCase("Y"))	{
	            	pstmtdetails.setString(parameterIndex++, leadDtoObj.getIsCustomer());
	            	}
				else {
					pstmtdetails.setString(parameterIndex++, "N");
				}
	            pstmtdetails.setString(parameterIndex++,Constants.LEAD_UPDATED_BY);
	            pstmtdetails.setString(parameterIndex++,PRODUCT_LOB_ID);
	            pstmtdetails.setString(parameterIndex++, (zoneCode.length()>0)?zoneCode:null);
	            pstmtdetails.setString(parameterIndex++, (cityZoneCode.length()>0)?cityZoneCode:null);
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getRental());
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getKeyWord());
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getPytAmt());
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getTranRefno());
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getQualLeadParam());
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getAdParameter());
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getExtraParam2());
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getExtraParam3());
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getGeoIpCity());
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getTag());
	            pstmtdetails.setString(parameterIndex++, leadId);
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getPaymentAmountDue());
	            logger.info("lead_prospect_detils*********"+leadDtoObj.getAppointmentEndTime());
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getAppointmentTime());        //Add Appointment time
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getPrimaryLanguage());       //Add primary language
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getLandlineNumber());        //landline
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getMaritalStatus());         //marital
	            pstmtdetails.setString(parameterIndex++, leadDtoObj.getAppointmentEndTime());    //appointment end time
	         
	            pstmtdetails.executeUpdate();
	            pstmtdetails.clearParameters();
				
				//leadProspectId form lead_Prospect_details
	            int leadprospectId=0;
	            pstmtselect.setString(1,leadId);
	            rs = pstmtselect.executeQuery();
				if(rs.next())
				leadprospectId = rs.getInt("LEAD_PROSPECT_ID");
				pstmtselect.clearParameters();
				
				DBConnection.releaseResources(null, null, rs);
				logger.error("leadprospectId"+leadprospectId+"prospectId"+prospectsId+" lob: "+PRODUCT_LOB_ID);
				
				int leadStatusId = 0;
				psLeadData.setString(1, productId);
				if(autoAssign || autoQualified){
					psLeadData.setString(2, statusMap.get(Constants.LEAD_STATUS_NAME_QUALIFIED));
					leadStatusId = Integer.parseInt(statusMap.get(Constants.LEAD_STATUS_NAME_QUALIFIED));
				}
				else
				{
					psLeadData.setString(2, statusMap.get(Constants.LEAD_STATUS_CREATION_TIME_OPEN));
					logger.info("Constants.LEAD_STATUS_CREATION_TIME_OPEN"+statusMap.get(Constants.LEAD_STATUS_CREATION_TIME_OPEN));
					leadStatusId = Integer.parseInt(statusMap.get(Constants.LEAD_STATUS_CREATION_TIME_OPEN));
					//leadStatusId=200;
					
				}					
				psLeadData.setString(3, leadId);
				psLeadData.setString(4, prospectsId);
				psLeadData.setString(5, leadDtoObj.getFid());
				
				String CIDFlag = ServerPropertyReader.getString("LEAD_REGISTERATION_CIDFLAG").trim();//txnId will be used as CID
				if(CIDFlag.equalsIgnoreCase("Y")) {
				psLeadData.setString(6, String.valueOf(leadDtoObj.getLeadCaptureId())); 
				}else {
				psLeadData.setString(6, leadDtoObj.getTid()); 	//tid used as CID in LMS3
				}
				psLeadData.setString(7, leadDtoObj.getFromPage());
				//psLeadData.setString(11, leadDtoObj.getProductType());
				psLeadData.setString(8, leadDtoObj.getReferPage());
				psLeadData.setString(9, leadDtoObj.getReferUrl());
				psLeadData.setString(10, leadDtoObj.getService());
				psLeadData.setString(11, leadDtoObj.getPlan());
				psLeadData.setString(12, leadDtoObj.getKeyWord());
				psLeadData.setString(13, leadDtoObj.getCampaign());
				psLeadData.setString(14, String.valueOf(getValidateSubSource(con, leadDtoObj)));
				psLeadData.setInt(15, getValidateSource(con, leadDtoObj));
				psLeadData.setInt(16, leadprospectId);
				psLeadData.setString(17, Constants.LEAD_UPDATED_BY);
				psLeadData.setString(18, leadDtoObj.getOnlineCafNo());
				psLeadData.setString(19, leadDtoObj.getAllocatedNo());
				psLeadData.setString(20, leadDtoObj.getLeadSubmitTime());
				if(autoAssign){
					psLeadData.setString(21, Constants.LMSONLINE_LEADAUTO);
				}
				else{
					psLeadData.setString(21, Constants.LMSONLINE_LEADNORMAL);
				}	
				psLeadData.setString(22, leadDtoObj.getUtm_source()); 
				psLeadData.setString(23, null); //for utm_trem
				psLeadData.setString(24, leadDtoObj.getUtm_medium());
				psLeadData.setString(25, leadDtoObj.getUtm_campaign());
				psLeadData.setString(26, leadDtoObj.getRemarks());
				psLeadData.executeUpdate();
				
				logger.error("Inserted Successfully into lead_data table"+PRODUCT_LOB_ID+"  productId "+productId);
				
				/* Added By beeru for Qualified Status in transaction table in case of Auto Assign */
				psTrans.setString(1, PRODUCT_LOB_ID);
				if(autoAssign){
					if (PRODUCT_LOB_ID.equalsIgnoreCase(String.valueOf(Constants.PRODUCT_TELEMEDIA_LOB_ID)))
						psTrans.setString(2, statusMap.get(Constants.LEAD_STATUS_NAME_VERIFICATION));
					else
						psTrans.setString(2, statusMap.get(Constants.LEAD_STATUS_NAME_QUALIFIED));
				}
				else{
					psTrans.setString(2, statusMap.get(Constants.LEAD_STATUS_CREATION_TIME_OPEN));
				}					
				psTrans.setString(3, leadId);
				if(autoAssign){ //for substatus will same in case of auto assignment
					if (PRODUCT_LOB_ID.equalsIgnoreCase(String.valueOf(Constants.PRODUCT_TELEMEDIA_LOB_ID)))
						psTrans.setString(4, statusMap.get(Constants.LEAD_STATUS_NAME_VERIFICATION));
					else
						psTrans.setString(4, statusMap.get(Constants.LEAD_STATUS_NAME_QUALIFIED));
				}
				else{
					psTrans.setString(4, statusMap.get(Constants.LEAD_STATUS_CREATION_TIME_OPEN));
				}
				psTrans.setString(5, Constants.LEAD_UPDATED_BY);
				psTrans.setString(6, productId);
				psTrans.executeUpdate();
				
				/* Added by Beeru for inserting another row in Lead Transaction with 305 status in case of telemedia leads auto assignment */
				if (autoAssign && PRODUCT_LOB_ID.equalsIgnoreCase(String.valueOf(Constants.PRODUCT_TELEMEDIA_LOB_ID))){
					psTrans.clearParameters();
					psTrans.setString(1, PRODUCT_LOB_ID);
					psTrans.setString(2, statusMap.get(Constants.LEAD_STATUS_NAME_QUALIFIED));					
					psTrans.setString(3, leadId);
					psTrans.setString(4, statusMap.get(Constants.LEAD_STATUS_NAME_QUALIFIED));
					psTrans.setString(5, Constants.LEAD_UPDATED_BY);
					psTrans.setString(6, productId);  						
					psTrans.executeUpdate();
				}
				
				if(!multipleLeadFlag)  {
					psProspectsData.setString(1, (leadDtoObj.getProspectsName()==null || leadDtoObj.getProspectsName()=="")?"LMSOTHER":leadDtoObj.getProspectsName());
					psProspectsData.setString(2, leadDtoObj.getEmail());
					psProspectsData.setString(3, mobileNumber);
					if(leadDtoObj.getAlternateContactNumber() != null && leadDtoObj.getAlternateContactNumber().matches("\\d{10}") && leadDtoObj.getAlternateContactNumber().matches("^[1-9]*$"))	{
						psProspectsData.setString(4, leadDtoObj.getAlternateContactNumber());
					}else {
						psProspectsData.setString(4, null);
					}
					psProspectsData.setString(5, prospectsId);
					psProspectsData.setString(6, null);
					psProspectsData.setString(7, null);
					psProspectsData.setString(8, leadDtoObj.getCompany());
					psProspectsData.setString(9, Constants.LEAD_UPDATED_BY);
					psProspectsData.setString(10,leadDtoObj.getMaritalStatus());//add marital status
					psProspectsData.setString(11,leadDtoObj.getLandlineNumber());//add landline number
					psProspectsData.setString(12,leadDtoObj.getPrimaryLanguage());//add primary language
					
					
					psProspectsData.executeUpdate();
					logger.error("New Row Inserted Successfully into lead_prospect_master table");
				}
				
				psLeadDetails  = setLeadDetails(psLeadDetails, leadDtoObj, leadId);
				psLeadDetails.executeUpdate();
				
				

				//method for lead_update_data:
				leadUpdateData  = setUpdateLeadDetails(leadUpdateData, leadDtoObj, leadId);
				leadUpdateData.executeUpdate();
			
				try {
					/* Lead Priority  */
					if (leadId != null && !leadId.equalsIgnoreCase("0")){
						String schemaName = Constants.SCHEMA_NAME;
						cs = con.prepareCall("{call "+schemaName+".PRC_CALULATE_SCORE(?,?,?,?,?)}");
						cs.setLong(1, (Long.parseLong(leadId)));
						if(leadStatusId == Integer.parseInt(Constants.LEAD_STATUS_OPEN_ID))
						cs.setShort(2,Constants.SCORE_ONE);
						else if(leadStatusId == Constants.Qualified && !(PRODUCT_LOB_ID.equalsIgnoreCase(String.valueOf(Constants.PRODUCT_TELEMEDIA_LOB_ID))) )
							cs.setShort(2,Constants.SCORE_TWO);
						else if(leadStatusId == Integer.parseInt(Constants.LEAD_STATUS_QUALIFICATION) && PRODUCT_LOB_ID.equalsIgnoreCase(String.valueOf(Constants.PRODUCT_TELEMEDIA_LOB_ID)) )
							cs.setShort(2,Constants.SCORE_TWO);
						else
							cs.setShort(2,Constants.SCORE_ONE);
						cs.registerOutParameter(3, java.sql.Types.VARCHAR);
						cs.registerOutParameter(4, java.sql.Types.VARCHAR);
						cs.registerOutParameter(5, java.sql.Types.INTEGER);
						cs.execute();
					}
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				/* END OF Lead Priority  */
				
				//Added by BEERU for Sending SMS to Customer
				PreparedStatement psSelectSms=null;
		        PreparedStatement psInsertSms=null;
		        ResultSet rsSelectSms=null;
				try {
					String smsFlag = ServerPropertyReader.getString("LEAD_REGISTERATION_SMSFLAG").trim();
					//String duplicateFlag = InitializeLMSConfig.getInstance().getProperty("DUPLICATEONZEE_LOGIC").trim();
					logger.info("Lead source-"+leadDtoObj.getSource());
					if (leadId != null && !leadId.equalsIgnoreCase("0") && smsFlag.equalsIgnoreCase("Y") && !autoAssign && sourceName.equalsIgnoreCase(leadDtoObj.getSource())){
						
				       
						String SELECT_SMS_TEMPLATE = "SELECT MESSAGE_TEMPLATE FROM ALERT_MSTR  WHERE ALERT_ID =" +Constants.LEAD_REGISTERATION_TEMPLATE + "  AND ACTIVE = 'A' WITH UR";
			    		final String INSERT_SMS_TRANSACTIONS = " INSERT INTO CUSTOMER_SEND_SMS_DETAILS( MOBILE_NUMBER, MESSAGE, STATUS, SENT_ON, CREATED_ON, RESPONSE_MSG) VALUES( ?, ?, ?, CURRENT TIMESTAMP, CURRENT TIMESTAMP, ?)";
						String smsMessage = null;
						psSelectSms = con.prepareStatement(SELECT_SMS_TEMPLATE);
				        rsSelectSms = psSelectSms.executeQuery();
						if(rsSelectSms.next()){
							smsMessage = rsSelectSms.getString("MESSAGE_TEMPLATE")+" " + leadId;							 
						}
						//logger.info("SMS_TEMPLATE "+smsMessage);
						if(smsMessage != null && !smsMessage.trim().equals("")){
							 new SendSMSXML().sendSms(mobileNumber+"",smsMessage);
							// logger.info("Sending sms to "+mobileNumber);
							psInsertSms=con.prepareStatement(INSERT_SMS_TRANSACTIONS);
							psInsertSms.setString(1, mobileNumber+"");
							psInsertSms.setString(2, smsMessage);
							psInsertSms.setInt(3, 1);
							psInsertSms.setString(4, "");								
							psInsertSms.executeUpdate();
						}
					}
				} catch (Exception e) {
					logger.error("Error in Sending SMS to customer for LEAD ID: "+leadId+" with Mobile Number : "+mobileNumber);
					e.printStackTrace();
				}finally {
					DBConnection.releaseResources(null, psSelectSms, rsSelectSms);
					DBConnection.releaseResources(null, psInsertSms, null);
				}
				
				logger.error("lead creation END ");
				}
				}
				
				
				if(dirtyleadFlag || duplicateleadFlag) leadStatus ="Invalid";else leadStatus ="Valid";
				
				if(message != null && !message.equals(""))
					sb.append("\n"+leadDtoObj.toString() + "~"+ leadStatus +"~"+message);
				else
					sb.append("\n"+leadDtoObj.toString() + "~"+ leadStatus);
				
				try {
					psLeadCaputur.setInt(1, leadDtoObj.getLeadCaptureId());
					psLeadCaputur.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
					con.commit();
			} catch (Exception e) {
				e.printStackTrace();
				try
				{
					e.printStackTrace();
					con.rollback();
				}
				catch(Exception ex)
				{
					logger.error(ex);
				}
				logger.error("Exception while inserting lead capture details inner try block "+e);
				logger.error(leadDtoObj.toString());
				e.printStackTrace();
			} 
			 duplicateleadFlag = false;
			 rsuCodeFlag =  false;
			}
			try
			{
				DBConnection.releaseResources(null, ps, null);
				DBConnection.releaseResources(null, psLeadCaputur, null);
				DBConnection.releaseResources(null, psLeadProspectdt, null);
				DBConnection.releaseResources(null, psCicle, null);
				DBConnection.releaseResources(null, psProduct, null);
				DBConnection.releaseResources(null, psLeadData, null);
				DBConnection.releaseResources(null, psProspectsData, null);
				DBConnection.releaseResources(null, psMultiple, null);
				DBConnection.releaseResources(null, psTrans, null);
				DBConnection.releaseResources(null, ps, null);
				
				
			}
			catch(Exception ex)
			{
				ps=null;
				con=null;
			}
			
		  }
		} catch (Exception e) {
			e.printStackTrace();
			}
			return message;
		}
		
		private String getRequestCategory(Connection con ,CaptureLeadDetailsDTO leadDtoObj ,String product) throws com.ibm.lms.exception.DAOException
		{
			int src = 0;
			ResultSet rsRequestCategory =null;
			PreparedStatement psRequestCategory  = null;
			if(leadDtoObj.getRequestCategory() !=null && leadDtoObj.getRequestCategory().length() >0) {
				try {
					psRequestCategory = con.prepareStatement(GET_REQUEST_CATEGORY_ID);
					psRequestCategory.setString(1, leadDtoObj.getRequestCategory().toUpperCase());
					psRequestCategory.setString(2, product);
					rsRequestCategory = psRequestCategory.executeQuery();
				if(rsRequestCategory.next())
				{
					src = rsRequestCategory.getInt("REQUEST_CATEGORY_ID");
				}else {
				return null;	
			}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				DBConnection.releaseResources(null, psRequestCategory, rsRequestCategory);
				
			}
			}else {
				return null;
			}
			
			return String.valueOf(src);
		}
		
		private String UTMDataSource(CaptureLeadDetailsDTO captureLeadDO) {
			 try {
				 String referUrl  = captureLeadDO.getReferUrl();
				 if(referUrl != null  && referUrl.length() >0 ){
					 String soucetemp   =  referUrl.substring(referUrl.indexOf("/?")+2).replace('?', '&');
					 String tempMainSrc []  = soucetemp.split("&");
					for(String ur :tempMainSrc) {
						if(ur.contains("utm_campaign=")) {
							captureLeadDO.setUtm_campaign(ur.replaceAll("utm_campaign=",""));
						}else if(ur.contains("utm_medium=")) {
							captureLeadDO.setUtm_medium(ur.replaceAll("utm_medium=",""));
						}else if(ur.contains("utm_source=")) {
							captureLeadDO.setUtm_source(ur.replaceAll("utm_source=",""));
						}else if(ur.contains("cid=")) { 
							captureLeadDO.setUtm_cid(ur.replaceAll("cid=",""));
						}else if(ur.contains("utm_content=")) { 
							captureLeadDO.setUtm_content(ur.replaceAll("utm_content=",""));
						}
					}
				 }
			 } catch (Exception e) {
				e.printStackTrace();
			}
			return null;
			}
		
		private  void insertDirtyLead(CaptureLeadDetailsDTO leadDtoObj ,Connection con ,String message,String  mobileNumber) throws com.ibm.lms.exception.DAOException {
			logger.error("Start Inserting Dirty lead");
			PreparedStatement psDirtyLead  =null;
			try {
					
			psDirtyLead  = con.prepareStatement(SQL_INSERT_DIRTY_LEAD);
			String leadDataHistId = getHistoryDataId(con, leadDtoObj);
			psDirtyLead.setString(1, leadDtoObj.getProspectsName());
			psDirtyLead.setString(2, mobileNumber);
			psDirtyLead.setString(3, leadDtoObj.getAlternateContactNumber());
			psDirtyLead.setString(4, leadDtoObj.getAddress());
			psDirtyLead.setString(5, leadDtoObj.getCity());
			psDirtyLead.setString(6, leadDtoObj.getPincode());
			psDirtyLead.setString(7, leadDtoObj.getCircle());
			psDirtyLead.setString(8, leadDtoObj.getProduct());
			psDirtyLead.setString(9, leadDtoObj.getEmail());
			psDirtyLead.setString(10, leadDtoObj.getEmail());
			psDirtyLead.setString(11, leadDtoObj.getFid());
			psDirtyLead.setString(12, leadDtoObj.getTid());
			psDirtyLead.setString(13, leadDtoObj.getSubSource()); 
			psDirtyLead.setString(14, leadDtoObj.getSource()); 
			psDirtyLead.setString(15, leadDtoObj.getCampaign());
			psDirtyLead.setString(16, leadDtoObj.getReferUrl());
			psDirtyLead.setString(17, leadDtoObj.getReferPage());
			psDirtyLead.setString(18, leadDataHistId);
			
				
			psDirtyLead.executeUpdate();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				DBConnection.releaseResources(null, psDirtyLead, null);
				
			}
			
		}
		
		private String getHistoryDataId(Connection con ,CaptureLeadDetailsDTO leadDtoObj) throws com.ibm.lms.exception.DAOException {
			PreparedStatement ps = null;
			
			PreparedStatement pshistory = null;
			ResultSet rshistory = null;
			try {
				ps = con.prepareStatement(INSERT_OTHER_LEAD_DETAILS_HISTORY);
				ps = setLeadDetails(ps, leadDtoObj , null);
				ps.executeUpdate();

				pshistory = con.prepareStatement(GET_MAX_ID_OTHER_LEAD_DETAILS_HISTORY);
				rshistory = pshistory.executeQuery();
				if(rshistory.next()) {
					return String.valueOf(rshistory.getInt(1));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				DBConnection.releaseResources(null, ps, null);
				DBConnection.releaseResources(null, pshistory, rshistory);
			
			}
			return null;
		}
		
		private PreparedStatement setLeadDetails(PreparedStatement prep ,CaptureLeadDetailsDTO leadDtoObj, String leadId) {
			
			try {
				prep.setString(1, leadDtoObj.getCustomerInfoId());
				if(leadId !=null){
					prep.setString(2, leadId);
				}else {
					prep.setString(2, null);
				}
				prep.setString(3, leadDtoObj.getDob());
				prep.setString(4, leadDtoObj.getUsertype());
				prep.setString(5, leadDtoObj.getPlanId());
				prep.setString(6, leadDtoObj.getRentaltype());
				prep.setString(7, leadDtoObj.getState());
				if(leadDtoObj.getUtm_source() !=null || leadDtoObj.getUtm_medium() !=null || leadDtoObj.getUtm_source() !=null) {
					prep.setString(8, leadDtoObj.getUtm_source()+"|"+leadDtoObj.getUtm_medium()+"|"+leadDtoObj.getUtm_source());
				}
				else {
					prep.setString(8, null);
				}
				prep.setString(9, leadDtoObj.getUdId());
				prep.setString(10, leadDtoObj.getAgencyId());
				prep.setString(11, leadDtoObj.getBoostercount());
				prep.setString(12, leadDtoObj.getFreebietaken());
				prep.setString(13, leadDtoObj.getBoostertaken());
				prep.setString(14, leadDtoObj.getFlag());
				prep.setString(15, leadDtoObj.getPrepaidnumber());
				prep.setString(16, leadDtoObj.getOffer());
				prep.setString(17, leadDtoObj.getDownloadlimit());
				prep.setString(18, leadDtoObj.getDevicemrp());
				prep.setString(19, leadDtoObj.getVoicebenefit());
				prep.setString(20, leadDtoObj.getDataquota());
				if(leadDtoObj.getNdncDisclaimer() !=null && leadDtoObj.getNdncDisclaimer() !=""){
					prep.setString(21, leadDtoObj.getNdncDisclaimer());
				}else {
					prep.setString(21, Constants.LMS_NDNCDISCLAIMER);
				}
				prep.setString(22, leadDtoObj.getFeasibilityparam());
				prep.setString(23, leadDtoObj.getDevicetaken());
				prep.setString(24, leadDtoObj.getFreebiecount());
				prep.setString(25, leadDtoObj.getBenefit());
				prep.setString(26, leadDtoObj.getPkgduration());
				if(leadId !=null){
					try {
						if(leadDtoObj.getRequestCategory() !=null && leadDtoObj.getRequestCategory() !="") {
							prep.setInt(27, Integer.parseInt(leadDtoObj.getRequestCategory()));
						}else {
							prep.setInt(27, 0);
						}	
					} catch (Exception e) {
						prep.setInt(27, 0);
					}
				
				}else {
					prep.setString(27, leadDtoObj.getRequestCategory());	
				}
				prep.setString(28, leadDtoObj.getOpportunityTime());
				prep.setString(29, leadDtoObj.getHlrno());
				prep.setString(30, leadDtoObj.getUtm_cid());
				prep.setString(31, leadDtoObj.getUtm_content());
				//changes for newer version:
				prep.setString(32, leadDtoObj.getPaymentStatus());                       //payment status in EXTRA_PARAM6 of lead details
				
				
				if(leadDtoObj.getGender() !=null && leadDtoObj.getGender() !=""){
					prep.setString(33, leadDtoObj.getGender());                             // gender in EXTRA_PARAM7 in lead details
					
				}else {
					prep.setString(33, null);
				}
				
				
				
				
				if(leadDtoObj.getIdentityProofType() !=null && leadDtoObj.getIdentityProofType() !=""){
					prep.setString(34, leadDtoObj.getIdentityProofType());                             // identity proof type in EXTRA_PARAM8 in lead details
					
				}else {
					prep.setString(34, null);
				}
				
				prep.setString(35, leadDtoObj.getPaymentType());  					  //PAYMENT_TYPE in lead details
				prep.setString(36, leadDtoObj.getPaymentCollected());				 //PAYMENT_COLLECTED in lead details
				prep.setString(37, leadDtoObj.getExtraParam3());				    //LATITUDE as EXTRA_PARAM3 in lead details
				prep.setString(38, leadDtoObj.getExtraParam4());				   //LONGITUDE as EXTRA_PARAM4 in lead details
				
				
				if(leadDtoObj.getSimNo() !=null && leadDtoObj.getSimNo() !=""){
					prep.setString(39, leadDtoObj.getSimNo());                           //SIM_NUM as EXTRA_PARAM1 in lead details
					
				}else {
					prep.setString(39, null);
				}
				
				
				if(leadDtoObj.getCustomerSegment() !=null && leadDtoObj.getCustomerSegment() !=""){
					prep.setString(40, leadDtoObj.getCustomerSegment());                           //CUST SEGMENT as EXTRA_PARAM2 in lead details
					
				}else {
					prep.setString(40, null);
				}
				
				
				if(leadDtoObj.getNationality() !=null && leadDtoObj.getNationality() !=""){           //NATIONALITY as EXTRA_PARAM5 in lead details
					prep.setString(41, leadDtoObj.getNationality());
				}else {
					prep.setString(41, null);
				}
				
				
				
				if(leadId == null){
					prep.setString(43, Constants.WSFLAG);
					prep.setString(42, String.valueOf(leadDtoObj.getLeadCaptureId()));
				}else {
					prep.setString(42, String.valueOf(leadDtoObj.getLeadCaptureId()));
				}
			
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return prep;
		}
		
		private  void insertDuplicate(Connection con ,CaptureLeadDetailsDTO leadDtoObj ,String message,String  mobileNumber) throws com.ibm.lms.exception.DAOException{
			StringBuffer SQL_GET_DUPLICATE_LEAD = new StringBuffer("");
			PreparedStatement psDuplicateInsert = null;
			PreparedStatement psDuplicate = null;
			ResultSet rsDuplicate = null;
			
			try {
				if(ServerPropertyReader.getString("DUPLICATE_ONZEE_LOGIC").trim().equalsIgnoreCase("Y"))
				{		
					SQL_GET_DUPLICATE_LEAD.append("SELECT PROSPECT_MOBILE_NUMBER FROM LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,PRODUCT_SYNONYM c  WHERE a.PROSPECT_ID=b.PROSPECT_ID and a.PRODUCT_ID=c.PRODUCT_ID  and b.PROSPECT_MOBILE_NUMBER =? ");
					
					if(ServerPropertyReader.getString("DUPLICATE_ONZEE_DAYWISE").trim().equalsIgnoreCase("Y")){
						SQL_GET_DUPLICATE_LEAD.append("AND date(a.CREATE_TIME) >= current date - ? days ");
					if(ServerPropertyReader.getString("DUPLICATE_ONZEE_PRODUCTWISE").trim().equalsIgnoreCase("Y")){
					SQL_GET_DUPLICATE_LEAD.append(" and ucase(c.PRODUCT_SYNONYM_NAME) = ? ");
						}
					}
					else if(ServerPropertyReader.getString("DUPLICATE_ONZEE_PRODUCTWISE").trim().equalsIgnoreCase("Y")){
					SQL_GET_DUPLICATE_LEAD.append(" and ucase(c.PRODUCT_SYNONYM_NAME) = ? ");
					}
					SQL_GET_DUPLICATE_LEAD.append(" WITH UR");
					 psDuplicate =  con.prepareStatement(SQL_GET_DUPLICATE_LEAD.toString());
				}
				if(ServerPropertyReader.getString("DUPLICATE_ONZEE_LOGIC").trim().equalsIgnoreCase("Y")){
					psDuplicate.setString(1, mobileNumber);
					if(ServerPropertyReader.getString("DUPLICATE_ONZEE_DAYWISE").trim().equalsIgnoreCase("Y")){
					psDuplicate.setString(2,ServerPropertyReader.getString("DUPLICATE_DAYWISE").trim());
					if(ServerPropertyReader.getString("DUPLICATE_ONZEE_PRODUCTWISE").trim().equalsIgnoreCase("Y")){
					psDuplicate.setString(3, leadDtoObj.getProduct().toUpperCase());
						}
					}
					else if(ServerPropertyReader.getString("DUPLICATE_ONZEE_PRODUCTWISE").trim().equalsIgnoreCase("Y")){
					
						psDuplicate.setString(2, leadDtoObj.getProduct().toUpperCase());
					}
					SQL_GET_DUPLICATE_LEAD.append(" WITH UR");
					
					rsDuplicate= psDuplicate.executeQuery();
				}
				
				psDuplicateInsert = con.prepareStatement(SQL_INSERT_DUPLICATE_LEAD);
			
				if(rsDuplicate.next())
				{
					String leadDetailsId =  getHistoryDataId(con,leadDtoObj);
					duplicateleadFlag =  true;
					
					logger.error("This is duplicate lead. so inserting into DUPLICATE_LEAD table");
					psDuplicateInsert.setString(1, leadDtoObj.getProspectsName());
					psDuplicateInsert.setString(2, mobileNumber);
					psDuplicateInsert.setString(3, leadDtoObj.getAlternateContactNumber());
					psDuplicateInsert.setString(4, leadDtoObj.getAddress());
			        psDuplicateInsert.setString(5, leadDtoObj.getCity());
					psDuplicateInsert.setString(6, leadDtoObj.getPincode());
					psDuplicateInsert.setString(7, leadDtoObj.getCircle());
					psDuplicateInsert.setString(8, leadDtoObj.getProduct());
					psDuplicateInsert.setString(9, leadDtoObj.getEmail());
					psDuplicateInsert.setString(10, leadDtoObj.getFid());
					psDuplicateInsert.setString(11, leadDtoObj.getTid());
					psDuplicateInsert.setString(12, leadDtoObj.getSubSource());
					psDuplicateInsert.setString(13, leadDtoObj.getSource());
					psDuplicateInsert.setString(14, leadDtoObj.getCampaign());
					psDuplicateInsert.setString(15, leadDtoObj.getReferUrl());
					psDuplicateInsert.setString(16, leadDtoObj.getReferPage());
					psDuplicateInsert.setInt(17, Integer.parseInt(leadDetailsId));
					
					psDuplicateInsert.executeUpdate();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				DBConnection.releaseResources(null, psDuplicateInsert, rsDuplicate);
				DBConnection.releaseResources(null, psDuplicate, null);
			
			}
			
		}
		String SELECT_SEQ_LEAD_NO = " SELECT NEXTVAL FOR LMS.SEQ_LEAD_ID AS SEQ_ID FROM sysibm.SYSDUMMY1";
		public Long getLeadId(Long mobileNo , Connection con) 
		{
			Long leadId=0l;
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				
				ps = con.prepareStatement(SELECT_SEQ_LEAD_NO);
				rs = ps.executeQuery();
				rs.next();
				leadId = Long.parseLong((mobileNo/100000)+""+ rs.getInt("SEQ_ID"));
			} catch (Exception e) {
				e.printStackTrace();
				
			} finally {
				try {
					
					DBConnection.releaseResources(null, ps, rs);
				} catch (Exception e) {				
					
				}
			}
			return leadId;
		}
		public String getNextVal(String seqName,Connection con) 
		{
			String leadId="";
			PreparedStatement ps=null;
			ResultSet rs=null;
			String SQL_GET_LEAD_SEQ_ID = "select nextval for "+seqName+" as NEXTVAL from sysibm.SYSDUMMY1 with ur";
			
			try {
				 ps=con.prepareStatement(SQL_GET_LEAD_SEQ_ID);
				 rs=ps.executeQuery();
				 if(rs.next())
				 {
					 leadId=rs.getString("NEXTVAL");
				 }
			} catch (Exception e) {
				
			}
			finally
			{
				try
				{	
					DBConnection.releaseResources(null, ps, rs);
						
				}
				catch(Exception ex)
				{
					rs=null;
					ps = null;
					
				}
			}
			return leadId;
		}
		
		public int getCircleID(Connection con ,CaptureLeadDetailsDTO leadDtoObj,String PRODUCT_LOB_ID ) throws com.ibm.lms.exception.DAOException {
			PreparedStatement psCircle =null;
			ResultSet rsCircle = null;
			int circleId =-1;
			try {
			if(leadDtoObj.getCircleId() != null && leadDtoObj.getCircleId().length() >0) {
					try {
					psCircle = con.prepareStatement(GET_CIRCLE_ID_FROM_ID);
					psCircle.setString(1, leadDtoObj.getCircleId().trim());
					psCircle.setString(2, PRODUCT_LOB_ID);//Added this field as circle validation has changed now
					rsCircle= psCircle.executeQuery();
					psCircle.clearParameters();
					if(rsCircle.next())
					{
						circleId = rsCircle.getInt("CIRCLE_ID");
						return circleId;
					}
					} catch (Exception e) {
					}finally{
						DBConnection.releaseResources(null, psCircle, rsCircle);
						
					}
				}
				
			
			if(leadDtoObj.getCircle() != null && leadDtoObj.getCircle().length() > 0 && circleId <=0) {
				try {
					psCircle = con.prepareStatement(GET_CIRCLE_ID);
					psCircle.setString(1, leadDtoObj.getCircle().toUpperCase());
					psCircle.setString(2, PRODUCT_LOB_ID);
					rsCircle= psCircle.executeQuery();
					if(rsCircle.next())
					{
						circleId = rsCircle.getInt("CIRCLE_ID");
						return circleId;
					}
				} catch (Exception e) {
				}finally {
					
					DBConnection.releaseResources(null, psCircle, rsCircle);
				}
			}
			
			if(leadDtoObj.getState() != null && leadDtoObj.getState().length() >0  && circleId <=0) {
				try {
				psCircle = con.prepareStatement(GET_CIRCLE_ID_FROM_STATE);
				psCircle.setString(1, leadDtoObj.getState().toUpperCase());
				psCircle.setString(2, PRODUCT_LOB_ID);//Added this field as circle validation has changed now
				rsCircle= psCircle.executeQuery();
				psCircle.clearParameters();
				if(rsCircle.next())
				{
					circleId = rsCircle.getInt("CIRCLE_ID");
					return circleId;
				}
				} catch (Exception e) {
				}finally{
					DBConnection.releaseResources(null, psCircle, rsCircle);
					
				}
			}
			
			} catch (Exception e) {
				DBConnection.releaseResources(null, psCircle, rsCircle);
				
			}
			
			return circleId;
		}
		
		private String validateLeadOtherData(Connection con ,CaptureLeadDetailsDTO leadDtoObj ,String PRODUCT_LOB_ID ,int circleId){
			String pincode ="";
			String cityId ="" ;
			String zoneCode="";
			String cityZoneCode="";
			try {
				PreparedStatement psRsuCode = con.prepareStatement(GET_ALL_FROM_RSUCODE);
				PreparedStatement psPincode = con.prepareStatement(GET_ALL_FROM_PINCODE);
				PreparedStatement psCity =  null;
				PreparedStatement psCityZone = con.prepareStatement(GET_CITY_ZONE_CODE_FROM_CITY);
				PreparedStatement psCityZoneFromCircle = con.prepareStatement(GET_CITY_ZONE_CODE_FROM_CIRCLE); 
				PreparedStatement psZone = con.prepareStatement(GET_ZONE_CODE_FROM_CIRCLE);
				
				ResultSet rsRsuCode = null;
				ResultSet rsPincode  = null;
				ResultSet rsCity  = null;
				ResultSet rsCityZone  = null;
				ResultSet rsCityZoneFromCircle  = null;
				ResultSet rsZone  = null;
				
				//To validate rsu_code
				if(leadDtoObj.getRsuCode() != null && leadDtoObj.getRsuCode().length() >0 && PRODUCT_LOB_ID.equalsIgnoreCase(String.valueOf(Constants.PRODUCT_TELEMEDIA_LOB_ID)))
				{
					try {
						psRsuCode.setString(1, leadDtoObj.getRsuCode().trim());
						psRsuCode.setInt(2, circleId);
						psRsuCode.setString(3, PRODUCT_LOB_ID);
						rsRsuCode = psRsuCode.executeQuery();
						if(rsRsuCode.next()){			
							zoneCode = rsRsuCode.getString("ZONE_CODE");
							cityId = rsRsuCode.getString("CITY_CODE");
							cityZoneCode = rsRsuCode.getString("CITY_ZONE_CODE");
							//rsuCode  = rsRsuCode.getString("RSU_CODE");
							return cityId+"#"+cityZoneCode+"#"+zoneCode+"#"+pincode;
						}
					} catch (Exception e) {
					}finally{
						try {
							DBConnection.releaseResources(null, psRsuCode, rsRsuCode);
							
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
				//To find out the Zone-code, City Code, City-Zone Code from Pincode entered in web-service input
				if(leadDtoObj.getPincode() != null && leadDtoObj.getPincode().length() >0){
					try {
						psPincode.setString(1, leadDtoObj.getPincode());
						psPincode.setInt(2, circleId);
						psPincode.setString(3, PRODUCT_LOB_ID);
						rsPincode = psPincode.executeQuery();
						if(rsPincode.next()){							
							zoneCode = rsPincode.getString("ZONE_CODE");
							cityId = rsPincode.getString("CITY_CODE");
							cityZoneCode = rsPincode.getString("CITY_ZONE_CODE");
							pincode =  rsPincode.getString("PINCODE");
							return cityId+"#"+cityZoneCode+"#"+zoneCode+"#"+pincode;
						}	
					} catch (Exception e) {
					}
					finally {
						try {
							DBConnection.releaseResources(null, psPincode, rsPincode);
							
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
				
				//To find out the city code from city Name entered in web-service input 
				try {
					 if((leadDtoObj.getCity() != null && leadDtoObj.getCity().length() >0)||(leadDtoObj.getCityId() != null && leadDtoObj.getCityId().length() >0) ){	
							psCity = con.prepareStatement(GET_CITY_ID_FROMWS_ID);
							psCity.setString(1, leadDtoObj.getCityId().trim());
							psCity.setString(2, PRODUCT_LOB_ID);
							psCity.setInt(3, circleId);
							if(ServerPropertyReader.getString("DATA_ID_BASED_LOGIC").trim().equalsIgnoreCase("Y"))
							rsCity= psCity.executeQuery();
							if(rsCity.next()) {
								cityId = rsCity.getString("CITY_CODE");
								zoneCode = rsCity.getString("ZONE_CODE");
							}
							else {
								psCity = con.prepareStatement(GET_CITY_ID);
								psCity.setString(1, leadDtoObj.getCity().toUpperCase());
								psCity.setString(2, PRODUCT_LOB_ID);
								psCity.setInt(3, circleId);
								rsCity= psCity.executeQuery();
								if(rsCity.next()) {
									cityId=rsCity.getString("CITY_CODE");
									zoneCode = rsCity.getString("ZONE_CODE");
								}
							}
							//To find out the city-zone code entered in web-service input /* If Valid City Code is received */
							if(cityId !=null && cityId.length() >0 && leadDtoObj.getCityZoneCode() != null && leadDtoObj.getCityZoneCode().length() >0){
									psCityZone.setString(1, leadDtoObj.getCityZoneCode().toUpperCase());
									psCityZone.setString(2, cityId);
									rsCityZone= psCityZone.executeQuery();
									if(rsCityZone.next()) {
										cityZoneCode = rsCityZone.getString("CITY_ZONE_CODE");
										return cityId+"#"+cityZoneCode+"#"+zoneCode+"#"+pincode;
									}
							}
						}
					} catch (Exception e) {
						return cityId+"#"+cityZoneCode+"#"+zoneCode+"#"+pincode;
					}finally {
						try {
							DBConnection.releaseResources(null, psCity, rsCity);
							DBConnection.releaseResources(null, psPincode, rsCityZone);
							
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
					 /* If Valid City Code is not received */// If city Name is not available/ Valid , then reverse populating city and zone from city zone
					 if(leadDtoObj.getCityZoneCode() != null && leadDtoObj.getCityZoneCode().length() >0) {
						 try {
								psCityZoneFromCircle.setString(1, leadDtoObj.getCityZoneCode().toUpperCase());
								psCityZoneFromCircle.setString(2, PRODUCT_LOB_ID);
								psCityZoneFromCircle.setInt(3, circleId);
								rsCityZoneFromCircle= psCityZoneFromCircle.executeQuery();
								if(rsCityZoneFromCircle.next()) {
									cityZoneCode = rsCityZoneFromCircle.getString("CITY_ZONE_CODE");
									cityId = rsCityZoneFromCircle.getString("CITY_CODE");
									zoneCode = rsCityZoneFromCircle.getString("ZONE_CODE");
									return cityId+"#"+cityZoneCode+"#"+zoneCode+"#"+pincode;
								}
							} catch (Exception e) {
							}finally {
								try {
									DBConnection.releaseResources(null, psCityZoneFromCircle, rsCityZoneFromCircle);
									
								} catch (Exception e2) {
									e2.printStackTrace();
								}
							}
						}	
					// If City Zone Code is not Valid, then populating zone from zone Name
					 if(leadDtoObj.getZone() != null && leadDtoObj.getZone().length() > 0){
							psZone.setString(1, leadDtoObj.getZone().toUpperCase());
							psZone.setString(2, PRODUCT_LOB_ID);
							psZone.setInt(3, circleId);
							rsZone= psZone.executeQuery();
							if(rsZone.next()) 	{
								zoneCode = rsZone.getString("ZONE_CODE");
							}
							try {
								DBConnection.releaseResources(null, psZone, rsZone);
								
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					 return cityId+"#"+cityZoneCode+"#"+zoneCode+"#"+pincode;
				//end city, zone code logic. 
			} catch (Exception e) {
				//e.printStackTrace();
				return cityId+"#"+cityZoneCode+"#"+zoneCode+"#"+pincode;
			}
			
		}
		
		
		private boolean isValidAutoQualified(String productLobId, String productId, int circleId, CaptureLeadDetailsDTO leadDtoObj,Connection con,String  otherdata) 
		{	
			boolean autoAssign = false;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<LeadAutoQualifiedDTO>  leadAutoQuaList  = new ArrayList<LeadAutoQualifiedDTO>();
			
			String [] cIdCzCodeDataz  = otherdata.split("#");
			
			try {
				//MATRIX_ID ,ZONE_CODE,CITY_CODE,CITY_ZONE_CODE,PINCODE,REQUEST_CATEGORY_ID
				StringBuilder  builder  = new StringBuilder(SELECT_AUTO_QUALIFIED_LEAD);
				builder.append("WITH UR");
				ps = con.prepareStatement(builder.toString());
				ps.setString(1, productLobId);
				ps.setString(2, productId);
				ps.setInt(3, circleId);
				rs = ps.executeQuery();
				while(rs.next()){
					LeadAutoQualifiedDTO autoQualifiedDO  = new LeadAutoQualifiedDTO();
					autoQualifiedDO.setCityZoneCode(rs.getString("CITY_ZONE_CODE"));
					autoQualifiedDO.setCity(rs.getString("CITY_CODE"));
					autoQualifiedDO.setPincode(rs.getString("PINCODE"));
					autoQualifiedDO.setRequestCategory(rs.getString("REQUEST_CATEGORY_ID"));
					autoQualifiedDO.setZone(rs.getString("ZONE_CODE"));
					autoQualifiedDO.setMatrixId(rs.getString("MATRIX_ID"));
					leadAutoQuaList.add(autoQualifiedDO);
					autoAssign = true;
				}
				if(autoAssign && leadAutoQuaList.size()>0) {
					return validateQualified(cIdCzCodeDataz, leadAutoQuaList);
				}else {
					return false;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					
					DBConnection.releaseResources(null, ps, rs);
				} catch (Exception e) {				
					//throw new DAOException(e.getMessage(), e);
				}
			}
			return autoAssign;
		}
		private int getValidateSubSource(Connection con ,CaptureLeadDetailsDTO leadDtoObj) throws com.ibm.lms.exception.DAOException{
			int subsrc =0 ;
			ResultSet rsSubSource = null;
			PreparedStatement psSubSource = null;
			if(leadDtoObj.getSubSource() !=null && leadDtoObj.getSubSource().length() >0) {
				try {
			    psSubSource = con.prepareStatement(GET_SUB_SOURCE_ID);
				psSubSource.setString(1, leadDtoObj.getSubSource().toUpperCase());
				rsSubSource = psSubSource.executeQuery();
				if(rsSubSource.next())
				{
					subsrc = rsSubSource.getInt("SUBSOURCE_ID");
				}else {
					psSubSource.clearParameters();
					subsrc = getSourceMaxId("SUB_SOURCE_MSTR", con, "SUBSOURCE_ID");
					if(subsrc ==0) {
						return Constants.LEAD_SUB_SOURCE_OTHER;
					}
					psSubSource = con.prepareStatement(INSERT_SUB_SOURCE);
					psSubSource.setInt(1, subsrc);
					psSubSource.setString(2, leadDtoObj.getSubSource().toUpperCase());
					psSubSource.executeUpdate();
				}
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				finally{
					DBConnection.releaseResources(null, psSubSource, rsSubSource);
					
				}
				}else {
					subsrc = Constants.LEAD_SUB_SOURCE_OTHER;
				}
			
			return subsrc;
		}
		private int getValidateSource(Connection con ,CaptureLeadDetailsDTO leadDtoObj) throws com.ibm.lms.exception.DAOException{
			int src =0;
			ResultSet rsSource =null;
			PreparedStatement psSource  = null;
			if(leadDtoObj.getSource() !=null && leadDtoObj.getSource().length() >0) {
				try {
					
				psSource = con.prepareStatement(GET_SOURCE_ID);
				psSource.setString(1, leadDtoObj.getSource().toUpperCase());
				rsSource = psSource.executeQuery();
				if(rsSource.next())
				{
					src = rsSource.getInt("SOURCE_ID");
				}else {
				psSource.clearParameters();
				src = getSourceMaxId("SOURCE_MSTR", con, "SOURCE_ID");
				if(src ==0) {
					return Constants.LEAD_SOURCE_ONLINE1;
				}
				psSource = con.prepareStatement(INSERT_SOURCE);
				psSource.setInt(1, src);
				psSource.setString(2, leadDtoObj.getSource().toUpperCase());
				psSource.executeUpdate();
				return src;
			}
			} catch (Exception e) {
				e.printStackTrace();
			}finally 
			{
				DBConnection.releaseResources(null, psSource, rsSource);
				
			}
			}else {
				src = Constants.LEAD_SOURCE_ONLINE1;
			}
			
			return src;
		}
		private boolean validateQualified(String [] cIdCzCodeDataz ,List<LeadAutoQualifiedDTO>  leadAutoQuaList ){
			String cityId  = null;
			String cityZoneCode  = null;
			String zoneCode  = null;
			String pincode  =  null;
			String requestCategory = null;
			try {
				cityId  = cIdCzCodeDataz[0];
				cityZoneCode  = cIdCzCodeDataz[1];
				zoneCode  = cIdCzCodeDataz[2];
				pincode  = cIdCzCodeDataz[3];
				requestCategory  = cIdCzCodeDataz[4];
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			for(LeadAutoQualifiedDTO autoQualifiedDO : leadAutoQuaList) {
				List<String> list  = new LinkedList<String>();
				if(autoQualifiedDO.getCity()!=null && autoQualifiedDO.getCity().length() >=1) {
				list.add(autoQualifiedDO.getCity());
				}
				if(autoQualifiedDO.getCityZoneCode()!=null && autoQualifiedDO.getCityZoneCode().length()>=1) {
				list.add(autoQualifiedDO.getCityZoneCode());
				}
				if(autoQualifiedDO.getRequestCategory()!=null && autoQualifiedDO.getRequestCategory().length()>0){
				list.add(autoQualifiedDO.getRequestCategory());
				}
				if(autoQualifiedDO.getPincode()!=null && autoQualifiedDO.getPincode().length()>=1){
				list.add(autoQualifiedDO.getPincode());
				}
				if(autoQualifiedDO.getZone()!=null && autoQualifiedDO.getZone().length()>=1){
				list.add(autoQualifiedDO.getZone());
				}
				
				if(cityId !=null && cityZoneCode !=null && zoneCode !=null && pincode !=null && requestCategory != null && cityId.equalsIgnoreCase(autoQualifiedDO.getCity()) && cityZoneCode.equalsIgnoreCase(autoQualifiedDO.getCityZoneCode()) 
						&& zoneCode.equalsIgnoreCase(autoQualifiedDO.getZone()) && pincode.equalsIgnoreCase(autoQualifiedDO.getPincode()) && requestCategory.equalsIgnoreCase(autoQualifiedDO.getRequestCategory())) {
					return true;
				}
				if(list.size()==4) {
				 if(autoQualifiedDO.getRequestCategory()==null  && cityId !=null && cityZoneCode !=null && zoneCode !=null && pincode !=null && cityId.equalsIgnoreCase(autoQualifiedDO.getCity()) && cityZoneCode.equalsIgnoreCase(autoQualifiedDO.getCityZoneCode()) 
						&& zoneCode.equalsIgnoreCase(autoQualifiedDO.getZone()) && pincode.equalsIgnoreCase(autoQualifiedDO.getPincode())){
					return true;
				}else if(autoQualifiedDO.getCity()==null  && cityZoneCode !=null && zoneCode !=null && pincode !=null && requestCategory != null && cityZoneCode.equalsIgnoreCase(autoQualifiedDO.getCityZoneCode()) && zoneCode.equalsIgnoreCase(autoQualifiedDO.getZone())
						&& pincode.equalsIgnoreCase(autoQualifiedDO.getPincode()) && requestCategory.equalsIgnoreCase(autoQualifiedDO.getRequestCategory())){
					return true;
				}else if(autoQualifiedDO.getCityZoneCode()==null  && cityId !=null && zoneCode !=null && pincode !=null && requestCategory != null  && cityId.equalsIgnoreCase(autoQualifiedDO.getCity()) 
						&& zoneCode.equalsIgnoreCase(autoQualifiedDO.getZone()) && pincode.equalsIgnoreCase(autoQualifiedDO.getPincode()) && requestCategory.equalsIgnoreCase(autoQualifiedDO.getRequestCategory())){
					return true;
				}else if(autoQualifiedDO.getZone()==null  && cityId !=null && cityZoneCode !=null  && pincode !=null && requestCategory != null && cityId.equalsIgnoreCase(autoQualifiedDO.getCity()) && cityZoneCode.equalsIgnoreCase(autoQualifiedDO.getCityZoneCode()) 
						&& pincode.equalsIgnoreCase(autoQualifiedDO.getPincode()) && requestCategory.equalsIgnoreCase(autoQualifiedDO.getRequestCategory())){
					return true;
				}else if(autoQualifiedDO.getPincode()==null  && cityId !=null && cityZoneCode !=null && zoneCode !=null  && requestCategory != null && cityId.equalsIgnoreCase(autoQualifiedDO.getCity()) && cityZoneCode.equalsIgnoreCase(autoQualifiedDO.getCityZoneCode()) 
						&& zoneCode.equalsIgnoreCase(autoQualifiedDO.getZone()) && requestCategory.equalsIgnoreCase(autoQualifiedDO.getRequestCategory())){
					return true;
				}
				}
				if(list.size()==3) {
				 if((autoQualifiedDO.getPincode()==null ||autoQualifiedDO.getPincode()=="")  && (autoQualifiedDO.getRequestCategory()==null || autoQualifiedDO.getRequestCategory()=="") && cityId !=null && cityZoneCode !=null && zoneCode !=null && cityId.equalsIgnoreCase(autoQualifiedDO.getCity()) && cityZoneCode.equalsIgnoreCase(autoQualifiedDO.getCityZoneCode()) 
						&& zoneCode.equalsIgnoreCase(autoQualifiedDO.getZone())){
					return true;
				}else if((autoQualifiedDO.getRequestCategory()==null ||autoQualifiedDO.getRequestCategory()=="") && (autoQualifiedDO.getCity()==null || autoQualifiedDO.getCity()=="") && cityZoneCode !=null && zoneCode !=null && pincode !=null && cityZoneCode.equalsIgnoreCase(autoQualifiedDO.getCityZoneCode())&& zoneCode.equalsIgnoreCase(autoQualifiedDO.getZone()) && pincode.equalsIgnoreCase(autoQualifiedDO.getPincode())){
					return true;
				}else if((autoQualifiedDO.getCity()==null || autoQualifiedDO.getCity()=="")&& (autoQualifiedDO.getCityZoneCode()==null || autoQualifiedDO.getCityZoneCode()=="") && zoneCode !=null && pincode !=null && requestCategory != null 	&& zoneCode.equalsIgnoreCase(autoQualifiedDO.getZone()) && pincode.equalsIgnoreCase(autoQualifiedDO.getPincode()) && requestCategory.equalsIgnoreCase(autoQualifiedDO.getRequestCategory())){
					return true;
				}else if((autoQualifiedDO.getPincode()==null || autoQualifiedDO.getPincode()=="")&& (autoQualifiedDO.getZone()==null || autoQualifiedDO.getZone()=="") && cityId !=null && cityZoneCode !=null && requestCategory != null && cityId.equalsIgnoreCase(autoQualifiedDO.getCity()) && cityZoneCode.equalsIgnoreCase(autoQualifiedDO.getCityZoneCode()) 
						&& requestCategory.equalsIgnoreCase(autoQualifiedDO.getRequestCategory())){
					return true;
				}else if((autoQualifiedDO.getZone()==null ||autoQualifiedDO.getZone()=="") && (autoQualifiedDO.getCityZoneCode()==null || autoQualifiedDO.getCityZoneCode()=="") && cityId !=null && pincode !=null && requestCategory != null && cityId.equalsIgnoreCase(autoQualifiedDO.getCity()) && pincode.equalsIgnoreCase(autoQualifiedDO.getPincode()) && requestCategory.equalsIgnoreCase(autoQualifiedDO.getRequestCategory())){
					return true;
				}else if((autoQualifiedDO.getZone()==null ||autoQualifiedDO.getZone()=="") && (autoQualifiedDO.getPincode()==null || autoQualifiedDO.getPincode()=="") & cityZoneCode !=null && pincode !=null && requestCategory != null && cityZoneCode.equalsIgnoreCase(autoQualifiedDO.getCityZoneCode())&& pincode.equalsIgnoreCase(autoQualifiedDO.getPincode()) && requestCategory.equalsIgnoreCase(autoQualifiedDO.getRequestCategory())){
					return true;
				}else if((autoQualifiedDO.getPincode()==null ||autoQualifiedDO.getPincode().length() <=0) && (autoQualifiedDO.getCity()==null || autoQualifiedDO.getCity()=="") && cityZoneCode !=null && zoneCode !=null && requestCategory != null && cityZoneCode.equalsIgnoreCase(autoQualifiedDO.getCityZoneCode()) && zoneCode.equalsIgnoreCase(autoQualifiedDO.getZone()) && requestCategory.equalsIgnoreCase(autoQualifiedDO.getRequestCategory())){
					return true;
				}else if((autoQualifiedDO.getZone()==null ||autoQualifiedDO.getZone()=="") && (autoQualifiedDO.getRequestCategory()==null ||autoQualifiedDO.getRequestCategory()=="") && cityId !=null && pincode !=null && cityZoneCode !=null && cityZoneCode.equalsIgnoreCase(autoQualifiedDO.getCityZoneCode()) && cityId.equalsIgnoreCase(autoQualifiedDO.getCity()) && pincode.equalsIgnoreCase(autoQualifiedDO.getPincode())) {
					return true;
				}else if((autoQualifiedDO.getRequestCategory()==null ||autoQualifiedDO.getRequestCategory()=="") && (autoQualifiedDO.getCityZoneCode()==null || autoQualifiedDO.getCityZoneCode().length()<=0) && cityId !=null && pincode !=null && zoneCode !=null && zoneCode.equalsIgnoreCase(autoQualifiedDO.getZone()) && cityId.equalsIgnoreCase(autoQualifiedDO.getCity()) && pincode.equalsIgnoreCase(autoQualifiedDO.getPincode())) {
					return true;
				}else if((autoQualifiedDO.getPincode()==null ||autoQualifiedDO.getPincode()=="") && (autoQualifiedDO.getCityZoneCode()==null || autoQualifiedDO.getCityZoneCode()=="") && cityId !=null && zoneCode !=null && requestCategory != null && cityId.equalsIgnoreCase(autoQualifiedDO.getCity()) && zoneCode.equalsIgnoreCase(autoQualifiedDO.getZone()) && requestCategory.equalsIgnoreCase(autoQualifiedDO.getRequestCategory())) {
					return true;
				}
				}
				if(list.size() ==2) {
				 if(autoQualifiedDO.getPincode()==null && autoQualifiedDO.getZone()==null && autoQualifiedDO.getRequestCategory()==null && cityId !=null && cityZoneCode !=null && cityId.equalsIgnoreCase(autoQualifiedDO.getCity()) && cityZoneCode.equalsIgnoreCase(autoQualifiedDO.getCityZoneCode())){
					return true;
				}else if(autoQualifiedDO.getPincode()==null && autoQualifiedDO.getCityZoneCode()==null && autoQualifiedDO.getRequestCategory()==null && cityId !=null && zoneCode !=null && cityId.equalsIgnoreCase(autoQualifiedDO.getCity())	&& zoneCode.equalsIgnoreCase(autoQualifiedDO.getZone())){
					return true;
				}else if(autoQualifiedDO.getCityZoneCode()==null && autoQualifiedDO.getZone()==null && autoQualifiedDO.getRequestCategory()==null &&cityId !=null  && pincode !=null && cityId.equalsIgnoreCase(autoQualifiedDO.getCity()) && pincode.equalsIgnoreCase(autoQualifiedDO.getPincode())){
					return true;
				}else if(autoQualifiedDO.getPincode()==null && autoQualifiedDO.getZone()==null && autoQualifiedDO.getCityZoneCode()==null && cityId !=null && requestCategory != null && cityId.equalsIgnoreCase(autoQualifiedDO.getCity())&& requestCategory.equalsIgnoreCase(autoQualifiedDO.getRequestCategory())){
					return true;
				}else if(autoQualifiedDO.getPincode()==null && autoQualifiedDO.getCity()==null && autoQualifiedDO.getRequestCategory()==null && cityZoneCode !=null && zoneCode !=null && cityZoneCode.equalsIgnoreCase(autoQualifiedDO.getCityZoneCode())&& zoneCode.equalsIgnoreCase(autoQualifiedDO.getZone())){
					return true;
				}else if(autoQualifiedDO.getCity()==null && autoQualifiedDO.getZone()==null && autoQualifiedDO.getRequestCategory()==null && cityZoneCode !=null && pincode !=null && cityZoneCode.equalsIgnoreCase(autoQualifiedDO.getCityZoneCode()) && pincode.equalsIgnoreCase(autoQualifiedDO.getPincode())){
					return true;
				}else if(autoQualifiedDO.getPincode()==null && autoQualifiedDO.getZone()==null && autoQualifiedDO.getCity()==null && cityZoneCode !=null && requestCategory != null && cityZoneCode.equalsIgnoreCase(autoQualifiedDO.getCityZoneCode()) && requestCategory.equalsIgnoreCase(autoQualifiedDO.getRequestCategory())){
					return true;
				}else if(autoQualifiedDO.getCityZoneCode()==null && autoQualifiedDO.getCity()==null && autoQualifiedDO.getRequestCategory()==null && zoneCode !=null && pincode !=null 	&& zoneCode.equalsIgnoreCase(autoQualifiedDO.getZone()) && pincode.equalsIgnoreCase(autoQualifiedDO.getPincode())){
					return true;
				}else if(autoQualifiedDO.getPincode()==null && autoQualifiedDO.getCityZoneCode()==null && autoQualifiedDO.getCity()==null && zoneCode !=null && requestCategory != null && zoneCode.equalsIgnoreCase(autoQualifiedDO.getZone()) && requestCategory.equalsIgnoreCase(autoQualifiedDO.getRequestCategory())){
					return true;
				}else if(autoQualifiedDO.getCityZoneCode()==null && autoQualifiedDO.getZone()==null && autoQualifiedDO.getCity()==null && pincode !=null && requestCategory != null && pincode.equalsIgnoreCase(autoQualifiedDO.getPincode()) && requestCategory.equalsIgnoreCase(autoQualifiedDO.getRequestCategory())){
					return true;
				}
				}
				if(list.size() ==1) {
				 if(autoQualifiedDO.getCityZoneCode()==null && autoQualifiedDO.getPincode()==null && autoQualifiedDO.getZone()==null && autoQualifiedDO.getRequestCategory()==null&& cityId !=null && cityId.equalsIgnoreCase(autoQualifiedDO.getCity())){
					return true;
				}else if(autoQualifiedDO.getPincode()==null && autoQualifiedDO.getZone()==null && autoQualifiedDO.getRequestCategory()==null&& autoQualifiedDO.getCity()==null&& cityZoneCode !=null && cityZoneCode.equalsIgnoreCase(autoQualifiedDO.getCityZoneCode())){
					return true;
				}else if(autoQualifiedDO.getCityZoneCode()==null && autoQualifiedDO.getPincode()==null && autoQualifiedDO.getCity()==null && autoQualifiedDO.getRequestCategory()==null&& zoneCode !=null && zoneCode.equalsIgnoreCase(autoQualifiedDO.getZone())){
					return true;
				}else if(autoQualifiedDO.getCityZoneCode()==null && autoQualifiedDO.getCity()==null && autoQualifiedDO.getZone()==null && autoQualifiedDO.getRequestCategory()==null&& pincode !=null && pincode.equalsIgnoreCase(autoQualifiedDO.getPincode())){
					return true;
				}else if(autoQualifiedDO.getCityZoneCode()==null && autoQualifiedDO.getPincode()==null && autoQualifiedDO.getZone()==null && autoQualifiedDO.getCity()==null&& requestCategory != null && requestCategory.equalsIgnoreCase(autoQualifiedDO.getRequestCategory())){
					return true;
				}
				}
				
			}
			
			return false;
		}
		
		private int getSourceMaxId(String query ,Connection con ,String ColumnName) throws com.ibm.lms.exception.DAOException {
			int i =0;

			ResultSet rsSourceMax =null;
			PreparedStatement psSourceMax  = null;
			
			try {
				psSourceMax = con.prepareStatement("SELECT MAX("+ColumnName+") FROM "+query+" WITH UR");
				rsSourceMax  = psSourceMax.executeQuery();
				if(rsSourceMax.next()) {
					i = rsSourceMax.getInt(1)+1;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				DBConnection.releaseResources(null, psSourceMax, rsSourceMax);
				
			}
			return i;
		}
		
		public boolean getLcIsDoneStatus(String transactionid) throws DAOException {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				if(transactionid ==null || transactionid.trim().length() <=0) {
					return false;
				}
				con = DBConnection.getDBConnection();
				ps = con.prepareStatement(CHECK_LC_ISDONE);  //means lead is generated.
				ps.setLong(1, Long.parseLong(transactionid.trim()));
				rs = ps.executeQuery();
			
				while(rs.next()) {
					return true;
				}
				
			} catch (Exception e) {
				throw new DAOException("Exception occured while getting lc_is_done status :  "+ e.getMessage(),e);
			} finally
			{
				try {
					DBConnection.releaseResources(con, ps, rs);
				} catch (Exception e) {				
					throw new DAOException(e.getMessage(), e);
				}
			}
			return false;
			}
		
		public int getCapturedId(String leadId)throws DAOException {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int captureid=-1;
			try {
				con = DBConnection.getDBConnection();
				ps = con.prepareStatement(CHECK_LEAD_CAPTURED_DATA_ID);
				ps.setString(1, leadId);
				rs = ps.executeQuery();
			
				while(rs.next()) {
					captureid=rs.getInt("LEAD_CAPTURED_DATA_ID");
				}
				
			} catch (Exception e) {
				throw new DAOException("Exception occured while getting transaction id :  "+ e.getMessage(),e);
			} finally
			{
				try {
					DBConnection.releaseResources(con, ps, rs);
				} catch (Exception e) {				
					throw new DAOException(e.getMessage(), e);
				}
			}
			return captureid;
			}
		public String getLeadIdFromTransactionId(String capturedId)throws DAOException {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String leadId=null;
			try {
				con = DBConnection.getDBConnection();
				ps = con.prepareStatement(CHECK_LEAD_ID);
				if(capturedId==null || capturedId.equals(""))
				{
					ps.setString(1,null);
				}
				else
				{
				ps.setString(1, capturedId);
				}
				rs = ps.executeQuery();
			
				while(rs.next()) {
					leadId=rs.getString("LEAD_ID");
				}
				
			} catch (Exception e) {
				throw new DAOException("Exception occured while getting leadId :  "+ e.getMessage(),e);
			} finally
			{
				try {
					DBConnection.releaseResources(con, ps, rs);
				} catch (Exception e) {				
					throw new DAOException(e.getMessage(), e);
				}
			}
			return leadId;
			}
		@SuppressWarnings("null")
		public void resultSetData(String leadId) {
			UpdateLeadDataDO dto =null;
			
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				con = DBConnection.getDBConnection();
				ps = con.prepareStatement(GET_DATA);
				ps.setString(1, leadId);
			
				while(rs.next())
				{				
					
					dto.setPaymentType(rs.getString("PAYMENT_TYPE"));
					dto.setCafNumber(rs.getString("CAF"));
					dto.setCompetitorChosen(rs.getString("COMPETITOR_CHOSEN"));
					dto.setPaymentCollected(rs.getString("PAYMENT_COLLECTED"));
					dto.setProductBought(rs.getString("PRODUCT_BOUGHT"));
					dto.setRemarks(rs.getString("REMARKS"));
					dto.setRentalPlan(rs.getString("PLAN"));
					dto.setStatus(rs.getString("LEAD_STATUS_ID"));
					dto.setSentBy(rs.getString("SENTBY"));
					dto.setSubStatus(rs.getString("LEAD_SUB_STATUS_ID"));
					
				}
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
				return; 
				
			}
		
		public  Map<String, String> getStatusMap(Connection con)
		{
			//logger.info("Calling getStatusMap function for populating status");
			Map<String, String> map = new HashMap<String, String>();
			PreparedStatement ps = null;
			ResultSet rs=null;
			try
			{ 
				ps = con.prepareStatement(GET_MAP);
				rs=ps.executeQuery();
				while(rs.next())
				{
					map.put(rs.getString("LEAD_STATUS"), rs.getString("LEAD_STATUS_ID"));
				}
			}
			catch(Exception ex)
			{
				logger.info("Exception::"+ex);
				
			}
			finally
			{
				try {
					DBConnection.releaseResources(null, ps, rs);
					
				} catch(Exception ex)
				{
					logger.info(ex);
				}
			}
			return map;
		}	
		
		public String isValidCaptureId(String transactionid) throws DAOException {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String captureId="";
			try {
				con = DBConnection.getDBConnection();
				ps = con.prepareStatement(CHECK_TRANSACTION_ID);
				ps.setString(1, transactionid);
				rs = ps.executeQuery();
				while(rs.next()) 
				{
					captureId=rs.getString("LEAD_CAPTURED_DATA_ID");
				}
				
			} catch (Exception e) {
				throw new DAOException("Exception occured while getting lc_is_done status :  "+ e.getMessage(),e);
			} finally
			{
				try {
					DBConnection.releaseResources(con, ps, rs);
				} catch (Exception e) {				
					throw new DAOException(e.getMessage(), e);
				}
			}
			return captureId;
			}
		

		public String isValidTxnsId(String transactionid) throws DAOException {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String captureId="";
			try {
				con = DBConnection.getDBConnection();
				ps = con.prepareStatement(CHECK_TRANS_ID);
				ps.setString(1, transactionid);
				rs = ps.executeQuery();
				while(rs.next()) 
				{
					captureId=rs.getString("LEAD_ID");
				}
				
			} catch (Exception e) {
				throw new DAOException("Exception occured while getting lc_is_done status :  "+ e.getMessage(),e);
			} finally
			{
				try {
					DBConnection.releaseResources(con, ps, rs);
				} catch (Exception e) {				
					throw new DAOException(e.getMessage(), e);
				}
			}
			return captureId;
			}
		
		public PreparedStatement setUpdateLeadDetails(PreparedStatement prep ,CaptureLeadDetailsDTO leadDtoObj, String leadId) {
			
			try {
				//logger.info("leadId********"+leadId+"leadDtoObj.get*******"+leadDtoObj.getLeadCaptureId());
				prep.setString(1, leadId);
				prep.setString(2, leadDtoObj.getIdentityProofID()); 
				prep.setString(3, leadDtoObj.getRelationName());
				prep.setString(4, leadDtoObj.getPayment_date()); 
				prep.setString(5, leadDtoObj.getUpc());
				prep.setString(6, leadDtoObj.getUpc_gen_date());
				prep.setString(7, leadDtoObj.getPreviousOerator()); 
				prep.setString(8, leadDtoObj.getPreviousCircle());
				prep.setString(9, leadDtoObj.getExistingPart());
				prep.setString(10,leadDtoObj.getMnpStatus()); 
				prep.setString(11,leadDtoObj.getDocumentCollectedFlag());
				prep.setString(12,leadDtoObj.getPlanType());
				prep.setString(13,leadDtoObj.getRentalPlan()); 
			
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			return prep;
		}
}
			
		

