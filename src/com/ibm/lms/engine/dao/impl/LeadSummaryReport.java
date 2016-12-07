package com.ibm.lms.engine.dao.impl;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.ibm.lms.dao.DBConnection;
import com.ibm.lms.engine.util.Constants;


public class LeadSummaryReport {
	
	private static final Logger logger = Logger.getLogger(LeadSummaryReport.class);
	
	private static String CIRCLE_LIST="select CIRCLE_NAME,CIRCLE_ID from CIRCLE_MSTR with ur";
	/*private static String GET_COUNT =  "select * from  "   
										+" (select count(1) as CURRENT_MONTH  " 
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  " 
										+" where a.PROSPECT_ID = b.PROSPECT_ID   "
										+" and c.LEAD_ID=a.LEAD_ID  "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_OPEN+","+Constants.LEAD_STATUS_QUALIFICATION+")  and b.CIRCLE_ID = ? "
										+" and month(c.LEAD_ASSIGNMENT_TIME) = month(current date)) a,  " 
										+" (select count(1) as MONTH_1   "
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  " 
										+" where a.PROSPECT_ID = b.PROSPECT_ID   "
										+" and c.LEAD_ID=a.LEAD_ID  "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_OPEN+","+Constants.LEAD_STATUS_QUALIFICATION+")  and b.CIRCLE_ID = ? "
										+" and month(c.LEAD_ASSIGNMENT_TIME) = month(current date) -1 )b,  " 
										+" (select count(1) as MONTH_2   "
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  " 
										+" where a.PROSPECT_ID = b.PROSPECT_ID   "
										+" and c.LEAD_ID=a.LEAD_ID  "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_OPEN+","+Constants.LEAD_STATUS_QUALIFICATION+")  and b.CIRCLE_ID = ? "
										+" and month(c.LEAD_ASSIGNMENT_TIME) = month(current date) -2 )c,  " 
										+" (select count(1) as MONTH_3   "
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  " 
										+" where a.PROSPECT_ID = b.PROSPECT_ID   "
										+" and c.LEAD_ID=a.LEAD_ID  "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_OPEN+","+Constants.LEAD_STATUS_QUALIFICATION+")  and b.CIRCLE_ID = ? "
										+" and month(c.LEAD_ASSIGNMENT_TIME) = month(current date -3 month) )d with ur";*/
	
	
	private static String GET_COUNT  = "select sum(a.CURRENT_MONTH + e_Count + f_Count) as CURRENT_MONTH,  "
										+" sum(b.MONTH_1 + g_Count + h_Count) as MONTH_1,sum(c.MONTH_2 + i_Count + j_Count) as MONTH_2, sum(d.MONTH_3 + k_Count + l_Count) as MONTH_3 from (select count(1) as CURRENT_MONTH " 
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c where a.PROSPECT_ID = b.PROSPECT_ID and c.LEAD_ID=a.LEAD_ID and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_OPEN+","+Constants.LEAD_STATUS_QUALIFICATION+") and b.CIRCLE_ID = ?  "
										+" and month(c.TRANSACTION_TIME) = month(current date)) a, (select count(1) as MONTH_1 from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c where a.PROSPECT_ID = b.PROSPECT_ID and c.LEAD_ID=a.LEAD_ID "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_OPEN+","+Constants.LEAD_STATUS_QUALIFICATION+") and b.CIRCLE_ID = ? and month(c.TRANSACTION_TIME) = month(current date -1 month) )b, (select count(1) as MONTH_2 from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  "
										+" where a.PROSPECT_ID = b.PROSPECT_ID and c.LEAD_ID=a.LEAD_ID and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_OPEN+","+Constants.LEAD_STATUS_QUALIFICATION+") and b.CIRCLE_ID = ? and month(c.TRANSACTION_TIME) = month(current date -2 month) )c, (select count(1) as MONTH_3 from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c "  
										+" where a.PROSPECT_ID = b.PROSPECT_ID and c.LEAD_ID=a.LEAD_ID and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_OPEN+","+Constants.LEAD_STATUS_QUALIFICATION+") and b.CIRCLE_ID = ? and month(c.TRANSACTION_TIME) = month(current date -3 month) )d , "
										+" (select count(1) as e_Count from DIRTY_LEAD a, CIRCLE_MSTR b where ucase(trim(a.CIRCLE))=ucase(b.CIRCLE_DESC) and b.CIRCLE_ID = ? and  month(CREATE_TIME) = month(current date)) e , "
										+" (select count(1) as f_Count from DUPLICATE_LEAD a, CIRCLE_MSTR b where ucase(trim(a.CIRCLE))=ucase(b.CIRCLE_DESC) and b.CIRCLE_ID = ? and month(CREATE_TIME) = month(current date)) f , "
										+" (select count(1) as g_Count from DIRTY_LEAD a, CIRCLE_MSTR b where ucase(trim(a.CIRCLE))=ucase(b.CIRCLE_DESC) and b.CIRCLE_ID = ? and month(CREATE_TIME) = month(current date -1 month )) g , "
										+" (select count(1) as h_Count from DUPLICATE_LEAD a, CIRCLE_MSTR b where ucase(trim(a.CIRCLE))=ucase(b.CIRCLE_DESC) and b.CIRCLE_ID = ? and month(CREATE_TIME) = month(current date -1 month )) h , "
										+" (select count(1) as i_Count from DIRTY_LEAD a, CIRCLE_MSTR b where ucase(trim(a.CIRCLE))=ucase(b.CIRCLE_DESC) and b.CIRCLE_ID = ? and month(CREATE_TIME) = month(current date -2 month)) i , "
										+" (select count(1) as j_Count from DUPLICATE_LEAD a, CIRCLE_MSTR b where ucase(trim(a.CIRCLE))=ucase(b.CIRCLE_DESC) and b.CIRCLE_ID = ? and month(CREATE_TIME) = month(current date - 2 month )) j , "
										+" (select count(1) as k_Count from DIRTY_LEAD a, CIRCLE_MSTR b where ucase(trim(a.CIRCLE))=ucase(b.CIRCLE_DESC) and b.CIRCLE_ID = ? and month(CREATE_TIME) = month(current date - 3 month)) k , "
										+" (select count(1) as l_Count from DUPLICATE_LEAD a, CIRCLE_MSTR b where ucase(trim(a.CIRCLE))=ucase(b.CIRCLE_DESC) and b.CIRCLE_ID = ? and month(CREATE_TIME)  = month(current date - 3 month)) l  "
										+" with ur";
	
	
	private static String GET_COUNT_CONTACTABLE = "select * from  "   
										+" (select count(1) as CURRENT_MONTH  " 
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  " 
										+" where a.PROSPECT_ID = b.PROSPECT_ID   "
										+" and c.LEAD_ID=a.LEAD_ID  "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_QUALIFICATION+")  and b.CIRCLE_ID = ? "
										+" and month(c.TRANSACTION_TIME) = month(current date)) a,  " 
										+" (select count(1) as MONTH_1   "
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  " 
										+" where a.PROSPECT_ID = b.PROSPECT_ID   "
										+" and c.LEAD_ID=a.LEAD_ID  "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_QUALIFICATION+")  and b.CIRCLE_ID = ? "
										+" and month(c.TRANSACTION_TIME) = month(current date -1 month) )b,  " 
										+" (select count(1) as MONTH_2   "
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  " 
										+" where a.PROSPECT_ID = b.PROSPECT_ID   "
										+" and c.LEAD_ID=a.LEAD_ID  "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_QUALIFICATION+")  and b.CIRCLE_ID = ? "
										+" and month(c.TRANSACTION_TIME) = month(current date -2 month) )c,  " 
										+" (select count(1) as MONTH_3   "
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  " 
										+" where a.PROSPECT_ID = b.PROSPECT_ID   "
										+" and c.LEAD_ID=a.LEAD_ID  "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_QUALIFICATION+")  and b.CIRCLE_ID = ? "
										+" and month(c.TRANSACTION_TIME) = month(current date -3 month) )d with ur";
	
	private static String GET_COUNT_APPOINTMENT= "select * from  "   
										+" (select count(1) as CURRENT_MONTH  " 
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  " 
										+" where a.PROSPECT_ID = b.PROSPECT_ID   "
										+" and c.LEAD_ID=a.LEAD_ID  "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_ASSIGNED+")  and b.CIRCLE_ID = ? "
										+" and month(c.TRANSACTION_TIME) = month(current date)) a,  " 
										+" (select count(1) as MONTH_1   "
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  " 
										+" where a.PROSPECT_ID = b.PROSPECT_ID   "
										+" and c.LEAD_ID=a.LEAD_ID  "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_ASSIGNED+")  and b.CIRCLE_ID = ? "
										+" and month(c.TRANSACTION_TIME) = month(current date -1 month) )b,  " 
										+" (select count(1) as MONTH_2   "
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  " 
										+" where a.PROSPECT_ID = b.PROSPECT_ID   "
										+" and c.LEAD_ID=a.LEAD_ID  "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_ASSIGNED+")  and b.CIRCLE_ID = ? "
										+" and month(c.TRANSACTION_TIME) = month(current date -2 month) )c,  " 
										+" (select count(1) as MONTH_3   "
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  " 
										+" where a.PROSPECT_ID = b.PROSPECT_ID   "
										+" and c.LEAD_ID=a.LEAD_ID  "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_ASSIGNED+")  and b.CIRCLE_ID = ? "
										+" and month(c.TRANSACTION_TIME) = month(current date -3 month) )d with ur";
	
	
	private static String GET_COUNT_WON = "select * from  "   
										+" (select count(1) as CURRENT_MONTH  " 
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  " 
										+" where a.PROSPECT_ID = b.PROSPECT_ID   "
										+" and c.LEAD_ID=a.LEAD_ID  "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_WON+")  and b.CIRCLE_ID = ? "
										+" and month(c.TRANSACTION_TIME) = month(current date)) a,  " 
										+" (select count(1) as MONTH_1   "
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  " 
										+" where a.PROSPECT_ID = b.PROSPECT_ID   "
										+" and c.LEAD_ID=a.LEAD_ID  "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_WON+")  and b.CIRCLE_ID = ? "
										+" and month(c.TRANSACTION_TIME) = month(current date -1 month) )b,  " 
										+" (select count(1) as MONTH_2   "
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  " 
										+" where a.PROSPECT_ID = b.PROSPECT_ID   "
										+" and c.LEAD_ID=a.LEAD_ID  "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_WON+")  and b.CIRCLE_ID = ? "
										+" and month(c.TRANSACTION_TIME) = month(current date -2 month) )c,  " 
										+" (select count(1) as MONTH_3   "
										+" from LEAD_DATA a,LEAD_PROSPECT_CUSTOMER b,LEAD_TRANSACTION c  " 
										+" where a.PROSPECT_ID = b.PROSPECT_ID   "
										+" and c.LEAD_ID=a.LEAD_ID  "
										+" and c.LEAD_STATUS_ID in ("+Constants.LEAD_STATUS_WON+")  and b.CIRCLE_ID = ? "
										+" and month(c.TRANSACTION_TIME) = month(current date -3 month) )d with ur";
													
	
	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		
		int circleId=0;
		String circleName="";
		
		String monthName="";
		String yearName="";
		String mmmYY_2="";
		String mmmYY_3="";
		String mmmYY_4="";
		
		
		int sumLeadMonth_3=0;
		int sumLeadMonth_2=0;
		int sumLeadMonth_1=0;
		int sumLeadMonth_0=0;
		
		int sumContactMonth_3=0;
		int sumContactMonth_2=0;
		int sumContactMonth_1=0;
		int sumContactMonth_0=0;
		
		int sumAppointMonth_3=0;
		int sumAppointMonth_2=0;
		int sumAppointMonth_1=0;
		int sumAppointMonth_0=0;
		
		int sumWonMonth_3=0;
		int sumWonMonth_2=0;
		int sumWonMonth_1=0;
		int sumWonMonth_0=0;
		
		try
		{ 
			Calendar cal=new GregorianCalendar();
			Calendar cal2=new GregorianCalendar();
			Calendar cal3=new GregorianCalendar();
			Calendar cal4=new GregorianCalendar();
			
		/*	cal.set(cal.MONTH, cal.MONTH-1);
			java.util.Date d = new java.util.Date(cal.getTimeInMillis());*/
			/*logger.info("Month Name :"+new SimpleDateFormat("MMMM").format(d));*/
			//PropertyReader.setBundleName(Constants.PROPERTIES_FILE_LOCATION);
			String path = com.ibm.lms.common.PropertyReader.getAppValue("lms.report.path");
		    
			FileWriter fw = new FileWriter(path+"CircleSummaryReport_"+cal4.get(cal4.DATE)+"_"+(cal4.get(cal4.MONTH)+1)+"_"+cal4.get(cal4.YEAR)+".csv");
			PrintWriter out = new PrintWriter(fw);
			
			out.print(Constants.CIRCLE_SUMMARY_HDR_ALL);
			out.print(",");
			out.print(Constants.CIRCLE_SUMMARY_HDR_LEADS);
			out.print(",");
			out.print(",");
			out.print(",");
			out.print(",");
			out.print(Constants.CIRCLE_SUMMARY_HDR_CONTACTABLE);
			out.print(",");
			out.print(",");
			out.print(",");
			out.print(",");
			out.print(Constants.CIRCLE_SUMMARY_HDR_APPOINTMENT);
			out.print(",");
			out.print(",");
			out.print(",");
			out.print(",");
			out.print(Constants.CIRCLE_SUMMARY_HDR_WON);
			out.print("\n");
			
			
			out.print(Constants.CIRCLE_SUMMARY_HDR_CIRCLES);
			out.print(",");
			
			cal.set(cal.MONTH, cal.MONTH-4);
			java.util.Date d = new java.util.Date(cal.getTimeInMillis());
			monthName = new SimpleDateFormat("MMM").format(d);
			yearName=  cal.get(cal.YEAR)+"";
			mmmYY_4 = monthName+"-"+yearName;
			out.print(mmmYY_4);
			out.print(",");
			
			cal2.set(cal2.MONTH, cal2.MONTH-3);
			d = new java.util.Date(cal2.getTimeInMillis());
			monthName = new SimpleDateFormat("MMM").format(d);
			yearName=  cal2.get(cal.YEAR)+"";
			mmmYY_3 = monthName+"-"+yearName;
			out.print(mmmYY_3);
			out.print(",");
			
			cal3.set(cal3.MONTH, cal3.MONTH-2);
			d = new java.util.Date(cal3.getTimeInMillis());
			monthName = new SimpleDateFormat("MMM").format(d);
			yearName=  cal3.get(cal.YEAR)+"";
			mmmYY_2 = monthName+"-"+yearName;
			out.print(mmmYY_2);
			out.print(",");
			
			out.print(Constants.CIRCLE_SUMMARY_HDR_MTD);
			out.print(",");
			
			out.print(mmmYY_4);
			out.print(",");
			out.print(mmmYY_3);
			out.print(",");
			out.print(mmmYY_2);
			out.print(",");
			out.print("MTD");
			out.print(",");
			out.print(mmmYY_4);
			out.print(",");
			out.print(mmmYY_3);
			out.print(",");
			out.print(mmmYY_2);
			out.print(",");
			out.print("MTD");
			out.print(",");
			out.print(mmmYY_4);
			out.print(",");
			out.print(mmmYY_3);
			out.print(",");
			out.print(mmmYY_2);
			out.print(",");
			out.print("MTD");
			out.print("\n");
			
			
			
			
			con = DBConnection.getDBConnection();
			ps=con.prepareStatement(CIRCLE_LIST);
			ps1=con.prepareStatement(GET_COUNT);
			ps2=con.prepareStatement(GET_COUNT_CONTACTABLE);
			ps3=con.prepareStatement(GET_COUNT_APPOINTMENT);
			ps4=con.prepareStatement(GET_COUNT_WON);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				circleId =  rs.getInt("CIRCLE_ID");
				circleName =  rs.getString("CIRCLE_NAME");
				
				out.print(circleName);
				out.print(",");
				
				logger.info(circleName);
				ps1.setInt(1, circleId);
				ps1.setInt(2, circleId);
				ps1.setInt(3, circleId);
				ps1.setInt(4, circleId);
				ps1.setInt(5, circleId);
				ps1.setInt(6, circleId);
				ps1.setInt(7, circleId);
				ps1.setInt(8, circleId);
				ps1.setInt(9, circleId);
				ps1.setInt(10, circleId);
				ps1.setInt(11, circleId);
				ps1.setInt(12, circleId);
				
				rs1=ps1.executeQuery();
				while(rs1.next())
				{
					out.print(rs1.getInt("MONTH_3"));
					out.print(",");
					out.print(rs1.getInt("MONTH_2"));
					out.print(",");
					out.print(rs1.getInt("MONTH_1"));
					out.print(",");
					out.print(rs1.getInt("CURRENT_MONTH"));
					out.print(",");
					
					sumLeadMonth_3= rs1.getInt("MONTH_3") +  sumLeadMonth_3 ;
					sumLeadMonth_2= rs1.getInt("MONTH_2") +  sumLeadMonth_2 ;
					sumLeadMonth_1= rs1.getInt("MONTH_1") +  sumLeadMonth_1 ;
					sumLeadMonth_0= rs1.getInt("CURRENT_MONTH") +  sumLeadMonth_0 ;
				}
				DBConnection.releaseResources(null, null, rs1);
				ps2.setInt(1, circleId);
				//ps2.setString(2, Constants.LEAD_STATUS_OPEN);
				ps2.setInt(2, circleId);
				//ps2.setString(4, Constants.LEAD_STATUS_OPEN);
				ps2.setInt(3, circleId);
				//ps2.setString(6, Constants.LEAD_STATUS_OPEN);
				ps2.setInt(4, circleId);
				//ps2.setString(8, Constants.LEAD_STATUS_OPEN);
				
				rs2=ps2.executeQuery();
				while(rs2.next())
				{
					out.print(rs2.getInt("MONTH_3"));
					out.print(",");
					out.print(rs2.getInt("MONTH_2"));
					out.print(",");
					out.print(rs2.getInt("MONTH_1"));
					out.print(",");
					out.print(rs2.getInt("CURRENT_MONTH"));
					out.print(",");
					
					sumContactMonth_3= rs2.getInt("MONTH_3") +  sumContactMonth_3 ;
					sumContactMonth_2= rs2.getInt("MONTH_2") +  sumContactMonth_2 ;
					sumContactMonth_1= rs2.getInt("MONTH_1") +  sumContactMonth_1 ;
					sumContactMonth_0= rs2.getInt("CURRENT_MONTH") +  sumContactMonth_0 ;
					
				}
				DBConnection.releaseResources(null, null, rs2);
				ps3.setInt(1, circleId);
				//ps3.setString(2, Constants.LEAD_STATUS_OPEN);
				ps3.setInt(2, circleId);
				//ps3.setString(4, Constants.LEAD_STATUS_OPEN);
				ps3.setInt(3, circleId);
				//ps3.setString(6, Constants.LEAD_STATUS_OPEN);
				ps3.setInt(4, circleId);
				//ps3.setString(8, Constants.LEAD_STATUS_OPEN);
				
				rs3=ps3.executeQuery();
				while(rs3.next())
				{
					out.print(rs3.getInt("MONTH_3"));
					out.print(",");
					out.print(rs3.getInt("MONTH_2"));
					out.print(",");
					out.print(rs3.getInt("MONTH_1"));
					out.print(",");
					out.print(rs3.getInt("CURRENT_MONTH"));
					out.print(",");
					
					sumAppointMonth_3= rs3.getInt("MONTH_3") +  sumAppointMonth_3 ;
					sumAppointMonth_2= rs3.getInt("MONTH_2") +  sumAppointMonth_2 ;
					sumAppointMonth_1= rs3.getInt("MONTH_1") +  sumAppointMonth_1 ;
					sumAppointMonth_0= rs3.getInt("CURRENT_MONTH") +  sumAppointMonth_0 ;
					
				}
				DBConnection.releaseResources(null, null, rs3);
				
				ps4.setInt(1, circleId);
				//ps4.setString(2, Constants.LEAD_STATUS_OPEN);
				ps4.setInt(2, circleId);
				//ps4.setString(4, Constants.LEAD_STATUS_OPEN);
				ps4.setInt(3, circleId);
				//ps4.setString(6, Constants.LEAD_STATUS_OPEN);
				ps4.setInt(4, circleId);
				//ps4.setString(8, Constants.LEAD_STATUS_OPEN);
				
				rs4=ps4.executeQuery();
				while(rs4.next())
				{
					out.print(rs4.getInt("MONTH_3"));
					out.print(",");
					out.print(rs4.getInt("MONTH_2"));
					out.print(",");
					out.print(rs4.getInt("MONTH_1"));
					out.print(",");
					out.print(rs4.getInt("CURRENT_MONTH"));
					out.print(",");
					
					sumWonMonth_3= rs4.getInt("MONTH_3") +  sumWonMonth_3 ;
					sumWonMonth_2= rs4.getInt("MONTH_2") +  sumWonMonth_2 ;
					sumWonMonth_1= rs4.getInt("MONTH_1") +  sumWonMonth_1 ;
					sumWonMonth_0= rs4.getInt("CURRENT_MONTH") +  sumWonMonth_0 ;
				}
				DBConnection.releaseResources(null, null, rs4);
				out.print("\n");
			}
			DBConnection.releaseResources(null, null, rs);
			out.print(Constants.CIRCLE_SUMMARY_HDR_NATIONAL);
			out.print(",");
			out.print(sumLeadMonth_3);
			out.print(",");
			out.print(sumLeadMonth_2);
			out.print(",");
			out.print(sumLeadMonth_1);
			out.print(",");
			out.print(sumLeadMonth_0);
			out.print(",");
			
			out.print(sumContactMonth_3);
			out.print(",");
			out.print(sumContactMonth_2);
			out.print(",");
			out.print(sumContactMonth_1);
			out.print(",");
			out.print(sumContactMonth_0);
			out.print(",");
			
			out.print(sumAppointMonth_3);
			out.print(",");
			out.print(sumAppointMonth_2);
			out.print(",");
			out.print(sumAppointMonth_1);
			out.print(",");
			out.print(sumAppointMonth_0);
			out.print(",");
			
			out.print(sumWonMonth_3);
			out.print(",");
			out.print(sumWonMonth_2);
			out.print(",");
			out.print(sumWonMonth_1);
			out.print(",");
			out.print(sumWonMonth_0);
			out.print(",");
			
			 //Flush the output to the file
			   out.flush();
			       
			   //Close the Print Writer
			   out.close();
			       
			   //Close the File Writer
			   fw.close();       
		}
		catch(Exception ex)
		{
			logger.info(ex);
		}
		finally
		{
			try {
			DBConnection.releaseResources(null, null, rs);
			DBConnection.releaseResources(null, ps1, rs1);
			DBConnection.releaseResources(null, ps2, rs2);
			DBConnection.releaseResources(null, ps3, rs3);
			DBConnection.releaseResources(con, ps4, rs4);
			}
			catch(Exception ex)
			{
				logger.info(ex);
			}
			
		}
	}
	
	
}
