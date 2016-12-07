package com.ibm.lms.wf.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.ibm.lms.common.Constants;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.common.UserDetails;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.LeadRegistrationService;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.LeadRegistrationServiceImpl;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.services.impl.UserMstrServiceImpl;
import com.ibm.lms.wf.dto.BulkFeasibilityDTO;
import com.ibm.lms.wf.dto.Constant;
import com.ibm.lms.wf.dto.LeadDetailDTO;
import com.ibm.lms.wf.forms.LeadForm;
import com.ibm.lms.wf.services.AssignedLeadsManager;
import com.ibm.lms.wf.services.FeasibleLeadManager;
import com.ibm.lms.wf.services.impl.AssignedLeadsManagerImpl;
import com.ibm.lms.wf.services.impl.FeasibleLeadManagerImpl;

public class SearchFeasibleLeadsAction extends DispatchAction{
	
	private static Logger logger = Logger.getLogger(SearchFeasibleLeadsAction.class
			.getName());
	private UserMstrService userMstrService = new UserMstrServiceImpl();
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		
		ActionForward forward = new ActionForward(); // return value
		HttpSession session = request.getSession();
		if(Utility.isValidRequest(request) && (session.getAttribute("URLFLAG")==null || !"N".equalsIgnoreCase((String)session.getAttribute("URLFLAG")))) {
        	return mapping.findForward("error");
        }
		logger.info(UserDetails.getUserLoginId(request)+" : Entered init method");
		FeasibleLeadManager feasibleLeadManager = new FeasibleLeadManagerImpl();
		UserMstr sessionUserBean =(UserMstr) session.getAttribute("USER_INFO");
		logger.info(UserDetails.getUserLoginId(request)+" : Exiting init method");
		LeadForm commonForm = (LeadForm) form;
		MasterService  masterService = new MasterServiceImpl();
		commonForm.reset();
		commonForm.setMsg("");
		commonForm.setMessage("");
		commonForm.setIsError("false");
		int recordslimitdays = Integer.parseInt(PropertyReader.getAppValue("records.limit.days"));
		session.setAttribute("recordslimitdays", recordslimitdays);
		saveToken(request);
		try {
			List assignedLeads = new ArrayList();
			assignedLeads = feasibleLeadManager.listFeasibilityLeads(sessionUserBean.getUserLoginId(),null,null);
			commonForm.setAssignedLeads(assignedLeads);
			commonForm.setParam(masterService.getParameterName(PropertyReader.getAppValue("feasible.leads.no.of.days")));
		}
		// Finish with
	 catch (Exception e) {
		logger.error("Exception occurs in viewUserList: " + e.getMessage());
	}
			forward = mapping.findForward("search");
		return (forward);  

	}
	public ActionForward viewLeadDetail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		logger.info("inside view Lead Detail***********************");
		HttpSession session = request.getSession();
		AssignedLeadsManager assignedLeadsManager = new AssignedLeadsManagerImpl();
		FeasibleLeadManager feasibleLeadManager = new FeasibleLeadManagerImpl();
		UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
		LeadForm commonForm = (LeadForm) form;
		MasterService  masterService = new MasterServiceImpl();
		saveToken(request);
		try {
			List<Constant> actionList = assignedLeadsManager.getActionList("FEASIBILITY_STATUS");
			//List<Constant> masterList = feasibleLeadManager.getSubStatusList(actionList);
			request.setAttribute("actionList", actionList);
			List userList = assignedLeadsManager.getUsersList(sessionUserBean.getCircleList(),sessionUserBean.getLobList()); //changed by Sudhanshu
			request.setAttribute("userList", userList);
			Long leadID = Long.parseLong(commonForm.getLeadID().toString());
			LeadDetailDTO detailDTO = assignedLeadsManager.viewLeadDetail(leadID);
			request.setAttribute("detail", detailDTO);
			
			
			if(commonForm.getActionType() !=null && commonForm.getActionType().length() > 0)
			{
			int status = Integer.parseInt(commonForm.getActionType().toString());
			int lobId=Integer.parseInt(commonForm.getLobId().toString());
			logger.info("****lob id value in feasible leads is****"+ lobId);
			List subStatusList = feasibleLeadManager.getSubStatusList(status,lobId);
			request.setAttribute("subStatusList", subStatusList);
			}
			
			List rsuList = null;
		//	rsuList = masterService.getRsuList();
			request.setAttribute("rsuList", rsuList);
			request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));
			return mapping.findForward("viewDetail");
		} catch (Exception e) {
            e.printStackTrace();
			logger.error("Exception occured while initializing editUser page ");
		}
		return null;
	}
	
	public ActionForward qualifyTheFeasibleLead(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)throws Exception {
		ActionForward forward = new ActionForward();
		
		FeasibleLeadManager feasibleLeadManager = new FeasibleLeadManagerImpl();
		MasterService service = new MasterServiceImpl();
		
		ActionMessages messages = new ActionMessages();
		LeadForm commonForm = (LeadForm) form;
		HttpSession session = request.getSession();
		if ( !isTokenValid(request) ) {
			session.setAttribute("URLFLAG", "N");
			  return init(mapping, form, request, response);
			}
		UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
		Boolean flag = false;
		try {
			commonForm.setUpdatedBy(sessionUserBean.getUserLoginId());
			commonForm.setUdId(sessionUserBean.getUdId());
			if( commonForm.getRsuCode().length()>0 &&  !service.isValidRsuInCircle(commonForm.getRsuCode(),commonForm.getLeadID()))
			{
				commonForm.setMsg("Please enter valid RSU Code");
			}
			else
			{
				
				flag = feasibleLeadManager.qualifyTheFeasibleLead(commonForm);
				if(flag){
					commonForm.reset();
					List assignedLeads = new ArrayList();
					assignedLeads = feasibleLeadManager.listFeasibilityLeads(sessionUserBean.getUserLoginId(),commonForm.getStartDate(),commonForm.getEndDate());
					commonForm.setAssignedLeads(assignedLeads);
					messages.add("msg", new ActionMessage("Qualify.feasible.lead"));
					commonForm.setMsg("Feassibility has been done successfully!");
					saveMessages(request, messages);
					resetToken(request);
				}
				else
				{
					commonForm.setMsg("Feasibility can not be done!");
				}
			}
			saveMessages(request, messages);
		}
		catch (Exception e) {
			logger.error("Exception occurs in viewUserList: " + e.getMessage());
		}
		forward = mapping.findForward("search");
		logger.error("Exception occured while initializing editUser page ");

		return forward;
	}
	
	public ActionForward uploadExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
		logger.info("enter action");
		HttpSession session = request.getSession();
		boolean isError=false;
		ActionForward forward = new ActionForward();
		UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
		LeadForm leadForm =  (LeadForm) form;
		leadForm.setMsg("");
		FeasibleLeadManager  feasibleLeadManager = new FeasibleLeadManagerImpl();
		sessionUserBean.setFileName("FeasibilityLeads.xls for upload Feasible leads");
		ActionMessages messages = new ActionMessages();
		if ( !isTokenValid(request) ) {
			session.setAttribute("URLFLAG", "N");
			  return init(mapping, form, request, response);
			}
		try
		{
			
		
		FormFile file = leadForm.getNewFile();
		String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
		if(!arr.equalsIgnoreCase("xls") && !arr.equalsIgnoreCase("xlsx") )
		{
			messages.add("msg1",new ActionMessage("lms.bulk.feasibility.excel.only"));
			leadForm.setIsError("false");
			leadForm.setMsg("Upload Excel only!");
			sessionUserBean.setMessage("FAILED");
		}
		else
		{
			BulkUploadMsgDto msgDto = feasibleLeadManager.uploadFeasibilityMatrix((FormFile)file,request);
			logger.info("msgDto.getMsgId()-----------"+msgDto.getMsgId());
			switch(msgDto.getMsgId())
			{
			case Constants.SERVICE_MSG_SUCCESS :
				messages.add("msg",new ActionMessage("lms.bulk.feasibility.success"));
				leadForm.setMsg("Bulk Feasibility is done successfully");
				leadForm.setIsError("false");
				sessionUserBean.setMessage("UPLOAD SUCCESSFULLY");
				//userMstrService.insertBulkDataTransactionLogs(sessionUserBean);
				resetToken(request);
				break;
				
			case Constants.SERVICE_MSG_FAIL :
				//messages.add("msg",new ActionMessage("lms.bulk.assignment.failed"));
				leadForm.setMsg("Data upload failed or partial insertion. To check logs ");
				session.setAttribute("bulkLeadFeasibilityErrLogFilePath",msgDto.getMessage());
				leadForm.setIsError("true");
				sessionUserBean.setMessage("PARTIALLY DONE OR FAIL");
				break;
				
			case Constants.INVALID_EXCEL :
				//messages.add("msg",new ActionMessage("lms.bulk.upload.invalid.excel"));
				leadForm.setMsg("Invalid excel.");
				leadForm.setIsError("false");
				sessionUserBean.setMessage("FAILED");
				//bulkAssigmentFormBean.setDownloadTemplate("true");
				break;
				
			case Constants.SERVICE_MSG_ERROR :
			//	messages.add("msg",new ActionMessage("lms.bulk.assignment.error"));
				leadForm.setMsg("Error Occurred.");
				leadForm.setIsError("false");
				sessionUserBean.setMessage("FAILED");
				break;
				
			case Constants.BLANK_EXCEL :
			//	messages.add("msg",new ActionMessage("lms.bulk.upload.blank.excel"));
				leadForm.setMsg("Please check excel sheet is empty. ");
				leadForm.setIsError("false");
				sessionUserBean.setMessage("FAILED");
				break;
			}
				/*if(isError)
				{
					messages.add("msg",new ActionMessage("lms.bulk.feasibility.failed"));
					leadForm.setMsg("Bulk Feasibility failed or partially done");
					leadForm.setIsError("true");
				}	
				else		
				{
					messages.add("msg",new ActionMessage("lms.bulk.feasibility.success"));
					leadForm.setMsg("Bulk Feasibility is done successfully");
					leadForm.setIsError("false");
					resetToken(request);
				}*/	
				//leadForm.reset();
				List assignedLeads = new ArrayList();
				assignedLeads = feasibleLeadManager.listFeasibilityLeads(sessionUserBean.getUserLoginId(),leadForm.getStartDate(),leadForm.getEndDate());
				leadForm.setAssignedLeads(assignedLeads);
			}
		saveMessages(request, messages);
		
		}//try
		catch(LMSException km)
		{
			leadForm.setMsg("Invalid excel format.");
			leadForm.setIsError("false");	
			logger.info("LMS Exception occured in uploadExcel in action");
			sessionUserBean.setMessage("FAILED");
			//userMstrService.insertBulkDataTransactionLogs(sessionUserBean);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occured in uploadExcel");
			sessionUserBean.setMessage("FAILED");
			//userMstrService.insertBulkDataTransactionLogs(sessionUserBean);
	  }
		userMstrService.insertBulkDataTransactionLogs(sessionUserBean);
		forward = mapping.findForward("search");
		return (forward);  
	}//uploadExcel

	public ActionForward excelImport(ActionMapping mapping,	ActionForm form,HttpServletRequest request,	HttpServletResponse response)throws Exception 
	{
		HttpSession session = request.getSession();
			UserMstr sessionUserBean =(UserMstr) session.getAttribute("USER_INFO");
			UserMstrService userMstrService = new UserMstrServiceImpl();
			MasterService masterService = new MasterServiceImpl();
			logger.info(sessionUserBean.getUserLoginId()	+ " Entered into the excelImport method of SearchFeasibleLeadsAction");
			FeasibleLeadManager feasibleLeadManager = new FeasibleLeadManagerImpl();
			String loginActorId = sessionUserBean.getKmActorId();
			session.setAttribute("loginUserId", loginActorId);
			String startDate = null;
			String endDate = null;
			sessionUserBean.setFileName("FeasibilityLeads.xls for view Feasible leads");
			if(request.getParameter("startDate") !=null)
				startDate = (String) request.getParameter("startDate");
			if(request.getParameter("endDate") !=null)
				endDate = (String) request.getParameter("endDate");
				try {
					List<BulkFeasibilityDTO> masterList = new ArrayList<BulkFeasibilityDTO>();
					masterList = feasibleLeadManager.listFeasibilityLeadsExcel(sessionUserBean.getUserLoginId(),startDate,endDate);
					request.setAttribute("fisibilityList", masterList);
				
			} catch (Exception e) {
				logger.error("Exception occurs in viewUserList: " + e.getMessage());
				sessionUserBean.setMessage("FAILED");
				userMstrService.insertBulkDataTransactionLogs(sessionUserBean);
			}
			request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("content-Disposition","attachment;filename=FeasibilityLeads.xls");
			sessionUserBean.setMessage("SUCCESS");
			userMstrService.insertBulkDataTransactionLogs(sessionUserBean);
			return mapping.findForward("viewFeasibilityListExcel");
	}
	public ActionForward getFeasibilitySubStatusList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		int status ;
		int lobId;
		Long leadId;
		String leadId1;
		int productId;
		String product_name;
		Document document = DocumentHelper.createDocument();
		LeadForm commonForm = (LeadForm) form;
		Element root = document.addElement("options");
		FeasibleLeadManager feasibleLeadManager = new FeasibleLeadManagerImpl();
		MasterService masterService=new MasterServiceImpl();
		Element optionElement;
		try
		{
			
			if (request.getParameter("status") != null)
			{
				//added by Nancy
				leadId1=request.getParameter("leadID");
				
				leadId=Long.valueOf(leadId1);
				
				productId=masterService.getProductId(leadId);
				status = Integer.parseInt(request.getParameter("status").toString());
				
				List subStatusList = feasibleLeadManager.getSubStatusList(status,productId);
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

	public ActionForward searchLead(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)throws Exception {

		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		FeasibleLeadManager feasibleLeadManager = new FeasibleLeadManagerImpl();
		saveToken(request);
		UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
		logger.info(UserDetails.getUserLoginId(request)	+ " : Exiting init method");
		LeadForm commonForm = (LeadForm) form;
		try {
			String startDate = (String) request.getParameter("startDate");
			String endDate = (String) request.getParameter("endDate");
			List assignedLeads = new ArrayList();
			assignedLeads = feasibleLeadManager.listFeasibilityLeads(sessionUserBean.getUserLoginId(), startDate, endDate);
			commonForm.setAssignedLeads(assignedLeads);
		}
		// Finish with
		catch (Exception e) {
			logger.error("Exception occurs in viewUserList: " + e.getMessage());
			logger.error("Exception occured while initializing editUser page ");

		}
		forward = mapping.findForward("search");
		
		return forward;
	}
	public ActionForward openErrLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("Inside BulkFeasibilityAction openErrLog method");
		HttpSession session = request.getSession();
		java.util.Date date = new java.util.Date();
		String filePath="",fileNameNew="";

		try
		  {
			filePath=(String)session.getAttribute("bulkLeadFeasibilityErrLogFilePath");
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
		return null;
	}
	public ActionForward newFeasibleLeads(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		HttpSession session = request.getSession();
		logger.info(UserDetails.getUserLoginId(request)+" : Entered init method");
		FeasibleLeadManager feasibleLeadManager = new FeasibleLeadManagerImpl();
		UserMstr sessionUserBean =(UserMstr) session.getAttribute("USER_INFO");
		logger.info(UserDetails.getUserLoginId(request)+" : Exiting init method");
		LeadForm commonForm = (LeadForm) form;
		commonForm.reset();
		commonForm.setMsg("");
		commonForm.setMessage("");
		commonForm.setIsError("false");
		commonForm.setCircleMstrId(0);
		commonForm.setStatusId(0);
		int recordslimitdays = Integer.parseInt(PropertyReader.getAppValue("records.limit.days"));
		session.setAttribute("recordslimitdays", recordslimitdays);
		UserMstrService editUserService = new UserMstrServiceImpl();
		MasterService mstrService = new MasterServiceImpl();
		saveToken(request);
		try {
			if(Utility.isValidRequest(request)) {
	        	return mapping.findForward("error");
	        }
			if(sessionUserBean.getKmActorId().equalsIgnoreCase(Constants.CIRCLE_COORDINATOR_ACTOR)){
				commonForm.setCircleList(mstrService.getCircleForUserLob(sessionUserBean.getUserLoginId(),mstrService.getLobId(Constants.LOB_TELEMEDIA_ID)));
			
			}
			else{
				commonForm.setCircleList(mstrService.getCircleForLob(mstrService.getLobId(Constants.LOB_TELEMEDIA_ID)));
			}
			
			//commonForm.setCircleList(mstrService.getCircleForProductName());
			commonForm.setStatusList(feasibleLeadManager.getStatusList());
			List assignedLeads = new ArrayList();
			assignedLeads = feasibleLeadManager.listFeasibilityLeads(sessionUserBean.getUserLoginId(),null,null);
			commonForm.setAssignedLeads(assignedLeads);
			commonForm.setTotalRec((assignedLeads !=null)?assignedLeads.size():0);
			commonForm.setParam(mstrService.getParameterName(PropertyReader.getAppValue("view.feasible.leads.no.of.days")));
			
			
		}
		// Finish with
	 catch (Exception e) {
		logger.error("Exception occurs in viewUserList: " + e.getMessage());
	}
			forward = mapping.findForward("searchNew");
		return (forward);  

	}
	
	public ActionForward searchNewLead(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)throws Exception {

		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		FeasibleLeadManager feasibleLeadManager = new FeasibleLeadManagerImpl();
		saveToken(request);
		UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
		logger.info(UserDetails.getUserLoginId(request)	+ " : inside searchNewLead method");
		LeadForm commonForm = (LeadForm) form;
		try {
			String startDate = (String) request.getParameter("startDate");
			String endDate = (String) request.getParameter("endDate");
			//String circle = (String) request.getParameter("circleId");
			//String status = (String) request.getParameter("statusId");
			List assignedLeads = new ArrayList();
		logger.info("commonForm.getCircleMstrId()"+commonForm.getCircleMstrId());
		logger.info("commonForm.getStatusId()"+commonForm.getStatusId());	
		
			assignedLeads = feasibleLeadManager.getNewFeasibilityLeads(commonForm.getCircleMstrId(), commonForm.getStatusId(),  startDate, endDate);
			logger.info("assignedLeads????????????"+assignedLeads.size());
			int count = assignedLeads.size();
			commonForm.setAssignedLeads(assignedLeads);
			commonForm.setTotalRec(count);
		}
		// Finish with
		catch (Exception e) {
			logger.error("Exception occurs in searchNewLead: " + e.getMessage());
			logger.error("Exception occured while initializing searchNewLead page ");

		}
		forward = mapping.findForward("searchNew");
		
		return forward;
	}
	
	public ActionForward viewLeadDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
		logger.info("Inside viewLeadDetails");
		MasterService masterService = new MasterServiceImpl();

		try
		{
			
			LeadForm leadFormBean = (LeadForm)form;
			Long leadId=0l;
			leadFormBean.setInitStatus("false");
			//logger.info("leadFormBean.getLeadID()????????"+leadFormBean.getLeadID());
			if(!"".equals(leadFormBean.getLeadID()))
			{
				LeadRegistrationService leadRegistrationService = new LeadRegistrationServiceImpl();
				leadId = Long.parseLong(leadFormBean.getLeadID());
				
				request.setAttribute("LEAD_DETAILS",leadRegistrationService.getLeadDetails(leadId));

				request.setAttribute("LEAD_TRNS_DETAILS",leadRegistrationService.getLeadTransactinDetails(leadId));
				request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));
			}
		}
		catch (Exception e) {
		    e.printStackTrace();
			logger.error("Exception occured while getting lead Details :" + e.getMessage());
			ActionErrors errors = new ActionErrors();
			errors.add("errors",new ActionError("lead.not.found"));
			saveErrors(request,errors);
		}
		return mapping.findForward("viewLeadDetails"); 
	}
	
}