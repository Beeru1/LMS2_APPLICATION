package com.ibm.lms.engine.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibm.lms.common.ColumnKeys;
import com.ibm.lms.common.LMSStatusCodes;
import com.ibm.lms.dao.DBConnection;
import com.ibm.lms.dto.webservice.GisInfoCaptureDO;
import com.ibm.lms.engine.dao.CaptureGisDataDao;
import com.ibm.lms.engine.exception.DAOException;
import com.ibm.lms.engine.util.Constants;
import com.ibm.lms.exception.LMSException;



public class CaptureGisDataDaoImpl implements CaptureGisDataDao
{
	
	private static final Logger logger = Logger.getLogger(CaptureGisDataDaoImpl.class);

	private static final String SQL_SELECT_LEAD_DETAILS = "SELECT l.LEAD_ID,lob.PRODUCT_LOB, pm.PRODUCT_NAME, cm.circle_name,ls.LEAD_STATUS,C.CUSTOMER_NAME,C.PROSPECT_MOBILE_NUMBER MOBILE_NUMBER,c.EMAIL, l.CREATE_TIME,s.SOURCE_NAME  from lead_data l,LEAD_PROSPECT_DETAIL lpd,PRODUCT_LOB lob ,LEAD_PROSPECT_CUSTOMER c,SOURCE_MSTR s,LEAD_STATUS ls,CIRCLE_MSTR cm,PRODUCT_MSTR pm"+
	" where l.LEAD_PROSPECT_ID=lpd.LEAD_PROSPECT_ID and lpd.PROSPECT_ID=c.PROSPECT_ID and lob.PRODUCT_LOB_ID=lpd.PRODUCT_LOB_ID and s.SOURCE_ID=l.SOURCE and ls.LEAD_STATUS_ID=l.LEAD_STATUS_ID and lpd.CIRCLE_ID=cm.CIRCLE_ID and lob.PRODUCT_LOB_ID=cm.LOB_ID and pm.PRODUCT_ID=l.PRODUCT_ID " +
	"and l.LEAD_STATUS_ID=310 order by l.CREATE_TIME with ur";
	
	private static final String GET_ALL_FROM_RSUCODE= " SELECT rsu.RSU_CODE, rsu.CITY_ZONE_CODE,  czm.CITY_CODE, cim.ZONE_CODE, zm.CIRCLE_MSTR_ID  FROM RSU_MSTR rsu, CITY_ZONE_MSTR czm, CITY_MSTR cim, ZONE_MSTR zm, CIRCLE_MSTR cm WHERE rsu.CITY_ZONE_CODE = czm.CITY_ZONE_CODE AND czm.CITY_CODE = cim.CITY_CODE AND cim.ZONE_CODE = zm.ZONE_CODE AND zm.CIRCLE_MSTR_ID = cm.CIRCLE_MSTR_ID  AND rsu.RSU_CODE = ? AND cm.CIRCLE_ID = ? AND cm.LOB_ID = ? WITH UR";
	
	
	

	
	public String captureGisDataResponse(GisInfoCaptureDO[] captureDOs) throws DAOException, SQLException
	{
		logger.info("inside captureGisDataResponse******************************");
		Connection con = null;
		GisInfoCaptureDO captureDO =null;
	 	PreparedStatement psGisDetails = null;
	 	PreparedStatement ps3=null;
	 	PreparedStatement psLeadData=null;
	    String circleId=null;
		String lobId=null;
		String leadStatusId=null;
	int counter=1;
	String responce ="";

	if(captureDOs !=null && captureDOs.length >0) {
		try {
			 con = DBConnection.getDBConnection();
		for(int i=0; i< captureDOs.length; i++) {
			
			try {
				captureDO  = captureDOs[i];
				
				StringBuffer stringBuffer  = new StringBuffer ("UPDATE LEAD_PROSPECT_DETAIL SET ");
				StringBuffer query1= new StringBuffer("UPDATE LEAD_DATA");
				StringBuffer query2=new StringBuffer( "INSERT INTO LEAD_TRANSACTION (LEAD_ID, PRODUCT_ID, LEAD_ASSIGNMENT_TIME, LEAD_CLOSURE_TIME, LEAD_STATUS_ID, LEAD_ASSIGNED_PRIMARY_USER, REMARKS, TRANSACTION_TIME, EXPECTED_CLOSURE_DATE, PRIMARY_AUTH, SUB_STATUS_ID, UPDATED_BY, UD_ID ,LEAD_PRODUCT_ID,CLIENT_IP)  SELECT LEAD_ID,PRODUCT_ID,LEAD_ASSIGNMENT_TIME,CURRENT TIMESTAMP,?");
				String  data  = validateFeasibleLeadForUpdate(captureDO.getLeadId().toString(), con );
				if(data!=null)
				{
				leadStatusId=data.split("#")[0];
				circleId=data.split("#")[1];
				lobId=data.split("#")[2];
				}
				logger.info("leadStatusId**************"+leadStatusId);
				if(!leadStatusId.equals(Constants.WIRED) || !leadStatusId.equals(Constants.UNWIRED))
				{
			if((captureDO.getStatus()!="" || captureDO.getStatus()!=null) && captureDO.getRemarks()!="" && captureDO.getSentBy()!="")
			{
			if(data!=null && captureDO.getRsuCode() !=null && validateRSUCode(con,captureDO, Integer.parseInt(lobId),circleId)!=null && (captureDO.getStatus().equals(Constants.WIRED) || captureDO.getStatus().equalsIgnoreCase("FEASIBLE") || captureDO.getStatus().equalsIgnoreCase("IN SERVICE")))
			{
				
			if(captureDO.getAddress1() !=null)
			{
				stringBuffer.append("ADDRESS1 ='"+captureDO.getAddress1()+"'");
			}
			
			
			stringBuffer.append(" ,RSU_CODE = '"+captureDO.getRsuCode()+"'");
			
			psGisDetails = con.prepareStatement(stringBuffer.toString()+" WHERE LEAD_ID ="+captureDO.getLeadId()+" FETCH FIRST ROW ONLY WITH UR");	
			psGisDetails.executeUpdate();
			
					query1.append(" SET  LEAD_STATUS_ID = ?, LEAD_SUB_STATUS_ID = ? ,UPDATED_DT= current timestamp WHERE LEAD_ID = "+captureDO.getLeadId()+" ");
					psLeadData = con.prepareStatement(query1.append(" with ur ").toString());
					psLeadData.setInt(1, Constants.WIRED);
					psLeadData.setInt(2, Constants.WIRED);
					psLeadData.executeUpdate();
					
					query2.append(" ,LEAD_ASSIGNED_PRIMARY_USER,?,CURRENT TIMESTAMP,EXPECTED_CLOSURE_DATE,PRIMARY_AUTH,?,?,? ,LEAD_PRODUCT_ID ,(SELECT CLIENT_IP FROM KM_LOGIN_DATA WHERE USER_LOGIN_ID = ?  ORDER BY LOGIN_TIME DESC  FETCH FIRST ROW ONLY ) FROM LEAD_TRANSACTION ");//changed by sudhanshu
					query2.append(" WHERE LEAD_ID = ? AND SUB_STATUS_ID <> 210 AND LEAD_STATUS_ID = ? ORDER BY TRANSACTION_TIME DESC FETCH FIRST ROW ONLY ");
				
					ps3 = con.prepareStatement(query2.toString());
					ps3.setInt(counter++, Constants.WIRED);
					ps3.setString(counter++, captureDO.getRemarks());
					ps3.setInt(counter++, Constants.WIRED);
					ps3.setString(counter++,"A187GH65");
					ps3.setString(counter++,"A187GH65");
					ps3.setString(counter++,"A187GH65");
					ps3.setLong(counter++, Long.parseLong(captureDO.getLeadId()));
					ps3.setInt(counter++, LMSStatusCodes.FEASIBILITY);
					ps3.executeUpdate();
				
					responce="0";
					return responce;
		}
			
			
				else if(data!=null && (captureDO.getStatus().equals(Constants.WIRED)||captureDO.getStatus().equalsIgnoreCase("FEASIBLE") || captureDO.getStatus().equalsIgnoreCase("IN SERVICE")) && captureDO.getRsuCode().equals(""))
				{
					logger.info("RSU code is mandatory for Wired Leads!");
					responce="2";
					return responce;
				}
			
				else if(data!=null && (captureDO.getStatus().equals(Constants.UNWIRED)||captureDO.getStatus().equalsIgnoreCase("INFEASIBLE"))) // for unwired leads
				{
					logger.info("inside unwired block**************");
					if(captureDO.getAddress1() !=null)
					{
						stringBuffer.append("ADDRESS1 ='"+captureDO.getAddress1()+"'");
					}
					
					if((captureDO.getRsuCode()!=null || captureDO.getRsuCode()!="") && validateRSUCode(con,captureDO, Integer.parseInt(lobId),circleId)!=null)
					{
						stringBuffer.append(" ,RSU_CODE = '"+captureDO.getRsuCode()+"'");
					}
					psGisDetails = con.prepareStatement(stringBuffer.toString()+" WHERE LEAD_ID ="+captureDO.getLeadId()+" WITH UR");	
					psGisDetails.executeUpdate();
					
					query1.append(" SET  LEAD_STATUS_ID = ?, LEAD_SUB_STATUS_ID = ? ,UPDATED_DT= current timestamp WHERE LEAD_ID = "+captureDO.getLeadId()+" ");
					psLeadData = con.prepareStatement(query1.append(" with ur ").toString());
					psLeadData.setInt(1, Constants.UNWIRED);
					psLeadData.setInt(2, Constants.UNWIRED);
					psLeadData.executeUpdate();
					query2.append(" ,LEAD_ASSIGNED_PRIMARY_USER,?,CURRENT TIMESTAMP,EXPECTED_CLOSURE_DATE,PRIMARY_AUTH,?,?,? ,LEAD_PRODUCT_ID ,(SELECT CLIENT_IP FROM KM_LOGIN_DATA WHERE USER_LOGIN_ID = ?  ORDER BY LOGIN_TIME DESC  FETCH FIRST ROW ONLY ) FROM LEAD_TRANSACTION ");//changed by sudhanshu
					query2.append(" WHERE LEAD_ID = ? AND SUB_STATUS_ID <> 210 AND LEAD_STATUS_ID = ? ORDER BY TRANSACTION_TIME DESC FETCH FIRST ROW ONLY ");
				
					ps3 = con.prepareStatement(query2.toString());
					ps3.setInt(counter++, Constants.UNWIRED);
					ps3.setString(counter++, captureDO.getRemarks());
					ps3.setInt(counter++, Constants.UNWIRED);
					ps3.setString(counter++,"A187GH65");
					ps3.setString(counter++,"A187GH65");
					ps3.setString(counter++,"A187GH65");
					ps3.setLong(counter++, Long.parseLong(captureDO.getLeadId()));
					ps3.setInt(counter++, LMSStatusCodes.FEASIBILITY);
					ps3.executeUpdate();responce="0";
					return responce;
				}
					else if(data!=null && (captureDO.getStatus().equals(Constants.UNWIRED)|| captureDO.getStatus().equals(Constants.WIRED)||(captureDO.getStatus().equalsIgnoreCase("FEASIBLE")||captureDO.getStatus().equalsIgnoreCase("INFEASIBLE")) || captureDO.getStatus().equalsIgnoreCase("IN SERVICE")) && captureDO.getRsuCode()!=null && validateRSUCode(con,captureDO, Integer.parseInt(lobId),circleId)==null) // for unwired leads)
					{
						
						logger.info("Invalid RSU Code. Cannot be updated!");
						responce="1";
						return responce;
					}
					/*else if(data!=null && captureDO.getStatus()!="FEASIBLE" || captureDO.getStatus()!="INFEASIBLE" || captureDO.getStatus()!="311" || captureDO.getStatus()!="315")
				
					{
						logger.info("Invalid RSU Code. Cannot be updated!");
						responce="1";
						return responce;
					}*/
					else if(data==null)
					{
						logger.info("Invalid Lead!");
						responce="1";
						return responce;
					}
				
			}
			else
			{
				logger.info("Status,Remarks and Sent By are mandatory fields!");
				responce="2";
				return responce;
				
			}
				}
				else
				{
					logger.info("Lead already in status: "+ leadStatusId);
					responce="1";
					return responce;
				}
				
			
			}
			catch (Exception e)
			{
				e.printStackTrace();
				logger.info(" no GisData found ********"+e.getMessage());
				throw new DAOException(e.getMessage());
				//throw new Exception();
			}
	}
		}
		catch (Exception e) {
			throw new DAOException(e.getMessage());
		}finally {
			try {
				DBConnection.releaseResources(con, null, null);		
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
}
	
	return responce;
}
		
	
	
		public String validateFeasibleLeadForUpdate(String leadId ,Connection con) throws LMSException {
			PreparedStatement pdstatement =null;
			ResultSet resultSet =null;
			try {
				if(leadId.length() >0){
						pdstatement  = con.prepareStatement("SELECT D.LEAD_STATUS_ID,LPD.CIRCLE_ID,PRO.PRODUCT_LOB_ID FROM LEAD_DATA D,LEAD_PROSPECT_DETAIL LPD,PRODUCT_MSTR PRO WHERE PRO.PRODUCT_ID=D.PRODUCT_ID AND D.LEAD_ID=LPD.LEAD_ID AND D.LEAD_ID ="+leadId+" WITH UR");
						resultSet =  pdstatement.executeQuery();
						if(resultSet.next())
							return resultSet.getString(ColumnKeys.LEAD_STATUS_ID)+"#"+resultSet.getString(ColumnKeys.CIRCLE_ID)+"#"+resultSet.getString(ColumnKeys.PRODUCT_LOB_ID);
				}
			}
				catch (Exception e) {
					e.printStackTrace();
					return null;
				}finally{
					try {
						DBConnection.releaseResources(null, pdstatement, resultSet);	
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				return null;
			}
		
	
	public String validateRSUCode(Connection con ,GisInfoCaptureDO gisCaptureObj ,int PRODUCT_LOB_ID,String circleId ) throws DAOException
	{
		ResultSet rsRsuCode = null;
		PreparedStatement psRsuCode=null;
     	String cityId = null;
		String cityZoneCode =null;
		String zoneCode=null;
		String rsuCode = null;
		String returnval=null;
		try {
			
			       psRsuCode = con.prepareStatement(GET_ALL_FROM_RSUCODE);
				   psRsuCode.setString(1, gisCaptureObj.getRsuCode().trim());
				   psRsuCode.setString(2, circleId);
					psRsuCode.setInt(3, PRODUCT_LOB_ID);
					rsRsuCode = psRsuCode.executeQuery();
					if(rsRsuCode.next()){			
						zoneCode = rsRsuCode.getString("ZONE_CODE");
						cityId = rsRsuCode.getString("CITY_CODE");
						cityZoneCode = rsRsuCode.getString("CITY_ZONE_CODE");
						rsuCode  = rsRsuCode.getString("RSU_CODE");
						returnval=cityId+"#"+cityZoneCode+"#"+zoneCode+"#"+rsuCode;
						return returnval;
					}
				} catch (Exception e) {
				}finally{
					try {
						DBConnection.releaseResources(null, psRsuCode, rsRsuCode);	
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				return returnval;	
			}



	
	
}	