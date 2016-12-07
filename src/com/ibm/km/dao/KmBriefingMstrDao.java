/*
 * Created on Apr 29, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dao;

import java.util.ArrayList;

import com.ibm.km.dto.KmBriefingMstr;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmBriefingMstrDao {

	/**
	 * @param dto
	 */
	void insert(KmBriefingMstr dto) throws LMSException;
	
	/**
	 * 
	 * @param circleId
	 * @param date
	 * @return
	 * @throws LMSException
	 */
	
	ArrayList view(String circleId, String catgeoryId, String date) throws LMSException;

	/**
	 * 
	 * @param elementIds
	 * @param fromDate
	 * @param endDate
	 * @param userId
	 * @return
	 * @throws KmException
	 */
	//ArrayList editBriefings(String []  elementIds, String fromDate, String endDate, int userId)  throws KmException;
	
	/**
	 * 
	 * @param briefingId
	 * @return
	 * @throws LMSException
	 */
	KmBriefingMstr updateBriefings(int briefingId)  throws LMSException;
	
	/**
	 * 
	 * @param briefingId
	 * @param briefHeading
	 * @param arrBriefingDetails
	 * @param displayDt
	 * @throws LMSException
	 */
	void updateinDbBriefings(String briefingId,String briefHeading,
		String[] arrBriefingDetails,String displayDt)  throws LMSException;

	ArrayList editBriefings(String circleId, String fromDate, String endDate, int userId) throws LMSException;
}
