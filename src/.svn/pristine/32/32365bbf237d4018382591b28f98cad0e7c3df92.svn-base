package com.ibm.km.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class KmCsrLatestUpdatesDto {
	
	private String updateTitle=null;
	private String updateBrief=null;
	private String updateDesc=null;
	private String documentId = null;
	private int docType;
	private String documentViewUrl = null;
	
	private String activationDate = null;
	private String expiryDate = null;
	
	private String lob = null;
	private String circleId = null;
	private String category = null;
	private String creationDt = null;
	
	private String recId = null;
	 
	 
	public String getDocumentViewUrl() {
		return documentViewUrl;
	}
	public void setDocumentViewUrl(String documentViewUrl) {
		this.documentViewUrl = documentViewUrl;
	}
	public int getDocType() {
		return docType;
	}
	public void setDocType(int docType) {
		this.docType = docType;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String docId) {
		this.documentId = docId;
	}
	public String getUpdateTitle() {
		return updateTitle;
	}
	public void setUpdateTitle(String updateTitle) {
		this.updateTitle = updateTitle;
	}
	public String getUpdateBrief() {
		return updateBrief;
	}
	public void setUpdateBrief(String updateBrief) {
		this.updateBrief = updateBrief;
	}
	public String getUpdateDesc() {
		return updateDesc;
	}
	public void setUpdateDesc(String updateDesc) {
		this.updateDesc = updateDesc;
	}
	

	/**
	 * @return the activationDate
	 */
	public String getActivationDate() {
		return activationDate;
	}
	/**
	 * @param activationDate the activationDate to set
	 */
	public void setActivationDate(String activationDate) {
		this.activationDate = activationDate;
	}
	/**
	 * @return the expiryDate
	 */
	public String getExpiryDate() {
		return expiryDate;
	}
	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the lob
	 */
	public String getLob() {
		return lob;
	}
	/**
	 * @param lob the lob to set
	 */
	public void setLob(String lob) {
		this.lob = lob;
	}
	/**
	 * @return the circleId
	 */
	public String getCircleId() {
		return circleId;
	}
	/**
	 * @param circleId the circleId to set
	 */
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	
	public JSONObject toJSONObject() {
		JSONObject json=new JSONObject();
		try {
			json.put("updateTitle", updateTitle);
			json.put("updateBrief", updateBrief);
			json.put("updateDesc", updateDesc);
			json.put("documentId",documentId);
			json.put("docType", docType);
			json.put("documentViewUrl", documentViewUrl);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return json; 
	}
	public void setCreationDt(String creationDt) {
		this.creationDt = creationDt;
	}
	public String getCreationDt() {
		return creationDt;
	}
	public void setRecId(String recId) {
		this.recId = recId;
	}
	public String getRecId() {
		return recId;
	}


	
}
