package com.ibm.lms.dto.webservice;


public class RetrieveLeadDataResponse {
	
	private String responseMsg ="";
	private int responseCode ;
	private RetrieveLeadDataDTO[] webServiceResponse;
	
	
	
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public RetrieveLeadDataDTO[] getWebServiceResponse() {
		return webServiceResponse;
	}
	public void setWebServiceResponse(RetrieveLeadDataDTO[] webServiceResponse) {
		this.webServiceResponse = webServiceResponse;
	}
	

}
