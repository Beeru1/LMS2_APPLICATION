package com.ibm.lms.services;

import java.util.ArrayList;

import org.json.JSONObject;

import com.ibm.lms.dto.LeadDetailsDTO;
import com.ibm.lms.dto.ProductDTO;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.LeadRegistrationFormBean;

public interface LeadRegistrationService {
	
	public String insertRecord(LeadRegistrationFormBean leadRegistrationFormBean)throws LMSException;
	
	public String updateRecord(LeadRegistrationFormBean leadRegistrationFormBean,String flag)throws LMSException;
	
	public  ArrayList<LeadDetailsDTO> getLeadListByLeadId(Long leadId) throws LMSException;
	
	public  ArrayList<LeadDetailsDTO> getLeadListByLeadContact(Long leadId) throws LMSException;
	
	public  ArrayList<LeadDetailsDTO> getLeadListByLeadId(LeadRegistrationFormBean leadRegistrationFormBean) throws LMSException;
	
	public  ArrayList<LeadDetailsDTO> getLeadListByMobileNo(long mobileNo) throws LMSException;
	
	public  ArrayList<LeadDetailsDTO> getLeadDetails(long leadId) throws LMSException;
	
	public  ArrayList<LeadDetailsDTO> getLeadTransactinDetails(long leadId) throws LMSException;
	
	public  String[] getProductIDsOpenLeads(int prospectId) throws LMSException;

	public String insertLeadSearchTransaction(LeadRegistrationFormBean leadRegistrationFormBean, String userLoginId,String ipaddress)throws LMSException;

	public String insertLeadSearchDialerTransaction(LeadRegistrationFormBean leadRegistrationFormBean, String userLoginId,String ipaddress)throws LMSException;

	//added by Nancy
	public JSONObject getElementsAsJsonLeadSubStatus(String leadStatusId,int lobId)throws Exception;
	
	
	public  ArrayList<ProductDTO> getProductLobList() throws LMSException;
	public ArrayList<LeadDetailsDTO> getLeadListByTid(String tid)throws LMSException;
	public  ArrayList<LeadDetailsDTO> getHistoryDetails(long leadId) throws LMSException;
	public String update4GRecord(LeadRegistrationFormBean leadRegistrationFormBean)throws LMSException;

	public Boolean getChannelPartnerFlag(long leadId,String loginId);
//////Added by pernica for postpaid lead search
	
	public  ArrayList<LeadDetailsDTO> getLeadListByLeadIdAndProductID(Long leadId, ArrayList<LeadDetailsDTO> list ) throws LMSException;
	//Added by satish
	public ArrayList<LeadDetailsDTO> getLeadListofcheckLeadcreationByTid(LeadRegistrationFormBean leadRegistrationFormBean)throws LMSException;
	
	//added by satish
	

	
}
