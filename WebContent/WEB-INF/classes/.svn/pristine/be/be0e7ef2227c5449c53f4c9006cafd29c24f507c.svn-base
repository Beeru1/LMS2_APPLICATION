/*
 * Created on Apr 30, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services;

import java.util.ArrayList;
import java.util.List;

import com.ibm.km.dto.KmSearch;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmSearchService {

	/**
	 * Method to Search
	 * @param dto
	 * @return
	 * @throws LMSException
	 */
	ArrayList search(KmSearch dto)  throws LMSException;

	/**
	 * Method for Content Search
	 * @param dto
	 * @return
	 * @throws LMSException
	 */
	ArrayList contentSearch(KmSearch dto)  throws LMSException;
	
	/**
	 * Location search method to find network faults
	 * @param dto
	 * @return
	 * @throws LMSException
	 */
	List locationSearch(KmSearch dto)  throws LMSException; 

	/**
	 * Method to get Change
	 * @param Id
	 * @param cond
	 * @param circleId
	 * @return
	 * @throws LMSException
	 */
	ArrayList getChange(String Id, String cond, String circleId)throws LMSException;
	
	/* KM phase2 csr keywors serach */
    /**
     * Method for csr Search
     * @param dto
     * @return
     * @throws LMSException
     */	
	ArrayList csrSearch(KmSearch dto)  throws LMSException;

	ArrayList contentSearchCSR(KmSearch dto) throws LMSException;

	ArrayList contentSearchAdmin(KmSearch searchDto) throws LMSException;
		
}
