package com.ibm.lms.webservice;


public interface LeadUpdatedDataService_SEI extends java.rmi.Remote
{
  public com.ibm.lms.dto.webservice.ServiceResponseMsg leadUpdatedDataCapture(com.ibm.lms.dto.webservice.UpdatedLeadDataDTO[] updatedLeadDataDTO,com.ibm.lms.dto.webservice.AuthorizationData authorizationData) throws java.lang.Exception;
}