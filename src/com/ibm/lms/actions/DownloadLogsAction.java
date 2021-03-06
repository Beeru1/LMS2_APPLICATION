package com.ibm.lms.actions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.ibm.lms.common.UserDetails;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.forms.DownloadLogsFormBean;
import com.ibm.lms.services.LogsService;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.impl.LogsServiceImpl;
import com.ibm.lms.services.impl.MasterServiceImpl;

public class DownloadLogsAction extends DispatchAction {
	
private static Logger logger = Logger.getLogger(DownloadLogsAction.class.getName());
private static int SMSlogsId = 12;
private static int EmaillogsId = 13;
private static int uploadFilelogsId = 14;
private static int userMstrHistlogsId = 15;
private static int loginlogoutid = 16;
private static int AssignmentmatrixlogsId=17;
private static int LeadsearchTransactionlogs=18;
private static int LeadCaptureDownloadLogs = 26;
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initView method");
		saveToken(request);
		MasterService masterService = new MasterServiceImpl();
		DownloadLogsFormBean logsFormBean = (DownloadLogsFormBean) form;
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
		String date = sdf.format(new java.util.Date().getTime());
		date = date.substring(0,10);
		logger.info(date.toString());
		logsFormBean.setLogDate(date);
		//int recordslimitdays = Integer.parseInt(PropertyReader.getAppValue("records.limit.days"));
		
		
		if(Utility.isValidRequest(request)) {
        	return mapping.findForward("error");
        }
		
		String role =userBean.getKmActorName();
		if("SuperMasterAdmin".equalsIgnoreCase(role)){
			request.setAttribute("recordslimitdays", masterService.getParameterName("LOGDATE_FLAG_SUPERMASTER"));
			request.setAttribute("recordslimitdaysForId", masterService.getParameterName("LOGDATE_ID_FLAG_SUPERMASTER"));
		}
		else{
		request.setAttribute("recordslimitdays", masterService.getParameterName("LOGDATE_FLAG"));
		request.setAttribute("recordslimitdaysForId", masterService.getParameterName("LOGDATE_ID_FLAG"));
		}
		
		logsFormBean.setLogTypeList(masterService.getLogsList());
		forward = mapping.findForward("initLogs");
		logger.info(UserDetails.getUserLoginId(request)+" : Exiting init Logs method");
		return (forward);  
		
	}
	
	public ActionForward viewLogs(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
    	logger.info("Inside view Logs method");
		ActionErrors errors = new ActionErrors();
		DownloadLogsFormBean formBean = (DownloadLogsFormBean) form;
		MasterService masterService = new MasterServiceImpl();
		LogsService logsService = new LogsServiceImpl();
		String fileName="";

		try{
			
			formBean.setMessage("");
			int llogsId = formBean.getLlogsId();
			String logsData = logsService.getLogsData(llogsId,formBean);
			formBean.setMessage("");
			
			logger.info("Selected date "+formBean.getLogDate());	
			
				//int llogsId = formBean.getLlogsId();
		/*		
			if(llogsId==SMSlogsId)
			{
				fileName="SMSLogs";
				//logger.info("hiiiiiiiiiiiiiiii");
				forwardName="viewUserSMSLogs";
				logsData = logsService.getSMSLogs(formBean);
				request.setAttribute("logsSMSList",logsData);
				
			}
			else if(llogsId==EmaillogsId)
			{
				fileName="EmailLogs";
				forwardName="viewUserEmailLogs";
				//logger.info("byee.........");
				logsData=logsService.getEmailLogs(formBean);
				request.setAttribute("logsEmailList",logsData);
			}
		
			else if(llogsId==uploadFilelogsId)
			{
				fileName = "DownloadFileLogs";
				forwardName="viewImportExportFileLogs";
				logsData=logsService.getDownloadFilesLogs(formBean);
				request.setAttribute("logsDownloadFileList",logsData);
				
			}
			else if(llogsId==userMstrHistlogsId)
			{
				fileName = "UserMstrHistLogs";
				forwardName="viewUserMstrHistLogs";
				logsData=logsService.getUserMstrHistLogs(formBean);
				request.setAttribute("logsUserMstrHistList",logsData);
			}
			else if(llogsId==loginlogoutid)
			{
				fileName = "Login/Logut";
				forwardName="viewUserloginlogout";
				logsData=logsService.getUserMstrHistLogs(formBean);
				request.setAttribute("logsUserMstrHistList",logsData);
			}*/	
			/*resetToken(request);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("content-Disposition","attachment;filename="+fileName+".csv");
			//userMstrService.insertBulkDataTransactionLogs(userBean);*/
			
			if(llogsId==SMSlogsId)
			{
				fileName="SMSLogs";
			}
			else if(llogsId==EmaillogsId)
			{
				fileName="EmailLogs";
			}
			else if(llogsId==uploadFilelogsId)
			{
				fileName = "DownloadFileLogs";
			}
			else if(llogsId==userMstrHistlogsId)
			{
				fileName = "UserMstrHistLogs";
			}
			else if(llogsId==loginlogoutid)
			{
				fileName = "Login/LogoutLogs";
			}
			else if(llogsId==AssignmentmatrixlogsId)
			{
				fileName = "AssignmentMatrixLogs";
			}
			else if(llogsId==LeadsearchTransactionlogs)
			{
				fileName = "LeadSearchTrans";
			}
			else if(llogsId==LeadCaptureDownloadLogs)
			{
				fileName = "LeadCaptureDownloadLogs";
			}else {
				
				return init(mapping, formBean, request, response);
			}
			
			
			if(logsData == null || logsData.isEmpty()){
				formBean.setMessage("Logs Data Not Found");
				errors.add("Logs.not.found", new ActionError("LogsData.not.found"));
				saveErrors(request, errors);
				
				// Populating Report Type Dropdown
				
				formBean.setLogTypeList(masterService.getLogsList());
				formBean.setLlogsId(-1);
				//userBean.setMessage("FAILED");
				//logger.info("ERORRRRRRRRRRR");
				return mapping.findForward("viewLogs");
			}
			//resetToken(request);
			response.setContentType ("application/vnd.ms-excel");
			response.setHeader("Content-Disposition","attachment; filename="+fileName+".csv");
			OutputStream outStr =response.getOutputStream();
			OutputStreamWriter outStream = new OutputStreamWriter(outStr);
			InputStream is = new ByteArrayInputStream(logsData.getBytes());
			InputStreamReader inStr = new InputStreamReader(is);
			BufferedReader inStream = new BufferedReader(inStr);
			//logger.info("Show logsdatttttttttttttt"+logsData);
			formBean.setLlogsId(-1);
			//return mapping.findForward(forwardName);
			try {	 
				 
				 char[] buf = new char[8192];
				
				 int sizeRead = 0;
				 
				 while ((sizeRead = inStream.read(buf, 0, buf.length)) > 0) {
					
					 outStream.write(buf, 0, sizeRead);
				 }
				 inStream.close();
				 outStream.close();
			}
			 catch(IllegalStateException
						ignoredException) {
				if(outStream!=null){
					outStream.close();			
				}
				if(inStream!=null){
					inStream.close();			
				}
				logger.error(ignoredException);
			}
			//PrintWriter writer = response.getWriter();
			//writer.write(data);
			//writer.close();
			 
			
			} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error-----"+e);
			formBean.setMessage("Logs Not Found");
			errors.add("logs.not.found", new ActionError("logs.not.found"));
			saveErrors(request, errors);
			// Populating Report Type Dropdown
			
			formBean.setLogTypeList(masterService.getLogsList());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
			String startDate = sdf.format(new java.util.Date().getTime() - 24*60*60*1000);
			startDate = startDate.substring(0,10);
			String endDate = sdf.format(new java.util.Date().getTime());
			endDate = endDate.substring(0,10);
			logger.info(endDate.toString());
			formBean.setStartDate(startDate);
			formBean.setEndDate(endDate);
			
		}
		// Finish with
		//return init(mapping, formBean, request, response);
			//logger.info("FLA"+formBean.getFlagStatus());
			formBean.setFlagStatus("hi");
			//logger.info("FLA1111111111111"+formBean.getFlagStatus());
			return null;

	}
}
