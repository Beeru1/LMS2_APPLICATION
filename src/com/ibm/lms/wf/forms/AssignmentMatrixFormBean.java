package com.ibm.lms.wf.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class AssignmentMatrixFormBean extends ActionForm{

	private String olmID = "";
	private String createdBy = "";
	private String cityCode = "";
	private String pinCode = "";
	private String zoneCode = "";
	private String levelID;
	private int circleId ;
	private String requestType = "";
	private String rsuCode = "";
	private int productLobID ;
	private String otherIDs= "";
	private Boolean isError;
	private String msg;
	private boolean assignment=true;
	private String remarks="";
	private String ipaddress = "";
	
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	private String productId="";
	private String cityZoneCode="";
	private String L1approver;
	private String L2Approver;
	private String DRList;
	private String view="";
	private String param="";
	private List olmIDs;
	
	
/* Added By Parnika For LMS Phase 2 */
	
	public List getOlmIDs() {
		return olmIDs;
	}
	public void setOlmIDs(List olmIDs) {
		this.olmIDs = olmIDs;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getL1approver() {
		return L1approver;
	}
	public void setL1approver(String l1approver) {
		L1approver = l1approver;
	}
	public String getL2Approver() {
		return L2Approver;
	}
	public void setL2Approver(String approver) {
		L2Approver = approver;
	}
	public String getDRList() {
		return DRList;
	}
	public void setDRList(String list) {
		DRList = list;
	}
	private String productName;
	private int selectedProductId;
	private ArrayList productNameList = null;
	private int circleMstrId ;	
	
	private String zoneName;
	private ArrayList zoneList = null;
	
	private String cityName;
	private ArrayList cityList = null;
	
	private String cityZoneName;
	private ArrayList cityZoneList = null;
	private ArrayList rsuList = null;	
	
	
	private String levellCC = null;
	public int getSelectedRequestCategoryId() {
		return selectedRequestCategoryId;
	}
	public void setSelectedRequestCategoryId(int selectedRequestCategoryId) {
		this.selectedRequestCategoryId = selectedRequestCategoryId;
	}
	private String level2CC = null;
	private String level3CC =null;
	private String level4CC=null;
	
	private String requestCategoryId;
	private int selectedRequestCategoryId;
	private String requestCategoryName=null;
	private ArrayList requestCategoryList = null;
	
	/* End of changes by Parnika */
	
	public String getMsg() {
		return msg;
	}
	public String getRequestCategoryId() {
		return requestCategoryId;
	}
	public void setRequestCategoryId(String requestCategoryId) {
		this.requestCategoryId = requestCategoryId;
	}
	public String getRequestCategoryName() {
		return requestCategoryName;
	}
	public void setRequestCategoryName(String requestCategoryName) {
		this.requestCategoryName = requestCategoryName;
	}
	public ArrayList getRequestCategoryList() {
		return requestCategoryList;
	}
	public void setRequestCategoryList(ArrayList requestCategoryList) {
		this.requestCategoryList = requestCategoryList;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getSelectedProductId() {
		return selectedProductId;
	}
	public void setSelectedProductId(int selectedProductId) {
		this.selectedProductId = selectedProductId;
	}
	public ArrayList getProductNameList() {
		return productNameList;
	}
	public void setProductNameList(ArrayList productNameList) {
		this.productNameList = productNameList;
	}
	public int getCircleMstrId() {
		return circleMstrId;
	}
	public void setCircleMstrId(int circleMstrId) {
		this.circleMstrId = circleMstrId;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public ArrayList getZoneList() {
		return zoneList;
	}
	public void setZoneList(ArrayList zoneList) {
		this.zoneList = zoneList;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public ArrayList getCityList() {
		return cityList;
	}
	public void setCityList(ArrayList cityList) {
		this.cityList = cityList;
	}
	public String getCityZoneName() {
		return cityZoneName;
	}
	public void setCityZoneName(String cityZoneName) {
		this.cityZoneName = cityZoneName;
	}
	public ArrayList getCityZoneList() {
		return cityZoneList;
	}
	public void setCityZoneList(ArrayList cityZoneList) {
		this.cityZoneList = cityZoneList;
	}
	public ArrayList getRsuList() {
		return rsuList;
	}
	public void setRsuList(ArrayList rsuList) {
		this.rsuList = rsuList;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Boolean getIsError() {
		return isError;
	}
	public void setIsError(Boolean isError) {
		this.isError = isError;
	}
	public String getOlmID() {
		return olmID;
	}
	public void setOlmID(String olmID) {
		this.olmID = olmID;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	public String getLevelID() {
		return levelID;
	}
	public void setLevelID(String levelID) {
		this.levelID = levelID;
	}
	public int getCircleId() {
		return circleId;
	}
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getRsuCode() {
		return rsuCode;
	}
	public void setRsuCode(String rsuCode) {
		this.rsuCode = rsuCode;
	}
	public int getProductLobID() {
		return productLobID;
	}
	public void setProductLobID(int productLobID) {
		this.productLobID = productLobID;
	}
	public String getOtherIDs() {
		return otherIDs;
	}
	public void setOtherIDs(String otherIDs) {
		this.otherIDs = otherIDs;
	}
	public void reset()
	{
		olmID = "";
		productLobID = 0;
		productId = "";
		circleId = 0;
		createdBy = "";
		cityCode = "";
		pinCode = "";
		zoneCode = "";
		levelID = "";
		requestType = "";
		rsuCode = "";
		otherIDs= "";
		levellCC="";
		level2CC="";
		level3CC="";
		level4CC="";
	}
	public String getCityZoneCode() {
		return cityZoneCode;
	}
	public void setCityZoneCode(String cityZoneCode) {
		this.cityZoneCode = cityZoneCode;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public boolean isAssignment() {
		return assignment;
	}
	public void setAssignment(boolean assignment) {
		this.assignment = assignment;
	}
	public String getLevel2CC() {
		return level2CC;
	}
	public void setLevel2CC(String level2CC) {
		this.level2CC = level2CC;
	}
	public String getLevel3CC() {
		return level3CC;
	}
	public void setLevel3CC(String level3CC) {
		this.level3CC = level3CC;
	}
	public String getLevel4CC() {
		return level4CC;
	}
	public void setLevel4CC(String level4CC) {
		this.level4CC = level4CC;
	}
	public String getLevellCC() {
		return levellCC;
	}
	public void setLevellCC(String levellCC) {
		this.levellCC = levellCC;
	}
	
}
