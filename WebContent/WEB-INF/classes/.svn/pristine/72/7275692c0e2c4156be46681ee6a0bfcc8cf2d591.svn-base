package com.ibm.lms.dao;

import java.util.ArrayList;
import java.util.List;

import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.dto.LeadDetailsDTO;
import com.ibm.lms.dto.ProductDTO;
import com.ibm.lms.exception.DAOException;

import com.ibm.lms.forms.LeadRegistrationFormBean;

public interface LeadRegistrationDao {

	public ArrayList<BulkUploadMsgDto>  insertRecord(List<LeadDetailsDTO> leadList, int source) throws DAOException;
	
	public void  insertDirtyRecord(List<LeadDetailsDTO> leadList, int source) throws DAOException;
	
    public String updateLeadDetailsDialler(List<LeadDetailsDTO> leadList,String flag) throws DAOException;
    
    public  ArrayList<LeadDetailsDTO> getLeadListByLeadId(Long leadId) throws DAOException;

    public  ArrayList<LeadDetailsDTO> getLeadListByLeadContact(Long leadId) throws DAOException;

	public  ArrayList<LeadDetailsDTO> getLeadListByLeadId(LeadRegistrationFormBean leadRegistrationFormBean) throws DAOException;
	
	public  ArrayList<LeadDetailsDTO> getLeadListByMobileNo(long mobileNo) throws DAOException;
	
	public  ArrayList<LeadDetailsDTO> getLeadDetails(long leadId) throws DAOException;
	
	public  ArrayList<LeadDetailsDTO> getLeadTransactinDetails(long leadId) throws DAOException;
	
	public  ArrayList<LeadDetailsDTO> getProductIDsOpenLeads(int prospectId) throws DAOException;

	public String insertLeadSearchTransaction(LeadRegistrationFormBean leadRegistrationFormBean, String userLoginId,String ipaddress)throws DAOException;

	public String insertLeadSearchDialerTransaction(LeadRegistrationFormBean leadRegistrationFormBean, String userLoginId,String ipaddress)throws DAOException;

	public  ArrayList<ProductDTO> getProductLobList() throws DAOException;

	public ArrayList<LeadDetailsDTO> getLeadListByTid(String tid)throws DAOException;
	
	public ArrayList<LeadDetailsDTO> getHistoryDetails(long leadId) throws DAOException ;
	
	public String update4GRecord(LeadDetailsDTO leadDetailsDTO) throws DAOException;

	public Boolean getChannelPartnerFlag(long leadId,String loginId) throws DAOException;
	
	///added by Pernica for postpaid lead search
	
	 public  ArrayList<LeadDetailsDTO> getLeadListByLeadIdAndProductID(Long leadId,ArrayList<LeadDetailsDTO> list) throws DAOException;
	 
	//Added by satish
		public ArrayList<LeadDetailsDTO> getLeadListofcheckLeadcreationByTid(LeadRegistrationFormBean leadRegistrationFormBean)throws DAOException;
		
		//added by satish
	 
}
