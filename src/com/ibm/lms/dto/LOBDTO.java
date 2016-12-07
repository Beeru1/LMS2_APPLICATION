package com.ibm.lms.dto;

import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;


public class LOBDTO {

	private int lobId=0;
	private int productLobId=0;
	private String productLobName;
	public int getProductLobId() {
		return productLobId;
	}
	public void setProductLobId(int productLobId) {
		this.productLobId = productLobId;
	}
	public String getProductLobName() {
		return productLobName;
	}
	public void setProductLobName(String productLobName) {
		this.productLobName = productLobName;
	}
	private String lobName="";
	private String circleName="";
	
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
	
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public JSONObject toJSONObject() {
		JSONObject json=new JSONObject();
		try {
			json.put("lobId", lobId);
			json.put("lobName", lobName);
			json.put("circleName", circleName);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return json; 
	}

	
}