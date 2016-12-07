package com.ibm.lms.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class CityDTO {

	private String cityId="";	
	private String cityCode="";
	private String zoneCode="";
	private String cityName="";
	private int circleId;
	
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getCircleId() {
		return circleId;
	}
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}
	public String getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	
	/* Added By Parnika for LMS Phase 2 */
	
	public JSONObject toJSONObject() {
		JSONObject json=new JSONObject();
		try {
			json.put("zoneCode", zoneCode);
			json.put("cityCode", cityCode);
			json.put("cityName", cityName);
			json.put("cityId", cityId);		

		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return json; 
	}
	
	/* End of changes by Parnika */
	
}