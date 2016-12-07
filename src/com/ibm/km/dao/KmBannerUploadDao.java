package com.ibm.km.dao;

import java.io.InputStream;

import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;

public interface KmBannerUploadDao {

	public int uploadBanner(byte[] data,String fileName, String bannerPage) throws LMSException,DAOException;
	
	
	public InputStream retrieveBanner(String bannerPage) throws LMSException,DAOException;
	
	
}
