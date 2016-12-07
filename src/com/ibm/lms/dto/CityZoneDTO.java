package com.ibm.lms.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class CityZoneDTO {
private  int cityZoneId;
private String cityZoneCode;
private String cityZoneName;


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
public String getCityZoneName() {
	return cityZoneName;
}
public void setCityZoneName(String cityZoneName) {
	this.cityZoneName = cityZoneName;
}

/* Added By Parnika for LMS Phase 2 */

public JSONObject toJSONObject() {
	JSONObject json=new JSONObject();
	try {
		json.put("cityZoneId", cityZoneId);
		json.put("cityZoneCode", cityZoneCode);
		json.put("cityZoneName", cityZoneName);
	}
	catch (JSONException e) {
		e.printStackTrace();
	}
	return json; 
}

/* End of changes by Parnika */



}
