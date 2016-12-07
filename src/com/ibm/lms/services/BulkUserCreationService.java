package com.ibm.lms.services;

import org.apache.struts.upload.FormFile;

import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;

public interface BulkUserCreationService {
	
	public BulkUploadMsgDto  uploadUsers(FormFile file,UserMstr userBean) throws LMSException ;

}
