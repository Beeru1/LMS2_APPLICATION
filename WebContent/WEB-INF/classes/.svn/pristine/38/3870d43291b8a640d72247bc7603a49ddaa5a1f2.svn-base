package com.ibm.lms.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class ZoneDTO {

	private String zoneId;	
	private String zoneCode="";
	private String zoneName="";
	private String cityId="";
	private String circleMstrId="";
	private String cityCode;
	
	
	
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	} 

	
	public JSONObject toJSONObject() {
		JSONObject json=new JSONObject();
		try {
			json.put("zoneId", zoneId);
			json.put("zoneCode", zoneCode);
			json.put("zoneName", zoneName);
			json.put("circleMstrId", circleMstrId);		

		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return json; 
	}
	 
}