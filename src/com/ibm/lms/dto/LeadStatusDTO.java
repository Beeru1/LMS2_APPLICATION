package com.ibm.lms.dto;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class LeadStatusDTO {

	private String message="";
	//status details:
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private int leadStatusId;	
	private String leadStatus="";
	private String leadStatusName="";
	private String leadStatusDisplay="";
	
	//sub_status display:
	
	private int subStatusId;
	private int productLobId;
	private int lobId;
	private String lobName="";
	public int getLobId() {
		return lobId;
	}
	public void setLobId(int lobId) {
		this.lobId = lobId;
	}
	public String getLobName() {
		return lobName;
	}
	public void setLobName(String lobName) {
		this.lobName = lobName;
	}
	private String productName="";
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	private int subStatusCode;//this is the unique id
	private String leadSubStatus="";
	private String leadSubStatusDisplay="";
	
	//sub_sub_status display:
	
    private int leadSubSubStatusId;	
    public String getLeadSubSubStatus() {
		return leadSubSubStatus;
	}
	public void setLeadSubSubStatus(String leadSubSubStatus) {
		this.leadSubSubStatus = leadSubSubStatus;
	}
	public String getLeadSubSubStatusDisplay() {
		return leadSubSubStatusDisplay;
	}
	public void setLeadSubSubStatusDisplay(String leadSubSubStatusDisplay) {
		this.leadSubSubStatusDisplay = leadSubSubStatusDisplay;
	}
	private String leadSubSubStatus="";
	private String leadSubSubStatusDisplay="";
	
	
	public int getSubStatusCode() {
		return subStatusCode;
	}
	public void setSubStatusCode(int subStatusCode) {
		this.subStatusCode = subStatusCode;
	}
	public int getProductLobId() {
		return productLobId;
	}
	public void setProductLobId(int productLobId) {
		this.productLobId = productLobId;
	}
private String leadSubStatusSubStatusId="";
	
	
	private String leadStatusSubStatusId="";	
		
	private String leadSubStatusName="";
	
	private String allStatusId="";	
	
	private String allStatusName="";
	
	public int getSubStatusId() {
		return subStatusId;
	}

	public String getAllStatusId() {
		return allStatusId;
	}
	public void setAllStatusId(String allStatusId) {
		this.allStatusId = allStatusId;
	}
	public String getAllStatusName() {
		return allStatusName;
	}
	public void setAllStatusName(String allStatusName) {
		this.allStatusName = allStatusName;
	}
	public void setSubStatusId(int subStatusId) {
		this.subStatusId = subStatusId;
	}
	
	public String getLeadStatusSubStatusId() {
		return leadStatusSubStatusId;
	}
	public void setLeadStatusSubStatusId(String leadStatusSubStatusId) {
		this.leadStatusSubStatusId = leadStatusSubStatusId;
	}
	private HashMap leadSubStatusId = null; // Map of <SubStatusID, SubStatusValue>
		
	public int getLeadStatusId() {
		return leadStatusId;
	}
	public void setLeadStatusId(int leadStatusId) {
		this.leadStatusId = leadStatusId;
	}
	public String getLeadStatus() {
		return leadStatus;
	}
	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}
	public String getLeadStatusName() {
		return leadStatusName;
	}
	public void setLeadStatusName(String leadStatusName) {
		this.leadStatusName = leadStatusName;
	}
	public void setLeadStatusDisplay(String leadStatusDisplay) {
		this.leadStatusDisplay = leadStatusDisplay;
	}
	public String getLeadStatusDisplay() {
		return leadStatusDisplay;
	}
	public void setLeadSubStatusId(HashMap leadSubStatusId) {
		this.leadSubStatusId = leadSubStatusId;
	}
	public HashMap getLeadSubStatusId() {
		return leadSubStatusId;
	}
	public String getLeadSubStatus() {
		return leadSubStatus;
	}
	public void setLeadSubStatus(String leadSubStatus) {
		this.leadSubStatus = leadSubStatus;
	}
	public String getLeadSubStatusDisplay() {
		return leadSubStatusDisplay;
	}
	public void setLeadSubStatusDisplay(String leadSubStatusDisplay) {
		this.leadSubStatusDisplay = leadSubStatusDisplay;
	}
	public String getLeadSubStatusName() {
		return leadSubStatusName;
	}
	public void setLeadSubStatusName(String leadSubStatusName) {
		this.leadSubStatusName = leadSubStatusName;
	}
	public String getLeadSubStatusSubStatusId() {
		return leadSubStatusSubStatusId;
	}
	public void setLeadSubStatusSubStatusId(String leadSubStatusSubStatusId) {
		this.leadSubStatusSubStatusId = leadSubStatusSubStatusId;
	}
	public int getLeadSubSubStatusId() {
		return leadSubSubStatusId;
	}
	public void setLeadSubSubStatusId(int leadSubSubStatusId) {
		this.leadSubSubStatusId = leadSubSubStatusId;
	}
	

	public JSONObject toJSONObject() {
		JSONObject json=new JSONObject();
		try {
			json.put("leadSubStatus", leadSubStatus);
			json.put("leadSubSubStatusId", leadSubSubStatusId);
			
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return json; 
	}


}