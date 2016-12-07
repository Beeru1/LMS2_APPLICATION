/**
 * 
 */
package com.ibm.lms.dto;


/**
 * @author Nehil Parashar
 *
 */
public class CityArrayData
{
	private String cityCode;
	private String name;
	private String[] pincode;
	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the pincode
	 */
	public String[] getPincode() {
		return pincode;
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String[] pincode) {
		this.pincode = pincode;
	}
}
