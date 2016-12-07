package com.ibm.km.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.ibm.km.dto.KmCsrLatestUpdatesDto;

public class KmCsrLatestUpdatesFormBean extends ActionForm{

	private static final long serialVersionUID = 1L;
	private String methodName = null;
	private ArrayList<KmCsrLatestUpdatesDto> updatesList = new ArrayList<KmCsrLatestUpdatesDto>();
	
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public ArrayList<KmCsrLatestUpdatesDto> getUpdatesList() {
		return updatesList;
	}
	public void setUpdatesList(ArrayList<KmCsrLatestUpdatesDto> updatesList) {
		this.updatesList = updatesList;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	
}
