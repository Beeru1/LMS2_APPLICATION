package com.ibm.km.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.ibm.km.dao.impl.KmDashBoardDaoImpl;
import com.ibm.km.forms.KmViewDashboardBean;
import com.ibm.km.services.KmDashBoardService;
import com.ibm.km.services.impl.KmDashBoardServiceImpl;
import com.ibm.lms.common.Constants;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.common.UserDetails;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.DashBoardDTO;
import com.ibm.lms.dto.ReportsDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.UserMstrServiceImpl;




public class KmViewDashBoard extends DispatchAction {
	private static Logger logger = Logger.getLogger(KmViewDashBoard.class
			.getName());
	public ActionForward initViewReports(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmDashBoardDaoImpl kmdb=new KmDashBoardDaoImpl();
		KmViewDashboardBean reportFormBean = (KmViewDashboardBean) form;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String date = sdf.format(new java.util.Date().getTime());
		date = date.substring(0,10);
		reportFormBean.setReportDate(date);
		String restrictedflag=null;
		
	//	String restrictedTime=(PropertyReader.getAppValue("dashboard.restrictedtime")).toString();
		restrictedflag=kmdb.getParamValue("REPORT_AVAILABLE");
		reportFormBean.setRestrictedflag(restrictedflag);
		if(restrictedflag.equalsIgnoreCase("Y"))
		{
		String restrictedTime=kmdb.getParamValue("RESTRICTED_TIME");
		reportFormBean.setRestrictedTime(restrictedTime);
		
		String timeArray[]=restrictedTime.split("-");
		
		String message=(PropertyReader.getAppValue("dashboard.restriction")).toString();
		message=message+" : "+timeArray[0]+" - "+timeArray[1]+" hours";
		logger.info("message"+message);
		reportFormBean.setMessage(message);
		}
		String startHour=kmdb.getParamValue("START_HOUR");
		//String startHour=(PropertyReader.getAppValue("hourlyreport.starthour")).toString();
		reportFormBean.setStartHour(startHour);
		
		// Populating Report Type Dropdown
		ArrayList<ReportsDTO> list = new ArrayList<ReportsDTO>();
		ReportsDTO dto = null;
		dto = new ReportsDTO();
		dto.setReportId(1);
		dto.setReportName("Weekly");
		list.add(dto);
		dto = new ReportsDTO();
		dto.setReportId(2);
		dto.setReportName("Daily");
		list.add(dto);
		dto = new ReportsDTO();
		dto.setReportId(3);
		dto.setReportName("Hourly");
		list.add(dto);
		
		reportFormBean.setReportTypeList(list);
		logger.info("Inside initViewReport	s!!!!"+list.size());
		if(Utility.isValidRequest(request)) {
        	return mapping.findForward("error");
        }
		reportFormBean.setReportDataList(null);
		return mapping.findForward("success");
	}

	
	
	public ActionForward viewReport (ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward(); // return value
		
		KmViewDashboardBean commonForm = (KmViewDashboardBean) form;
		KmDashBoardDaoImpl kmdb=new KmDashBoardDaoImpl();
		KmDashBoardService viewreportImpl= new KmDashBoardServiceImpl();
		saveToken(request);
		String restrictedTime="";
		String restrictedflag=null;
	
		String message="";
		try 
		{
			//Put parameter which method to be called weekly/hourly/daily
			logger.info("Report Type of Dashboard is : "+commonForm.getSelectedReportId());
			ArrayList<DashBoardDTO> reportData = new ArrayList<DashBoardDTO>();
			ArrayList<ReportsDTO> list = new ArrayList<ReportsDTO>();
			if(commonForm.getSelectedReportId()==1)
			reportData = viewreportImpl.weeklyReportList();
			else if(commonForm.getSelectedReportId()==2)
			reportData = viewreportImpl.dailyReportList();
			else if(commonForm.getSelectedReportId()==3)
			{
				
				logger.info("Date is : "+commonForm.getReportDate());
				reportData = viewreportImpl.hourlyReportList(commonForm.getReportDate());
			}	
			
			commonForm.setReportDataList(reportData);
			logger.info("dashboard count"+commonForm.getReportDataList().size());
			
			restrictedflag=kmdb.getParamValue("REPORT_AVAILABLE");
			commonForm.setRestrictedflag(restrictedflag);
			if(restrictedflag.equalsIgnoreCase("Y"))
			{
			restrictedTime=kmdb.getParamValue("RESTRICTED_TIME");
			//String restrictedTime=(PropertyReader.getAppValue("dashboard.restrictedtime")).toString();
			commonForm.setRestrictedTime(restrictedTime);
			
			String timeArray[]=restrictedTime.split("-");
			message=(PropertyReader.getAppValue("dashboard.restriction")).toString();
			message=message+" : "+timeArray[0]+" - "+timeArray[1]+" hours";
			
			commonForm.setMessage(message);
			}
			logger.info("restrictedflag : "+restrictedflag+" message:"+message);
			String startHour=kmdb.getParamValue("START_HOUR");
			//String startHour=(PropertyReader.getAppValue("hourlyreport.starthour")).toString();
			commonForm.setStartHour(startHour);
			
			ReportsDTO dto = null;
			// Populating Report Type Dropdown
			
			dto = new ReportsDTO();
			dto.setReportId(1);
			dto.setReportName("Weekly");
			
			list.add(dto);
			dto = new ReportsDTO();
			dto.setReportId(2);
			dto.setReportName("Daily");
			list.add(dto);
			
			dto = new ReportsDTO();
			dto.setReportId(3);
			dto.setReportName("Hourly");
			list.add(dto);
			
			commonForm.setReportTypeList(list);
			request.setAttribute("commonForm", commonForm);		
			//return mapping.findForward("searchNew");
			
		}
		
		catch (Exception e) 
		{
			logger.error("Exception occurs in viewUserList: " + e.getMessage());
		}
		return mapping.findForward("searchNew");
		
	}
	
	public ActionForward excelImport (ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward(); // return value
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		UserMstrService userMstrService = new UserMstrServiceImpl();
		KmViewDashboardBean commonForm = (KmViewDashboardBean) form;
		KmDashBoardDaoImpl kmdb=new KmDashBoardDaoImpl();
		KmDashBoardService viewreportImpl= new KmDashBoardServiceImpl();
		saveToken(request);
		String restrictedTime="";
		String restrictedflag=null;
		userBean.setFileName("LeadDashBoardReport.xls for Dashboard Report");
		String message="";
		try 
		{
			//Put parameter which method to be called weekly/hourly/daily
			logger.info("Report Type of Dashboard is : "+commonForm.getSelectedReportId());
			ArrayList<DashBoardDTO> reportData = new ArrayList<DashBoardDTO>();
			ArrayList<ReportsDTO> list = new ArrayList<ReportsDTO>();
			if(commonForm.getSelectedReportId()==1)
			reportData = viewreportImpl.weeklyReportList();
			else if(commonForm.getSelectedReportId()==2)
			reportData = viewreportImpl.dailyReportList();
			else if(commonForm.getSelectedReportId()==3)
			{
				
				logger.info("Date is : "+commonForm.getReportDate());
				reportData = viewreportImpl.hourlyReportList(commonForm.getReportDate());
			}	
			
			commonForm.setReportDataList(reportData);
			logger.info("dashboard count"+commonForm.getReportDataList().size());
			
			restrictedflag=kmdb.getParamValue("REPORT_AVAILABLE");
			commonForm.setRestrictedflag(restrictedflag);
			if(restrictedflag.equalsIgnoreCase("Y"))
			{
			restrictedTime=kmdb.getParamValue("RESTRICTED_TIME");
			//String restrictedTime=(PropertyReader.getAppValue("dashboard.restrictedtime")).toString();
			commonForm.setRestrictedTime(restrictedTime);
			
			String timeArray[]=restrictedTime.split("-");
			message=(PropertyReader.getAppValue("dashboard.restriction")).toString();
			message=message+" : "+timeArray[0]+":00 - "+timeArray[1]+":00 hours";
			
			commonForm.setMessage(message);
			}
			logger.info("restrictedflag : "+restrictedflag+" message:"+message);
			String startHour=kmdb.getParamValue("START_HOUR");
			//String startHour=(PropertyReader.getAppValue("hourlyreport.starthour")).toString();
			commonForm.setStartHour(startHour);
			
			ReportsDTO dto = null;
			// Populating Report Type Dropdown
			
			dto = new ReportsDTO();
			dto.setReportId(1);
			dto.setReportName("Weekly");
			
			list.add(dto);
			dto = new ReportsDTO();
			dto.setReportId(2);
			dto.setReportName("Daily");
			list.add(dto);
			
			dto = new ReportsDTO();
			dto.setReportId(3);
			dto.setReportName("Hourly");
			list.add(dto);
			
			commonForm.setReportTypeList(list);
			request.setAttribute("commonForm", commonForm);		
			//return mapping.findForward("searchNew");
			
		}
		
		catch (Exception e) 
		{
			logger.error("Exception occurs in viewUserList: " + e.getMessage());
			userBean.setMessage("FAILED");
		}
		//return mapping.findForward("searchNew");
		request.setAttribute("assignedList",commonForm.getReportDataList());

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-Disposition","attachment;filename=LeadDashBoardReport.xls");
		userBean.setMessage("SUCCESS");
		userMstrService.insertBulkDataTransactionLogs(userBean);
		return mapping.findForward("viewDashBoardReportExcel");
		
		
	}
	

}
