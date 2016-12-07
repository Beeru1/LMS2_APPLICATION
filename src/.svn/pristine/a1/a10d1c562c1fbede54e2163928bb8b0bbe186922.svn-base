package com.ibm.km.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.km.dto.EmployeeAppreciationDTO;

public class KmEmployeeAppreciationFormBean extends ActionForm {

	
	List<EmployeeAppreciationDTO> employeeAppreciationList = null;
	private String methodName="";
	private String createdBy="";
	private String levelCount="";
	private String elementLevel="";
	private String token="";
	
	
	
	public List<EmployeeAppreciationDTO> getEmployeeAppreciationList() {
		return employeeAppreciationList;
	}

	public void setEmployeeAppreciationList(
			List<EmployeeAppreciationDTO> employeeAppreciationList) {
		this.employeeAppreciationList = employeeAppreciationList;
	}

    public EmployeeAppreciationDTO getOrderItem(int index)  
    {  
        if(this.employeeAppreciationList == null)  
        {  
            this.employeeAppreciationList = new ArrayList();  
        }  
        while(index >= this.employeeAppreciationList.size())  
        {  
            this.employeeAppreciationList.add(new EmployeeAppreciationDTO());  
        }  
        return (EmployeeAppreciationDTO) employeeAppreciationList.get(index);  
    }  
   
		
	
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLevelCount() {
		return levelCount;
	}

	public void setLevelCount(String levelCount) {
		this.levelCount = levelCount;
	}

	public String getElementLevel() {
		return elementLevel;
	}

	public void setElementLevel(String elementLevel) {
		this.elementLevel = elementLevel;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
