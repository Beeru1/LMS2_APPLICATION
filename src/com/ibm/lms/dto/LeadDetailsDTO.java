package com.ibm.lms.dto;

public class LeadDetailsDTO {
	
	private int prospectId;
	private int leadProspectId;
	private long leadId;
	private String customerName="";
	private long contactNo;
	private String alternateContactNo="";	
	private int  productId;
	private int  productLobId;
	private String postalCode="";
	private int[]  productIds;
	private String productName="";
	private String ipAddress="";

	private String language="";
	private String email="";
	private String landlineNo="";
	private String existingCustomer="";
	private String address1="";
	private String address2="";
	private String cityCode="";
	private String cityName="";	
	private String pinCode="";
	private String stateCode;
	private String stateName="";
	private int    circleId;
	private int    circleMasterId;//Added by Amarjeet for LMS phase -2 
	private String circleName="";
	private int    requestType;
	private String requestTypeDesc="";
	private String remarks="";
	private int  leadProductId;
	private String appointmentDate="";
	private String appointmentHour="";
	private String appointmentMinute="";
	private String appointmentTime="";
	
	private String maritalStatus="";
	
	
	private int sourceId;
	private String sourceName="";
	private int subSourceId;
	private String subSourceName="";
	
	private String oppertunityTime="";
	
	
	private String requestMode="";
	private String createdBy="";
	
	
	private String zoneCode="";
	private String subZoneName="";
	private String rsuCode="";
	private int leadStatusId;
	
	private int leadSubStatusId;
	
	private String zoneName="";
	private String leadStatusName="";
	private String udId;
	private Boolean isSecondcall;
	
	private String transactionTime="";
	private String assignedPrimaryUser="";
	
	private String expectedCloserDate="";
	private String closerTime="";
	private String leadSubStatusName="";
	private String cityZoneCode="";//Added by Neetika for LMS phase -2 
	private String remoteAddress="";
	private String cityZoneName;// Added by praatp on 13 Dec 2013
	private String campaign="";
	private String geoIpCity="";
	private String planId="";
	private String rental="";
	private String payment="";
	private String allocatedNo="";
	private String transactionRefNo="";
	private String onlineCafNo="";
	private String leadCategory="";
	
	private int leadSubSubStatusid=0;
	private String leadSubSubStatus = "";
	public String getLeadSubSubStatus() {
		return leadSubSubStatus;
	}
	public void setLeadSubSubStatus(String leadSubSubStatus) {
		this.leadSubSubStatus = leadSubSubStatus;
	}
	private String leadSubStatus="";
	
	/* Added by Parnika */
	
	private String utmSource="";
	private String utmTerm="";
	private String utmMedium="";
	private String utmCampaign="";
	private String leadPriority = "";
	
	/* End of changes by Parnika */
	//added by amarjeet
	private String hlrNo = "";
	private String plan="";
	private String rentalType="";
	private String freebieCount="";
	private String boosterCount="";
	private String freebieTaken="";
	private String boosterTaken="";
	
	private String prepaidNumber="";
	private String pytAmt="";
	private String tranRefno="";
	private String offer="";
	private String downloadLimit="";
	private String deviceMrp="";
	private String voiceBenefit="";
	private String dataQuota="";
	private String userType="";
	private String devicetaken="";
	private String benefit="";
	private String pkgDuration="";
	private String qualLeadParam ="";
	private String leadSubmitTime ="";
	private String tid  ="";
	private String fid  ="";
	private String keyWord  ="";
	private String fromPage  ="";
	private String service  ="";
	private String feasibilityParam  ="";
	private String referUrl  ="";
	private String company  ="";
	private Boolean flag;
	private String referPage="";
	private String ndncDisclaimer="";
	private String state="";
	private String requestCategory="";
	private String requestCategoryId;
	private String requestCategoryName="";
	private String deviceTaken ="";
	private String extraParam1=""; ////sales executive no 
	private String caf="";
	
	
	
	
	private String userFname="";
	private String extraParam3=""; ////Sales Channel Code
	private String appointment_Status="";
	private String extraParam4="";
	private String totalDue="";
	private String taskStartTime="";
	private String taskEndTime="";
	private String salesChannelCode="" ; 
	private String amtdue="" ;
	
	
	private String productflag="";
	
	
	//Added by Pernica For Lead Search
	private String identityProofId="";
	private String relationName="";
	private String upc="";
	private String upcGenDate="";
	private String previousOperator="";
	private String previousCircle="";
	private String existingPart="";
	private String mnpStatus="";
	private String documentCollectedFlag="";
	private String planType="";
	private String customerSegment="";
	private String simNumber="";
	private String gender="";
	private String nationality="";
	private String identityProofType="";
	
	private String allStatusId="";
	private String allStatusName="";
	
	//Added by satish
	
	private String lcIsDone="";
	
	private String duplicateLeadStatus="";
	private String dirtyLeadStatus="";
	private String errorMessage="";
	private String mobileNo="";
	
	
	
	//Added by satish
	
	public String getAllStatusId() {
		return allStatusId;
	}
	public void setAllStatusId(String allStatusId) {
		this.allStatusId = allStatusId;
	}
	public String getAllStatusName() {
		return allStatusName;
	}
	public void setAllStatusName(String allStatusName) {
		this.allStatusName = allStatusName;
	}
	public String getProductflag() {
		return productflag;
	}
	public void setProductflag(String productflag) {
		this.productflag = productflag;
	}
	public String getExtraParam4() {
		return extraParam4;
	}
	public void setExtraParam4(String extraParam4) {
		this.extraParam4 = extraParam4;
	}
	public String getTotalDue() {
		return totalDue;
	}
	public void setTotalDue(String totalDue) {
		this.totalDue = totalDue;
	}
	public String getTaskStartTime() {
		return taskStartTime;
	}
	public void setTaskStartTime(String taskStartTime) {
		this.taskStartTime = taskStartTime;
	}
	public String getTaskEndTime() {
		return taskEndTime;
	}
	public void setTaskEndTime(String taskEndTime) {
		this.taskEndTime = taskEndTime;
	}
	public String getAppointmentEndTime() {
		return appointmentEndTime;
	}
	public void setAppointmentEndTime(String appointmentEndTime) {
		this.appointmentEndTime = appointmentEndTime;
	}
	public String getAppointmentStatus() {
		return appointmentStatus;
	}
	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
	public String getAppointmentEndDate() {
		return appointmentEndDate;
	}
	public void setAppointmentEndDate(String appointmentEndDate) {
		this.appointmentEndDate = appointmentEndDate;
	}
	public String getAppointmentEndHour() {
		return appointmentEndHour;
	}
	public void setAppointmentEndHour(String appointmentEndHour) {
		this.appointmentEndHour = appointmentEndHour;
	}
	public String getAppointmentEndMinute() {
		return appointmentEndMinute;
	}
	public void setAppointmentEndMinute(String appointmentEndMinute) {
		this.appointmentEndMinute = appointmentEndMinute;
	}
	public String getAppointmentStartDate() {
		return appointmentStartDate;
	}
	public void setAppointmentStartDate(String appointmentStartDate) {
		this.appointmentStartDate = appointmentStartDate;
	}
	public String getAppointmentStartHour() {
		return appointmentStartHour;
	}
	public void setAppointmentStartHour(String appointmentStartHour) {
		this.appointmentStartHour = appointmentStartHour;
	}
	public String getAppointmentStartMinute() {
		return appointmentStartMinute;
	}
	public String getExtraParam6() {
		return extraParam6;
	}
	public void setExtraParam6(String extraParam6) {
		this.extraParam6 = extraParam6;
	}
	public void setAppointmentStartMinute(String appointmentStartMinute) {
		this.appointmentStartMinute = appointmentStartMinute;
	}
	private String appointmentEndTime="";
	private String appointmentStatus="";
	private String appointmentEndDate="";
	private String appointmentEndHour="";
	private String appointmentEndMinute="";
	private String appointmentStartDate="";
	private String appointmentStartHour="";
	private String appointmentStartMinute="";
	private String extraParam6="";
	private String extraParam5="";
	private String extraParam7="";
	
	
	
	public String getExtraParam7() {
		return extraParam7;
	}
	public void setExtraParam7(String extraParam7) {
		this.extraParam7 = extraParam7;
	}
	private String contact="";
	

	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getExtraParam5() {
		return extraParam5;
	}
	public void setExtraParam5(String extraParam5) {
		this.extraParam5 = extraParam5;
	}
	public String getAppointment_Status() {
		return appointment_Status;
	}
	public void setAppointment_Status(String appointment_Status) {
		this.appointment_Status = appointment_Status;
	}
	public String getExtraParam3() {
		return extraParam3;
	}
	public void setExtraParam3(String extraParam3) {
		this.extraParam3 = extraParam3;
	}
	public String getUserFname() {
		return userFname;
	}
	public void setUserFname(String userFname) {
		this.userFname = userFname;
	}
	public String getCaf() {
		return caf;
	}
	public void setCaf(String caf) {
		this.caf = caf;
	}
	public String getExtraParam1() {
		return extraParam1;
	}
	public void setExtraParam1(String extraParam1) {
		this.extraParam1 = extraParam1;
	}
	public String getRequestCategoryName() {
		return requestCategoryName;
	}
	public void setRequestCategoryName(String requestCategoryName) {
		this.requestCategoryName = requestCategoryName;
	}
	public String getNdncDisclaimer() {
		return ndncDisclaimer;
	}
	public String getRequestCategory() {
		return requestCategory;
	}
	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setNdncDisclaimer(String ndncDisclaimer) {
		this.ndncDisclaimer = ndncDisclaimer;
	}
	public String getReferPage() {
		return referPage;
	}
	public void setReferPage(String referPage) {
		this.referPage = referPage;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	private String utmLabels  ="";
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public String getRentalType() {
		return rentalType;
	}
	public void setRentalType(String rentalType) {
		this.rentalType = rentalType;
	}
	public String getFreebieCount() {
		return freebieCount;
	}
	public void setFreebieCount(String freebieCount) {
		this.freebieCount = freebieCount;
	}
	public String getBoosterCount() {
		return boosterCount;
	}
	public void setBoosterCount(String boosterCount) {
		this.boosterCount = boosterCount;
	}
	public String getFreebieTaken() {
		return freebieTaken;
	}
	public void setFreebieTaken(String freebieTaken) {
		this.freebieTaken = freebieTaken;
	}
	public String getBoosterTaken() {
		return boosterTaken;
	}
	public void setBoosterTaken(String boosterTaken) {
		this.boosterTaken = boosterTaken;
	}
	public String getPrepaidNumber() {
		return prepaidNumber;
	}
	public void setPrepaidNumber(String prepaidNumber) {
		this.prepaidNumber = prepaidNumber;
	}
	public String getPytAmt() {
		return pytAmt;
	}
	public void setPytAmt(String pytAmt) {
		this.pytAmt = pytAmt;
	}
	public String getTranRefno() {
		return tranRefno;
	}
	public void setTranRefno(String tranRefno) {
		this.tranRefno = tranRefno;
	}
	public String getOffer() {
		return offer;
	}
	public void setOffer(String offer) {
		this.offer = offer;
	}
	public String getDownloadLimit() {
		return downloadLimit;
	}
	public void setDownloadLimit(String downloadLimit) {
		this.downloadLimit = downloadLimit;
	}
	public String getDeviceMrp() {
		return deviceMrp;
	}
	public void setDeviceMrp(String deviceMrp) {
		this.deviceMrp = deviceMrp;
	}
	public String getVoiceBenefit() {
		return voiceBenefit;
	}
	public void setVoiceBenefit(String voiceBenefit) {
		this.voiceBenefit = voiceBenefit;
	}
	public String getDataQuota() {
		return dataQuota;
	}
	public void setDataQuota(String dataQuota) {
		this.dataQuota = dataQuota;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getDevicetaken() {
		return devicetaken;
	}
	public void setDevicetaken(String devicetaken) {
		this.devicetaken = devicetaken;
	}
	public String getBenefit() {
		return benefit;
	}
	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}
	public String getPkgDuration() {
		return pkgDuration;
	}
	public void setPkgDuration(String pkgDuration) {
		this.pkgDuration = pkgDuration;
	}
	public String getQualLeadParam() {
		return qualLeadParam;
	}
	public void setQualLeadParam(String qualLeadParam) {
		this.qualLeadParam = qualLeadParam;
	}
	public String getLeadSubmitTime() {
		return leadSubmitTime;
	}
	public void setLeadSubmitTime(String leadSubmitTime) {
		this.leadSubmitTime = leadSubmitTime;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getFromPage() {
		return fromPage;
	}
	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getFeasibilityParam() {
		return feasibilityParam;
	}
	public void setFeasibilityParam(String feasibilityParam) {
		this.feasibilityParam = feasibilityParam;
	}
	public String getReferUrl() {
		return referUrl;
	}
	public void setReferUrl(String referUrl) {
		this.referUrl = referUrl;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getUtmLabels() {
		return utmLabels;
	}
	public void setUtmLabels(String utmLabels) {
		this.utmLabels = utmLabels;
	}
	
	
	
	
	public String getHlrNo() {
		return hlrNo;
	}
	public void setHlrNo(String hlrNo) {
		this.hlrNo = hlrNo;
	}
	public String getLeadSubStatus() {
		return leadSubStatus;
	}
	public String getLeadPriority() {
		return leadPriority;
	}
	public void setLeadPriority(String leadPriority) {
		this.leadPriority = leadPriority;
	}
	public String getUtmSource() {
		return utmSource;
	}
	public void setUtmSource(String utmSource) {
		this.utmSource = utmSource;
	}
	public String getUtmTerm() {
		return utmTerm;
	}
	public void setUtmTerm(String utmTerm) {
		this.utmTerm = utmTerm;
	}
	public String getUtmMedium() {
		return utmMedium;
	}
	public void setUtmMedium(String utmMedium) {
		this.utmMedium = utmMedium;
	}
	public String getUtmCampaign() {
		return utmCampaign;
	}
	public void setUtmCampaign(String utmCampaign) {
		this.utmCampaign = utmCampaign;
	}
	public void setLeadSubStatus(String leadSubStatus) {
		this.leadSubStatus = leadSubStatus;
	}
	public int getLeadSubSubStatusid() {
		return leadSubSubStatusid;
	}
	public void setLeadSubSubStatusid(int leadSubSubStatusid) {
		this.leadSubSubStatusid = leadSubSubStatusid;
	}
	public String getLeadCategory() {
		return leadCategory;
	}
	public void setLeadCategory(String leadCategory) {
		this.leadCategory = leadCategory;
	}
	public String getOnlineCafNo() {
		return onlineCafNo;
	}
	public void setOnlineCafNo(String onlineCafNo) {
		this.onlineCafNo = onlineCafNo;
	}
	public String getTransactionRefNo() {
		return transactionRefNo;
	}
	public void setTransactionRefNo(String transactionRefNo) {
		this.transactionRefNo = transactionRefNo;
	}
	public String getAllocatedNo() {
		return allocatedNo;
	}
	public void setAllocatedNo(String allocatedNo) {
		this.allocatedNo = allocatedNo;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getRental() {
		return rental;
	}
	public void setRental(String rental) {
		this.rental = rental;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getGeoIpCity() {
		return geoIpCity;
	}
	public void setGeoIpCity(String geoIpCity) {
		this.geoIpCity = geoIpCity;
	}
	public String getCampaign() {
		return campaign;
	}
	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}
	public String getCityZoneName() {
		return cityZoneName;
	}
	public void setCityZoneName(String cityZoneName) {
		this.cityZoneName = cityZoneName;
	}
	public int getLeadProductId() {
		return leadProductId;
	}
	public void setLeadProductId(int leadProductId) {
		this.leadProductId = leadProductId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getLeadProspectId() {
		return leadProspectId;
	}
	public void setLeadProspectId(int leadProspectId) {
		this.leadProspectId = leadProspectId;
	}
	public int getCircleMasterId() {
		return circleMasterId;
	}
	public void setCircleMasterId(int circleMasterId) {
		this.circleMasterId = circleMasterId;
	}
	public String getRemoteAddress() {
		return remoteAddress;
	}
	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}
	public String getCityZoneCode() {
		return cityZoneCode;
	}
	public void setCityZoneCode(String cityZoneCode) {
		this.cityZoneCode = cityZoneCode;
	}
	public String getCloserTime() {
		return closerTime;
	}
	public void setCloserTime(String closerTime) {
		this.closerTime = closerTime;
	}
	public String getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	public String getAssignedPrimaryUser() {
		return assignedPrimaryUser;
	}
	public void setAssignedPrimaryUser(String assignedPrimaryUser) {
		this.assignedPrimaryUser = assignedPrimaryUser;
	}
	public String getExpectedCloserDate() {
		return expectedCloserDate;
	}
	public void setExpectedCloserDate(String expectedCloserDate) {
		this.expectedCloserDate = expectedCloserDate;
	}
	public String getLeadSubStatusName() {
		return leadSubStatusName;
	}
	public void setLeadSubStatusName(String leadSubStatusName) {
		this.leadSubStatusName = leadSubStatusName;
	}
	public Boolean getIsSecondcall() {
		return isSecondcall;
	}
	public void setIsSecondcall(Boolean isSecondcall) {
		this.isSecondcall = isSecondcall;
	}
	public String getUdId() {
		return udId;
	}
	public void setUdId(String udId) {
		this.udId = udId;
	}
	public int getProductLobId() {
		return productLobId;
	}
	public void setProductLobId(int productLobId) {
		this.productLobId = productLobId;
	}
	public int getLeadSubStatusId() {
		return leadSubStatusId;
	}
	public void setLeadSubStatusId(int leadSubStatusId) {
		this.leadSubStatusId = leadSubStatusId;
	}
	public String getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public String getAppointmentHour() {
		return appointmentHour;
	}
	public void setAppointmentHour(String appointmentHour) {
		this.appointmentHour = appointmentHour;
	}
	public String getAppointmentMinute() {
		return appointmentMinute;
	}
	public void setAppointmentMinute(String appointmentMinute) {
		this.appointmentMinute = appointmentMinute;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getRequestTypeDesc() {
		return requestTypeDesc;
	}
	public void setRequestTypeDesc(String requestTypeDesc) {
		this.requestTypeDesc = requestTypeDesc;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getLeadStatusName() {
		return leadStatusName;
	}
	public void setLeadStatusName(String leadStatusName) {
		this.leadStatusName = leadStatusName;
	}
	public String getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	public String getSubZoneName() {
		return subZoneName;
	}
	public void setSubZoneName(String subZoneName) {
		this.subZoneName = subZoneName;
	}
	public String getRsuCode() {
		return rsuCode;
	}
	public void setRsuCode(String rsuCode) {
		this.rsuCode = rsuCode;
	}
	public int getLeadStatusId() {
		return leadStatusId;
	}
	public void setLeadStatusId(int leadStatusId) {
		this.leadStatusId = leadStatusId;
	}
	public int[] getProductIds() {
		return productIds;
	}
	public void setProductIds(int[] productIds) {
		this.productIds = productIds;
	}
	public long getLeadId() {
		return leadId;
	}
	public void setLeadId(long leadId) {
		this.leadId = leadId;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public int getProspectId() {
		return prospectId;
	}
	public void setProspectId(int prospectId) {
		this.prospectId = prospectId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public long getContactNo() {
		return contactNo;
	}
	public void setContactNo(long contactNo) {
		this.contactNo = contactNo;
	}
	public String getAlternateContactNo() {
		return alternateContactNo;
	}
	public void setAlternateContactNo(String alternateContactNo) {
		this.alternateContactNo = alternateContactNo;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLandlineNo() {
		return landlineNo;
	}
	public void setLandlineNo(String landlineNo) {
		this.landlineNo = landlineNo;
	}
	public String getExistingCustomer() {
		return existingCustomer;
	}
	public void setExistingCustomer(String existingCustomer) {
		this.existingCustomer = existingCustomer;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	
	
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public int getCircleId() {
		return circleId;
	}
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public int getRequestType() {
		return requestType;
	}
	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	
	public int getSourceId() {
		return sourceId;
	}
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public int getSubSourceId() {
		return subSourceId;
	}
	public void setSubSourceId(int subSourceId) {
		this.subSourceId = subSourceId;
	}
	public String getSubSourceName() {
		return subSourceName;
	}
	public void setSubSourceName(String subSourceName) {
		this.subSourceName = subSourceName;
	}
	public String getOppertunityTime() {
		return oppertunityTime;
	}
	public void setOppertunityTime(String oppertunityTime) {
		this.oppertunityTime = oppertunityTime;
	}
		
	public String getRequestMode() {
		return requestMode;
	}
	public void setRequestMode(String requestMode) {
		this.requestMode = requestMode;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getDeviceTaken() {
		return deviceTaken;
	}
	public void setDeviceTaken(String deviceTaken) {
		this.deviceTaken = deviceTaken;
	}
	public String getSalesChannelCode() {
		return salesChannelCode;
	}
	public void setSalesChannelCode(String salesChannelCode) {
		this.salesChannelCode = salesChannelCode;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getRequestCategoryId() {
		return requestCategoryId;
	}
	public void setRequestCategoryId(String requestCategoryId) {
		this.requestCategoryId = requestCategoryId;
	}
	public String getIdentityProofId() {
		return identityProofId;
	}
	public void setIdentityProofId(String identityProofId) {
		this.identityProofId = identityProofId;
	}
	public String getRelationName() {
		return relationName;
	}
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	public String getUpc() {
		return upc;
	}
	public void setUpc(String upc) {
		this.upc = upc;
	}
	public String getUpcGenDate() {
		return upcGenDate;
	}
	public void setUpcGenDate(String upcGenDate) {
		this.upcGenDate = upcGenDate;
	}
	public String getPreviousOperator() {
		return previousOperator;
	}
	public void setPreviousOperator(String previousOperator) {
		this.previousOperator = previousOperator;
	}
	public String getPreviousCircle() {
		return previousCircle;
	}
	public void setPreviousCircle(String previousCircle) {
		this.previousCircle = previousCircle;
	}
	public String getExistingPart() {
		return existingPart;
	}
	public void setExistingPart(String existingPart) {
		this.existingPart = existingPart;
	}
	public String getMnpStatus() {
		return mnpStatus;
	}
	public void setMnpStatus(String mnpStatus) {
		this.mnpStatus = mnpStatus;
	}
	public String getDocumentCollectedFlag() {
		return documentCollectedFlag;
	}
	public void setDocumentCollectedFlag(String documentCollectedFlag) {
		this.documentCollectedFlag = documentCollectedFlag;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public String getCustomerSegment() {
		return customerSegment;
	}
	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}
	public String getSimNumber() {
		return simNumber;
	}
	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getIdentityProofType() {
		return identityProofType;
	}
	public void setIdentityProofType(String identityProofType) {
		this.identityProofType = identityProofType;
	}
	public String getLcIsDone() {
		return lcIsDone;
	}
	public void setLcIsDone(String lcIsDone) {
		this.lcIsDone = lcIsDone;
	}
	public String getDuplicateLeadStatus() {
		return duplicateLeadStatus;
	}
	public void setDuplicateLeadStatus(String duplicateLeadStatus) {
		this.duplicateLeadStatus = duplicateLeadStatus;
	}
	public String getDirtyLeadStatus() {
		return dirtyLeadStatus;
	}
	public void setDirtyLeadStatus(String dirtyLeadStatus) {
		this.dirtyLeadStatus = dirtyLeadStatus;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	
		
	
	
}