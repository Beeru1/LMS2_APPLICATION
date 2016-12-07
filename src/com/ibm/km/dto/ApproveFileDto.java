/*
 * Created on Feb 15, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dto;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author Pramod
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ApproveFileDto {
	
	private String documentId;
	private String docName;
	private String docCompletePath;
	private String categoryName;
	private String subCategoryName;
	private String comment="" ;
	private String userName="";
	private String docStringPath="";
	private Date uploadedDt;
	private Timestamp uploadedDate;
	private Timestamp escalationTime;
	private int escalationCount=0;
	private String elementId;
	private String approver="";
	private String uploadedBy="";
	private String uploadedUserName="";
	
	
	public String getUploadedUserName() {
		return uploadedUserName;
	}

	public void setUploadedUserName(String uploadedUserName) {
		this.uploadedUserName = uploadedUserName;
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
	public String getDocCompletePath() {
		return docCompletePath;
	}

	/**
	 * @return
	 */
	public String getDocName() {
		return docName;
	}

	/**
	 * @return
	 */
	public String getDocumentId() {
		return documentId;
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
	public void setDocCompletePath(String string) {
		docCompletePath = string;
	}

	/**
	 * @param string
	 */
	public void setDocName(String string) {
		docName = string;
	}

	/**
	 * @param string
	 */
	public void setDocumentId(String string) {
		documentId = string;
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
	public String getComment() {
		return comment;
	}

	/**
	 * @param strings
	 */
	public void setComment(String strings) {
		comment = strings;
	}

	/**
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param string
	 */
	public void setUserName(String string) {
		userName = string;
	}

	/**
	 * @return
	 */
	public String getDocStringPath() {
		return docStringPath;
	}

	/**
	 * @param string
	 */
	public void setDocStringPath(String string) {
		docStringPath = string;
	}

	/**
	 * @return
	 */
	public Date getUploadedDt() {
		return uploadedDt;
	}

	/**
	 * @param string
	 */
	public void setUploadedDt(Date string) {
		uploadedDt = string;
	}

	/**
	 * @return
	 */
	public Timestamp getUploadedDate() {
		return uploadedDate;
	}

	/**
	 * @param timestamp
	 */
	public void setUploadedDate(Timestamp timestamp) {
		uploadedDate = timestamp;
	}

	/**
	 * @return
	 */
	public Timestamp getEscalationTime() {
		return escalationTime;
	}

	/**
	 * @param timestamp
	 */
	public void setEscalationTime(Timestamp timestamp) {
		escalationTime = timestamp;
	}

	/**
	 * @return
	 */
	public int getEscalationCount() {
		return escalationCount;
	}

	/**
	 * @param i
	 */
	public void setEscalationCount(int i) {
		escalationCount = i;
	}

	/**
	 * @return
	 */
	public String getElementId() {
		return elementId;
	}

	/**
	 * @param string
	 */
	public void setElementId(String string) {
		elementId = string;
	}

	/**
	 * @return
	 */
	public String getApprover() {
		return approver;
	}

	/**
	 * @param string
	 */
	public void setApprover(String string) {
		approver = string;
	}

	/**
	 * @return
	 */
	public String getUploadedBy() {
		return uploadedBy;
	}

	/**
	 * @param string
	 */
	public void setUploadedBy(String string) {
		uploadedBy = string;
	}

}
