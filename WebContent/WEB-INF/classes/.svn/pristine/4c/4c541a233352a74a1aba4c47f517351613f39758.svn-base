/*
 * Created on Apr 28, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services;

import java.util.ArrayList;

import com.ibm.km.dto.KmFeedbackMstr;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmFeedbackMstrService {

	/**
	 * Method to create FeedBack 
	 * @param dto
	 * @throws LMSException
	 */
	public void createFeedbackService(KmFeedbackMstr dto)throws LMSException;

	/**
	 * Method to View Feedback
	 * @param elementIds
	 * @param elementId
	 * @return
	 * @throws LMSException
	 */
	public ArrayList viewFeedbacks(String[] elementIds,String elementId) throws LMSException;

	/**
	 * Method to response Feedback 
	 * @param readFeedbacks
	 * @param feedbackResp
	 * @param feedbackId
	 * @throws LMSException
	 * @throws DAOException
	 */
	public void readFeedbacksService(String[] readFeedbacks,String[] feedbackResp,String[] feedbackId) throws LMSException, DAOException;

	/*Method for reading feedback Responses - added by Atul*/
	/**
	 * Method to read feed back responses
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public ArrayList feedbackResponse(int userId) throws DAOException;
	
	
	public ArrayList feedbackResponseAll(int elementId) throws DAOException;
	
	
	/**
	 * Method to genrate Feedback Report
	 * @param elementIds
	 * @param elementId
	 * @param sendDate 
	 * @return
	 * @throws LMSException
	 */
	public ArrayList feedbackReport(String[] elementIds,String elementId, String startDate, String endDate) throws LMSException;



}
