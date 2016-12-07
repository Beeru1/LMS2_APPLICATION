package com.ibm.lms.dao;

import java.util.ArrayList;
import java.util.List;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.common.DataObject;
import com.ibm.lms.dto.CircleMstrDto;
import com.ibm.lms.dto.PushMstrWebserviceDTO;
import com.ibm.lms.dto.webservice.RetrieveLeadDataDTO;
public interface CircleManagementDao {
	
	public  ArrayList<CircleMstrDto> getLobList() throws DAOException;
	public  List<DataObject> getCircleDetailsUsingProductName(String productName) throws DAOException;
	public  ArrayList<CircleMstrDto> getCircleList(CircleMstrDto circleMstrDto) throws DAOException;
	public  int createCircle(CircleMstrDto circleMstrDto) throws DAOException;
	public  int editCircle(CircleMstrDto circleMstrDto) throws DAOException;
	public  int deleteCircle(CircleMstrDto circleMstrDto) throws DAOException;
	public  List<String> getPincodeUsingProductName(String product) throws DAOException;
	public  List<DataObject> getCityUsingProductName(String product) throws DAOException;
	public  PushMstrWebserviceDTO[] getFindAllDetailsUsingProductName(String product) throws DAOException;
	public boolean isValidProduct(String product) throws DAOException;
	
	public RetrieveLeadDataDTO[] getLeadList(String leadId,	String prospectMobileNumber, String transactionId, String flag)throws DAOException;
	
}
