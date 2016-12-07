/**
 * 
 */
package com.ibm.lms.dto;

/**
 * @author Nehil Parashar
 *
 */
public class FormIdDto 
{
	private long fid;
	private String pageUrl;
	private String status;
	private String updatedBy;
	
	/**
	 * @return the fid
	 */
	public long getFid() {
		return fid;
	}
	/**
	 * @param fid the fid to set
	 */
	public void setFid(long fid) {
		this.fid = fid;
	}
	/**
	 * @return the pageUrl
	 */
	public String getPageUrl() {
		return pageUrl;
	}
	/**
	 * @param pageUrl the pageUrl to set
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
