package com.ibm.km.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;
import com.ibm.km.dao.KmDashBoardDao;
import com.ibm.lms.common.Constants;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.dto.DashBoardDTO;
import com.ibm.lms.exception.LMSException;


public class KmDashBoardDaoImpl implements KmDashBoardDao
{
	private static final Logger logger;
	static {
		logger = Logger.getLogger(KmDashBoardDaoImpl.class);
	}
	
    public KmDashBoardDaoImpl()
    {
    }

    public  ArrayList<DashBoardDTO> weeklyReportList() throws LMSException
    {
    	Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		DashBoardDTO dto;
		int days=0;
		String sql = "";		
		Statement stmtCD=null;
		
		try {
			con = DBConnection.getDBConnection();
			sql="select DAYOFWEEK(CURRENT DATE)  from sysibm.sysdummy1 with ur ";
			stmtCD=con.createStatement();
			rs=stmtCD.executeQuery(sql);
			int currentDay=0;
			if(rs.next())
			{
				currentDay=rs.getInt(1);
			}
			if(currentDay==1) //Sunday
			{
				logger.info(currentDay +"Current Days is: Sunday");
				days=6;
			}
			if(currentDay==2) //Monday
			{
				logger.info(currentDay +"Current Days is: Monday");
				days=0;
			}
			if(currentDay==3)
			{
				logger.info(currentDay +"Current Days is: Tuesday");
				days=1;
			}
			if(currentDay==4)
			{
				logger.info(currentDay +"Current Days is: Wednesday");
				days=2;
			}
			if(currentDay==5)
			{
				logger.info(currentDay +"Current Days is: Thursday");
				days=3;
			}
			if(currentDay==6)
			{
				logger.info(currentDay +"Current Days is: Friday");
				days=4;
			}
			if(currentDay==7) //Saturday
			{
				logger.info(currentDay +"Current Days is: Saturday");
				days=5;
			}
			 String SQL_SELECT_WEEKLY_REPORT="select 1 as cnt, VARCHAR_FORMAT((current date - ? days), 'DD-MON') || ' to ' || VARCHAR_FORMAT((current date), 'DD-MON')  Param ,G.countg as Total_leads_submitted, A.counta as Total_Leads_Created, B.countb as Total_leads_sent_to_dialer, C.countc as Leads_Qualified, D.countd as Leads_in_Feasibility, F.countf as Leads_assigned from "+
				" (select  count(lead_id) counta  from lead_data where lead_id in "+
				" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME)  between (current date - ? days) and (CURRENT DATE)) "  +
				" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_OPEN+ "  )) as A, "+

				" (select  count(lead_id) countb  from lead_data where lead_id in "+
				" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME)  between (current date - ? days) and (CURRENT DATE)) "  +
				" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_VERIFICATION+ "  )) as B,"+
				" (select  count(lead_id) countc  from lead_data where lead_id in ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME)  between (current date - ? days) and (CURRENT DATE) ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_QUALIFIED+ " ) as C, "+
				"(select  count(lead_id) countd  from lead_data where lead_id in  ( "+
				" select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME)  between (current date - ? days) and (CURRENT DATE) )) and LEAD_STATUS_ID=310 ) as D, " +
				" (select  count(lead_id) countf  from lead_data where lead_id in  (  select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between  (current date - ? days) and (CURRENT DATE)   ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_ASSIGNED+ "  ) as F,  (select count(1) as countg from lead_capture where date(CREATE_TIME)  between (current date - ? days) and (CURRENT DATE) ) as G ";
			
			
			StringBuffer query=new StringBuffer(SQL_SELECT_WEEKLY_REPORT);
			
			
			
			query.append(" union all ");
			//for last 1 week add 7 days everywhere
			 query.append(" select 2 as cnt,VARCHAR_FORMAT((current date - (?+7) days), 'DD-MON') || ' to ' || VARCHAR_FORMAT((CURRENT DATE - (?+1) DAYS), 'DD-MON')  Param , G.countg as Total_leads_submitted, A.counta as Total_Leads_Created, B.countb as Total_leads_sent_to_dialer, C.countc as Leads_Qualified, D.countd as Leads_in_Feasibility, F.countf as Leads_assigned from "+
						" (select  count(lead_id) counta  from lead_data where lead_id in "+
						" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME)  between (current date - (?+7) days) and (CURRENT DATE - (?+1) DAYS)) "  +
						" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_OPEN+ "  )) as A, "+

						" (select  count(lead_id) countb  from lead_data where lead_id in "+
						" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+7) days) and (CURRENT DATE - (?+1) DAYS)  )) "  +
						" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_VERIFICATION+ "  ) as B,"+
						" (select  count(lead_id) countc  from lead_data where lead_id in ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+7) days) and (CURRENT DATE - (?+1) DAYS) ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_QUALIFIED+ " ) as C, "+
						"(select  count(lead_id) countd  from lead_data where lead_id in  ( "+
						" select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+7) days) and (CURRENT DATE - (?+1) DAYS) )) and LEAD_STATUS_ID=310 ) as D, (select  count(lead_id) countf  from lead_data where lead_id in  (  select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+7) days) and (CURRENT DATE - (?+1) DAYS)   ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_ASSIGNED+ "  ) as F,  (select count(1) as countg from lead_capture where date(CREATE_TIME) between (current date  - (?+7) days) and (CURRENT DATE - (?+1) DAYS) ) as G ");

				
			query.append(" union all ");
			//for last 2 week add 7 days everywhere
			query.append(" select 3 as cnt,VARCHAR_FORMAT((current date - (?+14) days), 'DD-MON') || ' to ' || VARCHAR_FORMAT((CURRENT DATE - (?+8) DAYS), 'DD-MON')  Param, G.countg as Total_leads_submitted, A.counta as Total_Leads_Created, B.countb as Total_leads_sent_to_dialer, C.countc as Leads_Qualified, D.countd as Leads_in_Feasibility, F.countf as Leads_assigned from "+
					" (select  count(lead_id) counta  from lead_data where lead_id in "+
					" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME)  between (current date - (?+14) days) and (CURRENT DATE - (?+8) DAYS)) "  +
					" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_OPEN+ "  )) as A, "+
					" (select  count(lead_id) countb  from LEAD_DATA where lead_id in "+
					" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+14) days) and (CURRENT DATE - (?+8) DAYS)  )) "  +
					" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_VERIFICATION+ " ) as B,"+
					" (select  count(lead_id) countc  from LEAD_DATA where lead_id in ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+14) days) and (CURRENT DATE - (?+8) DAYS) ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_QUALIFIED+ "  ) as C, "+
					"(select  count(lead_id) countd  from LEAD_DATA where lead_id in  ( "+
					" select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+14) days) and (CURRENT DATE - (?+8) DAYS) )) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_FEASIBILITY+ "   ) as D," +
					" (select  count(lead_id) countf  from LEAD_DATA where lead_id in  (  select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+14) days) and (CURRENT DATE - (?+8) DAYS)   ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_ASSIGNED+ "   ) as F,  " +
					" (select count(1) as countg from lead_capture where date(CREATE_TIME) between (current date  - (?+14) days) and (CURRENT DATE - (?+8) DAYS) ) as G  ");
			
			query.append(" union all ");
			//for last 3 week add 14 days everywhere

			query.append(" select 4 as cnt,VARCHAR_FORMAT((current date - (?+21) days), 'DD-MON') || ' to ' || VARCHAR_FORMAT((CURRENT DATE - (?+15) DAYS), 'DD-MON')  Param, G.countg as Total_leads_submitted, A.counta as Total_Leads_Created, B.countb as Total_leads_sent_to_dialer, C.countc as Leads_Qualified, D.countd as Leads_in_Feasibility, F.countf as Leads_assigned from "+
			" (select  count(lead_id) counta  from lead_data where lead_id in "+
			" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME)  between (current date - (?+21)  days) and (CURRENT DATE- (?+15) DAYS)) "  +
			" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_OPEN+ "  )) as A, "+
			" (select  count(lead_id) countb  from LEAD_DATA where lead_id in "+
			" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+21) days) and (CURRENT DATE - (?+15) DAYS)  )) "  +
			" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_VERIFICATION+ " ) as B,"+
			" (select  count(lead_id) countc  from LEAD_DATA where lead_id in ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+21) days) and (CURRENT DATE - (?+15) DAYS) ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_QUALIFIED+ "  ) as C, "+
			"(select  count(lead_id) countd  from LEAD_DATA where lead_id in  ( "+
			" select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+21) days) and (CURRENT DATE - (?+15) DAYS) )) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_FEASIBILITY+ " ) as D," +
			" (select  count(lead_id) countf  from LEAD_DATA where lead_id in  (  select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+21) days) and (CURRENT DATE - (?+15) DAYS)   ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_ASSIGNED+ "   ) as F,  (select count(1) as countg from lead_capture where date(CREATE_TIME) between (current date  - (?+21) days) and (CURRENT DATE - (?+15) DAYS) ) as G  ");
		
			query.append(" union all ");
			//for last 4 week add 21 days everywhere

			query.append(" select 5 as cnt,VARCHAR_FORMAT((current date - (?+28) days), 'DD-MON') || ' to ' || VARCHAR_FORMAT((CURRENT DATE - (?+22) DAYS), 'DD-MON')  Param, G.countg as Total_leads_submitted, A.counta as Total_Leads_Created, B.countb as Total_leads_sent_to_dialer, C.countc as Leads_Qualified, D.countd as Leads_in_Feasibility, F.countf as Leads_assigned from "+
						" (select  count(lead_id) counta  from lead_data where lead_id in "+
						" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME)  between (current date - (?+28) days) and (CURRENT DATE- (?+22) DAYS)) "  +
						" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_OPEN+ "  )) as A, "+
						" (select  count(lead_id) countb  from LEAD_DATA where lead_id in "+
						" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+28) days) and (CURRENT DATE - (?+22) DAYS)  )) "  +
						" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_VERIFICATION+ " ) as B,"+
						" (select  count(lead_id) countc  from LEAD_DATA where lead_id in ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+28) days) and (CURRENT DATE - (?+22) DAYS) ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_QUALIFIED+ "  ) as C, "+
						"(select  count(lead_id) countd  from LEAD_DATA where lead_id in  ( "+
						" select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+28) days) and (CURRENT DATE - (?+22) DAYS) )) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_FEASIBILITY+ " ) as D,(select  count(lead_id) countf  from LEAD_DATA where lead_id in  (  select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+28) days) and (CURRENT DATE - (?+22) DAYS)   ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_ASSIGNED+ "   ) as F, " +
						" (select count(1) as countg from lead_capture where date(CREATE_TIME) between (current date  - (?+28) days) and (CURRENT DATE - (?+22) DAYS) ) as G  ");
			
			query.append(" union all ");
			//for last 5 week add 28 days everywhere
			query.append(" select 6 as cnt,VARCHAR_FORMAT((current date - (?+35) days), 'DD-MON') || ' to ' || VARCHAR_FORMAT((CURRENT DATE - (?+29) DAYS), 'DD-MON')  Param,G.countg as Total_leads_submitted, A.counta as Total_Leads_Created, B.countb as Total_leads_sent_to_dialer, C.countc as Leads_Qualified, D.countd as Leads_in_Feasibility, F.countf as Leads_assigned from "+
					" (select  count(lead_id) counta  from lead_data where lead_id in "+
					" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME)  between (current date - (?+35) days) and (CURRENT DATE - (?+29) DAYS)) "  +
					" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_OPEN+ "  )) as A, "+
					" (select  count(lead_id) countb  from LEAD_DATA where lead_id in "+
					" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+35) days) and (CURRENT DATE - (?+29) DAYS)  )) "  +
					" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_VERIFICATION+ " ) as B,"+
					" (select  count(lead_id) countc  from LEAD_DATA where lead_id in ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+35) days) and (CURRENT DATE - (?+29) DAYS) ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_QUALIFIED+ ") as C, "+
					"(select  count(lead_id) countd  from LEAD_DATA where lead_id in  ( "+
					" select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+35) days) and (CURRENT DATE - (?+29) DAYS) )) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_FEASIBILITY+ " ) as D,(select  count(lead_id) countf  from LEAD_DATA where lead_id in  (  select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+35) days) and (CURRENT DATE - (?+29) DAYS)   ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_ASSIGNED+ "   ) as F, " +
					"  (select count(1) as countg from lead_capture where date(CREATE_TIME) between (current date  - (?+35) days) and (CURRENT DATE - (?+29) DAYS) ) as G  ");
		
		
	
			query.append(" union all ");
			//for last 6 week add 35 days everywhere
			query.append(" select 7 as cnt,VARCHAR_FORMAT((current date - (?+42) days), 'DD-MON') || ' to ' || VARCHAR_FORMAT((CURRENT DATE - (?+36) DAYS), 'DD-MON')    Param,G.countg as Total_leads_submitted, A.counta as Total_Leads_Created, B.countb as Total_leads_sent_to_dialer, C.countc as Leads_Qualified, D.countd as Leads_in_Feasibility, F.countf as Leads_assigned from "+
					" (select  count(lead_id) counta  from lead_data where lead_id in "+
					" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME)  between (current date - (?+42) days) and (CURRENT DATE- (?+36) days)) "  +
					" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_OPEN+ "  )) as A, "+
					" (select  count(lead_id) countb  from LEAD_DATA where lead_id in "+
					" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+42) days) and (CURRENT DATE - (?+36) DAYS)  )) "  +
					" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_VERIFICATION+ " ) as B,"+
					" (select  count(lead_id) countc  from LEAD_DATA where lead_id in ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+42) days) and (CURRENT DATE - (?+36) DAYS) ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_QUALIFIED+ "  ) as C, "+
					"(select  count(lead_id) countd  from LEAD_DATA where lead_id in  ( "+
					" select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+42) days) and (CURRENT DATE - (?+36) DAYS) )) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_FEASIBILITY+ " ) as D," +
					" (select  count(lead_id) countf  from LEAD_DATA where lead_id in  (  select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+42) days) and (CURRENT DATE - (?+36) DAYS)   ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_ASSIGNED+ "   ) as F,  (select count(1) as countg from lead_capture where date(CREATE_TIME) between (current date  - (?+42) days) and (CURRENT DATE - (?+36) DAYS) ) as G  ");
		
			query.append(" union all ");
			//for last 7 week add 42 days everywhere
			query.append(" select 8 as cnt, VARCHAR_FORMAT((current date - (?+49) days), 'DD-MON') || ' to ' || VARCHAR_FORMAT((CURRENT DATE - (?+43) DAYS), 'DD-MON')  Param , G.countg as Total_leads_submitted, A.counta as Total_Leads_Created, B.countb as Total_leads_sent_to_dialer, C.countc as Leads_Qualified, D.countd as Leads_in_Feasibility, F.countf as Leads_assigned from "+
					" (select  count(lead_id) counta  from lead_data where lead_id in "+
					" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME)  between (current date - (?+49) days) and (CURRENT DATE- (?+43) DAYS)) "  +
					" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_OPEN+ "  )) as A, "+
					" (select  count(lead_id) countb  from LEAD_DATA where lead_id in "+
					" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+49) days) and (CURRENT DATE - (?+43) DAYS)  )) "  +
					" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_VERIFICATION+ " ) as B,"+
					" (select  count(lead_id) countc  from LEAD_DATA where lead_id in ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+49) days) and (CURRENT DATE - (?+43) DAYS) ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_QUALIFIED+ "  ) as C, "+
					"(select  count(lead_id) countd  from LEAD_DATA where lead_id in  ( "+
					" select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+49) days) and (CURRENT DATE - (?+43) DAYS) )) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_FEASIBILITY+ " ) as D," +
					" (select  count(lead_id) countf  from LEAD_DATA where lead_id in  (  select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+49) days) and (CURRENT DATE - (?+43) DAYS)   ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_ASSIGNED+ "   ) as F,  (select count(1) as countg from lead_capture where date(CREATE_TIME) between (current date  - (?+49) days) and (CURRENT DATE - (?+43) DAYS) ) as G  ");
		/*	
			query.append(" union all ");
			//for last 6th week add 49 days everywhere
			query.append(" select G.countg as Total_leads_submitted, A.counta as Total_Leads_Created, B.countb as Total_leads_sent_to_dialer, C.countc as Leads_Qualified, D.countd as Leads_in_Feasibility, F.countf as Leads_assigned from "+
					" (select  count(lead_id) counta  from lead_transaction where lead_id in ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+49) days) and (CURRENT DATE - (?+6+49) DAYS) "+
					" ))  and date(TRANSACTION_TIME) between (current date  - (?+49) days) and (CURRENT DATE - (?+6+49) DAYS) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_OPEN+ "  ) as A, "+
					" (select  count(lead_id) countb  from LEAD_DATA where lead_id in "+
					" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+49) days) and (CURRENT DATE - (?+6+49) DAYS)  )) "  +
					" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_VERIFICATION+ " ) as B,"+
					" (select  count(lead_id) countc  from LEAD_DATA where lead_id in ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+49) days) and (CURRENT DATE - (?+6+49) DAYS) ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_QUALIFIED+ "  ) as C, "+
					"(select  count(lead_id) countd  from LEAD_DATA where lead_id in  ( "+
					" select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+49) days) and (CURRENT DATE - (?+6+49) DAYS) )) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_FEASIBILITY+ " ) as D," +
					" (select  count(lead_id) countf  from LEAD_DATA where lead_id in  (  select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) between (current date  - (?+49) days) and (CURRENT DATE - (?+6+49) DAYS)   ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_ASSIGNED+ "   ) as F,  (select count(1) as countg from lead_capture where date(CREATE_TIME) between (current date  - (?+49) days) and (CURRENT DATE - (?+6+49) DAYS) ) as G  ");
		*/
			logger.info("Inside DAO weekly report .. " +query);
			
			ArrayList<DashBoardDTO> assignedList = new ArrayList<DashBoardDTO>();
			ps = con.prepareStatement(query.append(" order by cnt with ur ").toString());
			for(int k=1;k<106;k++)
			ps.setInt(k, days);
				
			rs = ps.executeQuery();
			logger.info("SQL Stmt for View Weekly" + query);
			
			int count=0;
			while (rs.next()) {
				dto = new DashBoardDTO();
				dto.setSubmittedLead(rs.getInt("Total_leads_submitted"));
				dto.setCreatedLead(rs.getInt("Total_Leads_Created"));
				dto.setSenttoDialerLeads(rs.getInt("Total_leads_sent_to_dialer"));
				dto.setQualifiedLeads(rs.getInt("Leads_Qualified"));
				dto.setFeasibilityLeads(rs.getInt("Leads_in_Feasibility"));
				dto.setAssignedLeads(rs.getInt("Leads_assigned"));
				dto.setParam(rs.getString("Param"));
				/*if(count==0)
				dto.setParam("Current Week");
				else if(count==1)
				dto.setParam("Last " + count + "st week");	
				else if(count==2)
				dto.setParam("Last " + count + "nd week");	
				else if(count==3)
				dto.setParam("Last " + count + "rd week");	
				else if(count==4 || count==5 || count==6 || count==7)
				dto.setParam("Last " + count + "th week");	*/
				assignedList.add(dto);
				logger.info("Get week.............."+dto.getParam());
				count++;
			}
			logger.info("Weekly Report... ");
			return assignedList;
			} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while Weekly Report."
					+ "SQL Exception Message: "
					+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(
				" Exception occured while Weekly Report."
					+ " Exception Message: "
					+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} 
		finally 
		{
			try {
				DBConnection.releaseResources(con, ps, rs);
				DBConnection.releaseResources(null, stmtCD, rs);
				
			} catch (Exception e) {
				logger.error(
					"DAO Exception occured while Viewing Weekly Report"
						+ "DAO Exception Message: "
						+ e.getMessage());
				throw new LMSException("Exception: " + e.getMessage(), e);
			}
		}
    	
    }
   
    
    public ArrayList<DashBoardDTO> DailyReport() throws LMSException
    {

	Connection con = null;
	//Connection cond=null;
	ResultSet rs = null;
	ResultSet rscurrent = null;
	PreparedStatement ps = null;
	DashBoardDTO dto;
	
	String sql = "";
/*
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	Date currentD=new Date();
	String currentD1 = null;
	PreparedStatement stmtCD=null;*/
		try {
		con = DBConnection.getDBConnection();
		//cond= DBConnection.getDBConnection();
		String SQL_SELECT_DAILY_REPORT="select 0 as cnt,VARCHAR_FORMAT(current date, 'DD-MON-YYYY')  param,G.countg as Total_leads_submitted, A.counta as Total_Leads_Created, B.countb as Total_leads_sent_to_dialer, C.countc as Leads_Qualified, D.countd as Leads_in_Feasibility, F.countf as Leads_assigned from "+
		" (select  count(lead_id) counta  from LEAD_DATA where lead_id in  (  select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) = current date   ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_OPEN+ "   ) as A, "+
		" (select  count(lead_id) countb from LEAD_DATA where lead_id in "+
		" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) = current date  )) "  +
		" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_VERIFICATION+ " ) as B,"+
		" (select  count(lead_id) countc  from LEAD_DATA where lead_id in ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) = current date ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_QUALIFIED+ "  ) as C, "+
		"(select  count(lead_id) countd  from LEAD_DATA where lead_id in  ( "+
		" select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) = current date )) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_FEASIBILITY+ " ) as D," +
		" (select  count(lead_id) countf  from LEAD_DATA where lead_id in  (  select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) = current date   ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_ASSIGNED+ "   ) as F, " +
		"  (select count(1) as countg from lead_capture where date(CREATE_TIME) = current date ) as G ";
	
		StringBuffer query=new StringBuffer(SQL_SELECT_DAILY_REPORT);
		int numberDays=Integer.parseInt(PropertyReader.getAppValue("dailyreport.days"));
		for(int d=1;d<=numberDays;d++)
		{
		query.append(" union all ");

		query.append(" select "+d+" as cnt,VARCHAR_FORMAT((current date - " + d + " days ), 'DD-MON-YYYY') as param,G.countg as Total_leads_submitted, A.counta as Total_Leads_Created, B.countb as Total_leads_sent_to_dialer, C.countc as Leads_Qualified, D.countd as Leads_in_Feasibility, F.countf as Leads_assigned from "+
				" (select  count(lead_id) counta  from LEAD_DATA where lead_id in  (  select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) = (current date - " + d + " days )   ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_OPEN+ "   ) as A, "+
				" (select  count(lead_id) countb  from LEAD_DATA where lead_id in "+
				" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) = (current date - " + d + " days )  )) "  +
				" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_VERIFICATION+ " ) as B,"+
				" (select  count(lead_id) countc  from LEAD_DATA where lead_id in ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) = (current date - " + d + " days ) ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_QUALIFIED+ "  ) as C, "+
				"(select  count(lead_id) countd  from LEAD_DATA where lead_id in  ( "+
				" select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) = (current date - " + d + " days ) )) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_FEASIBILITY+ " ) as D," +
				" (select  count(lead_id) countf  from LEAD_DATA where lead_id in  (  select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where date(CREATE_TIME) = (current date - " + d + " days )   ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_ASSIGNED+ "   ) as F,  " +
				" (select count(1) as countg from lead_capture where date(CREATE_TIME) = (current date - " + d + " days ) ) as G  ");
		}
		
		logger.info("Inside DAO Daily  report .. " +query);
	
		ArrayList<DashBoardDTO> assignedList = new ArrayList<DashBoardDTO>();
		ps = con.prepareStatement(query.append(" order by cnt with ur ").toString());
		logger.info("SQL Stmt for View Daily Report" + query);
		rs = ps.executeQuery();
	

		int count=0;
		//sql="select (CURRENT DATE - ? days) from sysibm.sysdummy1 with ur ";
		//stmtCD=cond.prepareStatement(sql);
		while (rs.next()) {
			dto = new DashBoardDTO();
			dto.setSubmittedLead(rs.getInt("Total_leads_submitted"));
			dto.setCreatedLead(rs.getInt("Total_Leads_Created"));
			dto.setSenttoDialerLeads(rs.getInt("Total_leads_sent_to_dialer"));
			dto.setQualifiedLeads(rs.getInt("Leads_Qualified"));
			dto.setFeasibilityLeads(rs.getInt("Leads_in_Feasibility"));
			dto.setAssignedLeads(rs.getInt("Leads_assigned"));
			/*stmtCD.setInt(1, count);
			rscurrent=stmtCD.executeQuery();
			
			if(rscurrent.next())
			{
				currentD=rscurrent.getDate(1);
			}
			if(currentD !=null)
			{		
				currentD1 = dateFormat.format(currentD);
			}
			
			stmtCD.clearParameters();
			dto.setParam(currentD1);
			*/
			dto.setParam(rs.getString("param"));
			assignedList.add(dto);
			//logger.info("Get currentD1.............."+currentD1);
			count++;
		}
		logger.info("Daily Report... ");
		return assignedList;
	} catch (SQLException e) {
		e.printStackTrace();
		logger.error(
			"SQL Exception occured while Daily Report."
				+ "SQL Exception Message: "
				+ e.getMessage());
		throw new LMSException("Exception: " + e.getMessage(), e);
	} catch (Exception e) {
		e.printStackTrace();
		logger.error(
			" Exception occured while Daily Report."
				+ " Exception Message: "
				+ e.getMessage());
		throw new LMSException("Exception: " + e.getMessage(), e);
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
			//DBConnection.releaseResources(cond, stmtCD, rs);
			
		} catch (Exception e) {
			logger.error(
				"DAO Exception occured while Viewing Daily Report"
					+ "DAO Exception Message: "
					+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		}
	}
    }
    
    public ArrayList<DashBoardDTO> HourlyReport(String date) throws LMSException
    {

	Connection con = null;
	
	ResultSet rs = null;
	ResultSet rscurrent = null;
	PreparedStatement ps = null;
	DashBoardDTO dto;
	int startHour = -1;
	int currentHour=-1;
	ArrayList<DashBoardDTO> assignedList = new ArrayList<DashBoardDTO>();
	Statement stmt=null;
	ArrayList al=new ArrayList();
	String queryDB=null;
		try {
		con = DBConnection.getDBConnection();
		
		//startHour=Integer.parseInt(PropertyReader.getAppValue("hourlyreport.starthour")); //moved to database
		startHour=Integer.parseInt(getParamValue("START_HOUR"));
		
		
		
		queryDB="select hour (current time) from sysibm.sysdummy1  with ur";
		stmt=con.createStatement();
		rs=stmt.executeQuery(queryDB);
		if(rs.next())
		{
			currentHour=rs.getInt(1);
		}
		
		StringBuffer query=new StringBuffer("");
		
		logger.info(currentHour +"  currentHour " );
		logger.info(startHour +  "  startHour " );
		int diff=currentHour-startHour;
		if(diff>=0)
		{
		for(int j=0;j<=diff;j++)
		{
		al.add(date+" "+(startHour+j));
		al.add(date+" "+(startHour+1+j));
		}
		for(int i=0;i<al.size();i+=2)
		{
			logger.info("al first  ="+al.get(i));
			logger.info("al next  ="+al.get(i+1));
			if(i==0)// && i!=(al.size()-2))
			{
				query.append("select G.countg as Total_leads_submitted, A.counta as Total_Leads_Created, B.countb as Total_leads_sent_to_dialer, C.countc as Leads_Qualified, D.countd as Leads_in_Feasibility, F.countf as Leads_assigned from "+
						" (select  count(lead_id) counta  from lead_data where lead_id in "+
						" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where  TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' )" +
						" = '" + al.get(i) 
						//+"'  and '"+ al.get(i+1)
						+"'  )) "  +
						 " and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_OPEN+ "  ) as A, "+

						 " (select  count(lead_id) countb  from lead_data where lead_id in "+
						" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where  TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' )" +
						" = '" + al.get(i) 
						//+"'  and '"+ al.get(i+1)
						+"'  )) "  +
						 " and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_VERIFICATION+ "  ) as B,"+
						 " (select  count(lead_id) countc  from lead_data where lead_id in ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' ) " +
						 " = '" + al.get(i)
						//+"'  and '"+ al.get(i+1)
						 +"' ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_QUALIFIED+ " ) as C, "+
						 "(select  count(lead_id) countd  from lead_data where lead_id in  ( "+
						" select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' ) " +
						" = '" + al.get(i) 
						//+"'  and '"+ al.get(i+1)
						+"' )) and LEAD_STATUS_ID=310 ) as D, " +
						" (select  count(lead_id) countf  from lead_data where lead_id in  (  select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' ) " +
						" = '" + al.get(i) 
						//+"'  and '"+ al.get(i+1)
						+"'   ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_ASSIGNED+ "  ) as F,  " +
						" (select count(1) as countg from lead_capture where TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' ) = '" + al.get(i) 
						//+"'  and '"+ al.get(i+1)
						+"' ) as G ");
					
			}
			else //if(i!=(al.size()-2))
			{
				query.append("union all " +
						 " select G.countg as Total_leads_submitted, A.counta as Total_Leads_Created, B.countb as Total_leads_sent_to_dialer, C.countc as Leads_Qualified, D.countd as Leads_in_Feasibility, F.countf as Leads_assigned from "+
						 " (select  count(lead_id) counta  from lead_data where lead_id in "+
						 " ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where  TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' )" +
						 " = '" + al.get(i) 
						 //+"'  and '"+ al.get(i+1)
						 +"'  )) "  +
						 " and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_OPEN+ "  ) as A, "+

						 " (select  count(lead_id) countb  from lead_data where lead_id in "+
						 " ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where  TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' )" +
						 " = '" + al.get(i) 
						 //+"'  and '"+ al.get(i+1)
						 +"'  )) "  +
						 " and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_VERIFICATION+ "  ) as B,"+
						 " (select  count(lead_id) countc  from lead_data where lead_id in ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' ) " +
						 " = '" + al.get(i)
						 //+"'  and '"+ al.get(i+1)
						 +"' ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_QUALIFIED+ " ) as C, "+
						 "(select  count(lead_id) countd  from lead_data where lead_id in  ( "+
						 " select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' ) " +
						 " = '" + al.get(i) 
						 //+"'  and '"+ al.get(i+1)
						 +"' )) and LEAD_STATUS_ID=310 ) as D, " +
						 " (select  count(lead_id) countf  from lead_data where lead_id in  (  select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' ) " +
						 " = '" + al.get(i) 
						 //+"'  and '"+ al.get(i+1)
						 +"'   ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_ASSIGNED+ "  ) as F,  " +
						 " (select count(1) as countg from lead_capture where TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' ) = '" + al.get(i) 
						 //+"'  and '"+ al.get(i+1)
						 +"' ) as G ");

			}
			/*else
			{
				query.append("union all " +
						"select G.countg as Total_leads_submitted, A.counta as Total_Leads_Created, B.countb as Total_leads_sent_to_dialer, C.countc as Leads_Qualified, D.countd as Leads_in_Feasibility, F.countf as Leads_assigned from "+
						" (select  count(lead_id) counta  from lead_data where lead_id in "+
						" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where  TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' ) >= '" + al.get(i) +"'   )) "  +
						" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_OPEN+ "  ) as A, "+

						" (select  count(lead_id) countb  from lead_data where lead_id in "+
						" ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where  TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' ) >= '" + al.get(i) +"'   )) "  +
						" and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_VERIFICATION+ "  ) as B,"+
						" (select  count(lead_id) countc  from lead_data where lead_id in ( select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' ) >= '" + al.get(i) +"'  ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_QUALIFIED+ " ) as C, "+
					    "(select  count(lead_id) countd  from lead_data where lead_id in  ( "+
						" select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in ( select LEAD_CAPTURED_DATA_ID from lead_capture where TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' ) >= '" + al.get(i) +"'  )) and LEAD_STATUS_ID=310 ) as D, " +
						" (select  count(lead_id) countf  from lead_data where lead_id in  (  select LEAD_ID from lead_details where LEAD_CAPTURED_DATA_ID in  ( select LEAD_CAPTURED_DATA_ID from lead_capture where TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' ) >= '" + al.get(i) +"'    ) ) and LEAD_STATUS_ID="+ Constants.LEAD_STATUS_ASSIGNED+ "  ) as F,  " +
						" (select count(1) as countg from lead_capture where TO_CHAR(CREATE_TIME,'YYYY-MM-DD HH24' ) >= '" + al.get(i) +"' ) as G ");
					
			}*/
			//logger.info(query);
		}
		
	
		
		
		ps = con.prepareStatement(query.append(" with ur ").toString());
		logger.info("SQL Stmt for View Hourly Report" + query);
		rs = ps.executeQuery();
	

		int count=0;
		
		while (rs.next()) {
			String startH="";
			String endH="";
			dto = new DashBoardDTO();
			dto.setSubmittedLead(rs.getInt("Total_leads_submitted"));
			dto.setCreatedLead(rs.getInt("Total_Leads_Created"));
			dto.setSenttoDialerLeads(rs.getInt("Total_leads_sent_to_dialer"));
			dto.setQualifiedLeads(rs.getInt("Leads_Qualified"));
			dto.setFeasibilityLeads(rs.getInt("Leads_in_Feasibility"));
			dto.setAssignedLeads(rs.getInt("Leads_assigned"));
			
			if((startHour+count)!=currentHour)
			{
				if((startHour+count)>12)
				{
					startH=(startHour+count) +":00 PM";
				}
				else
				{
					startH=(startHour+count) +":00 AM";
				}
				if((startHour+1+count)>12)
				{
					endH=(startHour+1+count) +":00 PM";
				}
				else
				{
					endH=(startHour+1+count) +":00 AM";
				}
				
				dto.setParam(startH+" - "+endH);
			}
			else
			{
			
				if((startHour+count)>12)
				{
					startH=(startHour+count) +":00 PM";
				}
				else
				{
					startH=(startHour+count) +":00 AM";
				}
				endH="Present";
				//dto.setParam((startHour+count) +":00 - Present ");
				dto.setParam(startH+" - "+endH);
			}
			assignedList.add(dto);
		//	logger.info("Get currentD1.............."+currentD1);
			count++;
		}
		logger.info("Daily Report... ");
		}
	} catch (SQLException e) {
		e.printStackTrace();
		logger.error(
			"SQL Exception occured while Daily Report."
				+ "SQL Exception Message: "
				+ e.getMessage());
		throw new LMSException("Exception: " + e.getMessage(), e);
	} catch (Exception e) {
		e.printStackTrace();
		logger.error(
			" Exception occured while Daily Report."
				+ " Exception Message: "
				+ e.getMessage());
		throw new LMSException("Exception: " + e.getMessage(), e);
	} finally {
		try {
			DBConnection.releaseResources(con, ps, rs);
			DBConnection.releaseResources(null, stmt, rs);
			
		} catch (Exception e) {
			logger.error(
				"DAO Exception occured while Viewing Daily Report"
					+ "DAO Exception Message: "
					+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		}
	}
	
	return assignedList;
    }
	public String getParamValue(String paramName) throws LMSException
	{
		ResultSet rs = null;
		PreparedStatement stmt = null;
		String paramValue="";
		String queryDB=null;
		Connection con=null;
		try
		{
			con = DBConnection.getDBConnection();
			queryDB="select PARAM_NAME from PARAMETER_MASTER where FORM_NAME=? with ur";
			stmt=con.prepareStatement(queryDB);
			stmt.setString(1, paramName);
			rs=stmt.executeQuery();
			if(rs.next())
			{
				paramValue=rs.getString("PARAM_NAME");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(
				" Exception occured while Daily Report."
					+ " Exception Message: "
					+ e.getMessage());
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
		
			try {
			DBConnection.releaseResources(con,stmt,rs);
			} catch (Exception e) {
				logger.error(
					"DAO Exception occured while Viewing Daily Report"
						+ "DAO Exception Message: "
						+ e.getMessage());
				throw new LMSException("Exception: " + e.getMessage(), e);
			}
		}
		return paramValue;
	}

}
