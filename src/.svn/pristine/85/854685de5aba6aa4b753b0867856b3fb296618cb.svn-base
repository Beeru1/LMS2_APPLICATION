
package com.ibm.lms.services.impl;

/**
 * @author Parnika Sharma 
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ibm.lms.dao.MasterDao;
import com.ibm.lms.dao.ProductMappingDao;
import com.ibm.lms.dao.impl.MasterDaoImpl;
import com.ibm.lms.dao.impl.ProductMappingDaoImpl;
import com.ibm.lms.dto.CircleDTO;
import com.ibm.lms.dto.ProductDTO;
import com.ibm.lms.dto.StateDTO;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.ProductMappingService;


public class ProductMappingServiceImpl implements ProductMappingService {
	
	private static final Logger logger = Logger.getLogger(ProductMappingServiceImpl.class);
	
	public  ArrayList<ProductDTO> getProductLobList() throws LMSException
	{
		ProductMappingDao dao= ProductMappingDaoImpl.productMappingDaoInstance();// changed by srikant new ProductMappingDaoImpl();
		try
		{
		return dao.getProductLobList();
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting product list : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
		
	public  ArrayList<ProductDTO> getProductList() throws LMSException
	{
		ProductMappingDao dao= ProductMappingDaoImpl.productMappingDaoInstance();// changed by srikantv new ProductMappingDaoImpl();
		try
		{
		return dao.getProductList();
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting product list : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public  ArrayList<CircleDTO> getCircleList() throws LMSException
	{
		ProductMappingDao dao= ProductMappingDaoImpl.productMappingDaoInstance();// changed by srikant new ProductMappingDaoImpl();
		try
		{
		return dao.getCircleList();
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting circle list : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public  ArrayList<StateDTO> getStateList() throws LMSException
	{
		ProductMappingDao dao= ProductMappingDaoImpl.productMappingDaoInstance();// changed by srikant new ProductMappingDaoImpl();
		try
		{
		return dao.getStateList();
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting state list : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public JSONObject getElementsAsJson(String productLobId) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllChildren(productLobId);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			ProductDTO dto=(ProductDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}
	
	public ArrayList getAllChildren(String productLobId) throws Exception {
 		
		ArrayList productList=new ArrayList();
		ProductMappingDao dao= ProductMappingDaoImpl.productMappingDaoInstance();// changed by srikant new ProductMappingDaoImpl();
		productList= dao.getProductList(Integer.parseInt(productLobId));
		return productList;
	}
	
	public JSONObject getElementsAsJsonNewProductLob(String newProductLobName, String userLoginId) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllChildrenNewProductLob(newProductLobName, userLoginId);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			ProductDTO dto=(ProductDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}
	
	public ArrayList getAllChildrenNewProductLob(String newProductLobName, String userLoginId) throws Exception {
 		
		ArrayList newproductLobList=new ArrayList();
		ProductMappingDao dao= ProductMappingDaoImpl.productMappingDaoInstance();// changed by srikant new ProductMappingDaoImpl();
		newproductLobList= dao.getProductListNewProductLob(newProductLobName, userLoginId);
		return newproductLobList;
	}
	
	public JSONObject getElementsAsJsonNewProductName(String newProductName, int productLobId,String userLoginId) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllChildrenNewProductName(newProductName,productLobId,userLoginId);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			ProductDTO dto=(ProductDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}
	
	public ArrayList getAllChildrenNewProductName(String newProductName, int productLobId,String userLoginId) throws Exception {
 		
		ArrayList newproductNameList=new ArrayList();
		ProductMappingDao dao= ProductMappingDaoImpl.productMappingDaoInstance();// changed by srikant new ProductMappingDaoImpl();
		newproductNameList= dao.getProductListNewProductName(newProductName, productLobId, userLoginId);
		return newproductNameList;
	}
	
	public int insertProductSynonym(ProductDTO productDto) throws Exception {
		
		ProductMappingDao dao= ProductMappingDaoImpl.productMappingDaoInstance();// changed by srikant new ProductMappingDaoImpl();
		int rows = dao.insertProductSynonym(productDto);
		return rows;
	}
	
	public JSONObject getElementsAsJsonSynonymList(String selectedProductId) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllChildrenSynonymList(selectedProductId);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			ProductDTO dto=(ProductDTO)iter.next();
			jsonItems.put(dto.toJSONObject());
		}
		json.put("elements", jsonItems);
		return json;
	}
	
	public ArrayList getAllChildrenSynonymList(String selectedProductId) throws Exception {
 		
		ArrayList synonymList=new ArrayList();
		ProductMappingDao dao= ProductMappingDaoImpl.productMappingDaoInstance();// changed by srikant new ProductMappingDaoImpl();
		synonymList= dao.getSynonymListBasedOnProduct(Integer.parseInt(selectedProductId));
		return synonymList;
	}
	public String getDataForPinCode(String pinCode, int circleMstrId,int productlobId) throws LMSException { 
		ProductMappingDao dao= ProductMappingDaoImpl.productMappingDaoInstance();// changed by srikant new ProductMappingDaoImpl();
		try
		{
			logger.info(" pinCode in service impl :"+pinCode+" circleMstrId :"+circleMstrId);
			return dao.getDataForPinCode(pinCode,circleMstrId,productlobId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting City List for circle: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	public String populateZoneCityCityZoneBasedOnCircle(int circleMstrId) throws LMSException { 
		ProductMappingDao dao= ProductMappingDaoImpl.productMappingDaoInstance();// changed by srikant new ProductMappingDaoImpl();
		try
		{
			logger.info(" pinCode in service impl :"+"circleMstrId :"+circleMstrId);
			return dao.populateZoneCityCityZoneBasedOnCircle(circleMstrId);
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting City List for circle: "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
}

