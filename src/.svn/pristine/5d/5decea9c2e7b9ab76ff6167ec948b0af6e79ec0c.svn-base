package com.ibm.lms.dto;
import org.json.JSONException;
import org.json.JSONObject;
public class CircleForProductLob {
	private int circleId=0;	
	private String circleName="";
	private String circleDescription="";
	private int LobId;
	
	
	
	public int getLobId() {
		return LobId;
	}
	public void setLobId(int lobId) {
		LobId = lobId;
	}
	
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
	public String getCircleDescription() {
		return circleDescription;
	}
	public void setCircleDescription(String circleDescription) {
		this.circleDescription = circleDescription;
	}
	
	public JSONObject toJSONObject() {
		JSONObject json=new JSONObject();
		try {
			json.put("circleId", circleId);
			json.put("circleName", circleName);
			json.put("circleDescription", circleDescription);

		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return json; 
	}
	
}
