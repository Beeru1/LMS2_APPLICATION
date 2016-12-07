package com.ibm.lms.wf.forms;

import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.lms.dto.CircleDTO;
import com.ibm.lms.dto.LeadStatusDTO;
import com.ibm.lms.wf.dto.Leads;

public class LeadForm extends ActionForm{

	private String methodName;
	private List assignedLeads;
	private String message=null;
	private String leadID;
	private String actionType;
	private String forwardTo;
	private String closureComments;
	private String forwardComments;
	private FormFile newFile;
	private String isError;
	private String updatedBy;
	private String udId;
	private String leadId;
	private String msg;
	private String subStatusID;
	private String view;
	private String rsuCode;
	private String cafNumber;
	private String[] leadIds;
	private String startDate;
	private String endDate;
	private String zoneName; //changed by Sudhanshu
	private String cityZoneName; //changed by Sudhanshu
	private String  rental;//changed by Sudhanshu
	private String  onlineCafNumber;//changed by Sudhanshu
	private String  allocatedNumber;//changed by Sudhanshu
	
	private String  myOpId;//changed by Sudhanshu
	private String  paymentAmt;//changed by Sudhanshu
	private String  transRefNo;//changed by Sudhanshu
	private String 	rentalString; //changed by Sudhanshu
	private String 	remarks; //changed by Sudhanshu 
	private String  geoIpCity; //changed by Sudhanshu 
	
	private String  productType; //changed by Sudhanshu 
	private String  leadPriority; //changed by Sudhanshu 
	private String  lobId; //changed by Sudhanshu 
	
	private int  circleMstrId;//added by amarjeet
	private String param = "";//added by amarjeet
	private String prospect_mobile_number;
	

	private List<CircleDTO> circleList;
	private int statusId;
	private List<LeadStatusDTO> statusList;
	private String initStatus = "true";	
	private int  totalRec = 0;
	private int circleId;

	private String latitude;
	private String longitude;
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public int getTotalRec() {
		return totalRec;
	}

	public void setTotalRec(int totalRec) {
		this.totalRec = totalRec;
	}

	public String getInitStatus() {
		return initStatus;
	}

	public void setInitStatus(String initStatus) {
		this.initStatus = initStatus;
	}

	public List<LeadStatusDTO> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<LeadStatusDTO> statusList) {
		this.statusList = statusList;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public List<CircleDTO> getCircleList() {
		return circleList;
	}

	public void setCircleList(List<CircleDTO> circleList) {
		this.circleList = circleList;
	}

	public int getCircleMstrId() {
		return circleMstrId;
	}

	public void setCircleMstrId(int circleMstrId) {
		this.circleMstrId = circleMstrId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUdId() {
		return udId;
	}

	public void setUdId(String udId) {
		this.udId = udId;
	}
	public String[] getLeadIds() {
		return leadIds;
	}

	public void setLeadIds(String[] leadIds) {
		this.leadIds = leadIds;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCafNumber() {
		return cafNumber;
	}

	public void setCafNumber(String cafNumber) {
		this.cafNumber = cafNumber;
	}

	public String getRsuCode() {
		return rsuCode;
	}

	public void setRsuCode(String rsuCode) {
		this.rsuCode = rsuCode;
	}
	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getSubStatusID() {
		return subStatusID;
	}

	public void setSubStatusID(String subStatusID) {
		this.subStatusID = subStatusID;
	}

	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public FormFile getNewFile() {
		return newFile;
	}

	public void setNewFile(FormFile newFile) {
		this.newFile = newFile;
	}

	
	public String getIsError() {
		return isError;
	}

	public void setIsError(String isError) {
		this.isError = isError;
	}

	public String getForwardTo() {
		return forwardTo;
	}

	public void setForwardTo(String forwardTo) {
		this.forwardTo = forwardTo;
	}

	public String getClosureComments() {
		return closureComments;
	}

	public void setClosureComments(String closureComments) {
		this.closureComments = closureComments;
	}

	public String getForwardComments() {
		return forwardComments;
	}

	public void setForwardComments(String forwardComments) {
		this.forwardComments = forwardComments;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getLeadID() {
		return leadID;
	}

	public void setLeadID(String leadID) {
		this.leadID = leadID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List getAssignedLeads() {
		return assignedLeads;
	}

	public void setAssignedLeads(List assignedLeads) {
		this.assignedLeads = assignedLeads;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public void reset()
	{
		
		assignedLeads = null;
		message="";
		leadID = "";
		actionType = "";
		forwardTo = "";
		closureComments = "";
		forwardComments = "";
		isError= "";
		updatedBy = "";
		subStatusID ="";
		rsuCode = "";
		cafNumber = "";
		leadIds = null;
		startDate = null;
		endDate = null;
		prospect_mobile_number =null;
		circleId=0;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getCityZoneName() {
		return cityZoneName;
	}

	public void setCityZoneName(String cityZoneName) {
		this.cityZoneName = cityZoneName;
	}

	public String getRental() {
		return rental;
	}

	public void setRental(String rental) {
		this.rental = rental;
	}

	public String getOnlineCafNumber() {
		return onlineCafNumber;
	}

	public void setOnlineCafNumber(String onlineCafNumber) {
		this.onlineCafNumber = onlineCafNumber;
	}

	public String getAllocatedNumber() {
		return allocatedNumber;
	}

	public void setAllocatedNumber(String allocatedNumber) {
		this.allocatedNumber = allocatedNumber;
	}

	public String getMyOpId() {
		return myOpId;
	}

	public void setMyOpId(String myOpId) {
		this.myOpId = myOpId;
	}

	public String getPaymentAmt() {
		return paymentAmt;
	}

	public void setPaymentAmt(String paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	public String getTransRefNo() {
		return transRefNo;
	}

	public void setTransRefNo(String transRefNo) {
		this.transRefNo = transRefNo;
	}

	public String getRentalString() {
		return rentalString;
	}

	public void setRentalString(String rentalString) {
		this.rentalString = rentalString;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getGeoIpCity() {
		return geoIpCity;
	}

	public void setGeoIpCity(String geoIpCity) {
		this.geoIpCity = geoIpCity;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getLeadPriority() {
		return leadPriority;
	}

	public void setLeadPriority(String leadPriority) {
		this.leadPriority = leadPriority;
	}

	public String getLobId() {
		return lobId;
	}

	public void setLobId(String lobId) {
		this.lobId = lobId;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getProspect_mobile_number() {
		return prospect_mobile_number;
	}

	public void setProspect_mobile_number(String prospect_mobile_number) {
		this.prospect_mobile_number = prospect_mobile_number;
	}

	public int getCircleId() {
		return circleId;
	}

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	

}
