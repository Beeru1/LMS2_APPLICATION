/*
 * Created on Oct 16, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.lms.services;

import java.util.ArrayList;
import java.util.List;

import com.ibm.lms.dto.Login;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;

/**
 * @author namangup
 * @version 2.0
 * 
 */
public interface LoginService {

	/**
	 * 
	 * @param loginId
	 * @return
	 * @throws LMSException
	 * @throws DAOException
	 */
	
	public boolean isValidUser(String loginId) throws LMSException ;
	
	/**
	 * 
	 * @param olmId
	 * @return
	 * @throws LMSException
	 * @throws DAOException
	 */
	
	public boolean isValidOlmId(String olmId) throws LMSException ;
	
	
	/**
	 * Method to populate User Details
	 * @param login
	 * @return
	 * @throws LMSException
	 * @throws DAOException
	 */
	public UserMstr populateUserDetails(Login login) throws LMSException, DAOException;
	/**
	 * Method to populate User Details
	 * @param login
	 * @return
	 * @throws LMSException
	 * @throws DAOException
	 */
	public UserMstr populateUserDetailsforUD(String loginID) throws LMSException, DAOException;
	
	/**
	 * Method to get date
	 * @param d
	 * @return
	 * @throws LMSException
	 * @throws DAOException
	 */	
	public String getStringFromDate(java.util.Date d) throws LMSException, DAOException;
	
	/**
	 * Method to get Warning
	 * @param ob
	 * @return
	 * @throws LMSException
	 * @throws DAOException
	 */
	public String getWarning(Object ob) throws LMSException, DAOException;
	
	/**
	 * Method to getConfigValue
	 * @param string
	 * @return
	 * @throws LMSException
	 */
	public String getConfigValue(String string) throws LMSException;
   
   /**
    * method to get Email id
    * @param userName
    * @return
    * @throws LMSException
    */
	public ArrayList getEmailId(String userName)throws LMSException;
	
   /**
	 *method to update password 
	 * @param userName
	 * @param encPassword
	 * @throws LMSException
	 */
	public void updatePassword(String userName, String encPassword)throws LMSException;
	
	 public int getExpiredDocumentCount(UserMstr userBean) throws LMSException;
	 
	 public List getFavorites(int userId) throws LMSException;
	 
	 public String sendSMS(String userLoginId,String contact) throws LMSException;
}
