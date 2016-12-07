/*
 * Created on Feb 15, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

/**
 * @author Vipin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KmApproveFileFormBean extends ActionForm{
	
	//private String status = null;
	private String fromDate = null;
	private String toDate = null;
	private String methodName = null;
	private String completeFileName = null;
	private ArrayList fileList = null;
	private String[] approveFileList;
	private String[] commentList;
	private String filePath = "";
	private String showFile = "false";
	private String displayFilePath = null;
	private String status="";

	/**
	 * @return
	 */
	public String[] getApproveFileList() {
		return approveFileList;
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
	public ArrayList getFileList() {
		return fileList;
	}

	/**
	 * @return
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param strings
	 */
	public void setApproveFileList(String[] strings) {

		approveFileList = strings;
	}

	/**
	 *@param strings
	 */
	public void setCompleteFileName(String string) {
		completeFileName = string;
	}

	/**
	 * @param list
	 */
	public void setFileList(ArrayList list) {
		fileList = list;
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
	public String[] getCommentList() {
		return commentList;
	}

	/**
	 * @param strings
	 */
	public void setCommentList(String[] strings) {
		commentList = strings;
	}

	
	
	/**
	 * @return
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @return
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param string
	 */
	public void setFromDate(String string) {
		fromDate = string;
	}

	/**
	 * @param string
	 */
	public void setToDate(String string) {
		toDate = string;
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
	public String getShowFile() {
		return showFile;
	}

	/**
	 * @param string
	 */
	public void setShowFile(String string) {
		showFile = string;
	}

	/**
	 * @return
	 */
	public String getDisplayFilePath() {
		return displayFilePath;
	}

	/**
	 * @param string
	 */
	public void setDisplayFilePath(String string) {
		displayFilePath = string;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string) {
		status = string;
	}

}
