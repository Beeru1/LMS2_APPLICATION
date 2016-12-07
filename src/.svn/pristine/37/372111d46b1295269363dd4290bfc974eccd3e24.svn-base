package com.ibm.lms.services;

/**
 * @author Parnika Sharma 
 */

import java.util.ArrayList;

import org.json.JSONObject;

import com.ibm.lms.dto.CircleDTO;
import com.ibm.lms.dto.ProductDTO;
import com.ibm.lms.dto.StateDTO;
import com.ibm.lms.exception.LMSException;

public interface ProductMappingService {
	
	public  ArrayList<ProductDTO> getProductList() throws LMSException;
	public  ArrayList<CircleDTO> getCircleList() throws LMSException;
	public  ArrayList<StateDTO> getStateList() throws LMSException;
	public  ArrayList<ProductDTO> getProductLobList() throws LMSException;
	public JSONObject getElementsAsJson(String productLobId) throws Exception;
	public JSONObject getElementsAsJsonNewProductLob(String newProductLobName, String userLoginId) throws Exception;
	public JSONObject getElementsAsJsonNewProductName(String newProductName, int productLobId,String userLoginId) throws Exception;
	public ArrayList getAllChildrenNewProductName(String newProductName, int productLobId,String userLoginId) throws Exception;
	public ArrayList getAllChildrenNewProductLob(String newProductLobName , String userLoginId) throws Exception;
	public int insertProductSynonym(ProductDTO productDto) throws Exception;
	public JSONObject getElementsAsJsonSynonymList(String selectedProductId) throws Exception;
	public ArrayList getAllChildrenSynonymList(String selectedProductId) throws Exception;
	public  String getDataForPinCode(String pinCode, int circleMstrId,int productlobId) throws LMSException;
	public  String populateZoneCityCityZoneBasedOnCircle(int circleMstrId) throws LMSException;
	
	

}
