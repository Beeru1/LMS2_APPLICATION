/**
 * 
 */
package com.ibm.lms.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.lms.common.UserDetails;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.FormIdDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.forms.BulkAssignmentDownloadFormBean;
import com.ibm.lms.forms.LoginFormBean;
import com.ibm.lms.services.ManageFormIdService;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.ManageFormIdServiceImpl;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.services.impl.UserMstrServiceImpl;
import com.ibm.lms.wf.dto.BulkMatrixDownloadDTO;
import com.ibm.lms.wf.forms.FormIdBean;

/**
 * @author Nehil Parashar
 *
 */
public class ManageFormIdAction extends DispatchAction
{
	private static final String INIT 			= "initCreate";
	private static final String INIT_EDIT 		= "initView";
	private static final String CREATE_FID		= "createFid";
	private static final String DELETE_FID		= "deleteFid";
	private static final String GET_FID_DATA	= "getFidData";
	private static final String UPDATE_FID		= "updateFid";
	private static final String EDIT_FID		= "editFidData";
	private static final String USER_INFO		= "USER_INFO";
	private static final String DEFAULT_STATUS	= "A";
	
	UserMstrService userMstrService = new UserMstrServiceImpl();
	/**
	 * log4j logger
	 */
	private static Logger logger = Logger.getLogger(ManageFormIdAction.class);
	
	
	/**
	 * Gets the current user logged in
	 * 
	 * @param request
	 * @return
	 */
	private String getUpdatedBy(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		UserMstr sessionUserBean =(UserMstr) session.getAttribute(USER_INFO);
		return sessionUserBean.getUserLoginId();
	}
	
	
	/**
	 * This method is called when User clicks on 'Form Id Creation' link.  
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("Inside init method of ManageFormIdAction");
		ActionForward forward = null;
		saveToken(request);
		try
		{
			if(Utility.isValidRequest(request)) {
	        	return mapping.findForward("error");
	        }
			forward = mapping.findForward(INIT);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		return forward;
	}
	
	/**
	 * This method is called when User clicks on 'View/Edit' link.
	 * 
	 * @param mapping
	 * @param form
	 * @return
	 */
	public ActionForward initEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception

	{
		logger.info("Inside initEdit method of ManageFormIdAction");
		ActionForward forward = null;
		saveToken(request);
		try
		{
			if(Utility.isValidRequest(request)) {
	        	return mapping.findForward("error");
	        }
			
			forward = mapping.findForward(INIT_EDIT);
			request.setAttribute("HIDE_FIELDS", true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return forward;
	}
	
	/**
	 * This method is called when user submits a Page URL.
	 * by default the status is taken as A i.e. Active
	 * 
	 * Possible status are Active (A), Inactive (I) and Deleted (D).
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createFormId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
	{
		logger.info("Inside createFormId method of ManageFormIdAction");
		
		ActionForward forward = null;
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{	
			FormIdBean formIdBean = (FormIdBean) form;
			
			FormIdDto fiddto = new FormIdDto();
			fiddto.setPageUrl(formIdBean.getPageUrl().trim());
			fiddto.setStatus(DEFAULT_STATUS);
			fiddto.setUpdatedBy(getUpdatedBy(request));
			
			ManageFormIdService fidService = new ManageFormIdServiceImpl();
			formIdBean.setFid(fidService.createFid(fiddto)+"");
			
			// set in request and retrieved at corresponding jsp
			request.setAttribute("FORM_ID", formIdBean.getFid());

			forward = mapping.findForward(CREATE_FID);
			
			logger.info("Fid created successfully " + formIdBean.getFid());
			return forward;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteFormId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
	{
		logger.info("Inside deleteFormId method of ManageFormIdAction");
		
		ActionForward forward = null;
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{
			FormIdBean formIdBean = (FormIdBean) form;
			FormIdDto fiddto = new FormIdDto();
			
			fiddto.setFid(Long.parseLong(formIdBean.getFid()));
			fiddto.setUpdatedBy(getUpdatedBy(request));
			
			ManageFormIdService fidService = new ManageFormIdServiceImpl();
			fidService.deleteData(fiddto);
			
			forward = mapping.findForward(DELETE_FID);
			logger.info("FID data deleted correctly (Marked status as D)");
			return forward;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}


	/**
	 * This method is called when user inputs a Form Id to 
	 * view its URL and Status.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getFidData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
	{
		logger.info("Inside getFidData method of ManageFormIdAction");
		FormIdDto aDto = null;
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{
			ActionForward forward = null;
			
			FormIdBean formIdBean = (FormIdBean) form;
			String fid = formIdBean.getFid();
			
			ManageFormIdService fidService = new ManageFormIdServiceImpl();
			
			if(fid !=null && fid.length() >0){
				fid = fid.trim();
			aDto = fidService.getData(Long.parseLong(fid));
			}
			
			String pageUrl = aDto.getPageUrl();
			
			formIdBean.setFid(fid);
			formIdBean.setPageUrl(pageUrl);
			formIdBean.setStatus(aDto.getStatus());
			
			if(pageUrl == null || pageUrl.trim().equals(""))
			{
				request.removeAttribute("HIDE_FIELDS");
				formIdBean.setMessage("No result found.");
			}
			else
			{
				request.setAttribute("HIDE_FIELDS", false);
			}
			
			forward = mapping.findForward(GET_FID_DATA);
			
			return forward;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	
	/**
	 * This method is called when user clicks edit button
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editFidData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("Inside editFidData method of ManageFormIdAction");
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{
			ActionForward forward = null;
			
			FormIdBean formIdBean = (FormIdBean) form;
			String fid = formIdBean.getFid();
			
			ManageFormIdService fidService = new ManageFormIdServiceImpl();
			FormIdDto aDto = fidService.getData(Long.parseLong(fid));
			
			String pageUrl = aDto.getPageUrl();
			
			formIdBean.setFid(fid);
			formIdBean.setPageUrl(pageUrl);
			formIdBean.setStatus(aDto.getStatus());
			
			if(pageUrl == null || pageUrl.trim().equals(""))
			{
				request.removeAttribute("HIDE_FIELDS");
				formIdBean.setMessage("No result found.");
				forward = mapping.findForward(GET_FID_DATA);
			}
			else
			{
				request.setAttribute("HIDE_FIELDS", false);
				forward = mapping.findForward(EDIT_FID);
			}
			
			return forward;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	
	/**
	 * This method is called when user clicks update button.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateFidData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
	{
		logger.info("Inside updateFidData method of ManageFormIdAction");
		
		ActionForward forward = null;
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{	
			FormIdBean formIdBean = (FormIdBean) form;
			
			FormIdDto fiddto = new FormIdDto();
			fiddto.setFid(Long.parseLong(formIdBean.getFid()));
			fiddto.setPageUrl(formIdBean.getPageUrl().trim());
			fiddto.setStatus(formIdBean.getStatus().equals("Active")?"A":"I");
			fiddto.setUpdatedBy(getUpdatedBy(request));
			
			ManageFormIdService fidService = new ManageFormIdServiceImpl();
			fidService.updateData(fiddto);
			
			request.setAttribute("HIDE_FIELDS", false);
			forward = mapping.findForward(UPDATE_FID);
			return forward;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}


	public ActionForward downloadExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info("Entered downloadExcel method");
	
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{
			ManageFormIdService fidService = new ManageFormIdServiceImpl();
			List<FormIdDto> fidsList = fidService.downloadExcel();
			request.setAttribute("fidsList", fidsList);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		UserMstr sessionUserBean =(UserMstr) session.getAttribute(USER_INFO);
		sessionUserBean.setMessage("SUCCESS");
		sessionUserBean.setFileName("FID list for View/Edit form id");
		userMstrService.insertBulkDataTransactionLogs(sessionUserBean);
		return mapping.findForward("downloadExcel");
		
	}
}