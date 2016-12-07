package com.ibm.lms.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.exception.ValidationException;
import com.ibm.appsecure.service.GSDService;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.UserMstrServiceImpl;


/**
 * @version 	1.0
 * @author		Anil
 */
public class ChangePasswordAction extends DispatchAction {

	/**
 	*  Logger for this class.
 	**/
	private static Logger logger =
		Logger.getLogger(ChangePasswordAction.class.getName());

	/* Local Variables */
	private static String INITCHANGEPASSWORD_SUCCESS =
		"initchangePasswordSuccess";
	private static String CHANGEPASSWORD_SUCCESS = "changePasswordSuccess";

	private static String CHANGEPASSWORD_FAILURE = "changePasswordFailure";
	private static String CHANGEPASSWORD_FIRSTLOGINFAILURE = "changePasswordFistLoginFailure";
	/**
	*Initializes the change password for. Opens a seperate page for CSR user
	**/
	public ActionForward init(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
			ActionForward forward=new ActionForward();
			HttpSession session = request.getSession();
			UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
			try{
				logger.info(sessionUserBean.getUserLoginId()+" Entered into the init method of KmChangePasswordAction");
				String loginActorId=sessionUserBean.getKmActorId();
				logger.info("Actor id "+loginActorId);
				/*if(loginActorId.equals(Constants.CIRCLE_CSR)||loginActorId.equals(Constants.CATEGORY_CSR))
					forward=mapping.findForward("initCSRChangePassword");
				else*/
					forward= mapping.findForward(INITCHANGEPASSWORD_SUCCESS);
			}catch(Exception e){
				logger.error("Exception occured while initializing change password page "+e.getMessage());
				
			}
			return (forward);
	}
	
	public ActionForward initFirstLoginPwdChange(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
			ActionForward forward=new ActionForward();
			HttpSession session = request.getSession();
			UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
			try{
				logger.info(sessionUserBean.getUserLoginId()+" Entered into the init method of KmChangePasswordAction");
				String loginActorId=sessionUserBean.getKmActorId();
				logger.info("Actor id "+loginActorId);
				/*if(loginActorId.equals(Constants.CIRCLE_CSR)||loginActorId.equals(Constants.CATEGORY_CSR))
					forward=mapping.findForward("initCSRChangePasswordFirstLogin");
				else*/
					forward= mapping.findForward("initChangePasswordFirstLogin");
			}catch(Exception e){
				logger.error("Exception occured while initializing change password page "+e.getMessage());
				
			}
			return (forward);
	}

// Reset password will be done by change password link : defect no. MASDB00060767	
	
	/**
	*Changes the user password for the logged in user
	**/
	public ActionForward changePassword(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionErrors errors = new ActionErrors();
		ActionMessages messages =new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
	//	KmChangePasswordFormBean kmChangePasswordFormBean =
	//		(KmChangePasswordFormBean) form;
		DynaActionForm kmChangePasswordFormBean		=	(DynaActionForm) form;
		HttpSession session = request.getSession();
		String loginActorId=null;
		UserMstr sessionUserBean =
			(UserMstr) session.getAttribute("USER_INFO");
		//Boolean loginFlag = (Boolean)session.getAttribute("CSRFIRSTLOGIN");
			//logger.info("loginFlag?????????????????????"+sessionUserBean.getLastLoginTime());
		try {
				logger.info(sessionUserBean.getUserLoginId()+" Entered into the changePassword method of KmChangePasswordAction");
				loginActorId=sessionUserBean.getKmActorId();
				
				//	Encryption Code for Password 
				IEncryption encrypt = new Encryption();

				UserMstr userMstrDto = new UserMstr();
				UserMstrService userService = new UserMstrServiceImpl();
				userMstrDto.setOldPassword(
				encrypt.generateDigest((String) kmChangePasswordFormBean.get("oldPassword")));
				
				String newPassword = ( (String) kmChangePasswordFormBean.get("newPassword"));
				
				userMstrDto.setNewPassword(encrypt.generateDigest(newPassword));
				userMstrDto.setUserLoginId(sessionUserBean.getUserLoginId());
				userMstrDto.setUserId(sessionUserBean.getUserId());
			
			    String flag= (String)request.getParameter("flagLogin");
				String memberLogin= (String)request.getParameter("memberLogin");
			    if(flag.equals("true")) {
						request.setAttribute("csrFirstLogin","true");
				}
			    if(memberLogin.equals("true")) {
					request.setAttribute("FirstLogin","true");
				} 
			  
				if (userMstrDto
					.getOldPassword()
					.equals(sessionUserBean.getUserPassword()) && newPassword.length() <= 250) {
					
					 String passwordChars = PropertyReader.getAppValue("specialChars");
				     int count = 0;
				     for (int i = 0; i <newPassword.length(); i++) {
								if (passwordChars.indexOf(newPassword.charAt(i)) >= 0) {
									count++;
								} 
				     }
				     if(count < 1)
				     {
				    	 /*if(loginActorId.equals(Constants.CIRCLE_CSR)||loginActorId.equals(Constants.CATEGORY_CSR)){
				    		 forward=mapping.findForward("initCSRChangePassword"); 
				    	 }				    	       
							else{*/
								if("".equals(sessionUserBean.getLastLoginTime()) || sessionUserBean.getLastLoginTime()==null )
									forward = mapping.findForward(CHANGEPASSWORD_FIRSTLOGINFAILURE);
								else
									forward = mapping.findForward("changePasswordFailureInt");
							//}
								
							
				    	 errors.add("errors.incorrectPassword",new ActionError("msg.security.id015"));
							saveErrors(request, errors);
							 return forward;
				     }
				     
				     String numericChars = PropertyReader.getAppValue("numericChars");
				      count = 0;
				     for (int i = 0; i <newPassword.length(); i++) {
								if (numericChars.indexOf(newPassword.charAt(i)) >= 0) {
									count++;
								} 
				     }
				     if(count < 1)
				     {
				    	/* if(loginActorId.equals(Constants.CIRCLE_CSR)||loginActorId.equals(Constants.CATEGORY_CSR)){
				    		 forward=mapping.findForward("initCSRChangePassword"); 
				    	 }				    	       
							else{*/
								if("".equals(sessionUserBean.getLastLoginTime()) || sessionUserBean.getLastLoginTime()==null)
									forward = mapping.findForward(CHANGEPASSWORD_FIRSTLOGINFAILURE);
								else
									forward = mapping.findForward("changePasswordFailureInt");
							//}
							
				    	 errors.add("errors.incorrectPassword",new ActionError("msg.security.id016"));
							saveErrors(request, errors);
							 return forward;
				     }
				     
				     String upperChars = PropertyReader.getAppValue("upperChars");
				      count = 0;
				     for (int i = 0; i <newPassword.length(); i++) {
								if (upperChars.indexOf(newPassword.charAt(i)) >= 0) {
									count++;
								} 
				     }
				     if(count < 1)
				     {
				    	 /*if(loginActorId.equals(Constants.CIRCLE_CSR)||loginActorId.equals(Constants.CATEGORY_CSR)){
				    		 forward=mapping.findForward("initCSRChangePassword"); 
				    	 }				    	       
							else{*/
								if("".equals(sessionUserBean.getLastLoginTime()) || sessionUserBean.getLastLoginTime()==null)
									forward = mapping.findForward(CHANGEPASSWORD_FIRSTLOGINFAILURE);
								else
									forward = mapping.findForward("changePasswordFailureInt");
							//}
							
				    	 errors.add("errors.incorrectPassword",new ActionError("msg.security.id017"));
							saveErrors(request, errors);
							 return forward;
				     }
				     
				     String lowerChars = PropertyReader.getAppValue("lowerChars");
				      count = 0;
				     for (int i = 0; i <newPassword.length(); i++) {
								if (lowerChars.indexOf(newPassword.charAt(i)) >= 0) {
									count++;
								} 
				     }
				     if(count < 1)
				     {
				    	 /*if(loginActorId.equals(Constants.CIRCLE_CSR)||loginActorId.equals(Constants.CATEGORY_CSR)){
				    		 forward=mapping.findForward("initCSRChangePassword"); 
				    	 }				    	       
							else{*/
								if("".equals(sessionUserBean.getLastLoginTime()) || sessionUserBean.getLastLoginTime()==null)
									forward = mapping.findForward(CHANGEPASSWORD_FIRSTLOGINFAILURE);
								else
									forward = mapping.findForward("changePasswordFailureInt");
							//}
							
				    	 errors.add("errors.incorrectPassword",new ActionError("msg.security.id020"));
							saveErrors(request, errors);
							 return forward;
				     }
				
					//Passwd contains 3 or more chars from UserId.	
						int pwdLen = newPassword.length();
						for(int ii = 0;  ii <pwdLen-2; ii++)
						{
							if (sessionUserBean.getUserLoginId().contains(newPassword.substring(ii, ii+3)))
							{
								/* if(loginActorId.equals(Constants.CIRCLE_CSR)||loginActorId.equals(Constants.CATEGORY_CSR)){
						    		 forward=mapping.findForward("initCSRChangePassword"); 
						    	 }				    	       
									else{*/
										if("".equals(sessionUserBean.getLastLoginTime()) || sessionUserBean.getLastLoginTime()==null)
											forward = mapping.findForward(CHANGEPASSWORD_FIRSTLOGINFAILURE);
										else
											forward = mapping.findForward("changePasswordFailureInt");
									//}
								
					    	    errors.add("errors.incorrectPassword",new ActionError("msg.security.id022"));
								saveErrors(request, errors);
								 return forward;
							}
							
						}
						
					
				      boolean countFlag = userService.validateLastThreePasswords(userMstrDto.getUserLoginId(),userMstrDto.getNewPassword());
				     
				     if(countFlag)
				     {
				    	/* if(loginActorId.equals(Constants.CIRCLE_CSR)||loginActorId.equals(Constants.CATEGORY_CSR)){
				    		 forward=mapping.findForward("initCSRChangePassword"); 
				    	 }				    	       
							else{*/
								if("".equals(sessionUserBean.getLastLoginTime()) || sessionUserBean.getLastLoginTime()==null)
									forward = mapping.findForward(CHANGEPASSWORD_FIRSTLOGINFAILURE);
								else
									forward = mapping.findForward("changePasswordFailureInt");
							//}
							
				    	 errors.add("errors.incorrectPassword",new ActionError("msg.security.id018"));
							saveErrors(request, errors);
							 return forward;
				     }
			
				     		/* 
						 * GSD implementation to validate change password
					*/
					GSDService gsdDService = new GSDService();
					gsdDService.validateChangePwd(
						userMstrDto.getUserLoginId(),
						(String) kmChangePasswordFormBean.get("newPassword"),
						(String) kmChangePasswordFormBean.get("oldPassword"));

					//KmUserMstrService userService = new KmUserMstrServiceImpl();

					//Change password service called
					
					
					int changeCount =
						userService.changePasswordService(userMstrDto);
						
					if (changeCount == 1) {
						
						messages.add("msg",new ActionMessage("login.success"));
						saveMessages(request,messages);

						//kmChangePasswordFormBean.set("message",
					//		"Logging off......Please Use the New Password for Logging in");
						logger.info("Password changed successfully");	
						//Setting user information in session
						session.setAttribute("USER_CHANGEPWD", userMstrDto.getNewPassword());
						//sessionUserBean.setNewPassword(userMstrDto.getNewPassword());						
						
						kmChangePasswordFormBean.set("oldPassword","");
					    kmChangePasswordFormBean.set("newPassword","");
						kmChangePasswordFormBean.set("confirmPassword","");
						
						UserMstr userBean = (UserMstr) request.getSession().getAttribute(
						"USER_INFO");
						userBean.setUserLoginStatus("Y");
						userService.updateUserStatus(userBean);	
						
						/*if(loginActorId.equals(Constants.CIRCLE_CSR)||loginActorId.equals(Constants.CATEGORY_CSR))
							forward=mapping.findForward("initCSRChangePassword");
						else*/
						
						
						
							forward = mapping.findForward(CHANGEPASSWORD_SUCCESS);
					} else {
						kmChangePasswordFormBean.set("oldPassword","");
						kmChangePasswordFormBean.set("newPassword","");
						kmChangePasswordFormBean.set("confirmPassword","");
						/* if(loginActorId.equals(Constants.CIRCLE_CSR)||loginActorId.equals(Constants.CATEGORY_CSR)){
				    		 forward=mapping.findForward("initCSRChangePassword"); 
				    	 }				    	       
							else{*/
								if("".equals(sessionUserBean.getLastLoginTime()) || sessionUserBean.getLastLoginTime()==null)
									forward = mapping.findForward(CHANGEPASSWORD_FIRSTLOGINFAILURE);
								else
									forward = mapping.findForward("changePasswordFailureInt");
							//}
						errors.add("errors.incorrectPassword",new ActionError("errors.changePasswordNetworkError"));
						saveErrors(request, errors);							
					}

				} else {
					kmChangePasswordFormBean.set("oldPassword","");
					kmChangePasswordFormBean.set("newPassword","");
					kmChangePasswordFormBean.set("confirmPassword","");
					logger.info("Incorrect password is entered");
					if(newPassword.length() > 250)
					{
						errors.add("errors.incorrectPassword",new ActionError("msg.security.id019"));
					}
					else
					{
						// updating Wrong Password Count
			        	userService.updateLoginAttempted(sessionUserBean.getUserLoginId());
			        	
				        int wrongPwdCount = 0;
				        int PasswordDisableLimit =Integer.parseInt(PropertyReader.getGsdValue("PasswordDisableLimit"));
				        wrongPwdCount = userService.getWrongPwdCount(sessionUserBean.getUserLoginId());
				        logger.info("Disable value ---> " + wrongPwdCount);
				        logger.info("Disable Limit value ---> " + PasswordDisableLimit);

				        if (PasswordDisableLimit <= wrongPwdCount )
				        {
				            errors.add("errors",new ActionError("msg.security.id014"));
				            saveErrors(request, errors);
				            forward = mapping.findForward("changePasswordFailureLogout");
				            return forward;
				        }
				        else
				        {
				        	errors.add("errors",new ActionError("errors.incorrectPassword"));
				        }
				    }
						
					saveErrors(request, errors);
					
					
				
					if("".equals(sessionUserBean.getLastLoginTime()) || sessionUserBean.getLastLoginTime()==null)
						forward = mapping.findForward(CHANGEPASSWORD_FIRSTLOGINFAILURE);
					else
						forward = mapping.findForward("changePasswordFailureInt");
						

				}
			
		} catch (EncryptionException e) {
			logger.error(
				"EncryptionException in KmChangePasswordAction: "
					+ e.getMessage());
			saveErrors(request, errors);
			 /*if(loginActorId.equals(Constants.CIRCLE_CSR)||loginActorId.equals(Constants.CATEGORY_CSR)){
	    		 forward=mapping.findForward("initCSRChangePassword"); 
	    	 }				    	       
				else{*/
					if("".equals(sessionUserBean.getLastLoginTime()) || sessionUserBean.getLastLoginTime()==null)
						forward = mapping.findForward(CHANGEPASSWORD_FIRSTLOGINFAILURE);
					else
						forward = mapping.findForward("changePasswordFailureInt");
				//}
		} catch (ValidationException ve) {
			kmChangePasswordFormBean.set("oldPassword","");
			kmChangePasswordFormBean.set("newPassword","");
			kmChangePasswordFormBean.set("confirmPassword","");
			errors.add(
				"errors.changePassword",
				new ActionError(ve.getMessageId()));
			logger.error(
				"ValidationException in KmChangePasswordAction: "
					+ ve.getMessage());
			saveErrors(request, errors);
			 /*if(loginActorId.equals(Constants.CIRCLE_CSR)||loginActorId.equals(Constants.CATEGORY_CSR)){
	    		 forward=mapping.findForward("initCSRChangePassword"); 
	    	 }				    	       
				else{*/
					if("".equals(sessionUserBean.getLastLoginTime()) || sessionUserBean.getLastLoginTime()==null)
						forward = mapping.findForward(CHANGEPASSWORD_FIRSTLOGINFAILURE);
					else
						forward = mapping.findForward("changePasswordFailureInt");
				//}
		} catch (Exception e) {

			logger.info(e);
			logger.error(
				"Exception in KmChangePasswordAction: " + e.getMessage());
			 /*if(loginActorId.equals(Constants.CIRCLE_CSR)||loginActorId.equals(Constants.CATEGORY_CSR)){
	    		 forward=mapping.findForward("initCSRChangePassword"); 
	    	 }				    	       
				else{*/
					if("".equals(sessionUserBean.getLastLoginTime()) || sessionUserBean.getLastLoginTime()==null)
						forward = mapping.findForward(CHANGEPASSWORD_FIRSTLOGINFAILURE);
					else
						forward = mapping.findForward("changePasswordFailureInt");
				//}

		}

		return (forward);

	}
	
}
