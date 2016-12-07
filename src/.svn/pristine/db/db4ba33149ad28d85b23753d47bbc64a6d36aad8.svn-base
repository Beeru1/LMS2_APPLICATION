/*
 * Created on Feb 16, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package com.ibm.km.services;

import java.util.ArrayList;

import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public interface KmFileService  {
	

	
	/**
	 * Method to view File
	 * @param circleId
	 * @param categoryId
	 * @param subCategoryId
	 * @param userId
	 * @return
	 * @throws LMSException
	 */
	public ArrayList viewFileService(String circleId, String categoryId, String subCategoryId, String userId)throws LMSException;

    /**
     * Method to delete File 
     * @param documentId
     * @param updatedBy
     * @throws LMSException
     */
	public void deleteFileService(String documentId, String updatedBy)throws LMSException;


	/**
	 * Method for keyword file search
	 * @param keyword
	 * @param circleId
	 * @param uploadedBy
	 * @return
	 * @throws LMSException
	 */
	public ArrayList keywordFileSearch(String keyword, String circleId, String uploadedBy)throws LMSException;


}