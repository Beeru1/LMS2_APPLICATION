package com.ibm.km.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.km.dto.KmRcDataDTO;
import com.ibm.km.dto.KmRcHeaderDTO;
import com.ibm.km.dto.KmRcTypeDTO;
import com.ibm.lms.exception.LMSException;

public interface KmRcContentUploadDao {
	
	public ArrayList<KmRcTypeDTO> getCombo() throws LMSException;
	
	public ArrayList<KmRcHeaderDTO> getHeaders() throws LMSException;
	
	public int insertRcData(KmRcDataDTO dto) throws LMSException;
	
	public KmRcDataDTO getRcData(String documentId) throws LMSException;
	
	public String deleteRcData(String documentId) throws LMSException;
	
	public ArrayList<HashMap<String, String>> getRcDataCsr(String rcValue,String rcType) throws LMSException;

}
