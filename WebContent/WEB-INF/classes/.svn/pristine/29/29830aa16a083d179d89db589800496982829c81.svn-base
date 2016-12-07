package com.ibm.km.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


 /** 
	* @author Kundan Kumar 
	* Created On Mon Nov 23 18:13:38 IST 2010 
	* Form Bean class for table KM_NETWORK_FAULT_LOG 
 
 **/ 
public class KmNetworkErrorFormBean extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7972276164533288300L;

	private String problemDesc="";
	
	private String areaAffected="" ;
	
	private String resoTATHH;
	
	private String resoTATMM;

	private String methodName;

	public String getAreaAffected() {
		return areaAffected;
	}

	public void setAreaAffected(String areaAffected) {
		this.areaAffected = areaAffected;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
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
	
	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		
		problemDesc="";
		areaAffected="";
		resoTATHH="";
		resoTATMM="";
		super.reset(arg0, arg1);
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
