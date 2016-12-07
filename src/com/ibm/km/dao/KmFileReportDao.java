/*
 * Created on Apr 4, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dao;

import java.util.ArrayList;

import com.ibm.km.dto.FileReportDto;
import com.ibm.lms.exception.LMSException;

/**
 * @author Atishay
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmFileReportDao{
	
	/**
	 * @param circleId
	 * @param categoryId
	 * @param subCategoryId
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getApproverList(String circleId, String categoryId, String subCategoryId,String fromDate,String toDate)throws LMSException;
	
	/**
	 * @param circleId
	 * @param categoryId
	 * @param subCategoryId
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getHitList(String circleId, String categoryId, String subCategoryId,String fromDate,String toDate)throws LMSException;
	
	/**
	 * @param elementId
	 * @param selectedDate
	 * @return
	 * @throws LMSException
	 */
	public String getAddedFileCount(String elementId,String selectedDate) throws LMSException;
	
	/**
	 * @param elementId
	 * @param selectedDate
	 * @return
	 * @throws LMSException
	 */
	public int getDeletedFileList(String elementId,String selectedDate) throws LMSException;
	
	/**
	 * @param elementId
	 * @param selectedDate
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getFileCircleList(String elementId,String selectedDate) throws LMSException;
	
	/**
	 * @param elementId
	 * @param selectedDate
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getFileApprovedList(String elementId,String selectedDate) throws LMSException;
	
//	public int getAddedFileCount(String elementId,String selectedDate) throws KmException;
	public int getTotalFileCount(String elementId) throws LMSException;
	/**
	 * @param string
	 * @param selectedDate
	 * @return
	 */
	public String getDeletedFileCount(String string, String selectedDate) throws LMSException;
	
	/**
	 * @param elementId
	 * @return
	 */
	public FileReportDto getFileCount(String elementId)throws LMSException;
	
}
