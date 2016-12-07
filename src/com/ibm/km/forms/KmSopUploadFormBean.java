package com.ibm.km.forms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.km.dto.ProductDetails;

public class KmSopUploadFormBean extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String elementFolderPath;
	private String[] createMultiple = null;
	
	private String topic="";
	private String publishDt="";
	private String endDt="";
	
	private String[] header;	
	private String[] content;
	
	private String productHeader;	
	private String productPath;	
	private String[] productHeaders;	
	private String[] productPaths;
	private String[] productPathsLabels;
	
	private String   xmlFileName="";
	
	private String methodName="";
	private String createdBy="";
	private String token="";
	private String message="";
	private String updatedBy="";
	private String kmActorId="";
	
	private String parentId="";
	
	private String elementLevel="";
	private String initialLevel;
	private String initialSelectBox="";

	private String sopPathId="";
	private String docId="";
	
	private List<ProductDetails> productDetailsList= new ArrayList<ProductDetails>();
	private List<ProductDetails> productPathList= new ArrayList<ProductDetails>();
	
	String isFavourite = "";
	
	
	public String getIsFavourite() {
		return isFavourite;
	}
	public void setIsFavourite(String isFavourite) {
		this.isFavourite = isFavourite;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String[] getProductPathsLabels() {
		return productPathsLabels;
	}
	public void setProductPathsLabels(String[] productPathsLabels) {
		this.productPathsLabels = productPathsLabels;
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
	public String getSopPathId() {
		return sopPathId;
	}
	public void setSopPathId(String sopPathId) {
		this.sopPathId = sopPathId;
	}
	public String getProductHeader() {
		return productHeader;
	}
	public void setProductHeader(String productHeader) {
		this.productHeader = productHeader;
	}
	public String getProductPath() {
		return productPath;
	}
	public void setProductPath(String productPath) {
		this.productPath = productPath;
	}
	public String[] getProductHeaders() {
		return productHeaders;
	}
	public void setProductHeaders(String[] productHeaders) {
		this.productHeaders = productHeaders;
	}
	public String[] getProductPaths() {
		return productPaths;
	}
	public void setProductPaths(String[] productPaths) {
		this.productPaths = productPaths;
	}

	public String[] getCreateMultiple() {
		return createMultiple;
	}
	public void setCreateMultiple(String[] createMultiple) {
		this.createMultiple = createMultiple;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getElementFolderPath() {
		return elementFolderPath;
	}
	public void setElementFolderPath(String elementFolderPath) {
		this.elementFolderPath = elementFolderPath;
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

	public List<ProductDetails> getProductDetailsList() {
		return productDetailsList;
	}
	public void setProductDetailsList(List<ProductDetails> productDetailsList) {
		this.productDetailsList = productDetailsList;
	}
	
	public List<ProductDetails> getProductPathList() {
		return productPathList;
	}
	public void setProductPathList(List<ProductDetails> productPathList) {
		this.productPathList = productPathList;
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
		
	/*	if(content != null)
		for(int i=0; i < content.length ; i++)
		{ 
			content[i] = content[i].replaceAll("<", "&lt;");
			content[i] = content[i].replaceAll(">", "&gt;");
		}*/
		
		this.content = content;
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

	@Override 
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
		  topic="";
		  header=null;	
		  content=null;
		  publishDt="";
		  endDt="";
		  productHeader=null;
		  productPath=null;
		  xmlFileName="";		
		  methodName="";
		  createdBy="";
		  token="";
		  message="";
		  productDetailsList= null;
		  productPathList= null;
		  kmActorId="";
		  updatedBy="";
		  parentId="";
		  elementFolderPath="";
          elementLevel="";
		  initialLevel="";
		  initialSelectBox="";
		  createMultiple = null;
		  docId="";
		  super.reset(mapping, request);
	}
}
