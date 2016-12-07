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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.lms.common.CommonMstrUtil;
import com.ibm.lms.common.Constants;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.ProductLobDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.BulkAssigmentService;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.impl.BulkAssigmentServiceImpl;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.wf.actions.AssignmentMatrixAction;
import com.ibm.lms.wf.forms.AssignmentMatrixFormBean;

public class AssignmentApprovals extends DispatchAction
{
	private static Logger logger = Logger.getLogger(AssignmentApprovals.class.getName());

	public ActionForward init(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	throws Exception {

		AssignmentMatrixFormBean commonForm = (AssignmentMatrixFormBean) form;
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
		ActionMessages messages = new ActionMessages();
		logger.info(userBean.getUserLoginId() + " entered init method for Assignment approvals page.");
		List masterList = null;
		BulkAssigmentService bulkAssigmentService = new BulkAssigmentServiceImpl();
		saveToken(request);
		try
		{
			if(Utility.isValidRequest(request)) {
	        	return mapping.findForward("error");
	        }
			masterList = CommonMstrUtil.getAssignmentMatrixList(userBean.getUserLoginId());
		
		if(masterList !=null && masterList.size() > 0)
			request.setAttribute("assignmentMatrixList", masterList);
		}
		catch(Exception e)
		{
			logger.error("Exception occurs in init method: " + e.getMessage());
		}
		logger.info(userBean.getUserLoginId() + " exited init method for init method l1 approver List page.");
		return mapping.findForward("list");	
		
		
}
	
	public ActionForward rejectAssignmentMatrix(ActionMapping mapping,ActionForm form,	HttpServletRequest request,	HttpServletResponse response)	throws Exception {
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
		logger.info(userBean.getUserLoginId() + " entered init method for Lead Registration page.");
		List masterList = null;
		
		BulkAssigmentService bulkAssigmentService = new BulkAssigmentServiceImpl();
		AssignmentMatrixFormBean commonForm = (AssignmentMatrixFormBean) form;
		ActionMessages messages = new ActionMessages();
		String flag=request.getParameter("flag");
		String remrks=request.getParameter("remarks");
		HttpSession session = request.getSession();
		String appL1="";
		String appL2="";
		
		String error;
		String flagVal="";
		
		if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}
		try{
			
			
				
			if(request.getParameterValues("ids") !=null)
			{
				String[] ids = request.getParameterValues("ids");
				
						
				flagVal=flag+",L1";
				
				error = bulkAssigmentService.rejectAssignmentmatrix(ids,userBean,flagVal,commonForm);
				
				if(!error.equals(""))
				{
					commonForm.setMsg(error);
					
					populateAssignmentForm(mapping,form,request,response);
			}
				else{
					commonForm.reset();
					commonForm.setMsg("");
					if(flag.equalsIgnoreCase("true"))
					{
					messages.add("msg",new ActionMessage("msg.approve"));
					}
					else 
					{
					messages.add("msg",new ActionMessage("msg.reject"));	
					}
					resetToken(request);
				}
				
			}
		if(masterList !=null && masterList.size() > 0)
			request.setAttribute("assignmentMatrixList", masterList);
		saveMessages(request, messages);
		String approvalPending = CommonMstrUtil.getPendingapprovalsCount(userBean.getUserLoginId());
		if(approvalPending !=null && approvalPending.trim().length() >0)
		{
		String[] ids = approvalPending.split(",");
		appL1= ids[0];	//3		
		appL2 = ids[1];  //0
		}
		
		if(Integer.parseInt(appL1)>0 && Integer.parseInt(appL1)<9 )  //pending for L1 approval:
		{
			appL1 ="0".concat(appL1);
		}
				
		if(Integer.parseInt(appL2)>0 && Integer.parseInt(appL2)<9)   //pending for l2 approval:
		{
			appL2 ="0".concat(appL2);
			
		}
		
		session.setAttribute("appL1", appL1);
		session.setAttribute("appL2", appL2);
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
		commonForm.setProductLobID(0);
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
	
}
