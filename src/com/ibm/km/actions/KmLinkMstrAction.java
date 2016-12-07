package com.ibm.km.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.km.forms.KmLinkMstrFormBean;
import com.ibm.km.services.KmElementMstrService;
import com.ibm.km.services.impl.KmElementMstrServiceImpl;
import com.ibm.lms.common.Constants;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.dto.LinkMstrDto;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.services.KmLinkMstrService;
import com.ibm.lms.services.impl.LinkMstrServiceImpl;

public class KmLinkMstrAction extends DispatchAction{
	
	public ActionForward initExecute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		//logger.info("Inside KmLinkMstrAction.initExecute()......");
		KmLinkMstrFormBean formBean = (KmLinkMstrFormBean)form;
		LinkMstrDto dto = new LinkMstrDto();
		ArrayList<LinkMstrDto> list = new ArrayList<LinkMstrDto>();
		HttpSession session = request.getSession();
		//LinkMst linkMst = new LinkMst();
		
		UserMstr sessionUserBean =
			(UserMstr) session.getAttribute("USER_INFO");
		////logger.info("sesionUserBean(USER_INFO)=="+sessionUserBean.getElementId());
		//KmUserMstrFormBean kmUserMstrFormBean = (KmUserMstrFormBean) form;
		
		KmElementMstrService elementService = new KmElementMstrServiceImpl();
		formBean.setElementList(
				elementService.getChildren(sessionUserBean.getElementId()));

		
		KmLinkMstrService linkMstrService = new LinkMstrServiceImpl();
		////logger.info("formBean.elementId=="+formBean.getElementId()+","+request.getParameter("elementId"));
		if (formBean.getElementId() == null || formBean.getElementId().equalsIgnoreCase("")){
			list = linkMstrService.viewLinks("0");
		}else{
			list = linkMstrService.viewLinks(formBean.getElementId());
		}
		if(list.size() == 0 && !formBean.getElementId().equalsIgnoreCase("")){
			LinkMstrDto linkMst = new LinkMstrDto();
			linkMst.setLinkId(1);
			linkMst.setLinkTitle("");
			linkMst.setLinkPath("");
			list.add(linkMst);
			linkMst = new LinkMstrDto();
			linkMst.setLinkId(2);
			linkMst.setLinkTitle("");
			linkMst.setLinkPath("");
			list.add(linkMst);
			linkMst = new LinkMstrDto();
			linkMst.setLinkId(3);
			linkMst.setLinkTitle("");
			linkMst.setLinkPath("");
			list.add(linkMst);
		}
		formBean.setLinkList(list);
		////logger.info("list attribute===="+list);
		
		session.setAttribute("list",list);
		//request.setAttribute("list",list);
		return mapping.findForward("success");
	}

	public ActionForward create(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		//logger.info("Inside KmLinkMstrAction.create() method...........");
		return mapping.findForward("success");
	}
	
	public ActionForward update(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		//logger.info("Inside KmLinkMstrAction.update() method...........");
		
		ActionErrors errors = new ActionErrors();
		ActionMessages messages= new ActionMessages();
		KmLinkMstrFormBean formBean = (KmLinkMstrFormBean)form;
		
		ArrayList list = null;
		ArrayList list1 = null;
		HttpSession session = request.getSession();
		list = (ArrayList)session.getAttribute("list");
		formBean = (KmLinkMstrFormBean)form;
		LinkMstrDto dto;
		
		KmLinkMstrService service = new LinkMstrServiceImpl();
		////logger.info("populated listFromJSP==="+formBean.getLinkList()+","+formBean.getListKmLink());
		
		////logger.info("populated listFromJSP==="+formBean.getKmLinkMstrDto(0));
		int max_count = 0;
		int max_countForElement = 0;
		int min_linkIdForElement = 0;
		try{
			//max_count = service.getMaxLinkIdFromDB();
			max_countForElement = service.getMaxRowCountForElement(Integer.parseInt(formBean.getElementId()));
		}catch(DAOException daoe){
			daoe.printStackTrace();
		}
		////logger.info("max_count in action class=="+max_count+","+max_countForElement);
		for(int i=0;i<list.size();i++){
			String title = request.getParameter("linkMst["+i+"].linkTitle");
			////logger.info("parameter==="+title);
			String path = request.getParameter("linkMst["+i+"].linkPath");
			////logger.info("parameter==="+path);
			
			dto = new LinkMstrDto();
			if(max_countForElement == 3){
				dto.setLinkId(service.getLinkIdForElement(Integer.parseInt(formBean.getElementId()))+i);
				//dto.setLinkId(max_count+i+1);
			}
			dto.setLinkTitle(title);
			dto.setLinkPath(path);
			if(!(formBean.getElementId() == null || formBean.getElementId().equalsIgnoreCase(""))){
				dto.setElementId(Integer.parseInt(formBean.getElementId()));
				dto.setKmActorId(Constants.CIRCLE_ADMIN);
			}
			list1 = service.editLink(dto);
		}
		
		messages.add("msg1",new ActionMessage("linkupload.status"));
		saveMessages(request, messages);	

		session.setAttribute("list", list1);
		//request.setAttribute("list", list);
		formBean.setLinkList(list1);
		//logger.info("list in action class==="+list1);
		
		return mapping.findForward("success");
	
	}
}

