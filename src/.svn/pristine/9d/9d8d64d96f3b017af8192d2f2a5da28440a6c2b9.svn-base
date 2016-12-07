package com.ibm.lms.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.lms.common.Constants;


 /** 
	* @author Naman 
	* Created On Mon Jan 14 13:13:38 IST 2008 
	* Data Trasnfer Object class for table KM_USER_MSTR 
 
 **/ 
public class UserMstr implements Serializable {
	
	private String flag="";
	private String startDate="";
	private String endDate="";
    private String categoryId ="0";
    
	private String elementName="";    
    
    private String userId="";

    private String userLoginId="";

    private String userFname="";
    
    private String fileName="";

    private String userMname="";

    private String userLname="";

    private String userMobileNumber="";

    private String userEmailid="";
    
    private String userPassword="";

    private String userPasswordEncrypted="";

    private java.sql.Timestamp userPsswrdExpryDt=null;

    private java.sql.Timestamp createdDt;

    private String createdBy="";

    private java.sql.Timestamp updatedDt=null;

    private String updatedBy=null;

    private String status="";

    private String circleId="";
    
    private String leadStatus="";
    
    private String circleName="";

    private String groupId="";

    private String kmActorId="";
    
    private String kmActorName="";

    private String kmOwnerId="";

    private String loginAttempted="0";

    private String lastLoginTime= "";
    
    private String userLoginStatus=null;
    
	private String favCategoryId="";
	
	private String elementId="";    
    
	private String newPassword="";
   
	private String oldPassword="";
	
	private String ASSIGNMENT_APPROVAL_FLAG="";
	private int alertForSessionExpiry=0;
	
	private int rowNumber = -1;
	private String message = "";
	private boolean errFlag ;
	
	//private Calendar sessionExpiry=null;
    
	private long alertUpdateTime=0;
	
	private boolean csr = false;
	
	/*km Phase II */
	
	private String partnerName="";
	private String categoryName="";
	private ArrayList moduleList;
	private HashMap categoryList;
	private HashMap ModuleData;
	private String favCategoryName="";
	
	/* edited by harpreet for phase II report */
	
	private String loginTime="";
	private int noOfLoggedInUser;
	private String lobName="";
	private String firstName="";
	private String lastName="";
	private int total;
	private String lockType="";
	
	private boolean restricted = false;
	
	private String initialElementPath="";
	
	private String process = "";
	private String businessSegment = "";
	private String activity = "";
	private String location = "";
	private String role = "";
	private String circle = "";
	private String lob = "";
	private String udId = "";
	
	/* Added by Parnika for LMS Phase 2 */
	private String[] createMultiple = null;
	private String selectedLobId; 
    private String circleMstrId=null;
    private int selectedTypeId;
    private String lobId="";
    
	private ArrayList zoneList = null;
	private int selectedZoneId; 
    private String zoneName=null;
    private int zoneId;
    private ArrayList circleList = null;
    
    private ArrayList lobList = null;
    private String methodTempName;
    private String channelPartnerId = null;
    /* End of changes by Parnika */
    
	
    
	public String getUdId() {
		return udId;
	}


	public String getChannelPartnerId() {
		return channelPartnerId;
	}


	public void setChannelPartnerId(String channelPartnerId) {
		this.channelPartnerId = channelPartnerId;
	}


	public String getMethodTempName() {
		return methodTempName;
	}


	public void setMethodTempName(String methodTempName) {
		this.methodTempName = methodTempName;
	}


	public ArrayList getLobList() {
		return lobList;
	}


	public void setLobList(ArrayList lobList) {
		this.lobList = lobList;
	}


	public ArrayList getCircleList() {
		return circleList;
	}


	public void setCircleList(ArrayList circleList) {
		this.circleList = circleList;
	}


	public String[] getCreateMultiple() {
		return createMultiple;
	}


	public void setCreateMultiple(String[] createMultiple) {
		this.createMultiple = createMultiple;
	}


	public String getSelectedLobId() {
		return selectedLobId;
	}


	public void setSelectedLobId(String selectedLobId) {
		this.selectedLobId = selectedLobId;
	}


	public String getCircleMstrId() {
		return circleMstrId;
	}


	public void setCircleMstrId(String circleMstrId) {
		this.circleMstrId = circleMstrId;
	}


	public int getSelectedTypeId() {
		return selectedTypeId;
	}


	public void setSelectedTypeId(int selectedTypeId) {
		this.selectedTypeId = selectedTypeId;
	}


	public String getLobId() {
		return lobId;
	}


	public void setLobId(String lobId) {
		this.lobId = lobId;
	}


	public ArrayList getZoneList() {
		return zoneList;
	}


	public void setZoneList(ArrayList zoneList) {
		this.zoneList = zoneList;
	}


	public int getSelectedZoneId() {
		return selectedZoneId;
	}


	public void setSelectedZoneId(int selectedZoneId) {
		this.selectedZoneId = selectedZoneId;
	}


	public String getZoneName() {
		return zoneName;
	}


	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}


	public int getZoneId() {
		return zoneId;
	}


	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}


	public void setUdId(String udId) {
		this.udId = udId;
	}


	//added by ashutosh for login user status report
	private Double totalLoginTime;
	
	//added by Karan for keeping track of session id of the user
	private String sessionID ="";
	
	private List favoriteList;
	
	/* added by parnika for new fields in create user */	
	private String partner="";
	private String pbxId="";
	/* End of changes by Parnika */
	
	
	/* Added by Parnika for capturing ip Address of logeed in user */
	private String ipaddress = "";
	/* End of changes by Parnika */
	
	/*Added by sugandha for bulk user creation */
	private String zoneCode = "";
	private String cityZoneCode = "";
	private String zoneFlag = "";
	/*End of changes by Sugandha*/
	
	public List getFavoriteList() {
		return favoriteList;
	}


	public String getZoneCode() {
		return zoneCode;
	}


	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}


	public String getCityZoneCode() {
		return cityZoneCode;
	}


	public void setCityZoneCode(String cityZoneCode) {
		this.cityZoneCode = cityZoneCode;
	}


	public String getZoneFlag() {
		return zoneFlag;
	}


	public void setZoneFlag(String zoneFlag) {
		this.zoneFlag = zoneFlag;
	}


	public String getIpaddress() {
		return ipaddress;
	}


	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}


	public void setFavoriteList(List favoriteList) {
		this.favoriteList = favoriteList;
	}




	
	
	public String getPartner() {
		return partner;
	}


	public void setPartner(String partner) {
		this.partner = partner;
	}


	public String getPbxId() {
		return pbxId;
	}


	public void setPbxId(String pbxId) {
		this.pbxId = pbxId;
	}


	public Double getTotalLoginTime() {
		return totalLoginTime;
	}


	public void setTotalLoginTime(Double totalLoginTime) {
		this.totalLoginTime = totalLoginTime;
	}


	public String getProcess() {
		return process;
	}


	public void setProcess(String process) {
		this.process = process;
	}


	public String getBusinessSegment() {
		return businessSegment;
	}


	public void setBusinessSegment(String businessSegment) {
		this.businessSegment = businessSegment;
	}


	public String getActivity() {
		return activity;
	}


	public void setActivity(String activity) {
		this.activity = activity;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	
	
	public String getLeadStatus() {
		return leadStatus;
	}


	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}


	public String getCircle() {
		return circle;
	}


	public void setCircle(String circle) {
		this.circle = circle;
	}


	public String getLob() {
		return lob;
	}


	public void setLob(String lob) {
		this.lob = lob;
	}


	public String getInitialElementPath() {
		return initialElementPath;
	}


	public void setInitialElementPath(String initialElementPath) {
		this.initialElementPath = initialElementPath;
	}


	public String getInitialParentId() {
		return initialParentId;
	}


	public void setInitialParentId(String initialParentId) {
		this.initialParentId = initialParentId;
	}


	private String initialParentId;

	

    public boolean isRestricted() {
		return restricted;
	}


	public void setRestricted(boolean restricted) {
		this.restricted = restricted;
	}


	/** Creates a dto for the KM_USER_MSTR table */
    public UserMstr() {
    }


 /** 
	* Get userId associated with this object.
	* @return userId
 **/ 

    public String getUserId() {
        return userId;
    }

 /** 
	* Set userId associated with this object.
	* @param userId The userId value to set
 **/ 

    public void setUserId(String userId) {
        this.userId = userId;
    }

 /** 
	* Get userLoginId associated with this object.
	* @return userLoginId
 **/ 

    public String getUserLoginId() {
        return userLoginId;
    }

 /** 
	* Set userLoginId associated with this object.
	* @param userLoginId The userLoginId value to set
 **/ 

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId.toUpperCase();
    }

 /** 
	* Get userFname associated with this object.
	* @return userFname
 **/ 

    public String getUserFname() {
        return userFname;
    }

 /** 
	* Set userFname associated with this object.
	* @param userFname The userFname value to set
 **/ 

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

 /** 
	* Get userMname associated with this object.
	* @return userMname
 **/ 

    public String getUserMname() {
        return userMname;
    }

 /** 
	* Set userMname associated with this object.
	* @param userMname The userMname value to set
 **/ 

    public void setUserMname(String userMname) {
        this.userMname = userMname;
    }

 /** 
	* Get userLname associated with this object.
	* @return userLname
 **/ 

    public String getUserLname() {
        return userLname;
    }

 /** 
	* Set userLname associated with this object.
	* @param userLname The userLname value to set
 **/ 

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

 /** 
	* Get userMobileNumber associated with this object.
	* @return userMobileNumber
 **/ 

    public String getUserMobileNumber() {
        return userMobileNumber;
    }

 /** 
	* Set userMobileNumber associated with this object.
	* @param userMobileNumber The userMobileNumber value to set
 **/ 

    public void setUserMobileNumber(String userMobileNumber) {
        this.userMobileNumber = userMobileNumber;
    }

 /** 
	* Get userEmailid associated with this object.
	* @return userEmailid
 **/ 

    public String getUserEmailid() {
        return userEmailid;
    }

 /** 
	* Set userEmailid associated with this object.
	* @param userEmailid The userEmailid value to set
 **/ 

    public void setUserEmailid(String userEmailid) {
        this.userEmailid = userEmailid;
    }

 /** 
	* Get userPassword associated with this object.
	* @return userPassword
 **/ 

    public String getUserPassword() {
        return userPassword;
    }

 /** 
	* Set userPassword associated with this object.
	* @param userPassword The userPassword value to set
 **/ 

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

 /** 
	* Get userPsswrdExpryDt associated with this object.
	* @return userPsswrdExpryDt
 **/ 

    public java.sql.Timestamp getUserPsswrdExpryDt() {
        return userPsswrdExpryDt;
    }

 /** 
	* Set userPsswrdExpryDt associated with this object.
	* @param userPsswrdExpryDt The userPsswrdExpryDt value to set
 **/ 

    public void setUserPsswrdExpryDt(java.sql.Timestamp userPsswrdExpryDt) {
        this.userPsswrdExpryDt = userPsswrdExpryDt;
    }

 /** 
	* Get createdDt associated with this object.
	* @return createdDt
 **/ 

    public java.sql.Timestamp getCreatedDt() {
        return createdDt;
    }

 /** 
	* Set createdDt associated with this object.
	* @param createdDt The createdDt value to set
 **/ 

    public void setCreatedDt(java.sql.Timestamp createdDt) {
        this.createdDt = createdDt;
    }

 /** 
	* Get createdBy associated with this object.
	* @return createdBy
 **/ 

    public String getCreatedBy() {
        return createdBy;
    }

 /** 
	* Set createdBy associated with this object.
	* @param createdBy The createdBy value to set
 **/ 

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

 /** 
	* Get updatedDt associated with this object.
	* @return updatedDt
 **/ 

    public java.sql.Timestamp getUpdatedDt() {
        return updatedDt;
    }

 /** 
	* Set updatedDt associated with this object.
	* @param updatedDt The updatedDt value to set
 **/ 

    public void setUpdatedDt(java.sql.Timestamp updatedDt) {
        this.updatedDt = updatedDt;
    }

 /** 
	* Get updatedBy associated with this object.
	* @return updatedBy
 **/ 

    public String getUpdatedBy() {
        return updatedBy;
    }

 /** 
	* Set updatedBy associated with this object.
	* @param updatedBy The updatedBy value to set
 **/ 

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

 /** 
	* Get status associated with this object.
	* @return status
 **/ 

    public String getStatus() {
        return status;
    }

 /** 
	* Set status associated with this object.
	* @param status The status value to set
 **/ 

    public void setStatus(String status) {
        this.status = status;
    }

 /** 
	* Get circleId associated with this object.
	* @return circleId
 **/ 

    public String getCircleId() {
        return circleId;
    }

 /** 
	* Set circleId associated with this object.
	* @param circleId The circleId value to set
 **/ 

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

 /** 
	* Get groupId associated with this object.
	* @return groupId
 **/ 

    public String getGroupId() {
        return groupId;
    }

 /** 
	* Set groupId associated with this object.
	* @param groupId The groupId value to set
 **/ 

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

 /** 
	* Get kmActorId associated with this object.
	* @return kmActorId
 **/ 

    public String getKmActorId() {
        return kmActorId;
    }

 /** 
	* Set kmActorId associated with this object.
	* @param kmActorId The kmActorId value to set
 **/ 

    public void setKmActorId(String kmActorId) {
        this.kmActorId = kmActorId;
    }

 /** 
	* Get kmOwnerId associated with this object.
	* @return kmOwnerId
 **/ 

    public String getKmOwnerId() {
        return kmOwnerId;
    }

 /** 
	* Set kmOwnerId associated with this object.
	* @param kmOwnerId The kmOwnerId value to set
 **/ 

    public void setKmOwnerId(String kmOwnerId) {
        this.kmOwnerId = kmOwnerId;
    }

 /** 
	* Get loginAttempted associated with this object.
	* @return loginAttempted
 **/ 

    public String getLoginAttempted() {
        return loginAttempted;
    }

 /** 
	* Set loginAttempted associated with this object.
	* @param loginAttempted The loginAttempted value to set
 **/ 

    public void setLoginAttempted(String loginAttempted) {
        this.loginAttempted = loginAttempted;
    }

 /** 
	* Get lastLoginTime associated with this object.
	* @return lastLoginTime
 **/ 

   

	/**
	 * @return
	 */
	public HashMap getCategoryList() {
		return categoryList;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}


	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}


	/**
	 * @return
	 */
	public HashMap getModuleData() {
		return ModuleData;
	}

	/**
	 * @return
	 */
	public ArrayList getModuleList() {
		return moduleList;
	}

	/**
	 * @param map
	 */
	public void setCategoryList(HashMap map) {
		categoryList = map;
	}

	/**
	 * @param map
	 */
	public void setModuleData(HashMap map) {
		ModuleData = map;
	}

	/**
	 * @param list
	 */
	public void setModuleList(ArrayList list) {
		moduleList = list;
	}

	/**
	 * @return
	 */
	

	/**
	 * @return
	 */
	public String getNewPassword() {
		return newPassword;
	}

	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	/**
	 * @return
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param string
	 */
	public void setNewPassword(String string) {
		newPassword = string;
	}

	/**
	 * @param string
	 */
	public void setOldPassword(String string) {
		oldPassword = string;
	}

	/**
	 * @return
	 */
	public String getCircleName() {
		return circleName;
	}

	/**
	 * @return
	 */
	public String getKmActorName() {
		return kmActorName;
	}

	/**
	 * @param string
	 */
	public void setCircleName(String string) {
		circleName = string;
	}

	/**
	 * @param string
	 */
	public void setKmActorName(String string) {
		kmActorName = string;
	}

	/**
	 * @return
	 */
	public String getUserLoginStatus() {
		return userLoginStatus;
	}

	/**
	 * @param string
	 */
	public void setUserLoginStatus(String string) {
		userLoginStatus = string;
	}

	/**
	 * @return
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param string
	 */
	public void setCategoryId(String string) {
		categoryId = string;
	}

	/**
	 * @return
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param string
	 */
	public void setCategoryName(String string) {
		categoryName = string;
	}

	/**
	 * @return
	 */
	public String getElementId() {
		return elementId;
	}

	/**
	 * @return
	 */
	public String getFavCategoryId() {
		return favCategoryId;
	}

	/**
	 * @param string
	 */
	public void setElementId(String string) {
		elementId = string;
	}

	/**
	 * @param string
	 */
	public void setFavCategoryId(String string) {
		favCategoryId = string;
	}

	/**
	 * @return
	 */
	public String getElementName() {
		return elementName;
	}

	/**
	 * @param string
	 */
	public void setElementName(String string) {
		elementName = string;
	}

	/**
	 * @return
	 */
	public String getFavCategoryName() {
		return favCategoryName;
	}

	/**
	 * @param string
	 */
	
	
	public void setFavCategoryName(String string) {
		favCategoryName = string;
	}

	public String getASSIGNMENT_APPROVAL_FLAG() {
		return ASSIGNMENT_APPROVAL_FLAG;
	}


	public void setASSIGNMENT_APPROVAL_FLAG(String assignment_approval_flag) {
		ASSIGNMENT_APPROVAL_FLAG = assignment_approval_flag;
	}


	/**
	 * @return
	 */
	public String getPartnerName() {
		return partnerName;
	}

	/**
	 * @param string
	 */
	public void setPartnerName(String string) {
		partnerName = string;
	}

	/**
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return
	 */
	public String getLobName() {
		return lobName;
	}

	/**
	 * @return
	 */
	public String getLoginTime() {
		return loginTime;
	}

	/**
	 * @return
	 */
	public int getNoOfLoggedInUser() {
		return noOfLoggedInUser;
	}

	/**
	 * @return
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param string
	 */
	public void setFirstName(String string) {
		firstName = string;
	}

	/**
	 * @param string
	 */
	public void setLastName(String string) {
		lastName = string;
	}

	/**
	 * @param string
	 */
	public void setLobName(String string) {
		lobName = string;
	}

	/**
	 * @param string
	 */
	public void setLoginTime(String string) {
		loginTime = string;
	}

	/**
	 * @param i
	 */
	public void setNoOfLoggedInUser(int i) {
		noOfLoggedInUser = i;
	}

	/**
	 * @param i
	 */
	public void setTotal(int i) {
		total = i;
	}


	public int getAlertForSessionExpiry() {
		return alertForSessionExpiry;
	}


	public void setAlertForSessionExpiry(int alertForSessionExpiry) {
		this.alertForSessionExpiry = alertForSessionExpiry;
	}


	public String getLockType() {
		return lockType;
	}


	public void setLockType(String lockType) {
		this.lockType = lockType;
	}

	public long getAlertUpdateTime() {
		return alertUpdateTime;
	}


	public void setAlertUpdateTime(long alertUpdateTime) {
		this.alertUpdateTime = alertUpdateTime;
	}


	public void setCsr(boolean csr) {
		this.csr = csr;
	}


	public boolean isCsr() {
		return ( kmActorId.equals(Constants.CIRCLE_CSR) ||  kmActorId.equals(Constants.CATEGORY_CSR) );
	}


	public String getSessionID() {
		return sessionID;
	}


	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}


	public String getUserPasswordEncrypted() {
		return userPasswordEncrypted;
	}


	public void setUserPasswordEncrypted(String userPasswordEncrypted) {
		this.userPasswordEncrypted = userPasswordEncrypted;
	}


	public boolean isErrFlag() {
		return errFlag;
	}


	public void setErrFlag(boolean errFlag) {
		this.errFlag = errFlag;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public int getRowNumber() {
		return rowNumber;
	}


	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	
	
	  public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getFileName() {
			return fileName;
		}


		public void setFileName(String fileName) {
			this.fileName = fileName;
		}


	public JSONObject toJSONObject() {
		JSONObject json=new JSONObject();
		try {
			json.put("userId", userId);
			json.put("userFname", userFname);
			json.put("circleMstrId", circleMstrId);
			json.put("lobId", lobId);			

		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return json; 
	}

}
