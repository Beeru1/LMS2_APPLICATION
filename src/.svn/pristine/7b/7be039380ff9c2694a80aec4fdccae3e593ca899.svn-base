package com.ibm.lms.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class RSUDTO {

	private String rsuId="";
	private String zoneId="";
	private String rsuCode = "";
	private String cityZoneCode = "";
	
	public String getRsuCode() {
		return rsuCode;
	}
	public void setRsuCode(String rsuCode) {
		this.rsuCode = rsuCode;
	}
	public String getRsuId() {
		return rsuId;
	}
	public void setRsuId(String rsuId) {
		this.rsuId = rsuId;
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	
	
	/* Added By Parnika for LMS Phase 2 */
	
	
	public JSONObject toJSONObject() {
		JSONObject json=new JSONObject();
		try {
			json.put("rsuCode", rsuCode);
			json.put("rsuId", rsuId);
			json.put("cityZoneCode", cityZoneCode);	
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return json; 
	}
	public String getCityZoneCode() {
		return cityZoneCode;
	}
	public void setCityZoneCode(String cityZoneCode) {
		this.cityZoneCode = cityZoneCode;
	}
	
	/* End of changes by Parnika */
	 
}