package com.ibm.lms.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.ibm.km.forms.KmExcelUploadFormBean;
import com.ibm.km.services.KmExcelService;
import com.ibm.km.services.impl.KmExcelServiceImpl;
import com.ibm.lms.common.Constants;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;

/**
 * KmCompanyWisePlanAction
 * @author Anveeksha & Neeraj
 * For viewing bill plan rates based on user entered search
 * for Waiver Matrix Upload
 */
/**
*
Code 	Updated by     	  Date		  CSR NO				 Defect ID		    Description
------------------------------------------------------------------------------------------------------------------------------------------------------------
0001	Neeraj Aggarwal   11 MAY 09	  20081211-00-03016	     MASDB00111714  	Disallowing uploading if there are multiple bill plans with same name 
* 
*/
public class ExcelUploadAction extends DispatchAction {

	/**
	 * Logger for the class.
	**/
	private static final Logger logger;

	static {

		logger = Logger.getLogger(ExcelUploadAction.class);
	}

	/**
	 * Initializes the waiver matrix upload page
	**/
	public ActionForward initExcelUpload(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
			
				KmExcelUploadFormBean formBean = (KmExcelUploadFormBean)form;
				ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
				formBean.setFilePath("");
				HttpSession session = request.getSession();
				UserMstr userBean = (UserMstr) session.getAttribute("USER_INFO");	
				saveToken(request);
				if(userBean.getKmActorId().equals(Constants.CIRCLE_ADMIN)&&userBean.isRestricted()==true)
					return mapping.findForward("kmExcelAction");
				else{
					request.setAttribute("RES", bundle.getString("excel.authorization.error"));
					return mapping.findForward("home");
				
				}	
	}
	
	/**
	 * Save the uploaded file physically on server 
	**/
	public ActionForward initExcelProcessPopUp(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
				KmExcelUploadFormBean formBean = (KmExcelUploadFormBean)form;
				FormFile f = formBean.getNewFile();
				File file = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
				String date = sdf.format(new java.util.Date());
				date = date.substring(0,10);
			HttpSession session = request.getSession();
				UserMstr userBean = (UserMstr) session.getAttribute("USER_INFO");	
				ActionErrors errors= new ActionErrors();
				ActionMessages messages=new ActionMessages();
				saveToken(request);
				try
				{
				/*	if(!userBean.getKmActorId().equals(Constants.CIRCLE_ADMIN)||userBean.isRestricted()==false){
						errors.add("", new ActionError("excel.authorization.error"));
						saveErrors(request,errors);
						return mapping.findForward("kmExcelAction");
					}*/
					String strFile = f.getFileName();
					String ext = strFile.substring(strFile.lastIndexOf(".")+1);
					String fileName = strFile.substring(0,strFile.lastIndexOf("."))+ date +ext;
					formBean.setFilePath(bundle.getString("excel.location")+fileName);					
					if(f.getFileSize()>4194304)
					{
						errors.add("", new ActionError("excel.errorSize"));
						saveErrors(request,errors);
						formBean.setFilePath("");
					}
					else
					{	
						//Saving file physically
						file = new File(formBean.getFilePath());
						
						byte[] b=f.getFileData();
						RandomAccessFile raf= new RandomAccessFile(file,"rw");
						raf.write(b);
						raf.close();
						formBean.setMethodName("processExcel");
						request.setAttribute("FILE_OBJECT", file.getPath());
					}	
				}
				catch(Exception e)
				{
					errors.add("", new ActionError("excel.error1"));
					saveErrors(request,errors);
				}
												
				return mapping.findForward("kmExcelAction");
		}
	
	/**
	 * Calls the pop-up that disables user action on parent page while data from excel is being saved to temporary tables
	**/
	public ActionForward initProcessExcel(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
				
				saveToken(request);
				KmExcelUploadFormBean formBean = (KmExcelUploadFormBean)form;
				formBean.setDetailStatus(0);
				formBean.setMethodName("initExcelProcessPopUp");
				return mapping.findForward("kmExcelDisplay");
	}
	
	/**
	 * Starts saving the data from uploaded excel to temporary tables
	**/	
	public ActionForward processExcel(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
				
				KmExcelUploadFormBean formBean = (KmExcelUploadFormBean)form;
				ActionErrors errors= new ActionErrors();
				ActionMessages messages=new ActionMessages();
		
				ArrayList combinedArrayList= new ArrayList();
				KmExcelService servobj=new KmExcelServiceImpl();
				/*if ( !isTokenValid(request) ) {
					  return mapping.findForward("error");
					}*/
				try
				{	//0001  Start 
					combinedArrayList=servobj.createTempTable(formBean.getFilePath());
					
					formBean.setMissingPlanRateData((ArrayList)combinedArrayList.get(0));
					formBean.setDuplicateBillPlanRates((ArrayList)combinedArrayList.get(1));
					formBean.setDuplicateBillPlanRatesPackagId((ArrayList)combinedArrayList.get(2));
					//0001  End
					formBean.setDetailStatus(1);
				}
				catch(LMSException ke){
					ke.printStackTrace();
					formBean.setDetailStatus(2);
					errors.add("", new ActionError("excel.tempateError"));
					saveErrors(request,errors);
				}
				catch(Exception e){
					e.printStackTrace();
					formBean.setDetailStatus(2);
					errors.add("", new ActionError("excel.error1"));
					saveErrors(request,errors);
					
				}
				return mapping.findForward("kmExcelDisplay");
		
	}
	
	/**
	 * Saves the data from temporary tables to master tables
	**/
	public ActionForward saveData(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
			KmExcelUploadFormBean formBean = (KmExcelUploadFormBean)form;
				formBean.setMethodName("saveData");
				KmExcelService servobj=new KmExcelServiceImpl();
				UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
				ActionErrors errors= new ActionErrors();
				ActionMessages messages=new ActionMessages();
				
				/*if ( !isTokenValid(request) ) {
					  return mapping.findForward("error");
					}*/
				try
				{	
					servobj.updateMasterTables(userBean.getUserLoginId(),formBean.getFilePath());
				}
				catch(Exception e){
					errors.add("", new ActionError("excel.error1"));
					saveErrors(request,errors);
				}
				return mapping.findForward("kmExcelDisplay");
	}
	
	public ActionForward downloadDocument(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
				ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
				String filePath = bundle.getString("excel.template");
				String fileName = filePath.substring((filePath.lastIndexOf("/") + 1),filePath.length());
				File f = new File(filePath);
				response.setContentType("application/vnd.ms-excel");
				response.setContentLength((int) f.length());
				response.setHeader("Content-Disposition", "attachment; filename="+ fileName);
				response.setHeader("Cache-Control", "no-cache");
				OutputStream outStream = response.getOutputStream();
				FileInputStream inStream = new FileInputStream(f);
				/*if ( !isTokenValid(request) ) {
					  return mapping.findForward("error");
					}*/
				try 
				{
					byte[] buf = new byte[8192];
					int sizeRead = 0;
					while ((sizeRead = inStream.read(buf, 0, buf.length)) > 0) 
					{
						log.debug("size:" + sizeRead);
						outStream.write(buf, 0, sizeRead);
					}
					inStream.close();
					outStream.close();
				}
				catch (IllegalStateException ignoredException) 
				{
					if (outStream != null) 
					{
						outStream.close();
					}
					if (inStream != null) 
					{
						inStream.close();
					}
				}
				return null;
		}

}
