/**
 * 
 */
package com.ibm.lms.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



import com.ibm.lms.dto.UserMstr;


/**
 * @author Kumar Saurabh
 *
 */
public class UserDetails 
{
	/**
	 * Method to return user Id of the logged in user
	 * @param request userLoginId of the user
	 * @return
	 */
	public static String getUserLoginId(HttpServletRequest request)
	{
		String userLoginId = "";
		/*
		 * Added on 13th March 2008
		 * Checking whether user is CRM User
		 */
		if(request.getSession().getAttribute("CRM_USER_NAME")!=null) {
			return (String)request.getSession().getAttribute("CRM_USER_NAME");
		}
		if(request.getSession().getAttribute("USER_INFO")!=null)
		{
			UserMstr userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
			userLoginId =  userBean.getUserLoginId();
		}
		return userLoginId;
	}
	
	/**
	 * Method to return user Id of the logged in user
	 * @param request
	 * @return UserBean containing all the details
	 */
	public static UserMstr getUserDetails(HttpServletRequest request)
	{
		UserMstr userBean = new UserMstr();
		if(request.getSession().getAttribute("USER_INFO")!=null)
		{
			userBean = (UserMstr)request.getSession().getAttribute("USER_INFO");
		}
		return userBean;
	}
	
	
	
}
