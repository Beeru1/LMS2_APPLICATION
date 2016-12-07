/*
 * Created on Jun 11, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.lms.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;

/**
 * @author Atul
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface LoginDao {
	
	/**
	 * 
	 * @param userLoginId
	 * @return
	 * @throws LMSException
	 */
	public boolean isValidUser(String userLoginId) throws LMSException ;
	
	/**
	 * 
	 * @param userLoginId
	 * @return
	 * @throws LMSException
	 */
	public boolean isValidOlmId(String userLoginId) throws LMSException ;
	
	/**
	 * @param userLoginId
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getEmailId(String userLoginId) throws LMSException;
	
	/**
	 * @param userLoginId
	 * @param password
	 * @throws LMSException
	 */
	public void  updatePassword(String userLoginId, String password) throws LMSException;
  /**
   * @param string
   * @return
   */
  public String getConfigValue(String string) throws LMSException;
  
  public int getExpiredDocumentCount(UserMstr userBean) throws LMSException;
  // get favorites of logged in user 
  
  public List getFavorites(int userId) throws LMSException ;
  
  public Timestamp getLastLogin(String userLoginId) throws LMSException;
  
  /* Added By Parnika for LMS Phase 2 */
  
  public ArrayList getUserCircleList(String userLoginId) throws LMSException;
  
  public ArrayList getUserLobList(String userLoginId) throws LMSException;
  
  /* End of changes by parnika */
  public String smsUserList(String userLoginId,String contact) throws LMSException ;
}
