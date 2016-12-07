package com.ibm.km.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibm.lms.actions.SmsLeadClosureAction;
import com.ibm.lms.common.LMSStatusCodes;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.wf.dao.AssignedLeadsDAO;
import com.ibm.lms.wf.dao.impl.AssignedLeadsDAOImpl;
import com.ibm.lms.wf.dto.Leads;
import com.ibm.lms.wf.forms.LeadForm;


 

public class SmsLeadClosureServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String leadId=null;
	String status=null;
	String dist_id=null;
	String message=null;
	ActionForward responseMsg=null;
	private static final Logger logger = Logger.getLogger(SmsLeadClosureServlet.class);
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//** invoke doPost() method from doGet() **//* 
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		doPost(request, response);
	}  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		PrintWriter out=null;
		Boolean checkSMSFormat=true;
		leadId=request.getParameter("LEAD_ID");
		status=request.getParameter("STATUS");
		message=request.getParameter("MESSAGE");
		out=response.getWriter();
		logger.info("====== in do post pullsmsfortsmservler ==========");
		try
		{
		if(leadId!=null)
		{
		
			SmsLeadClosureAction smsLeadClosureAction = new SmsLeadClosureAction();
				ActionForm form;
				ActionMapping mapping;
			//	responseMsg= 
					smsLeadClosureAction.closeTheLeadSms(request);
			
		}
		else 
		{
		}
		out.print(responseMsg);
		out.flush();
		out.close();
		
		}
		catch(Exception ex)
		{
			out.flush();
			out.close();
			ex.printStackTrace();
		}
		
	}
	public Boolean closeTheLeadSms(LeadForm commonForm) throws LMSException {
		AssignedLeadsDAO assignedLeadsDAO = new AssignedLeadsDAOImpl();
		Boolean flag = false;
		ArrayList<Leads> masterList = new ArrayList<Leads>();
		Leads lead = new Leads();
		lead.setLeadID(commonForm.getLeadID().toString());
		lead.setStatus(commonForm.getActionType());
		lead.setRemarks(commonForm.getClosureComments());
		lead.setSubStatus(commonForm.getSubStatusID());
		lead.setCafNumber(commonForm.getCafNumber());
		lead.setUpdatedBy(commonForm.getUpdatedBy());
		lead.setUdId(commonForm.getUdId());
		masterList.add(lead);
		flag = assignedLeadsDAO.closeTheLeadSms(masterList);
		return flag;
		//return assignedLeadsDAO.closeTheLead(commonForm);
	}
}
