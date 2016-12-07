package com.ibm.lms.engine.dataobjects;

import com.ibm.lms.engine.intefaces.BatchConstants;

public class LeadToBeAssignedDO {

	private String leadID;
	private String productID;
	private String circleCd;
	private String stateCd;
	private String cityCd;
	private String zoneCd;
	private String subZoneCd;
	private String pinCode;
	
	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getCircleCd() {
		return circleCd;
	}

	public void setCircleCd(String circleCd) {
		this.circleCd = circleCd;
	}

	public String getStateCd() {
		return stateCd;
	}

	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}

	public String getCityCd() {
		return cityCd;
	}

	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
	}

	public String getZoneCd() {
		return zoneCd;
	}

	public void setZoneCd(String zoneCd) {
		this.zoneCd = zoneCd;
	}

	public String getSubZoneCd() {
		return subZoneCd;
	}

	public void setSubZoneCd(String subZoneCd) {
		this.subZoneCd = subZoneCd;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getLeadID() {
		return leadID;
	}

	public void setLeadID(String leadID) {
		this.leadID = leadID;
	}
	
	public String toString(){
		StringBuilder strBuilder = new StringBuilder(); 
		
		if(null != circleCd && !(BatchConstants.BLANK).equals(circleCd.trim())){
			strBuilder.append(circleCd);
		}
		
		if(null != stateCd && !(BatchConstants.BLANK).equals(stateCd.trim())){
			strBuilder.append(BatchConstants.SEPERATOR);
			strBuilder.append(stateCd);
		}
		
		if(null != cityCd && !(BatchConstants.BLANK).equals(cityCd.trim())){
			strBuilder.append(BatchConstants.SEPERATOR);
			strBuilder.append(cityCd);
		}
		
		if(null != zoneCd && !(BatchConstants.BLANK).equals(zoneCd.trim())){
			strBuilder.append(BatchConstants.SEPERATOR);
			strBuilder.append(zoneCd);
		}
		
		if(null != subZoneCd && !(BatchConstants.BLANK).equals(subZoneCd.trim())){
			strBuilder.append(BatchConstants.SEPERATOR);
			strBuilder.append(subZoneCd);
		}
		
		if(null != pinCode && !(BatchConstants.BLANK).equals(pinCode.trim())){
			strBuilder.append(BatchConstants.SEPERATOR);
			strBuilder.append(pinCode);
		}
		
		return strBuilder.toString();
	}
	
	
	
}
