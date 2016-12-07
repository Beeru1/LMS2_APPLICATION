package com.ibm.lms.engine.handlers;

import org.apache.log4j.Logger;

import com.ibm.lms.engine.LMSHandler;
import com.ibm.lms.engine.dao.UpdateLeadDataDAO;
import com.ibm.lms.engine.dao.impl.UpdateLeadDataDAOImpl;
import com.ibm.lms.engine.dataobjects.UpdateLeadDataDO;
import com.ibm.lms.engine.exception.LmsException;

public class UpdateLeadDataHandler extends LMSHandler {
	
	private UpdateLeadDataDO updateLeadDataDO[];
	private static Logger lmsLogger=Logger.getLogger(UpdateLeadDataHandler.class);

	
	
	public UpdateLeadDataHandler(UpdateLeadDataDO[] updateLeadDataDO) {
		
		this.updateLeadDataDO = updateLeadDataDO;
	}

	public Boolean call() throws Exception {
		lmsLogger.info("LMSHandler::call method****************aaaaaaaaaa************"+this.updateLeadDataDO.length);
		//lmsLogger.info("LMSHandler::call method***********captureLeadDO****bbbbbbbbb*************"+captureLeadDO);
		process();
		return new Boolean(true);
	}
	
	protected void process() throws LmsException
	{
		UpdateLeadDataDAO daoObj = new UpdateLeadDataDAOImpl();
		try
		{
			lmsLogger.info("!!!!!!!!!!!!!");
			daoObj.updateLeadDataStatus(updateLeadDataDO);
			lmsLogger.info("******************");
		}
		catch(Exception ex)
		{
			lmsLogger.error("Exception in ProcessUPdateLeadHandler:" + ex);
			ex.printStackTrace();
		}
	}
	
}