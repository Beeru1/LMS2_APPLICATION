package com.ibm.lms.actions;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.ArrayList;
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
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ObjectQuery.crud.util.Array;
import com.ibm.appsecure.service.GSDService;
import com.ibm.appsecure.util.AppUtils;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.lms.common.Constants;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.common.Utility;
import com.ibm.lms.dto.CircleDTO;
import com.ibm.lms.dto.LOBDTO;
import com.ibm.lms.dto.UserDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.forms.UserMstrFormBean;
import com.ibm.lms.services.LoginService;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.LoginServiceImpl;
import com.ibm.lms.services.impl.MasterServiceImpl;
import com.ibm.lms.services.impl.UserMstrServiceImpl;

/**
 * KmUserMstrAction
 * @author Anil
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UserMstrAction extends DispatchAction {

	/**
	 * Logger for the class.
	**/
	private static final Logger logger;

	static {

		logger = Logger.getLogger(UserMstrAction.class);
	}

	/* Local Variables */
	private static String INITCREATEUSER_SUCCESS = "initCreateUser";

	private static String CREATEUSER_FAILURE = "CreateUserFailure";

	private static String USERCREATED_SUCCESS = "UserCreatedSuccess";

	private static String INIT_EDIT_USER = "initEditUser";

	private static String VIEW_USER = "viewUser";
	
	private static String EDIT_USER_SUCCESS = "EditUserSuccess";

	public UserMstrAction() {
	}
	static int loginCounter = 0;

	/**
	 *Initailizes the Create User page. For SuperAdmin uploads all the circles and for CircleApprover shows the different user types
	**/
	public ActionForward init(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionForward forward = new ActionForward(); // return value
		HttpSession session = request.getSession();
		UserMstrFormBean kmUserMstrFormBean = (UserMstrFormBean) form;
		UserMstr sessionUserBean =
			(UserMstr) session.getAttribute("USER_INFO");
		
		saveToken(request);

		try {
			
			initializeParameter(request, sessionUserBean, kmUserMstrFormBean);
		
			logger.info(
				sessionUserBean.getUserLoginId()
					+ " Entered into the init method of KmUserMstrAction");
			String loginUserActorId = sessionUserBean.getKmActorId();
			kmUserMstrFormBean.setKmLoginActorId(loginUserActorId);
			session.setAttribute("LOGIN_USER_ACTOR_ID", loginUserActorId);
			
			forward = mapping.findForward("DO_NOT_COME_TO_THIS_PART_OF_CODE");
			return forward;

		} catch (Exception e) {

			logger.error(
				"Exception occured while initializing the create user page");
			forward = mapping.findForward("DO_NOT_COME_TO_THIS_PART_OF_CODE");
			return forward;
		}

	}
	
	
	public ActionForward initSearchUser(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		logger.info("Inside initSearchUser of User Master Action");
			UserMstrFormBean kmUserMstrFormBean = (UserMstrFormBean) form;
			UserMstr userDto = null;
			ActionForward forward = new ActionForward(); 
			forward =  mapping.findForward("searchUser");
			saveToken(request);
			if(null == kmUserMstrFormBean.getUserLoginId())
			{
				kmUserMstrFormBean.reset(mapping, request);
				kmUserMstrFormBean.setInitStatus("true");	
				if(Utility.isValidRequest(request)) {
		        	return mapping.findForward("error");
		        }
			}else
			{				
				kmUserMstrFormBean.setInitStatus("false");
				logger.info("searching details for user :: "+kmUserMstrFormBean.getUserLoginId());
				logger.info("searching details for user :: "+kmUserMstrFormBean.getUserFname());
				// check if user exist in the system				
				UserMstrService userService = new UserMstrServiceImpl();
				if(!"".equals(kmUserMstrFormBean.getUserLoginId()))
				{
					
				userDto=userService.getUserDetails(kmUserMstrFormBean.getUserLoginId());
				}
				if(!"".equals(kmUserMstrFormBean.getUserFname()))
				{
					userDto=userService.getUserFnameDetails(kmUserMstrFormBean.getUserFname());
				}
				 if(userDto==null)
				{
					logger.info("User not found.");
					// User not found
					ActionErrors errors = new ActionErrors();
					errors.add("msg10",new ActionError("user.not.found"));
					saveErrors(request, errors);					
				}
				else
				{	
					logger.info("User id found :"+userDto.getUserId());
					// forward to view/edit Users	
					kmUserMstrFormBean.setSelectedUserId(userDto.getUserId());
					forward = viewUser(mapping, kmUserMstrFormBean, request, response);
				}
			}
			return forward;
		}
	
	private void initializeParameter(HttpServletRequest request,
			UserMstr userBean,
			UserMstrFormBean formBean) throws LMSException {
		ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
		MasterService mstrService = new MasterServiceImpl();
		ArrayList lobList = new ArrayList();
		ArrayList actorList = new ArrayList();
		HttpSession session = request.getSession();
		UserMstr sessionUserBean =
			(UserMstr) session.getAttribute("USER_INFO");
		saveToken(request);
		try{
		logger.info("inside user action--------------");
		
		/* Added by Parnika for LMS Phase 2 , Introducing LOB list based on actor id*/
		
		if(sessionUserBean.getKmActorId().equalsIgnoreCase(Constants.CIRCLE_COORDINATOR_ACTOR)){
			lobList = mstrService.getLobListBasedOnUser(sessionUserBean.getLobList());
		}
		else {
			lobList = mstrService.getLobList();
		}		
		formBean.setLobList(lobList);
		//circleList = mstrService.getCircleUserList();
		//formBean.setCircleList(circleList);
		
		/* End of changes by Parnika */
		
		actorList = mstrService.getActorList(sessionUserBean.getKmActorId());
		if(actorList !=null && actorList.size() > 0)
			formBean.setActorList(actorList);
	//	request.setAttribute("circleList",circleList);
		}catch(Exception e){
			logger.info("Exception occured while initializing Parameter ");
			
		}
	}

	/**	
	*Creates new users. 
	**/
	public ActionForward insert(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionForward forward = new ActionForward(); // return value
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		UserMstrFormBean kmUserMstrFormBean = (UserMstrFormBean) form;
		UserMstr sessionUserBean =
			(UserMstr) session.getAttribute("USER_INFO");
		UserMstrService createUserService = new UserMstrServiceImpl();
		MasterService mstrService = new MasterServiceImpl();
		
		String loginUserActorId = sessionUserBean.getKmActorId();
		session.setAttribute("LOGIN_USER_ACTOR_ID", loginUserActorId);
		int countId = 0;
		List<LOBDTO> lobIdsOfUsr=null;
		
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try {

			logger.info(
				sessionUserBean.getUserLoginId()
					+ " Entered into the insert method of KmUserMstrAction");

			
			/*Modified by Karan 3 Jan 2013*/
			
			String pwd = generatePassword(kmUserMstrFormBean.getUserLoginId());
			
			IEncryption encpwd = new Encryption();
			String encpass = encpwd.generateDigest(pwd);
			kmUserMstrFormBean.setUserPassword(pwd);
			
			kmUserMstrFormBean.setCreatedBy(sessionUserBean.getUserId());

			
			/* Added by Parnika for LMS Phase 2 */
			
			kmUserMstrFormBean.setUpdatedBy(sessionUserBean.getUserLoginId());
			kmUserMstrFormBean.setLobId(kmUserMstrFormBean.getSelectedLobId());
			kmUserMstrFormBean.setCircleId(kmUserMstrFormBean.getCircleMstrId());
			kmUserMstrFormBean.setZoneId(kmUserMstrFormBean.getSelectedZoneId());
			
			/* End of changes by Parnika */
			
			kmUserMstrFormBean.setKmActorId(kmUserMstrFormBean.getKmActorId());			

			logger.info("loginactorId = " + kmUserMstrFormBean.getKmActorId());
			
			/*GSDService gSDService = new GSDService();
			IEncryption encrypt = new Encryption();

			logger.debug("Getting digest for user password");
			gSDService.validateCredentials(
				kmUserMstrFormBean.getUserLoginId(),
				kmUserMstrFormBean.getUserPassword());
			logger.info(
				"Start:" + kmUserMstrFormBean.getUserPassword() + ":End");
			//				Encrypting user password
			
			logger.info("\n\n\n\nValidating Password.....\n\n\n\n");*/
			

			// Security finding trans token impl...		
				 	if(!isTokenValid(request))
				     {
						kmUserMstrFormBean.setCircleList(mstrService.getCircleUserList());
						kmUserMstrFormBean.setActorList(mstrService.getActorList(loginUserActorId));
						forward = mapping.findForward(CREATEUSER_FAILURE);				
				    	errors.add("errors.incorrectPassword",new ActionError("msg.security.id021"));
						saveErrors(request, errors);
						return forward;
				     }
			
			UserMstr userMstrDto = new UserMstr();

			userMstrDto.setUserLoginId(kmUserMstrFormBean.getUserLoginId().toUpperCase());
			userMstrDto.setUserFname(kmUserMstrFormBean.getUserFname());
			userMstrDto.setUserMname(kmUserMstrFormBean.getUserMname());
			userMstrDto.setUserLname(kmUserMstrFormBean.getUserLname());
			userMstrDto.setUserMobileNumber(kmUserMstrFormBean.getUserMobileNumber());
			userMstrDto.setUserEmailid(kmUserMstrFormBean.getUserEmailid());
			
			userMstrDto.setUserPassword(encpass);
			userMstrDto.setCreatedBy(kmUserMstrFormBean.getCreatedBy());
			userMstrDto.setStatus("A");
			
			/* Added by Parnika for LMS phase 2 */
			
			userMstrDto.setLobId(kmUserMstrFormBean.getLobId());
			userMstrDto.setSelectedTypeId(kmUserMstrFormBean.getSelectedTypeId());
			userMstrDto.setZoneId(kmUserMstrFormBean.getZoneId());
			userMstrDto.setUpdatedBy(kmUserMstrFormBean.getUpdatedBy());
			
			/* If Circle is not selected , then circle will be Pan India */
			
			if(!(kmUserMstrFormBean.getKmActorId().equalsIgnoreCase(Constants.CIRCLE_COORDINATOR_ACTOR))){
				userMstrDto.setCircleId(kmUserMstrFormBean.getCircleId());
			}
/*			else if(kmUserMstrFormBean.getKmActorId().equalsIgnoreCase(Constants.ZBM_ACTOR)
					|| kmUserMstrFormBean.getKmActorId().equalsIgnoreCase(Constants.ZSM_ACTOR)
					|| kmUserMstrFormBean.getKmActorId().equalsIgnoreCase(Constants.ZONAL_COORDINATOR_ACTOR)){
				userMstrDto.setCircleId(kmUserMstrFormBean.getCircleId());
			}*/
			else if(kmUserMstrFormBean.getKmActorId().equalsIgnoreCase(Constants.CIRCLE_COORDINATOR_ACTOR)){
					userMstrDto.setCreateMultiple(kmUserMstrFormBean.getCreateMultiple());
				
			}
			
			/* End of changes by Parnika for LMS Phase 2*/
			
			userMstrDto.setKmActorId(kmUserMstrFormBean.getKmActorId());
			LoginService loginService = new LoginServiceImpl();
			userMstrDto.setPartner(kmUserMstrFormBean.getPartner());
			
			//Calling the service that check for duplicate user login id
			/*if (createUserService
				.checkDuplicateUserLogin(userMstrDto.getUserLoginId())) {

				messages.add("msg1", new ActionMessage("createUser.duplicate"));
				kmUserMstrFormBean.setUserStatus(
					"User Login Id Already exists");
				kmUserMstrFormBean.setUserLoginId("");
				kmUserMstrFormBean.setUserPassword("");
				kmUserMstrFormBean.setUserConfirmPassword("");
				kmUserMstrFormBean.setKmActorId(userMstrDto.getKmActorId());
				kmUserMstrFormBean.setCircleList(mstrService.getCircleUserList());
				kmUserMstrFormBean.setActorList(mstrService.getActorList(loginUserActorId));
				forward = mapping.findForward(CREATEUSER_FAILURE);
			} */
			
			//Calling the service that check for duplicate user login id for LMS Phase 2 based on LOB and Circle
			if (createUserService.checkDuplicateLOBCircleUserLogin(userMstrDto.getUserLoginId(),userMstrDto.getLobId(),userMstrDto.getKmActorId()))				
			{
				messages.add("msg1", new ActionMessage("createUser.duplicateLob"));
				kmUserMstrFormBean.setUserStatus(
					"User Login Id Already exists for selected LOB.");
				kmUserMstrFormBean.setUserLoginId("");
				kmUserMstrFormBean.setUserPassword("");
				kmUserMstrFormBean.setUserConfirmPassword("");
				kmUserMstrFormBean.setKmActorId(userMstrDto.getKmActorId());
				kmUserMstrFormBean.setCircleList(mstrService.getCircleUserList());
				kmUserMstrFormBean.setActorList(mstrService.getActorList(loginUserActorId));
				forward = mapping.findForward(CREATEUSER_FAILURE);
			}
			
			/* Added by Parnika for LMS Phase 2 */
			else if (createUserService.checkActorForUserLogin(userMstrDto.getUserLoginId(), userMstrDto.getKmActorId()))				
			{
				messages.add("msg1", new ActionMessage("createUser.actor"));
				kmUserMstrFormBean.setUserStatus(
					"User Login Id exists for a different User Role.");
				kmUserMstrFormBean.setUserLoginId("");
				kmUserMstrFormBean.setUserPassword("");
				kmUserMstrFormBean.setUserConfirmPassword("");
				kmUserMstrFormBean.setKmActorId(userMstrDto.getKmActorId());
				kmUserMstrFormBean.setCircleList(mstrService.getCircleUserList());
				kmUserMstrFormBean.setActorList(mstrService.getActorList(loginUserActorId));
				forward = mapping.findForward(CREATEUSER_FAILURE);
			}
			/* End of changes by Parnika */
			
			// Calling the service that check for Login id as valid OLM ID
			else if("Y".equalsIgnoreCase(PropertyReader.getAppValue("doLdapValidation")) && !loginService.isValidOlmId(kmUserMstrFormBean.getUserLoginId()))
			 {
					messages.add("msg1", new ActionMessage("createUser.invalidUserId"));
					kmUserMstrFormBean.setUserStatus("User Login Id should have active OLM Id");
					
					kmUserMstrFormBean.setCircleList(mstrService.getCircleUserList());
					kmUserMstrFormBean.setActorList(mstrService.getActorList(loginUserActorId));
					
					forward = mapping.findForward(CREATEUSER_FAILURE);
			}    // LDAP validation finish
			
			   else
			   {
				/*Calling create user service  */
				int userCount =createUserService.insertUserData(userMstrDto);
				if(userCount==-1){
					forward = mapping.findForward(CREATEUSER_FAILURE);
					errors.add(
							"errors.max_user_limit",
							new ActionError("max.report.admin.message",PropertyReader.getAppValue("max.report.admin.limit")));
						saveErrors(request, errors);
						kmUserMstrFormBean.setUserLoginId("");
						kmUserMstrFormBean.setUserFname("");
						kmUserMstrFormBean.setUserMname("");
						kmUserMstrFormBean.setUserLname("");
						kmUserMstrFormBean.setUserEmailid("");
						kmUserMstrFormBean.setUserMobileNumber("");
						kmUserMstrFormBean.setUserPassword("");
						kmUserMstrFormBean.setUserConfirmPassword("");
						kmUserMstrFormBean.setCircleId("-1");
						kmUserMstrFormBean.setPartner("");
						kmUserMstrFormBean.setKmActorId("");
						kmUserMstrFormBean.setActorList(mstrService.getActorList(loginUserActorId));
						kmUserMstrFormBean.setCircleList(mstrService.getCircleUserList());
						resetToken(request);
						return (forward);
				}

				//countId = createUserService.countUserLoginId(userMstrDto.getUserLoginId());
					lobIdsOfUsr = createUserService.countUserLoginId(userMstrDto.getUserLoginId());
					Iterator<LOBDTO> itr=lobIdsOfUsr.iterator();
					StringBuffer sb=new StringBuffer("");
					while(itr.hasNext())
					{
						LOBDTO lobDto=itr.next();
						sb.append(lobDto.getLobName()+",");
						countId++;
					}
					//builder.substring(0, builder.length()-1)
					kmUserMstrFormBean.setLobIdMsg(" for LOB "+sb.substring(0, sb.length()-1));
					messages.add("msg1",new ActionMessage("user.created"));
					kmUserMstrFormBean.setUserStatus("New User Created");
					resetToken(request);
					
					
					if(countId == 1){
						// Sending mail to user for password
						try{
							
						String eMail = kmUserMstrFormBean.getUserEmailid();
						String mailFlag = mstrService.getParameterName(PropertyReader.getAppValue("leadRegisteration.smsFlag"));
						if(mailFlag.equalsIgnoreCase("Y")){
						 sendMail(eMail, kmUserMstrFormBean, "Sending Mail", "User Creation");
						}
						}
						catch(Exception e){
							e.printStackTrace();
							messages.add("msg10",new ActionMessage("createUser.mailError"));
							
						}
					}
				saveMessages(request, messages);
				kmUserMstrFormBean.setUserLoginId("");
				kmUserMstrFormBean.setUserFname("");
				kmUserMstrFormBean.setUserMname("");
				kmUserMstrFormBean.setUserLname("");
				kmUserMstrFormBean.setUserEmailid("");
				kmUserMstrFormBean.setUserMobileNumber("");
				kmUserMstrFormBean.setUserPassword("");
				kmUserMstrFormBean.setUserConfirmPassword("");
				kmUserMstrFormBean.setCircleId("-1");
				kmUserMstrFormBean.setPartner("");
				kmUserMstrFormBean.setKmActorId("");
				
				/* Added by Parnika for LMS Phase 2 */
				// kmUserMstrFormBean.setCircleList(mstrService.getCircleUserList());
				kmUserMstrFormBean.setSelectedLobId("");
				kmUserMstrFormBean.setLobList(mstrService.getLobList());
				/* End of changes By parnika */
								
				kmUserMstrFormBean.setActorList(mstrService.getActorList(loginUserActorId));
				logger.info(
					"getUser() method : successful : redirected to target : UserEditJsp ");
				forward = mapping.findForward(USERCREATED_SUCCESS);
				
			}

			saveMessages(request, messages);
		} /*catch (ValidationException validationExp) {
			kmUserMstrFormBean.setCircleList(createUserService.getCircles());
			kmUserMstrFormBean.setUserPassword("");
			kmUserMstrFormBean.setUserConfirmPassword("");
			logger.error(
				"createUser method : caught ValidationException for GSD : "
					+ validationExp.getMessageId());
			errors.add(
				"errors.userName",
				new ActionError(validationExp.getMessageId()));
			//validationExp.printStackTrace();
			saveErrors(request, errors);
			return mapping.findForward(CREATEUSER_FAILURE);
		} */
		
		catch (Exception e) {
			e.printStackTrace();

			kmUserMstrFormBean.setCircleList(mstrService.getCircleUserList());
			kmUserMstrFormBean.setUserLoginId("");
			kmUserMstrFormBean.setUserFname("");
			kmUserMstrFormBean.setUserMname("");
			kmUserMstrFormBean.setUserLname("");
			kmUserMstrFormBean.setUserEmailid("");
			kmUserMstrFormBean.setUserMobileNumber("");
			kmUserMstrFormBean.setUserPassword("");
			kmUserMstrFormBean.setUserConfirmPassword("");
			kmUserMstrFormBean.setCircleId("-1");
			kmUserMstrFormBean.setPartner("");
			kmUserMstrFormBean.setKmActorId("");
			kmUserMstrFormBean.setActorList(mstrService.getActorList(loginUserActorId));
			if (e instanceof IllegalAccessException) {
				logger.error(
					"createUser() method : caught IllegalAccessException Exception while using BeanUtils.copyProperties()");
			}
			if (e instanceof InvocationTargetException) {
				logger.error(
					"createUser() method : caught InvocationTargetException Exception while using BeanUtils.copyProperties()");
			}
			if (e instanceof LMSException) {

				logger.error(
					"createUser() method : caught KmException Exception while using user creation");
			}
			logger.error("Exception From LoginAction" + e);

			forward = mapping.findForward(CREATEUSER_FAILURE);
		}
		return (forward);

	}
	/**
	*Lists the users depending upon the type of the logged in user
	**/
	public ActionForward viewUserList(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		//	KmUserMstrDao userDAO = new KmUserMstrDaoImpl();
		UserMstrFormBean kmUserMstrFormBean = (UserMstrFormBean) form;
		ArrayList userList = new ArrayList();
		HttpSession session = request.getSession();
		UserMstr sessionUserBean =
			(UserMstr) session.getAttribute("USER_INFO");
		logger.info(
			sessionUserBean.getUserLoginId()
				+ " Entered into the viewUserList method of KmUserMstrAction");
		UserMstrService viewUserService = new UserMstrServiceImpl();
		String loginActorId = sessionUserBean.getKmActorId();
		session.setAttribute("loginUserId", loginActorId);
		kmUserMstrFormBean.setUpdatedBy(loginActorId);

		//Retreiving different user types based on the login user type
		try {

			kmUserMstrFormBean.setUserList(
				(ArrayList) viewUserService.viewUsers(
					loginActorId,
					sessionUserBean.getUserId().toString()));
			MasterService mstrService = new MasterServiceImpl();
			ArrayList lobList = new ArrayList();
			lobList = mstrService.getLobList();
			logger.info("lobList.size()"+lobList.size());
			kmUserMstrFormBean.setLobList(lobList);
			//Setting the label for identifying the elementType while listing the users
			if (loginActorId.equals(Constants.SUPER_ADMIN)) {
				kmUserMstrFormBean.setElementType("LOB");
			} else
				kmUserMstrFormBean.setElementType("Circle");

		} catch (Exception e) {
			logger.error("Exception occurs in viewUserList: " + e.getMessage());
		}
		return mapping.findForward("DO_NOT_COME_TO_THIS_PART_OF_CODE");
	}
	public ActionForward excelImport(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

			//	KmUserMstrDao userDAO = new KmUserMstrDaoImpl();
			UserMstrFormBean kmUserMstrFormBean = (UserMstrFormBean) form;
			HttpSession session = request.getSession();
			UserMstr sessionUserBean =
				(UserMstr) session.getAttribute("USER_INFO");
			logger.info(
				sessionUserBean.getUserLoginId()
					+ " Entered into the excelImport method of KmUserMstrAction");
			UserMstrService viewUserService = new UserMstrServiceImpl();
			String loginActorId = sessionUserBean.getKmActorId();
			session.setAttribute("loginUserId", loginActorId);
			kmUserMstrFormBean.setUpdatedBy(loginActorId);

			//Retreiving different user types based on the login user type
			/*if ( !isTokenValid(request) ) {
				  return mapping.findForward("error");
				}*/
			try {

				kmUserMstrFormBean.setUserList(
					(ArrayList) viewUserService.viewUsers(
						loginActorId,
						sessionUserBean.getUserId().toString()));

				//Setting the label for identifying the elementType while listing the users
				if (loginActorId.equals(Constants.SUPER_ADMIN)) {
					kmUserMstrFormBean.setElementType("LOB");
				} else
					kmUserMstrFormBean.setElementType("Circle");

				/*	if(loginActorId.equals("1"))
						kmUserMstrFormBean.setUserList((ArrayList) viewUserService.viewUsers(loginActorId,sessionUserBean.getCircleId().toString()));
					else if(loginActorId.equals("2"))
						kmUserMstrFormBean.setUserList((ArrayList) viewUserService.viewUsers(loginActorId,sessionUserBean.getCircleId().toString()));
					else if(loginActorId.equals("3"))
						kmUserMstrFormBean.setUserList((ArrayList) viewUserService.viewUsers(loginActorId,sessionUserBean.getCircleId().toString())); */
				//		//	logger.info(UserDetails.getUserLoginId(request)+" : Exiting viewList method");
			} catch (Exception e) {
				logger.error("Exception occurs in viewUserList: " + e.getMessage());
			}
			return mapping.findForward("viewUserListExcel");
		}
	
	/**
	*Edit the details of the selected user
	**/
	public ActionForward editUser(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward();
		ActionMessages messages = new ActionMessages();
		UserMstrFormBean kmUserMstrFormBean = (UserMstrFormBean) form;
		//KmElementMstrService elementService = new KmElementMstrServiceImpl();
		HttpSession session = request.getSession();
		UserMstr sessionUserBean =
			(UserMstr) session.getAttribute("USER_INFO");
		logger.info(
			sessionUserBean.getUserLoginId() + " Entered editUser method.");
		
		String loginUserActorId = sessionUserBean.getKmActorId();
		kmUserMstrFormBean.setKmLoginActorId(loginUserActorId);
		session.setAttribute("LOGIN_USER_ACTOR_ID", loginUserActorId);
		
		UserMstr userMstrDto = new UserMstr();
		UserMstrService viewUserService = new UserMstrServiceImpl();
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try {

			/*request.getSession().setAttribute("org.apache.struts.action.TOKEN", "84ad3c9ac4053dd6bb93df702086a456");
			
			//logger.info(">>>>");
			//logger.info(request.getSession().getAttribute("org.apache.struts.action.TOKEN"));
		
			//logger.info("<<<<");*/
			
//			 Security finding trans token impl...		
		 	if(!isTokenValid(request))
		     {
				//kmUserMstrFormBean.setElementList(elementService.getChildren(sessionUserBean.getElementId()));
				forward = mapping.findForward(INIT_EDIT_USER);		
		    	errors.add("errors.incorrectPassword",new ActionError("msg.security.id021"));
				saveErrors(request, errors);
				return forward;
		     }

			String loginUserId = sessionUserBean.getKmActorId();
			/* Set form bean data (edited information) to DTO */
			userMstrDto.setUserId(kmUserMstrFormBean.getUserId());
			userMstrDto.setUserLoginId(kmUserMstrFormBean.getUserLoginId());
			userMstrDto.setUserFname(kmUserMstrFormBean.getUserFname());
			userMstrDto.setUserMname(kmUserMstrFormBean.getUserMname());
			userMstrDto.setUserLname(kmUserMstrFormBean.getUserLname());
			userMstrDto.setUserMobileNumber(
				kmUserMstrFormBean.getUserMobileNumber());
			userMstrDto.setUserEmailid(kmUserMstrFormBean.getUserEmailid());
			userMstrDto.setStatus(kmUserMstrFormBean.getStatus());
			userMstrDto.setUpdatedBy(sessionUserBean.getUserId());
			userMstrDto.setCategoryId(kmUserMstrFormBean.getCategoryId());
			userMstrDto.setPartnerName(kmUserMstrFormBean.getPartnerName());		
			userMstrDto.setKmActorId(String.valueOf(kmUserMstrFormBean.getSelectedActorId()));
			userMstrDto.setLobId(kmUserMstrFormBean.getSelectedLobId());
			userMstrDto.setSelectedTypeId(kmUserMstrFormBean.getSelectedTypeId());
			userMstrDto.setSelectedZoneId(kmUserMstrFormBean.getSelectedZoneId());
			String selectedCircles = kmUserMstrFormBean.getSelectedvalues();
			logger.info("selectedCircles"+selectedCircles);
			String circle[] = selectedCircles.split(",");
			String[]  CircleId = new String[circle.length];
			for(int i=0 ; i< circle.length ; i++){
				logger.info("circle[]"+circle[i] );				
				CircleId[i] = viewUserService.getCircleId(kmUserMstrFormBean.getSelectedLobId(), circle[i]);
				
			}
			/*for(int j=0 ; j < kmUserMstrFormBean.getSaveMultiple().length ; j++)
			{
				CircleId[circle.length+1+j] = kmUserMstrFormBean.getSaveMultiple()[j];
			}*/
			logger.info("CircleId"+CircleId.length);
			
		/*	
			logger.info("kmUserMstrFormBean.getCreateMultiple()"+kmUserMstrFormBean.getCreateMultiple().length);
			String[]  Circleid = new String[kmUserMstrFormBean.getCreateMultiple().length];
			for(int i=0 ; i< kmUserMstrFormBean.getCreateMultiple().length ; i++){
				logger.info("kmUserMstrFormBean.getCreateMultiple()[i]"+kmUserMstrFormBean.getCreateMultiple()[i] );
				if(!kmUserMstrFormBean.getCreateMultiple()[i].equalsIgnoreCase(""))
				Circleid[i] = kmUserMstrFormBean.getCreateMultiple()[i];
				
			}
			logger.info("Circleid"+Circleid.length );*/
			/* Added By Parnika for editing Circle  */
/* If Circle is not selected , then circle will be Pan India */
			
		
			if(Integer.parseInt(loginUserActorId) != 8)
			{
				
				if(userMstrDto.getKmActorId().equalsIgnoreCase("2"))
				{
					if(!(String.valueOf(kmUserMstrFormBean.getSelectedActorId()).equalsIgnoreCase(Constants.CIRCLE_COORDINATOR_ACTOR))){
						userMstrDto.setCircleId(kmUserMstrFormBean.getCircleMstrId());
						
					}
		/*			else if(kmUserMstrFormBean.getKmActorId().equalsIgnoreCase(Constants.ZBM_ACTOR)
							|| kmUserMstrFormBean.getKmActorId().equalsIgnoreCase(Constants.ZSM_ACTOR)
							|| kmUserMstrFormBean.getKmActorId().equalsIgnoreCase(Constants.ZONAL_COORDINATOR_ACTOR)){
						userMstrDto.setCircleId(kmUserMstrFormBean.getCircleId());
					}*/
					else if(String.valueOf(kmUserMstrFormBean.getSelectedActorId()).equalsIgnoreCase(Constants.CIRCLE_COORDINATOR_ACTOR)){
						if(CircleId.length >0){
							userMstrDto.setCreateMultiple(CircleId);
						}
						else{
							userMstrDto.setCreateMultiple(kmUserMstrFormBean.getCreateMultiple());
						}
							userMstrDto.setSelectedZoneId(0);
							//logger.info("kmUserMstrFormBean.getSaveMultiple() "+CircleId.toString());
						
					}
				}
				else
				{
					if(!(String.valueOf(kmUserMstrFormBean.getSelectedActorId()).equalsIgnoreCase(Constants.CIRCLE_COORDINATOR_ACTOR))){
						userMstrDto.setCircleId(kmUserMstrFormBean.getCircleMstrId());
					}
	
					else if(String.valueOf(kmUserMstrFormBean.getSelectedActorId()).equalsIgnoreCase(Constants.CIRCLE_COORDINATOR_ACTOR)){
						if(CircleId.length > 0){
							userMstrDto.setCreateMultiple(CircleId);
						}
						else{
							userMstrDto.setCreateMultiple(kmUserMstrFormBean.getCreateMultiple());
						}
							userMstrDto.setSelectedZoneId(0);
							//logger.info("kmUserMstrFormBean.getSaveMultiple() "+CircleId.toString());
					}
				}
				
			}
			else{
				if(!(String.valueOf(kmUserMstrFormBean.getSelectedActorId()).equalsIgnoreCase(Constants.CIRCLE_COORDINATOR_ACTOR))){
					userMstrDto.setCircleId(kmUserMstrFormBean.getCircleMstrId());
				}
	
				else if(String.valueOf(kmUserMstrFormBean.getSelectedActorId()).equalsIgnoreCase(Constants.CIRCLE_COORDINATOR_ACTOR)){
					if(CircleId.length > 0){
						userMstrDto.setCreateMultiple(CircleId);
					}
					else{
						userMstrDto.setCreateMultiple(kmUserMstrFormBean.getCreateMultiple());
					}
						userMstrDto.setSelectedZoneId(0);
						//logger.info("kmUserMstrFormBean.getSaveMultiple() "+CircleId.toString());
				}
			}

			/* End of changes by Parnika  */
			UserMstrService editUserService = new UserMstrServiceImpl();
			
			/*if (sessionUserBean.getKmActorId().equals(Constants.SUPER_ADMIN)) {
				kmUserMstrFormBean.setElementType("LOB");
			} else {

				kmUserMstrFormBean.setElementType("Circle");
			}*/
			logger.info("editUser() : Calling updateUser of User Service.");
			
			// Security finding trans token impl...		
		 	if(!isTokenValid(request))
		     {
		 		//kmUserMstrFormBean.setElementList(elementService.getChildren(sessionUserBean.getElementId()));
				forward = mapping.findForward(CREATEUSER_FAILURE);
				errors.add("errors.incorrectPassword",new ActionError("msg.security.id021"));	
				saveErrors(request, errors);
				return forward;
		     }
		 	
		 	
			/*
			 * Call the updateUser() method of Service Layer to update user
			 * details.
			 */
			int count = editUserService.editUserService(userMstrDto);
			if(count> 0){
			messages.add("msg", new ActionMessage("updateUser.success"));
			saveMessages(request, messages);
			kmUserMstrFormBean.setUserStatus(
				"User Details Successfully Updated");
			resetToken(request);
			
			String loginActorId = sessionUserBean.getKmActorId();
			logger.info("editUser() : User details successfully updated.");
			kmUserMstrFormBean.setSelectedActorId(Integer.parseInt(userMstrDto.getKmActorId()));
			kmUserMstrFormBean.setSelectedUserId(userMstrDto.getUserId());
			kmUserMstrFormBean.setSelectedLobId(userMstrDto.getLobId());
			logger.info("kmUserMstrFormBean-----------"+kmUserMstrFormBean.getSelectedUserId());
			
			forward = viewUserForEdit(mapping, kmUserMstrFormBean, request, response);
			kmUserMstrFormBean.reset(mapping, request);
			}
		} catch (Exception e) {
			logger.error("Exception occurs in editUser: " + e.getMessage());
			 e.printStackTrace();
			if (e instanceof IllegalAccessException) {
				logger.error(
					"editUser() method : caught IllegalAccessException Exception while using BeanUtils.copyProperties()");
			}
			if (e instanceof InvocationTargetException) {
				logger.error(
					"editUser() method : caught InvocationTargetException Exception while using BeanUtils.copyProperties()");
			}
			if (e instanceof LMSException) {
				logger.error(
					"editUser() method : caught KmException Exception while using user creation");
			}
			logger.error("Exception From LoginAction" + e);
			forward = mapping.findForward(CREATEUSER_FAILURE);

		}
		logger.info("editUser() method : successfully updated user details : redirected to target : showList ");
		
		return mapping.findForward(EDIT_USER_SUCCESS);
	}
	
	/**
	*Initializes the User Edit page by listing all the users depending upon the login user type
	**/
	public ActionForward viewUser(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionForward forward = new ActionForward();
		UserMstrFormBean kmUserMstrFormBean = (UserMstrFormBean) form;
		UserMstr userMstrDto = new UserMstr();
		UserMstrService editUserService = new UserMstrServiceImpl();
		HttpSession session = request.getSession();
		UserMstr sessionUserBean =
			(UserMstr) session.getAttribute("USER_INFO");
		//KmElementMstrService elementService = new KmElementMstrServiceImpl();
		saveToken(request);
		logger.info("----------------------view------------------------------");
		/*if ( !isTokenValid(request) ) {
			  return mapping.findForward("error");
			}*/
		try {
			logger.info(
				sessionUserBean.getUserLoginId()
					+ " Entered view User method.");
			String userId = kmUserMstrFormBean.getSelectedUserId();
			logger.info("Selected user Id= " + userId);
			//	String userLoginId=request.getParameter("USER_LOGIN_ID");

			kmUserMstrFormBean.setUserId(userId);
			//Calling service to get the details of user by passing userId
			kmUserMstrFormBean.setUserDetails(
				editUserService.getUserService(userId, sessionUserBean.getKmActorId()));
			UserDto userDto = new UserDto();
			userDto = kmUserMstrFormBean.getUserDetails();
			logger.info("sessionUserBean.getKmActorId()"+sessionUserBean.getKmActorId());
			int loginUserActorId = Integer.parseInt(sessionUserBean.getKmActorId());
			logger.info("sessionUserBean.getKmActorId()"+userDto.getKmActorId());
			if(userDto.getKmActorId()!=null){
				int searchedUserActorId = Integer.parseInt(userDto.getKmActorId());
				if(loginUserActorId >= searchedUserActorId)
					kmUserMstrFormBean.setLevelCheck("N");
				else
					kmUserMstrFormBean.setLevelCheck("Y");
			}
			
			
			
			kmUserMstrFormBean.setUserLoginId(userDto.getUserLoginId());
			kmUserMstrFormBean.setUserFname(userDto.getUserFname());
			kmUserMstrFormBean.setUserMname(userDto.getUserMname());
			kmUserMstrFormBean.setUserLname(userDto.getUserLname());
			kmUserMstrFormBean.setUserMobileNumber(userDto.getUserMobileNumber());
			kmUserMstrFormBean.setUserEmailid(userDto.getUserEmailid());			
			kmUserMstrFormBean.setKmActorId(userDto.getKmActorId());
			kmUserMstrFormBean.setZoneName(userDto.getZoneName());
			kmUserMstrFormBean.setCityZoneName(userDto.getCityZoneName());
			String lobs="";
			String lobId="";
			//String lobCircle ="";
			for(int i=0 ; i < userDto.getLobForUserList().size(); i++){
				lobs = userDto.getLobForUserList().get(i).getLobName();	
				
				lobId = lobId+", "+lobs;
				}			
			kmUserMstrFormBean.setLobId(lobId.replaceFirst(",", ""));
			kmUserMstrFormBean.setElementId(userDto.getElementId());
			kmUserMstrFormBean.setCircleId(userDto.getCircleId());
			kmUserMstrFormBean.setCategoryId(userDto.getCategoryId());
			kmUserMstrFormBean.setPartnerName(userDto.getPartnerName());
			kmUserMstrFormBean.setRole(userDto.getKmActorName());
			logger.info("userDto.getKmActorName()"+userDto.getKmActorName());
			String circles=null;
			String circlesName="";
			for(int i=0 ; i < userDto.getCircleForUserList().size(); i++){
				circles = userDto.getCircleForUserList().get(i).getCircleName()+" ( "+ userDto.getCircleForUserList().get(i).getLobName()+" ) " ;	
				//circlesName = circles;
				circlesName = circlesName+", "+circles;
				
			}	
			//circlesName = circlesName+" ]";
			kmUserMstrFormBean.setCircleName(circlesName.replaceFirst(",", ""));
			logger.info("userDto.getCircleName()"+circlesName);
			String status = userDto.getStatus();
			
			if("A".equals(status))
			{
				kmUserMstrFormBean.setStatus("Active");
			}
			else
			 if("D".equals(status))
			 {
				 kmUserMstrFormBean.setStatus("Deactive");
			 }
			 else
				 kmUserMstrFormBean.setStatus(status);			
			if(userDto.getCreatedBy()!=null)
			kmUserMstrFormBean.setCreatedBy(editUserService.getUserLoginId(userDto.getCreatedBy()));			
			kmUserMstrFormBean.setCreatedDt(userDto.getCreatedDt());
			if(userDto.getUpdatedBy()!=null)
			kmUserMstrFormBean.setUpdatedBy(editUserService.getUserLoginId(userDto.getUpdatedBy()));
			kmUserMstrFormBean.setUpdatedDt(userDto.getUpdatedDt());
			
			return mapping.findForward(VIEW_USER);
		} 
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while initializing editUser page ");
		}
		return null;
	}
	/**
	*Initializes the User Edit page by listing all the users depending upon the login user type
	**/
	public ActionForward viewUserForEdit(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionForward forward = new ActionForward();
		UserMstrFormBean kmUserMstrFormBean = (UserMstrFormBean) form;
		UserMstr userMstrDto = new UserMstr();
		UserMstrService editUserService = new UserMstrServiceImpl();
		HttpSession session = request.getSession();
		UserMstr sessionUserBean =
			(UserMstr) session.getAttribute("USER_INFO");
		//KmElementMstrService elementService = new KmElementMstrServiceImpl();
		saveToken(request);
		logger.info("----------------------view------------------------------");
		try {
			//logger.info("hiiiiiii");
			logger.info(
				sessionUserBean.getUserLoginId()
					+ " Entered view User method.");
			MasterService mstrService = new MasterServiceImpl();
			ArrayList lobList = new ArrayList();
			lobList = mstrService.getLobList();
			
			logger.info("lobList.size()"+lobList.size());
			kmUserMstrFormBean.setLobList(lobList);
			String userId = kmUserMstrFormBean.getSelectedUserId();
			logger.info("Selected user Id= " + userId);
			String lobid = kmUserMstrFormBean.getSelectedLobId();
			logger.info("lobId???????"+lobid);
			//	String userLoginId=request.getParameter("USER_LOGIN_ID");
		
			kmUserMstrFormBean.setUserId(userId);
			//Calling service to get the details of user by passing userId
			kmUserMstrFormBean.setUserDetails(
				editUserService.getUserDetailsForEdit(userId, sessionUserBean.getKmActorId(), Integer.parseInt(lobid)));
			UserDto userDto = new UserDto();
			userDto = kmUserMstrFormBean.getUserDetails();
			logger.info("sessionUserBean.getKmActorId()"+sessionUserBean.getKmActorId());
			int loginUserActorId = Integer.parseInt(sessionUserBean.getKmActorId());
			logger.info("sessionUserBean.getKmActorId()"+userDto.getKmActorId());
			if(userDto.getKmActorId()!=null){
				int searchedUserActorId = Integer.parseInt(userDto.getKmActorId());
				if(loginUserActorId >= searchedUserActorId)
					kmUserMstrFormBean.setLevelCheck("Y");
				/* kmUserMstrFormBean.setLevelCheck("N");  */
				else
					kmUserMstrFormBean.setLevelCheck("Y");
			}
			
			kmUserMstrFormBean.setSelectedLobId(userDto.getLobId()+"");
			kmUserMstrFormBean.setUserLoginId(userDto.getUserLoginId());
			kmUserMstrFormBean.setUserFname(userDto.getUserFname());
			kmUserMstrFormBean.setUserMname(userDto.getUserMname());
			kmUserMstrFormBean.setUserLname(userDto.getUserLname());
			kmUserMstrFormBean.setUserMobileNumber(userDto.getUserMobileNumber());
			kmUserMstrFormBean.setUserEmailid(userDto.getUserEmailid());			
			kmUserMstrFormBean.setKmActorId(userDto.getKmActorId());
			kmUserMstrFormBean.setZoneName(userDto.getZoneName());
			kmUserMstrFormBean.setCityZoneName(userDto.getCityZoneName());
			String lobs="";
			String lobId="";
			for(int i=0 ; i < userDto.getLobForUserList().size(); i++){
				lobs = userDto.getLobForUserList().get(i).getLobName();				
				lobId = lobId+", "+lobs;
				}			
			kmUserMstrFormBean.setLobId(userDto.getLobName());
			kmUserMstrFormBean.setElementId(userDto.getElementId());
			kmUserMstrFormBean.setCircleId(userDto.getCircleId());
			kmUserMstrFormBean.setCategoryId(userDto.getCategoryId());
			kmUserMstrFormBean.setPartnerName(userDto.getPartnerName());
			kmUserMstrFormBean.setRole(userDto.getKmActorName());
			logger.info("userDto.getKmActorName()"+userDto.getKmActorName());
			String circles=null;
			String circlesName="";
			for(int i=0 ; i < userDto.getCircleForUserList().size(); i++){
				circles = userDto.getCircleForUserList().get(i).getCircleName();	
				//circlesName = circles;
				circlesName = circlesName+", "+circles;
				
			}		
			kmUserMstrFormBean.setCircleName(circlesName.replaceFirst(",", ""));
			logger.info("userDto.getCircleName()"+circlesName);
			String status = userDto.getStatus();
			kmUserMstrFormBean.setCreatedBy(editUserService.getUserLoginId(userDto.getCreatedBy()));
			kmUserMstrFormBean.setCreatedDt(userDto.getCreatedDt());
			kmUserMstrFormBean.setUpdatedBy(editUserService.getUserLoginId(userDto.getUpdatedBy()));
			kmUserMstrFormBean.setUpdatedDt(userDto.getUpdatedDt());
			
			if("A".equals(status))
			{
				kmUserMstrFormBean.setStatus("Active");
			}
			else
			 if("D".equals(status))
			 {
				 kmUserMstrFormBean.setStatus("Deactive");
			 }
			 else
				 kmUserMstrFormBean.setStatus(status);
			
			return mapping.findForward(VIEW_USER);
		} 
		
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while initializing editUser page ");
		}
		return null;
	}
	
	
	/**
	*Initializes the User Edit page by listing all the users depending upon the login user type
	**/
	public ActionForward initEdit(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionForward forward = new ActionForward();
		UserMstrFormBean kmUserMstrFormBean = (UserMstrFormBean) form;
		UserMstr userMstrDto = new UserMstr();
		UserMstrService editUserService = new UserMstrServiceImpl();
		MasterService mstrService = new MasterServiceImpl();
		HttpSession session = request.getSession();
		UserMstr sessionUserBean =
			(UserMstr) session.getAttribute("USER_INFO");
		//KmElementMstrService elementService = new KmElementMstrServiceImpl();
		saveToken(request);
		try {
		
			logger.info(
				sessionUserBean.getUserLoginId()
					+ " Entered initEditUser method.");
			kmUserMstrFormBean.reset(mapping, request);
			String userId = kmUserMstrFormBean.getSelectedUserId();
			String lobid = kmUserMstrFormBean.getSelectedLobId();
			logger.info("Selected user Id= " + userId);
			//	String userLoginId=request.getParameter("USER_LOGIN_ID");
			ArrayList lobList = new ArrayList();
			String loginUserActorId = sessionUserBean.getKmActorId();
			logger.info("sessionUserBean.getKmActorId()"+sessionUserBean.getKmActorId());
			kmUserMstrFormBean.setKmLoginActorId(loginUserActorId);
			session.setAttribute("LOGIN_USER_ACTOR_ID", loginUserActorId);
		//	ArrayList<CircleDTO> circleForUserList = new ArrayList<CircleDTO>();
			lobList = mstrService.getLobList();
			kmUserMstrFormBean.setUserId(userId);
			//Calling service to get the details of user by passing userId
			kmUserMstrFormBean.setUserDetails(
				editUserService.getUserDetailsForEdit(userId,sessionUserBean.getKmActorId(),Integer.parseInt(lobid) ));
			UserDto userDto = new UserDto();
			userDto = kmUserMstrFormBean.getUserDetails();
			kmUserMstrFormBean.setUserLoginId(userDto.getUserLoginId());
			kmUserMstrFormBean.setUserFname(userDto.getUserFname());
			kmUserMstrFormBean.setUserMname(userDto.getUserMname());
			kmUserMstrFormBean.setUserLname(userDto.getUserLname());
			kmUserMstrFormBean.setUserMobileNumber(
				userDto.getUserMobileNumber());
			kmUserMstrFormBean.setUserEmailid(userDto.getUserEmailid());
			kmUserMstrFormBean.setStatus(userDto.getStatus());
			kmUserMstrFormBean.setKmActorId(userDto.getKmActorId());
			kmUserMstrFormBean.setElementId(userDto.getElementId());
			logger.info("userDto.getKmActorId()"+userDto.getKmActorId());
			kmUserMstrFormBean.setLobName(userDto.getLobName());
			kmUserMstrFormBean.setSelectedLobId(lobid);
			logger.info(userDto.getCategoryId());
			kmUserMstrFormBean.setCategoryId(userDto.getCategoryId());
			kmUserMstrFormBean.setPartnerName(userDto.getPartnerName());
			kmUserMstrFormBean.setRole(userDto.getKmActorName());
			kmUserMstrFormBean.setActorList(userDto.getActorList());
			kmUserMstrFormBean.setSelectedActorId(Integer.parseInt(userDto.getKmActorId()));
			kmUserMstrFormBean.setCircleName(userDto.getCircleName());
			kmUserMstrFormBean.setCircleMstrId(userDto.getCircleId());
			if(userDto.getZoneFlag()!=null){
				if(userDto.getZoneFlag().equalsIgnoreCase(Constants.ZONE_CODE_FLAG_VALUE)){
					kmUserMstrFormBean.setSelectedTypeId(1);
				}
				else{
					kmUserMstrFormBean.setSelectedTypeId(2);
				}
			}
			
			
			logger.info("userDto.getZoneCode()"+userDto.getZoneCode());
			logger.info("userDto.getCityZoneCode()"+userDto.getCityZoneCode());
			kmUserMstrFormBean.setZoneList(editUserService.getAllChildrenZone(kmUserMstrFormBean.getSelectedTypeId()+"", userDto.getCircleId()));
			
			if((userDto.getCityZoneCode()!=null) && (!userDto.getCityZoneCode().equals("")) ){				
				kmUserMstrFormBean.setSelectedZoneId(Integer.parseInt(userDto.getCityZoneCode()));
				
			}		
			else if ((userDto.getZoneCode()!=null) && (!userDto.getZoneCode().equals(""))  ){				
				kmUserMstrFormBean.setSelectedZoneId(Integer.parseInt(userDto.getZoneCode()));
			}
			else{
				kmUserMstrFormBean.setSelectedZoneId(0); 
			}
			
			String circles=null;
			
			String[] circlesName= new String[100];
			//kmUserMstrFormBean.setCreateMultiple(circlesName);
			@SuppressWarnings("unchecked")
			ArrayList<CircleDTO> circleForUserList = editUserService.getAllChildrenNew(kmUserMstrFormBean.getSelectedLobId(), userDto.getUserLoginId());
			logger.info("circleForUserList.size()"+circleForUserList.size());
			for(int i=0 ; i < circleForUserList.size(); i++){
				circles = circleForUserList.get(i).getCircleMstrId()+"";	
				logger.info("circles"+ circles );
				//circlesName = circles;
				circlesName[i] = circles ;			
				
			}
			kmUserMstrFormBean.setMappedCircleList(circleForUserList);
			logger.info("circlesName"+circlesName.length);	
			//kmUserMstrFormBean.setCreateMultiple(circlesName);
			//kmUserMstrFormBean.setSaveMultiple(circlesName);
			//kmUserMstrFormBean.setSaveMultiple(circlesName);
			kmUserMstrFormBean.setCreateMultiple(circlesName);
			//String circleId = kmUserMstrFormBean.getCircleId();
			kmUserMstrFormBean.setCircleList(editUserService.getAllChildren(kmUserMstrFormBean.getSelectedLobId()));
			logger.info("lobList size"+lobList.size());
			kmUserMstrFormBean.setSelectedLobId(userDto.getLobId()+"");
			logger.info("kmUserMstrFormBean.getSelectedLobId()"+kmUserMstrFormBean.getSelectedLobId());
			kmUserMstrFormBean.setLobList(lobList);
			
			
			logger.info("kmUserMstrFormBean.getSelectedLobId()"+kmUserMstrFormBean.getSelectedLobId());
			
			logger.info(
				"CiateeId = == = = " + kmUserMstrFormBean.getCategoryId());
			/*String[] circleIdArray = { circleId };
			if (kmUserMstrFormBean.getKmActorId().equals(Constants.CIRCLE_CSR)
				|| kmUserMstrFormBean.getKmActorId().equals(Constants.CATEGORY_CSR)) {
				//kmUserMstrFormBean.setCategoryId(0);
				kmUserMstrFormBean.setElementList(
					elementService.getChildren(
						kmUserMstrFormBean.getElementId()));
			}*/
			
			return mapping.findForward(INIT_EDIT_USER);
			
		} catch (Exception e) {
				e.printStackTrace();
			logger.error("Exception occured while initializing editUser page ");
		}
		return null;
	}
	/*
	 * 
	 * fdsfdsfsfds
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */

	public ActionForward checkingTheUser(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		ActionErrors errors = new ActionErrors();
		UserMstrService service = new UserMstrServiceImpl();
		//KmUserMstr sessionUserBean = (KmUserMstr) request.getSession().getAttribute("USER_INFO");
		//logger.info(sessionUserBean.getUserLoginId()+ " Entered in to the view method of KmAlertMstrAction");

		String userId = request.getParameter("userid");
		boolean userExists = service.checkDuplicateUserLogin(userId);
		String flag;
		if (userExists) {
			flag = "true";
			errors.add(
				"errors.login.user_invalid",
				new ActionError("loginUser.exist"));

		} else {
			flag = "false";
			errors.add(
				"errors.login.user_invalid",
				new ActionError("loginUser.notExist"));
		}
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "No-Cache");
		PrintWriter out = response.getWriter();
		out.write(flag);
		out.flush();

		return null;

	}

	public boolean validateUpperCaseChar_(String pwd)
	  {
	  String expr = null;

	  expr = "[A-Z]";
	  if (!AppUtils.findRegularExp(expr, pwd)) {
		  //logger.info("Not Found......");
	    return false;
	    }
	  else
	  {
		//logger.info("Found......");  
	    return true;
	  }
	 }
	  
	  public boolean validateSpecialChar_(String pwd)
	  {
	    String expr = null;

	    expr= "[!@#$%&*]";
	    if (!AppUtils.findRegularExp(expr, pwd)) {
	    	//logger.info(" Not Found......");  
		    return false;
		    }
		  else
		  {
			  //logger.info("Found......");  
		    return true;
		  }
	  }
	  //mothed added by ashutosh for password generation
	  public String generatePwd(String Username) throws Exception {
			
			String pwd = Username.substring(0, 1)
					+ Math.abs(new Random().nextInt()) + Username.substring(2, 3);
			return pwd;
		}
	  
	  
	// Added by ashutosh for sending email

		public String sendMail(String userEmail,
				UserMstrFormBean kmUserMstrFormBean, String master,
				String activity) throws Exception {

			String message = null;
			StringBuffer sbMessage = new StringBuffer();

			String txtMessage = null;

			String strSubject = PropertyReader.getAppValue("lms.user.create.mail.subject");
			sbMessage.append("Hi \n\n");
			
			sbMessage.append(PropertyReader.getAppValue("lms.user.create.mail.body")+"\n\n");

			sbMessage.append("Login ID : "
					+ kmUserMstrFormBean.getUserLoginId()
					+ "\n");

			sbMessage.append("Password : "	
					+ kmUserMstrFormBean.getUserPassword()
					+ "\n");

			sbMessage.append("\n\nRegards ");
			sbMessage.append("\nLMS Administrator ");
			sbMessage
					.append("\n\n/** This is an Auto generated email.Please do not reply to this.**/");
			txtMessage = sbMessage.toString();
			String strHost = PropertyReader.getAppValue("LOGIN.SMTP");
			String strFromEmail = PropertyReader.getAppValue("SENDER.EMAIL");
			//
			logger.info("Login SMTP:" + strHost + " Mail Id:" + strFromEmail);
			try {

				Properties prop = System.getProperties();
				prop.put("mail.smtp.host", strHost);
				Session ses = Session.getDefaultInstance(prop, null);
				MimeMessage msg = new MimeMessage(ses);
				msg.setFrom(new InternetAddress(strFromEmail));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						userEmail));
				msg.setSubject(strSubject);
				msg.setText(txtMessage);
				Transport.send(msg);

			} catch (Exception e) {
				e.printStackTrace();
				//HAVE TO ADD DELETE METHOD FOR ROLLBACK THIS PROCESS
				//loginService.deleteUser(kmUserMstrFormBean.getUserLoginId());
				logger.error("Exception occured in sendMail():" + e.getMessage());
				throw new Exception(e.getMessage());

			}
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
		public ActionForward searchUser(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response)
				throws Exception {
			logger.info("Inside searchUser of User Master Action");

			UserMstrFormBean kmUserMstrFormBean = (UserMstrFormBean) form;
			ActionForward forward = new ActionForward(); 
			forward =  mapping.findForward("searchUser");
			MasterService mstrService = new MasterServiceImpl();
			ArrayList lobList = new ArrayList();
			lobList = mstrService.getLobList();
			logger.info("lobList.size()"+lobList.size());
			kmUserMstrFormBean.setLobList(lobList);
			if(null == kmUserMstrFormBean.getUserLoginId())
			{
				kmUserMstrFormBean.reset(mapping, request);
				kmUserMstrFormBean.setInitStatus("true");	
			}else
			{				
				kmUserMstrFormBean.setInitStatus("false");
				logger.info("searching details for user :: "+kmUserMstrFormBean.getUserLoginId());
				
				// check if user exist in the system				
				UserMstrService userService = new UserMstrServiceImpl();
				
				UserMstr userDto=userService.getUserDetails(kmUserMstrFormBean.getUserLoginId());
				if(userDto==null)
				{
					logger.info("User not found.");
					// User not found
					ActionErrors errors = new ActionErrors();
					errors.add("msg10",new ActionError("user.not.found"));
					saveErrors(request, errors);					
				}
				else
				{	
					logger.info("User id found :"+userDto.getUserId());
					// forward to view/edit Users	
					kmUserMstrFormBean.setSelectedUserId(userDto.getUserId());
					
					forward = viewUserForEdit(mapping, kmUserMstrFormBean, request, response);
				}
				resetToken(request);
			}
			return mapping.findForward("viewUserList");

			}
}//action

