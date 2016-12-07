/*
 * Created on Nov 28, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services;

import java.util.ArrayList;

import com.ibm.lms.exception.LMSException;

/**
 * @author Atul
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmHitCountReportService {
	
	/**
	 * Method to get Hit Count Report
	 * @param elementId
	 * @param date 
	 * @return
	 * @throws LMSException
	 */

	public ArrayList hitCountReport(String elementId, String date) throws LMSException;
}
