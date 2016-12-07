/*
 * Created on Apr 29, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services;

import java.util.ArrayList;

import com.ibm.km.dto.KmBriefingMstr;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmBriefingMstrService {

	/**
	 *Method  to create Briefings 
	 * @param dto
	 * @throws LMSException
	 */
	void createBriefing(KmBriefingMstr dto) throws LMSException;
	
	/**
	 * Method to view Briefings
	 * @param circleId
	 * @param date
	 * @return
	 * @throws LMSException
	 */
	ArrayList viewBriefing(String circleId, String cagtegoryId, String date) throws LMSException;
	
	/**
	 * Method to edit briefings
	 * @param elementIds
	 * @param fromDate
	 * @param endDate
	 * @param userId
	 * @return
	 * @throws KmException
	 */
    //public ArrayList editBriefings(String[] elementIds , String fromDate, String endDate, int userId)  throws KmException;
    
    /**
     * Method to UpdateBriefings 
     * @param briefingId
     * @return
     * @throws LMSException
     */
    KmBriefingMstr updateBriefings(int briefingId) throws LMSException;
    
	/**
	 * Method to Update Briefing in Db
	 * @param briefingId
	 * @param briefHeading
	 * @param arrBriefingDetails
	 * @param displayDt
	 * @throws LMSException
	 */
	void updateinDbBriefings(String briefingId,String briefHeading,
	String[] arrBriefingDetails,String displayDt) throws LMSException;
	
	/**
	 * Method to edit briefings
	 * @param circleId
	 * @param fromDate
	 * @param endDate
	 * @param userId
	 * @return
	 * @throws LMSException
	 */
    public ArrayList editBriefings(String circleId , String fromDate, String endDate, int userId)  throws LMSException;
}
