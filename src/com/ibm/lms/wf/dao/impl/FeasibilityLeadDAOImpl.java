package com.ibm.lms.wf.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.lms.common.Constants;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.common.LMSStatusCodes;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.LeadStatusDTO;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.sms.SendSMSXML;
import com.ibm.lms.wf.dao.FeasibilityLeadDAO;
import com.ibm.lms.wf.dto.BulkFeasibilityDTO;
import com.ibm.lms.wf.dto.Constant;
import com.ibm.lms.wf.dto.Leads;

public class FeasibilityLeadDAOImpl implements FeasibilityLeadDAO {

	private static Logger logger =
		Logger.getLogger(FeasibilityLeadDAOImpl.class);
//<changed by sudhanshu	
	public static final String SQL_SELECT_FEASIBILITY_LEADS ="SELECT LEAD.LEAD_ID , LEAD.PRODUCT_ID, LEAD.LEAD_ASSIGNED_PRIMARY_USER,LEAD_ASSIGNMENT_TIME,LEAD.EXPECTED_CLOSURE_DATE,PRO.PRODUCT_NAME FROM LEAD_TRANSACTION LEAD,PRODUCT_MSTR PRO,LEAD_DATA DATA WHERE LEAD.LEAD_STATUS_ID = "+LMSStatusCodes.FEASIBILITY+" AND LEAD.LEAD_ID=DATA.LEAD_ID AND DATA.LEAD_STATUS_ID = LEAD.LEAD_STATUS_ID AND PRO.PRODUCT_ID = DATA.PRODUCT_ID " +
			" and LEAD.TRANSACTION_TIME = (select TR.TRANSACTION_TIME FROM LEAD_TRANSACTION tr where  tr.LEAD_ID = lead.LEAD_ID AND tr.LEAD_STATUS_ID = DATA.LEAD_STATUS_ID AND TR.LEAD_ASSIGNED_PRIMARY_USER = ? order by TR.TRANSACTION_TIME desc fetch first row only )  AND " ;
//changed by sudhanshu	>	
	public static final String SQL_UPDATE_LEAD_STATUS = "UPDATE LEAD_DATA ";
	
	public static final String SQL_SELECT_RSU = "select RSU_CODE from RSU_MSTR where RSU_CODE = ? and STATUS = 'A' with ur";
	
	public static final String SQL_INSERT_LEAD_TRANSACTION = "INSERT INTO LEAD_TRANSACTION (LEAD_ID, PRODUCT_ID, LEAD_ASSIGNMENT_TIME, LEAD_CLOSURE_TIME, LEAD_STATUS_ID, LEAD_ASSIGNED_PRIMARY_USER, REMARKS, TRANSACTION_TIME, EXPECTED_CLOSURE_DATE, PRIMARY_AUTH, SUB_STATUS_ID, UPDATED_BY, UD_ID ,LEAD_PRODUCT_ID,CLIENT_IP)  SELECT LEAD_ID,PRODUCT_ID,LEAD_ASSIGNMENT_TIME,CURRENT TIMESTAMP,? ";

/*	public static final String SQL_SELECT_FEASIBLE_LEAD_EXCEL = "SELECT PRO.PRODUCT_NAME,LT.LEAD_ID,LP.CUSTOMER_NAME,(select CIRCLE_NAME from CIRCLE_MSTR where circle_id = LP.circle_ID) CIRCLE_NAME, LP.ADDRESS1,LP.PROSPECT_MOBILE_NUMBER,LT.LEAD_ASSIGNMENT_TIME," +
			"LT.EXPECTED_CLOSURE_DATE,(SELECT CITY_NAME FROM CITY_MSTR WHERE CITY_CODE = LP.CITY) CITY_NAME,lp.PINCODE  from LEAD_DATA LD,LEAD_PROSPECT_CUSTOMER LP,LEAD_TRANSACTION LT,PRODUCT_MSTR PRO WHERE" +
			" LD.PROSPECT_ID = LP.PROSPECT_ID and LD.LEAD_STATUS_ID = "+LMSStatusCodes.FEASIBILITY+" AND LT.LEAD_ID = LD.LEAD_ID AND LT.LEAD_STATUS_ID = LD.LEAD_STATUS_ID AND LD.PRODUCT_ID = PRO.PRODUCT_ID AND LT.LEAD_ASSIGNED_PRIMARY_USER = ? ";
*/
// changed by sudhanshu
	/*public static final String SQL_SELECT_FEASIBLE_LEAD_EXCEL = " SELECT PRO.PRODUCT_NAME,LT.LEAD_ID,LP.CUSTOMER_NAME,CM.CIRCLE_NAME, LPD.ADDRESS1,LPD.ADDRESS2,  LP.PROSPECT_MOBILE_NUMBER,LT.LEAD_ASSIGNMENT_TIME,LT.EXPECTED_CLOSURE_DATE, " +
			" 	(SELECT CITY_NAME FROM CITY_MSTR WHERE CITY_CODE = LPD.CITY_CODE) CITY_NAME, lpd.PINCODE ,  (SELECT ZONE_NAME FROM ZONE_MSTR where ZONE_CODE=lpd.ZONE_CODE) as ZONE_NAME , (SELECT CITY_ZONE_NAME FROM CITY_ZONE_MSTR where CITY_ZONE_CODE=lpd.CITY_ZONE_CODE) as CITY_ZONE_NAME  " +
//			" ,LD.RENTAL, LD.ONLINE_CAF_NO,LD.ALLOCATED_NO 	" +//changed by sudhanshu
			" ,(SELECT LEAD_STATUS FROM LEAD_STATUS WHERE LEAD_STATUS_ID =LD.LEAD_STATUS_ID) AS LEAD_STATUS,(SELECT SUB_STATUS FROM LEAD_SUB_STATUS WHERE LEAD_STATUS_ID =LD.LEAD_STATUS_ID AND SUB_STATUS_ID =LD.LEAD_SUB_STATUS_ID) AS SUB_STATUS ,(SELECT SOURCE_NAME FROM SOURCE_MSTR WHERE SOURCE_ID = LD.SOURCE  AND STATUS ='A') AS SOURCE_NAME,(SELECT SUBSOURCE_NAME FROM SUB_SOURCE_MSTR WHERE char(SUBSOURCE_ID) = LD.SUB_SOURCE AND  STATUS ='A') SUBSOURCE_NAME ,LD.UTM_CAMPAIGN , (SELECT PRODUCT_NAME FROM PRODUCT_MSTR where char(PRODUCT_ID) = LD.REQUEST_TYPE)AS REQUEST_TYPE from    LEAD_DATA LD,LEAD_PROSPECT_CUSTOMER 	LP, " +
			"   LEAD_TRANSACTION LT,PRODUCT_MSTR PRO,LEAD_PROSPECT_DETAIL lpd, CIRCLE_MSTR   cm WHERE  LD.LEAD_STATUS_ID = "+LMSStatusCodes.FEASIBILITY+" AND LD.PROSPECT_ID = LP.PROSPECT_ID " +
			" and LD.LEAD_PROSPECT_ID =  LPD.LEAD_PROSPECT_ID and cm.CIRCLE_ID = LPD.CIRCLE_ID and CM.LOB_ID = LPD.PRODUCT_LOB_ID and LD.LEAD_STATUS_ID = 310 AND LT.LEAD_ID = LD.LEAD_ID AND LT.LEAD_STATUS_ID = LD.LEAD_STATUS_ID  " +
			" AND LD.PRODUCT_ID = PRO.PRODUCT_ID	AND LT.LEAD_ASSIGNED_PRIMARY_USER = ? AND  LT.TRANSACTION_TIME = (SELECT TR.TRANSACTION_TIME FROM LEAD_TRANSACTION TR WHERE TR.LEAD_ID = LD.LEAD_ID AND TR.LEAD_STATUS_ID = LD.LEAD_STATUS_ID AND TR.LEAD_ASSIGNED_PRIMARY_USER = ? order by TR.TRANSACTION_TIME desc fetch first row only )";
*/
	//changes by Bhaksar
	
	//query changed for lobwise sub status by Nancy
	public static final String SQL_SELECT_FEASIBLE_LEAD_EXCEL = " SELECT PRO.PRODUCT_NAME,LT.LEAD_ID,LP.CUSTOMER_NAME,CM.CIRCLE_NAME, LPD.ADDRESS1,LPD.ADDRESS2,  LP.PROSPECT_MOBILE_NUMBER,LT.LEAD_ASSIGNMENT_TIME,LT.EXPECTED_CLOSURE_DATE, " +
			" 	(SELECT CITY_NAME FROM CITY_MSTR WHERE CITY_CODE = LPD.CITY_CODE) CITY_NAME, lpd.PINCODE ,lpd.RSU_CODE,  (SELECT ZONE_NAME FROM ZONE_MSTR where ZONE_CODE=lpd.ZONE_CODE) as ZONE_NAME , (SELECT CITY_ZONE_NAME FROM CITY_ZONE_MSTR where CITY_ZONE_CODE=lpd.CITY_ZONE_CODE) as CITY_ZONE_NAME  " +
//			" ,LD.RENTAL, LD.ONLINE_CAF_NO,LD.ALLOCATED_NO 	" +//changed by sudhanshu
			" ,(SELECT LEAD_STATUS FROM LEAD_STATUS WHERE LEAD_STATUS_ID =LD.LEAD_STATUS_ID) AS LEAD_STATUS,(SELECT SUB_STATUS FROM LEAD_SUB_STATUS WHERE LEAD_STATUS_ID =LD.LEAD_STATUS_ID AND SUB_STATUS_ID =LD.LEAD_SUB_STATUS_ID AND PRODUCT_LOB_ID=(SELECT PRODUCT_LOB_ID FROM PRODUCT_MSTR WHERE PRODUCT_ID=LD.PRODUCT_ID)) AS SUB_STATUS ,(SELECT SOURCE_NAME FROM SOURCE_MSTR WHERE SOURCE_ID = LD.SOURCE  AND STATUS ='A') AS SOURCE_NAME,(SELECT SUBSOURCE_NAME FROM SUB_SOURCE_MSTR WHERE char(SUBSOURCE_ID) = LD.SUB_SOURCE AND  STATUS ='A') SUBSOURCE_NAME ,LD.UTM_CAMPAIGN , (SELECT PRODUCT_NAME FROM PRODUCT_MSTR where char(PRODUCT_ID) = LD.REQUEST_TYPE)AS REQUEST_TYPE from    LEAD_DATA LD,LEAD_PROSPECT_CUSTOMER 	LP, " +
			"   LEAD_TRANSACTION LT,PRODUCT_MSTR PRO,LEAD_PROSPECT_DETAIL lpd, CIRCLE_MSTR   cm WHERE  LD.LEAD_STATUS_ID = "+LMSStatusCodes.FEASIBILITY+" AND LD.PROSPECT_ID = LP.PROSPECT_ID " +
			" and LD.LEAD_PROSPECT_ID =  LPD.LEAD_PROSPECT_ID and cm.CIRCLE_ID = LPD.CIRCLE_ID and CM.LOB_ID = LPD.PRODUCT_LOB_ID and LD.LEAD_STATUS_ID = 310 AND LT.LEAD_ID = LD.LEAD_ID AND LT.LEAD_STATUS_ID = LD.LEAD_STATUS_ID  " +
			" AND LD.PRODUCT_ID = PRO.PRODUCT_ID	AND LT.LEAD_ASSIGNED_PRIMARY_USER = ? AND  LT.TRANSACTION_TIME = (SELECT TR.TRANSACTION_TIME FROM LEAD_TRANSACTION TR WHERE TR.LEAD_ID = LD.LEAD_ID AND TR.LEAD_STATUS_ID = LD.LEAD_STATUS_ID AND TR.LEAD_ASSIGNED_PRIMARY_USER = ? order by TR.TRANSACTION_TIME desc fetch first row only )";
	
	
	// changed by sudhanshu 	
	//changed by Nancy
	public static final String SQL_SELECT_SUB_STATUS = "SELECT * FROM LEAD_SUB_STATUS WHERE LEAD_STATUS_ID = ? AND PRODUCT_LOB_ID=? AND SUB_STATUS_ID NOT IN (370) ORDER BY SUB_STATUS ";
	//changes by Amarjeet
	public static final String SQL_SELECT_LEAD_STATUS = "SELECT LEAD_STATUS_ID , LEAD_STATUS  FROM LEAD_STATUS WHERE LEAD_STATUS in ( '"+Constants.FEASIBILITY +"' ,'"+Constants.WIRED+"' ,'"+Constants.UNWIRED+"' ,'"+Constants.INFO_INADEQUATE+"' ) with ur ";
	public static final String SQL_UPDATE_PROSPECT_CUSTOMER = "UPDATE LEAD_PROSPECT_CUSTOMER PC SET PC.RSU_ID = ? WHERE PC.PROSPECT_ID = (SELECT PROSPECT_ID FROM LEAD_DATA WHERE LEAD_ID = ?)"; 
	
	
//	changed by sudhanshu 
	//public static final String SQL_UPDATE_PROSPECT_DETAIL = "UPDATE LEAD_PROSPECT_DETAIL PD SET PD.RSU_CODE = ? WHERE PD.PROSPECT_ID = (SELECT PROSPECT_ID FROM LEAD_DATA WHERE LEAD_ID = ?)";
//changed by sudhanshu 
	
	/* Modified By Parnika */
	public static final String SQL_UPDATE_PROSPECT_DETAIL = "UPDATE LEAD_PROSPECT_DETAIL PD SET PD.RSU_CODE = ? WHERE PD.LEAD_PROSPECT_ID = (SELECT LEAD_PROSPECT_ID FROM LEAD_DATA WHERE LEAD_ID = ?)";
	/* End of changes by Parnika */
	
	public static final String SQL_SELECT_NEW_FEASIBILITY_LEADS ="SELECT LEAD.LEAD_ID , LEAD.PRODUCT_ID, LEAD.LEAD_ASSIGNED_PRIMARY_USER,LEAD_ASSIGNMENT_TIME,LEAD.EXPECTED_CLOSURE_DATE,PRO.PRODUCT_NAME"
								+" FROM LEAD_TRANSACTION LEAD , PRODUCT_MSTR PRO, LEAD_DATA DATA ,LEAD_PROSPECT_DETAIL LD WHERE LD.PRODUCT_LOB_ID = ? AND LEAD.LEAD_STATUS_ID = ? AND LEAD.SUB_STATUS_ID != "+LMSStatusCodes.DIALER_SECOND_CALL+" AND LEAD.LEAD_ID=DATA.LEAD_ID "
								+" AND DATA.LEAD_STATUS_ID = LEAD.LEAD_STATUS_ID AND PRO.PRODUCT_ID = DATA.PRODUCT_ID AND LD.CIRCLE_ID = ? AND LD.LEAD_PROSPECT_ID = DATA.LEAD_PROSPECT_ID AND LEAD.TRANSACTION_TIME =(SELECT max(TRANSACTION_TIME) FROM LEAD_TRANSACTION TR WHERE  TR.LEAD_ID = lead.LEAD_ID AND TR.LEAD_STATUS_ID = DATA.LEAD_STATUS_ID AND TR.SUB_STATUS_ID != "+LMSStatusCodes.DIALER_SECOND_CALL+")"
								+" AND (SELECT Date(TRANSACTION_TIME) FROM LEAD_TRANSACTION TR WHERE  TR.LEAD_ID = lead.LEAD_ID AND TR.LEAD_STATUS_ID = DATA.LEAD_STATUS_ID AND TR.SUB_STATUS_ID != "+LMSStatusCodes.DIALER_SECOND_CALL+" order by TRANSACTION_TIME desc fetch first row only) BETWEEN  ? AND ?  WITH UR ";

	
	private String SELECT_SMS_TEMPLATE = "SELECT MESSAGE_TEMPLATE FROM ALERT_MSTR  WHERE ALERT_ID = ?  AND ACTIVE = 'A' WITH UR";
	private final String INSERT_SMS_TRANSACTIONS = " INSERT INTO CUSTOMER_SEND_SMS_DETAILS( MOBILE_NUMBER, MESSAGE, STATUS, SENT_ON, CREATED_ON, RESPONSE_MSG) VALUES( ?, ?, ?, CURRENT TIMESTAMP, CURRENT TIMESTAMP, ?)";
	
	public ArrayList listFeasibilityLeads(String loginID, String startDate,String endDate) throws LMSException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Leads dto;
		int counter = 1;
		//String sql = "";
		SimpleDateFormat otdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		Date start,end;
		int recordslimitdays = Integer.parseInt(PropertyReader.getAppValue("records.limit.days"));
		try {
			
			if(startDate !=null && !"".equalsIgnoreCase(startDate))
			{
				start = (Date) dateFormat.parse(startDate);
				startDate = dateFormat1.format(start);
			}
			if(endDate !=null && !"".equalsIgnoreCase(endDate))
			{
				end = (Date) dateFormat.parse(endDate);
				endDate = dateFormat1.format(end);
			}	
			StringBuffer query=new StringBuffer(SQL_SELECT_FEASIBILITY_LEADS);
			logger.info("Inside DAO listFeasibilityLeads");
			query.append(" (LEAD.LEAD_ASSIGNED_PRIMARY_USER = ? ) ");
			if(startDate !=null && !"".equalsIgnoreCase(startDate))
				query.append(" AND LEAD_ASSIGNMENT_TIME >= ? ");
			else
				query.append(" AND LEAD_ASSIGNMENT_TIME >= current timestamp - "+recordslimitdays +" days ");
			if(endDate !=null && !"".equalsIgnoreCase(endDate))
				query.append(" AND LEAD_ASSIGNMENT_TIME - 1 day <= ? ");
			ArrayList assignedList = new ArrayList();
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query.append(" with ur ").toString());
				ps.setString(counter++, loginID);
				ps.setString(counter++, loginID);
				if(startDate !=null && !"".equalsIgnoreCase(startDate))
					ps.setDate(counter++, Utility.getSqlDateFromString(startDate, "MM/dd/yyyy"));
				if(endDate !=null && !"".equalsIgnoreCase(endDate))
					ps.setDate(counter++, Utility.getSqlDateFromString(endDate, "MM/dd/yyyy"));
			rs = ps.executeQuery();
			//logger.info("SQL Stmt for View Feasibility Leads:" + query);
			//logger.info("SQL Stmt Vew Leads :" + query);
			while (rs.next()) {
				dto = new Leads();
				dto.setLeadID(rs.getString("LEAD_ID"));
				dto.setPrimaryUser(rs.getString("LEAD_ASSIGNED_PRIMARY_USER"));
				dto.setProduct(rs.getString("PRODUCT_NAME"));
				dto.setStartTime(otdf.format(rs.getTimestamp("LEAD_ASSIGNMENT_TIME")));
				dto.setEndTime(otdf.format(rs.getTimestamp("EXPECTED_CLOSURE_DATE")));
				
				assignedList.add(dto);

			}
			logger.info("circle user success");
			return assignedList;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while Viewing assigned Leads."
					+ "SQL Exception Message: "
					+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				" Exception occured while Viewing assigned Leads."
					+ " Exception Message: "
					+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error(
					"DAO Exception occured while Viewing assigned Leads."
						+ "DAO Exception Message: "
						+ e.getMessage());
				throw new LMSException("Exception: " + e.getMessage(), e);
			}
		}

	}


	public Boolean qualifyTheFeasibleLead(ArrayList masterList)
			throws LMSException {
		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		int counter = 1;
		int count = 0;
		ResultSet rs = null;
		boolean flag = false;
		Leads lead = null;
		MasterService mstrService = new MasterServiceImpl();
        ResultSet rsTemp=null;
        PreparedStatement ps =null;
        PreparedStatement ps4 =null;

		logger.info("Inside DAO  ::  qualifyTheFeasibleLead");
		try {
			con = DBConnection.getDBConnection();
			//con.setAutoCommit(false);
			for(int i = 0;i<masterList.size();i++)
			{
				StringBuffer query1 = new StringBuffer(SQL_UPDATE_PROSPECT_DETAIL); //changed by sudhanshu
				StringBuffer query=new StringBuffer(SQL_UPDATE_LEAD_STATUS);
				StringBuffer dmlStatement = new StringBuffer(SQL_INSERT_LEAD_TRANSACTION);
				
				counter = 1;
				count = 0;
				//lead = new Leads();
				lead = (Leads)masterList.get(i);
				ps1 = con.prepareStatement(query1.toString());
				ps1.setString(counter++, lead.getRsuCode());
				ps1.setString(counter++, lead.getLeadID());
				count = ps1.executeUpdate();
				if(count == 1)
				{
					counter = 1;
					count = 0;
					
//					logger.info("Inside DAO");
					
					query.append(" SET  LEAD_STATUS_ID = ?, LEAD_SUB_STATUS_ID = ? ,UPDATED_DT= current timestamp WHERE LEAD_ID = ? ");
					ps2 = con.prepareStatement(query.append(" with ur ").toString());
						ps2.setInt(counter++, Integer.parseInt(lead.getStatus()));
						
						if(LMSStatusCodes.WIRED == Integer.parseInt(lead.getStatus()) || LMSStatusCodes.UNWIRED == Integer.parseInt(lead.getStatus()))
							ps2.setInt(counter++, Integer.parseInt(lead.getSubStatus().toString()));
						else
							ps2.setInt(counter++, Integer.parseInt(lead.getStatus()));
						
						ps2.setString(counter++, lead.getLeadID());
						count = ps2.executeUpdate();
					
					
					
//					logger.info("SQL Stmt :" + query);
//					logger.info("circle user success");
					if(count == 1)
					{
						count = 0;
						counter = 1;
						int status = Integer.parseInt(lead.getStatus());
						
						dmlStatement.append(" ,LEAD_ASSIGNED_PRIMARY_USER,?,CURRENT TIMESTAMP,EXPECTED_CLOSURE_DATE,PRIMARY_AUTH,?,?,? ,LEAD_PRODUCT_ID ,(SELECT CLIENT_IP FROM KM_LOGIN_DATA WHERE USER_LOGIN_ID = ?  ORDER BY LOGIN_TIME DESC  FETCH FIRST ROW ONLY ) FROM LEAD_TRANSACTION ");//changed by sudhanshu
						dmlStatement.append(" WHERE LEAD_ID = ? AND SUB_STATUS_ID <> 210 AND LEAD_STATUS_ID = ? ORDER BY TRANSACTION_TIME DESC FETCH FIRST ROW ONLY ");
					
						ps3 = con.prepareStatement(dmlStatement.toString());
						ps3.setInt(counter++, Integer.parseInt(lead.getStatus()));
						ps3.setString(counter++, lead.getRemarks());
						if(LMSStatusCodes.WIRED == status || LMSStatusCodes.UNWIRED == status)
						ps3.setInt(counter++, Integer.parseInt(lead.getSubStatus().toString()));
						else
							ps3.setInt(counter++, status);
						ps3.setString(counter++, lead.getUpdatedBy());
						ps3.setString(counter++, lead.getUdId());
						ps3.setString(counter++, lead.getUpdatedBy());
						ps3.setString(counter++, lead.getLeadID());
						ps3.setInt(counter++, LMSStatusCodes.FEASIBILITY);
						count = ps3.executeUpdate();
						if(count == 1)
							flag = true;
						else 
							flag = false;
					}
				}
				//con.commit();
				
				String customerNo   = lead.getProspect_mobile_number();
				int status  =Integer.parseInt(lead.getStatus());
				String circleFlag = mstrService.getParameterName(PropertyReader.getAppValue("feasibilitylead.circle.smsFlag"));
				if(flag && customerNo != null && customerNo.trim().length() == 10 && (LMSStatusCodes.WIRED == status || LMSStatusCodes.UNWIRED == status) && circleFlag !=null && circleFlag.contains(String.valueOf(lead.getCircleId()))){
					try{
						String smsFlag = mstrService.getParameterName(PropertyReader.getAppValue("feasibilitylead.smsFlag"));
						if(smsFlag.equalsIgnoreCase("Y")){
						String meassage = null;
						ps = con.prepareStatement(SELECT_SMS_TEMPLATE);
						if(LMSStatusCodes.WIRED == status) {
						ps.setString(1, PropertyReader.getAppValue("lms.feasibilityWiredleadSMS.template"));
						}else if(LMSStatusCodes.UNWIRED == status) {
							ps.setString(1, PropertyReader.getAppValue("lms.feasibilityUnwiredleadSMS.template"));	
						}
						rsTemp = ps.executeQuery();
						if(rsTemp.next()){
							 meassage = rsTemp.getString("MESSAGE_TEMPLATE");
							 
							 new SendSMSXML().sendSms(customerNo+"",meassage);
								ps4=con.prepareStatement(INSERT_SMS_TRANSACTIONS);
								ps4.setString(1, customerNo+"");
								ps4.setString(2, meassage);
								ps4.setInt(3, 1);
								ps4.setString(4, "");
								ps4.executeUpdate();
						}
						
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				lead = null;
			}
			return flag;
			
		} catch (SQLException e) {
			/*try{
				con.rollback();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}*/
			e.printStackTrace();
			logger.error("SQL Exception occured while Feasibility Check."+ "SQL Exception Message: "+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			try{
				con.rollback();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			e.printStackTrace();
			logger.error(" Exception occured while Feasibility Check."	+ " Exception Message: "+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps1, rs);
				DBConnection.releaseResources(con, ps2, rs);
				DBConnection.releaseResources(con, ps3, rs);
			} catch (Exception e) {
				logger.error("DAO Exception occured while Feasibility Check."+ "DAO Exception Message: "+ e.getMessage());
				throw new LMSException("Exception: " + e.getMessage(), e);
			}
		}
		}

	
	public boolean isValidRSU(String rsuCode) throws LMSException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean isValidRSU = false; 		
		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_SELECT_RSU);
			ps.setString(1, rsuCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				isValidRSU = true;
			}
			//logger.info("isValidRSU : "+isValidRSU);
			
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(" Exception occured while validating RSU Code : "+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error("Exception occured while releasing resources, Exception Message: "+ e.getMessage());
				throw new LMSException("Exception: " + e.getMessage(), e);
			}
		}
		return isValidRSU;
	}

	
	
	
	public ArrayList<BulkFeasibilityDTO> listFeasibilityLeadsExcel(	String loginID,String startDate,String endDate) throws LMSException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		BulkFeasibilityDTO dto;
		int counter = 1;
		int recordslimitdays = Integer.parseInt(PropertyReader.getAppValue("records.limit.days"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		
		try {
			Date start,end;
			if(startDate !=null && !"".equalsIgnoreCase(startDate))
			{
				start = (Date) dateFormat.parse(startDate);
				startDate = dateFormat1.format(start);
			}
			if(endDate !=null && !"".equalsIgnoreCase(endDate))
			{
				end = (Date) dateFormat.parse(endDate);
				endDate = dateFormat1.format(end);
			}
			StringBuffer query=new StringBuffer(SQL_SELECT_FEASIBLE_LEAD_EXCEL);
			logger.info("Inside DAO");
			ArrayList<BulkFeasibilityDTO> masterList = new ArrayList<BulkFeasibilityDTO>();
			con = DBConnection.getDBConnection();
			if(startDate !=null && !"".equalsIgnoreCase(startDate))
				query.append(" AND LEAD_ASSIGNMENT_TIME >= ? ");
			else
				query.append(" AND LEAD_ASSIGNMENT_TIME >= current timestamp - "+recordslimitdays +" days ");
			if(endDate !=null && !"".equalsIgnoreCase(endDate))
				query.append(" AND LEAD_ASSIGNMENT_TIME - 1 day <= ? ");
			
			ps = con.prepareStatement(query.append(" with ur ").toString());
				ps.setString(counter++, loginID);
				ps.setString(counter++, loginID);
				if(startDate !=null && !"".equalsIgnoreCase(startDate)){
					ps.setDate(counter++, Utility.getSqlDateFromString(startDate, "MM/dd/yyyy"));
					
				}
					
				if(endDate !=null && !"".equalsIgnoreCase(endDate)){
					ps.setDate(counter++, Utility.getSqlDateFromString(endDate, "MM/dd/yyyy"));	
					
				}
								
			rs = ps.executeQuery();
			logger.info("SQL Stmt for View Feasibility Leads Excel:" + query);
			//logger.info("SQL Stmt View Excel:" + query);
			while (rs.next()) {
				dto = new BulkFeasibilityDTO();
				dto.setLeadID(rs.getString("LEAD_ID"));
				dto.setProductName(rs.getString("PRODUCT_NAME"));
				dto.setLeadAssignmentTime(rs.getDate("LEAD_ASSIGNMENT_TIME"));
				dto.setCustomerName(rs.getString("CUSTOMER_NAME"));
				dto.setCircleName(rs.getString("CIRCLE_NAME"));
				dto.setCityName(rs.getString("CITY_NAME"));
				dto.setPinCode(rs.getString("PINCODE"));
				dto.setAddress(rs.getString("ADDRESS1"));
				dto.setAddress2(rs.getString("ADDRESS2"));
				dto.setMobileNumber(rs.getString("PROSPECT_MOBILE_NUMBER"));
				dto.setLeadClosureTime(rs.getDate("EXPECTED_CLOSURE_DATE"));
				dto.setZoneName(rs.getString("ZONE_NAME"));
				dto.setCityZoneName(rs.getString("CITY_ZONE_NAME"));
				dto.setSource(rs.getString("SOURCE_NAME"));
				dto.setSubSource(rs.getString("SUBSOURCE_NAME"));
				dto.setCompaign(rs.getString("UTM_CAMPAIGN"));
				dto.setRequestType(rs.getString("REQUEST_TYPE"));
				dto.setSubStatus(rs.getString("SUB_STATUS"));
				dto.setStatus(rs.getString("LEAD_STATUS"));
				//Added By BHaskar
				dto.setRsuCode(rs.getString("RSU_CODE"));
				masterList.add(dto);

			}
			logger.info("circle user success");
			return masterList;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while Viewing feasibility Leads in Excel."+ "SQL Exception Message: "+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" Exception occured while Viewing feasibility Leads in Excel."	+ " Exception Message: "+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error("DAO Exception occured while Viewing feasibility Leads in Excel."+ "DAO Exception Message: "+ e.getMessage());
				throw new LMSException("Exception: " + e.getMessage(), e);
			}
		}
	}

	//function changed by Nancy Agrawal
	public ArrayList<Constant> getSubStatusList(int statusID,int lobId)throws LMSException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Constant dto;
		int counter = 1;
		try {
			MasterService masterService =new MasterServiceImpl();
			
			StringBuffer query=new StringBuffer(SQL_SELECT_SUB_STATUS);
			logger.info("Inside DAO");
			ArrayList<Constant> ActionList = new ArrayList<Constant>();
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query.append(" with ur ").toString());
				ps.setInt(counter++, statusID);
				ps.setInt(counter++,lobId);
			rs = ps.executeQuery();
			//logger.info("SQL Stmt :" + query);
			//logger.info(query);
			while (rs.next()) {
				dto = new Constant();
				dto.setKeyValue(rs.getString("SUB_STATUS"));
				dto.setLeadSubStatus(rs.getString("SUB_STATUS"));
				dto.setID(Long.parseLong(String.valueOf(rs.getInt("SUB_STATUS_ID"))));
				dto.setSubStatusId(Integer.parseInt(String.valueOf(rs.getInt("SUB_STATUS_ID"))));
				ActionList.add(dto);

			}
			logger.info("sub status success");
			return ActionList;
		} catch (SQLException e) {
			logger.error("SQL Exception occured while Getting substatus of Leads."	+ "SQL Exception Message: "	+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error(	" Exception occured while Getting substatus of Leads."+ " Exception Message: "+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error("DAO Exception occured while Getting substatus of Leads."	+ "DAO Exception Message: "	+ e.getMessage());
				throw new LMSException("Exception: " + e.getMessage(), e);
			}
		}

	}
	public ArrayList<LeadStatusDTO> getStatusList()	throws LMSException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		LeadStatusDTO dto;
		int counter = 1;
		try {
			StringBuffer query=new StringBuffer(SQL_SELECT_LEAD_STATUS);
			logger.info("Inside DAO");
			ArrayList<LeadStatusDTO> ActionList = new ArrayList<LeadStatusDTO>();
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query.toString());			
			rs = ps.executeQuery();
			//logger.info("SQL Stmt :" + query);
			
			while (rs.next()) {
				dto = new LeadStatusDTO();
				dto.setLeadStatusId(rs.getInt("LEAD_STATUS_ID"));
				dto.setLeadStatusName(rs.getString("LEAD_STATUS"));
				ActionList.add(dto);

			}
			logger.info("sub status success");
			return ActionList;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while Getting getStatusList of Leads."	+ "SQL Exception Message: "	+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(	" Exception occured while Getting getStatusList of Leads."+ " Exception Message: "+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error("DAO Exception occured while Getting getStatusList of Leads."	+ "DAO Exception Message: "	+ e.getMessage());
				throw new LMSException("Exception: " + e.getMessage(), e);
			}
		}

	}
	
	
	 public ArrayList<Leads> getNewFeasibilityLeads(int circleId,int statusId , String startDate,String endDate) throws LMSException {	
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Leads dto;
		int lobId =2;
		int counter = 1;
		//String sql = "";
		SimpleDateFormat otdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		Date start,end;
		int recordslimitdays = Integer.parseInt(PropertyReader.getAppValue("records.limit.days"));
		try {
			
			if(startDate !=null && !"".equalsIgnoreCase(startDate))
			{
				start = (Date) dateFormat.parse(startDate);
				startDate = dateFormat1.format(start);
			}
			if(endDate !=null && !"".equalsIgnoreCase(endDate))
			{
				end = (Date) dateFormat.parse(endDate);
				endDate = dateFormat1.format(end);
			}	
			StringBuffer query=new StringBuffer(SQL_SELECT_NEW_FEASIBILITY_LEADS);
			logger.info("Inside DAO");	
			ArrayList<Leads> assignedList = new ArrayList<Leads>();
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(query.toString());
			if(PropertyReader.getAppValue("lms.telemedia.productLobId")!=null)
			lobId = Integer.parseInt(PropertyReader.getAppValue("lms.telemedia.productLobId"));			
				ps.setInt(counter++, lobId);
				ps.setInt(counter++, statusId);
				ps.setInt(counter++, circleId);
				if(startDate !=null && !"".equalsIgnoreCase(startDate))
					
					ps.setDate(counter++, Utility.getSqlDateFromString(startDate, "MM/dd/yyyy"));
				if(endDate !=null && !"".equalsIgnoreCase(endDate))
					
					ps.setDate(counter++, Utility.getSqlDateFromString(endDate, "MM/dd/yyyy"));
			rs = ps.executeQuery();
			logger.info("SQL Stmt :" + query);
			//logger.info(query);
			while (rs.next()) {
				dto = new Leads();
				dto.setLeadID(rs.getString("LEAD_ID"));
				dto.setPrimaryUser(rs.getString("LEAD_ASSIGNED_PRIMARY_USER"));
				dto.setProduct(rs.getString("PRODUCT_NAME"));
				dto.setStartTime(otdf.format(rs.getTimestamp("LEAD_ASSIGNMENT_TIME")));
				dto.setEndTime(otdf.format(rs.getTimestamp("EXPECTED_CLOSURE_DATE")));
				
				assignedList.add(dto);

			}
			logger.info("circle user success");
			return assignedList;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while Viewing assigned Leads."
					+ "SQL Exception Message: "
					+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				" Exception occured while Viewing assigned Leads."
					+ " Exception Message: "
					+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				DBConnection.releaseResources(con, ps, rs);
			} catch (Exception e) {
				logger.error(
					"DAO Exception occured while Viewing assigned Leads."
						+ "DAO Exception Message: "
						+ e.getMessage());
				throw new LMSException("Exception: " + e.getMessage(), e);
			}
		}

	}
}
