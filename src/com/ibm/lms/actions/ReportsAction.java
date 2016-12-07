package com.ibm.lms.actions;

/**
 * @author Bhaskar Sharma
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.ibm.lms.common.Constants;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.common.UserDetails;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.CityDTO;
import com.ibm.lms.dto.DashBoardReportDTO;
import com.ibm.lms.dto.IDOCReportDTO;
import com.ibm.lms.dto.ReportsDTO;
import com.ibm.lms.dto.MTDReportDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.forms.ReportsFormBean;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.ReportService;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.services.impl.ReportServiceImpl;
import com.ibm.lms.services.impl.UserMstrServiceImpl;


public class ReportsAction extends DispatchAction {
	
	private static Logger logger = Logger.getLogger(ReportsAction.class.getName());
	
	public ActionForward initView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		
		ActionForward forward = new ActionForward(); // return value
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initView method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		
		ReportsFormBean reportBean = (ReportsFormBean) form;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
		String date = sdf.format(new java.util.Date().getTime() - 24*60*60*1000);
		date = date.substring(0,10);
		logger.info(date.toString());
		reportBean.setReportDate(date);
		
		MasterService masterService = new MasterServiceImpl();
		
		// Populating Report Type Dropdown
		
		reportBean.setReportTypeList(masterService.getReportList());
		if(Utility.isValidRequest(request)) {
        	return mapping.findForward("error");
        }
		
		forward = mapping.findForward("initViewReports");
		logger.info(UserDetails.getUserLoginId(request)+" : Exiting init method");
		// Finish with
		return (forward);  

	}
	
	public ActionForward viewReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		logger.info(UserDetails.getUserLoginId(request)+" : Entered insert method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
		UserMstrService userMstrService = new UserMstrServiceImpl();
		ReportsFormBean formBean = (ReportsFormBean) form;
		MasterService masterService = new MasterServiceImpl();
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try{
			formBean.setMessage("");
			logger.info("Selected date "+formBean.getReportDate());
			String date = formBean.getReportDate();
			int reportId = formBean.getSelectedReportId();
			
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			String path = bundle.getString("viewReports.path");
			
			/*String[] dateProps = date.split("-");
			date="";
			for(int i = 0;i<dateProps.length;i++)
				date = date+Integer.parseInt(dateProps[i])+"_";
			
			date = date.substring(0,date.lastIndexOf("_"));*/
			logger.info("date "+date);
			StringBuffer filePath=new StringBuffer(path);
			String fileName = masterService.getReportName(reportId);
			logger.info("FILEEE"+fileName);
			userBean.setFileName(fileName);
			filePath.append(fileName).append("_").append(date).append(".csv");
			
			// logger.info("filePath "+filePath);
		
			File file=new File(filePath.toString());
			logger.info(filePath);
			if(file.length()==0){
				formBean.setMessage("Report Not Found");
				errors.add("file.not.found", new ActionError("report.not.found"));
				saveErrors(request, errors);
				// Populating Report Type Dropdown
				
				formBean.setReportTypeList(masterService.getReportList());
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
				String date1 = sdf.format(new java.util.Date().getTime() - 24*60*60*1000);
				date1 = date1.substring(0,10);
				logger.info(date1.toString());
				formBean.setReportDate(date1);
				formBean.setSelectedReportId(-1);
				userBean.setMessage("FAILED");
				userMstrService.insertBulkDataTransactionLogs(userBean);
				return mapping.findForward("viewReports");
			}
			response.setContentType ("application/vnd.ms-excel");
			// response.setHeader("Cache-Control", "no-cache");
			response.setContentLength((int)file.length());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
			String timeStr = sdf.format(new java.util.Date().getTime() - 24*60*60*1000);
			response.setHeader("Content-Disposition","attachment; filename="+fileName+timeStr+".csv");
			//response.setHeader("Cache-Control", "no-cache");
			OutputStream outStream =response.getOutputStream();
			FileInputStream inStream = new FileInputStream(file);
			try {	 
				 
				 byte[] buf = new byte[8192];
				
				 int sizeRead = 0;
				 
				 while ((sizeRead = inStream.read(buf, 0, buf.length)) > 0) {
					
					 outStream.write(buf, 0, sizeRead);
				 }
				 inStream.close();
				 outStream.close();
			}
			 catch(IllegalStateException
						ignoredException) {
				if(outStream!=null){
					outStream.close();			
				}
				if(inStream!=null){
					inStream.close();			
				}
				logger.error(ignoredException);
			}				
			userBean.setMessage("SUCCESS");	
			userMstrService.insertBulkDataTransactionLogs(userBean);
		} catch (Exception e) {
			formBean.setMessage("Report Not Found");
			errors.add("file.not.found", new ActionError("report.not.found"));
			saveErrors(request, errors);
			// Populating Report Type Dropdown
			
			formBean.setReportTypeList(masterService.getReportList());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
			String date2 = sdf.format(new java.util.Date().getTime() - 24*60*60*1000);
			date2 = date2.substring(0,10);
			logger.info(date2.toString());
			formBean.setReportDate(date2);
			userBean.setMessage("FAILED");	
			userMstrService.insertBulkDataTransactionLogs(userBean);
		}
		// Finish with
		return null;

	}	
	public ActionForward initLeadDetailsMTD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initLeadDetailsMTD method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		ReportsFormBean reportBean = (ReportsFormBean) form;
		
		MasterService mstrService = new MasterServiceImpl();
		UserMstrService userService = new UserMstrServiceImpl();
		ArrayList<UserMstr> userDetailList = null;
		saveToken(request);
		if(Utility.isValidRequest(request)) {
        	return mapping.findForward("error");
        }
		try{
			//reportBean.setCircleList(mstrService.getCircleList());
			userDetailList = mstrService.getLogedInUserDetails(userBean.getUserLoginId() , 0 );
			reportBean.setStatusList(mstrService.getLeadStatusListMTD());
			//added on 03/04/2014 by amarjeet
			logger.info("circleId"+userBean.getCircleId());
			logger.info("circleName"+userBean.getCircleName());
			if(userBean.getKmActorId().equalsIgnoreCase(Constants.SUPER_ADMIN_ACTOR)|| 
					userBean.getKmActorId().equalsIgnoreCase(Constants.PAN_INDIA_ACTOR)){
				reportBean.setLobList(mstrService.getLobList());
			}
			else{				
				reportBean.setLobList(mstrService.getLobForUser(userBean.getUserLoginId()));
				reportBean.setLobId(userDetailList.get(0).getLobId());
				if(userDetailList.get(0).getLobId()!=null){
					
					reportBean.setCircleList(userService.getAllChildrenNew(userDetailList.get(0).getLobId(),userBean.getUserLoginId()));	
				}
				if(userDetailList.get(0).getCircleId()!=null && userBean.getKmActorId().equalsIgnoreCase(Constants.SUPER_ADMIN_ACTOR)|| 
						userBean.getKmActorId().equalsIgnoreCase(Constants.PAN_INDIA_ACTOR)){
					
					reportBean.setPartnerList(userService.getAllChannelPartner(userDetailList.get(0).getLobId(), userDetailList.get(0).getCircleId()));
					reportBean.setCircleMstrId(Integer.parseInt(userDetailList.get(0).getCircleId()));	
				}
				if(userDetailList.get(0).getZoneFlag()!=null && userDetailList.get(0).getZoneFlag().equalsIgnoreCase(Constants.ZONE_CODE_FLAG_VALUE) && userDetailList.get(0).getCircleId()!=null){
										
						reportBean.setZoneList(userService.getAllChildrenZone("1", userDetailList.get(0).getCircleId()));
						
				}else if (userDetailList.get(0).getZoneFlag()!=null && userDetailList.get(0).getZoneFlag().equalsIgnoreCase(Constants.CITY_ZONE_CODE_FLAG_VALUE) && userDetailList.get(0).getCircleId()!=null)
				{
					reportBean.setZoneList(userService.getAllChildrenZone("2", userDetailList.get(0).getCircleId()));
				}
				if(userDetailList.get(0).getZoneFlag()!=null && userDetailList.get(0).getZoneFlag().equalsIgnoreCase(Constants.ZONE_CODE_FLAG_VALUE) && userDetailList.get(0).getZoneCode()!=null){
					reportBean.setSelectedZoneId(mstrService.getZoneId(userDetailList.get(0).getZoneCode()));	
				}
				else if (userDetailList.get(0).getZoneFlag()!=null && userDetailList.get(0).getZoneFlag().equalsIgnoreCase(Constants.CITY_ZONE_CODE_FLAG_VALUE) && userDetailList.get(0).getZoneCode()!=null)
				{
					reportBean.setSelectedZoneId(mstrService.getCityZoneId(userDetailList.get(0).getZoneCode()));	
				}
			}
		
			reportBean.setParam(mstrService.getParameterName(PropertyReader.getAppValue("mtd.report.no.of.days")));
			//userService.getAllChildrenNew(productLobId, userLoginId)
			reportBean.setZoneFlag(userDetailList.get(0).getZoneFlag());
			
		
			
			
			//reportBean.setCircleMstrId();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurs in viewUserList: " + e.getMessage());
		}
		request.setAttribute("Mobile_FLAG", mstrService.getParameterName("Mobile_FLAG"));
		forward = mapping.findForward("initLeadMTDReports");
		logger.info(UserDetails.getUserLoginId(request)+" : Exiting init method");
		// Finish with
		return (forward);  
	}
	
	
	//<Added by Sudhanshu
	public ActionForward initViewLeadLifecycleReports(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initViewLeadLifecycleReports method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		
		if(Utility.isValidRequest(request)) {
        	return mapping.findForward("error");
        }
		
		ReportsFormBean reportBean = (ReportsFormBean) form;
		MasterService mstrService = new MasterServiceImpl();
		saveToken(request);
		try{
			//reportBean.setCircleList(mstrService.getCircleList());
			reportBean.setParam(mstrService.getParameterName(PropertyReader.getAppValue("lifeCycle.report.no.of.days")));		
			reportBean.setStatusList(mstrService.getLeadStatusList());
			reportBean.setLobList(mstrService.getLobList());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurs in viewUserList: " + e.getMessage());
		}
				
		forward = mapping.findForward("leadLifecycle");
		logger.info(UserDetails.getUserLoginId(request)+" : Exiting initViewLeadLifecycleReports method");
		// Finish with
		return (forward);  
	}
	
	public ActionForward viewLeadLifecycleReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		logger.info("Inside viewLeadLifecycleReport??????????????????????");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
		UserMstrService userMstrService = new UserMstrServiceImpl();
		ReportsFormBean formBean = (ReportsFormBean) form;
		MasterService masterService = new MasterServiceImpl();
		ReportService reportService = new ReportServiceImpl();
		userBean.setFileName("LifecycleReport.xls for Lead Lifecycle Report");
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try{
			formBean.setMessage("");
			logger.info("Selected date "+formBean.getReportDate());	
			
			
		
			String channelPartner = formBean.getChPartnerId();
			logger.info("channelPartner"+ channelPartner);			
		
			request.setAttribute("reportList",reportService.getLeadLifecycleReport(formBean));

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("content-Disposition","attachment;filename=LifeCycleReport.xls");
			
			userBean.setMessage("SUCCESS");
			userMstrService.insertBulkDataTransactionLogs(userBean);
			return mapping.findForward("viewLifeCycleReportExcel");
			
		} catch (Exception e) {
			e.printStackTrace();
			formBean.setMessage("Report Not Found");
			errors.add("file.not.found", new ActionError("report.not.found"));
			saveErrors(request, errors);
			// Populating Report Type Dropdown
			
			formBean.setReportTypeList(masterService.getReportList());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
			String date2 = sdf.format(new java.util.Date().getTime() - 24*60*60*1000);
			date2 = date2.substring(0,10);
			logger.info(date2.toString());
			formBean.setReportDate(date2);
			userBean.setMessage("FAILED");
			userMstrService.insertBulkDataTransactionLogs(userBean);

		}
		// Finish with
		return null;

	}
	
	//Added by Sudhanshu>
	
	public ActionForward viewLeadMTDReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		logger.info("Inside viewLeadMTDReport??????????????????????");
		ActionErrors errors = new ActionErrors();
	
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		UserMstrService userMstrService = new UserMstrServiceImpl();
		ReportsFormBean formBean = (ReportsFormBean) form;
		MasterService masterService = new MasterServiceImpl();
		ReportService reportService = new ReportServiceImpl();
		ArrayList<UserMstr> userDetailList = null;
		HttpSession session = request.getSession();
		userBean.setFileName("LeadDetailsMTDReport.xls for MTDREPORT");
		
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try{
			formBean.setMessage("");
			logger.info("Selected date "+formBean.getReportDate());	
			if(!formBean.getLobId().equals(null) && !formBean.getLobId().equals("")){
				userDetailList = masterService.getLogedInUserDetails(userBean.getUserLoginId(), Integer.parseInt(formBean.getLobId()));
			}			
	
			int ZoneId = formBean.getSelectedZoneId();
			if (userDetailList!=null && userDetailList.size() >0) {
				String zoneFlag = userDetailList.get(0).getZoneFlag();
				logger.info("ZoneId"+ ZoneId);
				String ZoneCode =null;
				if (ZoneId!=0 && zoneFlag != null){
				ZoneCode =masterService.getCityZoneCode(ZoneId+"", zoneFlag);
				}
				formBean.setZoneCode(ZoneCode);
				formBean.setZoneFlag(userDetailList.get(0).getZoneFlag());
				if(Constants.CIRCLE_COORDINATOR_ACTOR.equalsIgnoreCase(userBean.getKmActorId())) {
				String circleMstrId=userDetailList.get(0).getCircleId();
				formBean.setCircleMstrId(Integer.parseInt(circleMstrId));
			}
				//formBean.setCircleName(masterService.getCircleName(circleMstrId));
			}
			
			session.setAttribute("LOGIN_USER_ACTOR_ID", userBean.getKmActorId());
			formBean.setUserActorId(userBean.getKmActorId());		
		
			request.setAttribute("reportList",reportService.getLeadMTDReport(formBean));

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("content-Disposition","attachment;filename=LeadDetailsMTDReport.xls");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Report not found**************due to :***"+Utility.getStackTrace(e));
			formBean.setMessage("Report Not Found");
			errors.add("file.not.found", new ActionError("report.not.found"));
			saveErrors(request, errors);
			// Populating Report Type Dropdown
			
			formBean.setReportTypeList(masterService.getReportList());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
			String date2 = sdf.format(new java.util.Date().getTime() - 24*60*60*1000);
			date2 = date2.substring(0,10);
			logger.info(date2.toString());
			formBean.setReportDate(date2);
			userBean.setMessage("FAILED");
			userMstrService.insertBulkDataTransactionLogs(userBean);
		}
		// Finish with
		userBean.setMessage("SUCCESS");
		userBean.setLobId(formBean.getLobId());
		
		//logger.info("LLLLLLL"+formBean.getLobName());
		userBean.setCircleId(String.valueOf(formBean.getCircleMstrId()));
		//logger.info("CCCCC"+formBean.getCircleMstrId());
		userBean.setZoneCode(formBean.getZoneCode());
		//logger.info("ZZZZZ"+formBean.getZoneName());
		userBean.setChannelPartnerId(formBean.getChPartnerId());
		//logger.info("CCCCCHHH"+formBean.getChPartnerId());
		userBean.setLeadStatus(String.valueOf(formBean.getStatusId()));
		//logger.info("ssssss"+formBean.getStatusId());
		userBean.setStartDate(formBean.getStartDate());
		//logger.info("STTTT"+formBean.getStartDate());
		userBean.setEndDate(formBean.getEndDate());
		//logger.info("SEEEE"+formBean.getEndDate());
		userBean.setFlag("Y");
		request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));
		request.setAttribute("Mobile_FLAG_MTD", masterService.getParameterName("Mobile_FLAG_MTD"));
		//logger.info("ABBBBBBBBBBBBBBBBBBBBBBBBBBB"+request.getAttribute("Mobile_FLAG"));
		userMstrService.insertBulkDataTransactionLogs(userBean);
		return mapping.findForward("viewMTDReportExcel");
		

	}	
	
	
	
	public ActionForward MTDReportDAYMonthwise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		

		if(Utility.isValidRequest(request)) {
        	return mapping.findForward("error");
        }
		ActionForward forward = new ActionForward(); // return value
		logger.info(UserDetails.getUserLoginId(request)+" : Entered MTDReportDAY_Monthwise method");
		ReportsFormBean formBean = (ReportsFormBean) form;
		MasterService masterService = new MasterServiceImpl();
		List<ReportsDTO> newReportList = new ArrayList<ReportsDTO>();
		newReportList = masterService.getReportListDayMonthWise();
		if("Y".equalsIgnoreCase(masterService.getParameterName("QUATERLY_REPORT_FLAG")))
		{
			for(ReportsDTO report:newReportList)
			{
				if(report.getReportId()==28)
					newReportList.remove(report);
			}
				
		}
		else
		{
			for(ReportsDTO report:newReportList)
			{
				if(report.getReportId()==30)
					newReportList.remove(report);
			}
			
		}
		formBean.setReportTypeList((ArrayList) newReportList);
		forward = mapping.findForward("FtdMTDReportdownload");
		logger.info(UserDetails.getUserLoginId(request)+" : Exiting MTDReportDAY_Monthwise method");
		// Finish with
		return (forward);  

	}

	
	public ActionForward viewLeadMTDReportDay_Month_wise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("Inside viewLeadMTDReportDay_Month_wise??????????????????????");
		ActionErrors errors = new ActionErrors();

		ReportsFormBean formBean = (ReportsFormBean) form;
		ReportService reportService = new ReportServiceImpl();
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
	
		
		try{
			
			
			
			formBean.setMessage("");
		
		if(formBean.getReportTime().equals("27")){
			
			request.setAttribute("Report_FLAG", "Daily");
			
			
		}else if(formBean.getReportTime().equals("28")){
	
			request.setAttribute("Report_FLAG", "Monthly");
		
		}
		else if(formBean.getReportTime().equals("30")){
			
			request.setAttribute("Report_FLAG", "Quarterly");
		
		}
		
			request.setAttribute("reportList",reportService.getLeadMTDReportDayMonthwise(formBean,request));

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Report not found**************due to :***"+Utility.getStackTrace(e));
			formBean.setMessage("Report Not Found");
			errors.add("file.not.found", new ActionError("report.not.found"));
			saveErrors(request, errors);
			
			
					
		}
	

		
		
		return mapping.findForward("MTDReportExcelDayOrMonthwise");
		

	}	
	
	public ActionForward searchDatatoPopulateGrid(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		
		logger.info("Inside searchDatatoPopulateGrid of ReportAction");
		String reportTime="";
		
		reportTime=request.getParameter("reportTime").toString();
			
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward(); 
		ActionErrors errors = new ActionErrors();

		ReportsFormBean formBean = (ReportsFormBean) form;
		ReportService reportService = new ReportServiceImpl();
		MasterService masterService = new MasterServiceImpl();
		List<ReportsDTO> newReportList = new ArrayList<ReportsDTO>();
		newReportList = masterService.getReportListDayMonthWise();
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
		try{
			formBean.setReportflag(reportTime);
			
			if("Y".equalsIgnoreCase(masterService.getParameterName("QUATERLY_REPORT_FLAG")))
			{
				for(ReportsDTO report:newReportList)
				{
					if(report.getReportId()==28)
						newReportList.remove(report);
				}
					
			}
			else
			{
				for(ReportsDTO report:newReportList)
				{
					if(report.getReportId()==30)
						newReportList.remove(report);
				}
				
			}
			formBean.setReportTypeList((ArrayList) newReportList);
		if(formBean.getReportTime().equals("27")){
			
			
			request.setAttribute("reportListFtdMtd",reportService.getLeadMTDReportDayMonthwiseGridPopulate(formBean,request));
			formBean.setReportinitStatus("true");
						
			
		}else if(formBean.getReportTime().equals("28")){
	
		
			request.setAttribute("reportListFtdMtd",reportService.getLeadMTDReportDayMonthwiseGridPopulate(formBean,request));
			formBean.setReportinitStatus("true");
		}
		else if(formBean.getReportTime().equals("30")){
			
			
			request.setAttribute("reportListFtdMtd",reportService.getLeadMTDReportDayMonthwiseGridPopulate(formBean,request));
			formBean.setReportinitStatus("true");
		}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Report not found**************due to :***"+Utility.getStackTrace(e));
			formBean.setMessage("Report Not Found");
			errors.add("file.not.found", new ActionError("report.not.found"));
			saveErrors(request, errors);
					
		}
	
		return mapping.findForward("FtdMTDReportdownload");
		}


	
	public ActionForward PopulateGridfordetails(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		
		logger.info("Inside PopulateGridfordetails of ReportAction");
	
		logger.info("ajax method call");
		
		//ActionForward forward = new ActionForward(); 
		ActionErrors errors = new ActionErrors();
		ReportsFormBean formBean = (ReportsFormBean) form;
		ReportService reportService = new ReportServiceImpl();
		MasterService masterService = new MasterServiceImpl();
		List<MTDReportDTO>  countDetailsList = new ArrayList<MTDReportDTO>();
		//HttpSession session = request.getSession();
		
		//for ajax call
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		//for ajax call
		
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
				
		try{
		
			formBean.setReportTypeList(masterService.getReportListDayMonthWise());
			formBean.setFtdreportdetailstatus("true");
			formBean.setReportinitStatus("true");
			
			
		
			
			formBean.setReportType(request.getParameter("reporttype"));
			formBean.setHeaderid(request.getParameter("headerid"));
		if(!"".equalsIgnoreCase(request.getParameter("leadstatusid"))||!request.getParameter("leadstatusid").equals(null)||!"0".equalsIgnoreCase(request.getParameter("leadstatusid"))){
			formBean.setStatusId(Integer.parseInt(request.getParameter("leadstatusid")));}
			formBean.setUserloginid(userBean.getUserLoginId());
		
			
		
			countDetailsList=null;
			
			countDetailsList=reportService.getFTDMTDReportCountDetails(formBean);
			
			if (countDetailsList != null && countDetailsList.size() > 0)
			{
				for (int intCounter = 0; intCounter < countDetailsList.size(); intCounter++)
				{
					optionElement = root.addElement("row");
					optionElement.addAttribute("leadId", ((MTDReportDTO)countDetailsList.get(intCounter)).getLeadId());
					optionElement.addAttribute("customerName", ((MTDReportDTO)countDetailsList.get(intCounter)).getCustomerName());
					optionElement.addAttribute("address", ((MTDReportDTO)countDetailsList.get(intCounter)).getAddress());
					optionElement.addAttribute("customerMobile", ((MTDReportDTO)countDetailsList.get(intCounter)).getCustomerMobile());
					optionElement.addAttribute("longitude", ((MTDReportDTO)countDetailsList.get(intCounter)).getLatitude());
					optionElement.addAttribute("latitude", ((MTDReportDTO)countDetailsList.get(intCounter)).getLongitude());
					optionElement.addAttribute("leadStatus", ((MTDReportDTO)countDetailsList.get(intCounter)).getLeadStatus());
					optionElement.addAttribute("leadSubStatus", ((MTDReportDTO)countDetailsList.get(intCounter)).getLeadSubStatus());
					optionElement.addAttribute("assignedto", ((MTDReportDTO)countDetailsList.get(intCounter)).getAssignedto());
					optionElement.addAttribute("assignedsince", ((MTDReportDTO)countDetailsList.get(intCounter)).getAssignedsince());
					optionElement.addAttribute("lastmodifiedby", ((MTDReportDTO)countDetailsList.get(intCounter)).getLastmodifiedby());
					optionElement.addAttribute("remarks", ((MTDReportDTO)countDetailsList.get(intCounter)).getRemarks());
					optionElement.addAttribute("headerID", formBean.getHeaderid());
				}
			}
		
	
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "No-Cache");
			
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Report not found**************due to :***"+Utility.getStackTrace(e));
			formBean.setMessage("Report Not Found");
			errors.add("file.not.found", new ActionError("report.not.found"));
			saveErrors(request, errors);
					
		}
	
		
		
		return null;
		
		}
	
	
	
	
	public ActionForward PopulateGridfordetailsExcelDownload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("---------------------Inside PopulateGridfordetailsExcelDownload----------------------");
		ActionForward forward = new ActionForward(); 
		ActionErrors errors = new ActionErrors();
		ReportsFormBean formBean = (ReportsFormBean) form;
		ReportService reportService = new ReportServiceImpl();
		MasterService masterService = new MasterServiceImpl();
		HttpSession session = request.getSession();
		
		
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
				
		try{
		
			
			
		
			
			
			formBean.setUserloginid(userBean.getUserLoginId());
			
			
			formBean.setUserActorId(userBean.getKmActorId());
			
			request.setAttribute("FtdMtdCountdetails",reportService.getFTDMTDReportCountDetails(formBean));
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Report not found**************due to :***"+Utility.getStackTrace(e));
			formBean.setMessage("Report Not Found");
			errors.add("file.not.found", new ActionError("report.not.found"));
			saveErrors(request, errors);
					
		}
	
		
		//request.setAttribute("countdetailsFlag", true);
		return mapping.findForward("FTDMTDCOUNTReportExcel");
		
		

	}	
	
	
	//end
	
	
	//<Added by Amarjeet
	public ActionForward initDashBoardReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initDashBoardReport method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		
		ReportsFormBean reportBean = (ReportsFormBean) form;
		
		MasterService mstrService = new MasterServiceImpl();
		saveToken(request);
		try{
			
			if(Utility.isValidRequest(request)) {
	        	return mapping.findForward("error");
	        }
			//reportBean.setCircleList(mstrService.getCircleList());
			reportBean.setParam(mstrService.getParameterName(PropertyReader.getAppValue("dashboard.report.no.of.days")));		
			reportBean.setStatusList(mstrService.getLeadStatusList());
			reportBean.setLobList(mstrService.getLobList());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurs in viewUserList: " + e.getMessage());
		}
				
		forward = mapping.findForward("dashboardReport");
		logger.info(UserDetails.getUserLoginId(request)+" : Exiting initDashBoardReport method");
		// Finish with
		return (forward);  
	}
	
	public ActionForward viewDashboardReport(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,	
			HttpServletResponse response)
			throws Exception {
			logger.info("lead registration action");
				ReportsFormBean reportFormBean = (ReportsFormBean)form;
				ActionForward forward = new ActionForward(); 
				ReportService service = new ReportServiceImpl();
				/*if ( !isTokenValid(request) ) {
					  return mapping.findForward("error");
					}*/
				//UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
				//UserMstrService userMstrService = new UserMstrServiceImpl();
				forward =  mapping.findForward("dashboardReport");
				//userBean.setFileName("View Dash Board report");
				reportFormBean.setInitStatus("false");	
				
				request.setAttribute("LEAD_LIST",service.getDashboardReport(reportFormBean));
				/*if(("".equals(reportFormBean.getStartDate()) && "".equals(reportFormBean.getEndDate())))
				{
					reportFormBean.reset(mapping, request);
					reportFormBean.setInitStatus("true");	
				}else
				{
					
					reportFormBean.setInitStatus("false");	
					ReportService service = new ReportServiceImpl();
					if((!"".equals(reportFormBean.getStartDate()) && !"".equals(reportFormBean.getEndDate())))
					{
						logger.info("Inside false");
						request.setAttribute("LEAD_LIST",service.getDashboardReport(reportFormBean));
					}
					
				}*/
				//userBean.setMessage("SUCCESS");
				//userMstrService.insertBulkDataTransactionLogs(userBean);
				return forward;
		}
	
	public ActionForward excelImport(ActionMapping mapping,	ActionForm form,HttpServletRequest request,	HttpServletResponse response)throws Exception 
	{
		logger.info("Inside excel Import");
		ReportsFormBean reportFormBean = (ReportsFormBean)form;
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		UserMstrService userMstrService = new UserMstrServiceImpl();
			ReportService service = new ReportServiceImpl();
			String startDate = null;
			String endDate = null;
			String date = null;
			userBean.setFileName("View Dash board Report");
			if(request.getParameter("startDate") !=null)
			startDate = (String) request.getParameter("startDate");
			reportFormBean.setStartDate(startDate);
			if(request.getParameter("endDate") !=null)
				endDate = (String) request.getParameter("endDate");
				reportFormBean.setEndDate(endDate);
				
				if(request.getParameter("date") !=null){
					date = (String) request.getParameter("date");
					reportFormBean.setDate(date);
					
				}
					
				try {
					List<DashBoardReportDTO> masterList = new ArrayList<DashBoardReportDTO>();
					
					masterList = service.getDashboardReport(reportFormBean);
					request.setAttribute("dashList", masterList);
					
					
			} catch (Exception e) {
				logger.error("Exception occurs in excelImport: " + e.getMessage());
				userBean.setMessage("FAILED");
				userMstrService.insertBulkDataTransactionLogs(userBean);
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("content-Disposition","attachment;filename=DashboardReport.xls");
			userBean.setMessage("SUCCESS");
			userMstrService.insertBulkDataTransactionLogs(userBean);
			return mapping.findForward("dashboardReportExcel");
	}
	/* Added By Parnika on 30-Apr 2014 for Dirty/Duplicate Report */
	
	public ActionForward initViewDirtyDuplicate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initViewDirtyDuplicate method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		saveToken(request);
		if(Utility.isValidRequest(request)) {
        	return mapping.findForward("error");
        }
		ReportsFormBean reportBean = (ReportsFormBean) form;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
		String date = sdf.format(new java.util.Date().getTime() - 24*60*60*1000);
		date = date.substring(0,10);
		logger.info(date.toString());
		reportBean.setReportDate(date);
		
		MasterService masterService = new MasterServiceImpl();
		
		// Populating Report Type Dropdown
		
		reportBean.setReportTypeList(masterService.getReportListAdmin());
		
		
		forward = mapping.findForward("initViewDirtyDuplicateReport");
		logger.info(UserDetails.getUserLoginId(request)+" : Exiting init method");
		// Finish with
		return (forward);  

	}
	
	
	
	public ActionForward viewReportDirtyDuplicate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		logger.info(UserDetails.getUserLoginId(request)+" : Entered viewReportDirtyDuplicate method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		//UserMstr userBean = (UserMstr)session.getAttribute("USER_INFO");
		ReportsFormBean formBean = (ReportsFormBean) form;
		MasterService masterService = new MasterServiceImpl();
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		UserMstrService userMstrService = new UserMstrServiceImpl();
		userBean.setFileName("ViewReportDirtyDuplicate");
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try{
			formBean.setMessage("");
			logger.info("Selected date "+formBean.getReportDate());
			String date = formBean.getReportDate();
			int reportId = formBean.getSelectedReportId();
			
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			String path = bundle.getString("viewReports.path");
			

			logger.info("date "+date);
			StringBuffer filePath=new StringBuffer(path);
			String fileName = masterService.getReportName(reportId);
			userBean.setFileName(fileName+".xls for Dirty/Duplicate Dumps");
			filePath.append(fileName).append("_").append(date).append(".csv");
			
			// logger.info("filePath "+filePath);
		
			File file=new File(filePath.toString());
			logger.info(filePath);
			if(file.length()==0){
				formBean.setMessage("Report Not Found");
				errors.add("file.not.found", new ActionError("report.not.found"));
				saveErrors(request, errors);
				// Populating Report Type Dropdown
				
				formBean.setReportTypeList(masterService.getReportListAdmin());
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
				String date1 = sdf.format(new java.util.Date().getTime() - 24*60*60*1000);
				date1 = date1.substring(0,10);
				logger.info(date1.toString());
				formBean.setReportDate(date1);
				formBean.setSelectedReportId(-1);
				userBean.setMessage("FAILED");
				userMstrService.insertBulkDataTransactionLogs(userBean);
				return mapping.findForward("initViewDirtyDuplicateReport");
			}
			response.setContentType ("application/vnd.ms-excel");
			// response.setHeader("Cache-Control", "no-cache");
			response.setContentLength((int)file.length());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
			String timeStr = sdf.format(new java.util.Date().getTime() - 24*60*60*1000);
			response.setHeader("Content-Disposition","attachment; filename="+fileName+timeStr+".csv");
			//response.setHeader("Cache-Control", "no-cache");
			OutputStream outStream =response.getOutputStream();
			FileInputStream inStream = new FileInputStream(file);
			try {	 
				 
				 byte[] buf = new byte[8192];
				
				 int sizeRead = 0;
				 
				 while ((sizeRead = inStream.read(buf, 0, buf.length)) > 0) {
					
					 outStream.write(buf, 0, sizeRead);
				 }
				 inStream.close();
				 outStream.close();
			}
			 catch(IllegalStateException
						ignoredException) {
				if(outStream!=null){
					outStream.close();			
				}
				if(inStream!=null){
					inStream.close();			
				}
				logger.error(ignoredException);
			}				
			userBean.setMessage("SUCCESS");
			userMstrService.insertBulkDataTransactionLogs(userBean);
		} catch (Exception e) {
			formBean.setMessage("Report Not Found");
			errors.add("file.not.found", new ActionError("report.not.found"));
			saveErrors(request, errors);
			// Populating Report Type Dropdown
			
			formBean.setReportTypeList(masterService.getReportListAdmin());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
			String date2 = sdf.format(new java.util.Date().getTime() - 24*60*60*1000);
			date2 = date2.substring(0,10);
			logger.info(date2.toString());
			formBean.setReportDate(date2);
			userBean.setMessage("FAILED");
			userMstrService.insertBulkDataTransactionLogs(userBean);
		}
		// Finish with
		return null;

	}
	/* End of changes by Parnika on 30-Apr-2014  */
	
	//function added by Nancy Agrawal.
	public ActionForward initiDOCReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		logger.info(UserDetails.getUserLoginId(request)+" : Entered initiDOCReport method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		ReportsFormBean reportBean = (ReportsFormBean) form;
		MasterService mstrService = new MasterServiceImpl();
		saveToken(request);
		try{
			if(Utility.isValidRequest(request)) {
				logger.info("999999");
	        	return mapping.findForward("error");
	        }
			reportBean.setParam(mstrService.getParameterName(PropertyReader.getAppValue("iDOC.report.no.of.days")));;		
			//reportBean.setStatusList(mstrService.getLeadStatusList());
			reportBean.setLobList(mstrService.getLobList());
			
			}
			 catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurs in viewUserList: " + e.getMessage());
		}
			 request.setAttribute("Mobile_FLAG", mstrService.getParameterName("Mobile_FLAG"));
		forward = mapping.findForward("initiDOCReport");
		logger.info(UserDetails.getUserLoginId(request)+" : Exiting init method");
		// Finish with
		return (forward);  
	}	
	
//added by Nancy Agrawal.
	
	public ActionForward viewiDOCReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		logger.info("Inside viewiDOCReport**************************");
		ActionErrors errors = new ActionErrors();
	
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		ReportsFormBean formBean = (ReportsFormBean) form;
		MasterService masterService = new MasterServiceImpl();
		ReportService reportService = new ReportServiceImpl();
		ArrayList<IDOCReportDTO> reportList = new ArrayList<IDOCReportDTO>();
		HttpSession session = request.getSession();
		
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try{
			formBean.setMessage("");
			logger.info("Selected date "+formBean.getReportDate());	
			request.setAttribute("reportList",reportService.getiDOCReport(formBean));
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("content-Disposition","attachment;filename=iDOCReport.xls");
		
				
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Report not found**************due to :***"+Utility.getStackTrace(e));
			formBean.setMessage("Report Not Found");
			errors.add("file.not.found", new ActionError("report.not.found"));
			saveErrors(request, errors);
			
			formBean.setReportTypeList(masterService.getReportList());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
			String date2 = sdf.format(new java.util.Date().getTime() - 24*60*60*1000);
			date2 = date2.substring(0,10);
			logger.info(date2.toString());
			formBean.setReportDate(date2);
		
		}
		// Finish with
		request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));
		return mapping.findForward("viewiDOCReportExcel");
	}
		
		
		
	//added by Nancy Agrawal.
	
	public ActionForward excel_Import(ActionMapping mapping,ActionForm form,HttpServletRequest request,	HttpServletResponse response)throws Exception 
	{
		logger.info("Inside excel Import for iDOCReports:*************************");
		ReportsFormBean reportFormBean = (ReportsFormBean)form;
			
			ReportService service = new ReportServiceImpl();
		
			UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
			MasterService masterService = new MasterServiceImpl();
			UserMstrService userMstrService = new UserMstrServiceImpl();
			String startDate = null;
			String endDate = null;
			String date = null;
			userBean.setFileName("IDOC REPORT");
			if(request.getParameter("startDate") !=null)
			startDate = (String) request.getParameter("startDate");
			reportFormBean.setStartDate(startDate);
			if(request.getParameter("endDate") !=null)
				endDate = (String) request.getParameter("endDate");
				reportFormBean.setEndDate(endDate);
				
				if(request.getParameter("date") !=null){
					date = (String) request.getParameter("date");
					reportFormBean.setDate(date);
					
				}
				
				try {
					List<IDOCReportDTO> masterList = new ArrayList<IDOCReportDTO>();
					
					masterList = service.getiDOCReport(reportFormBean);
					request.setAttribute("reportList", masterList);
					
					
			} catch (Exception e) {
				logger.error("Exception occurs in excelImport: " + e.getMessage());
				userBean.setMessage("FAILURE");
				userMstrService.insertBulkDataTransactionLogs(userBean);
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("content-Disposition","attachment;filename=DashboardReport.xls");
				userBean.setMessage("SUCCESS");
				userMstrService.insertBulkDataTransactionLogs(userBean);
				request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));
			return mapping.findForward("viewiDOCReportExcel");
	}
	
	
	
	
	
}
