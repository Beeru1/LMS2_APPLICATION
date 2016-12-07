package com.ibm.km.dto;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ProductDetails extends ActionForm {


	
	private static final long serialVersionUID = 1L;
	
	private String header="";	
	private String content="";
	 
	private String imageTitle="";
	private String imageName="";
	
	private String productHeader="";	
	private String productPath="";
	
	private FormFile imageFile = null;
	
	public FormFile getImageFile() {
		return imageFile;
	}
	public void setImageFile(FormFile imageFile) {
		this.imageFile = imageFile;
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
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImageTitle() {
		return imageTitle;
	}
	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
}
