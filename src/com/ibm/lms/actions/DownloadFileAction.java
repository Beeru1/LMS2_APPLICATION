package com.ibm.lms.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.ibm.lms.forms.DownloadFileFormBean;

public class DownloadFileAction extends DispatchAction{
	
	private static Logger logger = Logger.getLogger(DownloadFileFormBean.class
			.getName());

	public ActionForward downloadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DownloadFileFormBean downloadFileFormBean =  (DownloadFileFormBean) form;
	
		logger.info("Inside DownloadFileAction init method");
		HttpSession session = request.getSession();
		
		ActionForward forward = new ActionForward(); // return value
		forward = mapping.findForward("downloadFile");
		

		try {
			logger.info("In download error log");
			downloadFileFormBean.setErrLogFilePath((String)session.getAttribute("errLogFilePath"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return (forward);

	}

}
