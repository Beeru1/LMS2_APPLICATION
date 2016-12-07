/*
 * Created on Feb 20, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dao;

import java.util.ArrayList;

import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmFileDao {


	/**
	 * @param fileId
	 */
	public void deleteDocument(String documentId, String updatedBy) throws LMSException;


	/**
	 * @param circleId
	 * @param categoryId
	 * @param subCategoryId
	 * @param userId
	 * @return
	 */
	public ArrayList viewFiles(String circleId, String categoryId, String subCategoryId, String userId)throws LMSException;

	/**
	 * @param keyword
	 * @param circleId
	 * @param uploadedBy
	 * @return file list
	 */
	public ArrayList keywordFileSearch(String keyword, String circleId, String uploadedBy)throws LMSException;
	

}
