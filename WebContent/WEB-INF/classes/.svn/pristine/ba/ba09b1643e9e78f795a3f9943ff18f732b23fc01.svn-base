
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


import com.ibm.lms.dao.AgencyMappingDao;
import com.ibm.lms.dao.impl.AgencyMappingDaoImpl;
import com.ibm.lms.dto.AgencyDTO;
import com.ibm.lms.dto.CircleDTO;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.AgencyMappingService;


public class AgencyMappingServiceImpl implements AgencyMappingService {
	
	private static final Logger logger = Logger.getLogger(AgencyMappingServiceImpl.class);
	
	public  ArrayList<CircleDTO> getCircleList() throws LMSException
	{
		AgencyMappingDao dao= AgencyMappingDaoImpl.agencyMappingDaoInstance();//changed by srikant new AgencyMappingDaoImpl();
		try
		{
		return dao.getCircleList();
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting circle list : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}

	public  String createAgency(AgencyDTO agencyDto) throws LMSException{

		AgencyMappingDao dao= AgencyMappingDaoImpl.agencyMappingDaoInstance();//changed by srikant new AgencyMappingDaoImpl();
		try
		{
		return dao.createAgency(agencyDto);
		}
		catch (Exception e) {
			logger.error("Exception occurred while createAgency() : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	
	}
	
	public  ArrayList<AgencyDTO> getAgencyList() throws LMSException
	{
		AgencyMappingDao dao= AgencyMappingDaoImpl.agencyMappingDaoInstance();//changed by srikant new AgencyMappingDaoImpl();
		try
		{
		return dao.getAgencyList();
		}
		catch (Exception e) {
			logger.error("Exception occurred while getting agency list : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	}
	
	// For populating Circle Mapped with agencies
	
	public JSONObject getElementsAsJsonCircleMapped(String selectedAgencyId) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllChildrenCircleMapped(selectedAgencyId);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			AgencyDTO agencyDto=(AgencyDTO)iter.next();
			jsonItems.put(agencyDto.toJSONObject());
		}
		json.put("elements1", jsonItems);
		return json;
	}
	
	public ArrayList<AgencyDTO> getAllChildrenCircleMapped(String selectedAgencyId) throws Exception {
 		
		AgencyMappingDao dao= AgencyMappingDaoImpl.agencyMappingDaoInstance();//changed by srikant new AgencyMappingDaoImpl();
		return dao.getCircleMappedList(Integer.parseInt(selectedAgencyId));
	}
	
	// For populating Circle Not Mapped with agencies
	
	public JSONObject getElementsAsJsonCircleNotMapped() throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;

		List list = getAllChildrenCircleNotMapped();
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			CircleDTO circleDto=(CircleDTO)iter.next();
			jsonItems.put(circleDto.toJSONObject());
		}
		json.put("elements2", jsonItems);
		return json;
	}
		

	public ArrayList<CircleDTO> getAllChildrenCircleNotMapped() throws Exception {
 		
		AgencyMappingDao dao= AgencyMappingDaoImpl.agencyMappingDaoInstance();//changed by srikant new AgencyMappingDaoImpl();
		return dao.getCircleList();
	}
	

	
	// For getting details of the agency

	
	public AgencyDTO getAgencyDetails(String selectedAgencyId) throws Exception {
		
		AgencyDTO dto = new AgencyDTO();
		AgencyMappingDao dao= AgencyMappingDaoImpl.agencyMappingDaoInstance();//changed by srikant new AgencyMappingDaoImpl();
		dto= dao.getAgencyDetails(Integer.parseInt(selectedAgencyId));
		return dto;
	}
	
	//for removing an already mapped circle
	public JSONObject getElementsAsJsonRemoveCircle(int agencyId, String[] circleList) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;

		List list = getAllChildrenRemoveCircle(agencyId,circleList);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			CircleDTO circleDto=(CircleDTO)iter.next();
			jsonItems.put(circleDto.toJSONObject());
		}
		json.put("elements2", jsonItems);
		return json;
	}
	
	
	public ArrayList<CircleDTO> getAllChildrenRemoveCircle(int agencyId, String[] circleList) throws Exception {
 		
		AgencyMappingDao dao= AgencyMappingDaoImpl.agencyMappingDaoInstance();//changed by srikant new AgencyMappingDaoImpl();
		return dao.removeCircleAgencyMapping(agencyId, circleList);
	}
	
	// for adding a new circle to mapping
	
	public JSONObject getElementsAsJsonAddCircle(int agencyId, String[] circleList) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;

		List list = getAllChildrenAddCircle(agencyId,circleList);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			AgencyDTO circleDto=(AgencyDTO)iter.next();
			jsonItems.put(circleDto.toJSONObject());
		}
		json.put("elements1", jsonItems);
		return json;
	}
	
	
	public ArrayList<AgencyDTO> getAllChildrenAddCircle(int agencyId, String[] circleList) throws Exception {
 		
		AgencyMappingDao dao= AgencyMappingDaoImpl.agencyMappingDaoInstance();//changed by srikant new AgencyMappingDaoImpl();
		return dao.addCircleAgencyMapping(agencyId, circleList);
	}
	
	public  boolean updateAgency(AgencyDTO agencyDto) throws LMSException{

		AgencyMappingDao dao= AgencyMappingDaoImpl.agencyMappingDaoInstance();//changed by srikant new AgencyMappingDaoImpl();
		try
		{
		return dao.updateAgency(agencyDto);
		}
		catch (Exception e) {
			logger.error("Exception occurred while updateAgency() : "+e.getMessage());
			throw new LMSException(e.getMessage(), e);
		}
	
	}
 
}

