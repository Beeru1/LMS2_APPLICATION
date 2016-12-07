package com.ibm.lms.actions;
/*  Activate user Account */
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.lms.common.UserDetails;
import com.ibm.lms.dao.ActivateUserAccountDao;
import com.ibm.lms.dao.impl.ActivateUserAccountDaoImpl;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.forms.ActivateUserAccountBean;

public class ActivateUserAccountAction extends DispatchAction {
	private static Logger logger = Logger.getLogger(ActivateUserAccountAction.class
			.getName());
	
	/**
	 * Method to initialize ActivateUserAccount
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		logger.info(UserDetails.getUserLoginId(request)+" : Entered init method");
		forward = mapping.findForward("init");

		logger.info(UserDetails.getUserLoginId(request)+" : Exiting init method");
		// Finish with
		return (forward);  

	}
	
	public ActionForward getExpiredLocked(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception 
	{
		logger.info(UserDetails.getUserLoginId(request)+" : Entered getExpiredLocked method");
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); // return value
		ActivateUserAccountBean activateUserAccountBean = (ActivateUserAccountBean) form;
		UserMstr userBean = UserDetails.getUserDetails(request);
		try {
			// Getting the data from JSP
			//int circleId = Integer.parseInt(activateUserAccountBean.getCircleId());
			//String circleName = activateUserAccountBean.getCircleName();
			//String userId = activateUserAccountBean.getUserId();
			//String userLoginId = activateUserAccountBean.getUserLoginId();
			//String userName = activateUserAccountBean.getUserName();
			String flag = activateUserAccountBean.getFlag();
	
			// Calling DAO method to insert data in databse
			ActivateUserAccountDao dao = ActivateUserAccountDaoImpl.activateUserAccountDaoInstance();
			ArrayList expiredUserList = null;
			if("Locked".equals(flag)){
				//changed by Atul
				expiredUserList = dao.getExpiredLocked(Integer.parseInt(userBean.getKmActorId()), 3, flag);
			}
			if("Expired".equals(flag)){
				expiredUserList = dao.getExpiredLocked(Integer.parseInt(userBean.getKmActorId()), 0, flag);
			}
			if("Forced-LogOff".equals(flag)){
				expiredUserList = dao.getExpiredLocked(Integer.parseInt(userBean.getKmActorId()), 0, flag);
			}
			if(expiredUserList==null || expiredUserList.size()==0) {
				errors.add("errors", new ActionError("record.not.found"));
				saveErrors(request, errors);
				forward = mapping.findForward("showList");
				return forward;
			}
			activateUserAccountBean.setExpiredUserList(expiredUserList);
			activateUserAccountBean.setCircleId(userBean.getElementId());
			
			// forward = mapping.findForward("inserted");
			forward = mapping.findForward("showList");
		} catch (Exception e) {

			// Report the error using the appropriate name and ID.
			//errors.add("errors", new ActionError(e.getMessage()));
			logger.error(UserDetails.getUserLoginId(request)+" : Error occured in getExpiredLocked method",e);
			forward = mapping.findForward("error");

		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);

		}

		// Finish with
		logger.info(UserDetails.getUserLoginId(request)+" : Exiting getExpiredLocked method");
		return (forward);

	}	

	
	public ActionForward updateExpired(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception 
	{
		logger.info(UserDetails.getUserLoginId(request)+" : Entered getExpiredLocked method");
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); // return value
		ActivateUserAccountBean activateUserAccountBean = (ActivateUserAccountBean) form;
		UserMstr userBean = UserDetails.getUserDetails(request);
		try {
			// Getting the data from JSP
			String flag = activateUserAccountBean.getFlag();
			String password = "0cdb20b73116f9f877e9a47fb66995cc1ad11584";
			logger.info(activateUserAccountBean.getSubmittedIds());
						
			// Calling DAO method to insert data in databse
			ActivateUserAccountDao dao = ActivateUserAccountDaoImpl.activateUserAccountDaoInstance();;
			ArrayList expiredUserList = null;
			dao.updateExpired(activateUserAccountBean.getSubmittedIds(),password,flag);
			
			activateUserAccountBean.setExpiredUserList(expiredUserList);
			activateUserAccountBean.setCircleId(userBean.getElementId());
			errors.add("user.activated", new ActionError("user.activated"));
			// forward = mapping.findForward("inserted");
			//forward = mapping.findForward("showList");
			forward=getExpiredLocked(mapping, form, request, response);
		} catch (Exception e) {

			// Report the error using the appropriate name and ID.
			//errors.add("errors", new ActionError(e.getMessage()));
			logger.error(UserDetails.getUserLoginId(request)+" : Error occured in getExpiredLocked method",e);
			forward = mapping.findForward("error");

		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);

		}

		// Finish with
		logger.info(UserDetails.getUserLoginId(request)+" : Exiting getExpiredLocked method");
		return (forward);

	}	
	
}
