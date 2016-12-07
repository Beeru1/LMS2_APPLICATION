package com.ibm.lms.engine.dao;


import com.ibm.lms.dto.AgencyResponseMessage;
import com.ibm.lms.dto.webservice.LeadCaptureServiceFirstVersDO;
import com.ibm.lms.engine.dataobjects.AgencyCaptureLeadDO;
import com.ibm.lms.engine.dataobjects.CaptureLeadDO;
import com.ibm.lms.engine.exception.DAOException;

public interface CaptureLeadDataDAO {
	
	public boolean captureLeadData(CaptureLeadDO[] leadData) throws DAOException;
	public AgencyResponseMessage captureLeadData(AgencyCaptureLeadDO[] leadData) throws DAOException;

	public String getLeadData(LeadCaptureServiceFirstVersDO leadData) throws DAOException;

}