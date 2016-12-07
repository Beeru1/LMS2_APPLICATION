package com.ibm.km.services;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.km.dto.KmBPUploadDto;
import com.ibm.km.dto.KmDocumentMstr;
import com.ibm.km.forms.KmBPUploadFormBean;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.DAOException;

public interface KmBPUploadService {

	public ArrayList<KmBPUploadDto> getHeaders() throws DAOException;
	
	public KmDocumentMstr saveBillPlanDetails(KmBPUploadFormBean form,UserMstr sessionUserBean) throws DAOException;
	
	public int insertBPData(KmBPUploadDto dto) throws DAOException;
	
	public ArrayList<KmBPUploadDto> viewBPDetails(KmBPUploadDto dto) throws DAOException;
	
	public int deleteBPDetails(KmBPUploadDto dto) throws DAOException;
	
	public ArrayList<String> getDocumentId(String searchKey) throws DAOException;
	
	public ArrayList<HashMap<String, String>> getCsrBPDetails(ArrayList<String> documentIds) throws DAOException;
	
	public String[] getCircleList(String documentId) throws DAOException;
}
