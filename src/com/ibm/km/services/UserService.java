/*
 * Created on Oct 23, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services;

import java.util.ArrayList;
import java.util.List;

import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;

/**
 * @author avanagar
 * @version 2.0
 * 
 */
public interface UserService {
	/** 
	
		* Returns different userType
		* @return List
	 **/

	public List getUserTypeService(String actorId) throws LMSException;

	/**
	 * To insert user details into Database
	 * @param userMstrDto
	 * @param sessionUserBean
	 */

	public void createUserService(UserMstr userMstrDto) throws LMSException;

	/**
		 * To edit user details into Database
		 * @param userMstrDto
		 * @param sessionUserBean
		 */

	public int editUserService(UserMstr userMstrDto) throws LMSException;

	/**
	 * To view the user details
	 * @param actorId
	 * @return List
	 * @throws LMSException
	 */

	public List viewUserService(String actorId,String userId) throws LMSException;

	/**
	 * 
	 * @param userId
	 * @param userList
	 * @return
	 * @throws LMSException
	 */

	public UserMstr getEditUserDetails(String userId, ArrayList userList) throws LMSException;

	/**
	 * 
	 * @param userLogingId
	 * @return boolean
	 * @throws LMSException
	 */

	public boolean checkDuplicateUserLogin(String userLogingId) throws LMSException;

	/**
	 * Change password service
	 * @param dto
	 * @throws LMSException
	 */
	public int changePasswordService(UserMstr dto) throws LMSException;

	/**
			 * Returns email id for userLogin Id
			 * @param userLogingId
			 * @return boolean
			 * @throws LMSException
			 */

	public UserMstr userLoginEmail(String userLogingId) throws LMSException;

	/**
				 * Returns email id for userLogin Id
				 * @param userLogingId
				 * @return boolean
				 * @throws LMSException
				 */

	public String pwdMailingService(String userLogingId, String userEmail) throws LMSException;
}
