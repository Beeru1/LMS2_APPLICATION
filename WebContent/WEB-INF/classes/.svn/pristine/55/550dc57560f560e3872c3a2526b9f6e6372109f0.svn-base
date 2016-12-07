/*
 * Created on Nov 26, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ibm.appsecure.exception.ValidationException;
import com.ibm.appsecure.service.GSDService;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.km.dao.BulkUserDao;
import com.ibm.km.dao.impl.BulkUserDaoImpl;
import com.ibm.km.services.BulkUserService;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.dao.UserMstrDao;
import com.ibm.lms.dao.impl.UserMstrDaoImpl;
import com.ibm.lms.dto.FileDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.exception.UserMstrDaoException;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.UserMstrServiceImpl;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BulkUserServiceImpl implements BulkUserService {
	
	private static final Logger logger;
	static {
		logger = Logger.getLogger(BulkUserServiceImpl.class);
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	StringBuffer loginIdsNotcreated = new StringBuffer("");
	String error="";
	String status="S";
	public void bulkUpload()
		throws IOException, LMSException {
	
		
			File errorLog=null;
			FileWriter writer=null;
		    PrintWriter out= null;	
			int j = 0;
			int usersCreated=0;
			UserMstrService userService=new UserMstrServiceImpl();
			UserMstrDao userDao=UserMstrDaoImpl.userMstrDaoInstance();
			IEncryption enc_dec = new Encryption();
			FileDto fileDTO=new FileDto();
			int usersUpdated=0;
			File excelFile=null;
			FileReader f=null;
			BufferedReader br=null;
			String fileName="";
			try{
				BulkUserDao dao=new BulkUserDaoImpl();
					fileDTO=dao.getBulkUploadDetails();
					if(fileDTO==null){
						return ;
					}else{
						errorLog=new File(fileDTO.getFilePath().substring(0,fileDTO.getFilePath().lastIndexOf("."))+".log");
						
						fileDTO.setErrorFile(errorLog);
						writer= new FileWriter(errorLog);
						out = new PrintWriter(writer);
						if(fileDTO.getFileType().equals("D")){
						bulkDelete(fileDTO);
						return;
					}
					fileName=fileDTO.getFilePath().substring(fileDTO.getFilePath().lastIndexOf("/")+1,fileDTO.getFilePath().length());		
					excelFile = new File(fileDTO.getFilePath());
					
					f = new FileReader(excelFile);
					br = new BufferedReader(f);
					}	
			}
			catch(Exception e){
				e.printStackTrace();
				logger.info("Exception occured while fetching bulk user file from the DB");
			}
		
		try {
	
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String line = "";
			String user_login_id = "";
			String user_fname = "";
			String user_mname = "";
			String user_lname = "";
			String user_mobile_number = "";
			String user_emailId = "";
			String user_password = "";
			String fav_category_name = "";
			String element_id = "";
			String partner_name="";
			
			j=0;
			while ((line = br.readLine()) != null) {
				String[] columnValues = new String[24];
				// Updated against defect Id MASDB00105265
				j++;
				if(j==1)
					continue;
				if(j==1002){
					j--;
					break;
				}	
				try {
					String rowString = line;
					boolean wasDelimiter = true;
					String token = null;
					StringTokenizer st =
						new StringTokenizer(rowString, ",", true);
					int i = 1;
					while (st.hasMoreTokens()) {
						token = st.nextToken();
						if (token.equals(",")) {
							if (wasDelimiter) {
								token = "";
							} else {
								token = null;
							}
							wasDelimiter = true;
						} else {
							wasDelimiter = false;
						}
						if (token != null) {
							columnValues[i] = token.trim();
							i++;
						}
						             
					}
					// Changes made Against defect Id MASDB00105230 and MASDB00105213
					if(columnValues[1]==null||columnValues[2]==null||columnValues[4]==null||columnValues[5]==null||columnValues[6]==null||columnValues[7]==null||columnValues[1].equals("")||columnValues[2].equals("")||columnValues[4].equals("")||columnValues[5].equals("")||columnValues[6].equals("")||columnValues[7].equals("")){
						if(columnValues[1]==null||columnValues[1].equals("")){
							user_login_id="USER";
						}
						else{
							user_login_id=columnValues[1].trim();
						}
						logger.info(user_login_id + " NOT PROCESSED : All mandatory fields are not filled");
						out.println(user_login_id + " NOT PROCESSED : All mandatory fields are not filled");
						continue;
					}

					user_login_id = columnValues[1].toUpperCase().trim();
					user_fname = columnValues[2];
					user_mname = columnValues[3];
					user_lname = columnValues[4];
					user_mobile_number = columnValues[5];
					user_emailId = columnValues[6];
					fav_category_name = columnValues[8];
					partner_name=columnValues[7];
					if(partner_name==null){
						partner_name="";
					}
					if (fav_category_name == null)
						fav_category_name = "";
					
					element_id = fileDTO.getElementId();
					user_password = enc_dec.decrypt(PropertyReader.getAppValue("default.password"));
					//Element id of the logged in user from the session
					
					

					//GSD validation 
					GSDService gSDService = new GSDService();
					IEncryption encrypt = new Encryption();
					gSDService.validateCredentials(
						user_login_id,
						user_password);
					user_password = encrypt.generateDigest(user_password);
					
					UserMstr user=new UserMstr();
					user.setUserLoginId(user_login_id.trim());
					if(user_fname!=null)
						user.setUserFname(user_fname.trim());
					if(user_mname!=null)
						user.setUserMname(user_mname.trim());
					if(user_lname!=null)
						user.setUserLname(user_lname.trim());
					user.setUserMobileNumber(user_mobile_number);
					if(user_emailId!=null)
						user.setUserEmailid(user_emailId.trim());
					if(!emailValidation(user.getUserEmailid())){
						logger.info(user_login_id + "IS NOT CREATED :Validation Exception in Email Id ");
						out.println(user_login_id + "IS NOT CREATED :Validation Exception in Email Id ");
						continue;
					}
					if(user_password!=null)
						user.setUserPassword(user_password.trim());
					user.setElementId(element_id);
					user.setFavCategoryName(fav_category_name);
					user.setPartnerName(partner_name);	
					user.setCreatedBy(fileDTO.getUploadedBy());	
					UserMstr userData=userService.checkUserLoginId(user_login_id);
					//Changes made against Defect ID MASDB00105205
					if (userData==null)
					{
						
						usersCreated+=userService.insertUserData(user);	
						logger.info(user_login_id + "  IS CREATED   File Name : "+fileName);
						out.println(user_login_id + "  IS CREATED ");
							
					}	
					else if(userData.getElementId().equals(fileDTO.getElementId())){
						
						usersUpdated+=userDao.bulkUpdate(user);		
						logger.info(user_login_id + "  IS UPDATED   File Name : "+fileName);	
						out.println(user_login_id + "  IS UPDATED");						
					}	
					else{
						logger.info(user_login_id + "  NOT PROCESSED :  User belongs to different circle    File Name : "+fileName);	
						out.println(user_login_id + "  NOT PROCESSED : User belongs to different circle");
					}
					
			
					
					

				} catch (ValidationException validationExp) {
					
					status="P";
					logger.error(
						"Validation Exception in userloginid : "
							+ user_login_id
							+ "   : "
							+ validationExp);
					out.println(
							"Validation Exception in userloginid : "
								+ user_login_id
								+ "   : "
								+ validationExp);
					//validationExp.printStackTrace();
					logger.error(
						"createUser method : caught ValidationException for GSD : "
							+ validationExp.getMessageId());
					// Fixed for Defect ID MASDB00070389
					//errors.add("errors.userName", new ActionError(validationExp
					//.getMessageId()));
					//createUserFormBean.setUserPassword("");
					//saveErrors(request, errors);
					//forward = mapping.findForward(CREATEUSER_FAILURE);
					continue;
					//return false;
				} catch (LMSException e) {
					e.printStackTrace();
					status="P";
					logger.error(
						"Exception in userloginid : "
							+ user_login_id
							+ "   : "
							+ e);
					out.println(
							"Exception in userloginid : "
								+ user_login_id
								+ "   : "
								+ e);
				//	e.printStackTrace();
					continue;
					//return false;
				  				
				} catch (Exception e) {
					e.printStackTrace();
					status="P";
					logger.error(
						"Exception in userloginid : "
							+ user_login_id
							+ "   : "
							+ e);
					logger.error(
							"Exception in userloginid : "
								+ user_login_id
								+ "   : "
								+ e);
					continue;
					//return false;
				}  
				finally{
					
					
					DBConnection.releaseResources(con,ps,rs);
					
				} 

			}

		} catch (Exception e) {
			
			e.printStackTrace();
			status="F";
			error=getStackTrace(e);
			error=e.getClass().toString()+" :  Error Code "+e.getLocalizedMessage();
			
			logger.info("Exception occured while uploading the page " + e);
		}
		finally{
			/*
			 * Stroing the file details in DB
			*/
			if(fileDTO.getFileId()==null){
				
				return;
			}
	try{
			out.close();
			writer.close();
			BulkUserDao dao=new BulkUserDaoImpl();
			FileDto file=new FileDto();
			file.setUsersCreated(usersCreated+"");
			file.setUsersUpdated(usersUpdated+"");
			file.setUsersDeleted("0");
			file.setFileType("C");
			file.setStatus("P");
			file.setTotalUsers((--j)+"");
			file.setErrorMessage(error);
			file.setFileId(fileDTO.getFileId());
			dao.updateFileStatus(file);
			}
			catch(Exception e){
				logger.error("Exception occured while updating the bulk file status");
			}
	
		} 
		
	//	dto.setStatus(status);
	//	return dto;
		

	}
	
		public String getStackTrace(Exception ex) {
				java.io.StringWriter out = new java.io.StringWriter();
				ex.printStackTrace(new java.io.PrintWriter(out));
				String stackTrace = out.toString();
 
				return stackTrace;
			}
			public boolean isEmailAddress(String objInput)
			{
				
				
				if (objInput.equals(""))
				{
					return false;
				}

				String theInput = objInput.trim();
				int theLength = theInput.length();

				// there must be >= 1 character before @, so
				// start looking from character[1]
				// (i.e. second character in the text field)
				// look for '@'
				int i = 1;
				int cnt = 0;

				for(int j=0;j<theLength;j++)
				{
					if (theInput.charAt(j) == '@')
						cnt += 1;
				}

				if (cnt != 1)
				{
					//This cant be a email address
					return(false);
				}

				while ((i < theLength) && (theInput.charAt(i) != '@'))
				{
					// search till the last character
					i++ ;
				}

				if ((i >= theLength) || (theInput.charAt(i) != '@'))
				{
					// did not find the '@' character hence can't be email address.
					return (false);
				}
				else
				{
					// go 2 characters forward so that '@' and . are not simultaneous.
					i += 2;
				}

				// look for . (dot)
				while ((i < theLength) && (theInput.charAt(i) != '.'))
				{
					// keep searching for '.'
					i++ ;
				}

				// there must be at least one character after the '.'
				if ((i >= theLength - 1) || (theInput.charAt(i) != '.'))
				{
					// didn't find a '.' so its not a valid email ID
					return (false);
				}
				if(hasSpecialCharactersEmail(objInput))
				{
					return false;
				}
				else
				{
					// finally its got to be email ID
					return true;
				}
			}
			public boolean hasSpecialCharactersEmail(String field){   
			String SpecialCharacters="`~!$^&*()=+><{}[]+|=?':;\\\" ";
			if (field.length() >= 0)	{
			for(int i=0; i<SpecialCharacters.length(); i++)	{
				if(field.indexOf(SpecialCharacters.substring(i, 1))>= 0)	{ 
					return true;
			}
		}
		return false;
	}	
	return false;
}

@SuppressWarnings("finally")
public FileDto bulkDelete(FileDto DTO)
			throws IOException, LMSException{
				
	ResourceBundle bundle =
							ResourceBundle.getBundle("ApplicationResources");
	UserMstrService userService=new UserMstrServiceImpl(); 						
	StringBuffer userNotDeleted=new StringBuffer("");
	int usersDeleted=0;
	String status="S";
	String fileId="";
	FileWriter writer=null;
	PrintWriter out= null;	
	FileDto dto=new FileDto();
	int j = 0;						
	try{
			
		
		writer= new FileWriter(DTO.getErrorFile());
		out = new PrintWriter(writer);
		File excelFile = new File(DTO.getFilePath());
		FileReader f = new FileReader(excelFile);
		BufferedReader br = new BufferedReader(f);
		String fileName=DTO.getFilePath().substring(DTO.getFilePath().lastIndexOf("/"),DTO.getFilePath().length());
		String line="";
		String error="";
		String userId="";
		boolean incorrect=false;
		while ((line = br.readLine()) != null) {
			try{
				j++;
				if(j==1)
					continue;
				
				
				if(j==1002){
					j--;
					break;
				}
				
				try{
					//Updated against defect id : MASDB05270
					userId=line.toUpperCase().trim();
					line=userId.substring(0,userId.indexOf(","));
					if(line!=null ){
						if(!line.equals("")){
							
							if(incorrect==false){
							logger.error("Incorrect format");
							
							out.println("Incorrect format");
							
							}
							incorrect =true;
							continue;
						}
						
					}
						
					
				}
				catch(Exception e){
					
				}
				
				UserMstr userData=userService.checkUserLoginId(userId);
				if (userData==null)
				{
				
					logger.error("USER ID :" + userId + " DOES NOT EXISTS ");
					
					out.println("USER ID :" + userId + " DOES NOT EXISTS");
					
					continue;
				}else if(userData.getElementId().equals(DTO.getElementId())){
					usersDeleted+=userService.deleteUserService(userId);
					logger.info(userId + "  IS DELETED    File Name : "+fileName);	
					out.println(userId + "  IS DELETED ");	
				}
				else{
					logger.info(userId + "  NOT PROCESSED : User belongs to different circle   File Name : "+fileName);	
					out.println(userId + "  NOT PROCESSED : User belongs to different circle");	
				}
			}catch(Exception e){
				userNotDeleted.append(userId).append(",");		
				status="P";
				e.printStackTrace();
				continue;
			}
			finally{
				continue;
				
			}
			
		}	
		
	}catch(Exception e){
		error=getStackTrace(e);
		status="F";
		
	}
	finally{
		
		try{
			out.close();
			writer.close();
			BulkUserDao dao=new BulkUserDaoImpl();
			FileDto file=new FileDto();
			file.setUsersCreated("0");
			file.setUsersUpdated("0");
			file.setUsersDeleted(usersDeleted+"");
			file.setFileType("D");
			file.setStatus("P");
			file.setTotalUsers((--j)+"");
			file.setErrorMessage(error);
			file.setFileId(DTO.getFileId());
			dao.updateFileStatus(file);

		} 
		catch(Exception e){
			e.printStackTrace();
		}
	}
	dto.setStatus(status);
	
	return dto;
	
	
}


	private static Connection getConnection() throws UserMstrDaoException {

		logger.info(
			"Entered getConnection for operation on table:USER_MSTR");

		try {
			return DBConnection.getDBConnection();
		} catch (DAOException e) {
			e.printStackTrace();
			logger.info("Exception Occured while obtaining connection.");

			throw new UserMstrDaoException(
				"Exception while trying to obtain a connection",
				e);
		}

	}

	/* (non-Javadoc)
	 * @see com.ibm.km.services.BulkUserService#getBulkUploadDetails(java.lang.String)
	 */
	public FileDto getBulkUploadDetails() throws LMSException {
		BulkUserDao dao=new BulkUserDaoImpl();
		return dao.getBulkUploadDetails();
	}

	/* (non-Javadoc)
	 * @see com.ibm.km.services.BulkUserService#uploadFile(com.ibm.km.dto.KmFileDto)
	 */
	public int uploadFile(FileDto fileDto) throws LMSException {
		BulkUserDao dao=new BulkUserDaoImpl();
		return dao.bulkUserUploadFile(fileDto);
		
	}

	/* (non-Javadoc)
	 * @see com.ibm.km.services.BulkUserService#getBulkDeleteDetails(java.lang.String)
	 */
	public ArrayList getBulkDeleteDetails(String string) throws LMSException {
		BulkUserDao dao=new BulkUserDaoImpl();
		return dao.getBulkDeleteDetails(string);
	}

	/* (non-Javadoc)
	 * @see com.ibm.km.services.BulkUserService#updateFileStatus(int)
	 */
	public void updateFileStatus(FileDto dto) throws LMSException {
		BulkUserDao dao=new BulkUserDaoImpl();
		dao.updateFileStatus(dto);
		
	}

	/* (non-Javadoc)
	 * @see com.ibm.km.services.BulkUserService#bulkDelete(java.lang.String)
	 */
	public FileDto bulkDelete(String filePath) throws IOException, LMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList getBulkUserFiles(String circleId,String date) throws LMSException {
		BulkUserDao dao= new BulkUserDaoImpl();
		return dao.getBulkUserFiles(circleId,date);
	}
	public boolean emailValidation(String emailId) 
	   {
	      //Input the string for validation
	      String email = emailId;

	      //Set the email pattern string
	      Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

	      //Match the given string with the pattern
	      Matcher m = p.matcher(email);

	      //check whether match is found 
	      boolean matchFound = m.matches();

	      if (matchFound)
	       return true;
	      else
	        return false;
	   }
}

