package com.ibm.lms.services.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;
import com.ibm.lms.common.Constants;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.dao.BulkMstrDao;
import com.ibm.lms.dao.impl.BulkMstrDaoImpl;
import com.ibm.lms.dto.BulkMsgDto;
import com.ibm.lms.dto.BulkMstrDTO;
import com.ibm.lms.dto.BulkUploadErrorLogDto;
import com.ibm.lms.dto.BulkUploadLogDto;
import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.BulkMstrService;
import com.ibm.lms.services.MasterService;

public class BulkMstrServiceImpl implements BulkMstrService  {
	private static final Logger logger;
	
	static {
		logger = Logger.getLogger(BulkMstrServiceImpl.class);
	}

	public BulkMsgDto uploadMstr(FormFile myfile,UserMstr userBean, String mstrType) throws LMSException {

		boolean isError = false;
		List<BulkUploadLogDto> dto = new ArrayList<BulkUploadLogDto>();
		String logFilePath = PropertyReader.getAppValue("lms.bulk.upload.error.log"); 
		BulkMstrDao bulkMstrDao = BulkMstrDaoImpl.bulkMstrDaoInstance();// changed by srikant new BulkMstrDaoImpl();
		BulkUploadMsgDto msgDto = new BulkUploadMsgDto();
		ArrayList<BulkMstrDTO> listBulkDto = new ArrayList<BulkMstrDTO>();
		ArrayList<BulkMstrDTO> msgListBulkDto = new ArrayList<BulkMstrDTO>();
		int mstrTypeSelected=Integer.parseInt(mstrType);
		BulkMsgDto bulkMsgDto=new BulkMsgDto();
		boolean isLarge = false;
		

		File newFile = null;
		try
		{
			byte[] fileData =myfile.getFileData();
			 String path= PropertyReader.getAppValue("path.uploadedTempFile")+ new java.util.Date().getTime()+"_"+myfile.getFileName() ;
			 newFile  = new File(path);
			 logger.info("path"+path);
			 
				RandomAccessFile raf = new RandomAccessFile(newFile, "rw");
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
			int totalrows = sheet.getLastRowNum()+1;

			if(totalrows > 10000)
			{
				// "Limit exceeds: Maximum"
				isLarge = true;
			}
			int rowNumber = -1;

			//added for handling large file and blank file
			
			if (isLarge) {
					
					bulkMsgDto.setMsgId(Constants.INVALID_FILESIZE);
					bulkMsgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.invalid.filesize"));
				    logger.info("File size exceeds.");
					return bulkMsgDto;
			}
			if (totalrows < 3) {
			logger.info("row empty");
				
				bulkMsgDto.setMsgId(Constants.BULK_UPLOAD_BLANK_EXCEL);
				bulkMsgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.blank.excel"));
				return bulkMsgDto;
			}
			
			while (rows.hasNext()) {

				Row row = (Row) rows.next();
				BulkMstrDTO bulkDto = new BulkMstrDTO();
				//logger.info("master type"+mstrTypeSelected);

				//logger.info(" row.getPhysicalNumberOfCells() "+ row.getPhysicalNumberOfCells() );
				//logger.info("totalrows::::::::::::::::::"+totalrows);
				rowNumber = row.getRowNum();

				if(rowNumber == 0)
				{
					if(mstrTypeSelected==1 || mstrTypeSelected==8)//zone || state
					{				
									if(row.getPhysicalNumberOfCells() != Constants.TOTAL_HEADERS_BULK_ZONE_CREATE) 
									{
										bulkMsgDto.setMessage("Invalid Excel");
										bulkMsgDto.setMsgId(Constants.BULK_UPLOAD_INVALID_EXCEL);
										logger.info("1 Invalid bulkMsgDto.getMessage()"+bulkMsgDto.getMessage());
										return bulkMsgDto;
									}
							}
					
					else if(mstrTypeSelected==2 || mstrTypeSelected==3 || mstrTypeSelected==4 || mstrTypeSelected==5 )
					{
									if(row.getPhysicalNumberOfCells() != Constants.TOTAL_HEADERS_BULK_CITY_CREATE)
									{	
										logger.info("row.getPhysicalNumberOfCells()::::::::"+row.getPhysicalNumberOfCells());
										logger.info("Invalid excel");
										bulkMsgDto.setMessage("Invalid Excel");
										bulkMsgDto.setMsgId(Constants.BULK_UPLOAD_INVALID_EXCEL);
										return bulkMsgDto;
									}
					}
					else if(mstrTypeSelected==6 )
					{
									if(row.getPhysicalNumberOfCells() != Constants.TOTAL_HEADERS_BULK_AUTO_ASSIGNMENT_MATRIX)
									{	
										logger.info("row.getPhysicalNumberOfCells()::::::::"+row.getPhysicalNumberOfCells());
										logger.info("Invalid excel");
										bulkMsgDto.setMessage("Invalid Excel");
										bulkMsgDto.setMsgId(Constants.BULK_UPLOAD_INVALID_EXCEL);
										return bulkMsgDto;
									}
					}
					//added By Bhaskar
					else if(mstrTypeSelected==7 )
					{
									if(row.getPhysicalNumberOfCells() != Constants.TOTAL_HEADERS_BULK_CHANNELpARTNER_CREATE)
									{	
										logger.info("row.getPhysicalNumberOfCells()::::::::"+row.getPhysicalNumberOfCells());
										logger.info("Invalid excel");
										bulkMsgDto.setMessage("Invalid Excel");
										bulkMsgDto.setMsgId(Constants.BULK_UPLOAD_INVALID_EXCEL);
										return bulkMsgDto;
									}
					}
					
					else if(mstrTypeSelected==9 )
					{
									if(row.getPhysicalNumberOfCells() != Constants.TOTAL_HEADERS_BULK_AGENCY_CREATE)
									{	
										logger.info("row.getPhysicalNumberOfCells()::::::::"+row.getPhysicalNumberOfCells());
										logger.info("Invalid excel");
										bulkMsgDto.setMessage("Invalid Excel");
										bulkMsgDto.setMsgId(Constants.BULK_UPLOAD_INVALID_EXCEL);
										return bulkMsgDto;
									}
					}
					else if(mstrTypeSelected==10 )
					{
									if(row.getPhysicalNumberOfCells() != Constants.TOTAL_HEADERS_BULK_WORKFLOW_ASSIGNMENT)
									{	
										logger.info("row.getPhysicalNumberOfCells()::::::::"+row.getPhysicalNumberOfCells());
										logger.info("Invalid excel");
										bulkMsgDto.setMessage("Invalid Excel");
										bulkMsgDto.setMsgId(Constants.BULK_UPLOAD_INVALID_EXCEL);
										return bulkMsgDto;
									}
					}
					//Added by srikant
					else if(mstrTypeSelected==11)
					{
						if(row.getPhysicalNumberOfCells() != Constants.TOTAL_HEADERS_BULK_CHANNEL_ID)
						{	
							logger.info("row.getPhysicalNumberOfCells()::::::::"+row.getPhysicalNumberOfCells());
							logger.info("Invalid excel");
							bulkMsgDto.setMessage("Invalid Excel");
							bulkMsgDto.setMsgId(Constants.BULK_UPLOAD_INVALID_EXCEL);
							return bulkMsgDto;
						}
					}
					
					else if(mstrTypeSelected==15)
					{
						if(row.getPhysicalNumberOfCells() != Constants.TOTAL_HEADERS_BULK_CAPABILITY_UPLOAD)
						{	
							logger.info("row.getPhysicalNumberOfCells()::::::::"+row.getPhysicalNumberOfCells());
							logger.info("Invalid excel");
							bulkMsgDto.setMessage("Invalid Excel");
							bulkMsgDto.setMsgId(Constants.BULK_UPLOAD_INVALID_EXCEL);
							return bulkMsgDto;
						}
					}
					
					else if(mstrTypeSelected==12)
					{
						if(row.getPhysicalNumberOfCells() != Constants.TOTAL_HEADERS_ESCALATION_MATRIX)
						{	
							logger.info("******************************"+row.getPhysicalNumberOfCells());
							logger.info("row.getPhysicalNumberOfCells()::::::::"+row.getPhysicalNumberOfCells());
							logger.info("Invalid excel");
							bulkMsgDto.setMessage("Invalid Excel");
							bulkMsgDto.setMsgId(Constants.BULK_UPLOAD_INVALID_EXCEL);
							return bulkMsgDto;
						}
					}
					
					
						/*end of changes by Nancy*/
					//else
						if(totalrows == 2)
			        	  {
							logger.info("Blank excel::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::asa");
								bulkMsgDto.setMessage("Blank Excel");
								bulkMsgDto.setMsgId(Constants.BULK_UPLOAD_BLANK_EXCEL);
								return bulkMsgDto;
			        	  }	
					else
						continue;
				}	
				

				if(rowNumber > 1)    //Starting parsing excel after 2nd row
				{
					Iterator cells = row.cellIterator();

					int columnIndex = 0;
					int cellNo = 0;

					if(cells != null)
					{
						//BulkZoneCreationDTO bulkDto = new BulkZoneCreationDTO();

						while (cells.hasNext()) {
							cellNo++;
							Cell cell = (Cell) cells.next();
							columnIndex = cell.getColumnIndex();

							String cellValue = null;
							switch(cell.getCellType()) {
							case Cell.CELL_TYPE_NUMERIC:
								cellValue = String.valueOf((long)cell.getNumericCellValue());
								break;
							case Cell.CELL_TYPE_STRING:
								cellValue = cell.getStringCellValue();
								break;
							}
							if(cellValue != null)
							{
								cellValue = cellValue.trim();
							}
							else
								cellValue = "";

							// mstrType Check 

							if(mstrTypeSelected==1)									//zone
							{
								switch(columnIndex) {
								case 0:
									bulkDto.setProductLobId(cellValue);
									break;
								case 1:
									bulkDto.setCircle(cellValue);
									break;
								case 2:
									bulkDto.setZoneName(cellValue);
									break;
								case 3:
									bulkDto.setActionType(cellValue);
									break;
								}
							}
							else if(mstrTypeSelected==2)							//city
							{
								switch(columnIndex) {
								case 0:
									bulkDto.setZoneCode(cellValue);
									break;
								case 1:
									bulkDto.setCityName(cellValue);
									break;
								case 2:
									bulkDto.setActionType(cellValue);
									break;
								}
							}
							else if(mstrTypeSelected==3)							//cityZone
							{
								switch(columnIndex) {
								case 0:
									bulkDto.setCitycode(cellValue);
									break;
								case 1:
									bulkDto.setCityZoneName(cellValue);
									break;
								case 2:
									bulkDto.setActionType(cellValue);
									break;
								}
							}
							
							else if(mstrTypeSelected==4)							//pincode
							{
								switch(columnIndex) {
								case 0:
									bulkDto.setPinCode(cellValue);
									break;
								case 1:
									bulkDto.setCityZoneCode(cellValue);
									break;
								case 2:
									bulkDto.setActionType(cellValue);
									break;
								}
							}
							
							else if(mstrTypeSelected==5)							//RSU
							{
								switch(columnIndex) {
								case 0:
									bulkDto.setRsuCode(cellValue);
									break;
								case 1:
									bulkDto.setCityZoneCode(cellValue);
									break;
								case 2:
									bulkDto.setActionType(cellValue);
									break;
								}
							}
							
							
							else if(mstrTypeSelected==6)							//Auto Assignment Matrix
							{
								switch(columnIndex) {
								case 0:
									bulkDto.setProductLobId(cellValue);
									break;
								case 1:
									bulkDto.setProductId(cellValue);
									break;
								case 2:
									bulkDto.setCircle(cellValue);
									break;
							/*	case 3:
									bulkDto.setZoneCode(cellValue);
									break;*/
								case 3:
									bulkDto.setCitycode(cellValue);
									break;
								case 4:
									bulkDto.setCityZoneCode(cellValue);
									break;
								case 5:
									bulkDto.setPinCode(cellValue);
									break;
								case 6:
									logger.info("<<<<<<<<<<"+ cellValue +">>>>>>>>>>>>>");
									bulkDto.setReqCategory(cellValue);
									break;	
									
								case 7:
									bulkDto.setActionType(cellValue);
									break;
								}
							}
						
							//Channel partner Upload
							
							else if(mstrTypeSelected==7)						
							{
								switch(columnIndex) {
								case 0:
									bulkDto.setChannelPartnerId(cellValue);
									break;
								case 1:
									bulkDto.setMobileNumber(cellValue);
									break;
								case 2:
									bulkDto.setActionType(cellValue);
									break;
								}
							}
							
							//State Upload
							else if(mstrTypeSelected==8)						
							{
								switch(columnIndex) {
								case 0:
									bulkDto.setProductLobId(cellValue);
									break;
								case 1:
									bulkDto.setCircle(cellValue);
									break;
								
								case 2:
									bulkDto.setState(cellValue);
									break;
								case 3:
									bulkDto.setActionType(cellValue);
									break;
								}
							}
							//agency Assignment Matrix 
							//added by Nancy Agrawal
							else if(mstrTypeSelected==9)
							{
								switch(columnIndex)
								{
								case 0:
									bulkDto.setAgencyId(cellValue);
									
								case 1:
									bulkDto.setProductLobId(cellValue);
									break;
								case 2:
									bulkDto.setProductId(cellValue);
									break;
									
								case 3:
									bulkDto.setCircle(cellValue);
									break;
								
								case 4:
									bulkDto.setCitycode(cellValue);
									break;
								
								case 5:
									bulkDto.setZoneCode(cellValue);
									break;
								case 6:
									bulkDto.setPincodersu(cellValue);
									break;
								case 7:
									bulkDto.setCityZoneCode(cellValue);
									break;
								case 8:
									bulkDto.setStatus(cellValue);
									break;
								case 9:
									bulkDto.setChannelPartnerId(cellValue);
									break;
								}
							}
							else if(mstrTypeSelected==10)
							{
								switch(columnIndex)
								{
								case 0:
									bulkDto.setProductLobId(cellValue);
									break;
								case 1:
									bulkDto.setProductId(cellValue);
									break;
								case 2:
									bulkDto.setCircle(cellValue);
									break;
									
								case 3:
									bulkDto.setChannelPartnerId(cellValue);
									break;
								case 4:
									bulkDto.setCitycode(cellValue);
									break;
								case 5:
									bulkDto.setPinCode(cellValue);
									break;
								case 6:
									bulkDto.setRsuCode(cellValue);
									break;
								case 7:
									bulkDto.setReqCategory(cellValue);
									break;
								
								case 8:
									bulkDto.setActionType(cellValue);
									break;
								}
							}
							
							else if(mstrTypeSelected==11)
							{
								switch(columnIndex)
								{
								case 0:
									bulkDto.setOlmId(cellValue);
									break;
								case 1:
									bulkDto.setChannelCode(cellValue);
									break;
								case 2:
									bulkDto.setActionType(cellValue);
									break;
								}
							}
							
							else if(mstrTypeSelected==15)
							{
								switch(columnIndex)
								{
								case 0:
									bulkDto.setChannelPartnerId(cellValue);
									break;
								case 1:
									bulkDto.setThreshold(cellValue);
									break;
								case 2:
									bulkDto.setActionType(cellValue);
									break;
								}
							}
							
							else if(mstrTypeSelected==12)
							{
								switch(columnIndex)
								{
								case 0:
									bulkDto.setStage(cellValue);
									break;
								case 1:
									bulkDto.setLevelId1(cellValue);
									break;
								case 2:
									bulkDto.setPartnerId(cellValue);
									break;
								case 3 : 
									bulkDto.setLevelId2(cellValue);
									break; 
								}
							}
		
								}//Adding single row to a dto
						
						bulkDto.setRowNumber(rowNumber);
						bulkDto = validateDto(bulkDto, mstrTypeSelected); 
						listBulkDto.add(bulkDto);
						//inserting and deleting
						
					}

				}//Parsing single row


			}//Parsing all rows
			
			
			if(mstrTypeSelected==1) // Insert/Delete for Zone
			{
					listBulkDto=bulkMstrDao.uploadZone(listBulkDto,userBean); // ManageZone
			}
			
			if(mstrTypeSelected==2) // Insert / Delete for 
			{
				listBulkDto=bulkMstrDao.uploadCity(listBulkDto,userBean);
			}
			if(mstrTypeSelected==3)
			{
				listBulkDto=bulkMstrDao.uploadCityZone(listBulkDto,userBean);
			}
			if(mstrTypeSelected==4)
			{
				listBulkDto=bulkMstrDao.uploadPinCode(listBulkDto,userBean);
			}
			if(mstrTypeSelected==5)
			{
				listBulkDto=bulkMstrDao.uploadRSUCode(listBulkDto,userBean);
			}
			if(mstrTypeSelected==6)
			{
				listBulkDto=bulkMstrDao.uploadAutoAssignmentMatrix(listBulkDto,userBean);
			}
			if(mstrTypeSelected==7)
			{
				
				listBulkDto=bulkMstrDao.uploadChannelPartner(listBulkDto,userBean);
			}
			if(mstrTypeSelected==8)
			{
				
				listBulkDto=bulkMstrDao.uploadState(listBulkDto,userBean);
			}
			
			if(mstrTypeSelected==9)
			{
				listBulkDto=bulkMstrDao.uploadAgencyAssignmentMatrix(listBulkDto, userBean);
			}
			
			if(mstrTypeSelected==10)
			{
				listBulkDto=bulkMstrDao.uploadWorkFlowAutoAssignmentMatrix(listBulkDto, userBean);
			}
			//Added by srikant
			if(mstrTypeSelected==11)
			{
				listBulkDto=bulkMstrDao.uploadChannelCode(listBulkDto, userBean);
			}
			if(mstrTypeSelected==15)
			{
				listBulkDto=bulkMstrDao.uploadChannelPartnerCapability(listBulkDto, userBean);
			}
			if(mstrTypeSelected==12)
			{
				listBulkDto=bulkMstrDao.escalationUpload(listBulkDto, userBean);
			}
			SimpleDateFormat otdf = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
			logFilePath = logFilePath+ "bulkChannelcodelog" + otdf.format(new java.util.Date()) + ".csv" ;
			logger.info("logFilePath::::::"+logFilePath);

		
			bulkMsgDto = writeLogs(listBulkDto, logFilePath, mstrTypeSelected);
			
			bulkMsgDto.setMsgId(Constants.BULK_UPLOAD_SUCCESS);

		}
		
		catch (Exception e) {
			e.printStackTrace();
			bulkMsgDto.setMsgId(Constants.BULK_UPLOAD_FAIL);
			logger.info("Error occurred while reading the file");
			throw new LMSException("Exception Occurred in uploadZone");
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
		return bulkMsgDto;

	}//uploadZone

	/**
	 * @param bulkDto
	 * @param rowNumber
	 * @param errorList
	 * @return
	 * @throws LMSException
	 */
	public BulkMstrDTO validateDto(BulkMstrDTO bulkDto,int mstrType) throws LMSException 
	{
		StringBuffer errMsg= new StringBuffer("");
		//StringBuffer assignmentKey = new StringBuffer("");
		//String validSecOlmIdsStr = "";
		boolean errFlag = false,isNumeric=true;
		MasterService mstrService = new MasterServiceImpl();
		
		int circleId = -1,productLobId = -1;
		try
		{	
			
			if(mstrType!=12){
			if(!bulkDto.getActionType().equalsIgnoreCase("c"))
			{
				if(!bulkDto.getActionType().equalsIgnoreCase("d"))
				{
					bulkDto.setErrFlag(true);
					return bulkDto;
				}  

			}
			}
			

			if(mstrType==1)
			{
				if(bulkDto.getProductLobId().equals("") || bulkDto.getCircle().equals("") || bulkDto.getZoneName().equals(""))
				{
					bulkDto.setErrFlag(true);
					return bulkDto;
				}

				if(bulkDto.getProductLobId().length()< Constants.MAX_LENGTH)
				{
					try
					{
						productLobId = Integer.parseInt(bulkDto.getProductLobId());
						logger.info("productLobId:::::::"+productLobId);
					}
					catch(Exception e)
					{
						isNumeric = false;
						bulkDto.setErrFlag(true);
					}
				}
				else
				{
					bulkDto.setErrFlag(true);
				}
				if(bulkDto.getCircle().length()> Constants.MAX_LENGTH)
				{
					bulkDto.setErrFlag(true);
				}
				
				
			}
			
			else if(mstrType==2)
			{
				if(bulkDto.getZoneCode().equals("") || bulkDto.getCityName().equals(""))
				{
					//bulkDto.setMessage("ZONE CODE and CITY NAME  are mandatory fields |");
					bulkDto.setErrFlag(true);
					return bulkDto;
				}
				
			}
			else if(mstrType==3)
			{
				if(bulkDto.getCitycode().equals("") || bulkDto.getCityZoneName().equals(""))
				{
					//bulkDto.setMessage("CITY CODE and CITYZONE NAME  are mandatory fields |");
					bulkDto.setErrFlag(true);
					return bulkDto;
				}
			}
			else if(mstrType==4)
			{
				if(bulkDto.getPinCode().equals("") || bulkDto.getCityZoneCode().equals(""))
				{
					bulkDto.setErrFlag(true);
					return bulkDto;
				}
				if(bulkDto.getPinCode().length()>6)
				{
					logger.info("pin code length is 6");
					bulkDto.setErrFlag(true);
					return bulkDto;
				}
				
			}
			else if(mstrType==5)
			{
				if(bulkDto.getRsuCode().equals("") || bulkDto.getCityZoneCode().equals(""))
				{
					bulkDto.setErrFlag(true);
					return bulkDto;
				}
			}
			
			else if(mstrType==6)
			{
				if(bulkDto.getProductLobId().equals("") || bulkDto.getCircle().equals(""))
				
				{
					bulkDto.setErrFlag(true);
					return bulkDto;
				}
				
				if(bulkDto.getProductLobId().length()>5)
				{
					bulkDto.setErrFlag(true);
					return bulkDto;
				}
				if(bulkDto.getProductId().length()>5)
				{
					bulkDto.setErrFlag(true);
					return bulkDto;
				}
				if(bulkDto.getCircle().length()>20)
				{
					bulkDto.setErrFlag(true);
					return bulkDto;
				}
				if(bulkDto.getCitycode().length()>20)
				{
					bulkDto.setErrFlag(true);
					return bulkDto;
				}
				if(bulkDto.getCityZoneCode().length()>20)
				{
					bulkDto.setErrFlag(true);
					return bulkDto;
				}
				if(bulkDto.getPinCode().length()>6)
				{
					bulkDto.setErrFlag(true);
					return bulkDto;
				}
			}
			
			else

					if(mstrType==7)
				{
					if(bulkDto.getChannelPartnerId().equals("") ||bulkDto.getMobileNumber().equals("") )
					{
						bulkDto.setErrFlag(true);
						return bulkDto;
					}
				}
			
				else 
					if(mstrType==8)
					{
						if(bulkDto.getProductLobId().equals("") || bulkDto.getCircle().equals("") || bulkDto.getState().equals(""))
						{
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
						if(bulkDto.getProductLobId().length()< Constants.MAX_LENGTH)
						{
							try
							{
								productLobId = Integer.parseInt(bulkDto.getProductLobId());
								logger.info("productLobId:::::::"+productLobId);
							}
							catch(Exception e)
							{
								isNumeric = false;
								bulkDto.setErrFlag(true);
							}
						}
						else
						{
							bulkDto.setErrFlag(true);
						}
						if(bulkDto.getCircle().length()> Constants.MAX_LENGTH)
						{
							bulkDto.setErrFlag(true);
						}
					}
					else if(mstrType==9)//added by Nancy Agrawal
					{
						if(bulkDto.getAgencyId().equals("") || bulkDto.getProductLobId().equals("") || bulkDto.getCircle().equals("") || bulkDto.getStatus().equals(""))
						
						{
							//agency id and lob id and circle id and status cant be blank 
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
											
						if(bulkDto.getProductLobId().length()>5)
						{
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
						if(bulkDto.getProductId().length()>5)
						{
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
						if(bulkDto.getCircle().length()>20)
						{
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
						
						if(bulkDto.getCitycode().length()>20)
						{
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
						if(bulkDto.getCityZoneCode().length()>20)
						{
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
						if(!(bulkDto.equals("")) && !(bulkDto.getPincodersu().length()>3 && bulkDto.getPincodersu().length()<7))
						{
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
					}
			
					
					else if(mstrType==10)
					{
						
						if(bulkDto.getProductLobId().equals("") || bulkDto.getProductId().equals("") || bulkDto.getCircle().equals("") || bulkDto.getChannelPartnerId().equals("") || bulkDto.getActionType().equals(""))
						
						{
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
											
						if(bulkDto.getProductLobId().length()>5)
						{
							
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
						if(bulkDto.getProductId().length()>5)
						{
							
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
						if(bulkDto.getCircle().length()>20)
						{
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
						
						if(bulkDto.getChannelPartnerId().length()>10)
						{
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
						
						if(bulkDto.getCitycode()!=null && bulkDto.getCitycode().length()>20)
						{
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
						
						if(bulkDto.getReqCategory()!=null && bulkDto.getReqCategory().length()>50)
						{
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
						
					}
			
			
					else 
							if(mstrType==11)
					{
						bulkDto.setErrFlag(false);
						
						if(bulkDto.getOlmId().equals("") || bulkDto.getChannelCode().equals("") || bulkDto.getActionType().equals(""))
						
						{
							logger.info("condition 1");
							bulkDto.setErrFlag(true);
							logger.info("bulkDto.getErrFlag"+bulkDto.isErrFlag());
							return bulkDto;
						}
											
						if(bulkDto.getOlmId().length()>15)
						{
							
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
						if(bulkDto.getChannelCode().length()>15)
						{
							
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
						if(bulkDto.getActionType().length()>2)
						{
							bulkDto.setErrFlag(true);
							return bulkDto;
						}
						
					}
							else if(mstrType== 15)
								{
								
									if(bulkDto.getChannelPartnerId().equals("") ||  bulkDto.getActionType().equals("") || bulkDto.getThreshold().equals(""))
									{
										bulkDto.setErrFlag(true);
										return bulkDto;
									}
									
									if(Integer.parseInt(bulkDto.getThreshold())<=0)
									{
										bulkDto.setErrFlag(true);
										return bulkDto;
									}
								}
			
							else 
								if(mstrType==12)
						{
							bulkDto.setErrFlag(false);
							
							if(bulkDto.getStage().equals("")||bulkDto.getLevelId1().equals("") || bulkDto.getLevelId2().equals("") || bulkDto.getPartnerId().equals(""))
							
							{
								//logger.info("condition 1");
								bulkDto.setErrFlag(true);
								bulkDto.setMessage("No bank values in row allowed");
								return bulkDto;
							}
							if((bulkDto.getLevelId1().equalsIgnoreCase(bulkDto.getLevelId2()))||(bulkDto.getLevelId1().equalsIgnoreCase(bulkDto.getPartnerId()))||(bulkDto.getLevelId2().equalsIgnoreCase(bulkDto.getPartnerId()))){
								bulkDto.setErrFlag(true);
								bulkDto.setMessage("Level1 Id, level2_Id or Partner_Id can't have identical values");
								return bulkDto;
							}
														
						}
				
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occured while validating bulkDto");
			throw new LMSException("Exception occurred in validateDto");
		}

		return 	bulkDto;
	}//validateDto

	public BulkMsgDto writeLogs(ArrayList<BulkMstrDTO> msgListBulkDto,String errLogFileName,int mstrTypeSelected) throws LMSException
	{
		boolean isError = false;
		BulkMsgDto bulkMsgDto = new BulkMsgDto();

		try
		{
			if(mstrTypeSelected==1){
			
			bulkMsgDto.setFilePath(errLogFileName);
			if(msgListBulkDto.size() > 0)
			{
				FileWriter fw = new FileWriter(errLogFileName);
				PrintWriter pw = new PrintWriter(fw);
				BulkMstrDTO bulkErrDto;

				pw.print("Row No");
				pw.print(",");
				pw.print("Upload Status");
				pw.print(",");
				pw.print("Zone Name");
				pw.print(",");
				pw.println("Zone Code");
				Iterator iter = msgListBulkDto.iterator();

				while(iter.hasNext())
				{	
					
					bulkErrDto = (BulkMstrDTO) iter.next();
					pw.print(bulkErrDto.getRowNumber());
					pw.print(",");
					pw.print(bulkErrDto.getMessage());
					pw.print(",");
					pw.print(bulkErrDto.getZoneName().toUpperCase());
					pw.print(",");
					pw.print(bulkErrDto.getZoneCode());
					pw.println(",");
					
				}

				pw.flush(); //Flush the output to the file 
				pw.close(); 
				fw.close();
			}
			}
			else if(mstrTypeSelected==2){
				
				bulkMsgDto.setFilePath(errLogFileName);
				if(msgListBulkDto.size() > 0)
				{
					FileWriter fw = new FileWriter(errLogFileName);
					PrintWriter pw = new PrintWriter(fw);
					BulkMstrDTO bulkErrDto;

					pw.print("Row No");
					pw.print(",");
					pw.print("Upload Status");
					pw.print(",");
					pw.print("City Name");
					pw.print(",");
					pw.println("City Code");
					Iterator iter = msgListBulkDto.iterator();

					while(iter.hasNext())
					{
						bulkErrDto = (BulkMstrDTO) iter.next();
						pw.print(bulkErrDto.getRowNumber());
						pw.print(",");
						pw.print(bulkErrDto.getMessage());
						pw.print(","); //added
						pw.print(bulkErrDto.getCityName().toUpperCase());
						pw.print(",");
						pw.println(bulkErrDto.getCitycode().toUpperCase());
						isError = true;
					}

					pw.flush(); //Flush the output to the file 
					pw.close(); 
					fw.close();
				}
				
			}
			else if(mstrTypeSelected==3){
				
				bulkMsgDto.setFilePath(errLogFileName);
				if(msgListBulkDto.size() > 0)
				{
					FileWriter fw = new FileWriter(errLogFileName);
					PrintWriter pw = new PrintWriter(fw);
					BulkMstrDTO bulkErrDto;

					pw.print("Row No");
					pw.print(",");
					pw.print("Upload Status");
					pw.print(",");
					pw.print("CityZone Name");
					pw.print(",");
					pw.println("CityZone Code");
					Iterator iter = msgListBulkDto.iterator();

					while(iter.hasNext())
					{
						bulkErrDto = (BulkMstrDTO) iter.next();
						pw.print(bulkErrDto.getRowNumber());
						pw.print(",");
						pw.print(bulkErrDto.getMessage());
						pw.print(","); //added
						pw.print(bulkErrDto.getCityZoneName().toUpperCase());
						pw.print(",");
						pw.println(bulkErrDto.getCityZoneCode().toUpperCase());
						isError = true;
					}

					pw.flush(); //Flush the output to the file 
					pw.close(); 
					fw.close();
				}	
				
			}
			
			
			else if(mstrTypeSelected==4){
							
							bulkMsgDto.setFilePath(errLogFileName);
							if(msgListBulkDto.size() > 0)
							{
								FileWriter fw = new FileWriter(errLogFileName);
								PrintWriter pw = new PrintWriter(fw);
								BulkMstrDTO bulkErrDto;
			
								pw.print("Row No");
								pw.print(",");
								pw.print("Upload Status");
								pw.print(",");
								pw.println("Pin Code");
								Iterator iter = msgListBulkDto.iterator();
			
								while(iter.hasNext())
								{
									bulkErrDto = (BulkMstrDTO) iter.next();
									pw.print(bulkErrDto.getRowNumber());
									pw.print(",");
									pw.print(bulkErrDto.getMessage());
									pw.print(","); //added
									pw.println(bulkErrDto.getPinCode());
									isError = true;
								}
			
								pw.flush(); //Flush the output to the file 
								pw.close(); 
								fw.close();
							}	
								
						}
							
				else if(mstrTypeSelected==5){
					
					bulkMsgDto.setFilePath(errLogFileName);
					if(msgListBulkDto.size() > 0)
					{
						FileWriter fw = new FileWriter(errLogFileName);
						PrintWriter pw = new PrintWriter(fw);
						BulkMstrDTO bulkErrDto;
				
						pw.print("Row No");
						pw.print(",");
						pw.print("Upload Status");
						pw.print(",");
						pw.println("RSU Code");
						Iterator iter = msgListBulkDto.iterator();
				
						while(iter.hasNext())
						{
							bulkErrDto = (BulkMstrDTO) iter.next();
							pw.print(bulkErrDto.getRowNumber());
							pw.print(",");
							pw.print(bulkErrDto.getMessage());
							pw.print(","); //added
							pw.println(bulkErrDto.getRsuCode().toUpperCase());
							isError = true;
						}
				
						pw.flush(); //Flush the output to the file 
						pw.close(); 
						fw.close();
					}	
					
				}
			
				else if(mstrTypeSelected==6){
					
					bulkMsgDto.setFilePath(errLogFileName);
					if(msgListBulkDto.size() > 0)
					{
						FileWriter fw = new FileWriter(errLogFileName);
						PrintWriter pw = new PrintWriter(fw);
						BulkMstrDTO bulkErrDto;
				
						pw.print("Row No");
						pw.print(",");
						pw.println("Upload Status");
						//pw.print(",");
						//pw.println("RSU Code");
						Iterator iter = msgListBulkDto.iterator();
				
						while(iter.hasNext())
						{
							bulkErrDto = (BulkMstrDTO) iter.next();
							pw.print(bulkErrDto.getRowNumber());
							pw.print(",");
							pw.println(bulkErrDto.getMessage());
							//pw.print(","); //added
							//pw.println(bulkErrDto.getRsuCode().toUpperCase());
							isError = true;
						}
				
						pw.flush(); //Flush the output to the file 
						pw.close(); 
						fw.close();
					}	
					
				}
				else if(mstrTypeSelected==7){
				
				bulkMsgDto.setFilePath(errLogFileName);
				if(msgListBulkDto.size() > 0)
				{
					FileWriter fw = new FileWriter(errLogFileName);
					PrintWriter pw = new PrintWriter(fw);
					BulkMstrDTO bulkErrDto;

					pw.print("Row No");
					pw.print(",");
					pw.println("Upload Status");
					//pw.print(",");
					//pw.print("Channel Partner");
					//pw.print(",");
					//pw.println("Mobile Number");
					Iterator iter = msgListBulkDto.iterator();

					while(iter.hasNext())
					{	
						
						bulkErrDto = (BulkMstrDTO) iter.next();
						pw.print(bulkErrDto.getRowNumber());
						pw.print(",");
						pw.println(bulkErrDto.getMessage());
						//pw.print(",");
						//pw.print(bulkErrDto.getChannelPartnerId());
						//pw.print(",");
						//pw.print(bulkErrDto.getMobileNumber());
						//pw.println(",");
						
					}

					pw.flush(); //Flush the output to the file 
					pw.close(); 
					fw.close();
				}
				}
			
			if(mstrTypeSelected==8){
				
				bulkMsgDto.setFilePath(errLogFileName);
				if(msgListBulkDto.size() > 0)
				{
					FileWriter fw = new FileWriter(errLogFileName);
					PrintWriter pw = new PrintWriter(fw);
					BulkMstrDTO bulkErrDto;

					pw.print("Row No");
					pw.print(",");
					pw.print("Upload Status");
					pw.print(",");
					pw.print("State Name");
					pw.print(",");
					pw.println("State Code");
					Iterator iter = msgListBulkDto.iterator();

					while(iter.hasNext())
					{	
						
						bulkErrDto = (BulkMstrDTO) iter.next();
						pw.print(bulkErrDto.getRowNumber());
						pw.print(",");
						pw.print(bulkErrDto.getMessage());
						pw.print(",");
						pw.print(bulkErrDto.getState().toUpperCase());
						pw.print(",");
						pw.print(bulkErrDto.getStateCode());
						pw.println(",");
						
					}

					pw.flush(); //Flush the output to the file 
					pw.close(); 
					fw.close();
				}
				}
			//added by Nancy
             else if(mstrTypeSelected==9){
				
				bulkMsgDto.setFilePath(errLogFileName);
				if(msgListBulkDto.size() > 0)
				{
					FileWriter fw = new FileWriter(errLogFileName);
					PrintWriter pw = new PrintWriter(fw);
					BulkMstrDTO bulkErrDto;
			
					pw.print("Row No");
					pw.print(",");
					pw.println("Upload Status");
					Iterator iter = msgListBulkDto.iterator();
			
					while(iter.hasNext())
					{
						bulkErrDto = (BulkMstrDTO) iter.next();
						pw.print(bulkErrDto.getRowNumber());
						pw.print(",");
						pw.println(bulkErrDto.getMessage());
						isError = true;
					}
			
					pw.flush();  
					pw.close(); 
					fw.close();
				}	
				
			}
			
             else if(mstrTypeSelected==10){
 				
 				bulkMsgDto.setFilePath(errLogFileName);
 				if(msgListBulkDto.size() > 0)
 				{
 					FileWriter fw = new FileWriter(errLogFileName);
 					PrintWriter pw = new PrintWriter(fw);
 					BulkMstrDTO bulkErrDto;
 			
 					pw.print("Row No");
 					pw.print(",");
 					pw.println("Upload Status");
 					Iterator iter = msgListBulkDto.iterator();
 			
 					while(iter.hasNext())
 					{
 						bulkErrDto = (BulkMstrDTO) iter.next();
 						pw.print(bulkErrDto.getRowNumber());
 						pw.print(",");
 						pw.println(bulkErrDto.getMessage());
 						isError = true;
 					}
 			
 					pw.flush();  
 					pw.close(); 
 					fw.close();
 				}	
 				
 			}
			
			
			//Added by srikant
			
             else if(mstrTypeSelected==11){
  				
  				bulkMsgDto.setFilePath(errLogFileName);
  				if(msgListBulkDto.size() > 0)
  				{
  					FileWriter fw = new FileWriter(errLogFileName);
  					PrintWriter pw = new PrintWriter(fw);
  					BulkMstrDTO bulkErrDto;
  			
  					pw.print("Row No");
  					pw.print(",");
  					pw.println("Upload Status");
  					Iterator iter = msgListBulkDto.iterator();
  			
  					while(iter.hasNext())
  					{
  						bulkErrDto = (BulkMstrDTO) iter.next();
  						pw.print(bulkErrDto.getRowNumber());
  						pw.print(",");
  						pw.println(bulkErrDto.getMessage());
  						isError = true;
  					}
  			
  					pw.flush();  
  					pw.close(); 
  					fw.close();
  				}
  				else{
  					
  					FileWriter fw = new FileWriter(errLogFileName);
  					PrintWriter pw = new PrintWriter(fw);
  					BulkMstrDTO bulkErrDto;
  			
  					pw.print("Row No");
  					pw.print(",");
  					pw.println("Upload Status");
  					Iterator iter = msgListBulkDto.iterator();
  					
  					while(iter.hasNext())
  					{
  						bulkErrDto = (BulkMstrDTO) iter.next();
  						pw.print(bulkErrDto.getRowNumber());
  						pw.print(",");
  						pw.println(bulkErrDto.getMessage());
  						isError = true;
  					}
  			
  					pw.flush();  
  					pw.close(); 
  					fw.close();
  					
  				}
  				
  			}
			
             else if(mstrTypeSelected==15){
   				
   				bulkMsgDto.setFilePath(errLogFileName);
   				if(msgListBulkDto.size() > 0)
   				{
   					FileWriter fw = new FileWriter(errLogFileName);
   					PrintWriter pw = new PrintWriter(fw);
   					BulkMstrDTO bulkErrDto;
   			
   					pw.print("Row No");
   					pw.print(",");
   					pw.println("Upload Status");
   					Iterator iter = msgListBulkDto.iterator();
   			
   					while(iter.hasNext())
   					{
   						bulkErrDto = (BulkMstrDTO) iter.next();
   						pw.print(bulkErrDto.getRowNumber());
   						pw.print(",");
   						pw.println(bulkErrDto.getMessage());
   						isError = true;
   					}
   			
   					pw.flush();  
   					pw.close(); 
   					fw.close();
   				}
   				else{
   					
   					FileWriter fw = new FileWriter(errLogFileName);
   					PrintWriter pw = new PrintWriter(fw);
   					BulkMstrDTO bulkErrDto;
   			
   					pw.print("Row No");
   					pw.print(",");
   					pw.println("Upload Status");
   					Iterator iter = msgListBulkDto.iterator();
   					
   					while(iter.hasNext())
   					{
   						bulkErrDto = (BulkMstrDTO) iter.next();
   						pw.print(bulkErrDto.getRowNumber());
   						pw.print(",");
   						pw.println(bulkErrDto.getMessage());
   						isError = true;
   					}
   			
   					pw.flush();  
   					pw.close(); 
   					fw.close();
   					
   				}
   				
   			}
			
             else if(mstrTypeSelected==12){
   				
   				bulkMsgDto.setFilePath(errLogFileName);
   				if(msgListBulkDto.size() > 0)
   				{
   					FileWriter fw = new FileWriter(errLogFileName);
   					PrintWriter pw = new PrintWriter(fw);
   					BulkMstrDTO bulkErrDto;
   			
   					pw.print("Row No");
   					pw.print(",");
   					pw.println("Upload Status");
   					Iterator iter = msgListBulkDto.iterator();
   			
   					while(iter.hasNext())
   					{
   						bulkErrDto = (BulkMstrDTO) iter.next();
   						pw.print(bulkErrDto.getRowNumber());
   						pw.print(",");
   						pw.println(bulkErrDto.getMessage());
   						isError = true;
   					}
   			
   					pw.flush();  
   					pw.close(); 
   					fw.close();
   				}
   				else{
   					
   					FileWriter fw = new FileWriter(errLogFileName);
   					PrintWriter pw = new PrintWriter(fw);
   					BulkMstrDTO bulkErrDto;
   			
   					pw.print("Row No");
   					pw.print(",");
   					pw.println("Upload Status");
   					Iterator iter = msgListBulkDto.iterator();
   					
   					while(iter.hasNext())
   					{
   						bulkErrDto = (BulkMstrDTO) iter.next();
   						pw.print(bulkErrDto.getRowNumber());
   						pw.print(",");
   						pw.println(bulkErrDto.getMessage());
   						isError = true;
   					}
   			
   					pw.flush();  
   					pw.close(); 
   					fw.close();
   					
   				}
   				
   			}
			
             
		}
		catch(IOException io)
		{
			io.printStackTrace();
			bulkMsgDto.setMessage("Error while File creation");
			logger.info("IO Exception occurred while writing logs to csv");
			throw new LMSException("IO Exception occurred while writting logs to csv");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			bulkMsgDto.setMessage("Error while File creation");
			logger.info("Exception occurred while writing error logs to csv");
			throw new LMSException("Exception occurred while writing logs to csv");
		}

		return bulkMsgDto;

	}//writeLogs

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


}//class



