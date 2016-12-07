package com.ibm.lms.engine.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.ibm.lms.common.ColumnKeys;
import com.ibm.lms.common.Constants;
import com.ibm.lms.dao.DBConnection;
import com.ibm.lms.dto.CaptureLeadDetailsDTO;
import com.ibm.lms.dto.webservice.UpdateLeadResponseMsg;
import com.ibm.lms.dto.webservice.UpdatedLeadDataFirstVersDTO;
import com.ibm.lms.engine.dao.UpdateLeadDataDAONew;
import com.ibm.lms.engine.exception.DAOException;
import com.ibm.lms.engine.util.ServerPropertyReader;
import com.ibm.lms.engine.util.StringUtils;
import com.ibm.lms.exception.LMSException;



public class  UpdateLeadDataDAOImplNew implements UpdateLeadDataDAONew{

	
	private static final Logger logger = Logger.getLogger(UpdateLeadDataDAOImplNew.class);
	
	private static final String UPDATE_LEAD_PROSPECT_DETAILS =" UPDATE LEAD_PROSPECT_DETAIL SET PYT_AMT= ? ,UPDATED_BY =?,UPDATED_DT =current timestamp WHERE LEAD_ID = ? WITH UR";
	private  StringBuffer UPDATE_LEAD_DATA  = new StringBuffer("");
	private static final String UPDATE_LEAD_DETAILS  = "UPDATE LEAD_DETAILS SET PAYMENT_TYPE=?,PRODUCT_BOUGHT =?,COMPETITOR_CHOSEN=?,SENTBY=? ,PAYMENT_COLLECTED =? WHERE LEAD_ID = ? WITH UR";
	private static final String VALIDAT_LEAD_SUBSTATUS = "SELECT count(1) FROM LEAD_SUB_STATUS WHERE  char(SUB_STATUS_ID)= ? and char(LEAD_STATUS_ID) = ? and PRODUCT_LOB_ID = (SELECT PRODUCT_LOB_ID FROM PRODUCT_MSTR WHERE PRODUCT_ID = ? and STATUS ='A' fetch first row only) WITH UR";
	private static final String INSERT_LEAD_TRANSACTION = "INSERT INTO LEAD_TRANSACTION(LEAD_ID, PRODUCT_ID, LEAD_ASSIGNMENT_TIME, LEAD_CLOSURE_TIME, LEAD_STATUS_ID, LEAD_ASSIGNED_PRIMARY_USER, REMARKS, TRANSACTION_TIME, EXPECTED_CLOSURE_DATE, PRIMARY_AUTH, SUB_STATUS_ID, UPDATED_BY, UD_ID, LEAD_PRODUCT_ID, LEAD_SUB_SUB_STATUS_ID)" +
			"SELECT LEAD_ID, PRODUCT_ID, LEAD_ASSIGNMENT_TIME, LEAD_CLOSURE_TIME, ?, LEAD_ASSIGNED_PRIMARY_USER, ?, current timestamp, EXPECTED_CLOSURE_DATE, PRIMARY_AUTH, ?, ?, UD_ID, LEAD_PRODUCT_ID, LEAD_SUB_SUB_STATUS_ID FROM LEAD_TRANSACTION  WHERE "+
			"LEAD_ID = ? AND LEAD_STATUS_ID IN ("+Constants.LEAD_STATUS_ASSIGNED+","+Constants.LEAD_STATUS_RE_ASSIGNED+")ORDER BY TRANSACTION_TIME DESC FETCH FIRST ROW ONLY WITH UR";
	
	
	private static final String INSERT_LEAD_TRANSACTION_RE_ASSIGN_STATE = "INSERT INTO LEAD_TRANSACTION(LEAD_ID, PRODUCT_ID, LEAD_ASSIGNMENT_TIME, LEAD_CLOSURE_TIME, LEAD_STATUS_ID, LEAD_ASSIGNED_PRIMARY_USER, REMARKS, TRANSACTION_TIME, EXPECTED_CLOSURE_DATE, PRIMARY_AUTH, SUB_STATUS_ID, UPDATED_BY, UD_ID, LEAD_PRODUCT_ID, LEAD_SUB_SUB_STATUS_ID)" +
	"SELECT LEAD_ID, PRODUCT_ID, LEAD_ASSIGNMENT_TIME, LEAD_CLOSURE_TIME, (SELECT LEAD_STATUS_ID FROM LEAD_DATA WHERE LEAD_ID = ?), LEAD_ASSIGNED_PRIMARY_USER, ?, current timestamp, EXPECTED_CLOSURE_DATE, PRIMARY_AUTH, ?, ?, UD_ID, LEAD_PRODUCT_ID, LEAD_SUB_SUB_STATUS_ID FROM LEAD_TRANSACTION  WHERE "+
	"LEAD_ID = ? AND LEAD_STATUS_ID IN ("+Constants.LEAD_STATUS_ASSIGNED+","+Constants.LEAD_STATUS_RE_ASSIGNED+")ORDER BY TRANSACTION_TIME DESC FETCH FIRST ROW ONLY WITH UR";

	//private static final String VALIDATE_LEAD_ID  ="SELECT PRODUCT_ID ,LEAD_ID FROM LEAD_DATA WHERE LEAD_ID = ? AND LEAD_STATUS_ID NOT IN ("+Constants.LEAD_SUB_STATUS_LOST+","+Constants.LEAD_STATUS_WON+")";
	private static final String PAYMENT =" NOT SENT BY ";
	
    private static  StringBuffer leadStatus  = null;
	private static final String GET_CIRCLE_ID_FROM_STATE = " SELECT c.CIRCLE_ID  FROM STATE_MSTR s,CIRCLE_MSTR c WHERE c.CIRCLE_MSTR_ID = s.CIRCLE_MSTR_ID AND UCASE(s.STATE_NAME) = ?  AND c.LOB_ID =?  AND s.STATUS ='A' with ur"; 
	
	private static final String GET_CIRCLE_ID = "SELECT CIRCLE_ID from CIRCLE_MSTR  where ucase(CIRCLE_NAME)=? AND LOB_ID=?  AND STATUS ='A' with ur";
	private  static final String GET_CIRCLE_ID_FROM_ID = "SELECT CIRCLE_ID from CIRCLE_MSTR  where CIRCLE_ID = ? AND LOB_ID=?  AND STATUS ='A'  with ur";
	private static final String GET_ALL_FROM_PINCODE= "SELECT pm.PINCODE, pm.CITY_ZONE_CODE,  czm.CITY_CODE, cim.ZONE_CODE, zm.CIRCLE_MSTR_ID FROM PINCODE_MSTR pm, CITY_ZONE_MSTR czm, CITY_MSTR cim, ZONE_MSTR zm, CIRCLE_MSTR cm WHERE pm.CITY_ZONE_CODE = czm.CITY_ZONE_CODE AND czm.CITY_CODE = cim.CITY_CODE AND cim.ZONE_CODE = zm.ZONE_CODE AND zm.CIRCLE_MSTR_ID = cm.CIRCLE_MSTR_ID AND pm.PINCODE = ? AND cm.CIRCLE_ID = ? AND cm.LOB_ID = ? WITH UR"; 
	private static final String GET_ALL_FROM_RSUCODE= " SELECT rsu.RSU_CODE, rsu.CITY_ZONE_CODE,  czm.CITY_CODE, cim.ZONE_CODE, zm.CIRCLE_MSTR_ID  FROM RSU_MSTR rsu, CITY_ZONE_MSTR czm, CITY_MSTR cim, ZONE_MSTR zm, CIRCLE_MSTR cm WHERE rsu.CITY_ZONE_CODE = czm.CITY_ZONE_CODE AND czm.CITY_CODE = cim.CITY_CODE AND cim.ZONE_CODE = zm.ZONE_CODE AND zm.CIRCLE_MSTR_ID = cm.CIRCLE_MSTR_ID  AND rsu.RSU_CODE = ? AND cm.CIRCLE_ID = ? AND cm.LOB_ID = ? WITH UR";
	private static final String GET_CITY_ZONE_CODE_FROM_CITY = "select CZM.CITY_ZONE_CODE  from CITY_ZONE_MSTR CZM , CITY_MSTR CM where ucase(CZM.CITY_ZONE_NAME)= ? and CZM.CITY_CODE = CM.CITY_CODE and czm.CITY_CODE = ? with ur";
	private static final String GET_CITY_ZONE_CODE_FROM_CIRCLE = "SELECT CZM.CITY_ZONE_CODE, CZM.CITY_CODE, CM.ZONE_CODE FROM CITY_ZONE_MSTR CZM, CITY_MSTR CM, ZONE_MSTR ZM , CIRCLE_MSTR CIM WHERE ucase(CZM.CITY_ZONE_NAME)= ? AND CZM.CITY_CODE = CM.CITY_CODE AND CM.ZONE_CODE = ZM.ZONE_CODE AND ZM.CIRCLE_MSTR_ID = CIM.CIRCLE_MSTR_ID AND CIM.LOB_ID = ? AND CIM.CIRCLE_ID = ? WITH UR"; 
	private static final String GET_ZONE_CODE_FROM_CIRCLE = "SELECT ZM.ZONE_CODE FROM  ZONE_MSTR ZM ,CIRCLE_MSTR CM WHERE ucase(ZM.ZONE_NAME)= ? AND  ZM.CIRCLE_MSTR_ID = CM.CIRCLE_MSTR_ID AND CM.LOB_ID = ? AND CM.CIRCLE_ID = ? WITH UR";
	private static final String GET_CITY_ID = "SELECT cm.CITY_CODE ,cm.ZONE_CODE from CITY_MSTR cm , ZONE_MSTR zm, CIRCLE_MSTR cim where ucase(cm.CITY_NAME)= ? and cm.zone_code = zm.zone_code and zm.circle_mstr_id = cim.circle_mstr_id and cim.lob_id = ? AND CIM.CIRCLE_ID = ? with ur ";
	private static final String GET_CITY_ID_FROMWS_ID = "SELECT cm.CITY_CODE ,cm.ZONE_CODE from CITY_MSTR cm , ZONE_MSTR zm, CIRCLE_MSTR cim where cm.CITY_CODE = ? and cm.zone_code = zm.zone_code and zm.circle_mstr_id = cim.circle_mstr_id and cim.lob_id = ? AND CIM.CIRCLE_ID = ? with ur ";
	private static final String UPDATE_LEAD_DETAILS_REQUEST_CATEORY =  "UPDATE LEAD_DETAILS SET  REQUEST_CATEGORY_ID = ? WHERE LEAD_ID = ? WITH UR";
	
	
	private static final String INSERT_LEAD_TRANSACTION_OPEN_STATE = "INSERT INTO LEAD_TRANSACTION(LEAD_ID, PRODUCT_ID, LEAD_ASSIGNMENT_TIME, LEAD_CLOSURE_TIME, LEAD_STATUS_ID, LEAD_ASSIGNED_PRIMARY_USER, REMARKS, TRANSACTION_TIME, EXPECTED_CLOSURE_DATE, PRIMARY_AUTH, SUB_STATUS_ID, UPDATED_BY, UD_ID, LEAD_PRODUCT_ID, LEAD_SUB_SUB_STATUS_ID)" +
	"SELECT LEAD_ID, PRODUCT_ID, LEAD_ASSIGNMENT_TIME, LEAD_CLOSURE_TIME, '305', LEAD_ASSIGNED_PRIMARY_USER, REMARKS, current timestamp, EXPECTED_CLOSURE_DATE, PRIMARY_AUTH, SUB_STATUS_ID, UPDATED_BY, UD_ID, LEAD_PRODUCT_ID, LEAD_SUB_SUB_STATUS_ID FROM LEAD_TRANSACTION  WHERE "+
	"LEAD_ID = ? AND LEAD_STATUS_ID ="+Constants.LEAD_STATUS_OPEN+" ORDER BY TRANSACTION_TIME DESC FETCH FIRST ROW ONLY WITH UR";
	
	
	private static final String SELECT_EXISTING_LEAD="SELECT LEAD_ID from LEAD_SCCHEDULE_DATA where LEAD_ID=? FETCH FIRST ROW ONLY WITH UR";
	private static final String UPDATE_EXISTING_LEAD="UPDATE LEAD_SCCHEDULE_DATA SET REQUEST_CATEGORY_ID=?,SMSEMAIL_INFOFLAG=1 ,UPDATED_DT=current timestamp"; 
	
	
	private static final String INSERT_LEAD_SCHEDULE="INSERT INTO LEAD_SCCHEDULE_DATA (LEAD_ID, REQUEST_CATEGORY_ID,CREATE_TIME,REFUND_STATUS) VALUES(?,?,current timestamp,1) with ur";
	private final static String IS_VALID_LEADID = "SELECT LEAD_ID FROM LEAD_DATA where LEAD_ID=? WITH UR";
	private final static String IS_VALID_TXNID = "SELECT LD.LEAD_ID FROM LEAD_DATA LD ,LEAD_DETAILS LDT WHERE LD.LEAD_ID=LDT.LEAD_ID AND LDT.LEAD_CAPTURED_DATA_ID=? WITH UR";
	/*private final static String IS_VALID_MOBILE = "SELECT LD.LEAD_ID,LD.SOURCE,LD.PRODUCT_ID FROM LEAD_DATA LD,LEAD_PROSPECT_CUSTOMER LPC WHERE LD.CREATE_TIME = (select max(CREATE_TIME)from LEAD_DATA L WHERE L.PROSPECT_ID = LPC.PROSPECT_ID AND LPC.PROSPECT_MOBILE_NUMBER=? AND L.SOURCE=(SELECT SOURCE_ID FROM SOURCE_MSTR WHERE SOURCE_NAME=?) AND L.PRODUCT_ID=(SELECT PRODUCT_ID FROM PRODUCT_MSTR WHERE PRODUCT_NAME=?)) WITH UR";
	*/
	private final static String DEFUAL_VALUE = "null";
	
	public UpdateLeadResponseMsg updateLeadDataCapture (UpdatedLeadDataFirstVersDTO captureDo) throws DAOException 
	{
		Connection con =null;
		UpdateLeadResponseMsg updateLeadResponseMessage = new UpdateLeadResponseMsg();
		
			try {
				 con = DBConnection.getDBConnection();
					try {
						
						PreparedStatement psUpdateLeadData =null;
						 PreparedStatement psUpdateLeadDetails =null;
						 PreparedStatement psInsertLeadTrxnData =null;
						 PreparedStatement psUpdateLeadProspectdetails =null;
						 Pattern pattern = Pattern.compile(".*[^0-9].*");
						 boolean updateLeadDetailsFlag= false;
						 boolean validatedStatusFlag=false;
						 boolean flagRequestCategory=false;
						 boolean validStatusFlag = false;
						 boolean statusNullflag= false;
						 String cafNumber =null;
						Integer requestCategoryId  =null;
						String lobId =null;
						String reuqstCategorySubStatus =null;
						String prospectId  =null;
						String leadId =null;
						String request  = StringUtils.converObjectToJson(captureDo);
						logger.info(" start validate lead data  request input value ======"+request);
					
					//String checkleadId = isValidLeadIdAndtransaction(captureDo.getLeadId(), con, captureDo.getTrxnId(),captureDo.getProspectsMobileNumber(),captureDo.getSource(),captureDo.getProduct());
					
					String checkleadId = isValidLeadIdAndtransaction(captureDo.getLeadId(), con, captureDo.getTrxnId());
					//String product = validateLeadId(leadId ,con);
					 cafNumber     =      captureDo.getCafNumber();
					 			
					 if(checkleadId !=null && checkleadId.trim().length() >0) 
					 {
						// logger.info("lead id is valid************"+checkleadId);
					String  data  = validateLeadForProcess(checkleadId, captureDo.getTrxnId() ,con ,0);
					
					String productId =null;
					if(data !=null && data.length() >0) {
					String [] leadData = data.split("#");
					 leadId  = leadData[0];
					 productId  = leadData[1];
					 
					}
					String  lobAndProspectId = validateLeadForProcess(leadId, null,con ,2);
					String leadStatuss  = validateLeadForProcess(leadId, null ,con ,1);
					
					if(lobAndProspectId !=null && lobAndProspectId.length() >0) {
						String tempdata []  =lobAndProspectId.split("#");
						lobId  = tempdata[0];
						prospectId  = tempdata[1];
						}
					
						
					if(captureDo.getRequestCategory() !=null && captureDo.getRequestCategory().trim().length() >0 )
					{
					
					 flagRequestCategory= true; 
					 requestCategoryId = getRequestCategory(con,captureDo.getRequestCategory(),productId);
					 reuqstCategorySubStatus =  validateReqCategoryASubStatus(lobId,captureDo.getRequestCategory(),con);
					}
					
					if(leadId !=null ) {
							if(captureDo.getStatus() == null || captureDo.getStatus().trim().length() <=0) {
								statusNullflag= true;
							captureDo.setStatus(leadStatuss);
						}
						
						if(leadStatuss !=null && leadStatuss.trim().length() > 0) {
							captureDo.setProductLob(lobId);
							updateLeadDetailsFlag= true;
												
						}
					}
					
					if(leadId !=null   && captureDo.getStatus() !=null && captureDo.getStatus().trim().length() >0 &&  validatedStatus(captureDo.getStatus()))
					{	if(ServerPropertyReader.getString("wfms.user.webservice").equalsIgnoreCase(captureDo.getSentBy())){
							captureDo.setSubStatus(reuqstCategorySubStatus);
							captureDo.setRequestCategory(String.valueOf(requestCategoryId));
							validStatusFlag = true;
							
						  }	
					}
					
					
					if(validatedStatus(captureDo.getStatus()) && productId !=null && validateStatusBeforeCheck(leadStatuss))
					{
						validatedStatusFlag = true;
						
					if(Constants.LEAD_STATUS_WON.equalsIgnoreCase(captureDo.getStatus()) && (captureDo.getCafNumber() ==null || captureDo.getCafNumber().length() ==0))
							{
						 updateLeadResponseMessage.setResponseCode("10");
						 updateLeadResponseMessage.setResponseMsg("CAF NOT SENT");
						 return updateLeadResponseMessage;	
							}
					else if((Constants.LEAD_STATUS_ASSIGNED.equalsIgnoreCase(captureDo.getStatus()) || Constants.LEAD_STATUS_RE_ASSIGNED.equalsIgnoreCase(captureDo.getStatus()))
							&& captureDo.getSubStatus()!=null && captureDo.getSubStatus().length()>0 && !(validateLeadSubStatus(captureDo.getSubStatus(),captureDo.getStatus(),con ,productId)))
							
					      {
						 updateLeadResponseMessage.setResponseCode("11");
						 updateLeadResponseMessage.setResponseMsg("INVALID SUB STATUS " + captureDo.getSubStatus());
						 return updateLeadResponseMessage;	
						 }
						 
			  		}
					
					if(!statusNullflag && captureDo.getStatus() != null && captureDo.getStatus().length()>0 && !validatedStatus(captureDo.getStatus()))
					{
						 if(leadId !=null) {
							 updateLeadResponseMessage.setResponseCode("9");
							 updateLeadResponseMessage.setResponseMsg(" INVALID LEAD_STATUS " + captureDo.getStatus());
							 return updateLeadResponseMessage;	
							
						 }	
					}
					
					  if (!statusNullflag && !(validatedStatus(captureDo.getStatus()) && productId !=null && validateStatusBeforeCheck(leadStatuss)))
					 {
						  if(leadId !=null) {
							 updateLeadResponseMessage.setResponseCode("9");
							 updateLeadResponseMessage.setResponseMsg(" INVALID LEAD_STATUS " + captureDo.getStatus());
							 return updateLeadResponseMessage;	
							
						 }
						
					}
					 
					if(flagRequestCategory && requestCategoryId  != null && productId!=null && productId.trim().length() >0) 
					{
						insertLeadSchedule(captureDo ,leadId,requestCategoryId,con ,productId);
						
				    }
					
					if(updateLeadDetailsFlag){
						
					updatedLeadDataDetails(con ,captureDo ,leadId ,requestCategoryId ,reuqstCategorySubStatus ,prospectId);
					
					}
					
				
					if(validStatusFlag)
						{
						updateAddressOfLeadDetails(con, captureDo, leadId); //for address update	
						
					}
					
					if(validatedStatusFlag) {
						
					 UPDATE_LEAD_DATA.append("UPDATE LEAD_DATA SET PLAN =?");
					 
					 if(Constants.LEAD_STATUS_ASSIGNED.equalsIgnoreCase(captureDo.getStatus())) {
						 UPDATE_LEAD_DATA.append(",LEAD_STATUS_ID = (SELECT LEAD_STATUS_ID FROM LEAD_DATA WHERE LEAD_ID = ?) ,REMARKS  =? ,LEAD_SUB_STATUS_ID =?"); //for lead has been  reassigned  in state
					 }else {
						 UPDATE_LEAD_DATA.append(",LEAD_STATUS_ID = ?,REMARKS  =? ,LEAD_SUB_STATUS_ID =?"); 
					 }
					 
					 if(Constants.LEAD_STATUS_WON.equalsIgnoreCase(captureDo.getStatus()) && cafNumber !=null && cafNumber.length() >0) {
						 UPDATE_LEAD_DATA.append(",CAF =?");
					 }
					 
					 UPDATE_LEAD_DATA.append(" WHERE  LEAD_ID = ? WITH UR");
					 
					 String paymentCollect =   captureDo.getPaymentCollected();
					 String paymentType    =   captureDo.getPaymentType();
					 String paymentAmount  =   captureDo.getPaymentAmount();
					
					 psUpdateLeadData  = con.prepareStatement(UPDATE_LEAD_DATA.toString());
					 
					 
				//	 psUpdateLeadData.setString(1 ,captureDo.getRentalPlan());
					 psUpdateLeadData.setString(1 ,captureDo.getPlan());         //changed to correct plan
					 
					 if(Constants.LEAD_STATUS_ASSIGNED.equalsIgnoreCase(captureDo.getStatus())) {
						 psUpdateLeadData.setString(2, leadId.trim());
					 }else {
						 psUpdateLeadData.setString(2, captureDo.getStatus());
					 }
					
					 psUpdateLeadData.setString(3, captureDo.getRemarks());
					 
					 if( captureDo.getSubStatus()!=null && captureDo.getSubStatus().length()>0 && validateLeadSubStatus(captureDo.getSubStatus(),captureDo.getStatus(),con ,productId)) { 
						 psUpdateLeadData.setString(4, captureDo.getSubStatus());
					 }else {
						 psUpdateLeadData.setString(4, captureDo.getStatus());
					 }
					 if(Constants.LEAD_STATUS_WON.equalsIgnoreCase(captureDo.getStatus())) {
						 psUpdateLeadData.setString(5, captureDo.getCafNumber());
						 psUpdateLeadData.setString(6, leadId.trim());
					 }else {
						 psUpdateLeadData.setString(5, leadId.trim()); 
					 }
					 psUpdateLeadData.executeUpdate();
					 
					 psUpdateLeadDetails = con.prepareStatement(UPDATE_LEAD_DETAILS);
					 
					 if(paymentCollect !=null && Constants.CHECKED_YES.equalsIgnoreCase(paymentCollect.trim()) && Constants.LEAD_STATUS_WON.equalsIgnoreCase(captureDo.getStatus())){  //this  block  are used for  lead details update 
						 psUpdateLeadProspectdetails = con.prepareStatement(UPDATE_LEAD_PROSPECT_DETAILS);
						 
						 //changed by nancy 
						 if(paymentAmount !=null && paymentAmount.length() >0 && !pattern.matcher(paymentAmount).matches())
						 {
							 psUpdateLeadProspectdetails.setString(1, paymentAmount);
							 
						 }else {
							 psUpdateLeadProspectdetails.setString(1 ,PAYMENT);
						 }
						 psUpdateLeadProspectdetails.setString(2, captureDo.getSentBy());
						 psUpdateLeadProspectdetails.setString(3, leadId.trim());
						 psUpdateLeadProspectdetails.executeUpdate();
						 
						 if(paymentType !=null && paymentType.length() >0) {
							 psUpdateLeadDetails.setString(1, paymentType);
						 }else {
							 psUpdateLeadDetails.setString(1 ,PAYMENT); 
						 }
					 }else {
						 psUpdateLeadDetails.setString(1 ,null);
					 }
					 psUpdateLeadDetails.setString(2 ,captureDo.getProductBought());
					 psUpdateLeadDetails.setString(3 ,captureDo.getCompetitorChosen());
					// psUpdateLeadDetails.setString(4 ,updateLeadDataDO.getRentalPlan());
					 psUpdateLeadDetails.setString(4, captureDo.getSentBy());
					 psUpdateLeadDetails.setString(5, captureDo.getPaymentCollected());
					 psUpdateLeadDetails.setString(6, leadId.trim());
					 psUpdateLeadDetails.executeUpdate();
					 if(Constants.LEAD_STATUS_ASSIGNED.equalsIgnoreCase(captureDo.getStatus())) {
						 psInsertLeadTrxnData   = con.prepareStatement(INSERT_LEAD_TRANSACTION_RE_ASSIGN_STATE);
						 psInsertLeadTrxnData.setString(1, leadId.trim());
					 }else {
						 psInsertLeadTrxnData   = con.prepareStatement(INSERT_LEAD_TRANSACTION);
						 psInsertLeadTrxnData.setString(1, captureDo.getStatus());
					 }
					 psInsertLeadTrxnData.setString(2, captureDo.getRemarks());
					 if(captureDo.getSubStatus()!=null && captureDo.getSubStatus().length()>0 && validateLeadSubStatus(captureDo.getSubStatus(),captureDo.getStatus(),con ,productId)) { 
						 psInsertLeadTrxnData.setString(3, captureDo.getSubStatus());
					 }else {
						 psInsertLeadTrxnData.setString(3, captureDo.getStatus());
					 }
					 psInsertLeadTrxnData.setString(4, captureDo.getSentBy());
					 psInsertLeadTrxnData.setString(5, leadId.trim());
					 psInsertLeadTrxnData.executeUpdate();
					 updateLeadResponseMessage.setResponseCode("0");
					 updateLeadResponseMessage.setResponseMsg("SUCCESS");
					 return updateLeadResponseMessage;	
					}
					
                	if(updateLeadResponseMessage.getResponseCode()== null || updateLeadResponseMessage.getResponseCode().length()<0 || updateLeadResponseMessage.getResponseCode().equalsIgnoreCase(""))
					{
						 updateLeadResponseMessage.setResponseCode("0");
					}
					
					if(updateLeadResponseMessage.getResponseMsg()== null || updateLeadResponseMessage.getResponseMsg().length()<0 ||  updateLeadResponseMessage.getResponseMsg().equalsIgnoreCase(""))
					{
						 updateLeadResponseMessage.setResponseMsg("SUCCESS");
					}
					 	
				} 
					 logger.info(" end  validate lead data"); 
				} catch (Exception e) {
					e.printStackTrace();
					logger.info(" no lead found ********"+e.getMessage());
					throw new DAOException(e.getMessage());
					//throw new Exception();
				}
			
			
			} catch (Exception e) {
				throw new DAOException(e.getMessage());
			}finally {
				try {
					DBConnection.releaseResources(con, null, null);		
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
		return updateLeadResponseMessage;
	}
	
	//private String validateLeadForProcess(String leadId ,String trxnId , String mobile, String source, String product, Connection con ,int flag) throws LMSException 
	private String validateLeadForProcess(String leadId ,String trxnId , Connection con ,int flag) throws LMSException {
		PreparedStatement pdstatement =null;
		ResultSet resultSet =null;
		try {
			if(flag==1 && leadId !=null && leadId.length() >0){
					pdstatement  = con.prepareStatement("SELECT LEAD_STATUS_ID FROM  LEAD_DATA WHERE LEAD_STATUS_ID NOT IN ("+Constants.LEAD_STATUS_WON+","+Constants.LEAD_SUB_STATUS_LOST+") AND  LEAD_ID ="+leadId+" WITH UR");
					resultSet =  pdstatement.executeQuery();
					if(resultSet.next()) {
						return resultSet.getString(ColumnKeys.LEAD_STATUS_ID);
					}
			}else if(flag==2 && leadId !=null && leadId.length() >0){
				pdstatement  = con.prepareStatement("SELECT PRODUCT_LOB_ID ,PROSPECT_ID FROM LEAD_PROSPECT_DETAIL WHERE LEAD_ID ="+leadId+" WITH UR");
				resultSet =  pdstatement.executeQuery();
				if(resultSet.next())
					return resultSet.getString("PRODUCT_LOB_ID")+"#"+resultSet.getString("PROSPECT_ID");
			} 
			
			else {
				if(leadId !=null && leadId.length() >0){
					pdstatement  = con.prepareStatement("SELECT LEAD_ID,PRODUCT_ID FROM LEAD_DATA WHERE LEAD_ID ="+leadId+" WITH UR");
					resultSet =  pdstatement.executeQuery();
					if(resultSet.next())
						return resultSet.getString(ColumnKeys.LEAD_ID)+"#"+resultSet.getString(ColumnKeys.PRODUCT_ID);
				}
				if(trxnId !=null && trxnId.length() >0){
					pdstatement  = con.prepareStatement(" SELECT d.LEAD_ID ,d.PRODUCT_ID FROM LEAD_DATA d ,LEAD_DETAILS l WHERE l.LEAD_ID =d.LEAD_ID AND  l.LEAD_CAPTURED_DATA_ID ="+trxnId+" WITH UR");
					resultSet =  pdstatement.executeQuery();
					if(resultSet.next())
						return resultSet.getString(ColumnKeys.LEAD_ID)+"#"+resultSet.getString(ColumnKeys.PRODUCT_ID);
				}
				
				/*if(mobile !=null && mobile.length() >0){
					pdstatement  = con.prepareStatement("SELECT LD.LEAD_ID,LD.PRODUCT_ID FROM LEAD_DATA LD,LEAD_PROSPECT_CUSTOMER LPC WHERE LD.CREATE_TIME = (select max(CREATE_TIME)from LEAD_DATA L WHERE L.PROSPECT_ID = LPC.PROSPECT_ID AND LPC.PROSPECT_MOBILE_NUMBER="+mobile+" AND L.SOURCE=(SELECT SOURCE_ID FROM SOURCE_MSTR WHERE SOURCE_NAME="+source+") AND L.PRODUCT_ID = (SELECT PRODUCT_ID FROM PRODUCT_MSTR WHERE PRODUCT_NAME=?"+product+"))");
					resultSet =  pdstatement.executeQuery();
					if(resultSet.next())
						return resultSet.getString(ColumnKeys.LEAD_ID)+"#"+resultSet.getString(ColumnKeys.PRODUCT_ID);
				}*/
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
			throw new LMSException(e.getMessage());
			//return null;
		}finally{
			try {
				DBConnection.releaseResources(null, pdstatement, resultSet);	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
	
	/*private String validateLeadId(String leadId ,Connection con) throws DAOException {
		ResultSet resultSet =null;
		PreparedStatement psLeadData = null;
		try {
			psLeadData  = con.prepareStatement(VALIDATE_LEAD_ID);
			
			if(leadId==null || leadId.equals(""))
			{
				
				psLeadData.setString(1, null);	
			}
			else
			{
				
			psLeadData.setString(1, leadId);
			}
			
			resultSet =  psLeadData.executeQuery();
			
			while(resultSet.next()){
				resultSet.getString("LEAD_ID");
				resultSet.getString("PRODUCT_ID");
				return resultSet.getString("PRODUCT_ID");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}finally {
			try {
				DBConnection.releaseResources(null, psLeadData, resultSet);	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return null;
	}*/
	
	private boolean validateLeadSubStatus(String subStatus ,String status ,Connection connection ,String  productId) {
		PreparedStatement psSubStatus =null;
		ResultSet rsSubStatus = null;
		try {
			psSubStatus  = connection.prepareStatement(VALIDAT_LEAD_SUBSTATUS);
			if(subStatus !=null && subStatus !="") {
			psSubStatus.setString(1, subStatus);
			}else {
				psSubStatus.setString(1, status);
			}
			psSubStatus.setString(2, status);
			psSubStatus.setString(3, productId);
			rsSubStatus =  psSubStatus.executeQuery();
			if(rsSubStatus.next() && rsSubStatus.getString(1) !=null && !rsSubStatus.getString(1).equalsIgnoreCase("0")){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				DBConnection.releaseResources(null, psSubStatus, rsSubStatus);	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return false;
	}

	private boolean validatedStatus(String stutus) {
		
		if(leadStatus == null && stutus != null && stutus.trim().length() >0) {
			
			leadStatus  =  new StringBuffer(Constants.LEAD_STATUS_ASSIGNED);
			leadStatus.append(",");
			leadStatus.append(Constants.LEAD_STATUS_RE_ASSIGNED);
			leadStatus.append(",");
			leadStatus.append(Constants.LEAD_SUB_STATUS_LOST);
			leadStatus.append(",");
			leadStatus.append(Constants.LEAD_STATUS_WON);
			return leadStatus.toString().contains(stutus.trim());
		}else if(stutus != null && stutus.trim().length() >0) {
		 return leadStatus.toString().contains(stutus.trim());
		}else {
		return false;
		}
	}
	
	private boolean validateStatusBeforeCheck(String stutus) {
		if(stutus != null && stutus.trim().length() >0) {
			StringBuffer statusBuffer  = new StringBuffer(Constants.LEAD_STATUS_ASSIGNED);
			statusBuffer.append(",");
			statusBuffer.append(Constants.LEAD_STATUS_RE_ASSIGNED);
			return statusBuffer.toString().contains(stutus.trim());
		}
		else {
			return false;
		}
	}
	private String validateReqCategoryASubStatus(String lobId ,String requestCategory ,Connection con) throws DAOException{
		PreparedStatement pdstatement =null;
		ResultSet resultSet =null;
		try {
			pdstatement  = con.prepareStatement("SELECT SUB_STATUS_ID FROM LEAD_SUB_STATUS WHERE ucase(SUB_STATUS) = ucase('"+requestCategory+"') AND PRODUCT_LOB_ID ="+lobId+" FETCH FIRST ROW ONLY WITH UR");
			resultSet =  pdstatement.executeQuery();
			if(resultSet.next())
				return resultSet.getString("SUB_STATUS_ID");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}finally {
			try {
				DBConnection.releaseResources(null, pdstatement, resultSet);	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
	
	private boolean updatedLeadDataDetails(Connection con,UpdatedLeadDataFirstVersDTO dataCaptureDo ,String leadId ,Integer requestCategoryId ,String reuqstCategorySubStatus ,String prospectId) {
		
		PreparedStatement psStatement =null;
		StringBuffer queryLeadDetails   = new StringBuffer("UPDATE LEAD_DETAILS SET ");
		StringBuffer queryLeadData   = new StringBuffer(" UPDATE LEAD_DATA SET ");
		StringBuffer queryLeadProspectdata   = new StringBuffer("UPDATE LEAD_PROSPECT_DETAIL SET ");
		StringBuffer queryLeadProspectCustom   = new StringBuffer("UPDATE LEAD_PROSPECT_CUSTOMER SET ");
		StringBuffer queryLeadUpdateData   = new StringBuffer("UPDATE LEAD_UPDATE_DATA SET ");
		StringBuffer  queryLeadInsertData1   = new StringBuffer("INSERT INTO LEAD_UPDATE_DATA( ");
		StringBuffer  queryLeadInsertData2   = new StringBuffer(" VALUES( ");
		
		boolean queryflag =false;
		boolean queryInsertflag =false;
		try {
			Map<String, String>  map  = preparedQuery(dataCaptureDo, 0); //0 for prepare query  of lead_details
			for (Map.Entry<String, String> entry  :map.entrySet()) {
				if(entry.getKey() !=null && !entry.getKey().equalsIgnoreCase(DEFUAL_VALUE) && entry.getKey().length() >0){
				queryLeadDetails.append(entry.getValue()+" = '"+entry.getKey()+"',");
				queryflag =true;
				}
			}
			if(requestCategoryId !=null )
			{
				queryLeadDetails.append(ColumnKeys.REQUEST_CATEGORY_ID +" ="+requestCategoryId+",");
				queryflag =true;
			}
			queryLeadDetails.append(" UPDATED_DATE =current timestamp  WHERE LEAD_ID ="+leadId+" WITH UR");
			psStatement  = con.prepareStatement(queryLeadDetails.toString());
			if(queryflag)
			psStatement.executeUpdate();
			map.clear();
			
			map = preparedLeadProspectdetailsQuery(dataCaptureDo, con, dataCaptureDo.getProductLob());
			queryflag = false;
			for (Map.Entry<String, String> entry  :map.entrySet()) {
				if(entry.getKey() !=null && !entry.getKey().equalsIgnoreCase(DEFUAL_VALUE) && entry.getKey().length() >0) {
					queryLeadProspectdata.append(entry.getValue()+" = '"+entry.getKey()+"',");
					queryflag =true;
				}
			}
			queryLeadProspectdata.append(" UPDATED_DT =current timestamp  WHERE LEAD_ID ="+leadId+" WITH UR");
			psStatement  = con.prepareStatement(queryLeadProspectdata.toString());
			if(queryflag)
			psStatement.executeUpdate();
			map.clear();
			
			map  = preparedQuery(dataCaptureDo, 2); // 2 for prepare query  of prospect customer 
			queryflag =false;
			for (Map.Entry<String, String> entry  :map.entrySet()) {
				if(entry.getKey() !=null && !entry.getKey().equalsIgnoreCase(DEFUAL_VALUE) && entry.getKey().length() >0) {
					queryLeadProspectCustom.append(entry.getValue()+" = '"+entry.getKey()+"',");
					queryflag =true;
				}
			}
			queryLeadProspectCustom.append(" UPDATED_DT =current timestamp  WHERE PROSPECT_ID ="+prospectId+" WITH UR");
			
			psStatement  = con.prepareStatement(queryLeadProspectCustom.toString());
			if(queryflag)
				psStatement.executeUpdate();
						
			if(dataCaptureDo.getStatus() !=null && Constants.LEAD_STATUS_OPEN.equalsIgnoreCase(dataCaptureDo.getStatus()) && dataCaptureDo.getQualLeadParam() !=null && dataCaptureDo.getQualLeadParam().equalsIgnoreCase("1")) {
				psStatement  = con.prepareStatement(" UPDATE LEAD_DATA SET LEAD_STATUS_ID = 305 ,UPDATED_DT =current timestamp  WHERE LEAD_ID ="+leadId+" WITH UR");
				psStatement.executeUpdate();

				psStatement.clearParameters();
				psStatement  = con.prepareStatement(INSERT_LEAD_TRANSACTION_OPEN_STATE);
				psStatement.setString(1, leadId);
				
				psStatement.executeUpdate();
				
				con.commit();
			}
			
			map.clear();
			map  = preparedQuery(dataCaptureDo, 1); //1 for prepare query of lead_data
			queryflag =false;
			for (Map.Entry<String, String> entry  :map.entrySet()) {
				if(entry.getKey() !=null && !entry.getKey().equalsIgnoreCase(DEFUAL_VALUE) && entry.getKey().length() >0) {
					queryLeadData.append(entry.getValue()+" = '"+entry.getKey()+"',");
					queryflag =true;
				}
			}
			
			//updation of extra params:
			queryLeadData.append(" UPDATED_DT =current timestamp  WHERE LEAD_ID ="+leadId+" WITH UR");
			
			psStatement  = con.prepareStatement(queryLeadData.toString());
			if(queryflag){
				psStatement.executeUpdate();
			}
		
		 	
			map.clear();
			map  = preparedQuery(dataCaptureDo, 3); // 3 for prepare query of UPDATE LEAD_UPDATE_DATA
			queryflag =false;
			psStatement  = con.prepareStatement("SELECT LEAD_ID FROM LEAD_UPDATE_DATA WHERE LEAD_ID ="+leadId+" FETCH FIRST ROW ONLY WITH UR");
			ResultSet resultSet   = psStatement.executeQuery();
			if(resultSet.next() && resultSet.getString("LEAD_ID")!=null && resultSet.getString("LEAD_ID").length() >0) {
				queryInsertflag = true;
			}
			for (Map.Entry<String, String> entry  :map.entrySet()) {
				if(entry.getKey() !=null && !entry.getKey().equalsIgnoreCase(DEFUAL_VALUE) && entry.getKey().length() >0) {
					if(queryInsertflag) {
						queryLeadUpdateData.append(entry.getValue()+" = '"+entry.getKey()+"',");
					}else {
						queryLeadInsertData1.append(entry.getValue()+" ,");	
						queryLeadInsertData2.append("'"+entry.getKey()+"',");	
					}
					queryflag =true;
				}
			}
			if(queryInsertflag) {
			queryLeadUpdateData.append(" UPDATED_DT = current timestamp WHERE LEAD_ID ="+leadId+" WITH UR");
			psStatement  = con.prepareStatement(queryLeadUpdateData.toString());
			}else {
				queryLeadInsertData1.append("LEAD_ID )");
				queryLeadInsertData2.append("'"+leadId+"')");
				queryLeadInsertData1.append(queryLeadInsertData2);
				psStatement  = con.prepareStatement(queryLeadInsertData1.toString());
			}
			
			psStatement.executeUpdate();
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			try {
				DBConnection.releaseResources(null, psStatement, null);	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return true;
	}
	
	private Map<String, String> preparedQuery(UpdatedLeadDataFirstVersDTO dataCaptureDo ,int flag)
	{
		Map<String, String> mapquery = new HashMap<String, String>();
		
		if(flag == 0) {
			
			mapquery.put(dataCaptureDo.getAgencyId(), ColumnKeys.AGENCY_ID);
			mapquery.put(dataCaptureDo.getBenefit(), ColumnKeys.BENEFIT);
			
			mapquery.put(dataCaptureDo.getBoostercount(), ColumnKeys.BOOSTER_COUNT);
			mapquery.put(dataCaptureDo.getBoostertaken(), ColumnKeys.BOOSTER_TAKEN);
			mapquery.put(dataCaptureDo.getCompetitorChosen(), ColumnKeys.COMPETITOR_CHOSEN);
			mapquery.put(dataCaptureDo.getCustomerInfoId(), ColumnKeys.CUSTOMER_INFO_ID);
			mapquery.put(dataCaptureDo.getDataquota(), ColumnKeys.DATA_QUOTA);
			mapquery.put(dataCaptureDo.getDevicemrp(), ColumnKeys.DEVICE_MRP);
			mapquery.put(dataCaptureDo.getDevicetaken(), ColumnKeys.DEVICE_TAKEN);
			mapquery.put(dataCaptureDo.getDob(), ColumnKeys.DOB);
			
			mapquery.put(dataCaptureDo.getDownloadlimit(), ColumnKeys.DOWNLOAD_LIMIT);
			mapquery.put(dataCaptureDo.getFeasibilityparam(), ColumnKeys.FEASIBILITY_PARAM);
			
			mapquery.put(dataCaptureDo.getFlag(), ColumnKeys.FLAG);
			
			mapquery.put(dataCaptureDo.getFreebiecount(), ColumnKeys.FREEBIE_COUNT);
			mapquery.put(dataCaptureDo.getFreebietaken(), ColumnKeys.FREEBIE_TAKEN);
			
			mapquery.put(dataCaptureDo.getHlrno(), ColumnKeys.HLR_NO);
			mapquery.put(dataCaptureDo.getOffer(), ColumnKeys.OFFER);
			mapquery.put(dataCaptureDo.getPkgduration(), ColumnKeys.PKG_DURATION);
			
			mapquery.put(dataCaptureDo.getPlanId(), ColumnKeys.PLAN_ID);
			mapquery.put(dataCaptureDo.getPrepaidnumber(), ColumnKeys.PREPAID_NUMBER);
			mapquery.put(dataCaptureDo.getProductBought(), ColumnKeys.PRODUCT_BOUGHT);
			mapquery.put(dataCaptureDo.getRentaltype(), ColumnKeys.RENTAL_TYPE);
			mapquery.put(dataCaptureDo.getState(), ColumnKeys.STATE);
			mapquery.put(dataCaptureDo.getUdId(), ColumnKeys.UD_ID);
			mapquery.put(dataCaptureDo.getUsertype(), ColumnKeys.USER_TYPE);
			mapquery.put(dataCaptureDo.getSentBy(), ColumnKeys.SENTBY);
			mapquery.put(dataCaptureDo.getVoicebenefit(), ColumnKeys.VOICE_BENEFIT);
			mapquery.put(dataCaptureDo.getUtmLAbels(), ColumnKeys.UTM_LABELS);
			mapquery.put(dataCaptureDo.getPaymentType(), ColumnKeys.PAYMENT_TYPE);
			mapquery.put(dataCaptureDo.getTaskStartTime(), ColumnKeys.TASK_START_TIME);
			mapquery.put(dataCaptureDo.getTaskEndTime(), ColumnKeys.TASK_END_TIME);
			mapquery.put(dataCaptureDo.getLatitude(), ColumnKeys.EXTRA_PARAM3);//latitude in lead_details
			mapquery.put(dataCaptureDo.getLongitude(), ColumnKeys.EXTRA_PARAM4);//longitude in lead_details
			mapquery.put(dataCaptureDo.getPaymentStatus(), ColumnKeys.EXTRA_PARAM6);//payment_status in lead_details
			mapquery.put(dataCaptureDo.getAssignedRemarks(), ColumnKeys.ASSIGNED_REMARKS);
			mapquery.put(dataCaptureDo.getPaymentCollected(), ColumnKeys.PAYMENT_COLLECTED);
			
			
			if(dataCaptureDo.getSimNo() !=null && dataCaptureDo.getSimNo() !="")
			{
				mapquery.put(dataCaptureDo.getSimNo(), ColumnKeys.EXTRA_PARAM1);                	// sim number in lead_details	
			}
			else
			{
				mapquery.put(dataCaptureDo.getSimNo(), ColumnKeys.nullString);
			}
			
			
			if(dataCaptureDo.getCustomerSegment() !=null && dataCaptureDo.getCustomerSegment() !="")
			{
				mapquery.put(dataCaptureDo.getCustomerSegment(), ColumnKeys.EXTRA_PARAM2);                	// getCustomerSegment in lead_details	
			}
			else
			{
				mapquery.put(dataCaptureDo.getCustomerSegment(), ColumnKeys.nullString);
			}
		
			
			if(dataCaptureDo.getNationality() !=null && dataCaptureDo.getNationality() !="")
			{
				mapquery.put(dataCaptureDo.getNationality(), ColumnKeys.EXTRA_PARAM5);                	// nationality in lead_details	
			}
			else
			{
				mapquery.put(dataCaptureDo.getNationality(), ColumnKeys.nullString);
			}
			
			
			if(dataCaptureDo.getGender() !=null && dataCaptureDo.getGender() !="")
			{
				mapquery.put(dataCaptureDo.getGender(), ColumnKeys.EXTRA_PARAM7);                	// gender in lead_details	
			}
			else
			{
				mapquery.put(dataCaptureDo.getGender(), ColumnKeys.nullString);
			}
			
			
			if(dataCaptureDo.getIdentityProofType() !=null && dataCaptureDo.getIdentityProofType() !="")
			{
				mapquery.put(dataCaptureDo.getIdentityProofType(), ColumnKeys.EXTRA_PARAM8);                	// identity proof type in lead_details	
			}
			else
			{
				mapquery.put(dataCaptureDo.getIdentityProofType(), ColumnKeys.nullString);
			}
			
			
			
		}else if(flag ==1)  {//for lead_data update	
			mapquery.put(dataCaptureDo.getAllocatedNo(), ColumnKeys.ALLOCATED_NO);
			mapquery.put(dataCaptureDo.getOnlineCafNo(), ColumnKeys.ONLINE_CAF_NO);
			mapquery.put(dataCaptureDo.getRemarks(), ColumnKeys.REMARKS);
			mapquery.put(dataCaptureDo.getService(), ColumnKeys.SERVICE);
			mapquery.put(dataCaptureDo.getPlan(), ColumnKeys.PLAN);
			
			
		}else if(flag ==2) {
			
			//for Customer data
			
			mapquery.put(dataCaptureDo.getProspectsName(), ColumnKeys.CUSTOMER_NAME);
			mapquery.put(dataCaptureDo.getEmail(), ColumnKeys.EMAIL);
			mapquery.put(dataCaptureDo.getProspectsMobileNumber(), ColumnKeys.PROSPECT_MOBILE_NUMBER);
			mapquery.put(dataCaptureDo.getLandlineNumber(), ColumnKeys.LANDLINE_NUMBER);
			mapquery.put(dataCaptureDo.getAlternateContactNumber(), ColumnKeys.ALTERNATE_CONTACT_NUMBER);
			mapquery.put(dataCaptureDo.getMaritalStatus(), ColumnKeys.MARITAL_STATUS);
			mapquery.put(dataCaptureDo.getCompany(), ColumnKeys.COMPANY);
		}
		else if(flag ==3) {
			
			if(dataCaptureDo.getIdentityProofID() !=null && dataCaptureDo.getIdentityProofID() !="")
			{
				mapquery.put(dataCaptureDo.getIdentityProofID(), ColumnKeys.IDENTITYPROOF);                	// identity proof ID in LEAD_UPDATE_DATA	
			}
			else
			{
				mapquery.put(dataCaptureDo.getIdentityProofID(), ColumnKeys.nullString);
			}
			
			
			if(dataCaptureDo.getRelationName() !=null && dataCaptureDo.getRelationName() !="")
			{
				mapquery.put(dataCaptureDo.getRelationName(), ColumnKeys.RELATION);                	// relation name in LEAD_UPDATE_DATA	
			}
			else
			{
				mapquery.put(dataCaptureDo.getRelationName(), ColumnKeys.nullString);
			}
			
			
			mapquery.put(dataCaptureDo.getExtraParam3(), ColumnKeys.EXT3);  
			
			if(dataCaptureDo.getPayment_date() !=null && dataCaptureDo.getPayment_date() !="")
			{
				mapquery.put(dataCaptureDo.getPayment_date(), ColumnKeys.PAYMENTDATE);                	// payment date in LEAD_UPDATE_DATA	
			}
			else
			{
				mapquery.put(dataCaptureDo.getPayment_date(), ColumnKeys.nullString);
			}
			
			
			if(dataCaptureDo.getUpc() !=null && dataCaptureDo.getUpc() !="")
			{
				mapquery.put(dataCaptureDo.getUpc(), ColumnKeys.UPC);                	// upc in LEAD_UPDATE_DATA	
			}
			else
			{
				mapquery.put(dataCaptureDo.getUpc(), ColumnKeys.nullString);
			}
			
			if(dataCaptureDo.getUpc_gen_date() !=null && dataCaptureDo.getUpc_gen_date() !="")
			{
				mapquery.put(dataCaptureDo.getUpc_gen_date(), ColumnKeys.UPCGENDT);                	// upc gen date in LEAD_UPDATE_DATA	
			}
			else
			{
				mapquery.put(dataCaptureDo.getUpc_gen_date(), ColumnKeys.nullString);
			}
			
			if(dataCaptureDo.getPreviousOerator() !=null && dataCaptureDo.getPreviousOerator() !="")
			{
				mapquery.put(dataCaptureDo.getPreviousOerator(), ColumnKeys.PREVOP);                	// previous operator in LEAD_UPDATE_DATA	
			}
			else
			{
				mapquery.put(dataCaptureDo.getPreviousOerator(), ColumnKeys.nullString);
			}
			
			if(dataCaptureDo.getPreviousCircle() !=null && dataCaptureDo.getPreviousCircle() !="")
			{
				mapquery.put(dataCaptureDo.getPreviousCircle(), ColumnKeys.PREVCIR);                	// previous circle in LEAD_UPDATE_DATA	
			}
			else
			{
				mapquery.put(dataCaptureDo.getPreviousCircle(), ColumnKeys.nullString);
			}
			
			if(dataCaptureDo.getPartExisting() !=null && dataCaptureDo.getPartExisting() !="")
			{
				mapquery.put(dataCaptureDo.getPartExisting(), ColumnKeys.EXISTPART);                	// existing part in LEAD_UPDATE_DATA	
			}
			else
			{
				mapquery.put(dataCaptureDo.getPartExisting(), ColumnKeys.nullString);
			}
			
			
			if(dataCaptureDo.getMnpStatus() !=null && dataCaptureDo.getMnpStatus() !="")
			{
				mapquery.put(dataCaptureDo.getMnpStatus(), ColumnKeys.MNPSTATUS);                	// mnp status in LEAD_UPDATE_DATA	
			}
			else
			{
				mapquery.put(dataCaptureDo.getMnpStatus(), ColumnKeys.nullString);
			}
			
			if(dataCaptureDo.getDocumentCollectedFlag() !=null && dataCaptureDo.getDocumentCollectedFlag() !="")
			{
				mapquery.put(dataCaptureDo.getDocumentCollectedFlag(), ColumnKeys.DOC_COLLECTED_FLAG);                	// doc collected in LEAD_UPDATE_DATA	
			}
			else
			{
				mapquery.put(dataCaptureDo.getDocumentCollectedFlag(), ColumnKeys.nullString);
			}
			
			
			if(dataCaptureDo.getPlanType() !=null && dataCaptureDo.getPlanType() !="")
			{
				mapquery.put(dataCaptureDo.getPlanType(), ColumnKeys.PLAN_TYPE);                	// plan type in LEAD_UPDATE_DATA	
			}
			else
			{
				mapquery.put(dataCaptureDo.getPlanType(), ColumnKeys.nullString);
			}
			
			if(dataCaptureDo.getRentalPlan() !=null && dataCaptureDo.getRentalPlan() !="")
			{
				mapquery.put(dataCaptureDo.getRentalPlan(), ColumnKeys.RENTAL_PLAN);                	// rental plan in LEAD_UPDATE_DATA	
			}
			else
			{
				mapquery.put(dataCaptureDo.getRentalPlan(), ColumnKeys.nullString);
			}
					
			
		}
		return mapquery;
	}
	private Map<String, String> preparedLeadProspectdetailsQuery(UpdatedLeadDataFirstVersDTO dataCaptureDo ,Connection con , String PRODUCT_LOB_ID) {
		
		String pincode =null;
		String cityId =null;
		String cityZoneCode =null;
		String zoneCode=null;
		String rsuCode =null;
		
		Map<String, String> mapquery = new HashMap<String, String>();
		mapquery.put(dataCaptureDo.getAddress(), ColumnKeys.ADDRESS1);
		mapquery.put(dataCaptureDo.getAddress2(), ColumnKeys.ADDRESS2);
		mapquery.put(dataCaptureDo.getAdParameter(), ColumnKeys.AD_PARAMETER);
		mapquery.put(dataCaptureDo.getAlternateContactNumber(), ColumnKeys.ALTERNATE_CONTACT_NUMBER);
		String  appoinmentstartTime = dataCaptureDo.getAppointmentTime();
		String appoinmentEndTime = dataCaptureDo.getAppointmentEndTime();
		if (appoinmentstartTime !=null && appoinmentEndTime != null && appoinmentstartTime.matches("[0-9]{4}(-)((0[1-9])|(1[0-2]))(-)((0[1-9])|([1-2][0-9])|(3[0-1]))(T)(([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])") && appoinmentEndTime.matches("[0-9]{4}(-)((0[1-9])|(1[0-2]))(-)((0[1-9])|([1-2][0-9])|(3[0-1]))(T)(([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])"))
		{
		 mapquery.put(dataCaptureDo.getAppointmentTime(), ColumnKeys.APPOINTMENT_TIME);
		 mapquery.put(dataCaptureDo.getAppointmentEndTime(), ColumnKeys.APPOINMENT_ENDTIME);
		}
		mapquery.put(dataCaptureDo.getCompany(), ColumnKeys.COMPANY);
		String isCustomer  = dataCaptureDo.getIsCustomer();
		if(isCustomer !=null && isCustomer.equalsIgnoreCase("Y")) {
		mapquery.put("Y", ColumnKeys.IS_CUSTOMER);
		}else {
		mapquery.put("N", ColumnKeys.IS_CUSTOMER);
		}
		mapquery.put(dataCaptureDo.getPaymentAmount(), "PYT_AMT");
		mapquery.put(dataCaptureDo.getPaymentAmtDue(), ColumnKeys.PYT_AMT_DUE);
		mapquery.put(dataCaptureDo.getQualLeadParam(), ColumnKeys.QUAL_LEAD_PARAM);
		mapquery.put(dataCaptureDo.getMaritalStatus(), ColumnKeys.MARITAL_STATUS);
		mapquery.put(dataCaptureDo.getRental(), ColumnKeys.RENTAL);
		mapquery.put(dataCaptureDo.getTag(), ColumnKeys.TAG);
		mapquery.put(dataCaptureDo.getTranRefno(), ColumnKeys.TRAN_REFNO);
		mapquery.put(dataCaptureDo.getLandlineNumber(), ColumnKeys.LANDLINE_NUMBER);
		
		if(!validatedStatus(dataCaptureDo.getStatus())){
			try {
				String  allData = 	validateLeadOtherData(con, dataCaptureDo, PRODUCT_LOB_ID);
				String [] cIdCzCodeDataz  = allData.split("#");
				if(cIdCzCodeDataz.length >0) {
				cityId  = cIdCzCodeDataz[0];
				cityZoneCode  = cIdCzCodeDataz[1];
				zoneCode  = cIdCzCodeDataz[2];
				pincode = cIdCzCodeDataz[3];
				rsuCode = cIdCzCodeDataz[4];
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		mapquery.put(dataCaptureDo.getCircleId(), ColumnKeys.CIRCLE_ID);
		mapquery.put(cityId, ColumnKeys.CITY_CODE);
		mapquery.put(cityZoneCode, ColumnKeys.CITY_ZONE_CODE);
		mapquery.put(pincode, ColumnKeys.PINCODE);
		mapquery.put(rsuCode, ColumnKeys.RSU_CODE);
		mapquery.put(zoneCode, ColumnKeys.ZONE_CODE);
		
		}
		
		return mapquery;
	}
	
	private Integer getRequestCategory(Connection con ,String requestCategory ,String productId) throws DAOException {
		
		int src = 0;
		ResultSet rsRequestCategory =null;
		PreparedStatement psUpdateLeadCategory  = null;
		if(requestCategory !=null && requestCategory.trim().length() >0) {
			try {
				
				StringBuffer psRequestCategory   = new StringBuffer( "SELECT REQUEST_CATEGORY_ID FROM REQUEST_CATEGORY_MSTR  WHERE ucase(REQUEST_CATEGORY)=ucase('"+requestCategory.trim()+"') AND PRODUCT_ID =? AND STATUS = 'A' ");
				
				 psUpdateLeadCategory  = con.prepareStatement(psRequestCategory.toString()+" with ur");
				// psUpdateLeadCategory.setString(1, requestCategory.toUpperCase());
				 psUpdateLeadCategory.setInt(1, Integer.parseInt(productId)  );
				 rsRequestCategory = psUpdateLeadCategory.executeQuery();
			
				if(rsRequestCategory.next())  
				{
					return rsRequestCategory.getInt("REQUEST_CATEGORY_ID");
				}
				else 
				{
					return null;	
				}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}finally {
			try {
				DBConnection.releaseResources(null, psUpdateLeadCategory, rsRequestCategory);	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		}else {
			return src;
		}
		
	}
	
	//Get Circle Id based on state , circle name and circle id from  data based
	private int getCircleID(Connection con ,UpdatedLeadDataFirstVersDTO leadDtoObj,String PRODUCT_LOB_ID ) {
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
					try {
						DBConnection.releaseResources(null, psCircle, rsCircle);	
					} catch (Exception e2) {
						e2.printStackTrace();
					}
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
				try {
					DBConnection.releaseResources(null, psCircle, rsCircle);	
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		if(leadDtoObj.getState() != null && leadDtoObj.getState().length() >0 && circleId <=0) {
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
		/*if(leadDtoObj.getCircle() == null || leadDtoObj.getCircle().trim().length() <= 0 || "".equalsIgnoreCase(leadDtoObj.getCircle()))
		{
			circleId=0;
		}*/
		} catch (Exception e) {
			try {
				DBConnection.releaseResources(null, psCircle, rsCircle);	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return circleId;
	}

	private String validateLeadOtherData(Connection con ,UpdatedLeadDataFirstVersDTO leadDtoObj ,String PRODUCT_LOB_ID)
	{
		PreparedStatement correctCircke= null;
		int circleId  = getCircleID(con, leadDtoObj, PRODUCT_LOB_ID);
		
		if(circleId==-1)
		{
		try
		{
			if(leadDtoObj.getLeadId()!= null && !leadDtoObj.getLeadId().equalsIgnoreCase("") && leadDtoObj.getLeadId().length()>0)
			{
			 correctCircke = con.prepareStatement("SELECT CIRCLE_ID FROM LEAD_PROSPECT_DETAIL WHERE LEAD_ID=? with ur");
			correctCircke.setString(1,leadDtoObj.getLeadId());
			}
			else 
			{
			 correctCircke = con.prepareStatement("SELECT CIRCLE_ID FROM LEAD_PROSPECT_DETAIL WHERE LEAD_ID= (SELECT LEAD_ID FROM LEAD_DETAILS WHERE LEAD_CAPTURED_DATA_ID=?) with ur");
			 correctCircke.setString(1,leadDtoObj.getTrxnId());
			}
			
			ResultSet coorectCircle = correctCircke.executeQuery();
			if(coorectCircle.next())
			{
			 circleId  = coorectCircle.getInt("CIRCLE_ID");
			}
			else
			{
			 circleId  = getCircleID(con, leadDtoObj, PRODUCT_LOB_ID);
			}
		}
		
		catch(Exception e)
		{
		   e.printStackTrace();
		}
		}
		
		leadDtoObj.setCircleId(String.valueOf(circleId));
		String pincode = null;
		String cityId = null;
		String cityZoneCode =null;
		String zoneCode=null;
		String rsuCode = null;
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
			if(PRODUCT_LOB_ID !=null && leadDtoObj.getRsuCode() != null && leadDtoObj.getRsuCode().length() >0 && PRODUCT_LOB_ID.equalsIgnoreCase("2"))
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
						rsuCode  = rsRsuCode.getString("RSU_CODE");
						return cityId+"#"+cityZoneCode+"#"+zoneCode+"#"+pincode+"#"+rsuCode;
					}
				} catch (Exception e) {
				}finally{
					try {
						DBConnection.releaseResources(null, psRsuCode, rsRsuCode);	
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}else {//To find out the Zone-code, City Code, City-Zone Code from Pincode entered in web-service input
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
							return cityId+"#"+cityZoneCode+"#"+zoneCode+"#"+pincode+"#"+rsuCode;
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
			}
			
		//To find out the city code from city Name entered in web-service input 
		try {
			 if((leadDtoObj.getCity() != null && leadDtoObj.getCity().length() >0)||(leadDtoObj.getCityId() != null && leadDtoObj.getCityId().length() >0) ){	
					psCity = con.prepareStatement(GET_CITY_ID);
					psCity.setString(1, leadDtoObj.getCity().toUpperCase());
					psCity.setString(2, PRODUCT_LOB_ID);
					psCity.setInt(3, circleId);
					rsCity= psCity.executeQuery();
					if(rsCity.next()) {
						cityId=rsCity.getString("CITY_CODE");
						zoneCode = rsCity.getString("ZONE_CODE");
					}else {
						psCity = con.prepareStatement(GET_CITY_ID_FROMWS_ID);
						psCity.setString(1, leadDtoObj.getCityId().trim());
						psCity.setString(2, PRODUCT_LOB_ID);
						psCity.setInt(3, circleId);
						rsCity= psCity.executeQuery();
						if(rsCity.next()) {
							cityId = rsCity.getString("CITY_CODE");
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
								return cityId+"#"+cityZoneCode+"#"+zoneCode+"#"+pincode+"#"+rsuCode;
							}
					}
				}
			} catch (Exception e) {
			}finally {
				try {
					DBConnection.releaseResources(null, psCity, rsCity);
					DBConnection.releaseResources(null, psCityZone, rsCityZone);	
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
						return cityId+"#"+cityZoneCode+"#"+zoneCode+"#"+pincode+"#"+rsuCode;
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
			else if(leadDtoObj.getZone() != null && leadDtoObj.getZone().length() > 0){
				
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
			return cityId+"#"+cityZoneCode+"#"+zoneCode+"#"+pincode+"#"+rsuCode;
			//end city, zone code logic. 
		} catch (Exception e) {
			return cityId+"#"+cityZoneCode+"#"+zoneCode+"#"+pincode+"#"+rsuCode;
		}
		
	}
	
	private void updateAddressOfLeadDetails(Connection con,UpdatedLeadDataFirstVersDTO leadDtoObj ,String LeadId) throws DAOException {
		PreparedStatement psLeadDetails =null;
		try {
			psLeadDetails = con.prepareStatement(UPDATE_LEAD_DETAILS_REQUEST_CATEORY);	
			psLeadDetails.setString(1, leadDtoObj.getRequestCategory());
			psLeadDetails.setString(2, LeadId);
			psLeadDetails.executeUpdate();
			if(leadDtoObj.getAddress() !=null || leadDtoObj.getAddress2() !=null){
				StringBuffer stringBuffer  = new StringBuffer ("UPDATE LEAD_PROSPECT_DETAIL SET ");
				if(leadDtoObj.getAddress() !=null){
					stringBuffer.append("ADDRESS1 ='"+leadDtoObj.getAddress()+"'");
				}
				if(leadDtoObj.getAddress2() !=null){
					stringBuffer.append(" ,ADDRESS2 = '"+leadDtoObj.getAddress2()+"'");
				}
				psLeadDetails = con.prepareStatement(stringBuffer.toString()+" WHERE LEAD_ID ="+LeadId+" WITH UR");	
				psLeadDetails.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}finally{
			try {
				DBConnection.releaseResources(null, psLeadDetails, null);	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

	private void insertLeadSchedule(UpdatedLeadDataFirstVersDTO captureDo,String leadId ,int requestCategoryId,Connection con,String productId) throws DAOException {
		PreparedStatement psSubStatus =null;
		PreparedStatement psSubStatus1 =null;
		ResultSet rsSubStatus = null;
		String lead_Id=null;
		try {
			String refoudPaymentProduct  = ServerPropertyReader.getString("4G.leaddata.Product.RefoudPayment");
			psSubStatus1  = con.prepareStatement(SELECT_EXISTING_LEAD);
			psSubStatus1.setString(1, leadId);
			rsSubStatus=psSubStatus1.executeQuery();
			
			if(rsSubStatus.next()) //UPDATE IF EXISTING
			{
				
				StringBuffer query  = new  StringBuffer(UPDATE_EXISTING_LEAD);
				lead_Id=rsSubStatus.getString("LEAD_ID");
								
				if(refoudPaymentProduct !=null && refoudPaymentProduct.contains(productId) ) {
					query.append(",REFUND_STATUS=(SELECT case when REFUND_CATEGORY ='Y' then '1' end  FROM REQUEST_CATEGORY_MSTR where REQUEST_CATEGORY_ID=? and REFUND_CATEGORY ='Y' FETCH FIRST ROW ONLY)"); 
				}
				
				psSubStatus  = con.prepareStatement(query.toString()+" where LEAD_ID="+lead_Id+" WITH UR");
				psSubStatus.setInt(1, requestCategoryId);
				if(refoudPaymentProduct !=null && refoudPaymentProduct.contains(productId) ) {
					psSubStatus.setInt(2, requestCategoryId);
				}
				
				psSubStatus.executeUpdate();
			}
			
			else
			{
				psSubStatus  = con.prepareStatement(INSERT_LEAD_SCHEDULE);
				psSubStatus.setString(1, leadId);
				psSubStatus.setInt(2, requestCategoryId);
				psSubStatus.executeUpdate();
				
			}
		
			
			
		} catch (Exception e) {
			//e.printStackTrace();
			throw new DAOException(e);
		}finally {
			try {
				DBConnection.releaseResources(null, null, rsSubStatus);	
				DBConnection.releaseResources(null, psSubStatus, null);	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	private String isValidLeadIdAndtransaction(String leadId , Connection con ,String trxnId)  throws DAOException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String lead_Id = null ;
		String [] tempTrxId =null;
		Pattern pattern = Pattern.compile(".*[^0-9].*");
		try 
		{
			if((leadId !=null && leadId.trim().length() >0  && !pattern.matcher(leadId).matches())) 
			{
				
				ps = con.prepareStatement(IS_VALID_LEADID);
				ps.setLong(1, Long.parseLong(leadId.toString()));
				rs = ps.executeQuery();
				if(rs.next())
				{
					return rs.getString("LEAD_ID");
				}
			}
		 if(trxnId !=null && trxnId.trim().length() >0)
			{
				//logger.info("transaction id block");
				if(trxnId.contains("-")) {
				 tempTrxId = trxnId.split("-");
				 trxnId = tempTrxId[1];
				}
				if(!pattern.matcher(trxnId).matches()) {
				ps = con.prepareStatement(IS_VALID_TXNID);
				ps.setLong(1, Long.parseLong(trxnId));
				rs = ps.executeQuery();
				if(rs.next())
				{
					return rs.getString("LEAD_ID");
				}
				}
				
			}
		 
		/* if(mobile !=null && mobile.trim().length() >0)
			{
				logger.info("Mobile Number Block");
				if(!pattern.matcher(mobile).matches()) {
				ps = con.prepareStatement(IS_VALID_MOBILE);
				ps.setLong(1, Long.parseLong(mobile));
				ps.setString(2, source);
				ps.setString(3, product);
				rs = ps.executeQuery();
				if(rs.next())
				{
					return rs.getString("LEAD_ID");
				}
				}
				
			}*/
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("error"+e);
			throw new DAOException(e);
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

	
	public UpdateLeadResponseMsg isValidId(UpdatedLeadDataFirstVersDTO updatedLeadDataDTOs)  throws DAOException 
	{
		
		Connection con=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String [] tempTrxId =null;
		String leadId =null;
		String trxnId=null;
		String invalidTxnId="3";
		String validLeadId="8";
		
		GetLeadCaptureData getLeadCaptureData=new GetLeadCaptureData();
		Pattern pattern = Pattern.compile(".*[^0-9].*");
		boolean flag=false;
		UpdateLeadResponseMsg updateLeadResponseMessage = new UpdateLeadResponseMsg();
			try 
		{
			Gson gson  = new Gson();
			
			
			        con = DBConnection.getDBConnection();
					String request  = gson.toJson(updatedLeadDataDTOs);
					logger.info("Request data  from web service==="+request);
					
					//logger.info(request+"************request****************");
					leadId=updatedLeadDataDTOs.getLeadId();
					trxnId=updatedLeadDataDTOs.getTrxnId();
					
					if(trxnId !=null && trxnId.length() >0 && trxnId.contains("-")) 
					{
						 tempTrxId = trxnId.split("-");
						 trxnId = tempTrxId[1];
					}
			
			
			if((leadId !=null && leadId.trim().length() >0 && !pattern.matcher(leadId.trim()).matches()) || (trxnId !=null && trxnId.trim().length() >0 && !pattern.matcher(trxnId.trim()).matches()))
			//	||(phoneNumber !=null && phoneNumber.trim().length()>0 && !pattern.matcher(phoneNumber.trim()).matches())) 
			{
				logger.info("lead Id , trxn Id and phone number vailadtion start ");
				
				
				if(leadId !=null && leadId.trim().length() >0 && !pattern.matcher(leadId.trim()).matches())
				{
					
					ps = con.prepareStatement(IS_VALID_LEADID);
					ps.setLong(1, Long.parseLong(leadId.trim()));
					rs = ps.executeQuery();
					if(!rs.next())  {       //invalid lead id
						
						flag=true;
						updateLeadResponseMessage.setResponseMsg("Invalid Lead ID");
						updateLeadResponseMessage.setResponseCode("2");
					}
					else
					{
						updateLeadResponseMessage.setResponseMsg("Valid Lead Id");
						updateLeadResponseMessage.setResponseCode("8");
					}
				}
				
				//when lead is valid flag is false
				
				if (updateLeadResponseMessage.getResponseCode()!=validLeadId && trxnId != null && trxnId.trim().length() > 0 && !pattern.matcher(trxnId.trim()).matches()) {
						
						ps = con.prepareStatement(IS_VALID_TXNID);
						ps.setLong(1, Long.parseLong(trxnId));
						rs = ps.executeQuery();
						if (!rs.next()) // txn id is invalid
						{
							
							flag = true;
							updateLeadResponseMessage.setResponseMsg("Invalid Transaction ID");
							updateLeadResponseMessage.setResponseCode("3");
							
						} 
						else 
						{
							updateLeadResponseMessage.setResponseMsg("Valid Transaction ID");
							updateLeadResponseMessage.setResponseCode("9");
						}
					}
				
				if(flag)
		        {	
		        	
		        	if(invalidTxnId.equalsIgnoreCase(updateLeadResponseMessage.getResponseCode())) {
		        	String trnsCaptureId = getLeadCaptureData.isValidCaptureId(trxnId);
		        	
		        	//lead creation process start
			         if(trnsCaptureId!=null && trnsCaptureId.trim().length()>0 && !getLeadCaptureData.getLcIsDoneStatus(trxnId))  //lead is not generated for the txn id.
						{
			        	        Long lngsId = Long.parseLong(trxnId.trim());
			        	        
			        	        //check for valid lead id or transaction id before creating lead:
					        	String leadCheckId= isValidLeadIdAndtransaction(leadId.trim(),con,trxnId.trim());
					        	logger.info("leadCheckId******************"+leadCheckId);
					        	if(leadCheckId != null && !("").equalsIgnoreCase(leadCheckId) && leadCheckId.length()<0)
					        	{
					        		updateLeadResponseMessage.setResponseMsg("Valid Transaction ID");
									updateLeadResponseMessage.setResponseCode("9");
					        	}
					        	else
					        	{
						 		List<CaptureLeadDetailsDTO> captureList= GetLeadCaptureData.captureLeadData(lngsId.intValue());
						 		if(captureList.size() >0) {
						 		String message= getLeadCaptureData.insertLeadData(captureList);
						 		logger.info("message in UpdateLeadDataDaoImplNew is******"+message);
						 		if(message.equalsIgnoreCase("Duplicate Lead") || message.equalsIgnoreCase(com.ibm.lms.engine.util.Constants.MSG_INVALID_MOBILE)|| 
						 				message.equalsIgnoreCase(com.ibm.lms.engine.util.Constants.MSG_INVALID_PODUCT) || 
						 				(message.equalsIgnoreCase(com.ibm.lms.engine.util.Constants.MSG_INVALID_FID)))
						 		{
						 			updateLeadResponseMessage.setResponseMsg("Invalid Transaction ID");
									updateLeadResponseMessage.setResponseCode("7");
						 		}
						 		else
						 		{
						 		updateLeadResponseMessage.setResponseMsg("LEAD GENERATION");
								updateLeadResponseMessage.setResponseCode("4");
						 		}
						 		}
								
						}
						}
		        	else {
							updateLeadResponseMessage.setResponseMsg("Invalid Transaction ID");
							updateLeadResponseMessage.setResponseCode("7");
					 	
						}
		        }
		        }
				

			   logger.info("lead Id, trxn Id and phone number validation end ");
			} 
			else {
				updateLeadResponseMessage.setResponseMsg("Invalid Lead/txn id");
				updateLeadResponseMessage.setResponseCode("5");
			}
			 //end for -loop
			
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
		
		return updateLeadResponseMessage;
	}

	

	}
	
		
