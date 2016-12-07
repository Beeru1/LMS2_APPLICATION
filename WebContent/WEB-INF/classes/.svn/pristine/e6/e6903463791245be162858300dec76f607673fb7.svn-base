package com.ibm.km.forms;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


 /** 
	* @author Naman 
	* Created On Mon Jan 14 13:12:17 IST 2008 
	* Form Bean class for table KM_DOCUMENT_MSTR 
 
 **/ 
public class KmDocumentMstrFormBean extends ActionForm {

	//	 iPortal_Enhancements changes for complete string search; CSR20111025-00-06938:BFR2
	private String searchContentHidden="";
	// End of Change

	
	private String documentId;

	private String documentGroupId;
	
	private String selectedParentId;

	private String documentName;

	private String documentDisplayName;

	private String documentDesc;

	private String subCategoryId;
    
	private String subCategoryName;

	private String categoryId;
    
	private String categoryName;

	private String circleId;
    
	private String circleName;
	
	private ArrayList categoryList = null;
	
	private ArrayList subCategoryList = null;
	
	private ArrayList circleList = null;
	
	private String loginActorId = null; 
	
	private String numberOfHits;

	private String status;

	private String approvalStatus;

	private String uploadedBy;

	private java.sql.Timestamp uploadedDt;

	private String updatedBy;

	private java.sql.Timestamp updatedDt;

	private java.sql.Timestamp approvalRejectionDt;

	private String approvalRejectionBy;

	private java.sql.Timestamp publishingStartDt;

	private java.sql.Timestamp publishingEndDt;
	
	private String methodName=null;
	
	private String message = null;
	
	private ArrayList fileList = null;
	
	private HashMap documentList=null;
	
	private String delete="OFF";

	private String deleteStatus=null;
	
	private String selectedDocumentId=null;
	
	private String keyword=null;
	
	private String searchType="";
	
	private String selectedDocumentPath = "";
	
	private String filePath = "";
	
	private String reset="1";
	
	private String showFile="false";
	
	private String favorite="false";
	
	private String initialLevel;
	private String initialLevelName;
	private String parentId="";
	private String initialSelectBox;
	private String levelCount;
	private String elementFolderPath;
	private String elementLevel="";
	private String initStatus="true";
	private String documentStringPath="";
	private String elementPath="";
	private String oldKeyword="";
	private String kmActorId;
	private ArrayList versionList;
	private String documentKeyword="";
	
	private String downloadAccess="";
	private String docName="";
	private ArrayList docList;
	
	private String dummy = "";
	private String[] checkedDocs = null;
	
	private String publishStartDt=null;
	private String publishEndDt=null;
	
	private String searchMode="";
	
	private String searchModeChecked = "";
	
	private String searchFromDt ="";
	private String searchToDt = "";
	private String dateCheck = "";
	
	public String getDateCheck() {
		return dateCheck;
	}

	public void setDateCheck(String dateCheck) {
		this.dateCheck = dateCheck;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public String getPublishStartDt() {
		return publishStartDt;
	}

	public String getSearchFromDt() {
		return searchFromDt;
	}

	public void setSearchFromDt(String searchFromDt) {
		this.searchFromDt = searchFromDt;
	}

	public String getSearchToDt() {
		return searchToDt;
	}

	public void setSearchToDt(String searchToDt) {
		this.searchToDt = searchToDt;
	}

	public void setPublishStartDt(String publishStartDt) {
		this.publishStartDt = publishStartDt;
	}

	public String getDummy() {
		return dummy;
	}

	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

	//Added by Atul
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
		initialSelectBox="";
		
	}
	
    /** Creates a dto for the KM_DOCUMENT_MSTR table */
    public KmDocumentMstrFormBean() {
    }


    public ActionErrors validate(
        ActionMapping mapping,
        HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        //TODO Replace with Actual code.This method is called if validate for this action mapping is set to true. and iff errors is not null and not emoty it forwards to the page defined in input attritbute of the ActionMapping
        return errors;
    }
 /** 
	* Get documentId associated with this object.
	* @return documentId
 **/ 

    public String getDocumentId() {
        return documentId;
    }

 /** 
	* Set documentId associated with this object.
	* @param documentId The documentId value to set
 **/ 

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

 /** 
	* Get documentGroupId associated with this object.
	* @return documentGroupId
 **/ 

    public String getDocumentGroupId() {
        return documentGroupId;
    }

 /** 
	* Set documentGroupId associated with this object.
	* @param documentGroupId The documentGroupId value to set
 **/ 

    public void setDocumentGroupId(String documentGroupId) {
        this.documentGroupId = documentGroupId;
    }

 /** 
	* Get documentName associated with this object.
	* @return documentName
 **/ 

    public String getDocumentName() {
        return documentName;
    }

 /** 
	* Set documentName associated with this object.
	* @param documentName The documentName value to set
 **/ 

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

 /** 
	* Get documentDisplayName associated with this object.
	* @return documentDisplayName
 **/ 

    public String getDocumentDisplayName() {
        return documentDisplayName;
    }

 /** 
	* Set documentDisplayName associated with this object.
	* @param documentDisplayName The documentDisplayName value to set
 **/ 

    public void setDocumentDisplayName(String documentDisplayName) {
        this.documentDisplayName = documentDisplayName;
    }

 /** 
	* Get documentDesc associated with this object.
	* @return documentDesc
 **/ 

    public String getDocumentDesc() {
        return documentDesc;
    }

 /** 
	* Set documentDesc associated with this object.
	* @param documentDesc The documentDesc value to set
 **/ 

    public void setDocumentDesc(String documentDesc) {
        this.documentDesc = documentDesc;
    }

 /** 
	* Get subCategoryId associated with this object.
	* @return subCategoryId
 **/ 

    public String getSubCategoryId() {
        return subCategoryId;
    }

 /** 
	* Set subCategoryId associated with this object.
	* @param subCategoryId The subCategoryId value to set
 **/ 

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

 /** 
	* Get categoryId associated with this object.
	* @return categoryId
 **/ 

    public String getCategoryId() {
        return categoryId;
    }

 /** 
	* Set categoryId associated with this object.
	* @param categoryId The categoryId value to set
 **/ 

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
	* Get numberOfHits associated with this object.
	* @return numberOfHits
 **/ 

    public String getNumberOfHits() {
        return numberOfHits;
    }

 /** 
	* Set numberOfHits associated with this object.
	* @param numberOfHits The numberOfHits value to set
 **/ 

    public void setNumberOfHits(String numberOfHits) {
        this.numberOfHits = numberOfHits;
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
	* Get approvalStatus associated with this object.
	* @return approvalStatus
 **/ 

    public String getApprovalStatus() {
        return approvalStatus;
    }

 /** 
	* Set approvalStatus associated with this object.
	* @param approvalStatus The approvalStatus value to set
 **/ 

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

 /** 
	* Get uploadedBy associated with this object.
	* @return uploadedBy
 **/ 

    public String getUploadedBy() {
        return uploadedBy;
    }

 /** 
	* Set uploadedBy associated with this object.
	* @param uploadedBy The uploadedBy value to set
 **/ 

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

 /** 
	* Get uploadedDt associated with this object.
	* @return uploadedDt
 **/ 

    public java.sql.Timestamp getUploadedDt() {
        return uploadedDt;
    }

 /** 
	* Set uploadedDt associated with this object.
	* @param uploadedDt The uploadedDt value to set
 **/ 

    public void setUploadedDt(java.sql.Timestamp uploadedDt) {
        this.uploadedDt = uploadedDt;
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
	* Get approvalRejectionDt associated with this object.
	* @return approvalRejectionDt
 **/ 

    public java.sql.Timestamp getApprovalRejectionDt() {
        return approvalRejectionDt;
    }

 /** 
	* Set approvalRejectionDt associated with this object.
	* @param approvalRejectionDt The approvalRejectionDt value to set
 **/ 

    public void setApprovalRejectionDt(java.sql.Timestamp approvalRejectionDt) {
        this.approvalRejectionDt = approvalRejectionDt;
    }

 /** 
	* Get approvalRejectionBy associated with this object.
	* @return approvalRejectionBy
 **/ 

    public String getApprovalRejectionBy() {
        return approvalRejectionBy;
    }

 /** 
	* Set approvalRejectionBy associated with this object.
	* @param approvalRejectionBy The approvalRejectionBy value to set
 **/ 

    public void setApprovalRejectionBy(String approvalRejectionBy) {
        this.approvalRejectionBy = approvalRejectionBy;
    }

 /** 
	* Get publishingStartDt associated with this object.
	* @return publishingStartDt
 **/ 

    public java.sql.Timestamp getPublishingStartDt() {
        return publishingStartDt;
    }

 /** 
	* Set publishingStartDt associated with this object.
	* @param publishingStartDt The publishingStartDt value to set
 **/ 

    public void setPublishingStartDt(java.sql.Timestamp publishingStartDt) {
        this.publishingStartDt = publishingStartDt;
    }

 /** 
	* Get publishingEndDt associated with this object.
	* @return publishingEndDt
 **/ 

    public java.sql.Timestamp getPublishingEndDt() {
        return publishingEndDt;
    }

 /** 
	* Set publishingEndDt associated with this object.
	* @param publishingEndDt The publishingEndDt value to set
 **/ 

    public void setPublishingEndDt(java.sql.Timestamp publishingEndDt) {
        this.publishingEndDt = publishingEndDt;
    }

	/**
	 * @return
	 */
	public String getCategoryName() {
		return categoryName;
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
	public String getSubCategoryName() {
		return subCategoryName;
	}

	/**
	 * @param string
	 */
	public void setCategoryName(String string) {
		categoryName = string;
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
	public void setSubCategoryName(String string) {
		subCategoryName = string;
	}

	/**
	 * @return
	 */
	public ArrayList getCategoryList() {
		return categoryList;
	}

	/**
	 * @return
	 */
	public ArrayList getCircleList() {
		return circleList;
	}

	/**
	 * @return
	 */
	public String getDelete() {
		return delete;
	}

	/**
	 * @return
	 */
	public String getDeleteStatus() {
		return deleteStatus;
	}

	/**
	 * @return
	 */
	public HashMap getDocumentList() {
		return documentList;
	}

	/**
	 * @return
	 */
	public ArrayList getFileList() {
		return fileList;
	}

	/**
	 * @return
	 */
	public String getLoginActorId() {
		return loginActorId;
	}

	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @return
	 */
	public ArrayList getSubCategoryList() {
		return subCategoryList;
	}

	/**
	 * @param list
	 */
	public void setCategoryList(ArrayList list) {
		categoryList = list;
	}

	/**
	 * @param list
	 */
	public void setCircleList(ArrayList list) {
		circleList = list;
	}

	/**
	 * @param string
	 */
	public void setDelete(String string) {
		delete = string;
	}

	/**
	 * @param string
	 */
	public void setDeleteStatus(String string) {
		deleteStatus = string;
	}

	/**
	 * @param map
	 */
	public void setDocumentList(HashMap map) {
		documentList = map;
	}

	/**
	 * @param list
	 */
	public void setFileList(ArrayList list) {
		fileList = list;
	}

	/**
	 * @param string
	 */
	public void setLoginActorId(String string) {
		loginActorId = string;
	}

	/**
	 * @param string
	 */
	public void setMessage(String string) {
		message = string;
	}

	/**
	 * @param string
	 */
	public void setMethodName(String string) {
		methodName = string;
	}

	/**
	 * @param list
	 */
	public void setSubCategoryList(ArrayList list) {
		subCategoryList = list;
	}

	
	/**
	 * @return
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param string
	 */
	public void setKeyword(String string) {
		keyword = string;
	}

	/**
	 * @return
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * @param string
	 */
	public void setSearchType(String string) {
		searchType = string;
	}

	/**
	 * @return
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @return
	 */
	public String getSelectedDocumentPath() {
		return selectedDocumentPath;
	}

	/**
	 * @param string
	 */
	public void setFilePath(String string) {
		filePath = string;
	}

	/**
	 * @param string
	 */
	public void setSelectedDocumentPath(String string) {
		selectedDocumentPath = string;
	}

	/**
	 * @return
	 */
	public String getSelectedDocumentId() {
		return selectedDocumentId;
	}

	/**
	 * @param string
	 */
	public void setSelectedDocumentId(String string) {
		selectedDocumentId = string;
	}

	/**
	 * @return
	 */
	public String getReset() {
		return reset;
	}



	/**
	 * @param string
	 */
	public void setReset(String string) {
		reset = string;
	}



	/**
	 * @return
	 */
	public String getShowFile() {
		return showFile;
	}

	/**
	 * @param string
	 */
	public void setShowFile(String string) {
		showFile = string;
	}

	/**
	 * @return
	 */
	public String getFavorite() {
		return favorite;
	}

	/**
	 * @param string
	 */
	public void setFavorite(String string) {
		favorite = string;
	}

	/**
	 * @return
	 */
	public String getElementFolderPath() {
		return elementFolderPath;
	}

	/**
	 * @return
	 */
	public String getElementLevel() {
		return elementLevel;
	}

	/**
	 * @return
	 */
	public String getInitialLevel() {
		return initialLevel;
	}

	/**
	 * @return
	 */
	public String getInitialLevelName() {
		return initialLevelName;
	}

	/**
	 * @return
	 */
	public String getInitialSelectBox() {
		return initialSelectBox;
	}

	/**
	 * @return
	 */
	public String getLevelCount() {
		return levelCount;
	}

	/**
	 * @return
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param string
	 */
	public void setElementFolderPath(String string) {
		elementFolderPath = string;
	}

	/**
	 * @param string
	 */
	public void setElementLevel(String string) {
		elementLevel = string;
	}

	/**
	 * @param string
	 */
	public void setInitialLevel(String string) {
		initialLevel = string;
	}

	/**
	 * @param string
	 */
	public void setInitialLevelName(String string) {
		initialLevelName = string;
	}

	/**
	 * @param string
	 */
	public void setInitialSelectBox(String string) {
		initialSelectBox = string;
	}

	/**
	 * @param string
	 */
	public void setLevelCount(String string) {
		levelCount = string;
	}

	/**
	 * @param string
	 */
	public void setParentId(String string) {
		parentId = string;
	}

	/**
	 * @return
	 */
	public String getSelectedParentId() {
		return selectedParentId;
	}

	/**
	 * @param string
	 */
	public void setSelectedParentId(String string) {
		selectedParentId = string;
	}

	/**
	 * @return
	 */
	public String getInitStatus() {
		return initStatus;
	}

	/**
	 * @param string
	 */
	public void setInitStatus(String string) {
		initStatus = string;
	}

	/**
	 * @return
	 */
	public String getDocumentStringPath() {
		return documentStringPath;
	}

	/**
	 * @param string
	 */
	public void setDocumentStringPath(String string) {
		documentStringPath = string;
	}

	/**
	 * @return
	 */
	public String getElementPath() {
		return elementPath;
	}

	/**
	 * @param string
	 */
	public void setElementPath(String string) {
		elementPath = string;
	}

	/**
	 * @return
	 */
	public String getOldKeyword() {
		return oldKeyword;
	}

	/**
	 * @param string
	 */
	public void setOldKeyword(String string) {
		oldKeyword = string;
	}

	/**
	 * @return
	 */
	public String getKmActorId() {
		return kmActorId;
	}

	/**
	 * @param string
	 */
	public void setKmActorId(String string) {
		kmActorId = string;
	}

	/**
	 * @return
	 */
	public ArrayList getVersionList() {
		return versionList;
	}

	/**
	 * @param list
	 */
	public void setVersionList(ArrayList list) {
		versionList = list;
	}

	/**
	 * @return
	 */
	public String getDocumentKeyword() {
		return documentKeyword;
	}

	/**
	 * @param string
	 */
	public void setDocumentKeyword(String string) {
		documentKeyword = string;
	}

	/**
	 * @return
	 */
	public String getPublishEndDt() {
		return publishEndDt;
	}

	/**
	 * @param string
	 */
	public void setPublishEndDt(String string) {
		publishEndDt = string;
	}

	/**
	 * @return
	 */
	public String getDownloadAccess() {
		return downloadAccess;
	}

	/**
	 * @param string
	 */
	public void setDownloadAccess(String string) {
		downloadAccess = string;
	}

	/**
	 * @return
	 */
	public String getDocName() {
		return docName;
	}

	/**
	 * @param string
	 */
	public void setDocName(String string) {
		docName = string;
	}

	public ArrayList getDocList() {
		return docList;
	}

	public void setDocList(ArrayList docList) {
		this.docList = docList;
	}

		//	 iPortal_Enhancements changes for complete string search; CSR20111025-00-06938:BFR2
	public String getSearchContentHidden() {
		return searchContentHidden;
	}

	public void setSearchContentHidden(String searchContentHidden) {
		this.searchContentHidden = searchContentHidden;
	}

	public String[] getCheckedDocs() {
		return checkedDocs;
	}

	public void setCheckedDocs(String[] checkedDocs) {
		this.checkedDocs = checkedDocs;
	}

	/**
	 * @param searchModeChecked the searchModeChecked to set
	 */
	public void setSearchModeChecked(String searchModeChecked) {
		this.searchModeChecked = searchModeChecked;
	}

	/**
	 * @return the searchModeChecked
	 */
	public String getSearchModeChecked() {
		return searchModeChecked;
	}

}
