package com.ibm.lms.forms;


import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class UserMasterDownloadFormBean extends ActionForm {
	
	
	/**
	 * 
	 */
	
	private String isError = "false";
	private String downloadTemplate = "true";
	private String methodName="";
	private String msg;
	private FormFile newFile;

	private int productLobID;
	private ArrayList lobsNameList = null;
	
	private String productlobName="";
private int selectedproductlobId=0;
	
	
	private int circleId;	
	private int circleMstrId ;	
	private ArrayList circleTypeList = null;
	private int selectedCircleId=0;	
	private String circleName="";
	
	

	 private String kmActorId=null;
	 private int selectedActorId;
	 private ArrayList actorList = null;
	 
	
	

	
	
	
	
	public ArrayList getCircleTypeList() {
		return circleTypeList;
	}
	public void setCircleTypeList(ArrayList circleTypeList) {
		this.circleTypeList = circleTypeList;
	}
	public String getDownloadTemplate() {
		return downloadTemplate;
	}
	public void setDownloadTemplate(String downloadTemplate) {
		this.downloadTemplate = downloadTemplate;
	}
	public String getIsError() {
		return isError;
	}
	public void setIsError(String isError) {
		this.isError = isError;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
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
	public int getCircleId() {
		return circleId;
	}
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}
	public int getSelectedCircleId() {
		return selectedCircleId;
	}
	public void setSelectedCircleId(int selectedCircleId) {
		this.selectedCircleId = selectedCircleId;
	}
	public ArrayList getLobsNameList() {
		return lobsNameList;
	}
	public void setLobsNameList(ArrayList lobsNameList) {
		this.lobsNameList = lobsNameList;
	}
	
	public String getProductlobName() {
		return productlobName;
	}
	public void setProductlobName(String productlobName) {
		this.productlobName = productlobName;
	}
	/*public int getSelectedproductlobId() {
		return selectedproductlobId;
	}
	public void setSelectedproductlobId(int selectedproductlobId) {
		this.selectedproductlobId = selectedproductlobId;
	}*/
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public ArrayList getActorList() {
		return actorList;
	}
	public void setActorList(ArrayList actorList) {
		this.actorList = actorList;
	}
	public String getKmActorId() {
		return kmActorId;
	}
	public void setKmActorId(String kmActorId) {
		this.kmActorId = kmActorId;
	}
	public int getSelectedActorId() {
		return selectedActorId;
	}
	public void setSelectedActorId(int selectedActorId) {
		this.selectedActorId = selectedActorId;
	}
	public int getCircleMstrId() {
		return circleMstrId;
	}
	public void setCircleMstrId(int circleMstrId) {
		this.circleMstrId = circleMstrId;
	}
	public int getSelectedproductlobId() {
		return selectedproductlobId;
	}
	public void setSelectedproductlobId(int selectedproductlobId) {
		this.selectedproductlobId = selectedproductlobId;
	}
	public int getProductLobID() {
		return productLobID;
	}
	public void setProductLobID(int productLobID) {
		this.productLobID = productLobID;
	}
	
	
	
	
	
	
	
	
}
