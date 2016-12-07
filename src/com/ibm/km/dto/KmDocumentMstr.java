package com.ibm.km.dto;

import java.io.Serializable;
import java.sql.Timestamp;


 /** 
	* @author Naman 
	* Created On Mon Jan 14 13:12:17 IST 2008 
	* Data Trasnfer Object class for table KM_DOCUMENT_MSTR 
 
 **/ 
public class KmDocumentMstr implements Serializable {

	private String documentViewer="";
	private String documentPath;
    
	private String documentId;

	private String documentGroupId;

	private String documentName;

	private String documentDisplayName;

	private String documentDesc="";

	private String subCategoryId;
    
	private String subCategoryName;

	private String categoryId;
    
	private String categoryName;

	private String circleId;
    
	private String circleName;

	private String numberOfHits;

	private String status;

	private String approvalStatus;

	private String uploadedBy;

	private Timestamp uploadedDt;
    
	private String uploadedDate;

	private String updatedBy;

	private java.sql.Timestamp updatedDt;

	private Timestamp approvalRejectionDt;
    
	private String approvalRejectionDate;

	private String approvalRejectionBy;

	private Timestamp publishingStartDt;

	private Timestamp publishingEndDt;
	
	private String publishingStartDate;

	private String publishingEndDate;
    
	private String parentId="";
    
	private String keyword="";
    
	private String elementName="";
    
	private String elementId="";
    
	private String path="";
    
	private String documentStringPath="";
	
	private String publishDt="";
	private String publishEndDt="";
	private int versionCount=0;
	
	private String oldFileName="";
	private String fileName="";
	private String userId="";
	private String docName="";
	private String root="";
	private String searchType="";
	
	private int docType = -1;
	
	private boolean isElementCopyActivity = false;
	
	private KmCsrLatestUpdatesDto kmCsrLatestUpdatesDto = null;

	
	/**
	 * @return the kmCsrLatestUpdatesDto
	 */
	public KmCsrLatestUpdatesDto getKmCsrLatestUpdatesDto() {
		return kmCsrLatestUpdatesDto;
	}



	/**
	 * @param kmCsrLatestUpdatesDto the kmCsrLatestUpdatesDto to set
	 */
	public void setKmCsrLatestUpdatesDto(KmCsrLatestUpdatesDto kmCsrLatestUpdatesDto) {
		this.kmCsrLatestUpdatesDto = kmCsrLatestUpdatesDto;
	}



	/** Creates a dto for the KM_DOCUMENT_MSTR table */
	public KmDocumentMstr() {
	}


	
   public String getDocumentViewer() {
		return documentViewer;
	}



	public void setDocumentViewer(String documentViewer) {
		this.documentViewer = documentViewer;
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

	public Timestamp getApprovalRejectionDt() {
		return approvalRejectionDt;
	}

 /** 
	* Set approvalRejectionDt associated with this object.
	* @param approvalRejectionDt The approvalRejectionDt value to set
 **/ 

	public void setApprovalRejectionDt(Timestamp approvalRejectionDt) {
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

	public Timestamp getPublishingStartDt() {
		return publishingStartDt;
	}

 /** 
	* Set publishingStartDt associated with this object.
	* @param publishingStartDt The publishingStartDt value to set
 **/ 

	public void setPublishingStartDt(Timestamp publishingStartDt) {
		this.publishingStartDt = publishingStartDt;
	}

 /** 
	* Get publishingEndDt associated with this object.
	* @return publishingEndDt
 **/ 

	public Timestamp getPublishingEndDt() {
		return publishingEndDt;
	}

 /** 
	* Set publishingEndDt associated with this object.
	* @param publishingEndDt The publishingEndDt value to set
 **/ 

	public void setPublishingEndDt(Timestamp publishingEndDt) {
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
	public String getDocumentPath() {
		return documentPath;
	}

	/**
	 * @param string
	 */
	public void setDocumentPath(String string) {
		documentPath = string;
	}
	/**
	 * @return
	 */
	public String getKeyword() {
		return keyword;
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
	public void setKeyword(String string) {
		keyword = string;
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
	public String getElementId() {
		return elementId;
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
	public void setElementId(String string) {
		elementId = string;
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
	public String getPath() {
		return path;
	}

	/**
	 * @param string
	 */
	public void setPath(String string) {
		path = string;
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
	public String getApprovalRejectionDate() {
		return approvalRejectionDate;
	}

	/**
	 * @return
	 */
	public String getUploadedDate() {
		return uploadedDate;
	}

	/**
	 * @param string
	 */
	public void setApprovalRejectionDate(String string) {
		approvalRejectionDate = string;
	}

	/**
	 * @param string
	 */
	public void setUploadedDate(String string) {
		uploadedDate = string;
	}


	/**
	 * @return
	 */
	public String getPublishingEndDate() {
		return publishingEndDate;
	}

	/**
	 * @return
	 */
	public String getPublishingStartDate() {
		return publishingStartDate;
	}

	/**
	 * @param string
	 */
	public void setPublishingEndDate(String string) {
		publishingEndDate = string;
	}

	/**
	 * @param string
	 */
	public void setPublishingStartDate(String string) {
		publishingStartDate = string;
	}

	/**
	 * @return
	 */
	public String getPublishDt() {
		return publishDt;
	}

	/**
	 * @param string
	 */
	public void setPublishDt(String string) {
		publishDt = string;
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
	public int getVersionCount() {
		return versionCount;
	}

	/**
	 * @param i
	 */
	public void setVersionCount(int i) {
		versionCount = i;
	}

	/**
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return
	 */
	public String getOldFileName() {
		return oldFileName;
	}

	/**
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param string
	 */
	public void setFileName(String string) {
		fileName = string;
	}

	/**
	 * @param string
	 */
	public void setOldFileName(String string) {
		oldFileName = string;
	}

	/**
	 * @param string
	 */
	public void setUserId(String string) {
		userId = string;
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

	/**
	 * @return
	 */
	public String getRoot() {
		return root;
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
	public void setRoot(String string) {
		root = string;
	}

	/**
	 * @param string
	 */
	public void setSearchType(String string) {
		searchType = string;
	}


	public void setDocType(int docType) {
		this.docType = docType;
	}


	public int getDocType() {
		return docType;
	}


	public boolean isElementCopyActivity() {
		return isElementCopyActivity;
	}


	public void setElementCopyActivity(boolean isElementCopyActivity) {
		this.isElementCopyActivity = isElementCopyActivity;
	}
	

}
