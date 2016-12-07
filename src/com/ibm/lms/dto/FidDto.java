/**
 * 
 */
package com.ibm.lms.dto;

/**
 * @author Administrator
 *
 */
public class FidDto 
{
	private int fid;
	private String pageUrl;
	/**
	 * @return the fidName
	 */
	public int getFid() {
		return fid;
	}
	/**
	 * @param fidName the fidName to set
	 */
	public void setFid(int fid) {
		this.fid = fid;
	}
	/**
	 * @return the fidValue
	 */
	public String getFidValue() {
		return pageUrl;
	}
	/**
	 * @param fidValue the fidValue to set
	 */
	public void setFidValue(String pageUrl) {
		this.pageUrl = pageUrl;
	}	
}
