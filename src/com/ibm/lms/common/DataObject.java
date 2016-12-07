/**
 * 
 */
package com.ibm.lms.common;

/**
 * @author Nehil Parashar
 *
 */
public class DataObject 
{
	private String code;
	private String name;
	
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
	 * @param cityCode the cityCode to set
	 */
	public void setCode(String cityCode) {
		this.code = cityCode;
	}
	/**
	 * @return the cityCode
	 */
	public String getCode() {
		return code;
	}
}