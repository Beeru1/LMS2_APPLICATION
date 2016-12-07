/*
 * Created on Apr 14, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services;

import java.util.ArrayList;
import java.util.List;

import com.ibm.km.dto.KmAlertMstr;
import com.ibm.km.dto.KmScrollerMstr;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmScrollerMstrService {

	/**
	 * Method to create Scroller
	 * @param dto
	 * @return
	 * @throws LMSException
	 */
	public int createScroller(KmScrollerMstr dto) throws LMSException;
	
	/**
	 * Method to get Scroller Message
	 * @param circleId
	 * @return
	 * @throws LMSException
	 */
	public String getScrollerMessage(String circleId)throws LMSException;

	/**
	 * Method to get Scroller List
	 * @param dto
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getScrollerList(KmScrollerMstr dto)throws LMSException;

	/**
	 * method to delete Scroller
	 * @param dto
	 * @return
	 * @throws LMSException
	 */
	public String deleteScroller(KmScrollerMstr dto)throws LMSException;
	
	/**
	 * Method to edit Scroller
	 * @param dto
	 * @return
	 * @throws LMSException
	 */
	public int editScroller(KmAlertMstr dto) throws LMSException;
	
	public int createAllLobScroller(KmScrollerMstr dto) throws LMSException;
	
	public String getBulkScrollerMessage(List<Integer> elementIds)throws LMSException;

}
