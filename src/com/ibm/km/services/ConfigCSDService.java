package com.ibm.km.services;
/**
 * @author Ajil Chandran
 * Created On  11 Nov 2010
 */
import java.util.List;

import com.ibm.km.dto.ConfigCSDDto;
import com.ibm.lms.exception.LMSException;

public interface ConfigCSDService {

	public int insertCsd (ConfigCSDDto configCSDDto) throws LMSException;
	
	public List fetchCsdUsers(int circleId) throws LMSException;
	
	public int removeCsd(String[] csdList, String circleId,String status) throws LMSException;
	/*
	 * in this method instead of deletion we changes the status to 'D'
	 */
}
