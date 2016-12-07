/*
 * Created on Feb 13, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services;

import java.util.ArrayList;

import com.ibm.lms.exception.LMSException;

/**
 * @author Vipin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface SubmitFileService {
	
	/**
	 * Method to interact with DAO and get the list of file to submit for approval.
	 * @param userId
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws LMSException
	 */
	ArrayList getFileList(String userId,String fromDate,String toDate)throws LMSException;
	
	/**
	 * Method to submit files.
	 * @param fileIds
	 * @throws LMSException
	 */
	void submitFile(String[] fileIds)throws LMSException;
}
