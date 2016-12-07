package com.ibm.lms.dao;

/**
 * @author Parnika Sharma 
 */

import java.util.ArrayList;


import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.dto.AgencyDTO;
import com.ibm.lms.dto.CircleDTO;
import com.ibm.lms.dto.ProductDTO;
import com.ibm.lms.dto.StateDTO;

public interface AgencyMappingDao {

	public  ArrayList<CircleDTO> getCircleList() throws DAOException;
	public  String createAgency(AgencyDTO agencyDto) throws DAOException;
	public  ArrayList<AgencyDTO> getAgencyList() throws DAOException;
	public  AgencyDTO getAgencyDetails(int agencyId) throws DAOException;
	public  ArrayList<AgencyDTO> getCircleMappedList(int agencyId) throws DAOException;
	public  ArrayList<CircleDTO> removeCircleAgencyMapping(int agencyId, String[] circleList) throws DAOException;
	public  ArrayList<AgencyDTO> addCircleAgencyMapping(int agencyId, String[] circleList) throws DAOException;
	public  boolean updateAgency(AgencyDTO agencyDto) throws DAOException;


}
