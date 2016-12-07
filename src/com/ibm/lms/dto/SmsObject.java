package com.ibm.lms.dto;

public class SmsObject {
	
	
	
	private Long leadId;
	private String msIsdn;
	
	public Long getLeadId() {
		return leadId;
	}
	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}
	public String getMsIsdn() {
		return msIsdn;
	}
	public void setMsIsdn(String msIsdn) {
		this.msIsdn = msIsdn;
	}
	@Override
	public boolean equals(Object smsObj){
		
		if(this==smsObj)
			return true;
		
		if(this!=null && smsObj==null){
			
			//logger.info("NEqual1");
			return false;
		}
		if(!(smsObj instanceof SmsObject))
		{
		//	logger.info("NEqual2");
			return false;
		}
		else if(this!=null && smsObj!=null){
			SmsObject sms=(SmsObject) smsObj;
		//	logger.info("Equal");
			return (this.getLeadId().equals(sms.getLeadId()) && this.getMsIsdn().equals(sms.getMsIsdn()));
			
		}
		
		return false;
		
		
	}
	@Override
	public int hashCode(){
		
		int multiPlier=31;
		int result=0;
		
		result =result+multiPlier*this.getLeadId().hashCode();
		result=result+multiPlier*this.getMsIsdn().hashCode();	
		
		return result;
		
	}

}
