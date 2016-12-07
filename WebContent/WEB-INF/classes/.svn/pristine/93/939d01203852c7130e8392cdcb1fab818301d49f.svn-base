package com.ibm.lms.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class CircleDTO {

	private int circleId=0;	
	private String circleName="";
	private String circleDescription="";
	private int zoneId=0;	
	private String zoneName="";
	private int cityId=0;	
	private String cityCode="";
	private int cityZoneId;
	private String cityZoneCode="";
	
	/* Added By Parnika for LMS Phase 2 */
	
	private int circleMstrId=0;	
	private int lobId=0;
	private String lobName="";	
	
	/* End of changes by Parnika */
	
	
	//added by aman for phase 3
	
	private int stateId=0;
	private String stateCode="";
	
	//end of changes by aman
	
	
	
	public int getZoneId() {
		return zoneId;
	}
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getLobName() {
		return lobName;
	}
	public void setLobName(String lobName) {
		this.lobName = lobName;
	}
	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public int getCityZoneId() {
		return cityZoneId;
	}
	public void setCityZoneId(int cityZoneId) {
		this.cityZoneId = cityZoneId;
	}
	public String getCityZoneCode() {
		return cityZoneCode;
	}
	public void setCityZoneCode(String cityZoneCode) {
		this.cityZoneCode = cityZoneCode;
	}
	public int getCircleId() {
		return circleId;
	}
	public int getCircleMstrId() {
		return circleMstrId;
	}
	public void setCircleMstrId(int circleMstrId) {
		this.circleMstrId = circleMstrId;
	}
	public int getLobId() {
		return lobId;
	}
	public void setLobId(int lobId) {
		this.lobId = lobId;
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
			json.put("circleMstrId", circleMstrId);
			json.put("lobId", lobId);		

		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return json; 
	}
	
}