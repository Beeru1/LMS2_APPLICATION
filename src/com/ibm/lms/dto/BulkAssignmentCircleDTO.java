package com.ibm.lms.dto;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class BulkAssignmentCircleDTO {

	private int circleId=0;	
	private String circleName="";

	
	private int selectedCircleId=0;	
	
	private ArrayList circleTypeList = null;
	
	
	
	public int getCircleId() {
		return circleId;
	}
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	
	
	public JSONObject toJSONObject() {
		JSONObject json=new JSONObject();
		try {
			json.put("circleId", circleId);
			json.put("circleName", circleName);
		

		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return json; 
	}
	public ArrayList getCircleTypeList() {
		return circleTypeList;
	}
	public void setCircleTypeList(ArrayList circleTypeList) {
		this.circleTypeList = circleTypeList;
	}
	public int getSelectedCircleId() {
		return selectedCircleId;
	}
	public void setSelectedCircleId(int selectedCircleId) {
		this.selectedCircleId = selectedCircleId;
	}
	
	
}