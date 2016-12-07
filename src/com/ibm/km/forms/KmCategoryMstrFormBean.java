package com.ibm.km.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;



 /** 
	* @author Anil 
	* Created On Tue Jan 15 14:06:56 IST 2008 
	* Form Bean class for table KM_CATEGORY_MSTR 
 
 **/ 
public class KmCategoryMstrFormBean extends ActionForm {


	private String categoryId;

	private String categoryName;

	private String categoryDesc;

	private String status;

	private String circleId;

	private String updatedBy;

	private String createdBy;

	private java.sql.Date createdDt;

	private java.sql.Date updateDt;
	
	private ArrayList circleList;	
	
	private String selectedCircleId;
	
	private String categoryStatus=null;
	
	private ArrayList categoryList=null;
	
	private String methodName;
	
	private FormFile newFile;
	
	private String completeFileName = "";

	/** Creates a dto for the KM_CATEGORY_MSTR table */
	public KmCategoryMstrFormBean() {
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
	* Get categoryName associated with this object.
	* @return categoryName
 **/ 

	public String getCategoryName() {
		return categoryName;
	}

 /** 
	* Set categoryName associated with this object.
	* @param categoryName The categoryName value to set
 **/ 

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

 /** 
	* Get categoryDesc associated with this object.
	* @return categoryDesc
 **/ 

	public String getCategoryDesc() {
		return categoryDesc;
	}

 /** 
	* Set categoryDesc associated with this object.
	* @param categoryDesc The categoryDesc value to set
 **/ 

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
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
	* Get createdDt associated with this object.
	* @return createdDt
 **/ 

	public java.sql.Date getCreatedDt() {
		return createdDt;
	}

 /** 
	* Set createdDt associated with this object.
	* @param createdDt The createdDt value to set
 **/ 

	public void setCreatedDt(java.sql.Date createdDt) {
		this.createdDt = createdDt;
	}

 /** 
	* Get updateDt associated with this object.
	* @return updateDt
 **/ 

	public java.sql.Date getUpdateDt() {
		return updateDt;
	}

 /** 
	* Set updateDt associated with this object.
	* @param updateDt The updateDt value to set
 **/ 

	public void setUpdateDt(java.sql.Date updateDt) {
		this.updateDt = updateDt;
	}

	/**
	 * @return
	 */
	public ArrayList getCircleList() {
		return circleList;
	}

	/**
	 * @param list
	 */
	public void setCircleList(ArrayList list) {
		circleList = list;
	}

	/**
	 * @return
	 */
	public String getSelectedCircleId() {
		return selectedCircleId;
	}

	/**
	 * @param string
	 */
	public void setSelectedCircleId(String string) {
		selectedCircleId= string;
	}

	/**
	 * @return
	 */
	public String getCategoryStatus() {
		return categoryStatus;
	}

	/**
	 * @param string
	 */
	public void setCategoryStatus(String string) {
		categoryStatus = string;
	}

	/**
	 * @return
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param string
	 */
	public void setMethodName(String string) {
		methodName = string;
	}

	/**
	 * @return
	 */
	public ArrayList getCategoryList() {
		return categoryList;
	}

	/**
	 * @param list
	 */
	public void setCategoryList(ArrayList list) {
		categoryList = list;
	}

	/**
	 * @return
	 */
	public String getCompleteFileName() {
		return completeFileName;
	}

	/**
	 * @return
	 */
	public FormFile getNewFile() {
		return newFile;
	}

	/**
	 * @param string
	 */
	public void setCompleteFileName(String string) {
		completeFileName = string;
	}

	/**
	 * @param file
	 */
	public void setNewFile(FormFile file) {
		newFile = file;
	}

}
