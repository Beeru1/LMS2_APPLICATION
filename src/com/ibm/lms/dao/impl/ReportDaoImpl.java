/*
 * Created on jan 15, 2014
 */
package com.ibm.lms.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ibm.lms.common.Constants;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dao.ReportDao;
import com.ibm.lms.dto.DashBoardReportDTO;
import com.ibm.lms.dto.IDOCReportDTO;
import com.ibm.lms.dto.LifeCycleReportDTO;
import com.ibm.lms.dto.MTDReportDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.forms.ReportsFormBean;
/**
 * @author Amarjeet
 */
public class ReportDaoImpl implements ReportDao{

	private static final Logger logger;
	static {
		logger = Logger.getLogger(LeadRegistrationDaoImpl.class);
	}
	
	//query changed by Nancy.
	
	String SQL_SELECT_IDOC_REPORT="SELECT LEAD_ID,PRODUCTTYPE,PRODUCT_LOB_ID,CHANNEL_PARTNER_LMS, CHANNEL_NAME_IDOC as CHANNEL_PARTNER_IDOC,LOCAL_PHONE_NUMBER as PROSPECT_MOBILE_NUMBER,  CAFNO, MOBILENO, ORDERID,DE1STATUS, DE1DATE, TVSTATUS,TVCRMDATE, TVDATE, INWARDSTATUS, INWARDDATE, INWARDUSER, DEAUDITSTATUS, DEAUDITDATE, DEAUDITUSER, EVSTATUS, EVDATE, EVUSER, DDSTATUS as DEDUPE_STATUS, DDDATE as DEDUPE_DATE, SCAN_STATUS, DDEDATE as DE2DATETIME, DDEUSER as DE2_USER, DDESTATUS as DE2_STATUS,WHXDATE, WHXUSER, PASTATUS as PARTY_CREATION_STATUS, PADATE as PARTY_CREATION_DATE, ORDER_CREATION_STATUS, ORDERINITDATE as ORDER_CREATION_DATE_IDOC, ORDER_REMARKS, ORDERCOMPDATE as ORDER_COMPLETION_DATE_IDOC, ORDERUSER as ORDER_CREATION_AGENT_IDOC,LEAD_CREATE_TIME as LEAD_CREATE_TIME, CHANNELID as CHANNEL_CODE,TVREASON,PAREASON as PARTY_CREATION_REASON,EVREASON FROM LEAD_IDOC_STATUS AS li,LEAD_IDOC_STATUS_PARTITION AS lis where li.LEAD_IDOC_STATUS_ID=lis.LEAD_IDOC_STATUS_ID " +
	"group by LEAD_ID,PRODUCTTYPE,PRODUCT_LOB_ID,CHANNEL_PARTNER_LMS, CHANNEL_NAME_IDOC,LOCAL_PHONE_NUMBER, CAFNO, MOBILENO, ORDERID,DE1STATUS,DE1DATE, TVSTATUS,TVCRMDATE, TVDATE, INWARDSTATUS, INWARDDATE, INWARDUSER, DEAUDITSTATUS, DEAUDITDATE, DEAUDITUSER, EVSTATUS, EVDATE,EVUSER, DDSTATUS, DDDATE, SCAN_STATUS, DDEDATE , DDEUSER, DDESTATUS,WHXDATE, WHXUSER, PASTATUS, PADATE, ORDER_CREATION_STATUS,ORDERINITDATE, ORDER_REMARKS, ORDERCOMPDATE, ORDERUSER,LEAD_CREATE_TIME, CHANNELID,DELETE_FLAG,TVREASON,PAREASON,EVREASON having DELETE_FLAG is null";


//String SQL_SELECT_IDOC_REPORT="SELECT LEAD_ID,PRODUCT,PRODUCT_LOB_ID,CHANNEL_PARTNER_LMS, CHANNEL_NAME_IDOC as CHANNEL_PARTNER_IDOC,LOCAL_PHONE_NUMBER as PROSPECT_MOBILE_NUMBER,  CAFNO, MOBILENO, ORDERID,DE1STATUS, DE1DATE, TVSTATUS,TVCRMDATE, TVDATE, INWARDSTATUS, INWARDDATE, INWARDUSER, DEAUDITSTATUS, DEAUDITDATE, DEAUDITUSER, EVSTATUS, EVDATE, EVUSER, DDSTATUS as DEDUPE_STATUS, DDDATE as DEDUPE_DATE, SCAN_STATUS, DDEDATE as DE2DATETIME, DDEUSER as DE2_USER, DDESTATUS as DE2_STATUS,WHXDATE, WHXUSER, PASTATUS as PARTY_CREATION_STATUS, PADATE as PARTY_CREATION_DATE, ORDER_CREATION_STATUS, ORDERINITDATE as ORDER_CREATION_DATE_IDOC, ORDER_REMARKS, ORDERCOMPDATE as ORDER_COMPLETION_DATE_IDOC, ORDERUSER as ORDER_CREATION_AGENT_IDOC,LEAD_CREATE_TIME as LEAD_CREATE_TIME, CHANNELID as CHANNEL_CODE FROM LEAD_IDOC_STATUS AS li group by LEAD_ID,PRODUCT,PRODUCT_LOB_ID,CHANNEL_PARTNER_LMS, CHANNEL_NAME_IDOC,LOCAL_PHONE_NUMBER, CAFNO, MOBILENO, ORDERID,DE1STATUS,DE1DATE, TVSTATUS,TVCRMDATE, TVDATE, INWARDSTATUS, INWARDDATE, INWARDUSER, DEAUDITSTATUS, DEAUDITDATE, DEAUDITUSER, EVSTATUS, EVDATE,EVUSER, DDSTATUS, DDDATE, SCAN_STATUS, DDEDATE , DDEUSER, DDESTATUS,WHXDATE, WHXUSER, PASTATUS, PADATE, ORDER_CREATION_STATUS,ORDERINITDATE, ORDER_REMARKS, ORDERCOMPDATE, ORDERUSER,LEAD_CREATE_TIME, CHANNELID,DELETE_FLAG having DELETE_FLAG='N' ";

	
	String SQL_SELECT_MTD_REPORT = "SELECT LEAD_ID, CUSTOMER_NAME, MOBILE_NUMBER, PRODUCT_LOB, PRODUCT_NAME,PLAN_ID,PLAN, CIRCLE_NAME, CITY, " +
	 		"ZONE_NAME, ZONE, PINCODE, EMAIL, RSU_ID, LEAD_STATUS, LEAD_SUB_STATUS,CAF_NUMBER , TRANSACTION_TIME, CREATE_TIME, SUBMIT_TIME, " +
	 		"PENDING_WITH, PARTNER_NAME, ACTOR_NAME, SOURCE, SUB_SOURCE,REQUEST_TYPE,REQUEST_CATEGORY,UTM_LABELS,UTM_SOURCE,LEAD_CATEGORY," +
	 		"AUTO_ASSIGN, CAMPAIGN, REFERER_PAGE, REFERER_URL, FID, CID , DISPOSITION_RECEIVED , CITY_ZONE_CODE, CITY_ZONE_NAME , " +
	 		"CHANNEL_PARTNER_NAME, ADDRESS , LEAD_PRIORITY , DISPOSITION_COUNT, DISPOSITION_CODE, DISPOSITION_UPDATED_DATE, LEAD_ASSIGNMENT_TIME," +
	 		" DISPOSITION_FIRST_DATE, ASSIGNED_CHANNEL_PARTNER ,INFO_ADEQUATE_PARTNER,SALES_EXECUTIVE_NUMBER,UPDATED_BY,UPDATED_DT,AGENCY_SOURCE,SALES_CHANNEL_CODE,COMPANY,IS_CUSTOMER,LATITUDE,LONGITUDE,LEAD_TXN_ID " +
	 		"from REPORT_LEAD_DETAILS_MTD GROUP BY LEAD_ID, CUSTOMER_NAME, MOBILE_NUMBER, PRODUCT_LOB, PRODUCT_NAME,PLAN_ID,PLAN, CIRCLE_NAME, CITY," +
	 		" ZONE_NAME, ZONE, PINCODE, EMAIL, RSU_ID, LEAD_STATUS, LEAD_SUB_STATUS,CAF_NUMBER, TRANSACTION_TIME, CREATE_TIME, SUBMIT_TIME, " +
	 		"PENDING_WITH, PARTNER_NAME, ACTOR_NAME, SOURCE, SUB_SOURCE,REQUEST_TYPE,REQUEST_CATEGORY,UTM_LABELS,UTM_SOURCE,LEAD_CATEGORY,AUTO_ASSIGN," +
	 		" CAMPAIGN, REFERER_PAGE, REFERER_URL, FID, CID , DISPOSITION_RECEIVED , CITY_ZONE_CODE, CITY_ZONE_NAME , CHANNEL_PARTNER_NAME, ADDRESS , " +
	 		"LEAD_PRIORITY , DISPOSITION_COUNT, DISPOSITION_CODE, DISPOSITION_UPDATED_DATE, LEAD_ASSIGNMENT_TIME, DISPOSITION_FIRST_DATE," +
	 		" ASSIGNED_CHANNEL_PARTNER ,INFO_ADEQUATE_PARTNER,SALES_EXECUTIVE_NUMBER,UPDATED_BY,UPDATED_DT,AGENCY_SOURCE,SALES_CHANNEL_CODE,COMPANY,IS_CUSTOMER,LATITUDE,LONGITUDE,LEAD_TXN_ID  " +
	 		"HAVING TRANSACTION_TIME = max(TRANSACTION_TIME) AND ";
	 
	 String SQL_SELECT_LIFECYCLE_REPORT =" SELECT LEAD_ID, LOB,INSERT_DATE,VERIFICATION_DATE, ASSIGNED_TO_CENTER_DATE, CONTACT_DATE,QUALIFIED_DATE,ASSIGNED_DATE,REASSIGNED,CLOSURE FROM LIFECYCLE_REPORT WHERE ASSIGNED_TO_CENTER_DATE  BETWEEN  ? AND  ? ";
	
	 String SQL_SELECT_DASHBOARD_REPORT_MTD ="SELECT OPEN_VERI ,QUALI_WIR_ASSI, WON ,LOST_UNWIRD ,FEASIBILITY , INF0_INADEQUATE , PRODUCT_LOB, PRODUCT_NAME FROM " +
	 		"(select CASE WHEN LEAD_STATUS_ID IN(200,300) THEN LEAD_COUNT END as OPEN_VERI  , CASE WHEN LEAD_STATUS_ID IN (305,311,400 ) THEN  LEAD_COUNT END as QUALI_WIR_ASSI," +
	 		"CASE WHEN LEAD_STATUS_ID = 500 THEN LEAD_COUNT END as WON,CASE WHEN LEAD_STATUS_ID IN( 600, 315 ) THEN LEAD_COUNT END as LOST_UNWIRD," +
	 		"CASE WHEN LEAD_STATUS_ID = 310 THEN LEAD_COUNT END as FEASIBILITY,CASE WHEN LEAD_STATUS_ID = 320 THEN LEAD_COUNT END as INF0_INADEQUATE , " +
	 		"PRODUCT_ID  FROM (select  COUNT(*) AS LEAD_COUNT  , PRODUCT_ID , LEAD_STATUS_ID  FROM LEAD_DATA WHERE  month(CREATE_TIME) = month (current date - 1 day ) and year(CREATE_TIME) = year(current date - 1 day) and date(CREATE_TIME) != current date " +
	 		" GROUP BY PRODUCT_ID, LEAD_STATUS_ID) GROUP BY PRODUCT_ID , LEAD_STATUS_ID ,LEAD_COUNT ) T,(SELECT PL.PRODUCT_LOB , PM.PRODUCT_NAME , " +
	 		"PM.PRODUCT_ID FROM PRODUCT_MSTR PM , PRODUCT_LOB PL)L WHERE L.PRODUCT_ID = T.PRODUCT_ID GROUP BY PRODUCT_LOB ,PRODUCT_NAME ,  OPEN_VERI ,QUALI_WIR_ASSI,WON ,LOST_UNWIRD ,FEASIBILITY , INF0_INADEQUATE ORDER BY PRODUCT_LOB  WITH  UR ";
	 
	 String SQL_SELECT_DASHBOARD_REPORT_DAILY ="SELECT OPEN_VERI ,QUALI_WIR_ASSI, WON ,LOST_UNWIRD ,FEASIBILITY , INF0_INADEQUATE , PRODUCT_LOB, PRODUCT_NAME FROM " +
		"(select CASE WHEN LEAD_STATUS_ID IN(200,300) THEN LEAD_COUNT END as OPEN_VERI  , CASE WHEN LEAD_STATUS_ID IN (305,311,400 ) THEN  LEAD_COUNT END as QUALI_WIR_ASSI," +
		"CASE WHEN LEAD_STATUS_ID = 500 THEN LEAD_COUNT END as WON,CASE WHEN LEAD_STATUS_ID IN( 600, 315 ) THEN LEAD_COUNT END as LOST_UNWIRD," +
		"CASE WHEN LEAD_STATUS_ID = 310 THEN LEAD_COUNT END as FEASIBILITY,CASE WHEN LEAD_STATUS_ID = 320 THEN LEAD_COUNT END as INF0_INADEQUATE , " +
		"PRODUCT_ID  FROM (select  COUNT(*) AS LEAD_COUNT  , PRODUCT_ID , LEAD_STATUS_ID  FROM LEAD_DATA  WHERE  DATE(CREATE_TIME) = ? " +
		" GROUP BY PRODUCT_ID, LEAD_STATUS_ID) GROUP BY PRODUCT_ID , LEAD_STATUS_ID ,LEAD_COUNT ) T,(SELECT PL.PRODUCT_LOB , PM.PRODUCT_NAME , " +
		"PM.PRODUCT_ID FROM PRODUCT_MSTR PM , PRODUCT_LOB PL)L WHERE L.PRODUCT_ID = T.PRODUCT_ID GROUP BY PRODUCT_LOB ,PRODUCT_NAME ,  OPEN_VERI ,QUALI_WIR_ASSI,WON ,LOST_UNWIRD ,FEASIBILITY , INF0_INADEQUATE ORDER BY PRODUCT_LOB  WITH  UR ";

	 
	 
	 
	 //Added by srikant 
	 
	 private static ReportDaoImpl reportDaoImpl=null;
		
		private ReportDaoImpl(){
			
		}
		
		public static ReportDaoImpl reportDaoInstance()
		{
			if(reportDaoImpl==null)
			{
				reportDaoImpl=new ReportDaoImpl();
			}
			return reportDaoImpl;
			
		}
	 public ArrayList<MTDReportDTO> getLeadMTDReport(ReportsFormBean formBean ) throws DAOException {
		 logger.info("Inside getLeadMTDReport of ReportDaoImpl");
		
			ArrayList<MTDReportDTO> reportList = new ArrayList<MTDReportDTO>();
			MTDReportDTO dto = null;
			Connection con = null;
			PreparedStatement ps = null;			
			ResultSet rs = null;
			String pincode = "";
			String email = "";		
			Date start,end;
			String startDate = null;
			String endDate = null;
			int paramCount = 1;
				
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");			
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");				
				
					if(formBean.getStartDate()!=null && !"".equalsIgnoreCase(formBean.getStartDate()))
					{
						start = (Date) sdf.parse(formBean.getStartDate());
						startDate = dateFormat1.format(start);
					}
				if(formBean.getEndDate()!=null && !"".equalsIgnoreCase(formBean.getEndDate()))
					{
						end = (Date) sdf.parse(formBean.getEndDate());
						endDate = dateFormat1.format(end);
					}	
			
				con = DBConnection.getDBConnection();	 
				StringBuffer query = new StringBuffer(SQL_SELECT_MTD_REPORT);
				
				logger.info("Actor Id:" + formBean.getUserActorId());
				
			
				    if(startDate ==null && endDate ==null && (formBean.getLobId().equals(null) || formBean.getLobId().equals("")) && formBean.getCircleMstrId()==0 && formBean.getStatusId()==0 && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase("")) )
						{
						logger.info("no input provided!");
						}
				    else if(startDate !=null && !"".equalsIgnoreCase(startDate)&& endDate !=null && !"".equalsIgnoreCase(endDate))
					{
						 if(formBean.getStatusId()!=0 && !formBean.getLobId().equals(null) && !formBean.getLobId().equals("") && formBean.getCircleMstrId()!=0 && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase("")))
							{
								//query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? )  AND LEAD_STATUS = (select LEAD_STATUS from LEAD_STATUS where LEAD_STATUS_ID = ? ) AND CIRCLE_NAME = (SELECT DISTINCT(CIRCLE_NAME) FROM CIRCLE_MSTR WHERE CIRCLE_MSTR_ID = ?) AND TRANSACTION_TIME BETWEEN  ? AND  ? ");
								query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? )  AND LEAD_STATUS = (select LEAD_STATUS from LEAD_STATUS where LEAD_STATUS_ID = ? ) AND CIRCLE_NAME = (SELECT DISTINCT(CIRCLE_NAME) FROM CIRCLE_MSTR WHERE CIRCLE_MSTR_ID = ?)");
							}
						
				  
						else if(formBean.getStatusId()!=0 && !formBean.getLobId().equals(null) && !formBean.getLobId().equals("")&& formBean.getCircleMstrId()!=0  && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase("")))
							{
								query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? )  AND LEAD_STATUS = (select LEAD_STATUS from LEAD_STATUS where LEAD_STATUS_ID = ? ) AND CIRCLE_NAME = (SELECT DISTINCT(CIRCLE_NAME) FROM CIRCLE_MSTR WHERE CIRCLE_MSTR_ID = ?)");				
							}
						
				
						else if((formBean.getLobId().equals(null) || formBean.getLobId().equals("")) && formBean.getCircleMstrId()==0 && formBean.getStatusId()==0 && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase("")))
							{
								
							}
						
				
						else if(!formBean.getLobId().equals(null) && !formBean.getLobId().equals("") && formBean.getCircleMstrId()==0 && formBean.getStatusId()==0  && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase("")))
							{
								query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? )")	;
							}
					
				
						else if(formBean.getCircleMstrId()!=0 && formBean.getStatusId()==0 && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase("")))
							{
								//query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? ) AND CIRCLE_NAME = (SELECT DISTINCT(CIRCLE_NAME) FROM CIRCLE_MSTR WHERE CIRCLE_MSTR_ID = ?) AND TRANSACTION_TIME BETWEEN  ? AND  ? ")	;
								query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? ) AND CIRCLE_NAME = (SELECT DISTINCT(CIRCLE_NAME) FROM CIRCLE_MSTR WHERE CIRCLE_MSTR_ID = ?)")	;
							}
					
				
					else if(formBean.getStatusId()!=0 && (formBean.getLobId().equals(null) || formBean.getLobId().equals("")) && formBean.getCircleMstrId()==0 && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase("")))
							{
						      //query.append(" LEAD_STATUS = (select LEAD_STATUS from LEAD_STATUS where LEAD_STATUS_ID = ? ) AND TRANSACTION_TIME BETWEEN  ? AND  ? ");
							   query.append(" LEAD_STATUS = (select LEAD_STATUS from LEAD_STATUS where LEAD_STATUS_ID = ? )");
							}
						
					
					else if(formBean.getStatusId()!=0 && !formBean.getLobId().equals(null) && !formBean.getLobId().equals("")&& formBean.getCircleMstrId()==0 &&(formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase("")))
							{
						    //query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? )  AND LEAD_STATUS = (select LEAD_STATUS from LEAD_STATUS where LEAD_STATUS_ID = ? AND TRANSACTION_TIME BETWEEN  ? AND  ?) ");
							query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? )  AND LEAD_STATUS = (select LEAD_STATUS from LEAD_STATUS where LEAD_STATUS_ID = ?)");
							}
						
						
					else if(formBean.getStatusId()!=0 && !formBean.getLobId().equals(null) && !formBean.getLobId().equals("") && formBean.getCircleMstrId()!=0 && !formBean.getChPartnerId().equalsIgnoreCase(null) && !formBean.getChPartnerId().equalsIgnoreCase(""))
							{
								//query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? )  AND LEAD_STATUS = (select LEAD_STATUS from LEAD_STATUS where LEAD_STATUS_ID = ? ) AND CIRCLE_NAME = (SELECT DISTINCT(CIRCLE_NAME) FROM CIRCLE_MSTR WHERE CIRCLE_MSTR_ID = ?) AND TRANSACTION_TIME BETWEEN  ? AND  ?  AND PENDING_WITH = ?  ");
								query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? )  AND LEAD_STATUS = (select LEAD_STATUS from LEAD_STATUS where LEAD_STATUS_ID = ? ) AND CIRCLE_NAME = (SELECT DISTINCT(CIRCLE_NAME) FROM CIRCLE_MSTR WHERE CIRCLE_MSTR_ID = ?) AND PENDING_WITH = ?  ");
							}
						
						
					else if(!formBean.getChPartnerId().equalsIgnoreCase(null) && !formBean.getChPartnerId().equalsIgnoreCase("") && formBean.getStatusId()==0)
							{
								//query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? ) AND CIRCLE_NAME = (SELECT DISTINCT(CIRCLE_NAME) FROM CIRCLE_MSTR WHERE CIRCLE_MSTR_ID = ?)  AND PENDING_WITH = ? AND TRANSACTION_TIME BETWEEN  ? AND  ? ");
								query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? ) AND CIRCLE_NAME = (SELECT DISTINCT(CIRCLE_NAME) FROM CIRCLE_MSTR WHERE CIRCLE_MSTR_ID = ?)  AND PENDING_WITH = ? ");
							}
				
							//query.append("  TRANSACTION_TIME BETWEEN  ? AND  ?  ");
							logger.info("value of getSelectType is*******"+ formBean.getSelectType());
							if(formBean.getSelectType()!=null && formBean.getSelectType().equals("1"))
							{
								query.append(" AND Date(UPDATED_DT) >= ? and  Date(UPDATED_DT) <= ?   ");
							}else 
							{
							query.append("AND Date(CREATE_TIME) >= ? and  Date(CREATE_TIME) <= ? ");
							}
							
					}
				    else 
				    {
				    	
					if(!formBean.getLobId().equals(null) && !formBean.getLobId().equals("") && formBean.getCircleMstrId()==0 && formBean.getStatusId()==0 && startDate == null  && endDate== null && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase("")))
						{
						query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? ) ")	;
						}
					
				
				    else if(!formBean.getLobId().equals(null) && !formBean.getLobId().equals("") && formBean.getCircleMstrId()!=0 && formBean.getStatusId()==0 && startDate == null && endDate== null && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase("")))
					    {
						query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? ) AND CIRCLE_NAME = (SELECT DISTINCT(CIRCLE_NAME) FROM CIRCLE_MSTR WHERE CIRCLE_MSTR_ID = ?) ")	;
					    }
					
				
				    else if(formBean.getStatusId()!=0 && (formBean.getLobId().equals(null) || formBean.getLobId().equals("")) && formBean.getCircleMstrId()==0 && startDate == null  && endDate== null && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase("")))
						{
							query.append(" LEAD_STATUS = (select LEAD_STATUS from LEAD_STATUS where LEAD_STATUS_ID = ? )");
						}
				
				 
				     else if(formBean.getStatusId()!=0 && !formBean.getLobId().equals(null) && !formBean.getLobId().equals("") && formBean.getCircleMstrId()==0 && (startDate == null  && endDate== null && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase(""))))
					     {
						query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? )  AND LEAD_STATUS = (select LEAD_STATUS from LEAD_STATUS where LEAD_STATUS_ID = ? ) ");
					     }

				
					else if(!formBean.getChPartnerId().equalsIgnoreCase(null) && !formBean.getChPartnerId().equalsIgnoreCase("")&& startDate == null  && endDate== null && formBean.getStatusId()==0)
						{
							query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? ) AND CIRCLE_NAME = (SELECT DISTINCT(CIRCLE_NAME) FROM CIRCLE_MSTR WHERE CIRCLE_MSTR_ID = ?)  AND PENDING_WITH = ? ");
						}
					
			
					else if(!formBean.getChPartnerId().equalsIgnoreCase(null) && !formBean.getChPartnerId().equalsIgnoreCase("") && formBean.getStatusId()!=0 && startDate == null  && endDate== null)
						{
							query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? ) AND CIRCLE_NAME = (SELECT DISTINCT(CIRCLE_NAME) FROM CIRCLE_MSTR WHERE CIRCLE_MSTR_ID = ?)  AND PENDING_WITH = ? AND LEAD_STATUS = (select LEAD_STATUS from LEAD_STATUS where LEAD_STATUS_ID = ? ) ");
						}
							
			
					else if((formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase(""))&& formBean.getStatusId()!= 0 && startDate ==null && endDate ==null && !formBean.getLobId().equals(null) && !formBean.getLobId().equals("") && formBean.getCircleMstrId()!=0)
						{
							query.append(" PRODUCT_LOB = (SELECT PRODUCT_LOB FROM PRODUCT_LOB WHERE PRODUCT_LOB_ID = ? ) AND CIRCLE_NAME = (SELECT DISTINCT(CIRCLE_NAME) FROM CIRCLE_MSTR WHERE CIRCLE_MSTR_ID = ?) AND LEAD_STATUS = (select LEAD_STATUS from LEAD_STATUS where LEAD_STATUS_ID = ? ) ");
						}
					
				    }
				
				
					if(formBean.getZoneCode()!=null)
					 {
						logger.info("formBean.getZoneCode()"+formBean.getZoneCode());
						logger.info("formBean.getZoneFlag().equalsIgnoreCase(Constants.CITY_ZONE_CODE_FLAG_VALUE)"+formBean.getZoneFlag().equalsIgnoreCase(Constants.CITY_ZONE_CODE_FLAG_VALUE));
							if(formBean.getZoneFlag().equalsIgnoreCase(Constants.CITY_ZONE_CODE_FLAG_VALUE)){
								query.append("AND CITY_ZONE_CODE = ?  ");
							} else if(formBean.getZoneFlag().equalsIgnoreCase(Constants.ZONE_CODE_FLAG_VALUE)){
								query.append("AND ZONE =  ? ");
							}
							
						}
				query.append(" WITH UR");
				logger.info("query leadmtd**************"+query);
				
				
				ps=con.prepareStatement(query.toString());
				
				logger.info("formBean.getZoneCode()"+formBean.getZoneCode());
				if(!formBean.getLobId().equals(null) && !formBean.getLobId().equals("") && formBean.getCircleMstrId()==0 && formBean.getStatusId()==0 && startDate == null  && endDate== null && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase(""))){
					ps.setInt(paramCount++, Integer.parseInt(formBean.getLobId()));
				}
				else if(!formBean.getLobId().equals(null) && !formBean.getLobId().equals("") && formBean.getCircleMstrId()!=0 && formBean.getStatusId()==0 && startDate == null  && endDate== null && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase(""))){
					logger.info("formBean.getZoneCode()"+formBean.getZoneCode());
					ps.setInt(paramCount++, Integer.parseInt(formBean.getLobId()));
					ps.setInt(paramCount++, formBean.getCircleMstrId());
				}
				else if(formBean.getStatusId()!=0 &&(formBean.getLobId().equals(null) || formBean.getLobId().equals("")) && formBean.getCircleMstrId()==0 && startDate == null  && endDate== null && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase(""))){
					
					ps.setInt(paramCount++, formBean.getStatusId());
				}
				else if(formBean.getStatusId()!=0 && !formBean.getLobId().equals(null) && !formBean.getLobId().equals("") && formBean.getCircleMstrId()==0 && startDate == null  && endDate== null && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase(""))){
					
					ps.setInt(paramCount++, Integer.parseInt(formBean.getLobId()));
					ps.setInt(paramCount++, formBean.getStatusId());
				}
				else if(formBean.getStatusId()!=0 && !formBean.getLobId().equals(null) && !formBean.getLobId().equals("") && formBean.getCircleMstrId()!=0 && startDate !=null && !"".equalsIgnoreCase(startDate) && endDate !=null && !"".equalsIgnoreCase(endDate) && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase(""))){
					
					ps.setInt(paramCount++, Integer.parseInt(formBean.getLobId()));
					ps.setInt(paramCount++, formBean.getStatusId());
					ps.setInt(paramCount++, formBean.getCircleMstrId());
					ps.setDate(paramCount++, Utility.getSqlDateFromString(startDate, "MM/dd/yyyy"));
					ps.setDate(paramCount++, Utility.getSqlDateFromString(endDate, "MM/dd/yyyy"));
				}
				else if(startDate !=null && !"".equalsIgnoreCase(startDate) && endDate !=null && !"".equalsIgnoreCase(endDate) 
						&& (formBean.getLobId().equals(null) || formBean.getLobId().equals("")) && formBean.getCircleMstrId()==0 
						&& formBean.getStatusId()==0 && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase("")) ){
						
					ps.setDate(paramCount++, Utility.getSqlDateFromString(startDate, "MM/dd/yyyy"));
					ps.setDate(paramCount++, Utility.getSqlDateFromString(endDate, "MM/dd/yyyy"));
				}
				else if(!formBean.getLobId().equals(null) && !formBean.getLobId().equals("") && formBean.getCircleMstrId()==0 && formBean.getStatusId()==0 && startDate !=null && !"".equalsIgnoreCase(startDate) && endDate !=null && !"".equalsIgnoreCase(endDate) && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase(""))){
					
					ps.setInt(paramCount++, Integer.parseInt(formBean.getLobId()));
					ps.setDate(paramCount++, Utility.getSqlDateFromString(startDate, "MM/dd/yyyy"));
					ps.setDate(paramCount++, Utility.getSqlDateFromString(endDate, "MM/dd/yyyy"));
				}
				else if(formBean.getCircleMstrId()!=0 && formBean.getStatusId()==0 && startDate !=null && !"".equalsIgnoreCase(startDate) && endDate !=null && !"".equalsIgnoreCase(endDate) && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase(""))){
					
					ps.setInt(paramCount++, Integer.parseInt(formBean.getLobId()));
					ps.setInt(paramCount++, formBean.getCircleMstrId());
					ps.setDate(paramCount++, Utility.getSqlDateFromString(startDate, "MM/dd/yyyy"));
					ps.setDate(paramCount++, Utility.getSqlDateFromString(endDate, "MM/dd/yyyy"));
				}
				else if(formBean.getStatusId()!=0 && (formBean.getLobId().equals(null) || formBean.getLobId().equals("")) && formBean.getCircleMstrId()==0 && startDate !=null && !"".equalsIgnoreCase(startDate) && endDate !=null && !"".equalsIgnoreCase(endDate) && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase(""))){
					
					ps.setInt(paramCount++, formBean.getStatusId());
					ps.setDate(paramCount++, Utility.getSqlDateFromString(startDate, "MM/dd/yyyy"));
					ps.setDate(paramCount++, Utility.getSqlDateFromString(endDate, "MM/dd/yyyy"));
				}
				else if(formBean.getStatusId()!=0 && !formBean.getLobId().equals(null) && !formBean.getLobId().equals("") && formBean.getCircleMstrId()==0 && startDate !=null && !"".equalsIgnoreCase(startDate) && endDate !=null && !"".equalsIgnoreCase(endDate) && (formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase(""))){
					
					ps.setInt(paramCount++, Integer.parseInt(formBean.getLobId()));
					ps.setInt(paramCount++, formBean.getStatusId());
					ps.setDate(paramCount++, Utility.getSqlDateFromString(startDate, "MM/dd/yyyy"));
					ps.setDate(paramCount++, Utility.getSqlDateFromString(endDate, "MM/dd/yyyy"));
				}
				else if(!formBean.getChPartnerId().equalsIgnoreCase(null) && !formBean.getChPartnerId().equalsIgnoreCase("") && startDate == null  && endDate== null && formBean.getStatusId()==0)
				{
					ps.setInt(paramCount++, Integer.parseInt(formBean.getLobId()));
					ps.setInt(paramCount++, formBean.getCircleMstrId());
					ps.setString(paramCount++, formBean.getChPartnerId());
				}
				else if(!formBean.getChPartnerId().equalsIgnoreCase(null) && !formBean.getChPartnerId().equalsIgnoreCase("") && formBean.getStatusId()!=0 && startDate == null  && endDate== null)
				{
					ps.setInt(paramCount++, Integer.parseInt(formBean.getLobId()));
					ps.setInt(paramCount++, formBean.getCircleMstrId());
					ps.setString(paramCount++, formBean.getChPartnerId());
					ps.setInt(paramCount++, formBean.getStatusId());
				}
				else if(formBean.getStatusId()!=0 && !formBean.getLobId().equals(null) && !formBean.getLobId().equals("") && formBean.getCircleMstrId()!=0 && startDate !=null && !"".equalsIgnoreCase(startDate) && endDate !=null && !"".equalsIgnoreCase(endDate) && !formBean.getChPartnerId().equalsIgnoreCase(null) && !formBean.getChPartnerId().equalsIgnoreCase("")){
					
					ps.setInt(paramCount++, Integer.parseInt(formBean.getLobId()));
					ps.setInt(paramCount++, formBean.getStatusId());
					ps.setInt(paramCount++, formBean.getCircleMstrId());
					ps.setDate(paramCount++, Utility.getSqlDateFromString(startDate, "MM/dd/yyyy"));
					ps.setDate(paramCount++, Utility.getSqlDateFromString(endDate, "MM/dd/yyyy"));
					ps.setString(paramCount++, formBean.getChPartnerId());
				}
				else if(!formBean.getChPartnerId().equalsIgnoreCase(null) && !formBean.getChPartnerId().equalsIgnoreCase("") 
						&& formBean.getStatusId()==0 && startDate !=null && !"".equalsIgnoreCase(startDate) && endDate !=null 
						&& !"".equalsIgnoreCase(endDate))
				{
					ps.setInt(paramCount++, Integer.parseInt(formBean.getLobId()));					
					ps.setInt(paramCount++, formBean.getCircleMstrId());
					ps.setString(paramCount++, formBean.getChPartnerId());
					ps.setDate(paramCount++, Utility.getSqlDateFromString(startDate, "MM/dd/yyyy"));
					ps.setDate(paramCount++, Utility.getSqlDateFromString(endDate, "MM/dd/yyyy"));
				
				}
				else if((formBean.getChPartnerId().equalsIgnoreCase(null) || formBean.getChPartnerId().equalsIgnoreCase("") )
						&& formBean.getStatusId()!= 0 && startDate ==null && endDate ==null && !formBean.getLobId().equals(null) && !formBean.getLobId().equals("") 
						&& formBean.getCircleMstrId()!=0 )
				{
					ps.setInt(paramCount++, Integer.parseInt(formBean.getLobId()));					
					ps.setInt(paramCount++, formBean.getCircleMstrId());
					ps.setInt(paramCount++, formBean.getStatusId());
				}
				
			else if(!formBean.getZoneCode().equalsIgnoreCase("") || formBean.getZoneCode()!=null)
				{
					logger.info("formBean.getZoneCode()"+formBean.getZoneCode());
					logger.info("test");
				 if(formBean.getZoneFlag().equalsIgnoreCase(Constants.CITY_ZONE_CODE_FLAG_VALUE)){
						ps.setString(paramCount++, formBean.getZoneCode());
					} else if(formBean.getZoneFlag().equalsIgnoreCase(Constants.ZONE_CODE_FLAG_VALUE)){
						ps.setString(paramCount++, formBean.getZoneCode());
					}
				}
				
				
				//logger.info("SELECT_LEAD_DETAILS "+query.toString());
				logger.info("query.toString()*******"+query.toString());
				rs=ps.executeQuery();
			
				
				while(rs.next())
				{
					
					dto = new MTDReportDTO();
					dto.setLeadId(rs.getString("LEAD_ID"));
					dto.setCustomerName(rs.getString("CUSTOMER_NAME"));
					dto.setCustomerMobile(rs.getString("MOBILE_NUMBER"));
					dto.setLobName(rs.getString("PRODUCT_LOB"));
					dto.setProductName(rs.getString("PRODUCT_NAME"));
					dto.setPlanId(rs.getString("PLAN_ID"));
					dto.setPlan(rs.getString("PLAN"));
					dto.setCircleName(rs.getString("CIRCLE_NAME"));
					dto.setCityName(rs.getString("CITY"));
					dto.setZoneName(rs.getString("ZONE_NAME"));
					dto.setZoneCode(rs.getString("ZONE"));
					pincode = rs.getString("PINCODE");
					
					int pinCode;
					if(pincode!=null && pincode!="")
						 pinCode = Integer.parseInt(pincode);
					else
						pinCode = 0;
					dto.setPinCode(pinCode);
					email = rs.getString("EMAIL");
					dto.setEmail(email);
					dto.setRsuId(rs.getString("RSU_ID"));
					dto.setLeadStatus(rs.getString("LEAD_STATUS"));
					dto.setLeadSubStatus(rs.getString("LEAD_SUB_STATUS"));
					dto.setCafNumber(rs.getString("CAF_NUMBER"));
					
					String transactionTime = null;					
					SimpleDateFormat sdfTransaction = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
					if(rs.getTimestamp("TRANSACTION_TIME") != null){
						transactionTime = sdfTransaction.format(rs.getTimestamp("TRANSACTION_TIME"));
					}
					dto.setTransactionTime(transactionTime);
					
					String createTime = null;					
					SimpleDateFormat sdfCreate = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
					if(rs.getTimestamp("CREATE_TIME") != null){
						createTime = sdfCreate.format(rs.getTimestamp("CREATE_TIME"));
					}
					
					dto.setCreateTime(createTime);
					dto.setSubmitTime(rs.getString("SUBMIT_TIME"));
					dto.setPendingWith(rs.getString("PENDING_WITH"));
					dto.setPartnerName(rs.getString("PARTNER_NAME"));
					dto.setActorName(rs.getString("ACTOR_NAME"));
					dto.setSource(rs.getString("SOURCE"));
					dto.setSubSource(rs.getString("SUB_SOURCE"));
					dto.setRequestType(rs.getString("REQUEST_TYPE"));
					dto.setRequestCategory(rs.getString("REQUEST_CATEGORY"));
					dto.setUtmLabels(rs.getString("UTM_LABELS"));
					dto.setUtmSource(rs.getString("UTM_SOURCE"));
					dto.setLeadCategory(rs.getString("LEAD_CATEGORY"));
					dto.setAutoAssign(rs.getString("AUTO_ASSIGN"));
					dto.setCampaign(rs.getString("CAMPAIGN"));
					dto.setReferPage(rs.getString("REFERER_PAGE"));
					dto.setReferUrl(rs.getString("REFERER_URL"));
					dto.setFid(rs.getString("FID"));
					dto.setCid(rs.getString("CID"));
					dto.setDispRecieved(rs.getString("DISPOSITION_RECEIVED"));
					dto.setCityZoneName(rs.getString("CITY_ZONE_NAME"));
					dto.setChannalPartnerName(rs.getString("CHANNEL_PARTNER_NAME"));
					
					dto.setAddress(rs.getString("ADDRESS"));
					dto.setLaedPriority(rs.getInt("LEAD_PRIORITY"));
					dto.setCompany(rs.getString("COMPANY"));   //added by Nancy
					dto.setIsCustomer(rs.getString("IS_CUSTOMER")); //added by Nancy
					dto.setCompany(rs.getString("COMPANY"));   //added by Nancy
					dto.setTransactionId(rs.getString("LEAD_TXN_ID")); //added by Nancy
					
					dto.setUserActor(formBean.getUserActorId());
					
					/* Added By parnika on 20 May 2014 */
		
					dto.setDispositionCount(rs.getString("DISPOSITION_COUNT"));
					dto.setDispositionCode(rs.getString("DISPOSITION_CODE"));
					
					String dispositionDate = null;
					
					SimpleDateFormat sdfDisposition = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
					if(rs.getTimestamp("DISPOSITION_UPDATED_DATE") != null){
						dispositionDate = sdfDisposition.format(rs.getTimestamp("DISPOSITION_UPDATED_DATE"));
					}
					dto.setLastDispositionDate(dispositionDate);
					
					String assignDate = null;
					
					SimpleDateFormat sdfAssign = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
					if(rs.getTimestamp("LEAD_ASSIGNMENT_TIME") != null){
						assignDate = sdfAssign.format(rs.getTimestamp("LEAD_ASSIGNMENT_TIME"));
					}
					dto.setLeadAssignmentDate(assignDate);
					
					String firstDispositionDate = null;
					SimpleDateFormat sdfFirstDisposition = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
					if(rs.getTimestamp("DISPOSITION_FIRST_DATE") != null){
						firstDispositionDate = sdfFirstDisposition.format(rs.getTimestamp("DISPOSITION_FIRST_DATE"));
					}					
					dto.setFirstDispositionDate(firstDispositionDate);
					
					dto.setAssignedChannelPartner(rs.getString("ASSIGNED_CHANNEL_PARTNER"));
					dto.setInfo_done_olmId(rs.getString("INFO_ADEQUATE_PARTNER"));
					dto.setSalesExecutiveNumber(rs.getString("SALES_EXECUTIVE_NUMBER"));
					
					dto.setUpdatedBy(rs.getString("UPDATED_BY"));
					//added by nancy
					dto.setAgencySource(rs.getString("AGENCY_SOURCE")); 
					dto.setSalesChannelCode(rs.getString("SALES_CHANNEL_CODE"));
					dto.setLatitude(rs.getString("LATITUDE")); 
					dto.setLongitude(rs.getString("LONGITUDE"));
					/* End of changes By beeru */
					
					reportList.add(dto);
					
				}				
				}	
			
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error(ex);
			}
			finally
			{
				try {
					DBConnection.releaseResources(con,ps, rs);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					logger.error(ex);
				}
				
			}
			return reportList;
			
		}
	
	 
	 public ArrayList<MTDReportDTO> getLeadMTDReportDayMonthwise(ReportsFormBean formBean,HttpServletRequest request) throws DAOException {
		  
		 	logger.info("Inside getLeadMTDReportDayMonthwise of ReportDaoImpl");
		    ArrayList<MTDReportDTO> reportList = new ArrayList<MTDReportDTO>();
			MTDReportDTO dto = null;
			Connection con = null;
			PreparedStatement ps = null;			
			ResultSet rs = null;
			ResultSet rs1 = null;
			StringBuffer query =null;
			int paramCount=1;
		
			try
			{
				con = DBConnection.getDBConnection();	 
				UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
				String query1="select LOB_ID,CIRCLE_ID from USER_MAPPING where USER_LOGIN_ID=? order by TRANSACTION_TIME desc fetch first row only";
				ps= con.prepareStatement(query1);
				ps.setString(paramCount++, userBean.getUserLoginId());
				rs1 = ps.executeQuery();
				
				while(rs1.next()){
					formBean.setLobId(String.valueOf(rs1.getInt(1)));
					formBean.setCircleName(String.valueOf(rs1.getInt(2)));
				}
				
			
				if(formBean.getReportTime().equals("27")){
					
					if("3".equalsIgnoreCase(userBean.getKmActorId()))
					{
						 query = new StringBuffer("select COLUMN_NAME,COUNT,PERCENTAGE,REPORT_TYPE,HEADER_ID  from REPORT_COUNTS_DATA where UCASE(REPORT_TYPE)=UCASE('ftd') and month(CREATED_DT)= month (current date) and  year(CREATED_DT)= year (current date)  and PRODUCT_LOB_ID=? and CIRCLE=? order by HEADER_ID  with ur");
						 ps=con.prepareStatement(query.toString());
						 ps.setString(1, formBean.getLobId());
						 ps.setString(2, formBean.getCircleName());
						 rs=ps.executeQuery();
					
					}
					else
					{
						 query = new StringBuffer("select * from REPORT_COUNTS_DATA where UCASE(REPORT_TYPE)=UCASE('ftd') and (CREATED_DT=current date or date(UPDATED_DT)=current_date ) and CIRCLE=-10 order by HEADER_ID  with ur");
						 ps=con.prepareStatement(query.toString());
							rs=ps.executeQuery();
					}
					
			
				}else if(formBean.getReportTime().equals("28")){
					
					if("3".equalsIgnoreCase(userBean.getKmActorId()))
					{
						 query = new StringBuffer("select COLUMN_NAME,COUNT,PERCENTAGE,REPORT_TYPE,HEADER_ID  from REPORT_COUNTS_DATA where UCASE(REPORT_TYPE)=UCASE('mtd') and month(CREATED_DT)= month (current date) and  year(CREATED_DT)= year (current date)  and PRODUCT_LOB_ID=? and CIRCLE=? order by HEADER_ID  with ur");
						 ps=con.prepareStatement(query.toString());
						 ps.setString(1, formBean.getLobId());
						 ps.setString(2, formBean.getCircleName());
						 rs=ps.executeQuery();
					}
					else
					{
						 query = new StringBuffer("select * from REPORT_COUNTS_DATA where UCASE(REPORT_TYPE)=UCASE('mtd') and month(CREATED_DT)= month (current date) and  year(CREATED_DT)= year (current date)   and  CIRCLE=-10 order by HEADER_ID   with ur");
						 ps=con.prepareStatement(query.toString());
							rs=ps.executeQuery();
					}
				}
                 else if(formBean.getReportTime().equals("30")){
					
					if("3".equalsIgnoreCase(userBean.getKmActorId()))
					{
						 query = new StringBuffer("select COLUMN_NAME,COUNT,PERCENTAGE,REPORT_TYPE,HEADER_ID  from REPORT_COUNTS_DATA where UCASE(REPORT_TYPE)=UCASE('mtd') and month(CREATED_DT)= month (current date) and  year(CREATED_DT)= year (current date)  and PRODUCT_LOB_ID=? and CIRCLE=? order by HEADER_ID  with ur");
						 ps=con.prepareStatement(query.toString());
						 ps.setString(1, formBean.getLobId());
						 ps.setString(2, formBean.getCircleName());
						 rs=ps.executeQuery();
					}
					else
					{
						 query = new StringBuffer("select * from REPORT_COUNTS_DATA where UCASE(REPORT_TYPE)=UCASE('mtd') and month(CREATED_DT)= month (current date) and  year(CREATED_DT)= year (current date)   and  CIRCLE=-10 order by HEADER_ID   with ur");
						 ps=con.prepareStatement(query.toString());
							rs=ps.executeQuery();
					}
				}
				while(rs.next()) {
					
					dto = new MTDReportDTO();
					dto.setColumnName(rs.getString("COLUMN_NAME"));
					dto.setCount(String.valueOf(rs.getInt("COUNT")));
					dto.setPercent(String.valueOf(rs.getInt("PERCENTAGE")).concat("%"));
					dto.setReportType(rs.getString("REPORT_TYPE"));
					dto.setHeaderid(rs.getString("HEADER_ID"));
					reportList.add(dto);
					
				}
				
					}	
			
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error(ex);
			}
			finally
			{
				try {
					DBConnection.releaseResources(con,ps, rs);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					logger.error(ex);
				}
			}
		    return reportList;
		}
	 
	 public ArrayList<MTDReportDTO> getLeadMTDReportDayMonthwiseGridPopulate(ReportsFormBean formBean,HttpServletRequest request ) throws DAOException {
		 logger.info("Inside getLeadMTDReportDAYMonthwiseGridPopulate reportdaoimpl");
		
			ArrayList<MTDReportDTO> reportList = new ArrayList<MTDReportDTO>();
			MTDReportDTO dto = null;
			Connection con = null;
			PreparedStatement ps = null;			
			ResultSet rs = null;
			ResultSet rs1 = null;
			StringBuffer query =null;
			int paramCount=1;
		
	        try
			{
		        con = DBConnection.getDBConnection();	 
				UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
				String query1="select LOB_ID,CIRCLE_ID from USER_MAPPING where USER_LOGIN_ID=? order by TRANSACTION_TIME desc fetch first row only";
				ps= con.prepareStatement(query1);
				ps.setString(paramCount++, userBean.getUserLoginId());
				rs1 = ps.executeQuery();
				
				while(rs1.next()){
					formBean.setLobId(String.valueOf(rs1.getInt(1)));
					formBean.setCircleName(String.valueOf(rs1.getInt(2)));
				}
			
				if("27".equalsIgnoreCase(formBean.getReportTime())){ // for daily wise report
					
					if("3".equalsIgnoreCase(userBean.getKmActorId()))
					{
						 query = new StringBuffer("select COLUMN_NAME,COUNT,PERCENTAGE,REPORT_TYPE,HEADER_ID  from REPORT_COUNTS_DATA where UCASE(REPORT_TYPE)=UCASE('ftd') and month(CREATED_DT)= month (current date) and  year(CREATED_DT)= year (current date)  and PRODUCT_LOB_ID=? and CIRCLE=? order by HEADER_ID with ur");
						 ps=con.prepareStatement(query.toString());
						 ps.setString(1, formBean.getLobId());
						 ps.setString(2, formBean.getCircleName());
						 rs=ps.executeQuery();
					
					}
					else
					{
						 query = new StringBuffer("select * from REPORT_COUNTS_DATA where UCASE(REPORT_TYPE)=UCASE('ftd') and (CREATED_DT=current date or date(UPDATED_DT)=current_date )  and CIRCLE=-10 order by HEADER_ID  with ur");
						 ps=con.prepareStatement(query.toString());
							rs=ps.executeQuery();
					}
				}else if("28".equalsIgnoreCase(formBean.getReportTime())){ // for month  wise report
				
					if("3".equalsIgnoreCase(userBean.getKmActorId()))
					{
						query = new StringBuffer("select COLUMN_NAME,COUNT,PERCENTAGE,REPORT_TYPE,HEADER_ID  from REPORT_COUNTS_DATA where UCASE(REPORT_TYPE)=UCASE('mtd') and month(CREATED_DT)= month (current date) and  year(CREATED_DT)= year (current date)  and PRODUCT_LOB_ID=? and CIRCLE=? order by HEADER_ID  with ur");
						 ps=con.prepareStatement(query.toString());
						 ps.setString(1, formBean.getLobId());
						 ps.setString(2, formBean.getCircleName());
						 rs=ps.executeQuery();
					}
					else
					{
						 query = new StringBuffer("select * from REPORT_COUNTS_DATA where UCASE(REPORT_TYPE)=UCASE('mtd') and month(CREATED_DT)= month (current date) and  year(CREATED_DT)= year (current date)   and CIRCLE=-10 order by HEADER_ID  with ur");
						 ps=con.prepareStatement(query.toString());
							rs=ps.executeQuery();
					}
				}
				else if("30".equalsIgnoreCase(formBean.getReportTime())){ // for month  wise report
					
					if("3".equalsIgnoreCase(userBean.getKmActorId()))
					{
						query = new StringBuffer("select COLUMN_NAME,COUNT,PERCENTAGE,REPORT_TYPE,HEADER_ID  from REPORT_COUNTS_DATA where UCASE(REPORT_TYPE)=UCASE('mtd') and month(CREATED_DT)= month (current date) and  year(CREATED_DT)= year (current date)  and PRODUCT_LOB_ID=? and CIRCLE=? order by HEADER_ID  with ur");
						 ps=con.prepareStatement(query.toString());
						 ps.setString(1, formBean.getLobId());
						 ps.setString(2, formBean.getCircleName());
						 rs=ps.executeQuery();
					}
					else
					{
						 query = new StringBuffer("select * from REPORT_COUNTS_DATA where UCASE(REPORT_TYPE)=UCASE('mtd') and month(CREATED_DT)= month (current date) and  year(CREATED_DT)= year (current date)   and CIRCLE=-10 order by HEADER_ID  with ur");
						 ps=con.prepareStatement(query.toString());
							rs=ps.executeQuery();
					}
				}
				logger.info(query.toString());
				while(rs.next())
				{
					dto = new MTDReportDTO();
					dto.setColumnName(rs.getString("COLUMN_NAME"));
					dto.setCount(String.valueOf(rs.getInt("COUNT")));
					dto.setPercent(String.valueOf(rs.getInt("PERCENTAGE")).concat("%"));
					dto.setReportType(rs.getString("REPORT_TYPE"));
					dto.setHeaderid(rs.getString("HEADER_ID"));
					reportList.add(dto);
					
				}	
			}	
			
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error(ex);
			}
			finally
			{
				try {
					DBConnection.releaseResources(con,ps, rs);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					logger.error(ex);
				}
				
			}
		return reportList;
			
		}
	 
	 
	 
	 public ArrayList<MTDReportDTO> getFTDMTDReportCountDetails(ReportsFormBean formBean) throws DAOException {
		 logger.info("Inside getLeadMTDReportDAYMonthwiseGridPopulate reportdaoimpl");
		
			ArrayList<MTDReportDTO> reportList = new ArrayList<MTDReportDTO>();
			MTDReportDTO dto = null;
			Connection con = null;
			PreparedStatement ps = null;			
			ResultSet rs = null;
			ResultSet rs1 = null;
			StringBuffer query =null;
			int paramCount = 1;
		
	        try
			{
				con = DBConnection.getDBConnection();	 
				
				String query1="select CIRCLE_ID from USER_MAPPING where USER_LOGIN_ID=? order by TRANSACTION_TIME desc fetch first row only";
				ps=con.prepareStatement(query1);
				ps.setString(1, formBean.getUserloginid());
				rs1=ps.executeQuery();
				while(rs1.next()){
					formBean.setCircleName(String.valueOf(rs1.getInt(1)));
				}
							
				if("FTD".equalsIgnoreCase(formBean.getReportType())){ // for daily wise report
					
					/*	 query = new StringBuffer(	 "SELECT distinct(rcdm.LEAD_ID),lpc.CUSTOMER_NAME,rcdm.HEADER_ID,"+
						" (select concat(ADDRESS1,ADDRESS2)  from LEAD_PROSPECT_DETAIL where LEAD_ID=ld.LEAD_ID) as address,lpc.PROSPECT_MOBILE_NUMBER,lde.EXTRA_PARAM3 as longitude ,lde.EXTRA_PARAM4 as latitude,"+
						 "(SELECT LEAD_STATUS FROM LEAD_STATUS WHERE LEAD_STATUS_ID=ld.LEAD_STATUS_ID) AS STATUS,"+
						" (SELECT SUB_STATUS FROM LEAD_SUB_STATUS WHERE SUB_STATUS_ID =ld.LEAD_SUB_STATUS_ID and PRODUCT_LOB_ID in (select product_lob_id from PRODUCT_MSTR where product_id=ld.product_id) and LEAD_STATUS_ID=ld.lead_status_id) AS SUB_STATUS,"+
						 "(select LEAD_ASSIGNMENT_TIME from LEAD_TRANSACTION where LEAD_ID =ld.LEAD_ID order by LEAD_ASSIGNMENT_TIME desc FETCH FIRST ROW ONLY) as ASSIGNED_SINCE,"+
						" (select LEAD_ASSIGNED_PRIMARY_USER from LEAD_TRANSACTION where LEAD_ID =ld.LEAD_ID order by LEAD_ASSIGNMENT_TIME desc FETCH FIRST ROW ONLY) as ASSIGNED_TO,ld.REMARKS FROM LEAD_DETAILS lde ,LEAD_DATA ld,LEAD_PROSPECT_CUSTOMER lpc,LEAD_PROSPECT_DETAIL lpd,LEAD_TRANSACTION lt,REPORT_COUNTS_DATA_MAPPING rcdm "+
						"WHERE rcdm.LEAD_ID=ld.LEAD_ID and ld.PROSPECT_ID=lpc.PROSPECT_ID and lpc.PROSPECT_ID=lpd.PROSPECT_ID  and"+
						 " rcdm.LEAD_ID=lde.LEAD_ID and rcdm.HEADER_ID=?");*/
						 
						 
						 
						 query = new StringBuffer(	" SELECT ld.LEAD_ID,lpc.CUSTOMER_NAME,"+
						" (concat(lpd.ADDRESS1,lpd.ADDRESS2)) as address,lpc.PROSPECT_MOBILE_NUMBER,lde.EXTRA_PARAM3 as longitude ,lde.EXTRA_PARAM4 as latitude,"+
						 "(SELECT LEAD_STATUS FROM LEAD_STATUS WHERE LEAD_STATUS_ID=ld.LEAD_STATUS_ID) AS STATUS,"+
						 "(SELECT SUB_STATUS FROM LEAD_SUB_STATUS WHERE SUB_STATUS_ID =ld.LEAD_SUB_STATUS_ID and PRODUCT_LOB_ID  = lpd.PRODUCT_LOB_ID"+
						 " and LEAD_STATUS_ID=ld.lead_status_id) AS SUB_STATUS,"+
						" (select LEAD_ASSIGNMENT_TIME from LEAD_TRANSACTION where LEAD_ID =ld.LEAD_ID order by LEAD_ASSIGNMENT_TIME desc FETCH FIRST ROW ONLY) as ASSIGNED_SINCE,"+
						 "(select LEAD_ASSIGNED_PRIMARY_USER from LEAD_TRANSACTION where LEAD_ID =ld.LEAD_ID order by LEAD_ASSIGNMENT_TIME desc FETCH FIRST ROW ONLY) as ASSIGNED_TO,(select UPDATED_BY from LEAD_TRANSACTION where LEAD_ID =ld.LEAD_ID order by LEAD_ASSIGNMENT_TIME desc FETCH FIRST ROW ONLY) as LAST_MODIFIED_BY,"+
						"ld.REMARKS FROM LEAD_DETAILS lde ,LEAD_DATA ld,LEAD_PROSPECT_CUSTOMER lpc,LEAD_PROSPECT_DETAIL lpd "+
						" WHERE ld.LEAD_ID=lde.LEAD_ID and ld.LEAD_ID =lpd.LEAD_ID and lpd.PROSPECT_ID =lpc.PROSPECT_ID "+
						"  and  ld.LEAD_ID in (SELECT distinct(LEAD_ID) FROM REPORT_COUNTS_DATA_MAPPING WHERE HEADER_ID =?");
						
						
				if((!formBean.getCircleName().equals(null)||!"".equalsIgnoreCase(formBean.getCircleName()))&&"3".equalsIgnoreCase(formBean.getUserActorId())) {
					 query.append("and CIRCLE=?) ");
						 
				}else{
					query.append(") ");
				}
				 if(formBean.getStatusId()==300||formBean.getStatusId()==500||formBean.getStatusId()==600){
				 
				 query.append("and ld.LEAD_STATUS_ID=? with ur");
				 
			 }else{
				 query.append("with ur");
			 }
				
						 ps=con.prepareStatement(query.toString());
						 ps.setInt(paramCount++, Integer.parseInt(formBean.getHeaderid()));
						 if((!formBean.getCircleName().equals(null)||!"".equalsIgnoreCase(formBean.getCircleName()))&&"3".equalsIgnoreCase(formBean.getUserActorId())) {
						 ps.setInt(paramCount++, Integer.parseInt(formBean.getCircleName()));
						 }
						 if(formBean.getStatusId()==300||formBean.getStatusId()==500||formBean.getStatusId()==600){
							 ps.setInt(paramCount++, formBean.getStatusId());
						 }
						rs=ps.executeQuery();
							
				}else if("MTD".equalsIgnoreCase(formBean.getReportType())){ // for month  wise report		
					
					 query = new StringBuffer(	" SELECT ld.LEAD_ID,lpc.CUSTOMER_NAME,"+
								" (concat(lpd.ADDRESS1,lpd.ADDRESS2)) as address,lpc.PROSPECT_MOBILE_NUMBER,lde.EXTRA_PARAM3 as longitude ,lde.EXTRA_PARAM4 as latitude,"+
								 "(SELECT LEAD_STATUS FROM LEAD_STATUS WHERE LEAD_STATUS_ID=ld.LEAD_STATUS_ID) AS STATUS,"+
								 "(SELECT SUB_STATUS FROM LEAD_SUB_STATUS WHERE SUB_STATUS_ID =ld.LEAD_SUB_STATUS_ID and PRODUCT_LOB_ID  = lpd.PRODUCT_LOB_ID"+
								 " and LEAD_STATUS_ID=ld.lead_status_id) AS SUB_STATUS,"+
								" (select LEAD_ASSIGNMENT_TIME from LEAD_TRANSACTION where LEAD_ID =ld.LEAD_ID order by LEAD_ASSIGNMENT_TIME desc FETCH FIRST ROW ONLY) as ASSIGNED_SINCE,"+
								 "(select LEAD_ASSIGNED_PRIMARY_USER from LEAD_TRANSACTION where LEAD_ID =ld.LEAD_ID order by LEAD_ASSIGNMENT_TIME desc FETCH FIRST ROW ONLY) as ASSIGNED_TO,(select UPDATED_BY from LEAD_TRANSACTION where LEAD_ID =ld.LEAD_ID order by LEAD_ASSIGNMENT_TIME desc FETCH FIRST ROW ONLY) as LAST_MODIFIED_BY,"+
								"ld.REMARKS FROM LEAD_DETAILS lde ,LEAD_DATA ld,LEAD_PROSPECT_CUSTOMER lpc,LEAD_PROSPECT_DETAIL lpd "+
								" WHERE ld.LEAD_ID=lde.LEAD_ID and ld.LEAD_ID =lpd.LEAD_ID and lpd.PROSPECT_ID =lpc.PROSPECT_ID "+
								"  and  ld.LEAD_ID in (SELECT distinct(LEAD_ID) FROM REPORT_COUNTS_DATA_MAPPING WHERE HEADER_ID =?");
					 if((!formBean.getCircleName().equals(null)||!"".equalsIgnoreCase(formBean.getCircleName()))&&"3".equalsIgnoreCase(formBean.getUserActorId())) {
								 query.append("and CIRCLE=?) ");
						}else{
							query.append(") ");
						}
					 
					 if(formBean.getStatusId()==300||formBean.getStatusId()==500||formBean.getStatusId()==600){
						 
						 query.append("and ld.LEAD_STATUS_ID=? with ur");
						 
					 }else{
						 query.append("with ur");
					 }
					 
					 ps=con.prepareStatement(query.toString());
					 ps.setInt(paramCount++, Integer.parseInt(formBean.getHeaderid()));
					 if((!formBean.getCircleName().equals(null)||!"".equalsIgnoreCase(formBean.getCircleName()))&&"3".equalsIgnoreCase(formBean.getUserActorId())) {
						 ps.setInt(paramCount++, Integer.parseInt(formBean.getCircleName()));
						 }
					 if(formBean.getStatusId()==300||formBean.getStatusId()==500||formBean.getStatusId()==600){
						 ps.setInt(paramCount++, formBean.getStatusId());
					 }
					 
					 rs=ps.executeQuery();
				}
				while(rs.next())
				{
					dto = new MTDReportDTO();
					dto.setLeadId(rs.getString("LEAD_ID"));
					dto.setCustomerName(rs.getString("CUSTOMER_NAME"));
					dto.setAddress(rs.getString("ADDRESS"));
					dto.setCustomerMobile(rs.getString("PROSPECT_MOBILE_NUMBER"));
					dto.setLongitude(rs.getString("LONGITUDE"));
					dto.setLatitude(rs.getString("LATITUDE"));
					dto.setLeadStatus(rs.getString("STATUS"));
					dto.setLeadSubStatus(rs.getString("SUB_STATUS"));
					dto.setAssignedsince(rs.getString("ASSIGNED_SINCE"));
					dto.setAssignedto(rs.getString("ASSIGNED_TO"));
					dto.setLastmodifiedby(rs.getString("LAST_MODIFIED_BY"));
					dto.setRemarks(rs.getString("REMARKS"));
					
					reportList.add(dto);
					
				}	
				}	
			
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error(ex);
			}
			finally
			{
				try {
					DBConnection.releaseResources(con,ps, rs);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					logger.error(ex);
				}
				
			}
		
			
			return reportList;
			
		}
	 
	 	 
	 
	 
	 //added by sud Changed by amarjeet
	 public ArrayList<LifeCycleReportDTO> getLeadLifecycleReport(ReportsFormBean formBean ) throws DAOException {
		 logger.info("Inside getLeadLifecycleReport of ReportDaoImpl");
			ArrayList<LifeCycleReportDTO> reportList = new ArrayList<LifeCycleReportDTO>();
		 	LifeCycleReportDTO dto =null;		 	
			Calendar cal4=new GregorianCalendar();
			Connection con = null;
			PreparedStatement ps = null;			
			ResultSet rs = null;			
			Date start,end;
			String startDate = null;
			String endDate = null;
			int paramCount = 1;
			
				
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");			
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
				String date = sdf.format(new java.util.Date().getTime() - 24*60*60*1000);
							
				if(formBean.getStartDate()!=null && !"".equalsIgnoreCase(formBean.getStartDate()))
				{
					start = (Date) sdf.parse(formBean.getStartDate());
					startDate = dateFormat1.format(start);
				}
				if(formBean.getEndDate()!=null && !"".equalsIgnoreCase(formBean.getEndDate()))
				{
					end = (Date) sdf.parse(formBean.getEndDate());
					endDate = dateFormat1.format(end);
				}
				////////////////////////////////////////////////////////////////
				con = DBConnection.getDBConnection();	 
				StringBuffer query = new StringBuffer(SQL_SELECT_LIFECYCLE_REPORT);
				logger.info("query ?????????"+query );
				if(!formBean.getLobId().equals(null) && !formBean.getLobId().equals("")){
					
					query.append("AND LOB = ?");
				}
				if(formBean.getCircleMstrId()!=0){
					
					query.append("AND CIRCLE_MASTER_ID = ?");
					
				}
				
				query.append(" WITH UR ");
				ps = con.prepareStatement(query.toString());
				
				
				if(formBean.getStartDate()!=null && !"".equalsIgnoreCase(formBean.getStartDate())){
					ps.setDate(paramCount++, Utility.getSqlDateFromString(startDate, "MM/dd/yyyy"));
				}
				
				if(formBean.getEndDate()!=null && !"".equalsIgnoreCase(formBean.getEndDate())){
				
					ps.setDate(paramCount++, Utility.getSqlDateFromString(endDate, "MM/dd/yyyy"));
				}	
				
				if(!formBean.getLobId().equals(null) && !formBean.getLobId().equals("")){
					
					ps.setString(paramCount++, formBean.getLobId());
				}
				if(formBean.getCircleMstrId()!=0){
					
					ps.setInt(paramCount++, formBean.getCircleMstrId());
					
				}
				
				rs = ps.executeQuery();
				
				
				
				while(rs.next()){
					dto = new LifeCycleReportDTO();
					dto.setLeadId(rs.getString("LEAD_ID"));					
					dto.setLobName(rs.getString("LOB"));					
					dto.setInsertDate(rs.getTimestamp("INSERT_DATE"));
					dto.setVeriDate(rs.getTimestamp("VERIFICATION_DATE"));
					dto.setAssignToCenDate(rs.getTimestamp("ASSIGNED_TO_CENTER_DATE"));
					dto.setContactDate(rs.getTimestamp("CONTACT_DATE"));
					dto.setQualifiedDate(rs.getTimestamp("QUALIFIED_DATE"));
					dto.setAssognedDate(rs.getTimestamp("ASSIGNED_DATE"));
					dto.setReassignedDate(rs.getTimestamp("REASSIGNED"));
					dto.setClosuredate(rs.getTimestamp("CLOSURE"));
					reportList.add(dto);
								
				}
				
						
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error(ex);
			}
			finally
			{
				try {
					DBConnection.releaseResources(con,ps, rs);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					logger.error(ex);
				}
				
			}
			return reportList;
		}
	 //added by sud
	 //added by amarjeet
	 public ArrayList<DashBoardReportDTO> getDashboardReport(ReportsFormBean formBean ) throws DAOException {
		 logger.info("Inside getLeadLifecycleReport of ReportDaoImpl");
			ArrayList<DashBoardReportDTO> reportList = new ArrayList<DashBoardReportDTO>();
			DashBoardReportDTO dto =null;		 	
			Calendar cal4=new GregorianCalendar();
			Connection con = null;
			PreparedStatement ps = null;			
			ResultSet rs = null;			
			Date start,end , day;
			String startDate = null;
			String endDate = null;
			String dailyDate = null;
			int paramCount = 1;
			
				
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");			
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
				String date = sdf.format(new java.util.Date().getTime() - 24*60*60*1000);
							
				if(formBean.getStartDate()!=null && !"".equalsIgnoreCase(formBean.getStartDate()))
				{
					start = (Date) sdf.parse(formBean.getStartDate());
					startDate = dateFormat1.format(start);
				}
				if(formBean.getEndDate()!=null && !"".equalsIgnoreCase(formBean.getEndDate()))
				{
					end = (Date) sdf.parse(formBean.getEndDate());
					endDate = dateFormat1.format(end);
				}
				if(formBean.getDate()!=null && !"".equalsIgnoreCase(formBean.getDate()))
				{
					day = (Date) sdf.parse(formBean.getDate());
					dailyDate = dateFormat1.format(day);
				}
				////////////////////////////////////////////////////////////////
				con = DBConnection.getDBConnection();
				StringBuffer query =null;
				if(formBean.getDate()!=null && !"".equalsIgnoreCase(formBean.getDate()))
				{					
					 query = new StringBuffer(SQL_SELECT_DASHBOARD_REPORT_DAILY);
					logger.info("query ?????????"+query );
				}
				else{
					 query = new StringBuffer(SQL_SELECT_DASHBOARD_REPORT_MTD);
					logger.info("query ?????????"+query );
				}
				
			/*	if(!formBean.getLobId().equals(null) && !formBean.getLobId().equals("")){
					
					query.append("AND LOB = ?");
				}
				if(formBean.getCircleMstrId()!=0){
					
					query.append("AND CIRCLE_MASTER_ID = ?");
					
				}
				
				query.append(" WITH UR ");*/
				ps = con.prepareStatement(query.toString());
				if(formBean.getDate()!=null && !"".equalsIgnoreCase(formBean.getDate()))
				{
					ps.setDate(paramCount++, Utility.getSqlDateFromString(dailyDate, "MM/dd/yyyy"));
				}
				
				if(formBean.getStartDate()!=null && !"".equalsIgnoreCase(formBean.getStartDate())){
					ps.setDate(paramCount++, Utility.getSqlDateFromString(startDate, "MM/dd/yyyy"));
				}
				
				if(formBean.getEndDate()!=null && !"".equalsIgnoreCase(formBean.getEndDate())){
				
					ps.setDate(paramCount++, Utility.getSqlDateFromString(endDate, "MM/dd/yyyy"));
				}	
			/*	
				if(!formBean.getLobId().equals(null) && !formBean.getLobId().equals("")){
					
					ps.setString(paramCount++, formBean.getLobId());
				}
				if(formBean.getCircleMstrId()!=0){
					
					ps.setInt(paramCount++, formBean.getCircleMstrId());
					
				}*/
				
				rs = ps.executeQuery();
				
				
				
				while(rs.next()){
					dto = new DashBoardReportDTO();
					dto.setLobName(rs.getString("PRODUCT_LOB"));		
					dto.setPoductName(rs.getString("PRODUCT_NAME"));					
					dto.setOpen_veri(rs.getString("OPEN_VERI"));
					dto.setQuali_wired_assi(rs.getString("QUALI_WIR_ASSI"));
					dto.setFaesibility(rs.getString("FEASIBILITY"));
					dto.setInfoinedu(rs.getString("INF0_INADEQUATE"));
					dto.setUnwir_lost(rs.getString("LOST_UNWIRD"));
					dto.setWon(rs.getString("WON"));
					//dto.setTotal(rs.getString(""));		
					reportList.add(dto);
								
				}
				
						
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error(ex);
			}
			finally
			{
				try {
					DBConnection.releaseResources(con,ps, rs);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					logger.error(ex);
				}
				
			}
			return reportList;
		}
	 
	//added by Nancy Agrawal
	 public ArrayList<IDOCReportDTO> getiDOCReport(ReportsFormBean formBean)throws DAOException
	 {
	logger.info("Inside iDOCReport of ReportDaoImpl");
	ArrayList<IDOCReportDTO> myReport=new ArrayList<IDOCReportDTO>();
	
	ArrayList<IDOCReportDTO> newmyReport=null;
	
	
	IDOCReportDTO dto =null;		 	
	Calendar cal4=new GregorianCalendar();
	Connection con = null;
	PreparedStatement ps = null;			
	ResultSet rs = null;			
	Date start,end , day;
	String startDate = null;
	String endDate = null;
	String dailyDate = null;
	int paramCount = 1;
	int j=0;
	int l=0;
	
		
	try
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");			
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");	
		//int lobId=Integer.parseInt(formBean.getLobId());
		
			if(formBean.getStartDate()!=null && !"".equalsIgnoreCase(formBean.getStartDate()))
			{
				start = (Date) sdf.parse(formBean.getStartDate());
				startDate = dateFormat1.format(start);
				
			}
		if(formBean.getEndDate()!=null && !"".equalsIgnoreCase(formBean.getEndDate()))
			{
				end = (Date) sdf.parse(formBean.getEndDate());
				endDate = dateFormat1.format(end);
				
			}
	
		con = DBConnection.getDBConnection();	 
		StringBuffer query = new StringBuffer(SQL_SELECT_IDOC_REPORT);
		
		
		/*if(!formBean.getLobId().equals(null) && !formBean.getLobId().equalsIgnoreCase("")  && startDate == null  && endDate== null)
		{
			
			query.append("PRODUCT_LOB_ID ='"+lobId+"'")	;
			
		}*/
		
		if(startDate !=null && !"".equalsIgnoreCase(startDate) && endDate !=null && !"".equalsIgnoreCase(endDate) )
		{
			
			query.append(" and Date(LEAD_CREATE_TIME) between '"+startDate+"' and  '"+endDate+"' ");
			
		}
		
		/*else if(formBean.getLobId()!=null && !formBean.getLobId().equalsIgnoreCase("") && startDate !=null && !"".equalsIgnoreCase(startDate) && endDate !=null && !"".equalsIgnoreCase(endDate))
				{
			
		query.append("PRODUCT_LOB_ID = '"+lobId+"' AND Date(LEAD_CREATE_TIME) between '"+startDate+"' and  '"+endDate+"' ");
				}
*/						
		query.append("  WITH UR");
		
		ps=con.prepareStatement(query.toString());
		
		rs=ps.executeQuery();
		
		
		while(rs.next())
				
			{
			   
				 dto = new IDOCReportDTO();
				 dto.setLeadId(rs.getString("LEAD_ID"));
				 dto.setCustomerMobile(rs.getString("PROSPECT_MOBILE_NUMBER"));
				 dto.setOrder_id(rs.getString("ORDERID"));
				 dto.setAlternateMobileNumber(rs.getString("MOBILENO"));
				 dto.setTvDate(rs.getString("TVDATE"));
				 dto.setChannelPartner(rs.getString("CHANNEL_PARTNER_LMS"));
				 dto.setChannelPartnerIdoc(rs.getString("CHANNEL_PARTNER_IDOC"));
				 dto.setCafNo(rs.getString("CAFNO"));
				 dto.setDe1Status(rs.getString("DE1STATUS"));
				 dto.setDe1Date(rs.getString("DE1DATE"));
				 dto.setTvStatus(rs.getString("TVSTATUS"));
				 dto.setTvCrmDate(rs.getString("TVCRMDATE"));
				 dto.setTvReason(rs.getString("TVREASON"));
				 dto.setInWardStatus(rs.getString("INWARDSTATUS"));
				 dto.setInWardDate(rs.getString("INWARDDATE"));
				 dto.setInWardUser(rs.getString("INWARDUSER"));
				 dto.setDeAuditStatus(rs.getString("DEAUDITSTATUS"));
				 dto.setDeAditDate(rs.getString("DEAUDITDATE"));
				 dto.setDeAuditUser(rs.getString("DEAUDITUSER"));
				 dto.setEvStatus(rs.getString("EVSTATUS"));
				 dto.setEvDate(rs.getString("EVDATE"));
				 dto.setEvReason(rs.getString("EVREASON"));
				 dto.setEvUser(rs.getString("EVUSER"));
				 dto.setDedupe_Status(rs.getString("DEDUPE_STATUS"));
				 dto.setDeDupe_Date(rs.getString("DEDUPE_DATE"));
				 dto.setScanStatus(rs.getString("SCAN_STATUS"));
				 dto.setDe2DateTime(rs.getString("DE2DATETIME"));
				 dto.setDe2_User(rs.getString("DE2_USER"));
				 dto.setDe2_Status(rs.getString("DE2_STATUS"));
				 dto.setWxhDate(rs.getString("WHXDATE"));
				 dto.setWhxUser(rs.getString("WHXUSER"));
				 dto.setParty_Creation_Status(rs.getString("PARTY_CREATION_STATUS"));
				 dto.setParty_Creation_Date(rs.getString("PARTY_CREATION_DATE"));
				 dto.setParty_Creation_Reason(rs.getString("PARTY_CREATION_REASON"));
				 dto.setOrderCreationStatus(rs.getString("ORDER_CREATION_STATUS"));
				 dto.setOrderCreationIdoc(rs.getString("ORDER_CREATION_DATE_IDOC"));
				 dto.setOrderRemarks(rs.getString("ORDER_REMARKS"));
				 dto.setOrderCompletionDateIdoc(rs.getString("ORDER_COMPLETION_DATE_IDOC"));
				 dto.setOrderCreationAgentIdoc(rs.getString("ORDER_CREATION_AGENT_IDOC"));
				 dto.setLead_Creation_Time(rs.getString("LEAD_CREATE_TIME"));
				 dto.setChannelCode(rs.getString("CHANNEL_CODE"));
				 dto.setProduct(rs.getString("PRODUCTTYPE"));
				 myReport.add(dto);
				 
				}
				newmyReport = new ArrayList<IDOCReportDTO>(myReport.size());
				
			if(myReport.size()>0)
			{
		
				for(int i=0;i<myReport.size();)
				{
					
				String currLeadId=myReport.get(i).getLeadId();
				
				if(i<myReport.size()-1 && myReport.get(i+1).getLeadId().equals(currLeadId)){
					l=i;
						while(i<myReport.size()-1 && myReport.get(i+1).getLeadId().equals(currLeadId))// && i<myReport.size()-2)
					{
					 int index = i+1;
					  IDOCReportDTO t = myReport.get(index);
			    	  if(index != -1) 
			           {
			    	
				   	myReport.get(l).setLeadId(myReport.get(l).getLeadId());
			    	myReport.get(l).setOrder_id(myReport.get(l).getOrder_id() +"," + t.getOrder_id());
			    	myReport.get(l).setCustomerMobile(myReport.get(l).getCustomerMobile());
			    	myReport.get(l).setAlternateMobileNumber(myReport.get(l).getAlternateMobileNumber() +"," + t.getAlternateMobileNumber());
			    	myReport.get(l).setAv(myReport.get(l).getAv() +"," + t.getAv());
			    	myReport.get(l).setAvDate(myReport.get(l).getAvDate() +"," + t.getAvDate());
			    	myReport.get(l).setCafNo(myReport.get(l).getCafNo() +"," + t.getCafNo());
			    	myReport.get(l).setChannelCode(myReport.get(l).getChannelCode() +"," + t.getChannelCode());
			    	myReport.get(l).setChannelPartner(myReport.get(l).getChannelPartner() +"," + t.getChannelPartner());
			    	myReport.get(l).setChannelPartnerIdoc(myReport.get(l).getChannelPartnerIdoc() +"," + t.getChannelPartnerIdoc());
			    	myReport.get(l).setCreateTime(myReport.get(l).getCreateTime() +"," + t.getCreateTime());
			    	myReport.get(l).setDe1Date(myReport.get(l).getDe1Date() +"," + t.getDe1Date());
			    	myReport.get(l).setDe1Status(myReport.get(l).getDe1Status() +"," + t.getDe1Status());
			    	myReport.get(l).setDe2_Status(myReport.get(l).getDe2_Status() +"," + t.getDe2_Status());
			    	myReport.get(l).setDe2_User(myReport.get(l).getDe2_User() +"," + t.getDe2_User());
			    	myReport.get(l).setDe2DateTime(myReport.get(l).getDe2DateTime() +"," + t.getDe2DateTime());
			    	myReport.get(l).setDeAditDate(myReport.get(l).getDeAditDate() +"," + t.getDeAditDate());
			    	myReport.get(l).setDeAuditStatus(myReport.get(l).getDeAuditStatus() +"," + t.getDeAuditStatus());
			    	myReport.get(l).setDeAuditUser(myReport.get(l).getDeAuditUser() +"," + t.getDeAuditUser());
			    	myReport.get(l).setDeDupe_Date(myReport.get(l).getDeDupe_Date() +"," + t.getDeDupe_Date());
			    	myReport.get(l).setDedupe_Status(myReport.get(l).getDedupe_Status() +"," + t.getDedupe_Status());
			    	myReport.get(l).setEvDate(myReport.get(l).getEvDate() +"," + t.getEvDate());
			    	myReport.get(l).setEvReason(myReport.get(l).getEvReason() +"," + t.getEvReason());
			    	myReport.get(l).setEvStatus(myReport.get(l).getEvStatus() +"," + t.getEvStatus());
			    	myReport.get(l).setEvUser(myReport.get(l).getEvUser() +"," + t.getEvUser());
			    	myReport.get(l).setInWardDate(myReport.get(l).getInWardDate() +"," + t.getInWardDate());
			    	myReport.get(l).setInWardStatus(myReport.get(l).getInWardStatus() +"," + t.getInWardStatus());
			    	myReport.get(l).setInWardUser(myReport.get(l).getInWardUser() +"," + t.getInWardUser());
			    	myReport.get(l).setLead_Creation_Time(myReport.get(l).getLead_Creation_Time() +"," + t.getLead_Creation_Time());
			    	myReport.get(l).setOrderCompletionDateIdoc(myReport.get(l).getOrderCompletionDateIdoc() +"," + t.getOrderCompletionDateIdoc());
			    	myReport.get(l).setOrderCreationAgentIdoc(myReport.get(l).getOrderCreationAgentIdoc() +"," + t.getOrderCreationAgentIdoc());
			    	myReport.get(l).setTvStatus(myReport.get(l).getTvStatus() +"," + t.getTvStatus());
			    	myReport.get(l).setWhxUser(myReport.get(l).getWhxUser() +"," + t.getWhxUser());
			    	myReport.get(l).setWxhDate(myReport.get(l).getWxhDate() +"," + t.getWxhDate());
			    	myReport.get(l).setProduct(myReport.get(l).getProduct() +"," + t.getProduct());
			    	myReport.get(l).setTvReason(myReport.get(l).getTvReason() +"," + t.getTvReason());
			    	myReport.get(l).setTvDate(myReport.get(l).getTvDate() +"," + t.getTvDate());
			    	myReport.get(l).setTvCrmDate(myReport.get(l).getTvCrmDate() +"," + t.getTvCrmDate());
			    	myReport.get(l).setScanStatus(myReport.get(l).getScanStatus() +"," + t.getScanStatus());
			    	myReport.get(l).setParty_Creation_Status(myReport.get(l).getParty_Creation_Status() +"," + t.getParty_Creation_Status());
			    	myReport.get(l).setParty_Creation_Date(myReport.get(l).getParty_Creation_Date() +"," + t.getParty_Creation_Date());
			    	myReport.get(l).setOrderCreationStatus(myReport.get(l).getOrderCreationStatus() +"," + t.getOrderCreationStatus());
			    	myReport.get(l).setParty_Creation_Reason(myReport.get(l).getParty_Creation_Reason() +"," + t.getParty_Creation_Reason());
			    	myReport.get(l).setOrderCreationIdoc(myReport.get(l).getOrderCreationIdoc() +"," + t.getOrderCreationIdoc()); 
			    	myReport.get(l).setOrderRemarks(myReport.get(l).getOrderRemarks() +"," + t.getOrderRemarks()); 
			    	 
			           
			           }
					  i=i+1;
					}
					newmyReport.add(myReport.get(l)); 
					 i=i+1;
					
				}
					
				else
			    {
			    	
			    	currLeadId=myReport.get(i).getLeadId();
			    	newmyReport.add(myReport.get(i)); 
			    	i=i+1;
			    	   
			    }
					 
			
				 
					
			}	
			
			}
	}
			
	catch(Exception ex)
	{
		ex.printStackTrace();
		logger.error(ex);
	}
	finally
	{
		try {
			DBConnection.releaseResources(con,ps, rs);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.error(ex);
		}
		
	}
	return newmyReport;
	
}

}
