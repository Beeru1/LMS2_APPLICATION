/*
 * Created on Jun 13, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.lms.common;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.UserMstrServiceImpl;

/**
 * @author 
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SessionListener  implements HttpSessionListener {
	private static final Logger logger;
	static {
		logger = Logger.getLogger(SessionListener.class);
	}
	
	public void sessionCreated(HttpSessionEvent arg0) {
		
		    logger.info("Session Created.  session id : "+arg0.getSession().getId());
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		 logger.info("Session Destroyed. session id : "+arg0.getSession().getId());
		UserMstr sessionUserBean =
			(UserMstr) arg0.getSession().getAttribute("USER_INFO");
		
		String actorId ="";
		try {
			if(sessionUserBean!=null) {
				actorId= sessionUserBean.getKmActorId();
				logger.info("actorID------" + actorId);
				
				/*if (actorId.equals(Constants.CIRCLE_CSR) || actorId.equals(Constants.CATEGORY_CSR)) {
					sessionUserBean.setUserLoginStatus("N");
					KmUserMstrService userService= new KmUserMstrServiceImpl();
					userService.updateUserStatus(sessionUserBean);	
				  logger.info("inside................/errors/sessionTimeout_CSR.jsp");
		
				} else {*/
				if(sessionUserBean.getUserLoginStatus().equalsIgnoreCase("Y")) {
					sessionUserBean.setUserLoginStatus("N");
					sessionUserBean.setSessionID(arg0.getSession().getId());
					UserMstrService userService= new UserMstrServiceImpl();
					userService.updateUserStatus(sessionUserBean);
					logger.info("inside................/errors/sessionTimeout.jsp ");
				}
			/*
				}*/
			
			}	
		} catch (Exception e)
		 {
		 	e.printStackTrace();
			// TODO: handle exception
		}
	}
}
