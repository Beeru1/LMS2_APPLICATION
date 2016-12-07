package com.ibm.km.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * Form bean for a Struts application.
 * Users may access 1 field on this form:
 * <ul>
 * <li><%="briefingDetails[" + ((Integer)(pageContext - [your comment here]
 * </ul>
 * @version 	1.0
 * @author
 */
public class BulkUserUploadFormBean extends ActionForm {

	private FormFile newFile;
	private List circleList=null;
	private String methodName="";
	private String circleId="";
	private String loginActorId="";
	private String filePath="";
	private String uploadType="";
	private String date="";
	private String initStatus="";
	private String parentId="";
	private String initialLevel;
	private String initialSelectBox;
	private String circleElementId="";
	
	
	public String getInitStatus() {
		return initStatus;
	}
	public void setInitStatus(String initStatus) {
		this.initStatus = initStatus;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {

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
	public List getCircleList() {
		return circleList;
	}

	/**
	 * @param list
	 */
	public void setCircleList(List list) {
		circleList = list;
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
	public String getCircleId() {
		return circleId;
	}

	/**
	 * @param string
	 */
	public void setCircleId(String string) {
		circleId = string;
	}

	/**
	 * @return
	 */
	public String getLoginActorId() {
		return loginActorId;
	}

	/**
	 * @param string
	 */
	public void setLoginActorId(String string) {
		loginActorId = string;
	}

	/**
	 * @return
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param string
	 */
	public void setFilePath(String string) {
		filePath = string;
	}

	/**
	 * @return
	 */
	public String getUploadType() {
		return uploadType;
	}

	/**
	 * @param string
	 */
	public void setUploadType(String string) {
		uploadType = string;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getInitialLevel() {
		return initialLevel;
	}
	public void setInitialLevel(String initialLevel) {
		this.initialLevel = initialLevel;
	}
	public String getInitialSelectBox() {
		return initialSelectBox;
	}
	public void setInitialSelectBox(String initialSelectBox) {
		this.initialSelectBox = initialSelectBox;
	}
	public String getCircleElementId() {
		return circleElementId;
	}
	public void setCircleElementId(String circleElementId) {
		this.circleElementId = circleElementId;
	}
	
	

}
