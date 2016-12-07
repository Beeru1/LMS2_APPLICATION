package com.ibm.lms.actions;

import java.util.List;

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
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.BulkAssigmentFormBean;
import com.ibm.lms.services.BulkAssigmentService;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.BulkAssigmentServiceImpl;
import com.ibm.lms.services.impl.UserMstrServiceImpl;

public class BulkAssigmentAction extends DispatchAction {

	private static Logger logger = Logger.getLogger(BulkAssigmentAction.class
			.getName());

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("Inside BulkAssigmentAction init method");
		if(Utility.isValidRequest(request)) {
        	return mapping.findForward("error");
        }
		ActionForward forward = new ActionForward(); // return value
		forward = mapping.findForward("bulkAssignment");
		BulkAssigmentFormBean bulkAssigmentFormBean =  (BulkAssigmentFormBean) form;
		
		saveToken(request);
		try {
			bulkAssigmentFormBean.setIsError("false");
			bulkAssigmentFormBean.setDownloadTemplate("true");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return (forward);

	}

	public ActionForward uploadExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
			{

		
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		userBean.getIpaddress();
		BulkAssigmentFormBean bulkAssigmentFormBean =  (BulkAssigmentFormBean) form;
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		UserMstrService userMstrService = new UserMstrServiceImpl();
		userBean.setFileName("bulkAssignmentMatrix.xls for upload");
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{
			bulkAssigmentFormBean.setDownloadTemplate("false");
			BulkAssigmentService  bulkAssigmentService = new BulkAssigmentServiceImpl();
			
			FormFile file = bulkAssigmentFormBean.getNewFile();

			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			if(!arr.equalsIgnoreCase("xls") && !arr.equalsIgnoreCase("xlsx"))
			{

				messages.add("msg1",new ActionMessage("lms.bulk.assignment.excel.only"));
				userBean.setMessage("FAILED");
			}
			else
			{

				BulkUploadMsgDto msgDto = bulkAssigmentService.uploadAssignmentMatrix(file,userBean);
	
				switch(msgDto.getMsgId())
				{
				case Constants.SERVICE_MSG_SUCCESS :
					messages.add("msg",new ActionMessage("lms.bulk.assignment.success"));
					bulkAssigmentFormBean.setIsError("false");
					userBean.setMessage("UPLOADED SUCESSFULLY");
					break;
					
				case Constants.SERVICE_MSG_FAIL :
					messages.add("msg",new ActionMessage("lms.bulk.assignment.failed"));
					
					session.setAttribute("bulkLeadAssignmentErrLogFilePath",msgDto.getMessage());
					bulkAssigmentFormBean.setIsError("true");
					userBean.setMessage("PARTIALLY DONE OR FAIL");
					break;
					
				case Constants.INVALID_EXCEL :
					messages.add("msg",new ActionMessage("lms.bulk.upload.invalid.excel"));
					bulkAssigmentFormBean.setIsError("false");
					bulkAssigmentFormBean.setDownloadTemplate("true");
					userBean.setMessage("FAILED");
					break;
					
				case Constants.SERVICE_MSG_ERROR :
					messages.add("msg",new ActionMessage("lms.bulk.assignment.error"));
					bulkAssigmentFormBean.setIsError("false");
					userBean.setMessage("FAILED");
					break;
					
				case Constants.BLANK_EXCEL :
					messages.add("msg",new ActionMessage("lms.bulk.upload.blank.excel"));
					bulkAssigmentFormBean.setIsError("false");
					userBean.setMessage("FAILED");
					break;
					
				case Constants.SERVICE_PENDING_ERROR :
					messages.add("msg",new ActionMessage("lms.bulk.ASSIGNMENT.UPLOAD.error"));
					bulkAssigmentFormBean.setIsError("false");
					userBean.setMessage("FAILED");
					break;
					
				}

			}
			//userMstrService.insertBulkDataTransactionLogs(userBean);
			saveMessages(request, messages);

		}//try
		catch(LMSException km)
		{
			messages.add("msg",new ActionMessage("lms.upload.invalid.excel"));
			bulkAssigmentFormBean.setIsError("false");	
			
			userBean.setMessage("FAILED");
			saveMessages(request, messages);
			//userMstrService.insertBulkDataTransactionLogs(userBean);
			logger.info("KM Exception occured in uploadExcel");
		}
		catch (Exception e) {
			e.printStackTrace();
			userBean.setMessage("FAILED");
			//userMstrService.insertBulkDataTransactionLogs(userBean);
			logger.info("Error occured in uploadExcel");	
		}
		userMstrService.insertBulkDataTransactionLogs(userBean);
		return mapping.findForward("bulkAssignment");	
	}//uploadExcel

	public ActionForward openErrLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("Inside BulkAssigmentAction openErrLog method");
		HttpSession session = request.getSession();
		//java.util.Date date = new java.util.Date();
		String filePath="",fileNameNew="";
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		  {
			filePath=(String)session.getAttribute("bulkLeadAssignmentErrLogFilePath");
			fileNameNew = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
			
			response.setContentType("text/csv");
			response.addHeader("Content-Disposition", "attachment; filename="+fileNameNew);	
			
			java.io.File uFile= new java.io.File(filePath);
			int fSize =(int)uFile.length();
			logger.info("file size "+fSize);
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

		logger.info("Inside BulkAssignmentAction downloadTemplate method");
		String filePath="",fileNameNew="";
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
		UserMstrService userMstrService = new UserMstrServiceImpl();
		userBean.setFileName("bulkAssignmentMatrix.xls for Bulk assignment creation");
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		  {
			filePath=PropertyReader.getAppValue("lms.bulk.upload.download.template")+
			PropertyReader.getAppValue("lms.bulk.upload.template.lead.assignment");
			
			fileNameNew = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
			
			response.setContentType("text/csv");
			response.addHeader("Content-Disposition", "attachment; filename="+fileNameNew);	
			
			java.io.File uFile= new java.io.File(filePath);
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
			userBean.setMessage("SUCCESS");
			//userMstrService.insertBulkDataTransactionLogs(userBean);
		}
		catch(Exception e)
		{
			userBean.setMessage("FAILED");
			//userMstrService.insertBulkDataTransactionLogs(userBean);
			e.printStackTrace();
		}
		userMstrService.insertBulkDataTransactionLogs(userBean);
		return (null);

	}
	
	public ActionForward viewAssignmentMatrixStatus(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		logger.info("Inside viewAssignmentMatrixStatus method");
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
		BulkAssigmentService userMstrService = new BulkAssigmentServiceImpl();
		List masterList=null;
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		  {
			masterList=	userMstrService.viewAssignmentMatrixStatus(userBean);
			if(masterList !=null && masterList.size() > 0)
			request.setAttribute("masterList", masterList);
		  }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return mapping.findForward("bulkAssignment");	

	}
}
