/*
 * Created on Dec 5, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dao;
import java.util.ArrayList;

import com.ibm.lms.exception.LMSException;
/**
 * @author harpreet
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmUserReportDao {

	
	/**
	 * @param elementId
	 * @param toDate
	 * @param fromDate
	 * @param elementLevel
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getUserLoginList( String elementId,String elementLevel)throws LMSException;
	
	/**
	 * @param elementId
	 * @param kmActorId
	 * @return
	 * @throws LMSException
	 */
	
	public ArrayList getcircleWisereport(String elementId, String kmActorId, String date) throws LMSException;
/**
	* @param elementId
	* @param 
	* @return
	*/
	public ArrayList getAgentList( String elementId,String eleLevelId,String partner)throws LMSException;

public ArrayList getLockedUserList(String elementId)throws LMSException;
}