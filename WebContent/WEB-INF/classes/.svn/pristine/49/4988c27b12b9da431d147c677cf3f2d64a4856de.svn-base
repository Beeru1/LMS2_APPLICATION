package com.ibm.km.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.km.dto.KmBPUploadDto;
import com.ibm.lms.exception.DAOException;

public interface KmBPUploadDao {

	
	public ArrayList<KmBPUploadDto> getHeaders() throws DAOException;
	
	public int insertBPData(KmBPUploadDto dto) throws DAOException;
	
	public ArrayList<KmBPUploadDto> viewBPDetails(KmBPUploadDto dto) throws DAOException;
	
	public int deleteBPDetails(KmBPUploadDto dto) throws DAOException;
	
	public ArrayList<String> getDocumentId(String searchKey) throws DAOException;
	
	public ArrayList<HashMap<String, String>> getCsrBPDetails(ArrayList<String> documentIds) throws DAOException;
	
	public String[] getCircleList(String documentId) throws DAOException;
}
