package com.ibm.km.services.impl;

import java.io.InputStream;

import com.ibm.km.dao.KmBannerUploadDao;
import com.ibm.km.dao.impl.KmBannerUploadDaoImpl;
import com.ibm.km.services.KmBannerUploadService;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;

public class KmBannerUploadServiceImpl implements KmBannerUploadService{

	public int uploadBanner(byte[] data,String fileName, String bannerPage) throws LMSException,DAOException{
		
		KmBannerUploadDao dao = new KmBannerUploadDaoImpl();
		return dao.uploadBanner(data, fileName, bannerPage);

	}

	public InputStream retrieveBanner(String bannerPage) throws LMSException,DAOException{
		
		KmBannerUploadDao dao = new KmBannerUploadDaoImpl();
		return dao.retrieveBanner(bannerPage);
	}
	

}
