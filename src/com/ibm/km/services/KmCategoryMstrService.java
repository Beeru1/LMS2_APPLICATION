/*
 * Created on Jan 26, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.km.dto.KmCategoryMstr;
import com.ibm.lms.exception.LMSException;


/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */


public interface KmCategoryMstrService {
	
	/**
	 * Method to create Category Service
	 * @param circleMstrDto
	 * @throws LMSException
	 */
	public void createCategoryService(KmCategoryMstr circleMstrDto) throws LMSException;


	/**
	 * Method to check Category name service 
	 * @param categoryName
	 * @param circleId
	 * @return true or false
	 */
	public boolean checkOnCategoryNameService(String categoryName,String circleId)throws LMSException;

	/**
	 * Method to retrieve Category Service
	 * @param circleId
	 * @return
	 * @throws LMSException
	 */
	public ArrayList retrieveCategoryService(String[] circleId)throws LMSException;
	
	/**
	 * Method to retrieve Category Map
	 * @param circleId
	 * @param favCategoryId
	 * @return
	 * @throws LMSException
	 */
	public HashMap retrieveCategoryMap(String circleId,String favCategoryId)throws LMSException;

    /**
     * Method to retrieve No of Categories
     * @param string
     * @return
     * @throws LMSException
     */
	public int getNoOfCategories(String string) throws LMSException;

}
