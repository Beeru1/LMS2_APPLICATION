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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;
import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.lms.common.Constants;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.BulkUploadErrorLogDto;
import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.BulkUserCreationService;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.LoginService;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.impl.MasterServiceImpl;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class BulkUserCreationServiceImpl implements BulkUserCreationService  
{
	private static final Logger logger;
	private ArrayList<UserMstr> validListInsert = new ArrayList<UserMstr>();
	private ArrayList<Integer> validRowNosList = new ArrayList<Integer>();
	//private HashMap<Integer,String> hm=new HashMap<Integer,String>(); 
	ArrayList<UserMstr> listBulkDto = new ArrayList<UserMstr>();
	//private int prevlobid;
	static 
	{
		logger = Logger.getLogger(BulkUserCreationServiceImpl.class);
	}

	public BulkUploadMsgDto uploadUsers(FormFile myfile,UserMstr userBean) throws LMSException {

		boolean isError = false;
		//String failedEmailId="";
		List<BulkUploadErrorLogDto> errorList = new ArrayList<BulkUploadErrorLogDto>();
		String logFilePath = PropertyReader.getAppValue("lms.bulk.upload.error.log"); 
		UserMstrService createUserService =  new UserMstrServiceImpl();//.userMstrServiceInstance();//changed by srikant new UserMstrServiceImpl();
		BulkUploadMsgDto msgDto = new BulkUploadMsgDto();
		UserMstr bulkDto =null;
		ArrayList validUserEmailSend = new ArrayList();
		String eMail = "";

		File newFile = null;
		try
		{
			byte[] fileData =myfile.getFileData();
			 String path= PropertyReader.getAppValue("path.uploadedTempFile")+ new java.util.Date().getTime()+"_"+myfile.getFileName() ;
			 newFile  = new File(path);
			 boolean isLarge = false;
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
			//logger.info("totalrows:::"+totalrows);
			int rowNumber = 1;
			if (isLarge)
			{
				
				msgDto.setMsgId(Constants.INVALID_FILESIZE);
				msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.invalid.filesize"));
				logger.info("File size exceeds.");
				return msgDto;
			}
			if (totalrows <= 2) {
				
				msgDto.setMsgId(Constants.INVALID_EXCEL);
				msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.invalid.excel"));
				return msgDto;
			}
			
			while (rows.hasNext()) {

				Row row = (Row) rows.next();
				rowNumber = row.getRowNum();
				//logger.info("rowNumber"+rowNumber);
				if(rowNumber == 0)
				{
					if(row.getPhysicalNumberOfCells() != Constants.TOTAL_HEADERS_BULK_USER_CREATION)
					{
						msgDto.setMsgId(Constants.INVALID_EXCEL);
						msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.invalid.excel"));
						return msgDto;
					}
					else
						if(totalrows == 2)
			        	  {
								//logger.info("totalrows:::"+totalrows);
								msgDto.setMsgId(Constants.BLANK_EXCEL);
								msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.blank.excel"));
								return msgDto;
			        	  }	
					else
					{
						continue;
					}
				}

				if(rowNumber > 1)    //Starting parsing excel after 2nd row
				{

					Iterator cells = row.cellIterator();

					int columnIndex = 0;
					int cellNo = 0;

					if(cells != null)
					{
						 bulkDto = new UserMstr();
						
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

				//USER_LOGIN_ID	LOB_ID	CIRCLE_ID	ZONE_CODE	CITY_ZONE_CODE	USER_FNAME	USER_MNAME	USER_LNAME	USER_ROLE	EMAIL_ID	MOBILE_NO	PARTNER	ZONE_FLAG
			
							
							switch(columnIndex) {
							case 0:
								bulkDto.setUserLoginId(cellValue);
								break;
							case 1:
								bulkDto.setLob(cellValue);
								break;	
							case 2:
								bulkDto.setCircleId(cellValue);
								break;
							case 3:
								bulkDto.setZoneCode(cellValue);
								break;
							case 4:
								bulkDto.setCityZoneCode(cellValue);
								break;
							case 5:
								bulkDto.setUserFname(cellValue);
								break;
							case 6:
								bulkDto.setUserMname(cellValue);
								break;
							case 7:
								bulkDto.setUserLname(cellValue);
								break;
							case 8:
								bulkDto.setKmActorId(cellValue);
								break;
							case 9:
								bulkDto.setUserEmailid(cellValue);
								break;
							case 10:
								bulkDto.setUserMobileNumber(cellValue);
								break;
							case 11:
								bulkDto.setPartner(cellValue);
								break;
							case 12:
								bulkDto.setZoneFlag(cellValue);
								break;
							}
							
							bulkDto.setStatus("A");
							//bulkDto.setUserPassword(PropertyReader.getAppValue("lms.bulk.user.password"));
							bulkDto.setCreatedBy(userBean.getUserId());
							

						}//Adding single row to a dto

						errorList = validateDto(bulkDto,rowNumber,errorList,userBean); 
						//createUserService.updateBulkUserCreation( bulkDto,listBulkDto);
						
						listBulkDto.add(bulkDto);
					}

				}//Parsing single row


			}//Parsing all rows
			
			SimpleDateFormat otdf = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
			logFilePath = logFilePath+ "bulkUserCreationLog" + otdf.format(new java.util.Date()) + ".csv" ;
			
			if(errorList.size()>0)
				isError = true;
			
		
			if(validListInsert.size() > 0)
			{
				validUserEmailSend = createUserService.insertBulkUserData(validListInsert);
				//logger.info("validListInsert::::::::"+validListInsert.size());
				
					for(int i=0;i<validRowNosList.size();i++)
					{
						BulkUploadErrorLogDto bulkErr = new BulkUploadErrorLogDto();
						bulkErr.setRowNum(validRowNosList.get(i));
						bulkErr.setErrMsg("User successfully created !!");
						errorList.add(bulkErr);
					}
				
			}  
			/*
			if(errorList.size()>0){
			Iterator<BulkUploadErrorLogDto> itr=errorList.iterator();
			while(itr.hasNext())
			{
				BulkUploadErrorLogDto dt=itr.next();
				
				if(dt.getErrMsg().contains("Different LOB for Existing User |"))
				{
					createUserService.updateBulkUserCreation( bulkDto,listBulkDto);
				}
			}
			}
			 */
			if(!isError)
			{
				msgDto.setMsgId(Constants.SERVICE_MSG_SUCCESS);
				msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.assignment.success"));
			}
			else
			{
				msgDto.setMsgId(Constants.SERVICE_MSG_FAIL);
				msgDto.setMessage(logFilePath);
				writeLogs(errorList,logFilePath);  //writing logs to the csv file
			}
			
			/* Added by Parnika for sending mail after generation of error logs */
			try
		      {
					for (Iterator<UserMstr> itr = validUserEmailSend.iterator();itr.hasNext();) 
					{	
						UserMstr user = (UserMstr) itr.next();	
						eMail = user.getUserEmailid();
						sendMail(eMail, user,"Sending Mail", "User Creation");
					}
				}
		     catch (Exception e) {
		    	  e.printStackTrace();
		    	  throw new LMSException("Exception occured while sending mail :  "+ e.getMessage(),e);
		      }
		      
		       /* End of changes by Parnika */

			/*if(!failedEmailId.equals(""))
			{
				BulkUploadErrorLogDto bulkErr = new BulkUploadErrorLogDto();
				bulkErr.setRowNum(0);
				bulkErr.setErrMsg("Email Sending failed to following email ids "+failedEmailId);
				errorList.add(bulkErr);
			}*/
			
			

		}
		catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred while reading the file");
			msgDto.setMsgId(Constants.SERVICE_MSG_ERROR);
			msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.error"));
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

	}//uploadUsers

	/**
	 * @param bulkDto
	 * @param rowNumber
	 * @param errorList
	 * @return
	 * @throws LMSException
	 */
	public List<BulkUploadErrorLogDto> validateDto(UserMstr bulkDto,int rowNumber, List<BulkUploadErrorLogDto> errorList,UserMstr userBean) throws LMSException 
	{
		StringBuffer errMsg= new StringBuffer("");
		boolean errFlag = false,isNumeric=true;
		MasterService mstrService = new MasterServiceImpl();
		UserMstrService createUserService = new UserMstrServiceImpl();//.userMstrServiceInstance();//changed by srikant new UserMstrServiceImpl();
		LoginService loginService = new LoginServiceImpl();//.loginServiceInstance(); //cahnged by srikant new LoginServiceImpl();
		int circleId = 0,actorId=0,productLobId=0;
		UserMstr userMstr=null;
		

		try
		{
			BulkUploadErrorLogDto bulkErr = new BulkUploadErrorLogDto();
			
			
//Start iterating the list (validation for user role starts here)
			
			//Iterator itr=listBulkDto.iterator();
			//while(itr.hasNext())
			//{
				//userMstr=(UserMstr)itr.next();
				//logger.info("bulk dto actor id"+bulkDto.getKmActorId());
				//logger.info("usermaster actor id'"+userMstr.getKmActorId());
				//if(bulkDto.getUserLoginId().equals(userMstr.getUserLoginId()))
				
			//}
			
			/* Added by Parnika for duplicate check for  on 14 Jan - 2014 */
			int lobId = -2;
			try{
				 lobId = Integer.parseInt(bulkDto.getLob());
			}
			catch(Exception e){
				lobId = -2;
			}
			
			if(lobId != -2){
				if(createUserService.checkDuplicateUserIdLob(bulkDto.getUserLoginId(), lobId))					
				{					
						errMsg.append("User Already Exists  |");
						errFlag = true;

				}
				else if(createUserService.checkActorForUserLogin(bulkDto.getUserLoginId(),bulkDto.getKmActorId()))							
				{
					errMsg.append("User is already created for different User Role |");
					errFlag = true;
				}
				else if (createUserService.checkDuplicateUserLogin(bulkDto.getUserLoginId()))
				{
					// Appended to identify that user already exists for some other lob
					errMsg.append("Different LOB for Existing User |");
					
					
				}

				}
			
			/* End of changes by Parnika */

			//single type validations
			
			if(bulkDto.getUserLoginId().equals("") || bulkDto.getLob().equals("") || bulkDto.getCircleId().equals("") ||bulkDto.getUserFname().equals("")|| bulkDto.getUserLname().equals("")||bulkDto.getKmActorId().equals("") || bulkDto.getUserEmailid().equals("") || bulkDto.getUserMobileNumber().equals(""))
			{
				errMsg.append("USER_LOGIN_ID: LOB_ID: CIRCLE_ID: F_NAME: L_NAME: USER_ROLE: EMAIL_ID AND MOBILE_NO are mandatory fields |");
				errFlag = true;
			}
			
			if(bulkDto.getCircleId().equals(""))
			{
				errMsg.append("Circle id should not be empty");
				errFlag = true;
				
			}
			
			isNumeric = true;
			try
			{
				productLobId = Integer.parseInt(bulkDto.getLob());
			}
			catch(Exception e)
			{
				isNumeric = false;
				errMsg.append("LOB_ID should be numeric |");
				errFlag = true;
			}

			if(isNumeric)
			{
				if(!mstrService.isValidProductLobId(productLobId))
				{
					errMsg.append("Invalid LOB_ID |");
					errFlag = true;
				}
			}
			
		
			//Added by suagndha for multi-Circle
				if(bulkDto.getKmActorId() != null && !(bulkDto.getKmActorId().trim().equalsIgnoreCase("")))
				{
						actorId = Integer.parseInt(bulkDto.getKmActorId());
						logger.info("Actor id"+actorId);
						
						if(actorId == Constants.Super_Admin_ID){
							
							errMsg.append("Super Admin Role is not  Created|");
							errFlag = true;
						}
						
						if (actorId==Constants.Circle_Coordinator_ID)//Actor Id 3
						{
					
							if(bulkDto.getCircleId().equals(""))
							{
								errMsg.append("CIRCLE_ID Is  mandatory fields for this User Group |");
								errFlag = true;
							}
							String circleListStr = bulkDto.getCircleId();
							logger.info("String arrray"+circleListStr);
							
							
							if (!mstrService.isValidBulkUserCircle_LOB(circleListStr, productLobId))
					      	{
								errMsg.append("Invalid CIRCLE_ID |");
								errFlag = true;
					      	}
							/*if (createUserService.checkDuplicateUserIdMultiCircle(bulkDto.getUserLoginId(),productLobId,circleListStr)) {
								errMsg.append("User_Id already exists for same LOB and same Circle |");
								errFlag = true;
					      	}*/
						}//multi-circle ends here
						else
						{
							if(bulkDto.getCircleId().equals(""))
							{
								errMsg.append("CIRCLE_ID IS  mandatory  |");
								errFlag = true;
							}
							try
							{
								circleId = Integer.parseInt(bulkDto.getCircleId());
							}
							catch(Exception e)
							{
								isNumeric = false;
								errMsg.append("CIRCLE_ID should be numeric |");
								errFlag = true;
							}
							if(isNumeric)
							{
								if(!mstrService.isValidCircle(circleId,productLobId))
								{
								errMsg.append("Invalid CIRCLE_ID |");
								errFlag = true;
								}
							}
						}//Single Circle 
						
						// Added by sugandha where Zone code is mandatory
					if(actorId==Constants.ZBM_ID|| actorId==Constants.ZSM_ID ||actorId==Constants.Zone_Coordinator_ID )
						{
							if(bulkDto.getZoneFlag().equals(""))
								{
									errMsg.append("ZONE_FLAG  is  mandatory field for this User Group |");
									errFlag = true;
								}
								String zoneFlag =bulkDto.getZoneFlag();
								logger.info("zoneFlag:::::::::::::::::"+  zoneFlag);
								if(!bulkDto.getZoneFlag().equalsIgnoreCase(Constants.ZONE_CODE_FLAG_VALUE))
									{
									if(!bulkDto.getZoneFlag().equalsIgnoreCase(Constants.CITY_ZONE_CODE_FLAG_VALUE))
										{
										errMsg.append("Invalid Zone_Flag value | ");
										errFlag = true;  
										}  
									}
								if(zoneFlag.equalsIgnoreCase(Constants.ZONE_CODE_FLAG_VALUE))
								{
									String zoneCode=bulkDto.getZoneCode();
									if(bulkDto.getZoneCode().equals(""))
									{
										errMsg.append("ZONE_CODE  IS REQUIRED AS ZONE_FLAG value= Z|");
										errFlag = true;
									}
									if (bulkDto.getZoneCode().length() > 20) 
									{
										errMsg.append("ZONE_CODE should not be of more than 20 characters |");
									} 
									if(!mstrService.isValidUserZoneCode(zoneCode, circleId, productLobId))
									{
										errMsg.append("Invalid ZONE_CODE |");
										errFlag = true;
									}//ends here for ZoneCode circle 
								}
								if(zoneFlag.equalsIgnoreCase(Constants.CITY_ZONE_CODE_FLAG_VALUE))
								{	
								String cityzoneCode=bulkDto.getCityZoneCode();
								logger.info("zone flag value"+cityzoneCode);
								
									if(bulkDto.getCityZoneCode().equals(""))
									{
										errMsg.append("CITY_ZONE_CODE  IS REQUIRED AS ZONE_FLAG value= CZ|");
										errFlag = true;
									}
									if (bulkDto.getCityZoneCode().length() > 20) 
									{
										errMsg.append("CITY_ZONE_CODE should not be of more than 20 characters |");
									}
									if(!mstrService.isValidCityZoneCodeNewOne(cityzoneCode,circleId,productLobId))
									{	
										errMsg.append("Invalid CITY_ZONE_CODE |");
										errFlag = true;
									}
								}
								
							}
					}		
				// Duplicate UserLoginId and Lob id Checks
			
				
				/*if (createUserService.checkDuplicateUserIdLob(bulkDto.getUserLoginId(),productLobId)) {
					
					logger.info("with in duplicate loop......");
					errMsg.append("User_Id already exists for same LOB |");
					
					errFlag = true;
					 }
				*/
			
			if("Y".equalsIgnoreCase(PropertyReader.getAppValue("doLdapValidation")) 
					&& !loginService.isValidOlmId(bulkDto.getUserLoginId()))
			 {
				errMsg.append("User Login Id should have active OLM Id |");
				errFlag = true;
					
			}    // LDAP validation finish
			
			else
			{
				String encpass="";
				String pwd = generatePassword(bulkDto.getUserLoginId());
				IEncryption encpwd = new Encryption();
			    encpass = encpwd.generateDigest(pwd);
			    bulkDto.setUserPassword(pwd);
				bulkDto.setUserPasswordEncrypted(encpass);
			}
			
			isNumeric = true;

			try
			{
				actorId = Integer.parseInt(bulkDto.getKmActorId());
			}
			catch(Exception e)
			{
				isNumeric = false;
				errMsg.append("USER_ROLE should be numeric |");
				errFlag = true;
			}

			if(isNumeric)
			{

				if(!mstrService.isValidActorId(actorId))
				{
					errMsg.append("Invalid USER_ROLE |");
					errFlag = true;

				}
				
			}
			if(!bulkDto.getUserMobileNumber().equals(""))
			{

				if(bulkDto.getUserMobileNumber().length() != 10)
				{
					errMsg.append("MOBILE_NO should be of 10 digits only |");
					errFlag = true;  
				}
				else
				{
					if(!Utility.validateNumber(bulkDto.getUserMobileNumber()))
					{
						errMsg.append("MOBILE_NO should be numeric only |");
						errFlag = true;  
					}
				}
				
			}
			

			if(!bulkDto.getUserFname().equals(""))
			{
				if(bulkDto.getUserFname().length() > 100)
				{
					errMsg.append("USER_FNAME should not be of more than 100 characters |");
					errFlag = true;  
				}


			}
			
			if(!bulkDto.getUserMname().equals(""))
			{
				if(bulkDto.getUserMname().length() > 100)
				{
					errMsg.append("USER_MNAME should not be of more than 100 characters |");
					errFlag = true;  
				}


			}
			
			if(!bulkDto.getUserLname().equals(""))
			{
				if(bulkDto.getUserLname().length() > 100)
				{
					errMsg.append("USER_LNAME should not be of more than 100 characters |");
					errFlag = true;  
				}

			}
			
			
			if(!bulkDto.getUserEmailid().equals(""))
			{
				if(!Utility.validateEmail(bulkDto.getUserEmailid()) )
				{
					errMsg.append("EMAIL_ID should be of proper format |");
					errFlag = true;  
					
				}
			
			}
			if(!bulkDto.getPartner().equals(""))
			{
				if(bulkDto.getPartner().length() > 50)
				{
					errMsg.append("PARTNER should not be of more than 50 characters |");
					errFlag = true;  
				}

			}
			

			
			// validation for Duplicate checking user Login Id and Lob Id
			
			/*if(bulkDto.getLob()!="")
			
			if(hm.get(Integer.parseInt(bulkDto.getLob())) != null && hm.get(Integer.parseInt(bulkDto.getLob())).split("#")[0].equals(bulkDto.getUserLoginId()))
			{
				
				errMsg.append("Same User Login id and Lob Id ");
				errFlag = true; 
				
			}
			else if(prevlobid!=0){
				
				if( hm.get(prevlobid).split("#")[0].equals(bulkDto.getUserLoginId()) && !bulkDto.getKmActorId().equals(hm.get(prevlobid).split("#")[1].toString()))
			{
				
				errMsg.append("User role is different |");
				errFlag = true; 
			}
				else 
				{					
					if(hm.get(prevlobid).split("#")[0].equals(bulkDto.getUserLoginId()) && bulkDto.getKmActorId().equals(hm.get(prevlobid).split("#")[1]))
					{
					
					createUserService.updateBulkUserCreation(bulkDto,bulkDto.getUserLoginId());
					errMsg.append("User role is same  |");
					errFlag = true; 
				}
				}
		}*/
		
			if(errFlag)
			{
				bulkErr.setRowNum(rowNumber);
				bulkErr.setErrMsg(errMsg.toString());
				errorList.add(bulkErr);
			}
			else
			{
			   //logger.info("333333333333333 :"+bulkDto.getLob()  +"="+bulkDto.getUserLoginId()+"#"+bulkDto.getKmActorId());
				//hm.put(Integer.parseInt(bulkDto.getLob()), bulkDto.getUserLoginId()+"#"+bulkDto.getKmActorId());
				//prevlobid=Integer.parseInt(bulkDto.getLob());
				validListInsert.add(bulkDto);
				validRowNosList.add(rowNumber);
			   
			}
		}
		catch (EncryptionException e) 
		{
			
			e.printStackTrace();
			throw new LMSException("Exception occurred in validateDto"+e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occured while validating bulkDto");
			throw new LMSException("Exception occurred in validateDto"+e.getMessage());
		}

		return errorList;
	}//validateDto

	public boolean writeLogs(List<BulkUploadErrorLogDto> errorList,String errLogFileName) throws LMSException
	{
		boolean isError = false;

		try
		{
			if(errorList.size() > 0)
			{
				FileWriter fw = new FileWriter(errLogFileName);
				PrintWriter pw = new PrintWriter(fw);
				BulkUploadErrorLogDto bulkErrDto;

				pw.print("Row No");
				pw.print(",");
				pw.println("Upload Status");

				Iterator iter = errorList.iterator();

				while(iter.hasNext())
				{
					bulkErrDto = (BulkUploadErrorLogDto) iter.next();
					pw.print(bulkErrDto.getRowNum()+1);
					pw.print(",");
					pw.println(bulkErrDto.getErrMsg());
					isError = true;
				}

				pw.flush(); //Flush the output to the file 
				pw.close(); 
				fw.close();
			
			}
			
		}
		catch(IOException io)
		{
			io.printStackTrace();
			logger.info("IO Exception occurred while writing logs to csv");
			throw new LMSException("IO Exception occurred while writting logs to csv");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("Exception occurred while writing logs to csv");
			throw new LMSException("Exception occurred while writing logs to csv");
		}

		return isError;

	}//writeErrorlogs
	
	
	
	
	public String generatePassword(String userLoginId)
	 {		 
		String specialChars="@#!";
		String lowerChars = "qwertyuipasdfghjklzxcvbnm";
		int alphabetIndex = (int)(Math.random()*23);			
		int specialCharsIndex = (int)(Math.random()*2);		
		StringBuilder strPassword = new StringBuilder();
		strPassword.append(lowerChars.toUpperCase().charAt(alphabetIndex)+""+ (11+(int)(Math.random()*80))+""+lowerChars.toUpperCase().charAt((int)(Math.random()*25))+""+((11+(int)(Math.random()*80))+7) +""+ specialChars.charAt(specialCharsIndex)+""+ lowerChars.charAt(alphabetIndex+1)); 
		return strPassword.toString().replace("0", "5");
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
	
	public void sendMail(String userEmail,
			UserMstr kmUserMstr, String master,
			String activity) throws Exception {

		StringBuffer sbMessage = new StringBuffer();

		String txtMessage = null;
		try {

		String strSubject = PropertyReader.getAppValue("lms.user.create.mail.subject");
		sbMessage.append("Hi \n\n");
		
		sbMessage.append(PropertyReader.getAppValue("lms.user.create.mail.body")+"\n\n");

		sbMessage.append("Login ID : "
				+ kmUserMstr.getUserLoginId()
				+ "\n");

		sbMessage.append("Password : "
				+ kmUserMstr.getUserPassword()
				+ "\n");

		sbMessage.append("\n\nRegards ");
		sbMessage.append("\nLMS Administrator ");
		sbMessage
		.append("\n\n/** This is an Auto generated email.Please do not reply to this.**/");
		txtMessage = sbMessage.toString();
		
		//logger.info("txtMessage : "+txtMessage);
		
		String strHost = PropertyReader.getAppValue("LOGIN.SMTP");
		String strFromEmail = PropertyReader.getAppValue("SENDER.EMAIL");
		//
		logger.info("Login SMTP:" + strHost + " Mail Id:" + strFromEmail);
		
			Properties prop = System.getProperties();
			prop.put("mail.smtp.host", strHost);
			Session ses = Session.getDefaultInstance(prop, null);
			MimeMessage msg = new MimeMessage(ses);
			msg.setFrom(new InternetAddress(strFromEmail));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					userEmail));
			msg.setSubject(strSubject);
			msg.setText(txtMessage);
			Transport.send(msg);

		} catch (Exception e) {
			e.printStackTrace();
			//HAVE TO ADD DELETE METHOD FOR ROLLBACK THIS PROCESS
			//loginService.deleteUser(kmUserMstrFormBean.getUserLoginId());
			logger.error("Exception occured in sendMail():" + e.getMessage());
			throw new Exception(e.getMessage());

		}
	}

 
}//class


