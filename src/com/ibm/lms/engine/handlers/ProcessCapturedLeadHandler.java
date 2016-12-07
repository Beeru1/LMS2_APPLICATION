package com.ibm.lms.engine.handlers;

import org.apache.log4j.Logger;

import com.ibm.lms.engine.dataobjects.CaptureLeadDO;
import com.ibm.lms.engine.dao.CaptureLeadDataDAO;
import com.ibm.lms.engine.dao.impl.CaptureLeadDataDAOImpl;
import com.ibm.lms.engine.LMSHandler;
import com.ibm.lms.engine.exception.LmsException;

public class ProcessCapturedLeadHandler extends LMSHandler {
	
	private CaptureLeadDO captureLeadDO[];
	private static Logger lmsLogger=Logger.getLogger(ProcessCapturedLeadHandler.class);

	public ProcessCapturedLeadHandler(CaptureLeadDO[] captureLeadDO)
	{
		this.captureLeadDO=captureLeadDO;
	}
	
	
	public Boolean call() throws Exception {
		//lmsLogger.info("LMSHandler::call method****************aaaaaaaaaa************"+this.captureLeadDO.length);
		//lmsLogger.info("LMSHandler::call method***********captureLeadDO****bbbbbbbbb*************"+captureLeadDO);
		process();
		return new Boolean(true);
	}
	
	protected void process() throws LmsException
	{
		CaptureLeadDataDAO daoObj = new CaptureLeadDataDAOImpl();
//		logger.info("process:::::::"+this.captureLeadDO.length);
//		logger.info("process length::"+captureLeadDO[0].getProspectsName());
		try
		{
//			logger.info("!!!!!!!!!!!!!");
			daoObj.captureLeadData(captureLeadDO);
//			logger.info("******************");
		}
		catch(Exception ex)
		{
			lmsLogger.error("Exception in ProcessCapturedLeadHandler:" + ex);
			ex.printStackTrace();
//			logger.info("^^^^^^^^^^^^^^^^^^^"+ex);
//			logger.info(ex);
		}
	}
	
}
