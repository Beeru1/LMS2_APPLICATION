package com.ibm.lms.dto;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class BulkAssignmentLobDTO {

	
	private int productlobId=0;	
	private String productlobName="";
	private ArrayList lobsNameList = null;
	
	private int selectedproductlobId=0;
	
	
	
	
	
	
	public int getProductlobId() {
		return productlobId;
	}
	public void setProductlobId(int productlobId) {
		this.productlobId = productlobId;
	}
	public String getProductlobName() {
		return productlobName;
	}
	public void setProductlobName(String productlobName) {
		this.productlobName = productlobName;
	}
	
	
	
	public JSONObject toJSONObject() {
		JSONObject json=new JSONObject();
		try {
			json.put("circleId", productlobId);
			json.put("circleName", productlobName);
		

		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return json; 
	}
	public ArrayList getLobsNameList() {
		return lobsNameList;
	}
	public void setLobsNameList(ArrayList lobsNameList) {
		this.lobsNameList = lobsNameList;
	}
	public int getSelectedproductlobId() {
		return selectedproductlobId;
	}
	public void setSelectedproductlobId(int selectedproductlobId) {
		this.selectedproductlobId = selectedproductlobId;
	}
	
	
}