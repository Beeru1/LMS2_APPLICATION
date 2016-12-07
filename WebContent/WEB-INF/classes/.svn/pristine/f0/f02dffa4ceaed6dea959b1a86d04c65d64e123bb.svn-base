package com.ibm.km.dao;

import java.util.ArrayList;

import com.ibm.lms.exception.LMSException;
/**
 * @author Anveeksha & Neeraj
 * for waiver matrix uplaod 
 * save the data of uploaded excel to database
 */
public interface KmCompanyWiseBillPlanDao {
	
	/**
	 * @param str
	 * @return ArrayList for list of companies 
	 * @throws LMSException
	 */
	public ArrayList getCompanyList(String str) throws LMSException;
	
	/**
	 * @param id
	 * @return ArrayList for Bill Plans of selected company
	 * @throws LMSException
	 */
	public ArrayList getBillPlanList(int id) throws LMSException;
	
	/**
	 * @param id
	 * @return Arraylist for details of company selected
	 * @throws LMSException
	 */
	public ArrayList getCompanyDetails(int id) throws LMSException;
	
	/**
	 * @param plan_name
	 * @return ArrayList for Rate Details fo selected bill plan
	 * @throws LMSException
	 */
	public ArrayList getBillPlanRateDetail(String planName ) throws LMSException;

}
