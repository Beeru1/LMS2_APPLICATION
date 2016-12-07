package com.ibm.km.services;

import java.util.ArrayList;

import com.ibm.lms.exception.LMSException;
/**
* @author Anveeksha & Neeraj 
* created for Waiver Matrix Upload
* for viewing Company Wise bill Plan details
*/


public interface KmExcelService {
	/**
	 * @param filepath
	 * @return ArrayList of Bill Pans with no rate details
	 * @throws LMSException
	 */
	public ArrayList createTempTable(String filepath) throws LMSException;
	public int getErrorStatus() throws LMSException;
	/**
	 * @param userID,filePath
	 * @throws LMSException
	 */
	public void updateMasterTables(String userID,String filePath) throws LMSException;
}
