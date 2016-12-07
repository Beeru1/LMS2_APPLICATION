package com.ibm.km.services.impl;


import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ibm.km.dao.KmExcelMstrDao;
import com.ibm.km.dao.impl.KmExcelMstrDaoImpl;
import com.ibm.km.dto.KmExcelMstrDTO;
import com.ibm.km.services.KmExcelService;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
/**
* @author Anveeksha & Neeraj 
* created for Waiver Matrix Upload
* for viewing Company Wise bill Plan details
*/


public class KmExcelServiceImpl implements KmExcelService {
	
	public int errorStatus;
	/**
	* Logger for the class.
	**/
	private static final Logger logger;

	static {

		logger = Logger.getLogger(KmExcelServiceImpl.class);
	}
	
	/**
	 * @param filepath
	 * @return ArrayList of Bill Pans with no rate details
	 * @throws LMSException
	 */
	public ArrayList createTempTable(String filepath) throws LMSException {
		
		KmExcelMstrDTO dto=new KmExcelMstrDTO();
		KmExcelMstrDao daoobj=new KmExcelMstrDaoImpl();
		ArrayList returnArrayList = new ArrayList();
		
		int rowsOfCompanyDetails,columnsOfBillPlanDetails = 22, rowsOfRateDetails,columnsOfRateDetails,tempColCount;
		int colCount = 0,initColCount = 0,sheetCount,indexOfPlanRateSheet,indexofAES1Sheet;
		ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
		HSSFWorkbook fileWorkBook,templateWorkbook;
		HSSFSheet templateSheet,templateRateSheet, planRateSheet,companyListSheet;
		HSSFCell cellInSheet,cellInTemplate;
		logger.info("Excel Uploading Process");
		String sheetName;
		try
		{
			fileWorkBook = new HSSFWorkbook(new FileInputStream(filepath));
		
			templateWorkbook = new HSSFWorkbook(new FileInputStream(bundle.getString("excel.template")));
			templateSheet = templateWorkbook.getSheetAt(0);
			templateRateSheet = templateWorkbook.getSheetAt(1);
			
			indexOfPlanRateSheet = fileWorkBook.getSheetIndex("BILLPLAN");
			indexofAES1Sheet = fileWorkBook.getSheetIndex("AES 1");
			sheetCount = fileWorkBook.getNumberOfSheets();
			if(indexOfPlanRateSheet==-1||indexofAES1Sheet==-1)
			{
				throw new LMSException("Exception:Uploaded file does not contain the required sheets");	
			}
			planRateSheet = fileWorkBook.getSheet("BILLPLAN");
			companyListSheet = fileWorkBook.getSheet("AES 1");
			
			
			rowsOfCompanyDetails = companyListSheet.getLastRowNum();
			rowsOfRateDetails = planRateSheet.getLastRowNum();
			columnsOfRateDetails = planRateSheet.getRow(0).getLastCellNum();
			
			/**Validating Bill Plan Rate Data Sheet*/
			for(int row=0;row<1;row++)
			{	
				for(int col=0;col<20;col++)
				{	
					cellInSheet = planRateSheet.getRow(row).getCell(col);
					cellInTemplate = templateRateSheet.getRow(row).getCell(col);
					if(cellInSheet!=null&&cellInTemplate!=null)
					{	
						if(!(cellInSheet.toString().toUpperCase().equals(cellInTemplate.toString().toUpperCase())))
						{
							throw new LMSException("Exception:Bill Rates Template Mismatch");	
						} 
					}	
					else
					{	
						if(!(cellInSheet==null&&cellInTemplate==null))
						{	
							throw new LMSException("Exception:Bill Rates Template Mismatch");	
						}
					}
				}
			}
			
			/**Validation over*/
			
			for(int count=0;count<sheetCount;count++)
			{	
				if((count!=indexOfPlanRateSheet)&&(((fileWorkBook.getSheetName(count)).toUpperCase()).indexOf("AES")>=0))
				{	
					columnsOfBillPlanDetails = columnsOfBillPlanDetails + fileWorkBook.getSheetAt(count).getRow(0).getLastCellNum()-22;
				}
			}
			
			Object[][] companyAndPlanDetailsObj = new Object[rowsOfCompanyDetails+1][columnsOfBillPlanDetails]; 
			Object[][] billPlanExcelObj = new Object[rowsOfRateDetails+1][columnsOfRateDetails];
			
			for(int count=0;count<sheetCount;count++)
			{
				if(count!=indexOfPlanRateSheet&&fileWorkBook.getSheetName(count).toUpperCase().indexOf("AES")>=0)
				{
					sheetName = fileWorkBook.getSheetName(count);
					companyListSheet = fileWorkBook.getSheetAt(count);
					tempColCount = companyListSheet.getRow(0).getLastCellNum();
					
					/**Validating Company and Bill Plan Data Sheet*/
					for(int row=0;row<2;row++)
					{	
						for(int col=0;col<22;col++)
						{	cellInSheet = companyListSheet.getRow(row).getCell(col);
							cellInTemplate = templateSheet.getRow(row).getCell(col);
							if(cellInSheet!=null&&cellInTemplate!=null)
							{	
								if(!(cellInSheet.toString().toUpperCase().equals(cellInTemplate.toString().toUpperCase())))
								{	
									throw new LMSException("Exception:Template Mismatch1");	
								} 
							}	
							else
							{	
								if(!(cellInSheet==null&&cellInTemplate==null))
								{	
									throw new LMSException("Exception:Template Mismatch2");	
								}
							}
						}
					}
					/**Validation over*/
					
					for(int row=0;row<=rowsOfCompanyDetails;row++)
					{
						colCount=initColCount;
						if(initColCount==0)
						{
							for(int col=0;col<22;col++)
							{	cellInSheet = companyListSheet.getRow(row).getCell(col);
								if(cellInSheet==null)
								{	
									companyAndPlanDetailsObj[row][colCount]="";		
								}
								else
								{	
									companyAndPlanDetailsObj[row][colCount]=cellInSheet.toString();		
								}	
								colCount++;
							}
						}
						
						for(int col=22;col<tempColCount;col++)
						{	cellInSheet = companyListSheet.getRow(row).getCell(col);
							if(cellInSheet==null)
							{	
								companyAndPlanDetailsObj[row][colCount]="";		
							}
							else
							{	
								companyAndPlanDetailsObj[row][colCount]=cellInSheet.toString();		
							}
							colCount++;
						}
					}
					initColCount=colCount;
				}	
			}
			
			for(int row = 0; row <= rowsOfRateDetails; row++)
			{	
				for(int col= 0; col < columnsOfRateDetails; col++)
				{	
					cellInSheet = planRateSheet.getRow(row).getCell(col);
					if(cellInSheet==null)
					{	
						billPlanExcelObj[row][col]="";		
					}
					else
					{	
						billPlanExcelObj[row][col]= cellInSheet.toString();			
					}
				}
			}
			
			dto.setNumColSheet1(columnsOfBillPlanDetails);
			dto.setNumRowSheet1(rowsOfCompanyDetails+1);
			dto.setNumColSheet2(columnsOfRateDetails);
			dto.setNumRowSheet2(rowsOfRateDetails+1);
			
			dto.setExcelObj(companyAndPlanDetailsObj);
			dto.setBillPlanExcelObj(billPlanExcelObj);
			returnArrayList= daoobj.createTempTable(dto);
			return returnArrayList;
		} 
		catch(LMSException e) 
		{
			e.printStackTrace();
			logger.error(" Exception occured while showing DocumentViews." + "Exception Message: " + e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		} 
		catch(DAOException e)
		{
			e.printStackTrace();
			logger.error(" Exception occured while showing DocumentViews." + "Exception Message: " + e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(" Exception occured while showing DocumentViews." + "Exception Message: " + e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		}
		finally 
		{
			logger.info("Exit from Create Temp Table method in Service");
			
		}	
	}
	
	public int getErrorStatus() throws LMSException
	{
		return errorStatus;
	}	
	/**
	 * @param userID
	 * @throws LMSException
	 */
	public void updateMasterTables(String userID,String filePath) throws LMSException{
		try
		{
			KmExcelMstrDao daoobj=new KmExcelMstrDaoImpl();
			daoobj.updateMasterTables(userID,filePath);
		}
		catch(LMSException e) 
		{
			e.printStackTrace();
			logger.error(" Exception occured while showing DocumentViews." + "Exception Message: " + e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		} 
		catch(DAOException e)
		{
			e.printStackTrace();
			logger.error(" Exception occured while showing DocumentViews." + "Exception Message: " + e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(" Exception occured while showing DocumentViews." + "Exception Message: " + e.getMessage());
			throw new LMSException(" Exception: " + e.getMessage(), e);
		}
		finally 
		{
			logger.info("Exit from Update Master Table method in Service");
		}	
	}
	
}
