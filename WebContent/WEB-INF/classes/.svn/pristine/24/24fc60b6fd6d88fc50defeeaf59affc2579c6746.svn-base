package com.ibm.lms.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class PINCodeDTO {

	private String pinCode="";
	private String cityCode="";
	private String cityZoneCode = "";
	
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	/* Added By Parnika for LMS Phase 2 */
	
	public String getCityZoneCode() {
		return cityZoneCode;
	}
	public void setCityZoneCode(String cityZoneCode) {
		this.cityZoneCode = cityZoneCode;
	}
	public JSONObject toJSONObject() {
		JSONObject json=new JSONObject();
		try {
			json.put("pinCode", pinCode);
			json.put("cityCode", cityCode);
			json.put("cityZoneCode", cityZoneCode);	
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return json; 
	}
	
	/* End of changes by Parnika */
	}