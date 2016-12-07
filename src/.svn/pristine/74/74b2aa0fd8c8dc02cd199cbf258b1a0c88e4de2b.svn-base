/**
 * 
 */
package com.ibm.lms.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.lms.common.Constants;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.ActorDto;
import com.ibm.lms.dto.ColumnDto;
import com.ibm.lms.dto.FidDto;
import com.ibm.lms.dto.ProductDTO;
import com.ibm.lms.dto.ReportsDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.forms.ReportConfigurationBean;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.impl.MasterServiceImpl;

/**
 * @author Nehil Parashar
 *
 */
public class ReportConfigurationAction extends DispatchAction
{
	private static final String SUCCESS = "success";

	private static final String REPORT_TYPE_FID = "fidwise";
	private static final String REPORT_TYPE_PRODUCT = "productwise";
	
	/**
	 * log4j logger
	 */
	private static Logger logger = Logger.getLogger(ReportConfigurationAction.class);
	
	
	
	/**
	 * Initializes the variables used as request attributes
	 * true means visible in UI
	 * false means invisible in UI
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("Inside init method");
		ActionForward forward = null;
		saveToken(request);
		try
		{
			// set true/false accordingly to show/hide the UI controls on page 
			request.setAttribute(Constants.RC_SHOW_ACTION, true);
			request.setAttribute(Constants.RC_SHOW_REPORT, false);
			request.setAttribute(Constants.RC_EDIT_REPORT_NAME, false);
			request.setAttribute(Constants.RC_SHOW_REPORT_TYPES, false);
			request.setAttribute(Constants.RC_SHOW_ROLES,false);
			request.setAttribute(Constants.RC_SHOW_FREQUENCY,false);
			request.setAttribute(Constants.RC_SHOW_TIMINGS,false);
			request.setAttribute(Constants.RC_SHOW_EMAILS,false);
			request.setAttribute(Constants.RC_SHOW_LOBS,false);
			request.setAttribute(Constants.RC_SHOW_PRODUCTS,false);
			request.setAttribute(Constants.RC_SHOW_FIDS,false);
			request.setAttribute(Constants.RC_SHOW_COLUMNS,false);
			
			ReportConfigurationBean formBean = (ReportConfigurationBean)form;
			formBean.setMessage("");
			if(Utility.isValidRequest(request)) {
	        	return mapping.findForward("error");
	        }
			
			// forward to success page
			forward = mapping.findForward(SUCCESS);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		logger.info("Exiting init method");
		return forward;
	}	
	

	
	/**
	 * Initializes the variables used as request attributes
	 * true means visible in UI
	 * false means invisible in UI
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createReportConfigurationInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("Inside createReportConfigurationInit method");
		ActionForward forward = null;
	
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{
			// set true/false accordingly to show/hide the configuration controls on page 
			request.setAttribute(Constants.RC_SHOW_ACTION, false);
			request.setAttribute(Constants.RC_SHOW_REPORT, false);
			request.setAttribute(Constants.RC_EDIT_REPORT_NAME, true);
			request.setAttribute(Constants.RC_SHOW_REPORT_TYPES, true);
			request.setAttribute(Constants.RC_SHOW_ROLES,false);
			request.setAttribute(Constants.RC_SHOW_FREQUENCY,false);
			request.setAttribute(Constants.RC_SHOW_TIMINGS,false);
			request.setAttribute(Constants.RC_SHOW_EMAILS,false);
			request.setAttribute(Constants.RC_SHOW_LOBS,false);
			request.setAttribute(Constants.RC_SHOW_PRODUCTS,false);
			request.setAttribute(Constants.RC_SHOW_FIDS,false);
			request.setAttribute(Constants.RC_SHOW_COLUMNS,false);
		
			ReportConfigurationBean formBean = (ReportConfigurationBean)form;
			formBean.setMessage("");
			
			forward = mapping.findForward(SUCCESS);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		logger.info("Exiting createReportConfigurationInit method");
		// forward to success page
		return forward;
	}
	
	
	
	/**
	 * Displays Active Fids and all the columns 
	 * configured in DB.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createRCGetDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("Inside createRCGetDetails method");
		ActionForward forward = null;
		
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		
		try
		{
			ReportConfigurationBean formBean = (ReportConfigurationBean)form;
			MasterService service = new MasterServiceImpl();
			
			// set true/false accordingly to show/hide the UI controls on page 
			request.setAttribute(Constants.RC_SHOW_ACTION, false);
			request.setAttribute(Constants.RC_SHOW_REPORT, false);
			request.setAttribute(Constants.RC_EDIT_REPORT_NAME, true);
			request.setAttribute(Constants.RC_SHOW_REPORT_TYPES, true);
			request.setAttribute(Constants.RC_SHOW_ROLES,false);
			request.setAttribute(Constants.RC_SHOW_FREQUENCY,true);
			request.setAttribute(Constants.RC_SHOW_TIMINGS,true);
			request.setAttribute(Constants.RC_SHOW_EMAILS,true);
			request.setAttribute(Constants.RC_SHOW_LOBS,false);
			request.setAttribute(Constants.RC_SHOW_COLUMNS,true);
			
			String reportType = request.getParameter("reportType");
			if(reportType.equals(REPORT_TYPE_FID))
			{
				List<FidDto> inVisibleFids = service.getAllActiveFids();
				formBean.setInVisibleFids(inVisibleFids);
		    	
				request.setAttribute(Constants.RC_SHOW_PRODUCTS,false);
				request.setAttribute(Constants.RC_SHOW_FIDS,true);
			}
			else if(reportType.equals(REPORT_TYPE_PRODUCT))
			{
				List<ProductDTO> products = service.getAllProducts();
		    	formBean.setProducts(products);
		    	
				request.setAttribute(Constants.RC_SHOW_PRODUCTS,true);
				request.setAttribute(Constants.RC_SHOW_FIDS,false);
		    	
			}
			
			// get all columns
			List<ColumnDto> columns = service.getAllColumns();
			formBean.setInVisibleColumns(columns);
			
			// message to display on screen
			formBean.setMessage("");
			
	    	forward = mapping.findForward(SUCCESS);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		logger.info("Exiting createRCGetDetails method");
		return forward;
	}
	

	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param status
	 * @return
	 * @throws Exception
	 */
	private ActionForward saveCreatedConfigurationCommon(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, String status) throws Exception
	{
		logger.info("Inside saveCreatedConfigurationCommon method");
		ReportConfigurationBean formBean =  (ReportConfigurationBean)form;
		HttpSession session = request.getSession();
		
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{
			ReportsDTO aDto = new ReportsDTO();
			
		 	UserMstr userDto =  (UserMstr)session.getAttribute("USER_INFO");
			aDto.setUpdatedBy(userDto.getUserLoginId());

			String reportName = formBean.getReportName();

			String appender = "";
			String reportType = formBean.getReportType();
			if(reportType != null)
			{
				if(reportType.equals(REPORT_TYPE_FID))
				{
					appender = "_F";
					reportName = "F_"+reportName;
					aDto.setReportName(reportName);
				}
				else if(reportType.equals(REPORT_TYPE_PRODUCT))
				{
					appender = "_P";
					reportName = "P_"+reportName;
					aDto.setReportName(reportName);
				}
			}
			
			
			MasterService service = new MasterServiceImpl();
			
			boolean exist = service.isReportExist(aDto.getReportName().trim());
			if(exist)
			{
				logger.info("Report exist already : " + reportName);
				logger.info("Exiting saveCreatedReportConfiguration method");
				
				// set true/false accordingly to show/hide the UI controls on page 
				request.setAttribute(Constants.RC_SHOW_ACTION, true);
				request.setAttribute(Constants.RC_SHOW_REPORT, false);
				request.setAttribute(Constants.RC_EDIT_REPORT_NAME, false);
				request.setAttribute(Constants.RC_SHOW_REPORT_TYPES, false);
				request.setAttribute(Constants.RC_SHOW_ROLES,false);
				request.setAttribute(Constants.RC_SHOW_FREQUENCY,false);
				request.setAttribute(Constants.RC_SHOW_TIMINGS,false);
				request.setAttribute(Constants.RC_SHOW_EMAILS,false);
				request.setAttribute(Constants.RC_SHOW_LOBS,false);
				request.setAttribute(Constants.RC_SHOW_PRODUCTS,false);
				request.setAttribute(Constants.RC_SHOW_FIDS,false);
				request.setAttribute(Constants.RC_SHOW_COLUMNS,false);
				
				formBean.setMessage(reportName + " exists already. Pls. modify instead of create.");
				
				return mapping.findForward(SUCCESS);
			}
			
			// Set frequency, email ids in DTO
			aDto.setFrequency(Integer.parseInt(formBean.getFrequency()));
			aDto.setToRecipients(formBean.getToRecipients());
			aDto.setCcRecipients(formBean.getCcRecipients());
			aDto.setSubject(formBean.getSubject());
			
			// parse timings and fill in DTO
			String[] timings = formBean.getTimings();
			String timeSelected = "";
			for(String time : timings)
			{
				timeSelected += time+",";
			}
			
			aDto.setTimings(timeSelected);

			int reportId = service.addDynamicReport(aDto.getReportName().trim());
			String reportConfigId = reportId + appender;
			
			aDto.setReportConfigId(reportConfigId);
			aDto.setReportId(reportId);
			aDto.setStatus(status);
			
			service.insertReportConfiguration(aDto);
			
			String[] reportColumns = formBean.getUpdatedColumns();
			if(reportColumns != null)
			{
				int[] columns = new int[reportColumns.length];
				for(int i=0; i<reportColumns.length; i++)
				{
					columns[i] = Integer.parseInt(reportColumns[i]);
				}
				
				aDto.setReportColumns(columns);
				service.updateReportColumns(reportConfigId, aDto.getReportColumns(), formBean.getDisplayNamesMap(), reportId, -1, -1);
			}
			
			if(reportType != null)
			{
				if(reportType.equals(REPORT_TYPE_FID))
				{
					String[] fids = formBean.getUpdatedFids();
					if(fids != null)
					{
						service.updateFids(reportConfigId, fids);
					}
				}
				else if(reportType.equals(REPORT_TYPE_PRODUCT))
				{
					// save selected products
					// report id, lob id product id
					String[] products = formBean.getSelectedProducts();
					if(products != null)
					{
						service.updateProducts(reportConfigId, -1, products);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		// set true/false accordingly to show/hide the UI controls on page 
		request.setAttribute(Constants.RC_SHOW_ACTION, true);
		request.setAttribute(Constants.RC_SHOW_REPORT, false);
		request.setAttribute(Constants.RC_EDIT_REPORT_NAME, false);
		request.setAttribute(Constants.RC_SHOW_REPORT_TYPES, false);
		request.setAttribute(Constants.RC_SHOW_ROLES,false);
		request.setAttribute(Constants.RC_SHOW_FREQUENCY,false);
		request.setAttribute(Constants.RC_SHOW_TIMINGS,false);
		request.setAttribute(Constants.RC_SHOW_EMAILS,false);
		request.setAttribute(Constants.RC_SHOW_LOBS,false);
		request.setAttribute(Constants.RC_SHOW_PRODUCTS,false);
		request.setAttribute(Constants.RC_SHOW_FIDS,false);
		request.setAttribute(Constants.RC_SHOW_COLUMNS,false);
		
		if(status.equals("A"))
		{
			formBean.setMessage("Configuration saved.");
		}
		else
		{
			formBean.setMessage("Configuration is put on hold.");
		}
		
		logger.info("Exiting saveCreatedConfigurationCommon method");
		return mapping.findForward(SUCCESS);
	}
	
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveCreatedReportConfiguration(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return saveCreatedConfigurationCommon(mapping, form, request, response, "A");
	}
	
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward holdCreatedReportConfiguration(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return saveCreatedConfigurationCommon(mapping, form, request, response, "H");
	}
	
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifyReportConfiguration(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("Inside modifyReportConfiguration method");
		
		ActionForward forward = null;
		ReportConfigurationBean formBean = (ReportConfigurationBean)form;
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{		
			MasterService service = new MasterServiceImpl();
			
            // get new reports
			List<ReportsDTO> reportsList = service.getDynamicReports();
			formBean.setReports(reportsList);
			
			// get session
			HttpSession session = request.getSession();
			
			// set common values in session
			session.setAttribute(Constants.RC_ALL_REPORTS, reportsList);

			request.setAttribute(Constants.RC_SHOW_ACTION, false);
			request.setAttribute(Constants.RC_SHOW_REPORT, true);
			request.setAttribute(Constants.RC_EDIT_REPORT_NAME, false);
			request.setAttribute(Constants.RC_SHOW_REPORT_TYPES, false);
			request.setAttribute(Constants.RC_SHOW_ROLES,false);
			request.setAttribute(Constants.RC_SHOW_FREQUENCY,false);
			request.setAttribute(Constants.RC_SHOW_TIMINGS,false);
			request.setAttribute(Constants.RC_SHOW_EMAILS,false);
			request.setAttribute(Constants.RC_SHOW_LOBS,false);
			request.setAttribute(Constants.RC_SHOW_PRODUCTS,false);
			request.setAttribute(Constants.RC_SHOW_COLUMNS,false);
			request.setAttribute(Constants.RC_SHOW_FIDS,false);
			
			formBean.setMessage("");
			
			forward = mapping.findForward(SUCCESS);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		return forward;
	}
	
	
	
	/**
	 * Gets the configuration for (selected report and actor) OR 
	 * (selected report and Lob combination)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getReportConfiguration(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("Inside getReportConfiguration method");
		
		ActionForward forward = null;
		ReportConfigurationBean formBean = (ReportConfigurationBean)form;
		
		try
		{
			// Get from session the reports list
			HttpSession session = request.getSession();
			List<ReportsDTO> reportsList = (List<ReportsDTO>)session.getAttribute(Constants.RC_ALL_REPORTS);
			
			// Set to form bean the reports and actor list
			formBean.setReports(reportsList);
			
			MasterService service = new MasterServiceImpl();
			
			int reportId = Integer.parseInt(formBean.getSelectedReport());
			String reportConfigId = "";
			
			int selectedActor = -1;
			int selectedLOB = -1;
			String reportType = null;
			
			// if MTD required then get the name from report id
			// and check the name instead of id.
			
			/*if(reportId == 5) // MTD report
			{
				List<ActorDto> actors = service.getActors();
	            formBean.setActors(actors);
	            
	            request.setAttribute(Constants.RC_SHOW_ACTION, false);
	            request.setAttribute(Constants.RC_SHOW_REPORT, true);
				request.setAttribute(Constants.RC_EDIT_REPORT_NAME, false);
				request.setAttribute(Constants.RC_SHOW_REPORT_TYPES, false);
				request.setAttribute(Constants.RC_SHOW_ROLES,true);
				request.setAttribute(Constants.RC_SHOW_LOBS,false);
				request.setAttribute(Constants.RC_SHOW_PRODUCTS,false);
				request.setAttribute(Constants.RC_SHOW_FREQUENCY,false);
				request.setAttribute(Constants.RC_SHOW_TIMINGS, false);
				request.setAttribute(Constants.RC_SHOW_EMAILS,false);
				request.setAttribute(Constants.RC_SHOW_FIDS,false);
				
				if(formBean.getSelectedActor() != null)
				{
					selectedActor = Integer.parseInt(formBean.getSelectedActor());
				}
			}
			else*/
			//{
				request.setAttribute(Constants.RC_SHOW_REPORT, true);
				request.setAttribute(Constants.RC_SHOW_REPORT_TYPES, false);
				request.setAttribute(Constants.RC_EDIT_REPORT_NAME, false);

				String reportName = service.getReportNameFromReportId(reportId);
				
				if(reportName.trim().startsWith("F_"))
					reportType = REPORT_TYPE_FID;
				else
					reportType = REPORT_TYPE_PRODUCT;
				
				if(reportType.equals(REPORT_TYPE_FID))
				{
					request.setAttribute(Constants.RC_SHOW_FIDS,true);
					request.setAttribute(Constants.RC_SHOW_LOBS,false);
					request.setAttribute(Constants.RC_SHOW_PRODUCTS,false);
					
					reportConfigId = reportId+"_F";
					
					// get selected Fids for report id
					List<FidDto> visibleFids = service.getSelectedFidsForReport(reportConfigId);
					formBean.setVisibleFids(visibleFids);
					
					// get All Fids from Form Details not found in above line
					List<FidDto> inVisibleFids = service.getAllFids(reportConfigId);
					formBean.setInVisibleFids(inVisibleFids);
				}
				else if(reportType.equals(REPORT_TYPE_PRODUCT))
				{
					request.setAttribute(Constants.RC_SHOW_LOBS,false);
					request.setAttribute(Constants.RC_SHOW_PRODUCTS,true);
					request.setAttribute(Constants.RC_SHOW_FIDS,false);

					reportConfigId = reportId+"_P";
					
		            if(selectedLOB == -1)
		            {
		            	// Show all the products
		            	List<ProductDTO> products = service.getAllProducts();
		            	formBean.setProducts(products);
		            }

		            // Get products for selected report 
		            List<String> selectedProducts = service.getSelectedProducts(reportConfigId, selectedLOB);
		            String[] arr = new String[selectedProducts.size()];
		            for(int i=0; i<arr.length; i++)
		            {
		            	arr[i] = selectedProducts.get(i);
		            }
		            formBean.setSelectedProducts(arr);
				}
				
				request.setAttribute(Constants.RC_SHOW_ACTION, false);
				request.setAttribute(Constants.RC_SHOW_ROLES,false);
				request.setAttribute(Constants.RC_SHOW_FREQUENCY,true);
				request.setAttribute(Constants.RC_SHOW_TIMINGS, true);
				request.setAttribute(Constants.RC_SHOW_EMAILS, true);
			//}
			
			// if not MTD report
			//if(reportId != 5)
			//{
	            // get report Configuration
	            ReportsDTO reportConfiguration = service.getReportConfiguration(reportConfigId, selectedActor, selectedLOB);
	
	            // Set in form bean the fetched values from DB
	            int frequency = reportConfiguration.getFrequency();
	            formBean.setFrequency( frequency == 0 ? "" : (frequency+""));
	            formBean.setToRecipients(reportConfiguration.getToRecipients());
	            formBean.setCcRecipients(reportConfiguration.getCcRecipients());
	            
	            // Prepare the array for timings
	            String timings = reportConfiguration.getTimings();
	            String[] timeSelected = new String[0];
	            if(timings != null)
	            {
	            	timeSelected = timings.split(",");
	            }
	            // Set timings to form bean
	            formBean.setTimings(timeSelected);
	            formBean.setSubject(reportConfiguration.getSubject());
			//}
			
			//if(reportId == 5 || reportType != null)
			if(reportType != null)
			{
	            // Get all the columns relevant for selected report and actor combination
	            List<ColumnDto> columns = service.getColumns(reportConfigId, selectedActor, selectedLOB);
	            
	            // The columns selected to be visible in report
	            List<ColumnDto> visibleColumns = new ArrayList<ColumnDto>();
	            
	            // The columns excluded from report
	            List<ColumnDto> inVisibleColumns = new ArrayList<ColumnDto>();
	            
	            // the display name for visible columns
	            List<ColumnDto> displayNames = new ArrayList<ColumnDto>();
	            
	            // fill the lists with appropriate DTO
	            for(ColumnDto aDto : columns)
	            {
	            	if(aDto.isSelectedInReport())
	            	{
	            		visibleColumns.add(aDto);
	            	}
	            	else
	            	{
	            		inVisibleColumns.add(aDto);
	            	}
	            	
	            	String displayName = aDto.getDisplayName();
	            	
	            	if(displayName != null && !displayName.trim().equals(""))
	            	{
	            		displayNames.add(aDto);
	            	}
	            }
	            
	            // Set in form bean 
	            formBean.setVisibleColumns(visibleColumns);
	            formBean.setInVisibleColumns(inVisibleColumns);
	            formBean.setDisplayNamesList(displayNames);
	            
	            //update the request attribute to show UI controls
	            request.setAttribute(Constants.RC_SHOW_COLUMNS,true);
			}
			else
			{
				request.setAttribute(Constants.RC_SHOW_COLUMNS,false);
			}
			
			// forward to success page
			forward = mapping.findForward(SUCCESS);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		formBean.setMessage("");
		
		logger.info("Exiting getReportConfiguration method");
		return forward;
	}

	
	
	/**
	 * 
	 * @param reportId
	 * @param actorId
	 * @return
	 */
	private boolean checkIfConfigurationExist(String reportConfigId) throws Exception
	{
		logger.info("Inside checkIfConfigurationExist method");
		boolean exist = false;
		try
		{
			MasterService service = new MasterServiceImpl();
			exist = service.checkIfConfigurationExist(reportConfigId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		logger.info("Exiting checkIfConfigurationExist method");
		return exist;
	}
	
	
	/**
	 * 
	 */
	private void createReportConfigurationId(int reportId, String reportName, ReportsDTO aDto)
	{
		
		String appender = "";
		if(reportName.startsWith("F_"))
		{
			appender = "_F";
		}
		else
		{
			appender = "_P";
		}
		
		aDto.setReportConfigId(reportId + appender);
	}
 
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param status
	 * @return
	 * @throws Exception
	 */
	private ActionForward saveConfigurationCommon(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, String status) throws Exception
	{
		logger.info("Inside saveConfigurationCommon method");
		ReportConfigurationBean formBean =  (ReportConfigurationBean)form;
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try
		{
			ReportsDTO aDto = new ReportsDTO();
			String reportType = formBean.getReportType();
			MasterService service = new MasterServiceImpl();
			
			// Get Report id and actor id
			int reportId = Integer.parseInt(formBean.getSelectedReport());
			String reportName = service.getReportNameFromReportId(reportId);
			
			int actorId = -1;
			int lobId = -1;
			
			if(formBean.getSelectedActor() != null)
			{
				actorId = Integer.parseInt(formBean.getSelectedActor());
			}
			
			// set in DTO
			aDto.setReportId(reportId);
			aDto.setActorId(actorId);
			aDto.setLobId(lobId);
			aDto.setReportName(reportName);
			aDto.setStatus(status);
			
			createReportConfigurationId(reportId, reportName, aDto);
			
			//if(reportId != 5)
			//{
				// Set frequency, email ids in DTO
				aDto.setFrequency(Integer.parseInt(formBean.getFrequency()));
				aDto.setToRecipients(formBean.getToRecipients());
				aDto.setCcRecipients(formBean.getCcRecipients());
				aDto.setSubject(formBean.getSubject());
				
				// parse timings and fill in DTO
				String[] timings = formBean.getTimings();
				String timeSelected = "";
				for(String time : timings)
				{
					timeSelected += time+",";
				}
				
				aDto.setTimings(timeSelected);
			//}
			
			// Check if configuration exists  already
			// for combination of report id and actor id
			boolean isExist = checkIfConfigurationExist(aDto.getReportConfigId());
			
			// if exists already, update the record
			// else insert the configuration details
			if(isExist)
			{
				service.updateReportConfiguration(aDto);
			}
			else
			{
				service.insertReportConfiguration(aDto);
			}
			
			String[] reportColumns = formBean.getUpdatedColumns();
			int[] columns = new int[reportColumns.length];
			for(int i=0; i<reportColumns.length; i++)
			{
				columns[i] = Integer.parseInt(reportColumns[i]);
			}
			
			aDto.setReportColumns(columns);
			service.updateReportColumns(aDto.getReportConfigId(), aDto.getReportColumns(), formBean.getDisplayNamesMap(), reportId, actorId, lobId);
			
			if(reportType != null)
			{
				if(reportType.equals(REPORT_TYPE_FID))
				{
					String[] fids = formBean.getUpdatedFids();
					if(fids != null)
					{
						service.updateFids(aDto.getReportConfigId(), fids);
					}
				}
				else if(reportType.equals(REPORT_TYPE_PRODUCT))
				{
					// save selected products
					// report id, lob id product id
					String[] products = formBean.getSelectedProducts();
					if(products != null)
					{
						service.updateProducts(aDto.getReportConfigId(), lobId, products);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		// set true/false accordingly to show/hide the UI controls on page 
		request.setAttribute(Constants.RC_SHOW_ACTION, true);
		request.setAttribute(Constants.RC_SHOW_REPORT, false);
		request.setAttribute(Constants.RC_EDIT_REPORT_NAME, false);
		request.setAttribute(Constants.RC_SHOW_REPORT_TYPES, false);
		request.setAttribute(Constants.RC_SHOW_ROLES,false);
		request.setAttribute(Constants.RC_SHOW_FREQUENCY,false);
		request.setAttribute(Constants.RC_SHOW_TIMINGS,false);
		request.setAttribute(Constants.RC_SHOW_EMAILS,false);
		request.setAttribute(Constants.RC_SHOW_LOBS,false);
		request.setAttribute(Constants.RC_SHOW_PRODUCTS,false);
		request.setAttribute(Constants.RC_SHOW_FIDS,false);
		request.setAttribute(Constants.RC_SHOW_COLUMNS,false);
		
		
		if(status.equals("A"))
		{
			formBean.setMessage("Configuration saved.");
		}
		else
		{
			formBean.setMessage("Configuration is put on hold.");
		}
		
		
		logger.info("Exiting saveConfigurationCommon method");
		return mapping.findForward(SUCCESS);
	}
	
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveReportConfiguration(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return saveConfigurationCommon(mapping, form, request, response, "A");
	}



	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward holdReportConfiguration(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return saveConfigurationCommon(mapping, form, request, response, "H");
	}



	/**
	 * 
	 * @param reportId
	 * @param reportType
	 * @return
	 */
	private String getReportConfigIdFromReportType(int reportId, String reportType)
	{
		String reportConfigId = "";
		
		if(reportType != null)
		{
			if(reportType.equals(REPORT_TYPE_FID))
			{
				reportConfigId = reportId + "_F";
			}
			else if(reportType.equals(REPORT_TYPE_PRODUCT))
			{
				reportConfigId = reportId + "_P";
			}
		}
		return reportConfigId; 
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteReportConfiguration(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("Inside deleteReportConfiguration method");
		
		ActionForward forward = null;
		ReportConfigurationBean formBean = (ReportConfigurationBean)form;
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		
		try
		{
			MasterService service = new MasterServiceImpl();
			
			int reportId = Integer.parseInt(formBean.getSelectedReport());
			String reportType = formBean.getReportType();
			
			String reportName = service.getReportNameFromReportId(reportId);
			if(reportName.trim().startsWith("F_"))
				reportType = REPORT_TYPE_FID;
			else
				reportType = REPORT_TYPE_PRODUCT;
			
			String reportConfigId = getReportConfigIdFromReportType(reportId, reportType);
			
			if( ! reportConfigId.equals(""))
			{
				// delete
				service.deleteReportConfiguration(reportConfigId);
				service.deleteReport(reportId);
			}
			
			// forward to success page
			forward = mapping.findForward(SUCCESS);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		// set true/false accordingly to show/hide the UI controls on page 
		request.setAttribute(Constants.RC_SHOW_ACTION, true);
		request.setAttribute(Constants.RC_SHOW_REPORT, false);
		request.setAttribute(Constants.RC_EDIT_REPORT_NAME, false);
		request.setAttribute(Constants.RC_SHOW_REPORT_TYPES, false);
		request.setAttribute(Constants.RC_SHOW_ROLES,false);
		request.setAttribute(Constants.RC_SHOW_FREQUENCY,false);
		request.setAttribute(Constants.RC_SHOW_TIMINGS,false);
		request.setAttribute(Constants.RC_SHOW_EMAILS,false);
		request.setAttribute(Constants.RC_SHOW_LOBS,false);
		request.setAttribute(Constants.RC_SHOW_PRODUCTS,false);
		request.setAttribute(Constants.RC_SHOW_FIDS,false);
		request.setAttribute(Constants.RC_SHOW_COLUMNS,false);
		
		
		formBean.setMessage("Configuration deleted successfully.");
		
		
		logger.info("Exiting deleteReportConfiguration method");
		return forward;
	}
}