package com.ibm.lms.wf.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.dto.UserDto;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.wf.dto.BulkFeasibilityDTO;
import com.ibm.lms.wf.dto.Constant;
import com.ibm.lms.wf.dto.LeadDetailDTO;
import com.ibm.lms.wf.dto.Leads;
import com.ibm.lms.wf.forms.LeadForm;

public interface AssignedLeadsManager {
	public ArrayList<Leads> listAssignedLeads(String loginID,String startDate,String endDate,String view) throws LMSException;
	public ArrayList<Leads> listAssignedLeadsEscalation(String loginID,String startDate,String endDate,String view) throws LMSException;
	public ArrayList<Leads> listAssignedLeadsFeasibility(String loginID,String startDate,String endDate,String view) throws LMSException;
	public ArrayList<Constant> getActionList(String keyName) throws LMSException;
	public ArrayList<UserDto> getUsersList(ArrayList circleList, ArrayList lobList) throws LMSException;
	public Boolean closeTheLead(LeadForm commonForm) throws LMSException;
	public Boolean closeTheLeadCloseLoop(LeadForm commonForm) throws LMSException;
	public Boolean closeTheLeadSms(LeadForm commonForm) throws LMSException;
	public Boolean reAssignTheLead(LeadForm commonForm) throws LMSException;
	public LeadDetailDTO viewLeadDetail(Long LeadID) throws LMSException;
	public ArrayList<BulkFeasibilityDTO> listAssignedLeadsExcel(String loginID,String startDate,String endDate) throws LMSException;
	public BulkUploadMsgDto uploadAssignedMatrix(FormFile file,HttpServletRequest request) throws LMSException ;
	public ArrayList<UserDto> getChannelPartnerList(ArrayList circleList, ArrayList lobList) throws LMSException;//changed by sudhanshu
	public ArrayList<UserDto> getZonalCoordinatorList(ArrayList circleList, ArrayList lobList) throws LMSException;//changed by sudhanshu
	public ArrayList<UserDto> getChannelPartnerListForLead(ArrayList circleList, LeadDetailDTO detailDTO, ArrayList lobList)throws LMSException;
	public String getUserID(String mobileNo) throws LMSException;
	public BulkUploadMsgDto bulkUploadLeadFinalStatus(FormFile file, HttpServletRequest request)throws LMSException;
    public ArrayList<UserDto> getUserListForNewProduct(LeadDetailDTO leadDetails, String userLoginId) throws LMSException;
    public int getLeadStatus(long leadId) throws Exception;
    public ArrayList<String> getSiteClosureList(String lob) throws Exception;
	public String checkAsssignedPrimaryUser(Long leadId) throws Exception;

}
