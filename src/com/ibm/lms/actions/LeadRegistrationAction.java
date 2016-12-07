/*
 * Created on May 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.lms.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ibm.lms.common.Constants;
import com.ibm.lms.common.DateHelper;
import com.ibm.lms.common.LMSStatusCodes;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.dto.CircleDTO;
import com.ibm.lms.dto.CircleForProductDTO;
import com.ibm.lms.dto.CityDTO;
import com.ibm.lms.dto.CityZoneDTO;
import com.ibm.lms.dto.LeadDetailsDTO;
import com.ibm.lms.dto.LeadStatusDTO;
import com.ibm.lms.dto.MTDReportDTO;
import com.ibm.lms.dto.NoneditablefieldsDTO;
import com.ibm.lms.dto.PINCodeDTO;
import com.ibm.lms.dto.ProductDTO;
import com.ibm.lms.dto.ProductLobDTO;
import com.ibm.lms.dto.RSUDTO;
import com.ibm.lms.dto.RequestCategoryDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.dto.ZoneDTO;
import com.ibm.lms.engine.util.InterfaceHelper;
import com.ibm.lms.engine.util.ServerPropertyReader;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.LeadRegistrationFormBean;
import com.ibm.lms.services.LeadRegistrationService;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.ProductMappingService;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.LeadRegistrationServiceImpl;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.services.impl.ProductMappingServiceImpl;
import com.ibm.lms.services.impl.UserMstrServiceImpl;
import com.ibm.lms.wf.dto.Constant;
import com.ibm.lms.wf.dto.LeadDetailDTO;
import com.ibm.lms.wf.forms.LeadForm;
import com.ibm.lms.wf.services.AssignedLeadsManager;
import com.ibm.lms.wf.services.FeasibleLeadManager;
import com.ibm.lms.wf.services.impl.AssignedLeadsManagerImpl;
import com.ibm.lms.wf.services.impl.FeasibleLeadManagerImpl;

/**
 * @author Kundan Kumar
 * @since 18th Feb, 2013
 */
public class LeadRegistrationAction extends DispatchAction {
	private static final Logger logger;
	static {
		logger = Logger.getLogger(LeadRegistrationAction.class);
	}
	
	public ActionForward init(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		        saveToken(request);
				UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
				LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
				logger.info(userBean.getUserLoginId() + " entered init omethod for Lead Registration page.");
				leadRegistrationFormBean.reset(mapping,request);
				

				MasterService masterService = new MasterServiceImpl();
				//leadRegistrationFormBean.setProductList(masterService.getProductList());
				leadRegistrationFormBean.setMobilityProductList(masterService.getMobilityProductListNew());
				leadRegistrationFormBean.setTelemediaProductList(masterService.getTelemediaProductList());
				leadRegistrationFormBean.setFourGflag("false");
				leadRegistrationFormBean.setOtherProductsList(masterService.getOtherProductsList());
				//leadRegistrationFormBean.setFourGProductsList(masterService.getFourgProductList());
				//	leadRegistrationFormBean.setCityList(masterService.getCityList());g
				//	leadRegistrationFormBean.setPinCodeList(masterService.getPinCodeList());
				//leadRegistrationFormBean.setStateList(masterService.getStateList());
				
				List<ProductLobDTO> list  = masterService.getProductLobList();
				/*String Gflag  =ServerPropertyReader.getString("4G.leadRegistration.initialize");
				List<ProductLobDTO> listMains   = new ArrayList<ProductLobDTO>();
				ProductLobDTO lobDTO =null;
				for(int i=0; i<list.size(); i++) {
					 lobDTO  = list.get(i);
					if(!"YES".equalsIgnoreCase(Gflag) && lobDTO.getProductLobID()==Integer.parseInt(PropertyReader.getAppValue("lms.4G.productLobId"))) {
						listMains.add(lobDTO); 
					}
				}*/
				request.setAttribute("productLobList", list);
				leadRegistrationFormBean.setCircleList(masterService.getCircleList());
				

				logger.info(userBean.getUserLoginId() + " exited init method for Lead Registration page.");
				
				return mapping.findForward("initialize");
				
		}
	
	//added by aman for lead final status update on 23/01/14
	
	public ActionForward initLeadFinalStatus(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		        saveToken(request);
		        if(Utility.isValidRequest(request)) {
		        	return mapping.findForward("error");
		        }
				UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
				LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
				MasterService  masterService = new MasterServiceImpl();
				logger.info(userBean.getUserLoginId() + " entered initLeadFinalStatus method for Lead Registration page.");
				leadRegistrationFormBean.reset(mapping,request);

				leadRegistrationFormBean.setParam(masterService.getParameterName(PropertyReader.getAppValue("finalUpdate.no.of.days")));

				leadRegistrationFormBean.setLeadId("");
				leadRegistrationFormBean.setOlmId("");
				leadRegistrationFormBean.setStartDate("");
				leadRegistrationFormBean.setEndDate("");
				leadRegistrationFormBean.setCircleId("0");
				/*String view =  "";
				if(request.getParameter("view") !=null){
					view = request.getParameter("view").toString();
					leadRegistrationFormBean.setView(view);
				}
				*/
				
			//	ProductMappingForm productBean = (ProductMappingForm) form;
			//	ProductMappingService productService = new ProductMappingServiceImpl();
				
				LeadRegistrationService leadRegistrationService = new LeadRegistrationServiceImpl();
				
				
				// Populating Dropdowns
				leadRegistrationFormBean.setProductList(leadRegistrationService.getProductLobList());
			

				logger.info(userBean.getUserLoginId() + " exited initLeadFinalStatus method for Lead Registration page.");
				return mapping.findForward("leadSearchFinalUpdatePage");
				
		}
	
	
	public ActionForward insertRecord(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception { 
		String remotAddress=request.getRemoteAddr();
		logger.info("Request comming from :"+remotAddress);
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
		logger.info(userBean.getUserLoginId() + " entered insert method for Lead Registration page.");
		if ( !isTokenValid(request) ) {
			  return init(mapping, form, request, response);
			}
		ActionErrors errors = new ActionErrors();
		LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
		leadRegistrationFormBean.setCreatedBy(userBean.getUserLoginId());
		//leadRegistrationFormBean.setUdid(userBean.getUdId());
			
		ActionMessages messages = new ActionMessages();
		LeadRegistrationService leadRegistrationService = new LeadRegistrationServiceImpl();
		//leadRegistrationFormBean.setRemotAddress(remotAddress);
		String insertStatus = ""; 
		String leadId="";
		try {
			
			insertStatus = leadRegistrationService.insertRecord(leadRegistrationFormBean);
			leadId=insertStatus.substring(41, 55);
			leadRegistrationFormBean.setLeadId(leadId.trim());
			request.setAttribute("leadId", leadId);
			if ("INSERT_FAILURE".equals(insertStatus))
			{
				messages.add("msg1",new ActionMessage("lead.not.registered"));
				saveMessages(request, messages);	
				
			}
			else if("DIRTY_LEAD".equals(insertStatus))
			{
				messages.add("msg1",new ActionMessage("dirty.lead.registered"));
				saveMessages(request, messages);
			}
			else
			{
				request.setAttribute("insertStatus", insertStatus);
				leadRegistrationFormBean.reset(mapping, request);
				resetToken(request);
			}
		} catch (Exception e) {
		    e.printStackTrace();
			logger.error("Exception occured while registering lead :" + e.getMessage());
			errors.add("errors",new ActionError("lead.not.registered"));
			saveErrors(request,errors);
		}
		//return mapping.findForward("initialize");
	
		if("true".equalsIgnoreCase(leadRegistrationFormBean.getFourGflag())) {
			logger.info(leadRegistrationFormBean.getFourGflag());
			return initialize(mapping, leadRegistrationFormBean, request, response);
		}else {
			logger.info(leadRegistrationFormBean.getFourGflag());
			return init(mapping, leadRegistrationFormBean, request, response);
		}
		
	}
	
	public ActionForward searchLead(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		
		String leadSearchTransaction;
		String tid;
		MasterService masterService = new MasterServiceImpl();
			saveToken(request);
			UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
			String loginId=userBean.getUserLoginId();
				LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
				LeadRegistrationService leadRegistrationService = new LeadRegistrationServiceImpl();
				
				
				
				ActionForward forward = new ActionForward(); 
				
				forward =  mapping.findForward("leadSearchPage");
				
				
				
				if("".equals(leadRegistrationFormBean.getLeadId()) && "".equals(leadRegistrationFormBean.getContactNo()) && "".equals(leadRegistrationFormBean.getTid()))
				{
					leadRegistrationFormBean.reset(mapping, request);
					leadRegistrationFormBean.setInitStatus("true");	
					if(Utility.isValidRequest(request)) {
			        	return mapping.findForward("error");
			        }
				}else
				{
					//list to arraylist by pernica
					ArrayList<LeadDetailsDTO> LeadDetailsDTOList =null;
					Long leadId=0l;
					Long mobileNo=0l;
					leadRegistrationFormBean.setInitStatus("false");	
					
					if(!"".equals(leadRegistrationFormBean.getLeadId()))
				{
							leadId = Long.parseLong(leadRegistrationFormBean.getLeadId());
						 LeadDetailsDTOList = leadRegistrationService.getLeadListByLeadId(leadId);
						 
					
						leadSearchTransaction=leadRegistrationService.insertLeadSearchTransaction(leadRegistrationFormBean,userBean.getUserLoginId(),userBean.getIpaddress());
						/*//Added by Pernica for Postpaid Lead Search 
						
						request.setAttribute("productIdForPostpaid", masterService.getParameterName("ProductId"));
						String productIdForPostpaid= request.getAttribute("productIdForPostpaid").toString();
						
						int productIdOfLead=0;
							
							for(LeadDetailsDTO lead :LeadDetailsDTOList){
								productIdOfLead=lead.getProductId();
								logger.info("***************"+lead.getProductId());
							}
							
							logger.info("++++++++++++++++++++++++++++++++++++++++++++++++"+productIdOfLead);
							int productIdForPostpaidInt = Integer.parseInt(productIdForPostpaid);
							
							if(productIdForPostpaid!=null){
							if(productIdOfLead==productIdForPostpaidInt){
								logger.info("................in new lead search");
								
								LeadDetailsDTOList=	leadRegistrationService.getLeadListByLeadIdForPostpaid(leadId, LeadDetailsDTOList);
								
								if(LeadDetailsDTOList.size()!=0)
								{
									
									for(LeadDetailsDTO lead :LeadDetailsDTOList){
										productIdOfLead=lead.getProductId();
										logger.info("************************************"+lead.getUpc());
										logger.info("******************************************"+lead.getIdentityProofId());
										logger.info("***************"+lead.getProductId());
									}
									request.setAttribute("LEAD_LIST_NEW",LeadDetailsDTOList);
									//return forward;
								}
								
							}
							}
							
							
							//////////////end of changes by pernica
*/						
						
						// LeadDetailsDTOList = leadRegistrationService.getLeadListByLeadId(leadId);
						if(LeadDetailsDTOList.size()!=0)
						{
							request.setAttribute("LEAD_LIST",LeadDetailsDTOList);
							//return forward;
						}
						
					}
					if(!"".equals(leadRegistrationFormBean.getContactNo()) && (LeadDetailsDTOList==null || LeadDetailsDTOList.size()==0))
					{
						mobileNo = Long.parseLong(leadRegistrationFormBean.getContactNo());
						request.setAttribute("LEAD_LIST",leadRegistrationService.getLeadListByMobileNo(mobileNo));
						
						List<LeadDetailsDTO>  countDetailsList = new ArrayList<LeadDetailsDTO>();
						countDetailsList=leadRegistrationService.getLeadListByMobileNo(mobileNo);
						
						for(int i=0;i<countDetailsList.size();i++){
							logger.info("================================================================="+countDetailsList.get(i).getLeadId());
							
						}
					}
					
					if(!"".equals(leadRegistrationFormBean.getTid()) && (LeadDetailsDTOList==null || LeadDetailsDTOList.size()==0))
					{
						tid = leadRegistrationFormBean.getTid();
						request.setAttribute("LEAD_LIST",leadRegistrationService.getLeadListByTid(tid));
					}
					//forward = viewLeadDetails( mapping, leadRegistrationFormBean, request, response) ;
					Boolean channel_flag=leadRegistrationService.getChannelPartnerFlag(leadId,loginId);
					request.setAttribute("channel_flag", channel_flag);
					request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));
				}
				
				
				return forward;
		}
	
	public ActionForward searchLeadDialler(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		        
				LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
				 MasterService masterService = new MasterServiceImpl();
				String leadSearchDialerTransaction;
				saveToken(request);
				UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
			    boolean productFlag=false;
			    boolean leadDetailsPopUpFlag= false;
			    boolean dropdownFlag = false;
				
				logger.info("Inside lead search, Lead Id : "+leadRegistrationFormBean.getLeadId());
				if("".equals(leadRegistrationFormBean.getLeadId()))
				{
					leadRegistrationFormBean.reset(mapping, request);
					leadRegistrationFormBean.setInitStatus("true");	
					if(Utility.isValidRequest(request)) {
			        	return mapping.findForward("error");
			        }
				}else
				{ 
					
					String GISUrl = masterService.getParameterName(PropertyReader.getAppValue("lead.search.dialer.GisUrl"));
					if(GISUrl !=null && GISUrl.length() >0){
						String GISUserPass = masterService.getParameterName(PropertyReader.getAppValue("lead.search.dialer.GisUrl.UserPass"));
						leadRegistrationFormBean.setGISFlag("true");
						leadRegistrationFormBean.setGISUrlAddress(GISUrl);
						if(GISUserPass !=null && GISUserPass.trim().length() >0) {
							String ar []  = GISUserPass.split("#");
							leadRegistrationFormBean.setUsername(ar[0]);
							leadRegistrationFormBean.setPassword(ar[1]);
					
						}
					}
					
					leadRegistrationFormBean.setInitStatus("false");	
					Long leadId=0l;
					leadId = Long.parseLong(leadRegistrationFormBean.getLeadId());
					
					LeadRegistrationService leadRegistrationService = new LeadRegistrationServiceImpl();
					List<LeadDetailsDTO> LeadDetailsDTOList = leadRegistrationService.getLeadDetails(leadId);
						
                    String prodata  =ServerPropertyReader.getString("Dailler.leaddata.Product1");
					String prodata2  =ServerPropertyReader.getString("Dailler.leaddata.Product2");
					String prodata3  =ServerPropertyReader.getString("Dailler.leaddata.Product3");
					String prodata4  =ServerPropertyReader.getString("Dailler.leaddata.Product4");
					String prodata5  =ServerPropertyReader.getString("Dailler.leaddata.Product5");
					String prodata6  =ServerPropertyReader.getString("Dailler.leaddata.Product5");
					//String popUpProduct =  mstrService.getParameterName("NEW_PRODUCT");
					List<String> products  = null;
					try {
						 products = Arrays.asList(masterService.getParameterName("NEW_PRODUCT").trim().split(","));	
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					
					
					java.util.Iterator<LeadDetailsDTO> itr= LeadDetailsDTOList.iterator();
					while(itr.hasNext())
					{
						LeadDetailsDTO lsDto=itr.next();
						boolean productCheck = products !=null ? products.contains(String.valueOf(lsDto.getProductId())): false;
						
                    if(productCheck && ((lsDto.getLeadStatusId()!=500 && lsDto.getLeadSubStatusId()!=370) || (lsDto.getLeadStatusId()!=600 && lsDto.getLeadSubStatusId()!=370)))
					{
					//getting pop up contents:
					leadDetailsPopUpFlag = true;
					request.setAttribute("leadDetailsPopUpFlag",leadDetailsPopUpFlag);
					}
                    
                    if(productCheck && ((lsDto.getLeadStatusId()==500 && lsDto.getLeadSubStatusId()==370) || (lsDto.getLeadStatusId()==600 && lsDto.getLeadSubStatusId()==370)))
					{
					dropdownFlag = true;
					}
                    
						
					if(lsDto.getProductId() == Integer.parseInt(prodata) || lsDto.getProductId() == Integer.parseInt(prodata2) || lsDto.getProductId() == Integer.parseInt(prodata3) || lsDto.getProductId() == Integer.parseInt(prodata4) || lsDto.getProductId() == Integer.parseInt(prodata5) || lsDto.getProductId() == Integer.parseInt(prodata6))
					{
					  productFlag= true;
					  request.setAttribute("productFlag",productFlag);
					}
					}
              leadSearchDialerTransaction=leadRegistrationService.insertLeadSearchDialerTransaction(leadRegistrationFormBean,userBean.getUserLoginId(),userBean.getIpaddress());
					
					request.setAttribute("LEAD_DETAILS",LeadDetailsDTOList);
					
					if(LeadDetailsDTOList.size()==0)
					{  
						return mapping.findForward("leadSearchDialler");
					}
					else
					{			
						request.getSession().setAttribute("UPDATE_LEAD_DATA", "true");
						//leadRegistrationFormBean.setProductList(masterService.getProductList());
						//Added By Bhaskar
						leadRegistrationFormBean.setMobilityProductList(masterService.getMobilityProductListNew());
						leadRegistrationFormBean.setTelemediaProductList(masterService.getTelemediaProductList());
						leadRegistrationFormBean.setOtherProductsList(masterService.getOtherProductsList());
						//logger.info("mobilityList"+leadRegistrationFormBean.getMobilityProductList().size());
						//Ended  By Bhaskar
						LeadDetailsDTO leadDetailsDTO = LeadDetailsDTOList.get(0);
						
					//	leadRegistrationFormBean.setStateList(masterService.getStateList());
						leadRegistrationFormBean.setCircleForProductList(masterService.getCircleForProduct(masterService.getProductLobId(leadDetailsDTO.getProductId())));
					//	leadRegistrationFormBean.setRequestTypeList(masterService.getRequestTypeList());				
						leadRegistrationFormBean.setSourceList(masterService.getSourceList());
						leadRegistrationFormBean.setSubSourceList(masterService.getSubSourceList());
						leadRegistrationFormBean.setLeadProspectId(leadDetailsDTO.getLeadProspectId());
						//leadRegistrationFormBean.setRsuList(masterService.getRsuList());
						leadRegistrationFormBean.setProductList(masterService.getProductListByLob(leadDetailsDTO.getProductId(),leadDetailsDTO.getProductLobId()));
						
                        //Added by satish
						leadRegistrationFormBean.setExtraParam3(leadDetailsDTO.getExtraParam3());
						leadRegistrationFormBean.setExtraParam4(leadDetailsDTO.getExtraParam4());
						
						
						List<LeadDetailsDTO> list  = masterService.getPreviousoperatornameList();
						
						request.setAttribute("previousoperatorList", list);
                        leadRegistrationFormBean.setLeadId(""+leadDetailsDTO.getLeadId());
						leadRegistrationFormBean.setProspectId(""+leadDetailsDTO.getProspectId());
						leadRegistrationFormBean.setCustomerName(leadDetailsDTO.getCustomerName());
						leadRegistrationFormBean.setContactNo(""+leadDetailsDTO.getContactNo());
						
						String []productIds = new String[1];
						productIds[0] = leadDetailsDTO.getProductId()+"";
						
						leadRegistrationFormBean.setProductId(leadDetailsDTO.getProductId());
						//Added By Bhaskar
						leadRegistrationFormBean.setProductName(leadDetailsDTO.getProductName());
						leadRegistrationFormBean.setProductLobName(masterService.getProductLobName(leadDetailsDTO.getProductLobId()));
						
                      	//Ended By Bhaskar
						//Added by satish
						
						NoneditablefieldsDTO dto=new NoneditablefieldsDTO();
						dto=masterService.getNonEditableFiedls(leadRegistrationFormBean.getLeadId());
						
						if(dto != null )
						{		leadRegistrationFormBean.setSimNumber(dto.getSimno());
						
						leadRegistrationFormBean.setCustomerSegment(dto.getCustomer_Segment());
						leadRegistrationFormBean.setNationality(dto.getNationality());
						leadRegistrationFormBean.setGender(dto.getGender());
						leadRegistrationFormBean.setIdentityProofId(dto.getIdentityProofID());
						leadRegistrationFormBean.setIdentityProofType(dto.getIdentityProofType());
						leadRegistrationFormBean.setUpc(dto.getUpc());
						leadRegistrationFormBean.setPreviousOperator(dto.getPreviousoperatorname());
						leadRegistrationFormBean.setUpcGenDate(dto.getUpcGenDate());
						leadRegistrationFormBean.setPaymentDate(dto.getPaymentDate());
						leadRegistrationFormBean.setExistingPart(dto.getExistingpart());
						leadRegistrationFormBean.setMnpStatus(dto.getMnpstatus());
						leadRegistrationFormBean.setPlanType(dto.getPlanType());
						leadRegistrationFormBean.setDocumentCollectedFlag(dto.getDocumentCollectedFlag());
						leadRegistrationFormBean.setRelationName(dto.getRelationname());
						leadRegistrationFormBean.setPreviousCircle(dto.getPreviouscircle());
						}
						//leadRegistrationFormBean.setPreviousoperatornameList(masterService.getPreviousoperatornameList(leadRegistrationFormBean.getLeadId()));
						
                        //Ended By Bhaskar
						
						
						leadRegistrationFormBean.setProductIds(productIds);
						leadRegistrationFormBean.setProductIdsOpen(leadRegistrationService.getProductIDsOpenLeads(leadDetailsDTO.getProspectId()));
						leadRegistrationFormBean.setTelemediaProductList(masterService.getTelemediaProductList());
						leadRegistrationFormBean.setDthProductList(masterService.getDTHProductList());
						leadRegistrationFormBean.setAddress1(leadDetailsDTO.getAddress1());
						leadRegistrationFormBean.setAddress2(leadDetailsDTO.getAddress2());
						//leadRegistrationFormBean.setProductLobId(leadDetailsDTO.getProductLobId());
						leadRegistrationFormBean.setCircleId(leadDetailsDTO.getCircleId()+"#"+masterService.getProductLobId(leadDetailsDTO.getProductId())+"#"+leadDetailsDTO.getCircleMasterId());
						logger.info("circle Id "+leadDetailsDTO.getCircleId()+"#"+masterService.getProductLobId(leadDetailsDTO.getProductId())+"#"+leadDetailsDTO.getCircleMasterId());
						if(leadDetailsDTO.getCircleId() !=0)
						
							leadRegistrationFormBean.setCityList(masterService.getCityForCircleForDialer(leadDetailsDTO.getCircleMasterId()));	
						
							//logger.info("leadDetailsDTO.getCityCode()?????????????"+leadDetailsDTO.getCityCode());
							//logger.info("leadDetailsDTO.getZoneCode()?????????????"+masterService.getZoneCodeforCity(leadDetailsDTO.getCityCode()));
							
						leadRegistrationFormBean.setCityCode(masterService.getZoneCodeforCity(leadDetailsDTO.getCityCode())+"#"+leadDetailsDTO.getCityCode());
						
						if(!"".equalsIgnoreCase(leadDetailsDTO.getCityCode()))
						{
							//leadRegistrationFormBean.setPinCodeList(masterService.getPinCodeForCity(leadDetailsDTO.getCityCode()));
							leadRegistrationFormBean.setCityZoneList(masterService.getCityZoneOnCityChange(leadDetailsDTO.getCityCode()));	
						}
						leadRegistrationFormBean.setPinCode(leadDetailsDTO.getPinCode());
					
						
						leadRegistrationFormBean.setRsuList(masterService.getRsuForCityZoneCodeChange(leadDetailsDTO.getCircleMasterId(), leadDetailsDTO.getCityCode(), leadDetailsDTO.getCityZoneCode()));
						
						
						leadRegistrationFormBean.setCityZoneCode(leadDetailsDTO.getCityZoneCode());
						
							leadRegistrationFormBean.setEmail(leadDetailsDTO.getEmail());
						
						if(leadDetailsDTO.getAlternateContactNo()==null)
						{
							leadRegistrationFormBean.setAlternateContactNo("");
						}
						else
						{
							//logger.info("alternate contact number--"+leadDetailsDTO.getAlternateContactNo());
							leadRegistrationFormBean.setAlternateContactNo(leadDetailsDTO.getAlternateContactNo());
						}
						
						leadRegistrationFormBean.setLandlineNo(leadDetailsDTO.getLandlineNo());
						leadRegistrationFormBean.setExistingCustomer(leadDetailsDTO.getExistingCustomer());
						leadRegistrationFormBean.setLanguage(leadDetailsDTO.getLanguage());
						leadRegistrationFormBean.setMaritalStatus(leadDetailsDTO.getMaritalStatus());
						leadRegistrationFormBean.setRequestType(""+leadDetailsDTO.getRequestType());
						leadRegistrationFormBean.setAppointmentDate(leadDetailsDTO.getAppointmentDate());
						leadRegistrationFormBean.setAppointmentHour(leadDetailsDTO.getAppointmentHour());
						leadRegistrationFormBean.setAppointmentMinute(leadDetailsDTO.getAppointmentMinute());
                        leadRegistrationFormBean.setAppointmentEndDate(leadDetailsDTO.getAppointmentEndDate());
						leadRegistrationFormBean.setAppointmentEndHour(leadDetailsDTO.getAppointmentEndHour());
						leadRegistrationFormBean.setAppointmentEndMinute(leadDetailsDTO.getAppointmentEndMinute());
						
                        leadRegistrationFormBean.setSourceId(""+leadDetailsDTO.getSourceId());
						leadRegistrationFormBean.setSubSourceId(""+leadDetailsDTO.getSubSourceId());
						leadRegistrationFormBean.setSourceName(leadDetailsDTO.getSourceName());
						leadRegistrationFormBean.setSubSourceName(leadDetailsDTO.getSubSourceName());
					//	leadRegistrationFormBean.setRemarks(leadDetailsDTO.getRemarks());
						
						leadRegistrationFormBean.setZoneCode(masterService.getZoneCodeforCity(leadDetailsDTO.getCityCode()));
						leadRegistrationFormBean.setSubZoneName(leadDetailsDTO.getSubZoneName());
						leadRegistrationFormBean.setRsuCode(leadDetailsDTO.getRsuCode());
						leadRegistrationFormBean.setLeadStatusId(""+leadDetailsDTO.getLeadStatusId());
						leadRegistrationFormBean.setLeadProductId(leadDetailsDTO.getLeadProductId());
						logger.info("leadDetailsDTO.getLeadStatusName()****"+leadDetailsDTO.getLeadStatusName());
						leadRegistrationFormBean.setLeadStatusName(leadDetailsDTO.getLeadStatusName());
						
						leadRegistrationFormBean.setLeadStatusList(masterService.getLeadStatusDiallerList(leadDetailsDTO.getLeadStatusId(), leadDetailsDTO.getLeadSubStatusId(), leadDetailsDTO.getProductLobId()));
						
						//when site lead product is there , set the sub sub status list:
						if(dropdownFlag == true)
						{
							leadRegistrationFormBean.setLeadSubSubStatusList(masterService.getLeadSubSubStatusDiallerList(leadDetailsDTO.getLeadStatusId(), leadDetailsDTO.getLeadSubStatusId(), leadDetailsDTO.getLeadSubSubStatusid(), leadDetailsDTO.getProductLobId()));
						}
						
						leadRegistrationFormBean.setRsuList(masterService.getRsuForCityZoneCodeChange(leadDetailsDTO.getCircleMasterId(), leadDetailsDTO.getCityCode(), leadDetailsDTO.getCityZoneCode()));
						//leadRegistrationFormBean.setLeadSubStatusList(masterService.getLeadSubStatusDialerList(leadDetailsDTO.getLeadStatusId(), leadDetailsDTO.getLeadSubStatusId()));
						//added by Nancy
						leadRegistrationFormBean.setAppointment_Status(leadDetailsDTO.getAppointment_Status());
						int status = leadDetailsDTO.getLeadStatusId();
						boolean flag = false;
						if(status == LMSStatusCodes.ASSIGNED || status == LMSStatusCodes.INFO_INADEQUATE || status == LMSStatusCodes.REASSIGNED || status == LMSStatusCodes.WIRED || status == LMSStatusCodes.UNWIRED)
						{
							
							int substatus = leadDetailsDTO.getLeadSubStatusId();
							
							
							if(substatus == LMSStatusCodes.DIALER_SECOND_CALL)
								flag = true;
						}
						else
							flag = false;
						leadRegistrationFormBean.setIsSecondCall(flag);
						if ("".equals(leadRegistrationFormBean.getAppointmentHour()))
						{
							leadRegistrationFormBean.setAppointmentHour("10");
						}
						if ("".equals(leadRegistrationFormBean.getAppointmentMinute()))
						{
							leadRegistrationFormBean.setAppointmentMinute("00");
						}
						// adding by pratap lead search dialer page change 19-Dec 2013
						leadRegistrationFormBean.setCampaign(leadDetailsDTO.getCampaign());
						leadRegistrationFormBean.setGeoIpCity(leadDetailsDTO.getGeoIpCity());
						leadRegistrationFormBean.setPlanId(leadDetailsDTO.getPlanId());
						leadRegistrationFormBean.setRental(leadDetailsDTO.getRental());
						leadRegistrationFormBean.setRentalType(leadDetailsDTO.getRentalType());
						leadRegistrationFormBean.setPayment(leadDetailsDTO.getPayment());
						leadRegistrationFormBean.setAllocatedNo(leadDetailsDTO.getAllocatedNo());
						leadRegistrationFormBean.setTransactionRefNo(leadDetailsDTO.getTransactionRefNo());
						leadRegistrationFormBean.setOnlineCafNo(leadDetailsDTO.getOnlineCafNo());
						leadRegistrationFormBean.setProductLobId(leadDetailsDTO.getProductLobId());
						leadRegistrationFormBean.setLeadCategory(leadDetailsDTO.getLeadCategory());
						// gettging postpaid mobility product list added by pratap
						leadRegistrationFormBean.setMobilityProductList(masterService.getMobilityProductList());
						// end of pratap to geeting mobility product
						// getting restricted products from properties file : pratap 19-Dec 2013
						leadRegistrationFormBean.setRestrictedProductList(masterService.getRestrictedProductList());
						// End of  getting restricted products from properties file : pratap 19-Dec 2013
						
						/* Added by Parnika for LMS Phase 2 splitting 4 fields of sub source */
						
						leadRegistrationFormBean.setUtmSource(leadDetailsDTO.getUtmSource());
						leadRegistrationFormBean.setUtmMedium(leadDetailsDTO.getUtmMedium());
						leadRegistrationFormBean.setUtmTerm(leadDetailsDTO.getUtmTerm());
						leadRegistrationFormBean.setUtmCampaign(leadDetailsDTO.getUtmCampaign());
						leadRegistrationFormBean.setLeadPriority(leadDetailsDTO.getLeadPriority());
						//Added By Bhaskar
						//leadRegistrationFormBean.setPlanId(leadDetailsDTO.getPlanId());
						leadRegistrationFormBean.setUserType(leadDetailsDTO.getUserType());
						leadRegistrationFormBean.setRentalType(leadDetailsDTO.getRentalType());
						leadRegistrationFormBean.setFreebieCount(leadDetailsDTO.getFreebieCount());
						leadRegistrationFormBean.setBoosterCount(leadDetailsDTO.getBoosterCount());
						leadRegistrationFormBean.setFreebieTaken(leadDetailsDTO.getFreebieTaken());
						leadRegistrationFormBean.setBoosterTaken(leadDetailsDTO.getBoosterTaken());
						leadRegistrationFormBean.setPrepaidNumber(leadDetailsDTO.getPrepaidNumber());
						leadRegistrationFormBean.setOffer(leadDetailsDTO.getOffer());
						leadRegistrationFormBean.setDownloadLimit(leadDetailsDTO.getDownloadLimit());
						leadRegistrationFormBean.setDeviceMrp(leadDetailsDTO.getDeviceMrp());
						leadRegistrationFormBean.setVoiceBenefit(leadDetailsDTO.getVoiceBenefit());
						leadRegistrationFormBean.setDataQuota(leadDetailsDTO.getDataQuota());
						leadRegistrationFormBean.setFeasibilityParam(leadDetailsDTO.getFeasibilityParam());
						leadRegistrationFormBean.setQualLeadParam(leadDetailsDTO.getQualLeadParam());
						leadRegistrationFormBean.setLeadSubmitTime(leadDetailsDTO.getLeadSubmitTime());
						leadRegistrationFormBean.setFid(leadDetailsDTO.getFid());
						leadRegistrationFormBean.setTid(leadDetailsDTO.getTid());
						leadRegistrationFormBean.setKeyWord(leadDetailsDTO.getKeyWord());
						leadRegistrationFormBean.setFromPage(leadDetailsDTO.getFromPage());
						leadRegistrationFormBean.setService(leadDetailsDTO.getService());
						leadRegistrationFormBean.setUtmLabels(leadDetailsDTO.getUtmLabels());
						leadRegistrationFormBean.setReferUrl(leadDetailsDTO.getReferUrl());
						leadRegistrationFormBean.setFlag(leadDetailsDTO.getFlag());
						leadRegistrationFormBean.setCompany(leadDetailsDTO.getCompany());
						leadRegistrationFormBean.setPlan(leadDetailsDTO.getPlan());
						leadRegistrationFormBean.setDevicetaken(leadDetailsDTO.getDevicetaken());
						leadRegistrationFormBean.setBenefit(leadDetailsDTO.getBenefit());
						leadRegistrationFormBean.setPkgDuration(leadDetailsDTO.getPkgDuration());
						leadRegistrationFormBean.setRequestCategory(leadDetailsDTO.getRequestCategory());
						leadRegistrationFormBean.setSalesChannelCode(leadDetailsDTO.getSalesChannelCode());
						
						//////pernica
						
						List<ProductLobDTO> list1= masterService.getProductLobList();
						
					//	request.setAttribute("fromUpdate", "fromUpdate");

						
						//Ended By Bhaskar	
						String mobile_flag=masterService.getParameterName("Mobile_FLAG");
						request.setAttribute("mobile_flag", mobile_flag);
						request.setAttribute("productLobList", list1);
					}
					}
					return mapping.findForward("leadSearchDialler");
				}
	
	public ActionForward viewLeadDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
		
		LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
		LeadRegistrationService leadRegistrationService = new LeadRegistrationServiceImpl();
		MasterService masterService  = new MasterServiceImpl();
		Long leadId=0l;
		leadId = Long.parseLong(leadRegistrationFormBean.getLeadId());
		int productLobId=masterService.getProductId(leadId);
		
		String pageStatus=leadRegistrationFormBean.getPageStatus();
		
		
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
		int productId =0;
		int productIdForPostpaidInt =0;
		String prodata1  =ServerPropertyReader.getString("Dailler.leaddata.Product1");
		String prodata2  =ServerPropertyReader.getString("Dailler.leaddata.Product2");
		String prodata3  =ServerPropertyReader.getString("Dailler.leaddata.Product3");
		String prodata4  =ServerPropertyReader.getString("Dailler.leaddata.Product4");
		String prodata5  =ServerPropertyReader.getString("Dailler.leaddata.Product5");
		String prodata6  =ServerPropertyReader.getString("Dailler.leaddata.Product5");
		try
		{
			String productName = null;
			ArrayList<ProductDTO>  productList =null;
			///pernica
			ArrayList<LeadDetailsDTO> lead_details = null;
			
	
			if(!"".equals(leadRegistrationFormBean.getLeadId()))
			{
				lead_details =  leadRegistrationService.getLeadDetails(leadId);
			
				if(lead_details !=null && lead_details.size() >0 && lead_details.get(0)!=null ) {
					LeadDetailsDTO detailsDTO	   = lead_details.get(0);
					productId  = detailsDTO.getProductId();
					if(lead_details.get(0).getRequestType() >0)
					  productList = masterService.getProductListByLob(lead_details.get(0).getRequestType(),0);
				}
				if(productList !=null && productList.size() >0 && productList.get(0) !=null )
					 productName = productList.get(0).getProductName();
				for(LeadDetailsDTO detailsDTO :lead_details){
					detailsDTO.setRequestTypeDesc(productName);
				}
				//**************************Changes by Pernica for Lead Search by product ID**********************************//
				
				try {
					/*String configuredproductId =  masterService.getParameterName("ProductId");
					if(!"".equalsIgnoreCase(configuredproductId)&&configuredproductId!=null){
						logger.info("Flag is here");
						if(configuredproductId.indexOf(",")!=-1){
                           StringTokenizer st2 = new StringTokenizer(configuredproductId, ",");
							while (st2.hasMoreElements()) {
								
                                   if(Integer.parseInt(st2.nextElement().toString())==productId) {
                                	   productIdForPostpaidInt=Integer.parseInt(st2.nextElement().toString());
									logger.info("product id flag activated"+productId+Integer.parseInt(st2.nextElement().toString()));
									lead_details =	leadRegistrationService.getLeadListByLeadIdAndProductID(leadId, lead_details);
									
								}

							}
							// productIdForPostpaidInt = configuredproductId !=null ?Integer.parseInt(configuredproductId) :0;
								
						}
						else
						{
							productIdForPostpaidInt = configuredproductId !=null ?Integer.parseInt(configuredproductId) :0;
							if(productIdForPostpaidInt==productId)
							lead_details =	leadRegistrationService.getLeadListByLeadIdAndProductID(leadId, lead_details);
						}
						
					}*/
					
		               
							
							if(productId == Integer.parseInt(prodata1) || productId == Integer.parseInt(prodata2) || productId == Integer.parseInt(prodata3) || productId == Integer.parseInt(prodata4) || productId == Integer.parseInt(prodata5) || productId == Integer.parseInt(prodata6))
							{
								lead_details =	leadRegistrationService.getLeadListByLeadIdAndProductID(leadId, lead_details);
							}
							
					
				} catch (Exception e) {
				
					e.printStackTrace();
				}
				//**************************End of Changes by Pernica for Lead Search by product ID**********************************//
				
				request.setAttribute("LEAD_DETAILS",lead_details);
				request.setAttribute("LEAD_TRNS_DETAILS",leadRegistrationService.getLeadTransactinDetails(leadId));
				if(productLobId==Integer.parseInt(PropertyReader.getAppValue("lms.4G.productLobId")))
				request.setAttribute("LEAD_HIST_DETAILS", leadRegistrationService.getHistoryDetails(leadId));
				
				
				
				
				
				//leadRegistrationFormBean.setInitStatus("false");
			}
		}
		catch (Exception e) {
		    e.printStackTrace();
			logger.error("Exception occured while getting lead Details :" + e.getMessage());
			ActionErrors errors = new ActionErrors();
			errors.add("errors",new ActionError("lead.not.found"));
			saveErrors(request,errors);
		}
		leadRegistrationFormBean.setInitStatus("false");
		String prodata  =ServerPropertyReader.getString("4G.leaddata.Product");
		Boolean channel_flag=leadRegistrationService.getChannelPartnerFlag(leadId,userBean.getUserLoginId());
		request.setAttribute("channel_flag", channel_flag);

		
		request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));
		
		if(productId == Integer.parseInt(prodata1) || productId == Integer.parseInt(prodata2) || productId == Integer.parseInt(prodata3) || productId == Integer.parseInt(prodata4) || productId == Integer.parseInt(prodata5) || productId == Integer.parseInt(prodata6))
		{
			logger.info("Activated for new page");
			return mapping.findForward("leadDetailsForProductId");
		}
						
		if(pageStatus.equalsIgnoreCase("edit"))
		{
			if(productLobId==Integer.parseInt(PropertyReader.getAppValue("lms.4G.productLobId")))
			{
				return mapping.findForward("editLeadDetails");
			}
			    else
			{
			   return mapping.findForward("viewLeadDetails"); 	
			}
		}
			else
			{
				return mapping.findForward("viewLeadDetails"); 
			}
			
	}
	
	 

	public ActionForward updateLeadDetailsDialler(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{ 
		logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++11111111111111111111");
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
		LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
		String flag=request.getParameter("flag12");
				 
		if ( request.getSession().getAttribute("UPDATE_LEAD_DATA") == null ) { 
			
			leadRegistrationFormBean.setLeadId("");
			return searchLeadDialler(mapping, form, request, response);}
		request.getSession().setAttribute("UPDATE_LEAD_DATA", null);
		
		ActionForward forward = new ActionForward();	
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		
		LeadRegistrationService leadRegistrationService = new LeadRegistrationServiceImpl();
		request.setAttribute("fromUpdate", "fromUpdate");
	
		logger.info(userBean.getUserLoginId() + " entered into Lead dialer update method, with Lead Id : "+leadRegistrationFormBean.getLeadId());
		
		String updateStatus = ""; 
		try {
			String leadId = leadRegistrationFormBean.getLeadId();
			leadRegistrationFormBean.setCreatedBy(userBean.getUserLoginId());
			leadRegistrationFormBean.setUdid(userBean.getUdId());
			leadRegistrationFormBean.setIpAddress(userBean.getIpaddress());
			logger.info("&&&&&&&&&&&&"+leadRegistrationFormBean.getLeadStatusId());
			updateStatus = leadRegistrationService.updateRecord(leadRegistrationFormBean,flag);
			String[] ids = updateStatus.split("#");
			updateStatus= ids[0];			
			String newId = ids[1];
			
			if(Long.parseLong(newId)!=0)
			{
				
				leadId = newId;
			}
			
			if ("SUCCESS".equals(updateStatus))
			{
				messages.add("msg1",new ActionMessage("lead.updated"));
				saveMessages(request, messages);	
				// reset mandatory 
				leadRegistrationFormBean.reset(mapping, request);
				leadRegistrationFormBean.setLeadId(leadId);
				if(leadRegistrationFormBean.getFlag()==true){
					
					return searchLeadDialler(mapping, leadRegistrationFormBean, request, response);
				}
				else{
					leadRegistrationFormBean.setInitStatus("false");
						forward = viewLeadDetails(mapping, leadRegistrationFormBean, request, response);
					
				}
					
				
			}else
			{
				
				request.setAttribute("updateStatus", updateStatus);
				forward = mapping.findForward("leadSearchDialler");
			}
		} catch (Exception e) {
		    e.printStackTrace();
			logger.error("Exception occured while updating lead details Dialler :" + e.getMessage());
			errors.add("errors",new ActionError("lead.not.updated"));
			saveErrors(request,errors);
		}
		
		return forward;
	}
	public ActionForward getCityOnCircleChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		int circleMstriD ;
		int circleId;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		MasterService masterService = new MasterServiceImpl();
		Element optionElement;
		
		try
		{
			if (request.getParameter("circleMstriD") != null)
			{
				//circleMstriD = Integer.parseInt(request.getParameter("circleMstriD").toString());
				circleMstriD=Integer.parseInt(request.getParameter("circleMstriD").toString());
				
				List cityList = masterService.getCityForCircle(circleMstriD);
				
				if (cityList != null && cityList.size() > 0)
				{
					for (int intCounter = 0; intCounter < cityList.size(); intCounter++)
					{
						optionElement = root.addElement("option");
						optionElement.addAttribute("value", ((CityDTO)cityList.get(intCounter)).getZoneCode()+"#"+((CityDTO)cityList.get(intCounter)).getCityCode());
						optionElement.addAttribute("text", ((CityDTO)cityList.get(intCounter)).getCityName());
					}
				}
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				XMLWriter writer = new XMLWriter(out);
				writer.write(document);
				writer.flush();
				out.flush();
			}
		}
		catch (Exception applicationException)
		{
			applicationException.printStackTrace();
		}
		return null;
	}
	
	public ActionForward getCityOnCircleChangeForDialer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		int circleMstriD ;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		MasterService masterService = new MasterServiceImpl();
		Element optionElement;
		try
		{
			logger.info("============getCityOnCircleChange ==============");
			if (request.getParameter("circleMstriD") != null)
			{
				circleMstriD = Integer.parseInt(request.getParameter("circleMstriD").toString());
				List cityList = masterService.getCityForCircle(circleMstriD);
				if (cityList != null && cityList.size() > 0)
				{
					for (int intCounter = 0; intCounter < cityList.size(); intCounter++)
					{
						optionElement = root.addElement("option");
						//optionElement.addAttribute("value", ((CityDTO)cityList.get(intCounter)).getZoneCode()+"#"+((CityDTO)cityList.get(intCounter)).getCityCode());
						optionElement.addAttribute("value", ((CityDTO)cityList.get(intCounter)).getCityCode());
						optionElement.addAttribute("text", ((CityDTO)cityList.get(intCounter)).getCityName());
					}
				}
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				XMLWriter writer = new XMLWriter(out);
				writer.write(document);
				writer.flush();
				out.flush();
			}
		}
		catch (Exception applicationException)
		{
			applicationException.printStackTrace();
		}
		return null;
	}
	public ActionForward getPINOnCityChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		String CityCode ;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		MasterService masterService = new MasterServiceImpl();
		Element optionElement;
		
		try
		{
			if (request.getParameter("CityCode") != null)
			{
				CityCode = request.getParameter("CityCode").toString();
				logger.info("CityCode------------------"+CityCode);
				List pinList = masterService.getPinCodeForCity(CityCode);
				
				if (pinList != null && pinList.size() > 0)
				{
					for (int intCounter = 0; intCounter < pinList.size(); intCounter++)
					{
						optionElement = root.addElement("option");
						optionElement.addAttribute("value", ((PINCodeDTO)pinList.get(intCounter)).getPinCode());
						optionElement.addAttribute("text", ((PINCodeDTO)pinList.get(intCounter)).getPinCode());
					}
				}
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				XMLWriter writer = new XMLWriter(out);
				writer.write(document);
				writer.flush();
				out.flush();
			}
		}
		catch (Exception applicationException)
		{
			applicationException.printStackTrace();
		}
		return null;
	}
	public ActionForward getZoneOnCityChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		String CityCode ;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		MasterService masterService = new MasterServiceImpl();
		Element optionElement;
		try
		{
			if (request.getParameter("CityCode") != null)
			{
				CityCode = request.getParameter("CityCode").toString();
				List zoneList = masterService.getZoneForCity(CityCode);
				if (zoneList != null && zoneList.size() > 0)
				{
					for (int intCounter = 0; intCounter < zoneList.size(); intCounter++)
					{
						optionElement = root.addElement("zoneOption");
						optionElement.addAttribute("value", ((ZoneDTO)zoneList.get(intCounter)).getZoneCode());
						optionElement.addAttribute("text", ((ZoneDTO)zoneList.get(intCounter)).getZoneName());
					}
				}
				logger.info("CityCode------------------"+CityCode);
				List pinList = masterService.getPinCodeForCity(CityCode);
				
				if (pinList != null && pinList.size() > 0)
				{
					for (int intCounter = 0; intCounter < pinList.size(); intCounter++)
					{
						optionElement = root.addElement("pinCodeOption");
						optionElement.addAttribute("value", ((PINCodeDTO)pinList.get(intCounter)).getPinCode());
						optionElement.addAttribute("text", ((PINCodeDTO)pinList.get(intCounter)).getPinCode());
					}
				}
				
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				XMLWriter writer = new XMLWriter(out);
				writer.write(document);
				writer.flush();
				out.flush();
			}
		}
		catch (Exception applicationException)
		{
			applicationException.printStackTrace();
		}
		return null;
	}
	public ActionForward getRSUOnZoneChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		String zoneCode ;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		MasterService masterService = new MasterServiceImpl();
		Element optionElement;
		try
		{
			if (request.getParameter("zoneCode") != null)
			{
				zoneCode = request.getParameter("zoneCode").toString();
				List rsuList = masterService.getRsuForZone(zoneCode);
				logger.info("rsuList-----------"+rsuList);
				if (rsuList != null && rsuList.size() > 0)
				{
					for (int intCounter = 0; intCounter < rsuList.size(); intCounter++)
					{
						optionElement = root.addElement("option");
						optionElement.addAttribute("value", ((RSUDTO)rsuList.get(intCounter)).getRsuCode());
						optionElement.addAttribute("text", ((RSUDTO)rsuList.get(intCounter)).getRsuCode());
					}
				}
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				XMLWriter writer = new XMLWriter(out);
				writer.write(document);
				writer.flush();
				out.flush();
			}
		}
		catch (Exception applicationException)
		{
			applicationException.printStackTrace();
		}
		return null;
	}
	
	public ActionForward getCircleOnProductChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		logger.info("inside getCircleOnProductChange ???????????????????????????");
		int productLobId =0;
		int productId ;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		MasterService masterService = new MasterServiceImpl();
		Element optionElement;
		try
		{
			// changes for product search dialer
			if (request.getParameter("productLobId") != null && request.getParameter("productLobId").length() >0)
			{
				productLobId = Integer.parseInt(request.getParameter("productLobId").toString());
			}
			if (request.getParameter("productId") != null && request.getParameter("productId").length() >0)
			{
				productId = Integer.parseInt(request.getParameter("productId").toString());
				productLobId = masterService.getProductLobId(productId);
				logger.info("productLobId "+productLobId);
			}
			
			if (productLobId != 0)
			{
				//productLobId = Integer.parseInt(request.getParameter("productLobId").toString());
			
				List circleList = masterService.getCircleForProduct(productLobId);
				if (circleList != null && circleList.size() > 0)
				{
					for (int intCounter = 0; intCounter < circleList.size(); intCounter++)
					{
						//logger.info("(CircleForProductDTO)circleList.get(intCounter)).getCircleIdLobIdCircleMstrId() :"+((CircleForProductDTO)circleList.get(intCounter)).getCircleIdLobIdCircleMstrId());
						optionElement = root.addElement("option");
						//optionElement.addAttribute("value", String.valueOf(((CircleForProductDTO)circleList.get(intCounter)).getCircleId()));
						optionElement.addAttribute("value", ((CircleForProductDTO)circleList.get(intCounter)).getCircleIdLobIdCircleMstrId());
						optionElement.addAttribute("text", ((CircleForProductDTO)circleList.get(intCounter)).getCircleName());
					}
				}
				if( circleList.size()== 0)
				{
					optionElement = root.addElement("option");
					optionElement.addAttribute("value","'-1'");
					optionElement.addAttribute("text", "Select Circle");
				}
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				XMLWriter writer = new XMLWriter(out);
				writer.write(document);
				writer.flush();
				out.flush();
			
		}
		}
		catch (Exception applicationException)
		{
			applicationException.printStackTrace();
		}
		return null;
	}
	public ActionForward getCircleOnProductChangeForDialer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		logger.info("inside getCircleOnProductChangeForDialer");
		int productLobId =0;
		int productId ;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		MasterService masterService = new MasterServiceImpl();
		Element optionElement;
		try
		{
			// changes for product search dialer
			if (request.getParameter("productLobId") != null)
			{
				productLobId = Integer.parseInt(request.getParameter("productLobId").toString());
			}
			if (request.getParameter("productId") != null)
			{
				logger.info("proudct id :"+request.getParameter("productId"));
				productId = Integer.parseInt(request.getParameter("productId").toString());
				productLobId = masterService.getProductLobId(productId);
				logger.info("productLobId "+productLobId);
			}
			
			if (productLobId != 0)
			{
				//productLobId = Integer.parseInt(request.getParameter("productLobId").toString());
			
				List circleList = masterService.getCircleForProduct(productLobId);
				logger.info("circleList.size() "+circleList.size());
				if (circleList != null && circleList.size() > 0)
				{
					for (int intCounter = 0; intCounter < circleList.size(); intCounter++)
					{
						optionElement = root.addElement("option");
						optionElement.addAttribute("value", String.valueOf(((CircleForProductDTO)circleList.get(intCounter)).getCircleId()));
						//optionElement.addAttribute("value", ((CircleForProductDTO)circleList.get(intCounter)).getCircleIdLobIdCircleMstrId());
						optionElement.addAttribute("text", ((CircleForProductDTO)circleList.get(intCounter)).getCircleName());
					}
				}
				if( circleList.size()==0)
				{
					optionElement = root.addElement("option");
					optionElement.addAttribute("value","prataptesting");
					optionElement.addAttribute("text", "prataptesting");
					//document.add(optionElement);
				}
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				XMLWriter writer = new XMLWriter(out);
				writer.write(document);
				writer.flush();
				out.flush();
			
		}
		}
		catch (Exception applicationException)
		{
			applicationException.printStackTrace();
		}
		return null;
	}
	
	public ActionForward getZoneOnCircleChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		String circleIDLobIdCircleMstrId ;
		int circleId;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		MasterService masterService = new MasterServiceImpl();
		Element optionElement;
		try
		{
			if (request.getParameter("circleID") != null)
			{
				circleIDLobIdCircleMstrId = request.getParameter("circleID").toString();
				circleId=Integer.parseInt(circleIDLobIdCircleMstrId.split("#")[2].toString());
				//List cityList = masterService.getCityForCircle(circleId);
				List zoneList = masterService.getZoneForCircle(circleId);
				if (zoneList != null && zoneList.size() > 0)
				{
					for (int intCounter = 0; intCounter < zoneList.size(); intCounter++)
					{
						optionElement = root.addElement("option");
						optionElement.addAttribute("value", ((ZoneDTO)zoneList.get(intCounter)).getCityCode()+"#"+((ZoneDTO)zoneList.get(intCounter)).getZoneCode());
						optionElement.addAttribute("text", ((ZoneDTO)zoneList.get(intCounter)).getZoneName());
					}
				}
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				XMLWriter writer = new XMLWriter(out);
				writer.write(document);
				writer.flush();
				out.flush();
			}
		}
		catch (Exception applicationException)
		{
			applicationException.printStackTrace();
		}
		return null;
	}
	public ActionForward getCityOnZoneChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		String cityCode ;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		MasterService masterService = new MasterServiceImpl();
		Element optionElement;
		try
		{
			if (request.getParameter("cityCode") != null)
			{
				//logger.info("****************************"+request.getParameter("cityCode"));
				cityCode = request.getParameter("cityCode").toString();
				List cityList = masterService.getCityForZone(cityCode);
				
				if (cityList != null && cityList.size() > 0)
				{
					for (int intCounter = 0; intCounter < cityList.size(); intCounter++)
					{
						optionElement = root.addElement("option");
						optionElement.addAttribute("value", ((CityDTO)cityList.get(intCounter)).getCityCode());
						optionElement.addAttribute("text", ((CityDTO)cityList.get(intCounter)).getCityName());
					}
				}
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				XMLWriter writer = new XMLWriter(out);
				writer.write(document);
				writer.flush();
				out.flush();
			}
		}
		catch (Exception applicationException)
		{
			applicationException.printStackTrace();
		}
		return null;
	}

	public ActionForward getCityZoneOnCityChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		String cityCode ;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		MasterService masterService = new MasterServiceImpl();
		Element optionElement;
		try
		{
			if (request.getParameter("cityCode") != null)
			{
				cityCode = request.getParameter("cityCode").toString();
				List cityList = masterService.getCityZoneOnCityChange(cityCode);
				
				if (cityList != null && cityList.size() > 0)
				{
					for (int intCounter = 0; intCounter < cityList.size(); intCounter++)
					{
						optionElement = root.addElement("option");
						optionElement.addAttribute("value", ((CityZoneDTO)cityList.get(intCounter)).getCityZoneCode());
						optionElement.addAttribute("text", ((CityZoneDTO)cityList.get(intCounter)).getCityZoneName());
					}
				}
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				XMLWriter writer = new XMLWriter(out);
				writer.write(document);
				writer.flush();
				out.flush();
			}
		}
		catch (Exception applicationException)
		{
			applicationException.printStackTrace();
		}
		return null;
	}
	
	
	public ActionForward getZoneCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		String zCode ;
		MasterService masterService = new MasterServiceImpl();
		String zoneCode="";
		try
		{
			if (request.getParameter("zonecode") != null)
			{
				
				zCode = request.getParameter("zonecode").toString();
				zoneCode = masterService.getZoneCode(zCode);
				if(zoneCode.trim().equals("#"))zoneCode=" ";
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				
				out.print(zoneCode);
				out.flush();
			}
		}
		catch (Exception applicationException)
		{
			applicationException.printStackTrace();
		}
		return null;
	}
	
	public ActionForward getCityForPinCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		String pinCode ;
		MasterService masterService = new MasterServiceImpl();
		String cityCodeName="";
		try
		{
			if (request.getParameter("pinCode") != null)
			{
				pinCode = request.getParameter("pinCode").toString();
				cityCodeName = masterService.getCityForPinCode(pinCode);
				if(cityCodeName.trim().equals("#")) cityCodeName="";
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				out.print(cityCodeName);
				out.flush();
			}
		}
		catch (Exception applicationException)
		{
			applicationException.printStackTrace();
		}
		return null;
	}
	
	
	public ActionForward getDataForPinCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		String pinCode ;
		int circleMstrId=0;
		int productlobId=0;
		ProductMappingService productService = new ProductMappingServiceImpl();
		String details="";
		try
		{
			if (request.getParameter("pinCode") != null)
			{
				pinCode = request.getParameter("pinCode").toString(); 
				if(request.getParameter("circleMstrId") !=null && request.getParameter("circleMstrId").length() >0) {
					circleMstrId = Integer.parseInt(request.getParameter("circleMstrId").toString());
				}
				if(request.getParameter("productLobId") !=null && request.getParameter("productLobId").length() >0) {
					productlobId = Integer.parseInt(request.getParameter("productLobId").toString());
				}
				
				
				details = productService.getDataForPinCode(pinCode,circleMstrId,productlobId);
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				out.print(details);
				out.flush();
			}
		}
		catch (Exception applicationException)
		{
			applicationException.printStackTrace();
		}
		return null;
	}
	public ActionForward getLeadStatusIfLeadNotServiceAble(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		
		String pinCode ;
		int circleMstrId;
		MasterService masterService = new MasterServiceImpl();
		String details="";
		ArrayList<LeadStatusDTO> statuslist=null;
		LeadStatusDTO lsDto=null;
		Object notServiceAble=null;
		StringBuffer sb=new StringBuffer("");
		String leadId2;
		long leadId1;
		int product_id1;
		try
		{
			/*if(request.getParameter("nonServiceAble") == null)
				logger.info("nonServiceAble is null****************8 ");
			*/
			//changed by Nancy Agrawal.
			leadId2=request.getParameter("leadId");
			leadId1=Long.parseLong(leadId2);
			
			product_id1=masterService.getProductId(leadId1);
			if((request.getParameter("nonServiceAble") != null) && (request.getParameter("nonServiceAble").toString().equals("true")))
			{
				
				details = masterService.getLeadStatusIfLeadNotServiceAble(product_id1);
			}
			else if((request.getParameter("nonServiceAble") != null) && (request.getParameter("nonServiceAble").toString().equals("false")))
			{
			
			LeadRegistrationFormBean leadRegistrationFormBean=(LeadRegistrationFormBean)form;
			LeadRegistrationService leadRegistrationService = new LeadRegistrationServiceImpl();
			Long leadId=0l;
			String lead=(String)request.getParameter("leadId");
			leadId = Long.parseLong(lead);
		
			List<LeadDetailsDTO> LeadDetailsDTOList = leadRegistrationService.getLeadDetails(leadId);
			LeadDetailsDTO leadDetailsDTO = LeadDetailsDTOList.get(0);
			statuslist=masterService.getLeadStatusDiallerList(leadDetailsDTO.getLeadStatusId(), leadDetailsDTO.getLeadSubStatusId(), leadDetailsDTO.getProductLobId());
			java.util.Iterator<LeadStatusDTO> itr= statuslist.iterator();
			while(itr.hasNext())
			{
				lsDto=itr.next();
				//status_subStatus_id+"="+status_substatus;
				logger.info(lsDto.getLeadStatusSubStatusId()+"="+lsDto.getLeadStatus()+",");
				sb.append(lsDto.getLeadStatusSubStatusId()+"="+lsDto.getLeadStatus()+",");
			}
			
			details=sb.toString();
			}
			logger.info("details :"+details);
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			out.print(details);
			out.flush();
			
		}
		catch (Exception applicationException)
		{
			applicationException.printStackTrace();
		}
		return null;
	
	}
	
	public ActionForward searchLeadFinalStatus(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,	
			HttpServletResponse response)
			throws Exception {
			logger.info("lead registration action");
				LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
				UserMstrService userMstrService=new UserMstrServiceImpl();
				MasterService masterService = new MasterServiceImpl();

				UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
				ActionForward forward = new ActionForward(); 
				forward =  mapping.findForward("leadSearchFinalUpdatePage");
				
				String selectedProductLobId=request.getParameter("selectedProductLobId");
				String user_login_id=userBean.getUserLoginId();
				
				ArrayList<CircleDTO> circleList= userMstrService.getAllChildrenNewUser(selectedProductLobId,user_login_id);
				leadRegistrationFormBean.setCircleList(circleList);
				
				if("".equals(leadRegistrationFormBean.getLeadId()) && ("".equals(leadRegistrationFormBean.getStartDate()) && "".equals(leadRegistrationFormBean.getEndDate())))
				{
					leadRegistrationFormBean.reset(mapping, request);
					leadRegistrationFormBean.setInitStatus("true");	
				}else
				{
					Long leadId=0l;
					leadRegistrationFormBean.setInitStatus("false");	
					LeadRegistrationService leadRegistrationService = new LeadRegistrationServiceImpl();
					leadRegistrationFormBean.setProductList(leadRegistrationService.getProductLobList());
					leadRegistrationFormBean.setCircleList(circleList);
					if(!"".equals(leadRegistrationFormBean.getLeadId()) || (!"".equals(leadRegistrationFormBean.getStartDate()) && !"".equals(leadRegistrationFormBean.getEndDate())))
					{
						request.setAttribute("LEAD_LIST",leadRegistrationService.getLeadListByLeadId(leadRegistrationFormBean));
					}
					/*if("".equals(leadRegistrationFormBean.getLeadId()) && !"".equals(leadRegistrationFormBean.getOlmId()) && (!"".equals(leadRegistrationFormBean.getStartDate()) && !"".equals(leadRegistrationFormBean.getEndDate())))
					{
						logger.info("olm id block");
						request.setAttribute("LEAD_LIST",leadRegistrationService.getLeadListByLeadId(leadRegistrationFormBean));
					}
					leadRegistrationFormBean.setCircleId("");*/
				}

				request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));
				return forward;
		}
	public ActionForward viewFinalLeadStatusDetail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();
		LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean) form;
		AssignedLeadsManager assignedLeadsManager = new AssignedLeadsManagerImpl();
		LeadRegistrationService leadRegistrationService=new LeadRegistrationServiceImpl();
		UserMstrService userMstrService=new UserMstrServiceImpl();
		
		FeasibleLeadManager feasibleLeadManager = new FeasibleLeadManagerImpl();
		UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
		MasterService masterService = new MasterServiceImpl();

		saveToken(request);
		Constant cons= new Constant();
		List userList = null;
		try {
			leadRegistrationFormBean.setProductList(leadRegistrationService.getProductLobList());
			
			List<Constant> actionList = assignedLeadsManager.getActionList("LEAD_CLOSURE");
			//List<Constant> actionList=new ArrayList<Constant>();
		//	sessionUserBean.setKmActorId("15");
			
			Long leadID = Long.parseLong(leadRegistrationFormBean.getLeadId().toString());
			
			LeadDetailDTO detailDTO = assignedLeadsManager.viewLeadDetail(leadID);
			String selectedProductLobId=request.getParameter("selectedProductLobId");
			
			logger.info("selectedProductLobId"+selectedProductLobId);
		//	int productLobId=Integer.parseInt(selectedProductLobId);
			String user_login_id=sessionUserBean.getUserLoginId();
			
			ArrayList<CircleDTO> circleList= userMstrService.getAllChildrenNewUser(selectedProductLobId,user_login_id);
			leadRegistrationFormBean.setCircleList(circleList);
					
			if(sessionUserBean.getKmActorId().equalsIgnoreCase(Constants.CIRCLE_COORDINATOR_ACTOR)){
				userList = assignedLeadsManager.getUsersList(sessionUserBean.getCircleList(),sessionUserBean.getLobList());//Fetches user list for  Circle CoOrdinator 
			}else if(sessionUserBean.getKmActorId().equalsIgnoreCase(Constants.ZONAL_COORDINATOR_ACTOR)){
				userList = assignedLeadsManager.getZonalCoordinatorList(sessionUserBean.getCircleList(),sessionUserBean.getLobList() );//changed by sudhanshu
			}else{
				//userList = assignedLeadsManager.getChannelPartnerList(sessionUserBean.getCircleList(),sessionUserBean.getLobList() );//changed by sudhanshu
				userList = assignedLeadsManager.getChannelPartnerListForLead(sessionUserBean.getCircleList(), detailDTO,sessionUserBean.getLobList());
			}
			logger.info("get circle co-ordi");
			request.setAttribute("userList", userList);
			//List subStatusList = feasibleLeadManager.getSubStatusList(400);
			//actionList.addAll(subStatusList);
			request.setAttribute("actionList", actionList);
			detailDTO.setLeadID(String.valueOf(leadID));
			request.setAttribute("detail", detailDTO);
			
			request.setAttribute("leadID", String.valueOf(leadID));
			request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));
			//if("N".equalsIgnoreCase(leadRegistrationFormBean.getView()))
				forward = mapping.findForward("viewLeadFinalUpdateDetails");
			//else
				//forward = mapping.findForward("viewAssignedLeadDetail");
			return forward;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while initializing editUser page ");
		}
		return null;
	}
	public ActionForward closeTheLead(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		AssignedLeadsManager assignedLeadsManager = new AssignedLeadsManagerImpl();
		LeadForm commonForm = (LeadForm) form;
		if ( !isTokenValid(request) ) {
			  return init(mapping, form, request, response);
			}
		ActionMessages messages = new ActionMessages();
		UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
		Boolean flag = false;
		List userList = null;
		try {	
			commonForm.setUpdatedBy(sessionUserBean.getUserLoginId());
			commonForm.setUdId(sessionUserBean.getUdId());
			flag = assignedLeadsManager.closeTheLeadCloseLoop(commonForm);
			if(flag)
			{	commonForm.setMsg("Lead has been closed successfully!");
				resetToken(request);
			}
			else
			{
				commonForm.setMsg("Lead was not closed!");
			}
			request.setAttribute("msg", commonForm.getMsg());
			//saveMessages(request, messages);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurs in viewUserList: " + e.getMessage());
		}
		forward = mapping.findForward("leadSearchFinalUpdatePage");
		
		return forward;
	}
	
	//Added By Bhaskar
	
	
	public ActionForward excelImport(ActionMapping mapping,	ActionForm form,HttpServletRequest request,	HttpServletResponse response)throws Exception 
	{
			HttpSession session = request.getSession();
			UserMstr sessionUserBean =(UserMstr) session.getAttribute("USER_INFO");
			LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean) form;
			logger.info(sessionUserBean.getUserLoginId()	+ " Entered into the excelImport method of LeadRegistrationAction");
			LeadRegistrationService leadRegistrationService = new LeadRegistrationServiceImpl();
			UserMstrService userMstrservice = new UserMstrServiceImpl();
			sessionUserBean.setFileName("LeadFinalStatusReport.xls for lead final status update");
			try{
				
					//logger.info("zone code in action viewLeadDetails method :"+leadRegistrationFormBean.getZoneCode());
					if(leadRegistrationFormBean.getLeadId()!=null && !leadRegistrationFormBean.getLeadId().equalsIgnoreCase("")){
					request.setAttribute("leadFinalStatusLeads",leadRegistrationService.getLeadListByLeadId(leadRegistrationFormBean));
					}
					//request.setAttribute("LEAD_TRNS_DETAILS",leadRegistrationService.getLeadTransactinDetails(leadId));
			
				}catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception occurs in viewUserList: " + e.getMessage());
				sessionUserBean.setMessage("FAILED");
				userMstrservice.insertBulkDataTransactionLogs(sessionUserBean);
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("content-Disposition","attachment;filename=LeadFinalStatusReport.xls");
			sessionUserBean.setMessage("FAILED");
			userMstrservice.insertBulkDataTransactionLogs(sessionUserBean);
			return mapping.findForward("viewFinalStatusLeadsExcel");
	}

	
	//upload XL  method
	
	public ActionForward uploadExcel(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)	
	throws Exception {
	boolean isError=false;
	HttpSession session = request.getSession();
	ActionForward forward = new ActionForward();
	ActionMessages messages = new ActionMessages();
	if ( !isTokenValid(request) ) {
		  return init(mapping, form, request, response);
		}
	AssignedLeadsManager assignedLeadsManager = new AssignedLeadsManagerImpl();
	UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
	
	LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean) form;
	UserMstrService userMstrService = new UserMstrServiceImpl();
	sessionUserBean.setFileName("LeadFinalStatusReport.xls for upload");
	try
	{
	
	FormFile file = leadRegistrationFormBean.getNewFile();
	String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
	if(!arr.equalsIgnoreCase("xls") && !arr.equalsIgnoreCase("xlsx") )
	{
		messages.add("msg1",new ActionMessage("lms.bulk.assignment.excel.only"));
		sessionUserBean.setMessage("FAILED");
	}
	else
	{
	BulkUploadMsgDto msgDto = assignedLeadsManager.bulkUploadLeadFinalStatus((FormFile)file,request);
	
	switch(msgDto.getMsgId())
	{
	case Constants.SERVICE_MSG_SUCCESS :
		messages.add("msg",new ActionMessage("lms.bulk.lead.upload.success"));
		session.setAttribute("bulkLeadFinalErrLogFilePath",msgDto.getMessage());
		leadRegistrationFormBean.setIsError("true");
		sessionUserBean.setMessage("UPLOADED SUCCESSFULLY");
		break;
		
	case Constants.SERVICE_MSG_FAIL :
		messages.add("msg",new ActionMessage("lms.bulk.assignment.failed"));
		session.setAttribute("bulkLeadFinalErrLogFilePath",msgDto.getMessage());
		leadRegistrationFormBean.setIsError("true");
		sessionUserBean.setMessage("PARTIALLY DONE OR FAIL");
		break;
		
	case Constants.INVALID_EXCEL :
		messages.add("msg",new ActionMessage("lms.bulk.upload.invalid.excel"));
		leadRegistrationFormBean.setIsError("false");
		sessionUserBean.setMessage("FAILED");
		
		break;
		
	case Constants.SERVICE_MSG_ERROR :
		messages.add("msg",new ActionMessage("lms.bulk.upload.error"));
		leadRegistrationFormBean.setIsError("false");
		sessionUserBean.setMessage("FAILED");
		break;
		
	case Constants.BLANK_EXCEL :
		messages.add("msg",new ActionMessage("lms.bulk.upload.blank.excel"));
		leadRegistrationFormBean.setIsError("false");
		sessionUserBean.setMessage("FAILED");
		break;	
		
	case Constants.INVALID_FILESIZE :
		messages.add("msg",new ActionMessage("lms.bulk.upload.invalid.filesize"));
		leadRegistrationFormBean.setIsError("false");
		sessionUserBean.setMessage("FAILED");
		break;
		
	}
	}
	saveMessages(request, messages);
	
	}//try
	
	catch (Exception e) {
		e.printStackTrace();
		logger.info("Error occured in uploadExcel");	
		sessionUserBean.setMessage("FAILED");
		//userMstrService.insertBulkDataTransactionLogs(sessionUserBean);
  }
	userMstrService.insertBulkDataTransactionLogs(sessionUserBean);
	return mapping.findForward("leadSearchFinalUpdatePage");
	
}//uploadExcel

	//Error Logs method
public ActionForward openErrLog(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		java.util.Date date = new java.util.Date();
		String filePath="",fileNameNew="";

		try
		  {
			filePath=(String)session.getAttribute("bulkLeadFinalErrorLog");
			fileNameNew = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
			
			response.setContentType("text/csv");
			response.addHeader("Content-Disposition", "attachment; filename="+fileNameNew);	
			
			java.io.File uFile= new java.io.File(filePath);
			
			int fSize=(int)uFile.length();
			
			java.io.FileInputStream fis = new java.io.FileInputStream(uFile);
			java.io.PrintWriter pw = response.getWriter();
			int c=-1;
			// Loop to read and write bytes.
			while ((c = fis.read()) != -1){
				pw.print((char)c);
			}
			// Close output and input resources.
			fis.close();
			pw.flush();
			pw=null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
//Added BY Bhaskar
public ActionForward getRsuForCircleChange(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) throws LMSException
{
	
	int circleMstriD ;
	Document document = DocumentHelper.createDocument();
	Element root = document.addElement("options");
	MasterService masterService = new MasterServiceImpl();
	Element optionElement;
	try
	{
		if (request.getParameter("circleMstriD") != null)
		{
			circleMstriD = Integer.parseInt(request.getParameter("circleMstriD").toString());
			List rsuList = masterService.getRsuForCircleChange(circleMstriD);
			
			if (rsuList != null && rsuList.size() > 0)
			{
				for (int intCounter = 0; intCounter < rsuList.size(); intCounter++)
				{
					optionElement = root.addElement("option");
					optionElement.addAttribute("value", ((RSUDTO)rsuList.get(intCounter)).getRsuCode());
					optionElement.addAttribute("text", ((RSUDTO)rsuList.get(intCounter)).getRsuCode());
				}
			}
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
		}
	}
	catch (Exception applicationException)
	{
		applicationException.printStackTrace();
	}
	return null;
}

public ActionForward getRsuForCityChange(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) throws LMSException
{
	
	int circleMstriD ;
	String cityCode;
	Document document = DocumentHelper.createDocument();
	Element root = document.addElement("options");
	MasterService masterService = new MasterServiceImpl();
	Element optionElement;
	try
	{
		
		if (request.getParameter("circleMstriD") != null && request.getParameter("CityCode") != null)
		{
			
			circleMstriD = Integer.parseInt(request.getParameter("circleMstriD").toString());
			cityCode=request.getParameter("CityCode");
			List rsuList = masterService.getRsuForCityChange(circleMstriD,cityCode);
			
			if (rsuList != null && rsuList.size() > 0)
			{
				for (int intCounter = 0; intCounter < rsuList.size(); intCounter++)
				{
					optionElement = root.addElement("option");
					optionElement.addAttribute("value", ((RSUDTO)rsuList.get(intCounter)).getRsuCode());
					optionElement.addAttribute("text", ((RSUDTO)rsuList.get(intCounter)).getRsuCode());
				}
			}
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
		}
	}
	catch (Exception applicationException)
	{
		applicationException.printStackTrace();
	}
	return null;
}


public ActionForward getRsuForCityZoneCodeChange(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) throws LMSException
{
	
	int circleMstriD ;
	String cityCode;
	String cityzonecode;
	Document document = DocumentHelper.createDocument();
	Element root = document.addElement("options");
	MasterService masterService = new MasterServiceImpl();
	Element optionElement;
	try
	{
		
		if (request.getParameter("circleMstriD") != null && request.getParameter("CityCode") != null && request.getParameter("cityzonecode") != null)
		{
			
			circleMstriD = Integer.parseInt(request.getParameter("circleMstriD").toString());
			cityCode=request.getParameter("CityCode");
			cityzonecode=request.getParameter("cityzonecode");
			List rsuList = masterService.getRsuForCityZoneCodeChange(circleMstriD,cityCode,cityzonecode);
			
			if (rsuList != null && rsuList.size() > 0)
			{
				for (int intCounter = 0; intCounter < rsuList.size(); intCounter++)
				{
					optionElement = root.addElement("option");
					optionElement.addAttribute("value", ((RSUDTO)rsuList.get(intCounter)).getRsuCode());
					optionElement.addAttribute("text", ((RSUDTO)rsuList.get(intCounter)).getRsuCode());
				}
			}
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
		}
	}
	catch (Exception applicationException)
	{
		applicationException.printStackTrace();
	}
	return null;
}

//added by Nancy
public ActionForward editSearchLead(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
        
         {
		    
			String leadSearchTransaction;
		    String tid;
		    saveToken(request);
		    UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
			LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
			LeadRegistrationService leadRegistrationService = new LeadRegistrationServiceImpl();
			MasterService service=new MasterServiceImpl();
			ActionForward forward = new ActionForward(); 
			forward =  mapping.findForward("editLeadSearchPage");
			userBean.setMethodTempName("editSearchLead");
			
			
			if("".equals(leadRegistrationFormBean.getLeadId()) && "".equals(leadRegistrationFormBean.getContactNo()) && "".equals(leadRegistrationFormBean.getTid()))
			{
				leadRegistrationFormBean.reset(mapping, request);
				leadRegistrationFormBean.setInitStatus("true");
				
				if(Utility.isValidRequest(request)) {
		        	return mapping.findForward("error");
		        }
			}
			else
			{
				List<LeadDetailsDTO> LeadDetailsDTOList =null;
				Long leadId=0l;
				Long mobileNo=0l;
				leadRegistrationFormBean.setInitStatus("false");
					
			if(!"".equals(leadRegistrationFormBean.getLeadId()) && service.getProductId(Long.parseLong(leadRegistrationFormBean.getLeadId()))==Integer.parseInt(PropertyReader.getAppValue("lms.4G.productLobId")))
				{
					
					leadId = Long.parseLong(leadRegistrationFormBean.getLeadId());
					 LeadDetailsDTOList = leadRegistrationService.getLeadListByLeadId(leadId);
					leadSearchTransaction=leadRegistrationService.insertLeadSearchTransaction(leadRegistrationFormBean,userBean.getUserLoginId(),userBean.getIpaddress());
					if(LeadDetailsDTOList.size()!=0)
					{
						request.setAttribute("LEAD_LIST",LeadDetailsDTOList);
					}
					
				}
				if(!"".equals(leadRegistrationFormBean.getContactNo()) && (LeadDetailsDTOList==null || LeadDetailsDTOList.size()==0) &&  service.getProductIdByMobile(Long.parseLong(leadRegistrationFormBean.getContactNo()))==Integer.parseInt(PropertyReader.getAppValue("lms.4G.productLobId")))
				{
					mobileNo = Long.parseLong(leadRegistrationFormBean.getContactNo());
					request.setAttribute("LEAD_LIST",leadRegistrationService.getLeadListByMobileNo(mobileNo));
					
				}
				
				if(!"".equals(leadRegistrationFormBean.getTid()) && (LeadDetailsDTOList==null || LeadDetailsDTOList.size()==0) && service.getProductIdByTid(Long.parseLong(leadRegistrationFormBean.getTid()))==Integer.parseInt(PropertyReader.getAppValue("lms.4G.productLobId")))
				{
					tid = leadRegistrationFormBean.getTid();
					request.setAttribute("LEAD_LIST",leadRegistrationService.getLeadListByTid(tid));
				
				}
			

				request.setAttribute("Mobile_FLAG", service.getParameterName("Mobile_FLAG"));
    /*else
	{
			ActionErrors errors = new ActionErrors();
			errors.add("errors",new ActionError("lead.not.4G"));
			saveErrors(request,errors);	
    }*/
				
			}	
			return forward;
	}
public ActionForward editLeadDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
{
	
	LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
	MasterService masterService  = new MasterServiceImpl();
	try
	{
		Long leadId=0l;
		String productName = null;
		ArrayList<ProductDTO>  productList =null;
		List<CircleForProductDTO> circleList =null;
		List<RequestCategoryDTO> categoryList=null;
		
		logger.info("leadId"+leadRegistrationFormBean.getLeadId());
		String leadIds  =  request.getParameter("leadId");
		leadRegistrationFormBean.setLeadId(leadIds);
		if(!"".equals(leadRegistrationFormBean.getLeadId()))
		{
			LeadRegistrationService leadRegistrationService = new LeadRegistrationServiceImpl();
			UserMstrServiceImpl service= new UserMstrServiceImpl();
			leadId = Long.parseLong(leadRegistrationFormBean.getLeadId());
	     	int productLobId=masterService.getProductId(leadId);
	     	String lobId=String.valueOf(productLobId);
	     	int productId=masterService.getProduct(leadId);
	     	String product=String.valueOf(productId);
	     	leadRegistrationFormBean.setProductLobId(productLobId);
	     	leadRegistrationFormBean.setProductId(productId);
	     	circleList = masterService.getCircleForProduct(productLobId);
	     	categoryList= service.getAllChildrenRequestCategoryLob(product);
			
			leadRegistrationFormBean.setCircleForProductList((ArrayList<CircleForProductDTO>) circleList);// setting circle list
			leadRegistrationFormBean.setCategoryForStatusList((ArrayList<RequestCategoryDTO>) categoryList);  // setting request Category Listquest Category
			
			ArrayList<LeadDetailsDTO> lead_details =  leadRegistrationService.getLeadDetails(leadId);
			//ArrayList<RetrieveLeadDataDTO> leadList=leadRegistrationService.getHistoryDetails(leadId);
			
			if(lead_details !=null && lead_details.get(0)!=null && lead_details.get(0).getRequestType() >0) {
				  productList = masterService.getProductListByLob(lead_details.get(0).getRequestType(),0);
			}
			if(productList !=null && productList.size() >0 && productList.get(0) !=null )
				 productName = productList.get(0).getProductName();
			for(LeadDetailsDTO detailsDTO :lead_details){
				detailsDTO.setRequestTypeDesc(productName);
			}
			
			request.setAttribute("LEAD_DETAILS",lead_details);
			request.setAttribute("LEAD_TRNS_DETAILS",leadRegistrationService.getLeadTransactinDetails(leadId));
			if(productLobId ==Constants.lob4g)
			request.setAttribute("LEAD_HIST_DETAILS", leadRegistrationService.getHistoryDetails(leadId));
			//from here added by Nancy
			LeadDetailsDTO leadDetailsDTO = lead_details.get(0);
			leadRegistrationFormBean.setLeadId(""+leadDetailsDTO.getLeadId());
			String []productIds = new String[1];
			productIds[0] = leadDetailsDTO.getProductId()+"";
     		leadRegistrationFormBean.setProductId(leadDetailsDTO.getProductId());
			leadRegistrationFormBean.setProductName(leadDetailsDTO.getProductName());
			leadRegistrationFormBean.setProductIds(productIds);
			leadRegistrationFormBean.setAddress1(leadDetailsDTO.getAddress1());
			leadRegistrationFormBean.setAddress2(leadDetailsDTO.getAddress2());
			leadRegistrationFormBean.setCircleId(leadDetailsDTO.getCircleId()+"#"+masterService.getProductLobId(leadDetailsDTO.getProductId())+"#"+leadDetailsDTO.getCircleMasterId());
			logger.info("circle Id "+leadDetailsDTO.getCircleId()+"#"+masterService.getProductLobId(leadDetailsDTO.getProductId())+"#"+leadDetailsDTO.getCircleMasterId());
			if(leadDetailsDTO.getCircleId() !=0)
			{
			leadRegistrationFormBean.setCityList(masterService.getCityForCircleForDialer(leadDetailsDTO.getCircleMasterId()));	
			leadRegistrationFormBean.setCityCode(masterService.getZoneCodeforCity(leadDetailsDTO.getCityCode())+"#"+leadDetailsDTO.getCityCode());
			}
			if(!"".equalsIgnoreCase(leadDetailsDTO.getCityCode()))
			{
			 leadRegistrationFormBean.setCityZoneList(masterService.getCityZoneOnCityChange(leadDetailsDTO.getCityCode()));	
			}
			
			
			leadRegistrationFormBean.setRequestCategoryId(leadDetailsDTO.getRequestCategoryId());
			
			leadRegistrationFormBean.setPinCode(leadDetailsDTO.getPinCode());
			leadRegistrationFormBean.setCityZoneCode(leadDetailsDTO.getCityZoneCode());
			leadRegistrationFormBean.setAppointmentDate(leadDetailsDTO.getAppointmentDate());
			leadRegistrationFormBean.setAppointmentHour(leadDetailsDTO.getAppointmentHour());
			leadRegistrationFormBean.setAppointmentMinute(leadDetailsDTO.getAppointmentMinute());
			leadRegistrationFormBean.setAppointmentEndDate(leadDetailsDTO.getAppointmentDate());
			leadRegistrationFormBean.setAppointmentEndHour(leadDetailsDTO.getAppointmentHour());
			leadRegistrationFormBean.setAppointmentEndMinute(leadDetailsDTO.getAppointmentMinute());
			leadRegistrationFormBean.setAppointmentTime(leadDetailsDTO.getAppointmentTime());
			leadRegistrationFormBean.setAppointmentEndTime(leadDetailsDTO.getAppointmentEndTime());
			leadRegistrationFormBean.setAppointment_Status(leadDetailsDTO.getAppointment_Status());
			leadRegistrationFormBean.setPlan(leadDetailsDTO.getPlan());
			leadRegistrationFormBean.setPayment(leadDetailsDTO.getPayment());
			leadRegistrationFormBean.setTransactionRefNo(leadDetailsDTO.getTransactionRefNo());
			leadRegistrationFormBean.setProductLobId(leadDetailsDTO.getProductLobId());
			leadRegistrationFormBean.setFeasibilityParam(leadDetailsDTO.getFeasibilityParam());
			leadRegistrationFormBean.setDevicetaken(leadDetailsDTO.getDevicetaken());
			
			leadRegistrationFormBean.setRequestCategory(leadDetailsDTO.getRequestCategory());
			
			
			leadRegistrationFormBean.setRequestCategoryName(leadDetailsDTO.getRequestCategoryName());
			leadRegistrationFormBean.setCustomerName(leadDetailsDTO.getCustomerName());
			leadRegistrationFormBean.setContactNo(String.valueOf(leadDetailsDTO.getContactNo()));
			leadRegistrationFormBean.setEmail(leadDetailsDTO.getEmail());
			leadRegistrationFormBean.setAlternateContactNo(leadDetailsDTO.getAlternateContactNo());
			leadRegistrationFormBean.setOffer(leadDetailsDTO.getOffer());
			leadRegistrationFormBean.setCompany(leadDetailsDTO.getCompany());
			leadRegistrationFormBean.setDeviceMrp(leadDetailsDTO.getDeviceMrp());
			leadRegistrationFormBean.setSourceName(leadDetailsDTO.getSourceName());
			leadRegistrationFormBean.setSubSourceName(leadDetailsDTO.getSubSourceName());
			leadRegistrationFormBean.setCampaign(leadDetailsDTO.getCampaign());
			leadRegistrationFormBean.setExtraParam6(leadDetailsDTO.getExtraParam6());
			//leadRegistrationFormBean.setExtraParam7(leadDetailsDTO.getExtraParam7());
	
			
			String prodata  =ServerPropertyReader.getString("4G.leaddata.Product");
			if(prodata.contains(String.valueOf(leadDetailsDTO.getProductId()))) {
				leadRegistrationFormBean.setAppointmentStartDate(DateHelper.getDateOnly(DateHelper.APPOINMENT_DATE_FORMAT, 0));
				leadRegistrationFormBean.setAppointmentStartHour("00");
				leadRegistrationFormBean.setAppointmentStartMinute("10");
				
				leadRegistrationFormBean.setAppointmentEndDate(DateHelper.getDateOnly(DateHelper.APPOINMENT_DATE_FORMAT, 6));
				leadRegistrationFormBean.setAppointmentEndHour("00");
				leadRegistrationFormBean.setAppointmentEndMinute("10");
				leadRegistrationFormBean.setProductflag("true");
			}else {
				leadRegistrationFormBean.setProductflag("false");	
			}
			
		}
	}
	catch (Exception e) {
	    e.printStackTrace();
		logger.error("Exception occured while getting lead Details :" + e.getMessage());
		ActionErrors errors = new ActionErrors();
		errors.add("errors",new ActionError("lead.not.found"));
		saveErrors(request,errors);
	}

	request.setAttribute("Mobile_FLAG", masterService.getParameterName("Mobile_FLAG"));
	leadRegistrationFormBean.setInitStatus("false");
	return mapping.findForward("editLead"); 
}


public ActionForward updateRecord(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

	logger.info("insert method");
	ActionMessages messages = new ActionMessages();
	ActionForward forward = new ActionForward();
	String remotAddress=request.getRemoteAddr();
	ActionErrors errors=new ActionErrors();
	
	try{
	 
	MasterService mstrService = new MasterServiceImpl();
	String result="";
	logger.info("Request comming from :"+remotAddress);
	UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
	logger.info(userBean.getUserLoginId() + " entered insert method for Edit Lead Search page.");
	
    LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
    leadRegistrationFormBean.setCreatedBy(userBean.getUserLoginId());
    LeadRegistrationService service = new LeadRegistrationServiceImpl();
    ArrayList<LeadDetailsDTO> lead_details =  service.getLeadDetails(Long.parseLong(leadRegistrationFormBean.getLeadId()));
   
     String masterId=leadRegistrationFormBean.getCircleId().split("#")[2];
    String circleId=leadRegistrationFormBean.getCircleId().split("#")[0];
    leadRegistrationFormBean.setCircleId(masterId);
     leadRegistrationFormBean.setCircle(circleId);
    
     
    /*  if(leadRegistrationFormBean.getRequestCategoryId()==null || leadRegistrationFormBean.getRequestCategoryId().length() <=0)
    {
    	leadRegistrationFormBean.setRequestCategoryId(lead_details.get(0).getRequestCategoryId());
    	leadRegistrationFormBean.setRequestCategoryName(lead_details.get(0).getRequestCategoryName());
    }
    if(leadRegistrationFormBean.getFeasibilityParam()==null || "".equals(leadRegistrationFormBean.getFeasibilityParam()))
    {
    	leadRegistrationFormBean.setFeasibilityParam(lead_details.get(0).getFeasibilityParam());
    	leadRegistrationFormBean.setFeasibilityParam(lead_details.get(0).getFeasibilityParam());
    }*/
	
    
	result =service.update4GRecord(leadRegistrationFormBean);
	String[] ids = result.split("#");
	String updateStatus= ids[0];			
	String newId = ids[1];
	if ("SUCCESS".equals(updateStatus))
	{
		messages.add("msg1",new ActionMessage("lead.updated"));
		saveMessages(request, messages);	
		// reset mandatory 
		//leadRegistrationFormBean.reset(mapping, request);
		leadRegistrationFormBean.setLeadId(leadRegistrationFormBean.getLeadId());
		
		return viewLeadDetails( mapping, leadRegistrationFormBean, request, response);
	}else
	{
		request.setAttribute("updateStatus", result);
		forward = mapping.findForward("editLead");
		
		
	}
} catch (Exception e) 
{
    e.printStackTrace();
	logger.error("Exception occured while updating lead details  :" + e.getMessage());
	errors.add("errors",new ActionError("lead.not.updated"));
	saveErrors(request,errors);
}

return forward;
}


//Added By Bhaskar


public ActionForward getProductonLobChange(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) throws LMSException
{
	
	
	Document document = DocumentHelper.createDocument();
	Element root = document.addElement("options");
	MasterService masterService = new MasterServiceImpl();
	Element optionElement;
	int productLobId;
	try
	{
		
		if (request.getParameter("selectedProductLobId") != null && request.getParameter("selectedProductLobId").length() >0)
		{
			
			productLobId = Integer.parseInt(request.getParameter("selectedProductLobId").toString());
			
			/*String productflag    =request.getParameter("productflag");
			String  fourG = PropertyReader.getAppValue("lms.4G.productLobId");
			String prodata  =ServerPropertyReader.getString("4G.leaddata.Product");*/
			
			List productList = masterService.getproductForLob(productLobId);
			
			if (productList != null && productList.size() > 0)
			{
				for (int intCounter = 0; intCounter < productList.size(); intCounter++)
				{
					String productId = String.valueOf(((ProductDTO)productList.get(intCounter)).getProductId());
					optionElement = root.addElement("option");
					optionElement.addAttribute("value", productId+"#"+((ProductDTO)productList.get(intCounter)).getProductName());
					optionElement.addAttribute("text", ((ProductDTO)productList.get(intCounter)).getProductName());
					
				}
			}
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
		}
	}
	catch (Exception applicationException)
	{
		applicationException.printStackTrace();
	}
	return null;
}


	
		
public ActionForward getAppointment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
{
	String taskType="";
	int productLobId=-1;
	String pinCode="";
	String appointmentStart;
	String appointmentEnd;
	Document document = DocumentHelper.createDocument();
	Element root = document.addElement("options");
	
	Element optionElement;
	
		
	
	
	try{
		
		pinCode=	request.getParameter("pinCode");
		appointmentStart= request.getParameter("appointmentStart");
		appointmentEnd= request.getParameter("appointmentEnd");
		
		PrintWriter out=response.getWriter();
		
			JSONObject GetAppointmentsResponse   = InterfaceHelper.startProcess(pinCode,appointmentStart,appointmentEnd);
			JSONArray arr = GetAppointmentsResponse.getJSONArray("AppointmentSlots");
	 		
	 		for(int i=0; i<arr.length(); i++)
	 		{   
	 			  JSONObject o = arr.getJSONObject(i).getJSONObject("TimePeriod");  
	 			  	optionElement = root.addElement("option");
					optionElement.addAttribute("value", o.getString("startDateTime")+" TO "+o.getString("endDateTime"));
					optionElement.addAttribute("text", o.getString("startDateTime")+" TO "+o.getString("endDateTime"));
	 		}
	 		
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "No-Cache");
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.flush();
		out.flush();
		  	
	     }
	     	catch (Exception e)
	     	{
			    e.printStackTrace();
				logger.error("Exception occured while getting appointment Details :" + e.getMessage());
				
			}
			
			return null;	
			
		}
			




public ActionForward initialize(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
	        saveToken(request);
			UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
			LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
			logger.info(userBean.getUserLoginId() + " entered init method for Lead Registration page.");
			leadRegistrationFormBean.reset(mapping,request);
			

			MasterService masterService = new MasterServiceImpl();
			List<ProductLobDTO> list  = masterService.getProductLobList();
			
			List<ProductLobDTO> listMains   = new ArrayList<ProductLobDTO>();
			ProductLobDTO lobDTO =null;
			for(int i=0; i<list.size(); i++) {
				 lobDTO  = list.get(i);
				if(lobDTO.getProductLobID()==Integer.parseInt(PropertyReader.getAppValue("lms.4G.productLobId"))) {
					listMains.add(lobDTO); 
				}
			}
			leadRegistrationFormBean.setFourGflag("true");
			String prodata  =ServerPropertyReader.getString("4G.leaddata.Product");
			//logger.info("*************************************************************************************"+prodata);
			request.setAttribute("products", prodata);
			request.setAttribute("productLobList", listMains);
			leadRegistrationFormBean.setCircleList(masterService.getCircleList());
			//leadRegistrationFormBean.setPinCodeList(masterService.getPinCodeListFor4G());
			
			leadRegistrationFormBean.setAppointmentStartDate(DateHelper.getDateOnly(DateHelper.APPOINMENT_DATE_FORMAT, 0));
			leadRegistrationFormBean.setAppointmentStartHour("00");
			leadRegistrationFormBean.setAppointmentStartMinute("10");
			
			leadRegistrationFormBean.setAppointmentEndDate(DateHelper.getDateOnly(DateHelper.APPOINMENT_DATE_FORMAT, 6));
			leadRegistrationFormBean.setAppointmentEndHour("00");
			leadRegistrationFormBean.setAppointmentEndMinute("10");
			
			logger.info(userBean.getUserLoginId() + " exited init method for Lead Registration page.");
			
			
			return mapping.findForward("initializeeditLead");
			
	}

public ActionForward getPostalCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
{
	logger.info("inside getPostalCode ???????????????????????????");
	int productLobId =0;
	int productId ;
	Document document = DocumentHelper.createDocument();
	Element root = document.addElement("options");
	MasterService masterService = new MasterServiceImpl();
	Element optionElement;
	try
	{
			List pinCodeList = masterService.getPinCodeListFor4G();
			if (pinCodeList != null && pinCodeList.size() > 0)
			{
				for (int intCounter = 0; intCounter < pinCodeList.size(); intCounter++)
				{
					optionElement = root.addElement("option");
					optionElement.addAttribute("value", ((PINCodeDTO)pinCodeList.get(intCounter)).getPinCode());
					optionElement.addAttribute("text", ((PINCodeDTO)pinCodeList.get(intCounter)).getPinCode());
				}
			}
			if( pinCodeList.size()== 0)
			{
				optionElement = root.addElement("option");
				optionElement.addAttribute("value","'-1'");
				optionElement.addAttribute("text", "Select PinCode");
			}
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
		
	}
	
	catch (Exception applicationException)
	{
		applicationException.printStackTrace();
	}
	return null;
}
public ActionForward getLeadDetailsByContact(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response){
	
	Long leadId=Long.parseLong(request.getParameter("leadId").toString());
	
	LeadRegistrationService leadRegistrationService= new LeadRegistrationServiceImpl();
	List<LeadDetailsDTO> LeadDetailsPopUpDetails=null;
	try {
		LeadDetailsPopUpDetails = leadRegistrationService.getLeadListByLeadContact(leadId);
	} catch (LMSException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	request.setAttribute("LeadDetailsPopUpDetails",LeadDetailsPopUpDetails);
	return mapping.findForward("leadDetailsbyContactPopup");
}

//Added by satish
public ActionForward SearchLeadCreationwithTxnId(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response){
	
	LeadRegistrationFormBean leadRegistrationFormBean = (LeadRegistrationFormBean)form;
	LeadRegistrationService leadRegistrationService = new LeadRegistrationServiceImpl();
	ActionForward forward = new ActionForward(); 
	forward =  mapping.findForward("leadCreateDetailscheckbyTxnID");
	if("".equals(leadRegistrationFormBean.getTid()))
	{
		leadRegistrationFormBean.reset(mapping, request);
		if(Utility.isValidRequest(request)) {
        	return mapping.findForward("error");
        }
	}else{
		try {
			request.setAttribute("LeadList", leadRegistrationService.getLeadListofcheckLeadcreationByTid(leadRegistrationFormBean));
		} catch (LMSException e) {
			e.printStackTrace();
		}
	}
	return forward;
}
//Added by satish

}

