/*
 * Created on jan 15, 2014
 */
package com.ibm.lms.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ibm.lms.dto.DashBoardReportDTO;
import com.ibm.lms.dto.IDOCReportDTO;
import com.ibm.lms.dto.LifeCycleReportDTO;
import com.ibm.lms.dto.MTDReportDTO;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.ReportsFormBean;

/**
 * @author Amarjeet
 */
public interface ReportDao {
	
	 public ArrayList<MTDReportDTO> getLeadMTDReport(ReportsFormBean formBean ) throws DAOException;
	
	 public ArrayList<MTDReportDTO> getLeadMTDReportDayMonthwise(ReportsFormBean formBean,HttpServletRequest request)  throws DAOException;
	 public ArrayList<MTDReportDTO> getLeadMTDReportDayMonthwiseGridPopulate(ReportsFormBean formBean,HttpServletRequest request ) throws DAOException;
	 public ArrayList<MTDReportDTO> getFTDMTDReportCountDetails(ReportsFormBean formBean) throws DAOException;
	 
	 public ArrayList<LifeCycleReportDTO> getLeadLifecycleReport(ReportsFormBean formBean ) throws DAOException;
	 public ArrayList<DashBoardReportDTO> getDashboardReport(ReportsFormBean formBean ) throws DAOException ;
	 public ArrayList<IDOCReportDTO> getiDOCReport(ReportsFormBean formBean)throws DAOException;
}
