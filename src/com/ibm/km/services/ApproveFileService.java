/*
 * Created on Feb 14, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services;

import java.util.ArrayList;
import java.util.List;

import com.ibm.lms.exception.LMSException;

/**
 * @author Vipin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface ApproveFileService {
	
	/**
	 * Method to interact with DAO and get the list of file to approve.
	 * @param circleId
	 * @param userId
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws LMSException
	 */
	ArrayList getFileList(String userId)throws LMSException;
	
	/**
	 * Method to approve files.
	 * @param fileIds
	 * @param commentList
	 * @param userId
	 * @throws LMSException
	 */
	void approveFiles(String[] fileIds,String[] commentList,String userId)throws LMSException;
	
	/**
	 * Method to reject files.
	 * @param fileIds
	 * @param commentList
	 * @param userId
	 * @throws LMSException
	 */
	void rejectFiles(String[] fileIds,String[] commentList,String userId)throws LMSException;

	
	/**
	 * Method to approve files.
	 * @param documentId
	 * @throws LMSException
	 */
	void updateEscalationTime(String documentId,String userId)throws LMSException;
	
	/**
	 * Metod to check approve files.
	 * @return
	 * @throws LMSException
	 */
	public List checkApprovedFiles() throws LMSException;
	
	
	
}
