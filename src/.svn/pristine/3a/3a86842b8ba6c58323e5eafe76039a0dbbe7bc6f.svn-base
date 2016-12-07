/*
 * Created on Apr 14, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dao;

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
public interface KmScrollerMstrDao {

	/**
	 * @param dto
	 * insert a record for a scroller in the database table KM_SCROLL_MSTR
	 */
	public int insert(KmScrollerMstr dto) throws LMSException;
	
	/**
	 * @param circleId
	 * @return
	 * @throws LMSException
	 */
	public String getScrollerMessage(String circleId)throws LMSException;
	/**
	 * @param dto
	 * @return
	 */
	public ArrayList getScrollerList(KmScrollerMstr dto)throws LMSException;
	/**
	 * @param dto
	 * @return
	 */
	public String deleteScroller(KmScrollerMstr dto)throws LMSException;
	
	/**
	 * @param dto
	 * @return
	 * @throws LMSException
	 */
	public int editScroller(KmAlertMstr dto)throws LMSException;
	
	public int createAllLobScroller(KmScrollerMstr dto) throws LMSException;
	
	public String getBulkScrollerMessage(List<Integer> elementIds)throws LMSException;
	
	

}
