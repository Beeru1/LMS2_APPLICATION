package com.ibm.km.services;

import com.ibm.km.dto.KmDocumentMstr;
import com.ibm.km.forms.KmSopUploadFormBean;
import com.ibm.lms.exception.LMSException;

public interface KmSopUploadService {
	
	
	/**
	 * Method to save SOP details in XML. 
	 */
	public KmDocumentMstr saveProductDetails(KmSopUploadFormBean kmSopUploadFormBean)throws LMSException;
	
	/**
	 * Method to read xml file from desk and display in jsp.
	 */
	public KmSopUploadFormBean viewProductDetails(KmSopUploadFormBean kmSopUploadFormBean)throws LMSException;
}
