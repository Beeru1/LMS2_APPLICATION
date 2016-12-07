package com.ibm.lms.engine;

import org.apache.log4j.Logger;

import com.ibm.lms.engine.exception.LmsException;
import com.ibm.lms.engine.intefaces.LMSJobHandler;

public abstract class LMSHandler implements LMSJobHandler {

	private static Logger lmsLogger=Logger.getLogger(LMSHandler.class);
	
	public Boolean call() throws Exception {
		lmsLogger.info("LMSHandler::call");
		process();
		return new Boolean(true);
	}
	
	protected abstract void process() throws LmsException; 
	

}
