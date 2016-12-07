package com.ibm.lms.actions;


import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.lms.common.UserDetails;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.AlertDTO;
import com.ibm.lms.dto.SourceDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.forms.AlertsFormBean;
import com.ibm.lms.services.AlertService;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.AlertServiceImpl;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.services.impl.UserMstrServiceImpl;


public class AlertsAction  extends DispatchAction
{
	private static final Logger logger;
	static 
	{
		logger = Logger.getLogger(AlertsAction.class);
	}
	
	private static String CREATEALERT_UPDATE = "CreateAlertUpdate";
	
	public ActionForward init(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		ActionForward forward = new ActionForward(); 
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initAlerts method");
		ActionErrors errors = new ActionErrors();
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		AlertsFormBean alertsFormBean = (AlertsFormBean)form;
		MasterService mstrService = new MasterServiceImpl();
		UserMstrService userService = new UserMstrServiceImpl();
		ArrayList<UserMstr> userDetailList = null;
		saveToken(request);
		AlertDTO alertDto=new AlertDTO();
		ArrayList<AlertDTO> alertList = new ArrayList<AlertDTO>();
		ArrayList<SourceDTO> sourceList= new ArrayList<SourceDTO>();
		saveToken(request);
		try{
			if(Utility.isValidRequest(request)) {
	        	return mapping.findForward("error");
	        }
			
			request.setAttribute("alertList", mstrService.getAlertList());
			request.setAttribute("sourceList", mstrService.getSourceList());
			
			} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured********************in init method*******: " + e.getMessage());
		}
				
		return mapping.findForward("initAlertReport");
		
		
	}
	public ActionForward selectDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		ActionForward forward = new ActionForward(); 
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initAlerts method&&&&&&&&&&&&");
		ActionErrors errors = new ActionErrors();
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		AlertsFormBean alertsFormBean = (AlertsFormBean)form;
		MasterService mstrService = new MasterServiceImpl();
		UserMstrService userService = new UserMstrServiceImpl();
		
		saveToken(request);
	
		ArrayList<AlertDTO> alertList = new ArrayList<AlertDTO>();
		String alert=request.getParameter("alertId");
		String source=request.getParameter("source");
		String type=request.getParameter("type");
		try{
			request.setAttribute("alertList", mstrService.getAlertList());
			String msg=mstrService.getAlertMsg(alert,source,type);
			
			//String status=mstrService.getStatus(alert);
			PrintWriter out=response.getWriter();
			out.println(msg);
			//out.println(status);
			} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured********************in init method*******: " + e.getMessage());
		}
				
			return null;
		
		
	}
	public ActionForward selectDetailsStatus(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		ActionForward forward = new ActionForward(); 
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initAlerts method");
		ActionErrors errors = new ActionErrors();
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		AlertsFormBean alertsFormBean = (AlertsFormBean)form;
		MasterService mstrService = new MasterServiceImpl();
		UserMstrService userService = new UserMstrServiceImpl();
		
		saveToken(request);
	
		ArrayList<AlertDTO> alertList = new ArrayList<AlertDTO>();
		String alert=request.getParameter("alertId");
		String source=request.getParameter("source");
		try{
			request.setAttribute("alertList", mstrService.getAlertList());
			String status=mstrService.getStatus(alert,source);
			PrintWriter out=response.getWriter();
			out.println(status);
			} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured********************in init method*******: " + e.getMessage());
		}
				
			return null;
		
		
	}
	
		public ActionForward selectDetailsSubject(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		ActionForward forward = new ActionForward(); 
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initAlerts method");
		ActionErrors errors = new ActionErrors();
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		AlertsFormBean alertsFormBean = (AlertsFormBean)form;
		MasterService mstrService = new MasterServiceImpl();
		UserMstrService userService = new UserMstrServiceImpl();
		
		saveToken(request);
	
		ArrayList<AlertDTO> alertList = new ArrayList<AlertDTO>();
		String alert=request.getParameter("alertId");
		String source=request.getParameter("source");
		try{
			//logger.info("selectDetailsSubject "+alert);
			request.setAttribute("alertList", mstrService.getAlertList());
			String subject=mstrService.getSubject(alert,source);
			PrintWriter out=response.getWriter();
			out.println(subject);
			} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured********************in init method*******: " + e.getMessage());
		}
				
			return null;
		
		
	}
	public ActionForward selectDetailsEmail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		ActionForward forward = new ActionForward(); 
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initAlerts method");
		ActionErrors errors = new ActionErrors();
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		AlertsFormBean alertsFormBean = (AlertsFormBean)form;
		MasterService mstrService = new MasterServiceImpl();
		UserMstrService userService = new UserMstrServiceImpl();
		
		saveToken(request);
	
		ArrayList<AlertDTO> alertList = new ArrayList<AlertDTO>();
		String alert=request.getParameter("alertId");
		String source=request.getParameter("source");
		//"alertdId"+alert);
		try{
			request.setAttribute("alertList", mstrService.getAlertList());
			String email=mstrService.getEmail(alert,source);
			//"email"+email);
			PrintWriter out=response.getWriter();
			out.println(email);
			} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured********************in init method*******: " + e.getMessage());
		}
				
			return null;
	}
	
	
	
	
	
	public ActionForward selectDetailsAlertType(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		ActionForward forward = new ActionForward(); 
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initAlerts method");
		ActionErrors errors = new ActionErrors();
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		AlertsFormBean alertsFormBean = (AlertsFormBean)form;
		MasterService mstrService = new MasterServiceImpl();
		UserMstrService userService = new UserMstrServiceImpl();
		
		saveToken(request);
	
		ArrayList<AlertDTO> alertList = new ArrayList<AlertDTO>();
		String alert=request.getParameter("alertId");
		String source=request.getParameter("source");
		
		try{
			request.setAttribute("alertList", mstrService.getAlertList());
			String alertType=mstrService.getAlertType(alert,source);
			
			PrintWriter out=response.getWriter();
			out.println(alertType);
			} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured********************in init method*******: " + e.getMessage());
		}
				
			return null;
	}
	
	public ActionForward selectDetailsSms(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		ActionForward forward = new ActionForward(); 
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initAlerts method");
		ActionErrors errors = new ActionErrors();
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		AlertsFormBean alertsFormBean = (AlertsFormBean)form;
		MasterService mstrService = new MasterServiceImpl();
		UserMstrService userService = new UserMstrServiceImpl();
		
		saveToken(request);
	
		ArrayList<AlertDTO> alertList = new ArrayList<AlertDTO>();
		String alert=request.getParameter("alertId");
		String source=request.getParameter("source");
		try{
			request.setAttribute("alertList", mstrService.getAlertList());
			String sms=mstrService.getSms(alert,source);
			PrintWriter out=response.getWriter();
			out.println(sms);
			} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured********************in init method*******: " + e.getMessage());
		}
				
			return null;
		
		
	}
	public ActionForward selectDetailsThresholdCount(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		ActionForward forward = new ActionForward(); 
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initAlerts method");
		ActionErrors errors = new ActionErrors();
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		AlertsFormBean alertsFormBean = (AlertsFormBean)form;
		MasterService mstrService = new MasterServiceImpl();
		UserMstrService userService = new UserMstrServiceImpl();
		
		saveToken(request);
	
		ArrayList<AlertDTO> alertList = new ArrayList<AlertDTO>();
		String alert=request.getParameter("alertId");
		String source=request.getParameter("source");
		try{
			request.setAttribute("alertList", mstrService.getAlertList());
			String count=mstrService.getCount(alert,source);
			PrintWriter out=response.getWriter();
			out.println(count);
			} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured********************in init method*******: " + e.getMessage());
		}
				
			return null;
		
		
	}
	public ActionForward selectDetailsThresholdPeriod(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		ActionForward forward = new ActionForward(); 
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initAlerts method");
		ActionErrors errors = new ActionErrors();
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		AlertsFormBean alertsFormBean = (AlertsFormBean)form;
		MasterService mstrService = new MasterServiceImpl();
		UserMstrService userService = new UserMstrServiceImpl();
		
		saveToken(request);
	
		ArrayList<AlertDTO> alertList = new ArrayList<AlertDTO>();
		String alert=request.getParameter("alertId");
		String source=request.getParameter("source");
		try{
			request.setAttribute("alertList", mstrService.getAlertList());
			String period=mstrService.getPeriod(alert,source);
			PrintWriter out=response.getWriter();
			out.println(period);
			} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured********************in init method*******: " + e.getMessage());
		}
				
			return null;
		
		
	}
	public ActionForward selectDetailsSource (ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		ActionForward forward = new ActionForward(); 
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initAlerts method");
		ActionErrors errors = new ActionErrors();
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		AlertsFormBean alertsFormBean = (AlertsFormBean)form;
		MasterService mstrService = new MasterServiceImpl();
		UserMstrService userService = new UserMstrServiceImpl();
		
		saveToken(request);
	
		ArrayList<AlertDTO> alertList = new ArrayList<AlertDTO>();
		String alert=request.getParameter("alertId");
		try{
			request.setAttribute("alertList", mstrService.getAlertList());
			String source=mstrService.getSource(alert);
			PrintWriter out=response.getWriter();
			out.println(source);
			} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured********************in init method*******: " + e.getMessage());
		}
			
			return null;
	}
	/*
	public ActionForward viewAlertDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
		AlertsFormBean alertsBean = (AlertsFormBean)form;
		try
		{
			int alertId=0;
			if(!"".equals(alertsBean.getAlertId()))
			{
				AlertService alertService = new AlertServiceImpl();
				MasterService masterService  = new MasterServiceImpl();
				alertId =alertsBean.getAlertId();

				ArrayList<AlertDTO> alert_details =  alertService.getAlertDetails(alertId);
							
				request.setAttribute("ALERT_DETAILS",alert_details);
				
			}
		}
		catch (Exception e) {
		    e.printStackTrace();
			logger.error("Exception occured while getting Alert Details :" + e.getMessage());
			ActionErrors errors = new ActionErrors();
			errors.add("errors",new ActionError("alert.not.found"));
			saveErrors(request,errors);
		}
		return mapping.findForward("viewAlertDetails"); 
	}
	*/
	
	public ActionForward insertRecord(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{ 
		logger.info("insert method");
		ActionMessages messages = new ActionMessages();
		try{
		ActionForward forward = new ActionForward(); 
		MasterService mstrService = new MasterServiceImpl();
		int result=0;
		String remotAddress=request.getRemoteAddr();
		logger.info("Request comming from :"+remotAddress);
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
		logger.info(userBean.getUserLoginId() + " entered insert method for Alerts page.");
		/*
		if ( !isTokenValid(request) ) {
			  return init(mapping, form, request, response);
			}*/
		ActionErrors errors = new ActionErrors();
	    AlertsFormBean alertsBean = (AlertsFormBean)form;
	    alertsBean.setCreatedBy(userBean.getUserLoginId());
		AlertDTO alertDto=new AlertDTO();
		
		AlertService alertService = new AlertServiceImpl();
		alertDto.setAlertId(alertsBean.getAlertId());
		alertDto.setAlertType(alertsBean.getAlert_type());
		alertDto.setEmail(alertsBean.getEmail());
		alertDto.setSms(alertsBean.getSms());
		alertDto.setSource(alertsBean.getSource());
		alertDto.setThreshold_count(alertsBean.getThreshold_count());
		alertDto.setSubjectTemplate(alertsBean.getSubjectEmail());
		
		logger.info("alert id is : "+alertsBean.getAlertId());
		if(alertsBean.getAlertId()==7)
		{
			alertDto.setThreshold_period(alertsBean.getThreshold_period1());
		}
		else if(alertsBean.getAlertId()==8)
			
		{
			alertDto.setThreshold_period(alertsBean.getThreshold_period2());
		}
		else
		{
		alertDto.setThreshold_period(alertsBean.getThreshold_period());
		}
		alertDto.setMessage(alertsBean.getMessage());
		alertDto.setMessage1(alertsBean.getMessage1());
		alertDto.setStatus(alertsBean.getStatus());
		if (alertService.checkDuplicateAlert(alertDto))
		{
			 //forward = mapping.findForward(CREATEALERT_UPDATE);
			 //return mapping.findForward("initAlertReport");
			 logger.info("alert updated successfully");
		}
		else
		{
		    result =alertService.insertAlert(alertDto);
		    logger.info("alert saved successfully");
	
         }
			
			messages.add("msg1",new ActionMessage("alerts.success"));
			ArrayList<AlertDTO> alertList = new ArrayList<AlertDTO>();
			ArrayList<SourceDTO> sourceList= new ArrayList<SourceDTO>();
		
			request.setAttribute("alertList", mstrService.getAlertList());
			request.setAttribute("sourceList", mstrService.getSourceList());
			alertsBean.setAlertId(0);
			alertsBean.reset();
			
		
			} catch (Exception e) {
			messages.add("msg1",new ActionMessage("alerts.failure"));
			e.printStackTrace();
			logger.error("Exception occured********************in init method*******: " + e.getMessage());
		}
			saveMessages(request, messages);
	
    return mapping.findForward("initAlertReport");
}
}