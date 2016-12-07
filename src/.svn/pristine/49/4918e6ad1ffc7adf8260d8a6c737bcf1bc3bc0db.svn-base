package com.ibm.lms.webservice;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.lms.dto.AgencyResponseMessage;
import com.ibm.lms.dto.AuthorizationData;
import com.ibm.lms.engine.dao.impl.CaptureLeadDataDAOImpl;
import com.ibm.lms.engine.dataobjects.AgencyCaptureLeadDO;

public class AgencyLeadCaptureWebService {

private static final Logger logger =  Logger.getLogger(AgencyLeadCaptureWebService.class);
	
	public AgencyResponseMessage captureLeadData(AgencyCaptureLeadDO[] leadData , AuthorizationData authData ) throws Exception
	{
		
		AgencyResponseMessage rseponseMsg=null;
		try
		{
			//DBConnection.getInstance();
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			String userName = bundle.getString("lms.dialer.ws.username");
			String password = bundle.getString("lms.dialer.ws.password");
			
			
			
			if((authData.getPassword()).equals(password) && authData.getUserName().equals(userName))
			{
				logger.info("Creating process >>>>>>>");
				CaptureLeadDataDAOImpl daoObj = new CaptureLeadDataDAOImpl();
				
				rseponseMsg =daoObj.captureLeadData(leadData);
			
				//logger.info("Process  End >>>>>>>");
				
			}
			else
			{
				rseponseMsg = new AgencyResponseMessage();
				rseponseMsg.setResponseCode("0");
				rseponseMsg.setResponseMsg("Invalid Userid/Password");
				logger.error("Invalid Userid/Password:" + authData.getUserName() );
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured in captureLeadData():" + e.getMessage());
			rseponseMsg = new AgencyResponseMessage();
			rseponseMsg.setResponseCode("1");
			rseponseMsg.setResponseMsg("Internal Error Occured ");
			//throw new Exception("Exception in captureLeadData method:: "+e);
		}
		return rseponseMsg;
	}
	
	
}
