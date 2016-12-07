/*
 * Created on Feb 11, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dto;

import java.util.ArrayList;

/**
 * @author Vipin 11/02/2008
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AddFileDTO {
	
	private ArrayList categoryList;
	private ArrayList subcategoryList;
	private String docName;
	private String docDisplayName;
	private String docDesc;
	private String docKeyword;
	private String approvalStatus;
	private String categoryId;
	private String subCategoryId;
	private String circleId;
	private String userId;
	
	private String parentId;
	private String documentPath;
	private String elementLevelId;
	
	

	/**
	 * @return ArrayList
	 */
	public ArrayList getCategoryList() {
		return categoryList;
	}

	/**
	 * @return ArrayList
	 */
	public ArrayList getSubcategoryList() {
		return subcategoryList;
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
	public void setSubcategoryList(ArrayList list) {
		subcategoryList = list;
	}

	/**
	 * @return
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @return
	 */
	public String getCircleId() {
		return circleId;
	}

	/**
	 * @return
	 */
	public String getDocDisplayName() {
		return docDisplayName;
	}

	/**
	 * @return
	 */
	public String getDocKeyword() {
		return docKeyword;
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
	public String getSubCategoryId() {
		return subCategoryId;
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
	public void setCategoryId(String string) {
		categoryId = string;
	}

	/**
	 * @param string
	 */
	public void setCircleId(String string) {
		circleId = string;
	}

	/**
	 * @param string
	 */
	public void setDocDisplayName(String string) {
		docDisplayName = string;
	}

	/**
	 * @param string
	 */
	public void setDocKeyword(String string) {
		docKeyword = string;
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
	public void setSubCategoryId(String string) {
		subCategoryId = string;
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
	public String getDocDesc() {
		return docDesc;
	}

	/**
	 * @param string
	 */
	public void setDocDesc(String string) {
		docDesc = string;
	}

	/**
	 * @return
	 */
	public String getApprovalStatus() {
		return approvalStatus;
	}

	/**
	 * @param string
	 */
	public void setApprovalStatus(String string) {
		approvalStatus = string;
	}

	/**
	 * @return
	 */
	public String getDocumentPath() {
		return documentPath;
	}

	/**
	 * @return
	 */
	public String getElementLevelId() {
		return elementLevelId;
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
	public void setDocumentPath(String string) {
		documentPath = string;
	}

	/**
	 * @param string
	 */
	public void setElementLevelId(String string) {
		elementLevelId = string;
	}

	/**
	 * @param string
	 */
	public void setParentId(String string) {
		parentId = string;
	}

}
