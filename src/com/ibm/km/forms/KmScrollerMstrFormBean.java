package com.ibm.km.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 
 * @version 	1.0
 * @author		Anil
 */
public class KmScrollerMstrFormBean extends ActionForm {

	private String messageId;
	
	private String message="";
	
	private String circleId;
	
	
	private String startDate=null;
	
	private String endDate=null;
	
	private ArrayList scrollerList=null;
	
	private String viewEditFlag=null;
	
	private String circleList=null;
	private String kmActorId="";
	private String scrollerCount="";
	
	private ArrayList elementList=null;
	private String elementId="";
	private String elementName="";
	private String elementDesc="";
	private String parentId="";
	private String elementLevel="";
	private String panStatus="N";
	private String status="A";
	private String createdBy="";
	private String createdDt=null;
	private String updatedDt=null;
	private String updatedBy="";
	private String initialLevel;
	private String initialLevelName;
	private FormFile newFile;
	private String initialSelectBox;
	private String levelCount;
	private String elementFolderPath;
	private String initStatus="";
	private String elementPath="";
	private String methodName="";
	private String elementStringPath="";
	private String selectedElementId;
	private String oldElementName="";
	private String token="";
	private String circleElementId="";
	
	public String getCircleElementId() {
		return circleElementId;
	}
	public void setCircleElementId(String circleElementId) {
		this.circleElementId = circleElementId;
	}
	public ArrayList getElementList() {
		return elementList;
	}
	public void setElementList(ArrayList elementList) {
		this.elementList = elementList;
	}
	public String getElementId() {
		return elementId;
	}
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	public String getElementName() {
		return elementName;
	}
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	public String getElementDesc() {
		return elementDesc;
	}
	public void setElementDesc(String elementDesc) {
		this.elementDesc = elementDesc;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getElementLevel() {
		return elementLevel;
	}
	public void setElementLevel(String elementLevel) {
		this.elementLevel = elementLevel;
	}
	public String getPanStatus() {
		return panStatus;
	}
	public void setPanStatus(String panStatus) {
		this.panStatus = panStatus;
	}
	public String getInitialLevel() {
		return initialLevel;
	}
	public void setInitialLevel(String initialLevel) {
		this.initialLevel = initialLevel;
	}
	public String getInitialLevelName() {
		return initialLevelName;
	}
	public void setInitialLevelName(String initialLevelName) {
		this.initialLevelName = initialLevelName;
	}
	public FormFile getNewFile() {
		return newFile;
	}
	public void setNewFile(FormFile newFile) {
		this.newFile = newFile;
	}
	public String getInitialSelectBox() {
		return initialSelectBox;
	}
	public void setInitialSelectBox(String initialSelectBox) {
		this.initialSelectBox = initialSelectBox;
	}
	public String getLevelCount() {
		return levelCount;
	}
	public void setLevelCount(String levelCount) {
		this.levelCount = levelCount;
	}
	public String getElementFolderPath() {
		return elementFolderPath;
	}
	public void setElementFolderPath(String elementFolderPath) {
		this.elementFolderPath = elementFolderPath;
	}
	public String getElementPath() {
		return elementPath;
	}
	public void setElementPath(String elementPath) {
		this.elementPath = elementPath;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getElementStringPath() {
		return elementStringPath;
	}
	public void setElementStringPath(String elementStringPath) {
		this.elementStringPath = elementStringPath;
	}
	public String getSelectedElementId() {
		return selectedElementId;
	}
	public void setSelectedElementId(String selectedElementId) {
		this.selectedElementId = selectedElementId;
	}
	public String getOldElementName() {
		return oldElementName;
	}
	public void setOldElementName(String oldElementName) {
		this.oldElementName = oldElementName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getInitStatus() {
		return initStatus;
	}
	public void setInitStatus(String initStatus) {
		this.initStatus = initStatus;
	}
	public String getScrollerCount() {
		return scrollerCount;
	}
	public void setScrollerCount(String scrollerCount) {
		this.scrollerCount = scrollerCount;
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
	public String getEndDate() {
		return endDate;
	}



	/**
	 * @return
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param string
	 */
	public void setEndDate(String string) {
		endDate = string;
	}



	/**
	 * @param string
	 */
	public void setStartDate(String string) {
		startDate = string;
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
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @return
	 */
	public String getCreatedDt() {
		return createdDt;
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
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
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
	public String getUpdatedDt() {
		return updatedDt;
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
	public void setCreatedBy(String string) {
		createdBy = string;
	}

	/**
	 * @param string
	 */
	public void setCreatedDt(String string) {
		createdDt = string;
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
	public void setMessageId(String string) {
		messageId = string;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string) {
		status = string;
	}

	/**
	 * @param string
	 */
	public void setUpdatedBy(String string) {
		updatedBy = string;
	}

	/**
	 * @param string
	 */
	public void setUpdatedDt(String string) {
		updatedDt = string;
	}

	/**
	 * @return
	 */
	public ArrayList getScrollerList() {
		return scrollerList;
	}

	/**
	 * @param list
	 */
	public void setScrollerList(ArrayList list) {
		scrollerList = list;
	}

	/**
	 * @return
	 */
	public String getViewEditFlag() {
		return viewEditFlag;
	}

	/**
	 * @param string
	 */
	public void setViewEditFlag(String string) {
		viewEditFlag = string;
	}

	/**
	 * @return
	 */
	public String getCircleList() {
		return circleList;
	}

	/**
	 * @param string
	 */
	public void setCircleList(String string) {
		circleList = string;
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

}
