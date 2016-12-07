package com.ibm.lms.actions;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;



import com.ibm.lms.common.Constants;
import com.ibm.lms.common.UserDetails;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.CircleForProductLob;
import com.ibm.lms.dto.ProductLobDTO;
import com.ibm.lms.dto.UserMstr;

import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.UserMasterDownloadFormBean;



import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.UserMstrService;

import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.services.impl.UserMstrServiceImpl;
import com.ibm.lms.wf.dto.BulkMatrixDownloadDTO;
import com.ibm.lms.wf.dto.UserDownloadDTO;



public class UserMasterDownloadAction extends DispatchAction {

	private static Logger logger = Logger.getLogger(UserMasterDownloadAction.class
			.getName());

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("Inside userMasterDownloadAction init method");
		HttpSession session = request.getSession();
		UserMstr userBean =(UserMstr) session.getAttribute("USER_INFO");
		ArrayList actorList = new ArrayList();
		ArrayList<ProductLobDTO> lobList=null;
		ActionForward forward = new ActionForward(); 
		
		MasterService masterService = new MasterServiceImpl();
		
	UserMasterDownloadFormBean userMaster =  (UserMasterDownloadFormBean) form;

	saveToken(request);	
	if(Utility.isValidRequest(request)) {
    	return mapping.findForward("error");
    }
	
	try{
				request.setAttribute("userBean", userBean);
		
				//logger.info("userBean.getKmActorId():::::::::::"+userBean.getKmActorId());
				
		if(userBean.getKmActorId().equals(Constants.CIRCLE_USER)){
			
			logger.info("circle coordinator loop");
			lobList=masterService.getAssignProductLobList(userBean.getLobList());
			logger.info("lobList Values........."+lobList);
		}
		
		if(userBean.getKmActorId().equals(Constants.CIRCLE_USER)){
			request.setAttribute("productLobList", lobList);
		}
		else
		{
		request.setAttribute("productLobList", masterService.getLobsNameList());
		
		}
		
		
		//lobsNameList
			//	lob Names Populated
			//userMaster.setLobsNameList(masterService.getLobsNameList());
			
			//User Role Populated
		if(userBean.getKmActorId().equals(Constants.CIRCLE_USER)){
			
			actorList = masterService.getActorList(userBean.getKmActorId());
		}
		
		else
		{
			actorList = masterService.getActorList(userBean.getKmActorId());
			
		}
			if(actorList !=null && actorList.size() > 0)
				userMaster.setActorList(actorList);
			
			forward = mapping.findForward("userMasterDownload");
			logger.info(UserDetails.getUserLoginId(request)+" : Exited init method");
			return (forward);
			
	}
	catch(Exception e)
	{
		logger.error("Exception occured while initializing the create user page");
		forward = mapping.findForward("userMasterDownload");
		return (forward);
	}
		
	}
	
	
	public ActionForward userMasterDownload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("calling userMasterdownload method");
		ActionForward forward = new ActionForward(); 
		logger.info(UserDetails.getUserLoginId(request)+" : Entered downloadData method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		UserMstr sessionUserBean =(UserMstr) session.getAttribute("USER_INFO");
		UserMasterDownloadFormBean userMaster =  (UserMasterDownloadFormBean) form;
		MasterService masterService = new MasterServiceImpl();
		UserMstrService userMstr = new UserMstrServiceImpl();
		sessionUserBean.setFileName("UserMasterDownload.xls");
		if(Utility.isValidRequest(request)) {
        	return mapping.findForward("error");
        }
		
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try{
		
		userMaster.setMsg("");
			
			int circleTableId = userMaster.getCircleId();
			logger.info("circle id......."+circleTableId);
			int productlobId=userMaster.getSelectedproductlobId();
			logger.info("productlobId......."+productlobId);
			int selectActorId=Integer.parseInt(userMaster.getKmActorId());
			logger.info("Actor id"+selectActorId);
			
			
		ArrayList<UserDownloadDTO> userList= new ArrayList<UserDownloadDTO>();
		userList = masterService.getuserMasterDownloadData(circleTableId,productlobId,selectActorId);
		
		logger.info("Data values.........."+userList);
		request.setAttribute("assignmentList", userList);
		
		if(userList == null || userList.equals(""))
		{
			userMaster.setMsg("UserMaster Data Not Found");
		}
		sessionUserBean.setMessage("SUCCESS");	
			
	} catch (Exception e) {
		sessionUserBean.setMessage("FAILED");
		e.printStackTrace();
		}
		
		response.setContentType ("application/vnd.ms-excel");			
		response.setHeader("Content-Disposition","attachment; filename=UserMasterDownload.xls");
		
		userMstr.insertBulkDataTransactionLogs(sessionUserBean);
		return mapping.findForward("userMasterDownloadExcel");
		
	}
	
	
	
	//public ActionForward getCircleOnProductChangeForUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{/*
		
		int productLobId ;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		MasterService masterService = new MasterServiceImpl();
		Element optionElement;
		logger.info("Inside Ajax method");
		try
		{
			
			if (request.getParameter("productLobId") != null)
			{
				productLobId = Integer.parseInt(request.getParameter("productLobId").toString());
				logger.info("productLobId :"+productLobId);
			
				List circleList = masterService.getCircleForProductName(productLobId);
				logger.info("circle list"+circleList);
			
				if(circleList != null && circleList.size()==0)
				{				
					optionElement = root.addElement("option");
					optionElement.addAttribute("value","testing");
					optionElement.addAttribute("text", "testing");
				}
				if (circleList != null && circleList.size() > 0)
				{
					
					for (int intCounter = 0; intCounter < circleList.size(); intCounter++)
					{
						optionElement = root.addElement("option");
						optionElement.addAttribute("value", String.valueOf(((CircleForProductLob)circleList.get(intCounter)).getCircleId()));
						optionElement.addAttribute("text", ((CircleForProductLob)circleList.get(intCounter)).getCircleName());
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
	*/
		//}
}
}
	