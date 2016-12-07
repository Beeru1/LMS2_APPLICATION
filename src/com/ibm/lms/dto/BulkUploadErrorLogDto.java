package com.ibm.lms.dto;

public class BulkUploadErrorLogDto {
	
	private int rowNum = 0;
	private String errMsg="";
	
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
 }
