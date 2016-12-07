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
import com.ibm.lms.forms.BulkUserCreationFormBean;
import com.ibm.lms.services.BulkUserCreationService;
import com.ibm.lms.services.impl.BulkUserCreationServiceImpl;

public class BulkUserCreationAction extends DispatchAction {

	private static Logger logger = Logger.getLogger(BulkUserCreationAction.class
			.getName());

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("Inside BulkUserCreationAction init method");

		ActionForward forward = new ActionForward(); // return value
		forward = mapping.findForward("bulkUserCreation");
		BulkUserCreationFormBean bulkUserCreationFormBean =  (BulkUserCreationFormBean) form;
		saveToken(request);
		try {
			bulkUserCreationFormBean.setIsError("false");
			bulkUserCreationFormBean.setDownloadTemplate("true");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return (forward);

	}

	public ActionForward uploadExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
			{

		String errLogFilePath = "";
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/

		try
		{

			BulkUserCreationFormBean bulkUserCreationFormBean =  (BulkUserCreationFormBean) form;
			bulkUserCreationFormBean.setDownloadTemplate("false");
			BulkUserCreationService  bulkUserCreationService = new BulkUserCreationServiceImpl();
			ActionMessages messages = new ActionMessages();
			HttpSession session = request.getSession();

			FormFile file = bulkUserCreationFormBean.getNewFile();

			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			if(!arr.equalsIgnoreCase("xls")  && !arr.equalsIgnoreCase("xlsx")  )
			{

				messages.add("msg1",new ActionMessage("lms.bulk.assignment.excel.only"));
			}
			else
			{
				BulkUploadMsgDto msgDto = bulkUserCreationService.uploadUsers(file,userBean);
				
				switch(msgDto.getMsgId())
				{
				case Constants.SERVICE_MSG_SUCCESS :
					messages.add("msg",new ActionMessage("lms.bulk.user.success"));
					bulkUserCreationFormBean.setIsError("false");
					break;
					
				case Constants.SERVICE_MSG_FAIL :
					messages.add("msg",new ActionMessage("lms.bulk.assignment.failed"));
					session.setAttribute("bulkUserCreationtErrLogFilePath",msgDto.getMessage());
					bulkUserCreationFormBean.setIsError("true");
					break;
					
				case Constants.INVALID_EXCEL :
					messages.add("msg",new ActionMessage("lms.bulk.upload.invalid.excel"));
					bulkUserCreationFormBean.setIsError("false");
					bulkUserCreationFormBean.setDownloadTemplate("true");
					break;
					
				case Constants.SERVICE_MSG_ERROR :
					messages.add("msg",new ActionMessage("lms.bulk.upload.error"));
					bulkUserCreationFormBean.setIsError("false");
					break;
					
				case Constants.BLANK_EXCEL :
					messages.add("msg",new ActionMessage("lms.bulk.upload.blank.excel"));
					bulkUserCreationFormBean.setIsError("false");
					break;
				}
			}

			saveMessages(request, messages);

		}//try
		catch(LMSException km)
		{
			logger.info("KM Exception occured in uploadExcel");

		}
		catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occured in uploadExcel");	
		}

		return mapping.findForward("bulkUserCreation");	

			}//uploadExcel

	public ActionForward openErrLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("Inside BulkUserCreationAction openErrLog method");
		HttpSession session = request.getSession();
		java.util.Date date = new java.util.Date();
		String filePath="",fileNameNew="";
		
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		  {
			filePath=(String)session.getAttribute("bulkUserCreationtErrLogFilePath");			
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

		logger.info("Inside BulkUserCreationAction downloadTemplate method");
		String filePath="",fileNameNew="";
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		
		try
		  {
			filePath=PropertyReader.getAppValue("lms.bulk.upload.download.template")+
			PropertyReader.getAppValue("lms.bulk.upload.template.user.creation");
			
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

}
