package com.ibm.lms.wf.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.lms.dto.CircleMstrDto;

public class CircleManagementForm extends ActionForm{

	/**
	 * @author Amandeep Singh
	 */
	private static final long serialVersionUID = 1L;
	
	public CircleManagementForm(){
		
	}
	
	private String methodName;
	private String circleName;
	private String circleDesc;
	private ArrayList circleList=null;
	private String lobName;
	private int lobId;
	private int selectedLobId;
	private ArrayList lobList = null;
	private int selectedProductId;
	private String circleIdToDelete="";
	private String circleIdToEdit="";
	private String message="";
	
	private String synonymName="";
	private int synonymId;
	private ArrayList synonymList = null;
	private String[] createMultiple = null;
	
	public void reset(ActionMapping mapping, HttpServletRequest request) 
	{
		 
		 lobName="";
		// newProductSynonym="";
		 synonymName="";
		 selectedLobId = -2;
	}

	
	public int getSelectedLobId() {
		return selectedLobId;
	}

	public void setSelectedLobId(int selectedLobId) {
		this.selectedLobId = selectedLobId;
	}

	public ArrayList getLobList() {
		return lobList;
	}

	public void setLobList(ArrayList lobList) {
		this.lobList = lobList;
	}

	public int getLobId() {
		return lobId;
	}

	public void setLobId(int lobId) {
		this.lobId = lobId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	public int getSelectedProductId() {
		return selectedProductId;
	}

	public void setSelectedProductId(int selectedProductId) {
		this.selectedProductId = selectedProductId;
	}



	public String getCircleIdToDelete() {
		return circleIdToDelete;
	}

	public void setCircleIdToDelete(String circleIdToDelete) {
		this.circleIdToDelete = circleIdToDelete;
	}

	public String getCircleIdToEdit() {
		return circleIdToEdit;
	}

	public void setCircleIdToEdit(String circleIdToEdit) {
		this.circleIdToEdit = circleIdToEdit;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSynonymName() {
		return synonymName;
	}

	public void setSynonymName(String synonymName) {
		this.synonymName = synonymName;
	}

	public int getSynonymId() {
		return synonymId;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getCircleDesc() {
		return circleDesc;
	}

	public void setCircleDesc(String circleDesc) {
		this.circleDesc = circleDesc;
	}

	

	public ArrayList getCircleList() {
		return circleList;
	}


	public void setCircleList(ArrayList circleList) {
		this.circleList = circleList;
	}


	public void setSynonymId(int synonymId) {
		this.synonymId = synonymId;
	}

	public ArrayList getSynonymList() {
		return synonymList;
	}

	public void setSynonymList(ArrayList synonymList) {
		this.synonymList = synonymList;
	}

	public String[] getCreateMultiple() {
		return createMultiple;
	}

	public void setCreateMultiple(String[] createMultiple) {
		this.createMultiple = createMultiple;
	}


	public String getLobName() {
		return lobName;
	}


	public void setLobName(String lobName) {
		this.lobName = lobName;
	}
	
	

}
