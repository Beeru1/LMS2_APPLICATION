package com.ibm.km.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 
 * @version 	1.0
 * @author		Aditya
 */
public class KmAlertMstrFormBean extends ActionForm {

	private String messageId;
	
	private String message="";
	
	private String circleId;
	
	private String createdDt;
	
	private String createdBy;
	
	private String updatedDt;
	
	private String updatedBy;
	
	private String startDate=null;
	
	private String endDate=null;
	
	private String status;
	
	private ArrayList alertList=null;
	private String kmActorId="";
	
	private String selectedCircleId="";
	private ArrayList circleList=null;
	
	private String alertCount="";
	private String methodName="";
	private String documentPath="";
	
	
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
	public ArrayList getAlertList() {
		return alertList;
	}

	/**
	 * @param list
	 */
	public void setAlertList(ArrayList list) {
		alertList = list;
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
	public String getSelectedCircleId() {
		return selectedCircleId;
	}

	/**
	 * @param string
	 */
	public void setSelectedCircleId(String string) {
		selectedCircleId = string;
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
	public String getAlertCount() {
		return alertCount;
	}

	/**
	 * @param string
	 */
	public void setAlertCount(String string) {
		alertCount = string;
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
	public String getDocumentPath() {
		return documentPath;
	}

	/**
	 * @param string
	 */
	public void setDocumentPath(String string) {
		documentPath = string;
	}

}
