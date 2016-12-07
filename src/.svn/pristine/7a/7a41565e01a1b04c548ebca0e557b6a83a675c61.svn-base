/**
 * 
 */
package com.ibm.lms.forms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.lms.dto.ActorDto;
import com.ibm.lms.dto.ColumnDto;
import com.ibm.lms.dto.FidDto;
import com.ibm.lms.dto.LOBDTO;
import com.ibm.lms.dto.ProductDTO;
import com.ibm.lms.dto.ReportsDTO;

/**
 * @author Nehil Parashar
 *
 */
public class ReportConfigurationBean extends ActionForm
{
	private List<ActorDto> actors;
	private String selectedActor;
	
	private List<LOBDTO> lobs;
	private String selectedLob;
	
	private List<ReportsDTO> reports;
	private String selectedReport;

	private String frequency="";
	private String[] timings;
	
	private String toRecipients="";
	private String ccRecipients="";
		
	private List<ColumnDto> visibleColumns;
	private List<ColumnDto> inVisibleColumns;
	
	private List<FidDto> visibleFids;
	private List<FidDto> inVisibleFids;
	
	private String[] selectedFids;
	private String[] unselectedFids;
	private String[] updatedFids;
	private String[] selectedColumns;
	private String[] unselectedColumns;
	private String[] updatedColumns;
	
	private List<ColumnDto> displayNamesList;
	private String[] displayNames;
	
	private String[] displayNamesMap;
	
	private List<ProductDTO> products;
	private String[] selectedProducts;

	private String reportType;
	private String actionType;
	
	private String reportName;
	private String subject;
	
	private String message;
	
	
	/**
	 * @return the selectedColumns
	 */
	public String[] getSelectedColumns() {
		return selectedColumns;
	}




	/**
	 * @param selectedColumns the selectedColumns to set
	 */
	public void setSelectedColumns(String[] selectedColumns) {
		this.selectedColumns = selectedColumns;
	}




	/**
	 * @return the unselectedColumns
	 */
	public String[] getUnselectedColumns() {
		return unselectedColumns;
	}




	/**
	 * @param unselectedColumns the unselectedColumns to set
	 */
	public void setUnselectedColumns(String[] unselectedColumns) {
		this.unselectedColumns = unselectedColumns;
	}




	/**
	 * @return the displayNames
	 */
	public String[] getDisplayNames() {
		return displayNames;
	}




	/**
	 * @param displayNames the displayNames to set
	 */
	public void setDisplayNames(String[] displayNames) {
		this.displayNames = displayNames;
	}




	/**
	 * @return the reports
	 */
	public List<ReportsDTO> getReports() {
		return reports;
	}




	/**
	 * @param reports the reports to set
	 */
	public void setReports(List<ReportsDTO> reports) {
		this.reports = reports;
	}




	/**
	 * @return the selectedReport
	 */
	public String getSelectedReport() {
		return selectedReport;
	}




	/**
	 * @param selectedReport the selectedReport to set
	 */
	public void setSelectedReport(String selectedReport) {
		this.selectedReport = selectedReport;
	}




	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}




	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}




	/**
	 * @return the timings
	 */
	public String[] getTimings() {
		return timings;
	}




	/**
	 * @param timings the timings to set
	 */
	public void setTimings(String[] timings) {
		this.timings = timings;
	}




	/**
	 * @return the toRecipients
	 */
	public String getToRecipients() {
		return toRecipients;
	}




	/**
	 * @param toRecipients the toRecipients to set
	 */
	public void setToRecipients(String toRecipients) {
		this.toRecipients = toRecipients;
	}




	/**
	 * @return the ccRecipients
	 */
	public String getCcRecipients() {
		return ccRecipients;
	}




	/**
	 * @param ccRecipients the ccRecipients to set
	 */
	public void setCcRecipients(String ccRecipients) {
		this.ccRecipients = ccRecipients;
	}




	/**
	 * @return the visibleColumns
	 */
	public List<ColumnDto> getVisibleColumns() {
		return visibleColumns;
	}




	/**
	 * @param visibleColumns the visibleColumns to set
	 */
	public void setVisibleColumns(List<ColumnDto> visibleColumns) {
		this.visibleColumns = visibleColumns;
	}




	/**
	 * @return the inVisibleColumns
	 */
	public List<ColumnDto> getInVisibleColumns() {
		return inVisibleColumns;
	}




	/**
	 * @param inVisibleColumns the inVisibleColumns to set
	 */
	public void setInVisibleColumns(List<ColumnDto> inVisibleColumns) {
		this.inVisibleColumns = inVisibleColumns;
	}

	/**
	 * @param actors the actors to set
	 */
	public void setActors(List<ActorDto> actors) {
		this.actors = actors;
	}




	/**
	 * @param selectedActor the selectedActor to set
	 */
	public void setSelectedActor(String selectedActor) {
		this.selectedActor = selectedActor;
	}




	/**
	 * @return the lobs
	 */
	public List<LOBDTO> getLobs() {
		return lobs;
	}




	/**
	 * @param lobs the lobs to set
	 */
	public void setLobs(List<LOBDTO> lobs) {
		this.lobs = lobs;
	}




	/**
	 * @return the selectedLob
	 */
	public String getSelectedLob() {
		return selectedLob;
	}




	/**
	 * @param selectedLob the selectedLob to set
	 */
	public void setSelectedLob(String selectedLob) {
		this.selectedLob = selectedLob;
	}




	/**
	 * @return the selectedActor
	 */
	public String getSelectedActor() {
		return selectedActor;
	}




	/**
	 * @return the actors
	 */
	public List<ActorDto> getActors() {
		return actors;
	}


	/**
	 * @return the updatedColumns
	 */
	public String[] getUpdatedColumns() {
		return updatedColumns;
	}




	/**
	 * @param updatedColumns the updatedColumns to set
	 */
	public void setUpdatedColumns(String[] updatedColumns) {
		this.updatedColumns = updatedColumns;
	}




	/**
	 * @return the displayNamesList
	 */
	public List<ColumnDto> getDisplayNamesList() {
		return displayNamesList;
	}




	/**
	 * @param displayNamesList the displayNamesList to set
	 */
	public void setDisplayNamesList(List<ColumnDto> displayNamesList) {
		this.displayNamesList = displayNamesList;
	}




	/**
	 * @return the displayNamesMap
	 */
	public String[] getDisplayNamesMap() {
		return displayNamesMap;
	}




	/**
	 * @param displayNamesMap the displayNamesMap to set
	 */
	public void setDisplayNamesMap(String[] displayNamesMap) {
		this.displayNamesMap = displayNamesMap;
	}




	/**
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		selectedReport = null;
		selectedActor = null;
		selectedLob = null;
		timings = new String[1];
		timings[0] = "3";
		toRecipients = "";
		ccRecipients = "";
		reportType = null;
		actionType = null;
		reportName = "";
		subject = "";
		visibleColumns = null;
		displayNamesList = null;
		visibleFids = null;
		frequency = "1";
		products = null;
		reportName = "";
		selectedProducts = null;
	}




	/**
	 * @return the products
	 */
	public List<ProductDTO> getProducts() {
		return products;
	}




	/**
	 * @param products the products to set
	 */
	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}




	/**
	 * @return the selectedProducts
	 */
	public String[] getSelectedProducts() {
		return selectedProducts;
	}




	/**
	 * @param selectedProducts the selectedProducts to set
	 */
	public void setSelectedProducts(String[] selectedProducts) {
		this.selectedProducts = selectedProducts;
	}




	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}




	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}




	/**
	 * @return the visibleFids
	 */
	public List<FidDto> getVisibleFids() {
		return visibleFids;
	}




	/**
	 * @param visibleFids the visibleFids to set
	 */
	public void setVisibleFids(List<FidDto> visibleFids) {
		this.visibleFids = visibleFids;
	}




	/**
	 * @return the inVisibleFids
	 */
	public List<FidDto> getInVisibleFids() {
		return inVisibleFids;
	}




	/**
	 * @param inVisibleFids the inVisibleFids to set
	 */
	public void setInVisibleFids(List<FidDto> inVisibleFids) {
		this.inVisibleFids = inVisibleFids;
	}




	/**
	 * @return the selectedFids
	 */
	public String[] getSelectedFids() {
		return selectedFids;
	}




	/**
	 * @param selectedFids the selectedFids to set
	 */
	public void setSelectedFids(String[] selectedFids) {
		this.selectedFids = selectedFids;
	}




	/**
	 * @return the unselectedFids
	 */
	public String[] getUnselectedFids() {
		return unselectedFids;
	}




	/**
	 * @param unselectedFids the unselectedFids to set
	 */
	public void setUnselectedFids(String[] unselectedFids) {
		this.unselectedFids = unselectedFids;
	}




	/**
	 * @return the updatedFids
	 */
	public String[] getUpdatedFids() {
		return updatedFids;
	}




	/**
	 * @param updatedFids the updatedFids to set
	 */
	public void setUpdatedFids(String[] updatedFids) {
		this.updatedFids = updatedFids;
	}




	/**
	 * @return the actionType
	 */
	public String getActionType() {
		return actionType;
	}




	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}




	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}




	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}




	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}




	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}




	public String getMessage() {
		return message;
	}




	public void setMessage(String message) {
		this.message = message;
	}
}
