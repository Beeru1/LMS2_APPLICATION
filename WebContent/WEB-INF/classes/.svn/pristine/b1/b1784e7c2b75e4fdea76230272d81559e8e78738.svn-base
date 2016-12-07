package com.ibm.km.forms;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Ajil Chandran
 * Created On 25/11/2010
 */
public class ConfigCSDForm extends ActionForm {
	
	private String methodName=null;
	private String [] csdUserList;
	private String mobileNumber= null;
	private String circleId= null;

	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	

	public String[] getCsdUserList() {
		return csdUserList;
	}

	public void setCsdUserList(String[] csdUserList) {
		this.csdUserList = csdUserList;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getCsdUser(int x) {
		return csdUserList[x];
	}
	public void setCsdUser(String csdUser, int x) {
		this.csdUserList[x] = csdUser;
	}

	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		super.reset(arg0, arg1);
		mobileNumber=null;
		methodName=null;
		circleId= null;
	}
	

	
	
	
}
