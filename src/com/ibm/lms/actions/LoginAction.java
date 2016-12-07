
/*
 * Created on Jan 18, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.lms.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.exception.ValidationException;
import com.ibm.appsecure.service.GSDService;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.km.dto.KmElementMstr;
import com.ibm.km.services.KmElementMstrService;
import com.ibm.km.services.KmScrollerMstrService;
import com.ibm.km.services.UserService;
import com.ibm.km.services.impl.KmElementMstrServiceImpl;
import com.ibm.lms.common.CommonMstrUtil;
import com.ibm.lms.common.Constants;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.dto.LinkMstrDto;
import com.ibm.lms.dto.Login;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.LoginFormBean;
import com.ibm.lms.services.KmLinkMstrService;
import com.ibm.lms.services.LoginService;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.LinkMstrServiceImpl;
import com.ibm.lms.services.impl.LoginServiceImpl;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.services.impl.UserMstrServiceImpl;


/**
 * @author namangup
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LoginAction extends Action {

	//private static final String RedirectedURL = "www.google.co.in";
	private static Logger logger = Logger.getLogger(LoginAction.class.getName());
	/* Local Variables */
	private static String AUTHENTICATION_SUCCESS = "loginSuccess";

	private static String FORGOTPASSWORD = "forgotPassword";

	private static String CSRLOGIN_SUCCESS = "csrLoginSuccess";

	private static String CSR_AUTHENTICATION_FAILURE = "csrLoginFailure";

	private static String FORGOTPASSWORD_SUCCESS = "forgotPwdSuccess";

	private static String HOME_SUCCESS = "home";

	private static String HELP = "help";

	private static String OTP_USERS="otp_page";

	private static String OTP_PAGE= "displayOTPPage";
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

		java.util.Date dt1 = new java.util.Date();

		ActionErrors errors = new ActionErrors();
		ActionMessages messages=new ActionMessages();
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		LoginFormBean loginformBean=null;
		loginformBean = (LoginFormBean) form;
		Login login = new Login();
		ArrayList userRoleList = new ArrayList();
		KmLinkMstrService linkMstrService = new LinkMstrServiceImpl();
		UserMstrService userService = null;
		String appL1="";
		String appL2="";
		String approvalPending = CommonMstrUtil.getPendingapprovalsCount(loginformBean.getUserId());
		String redirectLink=CommonMstrUtil.getRedirectedLink();
		request.setAttribute("RedirectLink",redirectLink);
		logger.info(loginformBean.getUserId() + " in login attempt from ip:" + getAddressInfo(request) );
		String otp_pass="";
		String checkUser="";
		try {
			
			if(approvalPending !=null && approvalPending.trim().length() >0)
			{
			String[] ids = approvalPending.split(",");
			appL1= ids[0];	//3		
			appL2 = ids[1];  //0
			}
			
			if(appL1 !=null && appL1.trim().length() >0 && Integer.parseInt(appL1)>0 && Integer.parseInt(appL1)<9 )  //pending for L1 approval:
			{
				appL1 ="0".concat(appL1);
			}
					
			if(appL2 !=null && appL2.trim().length() >0 &&Integer.parseInt(appL2)>0 && Integer.parseInt(appL2)<9)   //pending for l2 approval:
			{
				appL2 ="0".concat(appL2);
				
			}
			

			if ("/initForgotPassword".equalsIgnoreCase(mapping.getPath())) {

				String kmForgot=request.getParameter("KmForgot");

				if(kmForgot!=null &&  kmForgot.equals("true")) {
					forward = mapping.findForward("kmForgotPassword");
				}
				else {
					forward = mapping.findForward(FORGOTPASSWORD);
				}

			}

			if ("/forgotPassword".equalsIgnoreCase(mapping.getPath())) {
				String userId=loginformBean.getUserName();
				String  ipaddress=null;
				//logger.info("USER ID"+userId);
				
				   if(request.getParameter("ipaddress") != null && !request.getParameter("ipaddress").equals("") ) {
				   ipaddress = request.getParameter("ipaddress");
				  // logger.info("IPADDRESSsssssssssssss"+ipaddress);
					logger.info(request.getHeader("X-Forwarded-For") + "X-Forwarded-For=== ipaddress in forgot password From the Machine with IP : "+ipaddress);
				   }

						//logger.info("IPADDRESS"+ipaddress);
				   			logger.info(loginformBean.getUserName() + " is using forgot password ====X-Forwarded-For==="+request.getHeader("X-Forwarded-For")+"===========module From the Machine with IP : "+request.getRemoteAddr());

							String csrStatus="";
							csrStatus=request.getParameter("CSR");
							//String kmPass="";
							String kmPass =loginformBean.getKmforgot();
							//kmPass=(String)request.getAttribute("PASSWORDKM");
							//logger.info("kmpass"+kmPass);
							LoginService loginService= new LoginServiceImpl();
							
							//UserMstr userBean = loginService.populateUserDetails(login);
							//Added to capture Loginid and ip details
							UserMstr userBean=new UserMstr();
							userBean.setUserLoginId(userId);
							
							
							String ipXForwarded  = request.getHeader("X-Forwarded-For");
							   if(ipXForwarded != null && ipXForwarded.length() >7) {
								   userBean.setIpaddress(ipXForwarded);
								   //logger.info("IP address 1"+userBean.getIpaddress());
								   logger.info(loginformBean.getUserName() + "ipaddress: setting ip address X-Forwarded-For : "+ipXForwarded);
							   }
							   else if(request.getParameter("ipaddress") != null && !request.getParameter("ipaddress").equals("") ) {
								   userBean.setIpaddress(request.getParameter("ipaddress"));
								   logger.info(loginformBean.getUserName() + "ipaddress: setting ip address : "+request.getParameter("ipaddress"));
								   
							   }
							   else {
								   userBean.setIpaddress(request.getRemoteAddr() );
								   logger.info(" setting getRemoteAddr : "+ request.getRemoteAddr());
							   }
							
							userService=new UserMstrServiceImpl();
							//Getting the user details and email id .
							ArrayList alist=loginService.getEmailId(loginformBean.getUserName());
							//	ArrayList alist=kmLoginDaoImpl.getEmailId(loginformBean.getUserName());
							String actorId="";
							String emailId="";
							String userLoginId="";
							String status="";

							if(alist!=null) {
								userLoginId=(String)alist.get(0);
								emailId=(String)alist.get(1);
								actorId=(String)alist.get(2);
								status=(String) alist.get(3);
								if(Integer.parseInt((String) alist.get(4))<6){
									errors.add("loginId", new ActionError("password.reset.retry"));
									saveErrors(request, errors);
									if(csrStatus.equals("TRUE")) {
										forward = mapping.findForward("forgotPassword");
										return (forward);
									}
									else{
										forward = mapping.findForward("kmForgotPassword");
										return (forward);
									}
								}
								if(!status.equalsIgnoreCase("A")){
									errors.add("loginId", new ActionError("login.user.deactivated"));
									saveErrors(request, errors);
									if(csrStatus.equals("TRUE")) {
										forward = mapping.findForward("forgotPassword");
										return (forward);
									}
									else{
										forward = mapping.findForward("kmForgotPassword");
										return (forward);
									}
								}

							}
							else {
								errors.add("loginId", new ActionError("login.invalid.login"));
								saveErrors(request, errors);
								if(csrStatus.equals("TRUE")) {
									forward = mapping.findForward("forgotPassword");
									return (forward);
								}
								else{
									forward = mapping.findForward("kmForgotPassword");
									return (forward);
								}
							}


							if(userLoginId.equals("")) {
								if(csrStatus.equals("TRUE")) {
									forward = mapping.findForward("forgotPassword");
									return (forward);
								}
								else {
									if(userLoginId.equals("")) {
										errors.add("loginId", new ActionError("errors.login.invalid_id"));
										saveErrors(request, errors);
									}
									forward = mapping.findForward("kmForgotPassword");
									return (forward);
								}

							}
							String strFromEmail = null;

						//	String UDAdmin=getResources(request).getMessage("UD.Admin")
						//	.toString();
							strFromEmail = getResources(request).getMessage("login.email")
											.toString();
									String strSubject = "Your LMS Password";
									String strMessage = null;
									String txtMessage = null;
									StringBuffer sbMessage = new StringBuffer();
									logger.info("strFromEmail : "+strFromEmail);
									//String strHost = getResources(request).getMessage("login.smtp").toString();
									String strHost = PropertyReader.getAppValue("LOGIN.SMTP");
									logger.info("strHost : "+strHost);
							IEncryption t = new Encryption();

							if(!userLoginId.equalsIgnoreCase("")) {
								if(!emailId.equalsIgnoreCase("")) {
									sbMessage.append("Dear " + loginformBean.getUserName()
																+ ", \n\n");
														sbMessage.append("Your LMS password is : ");
						//strMessage = loginformBean.getUserName().substring(0, 1) Math.abs(new Random().nextInt()) loginformBean.getUserName().substring(2, 3);

						// Security closer for new generated password as GSD complaint ...
					    strMessage = generatePassword(loginformBean.getUserName());
					    // ---------------------------------------------------------------

									String encPassword = t.generateDigest(strMessage);
									sbMessage.append(strMessage +"\n");
														sbMessage.append("\nRegards ");
														sbMessage.append("\nLMS Administrator ");
														sbMessage.append("\n\n/** This is an Auto generated email.Please do not reply to this.**/");
														txtMessage = sbMessage.toString();
									try {
											Properties prop = System.getProperties();
											prop.put("mail.smtp.host", strHost);
											Session ses = Session.getDefaultInstance(prop, null);
											MimeMessage msg = new MimeMessage(ses);
											msg.setFrom(new InternetAddress(strFromEmail));
											msg.addRecipient(Message.RecipientType.TO,
													new InternetAddress(emailId));
										//	msg.addRecipient(Message.RecipientType.CC,new InternetAddress(UDAdmin));
											msg.setSubject(strSubject);
											msg.setText(txtMessage);
											msg.setSentDate(new Date());
											Transport.send(msg);
											// Changes are included in login.jsp and csrLogin.jsp
											messages.add("msg1",new ActionMessage("password.sent",emailId));
											saveMessages(request,messages);
										//	kmLoginDaoImpl.updatePasswordExpiryDate(userLoginId);
											//Changed by Anil against code review defect : MASDB00098318
											loginService.updatePassword(loginformBean.getUserName(),encPassword);
											//Login id and ip address and element id entry go to KM_LOGIN_DATA table
											userService.updateForgotPasswordUser(userBean);
										//	kmLoginDaoImpl.updatePassword(loginformBean.getUserName(),encPassword);
											loginformBean.setMessage("Password is sent to your mailid : "+emailId);

												return  mapping.findForward(FORGOTPASSWORD_SUCCESS);

										} catch (javax.mail.internet.AddressException ae) {
											errors.add("errors.forgotPassword", new ActionError("error.forgotPassword"));
											saveErrors(request, errors);
											forward = mapping.findForward(FORGOTPASSWORD_SUCCESS);
											logger.error("AddressException occurs in execute of Login Action: "+ ae.getMessage());
										} catch (javax.mail.MessagingException me) {
											errors.add("errors.forgotPassword", new ActionError("error.server.forgotPassword"));
											forward = mapping.findForward(FORGOTPASSWORD_SUCCESS);
											logger.error("MessagingException occurs in execute of Login Action: "+ me.getMessage());
											saveErrors(request, errors);
										}
										catch(Exception e){
											forward = mapping.findForward(FORGOTPASSWORD_SUCCESS);

										}
														//logger.info("strMessage"+strMessage);




								}
								else {
									errors.add("loginId", new ActionError("login.noEmailId"));
									saveErrors(request, errors);
									if(csrStatus!=null && csrStatus.equals("TRUE")){
										forward=mapping.findForward("forgotPassword");
									}else{
										forward = mapping.findForward("kmForgotPassword");
									}
								}
							}
							else {
								errors.add("loginId", new ActionError("login.invalid.login"));
								saveErrors(request, errors);
								if(csrStatus!=null && csrStatus.equals("TRUE")){
										forward=mapping.findForward("csrLoginFailure");
								}else{
										forward = mapping.findForward("loginFailure");
								}
							}

						}
			if ("/home".equalsIgnoreCase(mapping.getPath()))
			{
				

				forward = mapping.findForward(HOME_SUCCESS);

			}

			if("/help".equalsIgnoreCase(mapping.getPath()))
			{
				forward = mapping.findForward(HELP);

			}

			if ("/login".equalsIgnoreCase(mapping.getPath())) {

				/*String userId=(String)request.getAttribute("userid");
				String password=(String)request.getAttribute("password");*/
				String userId = request.getHeader("iv-user");
				String ipAddress = request.getHeader("iv-remote-address");
				if(userId!=null && !userId.equalsIgnoreCase("") && ipAddress!=null && !ipAddress.equalsIgnoreCase("")){
				logger.info("User id"+userId);
				//logger.info("Password"+password);
				//userId="A1247292";
				//logger.info("User id"+userId);
					loginformBean.setUserId(userId.toUpperCase());
					//loginformBean.setPassword(password);
				//IEncryption encrypt = new Encryption();
				login.setUserId(userId.toUpperCase());
				login.setIpaddress(ipAddress);

				//login.setPassword(encrypt.generateDigest(loginformBean.getPassword()));
				//session.setAttribute("login", login);

				////logger.info(login.getPassword());
				 userService = new UserMstrServiceImpl();
				 LoginService loginService = new LoginServiceImpl();
				 MasterService masterService = new MasterServiceImpl();
				 logger.info("User id and ip address : "+userId+" "+ipAddress);
				if(!userService.checkDuplicateUserLogin(loginformBean.getUserId().toUpperCase())){
					errors.add("errors.login.userExpire", new ActionError("errors.login.userExpire"));
					saveErrors(request, errors);
					logger.error("Expired Login Id");
				return mapping.findForward("userExpireNotification");
								}
				else
				{	
					//logger.info("BLOCK HERE--------------");
					if("Y".equalsIgnoreCase(masterService.getParameterName("OTP_FLAG")))
					{
						//logger.info("BLOCK HERE--------------88888");
					checkUser=CommonMstrUtil.ifUserActive(loginformBean.getUserId());
					if(!("").equalsIgnoreCase(checkUser) && checkUser.length()>0){
					otp_pass=loginService.sendSMS(loginformBean.getUserId(),checkUser);
					login.setOtp(otp_pass);
					session.setAttribute("login",login);
					//logger.info("otp in action is*******************"+login.getOtp());
					//logger.info("username and password are^^^^^^^^^^^^^^^^^^^"+login.getUserId()+"^^^^^^"+login.getPassword());
					forward = mapping.findForward(OTP_USERS);
					return(forward);
					}
				}
			}
			

              // LDAP user validation 
				if("Y".equalsIgnoreCase(PropertyReader.getAppValue("doLdapValidation")))
				{
					logger.info("Checking LDAP as ValidateLDAP is "+PropertyReader.getAppValue("doLdapValidation"));
					try
					{
						if(!loginService.isValidUser(loginformBean.getUserId().toUpperCase()))
						{
			                errors.add("errors.login.user_invalid", new ActionError("login.ldapValidationError"));
			 				saveErrors(request, errors);
			 				logger.info("User LDAP validation failed for user : "+loginformBean.getUserId());
			 				forward=mapping.findForward(CSR_AUTHENTICATION_FAILURE);
			 				return (forward);
						}
					}catch(Exception ee)
					{
						 errors.add("errors.login.user_invalid", new ActionError("login.ldapConnectionFail"));
			 				saveErrors(request, errors);
			 				logger.info("Connection couldn't established for the user : "+loginformBean.getUserId());
			 				forward=mapping.findForward(CSR_AUTHENTICATION_FAILURE);
			 				return (forward);
					}
				}
				//---------------------------- LDAP validation finish

				UserMstr userBean = loginService.populateUserDetails(login);
				ArrayList linksList = new ArrayList();
				ArrayList<LinkMstrDto> toplinksList = new ArrayList<LinkMstrDto>();

				toplinksList = linkMstrService.viewLinks(Constants.TOP_LINKS);

				userRoleList = linkMstrService.getUserRoleList(userBean.getKmActorId());

				ArrayList topBarLinks = new ArrayList();
				ArrayList bottomBarLinks = new ArrayList();



				// Multiple login disable; inmplemented by Kundan Kumar for security finding closer

				logger.info("Checking Multiple login... : "+userBean.getUserLoginStatus());

					//Setting user information in session
				    session.invalidate();
					session = request.getSession(true);
					
					
					
					session.setAttribute("appL1", appL1);
					session.setAttribute("appL2", appL2);
					//session.setAttribute("RedirectLink",redirectLink);
					
					//userBean = populatePathInfo(userBean, session);
					session.setAttribute("chpassword","Y");
					session.setAttribute("USER_INFO", userBean);
					session.setAttribute("LINKS_LIST",linksList);
					session.setAttribute("TOP_LINKS_LIST",toplinksList);
					session.setAttribute("USER_ROLE_LIST",userRoleList);
					session.setAttribute("TOPBAR_LINKS",topBarLinks);
					session.setAttribute("BOTTOMBAR_LINKS",bottomBarLinks);
					if(!Constants.SUPER_ADMIN.equalsIgnoreCase(userBean.getKmActorId()) && !Constants.CIRCLE_ADMIN.equalsIgnoreCase(userBean.getKmActorId())) {
						List<String> grouplinksList = new ArrayList<String>();
						for(Object dto : userRoleList) {
							LinkMstrDto dto2 =(LinkMstrDto)dto;
							grouplinksList.add(dto2.getLinkPath());
						}
						session.setAttribute("GROUP_LINKS_LIST",grouplinksList);
						}
					//Warning the User for pasword expiry
					//request.setAttribute("warn", loginService.getWarning(userBean.getUserPsswrdExpryDt()));

					logger.info("Actor ID: "+userBean.getKmActorId());

					forward = mapping.findForward(AUTHENTICATION_SUCCESS);
					  //Added by Anil as part of UD integration
					   userBean.setUserLoginStatus("Y");
					   userBean.setSessionID(session.getId());
					if(ipAddress!=null && ipAddress.length()>7)
					   {
						   userBean.setIpaddress(ipAddress);
					   }
					  // logger.info("IPAddress"+userBean.getIpaddress());
					   userService.updateUserStatus(userBean);
					   logger.info(loginformBean.getUserId() + " Logged in to the LMS From the Machine with IP : "+ipAddress);
				}
			else
				{
					errors.add("errors.login.null_id", new ActionError("errors.login.null_id"));
					saveErrors(request, errors);
					return mapping.findForward("loginFailure");
				}
			}


			//For OTP Users
			if("/OTPlogin".equalsIgnoreCase(mapping.getPath()))
			{
				logger.info("inside otp login mapping^^^^^^^^^^^^^^^^^^^^^^^^^^");
				login=(Login)session.getAttribute("login");
				//logger.info("LOOOO"+login);
				//String flag= loginformBean.getFlag();
				String flag=request.getParameter("flag");
				//logger.info("Login flag"+flag);
				//logger.info("Login----"+request.getParameter("flag"));
				//String flag=loginformBean.getFlag();
				//logger.info("flag is**************"+flag);
				LoginService service=new LoginServiceImpl();
				//logger.info("login printing************"+login.getIpaddress()+"****"+login.getUserId()+"****");
				
				//logger.info("flagg"+flag);
			if(flag!=null && flag.trim().length()>0 && flag.equalsIgnoreCase("true"))
			{
				otp_pass=service.sendSMS(login.getUserId(),CommonMstrUtil.ifUserActive(login.getUserId()));
				login.setOtp(otp_pass);
				session.setAttribute("login",login);
				//logger.info("otp in action is*******************"+login.getOtp());
				//logger.info("username and password are^^^^^^^^^^^^^^^^^^^"+login.getUserId()+"^^^^^^"+login.getPassword());
				forward = mapping.findForward(OTP_USERS);
				loginformBean.setOTPCode("");
				return(forward);
			}
				
			if(login!=null && login.getUserId()!=null && login.getOtp()!=null && !"".equalsIgnoreCase(login.getUserId())&& !"".equalsIgnoreCase(login.getOtp()))
				
			{
				//logger.info("loginnnnnnnn"+login.getUserId());
				UserMstr userBeans = service.populateUserDetails(login);
				userService = new UserMstrServiceImpl();
				//logger.info("inside OTPlogin block**********************");
				String OTPCode=request.getParameter("OTPCode");
				//logger.info("request.getParameter OTPCode"+request.getParameter("OTPCode"));
				//logger.info("login.getOtp()*****"+login.getOtp());
				//logger.info("OTPCode.equalsIgnoreCase(login.getOtp()))"+OTPCode.equalsIgnoreCase(login.getOtp()));
				if(OTPCode!=null&&!"".equalsIgnoreCase(OTPCode)&& OTPCode.equalsIgnoreCase(login.getOtp())) 
				{
					ArrayList linksList = new ArrayList();
					ArrayList<LinkMstrDto> toplinksList = new ArrayList<LinkMstrDto>();
					toplinksList = linkMstrService.viewLinks(Constants.TOP_LINKS);
					userRoleList = linkMstrService.getUserRoleList(userBeans.getKmActorId());
					ArrayList topBarLinks = new ArrayList();
					ArrayList bottomBarLinks = new ArrayList();
					logger.info("Checking Multiple login... : "+userBeans.getUserLoginStatus());
					session.invalidate();
					session = request.getSession(true);
					//userBean = populatePathInfo(userBean, session);
					session.setAttribute("chpassword","Y");
					session.setAttribute("appL1", appL1);
					session.setAttribute("appL2", appL2);
					session.setAttribute("USER_INFO", userBeans);
					session.setAttribute("LINKS_LIST",linksList);
					session.setAttribute("TOP_LINKS_LIST",toplinksList);
					session.setAttribute("USER_ROLE_LIST",userRoleList);
					session.setAttribute("TOPBAR_LINKS",topBarLinks);
					session.setAttribute("BOTTOMBAR_LINKS",bottomBarLinks);
					//request.setAttribute("warn", service.getWarning(userBeans.getUserPsswrdExpryDt()));
					if(!Constants.SUPER_ADMIN.equalsIgnoreCase(userBeans.getKmActorId()) && !Constants.CIRCLE_ADMIN.equalsIgnoreCase(userBeans.getKmActorId())) {
						List<String> grouplinksList = new ArrayList<String>();
						for(Object dto : userRoleList) {
							LinkMstrDto dto2 =(LinkMstrDto)dto;
							grouplinksList.add(dto2.getLinkPath());
						}
						session.setAttribute("GROUP_LINKS_LIST",grouplinksList);
						}
					logger.info("Actor ID: "+userBeans.getKmActorId());
					//logger.info("correct login block*****************");
					forward = mapping.findForward(AUTHENTICATION_SUCCESS);
					userBeans.setUserLoginStatus("Y");
					userBeans.setSessionID(session.getId());
					logger.info(login.getUserId() + " Logged in to the LMS From the Machine with IP : "+request.getRemoteAddr());
					//displayHomePage(userBeans, request, session, login, userRoleList, linkMstrService);
					userBeans.setIpaddress(login.getIpaddress());
					userService.updateUserStatus(userBeans);
					}
					else
					{
						if(OTPCode!=null&&!"".equalsIgnoreCase(OTPCode)){
					errors.add("errors.login.OTPVerification.failed", new ActionError("errors.login.OTPVerification.failed"));
					saveErrors(request, errors);
					logger.error("OTP not matched in LMS");
					loginformBean.setOTPCode("");
					return mapping.findForward("OTPAuthnticationFailure");
						}
						else
						{
							return mapping.findForward("loginFailure");
						}
					
					}
			}
			else
			{
				return mapping.findForward("loginFailure");
			}
		}
			
			
			if("/Altlogin".equalsIgnoreCase(mapping.getPath()))
			{
				String userId=(String)request.getAttribute("userid");
				String password=(String)request.getAttribute("password");

				/*if(userId ==null || userId.length() <=0  || ( password == null || password.length() <= 0) )
				 {
					return mapping.findForward("loginFailed");
				 }
				
				*/
				if(userId!=null){
					loginformBean.setUserId(userId);
					loginformBean.setPassword(password);
				}
				IEncryption encrypt = new Encryption();
				login.setUserId(loginformBean.getUserId());
				
				if(password!=null && password.trim().length()>0){

				login.setPassword(encrypt.generateDigest(loginformBean.getPassword()));

				}
				
				if(loginformBean.getUserId() ==null || loginformBean.getUserId().length() <=0  || ( loginformBean.getPassword() == null || loginformBean.getPassword().length() <= 0) )
				 {
					return mapping.findForward("loginFailed");
				 }
				////logger.info(login.getPassword());
				 userService = new UserMstrServiceImpl();
				 
				if(userId !=null && !userService.checkDuplicateUserLogin(loginformBean.getUserId().toUpperCase())){
					errors.add("errors.login.invalid_id", new ActionError("errors.login.invalid_id"));
					saveErrors(request, errors);
					logger.error("Invalid Login Id");
				return mapping.findForward("loginFail");
								}
					           
				
				GSDService gSDService = new GSDService();
				UserMstr userInfo =
					(UserMstr) gSDService.validateCredentials(
						login.getUserId(),
 						loginformBean.getPassword(),
						"com.ibm.lms.dto.UserMstr");

				

				LoginService loginService = new LoginServiceImpl();

              // LDAP user validation 
				if("Y".equalsIgnoreCase(PropertyReader.getAppValue("doLdapValidation")))
				{
					logger.info("Checking LDAP as ValidateLDAP is "+PropertyReader.getAppValue("doLdapValidation"));
					try
					{
						if(!loginService.isValidUser(loginformBean.getUserId()))
						{
			                errors.add("errors.login.user_invalid", new ActionError("login.ldapValidationError"));
			 				saveErrors(request, errors);
			 				logger.info("User LDAP validation failed for user : "+loginformBean.getUserId());
			 				forward=mapping.findForward(CSR_AUTHENTICATION_FAILURE);
			 				return (forward);
						}
					}catch(Exception ee)
					{
						 errors.add("errors.login.user_invalid", new ActionError("login.ldapConnectionFail"));
			 				saveErrors(request, errors);
			 				logger.info("Connection couldn't established for the user : "+loginformBean.getUserId());
			 				forward=mapping.findForward(CSR_AUTHENTICATION_FAILURE);
			 				return (forward);
					}
				}
				//---------------------------- LDAP validation finish

				UserMstr userBean = loginService.populateUserDetails(login);
				ArrayList linksList = new ArrayList();
				ArrayList<LinkMstrDto> toplinksList = new ArrayList<LinkMstrDto>();

				toplinksList = linkMstrService.viewLinks(Constants.TOP_LINKS);

				userRoleList = linkMstrService.getUserRoleList(userBean.getKmActorId());

				ArrayList topBarLinks = new ArrayList();
				ArrayList bottomBarLinks = new ArrayList();



				// Multiple login disable; inmplemented by Kundan Kumar for security finding closer

				logger.info("Checking Multiple login... : "+userBean.getUserLoginStatus());
/*
				if("Y".equals(userBean.getUserLoginStatus()))
				{
	                errors.add("errors.login.user_invalid", new ActionError("login.singleSignError"));
	 				saveErrors(request, errors);
	 				logger.info("Multiple login found for user : "+loginformBean.getUserId());
	 			 // userBean.setUserPassword("");
	 				forward=mapping.findForward(CSR_AUTHENTICATION_FAILURE);
	 				return (forward);
				}
				*/
				
				
				//---------------------------- Multiple login disable finish

				// Refreshing Cache for all the Master Table data List

			/*	if(userBean.getKmActorId().equals(Constants.SUPER_ADMIN))
				{
					new MasterServiceImpl().refreshCache();
				}
		 	*/
		/*	
				String sessionid = request.getSession().getId();
				// be careful overwriting: JSESSIONID may have been set with other flags
				response.setHeader("SET-COOKIE", "JSESSIONID=" + sessionid + "; HttpOnly");
		*/		

					//Setting user information in session
				    session.invalidate();
					session = request.getSession(true);
					//userBean = populatePathInfo(userBean, session);
					session.setAttribute("appL1", appL1);
					session.setAttribute("appL2", appL2);
					session.setAttribute("USER_INFO", userBean);
					session.setAttribute("LINKS_LIST",linksList);
					session.setAttribute("TOP_LINKS_LIST",toplinksList);
					session.setAttribute("USER_ROLE_LIST",userRoleList);
					session.setAttribute("TOPBAR_LINKS",topBarLinks);
					session.setAttribute("BOTTOMBAR_LINKS",bottomBarLinks);
					//Warning the User for pasword expiry
					request.setAttribute("warn", loginService.getWarning(userBean.getUserPsswrdExpryDt()));
					if(!Constants.SUPER_ADMIN.equalsIgnoreCase(userBean.getKmActorId()) && !Constants.CIRCLE_ADMIN.equalsIgnoreCase(userBean.getKmActorId())) {
						List<String> grouplinksList = new ArrayList<String>();
						for(Object dto : userRoleList) {
							LinkMstrDto dto2 =(LinkMstrDto)dto;
							grouplinksList.add(dto2.getLinkPath());
						}
						session.setAttribute("GROUP_LINKS_LIST",grouplinksList);
						}

					logger.info("Actor ID: "+userBean.getKmActorId());

					if(userBean.getLastLoginTime()==null || userBean.getLastLoginTime().equals("")) {
							  request.setAttribute("FirstLogin", "true");

							  forward = mapping.findForward("firstLoginChangePassword");
					   }

					   else {
					   forward = mapping.findForward(AUTHENTICATION_SUCCESS);
					  //Added by Anil as part of UD integration
					   userBean.setUserLoginStatus("Y");
					   userBean.setSessionID(session.getId());
					   String ipXForwarded  = request.getHeader("X-Forwarded-For");
					   if(ipXForwarded != null && ipXForwarded.length() >7) {
						   userBean.setIpaddress(ipXForwarded);
						   logger.info(loginformBean.getUserName() + "ipaddress: setting ip address X-Forwarded-For : "+ipXForwarded);
					   }
					   else if(request.getParameter("ipaddress") != null && !request.getParameter("ipaddress").equals("") ) {
						   userBean.setIpaddress(request.getParameter("ipaddress"));
						   logger.info(loginformBean.getUserName() + "ipaddress: setting ip address : "+request.getParameter("ipaddress"));
					   }
					   else {
						   userBean.setIpaddress(request.getRemoteAddr() );
						   logger.info(" setting getRemoteAddr : "+ request.getRemoteAddr());
					   }
					   userService.updateUserStatus(userBean);
					   logger.info(loginformBean.getUserId() + " Logged in to the LMS From the Machine with IP : "+userBean.getIpaddress());
					   }
			
			}
			
			
			if ("/UDlogin".equalsIgnoreCase(mapping.getPath())) {
				String msisdn = "";
				String udid ="";
				String encdata ="";
		        String password="welcome2ibm";
				String userId=request.getParameter("userid");
				udid=request.getParameter("udid");
				encdata=request.getParameter("encdata");

//				String userId1=userId;
				ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
				String udkey=bundle.getString("UDKey");
				//String data = "h5Q2t1+JhmoozPwsCUloaF+kcs6VfeXuqk/TXIpf0h9BrqBRIZke5+tJKfoPVswF";
				String encyData;
				encyData = encdata; //'B0015265_TEST1' = "R0PB3As+v7FRZXIMMtUcwRyoiwO5sPV71uQ5hYfLvC7Qijj9SnvMvvpn8ssUd2qXK2sqnuWdKisJuSWbA60mhw==";
				logger.info("encdata:"+encdata + " udkey:"+udkey);

            	if(userId == null || userId.trim().equalsIgnoreCase("")) {
    				errors.add("errors.login.invalid_id", new ActionError("errors.login.user_id"));
        			logger.info("userid is null, UD authentication failed.");
        			saveErrors(request, errors);
        			forward=mapping.findForward("loginFailure");
        			return forward;
            	
            	}

            	if(encdata == null || encdata.trim().equalsIgnoreCase("")) {
    				errors.add("errors.login.invalid_id", new ActionError("errors.login.encdata"));
        			logger.info("encdata is null, UD authentication failed for User ID: " + userId);
        			saveErrors(request, errors);
        			forward=mapping.findForward("loginFailure");
        			return forward;
                }
				//This method takes two parameter first encrypted data and second key.
				JAVASHA.UserInfo us = JAVASHA.SHA.Decrypt(encyData,udkey);
				logger.debug("US Info authentication: " + us);
				boolean udAuth = false;
				logger.info("UD authentication is: " + udAuth + " UserId:" + us.UserId + " CurrentTime:" + us.CurrentTime + " MacAddress:"+  us.MacAddress);
				if(us != null) {
					if(userId.equalsIgnoreCase(us.UserId))
	                {
						udAuth = true;
	                }
				}
            	
            	if(!udAuth) {
				errors.add("errors.login.invalid_id", new ActionError("errors.login.udauth"));
    			logger.info("UD authentication failed for User ID: " + userId);
    			saveErrors(request, errors);
    			forward=mapping.findForward("loginFailure");
    			return forward;
            	}
    			
            	String udUserOLMID = PropertyReader.getAppValue("ud.user.olmid");
            	
				userService = new UserMstrServiceImpl();
                LoginService loginService = new LoginServiceImpl();
//                UserMstr userBean = loginService.populateUserDetailsforUD(us.UserId);
                UserMstr userBean = loginService.populateUserDetailsforUD(udUserOLMID);
                userBean.setUdId(udid);


				ArrayList linksList = new ArrayList();
				ArrayList<LinkMstrDto> toplinksList = new ArrayList<LinkMstrDto>();

				toplinksList = linkMstrService.viewLinks(Constants.TOP_LINKS);

				//logger.info("\n\nList Top Link    "+toplinksList);
				userRoleList = linkMstrService.getUserRoleList(userBean.getKmActorId());

				ArrayList topBarLinks = new ArrayList();
				ArrayList bottomBarLinks = new ArrayList();

				// Refreshing Cache for all the Master Table data List

				if(userBean.getKmActorId().equals(Constants.SUPER_ADMIN))
				{
					new MasterServiceImpl().refreshCache();
				}


				//checking for the restricted circle

				    KmElementMstrService elementService= new KmElementMstrServiceImpl();
				    //userBean.setRestricted(elementService.getCircleStatus(userBean.getElementId()));

					//Setting user information in session
				    session.invalidate();
					session = request.getSession(true);
					//userBean = populatePathInfo(userBean, session);
					session.setAttribute("USER_INFO", userBean);
					session.setAttribute("LINKS_LIST",linksList);
					session.setAttribute("TOP_LINKS_LIST",toplinksList);
					session.setAttribute("USER_ROLE_LIST",userRoleList);
					session.setAttribute("TOPBAR_LINKS",topBarLinks);
					session.setAttribute("BOTTOMBAR_LINKS",bottomBarLinks);
					if(!Constants.SUPER_ADMIN.equalsIgnoreCase(userBean.getKmActorId()) && !Constants.CIRCLE_ADMIN.equalsIgnoreCase(userBean.getKmActorId())) {
						List<String> grouplinksList = new ArrayList<String>();
						for(Object dto : userRoleList) {
							LinkMstrDto dto2 =(LinkMstrDto)dto;
							grouplinksList.add(dto2.getLinkPath());
						}
						session.setAttribute("GROUP_LINKS_LIST",grouplinksList);
						}
					   forward = mapping.findForward(AUTHENTICATION_SUCCESS);
					  //Added by Anil as part of UD integration
					   userBean.setUserLoginStatus("Y");
					   userBean.setSessionID(session.getId());
					   String ipXForwarded  = request.getHeader("X-Forwarded-For");
					   if(ipXForwarded != null && ipXForwarded.length() >7) {
						   userBean.setIpaddress(ipXForwarded);
						   logger.info(loginformBean.getUserName() + "ipaddress: setting ip address X-Forwarded-For : "+ipXForwarded);
					   }
					   else if(request.getParameter("ipaddress") != null && !request.getParameter("ipaddress").equals("") ) {
						   userBean.setIpaddress(request.getParameter("ipaddress"));
					   }
					   else {
						   userBean.setIpaddress(request.getRemoteAddr() );
					   }

					   userService.updateUserStatus(userBean);
					   logger.info(loginformBean.getUserId() + "UD: Logged in to the LMS From the Machine with IP : "+request.getRemoteAddr());
					   } // UD Login - End
		}

	catch (EncryptionException e) {
			errors.add("errors.login.user_invalid", new ActionError(e.getMessage()));
			e.printStackTrace();
			logger.error("EncryptionException in Login by User ID: " + loginformBean.getUserId());
			saveErrors(request, errors);
			forward=mapping.findForward(CSR_AUTHENTICATION_FAILURE);
	 }
	catch (ValidationException ve) {
		 
		 
		 
		 int wrongPwdCount = 0;
	        int PasswordDisableLimit =Integer.parseInt(PropertyReader.getGsdValue("PasswordDisableLimit"));
	        wrongPwdCount = userService.getWrongPwdCount(loginformBean.getUserId());
	   //logger.info(loginformBean.getUserId()+" "+PasswordDisableLimit+" "+wrongPwdCount);
	        if (PasswordDisableLimit <= wrongPwdCount )
	        {
	        	errors.add("errors",new ActionError("msg.security.id014"));
	            saveErrors(request, errors);
	            forward=mapping.findForward(CSR_AUTHENTICATION_FAILURE);
	            return forward;
	        }
	        
			errors.add("errors.login.user_invalid", new ActionError(ve.getMessageId()));
			//ve.printStackTrace();
			logger.error("ValidationException in Login by User ID: " + loginformBean.getUserId());
			saveErrors(request, errors);
			forward=mapping.findForward(CSR_AUTHENTICATION_FAILURE);
		}
	catch (LMSException ex) {
		
				errors.add("errors.login.user_invalid", new ActionError("errors.login.invalid_id"));
				saveErrors(request, errors);
			//	ex.printStackTrace();
				logger.error("Single Sign-In Exception in Login by User ID: " + loginformBean.getUserId() );
				forward=mapping.findForward(CSR_AUTHENTICATION_FAILURE);
			}
	catch (DAOException ex) {
		
				errors.add("errors.login.user_invalid", new ActionError("login.connectionError"));
				saveErrors(request, errors);
				logger.error("Network Exception in Login by User ID: " + ex.getMessage());
				forward=mapping.findForward(CSR_AUTHENTICATION_FAILURE);
	}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception in Login by User ID: " + ex.getMessage());
			forward=mapping.findForward(CSR_AUTHENTICATION_FAILURE);
		}

		java.util.Date dt2 = new java.util.Date();
	logger.info("Login processing Time:" + (dt2.getTime() - dt1.getTime()) );

		return (forward);
	}

	private UserMstr populatePathInfo(UserMstr userBean, HttpSession session) {

		ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
		try {

			KmElementMstrService kmElementService = new KmElementMstrServiceImpl();
			List firstDropDown = null;
			if (userBean.getKmActorId().equals(bundle.getString("LOBAdmin"))||userBean.getKmActorId().equals(bundle.getString("Superadmin"))) {
				firstDropDown = kmElementService.getAllChildren(userBean.getElementId());
			}else{
				firstDropDown = kmElementService.getChildren(userBean.getElementId());

			}
			if (firstDropDown!=null && firstDropDown.size()!=0){
				userBean.setInitialParentId(((KmElementMstr)firstDropDown.get(0)).getElementLevel());
				session.setAttribute("firstDropDown", firstDropDown);
			}
			else{

				int initialLevel=Integer.parseInt(kmElementService.getElementLevelId(userBean.getElementId()));
				initialLevel++;
				userBean.setInitialParentId(initialLevel+"");
			}

			String elementFolderPath = kmElementService.getAllParentIdString("1",userBean.getElementId());
			userBean.setInitialElementPath(elementFolderPath);

		} catch (LMSException e) {
			//logger.info("KmException in : "+e.getMessage());

		} catch (Exception e) {
			//logger.info("Exception in: "+e.getMessage());

		}

		return userBean;
	}

	/**
	 * @param session
	 * @return
	 * @throws LMSException
	 */
	private String[] getScroller(HttpSession session) throws LMSException {

		KmScrollerMstrService scrollerService = null;// new KmScrollerMstrServiceImpl();
		KmElementMstrService elementService = new KmElementMstrServiceImpl();
		UserMstr kmUser = null;
		kmUser = (UserMstr) session.getAttribute("USER_INFO");
		String elementId ="";
		String[] message = new String[2];

		if(kmUser.getKmActorId().equals(Constants.SUPER_ADMIN))
		{
			List<Integer> elementIds = new ArrayList();
			KmElementMstr klmDto;
			ArrayList<KmElementMstr> elementList;
			elementList = elementService.getAllChildren(Constants.SUPER_ADMIN);
			Iterator itr;
			for(itr=elementList.iterator();itr.hasNext();)
			  {
				klmDto = (KmElementMstr) itr.next();
				elementIds.add(Integer.parseInt(klmDto.getElementId()));
			  }
			elementIds.add(1);  //PAN India Scroller
			message[0] = scrollerService.getBulkScrollerMessage(elementIds);

			message[1] = scrollerService.getBulkScrollerMessage(elementService.getAllElementsAsPerLevel(3));

		} // Lob and Circle Scroller for Super Admin
		if(kmUser.getKmActorId().equals(Constants.CIRCLE_ADMIN) || kmUser.getKmActorId().equals(Constants.CIRCLE_USER) ||  kmUser.getKmActorId().equals(Constants.REPORT_ADMIN) || kmUser.getKmActorId().equals(Constants.TSG_USER) )
		{
		   elementId = elementService.getParentId(kmUser.getElementId());
		   message[0] = scrollerService.getScrollerMessage(Constants.PAN_INDIA_SCROLLER);
		   message[0] =
			   message[0] +  scrollerService.getScrollerMessage(elementId);

		   message[1] = scrollerService.getScrollerMessage(kmUser.getElementId());
		} // Lob and Circle Sroller for Circle Admin,Circle User,TSG User,Report Admin
		if(kmUser.getKmActorId().equals(Constants.LOB_ADMIN))
		{
			elementId = kmUser.getElementId();
			message[0] = scrollerService.getScrollerMessage(Constants.PAN_INDIA_SCROLLER);
			message[0] =
				   message[0] + scrollerService.getScrollerMessage(elementId);

			List<Integer> elementIds = new ArrayList();
			KmElementMstr klmDto;
			ArrayList<KmElementMstr> elementList;
			elementList = elementService.getAllChildren(elementId);
			Iterator itr;
			for(itr=elementList.iterator();itr.hasNext();)
			  {
				klmDto = (KmElementMstr) itr.next();
				elementIds.add(Integer.parseInt(klmDto.getElementId()));
			  }

			message[1] = scrollerService.getBulkScrollerMessage(elementIds);

		} // Lob and Circle Scroller for Lob Admin
		return message;
	}

	private static String generatePassword(String userLoginId)
	 {
		String specialChars="@#!";
		String lowerChars = "qwertyuipasdfghjklzxcvbnm";
		int alphabetIndex = (int)(Math.random()*23);			
		int specialCharsIndex = (int)(Math.random()*2);		
		StringBuilder strPassword = new StringBuilder();
		strPassword.append(lowerChars.toUpperCase().charAt(alphabetIndex)+""+ (11+(int)(Math.random()*80))+""+lowerChars.toUpperCase().charAt((int)(Math.random()*25))+""+((11+(int)(Math.random()*80))+7) +""+ specialChars.charAt(specialCharsIndex)+""+ lowerChars.charAt(alphabetIndex+1)); 
		return strPassword.toString().replace("0", "5");
	 }

	private String getAddressInfo(HttpServletRequest request) {
		String ip = "";
		 Enumeration headerNames = request.getHeaderNames();
		 while(headerNames.hasMoreElements()) {
		      String headerName = (String)headerNames.nextElement();
		      ip += "<Header:" + headerName;
		      ip += ":::Value:" + request.getHeader(headerName) + ">";
		    }
/*
		ip = "<<X-Forwarded-For:" + checkNull(request, "X-Forwarded-For") + ">>";  
            ip += "<<Proxy-Client-IP:"+ checkNull(request, "Proxy-Client-IP") + ">>";
            ip += "<<X-Proxy-Client-IP:"+ checkNull(request, "X-Proxy-Client-IP") + ">>";
            ip += "<<WL-Proxy-Client-IP:"+ checkNull(request, "WL-Proxy-Client-IP") + ">>";  
            ip += "<<HTTP_CLIENT_IP:"+ checkNull(request, "HTTP_CLIENT_IP") + ">>";  
            ip += "<<HTTP_X_FORWARDED_FOR:"+ checkNull(request, "HTTP_X_FORWARDED_FOR") + ">>"; 
            ip += "<<HTTP-X-FORWARDED-FOR:"+ checkNull(request, "HTTP-X-FORWARDED-FOR") + ">>"; 
            ip += "<<rlnclientipaddr:"+ checkNull(request, "rlnclientipaddr") + ">>";
            ip += "<<REMOTE_HOST:" + request.getRemoteHost() + ">>";  
            ip += "<<REMOTE_ADDR:" + request.getRemoteAddr() + ">>";  
            ip += "<<REMOTE_USER:" + request.getRemoteUser() + ">>";
*/        return ip;
	}

/*	private String checkNull(HttpServletRequest request, String header) {
		String temp = "";
			temp = request.getHeader(header);
			request.getHeaderNames();
			
		return temp;
	}
*/
	}
