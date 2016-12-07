package com.ibm.km.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form bean for a Struts application.
 * @version 	1.0
 * @author		Anil
 */
public class KmSearchFormBean extends ActionForm {
	
	// iPortal_Enhancements changes for complete string search; CSR20111025-00-06938:BFR2
	private String searchContentHidden="";
	// End of Change	
	private String searchCircleId="";
	
	private String categoryName = "";
	private String subCategoryName = "";
	private String categoryId="";
	
	private String subCategoryId="";
	
	private String subSubCategoryId="";
	
	private String fromDate="";
	private String toDate="";
	private String methodName="";
	private String keyword="";
	private String elementId="";
	
    private String selectedDocumentPath="";
    private String searchType="";
	private String searchButton="";
	private String documentKeyword="";
	private String selectedLob="";
	
	private String searchMode="";
	
	private String dummy = "";
	
	private String searchModeChecked="";
	
	private String searchFromDt ="";
	private String searchToDt = "";
	private String dateCheck = "";
	
	public String getDummy() {
		return dummy;
	}

	public void setDummy(String dummy) {
		this.dummy = dummy;
	}
	
	private String initStatus="true";

	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// Reset field values here.

	}

	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		// Validate the fields in your form, adding
		// adding each error to this.errors as found, e.g.

		// if ((field == null) || (field.length() == 0)) {
		//   errors.add("field", new org.apache.struts.action.ActionError("error.field.required"));
		// }
		return errors;

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
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @return
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @return
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @return
	 */
	public String getSubCategoryId() {
		return subCategoryId;
	}

	

	/**
	 * @return
	 */
	public String getToDate() {
		return toDate;
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
	public void setFromDate(String string) {
		fromDate = string;
	}

	/**
	 * @param string
	 */
	public void setKeyword(String string) {
		keyword = string;
	}

	/**
	 * @param string
	 */
	public void setMethodName(String string) {
		methodName = string;
	}

	/**
	 * @param string
	 */
	public void setSubCategoryId(String string) {
		subCategoryId = string;
	}



	/**
	 * @param string
	 */
	public void setToDate(String string) {
		toDate = string;
	}



	/**
	 * @return
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @return
	 */
	public String getSubCategoryName() {
		return subCategoryName;
	}

	/**
	 * @param string
	 */
	public void setCategoryName(String string) {
		categoryName = string;
	}

	/**
	 * @param string
	 */
	public void setSubCategoryName(String string) {
		subCategoryName = string;
	}



	/**
	 * @return
	 */
	public String getSelectedDocumentPath() {
		return selectedDocumentPath;
	}

	/**
	 * @param string
	 */
	public void setSelectedDocumentPath(String string) {
		selectedDocumentPath = string;
	}

	/**
	 * @return
	 */
	public String getSearchCircleId() {
		return searchCircleId;
	}

	/**
	 * @param string
	 */
	public void setSearchCircleId(String string) {
		searchCircleId = string;
	}

	/**
	 * @return
	 */
	public String getElementId() {
		return elementId;
	}

	/**
	 * @param string
	 */
	public void setElementId(String string) {
		elementId = string;
	}

	/**
	 * @return
	 */
	public String getSubSubCategoryId() {
		return subSubCategoryId;
	}

	/**
	 * @param string
	 */
	public void setSubSubCategoryId(String string) {
		subSubCategoryId = string;
	}

	/**
	 * @return
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * @param string
	 */
	public void setSearchType(String string) {
		searchType = string;
	}

	/**
	 * @return
	 */
	public String getSearchButton() {
		return searchButton;
	}

	/**
	 * @param string
	 */
	public void setSearchButton(String string) {
		searchButton = string;
	}

	/**
	 * @return
	 */
	public String getDocumentKeyword() {
		return documentKeyword;
	}

	/**
	 * @param string
	 */
	public void setDocumentKeyword(String string) {
		documentKeyword = string;
	}

	/**
	 * @return
	 */
	public String getSelectedLob() {
		return selectedLob;
	}

	/**
	 * @param string
	 */
	public void setSelectedLob(String string) {
		selectedLob = string;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	//	 iPortal_Enhancements changes for complete string search; CSR20111025-00-06938:BFR2
	public String getSearchContentHidden() {
		return searchContentHidden;
	}

	public void setSearchContentHidden(String searchContentHidden) {
		this.searchContentHidden = searchContentHidden;
	}
	// End of Change

	public String getSearchModeChecked() {
		return searchModeChecked;
	}

	public void setSearchModeChecked(String searchModeChecked) {
		this.searchModeChecked = searchModeChecked;
	}

	public String getSearchFromDt() {
		return searchFromDt;
	}

	public void setSearchFromDt(String searchFromDt) {
		this.searchFromDt = searchFromDt;
	}

	public String getSearchToDt() {
		return searchToDt;
	}

	public void setSearchToDt(String searchToDt) {
		this.searchToDt = searchToDt;
	}

	public String getDateCheck() {
		return dateCheck;
	}

	public void setDateCheck(String dateCheck) {
		this.dateCheck = dateCheck;
	}

	public String getInitStatus() {
		return initStatus;
	}

	public void setInitStatus(String initStatus) {
		this.initStatus = initStatus;
	}
	
	
	
}
