/*
 * Created on jan 15, 2014
 */
package com.ibm.lms.services;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ibm.lms.dto.DashBoardReportDTO;
import com.ibm.lms.dto.IDOCReportDTO;
import com.ibm.lms.dto.LifeCycleReportDTO;
import com.ibm.lms.dto.MTDReportDTO;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.ReportsFormBean;

/**
 * @author Amarjeet
 */
public interface ReportService {
	
	 public ArrayList<MTDReportDTO> getLeadMTDReport(ReportsFormBean formBean) throws LMSException; 
	 
	 public ArrayList<LifeCycleReportDTO> getLeadLifecycleReport(ReportsFormBean formBean) throws LMSException;  //added by sudhanshu

	 public ArrayList<DashBoardReportDTO> getDashboardReport(ReportsFormBean formBean ) throws LMSException;
	 public ArrayList<IDOCReportDTO> getiDOCReport(ReportsFormBean formBean)
		throws LMSException;
	 public ArrayList<MTDReportDTO> getLeadMTDReportDayMonthwise(ReportsFormBean formBean,HttpServletRequest request) throws LMSException;
	 
	 public ArrayList<MTDReportDTO> getLeadMTDReportDayMonthwiseGridPopulate(ReportsFormBean formBean,HttpServletRequest request ) throws LMSException;
	 
	 public ArrayList<MTDReportDTO> getFTDMTDReportCountDetails(ReportsFormBean formBean) throws LMSException;
}
