package com.ibm.km.forms;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/*
 * @version 	1.0
 * @author
 */
public class KmElementMstrFormBean extends ActionForm {

	private ArrayList elementList=null;
	private String elementId="";
	private String elementName="";
	private String elementDesc="";
	private String parentId="";
	private String elementLevel="";
	private String panStatus="N";
	private String status="A";
	private String createdBy="";
	private Timestamp createdDt=null;
	private Timestamp updatedDt=null;
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
	private String[] createMultiple = null;
	private boolean displayCircle = false;
	
		
public boolean isDisplayCircle() {
		return displayCircle;
	}

	public void setDisplayCircle(boolean displayCircle) {
		this.displayCircle = displayCircle;
	}

public String[] getCreateMultiple() {
		return createMultiple;
	}

	public void setCreateMultiple(String[] createMultiple) {
		this.createMultiple = createMultiple;
	}

public void reset(ActionMapping mapping, HttpServletRequest request) {
	elementId="";
	elementName="";
	elementDesc="";
	parentId="";
	elementLevel="";
	panStatus="N";
	status="A";
	createdBy="";
	initialSelectBox="";
	elementFolderPath="";
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
	public String getElementDesc() {
		return elementDesc;
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
	public String getElementLevel() {
		return elementLevel;
	}

	/**
	 * @return
	 */
	public String getElementName() {
		return elementName;
	}

	/**
	 * @return
	 */
	public String getPanStatus() {
		return panStatus;
	}

	/**
	 * @return
	 */
	public String getParentId() {
		return parentId;
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
	public Timestamp getUpdatedDt() {
		return updatedDt;
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
	public void setElementDesc(String string) {
		elementDesc = string;
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
	public void setElementLevel(String string) {
		elementLevel = string;
	}

	/**
	 * @param string
	 */
	public void setElementName(String string) {
		elementName = string;
	}

	/**
	 * @param string
	 */
	public void setPanStatus(String string) {
		panStatus = string;
	}

	/**
	 * @param string
	 */
	public void setParentId(String string) {
		parentId = string;
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
	 * @param timestamp
	 */
	public void setUpdatedDt(Timestamp timestamp) {
		updatedDt = timestamp;
	}

	/**
	 * @return
	 */
	public ArrayList getElementList() {
		return elementList;
	}

	/**
	 * @param list
	 */
	public void setElementList(ArrayList list) {
		elementList = list;
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
	 * 
	 */
	public FormFile getNewFile() {
		return newFile;
	}
	/**
	 * @param file
	 */
	public void setNewFile(FormFile file) {
		newFile = file;
	}	

	/**
	 * @return
	 */
	public String getInitialSelectBox() {
		return initialSelectBox;
	}

	/**
	 * @param string
	 */
	public void setInitialSelectBox(String string) {
		initialSelectBox = string;
	}

	/**
	 * @return
	 */
	public String getLevelCount() {
		return levelCount;
	}

	/**
	 * @param string
	 */
	public void setLevelCount(String string) {
		levelCount = string;
	}


	/**
	 * @return
	 */
	public String getElementFolderPath() {
		return elementFolderPath;
	}

	/**
	 * @param string
	 */
	public void setElementFolderPath(String string) {
		elementFolderPath = string;
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
	public String getElementStringPath() {
		return elementStringPath;
	}

	/**
	 * @param string
	 */
	public void setElementStringPath(String string) {
		elementStringPath = string;
	}

	

	/**
	 * @return
	 */
	public String getSelectedElementId() {
		return selectedElementId;
	}

	/**
	 * @param string
	 */
	public void setSelectedElementId(String string) {
		selectedElementId = string;
	}

	/**
	 * @return
	 */
	public String getOldElementName() {
		return oldElementName;
	}

	/**
	 * @param string
	 */
	public void setOldElementName(String string) {
		oldElementName = string;
	}

	/**
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param string
	 */
	public void setToken(String string) {
		token = string;
	}

}
