package com.ibm.lms.webservice;


public interface RetrieveLeadDataService_SEI extends java.rmi.Remote
{
  public com.ibm.lms.dto.webservice.RetrieveLeadDataResponse retrieveLeadData(com.ibm.lms.dto.webservice.AuthorizationData authData,java.lang.String leadId,java.lang.String prospectMobileNumber,java.lang.String transactionId,java.lang.String flag) throws java.lang.Exception;
}