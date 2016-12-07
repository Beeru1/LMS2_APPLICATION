package com.ibm.lms.services;

import java.util.ArrayList;

import com.ibm.lms.dto.CircleMstrDto;
import com.ibm.lms.dto.webservice.RetrieveLeadDataNewDTO;
import com.ibm.lms.exception.LMSException;

public interface CircleManagementServiceNew {
	
	
	public  ArrayList<CircleMstrDto> getLobList() throws LMSException;
	public  ArrayList<CircleMstrDto> getCircleList(CircleMstrDto circleMstrDto) throws LMSException;
	public  int createCircle(CircleMstrDto circleMstrDto) throws LMSException;
	public  int editCircle(CircleMstrDto circleMstrDto) throws LMSException;
	public  int deleteCircle(CircleMstrDto circleMstrDto) throws LMSException;
	
	public RetrieveLeadDataNewDTO[] getLeadListNew(String leadId,
			String prospectMobileNumber, String transactionId, String flag,
			String product, String source);
	

}
