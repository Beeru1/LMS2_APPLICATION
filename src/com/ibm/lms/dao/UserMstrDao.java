/* 
 * KmUserMstrDao.java
 * Created: January 14, 2008
 * 
 * 
 */ 

package com.ibm.lms.dao;

import java.util.ArrayList;
import java.util.List;

import com.ibm.lms.dto.LOBDTO;
import com.ibm.lms.dto.UserDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.exception.UserMstrDaoException;


public interface UserMstrDao {

 /** 

	* @param dto  DTO Object holding the data to be inserted.
	* @return  The no. of rows inserted. 
	* @throws UserMstrDaoException In case of an error
 **/ 

	public UserMstr getUserDetails(String user_login_id) throws LMSException, DAOException;
	
    public  int insert(UserMstr dto) throws LMSException;

 /** 

	* The Primary Key Object determines wich row gets updated.
	* @param dto  DTO Object holding the data to be updated. The primary key vales determine which row will be updated
	* @return  The no. of rows updated. 
	* @throws UserMstrDaoException In case of an error
 **/ 

    public  int update(UserMstr dto) throws LMSException;
    
    
    public void insertBulkDataTransactionLogs(UserMstr dto)throws LMSException;
    /** 

	* insert user details and client ip  for bulk upload and download
 **/ 

    public boolean checkOTPusers(String actorId)throws LMSException;

	
	/**
	 * Checking for duplicate user Id 
	 * @param userLoginId
	 * @return
	 * @throws DAOException
	 */
	
	public boolean CheckUserId(String userLoginId) throws DAOException,LMSException;
	
	/**
	 * To update the password
	 * @param dto
	 * @return
	 * @throws DAOException
	 */
	public int updatePassword(UserMstr dto) throws LMSException;
	

	/**
	 * @return list of circle approvers
	 * @throws UserMstrDaoException
	 */
	public ArrayList viewCircleApprovers()throws LMSException;

	/**
	 * @param circleId
	 * @return list of circle users and CSRs
	 * @throws UserMstrDaoException
	 */
	public ArrayList viewCircleUsers(String circleId)throws LMSException;

	/**
	 * @param userId
	 * @return a HashMap object of the user 
	 */
	public UserDto selectUser(String userId, String loginActorId) throws LMSException;

	/**
	 * @param circleId
	 * @return circleList
	 */
	public ArrayList viewCsrs(String circleId)throws LMSException;

	/**
	 * @param loginActorId
	 * @param userId
	 * @return
	 */
	public ArrayList viewUsers(String loginActorId, String userId)throws LMSException;

	/**
	 * @param elementId(circleId)
	 * @return Element Users(circle users)
	 */
	public String[] getElementsUsers(String elementId)throws LMSException;

	/**
	 * @param userId
	 * @return
	 */
	public String getFavCategory(String userId) throws LMSException;

	/**
	 * @param elementId
	 * @return
	 */
	public int noOfElementUsers(String elementId)throws LMSException;

	/**
	 * @param elementId
	 * @return
	 */
	public boolean checkForFavourite(String elementId)throws LMSException;

	/**
	 * @param circleId
	 * @return
	 */
	public UserMstr getManagers(String circleId,int escalationCount)throws LMSException;

	/**
	 * @param circleId
	 * @return
	 */
	public String getApprover(String circleId)throws LMSException;

	/**
	 * @param userLoginId
	 */
	public int deleteUser(String userLoginId) throws LMSException;

	
	public int getWrongPwdCount(String userId) throws LMSException ;
		
	public int updateLoginAttempted(String userId) throws LMSException ;
	/**
	 * @param user
	 * @return
	 */
	public int insertUserData(UserMstr user) throws DAOException,LMSException;

	/**
	 * @param userMstrDto
	 */
	public void updateUserStatus(UserMstr userMstrDto)throws LMSException;
	
	public void updateForgotPasswordUser(UserMstr userMstrDto)throws LMSException;
	/** To get user id and password for forgot password 
	 * @param user
	 * @return
	 */
	
	public void updateSessionExpiry(UserMstr userMstrDto)throws LMSException;

	/**
	 * @param user
	 * @return
	 */
	public int bulkUpdate(UserMstr user) throws LMSException;

	/**
	 * @param userLoginId
	 * @return
	 */
	public String getUserStatus(String userLoginId)throws LMSException;

	/*KM phase II partner dao*/
		/**
		 * @param 
		 * @return dto
		 */
	public ArrayList getPartner() throws LMSException;
		
	/**
	 * @param user_login_id
	 * @return
	 * @throws LMSException
	 * @throws DAOException
	 */
	public UserMstr CheckUserLoginId(String user_login_id)throws LMSException,DAOException;

	public UserMstr userIdFromMobile(String mobileNumber) throws DAOException;
	
	public UserMstr userIdFromMobile(String mobileNumber,String actorIDs) throws DAOException;
	
	public boolean validateLastThreePasswords(String userLoginId,String password) throws LMSException,DAOException;
	
	//public void insertBulkUserData(ArrayList<UserMstr> userList) throws DAOException;
	public ArrayList insertBulkUserData(ArrayList<UserMstr> userList) throws DAOException;

	public boolean CheckUserIdBasedOnLobAndCircle(String userLoginId, String lobId, String actorId ) throws DAOException,LMSException;

	public boolean checkDuplicateUserIdLob(String userLoginId, int productLobId)throws DAOException;
	public boolean checkDuplicateUserIdCircle(String userLoginId,int productLobId, int circleId)throws DAOException;
	public boolean checkDuplicateUserIdMultiCircle(String userLoginId,int productLobId, String circleListStr)throws DAOException;
	public UserDto getUserDetailsForEdit(String userId ,String loginActorId ,int lobId) throws LMSException, DAOException  ;
	//public void updateBulkUserCreation(UserMstr bulkDto, ArrayList<UserMstr> listBulkDto)throws DAOException;
	public boolean checkActorForUserLogin(String userLoginId, String actorId) throws DAOException,LMSException;
	public List<LOBDTO> countUserLoginId(String userLoginId) throws DAOException,LMSException;
	public String getUserLoginId(String userId) throws LMSException;
	public void updateBulkUserCreation(UserMstr bulkDto)throws DAOException;

	public UserMstr getUserFnameDetails(String userFname)throws DAOException;
}

