package com.ibm.lms.actions;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.LOBDTO;
import com.ibm.lms.dto.LeadStatusDTO;
import com.ibm.lms.dto.ProductDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.forms.LeadRegistrationFormBean;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.wf.dto.LeadDetailDTO;

public class StatusAction extends DispatchAction {
	private static final Logger logger;
	static {
		logger = Logger.getLogger(StatusAction.class);
	}

	public ActionForward init(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
		//load status and lob drop downs.
		        saveToken(request);
				UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
				LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
				logger.info(userBean.getUserLoginId() + " entered init method for Sub Status page.");
				MasterService masterService = new MasterServiceImpl();
				ArrayList<LeadStatusDTO> ledStatusList=masterService.getLeadStatusList();
				ArrayList<LOBDTO> lobList=masterService.getLobList();
				saveToken(request);
				if(Utility.isValidRequest(request)) {
		        	return mapping.findForward("error");
		        }
		        try{
					request.setAttribute("leadStatusList", ledStatusList);
					
					request.setAttribute("lobList", lobList);
					
					} catch (Exception e) {
					e.printStackTrace();
					logger.error("Exception occured********************in init method*******: " + e.getMessage());
				}

				logger.info(userBean.getUserLoginId() + " exited init method for Sub Status page.");
				return mapping.findForward("initialize");
				
		}
	
	public ActionForward viewSubStatus(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
		
		LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
		MasterService masterService = new MasterServiceImpl();
		ArrayList<LeadStatusDTO> ledStatusList=masterService.getLeadStatusList();
		ArrayList<LOBDTO> lobList=masterService.getLobList();
		
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{
					
			request.setAttribute("leadStatusList", ledStatusList);
			
			request.setAttribute("lobList", lobList);
			
			if(!"".equals(leadRegistrationFormBean.getLeadStatusId())&& leadRegistrationFormBean.getLobId()!=0)
			{
				//MasterService service = new MasterServiceImpl();
				int statusId=Integer.parseInt(leadRegistrationFormBean.getLeadStatusId());
				
				ArrayList<LeadStatusDTO> subStatusList=masterService.getLeadSubStatusList(statusId,leadRegistrationFormBean.getLobId());
				leadRegistrationFormBean.setLeadSubStatusList(subStatusList);
				//request.setAttribute("SUB_STATUS_LIST",subStatusList);
				
			}
		}
		catch (Exception e) {
		    e.printStackTrace();
			logger.error("Exception occured while getting SubStatus Details :" + e.getMessage());
			ActionErrors errors = new ActionErrors();
			errors.add("errors",new ActionError("subStatus.not.found"));
			saveErrors(request,errors);
		}
		leadRegistrationFormBean.setInitStatus("false");
		return mapping.findForward("viewSubStatus"); 
	}
	
	
	public ActionForward viewSubStatusDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
		
		LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
		MasterService masterService = new MasterServiceImpl();
		ArrayList<LeadStatusDTO> ledStatusList=masterService.getLeadStatusList();
		ArrayList<LOBDTO> lobList=masterService.getLobList();
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{
          request.setAttribute("leadStatusList", ledStatusList);
			
			request.setAttribute("lobList", lobList);		
			
			if(!"".equals(leadRegistrationFormBean.getLeadStatusId())&& leadRegistrationFormBean.getLobId()!=0)
			{
				MasterService service = new MasterServiceImpl();
				int statusId=Integer.parseInt(leadRegistrationFormBean.getLeadStatusId());
				int subStatusId=leadRegistrationFormBean.getSubStatusId();
				
				LeadStatusDTO subStatusList=service.getLeadSubStatusList(statusId,leadRegistrationFormBean.getLobId(),subStatusId);
				request.setAttribute("detail", subStatusList);
				
				
				
			}
		}
		catch (Exception e) {
		    e.printStackTrace();
			logger.error("Exception occured while getting SubStatus Details :" + e.getMessage());
			ActionErrors errors = new ActionErrors();
			errors.add("errors",new ActionError("subStatus.not.found"));
			saveErrors(request,errors);
		}
		leadRegistrationFormBean.setInitStatus("false");
		return mapping.findForward("viewSubStatusDetails"); 
	}
	public ActionForward updateSubStatusDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
		ActionMessages messages = new ActionMessages();
		
		/*if ( !isTokenValid(request) ) {
			  return init(mapping, form, request, response);
			}*/
		LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
		MasterService masterService = new MasterServiceImpl();
		ArrayList<LeadStatusDTO> ledStatusList=masterService.getLeadStatusList();
		ArrayList<LOBDTO> lobList=masterService.getLobList();
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{
            request.setAttribute("leadStatusList", ledStatusList);
			
			request.setAttribute("lobList", lobList);
			MasterService service = new MasterServiceImpl();
			int leadStatusId=Integer.parseInt(request.getParameter("leadStatusId"));
			
			int subStatusId=Integer.parseInt(request.getParameter("subStatusId"));
			
			String leadSubStatus=request.getParameter("leadSubStatus");
			String lobName=request.getParameter("lobName");
			int productLobId=service.getLobId(lobName);
			String leadSubStatusDisplay=request.getParameter("leadSubStatusDisplay");
				
			String message=service.updateSubStatusDetails(leadStatusId,subStatusId,productLobId,leadSubStatus,leadSubStatusDisplay);
			
			if(!message.equals("1"))
			{
				messages.add("msg1",new ActionMessage("subStatus.system"));
				leadRegistrationFormBean.setMessage("Sub Status cannot be updated!");
				saveMessages(request, messages);
				//resetToken(request);
			}
			else
			{
			messages.add("msg1",new ActionMessage("subStatus.success"));
			leadRegistrationFormBean.setMessage("Sub Status has been updated successfully!");
			saveMessages(request, messages);
			//resetToken(request);
				
		}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting SubStatus Update :" + e.getMessage());
			ActionErrors errors = new ActionErrors();
			errors.add("errors",new ActionError("subStatus.failure"));
			leadRegistrationFormBean.setMessage("Sub Status could not be updated!");
			saveErrors(request,errors);	
		}
		return mapping.findForward("updateDetails");
	
}
	public ActionForward addSubStatus(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
		ActionMessages messages = new ActionMessages();
		
		MasterService service = new MasterServiceImpl();
		ArrayList<LeadStatusDTO> ledStatusList=service.getLeadStatusList();
		ArrayList<LOBDTO> lobList=service.getLobList();
		String insertSubStatus = ""; 
		String message="";
		/*if ( !isTokenValid(request) ) {
			  return init(mapping, form, request, response);
			}
			*/
		LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{
			request.setAttribute("leadStatusList", ledStatusList);
			request.setAttribute("lobList", lobList);
			
			int statusId=Integer.parseInt(leadRegistrationFormBean.getLeadStatusId());
			int productLobId=leadRegistrationFormBean.getLobId();
			String substatusName=request.getParameter("leadSubStatus");
			
			LeadStatusDTO subStatusId=service.addSubStatus(statusId,productLobId,substatusName);
			 insertSubStatus=subStatusId.getMessage();
			 
			if(insertSubStatus.substring(0,1).equalsIgnoreCase("0"))
			{
				 message="Sub Status Added successfully."+insertSubStatus.substring(1);
			}
			   else if(insertSubStatus.substring(0,1).equalsIgnoreCase("1"))
			{  
				    message="Sub Status already exists."+insertSubStatus.substring(1);
			}
				
				request.setAttribute("insertsubStatus", message);
				leadRegistrationFormBean.reset(mapping, request);
				//resetToken(request);
					
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting SubStatus Update :" + e.getMessage());
			ActionErrors errors = new ActionErrors();
			errors.add("errors",new ActionError("subStatus.not.add"));
			saveErrors(request,errors);	
		}
		return mapping.findForward("addSubStatus");
	
}
}