
package com.ibm.lms.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;


import com.ibm.lms.common.Constants;
import com.ibm.lms.common.LMSStatusCodes;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.dao.LeadRegistrationDao;
import com.ibm.lms.dao.MasterDao;
import com.ibm.lms.dao.ProductMappingDao;
import com.ibm.lms.dao.impl.LeadRegistrationDaoImpl;
import com.ibm.lms.dao.impl.MasterDaoImpl;
import com.ibm.lms.dao.impl.ProductMappingDaoImpl;
import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.dto.LeadDetailsDTO;
import com.ibm.lms.dto.LeadStatusDTO;
import com.ibm.lms.dto.ProductDTO;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.LeadRegistrationFormBean;
import com.ibm.lms.services.LeadRegistrationService;
import com.ibm.lms.services.MasterService;

public class LeadRegistrationServiceImpl implements LeadRegistrationService{
	
	private static final Logger logger;

	static {

		logger = Logger.getLogger(LeadRegistrationServiceImpl.class);
	}

	
	public String insertRecord(LeadRegistrationFormBean leadRegistrationBean)throws LMSException{
		
		StringBuffer insertStatus = new StringBuffer("INSERT_FAILURE") ;
		String newLeadIds = "";
		String existingLeadIds = "";
		String tableValues;
		String tableValuesArray[];
		String tableValuesArrayValues[];
		
		LeadRegistrationDao dao = LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();
		//<-- Added by srikant -->
		MasterService service= new MasterServiceImpl();
		//MasterService service = new MasterServiceImpl();
		List<LeadDetailsDTO> leadList = new ArrayList<LeadDetailsDTO>();
		try
		{
			//String productIds[] = leadRegistrationBean.getProductIds();// commented by pratap
			
			tableValues = leadRegistrationBean.getTableValues();
			tableValuesArray=tableValues.split("=");
			/* The values are stored in tableValuesArray.length as per the following logic:
			
				lead1productId#Lead1CircleId#Lead1Zonecode#Lead1cityCode#Lead1CityZoneCode#Lead1pincode#=lead2productId#Lead2CircleId#Lead2Zonecode#Lead2cityCode#Lead2CityZoneCode#Lead2pincode
			 */
			
			for(int i=0;i<tableValuesArray.length;i++)
			{
				LeadDetailsDTO leadDetailsDTO;
				int productId;
				int circleId;
				String cityCode;
				String cityZoneCode;
				String pinCode;
				String zoneCode;
				Long ContactNo;
				String productIds[] = new String[1];
				int    productIdArray[] = new  int[productIds.length];

				tableValuesArrayValues=tableValuesArray[i].split("#");
/*				for(int k=0;k<tableValuesArrayValues.length;k++)
				{
				logger.info("tableValuesArrayValues"+k+" : " +tableValuesArrayValues[k]);
				}*/
				productId=Integer.parseInt(tableValuesArrayValues[0]);
				circleId=Integer.parseInt(tableValuesArrayValues[1]);
				zoneCode=tableValuesArrayValues[2];
				cityCode=tableValuesArrayValues[3];
				cityZoneCode=tableValuesArrayValues[4];
				pinCode=tableValuesArrayValues[5];
				leadDetailsDTO = new LeadDetailsDTO();
/*				leadDetailsDTO.setRemoteAddress(leadRegistrationBean.getRemotAddress());
				logger.info("getRemotAddress"+i+" :"+leadRegistrationBean.getRemotAddress());*/
				
			// end of adding by pratap
			
				
				leadDetailsDTO.setCircleMasterId(service.getCircleMstrIdValue(circleId,service.getProductLobId(productId)));
				
				leadDetailsDTO.setCustomerName(leadRegistrationBean.getCustomerName());	
				
				if(leadRegistrationBean.getContactNo() == "")
				{
					
					ContactNo=Long.parseLong(leadRegistrationBean.getContactNo());
					leadDetailsDTO.setContactNo(ContactNo);
					
				}
				else
				{
					leadDetailsDTO.setContactNo(Long.parseLong(leadRegistrationBean.getContactNo()));
				}
				
				
				if(!"".equals(leadRegistrationBean.getAlternateContactNo()))
					leadDetailsDTO.setAlternateContactNo(leadRegistrationBean.getAlternateContactNo());	
				
				for(int ii=0; ii< productIds.length; ii++)
				{
					//productIdArray[ii] = Integer.parseInt(productIds[ii]);// commented by pratap
					productIdArray[ii] = productId;
					
				}
				leadDetailsDTO.setProductIds(productIdArray);
				leadDetailsDTO.setCityZoneCode(cityZoneCode);
				leadDetailsDTO.setCreatedBy(leadRegistrationBean.getCreatedBy());
				leadDetailsDTO.setUdId(leadRegistrationBean.getUdid());
				leadDetailsDTO.setLanguage(leadRegistrationBean.getLanguage());
				leadDetailsDTO.setAddress1(leadRegistrationBean.getAddress1());
				leadDetailsDTO.setAddress2(leadRegistrationBean.getAddress2());
				//leadDetailsDTO.setCityCode(leadRegistrationBean.getCityCode());			
				leadDetailsDTO.setCityCode(cityCode);
				//leadDetailsDTO.setPinCode(leadRegistrationBean.getPinCode());
				if(pinCode !=null) {
				leadDetailsDTO.setPinCode(pinCode.trim());
				}else {
					leadDetailsDTO.setPinCode(pinCode);
				}
				if(!"".equals(leadRegistrationBean.getStateId()))
					leadDetailsDTO.setStateCode(leadRegistrationBean.getStateId());
				/*if(!"".equals(leadRegistrationBean.getCircleId()))
					leadDetailsDTO.setCircleId(Integer.parseInt(leadRegistrationBean.getCircleId()));*/
				leadDetailsDTO.setCircleId(circleId);
				leadDetailsDTO.setEmail(leadRegistrationBean.getEmail());
				leadDetailsDTO.setLandlineNo(leadRegistrationBean.getLandlineNo());
				leadDetailsDTO.setMaritalStatus(leadRegistrationBean.getMaritalStatus());
				leadDetailsDTO.setExistingCustomer(leadRegistrationBean.getExistingCustomer());
				
					if(!"".equals(leadRegistrationBean.getRequestType()))
					leadDetailsDTO.setRequestType(Integer.parseInt(leadRegistrationBean.getRequestType()));
					//logger.info("*****************************"+leadRegistrationBean.getRequestType());
				StringBuffer appointmentTime = new StringBuffer();
				
				if (!"".equals(leadRegistrationBean.getAppointmentDate()))
				{
				
					appointmentTime.append(leadRegistrationBean.getAppointmentDate());
					
					if(!"".equals(leadRegistrationBean.getAppointmentHour()))
					{
						appointmentTime.append(" ").append(leadRegistrationBean.getAppointmentHour());
						
						if(!"".equals(leadRegistrationBean.getAppointmentMinute()))
						{
							appointmentTime.append(":").append(leadRegistrationBean.getAppointmentMinute());
							
						}
					}
				}
				
				leadDetailsDTO.setAppointmentTime(appointmentTime.toString());
				leadDetailsDTO.setCaf(leadRegistrationBean.getCaf());
				leadDetailsDTO.setOnlineCafNo(leadRegistrationBean.getOnlineCafNo());
				
				leadDetailsDTO.setRemarks(leadRegistrationBean.getRemarks());
				if(!"".equals(leadRegistrationBean.getSourceId()))
					leadDetailsDTO.setSourceId(Integer.parseInt(leadRegistrationBean.getSourceId()));
				if(!"".equals(leadRegistrationBean.getSubSourceId()))
					leadDetailsDTO.setSubSourceId(Integer.parseInt(leadRegistrationBean.getSubSourceId()));
				
				leadDetailsDTO.setSubZoneName(leadRegistrationBean.getSubZoneName());
				leadDetailsDTO.setExtraParam1(leadRegistrationBean.getExtraParam1()); //sales executive no
				//added by Nancy
				leadDetailsDTO.setSalesChannelCode(leadRegistrationBean.getSalesChannelCode()); //Sales Channel Code
				leadDetailsDTO.setRsuCode(leadRegistrationBean.getRsuCode());
				//leadDetailsDTO.setLeadStatusId(LMSStatusCodes.LEAD_OPEN);
				 leadDetailsDTO.setCampaign(leadRegistrationBean.getCampaign());
				 leadDetailsDTO.setPlan(leadRegistrationBean.getPlan());
				 leadDetailsDTO.setPytAmt(leadRegistrationBean.getPytAmt());
				 leadDetailsDTO.setTotalDue(leadRegistrationBean.getTotalDue());
				 leadDetailsDTO.setTranRefno(leadRegistrationBean.getTranRefno());
				 leadDetailsDTO.setDevicetaken(leadRegistrationBean.getDevicetaken());
				 leadDetailsDTO.setExtraParam5(leadRegistrationBean.getExtraParam5()); // for Payment Type
				 leadDetailsDTO.setExtraParam6(leadRegistrationBean.getExtraParam6());// for payment status
				 leadDetailsDTO.setExtraParam7(leadRegistrationBean.getExtraParam7());// for service status
				 leadDetailsDTO.setFeasibilityParam(leadRegistrationBean.getFeasibilityParam());
				 leadDetailsDTO.setTotalDue(leadRegistrationBean.getTotalDue());  //amount due added
				 leadDetailsDTO.setProductLobId(service.getProductLobId(productId)); 
				 
				if(leadRegistrationBean.isQualifiedLeadStatus())
				{
					leadDetailsDTO.setLeadStatusId(Integer.parseInt(Constants.LEAD_STATUS_QUALIFIED));
					/* Changes by Parnika for telemedia products */
					if (PropertyReader.getAppValue("lms.telemedia.productLobId").trim().equals(service.getProductLobId(productId)+"")){
						leadDetailsDTO.setLeadStatusId(Integer.parseInt(Constants.LEAD_STATUS_VERIFICATION));
					}
					leadDetailsDTO.setLeadCategory(Constants.QUALIFIED_LEAD);
					/* End of changes by Parnika */
				}					
				else{
					leadDetailsDTO.setLeadStatusId(Integer.parseInt(Constants.LEAD_STATUS_OPEN));
					leadDetailsDTO.setLeadCategory(Constants.NORMAL);
				}
				
				//added by Nancy:
				
					
				StringBuffer appointmentEndTime = new StringBuffer();
				
				//for particular 4G product.
				if ((PropertyReader.getAppValue("lms.4G.productLobId").trim().equals(service.getProductLobId(productId)+"")))
				{
					
				//when appointments have been cancelled, selected values go into both fields:
					
				if(leadRegistrationBean.getAppointmentStatus().equals("false"))
				
				{
				leadDetailsDTO.setAppointmentStatus(Constants.APPOINTMENT_CANCEL);
				if (!"".equals(leadRegistrationBean.getAppointmentStartDate()))
				{
					Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(leadRegistrationBean.getAppointmentStartDate());
					String appointmentstart = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
					appointmentTime.append(appointmentstart);
					
					if(!"".equals(leadRegistrationBean.getAppointmentStartHour()))
					{
						appointmentTime.append("T").append(leadRegistrationBean.getAppointmentStartHour());
						
						if(!"".equals(leadRegistrationBean.getAppointmentStartMinute()))
						{
							appointmentTime.append(":").append(leadRegistrationBean.getAppointmentStartMinute()).append(":00");
							
						}
					}
				}
				leadDetailsDTO.setAppointmentTime(appointmentTime.toString());
				
				if (!"".equals(leadRegistrationBean.getAppointmentEndDate()))
				{
				
					Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(leadRegistrationBean.getAppointmentEndDate());
					String appointmentend = new SimpleDateFormat("yyyy-MM-dd").format(date1);
					appointmentEndTime.append(appointmentend);
					
					if(!"".equals(leadRegistrationBean.getAppointmentEndHour()))
					{
						appointmentEndTime.append("T").append(leadRegistrationBean.getAppointmentEndHour());
						
						if(!"".equals(leadRegistrationBean.getAppointmentEndMinute()))
						{
							appointmentEndTime.append(":").append(leadRegistrationBean.getAppointmentEndMinute()).append(":05");
							
						}
					}
				}
				leadDetailsDTO.setAppointmentEndTime(appointmentEndTime.toString());
				
				} 
				else if(leadRegistrationBean.getAppointmentStatus().equalsIgnoreCase("true")){
				leadDetailsDTO.setAppointmentStatus(Constants.APPOINTMENT_CHOSEN);
				
				try {
					if(leadRegistrationBean.getCheckslot() !=null && leadRegistrationBean.getCheckslot().length()> 0 && !leadRegistrationBean.getCheckslot().equalsIgnoreCase("-1")){
						String date[]  =leadRegistrationBean.getCheckslot().split("TO");
						leadDetailsDTO.setAppointmentTime(date[0]);
						leadDetailsDTO.setAppointmentEndTime(date[1]);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
					}
					else{
						leadDetailsDTO.setAppointmentStatus(Constants.APPOINTMENT_NOTCHOSEN);
						try {
							//if(leadRegistrationBean.getCheckslot() !=null && leadRegistrationBean.getCheckslot().length()> 0 && !leadRegistrationBean.getCheckslot().equalsIgnoreCase("-1")){
								/*String date[]  =leadRegistrationBean.getCheckslot().split("TO");
								leadDetailsDTO.setAppointmentTime(date[0]);
								leadDetailsDTO.setAppointmentEndTime(date[1]);
								logger.info("++++++"+leadDetailsDTO.getAppointmentStatus());*/
							
							
							leadDetailsDTO.setAppointmentStatus(Constants.APPOINTMENT_CANCEL);
							if (!"".equals(leadRegistrationBean.getAppointmentStartDate()))
							{
							
								Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(leadRegistrationBean.getAppointmentStartDate());
								String appointmentstart = new SimpleDateFormat("yyyy-MM-dd").format(date1);
								appointmentTime.append(appointmentstart);
								
								if(!"".equals(leadRegistrationBean.getAppointmentStartHour()))
								{
									appointmentTime.append("T").append(leadRegistrationBean.getAppointmentStartHour());
									
									if(!"".equals(leadRegistrationBean.getAppointmentStartMinute()))
									{
										appointmentTime.append(":").append(leadRegistrationBean.getAppointmentStartMinute()).append(":00");
										
									}
								}
							}
							leadDetailsDTO.setAppointmentTime(appointmentTime.toString());
							
							if (!"".equals(leadRegistrationBean.getAppointmentEndDate()))
							{
								Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(leadRegistrationBean.getAppointmentEndDate());
								String appointmentend = new SimpleDateFormat("yyyy-MM-dd").format(date1);
								appointmentEndTime.append(appointmentend);
								
								if(!"".equals(leadRegistrationBean.getAppointmentEndHour()))
								{
									appointmentEndTime.append("T").append(leadRegistrationBean.getAppointmentEndHour());
									
									if(!"".equals(leadRegistrationBean.getAppointmentEndMinute()))
									{
										appointmentEndTime.append(":").append(leadRegistrationBean.getAppointmentEndMinute()).append(":05");
										
									}
								}
							}
							leadDetailsDTO.setAppointmentEndTime(appointmentEndTime.toString());
							
							
							
							//}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
				
				leadList.add(leadDetailsDTO);
		}
			//TILL HERE for loop closed here pratap
			if(leadRegistrationBean.isDirtyLead())
			{
				dao.insertDirtyRecord(leadList, Constants.SOURCE_TYPE_CALL_CENTER);
				insertStatus.delete(0,14);  // remove INSERT_FAILURE
				insertStatus.append("DIRTY_LEAD");
			}
			else
			{

				ArrayList<BulkUploadMsgDto> msgArr = null;
				
				/* Added by Parnika */
				if(leadRegistrationBean.isQualifiedLeadStatus()){
					 msgArr = dao.insertRecord(leadList, Constants.SOURCE_TYPE_OUTBOUND);
				}
				else{
					 msgArr = dao.insertRecord(leadList, Constants.SOURCE_TYPE_CALL_CENTER);
				}
				
				
				/* End of changes by Parnika */
				for(int ii=0;ii<msgArr.size();ii++)
				{
					if(msgArr.get(ii).getMsgId() == Constants.LEAD_INSERT_NEW_LEAD)
					{
						newLeadIds = newLeadIds+", "+msgArr.get(ii).getLeadId();
					}
					if(msgArr.get(ii).getMsgId() == Constants.LEAD_INSERT_EXISTING_LEAD)
					{
						existingLeadIds = existingLeadIds+ ", <A HREF='leadRegistration.do?methodName=viewLeadDetails&leadId="+msgArr.get(ii).getLeadId()+"'>"+msgArr.get(ii).getLeadId()+"</A> ";
					}
				}
			
				if(!"".equals(newLeadIds))
				{
				insertStatus.delete(0,14);  // remove INSERT_FAILURE
				insertStatus.append("Lead Registered Successfully. Lead Id : "+newLeadIds.replaceFirst(",", "")+"\n");
				}
				
				if(!"".equals(existingLeadIds))
				{
					if("".equals(newLeadIds))
					{
						insertStatus.delete(0,14);  // remove INSERT_FAILURE
					}
				insertStatus.append("\nLead Existing : "+existingLeadIds.replaceFirst(",", ""));
				}
				
		}	
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		
		return insertStatus.toString();				
	}
	
	
	public  ArrayList<LeadDetailsDTO> getLeadListByLeadId(Long leadId) throws LMSException
	{
		LeadRegistrationDao dao= LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();
		try
		{
		return dao.getLeadListByLeadId(leadId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting lead list filtered on Lead Id : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	
	public  ArrayList<LeadDetailsDTO> getLeadListByLeadContact(Long leadId) throws LMSException
	{
		LeadRegistrationDao dao= LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();
		try
		{
		return dao.getLeadListByLeadContact(leadId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting lead list filtered on Lead Id : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public  ArrayList<LeadDetailsDTO> getLeadListByLeadId(LeadRegistrationFormBean leadRegistrationFormBean) throws LMSException
	{
		logger.info("asa::::lead registration seviceimpl");
		LeadRegistrationDao dao= LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();
		try
		{
		return dao.getLeadListByLeadId(leadRegistrationFormBean);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting lead list filtered on Lead Id : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public  ArrayList<LeadDetailsDTO> getLeadListByMobileNo(long mobileNo) throws LMSException
	{
		LeadRegistrationDao dao= LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();
		try
		{
			return dao.getLeadListByMobileNo(mobileNo);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting lead list filtered on Mobile No : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public  ArrayList<LeadDetailsDTO> getLeadDetails(long leadId) throws LMSException
	{
		LeadRegistrationDao dao= LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();
		try
		{
		return dao.getLeadDetails(leadId);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while getting lead details : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	
	public  ArrayList<LeadDetailsDTO> getLeadTransactinDetails(long leadId) throws LMSException
	{
		LeadRegistrationDao dao= LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();
		try
		{
		return dao.getLeadTransactinDetails(leadId);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while getting lead details : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	public  String[] getProductIDsOpenLeads(int prospectId) throws LMSException
	{
		LeadRegistrationDao dao= LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();
		ArrayList<LeadDetailsDTO> productIDsOpenLeads = new ArrayList<LeadDetailsDTO>();
		String[] productIDsOfOpenLeads = new String[0];
		try
		{
			productIDsOpenLeads = dao.getProductIDsOpenLeads(prospectId);
			if(null != productIDsOpenLeads)
			{
				productIDsOfOpenLeads = new String[productIDsOpenLeads.size()];
			}
			
			for(int ii=0;ii<productIDsOpenLeads.size(); ii++)
			{
				productIDsOfOpenLeads[ii] = productIDsOpenLeads.get(ii).getProductId()+"";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while getting open leades for the prospect : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		return productIDsOfOpenLeads ;
	}
	
	public String updateRecord(LeadRegistrationFormBean leadRegistrationBean,String flag)throws LMSException
	{
		
		String updateStatus ="";
		LeadRegistrationDao dao = LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();	
		MasterService masterService = new MasterServiceImpl();
		
		
		try
		{
			
			String productIds[] = leadRegistrationBean.getProductIds();
			int productIdArray[];
			if(null != productIds)
			{
				productIdArray = new int[productIds.length];
				
				for(int ii=0; ii< productIds.length ; ii++)
				{
					String productId = productIds[ii];
					String productId1[]= productId.split("#");
					String product2 = productId1[0];
					productIdArray[ii] = Integer.parseInt(product2);
				}
			}
			else
			{
				productIdArray = new int[1];
				productIdArray[0] = 0;
			}
			
			
			List<LeadDetailsDTO> leadList = new ArrayList<LeadDetailsDTO>();
			LeadDetailsDTO leadDetailsDTO = new LeadDetailsDTO();
			
				leadDetailsDTO.setLeadId(Long.parseLong(leadRegistrationBean.getLeadId()));
				leadDetailsDTO.setProspectId(Integer.parseInt(leadRegistrationBean.getProspectId()));
				leadDetailsDTO.setCustomerName(leadRegistrationBean.getCustomerName());
				logger.info("leadRegistrationBean.getContactNo()--->"+leadRegistrationBean.getContactNo());
				if(!"".equalsIgnoreCase(leadRegistrationBean.getContactNo())){
				leadDetailsDTO.setContactNo(Long.parseLong(leadRegistrationBean.getContactNo()));
				}
				leadDetailsDTO.setIpAddress(leadRegistrationBean.getIpAddress());
				
				leadDetailsDTO.setLeadProductId(leadRegistrationBean.getLeadProductId());
				if(!"".equals(leadRegistrationBean.getAlternateContactNo()) )
					leadDetailsDTO.setAlternateContactNo(leadRegistrationBean.getAlternateContactNo());			
				
				leadDetailsDTO.setProductIds(productIdArray);	
				leadDetailsDTO.setProductId(leadRegistrationBean.getProductId());	
				leadDetailsDTO.setProductLobId(masterService.getProductLobId(leadRegistrationBean.getProductId()));
				leadDetailsDTO.setLanguage(leadRegistrationBean.getLanguage());
				leadDetailsDTO.setAddress1(leadRegistrationBean.getAddress1());
				leadDetailsDTO.setAddress2(leadRegistrationBean.getAddress2());
				String cityId = null;
				if(!"".equals(leadRegistrationBean.getCityCode()))
				{
					cityId = leadRegistrationBean.getCityCode();
					//logger.info("cityId "+cityId);
					String[] str = cityId.split("#");
					//logger.info("str[1] "+str[1]);
					leadDetailsDTO.setCityCode(str[1]);
				}
		
				//leadDetailsDTO.setCityCode(leadRegistrationBean.getCityCode());
				
				leadDetailsDTO.setPinCode(leadRegistrationBean.getPinCode());
				leadDetailsDTO.setCreatedBy(leadRegistrationBean.getCreatedBy());
				leadDetailsDTO.setUdId(leadRegistrationBean.getUdid());
				if(!"".equals(leadRegistrationBean.getStateId()))
					leadDetailsDTO.setStateCode(leadRegistrationBean.getStateId());
				String crId = null;
				if(!"".equals(leadRegistrationBean.getCircleId()))
				{
					crId = leadRegistrationBean.getCircleId();
					//logger.info("crId "+crId);
					String[] str = crId.split("#");
					//logger.info("str[0] "+str[0]);
					leadDetailsDTO.setCircleId(Integer.parseInt(str[0]));
				}
		
					
				
				leadDetailsDTO.setEmail(leadRegistrationBean.getEmail());
				if(!"".equals(leadRegistrationBean.getLandlineNo()))
				leadDetailsDTO.setLandlineNo(leadRegistrationBean.getLandlineNo());
				//added by amarjeet to make fields editable in drop 3
				leadDetailsDTO.setHlrNo(leadRegistrationBean.getHlrNo());
				leadDetailsDTO.setRental(leadRegistrationBean.getRental());
				leadDetailsDTO.setPlanId(leadRegistrationBean.getPlanId());
				leadDetailsDTO.setAllocatedNo(leadRegistrationBean.getAllocatedNo());
				leadDetailsDTO.setOnlineCafNo(leadRegistrationBean.getOnlineCafNo());
				leadDetailsDTO.setTransactionRefNo(leadRegistrationBean.getTransactionRefNo());
				leadDetailsDTO.setPayment(leadRegistrationBean.getPayment());
				//added by amarjeet to make fields editable in drop 3
				leadDetailsDTO.setMaritalStatus(leadRegistrationBean.getMaritalStatus());
				leadDetailsDTO.setExistingCustomer(leadRegistrationBean.getExistingCustomer());
				leadDetailsDTO.setLeadProspectId(leadRegistrationBean.getLeadProspectId());
				//added by Nancy
				leadDetailsDTO.setSalesChannelCode(leadRegistrationBean.getSalesChannelCode());
				if(!"".equals(leadRegistrationBean.getRequestType()))
					leadDetailsDTO.setRequestType(Integer.parseInt(leadRegistrationBean.getRequestType()));
				
				StringBuffer appointmentTime = new StringBuffer();
				if (!"".equals(leadRegistrationBean.getAppointmentDate()))
				{
					appointmentTime.append(leadRegistrationBean.getAppointmentDate());
					
					if(!"".equals(leadRegistrationBean.getAppointmentHour()))
					{
						appointmentTime.append(" ").append(leadRegistrationBean.getAppointmentHour());
						
						if(!"".equals(leadRegistrationBean.getAppointmentMinute()))
						{
							appointmentTime.append(":").append(leadRegistrationBean.getAppointmentMinute());
						}
					}
				}
				
				leadDetailsDTO.setAppointmentTime(appointmentTime.toString());
				StringBuffer appointmentEndTime=new StringBuffer();
				if (!"".equals(leadRegistrationBean.getAppointmentEndDate()))
				{
					appointmentEndTime.append(leadRegistrationBean.getAppointmentEndDate());
					
					if(!"".equals(leadRegistrationBean.getAppointmentEndHour()))
					{
						appointmentEndTime.append(" ").append(leadRegistrationBean.getAppointmentEndHour());
						
						if(!"".equals(leadRegistrationBean.getAppointmentEndMinute()))
						{
							appointmentEndTime.append(":").append(leadRegistrationBean.getAppointmentEndMinute());
						}
					}
				}
				leadDetailsDTO.setAppointmentEndTime(appointmentEndTime.toString());
				leadDetailsDTO.setRemarks(leadRegistrationBean.getRemarks());
								
				leadDetailsDTO.setLeadSubSubStatusid(leadRegistrationBean.getLeadSubSubStatusid());
				
				//added by Nancy for new config:
				leadDetailsDTO.setAllStatusId(leadRegistrationBean.getAllStatusId());

				if(!"".equals(leadRegistrationBean.getSourceId()))
					leadDetailsDTO.setSourceId(Integer.parseInt(leadRegistrationBean.getSourceId()));
				
				if(!"".equals(leadRegistrationBean.getSubSourceId()))
					leadDetailsDTO.setSubSourceId(Integer.parseInt(leadRegistrationBean.getSubSourceId()));
				//logger.info("============ leadRegistrationBean.getZoneCode() :"+leadRegistrationBean.getZoneCode());
				leadDetailsDTO.setZoneCode(leadRegistrationBean.getZoneCode());
				leadDetailsDTO.setCityZoneCode(leadRegistrationBean.getCityZoneCode());
				leadDetailsDTO.setSubZoneName(leadRegistrationBean.getSubZoneName());
				leadDetailsDTO.setRsuCode(leadRegistrationBean.getRsuCode());
				leadDetailsDTO.setIsSecondcall(leadRegistrationBean.getIsSecondCall());
				
				
				if(!"".equals(leadRegistrationBean.getLeadStatusId()))
				{
				 String[] statusSubStatus = leadRegistrationBean.getLeadStatusId().split("#");
			
					leadDetailsDTO.setLeadStatusId(Integer.parseInt(statusSubStatus[0]));					
					leadDetailsDTO.setLeadSubStatusId(Integer.parseInt(statusSubStatus[1]));
				}
				
				if(!"".equalsIgnoreCase(leadRegistrationBean.getAllStatusId()))
				{
					String[] subsubstatus = leadRegistrationBean.getAllStatusId().split("#");
					leadDetailsDTO.setLeadStatusId(Integer.parseInt(subsubstatus[0]));					
					leadDetailsDTO.setLeadSubStatusId(Integer.parseInt(subsubstatus[1]));
					leadDetailsDTO.setLeadSubSubStatusid(Integer.parseInt(subsubstatus[2]));
				}
				leadList.add(leadDetailsDTO);
				
			
				updateStatus = dao.updateLeadDetailsDialler(leadList,flag);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		
		return updateStatus;				
	}


	public String insertLeadSearchTransaction(LeadRegistrationFormBean leadRegistrationFormBean, String userLoginId,String ipaddress) throws LMSException {
		
		LeadRegistrationDao dao= LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();
		try
		{
		return dao.insertLeadSearchTransaction(leadRegistrationFormBean,userLoginId,ipaddress);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting lead list filtered on Lead Id : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public String insertLeadSearchDialerTransaction(LeadRegistrationFormBean leadRegistrationFormBean, String userLoginId,String ipaddress) throws LMSException {
		
		LeadRegistrationDao dao= LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();
		try
		{
		return dao.insertLeadSearchDialerTransaction(leadRegistrationFormBean,userLoginId,ipaddress);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting lead list filtered on Lead Id : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	
	
	
	//added by Nancy 
	
	public JSONObject getElementsAsJsonLeadSubStatus(String leadStatusId,int lobId) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		
		List list = getAllChildrenLeadSubStatus(leadStatusId,lobId);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			LeadStatusDTO dto=(LeadStatusDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}

	//added by Nancy
public ArrayList getAllChildrenLeadSubStatus(String leadStatusId,int lobId)throws Exception {
 		
		ArrayList leadSubStatusList=new ArrayList();
		MasterDao dao= MasterDaoImpl.masterDaoInstance();//changed by srikant new MasterDaoImpl();
		if (leadStatusId != null){
			leadSubStatusList= dao.getLeadSubStatusList1(leadStatusId,lobId);
			
		}		
		return leadSubStatusList;
	}
	//sud
	public  ArrayList<ProductDTO> getProductLobList() throws LMSException
	{
		LeadRegistrationDao dao= LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();
		try
		{
		return dao.getProductLobList();
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting product list : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	//sud


	
	public ArrayList<LeadDetailsDTO> getLeadListByTid(String tid)
			throws LMSException {
		LeadRegistrationDao dao= LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();
		try
		{
		return dao.getLeadListByTid(tid);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting lead list filtered on Lead Id : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}


	public  ArrayList<LeadDetailsDTO> getHistoryDetails(long leadId) throws LMSException
	{
		LeadRegistrationDao dao= LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();
		try
		{
		return dao.getHistoryDetails(leadId);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while getting lead details : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	public String update4GRecord(LeadRegistrationFormBean leadRegistrationFormBean)throws LMSException
	{
		
		String update4GStatus ="";
		LeadRegistrationDao dao = LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();	
		MasterService masterService = new MasterServiceImpl();
		StringBuffer appointmentTime = new StringBuffer();
		try
		{
			
			LeadDetailsDTO leadDetailsDTO = new LeadDetailsDTO();
			leadDetailsDTO.setCircleMasterId(Integer.parseInt(leadRegistrationFormBean.getCircleId()));
			leadDetailsDTO.setCircleId(Integer.parseInt(leadRegistrationFormBean.getCircle()));
			leadDetailsDTO.setCityCode(leadRegistrationFormBean.getCityCode());
			leadDetailsDTO.setCityZoneCode(leadRegistrationFormBean.getCityZoneCode());
			leadDetailsDTO.setPinCode(leadRegistrationFormBean.getPinCode());
			leadDetailsDTO.setAddress1(leadRegistrationFormBean.getAddress1());
			leadDetailsDTO.setAddress2(leadRegistrationFormBean.getAddress2());
			leadDetailsDTO.setPlan(leadRegistrationFormBean.getPlan());
			leadDetailsDTO.setTranRefno(leadRegistrationFormBean.getTranRefno());
			leadDetailsDTO.setPytAmt(leadRegistrationFormBean.getPytAmt());
			leadDetailsDTO.setFeasibilityParam(leadRegistrationFormBean.getFeasibilityParam());
			leadDetailsDTO.setDevicetaken(leadRegistrationFormBean.getDevicetaken());
			//leadDetailsDTO.setExtraParam3(leadRegistrationFormBean.getExtraParam3());
			//leadDetailsDTO.setExtraParam4(leadRegistrationFormBean.getExtraParam4());
			leadDetailsDTO.setExtraParam5(leadRegistrationFormBean.getExtraParam5());
			leadDetailsDTO.setExtraParam6(leadRegistrationFormBean.getExtraParam6());
			//leadDetailsDTO.setTotalDue(leadRegistrationFormBean.getTotalDue());
			leadDetailsDTO.setLeadId(Long.parseLong(leadRegistrationFormBean.getLeadId()));
			leadDetailsDTO.setAppointmentStatus(leadRegistrationFormBean.getAppointmentStatus());
			leadDetailsDTO.setAppointmentTime(leadRegistrationFormBean.getAppointmentTime());
			leadDetailsDTO.setAppointmentEndTime(leadRegistrationFormBean.getAppointmentEndTime());
			
		
			leadDetailsDTO.setRequestCategory(leadRegistrationFormBean.getRequestCategoryId());	
			
			leadDetailsDTO.setFeasibilityParam(leadRegistrationFormBean.getFeasibilityParam());
			
			
			StringBuffer appointmentEndTime = new StringBuffer();
			
			
	
			//when appointments have been cancelled, selected values go into both fields:
			if(leadRegistrationFormBean.getAppointmentStatus().equals("false"))
			{
			leadDetailsDTO.setAppointmentStatus(Constants.APPOINTMENT_CANCEL);
			
			if (!"".equals(leadRegistrationFormBean.getAppointmentStartDate()))
			{
			
				appointmentTime.append(leadRegistrationFormBean.getAppointmentStartDate());
				
				if(!"".equals(leadRegistrationFormBean.getAppointmentStartHour()))
				{
					appointmentTime.append(" ").append(leadRegistrationFormBean.getAppointmentStartHour());
					
					if(!"".equals(leadRegistrationFormBean.getAppointmentStartMinute()))
					{
						appointmentTime.append(":").append(leadRegistrationFormBean.getAppointmentStartMinute());
						
					}
				}
			}
			leadDetailsDTO.setAppointmentTime(appointmentTime.toString());
			
			if (!"".equals(leadRegistrationFormBean.getAppointmentEndDate()))
			{
			
				appointmentEndTime.append(leadRegistrationFormBean.getAppointmentEndDate());
				
				if(!"".equals(leadRegistrationFormBean.getAppointmentEndHour()))
				{
					appointmentEndTime.append(" ").append(leadRegistrationFormBean.getAppointmentEndHour());
					
					if(!"".equals(leadRegistrationFormBean.getAppointmentEndMinute()))
					{
						appointmentEndTime.append(":").append(leadRegistrationFormBean.getAppointmentEndMinute());
						
					}
				}
			}
			leadDetailsDTO.setAppointmentEndTime(appointmentEndTime.toString());
			
			}
			//certain appointment has been chosen:
			else if(leadRegistrationFormBean.getAppointmentStatus().equalsIgnoreCase("true"))
			{
				leadDetailsDTO.setAppointmentStatus(Constants.APPOINTMENT_CHOSEN);
				try {
					if(leadRegistrationFormBean.getCheckslot() !=null && leadRegistrationFormBean.getCheckslot().length()>0){
						String date[]  =leadRegistrationFormBean.getCheckslot().split("TO");
						leadDetailsDTO.setAppointmentTime(date[0]);
						leadDetailsDTO.setAppointmentEndTime(date[1]);
						}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			 
			else{
				leadDetailsDTO.setAppointmentStatus(Constants.APPOINTMENT_NOTCHOSEN);
				try {
					//if(leadRegistrationFormBean.getCheckslot() !=null && leadRegistrationFormBean.getCheckslot().length()>0){
						/*String date[]  =leadRegistrationFormBean.getCheckslot().split("TO");
						leadDetailsDTO.setAppointmentTime(date[0]);
						leadDetailsDTO.setAppointmentEndTime(date[1]);*/
					
					
					if (!"".equals(leadRegistrationFormBean.getAppointmentStartDate()))
					{
					
						appointmentTime.append(leadRegistrationFormBean.getAppointmentStartDate());
						
						if(!"".equals(leadRegistrationFormBean.getAppointmentStartHour()))
						{
							appointmentTime.append(" ").append(leadRegistrationFormBean.getAppointmentStartHour());
							
							if(!"".equals(leadRegistrationFormBean.getAppointmentStartMinute()))
							{
								appointmentTime.append(":").append(leadRegistrationFormBean.getAppointmentStartMinute());
								
							}
						}
					}
					leadDetailsDTO.setAppointmentTime(appointmentTime.toString());
					
					if (!"".equals(leadRegistrationFormBean.getAppointmentEndDate()))
					{
					
						appointmentEndTime.append(leadRegistrationFormBean.getAppointmentEndDate());
						
						if(!"".equals(leadRegistrationFormBean.getAppointmentEndHour()))
						{
							appointmentEndTime.append(" ").append(leadRegistrationFormBean.getAppointmentEndHour());
							
							if(!"".equals(leadRegistrationFormBean.getAppointmentEndMinute()))
							{
								appointmentEndTime.append(":").append(leadRegistrationFormBean.getAppointmentEndMinute());
								
							}
						}
					}
					leadDetailsDTO.setAppointmentEndTime(appointmentEndTime.toString());
					
					
					
					
					//}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			update4GStatus = dao.update4GRecord(leadDetailsDTO);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
		
		return update4GStatus;				
	}


	@Override
	public Boolean getChannelPartnerFlag(long leadId,String loginId) {
		LeadRegistrationDao dao= LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by naresh chander to 
		Boolean flag=false;
		try {
 flag= dao.getChannelPartnerFlag(leadId,loginId);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
/////added by Pernica for postpaid lead search
	public  ArrayList<LeadDetailsDTO> getLeadListByLeadIdAndProductID(Long leadId,ArrayList<LeadDetailsDTO> list ) throws LMSException
	{
		LeadRegistrationDao dao= LeadRegistrationDaoImpl.leadRegistrationDaoInstance();//changed by srikant new LeadRegistrationDaoImpl();
		try
		{
		return dao.getLeadListByLeadIdAndProductID(leadId, list);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting lead list filtered on Lead Id : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	
	//Added by satish
	public ArrayList<LeadDetailsDTO> getLeadListofcheckLeadcreationByTid(LeadRegistrationFormBean leadRegistrationFormBean)throws LMSException{
		LeadRegistrationDao dao= LeadRegistrationDaoImpl.leadRegistrationDaoInstance();
		try
		{
		return dao.getLeadListofcheckLeadcreationByTid(leadRegistrationFormBean);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting lead list filtered on TXN Id : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	//added by satish

}
