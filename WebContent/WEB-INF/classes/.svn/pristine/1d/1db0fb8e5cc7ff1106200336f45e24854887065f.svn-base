package com.ibm.km.services;

import java.util.ArrayList;
import java.util.List;

import com.ibm.km.dto.OfferDetailsDTO;
import com.ibm.km.forms.KmOfferUploadFormBean;
import com.ibm.lms.exception.LMSException;

public interface KmOfferUploadService {
	
	public ArrayList<OfferDetailsDTO> getBucketList()throws LMSException;
	
	public int insertOffer(KmOfferUploadFormBean kmOfferUploadForm)throws LMSException;
	
	public int updateOffer(KmOfferUploadFormBean kmOfferUploadForm)throws LMSException;
	
	public List<OfferDetailsDTO> getOfferList(int offerId)throws LMSException;
}
