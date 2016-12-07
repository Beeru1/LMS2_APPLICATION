package com.ibm.km.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


 /** 
	* @author Naman 
	* Created On Mon Jan 14 13:10:52 IST 2008 
	* Form Bean class for table KM_CIRCLE_MSTR 
 
 **/ 
public class KmCircleMstrFormBean extends ActionForm {


    private String circleName=null;

    private String circleId=null;
    
    private ArrayList circleList=null;

    private java.sql.Date createdDt=null;

    private String createdBy=null;

    private java.sql.Date updatedDt=null;

    private String updatedBy=null;

	private String hidCircleId = null;
    
    private String status=null;
    
   	private ArrayList circleReportList = null;
	
	private String edit = null;

    /** Creates a dto for the KM_CIRCLE_MSTR table */
    public KmCircleMstrFormBean() {
    }


    public ActionErrors validate(
        ActionMapping mapping,
        HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        //TODO Replace with Actual code.This method is called if validate for this action mapping is set to true. and iff errors is not null and not emoty it forwards to the page defined in input attritbute of the ActionMapping
        return errors;
    }
 /** 
	* Get circleName associated with this object.
	* @return circleName
 **/ 

    public String getCircleName() {
        return circleName;
    }

 /** 
	* Set circleName associated with this object.
	* @param circleName The circleName value to set
 **/ 

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

 /** 
	* Get circleId associated with this object.
	* @return circleId
 **/ 

    public String getCircleId() {
        return circleId;
    }

 /** 
	* Set circleId associated with this object.
	* @param circleId The circleId value to set
 **/ 

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }



 

 /** 
	* Get createdBy associated with this object.
	* @return createdBy
 **/ 

    public String getCreatedBy() {
        return createdBy;
    }

 /** 
	* Set createdBy associated with this object.
	* @param createdBy The createdBy value to set
 **/ 

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

 

 

 /** 
	* Get updatedBy associated with this object.
	* @return updatedBy
 **/ 

    public String getUpdatedBy() {
        return updatedBy;
    }

 /** 
	* Set updatedBy associated with this object.
	* @param updatedBy The updatedBy value to set
 **/ 

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

 /** 
	* Get status associated with this object.
	* @return status
 **/ 

    public String getStatus() {
        return status;
    }

 /** 
	* Set status associated with this object.
	* @param status The status value to set
 **/ 

    public void setStatus(String status) {
        this.status = status;
    }



	/** Set date associated with this object
	 * @param date
	 */
	public void setCreatedDt(java.sql.Date date) {
		createdDt = date;
	}

	/**
	 * @param date
	 */
	public void setUpdatedDt(java.sql.Date date) {
		updatedDt = date;
	}

	/**
	 * @return circleReportList
	 */
	public ArrayList getCircleReportList() {
		return circleReportList;
	}

	/**
	 * @return date associated with the object
	 */
	public java.sql.Date getCreatedDt() {
		return createdDt;
	}

	/**
	 * @return edit value associated with the object
	 */
	public String getEdit() {
		return edit;
	}

	/**
	 * @return
	 */
	public java.sql.Date getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * @param list.The value is set to list
	 */
	public void setCircleReportList(ArrayList list) {
		circleReportList = list;
	}

	/**
	 * @param string
	 */
	public void setEdit(String string) {
		edit = string;
	}

	/**
	 * @return
	 */
	public String getHidCircleId() {
		return hidCircleId;
	}

	/**
	 * @param string
	 */
	public void setHidCircleId(String string) {
		hidCircleId = string;
	}

	/**
	 * @return
	 */
	public ArrayList getCircleList() {
		return circleList;
	}

	/**
	 * @param list
	 */
	public void setCircleList(ArrayList list) {
		circleList = list;
	}

}
