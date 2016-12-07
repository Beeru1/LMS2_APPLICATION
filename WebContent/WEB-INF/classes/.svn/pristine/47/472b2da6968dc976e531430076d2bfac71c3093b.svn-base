package com.ibm.km.services;

import java.io.InputStream;
import java.util.List;

import com.ibm.km.dto.EmployeeAppreciationDTO;
import com.ibm.km.forms.KmEmployeeAppreciationFormBean;
import com.ibm.lms.exception.LMSException;

public interface KmEmployeeAppreciationService {
	
	/**
	 * Method to insert appreciation
	 */
	
	public String insertAppreciation(KmEmployeeAppreciationFormBean kmEmployeeAppreciationForm)throws LMSException;
	
	/**
	 * Method to get list of employee appreciations
	 */
	public List<EmployeeAppreciationDTO> getEmployeeAppreciationList()throws LMSException;

	public InputStream getEmployeeImage(int appId) throws LMSException;

}
