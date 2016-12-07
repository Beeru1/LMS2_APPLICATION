
package com.ibm.km.services;

import java.util.ArrayList;

import com.ibm.km.dto.FileReportDto;
import com.ibm.lms.exception.LMSException;

/**
 * @author Atishay
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public interface KmFileReportService  {
	
	/**
	 * Method  for approval
	 * @param circleId
	 * @param categoryId
	 * @param subCategoryId
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getApproverService(String circleId, String categoryId, String subCategoryId,String fromDate,String toDate)throws LMSException;
	
	/**
	 * Method to get Hit
	 * @param circleId
	 * @param categoryId
	 * @param subCategoryId
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getHitService(String circleId, String categoryId, String subCategoryId,String fromDate,String toDate)throws LMSException;
	
	/**
	 * Method to get Added File List
	 * @param circleList
	 * @param selectedDate
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getAddedFileList(ArrayList circleList,String selectedDate)throws LMSException;
	
	/**
	 * Method to get Deleted File List
	 * @param elementId
	 * @param selectedDate
	 * @return
	 * @throws LMSException
	 */
	public int getDeletedFileList(String elementId,String selectedDate)throws LMSException;
	
	/**
	 * Method to get File Circle List
	 * @param elementId
	 * @param selectedDate
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getFileCircleList(String elementId,String selectedDate)throws LMSException;
	
	/**
	 * Method to get File Approver List
	 * @param elementId
	 * @param selectedDate
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getFileApprovedList(String elementId,String selectedDate)throws LMSException;
	
	/**
	 * Method to get Added File Count
	 * @param elementId
	 * @param selectedDate
	 * @return
	 * @throws LMSException
	 */
	public int getAddedFileCount(String elementId,String selectedDate)throws LMSException;
	
	/**
	 * Method to get Total File Count
	 * @param elementId
	 * @return
	 * @throws LMSException
	 */
	public int getTotalFileCount(String elementId)throws LMSException;
	
	/**
	 * Method to get deleted File List
	 * @param circleList
	 * @param selectedDate
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getDeletedFileList(ArrayList circleList, String selectedDate)throws LMSException;
	
	/**
	 * Method to get ApprovedRejected File COunt
	 * @param elementId
	 * @return
	 * @throws LMSException
	 */
	public FileReportDto getApprovedRejectedFileCount(String elementId) throws LMSException;

}
