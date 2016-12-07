/*
 * Created on Apr 1, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dto;

import java.util.ResourceBundle;

/**
 * @author namangup
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DocumentPath {
	private String circleId=null;
	private String categoryId=null;
	private String subCategoryId=null;
	private String documentName=null;
	
	

	
	public String getStringPath(){
		ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
		if(circleId!=null&&categoryId!=null&&subCategoryId!=null&&documentName!=null){
			return bundle.getString("folder.path") + circleId + "/" + categoryId + "/" + subCategoryId + "/" + documentName ;	
		}else if(circleId!=null&&categoryId!=null&&subCategoryId!=null&&documentName==null) {
			return bundle.getString("folder.path") + circleId + "/" + categoryId + "/" + subCategoryId ;
		}else if(circleId!=null&&categoryId!=null&&subCategoryId==null&&documentName==null){
			return bundle.getString("folder.path") + circleId + "/" + categoryId;
		}else if(circleId!=null&&categoryId==null&&subCategoryId==null&&documentName==null){
			return bundle.getString("folder.path") + circleId  ;
		}else{
			return bundle.getString("folder.path");
		}
		
	}
	public String getNewStringPath(){
		ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
		
			return bundle.getString("whatsnewfolder.path") + circleId + "/"  + documentName ;	
		
		}

	
	/**
	 * @return
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @return
	 */
	public String getCircleId() {
		return circleId;
	}

	/**
	 * @return
	 */
	public String getDocumentName() {
		return documentName;
	}

	/**
	 * @return
	 */
	public String getSubCategoryId() {
		return subCategoryId;
	}

	/**
	 * @param string
	 */
	public void setCategoryId(String string) {
		categoryId = string;
	}

	/**
	 * @param string
	 */
	public void setCircleId(String string) {
		circleId = string;
	}

	/**
	 * @param string
	 */
	public void setDocumentName(String string) {
		documentName = string;
	}

	/**
	 * @param string
	 */
	public void setSubCategoryId(String string) {
		subCategoryId = string;
	}

}
