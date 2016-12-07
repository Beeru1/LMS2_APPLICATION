package com.ibm.lms.services;

/**
 * @author Parnika Sharma 
 */

import java.util.ArrayList;

import org.json.JSONObject;

import com.ibm.lms.dto.AgencyDTO;
import com.ibm.lms.dto.CircleDTO;
import com.ibm.lms.dto.ProductDTO;
import com.ibm.lms.dto.StateDTO;
import com.ibm.lms.exception.LMSException;

public interface AgencyMappingService {
	
	public  ArrayList<CircleDTO> getCircleList() throws LMSException;
	public  String createAgency(AgencyDTO agencyDto) throws LMSException;
	public  ArrayList<AgencyDTO> getAgencyList() throws LMSException;
	public JSONObject getElementsAsJsonCircleMapped(String selectedAgencyId) throws Exception;
	public ArrayList<AgencyDTO> getAllChildrenCircleMapped(String selectedAgencyId) throws Exception;
	public JSONObject getElementsAsJsonCircleNotMapped() throws Exception;
	public ArrayList<CircleDTO> getAllChildrenCircleNotMapped() throws Exception;
	public AgencyDTO getAgencyDetails(String selectedAgencyId) throws Exception;
	public JSONObject getElementsAsJsonRemoveCircle(int agencyId, String[] circleList) throws Exception;
	public ArrayList<CircleDTO> getAllChildrenRemoveCircle(int agencyId, String[] circleList) throws Exception;
	public JSONObject getElementsAsJsonAddCircle(int agencyId, String[] circleList) throws Exception;
	public ArrayList<AgencyDTO> getAllChildrenAddCircle(int agencyId, String[] circleList) throws Exception;
	public  boolean updateAgency(AgencyDTO agencyDto) throws LMSException;

}
