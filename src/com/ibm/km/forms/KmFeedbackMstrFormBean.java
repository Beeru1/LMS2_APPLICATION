package com.ibm.km.forms;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/* @version 	1.0
 * @author	Anil
 */
public class KmFeedbackMstrFormBean extends ActionForm {

	private String feedbackId="";
	private String comment="";
	private Timestamp createdDt=null;
	private String createdBy="";
	private String circleId="";
	private String categoryId="";
	private String categoryName="";
	private String elementId="";
	private ArrayList categoryList=null;
	private String subCategoryId="";
	private String subCategoryName="";
	private ArrayList subCategoryList=null;
	private Timestamp updatedDt=null;
	private String updatedBy="";
	private String readStatus="N";
	private String methodName;
	private ArrayList feedbackList=null;
	private String[] readFeedbackList;
	private String initialLevel;
	private String initialLevelName;
	private String parentId="";
	private String initialSelectBox;
	private String levelCount;
	private String elementFolderPath;
	private String elementLevel="";
	private String initStatus="true";
	private String[] feedbackStatus;
	private String startDate="";
	private String endDate="";
	private ArrayList feedbackListMy=null;
	private ArrayList feedbackListAll=null;
public ArrayList getFeedbackListMy() {
		return feedbackListMy;
	}
	public void setFeedbackListMy(ArrayList feedbackListMy) {
		this.feedbackListMy = feedbackListMy;
	}
	public ArrayList getFeedbackListAll() {
		return feedbackListAll;
	}
	public void setFeedbackListAll(ArrayList feedbackListAll) {
		this.feedbackListAll = feedbackListAll;
	}
	//	Added by Atul for Feedback Combo box
	  private String[] feedbackArray;
	  // Ended by Atul 
	private String[] feedbackResp=null;  
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
		initialSelectBox="";
		
	}
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		// Validate the fields in your form, adding
		// adding each error to this.errors as found, e.g.

		// if ((field == null) || (field.length() == 0)) {
		//   errors.add("field", new org.apache.struts.action.ActionError("error.field.required"));
		// }
		return errors;

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
	public ArrayList getCategoryList() {
		return categoryList;
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
	public String getCircleId() {
		return circleId;
	}

	/**
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @return
	 */
	public Timestamp getCreatedDt() {
		return createdDt;
	}

	/**
	 * @return
	 */
	public String getFeedbackId() {
		return feedbackId;
	}

	/**
	 * @return
	 */
	public String getReadStatus() {
		return readStatus;
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
	public ArrayList getSubCategoryList() {
		return subCategoryList;
	}

	/**
	 * @return
	 */
	public String getSubCategoryName() {
		return subCategoryName;
	}

	/**
	 * @return
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @return
	 */
	public Timestamp getUpdatedDt() {
		return updatedDt;
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
	public void setCategoryList(ArrayList string) {
		categoryList = string;
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
	public void setCircleId(String string) {
		circleId = string;
	}

	/**
	 * @param string
	 */
	public void setComment(String string) {
		comment = string;
	}

	/**
	 * @param string
	 */
	public void setCreatedBy(String string) {
		createdBy = string;
	}

	/**
	 * @param timestamp
	 */
	public void setCreatedDt(Timestamp timestamp) {
		createdDt = timestamp;
	}

	/**
	 * @param string
	 */
	public void setFeedbackId(String string) {
		feedbackId = string;
	}

	/**
	 * @param string
	 */
	public void setReadStatus(String string) {
		readStatus = string;
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
	public void setSubCategoryList(ArrayList string) {
		subCategoryList = string;
	}

	/**
	 * @param string
	 */
	public void setSubCategoryName(String string) {
		subCategoryName = string;
	}

	/**
	 * @param string
	 */
	public void setUpdatedBy(String string) {
		updatedBy = string;
	}

	/**
	 * @param timestamp
	 */
	public void setUpdatedDt(Timestamp timestamp) {
		updatedDt = timestamp;
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
	public ArrayList getFeedbackList() {
		return feedbackList;
	}

	
	/**
	 * @param list
	 */
	public void setFeedbackList(ArrayList list) {
		feedbackList = list;
	}

	
	
	/**
	 * @return
	 */
	public String[] getReadFeedbackList() {
		return readFeedbackList;
	}

	/**
	 * @param strings
	 */
	public void setReadFeedbackList(String[] strings) {
		readFeedbackList = strings;
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
	public String getElementLevel() {
		return elementLevel;
	}

	/**
	 * @param string
	 */
	public void setElementLevel(String string) {
		elementLevel = string;
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
	public String getFeedbackStatus(int index) {
		return feedbackStatus[index];
	}

	/**
	 * @param strings
	 */
	public void setFeedbackStatus(String string,int index) {
		feedbackStatus[index] = string;
	}

/**
 * @return
 */
public String[] getFeedbackArray() {
	return feedbackArray;
}

	/**
	 * @return
	 */
	public String[] getFeedbackStatus() {
		return feedbackStatus;
	}

/**
 * @param strings
 */
public void setFeedbackArray(String[] strings) {
	feedbackArray = strings;
}

	/**
	 * @param strings
	 */
	public void setFeedbackStatus(String[] strings) {
		feedbackStatus = strings;
	}

	

	/**
	 * @return
	 */
	public String[] getFeedbackResp() {
		return feedbackResp;
	}

	/**
	 * @param strings
	 */
	public void setFeedbackResp(String[] strings) {
		feedbackResp = strings;
	}
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

}
