package com.ibm.km.forms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.km.dto.ProductDetails;

public class KmSopBDUploadFormBean extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String elementFolderPath;
	private String[] createMultiple = null;
	
	private String topic="";
	private String publishDt="";
	private String endDt="";
	
	private String[] header;	
    private String[] content;
    
	private String[] headerVas;	
    private String[] contentVas;
	
	private String[] headerVoice;	
	private String[] contentVoice;
	
	private String[] headerMo;	
	private String[] contentMo;
	
	private String[] headerCNN;	
	private String[] contentCNN;
	
	private String[] headerALive;	
	private String[] contentALive;
	
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
	
	
	
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String[] getHeaderVas() {
		if (null == headerVas)
		{
			headerVas = new String[5];	
			for(int ii=0; ii<5; ii++)
			{
				headerVas[ii] = "";
			}
		}
		return headerVas;
	}
	public void setHeaderVas(String[] headerVas) {
		this.headerVas = headerVas;
	}
	public String[] getContentVas() {
		if(null == contentVas)
		{
			contentVas = new String[5];	
			for(int ii=0; ii<5; ii++)
			{
				contentVas[ii] = "";
			}
		}
		return contentVas;
	}
	public void setContentVas(String[] contentVas) {
		/*if(contentVas != null)
			for(int i=0; i < contentVas.length ; i++)
			{ 
				contentVas[i] = contentVas[i].replaceAll("<", "&lt;");
				contentVas[i] = contentVas[i].replaceAll(">", "&gt;");
			}*/
		this.contentVas = contentVas;
	}
	public String[] getHeaderVoice() {
		if (null == headerVoice)
		{
			headerVoice = new String[5];	
			for(int ii=0; ii<5; ii++)
			{
				headerVoice[ii] = "";
			}
		}
		return headerVoice;
	}
	public void setHeaderVoice(String[] headerVoice) {
		this.headerVoice = headerVoice;
	}
	public String[] getContentVoice() {
		if(null == contentVoice)
		{
			contentVoice = new String[5];	
			for(int ii=0; ii<5; ii++)
			{
				contentVoice[ii] = "";
			}
		}
		return contentVoice;
	}
	public void setContentVoice(String[] contentVoice) {
		/*if(contentVoice != null)
			for(int i=0; i < contentVoice.length ; i++)
			{ 
				contentVoice[i] = contentVoice[i].replaceAll("<", "&lt;");
				contentVoice[i] = contentVoice[i].replaceAll(">", "&gt;");
			}*/
		this.contentVoice = contentVoice;
	}
	public String[] getHeaderMo() {
		if (null == headerMo)
		{
			headerMo = new String[5];	
			for(int ii=0; ii<5; ii++)
			{
				headerMo[ii] = "";
			}
		}
		return headerMo;
	}
	public void setHeaderMo(String[] headerMo) {
		this.headerMo = headerMo;
	}
	public String[] getContentMo() {
		if(null == contentMo)
		{
			contentMo = new String[5];	
			for(int ii=0; ii<5; ii++)
			{
				contentMo[ii] = "";
			}
		}
		return contentMo;
	}
	public void setContentMo(String[] contentMo) {
		/*if(contentMo != null)
			for(int i=0; i < contentMo.length ; i++)
			{ 
				contentMo[i] = contentMo[i].replaceAll("<", "&lt;");
				contentMo[i] = contentMo[i].replaceAll(">", "&gt;");
			}*/
		this.contentMo = contentMo;
	}
	public String[] getHeaderCNN() {
		if (null == headerCNN)
		{
			headerCNN = new String[5];	
			for(int ii=0; ii<5; ii++)
			{
				headerCNN[ii] = "";
			}
		}
		return headerCNN;
	}
	public void setHeaderCNN(String[] headerCNN) {
		this.headerCNN = headerCNN;
	}
	public String[] getContentCNN() {
		if(null == contentCNN)
		{
			contentCNN = new String[5];	
			for(int ii=0; ii<5; ii++)
			{
				contentCNN[ii] = "";
			}
		}
		return contentCNN;
	}
	public void setContentCNN(String[] contentCNN) {
		/*if(contentCNN != null)
			for(int i=0; i < contentCNN.length ; i++)
			{ 
				contentCNN[i] = contentCNN[i].replaceAll("<", "&lt;");
				contentCNN[i] = contentCNN[i].replaceAll(">", "&gt;");
			}*/
		this.contentCNN = contentCNN;
	}
	public String[] getHeaderALive() {
		if (null == headerALive)
		{
			headerALive = new String[5];	
			for(int ii=0; ii<5; ii++)
			{
				headerALive[ii] = "";
			}
		}
		return headerALive;
	}
	public void setHeaderALive(String[] headerALive) {
		this.headerALive = headerALive;
	}
	public String[] getContentALive() {
		if(null == contentALive)
		{
			contentALive = new String[5];	
			for(int ii=0; ii<5; ii++)
			{
				contentALive[ii] = "";
			}
		}
		return contentALive;
	}
	public void setContentALive(String[] contentALive) {
		/*if(contentALive != null)
			for(int i=0; i < contentALive.length ; i++)
			{ 
				contentALive[i] = contentALive[i].replaceAll("<", "&lt;");
				contentALive[i] = contentALive[i].replaceAll(">", "&gt;");
			}*/
		this.contentALive = contentALive;
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
		  xmlFileName="";		
		  methodName="";
		  createdBy="";
		  token="";
		  publishDt="";
		  endDt="";			
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
		  headerVas = null;	
		  contentVas = null;
		  headerVoice = null;
		  contentVoice = null;
		  headerMo = null;	
		  contentMo = null;
		  headerCNN = null;
		  contentCNN = null;
		  headerALive = null;
		  contentALive = null;
			
		  super.reset(mapping, request);
	}
}
