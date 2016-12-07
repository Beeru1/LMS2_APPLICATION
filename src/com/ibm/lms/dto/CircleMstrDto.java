package com.ibm.lms.dto;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class CircleMstrDto {

	private String circleId="";
	private String circleName="";
	private String circleDesc="";
	private int lobId;
	private String lobName="";
	private String userLoginId="";
	private ArrayList circleList = null;
	private String cirName="";
	private String cirDesc="";
	private String circleIdToDelete="";
	private String circleIdToEdit="";
	private String message="";
	
	private int productId=0;	
	private int productLobId=0;
	private String productName="";
	private String productDescription="";
	private String productHeadOlmId="";
	
	/* Added by Parnika for Product Mapping  */
	
	private String productLobName="";
	private String newProductLobName="";
	private String newProductName="";
	private String newProductSynonym="";
	private String synonymName;
	private int synonymId;
	private ArrayList synonymList = null;
		
	/*End of changes by parnika */
	 
	public String getNewProductLobName() {
		return newProductLobName;
	}
	public void setNewProductLobName(String newProductLobName) {
		this.newProductLobName = newProductLobName;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getProductLobId() {
		return productLobId;
	}
	public void setProductLobId(int productLobId) {
		this.productLobId = productLobId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getProductHeadOlmId() {
		return productHeadOlmId;
	}
	public void setProductHeadOlmId(String productHeadOlmId) {
		this.productHeadOlmId = productHeadOlmId;
	}
	public String getProductLobName() {
		return productLobName;
	}
	public void setProductLobName(String productLobName) {
		this.productLobName = productLobName;
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
	
	
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public int getLobId() {
		return lobId;
	}
	public void setLobId(int lobId) {
		this.lobId = lobId;
	}
	public String getLobName() {
		return lobName;
	}
	public void setLobName(String lobName) {
		this.lobName = lobName;
	}
	
	public ArrayList getCircleList() {
		return circleList;
	}
	public void setCircleList(ArrayList circleList) {
		this.circleList = circleList;
	}
	public String getUserLoginId() {
		return userLoginId;
	}
	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}
	
	public String getCircleDesc() {
		return circleDesc;
	}
	public void setCircleDesc(String circleDesc) {
		this.circleDesc = circleDesc;
	}
	public JSONObject toJSONObject() {
		JSONObject json=new JSONObject();
		try {
			json.put("productId", productId);
			json.put("productName", productName);
			json.put("productLobId", productLobId);
			json.put("productLobName", productLobName);
			json.put("newProductLobName", newProductLobName);
			json.put("newProductName", newProductName);
			json.put("synonymName", synonymName);
			json.put("synonymId", synonymId);

		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return json; 
	}
	public String getCirName() {
		return cirName;
	}
	public void setCirName(String cirName) {
		this.cirName = cirName;
	}
	public String getCirDesc() {
		return cirDesc;
	}
	public void setCirDesc(String cirDesc) {
		this.cirDesc = cirDesc;
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
	
	
	
}