package com.ibm.lms.actions;


import java.io.PrintWriter;
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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.ibm.lms.common.UserDetails;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.CircleForProductLob;
import com.ibm.lms.dto.ProductLobDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.BulkAssignmentDownloadFormBean;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.services.impl.UserMstrServiceImpl;
import com.ibm.lms.wf.dto.BulkMatrixDownloadDTO;


public class BulkAssignmentDownloadAction extends DispatchAction {

	private static Logger logger = Logger.getLogger(BulkAssignmentDownloadAction.class.getName());

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("Inside BulkAssigmentDownloadAction init method");
		
		ActionForward forward = new ActionForward(); 
		ArrayList<ProductLobDTO> lobList=null;
		if(Utility.isValidRequest(request)) {
        	return mapping.findForward("error");
        }
		MasterService masterService = new MasterServiceImpl();
		logger.info("Inside userMasterDownloadAction init method");
		HttpSession session = request.getSession();
		UserMstr userBean =(UserMstr) session.getAttribute("USER_INFO");
		BulkAssignmentDownloadFormBean bulkAssignmentDownloadFormBean =  (BulkAssignmentDownloadFormBean) form;

		saveToken(request);
			//Circle Names Populated
		
			//bulkAssignmentDownloadFormBean. setCircleTypeList(masterService.getCircleTypeList());
			
			//Lob Names List Populated
			
			//bulkAssignmentDownloadFormBean.setLobsNameList(masterService.getLobsNameList());
			lobList=masterService.getLobsNameList();
				//logger.info("loblist values........."+lobList);
			request.setAttribute("productLobList",lobList);
		
			forward = mapping.findForward("bulkAssignmentDownload");
			logger.info(UserDetails.getUserLoginId(request)+" : Exited init method");
			return (forward);
		
	}
	
	
	
	public ActionForward downloadAssignment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		
		logger.info(UserDetails.getUserLoginId(request)+" : Entered downLoadData method");
		
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		UserMstrService userMstrService = new UserMstrServiceImpl();
		BulkAssignmentDownloadFormBean bulkAssignmentDownloadFormBean =  (BulkAssignmentDownloadFormBean) form;
		MasterService masterService = new MasterServiceImpl();
		userBean.setFileName("BulkAssignmentMatrixDownload.xls for Bulk Assignment Matrix Download");
		userBean.setMessage("SUCCESS");
		
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try{
			
			bulkAssignmentDownloadFormBean.setMsg("");
			//logger.info("Selected Master Table "+bulkAssignmentDownloadFormBean.getSelectedCircleId());
			int circleTableId = bulkAssignmentDownloadFormBean.getSelectedCircleId();
			logger.info("circle id........"+circleTableId);
		int lobTableId=bulkAssignmentDownloadFormBean.getSelectedproductlobId();
			logger.info("lob id.........."+lobTableId);
			String usertype=bulkAssignmentDownloadFormBean.getUserType();
			logger.info("user type............."+usertype);
	
			ArrayList<BulkMatrixDownloadDTO> matrixList = new ArrayList<BulkMatrixDownloadDTO>();
			matrixList = masterService.getAssignmentDownloadData(circleTableId,lobTableId,usertype);
		
			//logger.info("Data values.........."+matrixList);
			request.setAttribute("assignmentList", matrixList);
	

			if(matrixList == null || matrixList.equals(""))
			{
				bulkAssignmentDownloadFormBean.setMsg("Bulk Assignment Data Not Found");
			}
				
			} catch (Exception e) {
					userBean.setMessage("FAILED");
				e.printStackTrace();
			}
			
			userMstrService.insertBulkDataTransactionLogs(userBean);
			return mapping.findForward("viewAssignmentDownloadExcel");
			}
	public ActionForward getCircleOnProductChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws LMSException
	{
		
		int productLobId ;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		MasterService masterService = new MasterServiceImpl();
		Element optionElement;
		try
		{
			
			if (request.getParameter("productLobId") != null)
			{
				productLobId = Integer.parseInt(request.getParameter("productLobId").toString());
				logger.info("productLobId :"+productLobId);
			
				List circleList = masterService.getCircleForProductName(productLobId);
			
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
	}
}
		
	
	