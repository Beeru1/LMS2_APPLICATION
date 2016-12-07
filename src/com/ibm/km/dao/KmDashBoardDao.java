/* 
 * KmRuleMstrDao.java
 * Created: January 14, 2008
 * 
 * 
 */ 

package com.ibm.km.dao;

import java.util.ArrayList;

import com.ibm.lms.dto.DashBoardDTO;
import com.ibm.lms.exception.LMSException;

public interface KmDashBoardDao {


    public  ArrayList<DashBoardDTO> weeklyReportList() throws LMSException;
    public  ArrayList<DashBoardDTO> DailyReport() throws LMSException;
    public  ArrayList<DashBoardDTO> HourlyReport(String date) throws LMSException;
}
