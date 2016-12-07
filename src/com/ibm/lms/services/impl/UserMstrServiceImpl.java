package com.ibm.lms.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONObject;
import com.ibm.lms.dao.ProductMappingDao;
import com.ibm.lms.dao.UserMstrDao;
import com.ibm.lms.dao.MasterDao;
import com.ibm.lms.dao.impl.ProductMappingDaoImpl;
import com.ibm.lms.dao.impl.UserMstrDaoImpl;
import com.ibm.lms.dao.impl.MasterDaoImpl;
import com.ibm.lms.dto.CircleDTO;
import com.ibm.lms.dto.LOBDTO;
import com.ibm.lms.dto.ProductDTO;
import com.ibm.lms.dto.RSUDTO;
import com.ibm.lms.dto.RequestCategoryDTO;
import com.ibm.lms.dto.UserDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.dto.ZoneDTO;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.UserMstrService;


public class UserMstrServiceImpl implements UserMstrService {

	public static Logger logger = Logger.getRootLogger();

	public boolean checkDuplicateUserLogin(String userLogingId) throws DAOException,LMSException {
		UserMstrDao userMstrDao = null;
		boolean exist = false;
		try {
			userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
			if (userMstrDao.CheckUserId(userLogingId)) {
				exist = true;
			} else {
				exist = false;
			}
		}
		catch (DAOException e) {
 
			   logger.error(
				   "SQL Exception occured while CheckUserId."
					   + "Exception Message: "
					   + e.getMessage());
			   throw new DAOException("SQLException: " + e.getMessage(), e); 
		}
		catch (Exception e) {
			e.printStackTrace();
			
				logger.error("DAOException occured in checkDuplicateUserLogin():" + e.getMessage());
				throw new LMSException(e.getMessage());
			
		}
		return exist;

	}

	
	public void insertBulkDataTransactionLogs(UserMstr userMstrDto)throws LMSException{
		UserMstrDao userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		try{
			userMstrDao.insertBulkDataTransactionLogs(userMstrDto);
		}
		catch (LMSException e) {
			throw new LMSException(e.getMessage());
		}
		
		
	}
	public int createUserService(UserMstr userMstrDto) throws LMSException {

			int insertCount = 0;
			UserMstrDao userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikan new UserMstrDaoImpl();
			try{
				insertCount = userMstrDao.insert(userMstrDto);
			}
			catch (LMSException e) {
				throw new LMSException(e.getMessage());
			}
			return insertCount;
	}


	public int editUserService(UserMstr userMstrDto) throws LMSException {
		int insertCount = 0;
		
			UserMstrDao userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();

			/*Updating into the table USER_MSTR*/
			insertCount = userMstrDao.update(userMstrDto);
			return insertCount;
	}




	public int changePasswordService(UserMstr dto) throws LMSException {
		UserMstrDao userDataDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		int updateCnt = 0;
		
			//Update password dao called
			updateCnt = userDataDao.updatePassword(dto);
			return updateCnt;
	}



	public boolean checkOTPusers(String actorId)throws LMSException
	{
		UserMstrDao userDao = UserMstrDaoImpl.userMstrDaoInstance();
		boolean existOTPUser=userDao.checkOTPusers(actorId);
		
		return existOTPUser;
	}
	
	public UserDto getUserService(String userId, String loginActorId) throws LMSException{
		UserMstrDao userDAO = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		UserDto userDto=new UserDto();
		userDto=userDAO.selectUser(userId, loginActorId);
		return userDto;	
	}


	public ArrayList viewUsers(String loginActorId, String userId) throws LMSException {
		UserMstrDao userDAO = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		ArrayList userList=new ArrayList();
		userList=userDAO.viewUsers(loginActorId, userId);

		return userList;
	}



	public String[] getElementUsers(String elementId) throws LMSException {
		UserMstrDao dao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		return dao.getElementsUsers(elementId);
	}



	public String getFavCategory(String userId) throws LMSException {
		UserMstrDao dao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		return dao.getFavCategory(userId);
	}
	
	public int getWrongPwdCount(String userId) throws LMSException {
		UserMstrDao dao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		return dao.getWrongPwdCount(userId);
	}

	public int updateLoginAttempted(String userId) throws LMSException {
			UserMstrDao dao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
			return dao.updateLoginAttempted(userId);
		}
	
	
	public int noOfElementUsers(String elementId) throws LMSException {
		UserMstrDao dao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		return dao.noOfElementUsers(elementId);
	}


	public boolean checkForFavourite(String elementId) throws LMSException {
		UserMstrDao dao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		return dao.checkForFavourite(elementId);
	}


	public UserMstr getManagers(String circleId,int escalationCount) throws LMSException {
		UserMstrDao dao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		return dao.getManagers(circleId,escalationCount);
	}


	public String getApprover(String circleId) throws LMSException {
		UserMstrDao dao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		return dao.getApprover(circleId);
	}
	
	public int deleteUserService(String userLoginId)throws LMSException{
		
		UserMstrDao dao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		return dao.deleteUser(userLoginId);
	}


	
	public void deleteUser(String userLoginId) throws LMSException {
		// TODO Auto-generated method stub
		
	}


	public int insertUserData(UserMstr user) throws LMSException, DAOException {
		UserMstrDao dao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		return dao.insertUserData(user);
	}

	
	public void updateUserStatus(UserMstr userMstrDto) throws LMSException {
		UserMstrDao userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();

			/*Updating into the table USER_MSTR*/
			userMstrDao.updateUserStatus(userMstrDto);
		
	}
	
	public void updateForgotPasswordUser(UserMstr userMstrDto) throws LMSException {
		UserMstrDao userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();

			/*Updating into the table USER_MSTR*/
			userMstrDao.updateForgotPasswordUser(userMstrDto);
		
	}
	
	
	public void updateSessionExpiry(UserMstr userMstrDto) throws LMSException {
		UserMstrDao userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();

			/*Updating into the table USER_MSTR*/
			userMstrDao.updateSessionExpiry(userMstrDto);
		
	}


	
	public String getUserStatus(String userLoginId) throws LMSException {
		UserMstrDao dao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		return dao.getUserStatus(userLoginId);
	}
	/* KM Phase II partner list retreival service*/
	public ArrayList getPartnerName() throws LMSException{
			ArrayList partnerList=new ArrayList();
			UserMstrDao dao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
			partnerList=dao.getPartner();
			return partnerList;

		}
	public UserMstr checkUserLoginId(String user_login_id) throws LMSException,DAOException {
		UserMstrDao userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		return userMstrDao.CheckUserLoginId(user_login_id);
	
		
		
	}

	public UserMstr getUserDetails(String user_login_id) throws LMSException {
		UserMstrDao userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		try{
			return userMstrDao.getUserDetails(user_login_id);
		}
		catch (DAOException e) {
			throw new LMSException(e.getMessage());
		}
	}
	
	
	public UserMstr userIdFromMobile(String mobileNumber) throws LMSException {
		UserMstrDao userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		try{
			return userMstrDao.userIdFromMobile(mobileNumber);
		}
		catch (DAOException e) {
			throw new LMSException(e.getMessage());
		}
	}
	public UserMstr userIdFromMobile(String mobileNumber,String actorIDs) throws LMSException{
	UserMstrDao userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
	try{
		return userMstrDao.userIdFromMobile(mobileNumber,actorIDs);
	}
	catch (DAOException e) {
		throw new LMSException(e.getMessage());
	}
  }
	public boolean validateLastThreePasswords(String userLoginId, String password) throws LMSException {
		UserMstrDao userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		try{
			return userMstrDao.validateLastThreePasswords(userLoginId,password);
		}
		catch (DAOException e) {
			throw new LMSException(e.getMessage());
		}
	}
	
	public ArrayList getCircles() throws LMSException
	{
		MasterDao mstrDao = MasterDaoImpl.masterDaoInstance(); //changed by srikant new MasterDaoImpl();
		ArrayList arr = null;
		try{
			arr = mstrDao.getCircleList();
		}
		catch (LMSException e) {
			throw new LMSException(e.getMessage());
		} catch (DAOException e) {
			throw new LMSException(e.getMessage());
		}
		return arr;
	}


	public ArrayList insertBulkUserData(ArrayList<UserMstr> userList)throws LMSException {
		UserMstrDao userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		ArrayList validUserEmailSend = new ArrayList();
		try{
			validUserEmailSend = userMstrDao.insertBulkUserData(userList);
			return validUserEmailSend;
		}
		catch (DAOException e) {
			throw new LMSException(e.getMessage());
		}
	}
	public boolean checkDuplicateUserIdLob(String userLoginId, int productLobId)
	throws LMSException {
		UserMstrDao userMstrDao = null;
		boolean exist = false;
		try {
			userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
			if (userMstrDao.checkDuplicateUserIdLob(userLoginId,productLobId)) 
			{
				exist = true;
			} 
			else 
			{
				exist = false;
			}
}
catch (DAOException e) {

	   logger.error(
		   "SQL Exception occured while CheckUserId."
			   + "Exception Message: "
			   + e.getMessage()); 
}
catch (Exception e) {
	e.printStackTrace();


		logger.error("DAOException occured in checkDuplicateUserLogin():" + e.getMessage());
		throw new LMSException(e.getMessage());
		}
		return exist;
	
		}
	
	
	/* Added By Parnika for LMS Phase 2 */   
	
	public JSONObject getElementsAsJson(String productLobId) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllChildren(productLobId);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			CircleDTO dto=(CircleDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}
	
	public ArrayList getAllChildren(String productLobId) throws Exception {
 		
		ArrayList circleList=new ArrayList();
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		if (productLobId != null){
			circleList= dao.getCircleListBasedOnLob(Integer.parseInt(productLobId));
		}		
		return circleList;
	}
	
public String getCircleId (String productLobId , String circleName) throws Exception {
 		
		String  circleId =null;
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		if (productLobId != null && circleName!=null){
			circleId= dao.getCircleIdValue(productLobId, circleName);
		}		
		return circleId;
	} 
	public JSONObject getElementsAsJsonZone(String selectedTypeId, String circleMstrId) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List<ZoneDTO> list = getAllChildrenZone(selectedTypeId, circleMstrId);
		
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			ZoneDTO dto=(ZoneDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}
	
	public ArrayList<ZoneDTO> getAllChildrenZone(String selectedTypeId, String circleMstrId) throws Exception {
 		
		ArrayList<ZoneDTO> zoneList=new ArrayList<ZoneDTO>();
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		if (selectedTypeId != null && circleMstrId != null){
			zoneList= dao.getZoneListBasedOnCircleAndType(Integer.parseInt(selectedTypeId),Integer.parseInt(circleMstrId) );
		}		
		return zoneList;
	}
	
	public boolean checkDuplicateLOBCircleUserLogin(String userLoginId, String lobId, String actorId) throws DAOException,LMSException {
		UserMstrDao userMstrDao = null;
		boolean exist = false;
		try {
			userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
			if (userMstrDao.CheckUserIdBasedOnLobAndCircle(userLoginId, lobId, actorId)) {
				exist = true;
			} else {
				exist = false;
			}
		}
		catch (DAOException e) {
 
			   logger.error(
				   "SQL Exception occured while CheckUserId."
					   + "Exception Message: "
					   + e.getMessage());
			   throw new DAOException("SQLException: " + e.getMessage(), e); 
		}
		catch (Exception e) {
			e.printStackTrace();
			
				logger.error("DAOException occured in CheckUserIdBasedOnLobAndCircle():" + e.getMessage());
				throw new LMSException(e.getMessage());
			
		}
		return exist;

	}
	
	public boolean checkActorForUserLogin(String userLoginId, String actorId) throws DAOException,LMSException {
		UserMstrDao userMstrDao = null;
		boolean differentActor = false;
		try {
			userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
			if (userMstrDao.checkActorForUserLogin(userLoginId, actorId)) {
				differentActor = true;
			} else {
				differentActor = false;
			}
		}
		catch (DAOException e) {
 
			   logger.error(
				   "SQL Exception occured while checkActorForUserLogin."
					   + "Exception Message: "
					   + e.getMessage());
			   throw new DAOException("SQLException: " + e.getMessage(), e); 
		}
		catch (Exception e) {
			e.printStackTrace();
			
				logger.error("DAOException occured in checkActorForUserLogin():" + e.getMessage());
				throw new LMSException(e.getMessage());
			
		}
		return differentActor;

	}
	
	public List<LOBDTO> countUserLoginId(String userLoginId) throws LMSException, DAOException {
		UserMstrDao dao= UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
		return dao.countUserLoginId(userLoginId);
	}
	
	/* End of changes by Parnika */
	


public boolean checkDuplicateUserIdCircle(String userLoginId,
	int productLobId, int circleId) throws LMSException {
		UserMstrDao userMstrDao = null;
		boolean exist = false;
		try {
			userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
			if (userMstrDao.checkDuplicateUserIdCircle(userLoginId,productLobId,circleId)) 
			{
				exist = true;
			} 
			else 
			{
				exist = false;
			}
		}
		catch (DAOException e) {

	   logger.error(
		   "SQL Exception occured while CheckUserId."
			   + "Exception Message: "
			   + e.getMessage()); 
			}
		catch (Exception e) {
			e.printStackTrace();
		logger.error("DAOException occured in checkDuplicateUserLogin():" + e.getMessage());
		throw new LMSException(e.getMessage());
	
		}
return exist;

}

public boolean checkDuplicateUserIdMultiCircle(String userLoginId,
	int productLobId, String circleListStr) throws LMSException {
	UserMstrDao userMstrDao = null;
	boolean exist = false;
	try {
	userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
	if (userMstrDao.checkDuplicateUserIdMultiCircle(userLoginId,productLobId,circleListStr)) 
	{
		exist = true;
	} 
	else 
	{
		exist = false;
	}
	}
	catch (DAOException e) {

	   logger.error(
		   "SQL Exception occured while CheckUserId."
			   + "Exception Message: "
			   + e.getMessage()); 
	}
	catch (Exception e) {
	e.printStackTrace();
		logger.error("DAOException occured in checkDuplicateUserLogin():" + e.getMessage());
		throw new LMSException(e.getMessage());
}
return exist;

}

public JSONObject getElementsAsJsonNew(String productLobId, String userLoginId) throws Exception {
	
	JSONObject json=new JSONObject();
	JSONArray jsonItems=new JSONArray();
	String level=null;
	List list = getAllChildrenNew(productLobId,userLoginId);
	for (Iterator iter=list.iterator(); iter.hasNext();) {
		CircleDTO dto=(CircleDTO)iter.next();
		jsonItems.put(dto.toJSONObject());
	}
	json.put("elements", jsonItems);
	return json;
}
//aaded by amarjeet for Channel wise MTD Report
public JSONObject getElementsAsJsonMTD(String productLobId, String CircleId , String selectedTypeId ) throws Exception {
	
	JSONObject json=new JSONObject();
	JSONArray jsonItems=new JSONArray();
	String level=null;
/*	List<ZoneDTO> zoneList = getAllChildrenZone(selectedTypeId, CircleId);
	logger.info("list size in zone method"+zoneList.size());
	for (Iterator iter=zoneList.iterator(); iter.hasNext();) {
		ZoneDTO dto=(ZoneDTO)iter.next();
		jsonItems.put(dto.toJSONObject());
	}*/
	List list = getAllChannelPartner(productLobId,CircleId);
	
	for (Iterator iter=list.iterator(); iter.hasNext();) {
		UserMstr dto=(UserMstr)iter.next();
		jsonItems.put(dto.toJSONObject());
	}
	json.put("elements", jsonItems);
	return json;
}

public ArrayList<UserMstr> getAllChannelPartner(String productLobId,String CircleId) throws Exception {
	
	ArrayList<UserMstr> partnerList=null;
	 
	MasterDao dao= MasterDaoImpl.masterDaoInstance();
	if (productLobId != null && CircleId!=null){
		partnerList= dao.getChannelPartnerBasedOnLobNew(Integer.parseInt(productLobId),CircleId);
	}		
	return partnerList;
}

//aaded by amarjeet for Channel wise MTD Report
public ArrayList<CircleDTO> getAllChildrenNew(String productLobId,String userLoginId) throws Exception {
		
	ArrayList<CircleDTO> circleList=null;
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	if (productLobId != null){
		circleList= dao.getCircleListBasedOnLobNew(Integer.parseInt(productLobId),userLoginId);
	}		
	return circleList;
}

public UserDto getUserDetailsForEdit(String userId ,String loginActorId ,int lobId) throws LMSException {
	UserMstrDao userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
	try{
		return userMstrDao.getUserDetailsForEdit(userId, loginActorId ,lobId);
	}
	catch (DAOException e) {
		throw new LMSException(e.getMessage());
	}
}	
public void updateBulkUserCreation(UserMstr bulkDto) throws LMSException {
	
	UserMstrDao userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
	try{
		  userMstrDao.updateBulkUserCreation(bulkDto);
	}
	catch (DAOException e) {
		throw new LMSException(e.getMessage());
	}
}

public String getUserLoginId(String userId) throws LMSException {
	
	UserMstrDao userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
	try{
		  return userMstrDao.getUserLoginId(userId);
	}
	catch (DAOException e) {
		throw new LMSException(e.getMessage());
	}
	
}

public JSONObject getElementsAsJsonNewUser(String productLobId, String userLoginId) throws Exception {
	
	JSONObject json=new JSONObject();
	JSONArray jsonItems=new JSONArray();
	String level=null;
	List list = getAllChildrenNewUser(productLobId,userLoginId);
	for (Iterator iter=list.iterator(); iter.hasNext();) {
		CircleDTO dto=(CircleDTO)iter.next();
		jsonItems.put(dto.toJSONObject());
	}
	json.put("elements", jsonItems);
	return json;
}

public ArrayList<CircleDTO> getAllChildrenNewUser(String productLobId,String userLoginId) throws Exception {
		
	ArrayList<CircleDTO> circleList=null;
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	if (productLobId != null){
		circleList= dao.getCircleListBasedOnLobNewUser(Integer.parseInt(productLobId),userLoginId);
	}		
	return circleList;
}


public JSONObject getElementsAsJsonRequest(String productId,String productLobId) throws Exception {
	
	JSONObject json=new JSONObject();
	JSONArray jsonItems=new JSONArray();
	String level=null;
	List list = getAllChildrenRequestCategory(productId,productLobId);
	for (Iterator iter=list.iterator(); iter.hasNext();) {
		RequestCategoryDTO dto=(RequestCategoryDTO)iter.next();
		jsonItems.put(dto.toJSONObject());
	}
	json.put("elements", jsonItems);
	return json;
}

public ArrayList<RequestCategoryDTO> getAllChildrenRequestCategory(String productId,String productLobId) throws Exception {
	
	ArrayList<RequestCategoryDTO> requestCategoryList=null;
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	if (productId != null){
		requestCategoryList= dao.getRequestListBasedOnProduct(Integer.parseInt(productId),Integer.parseInt(productLobId));
	}		
	return requestCategoryList;
}


public JSONObject getElementsAsJsonRequestLob(String productLobId)
		throws Exception {
	
	JSONObject json=new JSONObject();
	JSONArray jsonItems=new JSONArray();
	String level=null;
	List list = getAllChildrenRequestCategoryLob(productLobId);
	for (Iterator iter=list.iterator(); iter.hasNext();) {
		RequestCategoryDTO dto=(RequestCategoryDTO)iter.next();
		jsonItems.put(dto.toJSONObject());
	}
	json.put("elements", jsonItems);
	return json;
}
public ArrayList<RequestCategoryDTO> getAllChildrenRequestCategoryLob(String productLobId) throws Exception {
	
	ArrayList<RequestCategoryDTO> requestCategoryList=null;
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	if (productLobId != null){
		requestCategoryList= dao.getRequestListBasedOnProductLob(Integer.parseInt(productLobId));
	}		
	return requestCategoryList;
}

@Override
public JSONObject getRsuForCircleChange(String circleMstriD) throws Exception {
	
	JSONObject json=new JSONObject();
	JSONArray jsonItems=new JSONArray();
	String level=null;
	List list = getAllChildrenRsuForCircleChange(circleMstriD);
	for (Iterator iter=list.iterator(); iter.hasNext();) {
		RSUDTO dto=(RSUDTO)iter.next();
		jsonItems.put(dto.toJSONObject());
	}
	json.put("elements", jsonItems);
	return json;
}
public ArrayList<RSUDTO> getAllChildrenRsuForCircleChange(String circleMstriD) throws Exception {
	
	ArrayList<RSUDTO> rsuList=null;
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	if (circleMstriD != null){
		rsuList= dao.getRsuForCircleChange(Integer.parseInt(circleMstriD));
	}		
	return rsuList;
}

@Override
public UserMstr getUserFnameDetails(String userFname) throws Exception {
	UserMstrDao userMstrDao = UserMstrDaoImpl.userMstrDaoInstance();//changed by srikant new UserMstrDaoImpl();
	try{
		return userMstrDao.getUserFnameDetails(userFname);
	}
	catch (DAOException e) {
		throw new LMSException(e.getMessage());
	}
}


}

