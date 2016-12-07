package com.ibm.lms.dto;

public class SMSDto {

	private Long headerLead;
	private String msIsdn;
	private String smsText;
	private String headerStatus;
	public Long getHeaderLead() {
		return headerLead;
	}
	public void setHeaderLead(Long headerLead) {
		this.headerLead = headerLead;
	}
	public String getMsIsdn() {
		return msIsdn;
	}
	public void setMsIsdn(String msIsdn) {
		this.msIsdn = msIsdn;
	}
	public String getSmsText() {
		return smsText;
	}
	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}
	public String getHeaderStatus() {
		return headerStatus;
	}
	public void setHeaderStatus(String headerStatus) {
		this.headerStatus = headerStatus;
	}
	
}
