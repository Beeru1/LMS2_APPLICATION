
package com.ibm.lms.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ibm.km.dto.KmActorMstr;
import com.ibm.lms.dto.LogsDTO;
import com.ibm.lms.common.LMSStatusCodes;
import com.ibm.lms.dao.MasterDao;
import com.ibm.lms.dao.impl.MasterDaoImpl;
import com.ibm.lms.dao.impl.UserMstrDaoImpl;
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
import com.ibm.lms.engine.util.ServerPropertyReader;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.wf.dao.AssignedLeadsDAO;
import com.ibm.lms.wf.dao.impl.AssignedLeadsDAOImpl;
import com.ibm.lms.wf.dto.BulkMatrixDownloadDTO;
import com.ibm.lms.wf.dto.LeadDetailDTO;
import com.ibm.lms.wf.dto.UserDownloadDTO;


public class MasterServiceImpl implements MasterService {
	
	// List to populate combo boxes on the GUI 
	
	private static ArrayList<ProductDTO> productList = null;
	private static ArrayList<ZoneDTO> zoneList = null;  
	private static ArrayList<CityDTO> cityList = null;
	private static ArrayList<PINCodeDTO>  pinCodeList = null;	
	private static ArrayList<RSUDTO> rsuList = null;
	//added by Nancy Agrawal.
	private static ArrayList<AlertDTO> alertList=null;
	private static ArrayList<AlertDTO> alertSourceList=null;
	private static LeadStatusDTO leadSubStatusId;
	private static LeadStatusDTO leadSubSubStatusId;
	
	private static ArrayList<LOBDTO> lobList = null;
	private static ArrayList<CircleDTO> circleList = null;
	private static ArrayList<CircleDTO> userCircleList = null;
	private static ArrayList<UserMstr> userDetailList = null;
	private static ArrayList<StateDTO> stateList = null;
	private static ArrayList<RequestTypeDTO> requestTypeList = null;
	private static ArrayList<SourceDTO> sourceList = null;
	private static ArrayList<SubSourceDTO> subsourceList = null;
	private static ArrayList<KmActorMstr> actorList = null;	
	private static ArrayList<ProductDTO> telemediaProductList = null; 
	private static  ArrayList<ProductDTO> dthProductList = null;
	private static ArrayList<LeadStatusDTO> leadStatusList = null;
	private static ArrayList<LeadStatusDTO> leadSubStatusList = null;
	private static LeadStatusDTO leadSubStatusList1 = null;
	private static LeadStatusDTO leadSubSubStatusList1 = null;
	private static int subStatusCode=0;
	private static ArrayList<LeadStatusDTO> leadSubSubStatusList = null;
	private static LeadStatusDTO updateSubStatusDetails=null;
	private static ArrayList<LeadStatusDTO> leadStatusCallCenterList = null;
	private static ArrayList<LeadStatusDTO> leadQualificationStatusDiallerList = null;
	private static ArrayList<LeadStatusDTO> leadFeasibilityStatusDiallerList = null;
	private static ArrayList<LeadStatusDTO> leadInfoInadequateStatusDiallerList = null;
	private static ArrayList<LeadStatusDTO> leadWiredStatusDiallerList = null;
	private static ArrayList<LeadStatusDTO> leadUnWiredStatusDiallerList = null;
	private static ArrayList<LeadStatusDTO> leadLostStatusDiallerList = null;
	private static ArrayList<LeadStatusDTO> leadWonStatusDiallerList = null;
	private static ArrayList<LeadStatusDTO> leadAssignedStatusDiallerList = null;
	// Key value pair to get description of various codes 
	private static HashMap<String, String> zoneCodeNameMap = null;
	
	
	private static HashMap<String, String> zoneCodeCityCodeMap = null;
	private static HashMap<String, String> cityCodeNameMap = null;
	private static HashMap<String, String> cityCodeCircleIdMap = null;
	private static HashMap<String, String> circleIdCodeMap = null;
	private static HashMap<String, String> circleIdNameMap = null;
	private static HashMap<String, String> circleCodeNameMap = null;
	private static HashMap<String, String> stateCodeNameMap = null;
	private static HashMap<String, String> requestTypeIdDescMap = null;
	private static HashMap<String, String> sourceIdNameMap = null;
	private static HashMap<String, String> subSourceIdNameMap = null;
	private static HashMap<String, String> productIdNameMap = null;
	private static HashMap<String, String> productIdLobNameMap = null;	
	
	
	
	private static ArrayList<ProductDTO> fourGProductList  =null;
	
	// key is lead status Codes eg: OPEN
	// value is status id eg: 200
	private static HashMap<Integer, LeadStatusDTO> leadStatusIdCodeMap = null;
	
	private static final Logger logger = Logger.getLogger(MasterServiceImpl.class);
	
	
	public  HashMap<String, String> getZoneCodeNameMap() throws LMSException
	{
		try
		{
			
				HashMap<String, String> zoneCodeNameMap = new HashMap<String, String>();
				ZoneDTO zoneDto = new ZoneDTO();
				Iterator<ZoneDTO> itr = getZoneList().iterator();
				
				while(itr.hasNext())
				{
					zoneDto = itr.next();
					zoneCodeNameMap.put(zoneDto.getZoneCode(),zoneDto.getZoneName());
				}
			  // return zoneCodeNameMap;
				
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting ZONE CODE NAME MAP : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return zoneCodeNameMap;
	}
	
	
	public  ArrayList<ProductDTO> getProductList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
				productList =  dao.getProductList();
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Product List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return productList;
	}
	
	public  ArrayList<ZoneDTO> getZoneList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				zoneList =  dao.getZoneList();
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Zone List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return zoneList;
	}
	
	public  ArrayList<CityDTO> getCityList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				cityList =  dao.getCityList(); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting City List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return cityList;
	}
	
	
	public  ArrayList<PINCodeDTO> getPinCodeList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				pinCodeList =  dao.getPINCodeList(); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting PIN Code List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return pinCodeList;		
	}
	
	public  ArrayList<RSUDTO> getRsuList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				rsuList =  dao.getRsuList(); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting RSU List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return rsuList;
	}
	
	public  ArrayList<CircleDTO> getCircleList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				circleList = dao.getCircleList();
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Circle List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return circleList;
	}
	
	public  ArrayList<StateDTO> getStateList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				stateList =  dao.getStateList(); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting State List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return stateList;
	}
	
	public  ArrayList<RequestTypeDTO> getRequestTypeList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				requestTypeList =  dao.getRequestTypeList(); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Request Type List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return requestTypeList;
	}
	
	public  ArrayList<SourceDTO> getSourceList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				sourceList =  dao.getSourceList(); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Source List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return sourceList;
	}
	
	public  ArrayList<SubSourceDTO> getSubSourceList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				subsourceList =  dao.getSubSourceList(); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Sub Source List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return subsourceList;
	}
	
	public  ArrayList<LeadStatusDTO> getLeadStatusList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				leadStatusList =  dao.getLeadStatusList(); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Lead Status List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return leadStatusList;
	}
	public  ArrayList<LeadStatusDTO> getLeadSubStatusList(int statusId,int lobId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				leadSubStatusList =  dao.getLeadSubStatusList(statusId,lobId); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Lead Status List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return leadSubStatusList;
	}
	
	public  LeadStatusDTO addSubStatus(int statusId,int lobId,String subStatusName) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				leadSubStatusId =  dao.addSubStatus(statusId,lobId,subStatusName); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Lead Status List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return leadSubStatusId;
	}
	public  LeadStatusDTO addSubSubStatus(int statusId,int productLobId,int subStatusId,String leadSubSubStatus) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				leadSubSubStatusId =  dao.addSubSubStatus(statusId,productLobId,subStatusId,leadSubSubStatus); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Lead Status List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return leadSubSubStatusId;
	}
	
	public LeadStatusDTO getLeadSubStatusList(int statusId,int lobId,int subStatusId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				leadSubStatusList1 =  dao.getLeadSubStatusList(statusId,lobId,subStatusId); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Lead Status List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return leadSubStatusList1;
	}
	public LeadStatusDTO getLeadSubSubStatusList(int statusId,int lobId,int subStatusId,int subSubStatusId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				leadSubSubStatusList1 =  dao.getLeadSubSubStatusList(statusId,lobId,subStatusId,subSubStatusId); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Lead Status List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return leadSubSubStatusList1;
	}
	public  String updateSubStatusDetails(int leadStatusId,int subStatusId,int productLobId,String leadSubStatus,String leadSubStatusDisplay) throws LMSException
	{
		String message="";
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
			message=dao.updateSubStatusDetails(leadStatusId,subStatusId,productLobId,leadSubStatus,leadSubStatusDisplay); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Lead Status List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return message;
	}
	public  String updateSubSubStatusDetails(int statusId,int subStatusId,int productLobId,String subSubStatusName,String subSubStatusDisplay,int subSubStatusId,int subStatusCode) throws LMSException
	{
		String message="";
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
			message=dao.updateSubSubStatusDetails(statusId, subStatusId, productLobId, subSubStatusName,subSubStatusDisplay,subSubStatusId,subStatusCode); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Lead Status List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	return message;	
	}
	public  int getSubStatusCode(int statusId,int subStatusId,int lobId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
			subStatusCode =  dao.getSubStatusCode(statusId,subStatusId,lobId); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Lead Status List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return subStatusCode;
	}
	public  ArrayList<LeadStatusDTO> getLeadSubSubStatusList(int statusId,int prodLobId,int subStatusId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
				leadSubSubStatusList =  dao.getLeadSubSubStatusList(statusId, prodLobId, subStatusId); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Lead Status List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return leadSubSubStatusList;
	}
	
	
	public  HashMap<Integer, LeadStatusDTO> getLeadStatusIdCodeMap() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				leadStatusIdCodeMap =  dao.getLeadStatusIdCodeMap(); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Lead Status List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return leadStatusIdCodeMap;
	}

	public  ArrayList<LeadStatusDTO> getLeadStatusCallCenterList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				leadStatusCallCenterList =  dao.getLeadStatusCallCenterList(); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Lead Status List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return leadStatusCallCenterList;
	}
	
	public  ArrayList<LeadStatusDTO> getLeadSubSubStatusDiallerList(int leadStatus, int leadSubStatus, int subsubstatusid , int productLob) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();
		ArrayList<LeadStatusDTO> leadStatusList = new ArrayList<LeadStatusDTO>();
		try
		{
				if(leadStatus == LMSStatusCodes.WON) 
				{
					leadWonStatusDiallerList =  dao.getLeadSubSubStatusDiallerList("SQL_SELECT_LEAD_STATUS_DIALLER_WON",productLob,leadSubStatus);
					leadStatusList =  leadWonStatusDiallerList;
				}
					else if(leadStatus == LMSStatusCodes.LOST)
					{
					leadLostStatusDiallerList =  dao.getLeadSubSubStatusDiallerList("SQL_SELECT_LEAD_STATUS_DIALLER_LOST",productLob,leadSubStatus); 
					leadStatusList = leadLostStatusDiallerList;
				}
			}
		
		    catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while getting Lead Status List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return leadStatusList;
	}

	
	
	public  ArrayList<LeadStatusDTO> getLeadStatusDiallerList(int leadStatus, int leadSubStatus, int productLob) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		ArrayList<LeadStatusDTO> leadStatusList = new ArrayList<LeadStatusDTO>();
		try
		{
			
			
			if(leadStatus == LMSStatusCodes.QUALIFICATION) 
			{
				
					leadQualificationStatusDiallerList =  dao.getLeadStatusDiallerList("SQL_SELECT_LEAD_STATUS_DIALLER_QUALIFICATION",productLob); 
				
				leadStatusList =  leadQualificationStatusDiallerList;
			}
			if(leadStatus == LMSStatusCodes.FEASIBILITY) 
			{
				
					leadFeasibilityStatusDiallerList =  dao.getLeadStatusDiallerList("SQL_SELECT_LEAD_STATUS_DIALLER_QUALIFICATION",productLob); 
				
				leadStatusList =  leadFeasibilityStatusDiallerList;
			}
			if(leadStatus == LMSStatusCodes.ASSIGNED || leadStatus == LMSStatusCodes.REASSIGNED) 
			{
				
					if(leadSubStatus == LMSStatusCodes.DIALER_SECOND_CALL )
					leadAssignedStatusDiallerList =  dao.getLeadStatusDiallerList("SQL_SELECT_LEAD_STATUS_DIALLER_ASSIGNED",productLob); 
				
				leadStatusList =  leadAssignedStatusDiallerList;
			}
			if(leadStatus == LMSStatusCodes.INFO_INADEQUATE) 
			{
				
					if(leadSubStatus == LMSStatusCodes.DIALER_SECOND_CALL )
					leadInfoInadequateStatusDiallerList =  dao.getLeadStatusDiallerList("SQL_SELECT_LEAD_STATUS_DIALLER_INFO_INADEQUATE",productLob); 
				
				leadStatusList = leadInfoInadequateStatusDiallerList;
			}
			
			if(leadStatus == LMSStatusCodes.WIRED) 
			{
				
					if(leadSubStatus == LMSStatusCodes.DIALER_SECOND_CALL)
						leadWiredStatusDiallerList =  dao.getLeadStatusDiallerList("SQL_SELECT_LEAD_STATUS_DIALLER_WIRED",productLob);
					//else
					//leadWiredStatusDiallerList =  dao.getLeadStatusDiallerList("SQL_SELECT_LEAD_STATUS_DIALLER_WIRED"); 
				
				leadStatusList = leadWiredStatusDiallerList;
			}
			if(leadStatus == LMSStatusCodes.UNWIRED) 
			{
				
					if(leadSubStatus == LMSStatusCodes.DIALER_SECOND_CALL)
					leadUnWiredStatusDiallerList =  dao.getLeadStatusDiallerList("SQL_SELECT_LEAD_STATUS_DIALLER_UNWIRED",productLob); 
				
				leadStatusList = leadUnWiredStatusDiallerList;
			}
			
	//for new product SItE_Lead:
			List<ProductDTO> proList = getProductListByLob(productLob);
			if(proList.contains(getParameterName("NEW_PRODUCT")))
			{
				if(leadStatus == LMSStatusCodes.WON) 
				{
					leadWonStatusDiallerList =  dao.getLeadStatusDiallerList("SQL_SELECT_LEAD_STATUS_DIALLER_WON",productLob);
					leadStatusList =  leadWonStatusDiallerList;
				}
					else if(leadStatus == LMSStatusCodes.LOST)
					{
					leadLostStatusDiallerList =  dao.getLeadStatusDiallerList("SQL_SELECT_LEAD_STATUS_DIALLER_LOST",productLob); 
					leadStatusList = leadLostStatusDiallerList;
				}
			}
		
			
			/*if(leadStatusIdCodeMap == null)
				getLeadStatusIdCodeMap();
			
			// Incase Telemedia product than Main is not updated for QUALIFIED
			// Main is updated only for UNQUALIFIED, UNQUALIFIED_INVALID, FEASIBILITY_INFO_DONE
			if(leadStatus == LMSStatusCodes.QUALIFICATION) {
				leadStatusList.add(leadStatusIdCodeMap.get(LMSStatusCodes.QUALIFIED)); 
				leadStatusList.add(leadStatusIdCodeMap.get(LMSStatusCodes.LEAD_CONTACTBACK));
				
				ArrayList<LeadStatusDTO> leadSubStatusList = null; // dao.getSubStatusList(LMSStatusCodes.UNQUALIFIED);
				
				leadStatusList.add(leadStatusIdCodeMap.get(LMSStatusCodes.UNQUALIFIED));
				leadStatusList.add(leadStatusIdCodeMap.get(LMSStatusCodes.UNQUALIFIED_INVALID));
			}
			if(leadStatus == LMSStatusCodes.INFO_INADEQUATE) {
				leadStatusList.add(leadStatusIdCodeMap.get(LMSStatusCodes.FEASIBILITY_INFO_DONE));
				leadStatusList.add(leadStatusIdCodeMap.get(LMSStatusCodes.UNQUALIFIED));
				leadStatusList.add(leadStatusIdCodeMap.get(LMSStatusCodes.UNQUALIFIED_INVALID));
			}
			if(leadStatus == LMSStatusCodes.WIRED) {
				leadStatusList.add(leadStatusIdCodeMap.get(LMSStatusCodes.LEAD_CALLBACK_DONE));
			}
			if(leadStatus == LMSStatusCodes.UNWIRED) {
				leadStatusList.add(leadStatusIdCodeMap.get(LMSStatusCodes.LEAD_CALLBACK_DONE));
			}
			*/
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while getting Lead Status List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return leadStatusList;
	}

	public  ArrayList<ProductDTO> getTelemediaProductList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
				telemediaProductList =  dao.getTelemediaProductList(); 
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Telemedia Product List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return telemediaProductList;
	}
	public  ArrayList<ProductDTO> getMobilityProductList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
				telemediaProductList =  dao.getMobilityProductList(); 
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Telemedia Product List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return telemediaProductList;
	}
	
	public  ArrayList<Integer> getRestrictedProductList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		ArrayList<Integer> restrictedProductList=null;
		try
		{
			restrictedProductList =  dao.getRestrictedProductList(); 
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting restricted Product List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return restrictedProductList;
	}
	public String refreshCache() throws LMSException
	{
		String refreshStatus = "REFRESH DONE";
		//MasterDao dao= new MasterDaoImpl();
		try
		{
//			productList =  dao.getProductList();
			zoneList =  null;			
			cityList =  null;
			pinCodeList =  null;
			rsuList =  null;
			lobList = null;
			circleList =  null;
			stateList =  null;
			requestTypeList =  null;
			sourceList =  null;
			subsourceList =  null;
			leadStatusList =  null;
			leadStatusCallCenterList = null;
			leadQualificationStatusDiallerList = null;
			leadInfoInadequateStatusDiallerList = null;
			leadWiredStatusDiallerList = null;
			leadUnWiredStatusDiallerList = null;

			leadStatusIdCodeMap =  null; 
//			telemediaProductList =  dao.getTelemediaProductList(); 
		}
		catch (Exception e) {
			refreshStatus =  "REFRESH FAIL";
			logger.error("Exception occurred while getting Telemedia Product List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return refreshStatus;
	}

	public boolean emailValidation(String emailId) 
	   {
	      //Input the string for validation
	      String email = emailId;

	      //Set the email pattern string
	      Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

	      //Match the given string with the pattern
	      Matcher m = p.matcher(email);

	      //check whether match is found 
	      boolean matchFound = m.matches();

	      if (matchFound)
	       return true;
	      else
	        return false;
	   }


	public boolean isValidCircle(int circleId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidCircle(circleId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating circle : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public boolean isValidCity(String cityId,int circleId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidCity(cityId,circleId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating city : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	public boolean isValidPincode(String pincode,String cityZoneCode) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidPincode(pincode,cityZoneCode);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating pincode : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public boolean isValidProduct(int productId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidProduct(productId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating product : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public boolean isValidRsu(String rsuId,String cityZoneCode) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidRsu(rsuId,cityZoneCode);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating rsu : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public boolean isValidUserLoginId(String userLoginId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidUserLoginId(userLoginId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating user login id : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	/*public boolean isValidZone(String zoneId,String cityCode) throws LMSException {
		MasterDao dao= new MasterDaoImpl();
		try
		{
			return dao.isValidZone(zoneId,cityCode);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating zone : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}*/

	public boolean isValidProductLobId(int productLobId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidProductLobId(productLobId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating product lob id : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	public ArrayList<CityDTO> getCityForCircle(int circleMstriD) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getCityForCircle(circleMstriD);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting City List for circle: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}// 
	public ArrayList<CityDTO> getCityForCircleForDialer(int circleMstriD) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getCityForCircleForDialer(circleMstriD);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting City List for circle: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}// 
	

	public ArrayList<CityDTO> getCityForZone(String cityCode) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			logger.info(" cityCode in service impl :"+cityCode);
			return dao.getCityForZone(cityCode);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting City List for circle: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	public ArrayList<CityZoneDTO> getCityZoneOnCityChange(String cityCode) throws LMSException { 
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			logger.info(" cityCode in service impl :"+cityCode);
			return dao.getCityZoneOnCityChange(cityCode);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting City List for circle: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	public String getZoneCode(String zonec) throws LMSException { 
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			logger.info(" zonecode in service impl :"+zonec);
			return dao.getZoneCode(zonec);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting City List for circle: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	public String getCityForPinCode(String pinCode) throws LMSException { 
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			logger.info(" pinCode in service impl :"+pinCode);
			return dao.getCityForPinCode(pinCode);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting City List for circle: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	} 
	public String getDataForPinCode(String pinCode, int circleMstrId) throws LMSException { 
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			logger.info(" pinCode in service impl :"+pinCode+" circleMstrId :"+circleMstrId);
			return dao.getDataForPinCode(pinCode,circleMstrId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting City List for circle: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	public String getLeadStatusIfLeadNotServiceAble(int product_id) throws LMSException { 
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getLeadStatusIfLeadNotServiceAble(product_id);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getLeadStatusIfLeadNotServiceAble: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
//public  ArrayList<CityDTO> getCircleForProduct(int productLobId) throws LMSException;

	public  ArrayList<CircleForProductDTO> getCircleForProduct(int productLobId) throws LMSException{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getCircleForProduct(productLobId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting City List for circle: "+e.getMessage());
			e.printStackTrace();
			throw new LMSException(e.getMessage(), e);
		}
	}
	public ArrayList<PINCodeDTO> getPinCodeForCity(String CityCode) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getPINCodeForCity(CityCode); 
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting PIN Code List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	public ArrayList<ZoneDTO> getZoneForCity(String cityCode)	throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getZoneForCity(cityCode);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Zone List for City: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	public ArrayList<RSUDTO> getRsuForZone(String zoneCode) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getRsuForZone(zoneCode);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting RSU List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	public boolean isValidActorId(int actorId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidActorId(actorId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating actor id : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	public ArrayList<ProductLobDTO> getProductLobList() throws LMSException {
		MasterDao dao=  MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getProductLobList(); 
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Product Lob List List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public boolean isValidSource(int sourceId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidSource(sourceId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating sourceId : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	public boolean isValidState(String stateCode) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidState(stateCode);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating state : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	public boolean isValidSubSource(int subSourceId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidSubSource(subSourceId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating subSourceId : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public boolean isValidRequestType(int requestId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidRequestType(requestId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating requestId : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public  ArrayList<ReportsDTO> getReportList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getReportList();
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Report List: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public  ArrayList<ReportsDTO> getReportListDayMonthWise()  throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getReportListDayMonthwise();
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Report List: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public  ArrayList<LogsDTO> getLogsList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getLogsList();
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Report List: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	
	public String getReportName(int reportId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getReportName(reportId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Report Name: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public String getMasterTableData(int mstr, int lobId) throws LMSException
	{
		String data = "";
		int prodLobId;
		
//		data = "first col, second col, third col";
//		data += "\n data 1, data 2, data 3 ";
//changes by aman
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			prodLobId=lobId;
			logger.info("prodLobId::::::::::::::::::::"+prodLobId);
			
			
			if(mstr == 1) {
				data = dao.getMasterData("LEAD_STATUS", prodLobId,mstr); 
			}else if (mstr == 2) {
				data = dao.getMasterData("CIRCLE_MSTR", prodLobId,mstr); 
			}else if (mstr == 3) {
				data = dao.getMasterData("STATE_MSTR", prodLobId,mstr); 
			} else if (mstr == 4) {
				data = dao.getMasterData("CITY_MSTR", prodLobId,mstr); 			
			} else if (mstr == 5) {
				data = dao.getMasterData("ZONE_MSTR", prodLobId,mstr); 
			} else if (mstr == 6) {
				data = dao.getMasterData("RSU_MSTR", prodLobId,mstr); 
			} else if (mstr == 7) {
				data = dao.getMasterData("PINCODE_MSTR", prodLobId,mstr); 
			} else if (mstr == 8) {
				data = dao.getMasterData("PRODUCT_LOB", prodLobId,mstr); 
			} else if (mstr == 9) {
				data = dao.getMasterData("PRODUCT_MSTR", prodLobId,mstr); 
			} else if (mstr == 10) {
				data = dao.getMasterData("REQUEST_TYPE_MSTR", prodLobId,mstr); 
			} else if (mstr == 11) {
				data = dao.getMasterData("SOURCE_MSTR", prodLobId,mstr); 
			} else if (mstr == 12) {
				data = dao.getMasterData("SUB_SOURCE_MSTR", prodLobId,mstr); 
			} else if (mstr == 13) {
				data = dao.getMasterData("LEAD_SUB_STATUS", prodLobId,mstr); 
			}else if (mstr == 14) {
				data = dao.getMasterData("CITY_ZONE_MSTR", prodLobId,mstr); 
			}else if (mstr == 15 || mstr==19) {
				data = dao.getMasterData("AUTO_ASSIGNMENT_MATRIX", prodLobId,mstr); 
			}
			else if (mstr == 16) {
				data = dao.getMasterData("USER_MSTR_ADDITIONAL", prodLobId,mstr); 
			}
			else if (mstr == 17) {
				data = dao.getMasterData("AGENCY_ASSIGNMENT", prodLobId,mstr); 
				}
			//added by Nancy
			else if (mstr == 18) {
				data = dao.getMasterData("LEAD_SUB_SUB_STATUS", prodLobId,mstr); 
				}
			else if (mstr == 21){
				data = dao.getMasterData("ESCALATION_MSTR", prodLobId, mstr);
			}
			
//end of changes by aman			
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Report Name: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}

		return data;
	}
	
	
	public  ArrayList<MasterDataDTO> getMasterList() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getMasterList();
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Master List: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public  int getProductLobId(int productId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getProductLobId(productId);
		}
		catch (Exception e) {
			logger.error("Exception occurred in getProductLobId: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	public  int getProduct(long leadId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getProduct(leadId);
		}
		catch (Exception e) {
			logger.error("Exception occurred in getProductLobId: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	public ArrayList<ProductDTO> getDTHProductList() throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			dthProductList =  dao.getDTHProductList(); 
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Telemedia Product List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return dthProductList;
	}

	public  ArrayList<LOBDTO> getLobList() throws LMSException{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				lobList = dao.getLobList();
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting lob List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return lobList;
	}
	
	//added by Nancy Agrawal
	public ArrayList<AlertDTO> getAlertList()  throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		
		try
		{
			alertList= dao.getAlertList();
			
		}
		catch(Exception e)
		{
		logger.error("Exception occured while getting alert List : "+e.getMessage());
	   
		}
	return alertList;
	}
	//end of changes by Nancy Agrawal.
	public ArrayList<CircleDTO> getCircleUserList() throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				userCircleList = dao.getCircleUserList();
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Circle List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return userCircleList;
	}


	public boolean isValidUserCircle(int circleId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidUserCircle(circleId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating circle : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public boolean isValidRsu(String rsuCode) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidRsu(rsuCode);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating rsu : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public  ArrayList<KmActorMstr> getActorList(String actorID) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				actorList =  dao.getActorList(actorID); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Actor List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return actorList;
	}

	public boolean isValidLeadforFeasibility(Long leadID,int status) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidLeadforFeasibility(leadID,status);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating lead for feasibility : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}

	}


	public ArrayList<BulkAssignmentCircleDTO> getCircleTypeList() throws LMSException {
		
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getCircleTypeList();
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Report List: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		
	}


public ArrayList<ProductLobDTO> getLobsNameList() throws LMSException {
		
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getLobsNameList();
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Report List: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


public ArrayList<BulkMatrixDownloadDTO>  getAssignmentDownloadData(int circleTableId, int lobTableId,String usertype) throws LMSException {
	
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.getAssignmentDownloadData(circleTableId,lobTableId,usertype);
	}
	catch (Exception e) {
		e.printStackTrace();
		logger.error("Exception occurred while getting Report Name: "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
	
}
public  boolean isValidZoneCode(int circleId,int prodid,String zoneCode) throws LMSException
{
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidZoneCode(circleId,prodid,zoneCode);
	}
	catch (Exception e) {
		logger.error("Exception occurred while validating zone code.... : "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}
public boolean isValidCityFromZone(String cityId,String zonecode) throws LMSException {
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidCityFromZone(cityId,zonecode);
	}
	catch (Exception e) {
		logger.error("Exception occurred while validating city from Zone ..: "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}

public boolean isValidCityZoneCode(String cityId,String zonecode) throws LMSException {
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidCityZoneCode(cityId,zonecode);
	}
	catch (Exception e) {
		logger.error("Exception occurred while validating city Zone ..: "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}


public  boolean isValidCityZoneCodeReverse(int circleId,int prodid,String zoneCode) throws LMSException
{
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidCityZoneCodeReverse(circleId,prodid,zoneCode);
	}
	catch (Exception e) {
		logger.error("Exception occurred while validating  city zone code.... : "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}
public  boolean isValidCityReverse(int circleId,int prodid,String cityCode) throws LMSException
{
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidCityReverse(circleId,prodid,cityCode);
	}
	catch (Exception e) {
		logger.error("Exception occurred while validating city code.... : "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}
public  boolean isValidPinReverse(int circleId,int prodid,String pincode) throws LMSException
{
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidPinReverse(circleId,prodid,pincode);
	}
	catch (Exception e) {
		logger.error("Exception occurred while validating pinn code.... : "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}
public ArrayList<ZoneDTO> getZoneForCircle(int circleID) throws LMSException {
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.getZoneForCircle(circleID);
	}
	catch (Exception e) {
		logger.error("Exception occurred while getting zone List for circle: "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}


public boolean isValidCircle(int circleId, int productLobId) throws LMSException {
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidCircle(circleId,productLobId);
	}
	catch (Exception e) {
		logger.error("Exception occurred while getting zone List for circle: "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}


public boolean isValidCity(String city, String cityZone) throws LMSException {
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidCity(city,cityZone);
	}
	catch (Exception e) {
		logger.error("Exception occurred while getting zone List for circle: "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}


public boolean isValidCityZoneCodeId(String cityZoneCode, String city) throws LMSException {
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidCityZoneCodeId(cityZoneCode,city);
	}
	catch (Exception e) {
		logger.error("Exception occurred while getting zone List for circle: "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}


public boolean isValidProductid(int productId, int productLobId) throws LMSException {
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidProductid(productId,productLobId);
	}
	catch (Exception e) {
		logger.error("Exception occurred while getting zone List for circle: "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}


public boolean isValidZoneId(String cityZone, int circleId, int productLobId) throws LMSException {
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidZoneId(cityZone,circleId,productLobId);
	}
	catch (Exception e) {
		logger.error("Exception occurred while getting zone List for circle: "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}
public boolean isValidCircleUsingProd(int circle,int productId) throws LMSException {
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidCircleUsingProd(circle,productId);
	}
	catch (Exception e) {
		logger.error("Exception occurred while getting zone List for circle: "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}


public boolean isValidCircleId(int circleId, int productLobId) throws LMSException {
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidCircleId(circleId,productLobId);
	}
	catch (Exception e) {
		logger.error("Exception occurred : isValidCityZoneCode "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}


public boolean isValidCityId(String city, String cityZone) throws LMSException {
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidCityId(city,cityZone);
	}
	catch (Exception e) {
		logger.error("Exception occurred : isValidCityZoneCode "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}


public boolean isValidPincodeNew(String pincode, String cityZoneCode) throws LMSException {
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidPincodeNew(pincode,cityZoneCode);
	}
	catch (Exception e) {
		logger.error("Exception occurred : isValidCityZoneCode "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}


public boolean isValidRsuNew(String rsu, String cityZoneCode) throws LMSException {
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidRsuNew(rsu,cityZoneCode);
	}
	catch (Exception e) {
		logger.error("Exception occurred : isValidCityZoneCode "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}


public ArrayList<CircleForProductLob> getCircleForProductName(int productLobId) throws LMSException {
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.getCircleForProductName(productLobId);
	}
	catch (Exception e) {
		logger.error("Exception occurred while getting City List for circle: "+e.getMessage());
		e.printStackTrace();
		throw new LMSException(e.getMessage(), e);
	}
}


public boolean isValidBulkUserCircle_LOB(String circleListStr, int productLobId)
		throws LMSException {

	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.isValidBulkUserCircle_LOB(circleListStr,productLobId);
	}
	catch (Exception e) {
		logger.error("Exception occurred isValidBulkUserCircle_LOB: "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}


public boolean isValidCityZoneCode(String cityzoneCode, int circleId,
		int productLobId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidCityZoneCode(cityzoneCode,circleId,productLobId);
		}
		catch (Exception e) {
			logger.error("Exception occurred : isValidCityZoneCode "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

/* Added by Parnika for LMS Phase 2 */

	public int getCircleIdValue(int circleMstrId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getCircleIdValue(circleMstrId);
		}
		catch (Exception e) {
			logger.error("Exception occurred : getCircleIdValue "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public boolean isValidUserZoneCode(String zoneCode, int circleId,
			int productLobId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidUserZoneCode(zoneCode,circleId,productLobId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting zone List for circle: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
}
	public  ArrayList<LOBDTO> getLobListBasedOnUser(ArrayList lobList) throws LMSException{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				lobList = dao.getLobListBasedOnUser(lobList);
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting lob List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return lobList;
	}

/* End of changes by Parnika */
	
	/* Added by amarjeet for LMS Phase 2 */
	public String getZoneCodeforCity(String cityCode) throws LMSException { 
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			logger.info(" cityCode in service impl :"+cityCode);
			return dao.getZoneCodeforCity(cityCode);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting City List for circle: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public  ArrayList<CircleDTO> getCircleForUser(String userLoginId)throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				userCircleList = dao.getCircleForUser(userLoginId);
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Circle List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return userCircleList;
	}

	public  ArrayList<CircleDTO> getCircleForUserLob(String userLoginId , int lobId)throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				userCircleList = dao.getCircleForUserLob(userLoginId, lobId);
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Circle List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return userCircleList;
	}
	/* Added by amarjeet for LMS Phase 2 */
	public  ArrayList<LOBDTO> getLobForUser(String userLoginId) throws LMSException{
		
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
			lobList = dao.getLobForUser(userLoginId);
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting lobList List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return lobList;
	}
	
public  ArrayList<LOBDTO> getLobListForUser(String userLoginId) throws LMSException{
		
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
			lobList = dao.getLobListForUser(userLoginId);
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting lobList List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return lobList;
	}

	public ArrayList<ProductLobDTO> getAssignProductLobList(ArrayList lobList) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getAssignProductLobList(lobList);
		}
		catch (Exception e) {
			logger.error("Exception occurred : getCircleIdValue "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	
	}


	public boolean isValidCircleIdNew(int circleId,int productLobId, String userLoginId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidCircleIdNew(circleId,productLobId,userLoginId);
		}
		catch (Exception e) {
			logger.error("Exception occurred : getCircleIdValue "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public boolean isValidCityZoneCodeNewOne(String cityzoneCode, int circleId, int productLobId) throws LMSException {
		
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidCityZoneCodeNewOne(cityzoneCode,circleId,productLobId);
		}
		catch (Exception e) {
			logger.error("Exception occurred : getCircleIdValue "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public ArrayList<UserDownloadDTO> getuserMasterDownloadData(int circleTableId, int productlobId, int selectActorId ) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getuserMasterDownloadData(circleTableId,productlobId,selectActorId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Report Name: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		
	}
	
	/* Added By Amarjeet for LMS Phase 2 */   

	public JSONObject getLobElementsAsJson(String olmId) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		
		ArrayList list = getLobListForUser(olmId);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			LOBDTO dto=(LOBDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}


	public ArrayList<BulkPinCodeDTO> getAssignmentMatrixDataForPinCode(String pincode, int circleId, int productLobId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getAssignmentMatrixDataForPinCode(pincode,circleId,productLobId);
		}
		catch (Exception e) {
			logger.error("Exception occurred : getCircleIdValue "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public ArrayList<BulkRsuDTO> getAssignmentMatrixDataForRsuCode(String rsu, int circleId, int productLobId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getAssignmentMatrixDataForRsuCode(rsu,circleId,productLobId);
		}
		catch (Exception e) {
			logger.error("Exception occurred : getCircleIdValue "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public ArrayList<BulkCityZoneCodeCTO> getAssignmentMatrixDataForCityZoneCode(String cityZoneCode, int circleId, int productLobId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getAssignmentMatrixDataForCityZoneCode(cityZoneCode,circleId,productLobId);
		}
		catch (Exception e) {
			logger.error("Exception occurred : getCircleIdValue "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public ArrayList<BulkCityDTO> getAssignmentMatrixDataForCityCode(String city,int circleId, int productLobId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getAssignmentMatrixDataForCityCode(city,circleId,productLobId);
		}
		catch (Exception e) {
			logger.error("Exception occurred : getAssignmentMatrixDataForCityCode "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public ArrayList<CircleDTO> getCircleForLob(int productLobId) throws DAOException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				userCircleList = dao.getCircleForLob(productLobId);
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Circle List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return userCircleList;
	}
	
	public int getLobId(String lobName) throws DAOException {

		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		int lobId =0;
		try
		{
			
				lobId = dao.getLobId(lobName);
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Circle List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return lobId;
	
	}
	
		/* Added by Parnika for Checking Valid Rsu within the circle */
	
	public boolean isValidRsuInCircle(String rsu, String LeadId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidRsuInCircle(rsu,LeadId);
		}
		catch (Exception e) {
			logger.error("Exception occurred : isValidRsuInCircle "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	/* End of changes by Parnika */
	
	public  ArrayList<UserMstr> getLogedInUserDetails(String userLoginId , int lobId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				userDetailList = dao.getLogedInUserDetails(userLoginId , lobId);
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Circle List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return userDetailList;
	}

	public   int getZoneId(String zoneCode) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		int zoneId =0;
		try
		{
			zoneId = dao.getZoneId(zoneCode);
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Circle List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return zoneId;
	}
	
	
	public   int getCityZoneId(String cityZoneCode) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		int cityZoneId =0;
		try
		{
			cityZoneId = dao.getCityZoneId(cityZoneCode);
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting cityZoneId : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return cityZoneId;
	}
	public  String getCityZoneCode(String zoneId , String zoneFlag) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		String  zoneCode =null;
		try
		{
			zoneCode = dao.getCityZoneCode(zoneId, zoneFlag);
			
		}
		catch (Exception e) {
			//e.printStackTrace();//
			logger.error("Exception occurred while getting zoneCode  : "+e.getMessage());
			//throw new LMSException(e.getMessage(), e);
		}
		return zoneCode;
	}


	public ArrayList<ProductDTO> getMobilityProductListNew() throws LMSException {
		
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getMobilityProductListNew();
		}
		catch (Exception e) {
			logger.error("Exception occurred :  "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public ArrayList<ProductDTO> getOtherProductsList() throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getOtherProductsList();
		}
		catch (Exception e) {
			logger.error("Exception occurred :  "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public boolean isvalidCampaignField(String campaign) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isvalidCampaignField(campaign);
		}
		catch (Exception e) {
			logger.error("Exception occurred :  "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public String getParameterName(String formName) throws LMSException {
		
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getParameterName(formName);
		}
		catch (Exception e) {
			logger.error("Exception occurred :  "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		
	}
	
	/* Added by Parnika */
	
	public  ArrayList<LeadStatusDTO> getLeadStatusListMTD() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			
				leadStatusList =  dao.getLeadStatusListMTD(); 
			
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Lead Status List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return leadStatusList;
		
	}
	
	public  ArrayList<ReportsDTO> getReportListAdmin() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getReportListAdmin();
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting getReportListAdmin : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	
	/* End of changes by Parnika */
	public ArrayList<ProductDTO> getProductListByLob(int productId,int productLobId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getProductListByLob(productId,productLobId);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while getting getReportListAdmin : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	// Added by bhaskar
	public String getProductLobName(int productLobId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getProductLobName(productLobId);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while getting getReportListAdmin : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	


	public boolean isValidProductLobForRsuCode(int prodid) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidProductLobForRsuCode(prodid);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while getting getReportListAdmin : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	//Added By Bhaskar
	public boolean isValidRsuReverse(int circleId, int prodid, String rsuCode)
			throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidRsuReverse(circleId,prodid,rsuCode);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating pinn code.... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
}


	public boolean isValidRequestCategory(String productId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidRequestCategory(productId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating pinn code.... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
}


	@Override
	public boolean isValidRequestCategoryForLob(String productLobId)
			throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidRequestCategoryForLob(productLobId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating pinn code.... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
}

	public boolean isValidCityBasedRsuReverse(int circleId, int prodid,
			 String cityCode) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidCityBasedRsuReverse(circleId,prodid,cityCode);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating pinn code.... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
}


	public boolean isValidCityZoneBasedRsuReverse(int circleId, int prodid,
			String rsuCode, String cityZoneCode, String cityCode)
			throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidCityZoneBasedRsuReverse(circleId,prodid,rsuCode,cityZoneCode,cityCode);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating pinn code.... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
}


	public boolean isValidCityZoneBasedRsuReverseNew(int circleId, int prodid,
			 String cityZoneCode) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isValidCityZoneBasedRsuReverseNew(circleId,prodid,cityZoneCode);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating pinn code.... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
}

//added by satish
	public NoneditablefieldsDTO getNonEditableFiedls(String LeadID)throws LMSException{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getNonEditableFiedls(LeadID);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating pinn code.... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		
	}
	public ArrayList<LeadDetailsDTO> getPreviousoperatornameList()throws LMSException{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getPreviousoperatornameList();
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating pinn code.... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		
	}
	//added by satish	
	public ArrayList<RSUDTO> getRsuForCircleChange(int circleMstriD) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getRsuForCircleChange(circleMstriD);
		}
		catch (Exception e) {
			logger.error("Exception occurred while validating pinn code.... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
}


	
	public ArrayList<RSUDTO> getRsuForCityChange(int circleMstriD, String cityCode)
			throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getRsuForCityChange(circleMstriD,cityCode);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while validating pinn code.... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
}


	@Override
	public ArrayList<RSUDTO> getRsuForCityZoneCodeChange(int circleMstriD, String cityCode,
			String cityzonecode) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getRsuForCityZoneCodeChange(circleMstriD,cityCode,cityzonecode);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while validating pinn code.... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
}


	@Override
	public String getCircleName(String circleMstrId) throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getCircleName(circleMstrId);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while validating pinn code.... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
}

	public String getAlertMsg(String alert,String source,String type)  throws LMSException
	{
		MasterDao dao = MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		String msg=null;
		try
		{
			 msg= dao.getAlertMsg(alert,source,type);
			
		}
		catch(Exception e)
		{
		e.printStackTrace();
		logger.error("Exception occured while getting alert msg : "+e.getMessage());
	   
		}
	return msg;
	}
	
	public String getStatus(String alert,String source) throws LMSException
	{
		MasterDao dao = MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		String status=null;
		try
		{
			 status= dao.getStatus(alert,source);
			
		}
		catch(Exception e)
		{
		e.printStackTrace();
		logger.error("Exception occured while getting alert msg : "+e.getMessage());
	   
		}
	return status;
	}
	
	public String getSubject(String alert,String source) throws LMSException
	{
		MasterDao dao = MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		String subject=null;
		try
		{
			 subject= dao.getSubject(alert,source);
			
		}
		catch(Exception e)
		{
		e.printStackTrace();
		logger.error("Exception occured while getting alert msg : "+e.getMessage());
	   
		}
	return subject;
	}
	
	public String getEmail(String alert,String source) throws LMSException{
		MasterDao dao = MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		String email=null;
		try
		{
			email= dao.getEmail(alert,source);
			
		}
		catch(Exception e)
		{
		e.printStackTrace();
		logger.error("Exception occured while getting alert msg : "+e.getMessage());
	   
		}
	return email;
	}
	
	public String getAlertType(String alert,String source) throws LMSException
	{
		MasterDao dao = MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		String alertType=null;
		try
		{
			alertType= dao.getAlertType(alert,source);
			
		}
		catch(Exception e)
		{
		e.printStackTrace();
		logger.error("Exception occured while getting alert msg : "+e.getMessage());
	   
		}
	return alertType;	
	}

		public String getSms(String alert,String source) throws LMSException{
			MasterDao dao=MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
			String sms=null;
			try
			{
				 sms= dao.getSms(alert,source);
				
			}
			catch(Exception e)
			{
			e.printStackTrace();
			logger.error("Exception occured while getting alert msg : "+e.getMessage());
		   
			}
		return sms;
		}
		
	public String getCount(String alert,String source) throws LMSException
	{
		MasterDao dao = MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		String count="";
		try
		{
			 count= dao.getCount(alert,source);
			
		}
		catch(Exception e)
		{
		e.printStackTrace();
		logger.error("Exception occured while getting alert msg : "+e.getMessage());
	   
		}
	return count;
	}
	
	public String getPeriod(String alert,String source) throws LMSException{
		MasterDao dao = MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		String period="";
		try
		{
			period= dao.getPeriod(alert,source);
			
		}
		catch(Exception e)
		{
		e.printStackTrace();
		logger.error("Exception occured while getting alert msg : "+e.getMessage());
	   
		}
	return period;
	}

public List<ActorDto> getActors()  throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getActors();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while getActors ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public List<ReportsDTO> getDynamicReports() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getDynamicReports();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while getDynamicReports ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public ReportsDTO getReportConfiguration(String reportConfigId, int actorId, int lobId)throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getReportConfiguration(reportConfigId, actorId, lobId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while getReportConfiguration ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public void insertReportConfiguration(ReportsDTO aDto) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			dao.insertReportConfiguration(aDto);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while insertReportConfiguration... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public boolean checkIfConfigurationExist(String reportConfigId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.checkIfConfigurationExist(reportConfigId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while checkIfConfigurationExist ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public void updateReportConfiguration(ReportsDTO aDto) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			dao.updateReportConfiguration(aDto);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while updateReportConfiguration ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public List<ColumnDto> getColumns(String reportConfigId, int actorId, int lobId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getColumns(reportConfigId, actorId, lobId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while getColumns ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public int getReportConfigurationId(int reportId, int actorId, int lobId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getReportConfigurationId(reportId, actorId, lobId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while getReportConfigurationId ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public void updateReportColumns(String reportConfigId, int[] reportColumns, String[] displayNames, int reportId, int actorId, int lobId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			dao.updateReportColumns(reportConfigId, reportColumns, displayNames, reportId, actorId, lobId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while updateReportColumns ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public List<ProductDTO> getAllProducts() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getAllProducts();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while getAllProducts ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public List<ProductDTO> getProductListByLob(int lobid) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getProductListByLob(lobid);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while getAllProducts ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public List<FidDto> getSelectedFidsForReport(String reportConfigId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getSelectedFidsForReport(reportConfigId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while getSelectedFidsForReport ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	

	public List<FidDto> getAllFids(String reportConfigId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getAllFids(reportConfigId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while getAllFids ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	
	public List<FidDto> getAllActiveFids() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getAllActiveFids();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while getAllActiveFids ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	
	public void updateProducts(String reportConfigId, int lobId, String[] products) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			dao.updateProducts(reportConfigId, lobId, products);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while updateProducts ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public List<String> getSelectedProducts(String reportConfigId, int lobId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getSelectedProducts(reportConfigId, lobId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while getSelectedProducts ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public void updateFids(String reportConfigId, String[] fids) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			dao.updateFids(reportConfigId, fids);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while updateFids ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public List<ColumnDto> getAllColumns() throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getAllColumns();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while getAllColumns ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public int addDynamicReport(String reportName) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.addDynamicReport(reportName);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while addDynamicReport ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public boolean isReportExist(String reportName) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.isReportExist(reportName);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while isReportExist ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public void deleteReportConfiguration(String reportConfigId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			dao.deleteReportConfiguration(reportConfigId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while deleteReportConfiguration ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	public String getReportNameFromReportId(int reportId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getReportNameFromReportId(reportId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while getReportNameFromReportId ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public void deleteReport(int reportId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			dao.deleteReport(reportId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while deleteReport ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
			}
		}
	public String getSource(String alert)throws LMSException
	{
		MasterDao dao = MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		String source="";
		try
		{
			source= dao.getSource(alert);
			
		}
		catch(Exception e)
		{
		e.printStackTrace();
		logger.error("Exception occured while getting alert msg : "+e.getMessage());
	   
		}
	return source;
	}


	public List<ProductDTO> getproductForLob(int productLobId)
			throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getproductForLob(productLobId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occurred while deleteReport ... : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
			}
		}
	
	
	
	public ArrayList<ProductDTO> getFourgProductList() throws LMSException {
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
				fourGProductList =  dao.getFourgProductList(); 
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting Telemedia Product List : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return fourGProductList;
	}
	public  int getProductIdByMobile(long mobile) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getProductIdByMobile(mobile);
		}
		catch (Exception e) {
			logger.error("Exception occurred in getProductLobId: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	public  int getProductIdByTid(Long tid) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getProductIdByTid(tid);
		}
		catch (Exception e) {
			logger.error("Exception occurred in getProductLobId: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	

	
	public ArrayList<PINCodeDTO>getPinCodeListFor4G() throws LMSException
	
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getPinCodeListFor4G();
		}
		catch (Exception e) {
			logger.error("Exception occurred in getProductLobId: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public  int getProductId(long leadId) throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getProductId(leadId);
		}
		catch (Exception e) {
			logger.error("Exception occurred in getProductLobId: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public int getCircleMstrIdValue(int circleId,int lobId) throws LMSException
	{
	MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
	try
	{
		return dao.getCircleMstrIdValue(circleId,lobId);
	}
	catch (Exception e) {
		logger.error("Exception occurred in getProductLobId: "+e.getMessage());
		throw new LMSException(e.getMessage(), e);
	}
}
	public ArrayList<AssignmentReportDTO> getApproversList(String OlmsId)throws LMSException
	{
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		try
		{
			return dao.getApproversList(OlmsId);
		}
		catch (Exception e) {
			logger.error("Exception occurred in getProductLobId: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	@Override
	public boolean validateOlm(String olmId) throws DAOException {
		// TODO Auto-generated method stub
		MasterDao dao=MasterDaoImpl.masterDaoInstance();
		try{
			return dao.isValidOlm(olmId);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
		
	}


	@Override
	public boolean validateProduct(String product) throws DAOException {
		MasterDao dao=MasterDaoImpl.masterDaoInstance();
		try{
			return dao.isValidProuct(product);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}
}