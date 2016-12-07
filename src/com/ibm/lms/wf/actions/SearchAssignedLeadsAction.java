package com.ibm.lms.wf.actions;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.ibm.lms.common.Constants;
import com.ibm.lms.common.LMSStatusCodes;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.common.UserDetails;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.UserMstrService;
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

public class SearchAssignedLeadsAction extends DispatchAction {
	private static Logger logger = Logger
			.getLogger(SearchAssignedLeadsAction.class.getName());

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,

			HttpServletResponse response) throws Exception {

		ActionForward forward = new ActionForward(); // return value
		HttpSession session = request.getSession();
		logger.info(UserDetails.getUserLoginId(request)
				+ " : Entered init method");
		AssignedLeadsManager assignedLeadsManager = new AssignedLeadsManagerImpl();
		UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
		logger.info(UserDetails.getUserLoginId(request)
				+ " : Exiting init method");
		MasterService masterService = new MasterServiceImpl();
		String view = "";
		LeadForm commonForm = (LeadForm) form;
		commonForm.reset();
		commonForm.setMsg("");
		commonForm.setMessage("");
		int recordslimitdays = Integer.parseInt(PropertyReader
				.getAppValue("records.limit.days"));
		session.setAttribute("recordslimitdays", recordslimitdays);
		if(Utility.isValidRequest(request) && (session.getAttribute("URLFLAG")==null || !"N".equalsIgnoreCase((String)session.getAttribute("URLFLAG")))) {
        	return mapping.findForward("error");
        }
		List userList = null;
		if (request.getParameter("view") != null) {
			view = request.getParameter("view").toString();
			commonForm.setView(view);
		}
		try {
			saveToken(request);
			List assignedLeads = new ArrayList();
			assignedLeads = assignedLeadsManager.listAssignedLeads(
					sessionUserBean.getUserLoginId(), null, null, commonForm
							.getView());
			if (assignedLeads != null && assignedLeads.size() > 0)
				commonForm.setAssignedLeads(assignedLeads);
			commonForm.setParam(masterService.getParameterName(PropertyReader
					.getAppValue("assigned.leads.no.of.days")));
			// changed by sudhanshu
			/*
			 * if((sessionUserBean.getKmActorId().equalsIgnoreCase(Constants.CIRCLE_USER
			 * ))||(sessionUserBean.getKmActorId().equalsIgnoreCase(Constants.
			 * ZBM_ACTOR
			 * ))||(sessionUserBean.getKmActorId().equalsIgnoreCase(Constants
			 * .ZSM_ACTOR
			 * ))||(sessionUserBean.getKmActorId().equalsIgnoreCase(Constants
			 * .ZONAL_COORDINATOR_ACTOR))){ userList =
			 * assignedLeadsManager.getUsersList
			 * (sessionUserBean.getCircleList(),sessionUserBean.getLobList()
			 * );//changed by sudhanshu } else userList =
			 * assignedLeadsManager.getChannelPartnerList
			 * (sessionUserBean.getCircleList(),sessionUserBean.getLobList()
			 * );//changed by sudhanshu
			 */// logger.info("get circle co-ordi");
			if (sessionUserBean.getKmActorId().equalsIgnoreCase(
					Constants.CIRCLE_COORDINATOR_ACTOR)) {
				userList = assignedLeadsManager.getUsersList(sessionUserBean
						.getCircleList(), sessionUserBean.getLobList());// Fetches
																		// user
																		// list
																		// for
																		// Circle
																		// CoOrdinator
			} else if (sessionUserBean.getKmActorId().equalsIgnoreCase(
					Constants.ZONAL_COORDINATOR_ACTOR)) {
				userList = assignedLeadsManager.getZonalCoordinatorList(
						sessionUserBean.getCircleList(), sessionUserBean
								.getLobList());// changed by sudhanshu
			} else
				userList = assignedLeadsManager.getChannelPartnerList(
						sessionUserBean.getCircleList(), sessionUserBean
								.getLobList());// changed by sudhanshu


			request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));
			request.setAttribute("userList", userList);

		} catch (Exception e) {
			logger.error("Exception occurs in viewUserList: " + e.getMessage());
		}
		forward = mapping.findForward("search");
		return (forward);

	}

	public ActionForward viewLeadDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		

		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();
		ActionErrors errors = new ActionErrors();
		LeadForm commonForm = (LeadForm) form;
		String product_name;
		int lobId;
		Long leadId;
		int productId;
		long productIdForCheck;
		AssignedLeadsManager assignedLeadsManager = new AssignedLeadsManagerImpl();
		FeasibleLeadManager feasibleLeadManager = new FeasibleLeadManagerImpl();
		MasterService masterService = new MasterServiceImpl();
		UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
		saveToken(request);
		Constant cons = new Constant();
		List userList = null;
		List productConfigureList=null;
		String loggedinUser =sessionUserBean.getUserLoginId();
		boolean flag=false;
		try {
			List<Constant> actionList = assignedLeadsManager
					.getActionList("LEAD_CLOSURE");
			int size = actionList.size();
			// sessionUserBean.setKmActorId("15");
			// added by Nancy
			
			
			//
			leadId = Long.parseLong(request.getParameter("leadID"));
			String assignedUser =assignedLeadsManager.checkAsssignedPrimaryUser(leadId);
			
			if(loggedinUser.equalsIgnoreCase(assignedUser)){
				flag=true;
			}
			if(!flag){
				errors.add("record.not.found", new ActionError("record.not.found"));
				saveErrors(request, errors);
				return mapping.findForward("search");
				
			}
			productId = masterService.getProductId(leadId);
			
			
			Long leadID = Long.parseLong(commonForm.getLeadID().toString());
			productIdForCheck = masterService.getProduct(leadId);
			LeadDetailDTO detailDTO = assignedLeadsManager.viewLeadDetail(leadID);
			
			//New code added here
			List<String> products=Arrays.asList(masterService.getParameterName("NEW_PRODUCT").trim().split(","));
			//logger.info("FFFFFFFFFFFFFF"+masterService.getParameterName("NEW_PRODUCT"));
			
			boolean productCheck = products.contains(String.valueOf(productIdForCheck));
			if (sessionUserBean.getKmActorId().equalsIgnoreCase(
					Constants.CIRCLE_COORDINATOR_ACTOR)) {
				userList = assignedLeadsManager.getUsersList(sessionUserBean
						.getCircleList(), sessionUserBean.getLobList());// Fetches
																		// user
																		// list
																		// for
																		// Circle
																		// CoOrdinator
				
                  //Add extra olmids 
				
			} else if (sessionUserBean.getKmActorId().equalsIgnoreCase(
					Constants.ZONAL_COORDINATOR_ACTOR)) {
				userList = assignedLeadsManager.getZonalCoordinatorList(
						sessionUserBean.getCircleList(), sessionUserBean
								.getLobList());// changed by sudhanshu
			} else {
				// userList =
				// assignedLeadsManager.getChannelPartnerList(sessionUserBean
				// .getCircleList(),sessionUserBean.getLobList() );//changed by
				// sudhanshu
				userList = assignedLeadsManager.getChannelPartnerListForLead(
						sessionUserBean.getCircleList(), detailDTO,
						sessionUserBean.getLobList());
			}
			String dropDownFlag=masterService.getParameterName("DROPDOWN_FLAG");
			if (productCheck && dropDownFlag.equalsIgnoreCase("Y")){
				userList=assignedLeadsManager.getUserListForNewProduct(detailDTO,sessionUserBean.getUserLoginId());
				//userList.addAll(productConfigureList);
			}
			
			List subStatusList = feasibleLeadManager.getSubStatusList(400,productId);
			logger.info("Actor id"+sessionUserBean.getKmActorId());
			//logger.info("oooooooo"+(productCheck && Integer.parseInt(sessionUserBean.getKmActorId())==10));
			if(productCheck && Integer.parseInt(sessionUserBean.getKmActorId())== Constants.CHANNEL_PARTNER_ID )
			{
			
			for(Constant conste:actionList)
			{
				
				if(conste.getID()==Integer.parseInt(Constants.LEAD_STATUS_WON))
					actionList.remove(conste);
				}
			}
			request.setAttribute("cafStatus", false);
			if(productCheck){
				
				Constant constant=new Constant();
				int leadStatus=assignedLeadsManager.getLeadStatus(leadId);
				constant.setID((long)leadStatus);
				constant.setKeyValue("ON HOLD");
				actionList.add(constant);
				
				Constant constant1=new Constant();
				constant1.setID((long)leadStatus);
				constant1.setKeyValue("WIP");
				actionList.add(constant1);
				request.setAttribute("cafStatus", true);
				ArrayList<String> cafList=assignedLeadsManager.getSiteClosureList(detailDTO.getLobId());
				request.setAttribute("cafList", cafList);
				
			}
				
			else
				actionList.addAll(subStatusList);
			
			request.setAttribute("userList", userList);
			request.setAttribute("actionList", actionList);
			request.setAttribute("detail", detailDTO);
			request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));
			logger.info("commonForm.getView()" + commonForm.getView());
			if ("N".equalsIgnoreCase(commonForm.getView()))
				forward = mapping.findForward("viewDetail");
			else
				forward = mapping.findForward("viewAssignedLeadDetail");
			return forward;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while initializing editUser page ");
		}
		return null;
	
	}

	public ActionForward searchLead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("asaasa:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		AssignedLeadsManager assignedLeadsManager = new AssignedLeadsManagerImpl();
		MasterService masterService = new MasterServiceImpl();
		saveToken(request);
		UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
		logger.info(UserDetails.getUserLoginId(request)
				+ " : Exiting init method");
		LeadForm commonForm = (LeadForm) form;
		List userList = null;
		try {
			String startDate = (String) request.getParameter("startDate");
			String endDate = (String) request.getParameter("endDate");
			List assignedLeads = new ArrayList();
			assignedLeads = assignedLeadsManager.listAssignedLeads(
					sessionUserBean.getUserLoginId(), startDate, endDate,
					commonForm.getView());
			// assignedLeads = assignedLeadsManager.listAssignedLeadsEscalation(
			// sessionUserBean.getUserLoginId(), startDate,
			// endDate,commonForm.getView());
			commonForm.setAssignedLeads(assignedLeads);
			/*
			 * if(sessionUserBean.getKmActorId().equalsIgnoreCase(Constants.CIRCLE_USER
			 * )){ userList =
			 * assignedLeadsManager.getUsersList(sessionUserBean.getCircleList
			 * (),sessionUserBean.getLobList() );//changed by sudhanshu } else
			 * userList =
			 * assignedLeadsManager.getChannelPartnerList(sessionUserBean
			 * .getCircleList(),sessionUserBean.getLobList() );//changed by
			 * sudhanshu
			 */

			if (sessionUserBean.getKmActorId().equalsIgnoreCase(
					Constants.CIRCLE_COORDINATOR_ACTOR)) {
				userList = assignedLeadsManager.getUsersList(sessionUserBean
						.getCircleList(), sessionUserBean.getLobList());// Fetches
																		// user
																		// list
																		// for
																		// Circle
																		// CoOrdinator
			} else if (sessionUserBean.getKmActorId().equalsIgnoreCase(
					Constants.ZONAL_COORDINATOR_ACTOR)) {
				userList = assignedLeadsManager.getZonalCoordinatorList(
						sessionUserBean.getCircleList(), sessionUserBean
								.getLobList());// changed by sudhanshu
			} else
				userList = assignedLeadsManager.getChannelPartnerList(
						sessionUserBean.getCircleList(), sessionUserBean
								.getLobList());// changed by sudhanshu

			logger.info("sasaasa::::::get circle co-ordi");
			request.setAttribute("userList", userList);

			request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurs in search Lead: " + e.getMessage());
			logger
					.error("Exception occured while initializing lead Search page ");

		}
		forward = mapping.findForward("search");

		return forward;
	}

	public ActionForward searchLeadFeasibility(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		AssignedLeadsManager assignedLeadsManager = new AssignedLeadsManagerImpl();
		MasterService masterService = new MasterServiceImpl();
		saveToken(request);
		UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
		logger.info(UserDetails.getUserLoginId(request)
				+ " : Exiting init method");
		LeadForm commonForm = (LeadForm) form;
		List userList = null;
		try {
			String startDate = (String) request.getParameter("startDate");
			String endDate = (String) request.getParameter("endDate");
			List assignedLeads = new ArrayList();
			// assignedLeads = assignedLeadsManager.listAssignedLeads(
			// sessionUserBean.getUserLoginId(), startDate,
			// endDate,commonForm.getView());
			assignedLeads = assignedLeadsManager.listAssignedLeadsFeasibility(
					sessionUserBean.getUserLoginId(), startDate, endDate,
					commonForm.getView());
			commonForm.setAssignedLeads(assignedLeads);
			/*
			 * if(sessionUserBean.getKmActorId().equalsIgnoreCase(Constants.CIRCLE_USER
			 * )){ userList =
			 * assignedLeadsManager.getUsersList(sessionUserBean.getCircleList
			 * (),sessionUserBean.getLobList() );//changed by sudhanshu } else
			 * userList =
			 * assignedLeadsManager.getChannelPartnerList(sessionUserBean
			 * .getCircleList(),sessionUserBean.getLobList() );//changed by
			 * sudhanshu
			 */

			if (sessionUserBean.getKmActorId().equalsIgnoreCase(
					Constants.CIRCLE_COORDINATOR_ACTOR)) {
				userList = assignedLeadsManager.getUsersList(sessionUserBean
						.getCircleList(), sessionUserBean.getLobList());// Fetches
																		// user
																		// list
																		// for
																		// Circle
																		// CoOrdinator
			} else if (sessionUserBean.getKmActorId().equalsIgnoreCase(
					Constants.ZONAL_COORDINATOR_ACTOR)) {
				userList = assignedLeadsManager.getZonalCoordinatorList(
						sessionUserBean.getCircleList(), sessionUserBean
								.getLobList());// changed by sudhanshu
			} else
				userList = assignedLeadsManager.getChannelPartnerList(
						sessionUserBean.getCircleList(), sessionUserBean
								.getLobList());// changed by sudhanshu

			request.setAttribute("userList", userList);

			request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurs in search Lead: " + e.getMessage());
			logger
					.error("Exception occured while initializing lead Search page ");

		}
		forward = mapping.findForward("search");

		return forward;
	}

	public ActionForward closeTheLead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		AssignedLeadsManager assignedLeadsManager = new AssignedLeadsManagerImpl();
		MasterService masterService= new MasterServiceImpl();
		LeadForm commonForm = (LeadForm) form;
		if (!isTokenValid(request)) {
			session.setAttribute("URLFLAG", "N");
			return init(mapping, form, request, response);
		}
		ActionMessages messages = new ActionMessages();
		UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
		Boolean flag = false;
		List userList = null;
		try {
			commonForm.setUpdatedBy(sessionUserBean.getUserLoginId());
			commonForm.setUdId(sessionUserBean.getUdId());
			flag = assignedLeadsManager.closeTheLead(commonForm);
			String actiontype = commonForm.getActionType();
			if (flag) {
				commonForm.reset();
				List assignedLeads = new ArrayList();
				assignedLeads = assignedLeadsManager.listAssignedLeads(
						sessionUserBean.getUserLoginId(), commonForm
								.getStartDate(), commonForm.getEndDate(),
						commonForm.getView());
				commonForm.setAssignedLeads(assignedLeads);
				// forward = init(mapping,form,request,response);
				messages.add("msg", new ActionMessage("close.lead"));
				if (actiontype.equalsIgnoreCase(String
						.valueOf(LMSStatusCodes.WON))
						|| actiontype.equalsIgnoreCase(String
								.valueOf(LMSStatusCodes.LOST)))
					commonForm.setMsg("Lead has been closed successfully!");
				else
					commonForm.setMsg("Status has been updated successfully!");
				resetToken(request);
			} else {
				commonForm.setMsg("Lead was not closed!");
			}
			/*
			 * if(sessionUserBean.getKmActorId().equalsIgnoreCase(Constants.CIRCLE_USER
			 * )){ userList =
			 * assignedLeadsManager.getUsersList(sessionUserBean.getCircleList
			 * (),sessionUserBean.getLobList() );//changed by sudhanshu
			 * 
			 * }else userList =
			 * assignedLeadsManager.getChannelPartnerList(sessionUserBean
			 * .getCircleList(),sessionUserBean.getLobList() );//changed by
			 * sudhanshu
			 */
			if (sessionUserBean.getKmActorId().equalsIgnoreCase(
					Constants.CIRCLE_COORDINATOR_ACTOR)) {
				userList = assignedLeadsManager.getUsersList(sessionUserBean
						.getCircleList(), sessionUserBean.getLobList());// Fetches
																		// user
																		// list
																		// for
																		// Circle
																		// CoOrdinator
			} else if (sessionUserBean.getKmActorId().equalsIgnoreCase(
					Constants.ZONAL_COORDINATOR_ACTOR)) {
				userList = assignedLeadsManager.getZonalCoordinatorList(
						sessionUserBean.getCircleList(), sessionUserBean
								.getLobList());// changed by sudhanshu
			} else
				userList = assignedLeadsManager.getChannelPartnerList(
						sessionUserBean.getCircleList(), sessionUserBean
								.getLobList());// changed by sudhanshu

			logger.info("get circle co-ordi");

			request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));
			request.setAttribute("userList", userList);

			saveMessages(request, messages);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurs in viewUserList: " + e.getMessage());
		}
		forward = mapping.findForward("search");

		return forward;
	}

	public ActionForward forwardTheLead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
			{
		
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		ActionMessages messages = new ActionMessages();
		AssignedLeadsManager assignedLeadsManager = new AssignedLeadsManagerImpl();

		LeadForm commonForm = (LeadForm) form;
		UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
		Boolean flag = false;
		if (!isTokenValid(request)) {
			session.setAttribute("URLFLAG", "N");
			return init(mapping, form, request, response);
		}
		String ids[] = null;
		List userList = null;
		try {
			if (!"".equalsIgnoreCase(commonForm.getLeadID())) {
				ids = new String[1];
				ids[0] = commonForm.getLeadID();
			} else
				ids = request.getParameterValues("leadIDs");

			commonForm.setLeadIds(ids);
			commonForm.setUpdatedBy(sessionUserBean.getUserLoginId());
			commonForm.setUdId(sessionUserBean.getUdId());
			flag = assignedLeadsManager.reAssignTheLead(commonForm);
			if (flag) {
				commonForm.reset();
				List assignedLeads = new ArrayList();
				assignedLeads = assignedLeadsManager.listAssignedLeads(
						sessionUserBean.getUserLoginId(), commonForm
								.getStartDate(), commonForm.getEndDate(),
						commonForm.getView());
				commonForm.setAssignedLeads(assignedLeads);

				// forward = init(mapping,form,request,response);
				messages.add("msg", new ActionMessage("forward.lead"));
				commonForm.setMsg("Lead has been forwarded successfully!");
				resetToken(request);
			} else {
				commonForm.setMsg("Lead can not be forwarded!");
			}
			/*
			 * if(sessionUserBean.getKmActorId().equalsIgnoreCase(Constants.CIRCLE_USER
			 * )){ userList =
			 * assignedLeadsManager.getUsersList(sessionUserBean.getCircleList
			 * (),sessionUserBean.getLobList() );//changed by sudhanshu } else
			 * userList =
			 * assignedLeadsManager.getChannelPartnerList(sessionUserBean
			 * .getCircleList(),sessionUserBean.getLobList() );//changed by
			 * sudhanshu
			 */
			if (sessionUserBean.getKmActorId().equalsIgnoreCase(
					Constants.CIRCLE_COORDINATOR_ACTOR)) {
				userList = assignedLeadsManager.getUsersList(sessionUserBean
						.getCircleList(), sessionUserBean.getLobList());// Fetches
																		// user
																		// list
																		// for
																		// Circle
																		// CoOrdinator
			} else if (sessionUserBean.getKmActorId().equalsIgnoreCase(
					Constants.ZONAL_COORDINATOR_ACTOR)) {
				userList = assignedLeadsManager.getZonalCoordinatorList(
						sessionUserBean.getCircleList(), sessionUserBean
								.getLobList());// changed by sudhanshu
			} else
				userList = assignedLeadsManager.getChannelPartnerList(
						sessionUserBean.getCircleList(), sessionUserBean
								.getLobList());// changed by sudhanshu

			logger.info("get circle co-ordi");
			request.setAttribute("userList", userList);
			saveMessages(request, messages);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurs in viewUserList: " + e.getMessage());
		}
		forward = mapping.findForward("search");
		return forward;
	}

	public ActionForward uploadExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
			{
		
		boolean isError = false;
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();
		UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
		UserMstrService userMstrservice = new UserMstrServiceImpl();
		LeadForm leadForm = (LeadForm) form;
		leadForm.setMsg("");
		sessionUserBean.setFileName("AssignedLeads.xls for upload");
		AssignedLeadsManager assignedLeadsManager = new AssignedLeadsManagerImpl();
		FeasibleLeadManager feasibleLeadManager = new FeasibleLeadManagerImpl();
		ActionMessages messages = new ActionMessages();

		if (!isTokenValid(request)) {
			session.setAttribute("URLFLAG", "N");
			return init(mapping, form, request, response);
		}
		List userList = null;

		try {
			FormFile file = leadForm.getNewFile();
			String arr = (file.getFileName().toString()).substring(file
					.getFileName().toString().lastIndexOf(".") + 1, file
					.getFileName().toString().length());
			if (!arr.equalsIgnoreCase("xls") && !arr.equalsIgnoreCase("xlsx")) {

				messages.add("msg1", new ActionMessage(
						"lms.bulk.assignment.excel.only"));
				leadForm.setIsError("false");
				leadForm.setMsg("Upload Excel only!");
				sessionUserBean.setMessage("FAILED");
			} else {
				BulkUploadMsgDto msgDto = assignedLeadsManager.uploadAssignedMatrix((FormFile) file, request);
				switch (msgDto.getMsgId()) {
				case Constants.SERVICE_MSG_SUCCESS:
					messages.add("msg", new ActionMessage(
							"lms.bulk.feasibility.success"));
					leadForm.setMsg("Bulk Assignment is done successfully");
					leadForm.setIsError("false");
					sessionUserBean.setMessage("UPLOAD SUCCESSFULLY");
					resetToken(request);
					break;

				case Constants.SERVICE_MSG_FAIL:
					// messages.add("msg",new
					// ActionMessage("lms.bulk.assignment.failed"));
					leadForm
							.setMsg("Data upload failed or partial insertion. To check logs ");
					session.setAttribute("bulkLeadAssignedErrLogFilePath",
							msgDto.getMessage());
					leadForm.setIsError("true");
					sessionUserBean.setMessage("PARTIALLY DONE OR FAIL");
					break;

				case Constants.INVALID_EXCEL:
					// messages.add("msg",new
					// ActionMessage("lms.bulk.upload.invalid.excel"));
					leadForm.setMsg("Invalid excel.");
					leadForm.setIsError("false");
					// bulkAssigmentFormBean.setDownloadTemplate("true");
					sessionUserBean.setMessage("FAILED");
					break;

				case Constants.SERVICE_MSG_ERROR:
					// messages.add("msg",new
					// ActionMessage("lms.bulk.assignment.error"));
					leadForm.setMsg("Error Occurred.");
					leadForm.setIsError("false");
					sessionUserBean.setMessage("FAILED");
					break;

				case Constants.BLANK_EXCEL:
					// messages.add("msg",new
					// ActionMessage("lms.bulk.upload.blank.excel"));
					leadForm.setMsg("Please check excel sheet is empty. ");
					leadForm.setIsError("false");
					sessionUserBean.setMessage("FAILED");
					break;
				}
				List assignedLeads = new ArrayList();
				assignedLeads = assignedLeadsManager.listAssignedLeads(
						sessionUserBean.getUserLoginId(), leadForm
								.getStartDate(), leadForm.getEndDate(),
						leadForm.getView());
				if (assignedLeads != null && assignedLeads.size() > 0)
					leadForm.setAssignedLeads(assignedLeads);
				/*
				 * if(sessionUserBean.getKmActorId().equalsIgnoreCase(Constants.CIRCLE_USER
				 * )){ userList =
				 * assignedLeadsManager.getUsersList(sessionUserBean
				 * .getCircleList(),sessionUserBean.getLobList() );//changed by
				 * sudhanshu } else userList =
				 * assignedLeadsManager.getChannelPartnerList
				 * (sessionUserBean.getCircleList(),sessionUserBean.getLobList()
				 * );//changed by sudhanshu
				 */
				if (sessionUserBean.getKmActorId().equalsIgnoreCase(
						Constants.CIRCLE_COORDINATOR_ACTOR)) {
					userList = assignedLeadsManager.getUsersList(
							sessionUserBean.getCircleList(), sessionUserBean
									.getLobList());// Fetches user list for
													// Circle CoOrdinator
				} else if (sessionUserBean.getKmActorId().equalsIgnoreCase(
						Constants.ZONAL_COORDINATOR_ACTOR)) {
					userList = assignedLeadsManager.getZonalCoordinatorList(
							sessionUserBean.getCircleList(), sessionUserBean
									.getLobList());// changed by sudhanshu
				} else
					userList = assignedLeadsManager.getChannelPartnerList(
							sessionUserBean.getCircleList(), sessionUserBean
									.getLobList());// changed by sudhanshu

				logger.info("get circle co-ordinaters");
				request.setAttribute("userList", userList);
			}
			saveMessages(request, messages);
			//userMstrservice.insertBulkDataTransactionLogs(sessionUserBean);
		}// try
		catch (LMSException km) {
			leadForm.setMsg("Invalid excel format.");
			leadForm.setIsError("false");
			logger.info("LMS Exception occured in uploadExcel");
			sessionUserBean.setMessage("FAILED");
			//userMstrservice.insertBulkDataTransactionLogs(sessionUserBean);

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occured in uploadExcel");
			sessionUserBean.setMessage("FAILED");
			//userMstrservice.insertBulkDataTransactionLogs(sessionUserBean);
		}
		userMstrservice.insertBulkDataTransactionLogs(sessionUserBean);
		forward = mapping.findForward("search");
		return (forward);
	}// uploadExcel

	public ActionForward excelImport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
		LeadForm commonForm = (LeadForm) form;
		UserMstrService userMstrService = new UserMstrServiceImpl();
		MasterService masterService = new MasterServiceImpl();
		String startDate = null;	
		String endDate = null;
		sessionUserBean.setFileName("AssignedLeads.xls for Assigned leads");
		if (request.getParameter("startDate") != null)
			startDate = (String) request.getParameter("startDate");
		if (request.getParameter("endDate") != null)
			endDate = (String) request.getParameter("endDate");
		logger
				.info(sessionUserBean.getUserLoginId()
						+ " Entered into the excelImport method of SearchAssignedLeadsAction");
		AssignedLeadsManager assignedLeadsManager = new AssignedLeadsManagerImpl();
		try {
			List<BulkFeasibilityDTO> masterList = new ArrayList<BulkFeasibilityDTO>();
			masterList = assignedLeadsManager.listAssignedLeadsExcel(
					sessionUserBean.getUserLoginId(), startDate, endDate);
			request.setAttribute("assignedList", masterList);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurs in viewUserList: " + e.getMessage());
			sessionUserBean.setMessage("FAILED");
			userMstrService.insertBulkDataTransactionLogs(sessionUserBean);
			
		}
		
		request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-Disposition",
				"attachment;filename=AssignedLeads.xls");
		sessionUserBean.setMessage("SUCCESS");
		userMstrService.insertBulkDataTransactionLogs(sessionUserBean);
		return mapping.findForward("viewAssignedLeadsExcel");
	}
	public ActionForward excelImportFwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
		LeadForm commonForm = (LeadForm) form;
		UserMstrService userMstrService = new UserMstrServiceImpl();
		MasterService masterService = new MasterServiceImpl();
		String startDate = null;	
		String endDate = null;
		sessionUserBean.setFileName("AssignedLeads.xls for Assigned leads");
		if (request.getParameter("startDate") != null)
			startDate = (String) request.getParameter("startDate");
		if (request.getParameter("endDate") != null)
			endDate = (String) request.getParameter("endDate");
		logger
				.info(sessionUserBean.getUserLoginId()
						+ " Entered into the excelImport method of SearchAssignedLeadsAction");
		AssignedLeadsManager assignedLeadsManager = new AssignedLeadsManagerImpl();
		try {
			logger.info("Start date="+ commonForm.getStartDate());
			List<BulkFeasibilityDTO> masterList = new ArrayList<BulkFeasibilityDTO>();
			masterList = assignedLeadsManager.listAssignedLeadsExcel(
					sessionUserBean.getUserLoginId(), commonForm.getStartDate(), commonForm.getEndDate());
			request.setAttribute("assignedList", masterList);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurs in viewUserList: " + e.getMessage());
			sessionUserBean.setMessage("FAILED");
			userMstrService.insertBulkDataTransactionLogs(sessionUserBean);
			
		}
		
		request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-Disposition",
				"attachment;filename=AssignedLeads.xls");
		sessionUserBean.setMessage("SUCCESS");
		userMstrService.insertBulkDataTransactionLogs(sessionUserBean);
		return mapping.findForward("viewAssignedLeadsExcelFwd");
	}
	public ActionForward openErrLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("Inside bulkLeadAssignedErrLogFilePath openErrLog method");
		HttpSession session = request.getSession();
		java.util.Date date = new java.util.Date();
		String filePath = "", fileNameNew = "";

		try {
			filePath = (String) session
					.getAttribute("bulkLeadAssignedErrLogFilePath");
			fileNameNew = filePath.substring(filePath.lastIndexOf("/") + 1,
					filePath.length());

			response.setContentType("text/csv");
			response.addHeader("Content-Disposition", "attachment; filename="
					+ fileNameNew);

			java.io.File uFile = new java.io.File(filePath);
			int fSize = (int) uFile.length();
			java.io.FileInputStream fis = new java.io.FileInputStream(uFile);
			java.io.PrintWriter pw = response.getWriter();
			int c = -1;
			// Loop to read and write bytes.
			while ((c = fis.read()) != -1) {
				pw.print((char) c);
			}
			// Close output and input resources.
			fis.close();
			pw.flush();
			pw = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
