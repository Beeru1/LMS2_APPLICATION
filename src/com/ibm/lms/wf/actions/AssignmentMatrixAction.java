package com.ibm.lms.wf.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import com.ibm.lms.common.Constants;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.AssignmentReportDTO;
import com.ibm.lms.dto.ProductLobDTO;

import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.BulkAssigmentService;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.impl.BulkAssigmentServiceImpl;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.wf.dto.BulkMatrixDownloadDTO;
import com.ibm.lms.wf.forms.AssignmentMatrixFormBean;

public class AssignmentMatrixAction extends DispatchAction{
	private static Logger logger = Logger.getLogger(AssignmentMatrixAction.class.getName());

	public ActionForward init(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		
				AssignmentMatrixFormBean commonForm = (AssignmentMatrixFormBean) form;
				HttpSession session = request.getSession();
				session.setAttribute("SAVE_LEAD_DATA", "true");
				UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
				ActionMessages messages = new ActionMessages();
			
				ArrayList<ProductLobDTO> lobList=null;
				MasterService masterService = new MasterServiceImpl();
				request.setAttribute("userBean", userBean);
				if(Utility.isValidRequest(request) && (session.getAttribute("URLFLAG")==null || !"N".equalsIgnoreCase((String)session.getAttribute("URLFLAG")))) {
		        	return mapping.findForward("error");
		        }
				
				if(userBean.getKmActorId().equals(Constants.CIRCLE_USER)){
					
					logger.info("circle coordinator loop");
					lobList=masterService.getAssignProductLobList(userBean.getLobList());
				}
				
				//leadRegistrationFormBean.reset(mapping,request);
				commonForm.reset();
				commonForm.setMsg("");
				saveToken(request);
				
				//request.setAttribute("circleList", masterService.getCircleList());
				if(userBean.getKmActorId().equals(Constants.CIRCLE_USER)){
					request.setAttribute("productLobList", lobList);
				}
				else
				{
				request.setAttribute("productLobList", masterService.getProductLobList());
				}
				
				//request.setAttribute("requestCategoryList", masterService.getRequsetCategoryList());
				
				logger.info(userBean.getUserLoginId() + " exited init method for Lead Assignment page.");
				
				return mapping.findForward("success");
				
		}
	
		public ActionForward insertRecord(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception 
				{
			
			String error = "";
			UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
			AssignmentMatrixFormBean commonForm = (AssignmentMatrixFormBean) form;
			
			if ( !isTokenValid(request) ) {
				request.getSession().setAttribute("URLFLAG", "N");
				  return init(mapping, form, request, response);
				}
			try
			{
 
				BulkAssigmentService  bulkAssigmentService = new BulkAssigmentServiceImpl();
				ActionMessages messages = new ActionMessages();
				HttpSession session = request.getSession();
				MasterService masterService = new MasterServiceImpl();
				//String radioValue=commonForm.isAssignment();
				
					commonForm.setCircleId(masterService.getCircleIdValue(commonForm.getCircleMstrId()));
					if(commonForm.getSelectedProductId() != 0){
						commonForm.setProductId(commonForm.getSelectedProductId()+"");
					}
					else{
						commonForm.setProductId("");
					}
				
				if(commonForm.getRequestCategoryId() != ""){
					
						commonForm.setRequestCategoryId(commonForm.getRequestCategoryId()+"");
					}
					else{
						commonForm.setRequestCategoryId("");
					}
				
					
					error = bulkAssigmentService.CreateAssignmentMatrix(commonForm,userBean);

					if(!error.equals(""))
					{
						commonForm.setMsg(error);
					//	populateAssignmentForm(mapping,form,request,response);
					}	
					else{
						commonForm.setMsg("");
						commonForm.reset();
						messages.add("msg",new ActionMessage("assignmentMatrix.creation.success"));
					//	populateAssignmentForm(mapping,form,request,response);
						//resetToken(request);
						saveMessages(request, messages);
						request.getSession().setAttribute("URLFLAG", "N");
						return init(mapping, commonForm, request, response);
					}
					populateAssignmentForm(mapping,form,request,response);

			}//try
			catch(LMSException km)
			{
				logger.info("KM Exception occured in uploadExcel");

			}
			catch (Exception e) {
				e.printStackTrace();
				logger.info("Error occured in uploadExcel");	
			}

			return mapping.findForward("success");	

				}
		public ActionForward viewAssignmentMatrix(	ActionMapping mapping,ActionForm form,	HttpServletRequest request,	HttpServletResponse response)	throws Exception {
					HttpSession session  =request.getSession();
					UserMstr userBean = (UserMstr)session.getAttribute("USER_INFO");
					logger.info(userBean.getUserLoginId() + " entered init method for beeeeeeeeeeeeeeee   Lead Registration page.==");
					List masterList = null;
					BulkAssigmentService bulkAssigmentService = new BulkAssigmentServiceImpl();
					try{
						
					if(request.getParameter("olmID") !=null && !"".equalsIgnoreCase(request.getParameter("olmID"))){
						String olmID = (String)request.getParameter("olmID");
						masterList = bulkAssigmentService.getAssignmentMatrixList(olmID);
					}
					else
					{
					//masterList = bulkAssigmentService.getAssignmentMatrixList(null);
						if(Utility.isValidRequest(request) && ("".equalsIgnoreCase(request.getParameter("URL"))|| request.getParameter("URL") == null)) {
				        	return mapping.findForward("error");
				        }
					}
					if(masterList !=null && masterList.size() > 0)
						request.setAttribute("assignmentMatrixList", masterList);
					
					}
					catch(Exception e)
					{
						logger.error("Exception occurs in viewAssignmentMatrixList: " + e.getMessage());
					}
					logger.info(userBean.getUserLoginId() + " exited init method for Assignment Matrix List page.");
					return mapping.findForward("list");	
			}
		public ActionForward DeleteAssignmentMatrix(ActionMapping mapping,ActionForm form,	HttpServletRequest request,	HttpServletResponse response)	throws Exception {
			UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
			logger.info(userBean.getUserLoginId() + " entered init method for  page.");
			List masterList = null;
			
			BulkAssigmentService bulkAssigmentService = new BulkAssigmentServiceImpl();
			AssignmentMatrixFormBean commonForm = (AssignmentMatrixFormBean) form;
			ActionMessages messages = new ActionMessages();
			
			try{
				if(request.getParameterValues("ids") !=null)
				{
					String[] ids = request.getParameterValues("ids");
					String error = bulkAssigmentService.deleteAssignmentMatrix(ids,userBean);
					if(!error.equals(""))
					{
						commonForm.setMsg(error);
						populateAssignmentForm(mapping,form,request,response);
				}
					else{
						commonForm.reset();
						commonForm.setMsg("");
						messages.add("msg",new ActionMessage("assignmentMatrix.deletion.success"));
						resetToken(request);
					}
				
					//masterList = bulkAssigmentService.getAssignmentMatrixList(null);
				}
			if(masterList !=null && masterList.size() > 0)
				request.setAttribute("assignmentMatrixList", masterList);
			saveMessages(request, messages);
			}
			catch(Exception e)
			{
				logger.error("Exception occurs in viewAssignmentMatrixList: " + e.getMessage());
			}
			logger.info(userBean.getUserLoginId() + " exited init method for Assignment Matrix List page.");
			return mapping.findForward("list");	
	}
		public void populateAssignmentForm(ActionMapping mapping,ActionForm form,	HttpServletRequest request,	HttpServletResponse response)throws LMSException
		{
			MasterService masterService = new MasterServiceImpl();
			AssignmentMatrixFormBean commonForm = (AssignmentMatrixFormBean) form;
			//request.setAttribute("circleList", masterService.getCircleList());
			commonForm.setProductLobID(0);

			//request.setAttribute("productLobList", masterService.getProductLobList());
		
			
			
			//request.setAttribute("cityList", masterService.getCityForCircle(commonForm.getCircleId()));
			
			/*		Commented By Parnika as now Zone/ Pincode RSU are populated by Ajax Call 
			 	if(!"".equalsIgnoreCase(commonForm.getCityCode())){
					request.setAttribute("pinCodeList", masterService.getPinCodeForCity(commonForm.getCityCode()));
					request.setAttribute("zoneList", masterService.getZoneForCity(commonForm.getCityCode()));
			}
			if(!"".equalsIgnoreCase(commonForm.getZoneCode()))
			{
				List rsuList = masterService.getRsuForZone(commonForm.getZoneCode());
				if(rsuList !=null && rsuList.size() > 0)
				request.setAttribute("rsuList", rsuList);
			}*/
			
			/* Added by Parnika */
			UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
			request.setAttribute("userBean", userBean);			
			ArrayList<ProductLobDTO> lobList=null;		
			if(userBean.getKmActorId().equals(Constants.CIRCLE_USER)){
				
				logger.info("circle coordinator loop");
				lobList=masterService.getAssignProductLobList(userBean.getLobList());
			}

			
			//request.setAttribute("circleList", masterService.getCircleList());
			if(userBean.getKmActorId().equals(Constants.CIRCLE_USER)){
				request.setAttribute("productLobList", lobList);
			}
			else
			{
			request.setAttribute("productLobList", masterService.getProductLobList());
			}

		}
		
		
		public ActionForward viewOLMApprovers(ActionMapping mapping,ActionForm form,HttpServletRequest request,	HttpServletResponse response)throws LMSException
		{
			MasterService masterService = new MasterServiceImpl();
			AssignmentMatrixFormBean commonForm = (AssignmentMatrixFormBean) form;
			commonForm.setProductLobID(0);
			UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
			request.setAttribute("userBean", userBean);
			ActionMessages messages = new ActionMessages();
			
			try
			{
			String olmId= (String) request.getAttribute("OlmID");
			
			ArrayList<AssignmentReportDTO> populatelist = 	masterService.getApproversList(olmId);
			logger.info("populate list size is*************"+populatelist.size());
			
			if(populatelist !=null && populatelist.size() > 0)
				request.setAttribute("APPROVER LIST", populatelist);
			saveMessages(request, messages);
			}
			catch(Exception e)
			{
				logger.error("Exception occurs in viewAssignmentMatrixList: " + e.getMessage());
			}
			logger.info(userBean.getUserLoginId() + " exited init method for approver Matrix List page.");
			return mapping.findForward("list");	
			

		}
		
}
