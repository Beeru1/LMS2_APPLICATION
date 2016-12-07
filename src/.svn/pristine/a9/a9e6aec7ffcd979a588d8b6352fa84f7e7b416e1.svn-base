package com.ibm.lms.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.lms.dto.CircleDTO;
import com.ibm.lms.dto.DashBoardReportDTO;
import com.ibm.lms.dto.LeadStatusDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.dto.ZoneDTO;

public class ReportsFormBean extends ActionForm {
	
	/**
	 * @author Parnika Sharma
	 */
	private static final long serialVersionUID = 1L;
	
	private int reportId=0;	
	private String reportName="";
	private String reportDate="";
	private String reportinitStatus="false";
	private String reportflag="";
	private String methodName="";
	private String message="";
	private int selectedReportId=0;	
	private ArrayList reportTypeList = null;
	private String  lobId =null; //changed by Sudhanshu 
	private String reportTime="";//Added by satish
	private int  circleMstrId =0;//added by amarjeet
	private List<CircleDTO> circleList;
	private int statusId=0;
	private List<LeadStatusDTO> statusList;
	private String initStatus = "true";	
	private String startDate;
	private String date;
	private String endDate;
	private ArrayList lobList = null;
	private String lobName ;
	
	private String chPartnerId="";	
	private ArrayList<ZoneDTO> zoneList = null;
    private String zoneName=null;
    private int zoneId;
    private int selectedZoneId; 
    private String zoneCode=null;
    private String zoneFlag=null;    
    private String param = "";//added by amarjeet
    private String userActorId;//added by amarjeet
    private String reportType="";
    
    /* Added By Parnika on 20 May 2014 */
    private String dispositionCount="";
    private String dispositionCode="";
    private String dispositionDate="";
    private String leadAssignmentDate="";
    private String selectType ="";
    private String circleName="";
    
    /* End of changes By parnika */
    

    
    //added by satish
    private String ftdreportdetailstatus="false";
    
    private String headerid="";
    

    private String userloginid="";
    
    //end
    
	public String getDate() {
		return date;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getDispositionCount() {
		return dispositionCount;
	}
	public void setDispositionCount(String dispositionCount) {
		this.dispositionCount = dispositionCount;
	}
	public String getDispositionCode() {
		return dispositionCode;
	}
	public void setDispositionCode(String dispositionCode) {
		this.dispositionCode = dispositionCode;
	}
	public String getDispositionDate() {
		return dispositionDate;
	}
	public void setDispositionDate(String dispositionDate) {
		this.dispositionDate = dispositionDate;
	}
	public String getLeadAssignmentDate() {
		return leadAssignmentDate;
	}
	public void setLeadAssignmentDate(String leadAssignmentDate) {
		this.leadAssignmentDate = leadAssignmentDate;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	private ArrayList<DashBoardReportDTO> dashList = null;
    
	
	public ArrayList<DashBoardReportDTO> getDashList() {
		return dashList;
	}
	public void setDashList(ArrayList<DashBoardReportDTO> dashList) {
		this.dashList = dashList;
	}
	public String getUserActorId() {
		return userActorId;
	}
	public void setUserActorId(String userActorId) {
		this.userActorId = userActorId;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getZoneFlag() {
		return zoneFlag;
	}
	public void setZoneFlag(String zoneFlag) {
		this.zoneFlag = zoneFlag;
	}
	public int getSelectedZoneId() {
		return selectedZoneId;
	}
	public void setSelectedZoneId(int selectedZoneId) {
		this.selectedZoneId = selectedZoneId;
	}
	public String getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public int getZoneId() {
		return zoneId;
	}
	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public ArrayList<ZoneDTO> getZoneList() {
		return zoneList;
	}
	public void setZoneList(ArrayList<ZoneDTO> zoneList) {
		this.zoneList = zoneList;
	}
	public String getChPartnerId() {
		return chPartnerId;
	}
	public void setChPartnerId(String chPartnerId) {
		this.chPartnerId = chPartnerId;
	}
	private String chPartnerName="";
	private List<UserMstr> partnerList;
	
	
	
	public String getChPartnerName() {
		return chPartnerName;
	}
	public void setChPartnerName(String chPartnerName) {
		this.chPartnerName = chPartnerName;
	}
	public List<UserMstr> getPartnerList() {
		return partnerList;
	}
	public void setPartnerList(List<UserMstr> partnerList) {
		this.partnerList = partnerList;
	}
	
	public ArrayList getLobList() {
		return lobList;
	}
	public void setLobList(ArrayList lobList) {
		this.lobList = lobList;
	}
	public String getLobName() {
		return lobName;
	}
	public void setLobName(String lobName) {
		this.lobName = lobName;
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
	public String getLobId() {
		return lobId;
	}
	public void setLobId(String lobId) {
		this.lobId = lobId;
	}
	public int getCircleMstrId() {
		return circleMstrId;
	}
	public void setCircleMstrId(int circleMstrId) {
		this.circleMstrId = circleMstrId;
	}
	public List<CircleDTO> getCircleList() {
		return circleList;
	}
	public void setCircleList(List<CircleDTO> circleList) {
		this.circleList = circleList;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public List<LeadStatusDTO> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<LeadStatusDTO> statusList) {
		this.statusList = statusList;
	}
	public String getInitStatus() {
		return initStatus;
	}
	public void setInitStatus(String initStatus) {
		this.initStatus = initStatus;
	}
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getSelectedReportId() {
		return selectedReportId;
	}
	public void setSelectedReportId(int selectedReportId) {
		this.selectedReportId = selectedReportId;
	}
	public ArrayList getReportTypeList() {
		return reportTypeList;
	}
	public void setReportTypeList(ArrayList reportTypeList) {
		this.reportTypeList = reportTypeList;
	}
	public String getSelectType() {
		return selectType;
	}
	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}
	public String getReportTime() {
		return reportTime;
	}
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	public String getReportinitStatus() {
		return reportinitStatus;
	}
	public void setReportinitStatus(String reportinitStatus) {
		this.reportinitStatus = reportinitStatus;
	}
	public String getReportflag() {
		return reportflag;
	}
	public void setReportflag(String reportflag) {
		this.reportflag = reportflag;
	}
	
	public String getHeaderid() {
		return headerid;
	}
	public void setHeaderid(String headerid) {
		this.headerid = headerid;
	}
	public String getUserloginid() {
		return userloginid;
	}
	public void setUserloginid(String userloginid) {
		this.userloginid = userloginid;
	}
	public String getFtdreportdetailstatus() {
		return ftdreportdetailstatus;
	}
	public void setFtdreportdetailstatus(String ftdreportdetailstatus) {
		this.ftdreportdetailstatus = ftdreportdetailstatus;
	}
	
	
	
	
	

}
