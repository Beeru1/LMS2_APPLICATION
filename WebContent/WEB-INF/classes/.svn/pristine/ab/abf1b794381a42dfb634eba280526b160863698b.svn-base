package com.ibm.lms.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibm.km.dto.KmActorMstr;
import com.ibm.lms.dto.LeadDetailsDTO;
import com.ibm.lms.dto.LogsDTO;
import com.ibm.lms.dto.AssignmentReportDTO;
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
import com.ibm.lms.dto.ActorDto;
import com.ibm.lms.dto.ColumnDto;
import com.ibm.lms.dto.FidDto;
import com.ibm.lms.dto.AlertDTO;
import com.ibm.lms.dto.LOBDTO;
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


public interface MasterDao {

	public  int getSubStatusCode(int statusId,int subStatusId,int lobId) throws DAOException;
	public  ArrayList<ProductDTO> getProductList() throws DAOException;
	public  ArrayList<ZoneDTO> getZoneList() throws DAOException;	
	public  ArrayList<PINCodeDTO> getPINCodeList() throws DAOException;
	public  ArrayList<CityDTO> getCityList() throws DAOException;
	public  ArrayList<LOBDTO> getLobList() throws DAOException;
	public  ArrayList<CircleDTO> getCircleList() throws DAOException;
	public  ArrayList<CircleDTO> getCircleUserList() throws DAOException;
	public  ArrayList<StateDTO> getStateList() throws DAOException;
	public  ArrayList<SourceDTO> getSourceList() throws DAOException;
	public  ArrayList<SubSourceDTO> getSubSourceList() throws DAOException;
	public  ArrayList<KmActorMstr> getActorList(String actorID) throws DAOException;
	public  ArrayList<ProductDTO> getTelemediaProductList() throws DAOException;
	public  ArrayList<ProductDTO> getMobilityProductList() throws DAOException; 
	public  ArrayList<Integer> getRestrictedProductList() throws DAOException;
	public  ArrayList<ProductDTO> getDTHProductList() throws DAOException;
	public ArrayList<ProductLobDTO> getProductLobList() throws DAOException;
	public ArrayList<ReportsDTO> getReportListDayMonthwise() throws DAOException;
	public boolean isValidOlm(String olmId) throws DAOException;
	public boolean isValidProuct(String product) throws DAOException;
	
	public  boolean isValidCircle(int circleId) throws DAOException;
	public  boolean isValidUserCircle(int circleId) throws DAOException;
	public  boolean isValidCity(String cityCode,String cityZone) throws DAOException;
//	public  boolean isValidZone(String zoneCode,String cityCode) throws DAOException;
	public  boolean isValidRsu(String rsuCode,String cityZoneCode) throws DAOException;
	public 	boolean isValidRsu(String rsuCode) throws DAOException;
	boolean isValidPincode(String pincode,String cityZoneCode) throws DAOException;
	public  boolean isValidProduct(int productId) throws DAOException;
	public  boolean isValidUserLoginId(String userLoginId) throws DAOException;
	public 	boolean isValidProductLobId(int productLobId) throws DAOException;
	public 	boolean isValidActorId(int actorId) throws DAOException;
	public  boolean isValidState(String stateCode) throws DAOException;
	public  boolean isValidSource(int sourceId) throws DAOException;
	public  boolean isValidSubSource(int subSourceId) throws DAOException;
	public  boolean isValidRequestType(int requestId) throws DAOException;
	public 	int getProductLobId(int productId) throws DAOException;
	public  boolean isValidLeadforFeasibility(Long leadId,int status) throws DAOException;
	
	public  ArrayList<RequestTypeDTO> getRequestTypeList() throws DAOException;
	public  ArrayList<RSUDTO> getRsuList() throws DAOException;
	
	public  ArrayList<LeadStatusDTO> getLeadStatusList() throws DAOException;	
	public  ArrayList<LeadStatusDTO> getLeadStatusCallCenterList() throws DAOException;
	public  HashMap<Integer, LeadStatusDTO> getLeadStatusIdCodeMap() throws DAOException;	

	
	public  ArrayList<LeadStatusDTO> getLeadStatusDiallerList() throws DAOException;
	public  ArrayList<LeadStatusDTO> getLeadStatusDiallerList(String queryString ,int productLob) throws DAOException;
	public  ArrayList<LeadStatusDTO> getLeadSubSubStatusDiallerList(String queryString ,int productLob , int substatusId) throws DAOException;
	public  ArrayList<CityDTO> getCityForCircle(int circleMstriD) throws DAOException;
	public  ArrayList<CityDTO> getCityForZone(String cityCode ) throws DAOException;
	public  ArrayList<CityZoneDTO> getCityZoneOnCityChange(String cityCode ) throws DAOException;
	public  String getZoneCode(String cityCode ) throws DAOException;
	public  String getCityForPinCode(String pinCode ) throws DAOException;
	public  String getDataForPinCode(String pinCode,int circleMstrId ) throws DAOException;
	public  String getLeadStatusIfLeadNotServiceAble(int product_id) throws DAOException;
	public  ArrayList<ZoneDTO> getZoneForCircle(int circleID) throws DAOException;
	public  ArrayList<CircleForProductDTO> getCircleForProduct(int productLobId) throws DAOException;
	public  ArrayList<PINCodeDTO> getPINCodeForCity(String cityCode) throws DAOException;
	public  ArrayList<ZoneDTO> getZoneForCity(String cityCode) throws DAOException;
	public  ArrayList<RSUDTO> getRsuForZone(String zoneCode) throws DAOException;

	public  ArrayList<ReportsDTO> getReportList() throws DAOException;
	public  ArrayList<LogsDTO> getLogsList() throws DAOException;
	public  String getReportName(int reportId) throws DAOException;
	public String getMasterData(String masterTable, int lobId,int mstr) throws DAOException;
	public  ArrayList<MasterDataDTO> getMasterList() throws DAOException;
	public ArrayList<BulkAssignmentCircleDTO> getCircleTypeList() throws DAOException;
public ArrayList<ProductLobDTO> getLobsNameList() throws DAOException;
public ArrayList<BulkMatrixDownloadDTO> getAssignmentDownloadData(int circleTableId, int lobTableId,String usertype) throws DAOException;
	//Added by Neetika on 18-Nov-2013 for Open leads new column Zone code
	public  boolean isValidZoneCode(int circleId,int prodid,String zoneCode) throws DAOException;
	public  boolean isValidCityFromZone(String city,String zoneCode) throws DAOException;//this is from zone master
	public  boolean isValidCityZoneCode(String city,String zoneCode) throws DAOException;// this is from city zon master
	public  boolean isValidCityZoneCodeReverse(int circleId,int prodid,String cityzoneCode) throws DAOException;// reverse
	public  boolean isValidCityReverse(int circleId,int prodid,String cityzoneCode) throws DAOException;// reverse
	public  boolean isValidPinReverse(int circleId,int prodid,String pincode) throws DAOException;// reverse
	public boolean isValidCityZoneCodeId(String cityZoneCode, String city)throws DAOException;
	public boolean isValidProductid(int productId, int productLobId)throws DAOException;
	public boolean isValidZoneId(String cityZone, int circleId, int productLobId)throws DAOException;
	public boolean isValidCity(String cityId, int circleId)throws DAOException;
	public boolean isValidCircle(int circleId, int productLobId)throws DAOException;
	public boolean isValidCircleUsingProd(int circleId, int prodid)throws DAOException;
	public boolean isValidCircleId(int circleId, int productLobId)throws DAOException;
	public boolean isValidCityId(String city, String cityZone)throws DAOException;
	public boolean isValidPincodeNew(String pincode, String cityZoneCode)throws DAOException;
	public boolean isValidRsuNew(String rsu, String cityZoneCode)throws DAOException;
	public ArrayList<CircleForProductLob> getCircleForProductName(int productLobId)throws DAOException;
	//Added by sugandha 
	public boolean isValidBulkUserCircle_LOB(String circleListStr, int productLobId)throws DAOException;
	public boolean isValidCityZoneCode(String cityzoneCode, int circleId, int productLobId)throws DAOException;
	
	/*Added By Parnika for LMS Phase 2 */
	
	public ArrayList<CircleDTO> getCircleListBasedOnLob(int lobId) throws DAOException;
	public ArrayList<ZoneDTO> getZoneListBasedOnCircleAndType(int selectedTypeId, int circleMstrId) throws DAOException;
	public ArrayList<ZoneDTO> getZoneListBasedOnCircle(int circleMstrId) throws DAOException;
	public ArrayList<CityDTO> getCityListBasedOnZone(String zoneCode) throws DAOException;
	public ArrayList<CityZoneDTO> getCityZoneListBasedOnCity(String cityCode) throws DAOException;
	public ArrayList<PINCodeDTO> getPincodeListBasedOnCityCode(String cityZoneCode) throws DAOException;
	public ArrayList<RSUDTO> getRsuBasedOnCityCode(String cityZoneCode) throws DAOException;
	public int getCircleIdValue(int circleMstrId) throws DAOException;
	public ArrayList<LOBDTO> getLobListBasedOnUser(ArrayList lobList) throws DAOException;
	public  boolean isValidRsuInCircle(String rsuCode,String leadId) throws DAOException;
	public  ArrayList<LeadStatusDTO> getLeadStatusListMTD() throws DAOException;
	
	/*End of changes by Parnika */
	
	
	public boolean isValidUserZoneCode(String zoneCode, int circleId,int productLobId)throws DAOException ;//Added by sugandha 

	/* Added by amarjeet*/
	public String getZoneCodeforCity(String cityCode) throws DAOException;
	public ArrayList<CityDTO> getCityForCircleForDialer(int circleMstriD) throws DAOException ;
	public   ArrayList<CircleDTO> getCircleForUser(String userLoginId) throws DAOException;
	public  ArrayList<LOBDTO> getLobForUser(String userLoginId) throws DAOException;
	public ArrayList<ProductLobDTO> getAssignProductLobList(ArrayList lobList)throws DAOException ;
	public ArrayList getCircleListBasedOnLobNew(int lobId, String userLoginId)throws DAOException;
	public boolean isValidCircleIdNew(int circleId,int productLobId, String userLoginId)throws DAOException;
	public boolean isValidCityZoneCodeNewOne(String cityzoneCode, int circleId, int productLobId)throws DAOException;
	public  ArrayList<CircleDTO> getCircleForUserLob(String userLoginId , int lobId) throws DAOException;
	public  ArrayList<LOBDTO> getLobListForUser(String userLoginId) throws DAOException;

	public String getCircleIdValue(String productLobId , String circleName) throws DAOException;

	public ArrayList<UserDownloadDTO> getuserMasterDownloadData(int circleTableId, int productlobId, int selectActorId)throws DAOException;
	public ArrayList<CircleDTO> getCircleListBasedOnLobNewUser(int productlobId, String userLoginId)throws DAOException;
	public ArrayList<BulkPinCodeDTO> getAssignmentMatrixDataForPinCode(String pincode, int circleId, int productLobId)throws DAOException;
	public ArrayList<BulkRsuDTO> getAssignmentMatrixDataForRsuCode(String rsu, int circleId, int productLobId)throws DAOException;
	public ArrayList<BulkCityZoneCodeCTO> getAssignmentMatrixDataForCityZoneCode(String cityZoneCode, int circleId, int productLobId)throws DAOException;
	public ArrayList<BulkCityDTO> getAssignmentMatrixDataForCityCode(String city, int circleId, int productLobId)throws DAOException;
	public ArrayList getZoneListBasedOnCircleNew(int circleMstrId)throws DAOException;
	public ArrayList getElementsAsJsonCityNew(int circleMstrId)throws DAOException;
	public ArrayList getElementsAsJsonCityZoneNew(int circleMstrId)throws DAOException;
	public ArrayList getCityZoneListBasedOnZone(String zoneCode)throws DAOException;
	
	public int getLobId(String lobName) throws DAOException ;
	public ArrayList<CircleDTO> getCircleForLob(int productLobId) throws DAOException ;
	public  String  getCityZoneCode(String zoneId , String zoneFlag) throws DAOException;
	public String getParameterName(String formName) throws DAOException ;
	
	/* Added by amarjeet*/
	//public ArrayList<LeadStatusDTO> getLeadSubStatusDialerList(int leadStatusId, int leadSubStatusId)throws DAOException ;
	
	public ArrayList getLeadSubStatusList1(String leadStatusId,int lobId)throws DAOException ;
	public ArrayList<UserMstr> getChannelPartnerBasedOnLobNew(int lobId, String circleId) throws DAOException;

	public ArrayList<ProductDTO> getMobilityProductListNew()throws DAOException;
	public ArrayList<ProductDTO> getOtherProductsList()throws DAOException;

	public  ArrayList<UserMstr> getLogedInUserDetails(String userLoginId , int lobId) throws DAOException;
	public  int getZoneId(String zoneCode) throws DAOException;
	public  int getCityZoneId(String cityZoneCode) throws DAOException;
	public boolean isvalidCampaignField(String campaign)throws DAOException;
	
	public  ArrayList<ReportsDTO> getReportListAdmin() throws DAOException;
	public ArrayList<ProductDTO> getProductListByLob(int productId,	int productLobId)throws DAOException;
	public String getProductLobName(int productLobId) throws DAOException;
	
	//public ArrayList<RequestCategoryDTO> getRequsetCategoryList()throws DAOException;
	public boolean isValidProductLobForRsuCode(int prodid)throws DAOException;
	public boolean isValidRsuReverse(int circleId, int prodid, String rsuCode)throws DAOException;
	public ArrayList<RequestCategoryDTO> getRequestListBasedOnProduct(int productId,int productLobId) throws DAOException;
	public boolean isValidRequestCategory(String productId)throws DAOException;
	public boolean isValidRequestCategoryForLob(String productLobId)throws DAOException;
	public ArrayList<RequestCategoryDTO> getRequestListBasedOnProductLob(int productLobId)throws DAOException;
	public boolean isValidCityBasedRsuReverse(int circleId, int prodid,String cityCode)throws DAOException;
	public boolean isValidCityZoneBasedRsuReverse(int circleId, int prodid,String rsuCode, String cityZoneCode, String cityCode)throws DAOException;
	public boolean isValidCityZoneBasedRsuReverseNew(int circleId, int prodid,String cityZoneCode)throws DAOException;
	public ArrayList<RSUDTO> getRsuForCircleChange(int circleMstriD)throws DAOException;
	public ArrayList<RSUDTO> getRsuForCityChange(int circleMstriD, String cityCode)throws DAOException;
	public ArrayList<RSUDTO> getRsuForCityZoneCodeChange(int circleMstriD, String cityCode,String cityzonecode)throws DAOException;
	public String getCircleName(String circleMstrId)throws DAOException;
	public int getProductId(long leadId) throws DAOException ;
	public int getProduct(long leadId) throws DAOException ;
	//added by Nancy Agrawal.
	public ArrayList<AlertDTO> getAlertList() throws DAOException;
	public String getAlertMsg(String alert,String source,String type) throws DAOException;
	public String getStatus(String alert,String source) throws DAOException;
	public String getSubject(String alert,String source) throws DAOException;
	public String getSms(String alert,String source) throws DAOException;
	public String getEmail(String alert,String source) throws DAOException;
	public String getCount(String alert,String source) throws DAOException;
	public String getPeriod(String alert,String source) throws DAOException;
	public String getAlertType(String alert,String source) throws DAOException;
	public String getSource(String alert) throws DAOException;

	// added by Nehil for report configuration
	public List<ActorDto> getActors() throws DAOException;
	public List<ReportsDTO> getDynamicReports() throws DAOException;
	public ReportsDTO getReportConfiguration(String reportConfigId, int actorId, int lobId) throws DAOException;
	public void insertReportConfiguration(ReportsDTO aDto) throws DAOException;
	public boolean checkIfConfigurationExist(String reportConfigId) throws DAOException;
	public void updateReportConfiguration(ReportsDTO aDto) throws DAOException;
	public List<ColumnDto> getColumns(String reportConfigId, int actorId, int lobId) throws DAOException;
	public int getReportConfigurationId(int reportId, int actorId, int lobId) throws DAOException;
	public void updateReportColumns(String reportConfigId, int[] reportColumns, String[] displayNames, int reportId, int actorId, int lobId) throws DAOException;
	public List<ProductDTO> getAllProducts() throws DAOException;
	public List<ProductDTO> getProductListByLob(int lobid) throws DAOException;
	public List<FidDto> getSelectedFidsForReport(String reportConfigId) throws DAOException;
	public List<FidDto> getAllFids(String reportConfigId) throws DAOException;
	public List<FidDto> getAllActiveFids() throws DAOException;
	public void updateProducts(String reportConfigId, int lobId, String[] products) throws DAOException;
	public List<String> getSelectedProducts(String reportConfigId, int lobId) throws DAOException;
	public void updateFids(String reportConfigId, String[] fids) throws DAOException;
	public List<ColumnDto> getAllColumns() throws DAOException;
	public int addDynamicReport(String reportName) throws DAOException;
	public boolean isReportExist(String reportName) throws DAOException;
	public void deleteReportConfiguration(String reportConfigId) throws DAOException;
	public String getReportNameFromReportId(int reportId) throws DAOException;
	public void deleteReport(int reportId) throws DAOException;
	public  ArrayList<LeadStatusDTO> getLeadSubStatusList(int statusId,int lobId) throws DAOException;
	public  LeadStatusDTO getLeadSubStatusList(int statusId,int lobId,int subStatusId) throws DAOException;
//	public  ArrayList<LeadStatusDTO> getLeadSubStatusList(int statusId,int lobId,int subStatusId) throws DAOException;
	
	public String updateSubStatusDetails(int statusId,int subStatusId,int productLobId,String subStatusName,String subStatusDisplay) throws DAOException;
	public LeadStatusDTO addSubStatus(int statusId,int lobId,String subStatusName) throws DAOException;
	public  LeadStatusDTO getLeadSubSubStatusList(int statusId,int prodLobId,int subStatusId,int subSubStatusId) throws DAOException;
	public  ArrayList<LeadStatusDTO> getLeadSubSubStatusList(int statusId,int prodLobId,int subStatusId) throws DAOException;
	public String updateSubSubStatusDetails(int statusId,int subStatusId,int productLobId,String subSubStatusName,String subSubStatusDisplay,int subSubStatusId,int subStatusCode) throws DAOException;
	public LeadStatusDTO addSubSubStatus(int statusId,int productLobId,int subStatusId,String leadSubSubStatus) throws DAOException;
	public List<ProductDTO> getproductForLob(int productLobId)throws DAOException;
	
	
	
	public ArrayList<ProductDTO> getFourgProductList()throws DAOException;
	public int getProductIdByMobile(long mobileNumber) throws DAOException ;
	public int getProductIdByTid(Long tid) throws DAOException ;
	public ArrayList<PINCodeDTO>getPinCodeListFor4G () throws DAOException;
	
	public int getCircleMstrIdValue(int circleId,int lobId) throws DAOException;
	public ArrayList<AssignmentReportDTO> getApproversList(String olmid)throws DAOException;
	
     //added by satish
	public NoneditablefieldsDTO getNonEditableFiedls(String leadID)throws DAOException;
	public ArrayList<LeadDetailsDTO> getPreviousoperatornameList()throws DAOException;
	//added by satish
	
}
