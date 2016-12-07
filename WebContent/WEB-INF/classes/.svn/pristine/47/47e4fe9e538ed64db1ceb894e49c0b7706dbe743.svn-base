/*
 * Created on Feb 14, 2008
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
public interface RejectedFileService {
	
	/**
	 * This method is used to connect to DAO and get the list of rejected file submitted by
	 * the logged-in user from DB.
	 * It takes userId of the logged-in user and date range as input.
	 * @param userId
	 * @param onDate
	 * @return
	 * @throws LMSException
	 */
	ArrayList getFileList(String userId,String fromDate,String toDate, String root,String actorId)throws LMSException;


	
			
}
