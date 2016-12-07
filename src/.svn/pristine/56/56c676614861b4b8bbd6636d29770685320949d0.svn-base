package com.ibm.lms.wf.actions;

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
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.wf.forms.LeadForm;
import com.ibm.lms.wf.services.AssignedLeadsManager;
import com.ibm.lms.wf.services.impl.AssignedLeadsManagerImpl;

public class QualifyLeadAction extends DispatchAction{
	private static Logger logger = Logger.getLogger(SearchAssignedLeadsAction.class
			.getName());
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		HttpSession session = request.getSession();
		logger.info(UserDetails.getUserLoginId(request)+" : Entered init method");
		AssignedLeadsManager assignedLeadsManager = new AssignedLeadsManagerImpl();
		UserMstr sessionUserBean =(UserMstr) session.getAttribute("USER_INFO");
		logger.info(UserDetails.getUserLoginId(request)+" : Exiting init method");
		LeadForm commonForm = (LeadForm) form;
		try {
			List assignedLeads = new ArrayList();
			assignedLeads = assignedLeadsManager.listAssignedLeads(sessionUserBean.getUserLoginId(),null,null,"N");
			commonForm.setAssignedLeads(assignedLeads);
		}
		// Finish with
	 catch (Exception e) {
		logger.error("Exception occurs in viewUserList: " + e.getMessage());
	}
		forward = mapping.findForward("search");
		return (forward);  

	}

}
