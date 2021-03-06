package com.ibm.lms.actions;

/**
 * @author Parnika Sharma
 */


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONObject;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.lms.dao.ProductMappingDao;
import com.ibm.lms.dao.UserMstrDao;
import com.ibm.lms.dao.impl.ProductMappingDaoImpl;
import com.ibm.lms.dao.impl.UserMstrDaoImpl;
import com.ibm.lms.dto.AgencyDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;

import com.ibm.lms.services.AgencyMappingService;
import com.ibm.lms.services.BulkAssigmentService;
import com.ibm.lms.services.LeadRegistrationService;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.ProductMappingService;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.AgencyMappingServiceImpl;
import com.ibm.lms.services.impl.BulkAssigmentServiceImpl;
import com.ibm.lms.services.impl.LeadRegistrationServiceImpl;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.services.impl.ProductMappingServiceImpl;
import com.ibm.lms.services.impl.UserMstrServiceImpl;


public class AjaxSupportAction extends DispatchAction {

	private static final Logger logger = Logger.getLogger(AjaxSupportAction.class);
		
		
	public ActionForward getProductBasedOnLob(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ProductMappingDao productDao = ProductMappingDaoImpl.productMappingDaoInstance();

		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		HttpSession session = request.getSession();
		ProductMappingService productService = new ProductMappingServiceImpl();
		String productLobId = request.getParameter("selectedProductLobId");
        
		try {
			
			JSONObject json = productService.getElementsAsJson(productLobId);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Content-Type", "application/x-json");
			response.getWriter().print(json);		
		
		} catch (Exception e) {
			logger.error("Exception in Get Product Based on Product LOB " + e.getMessage());
		}
		return null;
	}
	
	
	public ActionForward getNewProductLobAdded(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ProductMappingDao productDao = ProductMappingDaoImpl.productMappingDaoInstance();;

		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		HttpSession session = request.getSession();
		ProductMappingService productService = new ProductMappingServiceImpl();
		String newProductLobName = request.getParameter("newProductLobName");
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
        
		try {
			
			JSONObject json = productService.getElementsAsJsonNewProductLob(newProductLobName, userBean.getUserLoginId());
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Content-Type", "application/x-json");
			response.getWriter().print(json);		
		
		} catch (Exception e) {
			logger.error("Exception in getNewProductLobAdded() " + e.getMessage());
		}
		return null;
	}

	public ActionForward getNewProductNameAdded(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			ProductMappingDao productDao = ProductMappingDaoImpl.productMappingDaoInstance();;

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			ProductMappingService productService = new ProductMappingServiceImpl();
			String newProductName = request.getParameter("newProductName");
			String productLobId = request.getParameter("productLobId");
			UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
			try {
				
				JSONObject json = productService.getElementsAsJsonNewProductName(newProductName, Integer.parseInt(productLobId), userBean.getUserLoginId());
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in getNewProductNameAdded() " + e.getMessage());
			}
			return null;
		}
	
	public ActionForward getProductSynonymList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			ProductMappingDao productDao = ProductMappingDaoImpl.productMappingDaoInstance();;

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			ProductMappingService productService = new ProductMappingServiceImpl();
			String selectedProductId = request.getParameter("selectedProductId");
	        
			try {
				
				JSONObject json = productService.getElementsAsJsonSynonymList(selectedProductId);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in getProductSynonymList() " + e.getMessage());
			}
			return null;
		}

	public ActionForward getCirclesMappedWithAgency(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			AgencyMappingService agencyService = new AgencyMappingServiceImpl();
			String selectedAgencyId = request.getParameter("selectedAgencyId");
	        
			try {
				
				JSONObject json = agencyService.getElementsAsJsonCircleMapped(selectedAgencyId);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in getCirclesMappedWithAgency() " + e.getMessage());
			}
			return null;
		}
	
	public ActionForward getCirclesNotMappedWithAgency(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {


			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			AgencyMappingService agencyService = new AgencyMappingServiceImpl();
	        
			try {
				
				JSONObject json = agencyService.getElementsAsJsonCircleNotMapped();
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in getCirclesNotMappedWithAgency() " + e.getMessage());
			}
			return null;
		}

	public ActionForward getAgencyDescriptionDetails(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			AgencyMappingService agencyService = new AgencyMappingServiceImpl();
			String selectedAgencyId = request.getParameter("selectedAgencyId");
			String message = "";
			AgencyDTO dto = null;
	        
			try {				
				dto = agencyService.getAgencyDetails(selectedAgencyId);
				// Decrypting the password
				IEncryption t = new Encryption();
				String decPassword = t.decrypt(dto.getPassword());

				JSONObject json=new JSONObject();
				json.put("desc", dto.getAgencyDescription());
				json.put("path", dto.getAgencyPath());
				json.put("classname", dto.getAgencyClass());
				json.put("username", dto.getUsername());
				json.put("password", decPassword);
				json.put("isDefault", dto.getDefaultCheck());
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);	
			
			} catch (Exception e) {
				logger.error("Exception in getCirclesNotMappedWithAgency() " + e.getMessage());
			}
			return null;
		}
	
	public ActionForward addCircleMapping(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			AgencyMappingService agencyService = new AgencyMappingServiceImpl();
			String selectedAgencyId = request.getParameter("selectedAgencyId");	
			String[] circleList= request.getParameterValues("mappedCircleId");     
			try {
				
				JSONObject json = agencyService.getElementsAsJsonAddCircle(Integer.parseInt(selectedAgencyId),circleList);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in addCircleMapping() " + e.getMessage());
			}
			return null;
		}
	
	public ActionForward removeCircleMapping(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {


			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			AgencyMappingService agencyService = new AgencyMappingServiceImpl();
			String selectedAgencyId = request.getParameter("selectedAgencyId");	
			String[] circleList= request.getParameterValues("circleId");
			try {
				
				JSONObject json = agencyService.getElementsAsJsonRemoveCircle(Integer.parseInt(selectedAgencyId),circleList);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in removeCircleMapping() " + e.getMessage());
			}
			return null;
		}
	
	/* Added by Parnika for LMS Phase 2  */
	
	
	public ActionForward getCircleBasedOnLob(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		HttpSession session = request.getSession();
		UserMstrService UserMstrService = new UserMstrServiceImpl();
		String productLobId = request.getParameter("selectedLobId");
        
		try {
			
			JSONObject json = UserMstrService.getElementsAsJson(productLobId);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Content-Type", "application/x-json");
			response.getWriter().print(json);		
		
		} catch (Exception e) {
			logger.error("Exception in Get Circle List Based on Product LOB in getCircleBasedOnLob() " + e.getMessage());
		}
		return null;
	}
	//New One
	
	
	public ActionForward getCircleBasedOnLobForCo(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

		logger.info("ajax calling method");
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			UserMstrService UserMstrService = new UserMstrServiceImpl();
			String productLobId = request.getParameter("selectedLobId");
			logger.info("product Lob Id;;;;;;;;;;;;;"+productLobId);
			String userLoginId=request.getParameter("loginId");
			logger.info("Login Id.................."+userLoginId);
	        
			try {
				
				JSONObject json = UserMstrService.getElementsAsJsonNew(productLobId,userLoginId);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in Get Circle List Based on Product LOB in getCircleBasedOnLobForCo() " + e.getMessage());
			}
			return null;
		}
	
	public ActionForward getChPartnerBasedOnLob(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

		logger.info("ajax calling method");
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			UserMstrService UserMstrService = new UserMstrServiceImpl();
			String productLobId = request.getParameter("selectedLobId");
			logger.info("product Lob Id;;;;;;;;;;;;;"+productLobId);
			String circleMstrId = request.getParameter("circleMstrId");
			logger.info("circleMstrId Id.................."+circleMstrId);
			String selectedTypeId = request.getParameter("selectedTypeId");
	        
			try {				
				JSONObject json = UserMstrService.getElementsAsJsonMTD(productLobId, circleMstrId , selectedTypeId);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
				logger.info("returning from ajax");
			} catch (Exception e) {
				logger.error("Exception in Get Circle List Based on Product LOB in getCircleBasedOnLobForCo() " + e.getMessage());
			}
			return null;
		}
	
	public ActionForward getZoneBasedOnCircle(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			UserMstrService UserMstrService = new UserMstrServiceImpl();
			String selectedTypeId = request.getParameter("selectedTypeId");
			String circleMstrId = request.getParameter("circleMstrId");
	        
			try {				
				JSONObject json = UserMstrService.getElementsAsJsonZone(selectedTypeId, circleMstrId);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Throwable e) {
				e.printStackTrace();
				logger.error("Exception in Get Zone Based on Circle in getZoneBasedOnCircle()" + e.getMessage());
			}
			return null;
		}
	
	public ActionForward getZoneBasedOnCircleFromZoneMaster(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			BulkAssigmentService bulkAssignService = new BulkAssigmentServiceImpl();
			String circleMstrId = request.getParameter("circleMstrId");        
			try {				
				JSONObject json = bulkAssignService.getElementsAsJsonZoneFromZoneMaster(circleMstrId);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in Get Zone Based on Circle in getZoneBasedOnCircleFromZoneMaster()" + e.getMessage());
			}
			return null;
		}
	
	public ActionForward getCityBasedOnZone(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			BulkAssigmentService bulkAssignService = new BulkAssigmentServiceImpl();
			String zoneCode = request.getParameter("zoneCode");        
			try {				
				JSONObject json = bulkAssignService.getElementsAsJsonCity(zoneCode);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in Get City Based on Circle in getCityBasedOnZone()" + e.getMessage());
			}
			return null;
		}
	
	public ActionForward getCityZoneBasedOnCity(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			BulkAssigmentService bulkAssignService = new BulkAssigmentServiceImpl();
			String cityCode = request.getParameter("cityCode");        
			try {				
				JSONObject json = bulkAssignService.getElementsAsJsonCityZone(cityCode);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in Get City Zone Based on Circle in getCityZoneBasedOnCity()" + e.getMessage());
			}
			return null;
		}
	
	public ActionForward getPincodeBasedOnCityZone(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			BulkAssigmentService bulkAssignService = new BulkAssigmentServiceImpl();
			String cityZoneCode = request.getParameter("cityZoneCode");        
			try {				
				JSONObject json = bulkAssignService.getElementsAsJsonPincode(cityZoneCode);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in Get Pincode Based on City zone in getPincodeBasedOnCityZone()" + e.getMessage());
			}
			return null;
		}
	public ActionForward getRsuBasedOnCityZone(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			BulkAssigmentService bulkAssignService = new BulkAssigmentServiceImpl();
			String cityZoneCode = request.getParameter("cityZoneCode");        
			try {				
				JSONObject json = bulkAssignService.getElementsAsJsonRsu(cityZoneCode);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in Get RSU Based on City Zone in getRsuBasedOnCityZone()" + e.getMessage());
			}
			return null;
		}

	
	
	
	/* End of changes by Parnika */
	
// adding by pratap for auto populate all on behalf of pin code enteredd
	public ActionForward getDataForPinCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		logger.info("in getDataForPinCode method of ajaxsupportaction action :::::::::::");
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
				}else if(request.getParameter("productLobId") !=null && request.getParameter("productLobId").length() >0) {
					productlobId = Integer.parseInt(request.getParameter("productLobId").toString());
				}
				
				
				details = productService.getDataForPinCode(pinCode,circleMstrId,productlobId);
				logger.info("details  : in action  :"+details);
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
	// end of adding by pratap
	
	// adding by pratap for auto populate all on zone, city , cityzone for circle selected
	public ActionForward populateZoneCityCityZoneBasedOnCircle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		logger.info("in populateZoneCityCityZoneBasedOnCircle method of ajaxsupportaction action :::::::::::");
		String pinCode ;
		int circleMstrId;
		ProductMappingService productService = new ProductMappingServiceImpl();
		String details="";
		try
		{
			if (request.getParameter("circleMstrId") != null)
			{
				circleMstrId = Integer.parseInt(request.getParameter("circleMstrId").toString());
				details = productService.populateZoneCityCityZoneBasedOnCircle(circleMstrId);
				logger.info("details  : in action  :"+details);
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
	// end of adding by pratap
	
	// adding by amarjeet for auto populate all LOB on behalf of entered Olm Id
	public ActionForward getLobForUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		logger.info("in getLobForUser method of ajaxsupportaction action :::::::::::");
		String olmId =null ;
		int lobId;
		MasterService  userService = new MasterServiceImpl();
		//String details="";
		
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		HttpSession session = request.getSession();
		
		if (request.getParameter("olmId") != null)
		{
			olmId = request.getParameter("olmId").toString(); 
		}        
		try {				
			JSONObject json = userService.getLobElementsAsJson(olmId);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Content-Type", "application/x-json");
			response.getWriter().print(json);	
			
									}
		catch (Exception applicationException)
		{
			applicationException.printStackTrace();
		}
		return null;
	}
	

	
	
	
	public ActionForward getCircleBasedOnLobForCoUser(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

		logger.info("ajax calling method");
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			UserMstrService UserMstrService = new UserMstrServiceImpl();
			String productLobId = request.getParameter("selectedLobId");
			logger.info("product Lob Id;;;;;;;;;;;;;"+productLobId);
			String userLoginId=request.getParameter("loginId");
			logger.info("Login Id.................."+userLoginId);
	        
			try {
				
				JSONObject json = UserMstrService.getElementsAsJsonNewUser(productLobId,userLoginId);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in Get Circle List Based on Product LOB in getCircleBasedOnLobForCo() " + e.getMessage());
			}
			return null;
		}
	
	
	
	public ActionForward getZoneBasedOnCircleFromZoneMasterNew(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
logger.info("*****************getZoneBasedOnCircleFromZoneMasterNew******************pop");
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			BulkAssigmentService bulkAssignService = new BulkAssigmentServiceImpl();
			String circleMstrId = request.getParameter("circleMstrId");        
			try {				
				JSONObject json = bulkAssignService.getElementsAsJsonZoneFromZoneMasterNew(circleMstrId);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in Get Zone Based on Circle in getZoneBasedOnCircleFromZoneMaster()" + e.getMessage());
			}
			return null;
		}
	
	public ActionForward getCityBasedOnZoneNew(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			BulkAssigmentService bulkAssignService = new BulkAssigmentServiceImpl();
			String circleMstrId = request.getParameter("circleMstrId");        
			try {				
				JSONObject json = bulkAssignService.getElementsAsJsonCityNew(circleMstrId);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in Get City Based on Circle in getCityBasedOnZone()" + e.getMessage());
			}
			return null;
		}
	
	public ActionForward getCityZoneBasedOnCityNew(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
logger.info("************** getCityZoneBasedOnCityNew ************pop");
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			BulkAssigmentService bulkAssignService = new BulkAssigmentServiceImpl();
			String circleMstrId = request.getParameter("circleMstrId");        
			try {				
				JSONObject json = bulkAssignService.getElementsAsJsonCityZoneNew(circleMstrId);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in Get City Zone Based on Circle in getCityZoneBasedOnCity()" + e.getMessage());
			}
			return null;
		}
	
	
	public ActionForward getCityZoneBasedOnZone(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			BulkAssigmentService bulkAssignService = new BulkAssigmentServiceImpl();
			String zoneCode = request.getParameter("zoneCode");        
			try {				
				JSONObject json = bulkAssignService.getElementsAsJsonCityZone1(zoneCode);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in Get City Based on Circle in getCityBasedOnZone()" + e.getMessage());
			}
			return null;
		}
	
	

	
	public ActionForward getLeadSubSubStatus1(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			LeadRegistrationService leadRegistrationService = new LeadRegistrationServiceImpl();
			MasterService service= new MasterServiceImpl();
			String leadStatusId = request.getParameter("leadStatusId");
			long leadId=Long.parseLong(request.getParameter("leadId"));
			int prodLobId=service.getProductId(leadId);
			
		
			
			try {				
				JSONObject json = leadRegistrationService.getElementsAsJsonLeadSubStatus(leadStatusId,prodLobId);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in Get leadStatusId  " + e.getMessage());
			}
			return null;
		}
	//Added By Bhaskar
	
	
	
	public ActionForward getRequestCategoryBasedOnProduct(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			logger.info("ajax calling method");
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			UserMstrService UserMstrService = new UserMstrServiceImpl();
			String productId = request.getParameter("selectedProductId");
			logger.info("product Lob Id;;;;;;;;;;;;;"+productId);
			String productLobId = request.getParameter("productLobID");
			logger.info("product Lob Id;;;;;;;;;;;;;"+productLobId);
			
			
			try {
				
				JSONObject json = UserMstrService.getElementsAsJsonRequest(productId,productLobId);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in Get request List Based on Product  in loadRequestCategoryDropdown() " + e.getMessage());
			}
			return null;
		}
	public ActionForward getRequestCategoryBasedOnProductLob(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			logger.info("ajax calling method");
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			UserMstrService UserMstrService = new UserMstrServiceImpl();
			String productLobId = request.getParameter("productLobID");
			logger.info("product Lob Id;;;;;;;;;;;;;"+productLobId);
			
			try {
				
				JSONObject json = UserMstrService.getElementsAsJsonRequestLob(productLobId);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in Get request List Based on Product  in loadRequestCategoryDropdown() " + e.getMessage());
			}
			return null;
		}
	
	public ActionForward getRsuForCircleChange(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			logger.info("ajax calling method");
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			HttpSession session = request.getSession();
			UserMstrService UserMstrService = new UserMstrServiceImpl();
			String circleMstriD = request.getParameter("circleMstriD");
			logger.info("circleMstriD;;;;;;;;;;;;;"+circleMstriD);
			
			try {
				
				JSONObject json = UserMstrService.getRsuForCircleChange(circleMstriD);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			
			} catch (Exception e) {
				logger.error("Exception in Get request List Based on Product  in loadRequestCategoryDropdown() " + e.getMessage());
			}
			return null;
		}
	
	}
