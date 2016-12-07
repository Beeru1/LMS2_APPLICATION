package com.ibm.lms.wf.actions;


/**
 * @author Amandeep Singh
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.lms.common.Constants;
import com.ibm.lms.common.UserDetails;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.CircleMstrDto;
import com.ibm.lms.dto.ProductDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.services.CircleManagementService;
import com.ibm.lms.services.ProductMappingService;
import com.ibm.lms.services.impl.CircleManagementServiceImpl;
import com.ibm.lms.services.impl.ProductMappingServiceImpl;
import com.ibm.lms.wf.forms.CircleManagementForm;

public class CircleManagementAction extends DispatchAction{
	private static Logger logger = Logger.getLogger(CircleManagementAction.class
			.getName());
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		if(Utility.isValidRequest(request)) {
        	return mapping.findForward("error");
        }
		
		ActionForward forward = new ActionForward(); // return value
		logger.info(UserDetails.getUserLoginId(request)+" : Entered init method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		CircleManagementForm circleManagementForm = (CircleManagementForm) form;
		CircleManagementService circleManagementService = new CircleManagementServiceImpl();
		circleManagementForm.setLobList(circleManagementService.getLobList());
		
		forward = mapping.findForward("initCircleManagement");
		logger.info(UserDetails.getUserLoginId(request)+" : Exited init method");
		// Finish with
		return (forward);  

	}
	
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		CircleManagementForm circleManagementForm = (CircleManagementForm) form;
		CircleManagementService circleManagementService = new CircleManagementServiceImpl();
		
		CircleMstrDto circleMstrDto = new CircleMstrDto();
		
		circleManagementForm.setLobList(circleManagementService.getLobList());
		
		circleMstrDto.setLobId(circleManagementForm.getSelectedLobId());
		circleMstrDto.setUserLoginId(userBean.getUserLoginId());
		circleManagementForm.setCircleList(circleManagementService.getCircleList(circleMstrDto));
		
		forward = mapping.findForward("initCircleManagement");

		return (forward);  

	}
	
	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		CircleManagementForm circleManagementForm = (CircleManagementForm) form;
		CircleManagementService circleManagementService = new CircleManagementServiceImpl();
		
		CircleMstrDto circleMstrDto = new CircleMstrDto();

		circleManagementForm.setLobList(circleManagementService.getLobList());
		
		circleMstrDto.setLobId(circleManagementForm.getSelectedLobId());
		circleMstrDto.setUserLoginId(userBean.getUserLoginId());
		circleMstrDto.setCircleName(circleManagementForm.getCircleName());
		circleMstrDto.setCircleDesc(circleManagementForm.getCircleDesc());
		
		int rows=circleManagementService.createCircle(circleMstrDto);
		
		if(rows==0)
		{
			circleManagementForm.setMessage("Circle Not Created.");
		}
		
		else if(rows==1)
		{
			circleManagementForm.setMessage("Circle Successfully Created.");
		}
		
		else if(rows==Constants.CIRCLE_EXISTS)
		{
			circleManagementForm.setMessage("Circle Already Present.");
		}
		

		forward = mapping.findForward("initCircleManagement");

		return (forward);  

	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		CircleManagementForm circleManagementForm = (CircleManagementForm) form;
		CircleManagementService circleManagementService = new CircleManagementServiceImpl();
		
		CircleMstrDto circleMstrDto = new CircleMstrDto();

		circleManagementForm.setLobList(circleManagementService.getLobList());
		
		circleMstrDto.setLobId(circleManagementForm.getSelectedLobId());
		circleMstrDto.setUserLoginId(userBean.getUserLoginId());
		circleMstrDto.setCircleName(circleManagementForm.getCircleName());
		circleMstrDto.setCircleDesc(circleManagementForm.getCircleDesc());
		circleMstrDto.setCircleId(circleManagementForm.getCircleIdToDelete());
		
		int rows=circleManagementService.editCircle(circleMstrDto);
		
		
		if(rows==0)
		{
			circleManagementForm.setMessage("Circle Not Updated.");
		}
		
		else if(rows==1)
		{
			circleManagementForm.setMessage("Circle Successfully Updated.");
		}
		
		else if(rows==Constants.CIRCLE_EXISTS)
		{
			circleManagementForm.setMessage("Circle Doesnt Exists.");
		}

		forward = mapping.findForward("initCircleManagement");
		// Finish with
		return (forward);  

	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		CircleManagementForm circleManagementForm = (CircleManagementForm) form;
		CircleManagementService circleManagementService = new CircleManagementServiceImpl();
		
		CircleMstrDto circleMstrDto = new CircleMstrDto();

		circleMstrDto.setLobId(circleManagementForm.getSelectedLobId());
		circleMstrDto.setUserLoginId(userBean.getUserLoginId());
		circleMstrDto.setCircleName(circleManagementForm.getCircleName());
		circleMstrDto.setCircleDesc(circleManagementForm.getCircleDesc());
		circleMstrDto.setCircleId(circleManagementForm.getCircleIdToDelete());
		
		
		int rows=circleManagementService.deleteCircle(circleMstrDto);
		
		
		if(rows==0)
		{
			circleManagementForm.setMessage("Circle Not Deleted.");
		}
		
		else
		{
			circleManagementForm.setMessage("Circle Successfully Deleted.");
		}
		

		forward = mapping.findForward("initCircleManagement");

		return (forward);  

	}
}
