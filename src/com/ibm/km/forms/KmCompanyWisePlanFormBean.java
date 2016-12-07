package com.ibm.km.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

/** 
* @author Anveeksha & Neeraj 
* created for Waiver Matrix Upload
* for viewing Company Wise bill Plan details
**/ 

public class KmCompanyWisePlanFormBean extends ActionForm{

	
	private String methodName = null;
	private String companySearchValue ;
	private ArrayList compList ;
	private ArrayList planList;
	private int billPlanSearchCompId;
	private ArrayList billPlanRateDetail;
	private ArrayList companyDetailsList;
	private int checkForCompanyList = 0;
	private int checkForDetails = 0;
	
	
	public int getCheckForCompanyList() {
		return checkForCompanyList;
	}
	public void setCheckForCompanyList(int checkForCompanyList) {
		this.checkForCompanyList = checkForCompanyList;
	}
	public int getCheckForDetails() {
		return checkForDetails;
	}
	public void setCheckForDetails(int checkForDetails) {
		this.checkForDetails = checkForDetails;
	}
	public int getBillPlanSearchCompId() {
		return billPlanSearchCompId;
	}
	public void setBillPlanSearchCompId(int billPlanSearchCompId) {
		this.billPlanSearchCompId = billPlanSearchCompId;
	}
	public String getCompanySearchValue() {
		return companySearchValue;
	}
	public void setCompanySearchValue(String companySearchValue) {
		this.companySearchValue = companySearchValue.trim();
	}
	public ArrayList getCompList() {
		return compList;
	}
	public void setCompList(ArrayList compList) {
		this.compList = compList;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public ArrayList getPlanList() {
		return planList;
	}
	public void setPlanList(ArrayList planList) {
		this.planList = planList;
	}
	public ArrayList getBillPlanRateDetail() {
		return billPlanRateDetail;
	}
	public void setBillPlanRateDetail(ArrayList billPlanRateDetail) {
		this.billPlanRateDetail = billPlanRateDetail;
	}
	public ArrayList getCompanyDetailsList() {
		return companyDetailsList;
	}
	public void setCompanyDetailsList(ArrayList companyDetailsList) {
		this.companyDetailsList = companyDetailsList;
	}

	

}
