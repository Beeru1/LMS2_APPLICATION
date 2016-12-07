package com.ibm.lms.engine.handlers;

import org.apache.log4j.Logger;

import com.ibm.lms.engine.LMSHandler;
import com.ibm.lms.engine.dataobjects.LeadToBeAssignedDO;
import com.ibm.lms.engine.exception.LmsException;

public class AssignmentHandler extends LMSHandler {
	
	private static Logger lmsLogger=Logger.getLogger(AssignmentHandler.class);

	private LeadToBeAssignedDO leadToBeAssignedObj = null;
	
	public AssignmentHandler(LeadToBeAssignedDO leadToBeAssignedObj){
		this.leadToBeAssignedObj = leadToBeAssignedObj;
	}
	
	public LeadToBeAssignedDO getLeadToBeAssignedObj() {
		return leadToBeAssignedObj;
	}


	public void setLeadToBeAssignedObj(LeadToBeAssignedDO leadToBeAssignedObj) {
		this.leadToBeAssignedObj = leadToBeAssignedObj;
	}


	protected void process() throws LmsException {
		lmsLogger.info("AssignmentHandler::process");
		
		if(null == leadToBeAssignedObj){
			throw new LmsException("leadToBeAssignedObj is null");
		}else{
			//TODO make call to DomainHelper class
			
		}

	}

}
