package com.ibm.km.forms;

import org.apache.struts.action.ActionForm;

public class KmDocumentHitsCountFormBean extends ActionForm{

	private int barType;
	private int documentId;
	private String documentName = null;
	private int documentCount;
	private int docType;
	private int lobId;
	private String methodName = null;
	
	
	public int getDocType() {
		return docType;
	}
	public void setDocType(int docType) {
		this.docType = docType;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public int getBarType() {
		return barType;
	}
	public void setBarType(int barType) {
		this.barType = barType;
	}
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public int getDocumentCount() {
		return documentCount;
	}
	public void setDocumentCount(int documentCount) {
		this.documentCount = documentCount;
	}
	public int getLobId() {
		return lobId;
	}
	public void setLobId(int lobId) {
		this.lobId = lobId;
	}
	
	
}
