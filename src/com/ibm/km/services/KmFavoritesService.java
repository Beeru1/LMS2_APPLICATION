/*
 * Created on May 2, 2008
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
public interface KmFavoritesService {
	/**
	 * Method  to show favourite
	 * @param userId
	 * @param circleId
	 * @return
	 * @throws LMSException
	 */
	public List showFavoritesService(String userId,String circleId) throws LMSException;
}
