package com.ibm.lms.dao;

import java.util.ArrayList;

import com.ibm.lms.exception.ActivateUserAccountException;

public interface ActivateUserAccountDao {
	
	/**
	 * Metod to get Expired Lock
	 * @param circleId
	 * @param val
	 * @param flag
	 * @return
	 * @throws ActivateUserAccountException
	 */
	public ArrayList getExpiredLocked(int circleId,int val,String flag) throws ActivateUserAccountException;
	/**
	 * Method to update expired
	 * @param userId
	 * @param password
	 * @param flag
	 * @return
	 * @throws ActivateUserAccountException
	 */
	public int[] updateExpired(Integer userId[ ],String password,String flag) throws ActivateUserAccountException;
}
