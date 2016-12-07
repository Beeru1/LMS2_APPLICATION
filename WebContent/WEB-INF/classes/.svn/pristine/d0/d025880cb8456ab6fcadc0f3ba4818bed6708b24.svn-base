package com.ibm.km.forms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.ibm.km.dto.ProductDetails;

public class KmProductUploadFormBean extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String elementFolderPath;
	private String[] createMultiple = null;
	private String elementLevel="";
	private String initialLevel;
	private String initialSelectBox="";
	
	private String topic="";
	private String publishDt="";
	private String endDt="";
	
	private String[] header;	
	private String[] content;
	
	private String[] imageTitle;
	private String[] imageName;

	private FormFile[] productImages= new FormFile[10];
	List<ProductDetails> productImageList =  new ArrayList<ProductDetails>();
	private String   xmlFileName="";
	
	private String methodName="";
	private String userAction="";
	private String createdBy="";
	private String token="";
	private String message="";
	private String updatedBy="";
	private String kmActorId="";
	private String docId="";
	
	private String[] displayedImageTitle;	
	private String[] displayedImagePath;
	
	
	

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public List<ProductDetails> getProductImageList() {
		return productImageList;
	}

	public void setProductImageList(List<ProductDetails> productImageList) {
		this.productImageList = productImageList;
	}

	public void setProductImages(FormFile[] productImages) {
		this.productImages = productImages;
	}

	public FormFile[] getProductImages() {
		return productImages;
	}

	public FormFile getProductImages(int index) {
		return productImages[index];
	}

	public void setProductImages(int index, FormFile file) {
		productImages[index] = file;
	}

	public String getElementLevel() {
		return elementLevel;
	}
	public void setElementLevel(String elementLevel) {
		this.elementLevel = elementLevel;
	}
	public String getInitialLevel() {
		return initialLevel;
	}
	public void setInitialLevel(String initialLevel) {
		this.initialLevel = initialLevel;
	}
	public String getInitialSelectBox() {
		return initialSelectBox;
	}
	public void setInitialSelectBox(String initialSelectBox) {
		this.initialSelectBox = initialSelectBox;
	}
	public String getElementFolderPath() {
		return elementFolderPath;
	}
	public void setElementFolderPath(String elementFolderPath) {
		this.elementFolderPath = elementFolderPath;
	}
	public String[] getCreateMultiple() {
		return createMultiple;
	}
	public void setCreateMultiple(String[] createMultiple) {
		this.createMultiple = createMultiple;
	}
	public String getUserAction() {
		return userAction;
	}
	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}

	
	public String getPublishDt() {
		return publishDt;
	}
	public void setPublishDt(String publishDt) {
		this.publishDt = publishDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public String getKmActorId() {
		return kmActorId;
	}
	public void setKmActorId(String kmActorId) {
		this.kmActorId = kmActorId;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getXmlFileName() {
		return xmlFileName;
	}
	public void setXmlFileName(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}
	
	
	
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
public String[] getHeader() {
		
		if (null == header)
		{
			header = new String[5];	
			for(int ii=0; ii<5; ii++)
			{
				header[ii] = "";
			}
		}
		
		return header;
	}
	public void setHeader(String[] header) {
		this.header = header;
	}
	public String[] getContent() {
		
		if(null == content)
		{
			content = new String[5];	
			for(int ii=0; ii<5; ii++)
			{
				content[ii] = "";
			}
		}

		return content;
	}
	public void setContent(String[] content) {
		
		/*if(content != null)
		for(int i=0; i < content.length ; i++)
		{ 
			content[i] = content[i].replaceAll("<", "&lt;");
			content[i] = content[i].replaceAll(">", "&gt;");
		}*/
		
		this.content = content;
	}
	
	public String[] getImageTitle() {
		return imageTitle;
	}
	public void setImageTitle(String[] imageTitle) {
		this.imageTitle = imageTitle;
	}
	public String[] getImageName() {
		return imageName;
	}
	public void setImageName(String[] imageName) {
		this.imageName = imageName;
	}
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}



	public String[] getDisplayedImageTitle() {
		return displayedImageTitle;
	}

	public void setDisplayedImageTitle(String[] displayedImageTitle) {
		this.displayedImageTitle = displayedImageTitle;
	}

	public String[] getDisplayedImagePath() {
		return displayedImagePath;
	}

	public void setDisplayedImagePath(String[] displayedImagePath) {
		this.displayedImagePath = displayedImagePath;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
		  topic="";
		  publishDt="";
		  endDt="";
		  header=null;	
		  content=null;
		  imageTitle=null;
		  imageName=null;
		  xmlFileName="";		
		  methodName="";
		  createdBy="";
		  token="";
		  message="";
		  productImages = null;
		  kmActorId="";
		  updatedBy="";
		  
		  displayedImageTitle=null;
		  displayedImagePath=null;
	
	//	  docId="";
		  productImageList =  new ArrayList<ProductDetails>();
		  for( int i=0; i<2; i++ )
		  {
			productImageList.add(new ProductDetails());
		  }
			
		  super.reset(mapping, request);
	}
}
