package com.ibm.lms.dto;
import org.json.JSONException;
import org.json.JSONObject;
public class CircleForProductDTO {
	private int circleId=0;	
	private String circleName="";
	private String circleDescription="";
	private int LobId;
	private int circleMstrId;
	private String circleIdLobIdCircleMstrId;
	
	public String getCircleIdLobIdCircleMstrId() {
		return circleIdLobIdCircleMstrId;
	}
	public void setCircleIdLobIdCircleMstrId(String circleIdLobIdCircleMstrId) {
		this.circleIdLobIdCircleMstrId = circleIdLobIdCircleMstrId;
	}
	public int getLobId() {
		return LobId;
	}
	public void setLobId(int lobId) {
		LobId = lobId;
	}
	public int getCircleMstrId() {
		return circleMstrId;
	}
	public void setCircleMstrId(int circleMstrId) {
		this.circleMstrId = circleMstrId;
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
