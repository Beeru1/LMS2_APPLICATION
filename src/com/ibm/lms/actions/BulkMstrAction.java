//auhor---aman

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
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.BulkMsgDto;
import com.ibm.lms.dto.BulkMstrDTO;
import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.BulkMstrFormBean;
import com.ibm.lms.services.BulkMstrService;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.BulkMstrServiceImpl;
import com.ibm.lms.services.impl.UserMstrServiceImpl;

public class BulkMstrAction extends DispatchAction {

	private static Logger logger = Logger.getLogger(BulkMstrAction.class
			.getName());

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("Inside Bulk Master init method");
		if(Utility.isValidRequest(request)) {
        	return mapping.findForward("error");
        }
		ActionForward forward = new ActionForward(); // return value
		forward = mapping.findForward("initMstrBulk");
		BulkMstrFormBean bulkMstrFormBean =  (BulkMstrFormBean) form;
		String mstrType=bulkMstrFormBean.getMstrType();
		saveToken(request);
		try {
			bulkMstrFormBean.setIsError("false");
			bulkMstrFormBean.setDownloadTemplate("true");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	return (forward);
	}
	
	public ActionForward capabilityUpload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.info("Inside capabilityUpload method");
		if(Utility.isValidRequest(request)) {
        	return mapping.findForward("error");
        }
		ActionForward forward = new ActionForward(); // return value
		forward = mapping.findForward("CapabilityMstrBulk");
		BulkMstrFormBean bulkMstrFormBean =  (BulkMstrFormBean) form;
		
		saveToken(request);
		try {
			bulkMstrFormBean.setIsError("false");
			bulkMstrFormBean.setDownloadTemplate("true");

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
		UserMstrService userMstrService = new UserMstrServiceImpl();
		BulkMstrFormBean bulkMstrFormBean =  (BulkMstrFormBean) form;
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();

		/*if ( !isTokenValid(request) ) {
					  return mapping.findForward("error");
		}*/
		
		if(Integer.parseInt(request.getParameter("mstrType"))!=15)
		{
		userBean.setFileName("Bulk Master management");
		}
		else
		{
		userBean.setFileName("Channel Partner Capbility Upload");
		}
		try
		{
			bulkMstrFormBean.setDownloadTemplate("false");
			BulkMstrService  bulkMstrService = new BulkMstrServiceImpl();
			String mstrType=request.getParameter("mstrType");
			logger.info("mstrType::::::::::::::::::::::::::::::::::::::::"+mstrType);
			
			FormFile file = bulkMstrFormBean.getNewFile();
			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			if(!arr.equalsIgnoreCase("xls") && !arr.equalsIgnoreCase("xlsx"))
			{

				messages.add("msg1",new ActionMessage("lms.bulk.assignment.excel.only"));
				userBean.setMessage("FAILED");
			}
			else
			{
				BulkMsgDto  msgDto = bulkMstrService.uploadMstr(file,userBean,mstrType );
				switch(msgDto.getMsgId())
				{
				case Constants.BULK_UPLOAD_INVALID_EXCEL :
					logger.info("INVALID_EXCEL::::::::::::::action");
					messages.add("msg",new ActionMessage("lms.bulk.upload.invalid.excel"));
					bulkMstrFormBean.setIsError("false");
					bulkMstrFormBean.setDownloadTemplate("true");
					userBean.setMessage("FAILED");
					break;
					
				case Constants.BULK_UPLOAD_BLANK_EXCEL :
					logger.info("blank excel:::::action");
					messages.add("msg",new ActionMessage("lms.bulk.upload.blank.excel"));
					bulkMstrFormBean.setIsError("false");
					bulkMstrFormBean.setDownloadTemplate("true");
					userBean.setMessage("FAILED");
					break;
					
				case Constants.BULK_UPLOAD_FAIL :
					logger.info("blank excel:::::action");
					messages.add("msg",new ActionMessage("lms.bulk.upload.fail"));
					bulkMstrFormBean.setIsError("false");
					bulkMstrFormBean.setDownloadTemplate("true");
					userBean.setMessage("FAILED");
					break;
					
				case Constants.BULK_UPLOAD_SUCCESS :
					logger.info("blank excel:::::action");
					messages.add("msg",new ActionMessage("lms.bulk.upload.success"));
					request.setAttribute("filePath",msgDto.getFilePath());
					bulkMstrFormBean.setIsError("true");
					userBean.setMessage("UPLOADED SUCCESSFULLY");
					break;
					
				case Constants.INVALID_FILESIZE :
					logger.info("blank excel:::::action");
					messages.add("msg",new ActionMessage("lms.bulk.upload.invalid.filesize"));
					request.setAttribute("filePath",msgDto.getFilePath());
					bulkMstrFormBean.setIsError("false");
					userBean.setMessage("FAILED");
					break;
					
				}
				
				
			}

			saveMessages(request, messages);

		}//try
		catch(LMSException km)
		{
			messages.add("msg",new ActionMessage("lms.upload.invalid.excel"));
			bulkMstrFormBean.setIsError("false");	
			saveMessages(request, messages);
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
		
		if(Integer.parseInt(bulkMstrFormBean.getMstrType())== 15)
		{
		return mapping.findForward("CapabilityMstrBulk");
		}
		else
		{
			return mapping.findForward("initMstrBulk");		
		}
	}//uploadExcel

	public ActionForward openErrLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		
		HttpSession session = request.getSession();
		java.util.Date date = new java.util.Date();
		BulkMstrFormBean bulkMstrFormBean =  (BulkMstrFormBean) form;
		
		String filePath="",fileNameNew="";

		/*if ( !isTokenValid(request) ) {
					  return mapping.findForward("error");
					}*/
		try
		  {
			//filePath=(String)session.getAttribute("bulkLeadAssignmentErrLogFilePath");
			filePath=(String)request.getParameter("filePath");
			logger.info("filePath::::::::::::"+filePath);
			fileNameNew = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
			logger.info("fileNameNew::::::::::::"+fileNameNew);
			
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
		if(bulkMstrFormBean.getMstrType()== "15")
		{
		return mapping.findForward("CapabilityMstrBulk");
		}
		else
		{
		return (null);
		}
	}
	
	public ActionForward downloadTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("Inside BulkCityCreationAction downloadTemplate method");
		String filePath="",fileNameNew="";
		BulkMstrFormBean bulkMstrFormBean =  (BulkMstrFormBean) form;
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		UserMstrService userMstrService = new UserMstrServiceImpl();
		

		/*if ( !isTokenValid(request) ) {
					  return mapping.findForward("error");
					}*/
		try
		  {
			int intMstrType=Integer.parseInt(bulkMstrFormBean.getMstrType());
			
			if(intMstrType==1)
			{
				filePath=PropertyReader.getAppValue("lms.bulk.upload.download.template")+
				PropertyReader.getAppValue("lms.bulk.upload.template.zone.create");
				userBean.setFileName(PropertyReader.getAppValue("lms.bulk.upload.template.zone.create")+"for zone");
			}
			else if(intMstrType==2)
			{
				filePath=PropertyReader.getAppValue("lms.bulk.upload.download.template")+
				PropertyReader.getAppValue("lms.bulk.upload.template.city");
				userBean.setFileName(PropertyReader.getAppValue("lms.bulk.upload.template.city")+"for city");
			}
			else if(intMstrType==3)
			{
				filePath=PropertyReader.getAppValue("lms.bulk.upload.download.template")+
				PropertyReader.getAppValue("lms.bulk.upload.template.cityZone");
				userBean.setFileName(PropertyReader.getAppValue("lms.bulk.upload.template.cityZone")+"for city zone");
			}
			else if(intMstrType==4)
			{
				filePath=PropertyReader.getAppValue("lms.bulk.upload.download.template")+
				PropertyReader.getAppValue("lms.bulk.upload.template.pinCode");
				userBean.setFileName(PropertyReader.getAppValue("lms.bulk.upload.template.pinCode")+"for pincode");
			}
			else if(intMstrType==5)
			{
				filePath=PropertyReader.getAppValue("lms.bulk.upload.download.template")+
				PropertyReader.getAppValue("lms.bulk.upload.template.rsuCode");
				userBean.setFileName(PropertyReader.getAppValue("lms.bulk.upload.template.rsuCode")+"for RSU Code");
			}
			else if(intMstrType==6)
			{
				filePath=PropertyReader.getAppValue("lms.bulk.upload.download.template")+
				PropertyReader.getAppValue("lms.bulk.upload.template.autoAssignmntUpload");
				userBean.setFileName(PropertyReader.getAppValue("lms.bulk.upload.template.autoAssignmntUpload")+"for Autoassignmentupload");
			}
			else if(intMstrType==7)
			{
				filePath=PropertyReader.getAppValue("lms.bulk.upload.download.template")+
				PropertyReader.getAppValue("lms.bulk.upload.template.channelPartner");
				userBean.setFileName(PropertyReader.getAppValue("lms.bulk.upload.template.channelPartner")+"for channel partner");
			}
			else if(intMstrType==8)
			{
				filePath=PropertyReader.getAppValue("lms.bulk.upload.download.template")+
				PropertyReader.getAppValue("lms.bulk.upload.template.state");
				userBean.setFileName(PropertyReader.getAppValue("lms.bulk.upload.template.state")+"for state");
			}
			else if(intMstrType==9)
			{
				filePath=PropertyReader.getAppValue("lms.bulk.upload.download.template")+
				PropertyReader.getAppValue("lms.bulk.upload.template.agency");
				userBean.setFileName(PropertyReader.getAppValue("lms.bulk.upload.template.agency")+"for Agency");
			}

			else if(intMstrType==10)
			{
				filePath=PropertyReader.getAppValue("lms.bulk.upload.download.template")+
				PropertyReader.getAppValue("lms.bulk.upload.template.WorkFlowAutoAssignmnt");
				userBean.setFileName(PropertyReader.getAppValue("lms.bulk.upload.template.WorkFlowAutoAssignmnt")+"for workflow auto assignment");
			}
			else if(intMstrType==11)
			{
				filePath=PropertyReader.getAppValue("lms.bulk.upload.download.template")+
				PropertyReader.getAppValue("lms.bulk.upload.template.channelid");
				userBean.setFileName(PropertyReader.getAppValue("lms.bulk.upload.template.channelid")+"for channel id");
			}
			
			else if(intMstrType==15)
			{
				filePath=PropertyReader.getAppValue("lms.bulk.upload.download.template")+
				PropertyReader.getAppValue("lms.bulk.upload.template.channePartnerCapability");
				userBean.setFileName(PropertyReader.getAppValue("lms.bulk.upload.template.channePartnerCapability")+"for channel partner");
			}
			else if(intMstrType==12)
			{
				filePath=PropertyReader.getAppValue("lms.bulk.upload.download.template")+
				PropertyReader.getAppValue("lms.bulk.upload.template.EscalationUpload");
				userBean.setFileName(PropertyReader.getAppValue("lms.bulk.upload.template.EscalationUpload")+"for Escalation Upload");
			}
			fileNameNew = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
			logger.info("file name"+filePath);
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
			userBean.setMessage("SUCCESS");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			userBean.setMessage("FAILED");
			//userMstrService.insertBulkDataTransactionLogs(userBean);
		}
		userMstrService.insertBulkDataTransactionLogs(userBean);
		if(request.getParameter("mstrType")== "15")
		{
		return mapping.findForward("CapabilityMstrBulk");
		}
		else
		{
		return (null);

	}}

}

