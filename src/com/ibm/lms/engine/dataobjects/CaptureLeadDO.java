package com.ibm.lms.engine.dataobjects;

import com.ibm.lms.common.Constants;

public class CaptureLeadDO {
	
	/* Do not declare any varible with Underscore (_) */
	
			private String prospectsName="";
			private String prospectsMobileNumber="";
			private String alternateContactNumber="";
			private String address="";
			private String city="";
			private String pinecode="";
			private String circle="";
			private String product="";
			private String email="";
			private String fid="";
			private String tid="";
			private String fromPage="";
			private String referPage="";
			private String referUrl="";
			private String company="";
			private String service="";
			private String isCustomer="";
			private String plan="";
			private String extraParametrs="";
			private String keyWord="";
			private String campaign="";
			private String utmLabels="";
			
			/* Added By Parnika for LMS Phase 2 */
		     
			private String zoneCode = "";
			private String cityZoneCode = "";
			private String rental = "";
			private String tag = "";
			private String onlineCafNo = "";
			private String tranRefno = "";
			private String pytAmt = "";
			private String qualLeadParam = "";
			private String adParameter = "";
			private String leadSubmitTime = "";
			private String extraParams2 = "";
			private String extraParams3 = "";
			private String allocatedNo = "";
			private String myopId = "";
			private String geoIpCity = "";
			
			/* End of changes By Parnika */
	
	@Override
	public String toString() {
		/*String str = "\n"+ prospectsName+"~"+prospectsMobileNumber+"~"+alternateContactNumber+"~"+landlineNumber+"~"+
				address1+"~"+address2+"~"+city+"~"+pinecode+"~"+state+"~"+circle+"~"+maritialStatus+"~"+source+"~"+
				subSource+"~"+requestType+"~"+preperredAppointmentTime+"~"+opportunityTime+"~"+product+"~"+email+"~"+
				primaryLanguage+"~"+subZone+"~"+zone+"~"+fid+"~"+cid+"~"+fromPage+"~"+productType+"~"+referPage+"~"+
				referUrl+"~"+company+"~"+service+"~"+isCustomer+"~"+plan+"~"+extraParametrs+"~"+udid+"~"+opparam+"~"+
				agencyId+"~";*/
		
		String str = "\n"+ prospectsName+"~"+prospectsMobileNumber+"~"+alternateContactNumber+"~"+
		address+"~"+city+"~"+pinecode+"~"+circle+"~"+
		product+"~"+email+"~"+
		fid+"~"+tid+"~"+fromPage+"~"+referPage+"~"+
		referUrl+"~"+company+"~"+service+"~"+isCustomer+"~"+plan+"~"+extraParametrs+"~"+utmLabels+"~"+
		zoneCode+"~"+cityZoneCode+"~"+rental+"~"+tag+"~"+onlineCafNo+"~"+tranRefno+"~"+pytAmt+"~"+
		qualLeadParam+"~"+adParameter+"~"+leadSubmitTime+"~"+extraParams2+"~"+extraParams3+"~"+allocatedNo+"~"+myopId+"~";
		
		return str;
	}

	

	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		int size = Constants.LEAD_PROSPECT_CUSTOMER_ADDRESS_ONE_SIZE;
		if(address!=null && address.length() > size) 
			address = address.substring(0, size -1 );
		this.address = address;
	}



	public String getAlternateContactNumber() {
		return alternateContactNumber;
	}

	public void setAlternateContactNumber(String alternateContactNumber) {
		int size = Constants.ALTERNATECONTACT_NUMBER;
		if(alternateContactNumber!=null && alternateContactNumber.length() > size) 
			alternateContactNumber = alternateContactNumber.substring(0, size -1 );	
		this.alternateContactNumber = alternateContactNumber;
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		int size = Constants.OTHER_SIZE;
		if(city!=null && city.length() > size) 
			city = city.substring(0, size -1 );
		this.city = city;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		int size = Constants.COMPANY_SIZE;
		if(company!=null && company.length() > size) 
			company = company.substring(0, size -1 );
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		int size = Constants.PROSPECT_SIZE;
		if(email!=null && email.length() > size) 
			email = email.substring(0, size -1 );
		this.email = email;
	}

	public String getExtraParametrs() {
		return extraParametrs;
	}

	public void setExtraParametrs(String extraParametrs) {
		int size = 50;
		if(extraParametrs!=null && extraParametrs.length() > size) 
			extraParametrs = extraParametrs.substring(0, size -1 );
		this.extraParametrs = extraParametrs;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		int size = Constants.ID_SIZE;
		if(fid!=null && fid.length() > size) 
			fid = fid.substring(0, size -1 );
		this.fid = fid;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		int size = Constants.GOOGLE_SIZE;
		if(fromPage!=null && fromPage.length() > size) 
			fromPage = fromPage.substring(0, size -1 );
		this.fromPage = fromPage;
	}

	public String getIsCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(String isCustomer) {
		int size = 1;
		if(isCustomer!=null && isCustomer.length() > size) 
			isCustomer = isCustomer.substring(0, size );
		this.isCustomer = isCustomer;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		int size = 255;
		if(keyWord!=null && keyWord.length() > size) 
			keyWord = keyWord.substring(0, size-1 );
		this.keyWord = keyWord;
	}

	public String getPinecode() {
		return pinecode;
	}

	public void setPinecode(String pinecode) {
		this.pinecode = pinecode;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		int size = 255;
		if(plan!=null && plan.length() > size) 
			plan = plan.substring(0, size-1 );
		this.plan = plan;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		int size = Constants.PRODUCT_SIZE;
		if(product!=null && product.length() > size) 
			product = product.substring(0, size-1 );
		this.product = product;
	}

	public String getProspectsMobileNumber() {
		return prospectsMobileNumber;
	}

	public void setProspectsMobileNumber(String prospectsMobileNumber) {
		this.prospectsMobileNumber = prospectsMobileNumber;
	}

	public String getProspectsName() {
		return prospectsName;
	}

	public void setProspectsName(String prospectsName) {
		int size = Constants.PROSPECT_SIZE;
		if(prospectsName!=null && prospectsName.length() > size) 
			prospectsName = prospectsName.substring(0, size-1 );
		this.prospectsName = prospectsName;
	}

	public String getReferPage() {
		return referPage;
	}

	public void setReferPage(String referPage) {
		int size = Constants.GOOGLE_SIZE;
		if(referPage!=null && referPage.length() > size) 
			referPage = referPage.substring(0, size-1 );
		this.referPage = referPage;
	}

	public String getReferUrl() {
		return referUrl;
	}

	public void setReferUrl(String referUrl) {
		int size = Constants.GOOGLE_SIZE;
		if(referUrl!=null && referUrl.length() > size) 
			referUrl = referUrl.substring(0, size-1 );
		this.referUrl = referUrl;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		int size = Constants.GOOGLE_SIZE;
		if(service!=null && service.length() > size) 
			service = service.substring(0, size-1 );
		this.service = service;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		int size = Constants.ID_SIZE;
		if(tid!=null && tid.length() > size) 
			tid = tid.substring(0, size-1 );
		this.tid = tid;
	}



	public String getUtmLabels() {
		return utmLabels;
	}



	public void setUtmLabels(String utmLabels) {
		int size = Constants.GOOGLE_SIZE;
		if(utmLabels!=null && utmLabels.length() > size) 
			utmLabels = utmLabels.substring(0, size-1 );
		this.utmLabels = utmLabels;
	}



	public String getZoneCode() {
		return zoneCode;
	}



	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}



	public String getCityZoneCode() {
		return cityZoneCode;
	}



	public void setCityZoneCode(String cityZoneCode) {
		this.cityZoneCode = cityZoneCode;
	}



	public String getRental() {
		return rental;
	}



	public void setRental(String rental) {
		int size = Constants.RENTAL_SIZE;
		if(rental!=null && rental.length() > size) 
			rental = rental.substring(0, size-1 );
		this.rental = rental;
	}



	public String getTag() {
		return tag;
	}



	public void setTag(String tag) {
		int size = Constants.PROSPECT_SIZE;
		if(tag!=null && tag.length() > size) 
			tag = tag.substring(0, size-1 );
		this.tag = tag;
	}



	public String getOnlineCafNo() {
		return onlineCafNo;
	}



	public void setOnlineCafNo(String onlineCafNo) {
		int size = Constants.PROSPECT_SIZE;
		if(onlineCafNo!=null && onlineCafNo.length() > size) 
			onlineCafNo = onlineCafNo.substring(0, size-1 );
		this.onlineCafNo = onlineCafNo;
	}



	public String getTranRefno() {
		return tranRefno;
	}



	public void setTranRefno(String tranRefno) {
		int size = Constants.PROSPECT_SIZE;
		if(tranRefno!=null && tranRefno.length() > size) 
			tranRefno = tranRefno.substring(0, size-1 );
		this.tranRefno = tranRefno;
	}



	public String getPytAmt() {
		return pytAmt;
	}



	public void setPytAmt(String pytAmt) {
		int size = Constants.OTHER_SIZE;
		if(pytAmt!=null && pytAmt.length() > size) 
			pytAmt = pytAmt.substring(0, size-1 );
		this.pytAmt = pytAmt;
	}



	public String getQualLeadParam() {
		return qualLeadParam;
	}



	public void setQualLeadParam(String qualLeadParam) {
		int size = 10;
		if(qualLeadParam!=null && qualLeadParam.length() > size) 
			qualLeadParam = qualLeadParam.substring(0, size-1 );
		this.qualLeadParam = qualLeadParam;
	}



	public String getAdParameter() {
		return adParameter;
	}



	public void setAdParameter(String adParameter) {
		int size = Constants.PROSPECT_SIZE;
		if(adParameter!=null && adParameter.length() > size) 
			adParameter = adParameter.substring(0, size-1 );
		this.adParameter = adParameter;
	}



	public String getLeadSubmitTime() {
		return leadSubmitTime;
	}



	public void setLeadSubmitTime(String leadSubmitTime) {
		int size = Constants.PROSPECT_SIZE;
		if(leadSubmitTime!=null && leadSubmitTime.length() > size) 
			leadSubmitTime = leadSubmitTime.substring(0, size-1 );
		this.leadSubmitTime = leadSubmitTime;
	}



	public String getExtraParams2() {
		return extraParams2;
	}



	public void setExtraParams2(String extraParams2) {
		int size = Constants.OTHER_SIZE;
		if(extraParams2!=null && extraParams2.length() > size) 
			extraParams2 = extraParams2.substring(0, size-1 );
		this.extraParams2 = extraParams2;
	}



	public String getExtraParams3() {
		return extraParams3;
	}



	public void setExtraParams3(String extraParams3) {
		int size = Constants.OTHER_SIZE;
		if(extraParams3!=null && extraParams3.length() > size) 
			extraParams3 = extraParams3.substring(0, size-1 );
		this.extraParams3 = extraParams3;
	}



	public String getAllocatedNo() {
		return allocatedNo;
	}



	public void setAllocatedNo(String allocatedNo) {
		int size = 20;
		if(allocatedNo!=null && allocatedNo.length() > size) 
			allocatedNo = allocatedNo.substring(0, size-1 );
		this.allocatedNo = allocatedNo;
	}



	public String getMyopId() {
		return myopId;
	}



	public void setMyopId(String myopId) {
		int size = Constants.RENTAL_SIZE;
		if(myopId!=null && myopId.length() > size) 
			myopId = myopId.substring(0, size-1 );
		this.myopId = myopId;
	}



	public String getGeoIpCity() {
		return geoIpCity;
	}



	public void setGeoIpCity(String geoIpCity) {
		int size = 50;
		if(geoIpCity!=null && geoIpCity.length() > size) 
			geoIpCity = geoIpCity.substring(0, size-1 );
		this.geoIpCity = geoIpCity;
	}


	
	
	

}
