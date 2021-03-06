package com.ibm.lms.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ibm.lms.dao.BulkAssigmentDao;
import com.ibm.lms.dao.MasterDao;
import com.ibm.lms.dao.impl.BulkAssigmentDaoImpl;
import com.ibm.lms.dao.impl.MasterDaoImpl;
import com.ibm.lms.dto.BulkAssignmentDto;
import com.ibm.lms.dto.BulkCityDTO;
import com.ibm.lms.dto.BulkCityZoneCodeCTO;
import com.ibm.lms.dto.BulkPinCodeDTO;
import com.ibm.lms.dto.BulkRsuDTO;
import com.ibm.lms.dto.BulkUploadErrorLogDto;
import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.dto.CityDTO;
import com.ibm.lms.dto.CityZoneDTO;
import com.ibm.lms.dto.LeadStatusDTO;
import com.ibm.lms.dto.PINCodeDTO;
import com.ibm.lms.dto.ProductLobDTO;
import com.ibm.lms.dto.RSUDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.dto.ZoneDTO;
import com.ibm.lms.engine.AsyncTaskManager;
import com.ibm.lms.engine.LMSHandler;
import com.ibm.lms.engine.handlers.EmailSMSHandler;
import com.ibm.lms.engine.handlers.UpdateLeadDataHandler;
import com.ibm.lms.engine.helper.InitializeLMSConfig;
import com.ibm.lms.engine.util.ServerPropertyReader;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.BulkAssigmentService;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.wf.forms.AssignmentMatrixFormBean;
import com.ibm.lms.common.CommonMstrUtil;
import com.ibm.lms.common.Constants;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.common.SendMail;
import com.ibm.ws.sib.msgstore.transactions.MSTransactionFactory;
import com.ibm.xtq.ast.nodes.ValueOf;

public class BulkAssigmentServiceImpl implements BulkAssigmentService  {

	private static final Logger logger;
	private ArrayList<BulkAssignmentDto> validListInsert = new ArrayList<BulkAssignmentDto>();
	private ArrayList<BulkAssignmentDto> validListDelete = new ArrayList<BulkAssignmentDto>();
	private ArrayList<Integer> validInsertRowNosList = new ArrayList<Integer>();
	private ArrayList<Integer> validDeleteRowNosList = new ArrayList<Integer>();
	private List<String> list = new ArrayList<String>();
	BulkUploadErrorLogDto bulkErr = new BulkUploadErrorLogDto();
	ArrayList<String> addlist= new ArrayList<String>();
	String keyFormes="";
	int  i= 0;
	static {
		logger = Logger.getLogger(BulkAssigmentServiceImpl.class);
	}

	public BulkUploadMsgDto uploadAssignmentMatrix(FormFile myfile,UserMstr userBean) throws LMSException {

		boolean isError = false;
		List<BulkUploadErrorLogDto> errorList = new ArrayList<BulkUploadErrorLogDto>();
		List<BulkUploadErrorLogDto> successList = new ArrayList<BulkUploadErrorLogDto>();
		String logFilePath = PropertyReader.getAppValue("lms.bulk.upload.error.log"); 
		BulkAssigmentDao bulkAssignmentDao = BulkAssigmentDaoImpl.bulkAssigmentDaoInstance();//chnaged by srikant new BulkAssigmentDaoImpl();
		BulkUploadMsgDto msgDto = new BulkUploadMsgDto();
		boolean isLarge = false;
		File newFile = null;
		boolean flagfile=false;
		MasterService mstrService = new MasterServiceImpl();
		try
		{
			//pending approvals if found, do nothing
			/*String countpending = CommonMstrUtil.getPendingapprovalsCount();
		    if((Integer.parseInt(countpending.split(",")[0])>0) ||(Integer.parseInt(countpending.split(",")[1])>0))
		    {   flagfile=true;
		    	msgDto.setMsgId(Constants.SERVICE_PENDING_ERROR);
				msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.ASSIGNMENT.UPLOAD.error"));
				return msgDto;	
		    }*/
		    
			byte[] fileData =myfile.getFileData();
			 String path= PropertyReader.getAppValue("path.uploadedTempFile")+ new java.util.Date().getTime()+"_"+myfile.getFileName() ;
			 newFile  = new File(path);
			 
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

			int rowNumber = 1;
	
			//logger.info(totalrows +" is totalrows");
			if (isLarge) {
				
				msgDto.setMsgId(Constants.INVALID_FILESIZE);
				msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.invalid.filesize"));
				logger.info("File size exceeds.");
				return msgDto;
			}
		if (totalrows < 3) {
			
				logger.info("row empty");
				
				msgDto.setMsgId(Constants.BLANK_EXCEL);
				msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.blank.excel"));
				return msgDto;
			}
		

			while (rows.hasNext()) {

	
				Row row = (Row) rows.next();
				logger.info(" row.getPhysicalNumberOfCells() "+ row.getPhysicalNumberOfCells() );
				rowNumber = row.getRowNum();

				if(rowNumber == 0)
				{
					if(row.getPhysicalNumberOfCells() != Constants.TOTAL_HEADERS_BULK_ASSIGNMENT_NEW)
					{
						msgDto.setMsgId(Constants.INVALID_EXCEL);
						msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.invalid.excel"));
						return msgDto;
					}
					else
						if(totalrows <=2)
			        	  {
								msgDto.setMsgId(Constants.BLANK_EXCEL);
								msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.blank.excel"));
								return msgDto;
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
						BulkAssignmentDto bulkDto = new BulkAssignmentDto();

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

							switch(columnIndex) {
							
							case 0:
								bulkDto.setProductLobId(cellValue);
								break;
							case 1:
								bulkDto.setProductId(cellValue);
								break;
							case 2:
								bulkDto.setRequestCategoryId(cellValue);
								break;
							case 3:
								bulkDto.setOlmId(cellValue);
								break;
							case 4:
								bulkDto.setCircle(cellValue);
								break;	
							case 5:
								bulkDto.setCityZone(cellValue);
								break;	
							case 6:
								bulkDto.setCity(cellValue);
								break;
							case 7:
								bulkDto.setCityZoneCode(cellValue);
								break;
							case 8:
								bulkDto.setPincode(cellValue);
								break;
							case 9:
								bulkDto.setRsu(cellValue);
								break;
							
							case 10:
								bulkDto.setSecondaryOlmId(cellValue);
								break;
							case 11:
								bulkDto.setUserType(cellValue);
								break;
							case 12:
								bulkDto.setActionType(cellValue);
								break;
							case 13:
								bulkDto.setLevellCC(cellValue);
								break;
							case 14:
								bulkDto.setLevel2CC(cellValue);
								break;
							case 15:
								bulkDto.setLevel3CC(cellValue);
								break;
							case 16:
								bulkDto.setLevel4CC(cellValue);
								break;
								//bulkDto.setUploadType("uploadType");
							}
							bulkDto.setUploadType("BULK UPLOAD");
							}//Adding single row to a dto

						errorList = validateDto(bulkDto,rowNumber,errorList, userBean);
							}

				}//Parsing single row


			}//Parsing all rows

			//SimpleDateFormat otdf = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
			String date = sdf1.format(new java.util.Date().getTime());
			
			//logFilePath = logFilePath+ "bulkAssignmentLog" + otdf.format(new java.util.Date()) + ".csv" ;
			logFilePath = logFilePath+ "AssignmentMatrixBulkUpload" + date + ".csv" ;
			
			if(errorList.size() ==0 )
				isError = false;
			else
				isError = true;

			String Assignment_Approval_flag = mstrService.getParameterName(PropertyReader.getAppValue("Assignment.approval.activate.flag"));
			if(validListInsert.size() > 0)
			{
				String msg="";
				//changing required
				
				if("Y".equalsIgnoreCase(Assignment_Approval_flag))
				{
			    msg = bulkAssignmentDao.insertTempAssignment(validListInsert,userBean);	//added by nancy, insert into temporary table.
				}
				else{
				 msg = bulkAssignmentDao.insertAssignment(validListInsert,userBean,"main");//for old one
				}
			    //changing required
			    if(msg.equalsIgnoreCase("error"))
			    {
			    	msgDto.setMsgId(Constants.SERVICE_MSG_ERROR);
					msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.upload.error"));
					return msgDto;
			    }
			    else
			    {
			    	for(int i=0;i<validInsertRowNosList.size();i++)
					{
						BulkUploadErrorLogDto bulkErr = new BulkUploadErrorLogDto();
						bulkErr.setRowNum(validInsertRowNosList.get(i));
						bulkErr.setErrMsg(msg);
						errorList.add(bulkErr);
					}
			    	
			    }

			}	

			if(validListDelete.size() > 0)
			{
				String msg="";
				
				
					 msg = bulkAssignmentDao.softDeleteAssignmentNew(validListDelete,userBean); 
				
				//String msg = bulkAssignmentDao.softDeleteAssignmentNew(validListDelete,userBean);
				logger.info("deleted "+validListDelete.size());
				logger.info("error mesasage"+msg);
				
				if(msg.equalsIgnoreCase("error"))
			    {
			    	msgDto.setMsgId(Constants.SERVICE_MSG_ERROR);
					msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.assignment.error"));
					return msgDto;
			    }
				else
				{
					for(int i=0;i<validDeleteRowNosList.size();i++)	
					{
						logger.info("delete rows data"+validDeleteRowNosList.size());
						BulkUploadErrorLogDto bulkErr = new BulkUploadErrorLogDto();
						bulkErr.setRowNum(validDeleteRowNosList.get(i));
						logger.info("valid delete list"+validDeleteRowNosList.get(i));
						bulkErr.setErrMsg("Assignmnet matrix deleted successfully");
						errorList.add(bulkErr);
					}
					
				}

			}	



			if(errorList.size() > 0)
			{
				msgDto.setMsgId(Constants.SERVICE_MSG_FAIL);
				msgDto.setMessage(logFilePath);
				writeLogs(errorList,logFilePath,userBean);  //writing logs to the csv file
				//writeFile(errorList,logFilePath,userBean);
			}

			if(!isError)
			{
				msgDto.setMsgId(Constants.SERVICE_MSG_SUCCESS);
				msgDto.setMessage(PropertyReader.getAppValue("lms.bulk.assignment.success"));
			}
           
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred while reading the file");
			throw new LMSException("Exception Occurred in uploadAssignmentMatrix");
		}
		finally{
			
			try{
				BulkAssignmentDto bulkAssignmentDto = bulkAssignmentDao.logs(userBean, logFilePath);
				EmailSMSHandler  handler = new  EmailSMSHandler(bulkAssignmentDto);
				new AsyncTaskManager().processAssignmentData(handler);
				
				
				if(flagfile==false)
				{
			newFile.delete();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.info("Error while deleting the file");
			}
		}
		
		return msgDto;
		
         
	}//uploadAssignmentMatrix

	
	public List<BulkUploadErrorLogDto> validateDto(BulkAssignmentDto bulkDto,int rowNumber, List<BulkUploadErrorLogDto> errorList,UserMstr userBean) throws LMSException 
	{
		logger.info("inside validateDto..................................");
		StringBuffer errMsg= new StringBuffer("");
		StringBuffer assignmentKey = new StringBuffer("");
		String validSecOlmIdsStr = "";
		boolean errFlag = false,isNumeric=true,isValidSecOlmId=true;
		MasterService mstrService = new MasterServiceImpl();
		BulkAssigmentDao bulkAssignmentDao = BulkAssigmentDaoImpl.bulkAssigmentDaoInstance();
		int circleId = -1,productLobId = -1,productId=-1,requestCategoryId=-1;
		int actorId=0;
		ArrayList<BulkPinCodeDTO> pincodeList = null;
		BulkPinCodeDTO dto1=null;
		int lobId=0;
		ArrayList<ProductLobDTO> lobList=null;
		ProductLobDTO dto = null;
		
		ArrayList<BulkRsuDTO> rsuList = null;
		BulkRsuDTO dto2=null;
		
		ArrayList<BulkCityZoneCodeCTO> cityZoneCodeList = null;
		BulkCityZoneCodeCTO dto3=null;
		
		ArrayList<BulkCityDTO> cityList = null;
		BulkCityDTO dto4=null;
		//MasterService mstrservice = new MasterServiceImpl();
		try
		{
			String Assignment_Approval_flag = mstrService.getParameterName(PropertyReader.getAppValue("Assignment.approval.activate.flag"));
			
			actorId=Integer.parseInt(userBean.getKmActorId());
		
			logger.info("User Login Id......."+userBean.getUserLoginId());
			logger.info("Lobs List"+userBean.getLobList());
			
			BulkUploadErrorLogDto bulkErr = new BulkUploadErrorLogDto();

			if(!bulkDto.getActionType().equalsIgnoreCase("c"))
			{
				if(!bulkDto.getActionType().equalsIgnoreCase("d"))
				{
					errMsg.append("Enter either c or d for CREATION/DELETION | ");
					errFlag = true;  
				}  

			}

			if(bulkDto.getProductLobId().equals("") || bulkDto.getOlmId().equals("") || bulkDto.getCircle().equals("") || bulkDto.getUserType().equals(""))
			{
				errMsg.append("PRODUCT_LOB_ID: OLM_ID: CIRCLE_ID and USER_TYPE are mandatory fields |");
				errFlag = true;
			}

			
			if(actorId== Constants.Circle_Coordinator_ID)
			{
				logger.info("if loop");
				lobList=mstrService.getAssignProductLobList(userBean.getLobList());
				logger.info("lob id list"+lobList);
				
				Iterator itr=lobList.iterator();
				while(itr.hasNext())
				{
					dto=(ProductLobDTO)itr.next();
					lobId=dto.getProductLobID();
					
				}
				
				try
				{
					productLobId = Integer.parseInt(bulkDto.getProductLobId()); 
					logger.info("xl sheet lob id"+productLobId);
				}
				catch(Exception e)
				{
					isNumeric = false;
					errMsg.append("PRODUCT_LOB_ID should be numeric |");
					errFlag = true;

				}
				if(lobId == productLobId )
				{
					logger.info("lob id is equal");
					if(isNumeric)
					{
						
						if(!mstrService.isValidProductLobId(productLobId))
						{
							errMsg.append("Invalid PRODUCT_LOB_ID |");
							errFlag = true;

						}
						else
							assignmentKey.append(productLobId).append("~");
					}
					
					try
					{
						circleId = Integer.parseInt(bulkDto.getCircle());
						//logger.info("circle id............"+circleId);
					}
					catch(Exception e)
					{
						isNumeric = false;
						errMsg.append("CIRCLE_ID should be numeric |");
						errFlag = true;

					}

					if(isNumeric)
					{
						

						if(!mstrService.isValidCircleIdNew(circleId,productLobId,userBean.getUserLoginId()))
						{
							
							errMsg.append("Invalid CIRCLE_ID |");
							errFlag = true;

						}
						else
							assignmentKey.append(circleId).append("~");
					
				}
				}
				}
			
			else{
					//logger.info("else loop");
			
			try
			{
				productLobId = Integer.parseInt(bulkDto.getProductLobId()); 
			}
			catch(Exception e)
			{
				isNumeric = false;
				errMsg.append("PRODUCT_LOB_ID should be numeric |");
				errFlag = true;

			}

			if(isNumeric)
			{
				if(!mstrService.isValidProductLobId(productLobId))
				{
					errMsg.append("Invalid PRODUCT_LOB_ID |");
					errFlag = true;

				}
				else
					assignmentKey.append(productLobId).append("~");
			}
			
			
			
			isNumeric = true;

			try
			{
				circleId = Integer.parseInt(bulkDto.getCircle());
			}
			catch(Exception e)
			{
				isNumeric = false;
				errMsg.append("CIRCLE_ID should be numeric |");
				errFlag = true;

			}

			if(isNumeric)
			{

				if(!mstrService.isValidCircleId(circleId,productLobId))
				{
					errMsg.append("Invalid CIRCLE_ID |");
					errFlag = true;

				}
				else
					assignmentKey.append(circleId).append("~");
			}
			}
				
			//validation for UserLogin Id
			
			if(!mstrService.isValidUserLoginId(bulkDto.getOlmId()))
			{
				errMsg.append("OLM_ID not available in iLMS user master |");
				errFlag = true;

			}

			//userType Validation
			
			if(!bulkDto.getUserType().equals(""))
			{
				if(bulkDto.getUserType().length() > 10)
				{
					errMsg.append("USER_TYPE should not be of more than 10 characters |");
					errFlag = true;  
				}
				else
				{
					if(!bulkDto.getUserType().equalsIgnoreCase("normal"))
					{
						if(!bulkDto.getUserType().equalsIgnoreCase("gis"))
						{
							errMsg.append("USER_TYPE should be either normal or gis | ");
							errFlag = true;  
						}  

					}
					if(bulkDto.getUserType().equalsIgnoreCase("normal"))
					{
						bulkDto.setUserType("normal");
					}
					if(bulkDto.getUserType().equalsIgnoreCase("gis"))
					{
						bulkDto.setUserType("GIS");
					}
				}
			}	
			
			//product id validation
			
			logger.info("bulkDto.getProductId().equals() :"+bulkDto.getProductId());
			logger.info("bulkDto.getProductId().length :"+bulkDto.getProductId().length());
			
			if(bulkDto.getProductId().length()!=0){
			
			try
			{	
				logger.info("product id if block");
				productId = Integer.parseInt(bulkDto.getProductId());
			
			}
			catch(Exception e)
			{
				isNumeric = false;
				errMsg.append("PRODUCT_ID should be numeric |");
				errFlag = true;

			}

			if(isNumeric)
			{
			if(!mstrService.isValidProductid(productId,productLobId))
			{
				errMsg.append("Invalid PRODUCT_ID |");
				errFlag = true;
			}
			else{
				assignmentKey.append(productId).append("~");
			}
			}
		
			/*if((Constants.PRODUCT_LOB_ID == mstrService.getProductLobId(productId)) && bulkDto.getCity().equals("") && !bulkDto.getUserType().equalsIgnoreCase("gis")){
				
				errMsg.append("For Telemedia Products ; City Code is mandatory |");
				errFlag = true;
			}
			 */
			}
			else{
				bulkDto.setProductId("-1");
				assignmentKey.append("#").append("~");
			}
			// product Id Validation 
			
			
			// Single Zone Code Validation
			
			if(!bulkDto.getCityZone().equals(""))
			{
					if(!mstrService.isValidZoneId(bulkDto.getCityZone(),circleId,productLobId))
					{
					
						errMsg.append("Zone should be of input City |");
						errFlag = true;

					}
					else
					{
					
						assignmentKey.append(bulkDto.getCityZone()).append("~");
					}
			}
			else if (bulkDto.getCity().equals("") && bulkDto.getCityZoneCode().equals("") && bulkDto.getPincode().equals("") && bulkDto.getRsu().equals("")){
				assignmentKey.append("#").append("~");
			}
			
			// City Code Validation 
			
			if( bulkDto.getCityZone().equals("") && !bulkDto.getCity().equals(""))
			{
						logger.info("InCity Method ");
						cityList=mstrService.getAssignmentMatrixDataForCityCode(bulkDto.getCity(),circleId,productLobId );
						logger.info("cityZone code list"+cityList);
					
						
						Iterator itr=cityList.iterator();
						while(itr.hasNext())
						{
						dto4=(BulkCityDTO)itr.next();
						
						bulkDto.setCityZone(dto4.getZonecode());
						
						}
						if(!bulkDto.getCityZone().equals("")){
							assignmentKey.append(bulkDto.getCityZone() + "~" +bulkDto.getCity()).append("~");
						}
						
						
			}
			else if(!bulkDto.getCity().equals(""))
				{
					
						if(!mstrService.isValidCityId(bulkDto.getCity(),bulkDto.getCityZone()) )
						{
							errMsg.append("City should be of Zone code |");
							errFlag = true;

						}
						else
						{
							assignmentKey.append(bulkDto.getCity()).append("~");
						}
				}
		
			else if (bulkDto.getCityZoneCode().equals("") && bulkDto.getPincode().equals("") && bulkDto.getRsu().equals("")){
				assignmentKey.append("#").append("~");
			}
				
			
			// cityZone code validation
			
			if( bulkDto.getCityZone().equals("") && bulkDto.getCity().equals("") && !bulkDto.getCityZoneCode().equals(""))
			{
				logger.info("InCity Zone Method ");
						cityZoneCodeList=mstrService.getAssignmentMatrixDataForCityZoneCode(bulkDto.getCityZoneCode(),circleId,productLobId );
						logger.info("cityZone code list"+cityZoneCodeList);
						
						
						Iterator itr=cityZoneCodeList.iterator();
						while(itr.hasNext())
						{
						dto3=(BulkCityZoneCodeCTO)itr.next();
						
						bulkDto.setCityZone(dto3.getZonecode());
						bulkDto.setCity(dto3.getCityCode());
						
						}
						if (!bulkDto.getCityZone().equals("") && !bulkDto.getCity().equals("")){
							assignmentKey.append(bulkDto.getCityZone() + "~" + bulkDto.getCity() + "~" + bulkDto.getCityZoneCode() + "~");	
						}
						
			}
		
			else if(!bulkDto.getCityZoneCode().equals(""))
			
			{
					if(!mstrService.isValidCityZoneCodeId(bulkDto.getCityZoneCode(),bulkDto.getCity()))
					{
						errMsg.append("City Zone Code should be of input City |");
						errFlag = true;

					}
					else
					{
						assignmentKey.append(bulkDto.getCityZoneCode()).append("~");
					}
				
			}
			
			else if (bulkDto.getPincode().equals("") && bulkDto.getRsu().equals("")){
				assignmentKey.append("#").append("~");
			}
			
			
			//rsu code validation 
			
			 if( bulkDto.getCityZoneCode().equals("") && bulkDto.getCityZone().equals("") && bulkDto.getCity().equals("") && !bulkDto.getRsu().equals(""))
				{
						logger.info("In Rsu Loop ");
						rsuList=mstrService.getAssignmentMatrixDataForRsuCode(bulkDto.getRsu(),circleId,productLobId );
						logger.info("rsu  list"+rsuList);
						
						
						Iterator itr=rsuList.iterator();
						while(itr.hasNext())
						{
						dto2=(BulkRsuDTO)itr.next();
						bulkDto.setCityZoneCode(dto2.getCityZoneCode());
						bulkDto.setCityZone(dto2.getZonecode());
						bulkDto.setCity(dto2.getCityCode());
						
						}
						if(!bulkDto.getCityZone().equals("") && !bulkDto.getCity().equals("") && !bulkDto.getCityZoneCode().equals("")){
							assignmentKey.append( bulkDto.getCityZone() + "~" + bulkDto.getCity() + "~" + bulkDto.getCityZoneCode() + "~" + bulkDto.getRsu() + "~");
						}
						
					}
			 else if(!bulkDto.getRsu().equals(""))
			{
				 logger.info("single rsu loop");
			
					if(!mstrService.isValidRsuNew(bulkDto.getRsu(),bulkDto.getCityZoneCode()))
					{
						errMsg.append("Rsu should be of input City Zone Code |");
						errFlag = true;

					}
					else
					{
						assignmentKey.append(bulkDto.getRsu()).append("~");
					}

				}
			else
				assignmentKey.append("#").append("~");
				
			
			//pin code validation generates cityZoneCode and zoneCode and cityCode 
			
			if( bulkDto.getCityZoneCode().equals("") && bulkDto.getCityZone().equals("") && bulkDto.getCity().equals("") &&  !bulkDto.getPincode().equals(""))
			{
				
				pincodeList=mstrService.getAssignmentMatrixDataForPinCode(bulkDto.getPincode(),circleId,productLobId );
				
				
				
				Iterator itr=pincodeList.iterator();
				while(itr.hasNext())
				{
				dto1=(BulkPinCodeDTO)itr.next();
				bulkDto.setCityZoneCode(dto1.getCityZoneCode());
				bulkDto.setCityZone(dto1.getZonecode());
				bulkDto.setCity(dto1.getCityCode());
				
				}
				if(!bulkDto.getCityZone().equals("") && !bulkDto.getCity().equals("") && !bulkDto.getCityZoneCode().equals("")){
					assignmentKey.append(bulkDto.getCityZone() + "~" + bulkDto.getCity() + "~" + bulkDto.getCityZoneCode()+ "~" + bulkDto.getPincode()+"~");
				}
				
			}
			else if(!bulkDto.getPincode().equals(""))
			{
					if(!mstrService.isValidPincodeNew(bulkDto.getPincode(),bulkDto.getCityZoneCode()))
					{ 
						errMsg.append("Pincode should be of input City Zone Code|");
						errFlag = true;
					}
					else
					{
						assignmentKey.append(bulkDto.getPincode()).append("~");
					}

			}
			else
				assignmentKey.append("#").append("~");
			
			//Request Category id validation
			
			logger.info("bulkDto.getRequestCategoryId().equals() :"+bulkDto.getRequestCategoryId());
				
			if(bulkDto.getRequestCategoryId().length()!=0){
			
			try
			{	
				logger.info("Request Category id if block");
				requestCategoryId = Integer.parseInt(bulkDto.getRequestCategoryId());
				//logger.info("requestCategoryId"+requestCategoryId);
			
			}
			catch(Exception e)
			{
				isNumeric = false;
				errMsg.append("Request_Category_id should be numeric |");
				errFlag = true;

			}
			//Lob wise validation
			/*if (!bulkDto.getProductLobId().equals("") &&  bulkDto.getProductId().equals(""))
			{
				if(!mstrService.isValidRequestCategoryForLob(bulkDto.getProductLobId()))
			{ 
				errMsg.append("Request Category should be of Product Lob");
				errFlag = true;
			}
				
			}
			//productId based validation
			else if( !bulkDto.getProductLobId().equals("") && bulkDto.getProductId().equals(""))
			{
				if(!mstrService.isValidRequestCategory(bulkDto.getProductId()))
			{ 
				errMsg.append("Request Category should be of Product Id");
				errFlag = true;
			}
				
			}*/
			
			 if(isNumeric)
			{
				assignmentKey.append(requestCategoryId).append("~");
			}
			}
			else{
				bulkDto.setRequestCategoryId("-1");
				assignmentKey.append("#").append("~");
			}
			
			//Request Category Validation
			
		/*if(!bulkDto.getRequestCategoryId().equals(""))
			{
				
				logger.info("request category Loop");
				 if (!bulkDto.getProductLobId().equals("") &&  bulkDto.getProductId().equals(""))
				{
					if(!mstrService.isValidRequestCategoryForLob(bulkDto.getProductLobId()))
				{ 
					errMsg.append("Request Category should be of Product Lob");
					errFlag = true;
				}
					
				}
				
				else if( !bulkDto.getProductId().equals(""))
				{
					if(!mstrService.isValidRequestCategory(bulkDto.getProductId()))
				{ 
					errMsg.append("Request Category should be of Product Id");
					errFlag = true;
				}
					
				}
				
			}*/
		
		
		// Assignment key Added Here
			
		bulkDto.setAssignmentKey(assignmentKey.toString());
		
		keyFormes=bulkDto.getAssignmentKey()+bulkDto.getCircle()+bulkDto.getOlmId()+bulkDto.getChannelPartnerId()+bulkDto.getLevelType()+bulkDto.getUserType();  //added by Nancy....creating a key for insertion:
		//logger.info("key is**************"+keyFormes+"****"+i);
		addlist.add(keyFormes);
		i++;
		//bulkDto.setUploadType("BULK");
		
		//// Secondary Olm Id String Validation starts ////
			
			if(bulkDto.getSecondaryOlmId().length() > 0 && bulkDto.getActionType().equalsIgnoreCase("c"))
			{
				String secOlmIds[] = bulkDto.getSecondaryOlmId().split(",");
			
				if(secOlmIds.length >4)
				{
					errMsg.append("Unable to insertion,Please check Secondary OLM Id's length |");
					errFlag = true;
					
				}
			}
		if(bulkDto.getSecondaryOlmId().length() > 100)
			{
				errMsg.append("SECONDARY_OLM_ID should not be of more than 100 characters |");
				errFlag = true;  
			}
			else
			{ 
				String secOlmIdsStr = bulkDto.getSecondaryOlmId();
				if(!secOlmIdsStr.equals(""))
				{

					if(secOlmIdsStr.indexOf(',') != -1)
					{  
						char tempChar = secOlmIdsStr.charAt(0);

						if(tempChar == ',')
						{
							secOlmIdsStr = secOlmIdsStr.substring(1,secOlmIdsStr.length());
						}

						tempChar = secOlmIdsStr.charAt(secOlmIdsStr.length()-1);

						if(tempChar == ',')
						{
							secOlmIdsStr = secOlmIdsStr.substring(0,secOlmIdsStr.length()-1);
						}  

						String secOlmIds[] = secOlmIdsStr.split(",");
						for(int j = 0;j< secOlmIds.length;j++)
						{
							if(!errFlag){
								logger.info("secOlmIds[j]----------"+secOlmIds[j]);
								logger.info("bulkDto.getOlmId()--------"+bulkDto.getOlmId());
								if(secOlmIds[j].equalsIgnoreCase(bulkDto.getOlmId()))
								{
									errMsg.append("OLM_ID can not be equal to SECONDARY_OLM_ID "); 
									errFlag = true;
								}
							}
						}
						if(!errFlag)
						for(int i=0;i< secOlmIds.length;i++)
						{
							boolean flag = true;
							for(int j = 0;j< i ; j++)
							{
								if(secOlmIds[i].equalsIgnoreCase(secOlmIds[j])){
									flag = false;
								}
							}
							if(!flag){
								errMsg.append("duplicate SECONDARY_OLM_ID "); 
								errFlag = true;
							}
						}
						for(int i = 0 ; i < secOlmIds.length ;i++)
						{
							
							if(!mstrService.isValidUserLoginId(secOlmIds[i].trim()) && 
									secOlmIds[i].equalsIgnoreCase(bulkDto.getOlmId()))
							{
								errMsg.append(secOlmIds[i]+" ");
								isValidSecOlmId = false;
								errFlag = true;

							}
							else
							{
								if(i == secOlmIds.length -1)
									validSecOlmIdsStr +=  secOlmIds[i].trim();
								else
									validSecOlmIdsStr += secOlmIds[i].trim() + ",";
							}
						}
					}
					
					//validating more than 1 secondary olm ids 
					else
					{
						if(!mstrService.isValidUserLoginId(secOlmIdsStr.trim()))
						{
							errMsg.append(secOlmIdsStr+" ");
							isValidSecOlmId = false;
						}
						else if(secOlmIdsStr.trim().equalsIgnoreCase(bulkDto.getOlmId()))
						{
							errMsg.append(" OLM_ID can not be equal to SECONDARY_OLM_ID "); 
							errFlag = true;
						}
						else
							validSecOlmIdsStr = secOlmIdsStr.trim();

					}//validating single secondary olm id

				}//if(!secOlmIdsStr.equals(""))

			}//else for secondary olm id string less than 100 characters	  


			if(!isValidSecOlmId)
			{
				errMsg.append("is/are invalid SECONDARY_OLM_ID "); 
				errFlag = true;
			}
			else
			{
				bulkDto.setSecondaryOlmId(validSecOlmIdsStr);
				bulkDto.setPrimaryAuth(Constants.PRIMARY_ASSIGNMENT);
			}

			//level1cc validation
			
			if(bulkDto.getLevellCC() == null)
			{
				
				bulkDto.setLevellCC("");
			}
			else if(!bulkDto.getLevellCC().equals("") )
				{
					
					String level1ccStr = bulkDto.getLevellCC();
					
					if(!level1ccStr.equals(""))
					{
						
						if(level1ccStr.indexOf(',') != -1)
						{  
							/*char tempChar = level1ccStr.charAt(0);

							if(tempChar == ',')
							{
								level1ccStr = level1ccStr.substring(1,level1ccStr.length());
							}

							tempChar = level1ccStr.charAt(level1ccStr.length()-1);

							if(tempChar == ',')
							{
								level1ccStr = level1ccStr.substring(0,level1ccStr.length()-1);
							}  */

							String level1ccIds[] = level1ccStr.split(",");
							for(int j = 0;j< level1ccIds.length;j++)
							{
								if(!errFlag){
									logger.info("secOlmIds[j]----------"+level1ccIds[j]);
									logger.info("bulkDto.getOlmId()--------"+bulkDto.getOlmId());
									if(level1ccIds[j].equalsIgnoreCase(bulkDto.getOlmId()))
									{
										errMsg.append("OLM_ID can not be equal to LEVEL1CC_ID "); 
										errFlag = true;
									}
								}
							}
							if(!errFlag)
							for(int i=0;i< level1ccIds.length;i++)
							{
								boolean flag = true;
								for(int j = 0;j< i ; j++)
								{
									if(level1ccIds[i].equalsIgnoreCase(level1ccIds[j])){
										flag = false;
									}
								}
								if(!flag){
									errMsg.append("duplicate LEVEL1CC_ID "); 
									errFlag = true;
								}
							}
							}
						
						//validating more than 1 level1cc Ids 
					else
						{
							
							if(!mstrService.isValidUserLoginId(level1ccStr.trim()))
							{
								errMsg.append(level1ccStr+" ");
								isValidSecOlmId = false;
							}
							else if(level1ccStr.trim().equalsIgnoreCase(bulkDto.getOlmId()))
							{
								errMsg.append(" OLM_ID can not be equal to LEVEL1CC_ID "); 
								errFlag = true;
							}
							else
								validSecOlmIdsStr = level1ccStr.trim();

						}
	
					}
				}

			
				//level2cc Validation
			
			if(bulkDto.getLevel2CC() == null)
			{
				//logger.info("inside level1cc ");
				bulkDto.setLevel2CC("");
			}
			else
				if(bulkDto.getLevel2CC().length() > 0 )
				{
					
					String level2ccStr = bulkDto.getLevel2CC();
					if(!level2ccStr.equals(""))
					{
						
						if(level2ccStr.indexOf(',') != -1)
						{  
							/*char tempChar = level2ccStr.charAt(0);

							if(tempChar == ',')
							{
								level2ccStr = level2ccStr.substring(1,level2ccStr.length());
							}

							tempChar = level2ccStr.charAt(level2ccStr.length()-1);

							if(tempChar == ',')
							{
								level2ccStr = level2ccStr.substring(0,level2ccStr.length()-1);
							}  */

							String level2ccIds[] = level2ccStr.split(",");
							for(int j = 0;j< level2ccIds.length;j++)
							{
								if(!errFlag){
									logger.info("secOlmIds[j]----------"+level2ccIds[j]);
									logger.info("bulkDto.getOlmId()--------"+bulkDto.getOlmId());
									if(level2ccIds[j].equalsIgnoreCase(bulkDto.getOlmId()))
									{
										errMsg.append("OLM_ID can not be equal to LEVEL2CC_ID "); 
										errFlag = true;
									}
								}
							}
							if(!errFlag)
							for(int i=0;i< level2ccIds.length;i++)
							{
								boolean flag = true;
								for(int j = 0;j< i ; j++)
								{
									if(level2ccIds[i].equalsIgnoreCase(level2ccIds[j])){
										flag = false;
									}
								}
								if(!flag){
									errMsg.append("duplicate LEVEL2CC_ID "); 
									errFlag = true;
								}
							}
							
								
							}
						
						else
						{
							if(!mstrService.isValidUserLoginId(level2ccStr.trim()))
							{
								errMsg.append(level2ccStr+" ");
								isValidSecOlmId = false;
							}
							else if(level2ccStr.trim().equalsIgnoreCase(bulkDto.getOlmId()))
							{
								errMsg.append(" OLM_ID can not be equal to LEVEL2CC_ID "); 
								errFlag = true;
							}
							else
								validSecOlmIdsStr = level2ccStr.trim();

						}
	
					}
				}

				//level3 cc validation
			
			if(bulkDto.getLevel3CC() == null)
			{
				//logger.info("inside level1cc ");
				bulkDto.setLevel3CC("");
			}
			else
				if(bulkDto.getLevel3CC().length() > 0 )
				{
					
					String level3ccStr = bulkDto.getLevel3CC();
					if(!level3ccStr.equals(""))
					{
						
						if(level3ccStr.indexOf(',') != -1)
						{  
							/*char tempChar = level3ccStr.charAt(0);

							if(tempChar == ',')
							{
								level3ccStr = level3ccStr.substring(1,level3ccStr.length());
							}

							tempChar = level3ccStr.charAt(level3ccStr.length()-1);

							if(tempChar == ',')
							{
								level3ccStr = level3ccStr.substring(0,level3ccStr.length()-1);
							}  */

							String level3ccIds[] = level3ccStr.split(",");
							for(int j = 0;j< level3ccIds.length;j++)
							{
								if(!errFlag){
									
									logger.info("bulkDto.getOlmId()--------"+bulkDto.getOlmId());
									if(level3ccIds[j].equalsIgnoreCase(bulkDto.getOlmId()))
									{
										errMsg.append("OLM_ID can not be equal to LEVEL3CC_ID "); 
										errFlag = true;
									}
								}
							}
							if(!errFlag)
							for(int i=0;i< level3ccIds.length;i++)
							{
								boolean flag = true;
								for(int j = 0;j< i ; j++)
								{
									if(level3ccIds[i].equalsIgnoreCase(level3ccIds[j])){
										flag = false;
									}
								}
								if(!flag){
									errMsg.append("duplicate LEVEL3CC_ID "); 
									errFlag = true;
								}
							}
							}
						
					else
						{
							if(!mstrService.isValidUserLoginId(level3ccStr.trim()))
							{
								errMsg.append(level3ccStr+" ");
								isValidSecOlmId = false;
							}
							else if(level3ccStr.trim().equalsIgnoreCase(bulkDto.getOlmId()))
							{
								errMsg.append(" OLM_ID can not be equal to LEVEL3CC_ID "); 
								errFlag = true;
							}
							else
								validSecOlmIdsStr = level3ccStr.trim();

						}
						}
						}

				//level4 cc validation
			
			if(bulkDto.getLevel4CC() == null)
			{
				//logger.info("inside level1cc ");
				bulkDto.setLevel4CC("");
			}
			else
				if(bulkDto.getLevel4CC().length() > 0 )
				{
					
					String level4ccStr = bulkDto.getLevel4CC();
					if(!level4ccStr.equals(""))
					{
					
						if(level4ccStr.indexOf(',') != -1)
						{  
							/*char tempChar = level4ccStr.charAt(0);

							if(tempChar == ',')
							{
								level4ccStr = level4ccStr.substring(1,level4ccStr.length());
							}

							tempChar = level4ccStr.charAt(level4ccStr.length()-1);

							if(tempChar == ',')
							{
								level4ccStr = level4ccStr.substring(0,level4ccStr.length()-1);
							}  
							 	*/
							String level4ccIds[] = level4ccStr.split(",");
							for(int j = 0;j< level4ccIds.length;j++)
							{
								if(!errFlag){
									logger.info("secOlmIds[j]----------"+level4ccIds[j]);
									logger.info("bulkDto.getOlmId()--------"+bulkDto.getOlmId());
									if(level4ccIds[j].equalsIgnoreCase(bulkDto.getOlmId()))
									{
										errMsg.append("OLM_ID can not be equal to LEVEL4CC_ID "); 
										errFlag = true;
									}
								}
							}
							if(!errFlag)
							for(int i=0;i< level4ccIds.length;i++)
							{
								boolean flag = true;
								for(int j = 0;j< i ; j++)
								{
									if(level4ccIds[i].equalsIgnoreCase(level4ccIds[j])){
										flag = false;
									}
								}
								if(!flag){
									errMsg.append("duplicate LEVEL4CC_ID "); 
									errFlag = true;
								}
							}
							}
						
					else
						{
							if(!mstrService.isValidUserLoginId(level4ccStr.trim()))
							{
								errMsg.append(level4ccStr+" ");
								isValidSecOlmId = false;
							}
							else if(level4ccStr.trim().equalsIgnoreCase(bulkDto.getOlmId()))
							{
								errMsg.append(" OLM_ID can not be equal to LEVEL4CC_ID "); 
								errFlag = true;
							}
							else
								validSecOlmIdsStr = level4ccStr.trim();

						}
	
					}
				}

			//// Secondary Olm Id String Validation ends ////
			logger.info("bulkDto.getOlmId().length() :"+bulkDto.getOlmId().length());
			if(bulkDto.getOlmId().length() > 12)
			{
					errMsg.append("OLM ID should not be of more than 10 characters |");
					errFlag = true;  
			}
			
			
			if(bulkDto.getSecondaryOlmId().length() > 0 && bulkDto.getActionType().equalsIgnoreCase("c"))
			{
				String secOlmIds[] = bulkDto.getSecondaryOlmId().split(",");
				
				for(int i = 0 ; i < secOlmIds.length ;i++)
				{
					int level = i+1;
					/*changes by Nancy */
					if("Y".equalsIgnoreCase(Assignment_Approval_flag))  //temp table insertion
					{

						if(	bulkAssignmentDao.isAssignmentTempKeyExist(secOlmIds[i],bulkDto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,Constants.ACTIVE_ASSIGNMENT,level, bulkDto.getUserType(), bulkDto.getOlmId(),0)
							|| 
							bulkAssignmentDao.isAssignmentKeyExist(secOlmIds[i],bulkDto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,Constants.ACTIVE_ASSIGNMENT,level, bulkDto.getUserType(), bulkDto.getOlmId(),0))
						{
							
							errMsg = new StringBuffer("");
							errMsg.append("Assignment not created because secondary assignment already created for " + secOlmIds[i]);
							errFlag = true;
						}
					
					else  if((addlist.contains(keyFormes) && Collections.frequency(addlist, keyFormes)>1)
								   ||(bulkAssignmentDao.isAssignmentKeyExist(secOlmIds[i],bulkDto.getAssignmentKey(), 
										Constants.SECONDARY_ASSIGNMENT,Constants.ACTIVE_ASSIGNMENT,level, bulkDto.getUserType(), bulkDto.getOlmId(),0))&& bulkDto.getActionType().equalsIgnoreCase("c"))	
						  {
							  //logger.info("addlist.contains(keyFormes) && addlist.size()>1"+(addlist.contains(keyFormes) && addlist.size()>1));
							  errMsg = new StringBuffer("");
							  errMsg.append("Assignment not created because secondary assignment already created for " + secOlmIds[i]);
							  errFlag = true;  
						  }
					}
					else
					{ 
						
						if(bulkAssignmentDao.isAssignmentKeyExist(secOlmIds[i],bulkDto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,Constants.ACTIVE_ASSIGNMENT,level, bulkDto.getUserType(), bulkDto.getOlmId(),0)
								||
						   bulkAssignmentDao.isAssignmentTempKeyExist(secOlmIds[i],bulkDto.getAssignmentKey(),Constants.SECONDARY_ASSIGNMENT,Constants.ACTIVE_ASSIGNMENT,level, bulkDto.getUserType(), bulkDto.getOlmId(),0))
						{
							errMsg = new StringBuffer("");
							errMsg.append("Assignment not created because secondary assignment already created for " + secOlmIds[i]);
							errFlag = true;
						}
						else   //false...does not exist in db but maybe has been already added in sheet to list......
						{
									  
						   if(addlist.contains(keyFormes) && Collections.frequency(addlist, keyFormes)>1)	
						  {
							 // logger.info("secondary ********addlist.contains(keyFormes) && addlist.size()>1"+(addlist.contains(keyFormes) && addlist.size()>1));
							  errMsg = new StringBuffer("");
							  errMsg.append("Assignment not created because secondary assignment already created for " + secOlmIds[i]);
							  errFlag = true;  
						  }
						}
					}
			}
			}
				/*end of changes by Nancy */
			
			
			
			else 
			{
				if("Y".equalsIgnoreCase(Assignment_Approval_flag))
				{
					if(addlist.contains(keyFormes) && Collections.frequency(addlist, keyFormes)>1)  //if its the multiple entry...
					{
						
						errMsg = new StringBuffer("");
						errMsg.append("Assignment not created because primary assignment already created for:"+bulkDto.getOlmId());
						errFlag = true;  	
					}
					else if(bulkDto.getActionType().equalsIgnoreCase("c") && ((bulkAssignmentDao.isAssignmentTempKeyExist(bulkDto.getOlmId(),bulkDto.getAssignmentKey(),Constants.PRIMARY_ASSIGNMENT,Constants.ACTIVE_ASSIGNMENT, 0,bulkDto.getUserType(), bulkDto.getOlmId(),0)
							||
						   (bulkAssignmentDao.isAssignmentKeyExist(bulkDto.getOlmId(),bulkDto.getAssignmentKey(),Constants.PRIMARY_ASSIGNMENT,Constants.ACTIVE_ASSIGNMENT, 0,bulkDto.getUserType(), bulkDto.getOlmId(),0)))))
									
					{   
						
						errMsg = new StringBuffer("");
						errMsg.append("Assignment not created because primary assignment already created for " + bulkDto.getOlmId());
						errFlag = true;
					}
				
				}
				else
				{

					if((addlist.contains(keyFormes) && Collections.frequency(addlist, keyFormes)>1) 
							||
					bulkAssignmentDao.isAssignmentTempKeyExist(bulkDto.getOlmId(),bulkDto.getAssignmentKey(),Constants.PRIMARY_ASSIGNMENT,Constants.ACTIVE_ASSIGNMENT, 0,bulkDto.getUserType(), bulkDto.getOlmId(),0))
							  //if its the multiple entry in n main table...
					{
						
						errMsg = new StringBuffer("");
						errMsg.append("Assignment not created because primary assignment already created for:"+bulkDto.getOlmId());
						errFlag = true;  	
					}
					else if(bulkDto.getActionType().equalsIgnoreCase("c") && (bulkAssignmentDao.isAssignmentKeyExist(bulkDto.getOlmId(),bulkDto.getAssignmentKey(),Constants.PRIMARY_ASSIGNMENT,Constants.ACTIVE_ASSIGNMENT, 0,bulkDto.getUserType(), bulkDto.getOlmId(),0)
							||
							bulkAssignmentDao.isAssignmentTempKeyExist(bulkDto.getOlmId(),bulkDto.getAssignmentKey(),Constants.PRIMARY_ASSIGNMENT,Constants.ACTIVE_ASSIGNMENT, 0,bulkDto.getUserType(), bulkDto.getOlmId(),0)))
							
									
					{
						errMsg = new StringBuffer("");
						errMsg.append("Assignment not created because primary assignment already created for " + bulkDto.getOlmId());
						errFlag = true;
					}
				
				
				}
	
				
			}
			
			if(errFlag)
			{
				bulkErr.setRowNum(rowNumber);
				bulkErr.setErrMsg(errMsg.toString());
				errorList.add(bulkErr);
			}
			else
			{
				if(bulkDto.getActionType().equalsIgnoreCase("c"))
				{
					validListInsert.add(bulkDto);
					validInsertRowNosList.add(rowNumber);
				}	
				if(bulkDto.getActionType().equalsIgnoreCase("d"))
				{
					validListDelete.add(bulkDto);	
					validDeleteRowNosList.add(rowNumber);
				}  	  
			}
			}
		
			
		catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occured while validating bulkDto");
			throw new LMSException("Exception occurred in validateDto");
		}

		return errorList;
	}//validateDto

	//Write error Logs Method
	
	public boolean writeLogs(List<BulkUploadErrorLogDto> errorList,String errLogFileName,UserMstr userBean) throws LMSException
	{
		boolean isError = false;
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		String date = sdf1.format(new java.util.Date().getTime());
		String fileName="";
		BulkAssigmentDao bulkAssignmentDao = BulkAssigmentDaoImpl.bulkAssigmentDaoInstance();//chnaged by srikant new BulkAssigmentDaoImpl();
		
		try{
		//String filePath= errLogFileName + "/AssignmentMatrixBulkUpload"+ date + ".csv";
		if(errorList.size() > 0)
		{
			FileWriter fw = new FileWriter(errLogFileName);
			PrintWriter pw = new PrintWriter(fw);
			
			
			BulkUploadErrorLogDto bulkErrDto;

			pw.print("PRODUCT_LOB_ID");
			pw.print(",");
			pw.print("PRODUCT_ID");
			pw.print(",");
			pw.print("REQUEST_CATEGORY_ID");
			pw.print(",");
			pw.print("OLM_ID");
			pw.print(",");
			pw.print("CIRCLE_ID");
			pw.print(",");
			pw.print("ZONE_CODE");
			pw.print(",");
			pw.print("CITY_CODE");
			pw.print(",");
			pw.print("CITY_ZONE_CODE");
			pw.print(",");
			pw.print("PINCODE");
			pw.print(",");
			pw.print("RSU_CODE");
			pw.print(",");
			pw.print("SECONDARY_OLM_ID");
			pw.print(",");
			pw.print("USER_TYPE GIS/normal");
			pw.print(",");
			pw.print("CREATION/DELETION (c/d)");
			pw.print(",");
			pw.print("LEVEL1 CC");
			pw.print(",");
			pw.print("LEVEL2 CC");
			pw.print(",");
			pw.print("LEVEL3 CC");
			pw.print(",");
			pw.println("LEVEL4 CC");
				
			
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
			//bulkAssignmentDao.logs(userBean,errLogFileName);
			
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
		logger.info("Exception occurred while writing error logs to csv");
		throw new LMSException("Exception occurred while writing logs to csv");
	}

	return isError;

	       } 

	public String CreateAssignmentMatrix(AssignmentMatrixFormBean form,	UserMstr userBean) throws LMSException {
		String error = "";
		List<BulkUploadErrorLogDto> errorList = new ArrayList<BulkUploadErrorLogDto>();
		BulkAssignmentDto bulkDto = new BulkAssignmentDto();
		BulkAssigmentDao bulkAssigmentDao = BulkAssigmentDaoImpl.bulkAssigmentDaoInstance();//chnaged by srikant new BulkAssigmentDaoImpl();
		MasterService mstrService = new MasterServiceImpl();
		try{
			
		bulkDto.setAssignment(form.isAssignment());	
		bulkDto.setOlmId(form.getOlmID());
		bulkDto.setLevelId(form.getLevelID());
		bulkDto.setCircle(String.valueOf(form.getCircleId()));
		bulkDto.setCity(form.getCityCode());
		bulkDto.setPincode(form.getPinCode());
		bulkDto.setCityZone(form.getZoneCode());
		bulkDto.setProductLobId(String.valueOf(form.getProductLobID()));
		bulkDto.setPrimaryAuth(Constants.PRIMARY_ASSIGNMENT);
		bulkDto.setSecondaryOlmId(form.getOtherIDs());
		bulkDto.setRsu(form.getRsuCode());
		bulkDto.setUserType(form.getRequestType());
		bulkDto.setRequestCategoryId(String.valueOf(form.getRequestCategoryId()));
		bulkDto.setLevelId(form.getLevelID());
		bulkDto.setProductId(form.getProductId());
		bulkDto.setCityZoneCode(form.getCityZoneCode());
		bulkDto.setActionType("c");
		bulkDto.setLevellCC(form.getLevellCC());
		bulkDto.setLevel2CC(form.getLevel2CC());
		bulkDto.setLevel3CC(form.getLevel3CC());
		bulkDto.setLevel4CC(form.getLevel4CC());
		bulkDto.setUploadType("SINGLE");
		
		errorList = validateDto(bulkDto,1,errorList,userBean); 
		if(errorList.size() <= 0){
		//validListInsert.add(bulkDto);
			String approval_Activate_flag = mstrService.getParameterName(PropertyReader.getAppValue("Assignment.approval.activate.flag"));
		if(validListInsert.size() > 0){
			//bulkAssigmentDao.insertAssignment(validListInsert,userBean);
			if("Y".equalsIgnoreCase(approval_Activate_flag))
			{
			bulkAssigmentDao.insertTempAssignment(validListInsert,userBean);
			error= "";
			}
			else
			{
				bulkAssigmentDao.insertAssignment(validListInsert,userBean,"main");
				error= "";
			}
		}
		}
		else
		{
			Iterator iterator = errorList.iterator();
			error = ((BulkUploadErrorLogDto)iterator.next()).getErrMsg();
		}
		}
		catch(Exception e )
		{
			e.printStackTrace();
			throw new LMSException("Exception Occurred in createAssignmentMatrix");
		}
		return error;
	}

	public List<BulkAssignmentDto> getAssignmentMatrixList(String olmID) throws LMSException {
		BulkAssigmentDao bulkAssigmentDao = BulkAssigmentDaoImpl.bulkAssigmentDaoInstance();//chnaged by srikant new BulkAssigmentDaoImpl();
		try{
		return bulkAssigmentDao.getAssignmentMatrixList(olmID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new LMSException("Exception Occurred in getAssignmentMatrixList");
		}
	}

	public String deleteAssignmentMatrix(String[] ids, UserMstr userBean)throws LMSException {
		String error = "";
		List<BulkUploadErrorLogDto> errorList = new ArrayList<BulkUploadErrorLogDto>();
		BulkAssignmentDto bulkDto = new BulkAssignmentDto();
		BulkAssigmentDao bulkAssigmentDao = BulkAssigmentDaoImpl.bulkAssigmentDaoInstance();//chnaged by srikant new BulkAssigmentDaoImpl();
		
		try{
			for(int i = 0;i<ids.length;i++)
			{
				bulkDto = new BulkAssignmentDto();
				
				String[] s = ids[i].split(",");
				bulkDto.setOlmId(s[0]);
				bulkDto.setAssignmentKey(s[1]);
				bulkDto.setPrimaryAuth(Integer.parseInt(s[2]));
				
				
				validListDelete.add(bulkDto);
			}
		if(validListDelete.size() > 0){
			bulkAssigmentDao.softDeleteAssignment(validListDelete,userBean);
			return "";
		}
		else
		return error;
		}
		catch(Exception e )
		{
			e.printStackTrace();
			throw new LMSException("Exception Occurred in createAssignmentMatrix");
		}
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
	
	/* Added by Parnika for LMS Phase 2 */
	
	public JSONObject getElementsAsJsonZoneFromZoneMaster(String circleMstrId)throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllChildrenZoneFromZoneMaster(circleMstrId);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			ZoneDTO dto=(ZoneDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}
	
	public ArrayList getAllChildrenZoneFromZoneMaster(String circleMstrId) throws Exception {
 		
		ArrayList zoneList=new ArrayList();
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		if (circleMstrId != null){
			zoneList= dao.getZoneListBasedOnCircle(Integer.parseInt(circleMstrId) );
		}		
		return zoneList;
	}
	
	public JSONObject getElementsAsJsonCity(String zoneCode)throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllChildrenCity(zoneCode);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			CityDTO dto=(CityDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}
	
	public ArrayList getAllChildrenCity(String zoneCode)throws Exception {
 		
		ArrayList cityList=new ArrayList();
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		if (zoneCode != null){
			cityList= dao.getCityListBasedOnZone(zoneCode);
		}		
		return cityList;
	}
	
	public JSONObject getElementsAsJsonCityZone(String cityCode)throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllChildrenCityZone(cityCode);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			CityZoneDTO dto=(CityZoneDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}
	
	public ArrayList getAllChildrenCityZone(String cityCode) throws Exception {
 		
		ArrayList cityZoneList=new ArrayList();
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		if (cityCode != null){
			cityZoneList= dao.getCityZoneListBasedOnCity(cityCode);
		}		
		return cityZoneList;
	}
		
	public JSONObject getElementsAsJsonRsu(String cityZoneCode)throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllChildrenRsu(cityZoneCode);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			RSUDTO dto=(RSUDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}
	public ArrayList getAllChildrenRsu(String cityCode) throws Exception {
 		
		ArrayList rsuList=new ArrayList();
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		if (cityCode != null){
			rsuList= dao.getRsuBasedOnCityCode(cityCode);
		}		
		return rsuList;
	}
	
	public JSONObject getElementsAsJsonPincode(String cityZoneCode)throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllChildrenPincode(cityZoneCode);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			PINCodeDTO dto=(PINCodeDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}
	
	public ArrayList getAllChildrenPincode(String cityZoneCode) throws Exception {
 		
		ArrayList pincodeList=new ArrayList();
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		if (cityZoneCode != null){
			pincodeList= dao.getPincodeListBasedOnCityCode(cityZoneCode);
		}		
		return pincodeList;
	}

	public JSONObject getElementsAsJsonZoneFromZoneMasterNew(String circleMstrId) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllChildrenZoneFromZoneMasterForCircle(circleMstrId);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			ZoneDTO dto=(ZoneDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}
	
	public ArrayList getAllChildrenZoneFromZoneMasterForCircle(String circleMstrId) throws Exception {
 		
		ArrayList zoneList=new ArrayList();
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		if (circleMstrId != null){
			zoneList= dao.getZoneListBasedOnCircleNew(Integer.parseInt(circleMstrId) );
			Iterator itr=zoneList.iterator();
			while(itr.hasNext())
			{
				ZoneDTO zd=(ZoneDTO)itr.next();
				logger.info("zone  :"+zd.getZoneCode()+","+zd.getZoneName());
				
			}
		}		
		return zoneList;
	}

	public JSONObject getElementsAsJsonCityNew(String circleMstrId) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllChildrenZoneFromZoneMasterForCircleNew(circleMstrId);
		Iterator itr=list.iterator();
		while(itr.hasNext())
		{
			CityDTO ct=(CityDTO)itr.next();
			logger.info("zone  :"+ct.getCityCode()+","+ct.getCityName());
			
		}
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			CityDTO dto=(CityDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}
	
	public ArrayList getAllChildrenZoneFromZoneMasterForCircleNew(String circleMstrId) throws Exception {
 		
		ArrayList cityList=new ArrayList();
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		if (circleMstrId != null){
			cityList= dao.getElementsAsJsonCityNew(Integer.parseInt(circleMstrId) );
		}		
		return cityList;
	}

	public JSONObject getElementsAsJsonCityZoneNew(String circleMstrId) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllChildrenCityZoneFromCityCode(circleMstrId);
		Iterator itr=list.iterator();
		while(itr.hasNext())
		{
			CityZoneDTO ct=(CityZoneDTO)itr.next();
			//logger.info("zone  :"+ct.getCityZoneCode()+","+ct.getCityZoneName());
			
		}
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			CityZoneDTO dto=(CityZoneDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}
	
	public ArrayList getAllChildrenCityZoneFromCityCode(String circleMstrId) throws Exception {
 		
		ArrayList cityZoneList=new ArrayList();
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		if (circleMstrId != null){
			cityZoneList= dao.getElementsAsJsonCityZoneNew(Integer.parseInt(circleMstrId) );
		}		
		return cityZoneList;
	}

	public JSONObject getElementsAsJsonCityZone1(String zoneCode) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllChildrenCityZone1(zoneCode);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			CityZoneDTO dto=(CityZoneDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}
	
	public ArrayList getAllChildrenCityZone1(String zoneCode)throws Exception {
 		
		ArrayList cityZoneList=new ArrayList();
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		if (zoneCode != null){
			cityZoneList= dao.getCityZoneListBasedOnZone(zoneCode);
		}		
		return cityZoneList;
	}

	
	public String rejectAssignmentmatrix(String[] ids, UserMstr userBean,String flag,AssignmentMatrixFormBean commonForm)throws LMSException {
		String error = "";
		List<BulkUploadErrorLogDto> errorList = new ArrayList<BulkUploadErrorLogDto>();
		BulkAssignmentDto bulkDto = new BulkAssignmentDto();
		BulkAssigmentDao bulkAssigmentDao = BulkAssigmentDaoImpl.bulkAssigmentDaoInstance();//chnaged by srikant new BulkAssigmentDaoImpl();
		String level=flag.split(",")[1];
		String flagLevel=flag.split(",")[0];
		
		try{
			for(int i = 0;i<ids.length;i++)
			
			{
				bulkDto = new BulkAssignmentDto();
				
				String[] s = ids[i].split(",");
				bulkDto.setOlmId(s[0]);
				bulkDto.setAssignmentKey(s[1]);
				bulkDto.setPrimaryAuth(Integer.parseInt(s[2]));
				bulkDto.setRemarks(commonForm.getRemarks());
				validListDelete.add(bulkDto);
			}
		if(validListDelete.size() > 0 )
		{
			if(level.equalsIgnoreCase("L1"))
			{
			error=bulkAssigmentDao.rejectAssignmentMatrix(validListDelete,userBean,flagLevel);
			}
			else
			{
			error=bulkAssigmentDao.rejectAssignmentMatrixL2(validListDelete,userBean,flagLevel);	
			
			bulkAssigmentDao.insertintoMainTable(userBean);  //insert data into main table: added by Nancy
			}
			return "";
		}
		else
		return error;
		}
		catch(Exception e )
		{
			e.printStackTrace();
			throw new LMSException("Exception Occurred in createAssignmentMatrix");
		}
	}

	public List<BulkAssignmentDto> viewAssignmentMatrixStatus(UserMstr userBean) throws LMSException {
		BulkAssigmentDao bulkAssigmentDao = BulkAssigmentDaoImpl.bulkAssigmentDaoInstance();//chnaged by srikant new BulkAssigmentDaoImpl();
		try{
		return bulkAssigmentDao.viewAssignmentMatrixStatus(userBean);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new LMSException("Exception Occurred in viewAssignmentMatrixStatus");
		}
	}
	
	public static boolean writeFile(List<BulkUploadErrorLogDto> errorList,String errLogFileName,UserMstr userBean)
    {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("BulkAssignmentMatrixUpload");
        int rownum = 0;
        BulkUploadErrorLogDto bulkErrDto;
        boolean isExist=false;
        CellStyle style = workbook.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(CellStyle.NO_FILL);
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
       
        data.put("1", new Object[] {"PRODUCT_LOB_ID", "PRODUCT_ID", "REQUEST_CATEGORY_ID" ,"OLM_ID","CIRCLE_ID","ZONE_CODE","CITY_CODE","CITY_ZONE_CODE","PINCODE","RSU_CODE","SECONDARY_OLM_ID","USER_TYPE GIS/normal" ,"CREATION/DELETION (c/d)","LEVEL1 CC","LEVEL2 CC","LEVEL3 CC","LEVEL4 CC"});
       // data.put("2", new Object[] {"Row Number", "Upload Status"});
        
       Iterator iter = errorList.iterator();

		while(iter.hasNext())
		{
			 bulkErrDto = (BulkUploadErrorLogDto) iter.next();
			 data.put(String.valueOf(bulkErrDto.getRowNum()+1), new Object[] {bulkErrDto.getRowNum()+1, bulkErrDto.getErrMsg()});
			 isExist=true;
		}
	
		Set<String> keyset = data.keySet();
         for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(rownum==1)
               {
               cell.setCellStyle(style);
               }
                if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(errLogFileName));
            workbook.write(out);
            out.close();
            logger.info(errLogFileName+ " written successfully on disk.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return isExist;
    }
       
    }




