package com.ibm.km.dto;

import java.sql.Date;
import java.util.ArrayList;

public class KmBPUploadDto {
	
	private String headerId = null;
	private String headerName = null;
	private String categoryId = null;
	private String content = null;
	private ArrayList<String> headerList = null;
	private ArrayList<String> contentList = null;
	private Date fromDate = null;
	private Date toDate = null;
	private String fromDt = null;
	private String toDt = null;
	private String topic = null;
	private int totalHeaders ;
	private String contentPath = null;
	private String documentId = null;
	private String path = null;
	private String circle = null;
	private String circleId = null;
	private String circles[] = null;
	
	public String[] getCircles() {
		return circles;
	}
	public void setCircles(String[] circles) {
		this.circles = circles;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public String getFromDt() {
		return fromDt;
	}
	public void setFromDt(String fromDt) {
		this.fromDt = fromDt;
	}
	public String getToDt() {
		return toDt;
	}
	public void setToDt(String toDt) {
		this.toDt = toDt;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getContentPath() {
		return contentPath;
	}
	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}
	public int getTotalHeaders() {
		return totalHeaders;
	}
	public void setTotalHeaders(int totalHeaders) {
		this.totalHeaders = totalHeaders;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}

	
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public ArrayList<String> getHeaderList() {
		return headerList;
	}
	public void setHeaderList(ArrayList<String> headerList) {
		this.headerList = headerList;
	}
	public ArrayList<String> getContentList() {
		return contentList;
	}
	public void setContentList(ArrayList<String> contentList) {
		this.contentList = contentList;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHeaderId() {
		return headerId;
	}
	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}
	public String getHeaderName() {
		return headerName;
	}
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	

}
