/*
 * Created on May 6, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services;

import java.util.List;

import com.ibm.lms.exception.LMSException;

/**
 * @author varunagg
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmDocumentViewsService {
	
	/**
	 * Method to show document views
	 * @param elementId
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws LMSException
	 */
	public List showDocumentViews(String elementId,String fromDate,String toDate) throws LMSException;

}
