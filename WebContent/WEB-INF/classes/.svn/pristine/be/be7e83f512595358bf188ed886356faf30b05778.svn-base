/*
 * Created on Feb 15, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dao;

import java.util.ArrayList;
import java.util.List;

import com.ibm.km.dto.ApproveFileDto;
import com.ibm.lms.exception.LMSException;

/**
 * @author Pramod
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface ApproveFileDao {
	
	/**
	 * This method is used to interact with DB and get the list of files to approve
	 * in the date range selected by user.
	 * @param userId
	 * @param circleId
	 * @param onDate
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getFileList(String userId)throws LMSException;
	
	/**
	 * Method to approve files 
	 * @param fileIds
	 * @param fileList
	 * @param userId
	 * @throws LMSException
	 */
	public void approveFile(String[] fileIds,String[] fileList,String userId) throws LMSException;

	/**
	 * Method to deactivate Old Files 
	 * @param fileIds
	 * @throws LMSException
	 */
	public void deactivateOldFile(String[] fileIds) throws LMSException;
	
	/**
	 * Method to reject files
	 * @param fileIds
	 * @param fileList
	 * @param userId
	 * @throws LMSException
	 */
	public void rejectFile(String[] fileIds,String[] fileList,String userId) throws LMSException;

    /**
     * Method for Escalation
     * @param fileIds
     * @param escalationTime
     * @param escalationCount
     * @param approverId
     * @throws LMSException
     */	
	public void updateEscalationTime(String fileIds,String escalationTime,int escalationCount,String approverId) throws LMSException;
	
	/**
	 * Method to check approved files
	 * @return
	 * @throws LMSException
	 */
	public List checkApprovedFiles() throws LMSException;
	
	/**
	 * Method to get upload time
	 * @param documentId
	 * @return
	 * @throws LMSException
	 */
	public ApproveFileDto getUploadedTime(String documentId) throws LMSException;
	
}
