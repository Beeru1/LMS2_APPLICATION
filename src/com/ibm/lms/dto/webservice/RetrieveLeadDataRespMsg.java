package com.ibm.lms.dto.webservice;

public class RetrieveLeadDataRespMsg{
	private String responseMsg ="";
	private int responseCode ;
	private RetrieveLeadDataNewDTO[] webServiceResponse;
	
	
	
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
	public RetrieveLeadDataNewDTO[] getWebServiceResponse() {
		return webServiceResponse;
	}
	public void setWebServiceResponse(RetrieveLeadDataNewDTO[] webServiceResponse) {
		this.webServiceResponse = webServiceResponse;
	}
	

}
