/*
 * Created on Apr 14, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dao;

import java.util.ArrayList;

import com.ibm.km.dto.KmAlertMstr;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmAlertMstrDao {

	/**
	 * @param dto
	 * insert a record for a Alert in the database table KM_SCROLL_MSTR
	 */
	public int insert(KmAlertMstr dto) throws LMSException;
	
	/**
	 * @param circleId
	 * @return
	 * @throws LMSException
	 */
	public String getAlertMessage(String circleId)throws LMSException;
	/**
	 * @param dto
	 * @return
	 */
	public ArrayList getAlertList(KmAlertMstr dto)throws LMSException;
	/**
	 * @param dto
	 * @return
	 */
	public String deleteAlert(KmAlertMstr dto)throws LMSException;
	
	/*KM phase II alerthistory dao*/
	/**
	* @param string parentId(elementId)
	* @returns an array list 
	**/
	public ArrayList getAlertHistoryReport(String parentId,String timeInterval) throws LMSException;

	/*KM phase II alerthistory dao*/
	/**
	* @param string parentId(elementId)
	* @returns an array list 
	**/
	public ArrayList getAlerts(String parentId,String time) throws LMSException;
	/* km phase II edit alert*/
	/**
	* @param dto
	* edit a record for a Alert in the database table KM_ALERT_MSTR
	*/
	public int editAlert(KmAlertMstr dto) throws LMSException;


}
