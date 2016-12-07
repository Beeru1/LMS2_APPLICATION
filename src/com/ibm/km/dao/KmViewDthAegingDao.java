package com.ibm.km.dao;

import java.util.ArrayList;

import com.ibm.km.dto.KmViewDthAegingDto;
import com.ibm.km.dto.OfferDetailsDTO;

public interface KmViewDthAegingDao {

	
	public ArrayList<KmViewDthAegingDto> getBucketId(String days) throws Exception;
	public ArrayList<OfferDetailsDTO> getOfferDetails(String bucketId) throws Exception;
}
