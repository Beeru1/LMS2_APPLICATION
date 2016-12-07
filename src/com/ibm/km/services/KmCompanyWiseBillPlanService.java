
package com.ibm.km.services;

import java.util.ArrayList;

import com.ibm.lms.exception.LMSException;
/**
* @author Anveeksha & Neeraj 
* created for Waiver Matrix Upload
* for viewing Company Wise bill Plan details
*/
public interface KmCompanyWiseBillPlanService {
	/**
	 * @param str
	 * @return ArrayList for companies as per user entered search value
	 * @throws LMSException
	 */
	public ArrayList getCompanyList(String str) throws LMSException;
	
	/**
	 * @param id
	 * @return ArrayList for bill plans of selected company   
	 * @throws LMSException
	 */
	public ArrayList getBillPlanList(int id) throws LMSException;
	
	/**
	 * @param id
	 * @return ArrayList for details of Company selected 
	 * @throws LMSException
	 */
	public ArrayList getCompanyDetails(int id) throws LMSException;
	
	/**
	 * @param planName
	 * @return ArralList for Rate Details of selected Bill Plan
	 * @throws LMSException
	 */
	public ArrayList getBillPlanRateDetails(String planName) throws LMSException;
}
