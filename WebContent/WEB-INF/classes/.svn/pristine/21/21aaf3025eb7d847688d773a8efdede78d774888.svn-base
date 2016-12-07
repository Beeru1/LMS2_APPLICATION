package com.ibm.lms.dto;


/**
 * @author Parnika Sharma 
 */

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class AgencyDTO {

	
	private String methodName;
	private String message="";
	private String agencyName;
	private String agencyDescription;
	private String circleName="";
	private int circleId;
	private ArrayList circleList = null;
	private String[] createMultiple = null;
	private ArrayList selectedcircleList = null;
	
	private int selectedAgencyId;
	private ArrayList agencyList = null;
	private int agencyId;
	
	private String[] createMultiple1 = null;
	
	private String mappedCircleName="";
	private int mappedCircleId;
	private ArrayList mappedCircleList = null;
	
	private String agencyPath;
	private String agencyClass="";
	private String username;
	private String password;
	
	private String defaultCheck="";
	
	
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
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getAgencyDescription() {
		return agencyDescription;
	}
	public void setAgencyDescription(String agencyDescription) {
		this.agencyDescription = agencyDescription;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public int getCircleId() {
		return circleId;
	}
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}
	public ArrayList getCircleList() {
		return circleList;
	}
	public void setCircleList(ArrayList circleList) {
		this.circleList = circleList;
	}
	public String[] getCreateMultiple() {
		return createMultiple;
	}
	public void setCreateMultiple(String[] createMultiple) {
		this.createMultiple = createMultiple;
	}
	public ArrayList getSelectedcircleList() {
		return selectedcircleList;
	}
	public void setSelectedcircleList(ArrayList selectedcircleList) {
		this.selectedcircleList = selectedcircleList;
	}
	public int getSelectedAgencyId() {
		return selectedAgencyId;
	}
	public void setSelectedAgencyId(int selectedAgencyId) {
		this.selectedAgencyId = selectedAgencyId;
	}
	public ArrayList getAgencyList() {
		return agencyList;
	}
	public void setAgencyList(ArrayList agencyList) {
		this.agencyList = agencyList;
	}
	public int getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}
	
	
	
	public String[] getCreateMultiple1() {
		return createMultiple1;
	}
	public void setCreateMultiple1(String[] createMultiple1) {
		this.createMultiple1 = createMultiple1;
	}
	
	
	public String getMappedCircleName() {
		return mappedCircleName;
	}
	public void setMappedCircleName(String mappedCircleName) {
		this.mappedCircleName = mappedCircleName;
	}
	public int getMappedCircleId() {
		return mappedCircleId;
	}
	public void setMappedCircleId(int mappedCircleId) {
		this.mappedCircleId = mappedCircleId;
	}
	public ArrayList getMappedCircleList() {
		return mappedCircleList;
	}
	public void setMappedCircleList(ArrayList mappedCircleList) {
		this.mappedCircleList = mappedCircleList;
	}	
	public String getAgencyPath() {
		return agencyPath;
	}
	public void setAgencyPath(String agencyPath) {
		this.agencyPath = agencyPath;
	}
	public String getAgencyClass() {
		return agencyClass;
	}
	public void setAgencyClass(String agencyClass) {
		this.agencyClass = agencyClass;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
		
	public String getDefaultCheck() {
		return defaultCheck;
	}
	public void setDefaultCheck(String defaultCheck) {
		this.defaultCheck = defaultCheck;
	}
	
	public JSONObject toJSONObject() {
		JSONObject json=new JSONObject();
		try {
			json.put("agencyId", agencyId);
			json.put("agencyName", agencyName);
			json.put("agencyDescription", agencyDescription);
			json.put("circleId", circleId);
			json.put("circleName", circleName);
			json.put("circleList", circleList);
			json.put("createMultiple", createMultiple);
			json.put("selectedAgencyId", selectedAgencyId);
			json.put("selectedcircleList", selectedcircleList);
			json.put("selectedAgencyId", selectedAgencyId);
			json.put("agencyList", agencyList);
			json.put("createMultiple1", createMultiple1);
			json.put("mappedCircleId", mappedCircleId);
			json.put("mappedCircleName", mappedCircleName);
			json.put("mappedCircleList", mappedCircleList);
			json.put("agencyPath", agencyPath);
			json.put("agencyClass", agencyClass);
			json.put("username", username);
			json.put("password", password);
			json.put("defaultCheck", defaultCheck);
			
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return json; 
	}

	
}