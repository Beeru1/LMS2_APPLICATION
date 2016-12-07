package com.ibm.lms.actions;

/**
 * @author Parnika Sharma
 */


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.ibm.lms.forms.MasterDataFormBean;
import com.ibm.lms.services.CircleManagementService;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.CircleManagementServiceImpl;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.services.impl.UserMstrServiceImpl;


public class MasterDataDownloadAction extends DispatchAction {
	
	private static Logger logger = Logger.getLogger(MasterDataDownloadAction.class.getName());
	
	public ActionForward initDownload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		if(Utility.isValidRequest(request)) {
        	return mapping.findForward("error");
        }
		ActionForward forward = new ActionForward(); // return value
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initDownload method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		saveToken(request);
		MasterDataFormBean masterBean = (MasterDataFormBean) form;
		MasterService masterService = new MasterServiceImpl();
		CircleManagementService circleManagementService = new CircleManagementServiceImpl();
		
		// Populating Download Type Dropdown
		
		masterBean.setMasterTableList(masterService.getMasterList());
		masterBean.setLobList(circleManagementService.getLobList());
		
		forward = mapping.findForward("initDownloadData");
		logger.info(UserDetails.getUserLoginId(request)+" : Exiting initDownload method");
		// Finish with
		return (forward);  

	}
	
	public ActionForward downloadData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		logger.info(UserDetails.getUserLoginId(request)+" : Entered downLoadData method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
		userBean.setFileName("Master data download");
		MasterDataFormBean formBean = (MasterDataFormBean) form;
		MasterService masterService = new MasterServiceImpl();
		UserMstrService userMstrService = new UserMstrServiceImpl();
		
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try{
			
			formBean.setMessage("");
			logger.info("Selected Master Table "+formBean.getSelectedMasterId());
			int masterTableId = formBean.getSelectedMasterId();
			int prodLobId = formBean.getSelectedLobId();
			logger.info("selected lob id::::::::::::::::::::;	"+formBean.getSelectedLobId());
			String data = masterService.getMasterTableData(masterTableId, prodLobId);
			logger.info("Master table data..........");
			
			if(data == null || data.equals("")){
				formBean.setMessage("Master Data Not Found");
				errors.add("masterdata.not.found", new ActionError("masterdata.not.found"));
				saveErrors(request, errors);
				
				// Populating Report Type Dropdown
				
				formBean.setMasterTableList(masterService.getMasterList());
				formBean.setSelectedMasterId(-1);
				//userBean.setMessage("FAILED");
				return mapping.findForward("viewMasterData");
			}
			
			response.setContentType ("application/vnd.ms-excel");			
			response.setHeader("Content-Disposition","attachment; filename=MasterData.csv");
			OutputStream outStr =response.getOutputStream();
			OutputStreamWriter outStream = new OutputStreamWriter(outStr);
			InputStream is = new ByteArrayInputStream(data.getBytes());
			InputStreamReader inStr = new InputStreamReader(is);
			BufferedReader inStream = new BufferedReader(inStr);
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
			 userBean.setMessage("SUCCESS");
			
		} catch (Exception e) {
			formBean.setMessage("Master Data Not Found");
			errors.add("masterdata.not.found", new ActionError("masterdata.not.found"));
			saveErrors(request, errors);
			// Populating Report Type Dropdown
			userBean.setMessage("FAILED");
			formBean.setMasterTableList(masterService.getMasterList());
		}
		// Finish with
		 userMstrService.insertBulkDataTransactionLogs(userBean);
		return null;

	}	

}
