package com.ibm.lms.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import com.ibm.lms.common.Constants;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dao.LeadRegistrationDao;
import com.ibm.lms.dao.impl.LeadRegistrationDaoImpl;
import com.ibm.lms.dto.BulkUploadErrorLogDto;
import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.dto.LeadDetailsDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.LeadRegistrationFormBean;
import com.ibm.lms.services.BulkLeadRegistrationService;
import com.ibm.lms.services.MasterService;

public class BulkLeadRegistrationServiceImpl implements
BulkLeadRegistrationService {

	private static final Logger logger;
	private static final String INCORRECT_FORMAT="INCORRECT_FORMAT";
	/* Commented by Parnika and converted to method variable instead of class variable */
	
	// private ArrayList<LeadDetailsDTO> validListInsert = new ArrayList<LeadDetailsDTO>();
	// private ArrayList<Integer> validRowNosList = new ArrayList<Integer>();
	 
	 /* End of comments by Parnika */

	static {
		logger = Logger.getLogger(BulkLeadRegistrationServiceImpl.class);
	}

	public BulkUploadMsgDto uploadOpenLeads(FormFile myfile, UserMstr userBean)
	throws LMSException {
		
		/* Added by Parnika */

		ArrayList<LeadDetailsDTO> validListInsert = new ArrayList<LeadDetailsDTO>();
		ArrayList<Integer> validRowNosList = new ArrayList<Integer>();
		
		/* End of changes by Parnika */

		boolean isError = false;
		List<BulkUploadErrorLogDto> errorList = new ArrayList<BulkUploadErrorLogDto>();
		String logFilePath = PropertyReader
		.getAppValue("lms.bulk.upload.error.log");
		LeadRegistrationDao dao = LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();
		BulkUploadMsgDto msgDto = new BulkUploadMsgDto();
		File newFile = null;
		try
		{
			byte[] fileData =myfile.getFileData();
			 String path= PropertyReader.getAppValue("path.uploadedTempFile")+ new java.util.Date().getTime()+"_"+myfile.getFileName() ;
			 newFile  = new File(path);
			 
				RandomAccessFile raf = new RandomAccessFile(newFile, "rw");
				/* Added by Parnika */
				boolean isLarge = false;
				/* End of changes by Parnika */
				raf.write(fileData);
				raf.close();
				 InputStream inp = new FileInputStream(path);
			      String fileExtn = GetFileExtension(path);
			      Workbook wb_xssf; //Declare XSSF WorkBook
			      Workbook wb_hssf; //Declare HSSF WorkBook
			      Sheet sheet = null; // sheet can be used as common for XSSF and HSSF
			      //	WorkBook
			      if (fileExtn.equalsIgnoreCase("xlsx"))
			      {
				       wb_xssf = new XSSFWorkbook(path);
				      logger.info("xlsx="+wb_xssf.getSheetName(0));
				      sheet = wb_xssf.getSheetAt(0);
			      }
			      if (fileExtn.equalsIgnoreCase("xls"))
			      {
				     POIFSFileSystem fs = new POIFSFileSystem(inp);
			    	  wb_hssf = new HSSFWorkbook(fs);
			    	  logger.info("xls="+wb_hssf.getSheetName(0));
			    	  sheet = wb_hssf.getSheetAt(0);
			      }
			Iterator rows = sheet.rowIterator();
			int totalrows = sheet.getLastRowNum() + 1;
			
			if (totalrows > 10000) {
				// "Limit exceeds: Maximum"
				isLarge = true;
			}

			int rowNumber = 1;
			//logger.info(totalrows +" is totalrows");
			if (isLarge) {
				
				msgDto.setMsgId(Constants.INVALID_FILESIZE);
				msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.invalid.filesize"));
				logger.info("File size exceeds.");
				return msgDto;
			}
			logger.info("totalrows==============="+totalrows);
			
			if (totalrows < 3) 
			{
				msgDto.setMsgId(Constants.BLANK_EXCEL);
				msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.blank.excel"));
				return msgDto;
			}
			
			while (rows.hasNext()) {

				Row row = (Row) rows.next();

				// logger.info(" row.getLastCellNum() "+row.getLastCellNum());
				// logger.info(" row.getPhysicalNumberOfCells() "+
				// row.getPhysicalNumberOfCells() );

				rowNumber = row.getRowNum();
				
				if (rowNumber == 0) {
					logger.info(" Physical No of Cells  "+row.getPhysicalNumberOfCells());
					
				if (row.getPhysicalNumberOfCells() != Constants.TOTAL_HEADERS_BULK_OPEN_LEAD_REGISTARTION) {
						msgDto.setMsgId(Constants.INVALID_EXCEL);
						msgDto.setMessage(PropertyReader
								.getAppValue("lms.bulk.upload.invalid.excel"));
						return msgDto;
					}
				else
					if(totalrows == 2)
						{
							msgDto.setMsgId(Constants.BLANK_EXCEL);
							msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.blank.excel"));
							return msgDto;
						}	

						else
							{
							continue;
							}
				}


				if (rowNumber > 1) // Starting parsing excel after 2nd row
				{

					Iterator cells = row.cellIterator();

					int columnIndex = 0;
					int cellNo = 0;
					//logger.info("validateOpenLeadDto   = "+rowNumber);
					if (cells != null) {
						LeadRegistrationFormBean beanObject = new LeadRegistrationFormBean();

						while (cells.hasNext()) {
							cellNo++;
							Cell cell = (Cell) cells.next();
							columnIndex = cell.getColumnIndex();

							String cellValue = null;

							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_NUMERIC:
								cellValue = String.valueOf((long) cell
										.getNumericCellValue());
								break;
							case Cell.CELL_TYPE_STRING:
								cellValue = cell.getStringCellValue();
								break;
							}
							if (cellValue != null) {
								cellValue = cellValue.trim();
							}
							else
								cellValue = "";

							switch (columnIndex) {
							case 0:
								beanObject.setCustomerName(cellValue);
								break;
							case 1:
								beanObject.setContactNo(cellValue);
								break;
							case 2:
								String productIds[] = new String[1]; ;
								if(cellValue.equals(""))
								{
									productIds[0]= "";
								}
								else
								{

									productIds[0]= cellValue;
								}
								beanObject.setProductIds(productIds);
								break;
							case 3:
								beanObject.setAddress1(cellValue);
								break;
								//Added By bhaskar
								case 4:
									beanObject.setEmail(cellValue);
									logger.info("Email for openload in service impl  :"+cellValue);
									break;
								/*
								 * case 4: beanObject.setAddress2(cellValue); break;
								 */
							/*case 4:  //Case 4 added by Neetika for open Leads as new column ZONE_CODE added at 4th place 
								beanObject.setZoneCode(cellValue);
								break;
								
								 * case 6: beanObject.setPinCode(cellValue); break;
								 * case 7: beanObject.setStateId(cellValue); break;
								 
							case 5:
								beanObject.setCityCode(cellValue);
								break;
								
								 * case 6: beanObject.setPinCode(cellValue); break;
								 * case 7: beanObject.setStateId(cellValue); break;
								 */
							
							case 5:  //Case 4 added by Neetika for open Leads as new column ZONE_CODE added at 4th place 
								
								beanObject.setCityCode(cellValue);
								break;
									
							
							case 6:
								
								beanObject.setCityZoneCode(cellValue);
								break;
								
							case 7:
								beanObject.setCircleId(cellValue);
								break;
								/*
								 * case 9: beanObject.setEmail(cellValue); break;
								 */
							case 8:
								beanObject.setAlternateContactNo(cellValue);
								break;
								/*
								 * case 11: beanObject.setLandlineNo(cellValue);
								 * break; case 12:
								 * beanObject.setExistingCustomer(cellValue); break;
								 */
							case 9:
								beanObject.setLanguage(cellValue);
								break;
								/*
								 * case 14: beanObject.setMaritalStatus(cellValue);
								 * break; case 15:
								 * beanObject.setRequestType(cellValue); break; case
								 * 16: beanObject.setAppointmentTime(cellValue);
								 * break; case 17:
								 * beanObject.setSourceId(cellValue); break; case
								 * 18: beanObject.setSubSourceId(cellValue); break;
								 * case 19: beanObject.setZoneCode(cellValue);
								 * break; case 20: beanObject.setRsuCode(cellValue);
								 * break; case 21:
								 * beanObject.setLeadStatusId(cellValue); break;
								 */
							case 10:
								beanObject.setRemarks(cellValue);
								beanObject.setCreatedBy(userBean.getUserLoginId());
								break;
							case 11:
								beanObject.setCampaign(cellValue);
								logger.info("campaing in service impl  :"+cellValue);
								break;
							
								
							}

							beanObject.setLeadStatusId(Constants.LEAD_STATUS_OPEN);
							beanObject.setSourceId(Constants.SOURCE_TYPE_BULK_UPLOAD+"");
							beanObject.setLeadCategory(Constants.NORMAL);
						}// Adding single row to a beanObject
						//logger.info("validateOpenLeadDto = "+rowNumber);
						errorList = validateOpenLeadDto(beanObject, rowNumber,
								errorList, validListInsert, validRowNosList);
					}// if

				}// Parsing single row

			}// Parsing all rows

			SimpleDateFormat otdf = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
			logFilePath = logFilePath + "bulkOpenLeadsRegistrationLog"
			+ otdf.format(new java.util.Date()) + ".csv";

			boolean isSucessTrnx = true;
			int errorRecordSize = 0;
			errorRecordSize = errorList.size();

			if (validListInsert.size() > 0) {
				ArrayList<BulkUploadMsgDto> daoMsgList = new ArrayList<BulkUploadMsgDto>();

				daoMsgList = dao.insertRecord(validListInsert, Constants.SOURCE_TYPE_BULK_UPLOAD);

				Iterator<Integer> 			itrValidRowNosList  =  validRowNosList.iterator();
				Iterator<BulkUploadMsgDto>  itrDaoMsgList 		=  daoMsgList.iterator();

				while(itrValidRowNosList.hasNext())
				{
					BulkUploadErrorLogDto bulkErr = new BulkUploadErrorLogDto();
					bulkErr.setRowNum(itrValidRowNosList.next());

					if(itrDaoMsgList.hasNext())
					{
						msgDto = itrDaoMsgList.next(); 
						switch(msgDto.getMsgId())
						{
						case Constants.LEAD_INSERT_NEW_LEAD :
							bulkErr.setErrMsg("Lead ID : "+msgDto.getLeadId());
							break;

						case Constants.LEAD_INSERT_EXISTING_LEAD :
							bulkErr.setErrMsg("Lead Already Exist : "+msgDto.getLeadId());
							isSucessTrnx = false;
							break;

						case Constants.LEAD_INSERT_FAIL :
							bulkErr.setErrMsg("ERROR:"+ msgDto.getMessage());		
							isSucessTrnx = false;
							break;
						default:
							bulkErr.setErrMsg(msgDto.getMessage());		
							isSucessTrnx = false;
							break;
						}

					}
					errorList.add(bulkErr);
				}

			}

			if (!isSucessTrnx || errorRecordSize>0 ) {
				msgDto.setMsgId(Constants.SERVICE_MSG_FAIL);
				msgDto.setMessage(logFilePath);
				writeLogs(errorList, logFilePath); // writing logs to the csv
			}
			else

			{
				msgDto.setMsgId(Constants.SERVICE_MSG_SUCCESS);
				msgDto.setMessage(logFilePath);
				writeLogs(errorList, logFilePath); // writing logs to the csv
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred..");
			msgDto.setMsgId(Constants.SERVICE_MSG_ERROR);
			msgDto.setMessage(PropertyReader
					.getAppValue("lms.bulk.upload.error"));
			return msgDto;
		}
		finally{
			try{
			newFile.delete();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.info("Error while deleting the file");
			}
		}
		return msgDto;

	}// uploadUsers

	public BulkUploadMsgDto uploadQualifiedLeads(FormFile myfile, UserMstr userBean)throws LMSException
	{

		/* Added by Parnika */

		ArrayList<LeadDetailsDTO> validListInsert = new ArrayList<LeadDetailsDTO>();
		ArrayList<Integer> validRowNosList = new ArrayList<Integer>();
		
		
		/* End of changes by Parnika */
		
		boolean isError = false;
		List<BulkUploadErrorLogDto> errorList = new ArrayList<BulkUploadErrorLogDto>();
		String logFilePath = PropertyReader
		.getAppValue("lms.bulk.upload.error.log");
		LeadRegistrationDao dao = LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();
		BulkUploadMsgDto msgDto = new BulkUploadMsgDto();

		File newFile = null;
		try
		{
			byte[] fileData =myfile.getFileData();
			 String path= PropertyReader.getAppValue("path.uploadedTempFile")+ new java.util.Date().getTime()+"_"+myfile.getFileName() ;
			 newFile  = new File(path);
			 
				RandomAccessFile raf = new RandomAccessFile(newFile, "rw");
				boolean isLarge = false;
				raf.write(fileData);
				raf.close();
				 InputStream inp = new FileInputStream(path);
			      String fileExtn = GetFileExtension(path);
			      Workbook wb_xssf; //Declare XSSF WorkBook
			      Workbook wb_hssf; //Declare HSSF WorkBook
			      Sheet sheet = null; // sheet can be used as common for XSSF and HSSF
		//	WorkBook
			      if (fileExtn.equalsIgnoreCase("xlsx"))
			      {
				       wb_xssf = new XSSFWorkbook(path);
				      logger.info("xlsx="+wb_xssf.getSheetName(0));
				      sheet = wb_xssf.getSheetAt(0);
			      }
			      if (fileExtn.equalsIgnoreCase("xls"))
			      {
				     POIFSFileSystem fs = new POIFSFileSystem(inp);
			    	  wb_hssf = new HSSFWorkbook(fs);
			    	  logger.info("xls="+wb_hssf.getSheetName(0));
			    	  sheet = wb_hssf.getSheetAt(0);
			      }
			Iterator rows = sheet.rowIterator();
			int totalrows = sheet.getLastRowNum() + 1;
			logger.info("totalrows==============="+totalrows);
			if (totalrows > 10000) {
				// "Limit exceeds: Maximum"
				isLarge = true;
			}

			int rowNumber = 1;
			//logger.info(totalrows +" is totalrows");
			if (isLarge) {
				
				msgDto.setMsgId(Constants.INVALID_FILESIZE);
				msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.invalid.filesize"));
				logger.info("File size exceeds.");
				return msgDto;
			}
				
			if (totalrows < 3) 
			{
				msgDto.setMsgId(Constants.BLANK_EXCEL);
				msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.blank.excel"));
				return msgDto;
			}
			while (rows.hasNext()) {

				Row row = (Row) rows.next();
				
				rowNumber = row.getRowNum();
				
				
				if (rowNumber == 0) 
				{
					
					if (row.getPhysicalNumberOfCells() != Constants.TOTAL_HEADERS_BULK_QUALIFIED_LEAD_REGISTARTION)
					{
						msgDto.setMsgId(Constants.INVALID_EXCEL);
						msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.invalid.excel"));
						return msgDto;
					}
					else
						if(totalrows == 2)
						{
							msgDto.setMsgId(Constants.BLANK_EXCEL);
							msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.blank.excel"));
							return msgDto;
						}	

						else
						{
							continue;
						}
					
				}
				
				if (rowNumber > 1) // Starting parsing excel after 1st row
				{

					Iterator cells = row.cellIterator();

					int columnIndex = 0;
					int cellNo = 0;

					if (cells != null) {
						LeadRegistrationFormBean beanObject = new LeadRegistrationFormBean();

						while (cells.hasNext()) {
							cellNo++;
							Cell cell = (Cell) cells.next();
							columnIndex = cell.getColumnIndex();

							String cellValue = null;

							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_NUMERIC:
								cellValue = String.valueOf((long) cell
										.getNumericCellValue());
								break;
							case Cell.CELL_TYPE_STRING:
								cellValue = cell.getStringCellValue();
								break;
							}
							if (cellValue != null) {
								cellValue = cellValue.trim();
							}
							else
								cellValue = "";

							switch (columnIndex) {
							case 0:
								beanObject.setCustomerName(cellValue);
								break;
							case 1:
								beanObject.setContactNo(cellValue);
								break;
							case 2:
								String productIds[] = new String[1]; ;
								if(cellValue.equals(""))
								{
									productIds[0]= "";
								}
								else
								{

									productIds[0]= cellValue;
								}
								beanObject.setProductIds(productIds);
								break;
							case 3:
								beanObject.setAddress1(cellValue);
								break;
								
							case 4: 
								beanObject.setAddress2(cellValue); 
								break;
					
							
							case 5:
								beanObject.setCityCode(cellValue);
								break;
								
								
							case 6:
								beanObject.setCityZoneCode(cellValue);
								break;
								
							case 7: 
								beanObject.setPinCode(cellValue); 
								break;
							
								//added BY Bhaskar
								
							case 8: 
								beanObject.setRsuCode(cellValue); 
								break;
								

							case 9:
								beanObject.setCircleId(cellValue);
								break;

							case 10: 
								beanObject.setEmail(cellValue); 
								break;

							case 11:
								beanObject.setAlternateContactNo(cellValue);
								break;

							case 12: 
								beanObject.setLandlineNo(cellValue);
								break; 
								
							case 13:
								beanObject.setExistingCustomer(cellValue); 
								break;

						/*	case 13: 
								//beanObject.setZoneCode(cellValue);
								beanObject.setCityZoneCode(cellValue);
								break;
								
							case 14: 
								beanObject.setRsuCode(cellValue);
								//logger.info("cellValue length:"+cellValue.length());
								//logger.info("beanObject.setRsuCode(cellValue):"+beanObject.getRsuCode());
								break;	*/
								
							case 14: // Above code commented by Neetika as RSU and Zone code are removed fields. and case15 changed to 13
								beanObject.setRemarks(cellValue);
								break;
								
							case 15: // Above code commented by Neetika as RSU and Zone code are removed fields. and case15 changed to 13
								LocalDateTime time = null;
								boolean flag = false;
								try{
								time = new LocalDateTime(cell.getDateCellValue());
								}
								catch(Exception e)
								{
								beanObject.setAppointmentTime(INCORRECT_FORMAT);
								flag = true;
								}
								if(!flag)
								{
									beanObject.setDate(time.getDayOfMonth());
									beanObject.setYear(time.getYear());
									beanObject.setMonth(time.getMonthOfYear());
									String Date=time.getMonthOfYear()+"/"+time.getDayOfMonth()+"/"+time.getYear();
									beanObject.setAppointmentDate(Date);
									String  hour=String.valueOf(time.getHourOfDay());
									//logger.info("hours is************"+ hour);
									String  min=String.valueOf(time.getMinuteOfHour());
									//logger.info("minutes is*********"+ min);
									//String min=String.valueOf(time.getSecondOfMinute());						
									beanObject.setAppointmentHour(hour);
									beanObject.setAppointmentMinute(min);
									String Date1=Date+" "+hour+":"+min;
									beanObject.setAppointmentTime(Date1);
								}											
								break;
								
							case 16: // Above code commented by Neetika as RSU and Zone code are removed fields. and case15 changed to 13
								beanObject.setCaf(cellValue);
								
								break;
								
							case 17: // Above code commented by Neetika as RSU and Zone code are removed fields. and case15 changed to 13
								beanObject.setExtraParam1(cellValue);
								
								break;
							case 18: // added by pratap campaing filed is added on 6 jan 2013
								beanObject.setCampaign(cellValue);
								break;

							}

							beanObject.setLeadStatusId(Constants.LEAD_STATUS_QUALIFIED);
							beanObject.setSourceId(Constants.SOURCE_TYPE_OUTBOUND+"");
							beanObject.setCreatedBy(userBean.getUserLoginId());
							/* Added by parnika for Adding Qualified Lead Category */
							beanObject.setLeadCategory(Constants.QUALIFIED_LEAD);
							/* End of changes by parnika */
						}// Adding single row to a beanObject

						errorList = validateQualifiedLeadDto(beanObject, rowNumber,
								errorList, validListInsert, validRowNosList);
					}// if


				}// Parsing single row

			}// Parsing all rows

			SimpleDateFormat otdf = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
			logFilePath = logFilePath + "bulkQualifiedLeadsRegistrationLog"
			+ otdf.format(new java.util.Date()) + ".csv";

			boolean isSucessTrnx = true;
			int errorRecordSize = 0;
			errorRecordSize = errorList.size();

			if (validListInsert.size() > 0) {
				ArrayList<BulkUploadMsgDto> daoMsgList = new ArrayList<BulkUploadMsgDto>();

				daoMsgList = dao.insertRecord(validListInsert,Constants.SOURCE_TYPE_OUTBOUND);

				Iterator<Integer> 			itrValidRowNosList  =  validRowNosList.iterator();
				Iterator<BulkUploadMsgDto>  itrDaoMsgList 		=  daoMsgList.iterator();

				while(itrValidRowNosList.hasNext())
				{
					BulkUploadErrorLogDto bulkErr = new BulkUploadErrorLogDto();
					bulkErr.setRowNum(itrValidRowNosList.next());

					if(itrDaoMsgList.hasNext())
					{
						msgDto = itrDaoMsgList.next(); 
						switch(msgDto.getMsgId())
						{
						case Constants.LEAD_INSERT_NEW_LEAD :
							bulkErr.setErrMsg("Lead ID : "+msgDto.getLeadId());
							break;

						case Constants.LEAD_INSERT_EXISTING_LEAD :
							bulkErr.setErrMsg("Lead Already Exist : "+msgDto.getLeadId());
							isSucessTrnx = false;
							break;

						case Constants.LEAD_INSERT_FAIL :
							bulkErr.setErrMsg(msgDto.getMessage());		
							isSucessTrnx = false;
							break;
						}

					}
					errorList.add(bulkErr);
				}

			}

			if (!isSucessTrnx || errorRecordSize>0 ) {
				msgDto.setMsgId(Constants.SERVICE_MSG_FAIL);
				msgDto.setMessage(logFilePath);
				writeLogs(errorList, logFilePath); // writing logs to the csv
			}
			else

			{
				msgDto.setMsgId(Constants.SERVICE_MSG_SUCCESS);
				msgDto.setMessage(logFilePath);
				writeLogs(errorList, logFilePath); // writing logs to the csv
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred..");
			msgDto.setMsgId(Constants.SERVICE_MSG_ERROR);
			msgDto.setMessage(PropertyReader
					.getAppValue("lms.bulk.upload.error"));
			return msgDto;
		}
		finally{
			try{
			newFile.delete();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.info("Error while deleting the file");
			}
		}
		return msgDto;

	}// uploadQualifiedLeads


	/**
	 * @param beanObject
	 * @param rowNumber
	 * @param errorList
	 * @return
	 * @throws LMSException
	 */
	private List<BulkUploadErrorLogDto> validateOpenLeadDto(
			LeadRegistrationFormBean beanObject, int rowNumber,
			List<BulkUploadErrorLogDto> errorList, ArrayList<LeadDetailsDTO> validListInsert,ArrayList<Integer> validRowNosList ) throws LMSException {
		
		StringBuffer errMsg = new StringBuffer("");
		boolean errFlag = false;
		MasterService mstrService = new MasterServiceImpl();
		
		int circleId = -1;
		//Added by Neetika for LMS phase -2
		String zoneCode=null;
		String CityzoneCode=null;
		String prodid=null;
		boolean circleflag=false;
		boolean prodflag=false;
		try {
			//logger.info("rowNumber==" +rowNumber);

			BulkUploadErrorLogDto bulkErr = new BulkUploadErrorLogDto();

			if (beanObject.getContactNo().equals("") || beanObject.getProductIds() == null
					|| beanObject.getCircleId() == null
					|| beanObject.getCircleId().equals("") || beanObject.getCustomerName().equals("") || beanObject.getProductIds().equals("")) {
				errMsg
				.append("MOBILE_NO or PRODUCT_ID or  CIRCLE_ID are mandatory fields |");
			}

			logger.info("product_id value"+beanObject.getProductIds());
			if(beanObject.getProductIds() == null || beanObject.getProductIds().equals(""))
			{
				errMsg.append("Product_Id is Mandatory |");
				
			}
			if (!beanObject.getCustomerName().equals("")) {
				if (!Utility.validateAlphaNumeric(beanObject.getCustomerName()) &&  beanObject.getCustomerName().length() > 50) {
					errMsg
					.append("CUSTOMER_NAME should not be of more than 50 characters |");
				} else {
					if (!Utility.validateAlphaNumeric(beanObject.getCustomerName())) {
						errMsg
						.append("CUSTOMER_NAME should contain valid characters only |");
					}
				}

			}

			if (!beanObject.getContactNo().equals("")) {
				if (beanObject.getContactNo().length() != 10) {
					errMsg.append("MOBILE_NO should be of 10 digits only |");
				} else {
					if (!Utility.validateNumber(beanObject.getContactNo())) {
						errMsg.append("MOBILE_NO should be numeric only |");
					}
				}

			}else {
				errMsg.append("MOBILE_NO are mandatory field |");
			}

			if (beanObject.getProductIds() != null) {
				if (!isNumeric(beanObject.getProductIds()[0])) {
					errMsg.append("PRODUCT_ID should be numeric |");
				}

				else if (!mstrService.isValidProduct(Integer
						.parseInt(beanObject.getProductIds()[0]))) {
					errMsg.append("Invalid PRODUCT_ID |");
					
			
					
				}
				else
					prodflag=true;
				/* Commented by Parnika as in case of open leads, it is not mandatory 
				else if (PropertyReader.getAppValue("lms.telemedia.productLobId").equals(String.valueOf(mstrService.getProductLobId(Integer
								.parseInt(beanObject.getProductIds()[0])))) 
						&& beanObject.getAddress1().equals("") && 
						beanObject.getCityCode().equals("")) 
				{
					errMsg.append("For Telemedia Products ; Address and City are mandatory |");
				}
				End of comments by Parnika */

			}

			if (!isNumeric(beanObject.getCircleId())) {
				errMsg.append("CIRCLE_ID should be numeric |");
			}

			else {
				circleId = Integer.parseInt(beanObject.getCircleId());
				if(beanObject.getProductIds() !=null)
				prodid=beanObject.getProductIds()[0];
				int porId =0;
				if(prodid !=null && prodid.length() >0){
					porId =Integer.parseInt(prodid);
				}else {
					porId =-1;
				}
				if (!mstrService.isValidCircleUsingProd(Integer.parseInt(beanObject.getCircleId()),porId)) {
					errMsg.append("Invalid CIRCLE_ID |");
				}
				else
					circleflag=true;
			}

			if (!beanObject.getAddress1().equals("")) {
				if (beanObject.getAddress1().length() >= Constants.LEAD_PROSPECT_CUSTOMER_ADDRESS_ONE_SIZE) {
					errMsg
					.append("ADDRESS should not be of more than "+Constants.LEAD_PROSPECT_CUSTOMER_ADDRESS_ONE_SIZE+" characters |");
				}

			}
			
			 if(!beanObject.getEmail().equals("")) {
				  if(!Utility.validateEmail(beanObject.getEmail()) ) {
				  errMsg.append("EMAIL_ID should be of proper format |");
				  errFlag = true;
				  
				  }
				  
				  }
				  

			/*
			 * if(!beanObject.getAddress2().equals("")) {
			 * if(beanObject.getAddress2().length() > 100) {
			 * errMsg.append("ADDRESS_2 should not be of more than 100 characters |"
			 * ); }
			 * 
			 * }
			 */

			/*if (!beanObject.getCityCode().equals("")) {
				if (beanObject.getZoneCode().equals("")) {
					errMsg.append("For City ; Zone Code is mandatory |");
					errFlag = true;
				} else {
					if (!mstrService.isValidCityFromZone(beanObject.getCityCode(),beanObject.getZoneCode()))
							 {
						errMsg.append("City should be of input Zone |");
						errFlag = true;

					}
				}
			}
*/
			/*
			 * if(!beanObject.getPinCode().equals("")) {
			 * if(beanObject.getCityCode().equals("")) {
			 * errMsg.append("For Pincode ; City is mandatory |"); errFlag =
			 * true; } else {
			 * if(!mstrService.isValidPincode(beanObject.getPinCode
			 * (),beanObject.getCityCode())) {
			 * errMsg.append("Pincode should be of input City |"); errFlag =
			 * true;
			 * 
			 * } } }
			 * 
			 * 
			 * if(!beanObject.getStateId().equals("")) {
			 * if(!mstrService.isValidState(beanObject.getStateId())) {
			 * errMsg.append("Invalid STATE_CODE |");
			 * 
			 * }
			 * 
			 * }
			 * 
			 * if(!beanObject.getEmail().equals("")) {
			 * if(!Utility.validateEmail(beanObject.getEmail()) ) {
			 * errMsg.append("EMAIL_ID should be of proper format |"); errFlag =
			 * true;
			 * 
			 * }
			 * 
			 * }
			 * 
			 * 
			 * if(!beanObject.getAlternateContactNo().equals("")) {
			 * if(beanObject.getAlternateContactNo().length() != 10) {
			 * errMsg.append("ALT_MOBILE_NO should be of 10 digits only |"); }
			 * else {
			 * if(!Utility.validateNumber(beanObject.getAlternateContactNo())) {
			 * errMsg.append("ALT_MOBILE_NO should be numeric only |"); } }
			 * 
			 * }
			 * 
			 * if(!beanObject.getLandlineNo().equals("")) {
			 * if(beanObject.getLandlineNo().length() >= 15) {
			 * errMsg.append("LANDLINE_NO too long |"); } else {
			 * if(!Utility.validatePhoneNo(beanObject.getLandlineNo())) {
			 * errMsg.
			 * append("LANDLINE_NO should of type state code-number <xxx-xxxxxxx> |"
			 * ); } }
			 * 
			 * }
			 * 
			 * if(!beanObject.getExistingCustomer().equalsIgnoreCase("y")) {
			 * if(!beanObject.getExistingCustomer().equalsIgnoreCase("n")) {
			 * errMsg.append("Enter either Y or N  for EXISTING_CUSTOMER | ");
			 * errFlag = true; }
			 * 
			 * }
			 */

			if (!beanObject.getLanguage().equals("")) {
				if (beanObject.getLanguage().length() > 30) {
					errMsg
					.append("LANGUAGE should not be of more than 30 characters |");
				} else {
					if (!Utility.validateName(beanObject.getLanguage())) {
						errMsg
						.append("LANGUAGE should contains characters only |");
					}
				}

			}

			/*
			 * if(!beanObject.getMaritalStatus().equalsIgnoreCase("m")) {
			 * if(!beanObject.getMaritalStatus().equalsIgnoreCase("u")) {
			 * errMsg.append("Enter either M or U  for MARITAL_STATUS | ");
			 * errFlag = true; }
			 * 
			 * }
			 * 
			 * 
			 * if(!isNumeric(beanObject.getRequestType())) {
			 * errMsg.append("REQUEST_ID should be numeric |"); }
			 * 
			 * else {
			 * 
			 * if(!mstrService.isValidRequestType(Integer.parseInt(beanObject.
			 * getRequestType()))) { errMsg.append("Invalid REQUEST_ID |"); }
			 * 
			 * }
			 * 
			 * if(!beanObject.getAppointmentTime().equals("")) {
			 * if(!isValidDateFormat(beanObject.getAppointmentTime())) {
			 * errMsg.append
			 * ("APPOINTMENT_TIME should be like dd-mm-yyyy hh-mm |"); }
			 * 
			 * }
			 * 
			 * 
			 * 
			 * if(!isNumeric(beanObject.getSourceId())) {
			 * errMsg.append("SOURCE_ID should be numeric |"); }
			 * 
			 * else {
			 * 
			 * if( !mstrService.isValidSource(
			 * Integer.parseInt(beanObject.getSourceId()) ) ) {
			 * errMsg.append("Invalid SOURCE_ID |"); }
			 * 
			 * }
			 * 
			 * 
			 * if(!isNumeric(beanObject.getSubSourceId())) {
			 * errMsg.append("SUB_SOURCE_ID should be numeric |"); }
			 * 
			 * else {
			 * 
			 * if( !mstrService.isValidSubSource(
			 * Integer.parseInt(beanObject.getSubSourceId()) ) ) {
			 * errMsg.append("Invalid SUB_SOURCE_ID |"); }
			 * 
			 * }
			 * 
			 * if(!beanObject.getZoneCode().equals("")) {
			 * if(beanObject.getCityCode().equals("")) {
			 * errMsg.append("For Zone ; City is mandatory |"); errFlag = true;
			 * } else {
			 * if(!mstrService.isValidZone(beanObject.getZoneCode(),beanObject
			 * .getCityCode())) {
			 * errMsg.append("Zone should be of input City |"); errFlag = true;
			 * 
			 * } } }
			 * 
			 * 
			 * if(!beanObject.getRsuCode().equals("")) {
			 * if(beanObject.getZoneCode().equals("")) {
			 * errMsg.append("For Rsu ; Zone is mandatory |"); errFlag = true; }
			 * else {
			 * if(!mstrService.isValidRsu(beanObject.getRsuCode(),beanObject
			 * .getZoneCode())) {
			 * errMsg.append("Rsu should be of input Zone |"); errFlag = true;
			 * 
			 * }
			 * 
			 * } }
			 * 
			 * 
			 * if(!isNumeric(beanObject.getLeadStatusId())) {
			 * errMsg.append("LEAD_STATUS should be numeric |"); }
			 * 
			 * else {
			 * 
			 * if(Integer.parseInt(beanObject.getLeadStatusId()) != 200) {
			 * if(Integer.parseInt(beanObject.getLeadStatusId()) != 305) {
			 * errMsg.append("LEAD_STATUS should be either 200 or 305 |"); }
			 * 
			 * }
			 * 
			 * }
			 */
			
			//Added by Neetika Mittal on 18-Nov for Open lead new column added Zone-code validation
		/*	if (!beanObject.getZoneCode().equalsIgnoreCase("")) {
			if (beanObject.getZoneCode().length()>25) 
			{
				errMsg.append("Zone code should note be more than 25 Characters |");
			}

			else 
			{
				
				if (circleId == -1) {
					errMsg.append("For Zone Code ; Circle is mandatory |");
					errFlag = true;
				}
				else if (!prodflag)
				{
					errMsg.append("For Zone Code ; Product ID is mandatory |");
					errFlag = true;
				}
				else {
				zoneCode = beanObject.getZoneCode();
				prodid=beanObject.getProductIds()[0];
				if(circleflag==true && prodflag==true) {
				if (!mstrService.isValidZoneCode((Integer.parseInt(beanObject
						.getCircleId())),Integer.parseInt(prodid),zoneCode)) {
					logger.info("Invalid Zone Code");
					errMsg.append("Invalid Zone Code |");
				}
				}
				}
			}
			}*/
			//end by neetika for zone code
			
			//
			
			
			if (!beanObject.getCityZoneCode().equalsIgnoreCase("")) {
				if (beanObject.getCityZoneCode().length()>25) 
				{
					errMsg.append("CityZone code should note be more than 25 Characters |");
				}

				else 
				{
					
					if (circleId == -1) {
						errMsg.append("For CityZone Code ; Circle is mandatory |");
						errFlag = true;
					}
					else if (!prodflag)
					{
						errMsg.append("For CityZone Code ; Product ID is mandatory |");
						errFlag = true;
					}
					else {
					CityzoneCode = beanObject.getCityZoneCode();
					prodid=beanObject.getProductIds()[0];
					if(circleflag==true && prodflag==true) {
					if (!mstrService.isValidCityZoneCodeReverse((Integer.parseInt(beanObject
							.getCircleId())),Integer.parseInt(prodid),CityzoneCode)) {
						logger.info("Invalid CityZone Code");
						errMsg.append("Invalid CityZone Code |");
					}
					}
					}
				}
				}
			
			
			if (beanObject.getCityZoneCode().equalsIgnoreCase("")) { // cityzone if present then dont check city code..if cityzone absent then only check city code based on circle and product id
				if (!beanObject.getCityCode().equals("")) {
					prodid=beanObject.getProductIds()[0];
					if (circleId == -1) {
						errMsg.append("For City Code ; Circle is mandatory |");
						errFlag = true;
					}
					else if (!prodflag)
					{
						errMsg.append("For City Code ; Product ID is mandatory |");
						errFlag = true;
					} else {
						if (!mstrService.isValidCityReverse((Integer.parseInt(beanObject
								.getCircleId())),Integer.parseInt(prodid),beanObject.getCityCode()))
								 {
							errMsg.append("City should be of input Circle and LOB  |");
							errFlag = true;

						}
					}
				}
			}
			//
			
			if (!beanObject.getRemarks().equals("")) {
				if (beanObject.getRemarks().length() > 100) {
					errMsg
					.append("REMARKS should not be of more than 100 characters |");
				}

			}
			
			//Campagin validation -- added by bhaskar
			
			if(!beanObject.getCampaign().equals(""))
			{
				//String Campaign= beanObject.getCampaign();
				if(!mstrService.isvalidCampaignField(beanObject.getCampaign()))
				{
					errMsg.append("Invalid Campagin Name");	
				}
			}

			if (errMsg.length() == 0)
				errFlag = false;
			else
				errFlag = true;

			if (errFlag) {
				bulkErr.setRowNum(rowNumber);
				bulkErr.setErrMsg(errMsg.toString());
				errorList.add(bulkErr);
			} else {
				validListInsert.add(getLeadDetailsDto(beanObject));
				validRowNosList.add(rowNumber);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occured while validating beanObject");
			throw new LMSException("Exception occurred in validateDto");
		}

		return errorList;
	}// validateOpenLeadDto
	
	@SuppressWarnings("deprecation")
	private List<BulkUploadErrorLogDto> validateQualifiedLeadDto(
			LeadRegistrationFormBean beanObject, int rowNumber,
			List<BulkUploadErrorLogDto> errorList, ArrayList<LeadDetailsDTO> validListInsert, ArrayList<Integer> validRowNosList ) throws LMSException {
		StringBuffer errMsg = new StringBuffer("");
		boolean errFlag = false;
		MasterService mstrService = new MasterServiceImpl();
		
		int circleId = -1;
		boolean prodflag=false;
		boolean circleflag=false;
		String zoneCode="";
		String prodid="";
		String CityzoneCode="";
		String pincode="";
		String rsuCode="";
		int productLobId = -1;
		int circleMstrId=-1;
		try {

			BulkUploadErrorLogDto bulkErr = new BulkUploadErrorLogDto();

			if (beanObject.getCustomerName().equals("") ||	beanObject.getContactNo().equals("") || beanObject.getProductIds() == null
					|| beanObject.getCircleId() == null || beanObject.getCircleId().equals("") || beanObject.getProductIds()[0] == null 
					|| beanObject.getCityCode().equals("") || beanObject.getAddress1().equals(""))
				
					{
				errMsg
				.append("CUSTOMER_NAME;MOBILE_NO;PRODUCT_ID,CIRCLE_ID,CITY CODE AND ADDRESS are mandatory fields |");
			}
			//logger.info("yyyyyyyyyyyyyy"+beanObject.getProductIds()[0]);
			
			if(productLobId!=2 && (beanObject.getPinCode().equals("") || beanObject.getPinCode()==null) )
			{
				errMsg
				.append("PINCODE is mandatory |");
			}
			
			if(beanObject.getCustomerName().equals(""))
			{
				errMsg
				.append("Please enter CUSTOMER_NAME|");
			}
			if(beanObject.getContactNo().equals(""))
			{
				errMsg
				.append("Please enter MOBILE_NO|");
			}
			if(beanObject.getCircleId().equals("") || beanObject.getCircleId() == null)
			{
				errMsg
				.append("Please enter CIRCLE_ID|");
			}
			

			if (!beanObject.getCustomerName().equals("")) {
				if ( !Utility.validateAlphaNumeric(beanObject.getCustomerName()) && beanObject.getCustomerName().length() > 50) {
					errMsg
					.append("CUSTOMER_NAME should not be of more than 50 characters |");
				} else {
					if (!Utility.validateAlphaNumeric(beanObject.getCustomerName())) 
					{
						errMsg
						.append("CUSTOMER_NAME should contain valid characters only |");
					}
				}

			}

			if (!beanObject.getContactNo().equals("")) {
				if (beanObject.getContactNo().length() != 10) {
					errMsg.append("MOBILE_NO should be of 10 digits only |");
				} else {
					if (!Utility.validateNumber(beanObject.getContactNo())) {
						errMsg.append("MOBILE_NO should be numeric only |");
					}
				}

			}

			if (beanObject.getProductIds() != null && beanObject.getProductIds()[0] != null) {
				if (!isNumeric(beanObject.getProductIds()[0])) {
					errMsg.append("PRODUCT_ID should be numeric |");
				}

				else if (!mstrService.isValidProduct(Integer
						.parseInt(beanObject.getProductIds()[0]))) {
					errMsg.append("Invalid PRODUCT_ID |");
					
					;
					
				} else if (PropertyReader
						.getAppValue("lms.telemedia.productLobId").equals(mstrService.getProductLobId(Integer
								.parseInt(beanObject.getProductIds()[0]))+"" ) 
						&& ( beanObject.getAddress1().equals("") || 
						beanObject.getCityCode().equals("") ) )
				{
					errMsg.append("For Telemedia Products ; Address_1 and City are mandatory |");
				}
				
				
				if (PropertyReader.getAppValue("lms.telemedia.productLobId").equals(mstrService.getProductLobId(Integer
								.parseInt(beanObject.getProductIds()[0]))+"" ) )
				{
					/* Changes by Parnika for telemedia products */
					
					// beanObject.setLeadStatusId(Constants.LEAD_STATUS_OPEN); 

					beanObject.setLeadStatusId(Constants.LEAD_STATUS_VERIFICATION);
					
					/* End of changes by Parnika */
				}
				
				if (PropertyReader
						.getAppValue("lms.dth.productLobId").equals(mstrService.getProductLobId(Integer
								.parseInt(beanObject.getProductIds()[0]))+"" ) )
				{
					errMsg.append("For DTH Products ; Pincode is mandatory |");
				}
				
				 prodflag=true;

			}
			
			if (!isNumeric(beanObject.getCircleId())) {
				errMsg.append("CIRCLE_ID should be numeric |");
			}

			else {
				circleId = Integer.parseInt(beanObject.getCircleId());
				if(beanObject.getProductIds() != null && beanObject.getProductIds()[0] != null){
					prodid=beanObject.getProductIds()[0];
					if (!mstrService.isValidCircleUsingProd(Integer.parseInt(beanObject
							.getCircleId()),Integer.parseInt(prodid))) {
						errMsg.append("Invalid CIRCLE_ID |");
					}

					else
					{
						 circleflag=true;
						 productLobId=mstrService.getProductLobId(Integer.parseInt(beanObject.getProductIds()[0]));
							circleMstrId=mstrService.getCircleMstrIdValue(Integer.parseInt(beanObject.getCircleId()),productLobId);
							beanObject.setLobId(productLobId);
							beanObject.setCircleMstrId(circleMstrId);
					}
				}


			}
			

			if (!beanObject.getAddress1().equals("")) {
				if (beanObject.getAddress1().length() >= Constants.LEAD_PROSPECT_CUSTOMER_ADDRESS_ONE_SIZE) {
					errMsg
					.append("ADDRESS_1 should be less than "+Constants.LEAD_PROSPECT_CUSTOMER_ADDRESS_ONE_SIZE+" characters |");
				}
				/*else
					if (!Utility.validateAddress(beanObject.getAddress1())) {
						errMsg
						.append("Only these special charcacters /\\-:()@ are allowed in ADDRESS_1 |");
					}*/

			}

			
		    if(!beanObject.getAddress2().equals("")) 
		    {
			  if(beanObject.getAddress2().length() >= 200) 
			  {
				  errMsg.append("ADDRESS_2 should be less than 200 characters |"); 
			  }
			  
			 }
			 
		    logger.info(beanObject.getCityCode()+"is city  "+" and cityzone is  "+beanObject.getCityZoneCode());
			/*if (!beanObject.getCityCode().equals("")) { //changed by neetika from circle to zone
				if (beanObject.getZoneCode().equals("")) {
					errMsg.append("For City ; Zone is mandatory |");
					errFlag = true;
				} else {
					if (!mstrService.isValidCity(beanObject.getCityCode(),
							circleId)) {
						errMsg.append("City should be of input Circle |");  //change
						errFlag = true;

					}
					//changed by neetika
					if (!mstrService.isValidCityFromZone(beanObject.getCityCode(),beanObject.getZoneCode()))
					 {
						errMsg.append("City should be of input Zone |");
						errFlag = true;

					 }
				}
			}
			if (!beanObject.getCityZoneCode().equals("")) { //Added by neetika 
				if (beanObject.getCityCode().equals("")) {
					errMsg.append("For CityZoneCode ; City is mandatory |");
					errFlag = true;
				} else {
					
					if (!mstrService.isValidCityZoneCode(beanObject.getCityCode(),beanObject.getCityZoneCode()))
					 {
						errMsg.append("CityZone should be of input City |");
						errFlag = true;

					 }
				}
			}
*/
		    //Added By Bhaskar RSU Validation
		    
		    if(!beanObject.getRsuCode().equals("")) 
			{
		    	if (circleId == -1) {
					errMsg.append("For Rsu ; Circle is mandatory |");
					errFlag = true;
				}
				else if (!prodflag)
				{
					errMsg.append("For Rsu ; Product ID is mandatory |");
					errFlag = true;
				}
				else if (!prodflag)
				{
					prodid=beanObject.getProductIds()[0];
					if(!mstrService.isValidProductLobForRsuCode(Integer.parseInt(prodid))){
					errMsg.append("For Rsu ; Product ID Should be Telemedia |");
					errFlag = true;
					}
				}
		    	
				else if (beanObject.getCityCode().equals("") &&  beanObject.getCityZoneCode().equals("")) {
				rsuCode = beanObject.getRsuCode();
				prodid=beanObject.getProductIds()[0];
				if(circleflag==true && prodflag==true) {
				if (!mstrService.isValidRsuReverse((Integer.parseInt(beanObject.getCircleId())),Integer.parseInt(prodid),rsuCode)) {
					errMsg.append("Invalid Rsu Code |");
				}
				}
				} 
			}
		    	
		    if (beanObject.getRsuCode().equalsIgnoreCase("") ) {
			    if (!beanObject.getCityZoneCode().equalsIgnoreCase("")) {
					if (beanObject.getCityZoneCode().length()>25) 
					{
						errMsg.append("CityZone code should note be more than 25 Characters |");
					}

					else 
					{
						
						if (circleId == -1) {
							errMsg.append("For Rsu ; Circle is mandatory |");
							errFlag = true;
						}
						else if (!prodflag)
						{
							errMsg.append("For Rsu ; Product ID is mandatory |");
							errFlag = true;
						}
						else if (!prodflag)
						{
							prodid=beanObject.getProductIds()[0];
							if(!mstrService.isValidProductLobForRsuCode(Integer.parseInt(prodid))){
							errMsg.append("For Rsu ; Product ID Should be Telemedia |");
							errFlag = true;
							}
						}
				    	
						else {
						CityzoneCode = beanObject.getCityZoneCode();
						prodid=beanObject.getProductIds()[0];
						if(circleflag==true && !prodflag) {
						if (!mstrService.isValidCityZoneBasedRsuReverseNew((Integer.parseInt(beanObject.getCircleId())),Integer.parseInt(prodid),beanObject.getCityZoneCode())) {
							logger.info("Invalid CityZone Code");
							errMsg.append("Invalid CityZone Code |");
						}
						}
						}
					}
					}
			    }
		    	
		    if (beanObject.getRsuCode().equalsIgnoreCase("")) {
				if (beanObject.getCityZoneCode().equalsIgnoreCase("")) { // cityzone if present then dont check city code..if cityzone absent then only check city code based on circle and product id
					if (!beanObject.getCityCode().equals("")) {
						if(beanObject.getProductIds() != null && beanObject.getProductIds()[0] != null){					
						prodid=beanObject.getProductIds()[0];
						if (circleId == -1) {
							errMsg.append("For Rsu ; Circle is mandatory |");
							errFlag = true;
						}
						else if (!prodflag)
						{
							errMsg.append("For Rsu ; Product ID is mandatory |");
							errFlag = true;
						}
						else if (!prodflag)
						{
							prodid=beanObject.getProductIds()[0];
							if(!mstrService.isValidProductLobForRsuCode(Integer.parseInt(prodid))){
							errMsg.append("For Rsu ; Product ID Should be Telemedia |");
							errFlag = true;
							}
						}
				    	 else {
							if (!mstrService.isValidCityBasedRsuReverse((Integer.parseInt(beanObject.getCircleId())),Integer.parseInt(prodid),beanObject.getCityCode()))
									 {
								errMsg.append("City should be of input Circle and LOB  |");
								errFlag = true;

							}
						}
						}
					}
				}
			    }
		    	
			 //End by Bhaskar
		    
		    if(!beanObject.getPinCode().equals("")) 
			{
		    	if (circleId == -1) {
					errMsg.append("For Pincode ; Circle is mandatory |");
					errFlag = true;
				}
				else if (!prodflag)
				{
					errMsg.append("For Pincode ; Product ID is mandatory |");
					errFlag = true;
				}
				else {
				pincode = beanObject.getPinCode();
				prodid=beanObject.getProductIds()[0];
				if(circleflag==true && prodflag==true) {
				if (!mstrService.isValidPinReverse((Integer.parseInt(beanObject.getCircleId())),Integer.parseInt(prodid),pincode))
				{
					logger.info("Invalid Pincode Code");
					errMsg.append("Invalid Pincode Code |");
				}
				}
				}
		    	
				
			}
		    
		    
		    
		    if (beanObject.getPinCode().equalsIgnoreCase("")) {
		    if (!beanObject.getCityZoneCode().equalsIgnoreCase("")) {
				if (beanObject.getCityZoneCode().length()>25) 
				{
					errMsg.append("CityZone code should note be more than 25 Characters |");
				}

				else 
				{
					
					if (circleId == -1) {
						errMsg.append("For CityZone Code ; Circle is mandatory |");
						errFlag = true;
					}
					else if (!prodflag)
					{
						errMsg.append("For CityZone Code ; Product ID is mandatory |");
						errFlag = true;
					}
					else {
					CityzoneCode = beanObject.getCityZoneCode();
					prodid=beanObject.getProductIds()[0];
					if(circleflag==true && prodflag==true) {
					if (!mstrService.isValidCityZoneCodeReverse((Integer.parseInt(beanObject
							.getCircleId())),Integer.parseInt(prodid),CityzoneCode)) {
						logger.info("Invalid CityZone Code");
						errMsg.append("Invalid CityZone Code |");
					}
					}
					}
				}
				}
		    }
		    if (beanObject.getPinCode().equalsIgnoreCase("")) {
			if (beanObject.getCityZoneCode().equalsIgnoreCase("")) { // cityzone if present then dont check city code..if cityzone absent then only check city code based on circle and product id
				if (!beanObject.getCityCode().equals("")) {
					if(beanObject.getProductIds() != null && beanObject.getProductIds()[0] != null){					
					prodid=beanObject.getProductIds()[0];
					if (circleId == -1) {
						errMsg.append("For City Code ; Circle is mandatory |");
						errFlag = true;
					}
					else if (!prodflag)
					{
						errMsg.append("For City Code ; Product ID is mandatory |");
						errFlag = true;
					} else {
						if (!mstrService.isValidCityReverse((Integer.parseInt(beanObject
								.getCircleId())),Integer.parseInt(prodid),beanObject.getCityCode()))
								 {
							errMsg.append("City should be of input Circle and LOB  |");
							errFlag = true;

						}
					}
					}
				}
			}
		    }
		    
		    //Commented by neetika as we need to move in reverse order
		    
		  /*  
			if(!beanObject.getPinCode().equals("")) 
			{
				if(beanObject.getCityZoneCode().equals("")) 
				{
					errMsg.append("For Pincode ; CityZone is mandatory |"); 
					errFlag = true; 
				} 
				else 
				     {
							if(!mstrService.isValidPincode(beanObject.getPinCode(),beanObject.getCityZoneCode())) //changed by neetika from city code to city zone code for phase 2 on 20 nov
							{
								errMsg.append("Pincode should be of input CityZone |"); 
								errFlag =true;

							} 
					} 
			}*/
			  
			  
			  if(!beanObject.getStateId().equals("")) {
			  if(!mstrService.isValidState(beanObject.getStateId())) {
			  errMsg.append("Invalid STATE_CODE |");
			  
			  }
			  
			  }
			  
			  if(!beanObject.getEmail().equals("")) {
			  if(!Utility.validateEmail(beanObject.getEmail()) ) {
			  errMsg.append("EMAIL_ID should be of proper format |"); errFlag =
			  true;
			  
			  }
			  
			  }
			  
			  
			  if(!beanObject.getAlternateContactNo().equals("")) {
			  if(beanObject.getAlternateContactNo().length() != 10) {
			  errMsg.append("ALT_MOBILE_NO should be of 10 digits only |"); }
			  else {
			  if(!Utility.validateNumber(beanObject.getAlternateContactNo())) {
			  errMsg.append("ALT_MOBILE_NO should be numeric only |"); } }
			  
			  }
			  
			  if(!beanObject.getLandlineNo().equals("")) {
			  if(beanObject.getLandlineNo().length() >= 15) {
			  errMsg.append("LANDLINE_NO too long |"); } else {
			  if(!Utility.validatePhoneNo(beanObject.getLandlineNo())) {
			  errMsg.
			  append("LANDLINE_NO should of type state code-number <xxx-xxxxxxx> |"
			  ); } }
			  
			  }
			  
			  /* Commented by Parnika as Is Customer Exist is not a a mandatory field */
//			  if(!beanObject.getExistingCustomer().equalsIgnoreCase("y")) 
//			  {
//			  if(!beanObject.getExistingCustomer().equalsIgnoreCase("n")) {
//			  errMsg.append("Enter either Y or N  for EXISTING_CUSTOMER | ");
//			  errFlag = true; }
//			  
//			  }
			  /* End of comments by Parnika */
			 
			  /* Added by Parnika for validation */
			  
			  if(!beanObject.getExistingCustomer().trim().equalsIgnoreCase("")) 
			  {
			  if(!beanObject.getExistingCustomer().equalsIgnoreCase("y")) {
				  
			 if(!beanObject.getExistingCustomer().equalsIgnoreCase("n")) {
				  
			  errMsg.append("Enter either Y or N  for EXISTING_CUSTOMER | ");
			  errFlag = true; 
			  }
			  }
			  }
			  
			  /* End of changes by Parnika */
			  
			/*  if(!beanObject.getZoneCode().equals("")) {
			  if(beanObject.getCityCode().equals("")) {
			  errMsg.append("For Zone ; City is mandatory |"); errFlag = true;  //change
			  } else {
			  if(!mstrService.isValidZone(beanObject.getZoneCode(),beanObject
			  .getCityCode())) {
			  errMsg.append("Zone should be of input City |"); errFlag = true;
			  
			  } } } */
			/*//Added by Neetika Mittal on 18-Nov for Open lead new column added Zone-code validation
				if (!beanObject.getZoneCode().equalsIgnoreCase("")) {
				if (beanObject.getZoneCode().length()>25) 
				{
					errMsg.append("Zone code should note be more than 25 Characters |");
				}

				else 
				{
					
					if (circleId == -1) {
						errMsg.append("For Zone Code ; Circle is mandatory |");
						errFlag = true;
					}
					else if (!prodflag)
					{
						errMsg.append("For Zone Code ; Product ID is mandatory |");
						errFlag = true;
					}
					else {
					zoneCode = beanObject.getZoneCode();
					prodid=beanObject.getProductIds()[0];
					if(circleflag==true && prodflag==true) {
					if (!mstrService.isValidZoneCode((Integer.parseInt(beanObject
							.getCircleId())),Integer.parseInt(prodid),zoneCode)) {
						logger.info("Invalid Zone Code");
						errMsg.append("Invalid Zone Code |");
					}
					}
					}
				}
				}*/
			 /* if(!beanObject.getRsuCode().equals("")) { //RSU no longer in Qualified leads
			  if(beanObject.getZoneCode().equals("")) {
			  errMsg.append("For Rsu ; Zone is mandatory |"); errFlag = true; }
			  else {
			  if(!mstrService.isValidRsu(beanObject.getRsuCode(),beanObject
			  .getZoneCode())) {
			  errMsg.append("Rsu should be of input Zone |"); errFlag = true;
			  
			  }
			  
			  } }
			  */
			  

			if (!beanObject.getRemarks().equals("")) {
				if (beanObject.getRemarks().length() > 100) {
					errMsg
					.append("REMARKS should not be of more than 100 characters |");
				}

			}
								//Caf number validation
			
			if (!beanObject.getCaf().equals("")) {
				
				if (beanObject.getCaf().length() > 25) {
					errMsg
					.append("Caf No should not be of more than 25 characters |");
				}

			}
			
			//Sales executive no valiadtion
			if (!beanObject.getExtraParam1().equals("")) {
				
				if (!isNumeric(beanObject.getExtraParam1())) {
					errMsg.append("Sales Executive No should be numeric |");
				}
				
				if (beanObject.getExtraParam1().length() > 25) {
					errMsg
					.append("Caf No should not be of more than 25 characters |");
				}
				}
			
		// Appointment Validation
			
			if(beanObject.getAppointmentTime().equals(INCORRECT_FORMAT) || !beanObject.getAppointmentTime().equalsIgnoreCase("") )
			{
				LocalDateTime d=new LocalDateTime();
				if(beanObject.getAppointmentTime().equals(INCORRECT_FORMAT))
				{
					errMsg.append("Please enter correct format for date.");
				}
					else
				if(d.getDayOfMonth() > beanObject.getDate())
				{
					errMsg.append("Appointment Date cannot be a past Date.");
				}
				else if(d.getMonthOfYear() > beanObject.getMonth())
				{
					errMsg.append("Appointment month cannot be a past month.");
				}
				else if(d.getYear() > beanObject.getYear())
				{
					errMsg.append("Appointment year cannot be a past year.");
				}
				
				else if(beanObject.getAppointmentHour().equalsIgnoreCase(""))
				{
					errMsg.append("Please select Appointment  Hour");
				}
				else if(beanObject.getAppointmentMinute().equalsIgnoreCase(""))
				{
					errMsg.append("Please select Appointment Minute");
				}
				else if(beanObject.getAppointmentMinute().equalsIgnoreCase("0")&& beanObject.getAppointmentHour().equalsIgnoreCase("0"))
				{
					errMsg.append("Please enter appointment hour and minutes.");
					
				}
			}
			
			//Campagin validation -- added by bhaskar
			if(!beanObject.getCampaign().equalsIgnoreCase(""))
			{
				String Campaign= beanObject.getCampaign();
				
				if(!mstrService.isvalidCampaignField(beanObject.getCampaign()))
				{
					errMsg.append("Invalid Campagin Name");	
				}
			}

			if (errMsg.length() == 0)
				errFlag = false;
			else
				errFlag = true;

			if (errFlag) {
				bulkErr.setRowNum(rowNumber);
				bulkErr.setErrMsg(errMsg.toString());
				errorList.add(bulkErr);
			} else {
				validListInsert.add(getLeadDetailsDto(beanObject));
				validRowNosList.add(rowNumber);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occured while validating beanObject");
			throw new LMSException("Exception occurred in validateQualifiedLeadDto");
		}

		return errorList;
	}// validateQualifiedLeadDto

	private String prodid() {
		// TODO Auto-generated method stub
		return null;
	}

	public void writeLogs(List<BulkUploadErrorLogDto> errorList,
			String errLogFileName) throws LMSException {
		boolean isError = false;

		try {
			if (errorList.size() > 0) {
				FileWriter fw = new FileWriter(errLogFileName);
				PrintWriter pw = new PrintWriter(fw);
				BulkUploadErrorLogDto bulkErrDto;

				pw.print("Row No");
				pw.print(",");
				pw.println("Upload Status");

				Iterator iter = errorList.iterator();

				while (iter.hasNext()) {
					bulkErrDto = (BulkUploadErrorLogDto) iter.next();
					pw.print(bulkErrDto.getRowNum());
					pw.print(",");
					pw.println(bulkErrDto.getErrMsg());
					isError = true;
				}

				pw.flush(); // Flush the output to the file
				pw.close();
				fw.close();

			}

		} catch (IOException io) {
			io.printStackTrace();
			logger.info("IO Exception occurred while writing logs to csv");
			throw new LMSException(
			"IO Exception occurred while writting error logs to csv");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exception occurred while writing logs to csv");
			throw new LMSException(
			"Exception occurred while writing error logs to csv");
		}

	}// writeLogs

	private LeadDetailsDTO getLeadDetailsDto(
			LeadRegistrationFormBean leadRegistrationBean) throws LMSException {
		LeadDetailsDTO leadDetailsDTO = new LeadDetailsDTO();

		try {
			String productIds[] = leadRegistrationBean.getProductIds();
			int productIdArray[] = new int[productIds.length];
			
			if(leadRegistrationBean.getLeadStatusId().equals(Constants.LEAD_STATUS_OPEN))
				leadDetailsDTO.setLeadStatusId(Integer.parseInt(Constants.LEAD_STATUS_OPEN));
			else if (leadRegistrationBean.getLeadStatusId().equals(Constants.LEAD_STATUS_QUALIFIED))
			{
					leadDetailsDTO.setLeadStatusId(Integer.parseInt(Constants.LEAD_STATUS_QUALIFIED));
			}
			
			/* Added By Parnika */
			else if (leadRegistrationBean.getLeadStatusId().equals(Constants.LEAD_STATUS_VERIFICATION))
			{
					leadDetailsDTO.setLeadStatusId(Integer.parseInt(Constants.LEAD_STATUS_VERIFICATION));
			}
			
			/* End of changes by Parnika */
				
	
			leadDetailsDTO.setCustomerName(leadRegistrationBean
					.getCustomerName());
			leadDetailsDTO.setContactNo(Long.parseLong(leadRegistrationBean
					.getContactNo()));

			if (!"".equals(leadRegistrationBean.getAlternateContactNo()))
				leadDetailsDTO.setAlternateContactNo(leadRegistrationBean
						.getAlternateContactNo());

			for (int ii = 0; ii < productIds.length; ii++) {
				productIdArray[ii] = Integer.parseInt(productIds[ii]);

			}

			leadDetailsDTO.setProductIds(productIdArray);

			leadDetailsDTO.setLanguage(leadRegistrationBean.getLanguage());
			leadDetailsDTO.setAddress1(leadRegistrationBean.getAddress1());
		    leadDetailsDTO.setAddress2(leadRegistrationBean.getAddress2());
			leadDetailsDTO.setCityCode(leadRegistrationBean.getCityCode());

			
			  leadDetailsDTO.setPinCode(leadRegistrationBean.getPinCode());
		
			 		 
			if (!"".equals(leadRegistrationBean.getCircleId()))
				leadDetailsDTO.setCircleId(Integer
						.parseInt(leadRegistrationBean.getCircleId()));

			  leadDetailsDTO.setRemarks(leadRegistrationBean.getRemarks());

			
			  leadDetailsDTO.setEmail(leadRegistrationBean.getEmail());
			  leadDetailsDTO
			  .setLandlineNo(leadRegistrationBean.getLandlineNo());
			  
			  /*leadDetailsDTO
			  .setMaritalStatus(leadRegistrationBean.getMaritalStatus());*/
			  
			  
			  leadDetailsDTO
			  .setExistingCustomer(leadRegistrationBean.getExistingCustomer());
			  
			/*  if(!"".equals(leadRegistrationBean.getRequestType()))
			  leadDetailsDTO
			  .setRequestType(Integer.parseInt(leadRegistrationBean
			  .getRequestType()));*/
			  
			  
			  
			/*  leadDetailsDTO.setAppointmentTime(leadRegistrationBean.
			  getAppointmentTime());*/
			  
			  leadDetailsDTO.setRemarks(leadRegistrationBean.getRemarks());
			  
			 if(!"".equals(leadRegistrationBean.getSourceId()))
			  leadDetailsDTO.
			  setSourceId(Integer.parseInt(leadRegistrationBean.getSourceId
			  ()));
			 
			  
			/*  if(!"".equals(leadRegistrationBean.getSubSourceId()))
			  leadDetailsDTO
			  .setSubSourceId(Integer.parseInt(leadRegistrationBean
			  .getSubSourceId()));*/
			  
			  leadDetailsDTO.setZoneCode(leadRegistrationBean.getZoneCode());
			  leadDetailsDTO
			  .setSubZoneName(leadRegistrationBean.getSubZoneName());
			  leadDetailsDTO.setRsuCode(leadRegistrationBean.getRsuCode());
			  
			  leadDetailsDTO.setCreatedBy(leadRegistrationBean.getCreatedBy());
			  
			  leadDetailsDTO.setCityZoneCode(leadRegistrationBean.getCityZoneCode());
			  leadDetailsDTO.setCampaign(leadRegistrationBean.getCampaign());// added by pratap 06 jan 2013
			  /* Added by Parnika */
			  leadDetailsDTO.setLeadCategory(leadRegistrationBean.getLeadCategory());
			  leadDetailsDTO.setCaf(leadRegistrationBean.getCaf());
			  leadDetailsDTO.setExtraParam1(leadRegistrationBean.getExtraParam1());
			  leadDetailsDTO.setAppointmentTime(leadRegistrationBean.getAppointmentTime());
			  /* End of changes by Parnika */
			 leadDetailsDTO.setProductLobId(leadRegistrationBean.getLobId());
			 leadDetailsDTO.setCircleMasterId(leadRegistrationBean.getCircleMstrId());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}

		return leadDetailsDTO;
	}

	public boolean isNumeric(String number) {
		boolean isNumeric = true;

		try {
			Integer.parseInt(number);
		} catch (Exception e) {
			isNumeric = false;
		}
		return isNumeric;

	}

	public boolean isValidDateFormat(String date) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH-mm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH-mm");
		boolean validate = false;
		try {
			if (date == null || "".equals(date)) {
				return true;
			}
			String date1 = sdf1.format(sdf2.parse(date));
			validate = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			validate = false;
		}
		return validate;
	}
	private static String GetFileExtension(String fname2)
	  {
	      String fileName = fname2;
	      String fname="";
	      String ext="";
	      int mid= fileName.lastIndexOf(".");
	      fname=fileName.substring(0,mid);
	      ext=fileName.substring(mid+1,fileName.length());
	      return ext;
	  }
}// class

