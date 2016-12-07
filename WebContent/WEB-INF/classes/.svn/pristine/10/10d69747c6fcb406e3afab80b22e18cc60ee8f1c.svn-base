package com.ibm.lms.engine.dao;


import java.sql.Connection;
import java.sql.SQLException;

import com.ibm.lms.dto.webservice.GisInfoCaptureDO;
import com.ibm.lms.dto.webservice.GisResponseMessage;

import com.ibm.lms.engine.exception.DAOException;
import com.ibm.lms.exception.LMSException;

public interface CaptureGisDataDao {
	
	public String captureGisDataResponse(GisInfoCaptureDO[] captureDOs) throws DAOException, SQLException;
	public String validateFeasibleLeadForUpdate(String leadId ,Connection con) throws LMSException;
	public  String validateRSUCode(Connection con ,GisInfoCaptureDO gisCaptureObj ,int PRODUCT_LOB_ID,String circleId ) throws DAOException;
	
	
	}