package com.ibm.km.dao;

import java.util.ArrayList;

import com.ibm.lms.exception.LMSException;

public interface KeywordMgmtDao {
	
	public ArrayList<String> getMatchingKeywords(String keyword) throws LMSException;
	
	public int updateCount(String keyword) throws LMSException;
	
	public int insertKeyword(String keyword) throws LMSException;
	
	public boolean checkKeyword(String keyword) throws LMSException;

	public ArrayList<String> getKeywords() throws LMSException;

}
