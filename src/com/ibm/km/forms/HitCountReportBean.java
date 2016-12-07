package com.ibm.km.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

/**
 * Form bean for a Struts application.
 * @version 	1.0
 * @author
 */
public class HitCountReportBean extends ActionForm {


	private String elementId="";
	private String parentId="";
	private String initialSelectBox="";
	private String date="";
	private String documentPath=null;
	private String initialLevel=null;
	private String initialLevelName=null;
	private String methodName = null;
	private ArrayList alHitReport=null;
	private String elementPath="";
	private String parentId1="";
	public String getElementPath() {
		return elementPath;
	}

	public void setElementPath(String elementPath) {
		this.elementPath = elementPath;
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
	public String getElementId() {
		return elementId;
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
	public String getMethodName() {
		return methodName;
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
	public void setElementId(String string) {
		elementId = string;
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
	public void setMethodName(String string) {
		methodName = string;
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


	/**
	 * @return
	 */
	public ArrayList getAlHitReport() {
		return alHitReport;
	}

	/**
	 * @param list
	 */
	public void setAlHitReport(ArrayList list) {
		alHitReport = list;
	}

	public String getParentId1() {
		return parentId1;
	}

	public void setParentId1(String parentId1) {
		this.parentId1 = parentId1;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
