/*
 * Created on Dec 5, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services;

import java.util.ArrayList;

import com.ibm.lms.exception.LMSException;

/**
 * @author harpreet
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmUserReportService {
	
	/**
	 * Method to get user login report
	 * @param elementId
	 * @param toDate
	 * @param fromDate
	 * @param elementLevel
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getUserLoginReport( String elementId,String elementLevel)throws LMSException; 
	
	/**
	 * Method to get Circle Wise Report
	 * @param elementId
	 * @param kmActorId
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getcircleWisereport( String elementId, String kmActorId,String date) throws LMSException;
		
	/**
	 * method to get AgentWise report
	 * @param elementId
	 * @param eleLevelId
	 * @param partner
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getAgentIdWiseReport( String elementId, String eleLevelId, String partner)throws LMSException;

	public ArrayList getLockedUserReport(String elementId) throws LMSException;
	

}
