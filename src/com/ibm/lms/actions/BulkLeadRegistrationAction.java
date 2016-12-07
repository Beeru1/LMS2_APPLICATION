package com.ibm.lms.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.ibm.lms.common.Constants;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.BulkLeadRegistrationFormBean;
import com.ibm.lms.services.BulkLeadRegistrationService;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.BulkLeadRegistrationServiceImpl;
import com.ibm.lms.services.impl.UserMstrServiceImpl;

public class BulkLeadRegistrationAction extends DispatchAction {

	private static Logger logger = Logger.getLogger(BulkLeadRegistrationAction.class
			.getName());

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("Inside BulkLeadRegistrationAction init method");

		ActionForward forward = new ActionForward(); // return value
		forward = mapping.findForward("bulkLeadRegistration");
		BulkLeadRegistrationFormBean bulkLeadRegistrationFormBean =  (BulkLeadRegistrationFormBean) form;
		saveToken(request);
		try {
			bulkLeadRegistrationFormBean.setIsError("false");
			bulkLeadRegistrationFormBean.setDownloadTemplate("true");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return (forward);

	}
	
	

	public ActionForward uploadOpenLeads(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
			{
		UserMstrService userMstrService = new UserMstrServiceImpl();
		UserMstr userBean = (UserMstr) request.getSession().getAttribute(
		"USER_INFO");
		userBean.setFileName("bulkOpenLeadRegistartion for upload");
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{ 
			BulkLeadRegistrationFormBean bulkLeadRegistrationFormBean =  (BulkLeadRegistrationFormBean) form;
			bulkLeadRegistrationFormBean.setDownloadTemplate("false");
			//userBean.setFileName("bulkLeadRegistartion.xls for upload");
			BulkLeadRegistrationService  bulkLeadRegistrationService = new BulkLeadRegistrationServiceImpl();
			ActionMessages messages = new ActionMessages();
			HttpSession session = request.getSession();

			FormFile file = bulkLeadRegistrationFormBean.getNewFile();
				
			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			if(!arr.equalsIgnoreCase("xls") && !arr.equalsIgnoreCase("xlsx"))
			{
				messages.add("msg1",new ActionMessage("lms.bulk.assignment.excel.only"));
				userBean.setMessage("FAILED");
			}
			else
			{
				BulkUploadMsgDto msgDto = bulkLeadRegistrationService.uploadOpenLeads(file,userBean);
				
				switch(msgDto.getMsgId())
				{
				case Constants.SERVICE_MSG_SUCCESS :
					messages.add("msg",new ActionMessage("lms.bulk.lead.upload.success"));
					session.setAttribute("bulkLeadRegistrationErrLogFilePath",msgDto.getMessage());
					bulkLeadRegistrationFormBean.setIsError("true");
					userBean.setMessage("UPLOADED SUCCESSFULLY");
					break;
					
				case Constants.SERVICE_MSG_FAIL :
					messages.add("msg",new ActionMessage("lms.bulk.assignment.failed"));
					session.setAttribute("bulkLeadRegistrationErrLogFilePath",msgDto.getMessage());
					bulkLeadRegistrationFormBean.setIsError("true");
					userBean.setMessage("PARTIALLY DONE OR FAIL");
					break;
					
				case Constants.INVALID_EXCEL :
					messages.add("msg",new ActionMessage("lms.bulk.upload.invalid.excel"));
					bulkLeadRegistrationFormBean.setIsError("false");
					bulkLeadRegistrationFormBean.setDownloadTemplate("true");
					userBean.setMessage("FAILED");
					break;
					
				case Constants.SERVICE_MSG_ERROR :
					messages.add("msg",new ActionMessage("lms.bulk.upload.error"));
					bulkLeadRegistrationFormBean.setIsError("false");
					userBean.setMessage("FAILED");
					break;
					
				case Constants.BLANK_EXCEL :
					messages.add("msg",new ActionMessage("lms.bulk.upload.blank.excel"));
					bulkLeadRegistrationFormBean.setIsError("false");
					userBean.setMessage("FAILED");
					break;	
					
					/* Added by Parnika */
					
				case Constants.INVALID_FILESIZE :
					messages.add("msg",new ActionMessage("lms.bulk.upload.invalid.filesize"));
					bulkLeadRegistrationFormBean.setIsError("false");
					userBean.setMessage("FAILED");
					break;
					
					/* End of changes by Parnika */
					
				}

			}

			saveMessages(request, messages);
			//userMstrService.insertBulkDataTransactionLogs(userBean);

		}//try
		catch(LMSException km)
		{
			logger.info("KM Exception occured in uploadExcel");
			userBean.setMessage("FAILED");
			//userMstrService.insertBulkDataTransactionLogs(userBean);

		}
		catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occured in uploadExcel");	
			userBean.setMessage("FAILED");
			//userMstrService.insertBulkDataTransactionLogs(userBean);
		}
		userMstrService.insertBulkDataTransactionLogs(userBean);
		return mapping.findForward("bulkLeadRegistration");	

			}//uploadExcel
	
	public ActionForward uploadQualifiedLeads(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
			{
		UserMstrService userMstrService = new UserMstrServiceImpl();
		UserMstr userBean = (UserMstr) request.getSession().getAttribute(
		"USER_INFO");
		userBean.setFileName("bulkQualifiedLeadRegistartion.xls for upload");
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{ 
			BulkLeadRegistrationFormBean bulkLeadRegistrationFormBean =  (BulkLeadRegistrationFormBean) form;
			bulkLeadRegistrationFormBean.setDownloadTemplate("false");
			BulkLeadRegistrationService  bulkLeadRegistrationService = new BulkLeadRegistrationServiceImpl();
			ActionMessages messages = new ActionMessages();
			HttpSession session = request.getSession();

			FormFile file = bulkLeadRegistrationFormBean.getNewFile();

			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			if(!arr.equalsIgnoreCase("xls") && !arr.equalsIgnoreCase("xlsx"))
			{
				messages.add("msg1",new ActionMessage("lms.bulk.assignment.excel.only"));
				userBean.setMessage("FAILED");
			}
			else
			{
				BulkUploadMsgDto msgDto = bulkLeadRegistrationService.uploadQualifiedLeads(file,userBean);
				
				switch(msgDto.getMsgId())
				{
				case Constants.SERVICE_MSG_SUCCESS :
					messages.add("msg",new ActionMessage("lms.bulk.lead.upload.success"));
					session.setAttribute("bulkLeadRegistrationErrLogFilePath",msgDto.getMessage());
					bulkLeadRegistrationFormBean.setIsError("true");
					userBean.setMessage("UPLOADED SUCCESSFULLY");
					break;
					
				case Constants.SERVICE_MSG_FAIL :
					messages.add("msg",new ActionMessage("lms.bulk.assignment.failed"));
					session.setAttribute("bulkLeadRegistrationErrLogFilePath",msgDto.getMessage());
					bulkLeadRegistrationFormBean.setIsError("true");
					userBean.setMessage("FAILED");
					break;
					
				case Constants.INVALID_EXCEL :
					messages.add("msg",new ActionMessage("lms.bulk.upload.invalid.excel"));
					bulkLeadRegistrationFormBean.setIsError("false");
					bulkLeadRegistrationFormBean.setDownloadTemplate("true");
					userBean.setMessage("FAILED");
					break;
					
				case Constants.SERVICE_MSG_ERROR :
					messages.add("msg",new ActionMessage("lms.bulk.upload.error"));
					bulkLeadRegistrationFormBean.setIsError("false");
					userBean.setMessage("FAILED");
					break;
					
				case Constants.BLANK_EXCEL :
					messages.add("msg",new ActionMessage("lms.bulk.upload.blank.excel"));
					bulkLeadRegistrationFormBean.setIsError("false");
					userBean.setMessage("FAILED");
					break;	
					
				}

			}

			saveMessages(request, messages);
			//userMstrService.insertBulkDataTransactionLogs(userBean);
		}//try
		catch(LMSException km)
		{
			logger.info("KM Exception occured in uploadExcel");
			userBean.setMessage("FAILED");
			//userMstrService.insertBulkDataTransactionLogs(userBean);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occured in uploadExcel");
			userBean.setMessage("FAILED");
			//userMstrService.insertBulkDataTransactionLogs(userBean);
		}
		userMstrService.insertBulkDataTransactionLogs(userBean);
		return mapping.findForward("bulkLeadRegistration");	

			}//uploadQualifiedLeads

	public ActionForward openErrLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("Inside BulkLeadRegistrationAction openErrLog method");
		HttpSession session = request.getSession();
		String filePath="",fileNameNew="";
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		  {
			filePath=(String)session.getAttribute("bulkLeadRegistrationErrLogFilePath");			
			fileNameNew = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
			
			response.setContentType("text/csv");
			response.addHeader("Content-Disposition", "attachment; filename="+fileNameNew);	
			
			java.io.File uFile= new java.io.File(filePath);
			int fSize=(int)uFile.length();
			java.io.FileInputStream fis = new java.io.FileInputStream(uFile);
			java.io.PrintWriter pw = response.getWriter();
			int c=-1;
			// Loop to read and write bytes.
			while ((c = fis.read()) != -1){
				pw.print((char)c);
			}
			// Close output and input resources.
			fis.close();
			pw.flush();
			pw=null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return (null);

	}
	
	public ActionForward downloadTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("Inside BulkLeadRegistrationAction downloadTemplate method");
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		UserMstrService userMstrService = new UserMstrServiceImpl();
		String filePath="",fileNameNew="";
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		  {
			String leadType = request.getParameter("leadType");
			filePath=PropertyReader.getAppValue("lms.bulk.upload.download.template");	
			
			logger.info("\nleadType "+leadType);
			
			if(leadType.equals("1"))
			{
				filePath+=PropertyReader.getAppValue("lms.bulk.upload.template.open.lead.register");
				userBean.setFileName(PropertyReader.getAppValue("lms.bulk.upload.template.open.lead.register")+" for bulklead registartion");
			}
			else{
				if(leadType.equals("2"))
					filePath+=PropertyReader.getAppValue("lms.bulk.upload.template.qualified.lead.register");
				userBean.setFileName(PropertyReader.getAppValue("lms.bulk.upload.template.qualified.lead.register")+" for bulklead registartion");
			}
			fileNameNew = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
			userBean.setMessage("SUCCESS");
			response.setContentType("text/csv");
			response.addHeader("Content-Disposition", "attachment; filename="+fileNameNew);	
			
			java.io.File uFile= new java.io.File(filePath);
			int fSize=(int)uFile.length();
			java.io.FileInputStream fis = new java.io.FileInputStream(uFile);
			java.io.PrintWriter pw = response.getWriter();
			int c=-1;
			// Loop to read and write bytes.
			while ((c = fis.read()) != -1){
				pw.print((char)c);
			}
			// Close output and input resources.
			fis.close();
			pw.flush();
			pw=null;
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			userBean.setMessage("FAILED");
			//userMstrService.insertBulkDataTransactionLogs(userBean);
		}
		userMstrService.insertBulkDataTransactionLogs(userBean);
		return (null);

	}

}
