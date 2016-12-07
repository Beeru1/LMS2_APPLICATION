package com.ibm.km.dto;

import java.io.Serializable;
import java.sql.Date;

import org.apache.struts.upload.FormFile;


 /** 
	* @author Anil 
	* Created On Tue Jan 15 14:06:56 IST 2008 
	* Data Trasnfer Object class for table KM_CATEGORY_MSTR 
 
 **/ 
public class KmCategoryMstr implements Serializable {


	private int categoryId;

	private String categoryName;

	private String categoryDesc;

	private String status;

	private int circleId;

	private int updatedBy;

	private int createdBy;

	private java.sql.Date createdDt;

	private java.sql.Date updateDt;
	
	private FormFile file;

	/** Creates a dto for the KM_CATEGORY_MSTR table */
	public KmCategoryMstr() {
		categoryId=0;
		categoryName=null;
		categoryDesc=null;
		status="A";
		circleId=0;
		updatedBy=0;
		createdBy=0;
		updateDt=null;
		createdDt=null;

	}


 /** 
	* Get categoryId associated with this object.
	* @return categoryId
 **/ 

	public int getCategoryId() {
		return categoryId;
	}

 /** 
	* Set categoryId associated with this object.
	* @param categoryId The categoryId value to set
 **/ 

	public void setCategoryId(int categoryId) {
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

	public int getCircleId() {
		return circleId;
	}

 /** 
	* Set circleId associated with this object.
	* @param circleId The circleId value to set
 **/ 

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

 /** 
	* Get updatedBy associated with this object.
	* @return updatedBy
 **/ 

	public int getUpdatedBy() {
		return updatedBy;
	}

 /** 
	* Set updatedBy associated with this object.
	* @param updatedBy The updatedBy value to set
 **/ 

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

 /** 
	* Get createdBy associated with this object.
	* @return createdBy
 **/ 

	public int getCreatedBy() {
		return createdBy;
	}

 /** 
	* Set createdBy associated with this object.
	* @param createdBy The createdBy value to set
 **/ 

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

 /** 
	* Get createdDt associated with this object.
	* @return createdDt
 **/ 

	public Date getCreatedDt() {
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
 * @param string
 */


	/**
	 * @return
	 */
	public FormFile getFile() {
		return file;
	}

	/**
	 * @param file
	 */
	public void setFile(FormFile file) {
		this.file = file;
	}

}
