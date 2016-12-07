package com.ibm.lms.services;

/**
 * @author Naresh Chander 
 */

import java.util.ArrayList;
import com.ibm.lms.dto.CircleMstrDto;
import com.ibm.lms.dto.webservice.RetrieveLeadDataDTO;
import com.ibm.lms.exception.LMSException;

public interface CircleManagementService {
	
	public  ArrayList<CircleMstrDto> getLobList() throws LMSException;
	public  ArrayList<CircleMstrDto> getCircleList(CircleMstrDto circleMstrDto) throws LMSException;
	public  int createCircle(CircleMstrDto circleMstrDto) throws LMSException;
	public  int editCircle(CircleMstrDto circleMstrDto) throws LMSException;
	public  int deleteCircle(CircleMstrDto circleMstrDto) throws LMSException;
	public RetrieveLeadDataDTO[] getLeadList(String leadId,	String prospectMobileNumber, String transactionId, String flag);

}
