package com.ibm.km.dao;

import java.util.ArrayList;

import com.ibm.km.dto.OfferDetailsDTO;
import com.ibm.km.forms.KmOfferUploadFormBean;
import com.ibm.lms.exception.LMSException;

public interface KmOfferUploadDao {

	public ArrayList<OfferDetailsDTO> getBucketList()throws LMSException;
	
	public int insertRecord(KmOfferUploadFormBean kmOfferUploadFormBean) throws LMSException;
	
	public int updateRecord(KmOfferUploadFormBean kmOfferUploadFormBean) throws LMSException;

	public ArrayList<OfferDetailsDTO> getRecordList(int offerId) throws LMSException;
}
