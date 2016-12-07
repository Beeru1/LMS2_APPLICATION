package com.ibm.lms.webservice;


public interface UpdateLeadWebService_SEI extends java.rmi.Remote
{
  public com.ibm.lms.dto.UpdateLeadResponseMessage updateLeadData(com.ibm.lms.engine.dataobjects.UpdateLeadDataDO[] leadData,com.ibm.lms.dto.AuthorizationData authData) throws java.lang.Exception;
}