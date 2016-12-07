/*
 * Created on Apr 14, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services;

import java.util.ArrayList;

import com.ibm.km.dto.KmAlertMstr;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmAlertMstrService {

	/**
	 * Method to create Alerts
	 * @param dto
	 * @return
	 * @throws LMSException
	 */
	public int createAlert(KmAlertMstr dto) throws LMSException;
	
	/**
	 * Method to retrieve Alert Message
	 * @param circleId
	 * @return
	 * @throws LMSException
	 */
	public String getAlertMessage(String circleId)throws LMSException;

	/**
	 * method to retrieve Alert List
	 * @param dto
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getAlertList(KmAlertMstr dto)throws LMSException;

	/**
	 * Method to Delete Alert
	 * @param dto
	 * @return
	 * @throws LMSException
	 */
	public String deleteAlert(KmAlertMstr dto)throws LMSException;
	
	/*km pase II view alert by harpreet*/
	/**
     * Method  to retrieve Alerts
     * @param circleId
     * @param time
     * @return
     * @throws LMSException
     */
	public ArrayList getAlerts(String circleId,String time) throws LMSException;
	
	/* km phase II edit alert by harpreet*/
	/**
	 * Method to Edit the Alerts
	 * @param dto
	 * @return
	 * @throws LMSException
	 */
	public int editAlert(KmAlertMstr dto) throws LMSException;
		
	/*km pase II alert history by harpreet*/
	/**
	 * Method to retrieve Alerts History report 
	 * @param circleId,alert count
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getAlertHistoryReport(String circleId,String timeInterval) throws LMSException;
	public String getAlertMessage(KmAlertMstr dto) throws LMSException;

}
