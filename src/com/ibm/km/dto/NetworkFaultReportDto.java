package com.ibm.km.dto;

public class NetworkFaultReportDto {
	
	
	private String date=null;
	 private String kmActorId=null;
	 private String initialSelectBox;
	 private String initialLevel;
	 private String levelCount;
	 private String elementLevel;
	 private String initStatus="true";
	 private String elementId="";
	 private String parentId="";
	 private String reportType=null;
	 
	private String methodName=null;

	private String  time=null;
	
	
	
	
	
	
	
	
	
	 
	 
	private int faultId;
	private String circleName=null;
	
	private int circleId;
	private String affectedArea = null;
	private int tatHours;
	private int tatMinutes;
	private String faultDescription=null;
	private String reportedDate=null;
	private String reportedTime=null;
	public String getReportedTime() {
		return reportedTime;
	}
	public void setReportedTime(String reportedTime) {
		this.reportedTime = reportedTime;
	}

	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public int getFaultId() {
		return faultId;
	}
	public void setFaultId(int faultId) {
		this.faultId = faultId;
	}
	public String getReportedDate() {
		return reportedDate;
	}
	public void setReportedDate(String reportedDate) {
		this.reportedDate = reportedDate;
	}
	public int getTatHours() {
		return tatHours;
	}
	public void setTatHours(int tatHours) {
		this.tatHours = tatHours;
	}
	public int getTatMinutes() {
		return tatMinutes;
	}
	public void setTatMinutes(int tatMinutes) {
		this.tatMinutes = tatMinutes;
	}
	public String getFaultDescription() {
		return faultDescription;
	}
	public void setFaultDescription(String faultDescription) {
		this.faultDescription = faultDescription;
	}
	public int getCircleId() {
		return circleId;
	}
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}
	public String getAffectedArea() {
		return affectedArea;
	}
	public void setAffectedArea(String affectedArea) {
		this.affectedArea = affectedArea;
	}


	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getKmActorId() {
		return kmActorId;
	}
	public void setKmActorId(String kmActorId) {
		this.kmActorId = kmActorId;
	}
	public String getElementLevel() {
		return elementLevel;
	}
	public void setElementLevel(String elementLevel) {
		this.elementLevel = elementLevel;
	}
	public String getInitialLevel() {
		return initialLevel;
	}
	public void setInitialLevel(String initialLevel) {
		this.initialLevel = initialLevel;
	}
	public String getInitialSelectBox() {
		return initialSelectBox;
	}
	public void setInitialSelectBox(String initialSelectBox) {
		this.initialSelectBox = initialSelectBox;
	}
	public String getInitStatus() {
		return initStatus;
	}
	public void setInitStatus(String initStatus) {
		this.initStatus = initStatus;
	}
	public String getLevelCount() {
		return levelCount;
	}
	public void setLevelCount(String levelCount) {
		this.levelCount = levelCount;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

}
