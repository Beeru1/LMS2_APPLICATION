package com.ibm.km.services.impl;


import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import com.ibm.km.dao.KmDashBoardDao;
import com.ibm.km.dao.impl.KmDashBoardDaoImpl;

import com.ibm.km.services.KmDashBoardService;

import com.ibm.lms.dto.DashBoardDTO;
import com.ibm.lms.exception.LMSException;


public class KmDashBoardServiceImpl implements KmDashBoardService {
	
	public int errorStatus;
	/**
	* Logger for the class.
	**/
	private static final Logger logger;

	static {

		logger = Logger.getLogger(KmExcelServiceImpl.class);
	}
	public ArrayList<DashBoardDTO> weeklyReportList() throws LMSException {
		KmDashBoardDao dashBoardDao = new KmDashBoardDaoImpl();
		return dashBoardDao.weeklyReportList();
	}
	public ArrayList<DashBoardDTO> dailyReportList() throws LMSException {
		KmDashBoardDao dashBoardDao = new KmDashBoardDaoImpl();
		return dashBoardDao.DailyReport();
	}
	public ArrayList<DashBoardDTO> hourlyReportList(String date) throws LMSException {
		KmDashBoardDao dashBoardDao = new KmDashBoardDaoImpl();
		return dashBoardDao.HourlyReport(date);
	}

	
}
