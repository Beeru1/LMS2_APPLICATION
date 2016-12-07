package com.ibm.lms.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.core.exception.DAOException;
import com.ibm.lms.common.Constants;
import com.ibm.lms.dao.DBConnection;
import com.ibm.lms.dao.UpdateLeadDispositionDAO;
import com.ibm.lms.dto.LeadData;
import com.ibm.lms.dto.LeadDispositionDTO;
import com.ibm.lms.dto.ResponseMessageDisposition;

public class UpdateLeadDispositionDAOImpl implements UpdateLeadDispositionDAO {
	
	public static Map<String, String> statusMap = new HashMap<String, String>();
	
	
	private static final Logger logger = Logger.getLogger(UpdateLeadDispositionDAOImpl.class);
	
	public static String GET_MAP="select LEAD_STATUS_ID,LEAD_STATUS from LEAD_STATUS with ur";
	
	public static String SQL_INSERT_LEAD_DISPOSITION="INSERT INTO LEAD_DISPOSITION( DISPOSITION_CODE, LEAD_ID, IS_DIALER_DISPOSITION,"
													+" CALL_RETRY_COUNT, SETUP_TIME, RINGING_TIME, IVR_TIME, CUSTOMER_TALK_TIME, CUSTOMER_HOLDTIME, " 
													+" DIALER_AGENCY_CODE, AGENT_DISPOSITION_CODE , TRANSACTION_TIME ) VALUES(? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? ,current_timestamp )";
													
	public static String SQL_GET_LEAD_STATUS = " select * from LEAD_TRANSACTION where  LEAD_STATUS_ID = ? and LEAD_ID=? with ur ";
	//.public static String SQL_UPDATE_LEAD_STATUS="update LEAD_TRANSACTION set LEAD_STATUS_ID =?  where LEAD_ID=? ";
	
	public static String SQL_INSERT_TRANS="INSERT INTO LEAD_TRANSACTION(LEAD_ID, PRODUCT_ID, LEAD_ASSIGNMENT_TIME, LEAD_CLOSURE_TIME, LEAD_STATUS_ID, TRANSACTION_TIME, EXPECTED_CLOSURE_DATE, SUB_STATUS_ID, UPDATED_BY , LEAD_ASSIGNED_PRIMARY_USER," +
			" LEAD_PRODUCT_ID ) " // Added by Parnika for LMS Phase 2
			+" VALUES(?, ?, ?, current_timestamp, ? , current_timestamp, ? , ? , ? , ?, ?)";
	

	/* Added by Parnika for capturing disposition details in Lead data for LMS Phase2 Drop3 */
	
	public static String SQL_UPDATE_LEAD_DATA="UPDATE LEAD_DATA SET DISPOSITION_RECEIVED = 'Y' ,  DISPOSITION_CODE = ? ,  DISPOSITION_COUNT = ? , DISPOSITION_UPDATED_DATE = current timestamp  WHERE LEAD_ID = ? ";

	public static String SQL_UPDATE_LEAD_DATA_FIRST="UPDATE LEAD_DATA SET DISPOSITION_RECEIVED = 'Y' ,  DISPOSITION_CODE = ? ,  DISPOSITION_COUNT = ? , DISPOSITION_UPDATED_DATE = current timestamp , DISPOSITION_FIRST_DATE = current timestamp  WHERE LEAD_ID = ? ";

	public static String SQL_SELECT_LEAD_DISPOSITION="SELECT COUNT(1) FROM  LEAD_DISPOSITION  WHERE LEAD_ID = ? WITH UR";

	/* End of channges by Parnika */
	
	
	//Added by srikant 
	
	
private static UpdateLeadDispositionDAOImpl updateLeadDispositionDAOImpl=null;
	
	private UpdateLeadDispositionDAOImpl(){
		
	}
	
	public static UpdateLeadDispositionDAOImpl updateLeadDispositionDAOInstance()
	{
		if(updateLeadDispositionDAOImpl==null)
		{
			updateLeadDispositionDAOImpl=new UpdateLeadDispositionDAOImpl();
		}
		return updateLeadDispositionDAOImpl;
		
	}
	public static Map getStatusMap()
	{
		logger.info("Calling getStatusMap function for populating status");
		Map<String, String> map = new HashMap<String, String>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		try
		{ 
			con = DBConnection.getDBConnection();
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
				DBConnection.releaseResources(con, ps, rs);
			} catch(Exception ex)
			{
				logger.info(ex);
			}
		}
		return map;
	}
	
	public ResponseMessageDisposition[] updateLeadDisposition(LeadData[] leadData) throws DAOException
	{
		logger.info("started method updateLeadDisposition");
		Connection con = null;
		LeadData leadDtoObj=null;
		LeadDispositionDTO leadDispositionDtoObj=null;
		LeadDispositionDTO[] leadDispositionList =null;
		String leadId="";
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs1=null;
		ResultSet rs2=null;
		int count=0;
		String leadStatus="";
		ResponseMessageDisposition[] arrresponse=null;
		ResponseMessageDisposition objResponse=null;
		java.sql.Timestamp leadAssignTime=null;
		String primaryUser="";
		java.sql.Timestamp expectCloserTime=null;
		String productId="";
		String leadProductId = "";
		String dispositionCount = "";
		try
		{ 
			statusMap = getStatusMap();
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(SQL_INSERT_LEAD_DISPOSITION);
			ps1 = con.prepareStatement(SQL_GET_LEAD_STATUS);
			ps2 = con.prepareStatement(SQL_INSERT_TRANS);
			ps4 = con.prepareStatement(SQL_SELECT_LEAD_DISPOSITION);
			if(leadData != null && leadData.length > 0) {
			
			arrresponse = new ResponseMessageDisposition[leadData.length];
			
			logger.info("Total "+leadData.length +" leads need to be updated for Desposition");
			
			for(int i=0; i< leadData.length ; i++)
			{
				con.setAutoCommit(false);
				leadDtoObj = (LeadData) (leadData[i] );
				
				leadId = leadDtoObj.getLeadId();
				try {
					logger.info("leadId::::::::::::::"+leadId);
						objResponse = null;
						objResponse = new ResponseMessageDisposition();
						objResponse.setLeadId(leadId+"");
						//logger.info("1111");
						ps1.setString(1, Constants.LEAD_STATUS_VERIFICATION);
						ps1.setString(2, leadId+"");
						rs1=ps1.executeQuery();
						//logger.info("2222222");
						if(rs1.next())
						{
							leadAssignTime = rs1.getTimestamp("LEAD_ASSIGNMENT_TIME");
							expectCloserTime =rs1.getTimestamp("EXPECTED_CLOSURE_DATE");
							productId = rs1.getString("PRODUCT_ID");
							primaryUser = rs1.getString("LEAD_ASSIGNED_PRIMARY_USER");
							
							/* Added by Parnika for LMS Phase 2 */
							leadProductId= rs1.getString("LEAD_PRODUCT_ID");
							/* End of changes by Parnika */
							
						}
						//logger.info("33333333");
						com.ibm.lms.dao.DBConnection.releaseResources(null, null, rs1);
						
						
						
						
						leadDispositionList = (LeadDispositionDTO[]) leadDtoObj.getCallDispositionList();
						if(leadDispositionList != null && leadDispositionList.length > 0 ) {
						logger.info("Total "+leadDispositionList.length +" call Desposition need to be inserted for lead id::"+leadId);
						for(int j=0; j < leadDispositionList.length ; j++) {
							leadDispositionDtoObj = (LeadDispositionDTO) leadDispositionList[j];
							 
							//validation of date
							logger.info("Validation process");

							if(j==0)
							{
								ps2.setString(1,leadId+"" );
								ps2.setString(2,productId);
								ps2.setTimestamp(3,leadAssignTime );
								//logger.info("44444444");
								ps2.setString(4,Constants.LEAD_STATUS_ID_DISPOSITION);
								ps2.setTimestamp(5,expectCloserTime );
								ps2.setString(6,Constants.LEAD_STATUS_ID_DISPOSITION);
								ps2.setString(7,leadDispositionDtoObj.getDialerAgencyCode());
								ps2.setString(8,primaryUser);
								/* Added by Parnika for LMS Phase2 */
								ps2.setString(9,leadProductId);
								/* End of changes by Parnika */
								//logger.info("55555");
								ps2.executeUpdate();
								
								/* Added by Parnika for LMS2 Drop3 for updating Lead data on 10-Mar-2014 */
								
								ps4.setString(1, leadId+"");
								rs2 = ps4.executeQuery();
								if(rs2.next()){
									dispositionCount = rs2.getString(1);
									
								}
								if(dispositionCount != null && Integer.parseInt(dispositionCount) == 0){
									ps3 = con.prepareStatement(SQL_UPDATE_LEAD_DATA_FIRST);
								}
								else{
									ps3 = con.prepareStatement(SQL_UPDATE_LEAD_DATA);
								}								
								
								ps3.setString(1, leadDispositionDtoObj.getDispositionCode());
								
								if(dispositionCount != null){
									ps3.setInt(2, Integer.parseInt(dispositionCount)+1);
								}
								else{
									ps3.setInt(2, 1); // first Disposition Count.
								}
								
								ps3.setString(3, leadId+"");
								ps3.executeUpdate();
								
								/* End of changes by Parnika */
								
							}
							
								logger.info("Validation process true-------"+leadDispositionDtoObj.getCallRetryCount());
								ps.setString(1,leadDispositionDtoObj.getDispositionCode() );
								ps.setString(2, (leadId));
								ps.setString(3,"1" );
								if(leadDispositionDtoObj.getCallRetryCount() == null || leadDispositionDtoObj.getCallRetryCount().equals(""))
								{
									logger.info("retry count is null");
									ps.setInt(4, 0);
								}
								else
								{
									logger.info("retry count is not null");
									ps.setString(4, leadDispositionDtoObj.getCallRetryCount());
								}	
								
								ps.setString(5, leadDispositionDtoObj.getSetupTime());
								ps.setString(6, leadDispositionDtoObj.getRingingTime());
								ps.setString(7, leadDispositionDtoObj.getIvrTime());
								ps.setString(8, leadDispositionDtoObj.getCustomerTalkTime());
								ps.setString(9, leadDispositionDtoObj.getCustomerHoldTime());
								ps.setString(10, leadDispositionDtoObj.getDialerAgencyCode());
								ps.setString(11, leadDispositionDtoObj.getAgentDispositionCode());
								
								count = count + ps.executeUpdate();
								
							/*}
							else
							{
								logger.info("Validation process false");
								objResponse.setResponseCode("1");
								objResponse.setResponseMsg("Invalid Date/Time format for leadId "+leadId);
								throw new ParseException("Invalid Date/Time format for leadId "+leadId,1);
							}*/
							
						} 
						}
						objResponse.setResponseCode("0");
						objResponse.setResponseMsg("SUCCESS");
						
						//int[] insertedRows = ps.executeBatch();
						//ps.clearParameters();
						logger.info("Total "+count +" rows inserted into lead disposition table");
						logger.info("Connection commit");
						con.commit();
			}
				/*catch(ParseException ex)
				{
					logger.info("ParseException::"+ex);
					con.rollback();
					objResponse.setResponseCode("1");
					objResponse.setResponseMsg("Invalid Date/Time format for leadId "+leadId);
				}*/
				catch(Exception ex)
				{
					logger.info("Exception::"+ex);
					objResponse.setResponseCode("1");
					objResponse.setResponseMsg("Exception Occured for LeadId "+leadId);
					con.rollback();
				}
				arrresponse[i] = objResponse;
			} 
		}
			/*rseponseMsg = new ResponseMessage();
			rseponseMsg.setResponseCode("0");
			rseponseMsg.setResponseMsg("SUCCESS");*/
		}
		catch(Exception ex)
		{
			logger.info("Exception---------"+ex);
			/*rseponseMsg = new ResponseMessage();
			rseponseMsg.setResponseCode("1");
			rseponseMsg.setResponseMsg("Internal Error Occured");*/
			logger.error("Exception while inserting lead disposition details for lead id :: "+ leadId);
			ex.printStackTrace();
			throw new DAOException(ex.getMessage());
		}
		finally
		{
			try
			{
				DBConnection.releaseResources(null, ps1, rs1);
				DBConnection.releaseResources(null, ps2, null);
				DBConnection.releaseResources(con, ps, null);
			}
			catch(Exception ex)
			{
				ps=null;
				con=null;
			}
		}
		return arrresponse;
	}
	public boolean validateDate(String date) 
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		boolean validate = false;
		try
		{
			if(date == null || "".equals(date))
			{
				return true;
			}
			logger.info("Pasing....");
			String date1 = sdf1.format(sdf2.parse(date));
			logger.info("Pasing done...."+date1);
			validate = true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.info(ex);
			validate = false;
		}
		return validate;
	}
	public static void main(String[] args) {
		logger.info(new UpdateLeadDispositionDAOImpl().validateDate("04/03/2013"));
	}
}
