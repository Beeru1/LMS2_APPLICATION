package com.ibm.lms.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;


import com.ibm.km.dto.KmActorMstr;
import com.ibm.lms.dto.LogsDTO;
import com.ibm.lms.dto.BulkAssignmentCircleDTO;
import com.ibm.lms.dto.BulkAssignmentLobDTO;
import com.ibm.lms.dto.BulkCityDTO;
import com.ibm.lms.dto.BulkCityZoneCodeCTO;
import com.ibm.lms.dto.BulkPinCodeDTO;
import com.ibm.lms.dto.BulkRsuDTO;
import com.ibm.lms.dto.CircleDTO;
import com.ibm.lms.dto.CircleForProductDTO;
import com.ibm.lms.dto.CircleForProductLob;
import com.ibm.lms.dto.CityDTO;
import com.ibm.lms.dto.CityZoneDTO;

import com.ibm.lms.dto.AlertDTO;
import com.ibm.lms.dto.ActorDto;
import com.ibm.lms.dto.AssignmentReportDTO;
import com.ibm.lms.dto.ColumnDto;
import com.ibm.lms.dto.FidDto;
import com.ibm.lms.dto.LOBDTO;
import com.ibm.lms.dto.LeadDetailsDTO;
import com.ibm.lms.dto.LeadStatusDTO;
import com.ibm.lms.dto.MasterDataDTO;
import com.ibm.lms.dto.NoneditablefieldsDTO;
import com.ibm.lms.dto.PINCodeDTO;
import com.ibm.lms.dto.ProductDTO;
import com.ibm.lms.dto.ProductLobDTO;
import com.ibm.lms.dto.RSUDTO;
import com.ibm.lms.dto.ReportColumnDto;
import com.ibm.lms.dto.ReportsDTO;
import com.ibm.lms.dto.RequestCategoryDTO;
import com.ibm.lms.dto.RequestTypeDTO;
import com.ibm.lms.dto.SourceDTO;
import com.ibm.lms.dto.StateDTO;
import com.ibm.lms.dto.SubSourceDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.dto.ZoneDTO;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.wf.dto.BulkMatrixDownloadDTO;
import com.ibm.lms.wf.dto.UserDownloadDTO;

public interface MasterService {
	
	public  ArrayList<ProductDTO> getProductList() throws LMSException;
	public  ArrayList<LOBDTO> getLobList() throws LMSException;
	public  int getSubStatusCode(int statusId,int subStatusId,int lobId) throws LMSException;
	public  ArrayList<CircleDTO> getCircleList() throws LMSException;
	public  ArrayList<CircleDTO> getCircleUserList() throws LMSException;
	public  ArrayList<PINCodeDTO> getPinCodeList() throws LMSException;
	public  ArrayList<StateDTO> getStateList() throws LMSException;
	public  ArrayList<RequestTypeDTO> getRequestTypeList() throws LMSException;
	public  ArrayList<SourceDTO> getSourceList() throws LMSException;
	public  ArrayList<SubSourceDTO> getSubSourceList() throws LMSException;
	public  ArrayList<KmActorMstr> getActorList(String actorID) throws LMSException;
	public  ArrayList<ProductDTO> getTelemediaProductList() throws LMSException;
	public  ArrayList<ProductDTO> getMobilityProductList() throws LMSException;
	public  ArrayList<Integer> getRestrictedProductList() throws LMSException;
	//added by Nancy Agrawal
	public ArrayList<AlertDTO> getAlertList()  throws LMSException;
	public  boolean isValidCircle(int circleId,int productLobId) throws LMSException;
	public boolean isValidUserCircle(int circleId) throws LMSException;
	//public  boolean isValidCity(String cityCode,String string) throws LMSException;
	//public  boolean isValidZone(String zoneCode,String cityCode) throws LMSException;
	public  boolean isValidRsu(String rsuCode,String cityZoneCode) throws LMSException;
	
	public  boolean isValidRsu(String rsuCode) throws LMSException;

	public  boolean isValidPincode(String pincode,String cityZoneCode) throws LMSException;
	public  boolean isValidProduct(int productId) throws LMSException;
	public  boolean isValidProductLobId(int productLobId) throws LMSException;
	public  boolean isValidUserLoginId(String userLoginId) throws LMSException;
	public  boolean isValidState(String stateCode) throws LMSException;
	public  boolean isValidSource(int sourceId) throws LMSException;
	public  boolean isValidSubSource(int subSourceId) throws LMSException;
	public  boolean isValidRequestType(int requestId) throws LMSException;
	public 	boolean isValidActorId(int actorId) throws LMSException;
	public  boolean isValidLeadforFeasibility(Long leadID,int status) throws LMSException;
	public 	int getProductLobId(int productId) throws LMSException;


	public  ArrayList<CityDTO> getCityList() throws LMSException;
	public  ArrayList<ZoneDTO> getZoneList() throws LMSException;  
	public  ArrayList<RSUDTO> getRsuList() throws LMSException;
		
	public  ArrayList<LeadStatusDTO> getLeadStatusList() throws LMSException;
	public  ArrayList<LeadStatusDTO> getLeadSubStatusList(int statusId,int lobId) throws LMSException;
	public  ArrayList<LeadStatusDTO> getLeadSubSubStatusList(int statusId,int prodLobId,int subStatusId) throws LMSException;
	public LeadStatusDTO getLeadSubStatusList(int statusId,int lobId,int subStatusId) throws LMSException;
	public  LeadStatusDTO addSubStatus(int statusId,int lobId,String subStatusName) throws LMSException;
	//public  ArrayList<LeadStatusDTO> getLeadSubStatusList(int statusId,int lobId,int subStatusId) throws LMSException;
	public  String updateSubSubStatusDetails(int statusId,int subStatusId,int productLobId,String subSubStatusName,String subSubStatusDisplay,int subSubStatusId,int subStatusCode) throws LMSException;
	
	public  ArrayList<LeadStatusDTO> getLeadStatusCallCenterList() throws LMSException;
	
	public  ArrayList<LeadStatusDTO> getLeadStatusDiallerList(int leadStatus, int leadSubStatus, int productLob) throws LMSException;
	public  ArrayList<LeadStatusDTO> getLeadSubSubStatusDiallerList(int leadStatus, int leadSubStatus, int subsubstatusid, int productLob) throws LMSException;
	public  HashMap<Integer, LeadStatusDTO> getLeadStatusIdCodeMap() throws LMSException;	
	public LeadStatusDTO getLeadSubSubStatusList(int statusId,int lobId,int subStatusId,int subSubStatusId) throws LMSException;
	public  ArrayList<CityDTO> getCityForCircle(int circleMstriD) throws LMSException;
	public  ArrayList<CityDTO> getCityForZone(String cityCode) throws LMSException;
	public  ArrayList<CityZoneDTO> getCityZoneOnCityChange(String cityCode) throws LMSException;
	public  String getZoneCode(String cityCode) throws LMSException;
	public  String getCityForPinCode(String pinCode) throws LMSException;
	public  String getDataForPinCode(String pinCode, int circleMstrId) throws LMSException;
	public  String getLeadStatusIfLeadNotServiceAble(int product_id) throws LMSException;
	public  ArrayList<ZoneDTO> getZoneForCircle(int circleID) throws LMSException;
	public  ArrayList<CircleForProductDTO> getCircleForProduct(int productLobId) throws LMSException;
	public  ArrayList<PINCodeDTO> getPinCodeForCity(String CityCode) throws LMSException;
	public  ArrayList<ZoneDTO> getZoneForCity(String cityCode) throws LMSException;  
	public  ArrayList<RSUDTO> getRsuForZone(String zoneCode) throws LMSException;
	public  ArrayList<ProductLobDTO> getProductLobList() throws LMSException;
	public  ArrayList<ReportsDTO> getReportList() throws LMSException;
	public  ArrayList<ReportsDTO> getReportListDayMonthWise() throws LMSException;
	public 	ArrayList<LogsDTO> getLogsList() throws LMSException;
	public  String getReportName(int reportId) throws LMSException;
	public String getMasterTableData(int mstr , int lobID) throws LMSException;
	public  ArrayList<MasterDataDTO> getMasterList() throws LMSException;
	public  ArrayList<ProductDTO> getDTHProductList() throws LMSException;
	public ArrayList<BulkAssignmentCircleDTO> getCircleTypeList() throws LMSException;
	public ArrayList<ProductLobDTO> getLobsNameList()throws LMSException;
	public ArrayList<BulkMatrixDownloadDTO> getAssignmentDownloadData(int circleTableId, int lobTableId,String usertype) throws LMSException;
	
	
	//Added by Neetika Mittal for zone Code Validation for open/qualified leads on 18-Nov-2013
	
	public  boolean isValidZoneCode(int circleId,int prodid,String zoneCode) throws LMSException;
	public  boolean isValidCityFromZone(String cityCode,String zoneCode) throws LMSException;
	public boolean isValidCityZoneCode(String cityId,String zonecode) throws LMSException;
	public boolean isValidCityZoneCodeReverse(int circleId,int prodid,String cityzoneCode) throws LMSException;
	public boolean isValidCityReverse(int circleId,int prodid,String citycode) throws LMSException;
	public boolean isValidPinReverse(int circleId,int prodid,String pincode) throws LMSException;
	public boolean isValidCityZoneCodeId(String cityZoneCode, String city)throws LMSException;
	public boolean isValidProductid(int productId, int productLobId)throws LMSException;
	public boolean isValidZoneId(String cityZone, int circleId, int productLobId)throws LMSException;
	public boolean isValidCity(String city, String cityZone)throws LMSException;
	public  boolean isValidCircleUsingProd(int circleId,int prodid) throws LMSException;
	
	
	//	 Added By Bhaskar
	
	public boolean isValidCityId(String city, String cityZone)throws LMSException;
	public boolean isValidCircleId(int circleId, int productLobId)throws LMSException;
	public boolean isValidPincodeNew(String pincode, String cityZoneCode)throws LMSException;
	public boolean isValidRsuNew(String rsu, String cityZoneCode)throws LMSException;
	public ArrayList<CircleForProductLob> getCircleForProductName(int productLobId)throws LMSException;
	
	//Added by sugandha for Bulk User creation for LMS_PHASE_2 on 23-Nov-2013
	public boolean isValidBulkUserCircle_LOB(String circleListStr,int productLobId)throws LMSException;
	public boolean isValidCityZoneCode(String cityzoneCode, int circleId, int productLobId)throws LMSException;
	public boolean isValidUserZoneCode(String zoneCode, int circleId,int productLobId)throws LMSException;
	/* Added by Parnika for LMS Phase 2 */
	
	public int getCircleIdValue(int circleMstrId) throws LMSException;
	public  ArrayList<LOBDTO> getLobListBasedOnUser(ArrayList lobList) throws LMSException;
	public  ArrayList<LeadStatusDTO> getLeadStatusListMTD() throws LMSException;
	
	/* End of changes by Parnika */
	/* Added by amarjeet*/
	public String getZoneCodeforCity(String cityCode) throws LMSException;
	public ArrayList<CityDTO> getCityForCircleForDialer(int circleMstriD) throws LMSException;
	public  ArrayList<CircleDTO> getCircleForUser(String userLoginId)throws LMSException;
	public  ArrayList<LOBDTO> getLobForUser(String userLoginId) throws LMSException;
	public  ArrayList<LOBDTO> getLobListForUser(String userLoginId) throws LMSException;
	public  ArrayList<CircleDTO> getCircleForUserLob(String userLoginId , int lobId)throws LMSException ;
	public ArrayList<CircleDTO> getCircleForLob(int productLobId) throws DAOException ;
	public int getLobId(String lobName) throws DAOException ;
	public int getProductId(long leadId) throws DAOException;
	public int getProduct(long leadId) throws DAOException;
	public  ArrayList<UserMstr> getLogedInUserDetails(String userLoginId , int lobId) throws LMSException ;
	public  String getCityZoneCode(String zoneId , String zoneFlag) throws LMSException ;
	public String getParameterName(String formName) throws LMSException ;
	public boolean validateOlm(String olmId) throws DAOException;
	public boolean validateProduct(String product) throws DAOException;
	/* Added by amarjeet*/
	public ArrayList<ProductLobDTO> getAssignProductLobList(ArrayList lobList)throws LMSException;
	public boolean isValidCircleIdNew(int circleId, int productLobId, String userLoginId)throws LMSException;
	public boolean isValidCityZoneCodeNewOne(String cityzoneCode, int circleId, int productLobId)throws LMSException;
	
	public ArrayList<UserDownloadDTO> getuserMasterDownloadData(int circleTableId, int productlobId, int selectActorId)throws LMSException;
	public JSONObject getLobElementsAsJson(String olmId) throws Exception ;
	public ArrayList<BulkPinCodeDTO> getAssignmentMatrixDataForPinCode(String pincode, int circleId, int productLobId)throws LMSException;
	public ArrayList<BulkRsuDTO> getAssignmentMatrixDataForRsuCode(String rsu, int circleId, int productLobId)throws LMSException;
	public ArrayList<BulkCityZoneCodeCTO> getAssignmentMatrixDataForCityZoneCode(String cityZoneCode, int circleId, int productLobId)throws LMSException;
	public ArrayList<BulkCityDTO> getAssignmentMatrixDataForCityCode(String city,int circleId,int productLobId )throws LMSException;
	public   int getZoneId(String zoneCode) throws LMSException ;
	public   int getCityZoneId(String cityZoneCode) throws LMSException ;
	/* Added by Parnika for Checking Valid Rsu within the circle */
	
	public boolean isValidRsuInCircle(String rsu, String LeadId) throws LMSException;
	public ArrayList<ProductDTO> getMobilityProductListNew()throws LMSException;
	public ArrayList<ProductDTO> getOtherProductsList()throws LMSException;
	public boolean isvalidCampaignField(String campaign)throws LMSException;
	
	/* End of changes By parnika */
	
	/* Added by Parnika for Checking Valid Rsu within the circle */
	
	public  ArrayList<ReportsDTO> getReportListAdmin() throws LMSException;
	public ArrayList<ProductDTO> getProductListByLob(int productId,	int productLobId)throws LMSException;
	public String getProductLobName(int productLobId)throws LMSException;
	
	//public ArrayList<RequestCategoryDTO> getRequsetCategoryList()throws LMSException;
	public boolean isValidProductLobForRsuCode(int prodid) throws LMSException;
	public boolean isValidRsuReverse(int circleId, int prodid, String rsuCode)throws LMSException;
	public boolean isValidRequestCategory(String productId)throws LMSException;
	public boolean isValidRequestCategoryForLob(String productLobId)throws LMSException;
	public boolean isValidCityBasedRsuReverse(int circleId, int prodid, String cityCode)throws LMSException;
	public boolean isValidCityZoneBasedRsuReverse(int circleId, int prodid,String rsuCode, String cityZoneCode, String cityCode)throws LMSException;
	public boolean isValidCityZoneBasedRsuReverseNew(int circleId, int prodid, String cityZoneCode)throws LMSException;
	public ArrayList<RSUDTO> getRsuForCircleChange(int circleMstriD)throws LMSException;
	public ArrayList<RSUDTO> getRsuForCityChange(int circleMstriD, String cityCode)throws LMSException;
	public ArrayList<RSUDTO> getRsuForCityZoneCodeChange(int circleMstriD, String cityCode,String cityzonecode)throws LMSException;
	public String getCircleName(String circleMstrId)throws LMSException;
	
	
	/* End of changes By parnika */
//ADDED BY SATISH
	
	
	public NoneditablefieldsDTO getNonEditableFiedls(String LeadID)throws LMSException;
	
	public ArrayList<LeadDetailsDTO> getPreviousoperatornameList()throws LMSException;
	//END BY SATISH

	public String getAlertMsg(String alert,String source,String type)  throws LMSException; 
	public String getStatus(String alert,String source) throws LMSException;
	public String getSubject(String alert,String source) throws LMSException;
	public String getEmail(String alert,String source) throws LMSException;
	public String getSms(String alert,String source) throws LMSException;
	public String getCount(String alert,String source) throws LMSException;
	public String getPeriod(String alert,String source) throws LMSException;
	public String getAlertType(String alert,String source) throws LMSException;


	public String getSource(String alert)throws LMSException;


	// added by Nehil for report configuration
	public List<ActorDto> getActors() throws LMSException;

	public  String updateSubStatusDetails(int leadStatusId,int subStatusId,int productLobId,String leadSubStatus,String leadSubStatusDisplay) throws LMSException;
	public List<ReportsDTO> getDynamicReports() throws LMSException;
	public ReportsDTO getReportConfiguration(String reportConfigId, int actorId, int selectedLOB)throws LMSException;
	public void insertReportConfiguration(ReportsDTO aDto) throws LMSException;
	public boolean checkIfConfigurationExist(String reportConfigId) throws LMSException;
	public void updateReportConfiguration(ReportsDTO aDto) throws LMSException;
	public List<ColumnDto> getColumns(String reportConfigId, int actorId, int lobId) throws LMSException;
	public int getReportConfigurationId(int reportId, int actorId, int lobId) throws LMSException;
	public void updateReportColumns(String reportConfigId, int[] reportColumns, String[] displayNames, int reportId, int actorId, int lobId) throws LMSException;
	public List<ProductDTO> getAllProducts() throws LMSException;
	public List<ProductDTO> getProductListByLob(int lobid) throws LMSException;
	public List<FidDto> getSelectedFidsForReport(String reportConfigId) throws LMSException;
	public List<FidDto> getAllFids(String reportConfigId) throws LMSException;
	public List<FidDto> getAllActiveFids() throws LMSException;
	public void updateProducts(String reportConfigId, int lobId, String[] products) throws LMSException;
	public List<String> getSelectedProducts(String reportConfigId, int lobId) throws LMSException;
	public void updateFids(String reportConfigId, String[] fids) throws LMSException;
	public List<ColumnDto> getAllColumns() throws LMSException;
	public int addDynamicReport(String reportName) throws LMSException;
	public boolean isReportExist(String reportName) throws LMSException;
	public void deleteReportConfiguration(String reportConfigId) throws LMSException;
	public String getReportNameFromReportId(int reportId) throws LMSException;
	public void deleteReport(int reportId) throws LMSException;
	public  LeadStatusDTO addSubSubStatus(int statusId,int productLobId,int subStatusId,String leadSubSubStatus) throws LMSException;
	public List<ProductDTO> getproductForLob(int productLobId)throws LMSException;
	
	
	public  int getProductIdByMobile(long mobileNumber) throws LMSException;
	public  int getProductIdByTid(Long tid) throws LMSException;
	
	
	
	
	
	public ArrayList<ProductDTO> getFourgProductList() throws LMSException;
	public ArrayList<PINCodeDTO>getPinCodeListFor4G () throws LMSException;
	
	public int getCircleMstrIdValue(int circleId,int lobId) throws LMSException;
	public ArrayList<AssignmentReportDTO> getApproversList(String OlmsId)throws LMSException;
	
}
	