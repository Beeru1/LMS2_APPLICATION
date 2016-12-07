package com.ibm.km.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.km.dao.KmExcelMstrDao;
import com.ibm.km.dto.KmExcelMstrDTO;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anveeksha & Neeraj
 * for waiver matrix uplaod 
 * save the data of uploaded excel to database
 */
/**
 *
Code 	Updated by     	  Date		  CSR NO				 Defect ID		    Description
------------------------------------------------------------------------------------------------------------------------------------------------------------
0001	Neeraj Aggarwal   11 MAY 09	  20081211-00-03016	     MASDB00111714  	Disallowing uploading if there are multiple bill plans with same name 
 * 
 */
public class KmExcelMstrDaoImpl implements KmExcelMstrDao {

	/*
	 * query variables
	 */ 
	
	/*
	 * used for creating the temporary table on excel upload
	 */
	public static final String insertIntoTempCompanyMaster = "INSERT INTO LMS.KM_TEMP_COMPANY_MSTR(COMPANY_ID, SCANNING_CODE, DT, COMPANY_NAME, PARENT_NAME, REMARKS, ABBREVIATION, SERVICE_TAX_EXEMPTION, HIGH_RISK_SEGMENT, ACCOUNT_MANAGER, CODE, REGIONAL_SALES_HEAD, AUTHORIZED_SIGNATORY_NAME, REC_DATE, DESIGNATION, CONTACT_NO, SECURITY_DEPOSIT, ITEMISED, ROAMING_RENTAL, CLIP, CALL_CONF, FAX_AND_DATA) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String insertIntoTempBillPlanMaster = "INSERT INTO LMS.KM_TEMP_BILLPLAN_MSTR(BILLPLAN_ID, BILL_PLAN_NAME, BILL_PLAN_DETAILS) VALUES (?,?,?)";
	public static final String insertIntoTempCompanyPlanMapping = "INSERT INTO LMS.KM_TEMP_COMPANY_WISE_BILLPLAN(COMPANYID, BILLPLANID, REMARKS)  VALUES(?, ?, ?)";
	public static final String insertIntoTempBillPlanRates = "INSERT INTO LMS.KM_TEMP_BILLPLAN_RATES VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/*
	 * used to clean temporary tables before entering new data
	 */
	public static final String cleanTempBillPlanMstr = "DELETE FROM LMS.KM_TEMP_BILLPLAN_MSTR";
	public static final String cleanTempCompanyMstr = "DELETE FROM LMS.KM_TEMP_COMPANY_MSTR";
	public static final String cleanTempCompanyPlanMapping = "DELETE FROM LMS.KM_TEMP_COMPANY_WISE_BILLPLAN";
	public static final String cleanTempBillPlanRates = "DELETE FROM LMS.KM_TEMP_BILLPLAN_RATES";

	/*
	 * used to clean master tables before entering new data
	 */
	public static final String cleanBillPlanMstr = "DELETE FROM LMS.KM_BILLPLAN_MSTR";
	public static final String cleanCompanyMstr = "DELETE FROM LMS.KM_COMPANY_MSTR";
	public static final String cleanCompanyPlanMapping = "DELETE FROM LMS.KM_COMPANY_WISE_BILLPLAN";
	public static final String cleanBillPlanRate = "DELETE FROM LMS.KM_BILLPLAN_RATES";
		
	//0001  Start Query to check Bill plan duplicasy in both sheets
	/*
	 * check for bill plan details in BillPlan Rate Master
	 */
	//public static final String getBillPlansWithoutRates = "SELECT BILLPLAN_ID,BILL_PLAN_NAME FROM LMS.KM_TEMP_BILLPLAN_MSTR WHERE LMS.KM_TEMP_BILLPLAN_MSTR.BILL_PLAN_NAME NOT IN (SELECT BILL_PLAN_DESC FROM LMS.KM_TEMP_BILLPLAN_RATES) and BILL_PLAN_NAME<>''";
	public static final String getBillPlansWithoutRates = "SELECT BILLPLAN_ID,LTRIM(RTRIM(BILL_PLAN_NAME)) AS BILL_PLAN_NAME FROM LMS.KM_TEMP_BILLPLAN_MSTR WHERE LTRIM(RTRIM(BILL_PLAN_NAME)) NOT IN (SELECT LTRIM(RTRIM(BILL_PLAN_DESC)) FROM LMS.KM_TEMP_BILLPLAN_RATES) WITH UR ";
	/*
	 * check for duplicate bill plan columns
	 */
	//public static final String getDuplicateBillPlansColumns = "SELECT DISTINCT 1 as BILLPLAN_ID ,A.BILL_PLAN_NAME as BILL_PLAN_NAME FROM LMS.KM_TEMP_BILLPLAN_MSTR A INNER JOIN LMS.KM_TEMP_BILLPLAN_MSTR B ON A.BILL_PLAN_NAME= B.BILL_PLAN_NAME WHERE A.BILLPLAN_ID <> B.BILLPLAN_ID and A.BILL_PLAN_NAME<>''";
	//public static final String getDuplicateBillPlansColumns = "SELECT DISTINCT 1 as BILLPLAN_ID ,A.BILL_PLAN_NAME as BILL_PLAN_NAME FROM LMS.KM_TEMP_BILLPLAN_MSTR A INNER JOIN LMS.KM_TEMP_BILLPLAN_MSTR B ON LTRIM(RTRIM(A.BILL_PLAN_NAME))= LTRIM(RTRIM(B.BILL_PLAN_NAME))WHERE A.BILLPLAN_ID <> B.BILLPLAN_ID";
	/*
	 * check for duplicate bill plan Rate Details
	 */
	public static final String getDuplicateBillPlansRates = "SELECT LTRIM(RTRIM(BILL_PLAN_DESC)) as BILL_PLAN_NAME  FROM LMS.KM_TEMP_BILLPLAN_RATES GROUP BY LTRIM(RTRIM(BILL_PLAN_DESC)) HAVING COUNT(1)>1  WITH UR ";
	// and BILL_PLAN_DESC<>''";
	//check duplicate package description
	public static final String getDuplicateBillPlansRatesPackageId="SELECT LTRIM(RTRIM(COMPONENT_ID)) as BILL_PLAN_PACKAGEID  FROM LMS.KM_TEMP_BILLPLAN_RATES GROUP BY LTRIM(RTRIM(COMPONENT_ID)) HAVING COUNT(1)>1 WITH UR"; 
	
	//	0001  End
	
	/*
	 * used to insert data into the master tables
	 */
	public static final String insertIntoCompanyMaster = "INSERT INTO LMS.KM_COMPANY_MSTR SELECT * FROM LMS.KM_TEMP_COMPANY_MSTR";
	public static final String insertIntoBillPlanMaster = "INSERT INTO LMS.KM_BILLPLAN_MSTR SELECT * FROM LMS.KM_TEMP_BILLPLAN_MSTR";
	public static final String insertIntoCompanyPlanMapping = "INSERT INTO LMS.KM_COMPANY_WISE_BILLPLAN SELECT * FROM LMS.KM_TEMP_COMPANY_WISE_BILLPLAN";
	public static final String insertIntoBillPlanRate = "INSERT INTO LMS.KM_BILLPLAN_RATES SELECT * FROM LMS.KM_TEMP_BILLPLAN_RATES";
	public static final String insertIntoWaiverMatrixAudit = "INSERT INTO LMS.KM_WAIVER_MATRIX_AUDIT(UPDATED_BY,FILE_PATH) VALUES(?,?)";
	
	KmExcelMstrDTO tempDTO = new KmExcelMstrDTO();
	
	/*
	 * Logger for the class.
	 */
	private static final Logger logger;
	
	static {
		logger = Logger.getLogger(KmExcelMstrDaoImpl.class);
	}

	/**
	 * @param dto
	 * @return ArrayList of Bill Plans without Rate Details
	 * @throws LMSException
	 */
	public ArrayList createTempTable(KmExcelMstrDTO dto) throws LMSException {
	
		Connection con = null;
		KmExcelMstrDTO newDTO ;
		ArrayList missingPlanRateList = new ArrayList();
		ArrayList duplicateBillPlans = new ArrayList();
		ArrayList duplicateBillPlansPackageId = new ArrayList();
		ArrayList compiledArrayList = new ArrayList();
		ResultSet rs = null;
		//ResultSet rsDuplicateBillPlans = null;
		ResultSet rsDuplicateBillPlansRates = null;
		ResultSet rsDuplicateBillPlansRatesPackageId = null;
		PreparedStatement psInsertTempCompanyMaster = null;
		PreparedStatement psInsertTempBillPlanMaster = null;
		PreparedStatement psInsertTempCompanyPlanMapping = null;
		PreparedStatement psInsertTempBillPlanRates = null;
		
		try
		{
			Object[][] tempExcelObj = dto.getExcelObj();
			Object[][] tempPlanRate = dto.getBillPlanExcelObj(); 
			con=DBConnection.getDBConnection();
			con.setAutoCommit(false);
			
			con.prepareStatement(cleanTempCompanyPlanMapping).executeUpdate();
			con.prepareStatement(cleanTempBillPlanMstr).executeUpdate();
			con.prepareStatement(cleanTempCompanyMstr).executeUpdate();
			con.prepareStatement(cleanTempBillPlanRates).executeUpdate();
			
			psInsertTempCompanyMaster = con.prepareStatement(insertIntoTempCompanyMaster);
			psInsertTempBillPlanMaster = con.prepareStatement(insertIntoTempBillPlanMaster);
			psInsertTempCompanyPlanMapping = con.prepareStatement(insertIntoTempCompanyPlanMapping);
			psInsertTempBillPlanRates = con.prepareStatement(insertIntoTempBillPlanRates);
			for(int r=2;r<dto.getNumRowSheet1();r++)
			{
				psInsertTempCompanyMaster.setInt(1,r-1);
				
				for(int c=1;c<22;c++)
				{
					psInsertTempCompanyMaster.setString(c+1,tempExcelObj[r][c].toString().trim());
				}
				psInsertTempCompanyMaster.executeUpdate();
			}
			
			for(int c=22;c<dto.getNumColSheet1();c++)
			{
				psInsertTempBillPlanMaster.setInt(1,c-21);
				
				for(int r=0;r<2;r++)
				{
							psInsertTempBillPlanMaster.setString(r+2,tempExcelObj[r][c].toString().trim());
				}
				psInsertTempBillPlanMaster.executeUpdate();
			}
			
			for(int r=2;r<dto.getNumRowSheet1();r++)
			{
				for(int c=22;c<dto.getNumColSheet1();c++)
				{
					if(tempExcelObj[r][c].toString().trim()!=null)
					{
						if(tempExcelObj[r][c].toString().trim()!="")
						{
							psInsertTempCompanyPlanMapping.setInt(1,r-1);
							psInsertTempCompanyPlanMapping.setInt(2,c-21);
							psInsertTempCompanyPlanMapping.setString(3,tempExcelObj[r][c].toString().trim());
							psInsertTempCompanyPlanMapping.executeUpdate();
						}	
					}
				}	
			}
			
			for(int r=1;r<dto.getNumRowSheet2();r++)
			{
				for(int c=1;c<dto.getNumColSheet2();c++)
				{
					psInsertTempBillPlanRates.setString(c,tempPlanRate[r][c].toString().trim());
				}
				psInsertTempBillPlanRates.executeUpdate();
			}
			
			//0001  Start Using the same arraylist if there are duplicate bill plans and using arraylist as missing bill plan details if no duplicacy
			
			//rsDuplicateBillPlans=con.prepareStatement(getDuplicateBillPlansColumns).executeQuery();
			
			rs = con.prepareStatement(getBillPlansWithoutRates).executeQuery();
			
			rsDuplicateBillPlansRates=con.prepareStatement(getDuplicateBillPlansRates).executeQuery();
			
			rsDuplicateBillPlansRatesPackageId=con.prepareStatement(getDuplicateBillPlansRatesPackageId).executeQuery();
			
			
			/*
			while(rsDuplicateBillPlans.next())
			{
				newDTO = new KmExcelMstrDTO();
				newDTO.setBillPlanId(Integer.parseInt(rsDuplicateBillPlans.getString(1)));
				newDTO.setBillPlanName(rsDuplicateBillPlans.getString(2));
				missingPlanRateList.add(newDTO);
			}	
			*/
			while(rs.next())
			{
				newDTO = new KmExcelMstrDTO();
				newDTO.setBillPlanId(Integer.parseInt(rs.getString(1)));
				newDTO.setBillPlanName(rs.getString(2));
				missingPlanRateList.add(newDTO);
			}	
			
			while(rsDuplicateBillPlansRates.next())
			{
				newDTO = new KmExcelMstrDTO();
				newDTO.setBillPlanName(rsDuplicateBillPlansRates.getString(1));
				duplicateBillPlans.add(newDTO);
			}	
			
			while(rsDuplicateBillPlansRatesPackageId.next())
			{
				newDTO = new KmExcelMstrDTO();
				newDTO.setBillPlanPackageIdDuplicate(rsDuplicateBillPlansRatesPackageId.getString(1));
				duplicateBillPlansPackageId.add(newDTO);
			}	
			compiledArrayList.add(missingPlanRateList);
			compiledArrayList.add(duplicateBillPlans);
			compiledArrayList.add(duplicateBillPlansPackageId);
			
		//0001  End
			con.commit();
			return compiledArrayList;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			logger.error("SQLException occured while  showing DocumentViews" + "Exception Message: " + e.getMessage());
			throw new LMSException("SQL Exception: " + e.getMessage(), e);
		} 
		catch (DAOException e) 
		{
			e.printStackTrace();
			logger.error(" Exception occured while showing DocumentViews." + "Exception Message: " + e.getMessage());
			throw new LMSException(" DAOException: " + e.getMessage(), e);
		} 
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(" Exception occured while showing DocumentViews." + "Exception Message: " + e.getMessage());
			throw new LMSException(" DAOException: " + e.getMessage(), e);
		}
		finally 
		{
			try 
			{
				DBConnection.releaseResources(con,psInsertTempCompanyMaster,rs);
				DBConnection.releaseResources(null,psInsertTempBillPlanMaster,null);
				DBConnection.releaseResources(null,psInsertTempCompanyPlanMapping,null);
				DBConnection.releaseResources(null,psInsertTempBillPlanRates,null);
			} 
			catch (Exception e) 
			{
				logger.error("DAO Exception occured while inserting into temporary tables" + "Exception Message: " + e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}
			logger.info("Exit from Create Temp Table method in DAO");
		}
	}
	
	public int getErrorStatus() throws LMSException
	{
		return tempDTO.getErrorStatus();
	}
	
	/**
	 * @param userID
	 * @throws LMSException
	 */
	public void updateMasterTables(String userID,String filePath) throws LMSException {

		Connection con = null;
		PreparedStatement psWaiverMatrixAudit=null;
		
		try
		{
			con=DBConnection.getDBConnection();
			con.setAutoCommit(false);
						
			con.prepareStatement(cleanCompanyPlanMapping).executeUpdate();
			con.prepareStatement(cleanBillPlanMstr).executeUpdate();
			con.prepareStatement(cleanCompanyMstr).executeUpdate();
			con.prepareStatement(cleanBillPlanRate).executeUpdate();
			
			con.prepareStatement(insertIntoCompanyMaster).executeUpdate();
			con.prepareStatement(insertIntoBillPlanMaster).executeUpdate();
			con.prepareStatement(insertIntoCompanyPlanMapping).executeUpdate();
			con.prepareStatement(insertIntoBillPlanRate).executeUpdate();
						
			psWaiverMatrixAudit = con.prepareStatement(insertIntoWaiverMatrixAudit);
			psWaiverMatrixAudit.setString(1,userID);
			psWaiverMatrixAudit.setString(2,filePath);
			psWaiverMatrixAudit.executeUpdate();

			con.commit();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			logger.error("SQLException occured while  showing DocumentViews" + "Exception Message: " + e.getMessage());
			throw new LMSException("SQL Exception: " + e.getMessage(), e);
		} 
		catch (DAOException e) 
		{
			e.printStackTrace();
			logger.error(" Exception occured while showing DocumentViews." + "Exception Message: " + e.getMessage());
			throw new LMSException(" DAOException: " + e.getMessage(), e);
		} 
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(" Exception occured while showing DocumentViews." + "Exception Message: " + e.getMessage());
			throw new LMSException(" DAOException: " + e.getMessage(), e);
		}
		finally 
		{
			try 
			{
				DBConnection.releaseResources(con,psWaiverMatrixAudit,null);
			} 
			catch (Exception e) 
			{
				logger.error("DAO Exception occured while showing DocumentViews" + "Exception Message: " + e.getMessage());
				throw new LMSException("DAO Exception: " + e.getMessage(), e);
			}
			logger.info("Exit from Update Master Table method in DAO");
		}

	}
}