package com.ibm.km.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import org.json.JSONObject;

import com.ibm.km.dto.KmAlertMstr;
import com.ibm.km.dto.KmElementMstr;
import com.ibm.km.dto.KmScrollerMstr;
import com.ibm.km.forms.KmElementMstrFormBean;
import com.ibm.km.forms.KmMoveElementFormBean;
import com.ibm.km.forms.KmScrollerMstrFormBean;
import com.ibm.km.services.KmElementMstrService;
import com.ibm.km.services.KmScrollerMstrService;
import com.ibm.km.services.impl.KmElementMstrServiceImpl;
import com.ibm.km.services.impl.KmScrollerMstrServiceImpl;
import com.ibm.lms.common.Constants;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;

/**
 * @version 	1.0
 * @author		aditya 
 */
public class KmScrollerMstrAction extends DispatchAction {

	/**
	 * Logger for the class.
	**/
	private static final Logger logger;

	static {

		logger = Logger.getLogger(KmScrollerMstrAction.class);
	}
	/**
	 * Initializes Create Scroller page
	 **/

	public ActionForward init(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		
		UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
		
			KmScrollerMstrFormBean formBean=(KmScrollerMstrFormBean) form;	
			KmElementMstrService elementService= new KmElementMstrServiceImpl();
			/* Added by Anil for giving create alert access to all admins */
			try{
				
				initializeParameter(request, userBean, formBean);
			
			if(request.getParameter("message")==null){
				formBean.setMessage("");
				formBean.setViewEditFlag("Create Scroller");
				
			}
			else{
				formBean.setMessage(request.getParameter("message").toString());
				formBean.setMessageId(request.getParameter("messageId").toString());
				formBean.setViewEditFlag("Edit Scroller");
				
			}
			formBean.setKmActorId(userBean.getKmActorId());
			/*			added by harpreet the default date in KM Phase 2   */ 
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			String date = sdf.format(new java.util.Date());
			date = date.substring(0,10);
			formBean.setStartDate(date);
			formBean.setEndDate(date);
			formBean.setCircleId("");
			}catch(Exception e){
				logger.info("Exception occured while initializing the create scroller page ");
				
				
			}
			return mapping.findForward("addScroller");
		
		}
	/**
	 * @param request
	 * @param bundle
	 * @param userBean
	 * @param formBean
	 * @throws LMSException
	 */
	private void initializeParameter(HttpServletRequest request,
			UserMstr userBean,
			KmScrollerMstrFormBean formBean) throws LMSException {
		ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");

		KmElementMstrService kmElementService = new KmElementMstrServiceImpl();
		List firstDropDown;
		try{
		if (userBean.getKmActorId().equals(bundle.getString("LOBAdmin"))||userBean.getKmActorId().equals(bundle.getString("Superadmin"))) {
			firstDropDown = kmElementService.getAllChildren(userBean.getElementId());
		}else{
			firstDropDown = kmElementService.getChildren(userBean.getElementId());
		
		}					
		if (firstDropDown!=null && firstDropDown.size()!=0){
			formBean.setInitialLevel(((KmElementMstr)firstDropDown.get(0)).getElementLevel());
		}
		else{
			
			int initialLevel=Integer.parseInt(kmElementService.getElementLevelId(userBean.getElementId()));
			initialLevel++;
			formBean.setInitialLevel(initialLevel+"");
		}
		formBean.setParentId(userBean.getElementId());
		formBean.setCircleElementId("");
		formBean.setInitialSelectBox("-1");
	
		request.setAttribute("elementLevelNames",kmElementService.getAllElementLevelNames());
		request.setAttribute("allParentIdString",kmElementService.getAllParentIdString("1",userBean.getElementId()));
		request.setAttribute("firstList",firstDropDown);
		}catch(Exception e){
			logger.info("Exception occured while initializing the create scroller page ");
			
			
		}
	}
	/*
	 * Creates a new Scroller
	 */
	public ActionForward loadElementDropDown(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
			String parentId = request.getParameter("ParentId");		
			KmElementMstrService kmElementService = new KmElementMstrServiceImpl();

			try {
				request.setAttribute("FILE_COUNT_DTO",null);
				JSONObject json = kmElementService.getElementsAsJson(parentId);
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Type", "application/x-json");
				response.getWriter().print(json);		
			} catch (IOException e) {
				logger.error("IOException in Loading Element Dropdown: "+e.getMessage());
				
			} catch (Exception e) {
				logger.error("Exception in Loading Element Dropdown: "+e.getMessage());
				
			}
			
			return null;
		}
	
	
	
	private KmElementMstr setElementDTO(KmElementMstrFormBean kmElementMstrformBean) {
		KmElementMstr elementDTO=new KmElementMstr();

		elementDTO.setElementName(kmElementMstrformBean.getElementName());
		elementDTO.setElementDesc(kmElementMstrformBean.getElementDesc());
		elementDTO.setParentId(kmElementMstrformBean.getParentId());
		elementDTO.setElementLevel(kmElementMstrformBean.getElementLevel());
		//logger.info("Parent Id: "+elementDTO.getParentId());
		elementDTO.setPanStatus("N");
		elementDTO.setStatus("A");
		
		//elementDTO.setFile(file);
		return elementDTO;
	}
	
	public ActionForward initMove(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
				UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
				KmMoveElementFormBean formBean = (KmMoveElementFormBean)form;
				formBean.reset(mapping,request);
				logger.info("Init Button Type: "+formBean.getButtonType());
				logger.info(userBean.getUserLoginId() + " entered initMove method");
				try {
					KmElementMstrService kmElementService = new KmElementMstrServiceImpl();
					List firstDropDown = kmElementService.getChildren(userBean.getElementId());
					if (firstDropDown!=null && firstDropDown.size()!=0){
						formBean.setInitialLevel(((KmElementMstr)firstDropDown.get(0)).getElementLevel());
					}
					if(request.getAttribute("ELEMENT_LIST")==null)
						formBean.setInitStatus("true");	
					else
					formBean.setInitStatus("false");
					formBean.setParentId(userBean.getElementId());
					request.setAttribute("elementLevelNames",kmElementService.getAllElementLevelNames());
					request.setAttribute("allParentIdString",kmElementService.getAllParentIdString("1",userBean.getElementId()));
					request.setAttribute("firstList",firstDropDown);
				} catch (LMSException e) {
					logger.error("Exception in Loading page for document move "+e.getMessage());
					
				}
				logger.info(userBean.getUserLoginId() + " exited initMove ");
				return mapping.findForward("initMove");
		}
	
	public ActionForward listElements(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
				UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
				KmMoveElementFormBean formBean = (KmMoveElementFormBean)form;
				logger.info(userBean.getUserLoginId() + " entered listElements method");
				KmElementMstrService elementService=new KmElementMstrServiceImpl();
				ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
				try {
					formBean.setOldPath(formBean.getElementPath());
					formBean.setOldElementLevel(formBean.getElementLevel());
					String parentId=formBean.getParentId();
					
					formBean.setInitStatus("");
					ArrayList elementList=null;
					if (userBean.getKmActorId().equals(bundle.getString("LOBAdmin"))||userBean.getKmActorId().equals(bundle.getString("Superadmin"))) {
						elementList=elementService.getAllChildrenWithPath(parentId, userBean.getElementId());
					} 
					else {
						elementList=elementService.getChildrenWithPath(parentId, userBean.getElementId());
					}
					if(elementList.size()>0){
						formBean.setButtonType("move");
					}else{
						formBean.setButtonType("list");
					}
					request.setAttribute("ELEMENT_LIST",elementList);
				} catch (LMSException e) {
					logger.error("Exception in listing ELEMENTS in move elements "+e.getMessage());
				
				}
				logger.info(userBean.getUserLoginId() + " exited elementListing ");
				return initMove(mapping, formBean, request, response);
		}
	
	
	public ActionForward insert(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
				
				ActionErrors errors = new ActionErrors();
				ActionMessages messages= new ActionMessages();
				ActionForward forward = new ActionForward(); // return value
				KmScrollerMstrFormBean formBean=(KmScrollerMstrFormBean) form;		
				KmScrollerMstr dto=new KmScrollerMstr();
				KmScrollerMstrService service=new KmScrollerMstrServiceImpl();
				HttpSession session = request.getSession();
				KmElementMstrService elementService = new KmElementMstrServiceImpl();
				UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
				String circleId;
				try{
					logger.info(sessionUserBean.getUserLoginId()+ " Entered in to the insert method of KmScrollerMstrAction");
					circleId = formBean.getCircleElementId();
					int rowsUpdated = 0;
					//Populating the DTO
					
					dto.setMessage(formBean.getMessage());
					dto.setCreatedBy(sessionUserBean.getUserId());
					dto.setUpdatedBy(sessionUserBean.getUserId());
					dto.setStatus("A");
					dto.setStartDate(formBean.getStartDate());
					dto.setEndDate(formBean.getEndDate());	
					logger.info("-------"+formBean.getMessageId());
					
					if(sessionUserBean.getKmActorId().equals(Constants.SUPER_ADMIN)){ // If PAN India Admin creates the Scroller
						if(circleId.equals(""))
							dto.setCircleId("-2");   // -2 LOB Scroller will be created
						else
						    dto.setCircleId(circleId); // This will be for Circle Scroller
						dto.setLobId(formBean.getInitialSelectBox());
						if(formBean.getInitialSelectBox().equals("-1")) // This is case for PAN India
							rowsUpdated= service.createAllLobScroller(dto); // This will create PAN India Scroller
						else 
							rowsUpdated= service.createScroller(dto); // This creates for LOB or Circle Scroller
					}else
						if(sessionUserBean.getKmActorId().equals(Constants.LOB_ADMIN)){ // If LOB Admin is creating the Scroller
							if(formBean.getInitialSelectBox().equals("-1"))
							{
								dto.setCircleId("-2");
								dto.setLobId(sessionUserBean.getElementId());
							}
							else
							{
								dto.setCircleId(formBean.getInitialSelectBox());
								dto.setLobId("");
							} 
							 rowsUpdated= service.createScroller(dto); 
						}
					else{ // If circle Admin Creates the Scroller
						dto.setCircleId((sessionUserBean.getElementId()));
						dto.setLobId("");
						rowsUpdated= service.createScroller(dto);
					}
					
							
					if (rowsUpdated > 0){
						
						if(formBean.getViewEditFlag().equalsIgnoreCase("Create Scroller")){
							logger.info("A New Scroller is Created");
							messages.add("msg1",new ActionMessage("scroller.created"));
							initializeParameter(request, sessionUserBean, formBean);
							
						}
						else if(formBean.getViewEditFlag().equalsIgnoreCase("Edit Scroller")){
							logger.info("Scroller is Updated");
							messages.add("msg1",new ActionMessage("scroller.edited"));
						}
						formBean.setMessage("");
						/*			added by harpreet the default date in KM Phase 2   */ 
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
						String date = sdf.format(new java.util.Date());
						date = date.substring(0,10);
						formBean.setStartDate(date);
						formBean.setEndDate(date);
					}
					saveMessages(request, messages);	
					
	
				}catch(Exception e){
					e.printStackTrace();
					formBean.setMessage("");
					formBean.setStartDate("");
					formBean.setEndDate("");
					errors.add("",new ActionError("scroller.error"));
					logger.error("Error occured while creating the scroller");
					saveErrors(request, errors);
					logger.error("Exception occured while creating scroller :"+e);
					
				}
				 return mapping.findForward("addScroller");
	}
	
	public ActionForward view(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

			UserMstr sessionUserBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
			logger.info(sessionUserBean.getUserLoginId()+ " Entered in to the view method of KmScrollerMstrAction");
			KmScrollerMstrFormBean formBean=(KmScrollerMstrFormBean) form;	
			initializeParameter(request, sessionUserBean, formBean);
			formBean.setMessage("");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			String date = sdf.format(new java.util.Date());
			date = date.substring(0,10);
			formBean.setStartDate(date);
			formBean.setEndDate(date);
			formBean.setKmActorId(sessionUserBean.getKmActorId());
			formBean.setScrollerList(null);
			formBean.setInitStatus("true");
			return mapping.findForward("viewScroller");
		}
		
	public ActionForward viewScroller(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
				
				ActionErrors errors = new ActionErrors();
				ActionMessages messages= new ActionMessages();
				KmScrollerMstrFormBean formBean=(KmScrollerMstrFormBean) form;		
				KmScrollerMstr dto=new KmScrollerMstr();
				KmScrollerMstrService service=new KmScrollerMstrServiceImpl();
				HttpSession session = request.getSession();
				UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
				String initialSelectBox = formBean.getInitialSelectBox();
				String circleElementId = formBean.getCircleElementId();
				try{
					
					logger.info(sessionUserBean.getUserLoginId()+ " Entered in to the viewScroller of KmScrollerMstrAction");
				
					if(sessionUserBean.getKmActorId().equals(Constants.SUPER_ADMIN)){
						
					if(initialSelectBox.equals("-1"))
						dto.setCircleId("1");
					else 
						if(circleElementId.equals("") || circleElementId.equals("-2"))
							dto.setCircleId(initialSelectBox);
					else
						    dto.setCircleId(circleElementId);							
					}else
						if(sessionUserBean.getKmActorId().equals(Constants.LOB_ADMIN)){
							if(formBean.getInitialSelectBox().equals("-1"))
								dto.setCircleId(sessionUserBean.getElementId());
							else
								dto.setCircleId(initialSelectBox);	
					}else
					{
					  dto.setCircleId(sessionUserBean.getElementId());
					}
					
					dto.setActorId(Integer.parseInt(sessionUserBean.getKmActorId()));
					dto.setCreatedBy(sessionUserBean.getUserId());
					dto.setStartDate(formBean.getStartDate());
					dto.setEndDate(formBean.getEndDate());	
					ArrayList scrollerList=service.getScrollerList(dto);
					formBean.setScrollerList(scrollerList);
					if (scrollerList==null){
						logger.info("Listing Scrollers");
						messages.add("msg1",new ActionMessage("scroller.errorList"));
						formBean.setMessage("");
						formBean.setStartDate("");
						formBean.setEndDate("");
					}
					else{
						formBean.setScrollerCount(scrollerList.size()+"");
						
					}
					initializeParameter(request, sessionUserBean, formBean);
					saveMessages(request, messages);
					formBean.setInitStatus("false");
					formBean.setInitialSelectBox(initialSelectBox);
					formBean.setCircleElementId(circleElementId);
				}catch(Exception e){
					
					errors.add("",new ActionError("scroller.errorView"));
					logger.error("Error Occured While Fetching Scroller List");
					saveErrors(request, errors);
					logger.error("Exception Occured While Fetching Scroller List :"+e);
					return view(mapping,formBean,request,response);
				}
				return mapping.findForward("viewScroller");
	}
	
	// Method to Edit Scroller
	public ActionForward editScroller(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			  throws Exception {

				UserMstr sessionUserBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
				logger.info(sessionUserBean.getUserLoginId()+ " Entered in to the edit method of KmScrollerMstrAction");
				KmScrollerMstrFormBean formBean=(KmScrollerMstrFormBean) form;	
				KmScrollerMstrService service=new KmScrollerMstrServiceImpl();
				KmAlertMstr dto=new KmAlertMstr();
				int rowsUpdated=0;
				try{
				formBean.setMessage(request.getParameter("message").toString());
				formBean.setMessageId(request.getParameter("messageId").toString());
					  //formBean.setViewEditFlag("Edit Scroller");
				
				  //}
				dto.setMessage(formBean.getMessage());
				dto.setMessageId(formBean.getMessageId());
				dto.setCreatedDt(formBean.getCreatedDt());
				dto.setUpdatedDt(formBean.getUpdatedDt());
	
			//calling service to edit Alert
			   rowsUpdated=service.editScroller(dto);
				StringBuffer updated=new StringBuffer(rowsUpdated);
				if (rowsUpdated > 0){
					logger.info("Scroller is updated");
				}
			}	
			catch(Exception e){
				logger.error("Exception occured while editing alerts");
			}
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			//logger.info("hierarchy------" + updated);
			out.write(rowsUpdated+"");
			out.flush();
			return null;   
			
		 	 
			  }
	
	public ActionForward delete(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
				
				ActionErrors errors = new ActionErrors();
				ActionMessages messages= new ActionMessages();
				ActionForward forward = new ActionForward(); // return value
				KmScrollerMstrFormBean formBean=(KmScrollerMstrFormBean) form;		
				KmScrollerMstr dto=new KmScrollerMstr();
				KmScrollerMstrService service=new KmScrollerMstrServiceImpl();
				HttpSession session = request.getSession();
				UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
				String circleElementId = formBean.getCircleElementId();
				String initialSelectBox = formBean.getInitialSelectBox();
				try{
					logger.info(sessionUserBean.getUserLoginId()+ " Entered in to the Delete of KmScrollerMstrAction");
					//Populating the DTO
					if(request.getParameter("messageId").equals(null)){
						addmessage(messages,formBean,request);
						
					}
					else{
						logger.info("request.getParameter"+request.getParameter("messageId"));
						dto.setMessageId(request.getParameter("messageId"));
						messages.add("msg1",new ActionMessage("scroller.deleted"));
						saveMessages(request, messages);
						String message=service.deleteScroller(dto);
						if (message.equals("failure")){
							addmessage(messages,formBean,request);
						}
						else{
							messages.add("msg1",new ActionMessage("scroller.deleted"));
							
							if(sessionUserBean.getKmActorId().equals(Constants.SUPER_ADMIN)){
								if(!circleElementId.equals(""))
									formBean.setCircleElementId(circleElementId);
								else
								    formBean.setInitialSelectBox(initialSelectBox);						
							}
							
							if(sessionUserBean.getKmActorId().equals(Constants.LOB_ADMIN)){
								if(initialSelectBox.equals("-1"))
									formBean.setInitialSelectBox(initialSelectBox);
								else
									formBean.setCircleElementId(circleElementId);	
							}
							
							
							formBean.setStartDate("");
							formBean.setEndDate("");
							saveMessages(request, messages);
						}
						
					}
				
					return mapping.findForward("deleteScroller");
	
				}catch(Exception e){
					errors.add("",new ActionError("scroller.notdeleted"));
					logger.error("Error occured while deleting the scroller");
					saveErrors(request, errors);
					logger.error("Exception occured while deleting scroller :"+e);
					return mapping.findForward("deleteScroller");
				}
	}
	
				private void addmessage(ActionMessages messages,KmScrollerMstrFormBean formBean,HttpServletRequest request){
				messages.add("msg1",new ActionMessage("scroller.notdeleted"));
				formBean.setStartDate("");
				formBean.setEndDate("");
				saveMessages(request, messages);
			}
}
