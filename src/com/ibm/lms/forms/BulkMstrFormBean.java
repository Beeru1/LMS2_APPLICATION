package com.ibm.lms.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class BulkMstrFormBean extends ActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FormFile newFile;
	private String methodName="";
	//private String uploadType="";
	private String isError = "false";
	private String downloadTemplate = "true";
	private String errLogFilePath="";
	private String msg;
	private String mstrType;
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public FormFile getNewFile() {
		return newFile;
	}

	public void setNewFile(FormFile newFile) {
		this.newFile = newFile;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getIsError() {
		return isError;
	}

	public void setIsError(String isError) {
		this.isError = isError;
	}

	public String getErrLogFilePath() {
		return errLogFilePath;
	}

	public void setErrLogFilePath(String errLogFilePath) {
		this.errLogFilePath = errLogFilePath;
	}

	public String getDownloadTemplate() {
		return downloadTemplate;
	}

	public void setDownloadTemplate(String downloadTemplate) {
		this.downloadTemplate = downloadTemplate;
	}

	public void setMstrType(String mstrType) {
		this.mstrType = mstrType;
	}

	public String getMstrType() {
		return mstrType;
	}


}
