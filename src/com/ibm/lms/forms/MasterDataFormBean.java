package com.ibm.lms.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.lms.dto.MasterDataDTO;

public class MasterDataFormBean extends ActionForm {
	
	/**
	 * @author Parnika Sharma
	 */
	private static final long serialVersionUID = 1L;
	private String masterTableName="";
	private int masterTableId=0;	
	private String methodName="";
	private String message="";
	private int selectedMasterId=0;	
	private ArrayList<MasterDataDTO> masterTableList = null;
	//added by aman for adding LOB type 
	
	private String lobName;
	private int lobId;
	private int selectedLobId;
	private ArrayList lobList = null;
	
	//end of changes by aman
	
	
	public String getMasterTableName() {
		return masterTableName;
	}
	public void setMasterTableName(String masterTableName) {
		this.masterTableName = masterTableName;
	}
	public int getMasterTableId() {
		return masterTableId;
	}
	public void setMasterTableId(int masterTableId) {
		this.masterTableId = masterTableId;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getSelectedMasterId() {
		return selectedMasterId;
	}
	public void setSelectedMasterId(int selectedMasterId) {
		this.selectedMasterId = selectedMasterId;
	}
	public ArrayList<MasterDataDTO> getMasterTableList() {
		return masterTableList;
	}
	public void setMasterTableList(ArrayList<MasterDataDTO> masterTableList) {
		this.masterTableList = masterTableList;
	}
	public String getLobName() {
		return lobName;
	}
	public void setLobName(String lobName) {
		this.lobName = lobName;
	}
	public int getLobId() {
		return lobId;
	}
	public void setLobId(int lobId) {
		this.lobId = lobId;
	}
	public int getSelectedLobId() {
		return selectedLobId;
	}
	public void setSelectedLobId(int selectedLobId) {
		this.selectedLobId = selectedLobId;
	}
	public ArrayList getLobList() {
		return lobList;
	}
	public void setLobList(ArrayList lobList) {
		this.lobList = lobList;
	}

	

}
