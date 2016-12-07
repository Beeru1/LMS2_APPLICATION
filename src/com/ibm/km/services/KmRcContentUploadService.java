package com.ibm.km.services;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.km.dto.KmDocumentMstr;
import com.ibm.km.dto.KmRcDataDTO;
import com.ibm.km.dto.KmRcHeaderDTO;
import com.ibm.km.dto.KmRcTypeDTO;
import com.ibm.km.forms.KmRCContentUploadFormBean;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;



public interface KmRcContentUploadService {
	
	/**
	 * Method to Get Combo List
	 * @param dto
	 * @return
	 * @throws LMSException
	 */
	public ArrayList<KmRcTypeDTO> getCombo() throws LMSException;
	
	public ArrayList<KmRcHeaderDTO> getHeaders() throws LMSException;
	
	public  KmDocumentMstr  saveRechargeDetails(KmRCContentUploadFormBean formBean,UserMstr sessionUserBean) throws LMSException;
	
	public int insertRcData(KmRcDataDTO dto) throws LMSException;
	
	public KmRcDataDTO getRcData(String documentId) throws LMSException;
	
	public String deleteRcData(String documentId) throws LMSException;
	
	public ArrayList<HashMap<String,String>> getRcDataCsr(String rcValue,String rcType) throws LMSException;
	
	

}
