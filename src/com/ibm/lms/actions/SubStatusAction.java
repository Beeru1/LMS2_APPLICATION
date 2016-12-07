package com.ibm.lms.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.LOBDTO;
import com.ibm.lms.dto.LeadStatusDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.LeadRegistrationFormBean;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.wf.dto.Constant;
import com.ibm.lms.wf.services.FeasibleLeadManager;
import com.ibm.lms.wf.services.impl.FeasibleLeadManagerImpl;

public class SubStatusAction extends DispatchAction {
	private static final Logger logger;
	static {
		logger = Logger.getLogger(SubStatusAction.class);
	}

	public ActionForward init(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
		//load status and lob drop downs.
		        saveToken(request);
				UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
				LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
				logger.info(userBean.getUserLoginId() + " entered init method for SubSubStatus page.");
				MasterService masterService = new MasterServiceImpl();
				ArrayList<LeadStatusDTO> ledStatusList=masterService.getLeadStatusList();
				 ArrayList<LOBDTO> lobList=masterService.getLobList();
				 saveToken(request);
		       try{
		    	   if(Utility.isValidRequest(request)) {
			        	return mapping.findForward("error");
			        }
					request.setAttribute("leadStatusList", ledStatusList);
					
					request.setAttribute("lobList", lobList);
					
		       
					} catch (Exception e) {
					e.printStackTrace();
					logger.error("Exception occured********************in init method*******: " + e.getMessage());
				}

				logger.info(userBean.getUserLoginId() + " exited init method for Sub Status page.");
				return mapping.findForward("initialize");
				
		}
	public ActionForward getSubStatusOnStatusLobChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		
		int leadStatusId ;
		int productLobId;
		Document document = DocumentHelper.createDocument();
		LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean) form;
		Element root = document.addElement("options");
		FeasibleLeadManager feasibleLeadManager = new FeasibleLeadManagerImpl();
		MasterService masterService=new MasterServiceImpl();
		Element optionElement;
		try
		{
			if (request.getParameter("leadStatusId") != null && request.getParameter("lobId") != null)
			{
				
				leadStatusId = Integer.parseInt(request.getParameter("leadStatusId").toString());
				productLobId = Integer.parseInt(request.getParameter("lobId").toString());
				
				
				List subStatusList = feasibleLeadManager.getSubStatusList(leadStatusId,productLobId);
				
				if (subStatusList != null && subStatusList.size() > 0)
				{
					for (int intCounter = 0; intCounter < subStatusList.size(); intCounter++)
					{
						optionElement = root.addElement("option");
						optionElement.addAttribute("value", ((Constant)subStatusList.get(intCounter)).getID().toString());
						optionElement.addAttribute("text", ((Constant)subStatusList.get(intCounter)).getKeyValue());
						
					}
				}
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				XMLWriter writer = new XMLWriter(out);
				writer.write(document);
				writer.flush();
				out.flush();
			}
		}
		catch (Exception applicationException)
		{
			applicationException.printStackTrace();
		}
		return null;
	}
	
	
	
	
	public ActionForward viewSubSubStatus(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
		
		LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
		MasterService masterService = new MasterServiceImpl();
		ArrayList<LeadStatusDTO> ledStatusList=masterService.getLeadStatusList();
		ArrayList<LOBDTO> lobList=masterService.getLobList();
		FeasibleLeadManager feasibleLeadManager = new FeasibleLeadManagerImpl();
		
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		  
		try
		{
					
			request.setAttribute("leadStatusList", ledStatusList);
			request.setAttribute("lobList", lobList);
			int statusId=Integer.parseInt(leadRegistrationFormBean.getLeadStatusId());
			ArrayList<Constant> leadSubStatusList = feasibleLeadManager.getSubStatusList(statusId,leadRegistrationFormBean.getLobId());
			request.setAttribute("leadSubStatusList",leadSubStatusList);
			
			if(!"".equals(leadRegistrationFormBean.getLeadStatusId())&& leadRegistrationFormBean.getLobId()!=0 && leadRegistrationFormBean.getSubStatusId()!=0)
		{
		
				ArrayList<LeadStatusDTO> subSubStatusList=masterService.getLeadSubSubStatusList(statusId,leadRegistrationFormBean.getLobId(),leadRegistrationFormBean.getSubStatusId());
				leadRegistrationFormBean.setLeadSubSubStatusList(subSubStatusList);
				
			
		}
		} 
		catch (Exception e) {
		    e.printStackTrace();
			logger.error("Exception occured while getting SubSubStatus Details :" + e.getMessage());
			ActionErrors errors = new ActionErrors();
			errors.add("errors",new ActionError("subSubstatus.not.found"));
			saveErrors(request,errors);
		}
		leadRegistrationFormBean.setInitStatus("false");
		return mapping.findForward("viewSubSubStatus"); 
	}
	
	
	public ActionForward viewSubSubStatusDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
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
					
			if(!"".equals(leadRegistrationFormBean.getLeadStatusId())&& leadRegistrationFormBean.getLobId()!=0 && leadRegistrationFormBean.getSubStatusId()!=0 && leadRegistrationFormBean.getLeadSubSubStatusId()!=0)
			
			{
				MasterService service = new MasterServiceImpl();
				int statusId=Integer.parseInt(leadRegistrationFormBean.getLeadStatusId());
				int subStatusId=leadRegistrationFormBean.getSubStatusId();
				int subSubStatusId=leadRegistrationFormBean.getLeadSubSubStatusId();
				
				LeadStatusDTO subSubStatusList=service.getLeadSubSubStatusList(statusId,leadRegistrationFormBean.getLobId(),subStatusId,subSubStatusId);
				
				request.setAttribute("detail", subSubStatusList);
				
				
				
			}
		}
		catch (Exception e) {
		    e.printStackTrace();
			logger.error("Exception occured while getting SubSubStatus Details :" + e.getMessage());
			ActionErrors errors = new ActionErrors();
			errors.add("errors",new ActionError("subSubStatus.not.found"));
			saveErrors(request,errors);
		}
		leadRegistrationFormBean.setInitStatus("false");
		return mapping.findForward("viewSubSubStatusDetails"); 
	}
	public ActionForward updateSubSubStatusDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
		ActionMessages messages = new ActionMessages();
		String message="";
		MasterService masterService = new MasterServiceImpl();
		ArrayList<LeadStatusDTO> ledStatusList=masterService.getLeadStatusList();
		 ArrayList<LOBDTO> lobList=masterService.getLobList();
		
		/*if ( !isTokenValid(request) ) {
			  return init(mapping, form, request, response);
			}*/
		LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
		
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
			String lobName=request.getParameter("lobName");
			int productLobId=service.getLobId(lobName);
			String leadSubSubStatus=request.getParameter("leadSubSubStatus");
			String leadSubSubStatusDisplay=request.getParameter("leadSubSubStatusDisplay");
			int subSubStatusId=Integer.parseInt(request.getParameter("leadSubSubStatusId"));
			int subStatusCode=service.getSubStatusCode(leadStatusId,subStatusId,productLobId);
			
			message=service.updateSubSubStatusDetails(leadStatusId,subStatusId,productLobId,leadSubSubStatus,leadSubSubStatusDisplay,subSubStatusId,subStatusCode);
			if(!message.equals("1"))
			{
				messages.add("msg1",new ActionMessage("subSubStatus.system"));
				leadRegistrationFormBean.setMessage("SubSub Status cannot be updated!");
				saveMessages(request, messages);
				//resetToken(request);
			}
			else
			{
			messages.add("msg1",new ActionMessage("subSubStatus.success"));
			leadRegistrationFormBean.setMessage("SubSub Status has been updated successfully!");
			saveMessages(request, messages);
			//resetToken(request);
				
		}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting SubStatus Update :" + e.getMessage());
			ActionErrors errors = new ActionErrors();
			errors.add("errors",new ActionError("subSubStatus.failure"));
			leadRegistrationFormBean.setMessage("SubSub Status could not be updated!");
			saveErrors(request,errors);	
		}
		return mapping.findForward("updateDetails");
	
}
	public ActionForward addSubSubStatus(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
		
		
		ActionMessages messages = new ActionMessages();
		MasterService service = new MasterServiceImpl();
		ArrayList<LeadStatusDTO> ledStatusList=service.getLeadStatusList();
		ArrayList<LOBDTO> lobList=service.getLobList();
		FeasibleLeadManager feasibleLeadManager = new FeasibleLeadManagerImpl();
		String insertSubStatus="";
		String message="";
		
		  
		/*if ( !isTokenValid(request) ) {
			  return init(mapping, form, request, response);
			}*/
		LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{
			request.setAttribute("leadStatusList", ledStatusList);
			request.setAttribute("lobList", lobList);
			int statusId=Integer.parseInt(leadRegistrationFormBean.getLeadStatusId());
			List leadSubStatusList = feasibleLeadManager.getSubStatusList(statusId,leadRegistrationFormBean.getLobId());
			request.setAttribute("leadSubStatusList",leadSubStatusList);
			int productLobId=leadRegistrationFormBean.getLobId();
			int subStatusId=leadRegistrationFormBean.getSubStatusId();
			String leadSubSubStatus=request.getParameter("leadSubSubStatus");
			LeadStatusDTO subSubStatusId=service.addSubSubStatus(statusId,productLobId,subStatusId,leadSubSubStatus);
			insertSubStatus=subSubStatusId.getMessage();
			 
			if(insertSubStatus.substring(0,1).equalsIgnoreCase("0"))
			{
				 message="Sub Sub Status Added successfully."+insertSubStatus.substring(1);
			}
			   else if(insertSubStatus.substring(0,1).equalsIgnoreCase("1"))
			{  
				    message="Sub Sub Status already exists."+insertSubStatus.substring(1);
			}
				
				request.setAttribute("insertsubStatus", message);
				leadRegistrationFormBean.reset(mapping, request);
				//resetToken(request);
					
		}
			
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting SubSubStatus Update :" + e.getMessage());
			ActionErrors errors = new ActionErrors();
			errors.add("errors",new ActionError("subSubStatus.not.add"));
			saveErrors(request,errors);	
		}
		return mapping.findForward("addSubSubStatus");
	
}
}