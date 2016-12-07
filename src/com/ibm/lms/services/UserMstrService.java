/*
 * Created on Feb 1, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.lms.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.ibm.lms.dto.CircleDTO;
import com.ibm.lms.dto.LOBDTO;
import com.ibm.lms.dto.UserDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public interface UserMstrService  {
	

	public UserMstr getUserDetails(String user_login_id) throws LMSException ;
	/**
	 * Method to create User
	 * @param userMstrDto
	 * @throws LMSException
	 */
	public int createUserService(UserMstr userMstrDto) throws LMSException;

	/**
	 * Method to edit User
	 * @param userMstrDto
	 * @return
	 * @throws LMSException
	 */	
	public int editUserService(UserMstr userMstrDto) throws LMSException;

	/**
	 * Method for duplicate Check
	 * @param userLogingId
	 * @return
	 * @throws DAOException
	 * @throws LMSException
	 */
	public boolean checkDuplicateUserLogin(String userLogingId) throws DAOException,LMSException;

	/**
	 * Methode to change password
	 * @param dto
	 * @return
	 * @throws LMSException
	 */
	
	public int getWrongPwdCount(String userId) throws LMSException ;
	
	public int updateLoginAttempted(String userId) throws LMSException ;
	
	public int changePasswordService(UserMstr dto) throws LMSException;
	public boolean checkOTPusers(String actorId)throws LMSException;

    /**
     * Method to view users
     * @param loginActorId
     * @param userId
     * @return
     * @throws LMSException
     */
	public ArrayList viewUsers(String loginActorId, String userId) throws LMSException;
	
	/**
	 * Method to view users
	 * @param userId
	 * @return
	 * @throws LMSException
	 */
	public UserDto getUserService(String userId,  String loginActorId) throws LMSException;

	/**
	 * Method to retrieve Elements
	 * @param elementId
	 * @return
	 * @throws LMSException
	 */
	public String[] getElementUsers(String elementId) throws LMSException;

	/**
	 * Method to get favourite category
	 * @param string
	 * @return
	 * @throws LMSException
	 */
	public String getFavCategory(String string) throws LMSException;

	/**
	 * Method to get no of users
	 * @param elementId
	 * @return
	 * @throws LMSException
	 */
	public int noOfElementUsers(String elementId)throws LMSException;

	/**
	 * Method for checking favourite
	 * @param elementId
	 * @return
	 * @throws LMSException
	 */
	public boolean checkForFavourite(String elementId)throws LMSException;
	
	/**
	 * Method to get managers
	 * @param circleId
	 * @param escalationCount
	 * @return
	 * @throws LMSException
	 */
	public UserMstr getManagers(String circleId,int escalationCount)throws LMSException;

	/**
	 * Method to get Approver
	 * @param circleId
	 * @return
	 * @throws LMSException
	 */
	public String getApprover(String circleId)throws LMSException;
	
	/**
	 * Method to delete user
	 * @param userId
	 * @return
	 * @throws LMSException
	 */
	public int deleteUserService(String userId) throws LMSException;

	/**
	 * Method to insert user
	 * @param user
	 * @return
	 * @throws com.ibm.lms.exception.DAOException
	 * @throws LMSException
	 */
	public int insertUserData(UserMstr user) throws com.ibm.lms.exception.DAOException,LMSException;

	/**
	 * Method to update user Status
	 * @param userBean
	 * @throws LMSException
	 */
	
	public void updateUserStatus(UserMstr userBean) throws LMSException;

	/**
	 * Method to get user Status
	 * @param userLoginId
	 * @return
	 * @throws LMSException
	 */
	public void updateForgotPasswordUser(UserMstr userBean) throws LMSException;
	/**
	 * Method to get user id and ip adress for forgot password
	 * @param userLoginId
	 * @return
	 * @throws LMSException
	 */
	public void insertBulkDataTransactionLogs(UserMstr userBean)throws LMSException;
	/**
	 * Method to insert user details and ip adress for Bulk downloads
	 * @param userLoginId
	 * @return
	 * @throws LMSException
	 */
	public String getUserStatus(String userLoginId) throws LMSException;
	
	/* KM Phase II partner list retreival service by harpreet*/
	/**
	 * method to get partner Name
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getPartnerName() throws LMSException;
		
	/**
	 * Method to check user login Id
	 * @param user_login_id
	 * @return
	 * @throws LMSException
	 * @throws DAOException
	 */
	public UserMstr checkUserLoginId(String user_login_id)throws LMSException,DAOException;

	public void updateSessionExpiry(UserMstr sessionUserBean) throws LMSException;

	public UserMstr userIdFromMobile(String mobileNumber) throws LMSException;
	
	public UserMstr userIdFromMobile(String mobileNumber,String actorIDs) throws LMSException;
	
	/*
	 * Method added by Karan
	 * */
	public boolean validateLastThreePasswords(String userLoginId,String password) throws LMSException;
	
	public ArrayList getCircles() throws LMSException;
	
	//public void insertBulkUserData(ArrayList<UserMstr> userList) throws LMSException;
	public ArrayList insertBulkUserData(ArrayList<UserMstr> userList) throws LMSException;
	
	public boolean checkDuplicateUserIdLob(String userLoginId, int productLobId)throws LMSException;
	public boolean checkDuplicateUserIdCircle(String userLoginId,int productLobId, int circleId)throws LMSException;
	public boolean checkDuplicateUserIdMultiCircle(String userLoginId,int productLobId, String circleListStr)throws LMSException;
	
	public JSONObject getElementsAsJson(String productLobId) throws Exception;
	
	public JSONObject getElementsAsJsonZone(String selectedTypeId, String circleMstrId) throws Exception;
	
	public boolean checkDuplicateLOBCircleUserLogin(String userLoginId, String lobId, String actorId) throws DAOException,LMSException ;
	public JSONObject getElementsAsJsonNew(String productLobId, String userLoginId)throws Exception;
	public ArrayList getAllChildrenNew(String productLobId,String userLoginId) throws Exception ;
	public ArrayList getAllChildren(String productLobId) throws Exception ;
	public ArrayList getAllChildrenZone(String selectedTypeId, String circleMstrId) throws Exception ;
	public UserDto getUserDetailsForEdit(String userId ,String loginActorId ,int lobId) throws LMSException  ;
	//public void updateBulkUserCreation(UserMstr  bulkDto, ArrayList<UserMstr> listBulkDto)throws LMSException;
	public boolean checkActorForUserLogin(String userLoginId, String actorId) throws DAOException, LMSException;
	public List<LOBDTO> countUserLoginId(String userLoginId) throws LMSException, DAOException;
	public String getCircleId (String productLobId , String circleName) throws Exception;
	public String getUserLoginId(String userId) throws LMSException;
	public JSONObject getElementsAsJsonNewUser(String productLobId, String userLoginId)throws Exception;
	public void updateBulkUserCreation(UserMstr  bulkDto)throws LMSException;
	public ArrayList<UserMstr> getAllChannelPartner(String productLobId,String circleId) throws Exception;
	public JSONObject getElementsAsJsonMTD(String productLobId, String circleId , String selectedTypeId) throws Exception ;
	public JSONObject getElementsAsJsonRequest(String productId,String productLobId) throws Exception ;
	public JSONObject getElementsAsJsonRequestLob(String productLobId)throws Exception ;
	public JSONObject getRsuForCircleChange(String circleMstriD)throws Exception ;
	public UserMstr getUserFnameDetails(String userFname)throws Exception ;
	public ArrayList<CircleDTO> getAllChildrenNewUser(String productLobId,String userLoginId) throws Exception;
	
}
