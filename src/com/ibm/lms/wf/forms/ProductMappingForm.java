package com.ibm.lms.wf.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ProductMappingForm extends ActionForm{

	/**
	 * @author Parnika Sharma 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProductMappingForm(){
		
	}
	
	private String methodName;
	private String productLobName;
	private int productLobId;
	private int selectedProductLobId;
	private ArrayList productLobList = null;
	private String productName;
	private int productId;
	private int selectedProductId;
	private ArrayList productNameList = null;
	private String newProductLobName="";
	private String newProductName="";
	private String newProductSynonym="";
	private String message="";
	
	private String synonymName="";
	private int synonymId;
	private ArrayList synonymList = null;
	private String[] createMultiple = null;
	
	public void reset(ActionMapping mapping, HttpServletRequest request) 
	{
		 
		 productLobName="";
		 productName="";
		 newProductLobName="";
		 newProductName="";
		 newProductSynonym="";
		 synonymName="";
		 selectedProductLobId = -2;
	}

	public String getProductLobName() {
		return productLobName;
	}

	public int getSelectedProductLobId() {
		return selectedProductLobId;
	}

	public void setSelectedProductLobId(int selectedProductLobId) {
		this.selectedProductLobId = selectedProductLobId;
	}

	public void setProductLobName(String productLobName) {
		this.productLobName = productLobName;
	}

	public int getProductLobId() {
		return productLobId;
	}

	public void setProductLobId(int productLobId) {
		this.productLobId = productLobId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public ArrayList getProductLobList() {
		return productLobList;
	}

	public void setProductLobList(ArrayList productLobList) {
		this.productLobList = productLobList;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
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

	public String getNewProductLobName() {
		return newProductLobName;
	}

	public void setNewProductLobName(String newProductLobName) {
		this.newProductLobName = newProductLobName;
	}

	public String getNewProductName() {
		return newProductName;
	}

	public void setNewProductName(String newProductName) {
		this.newProductName = newProductName;
	}

	public String getNewProductSynonym() {
		return newProductSynonym;
	}

	public void setNewProductSynonym(String newProductSynonym) {
		this.newProductSynonym = newProductSynonym;
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
	
	

}
