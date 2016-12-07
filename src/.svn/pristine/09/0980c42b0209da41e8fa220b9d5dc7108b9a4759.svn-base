package com.ibm.lms.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestCategoryDTO {

	private int requestCategoryId;	
	private String requestCategoryName="";
	private String status="";
	private String requestCategory="";
	public String getRequestCategory() {
		return requestCategory;
	}
	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}
	public int getRequestCategoryId() {
		return requestCategoryId;
	}
	public void setRequestCategoryId(int requestCategoryId) {
		this.requestCategoryId = requestCategoryId;
	}
	public String getRequestCategoryName() {
		return requestCategoryName;
	}
	public void setRequestCategoryName(String requestCategoryName) {
		this.requestCategoryName = requestCategoryName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public JSONObject toJSONObject() {
		JSONObject json=new JSONObject();
		try {
			json.put("requestCategoryId", requestCategoryId);
			json.put("requestCategoryName", requestCategoryName);
			
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return json; 
	}
	
	 
}