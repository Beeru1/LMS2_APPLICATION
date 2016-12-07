/*
 * Created on Oct 18, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.lms.dao;

import java.util.List;

import com.ibm.lms.dto.Login;
import com.ibm.lms.exception.DAOException;

/**
 * @author avanagar
 * @version 2.0
 * 
 */
public interface PopulateUserDao {
	/** 
	
		* Select user details from ARC_MODULE_MSTR,ARC_USER_MSTR,ARC_ACTOR_MSTR,ARC_CIRCLE_MSTR table.
		* @param dto  DTO Object holding the data.
		* @return  List 
		* @throws DAOException In case of an error
	 **/

	public List populateValues(Login dto) throws DAOException;

	/** 
	
		* Select category details from ARC_CATEGORY_MSTR table.
		* @return  HashMap 
		* @throws DAOException In case of an error
	**/
	public List populateValuesforUD(String loginID) throws DAOException;

	/** 
	
		* Select category details from ARC_CATEGORY_MSTR table.
		* @return  HashMap 
		* @throws DAOException In case of an error
	**/
	//public HashMap category() throws DAOException;
}
