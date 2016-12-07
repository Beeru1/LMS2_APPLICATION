/*
 * Created on Apr 30, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dao;

import java.util.ArrayList;

import com.ibm.km.dto.KmSearch;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmSearchDao {

	/**
	 * @param dto
	 * @return
	 */
	ArrayList search(KmSearch dto) throws LMSException;

	/**
	 * @param Id
	 * @param condition
	 * @param circleId
	 * @return
	 */
	ArrayList getChange(String Id, String condition, String circleId) throws LMSException;

	/**
	 * @param dto
	 * @param documentIdString
	 * @return
	 */
	ArrayList contentSearch(KmSearch dto, String[] documentIds) throws LMSException;
	
	/* km phase2 csr keyword search */
	
	/**
	* @param dto
	* @return
	*/
	ArrayList csrSearch(KmSearch dto) throws LMSException;

}
