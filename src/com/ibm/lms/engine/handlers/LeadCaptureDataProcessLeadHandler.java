package com.ibm.lms.engine.handlers;

import org.apache.log4j.Logger;

import com.ibm.lms.dto.webservice.UpdatedLeadDataDTO;
import com.ibm.lms.engine.LMSHandler;
import com.ibm.lms.engine.dao.UpdateLeadDataDAO;
import com.ibm.lms.engine.dao.impl.UpdateLeadDataDAOImpl;
import com.ibm.lms.engine.exception.LmsException;

public class LeadCaptureDataProcessLeadHandler extends LMSHandler {
	
	
	private UpdatedLeadDataDTO captureLeadDO[];
	private static Logger lmsLogger=Logger.getLogger(LeadCaptureDataProcessLeadHandler.class);

	public LeadCaptureDataProcessLeadHandler(UpdatedLeadDataDTO[] captureLeadDO)
	{
		this.captureLeadDO=captureLeadDO;
	}
	
	
	public Boolean call() throws Exception {
		process();
		return new Boolean(true);
	}
	
	protected void process() throws LmsException
	{
		UpdateLeadDataDAO daoObj = new UpdateLeadDataDAOImpl();
		try
		{
			lmsLogger.info("***************************");
			daoObj.updateLeadDataCapture(captureLeadDO);
		}
		catch(Exception ex)
		{
			lmsLogger.info("************fffffffffffffffffffff***********"+ex.getMessage());
			lmsLogger.error("Exception in ProcessCapturedLeadHandler:" + ex);
			ex.printStackTrace();
			throw new LmsException(ex.getMessage());
			
		}
	}
	
}
