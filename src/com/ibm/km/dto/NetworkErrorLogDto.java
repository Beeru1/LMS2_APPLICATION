/*
 * Created on Apr 3, 2008
 *
 */
package com.ibm.km.dto;



/**
 * @author Kundan Kumar
 *
 */
public class NetworkErrorLogDto{ 
// implements Comparable{
	
	private String problemId;
	private int circleID;
	private String circleName;
	private String areaAffected;
	private String problemDesc;
	private String resoTATHH;
	private String resoTATMM;	
	private String tat;
	private String loggingTime;
	private String status;
	
	public String getLoggingTime() {
		return loggingTime;
	}
	public void setLoggingTime(String loggingTime) {
		this.loggingTime = loggingTime;
	}
	public String getAreaAffected() {
		return areaAffected;
	}
	public void setAreaAffected(String areaAffected) {
		this.areaAffected = areaAffected;
	}
	public String getProblemDesc() {
		return problemDesc;
	}
	public void setProblemDesc(String problemDesc) {
		this.problemDesc = problemDesc;
	}
	public String getResoTATHH() {
		return resoTATHH;
	}
	public void setResoTATHH(String resoTATHH) {
		this.resoTATHH = resoTATHH;
	}
	public String getResoTATMM() {
		return resoTATMM;
	}
	public void setResoTATMM(String resoTATMM) {
		this.resoTATMM = resoTATMM;
	}
	public int getCircleID() {
		return circleID;
	}
	public void setCircleID(int circleID) {
		this.circleID = circleID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProblemId() {
		return problemId;
	}
	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}
	public String getTat() {
		//return new StringBuilder(getResoTATHH()).append(":").append(getResoTATMM()).toString();
		return tat;
	}
	public void setTat(String tat) {
		this.tat = tat;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	/*public int compareTo(Object o) {
		NetworkErrorLogDto dto = (NetworkErrorLogDto) o;
		return this.circleName.compareTo(dto.getCircleName());
	}*/
}