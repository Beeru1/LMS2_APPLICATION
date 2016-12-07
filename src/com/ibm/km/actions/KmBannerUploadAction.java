package com.ibm.km.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.ibm.km.forms.KmBannerUploadFormBean;
import com.ibm.km.services.KmBannerUploadService;
import com.ibm.km.services.impl.KmBannerUploadServiceImpl;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;

public class KmBannerUploadAction extends DispatchAction{
	
	public ActionForward initUploadBanner(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		
		return mapping.findForward("success");
	}
	
	public ActionForward uploadBanner(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		  KmBannerUploadFormBean formBean = (KmBannerUploadFormBean)form;
		  FormFile bannerUploaded = formBean.getNewFile();
		  String initialSelectBox = formBean.getInitialSelectBox();
		  String bannerName = "";
		  ActionMessages messages =new ActionMessages();
		  ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
		  KmBannerUploadService service = new KmBannerUploadServiceImpl();
		  
		  if(initialSelectBox.equals("loginPage"))
			  bannerName = bundle.getString("addBanner.db.bannerName.loginPage");
		  else if(initialSelectBox.equals("csrHomePage"))
			  bannerName = bundle.getString("addBanner.db.bannerName.csrHomePage");
		  
		  try{
			  byte[] fileData = bannerUploaded.getFileData();
		  // check for file size..
			  if(bundle.getString("addBanner.size") != null)
			  if(Integer.parseInt(bundle.getString("addBanner.size"))  <  fileData.length)
			  {
				  	messages.add("msg1",new ActionMessage("addBanner.size.failure"));
			  } else {
				  service.uploadBanner(fileData, bannerUploaded.getFileName(), bannerName);
				  messages.add("msg1",new ActionMessage("addBanner.success"));
			  }
		 }catch(FileNotFoundException fnfe){
			  fnfe.printStackTrace();
			  	messages.add("msg1",new ActionMessage("addBanner.failure"));
		  }catch(IOException ioe){
			  ioe.printStackTrace();
				messages.add("msg1",new ActionMessage("addBanner.failure"));
		  }catch(LMSException kme){
			  kme.printStackTrace();
				messages.add("msg1",new ActionMessage("addBanner.failure"));
		  }catch(DAOException daoe){
			  daoe.printStackTrace();
				messages.add("msg1",new ActionMessage("addBanner.failure"));
		  }

		 saveMessages(request, messages);
		return mapping.findForward("success");
	}
	
}
