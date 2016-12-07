package com.ibm.lms.wf.actions;


/**
 * @author Parnika Sharma 
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.lms.common.UserDetails;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.ProductDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.services.ProductMappingService;
import com.ibm.lms.services.impl.ProductMappingServiceImpl;
import com.ibm.lms.wf.forms.ProductMappingForm;

public class ProductMappingAction extends DispatchAction{
	private static Logger logger = Logger.getLogger(ProductMappingAction.class
			.getName());
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		
		ActionForward forward = new ActionForward(); // return value
		logger.info(UserDetails.getUserLoginId(request)+" : Entered init method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		session.setAttribute("CREATE_SYNONYM", "true");
		ProductMappingForm productBean = (ProductMappingForm) form;
		ProductMappingService productService = new ProductMappingServiceImpl();
		
		if(Utility.isValidRequest(request) && (session.getAttribute("URLFLAG")==null || !"N".equalsIgnoreCase((String)session.getAttribute("URLFLAG")))) {
        	return mapping.findForward("error");
        }
		// Populating Dropdowns
		productBean.reset(mapping, request);
		productBean.setProductLobList(productService.getProductLobList());
		
		forward = mapping.findForward("initProductMapping");
		logger.info(UserDetails.getUserLoginId(request)+" : Exited init method");
		// Finish with
		return (forward);  

	}
	
	// create synonym
	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionForward forward = new ActionForward(); // return value
		logger.info(UserDetails.getUserLoginId(request)+" : Entered insert method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		
		if (session.getAttribute("CREATE_SYNONYM") == null ) {
			session.setAttribute("URLFLAG", "N");
			  return init(mapping, form, request, response);
			}
		session.setAttribute("CREATE_SYNONYM", null);
		
		
		ProductMappingForm productBean = (ProductMappingForm) form;
		ProductMappingService productService = new ProductMappingServiceImpl();
		UserMstr userBean = (UserMstr) request.getSession().getAttribute("USER_INFO");
		
		ProductDTO productDto = new ProductDTO();

		productDto.setNewProductSynonym(productBean.getNewProductSynonym());
		productDto.setProductId(productBean.getSelectedProductId());
		productDto.setUpdatedBy(userBean.getUserLoginId());
		//Inserting Product Synonym
	
		int rowInserted = productService.insertProductSynonym(productDto);
		
		if(rowInserted == 1){
			productBean.setMessage("Product Synonym successfully created");
			productBean.reset(mapping, request);
			productBean.setProductLobList(productService.getProductLobList());
		}
		
		else if(rowInserted != 1){
			productBean.setMessage("Product Synonym not created");
		}
		forward = mapping.findForward("initProductMapping");
		logger.info(UserDetails.getUserLoginId(request)+" : Exited insert method");
		// Finish with
		return (forward);  

	}
}
