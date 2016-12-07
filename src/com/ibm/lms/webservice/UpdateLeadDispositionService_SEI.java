package com.ibm.lms.webservice;


public interface UpdateLeadDispositionService_SEI extends java.rmi.Remote
{
  public com.ibm.lms.dto.ResponseMessageDisposition[] updateLeadDisposition(com.ibm.lms.dto.LeadData[] leadData,com.ibm.lms.dto.AuthorizationData authData);
}