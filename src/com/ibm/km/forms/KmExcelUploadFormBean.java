package com.ibm.km.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
/** 
* @author Anveeksha & Neeraj 
* created for Waiver Matrix Upload
* for viewing Company Wise bill Plan details
**/
public class KmExcelUploadFormBean extends ActionForm {
	
	private FormFile newFile;	
	private String methodName = null;
	private String filePath= "";
	private ArrayList missingPlanRateData = null;
	private ArrayList duplicateBillPlanRates = null;
	private ArrayList duplicateBillPlanRatesPackagId = null;
	private int detailStatus = 0;
	private int errMsg;
	
	public int getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(int errMsg) {
		this.errMsg = errMsg;
	}
	public int getDetailStatus() {
		return detailStatus;
	}
	public void setDetailStatus(int detailStatus) {
		this.detailStatus = detailStatus;
	}
	public ArrayList getMissingPlanRateData() {
		return missingPlanRateData;
	}
	public void setMissingPlanRateData(ArrayList missingPlanRateData) {
		this.missingPlanRateData = missingPlanRateData;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public FormFile getNewFile() {
		return newFile;
	}
	public void setNewFile(FormFile newFile) {
		this.newFile = newFile;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public ArrayList getDuplicateBillPlanRates() {
		return duplicateBillPlanRates;
	}
	public void setDuplicateBillPlanRates(ArrayList duplicateBillPlanRates) {
		this.duplicateBillPlanRates = duplicateBillPlanRates;
	}
	public ArrayList getDuplicateBillPlanRatesPackagId() {
		return duplicateBillPlanRatesPackagId;
	}
	public void setDuplicateBillPlanRatesPackagId(
			ArrayList duplicateBillPlanRatesPackagId) {
		this.duplicateBillPlanRatesPackagId = duplicateBillPlanRatesPackagId;
	}
	
	
	
}
