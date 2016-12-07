package com.ibm.lms.services.impl;

import com.ibm.lms.dto.webservice.IDOCDataStatusDTO;
import com.ibm.lms.dto.webservice.RetrieveIDOCLeadDataDTO;
import com.ibm.lms.engine.dao.WebServiceIdocDao;
import com.ibm.lms.engine.dao.impl.WebServiceIdocDaoImpl;
import com.ibm.lms.services.WebServiceIdocServices;

public class WebServiceIdocServiceImpl implements WebServiceIdocServices {

	public String IDOCDataStatus(IDOCDataStatusDTO [] dataStatusDTO) throws Exception{
		WebServiceIdocDao serviceIdocDao  =  WebServiceIdocDaoImpl.getServiceIdocDaoInstance();
		return serviceIdocDao.IDOCDataStatus(dataStatusDTO);
		
	}

	@Override
	public RetrieveIDOCLeadDataDTO[] getIDOCDataList(String lead,
			String prospectMobileNumber, String product, String source,
			String transactionId) throws Exception {
		WebServiceIdocDao serviceIdocDao  =  WebServiceIdocDaoImpl.getServiceIdocDaoInstance();
		return serviceIdocDao.getIDOCDataList(lead, prospectMobileNumber, product, source, transactionId);
	}
	
	
	
	
	
}
