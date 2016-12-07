package com.ibm.lms.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.tiles.TilesRequestProcessor;

import com.ibm.lms.dto.UserMstr;

public class LMSRequestProcessor extends TilesRequestProcessor { 

	private static final Logger logger;

	static {

		logger = Logger.getLogger(LMSRequestProcessor.class);
	}
	
	protected boolean processPreprocess(HttpServletRequest request, HttpServletResponse response) 
	{
		String requestedURI = request.getRequestURI();
		String requestMapping=requestedURI.substring(requestedURI.lastIndexOf("/") + 1,requestedURI.lastIndexOf(".do"));
		boolean flag = false;
		//boolean flag = true;
		logger.info("REqqee---"+requestMapping);
		try {
			logger.info("Inside request processor...");
			
			
			if("login".equalsIgnoreCase(requestMapping)){
				String userId=(String) request.getParameter("userid");
				String password=(String) request.getParameter("password");
				if(userId!=null&&request.getMethod().equals("POST")){
					request.setAttribute("userid",userId);
					request.setAttribute("password",password);
				}
			} 
			
			if("Altlogin".equalsIgnoreCase(requestMapping)){
				String userId=(String) request.getParameter("userid");
				String password=(String) request.getParameter("password");
				if(userId!=null&&request.getMethod().equals("POST")){
					request.setAttribute("userid",userId);
					request.setAttribute("password",password);
				}
			} 
			
			if("UDlogin".equalsIgnoreCase(requestMapping)){
				String userId=(String) request.getParameter("userid");
				String password=(String) request.getParameter("password");
				//if(userId!=null&&request.getMethod().equals("POST")){
					request.setAttribute("userid",userId);
					request.setAttribute("password",password);
				//}
			} 
			if("OTPlogin".equalsIgnoreCase(requestMapping))
			{
				String OTPCode=(String) request.getParameter("OTPCode");
				//String password=(String) request.getParameter("password");
				//if(userId!=null&&request.getMethod().equals("POST")){
					request.setAttribute("OTPCode",OTPCode);
					//request.setAttribute("password",password);
			}
			if (!(("login".equalsIgnoreCase(requestMapping))||("UDlogin".equalsIgnoreCase(requestMapping))||("Altlogin".equalsIgnoreCase(requestMapping)))
				&& !("logout".equalsIgnoreCase(requestMapping))
				&& !("initCSRLogin".equalsIgnoreCase(requestMapping))
				//Added by Atul 
				&& !("csrPageLogin".equalsIgnoreCase(requestMapping))
			&& !("timeout".equalsIgnoreCase(requestMapping))&&!("OTPlogin".equalsIgnoreCase(requestMapping))) 
			{
				if ("initforgotPassword".equalsIgnoreCase(requestMapping)
					|| "forgotPassword".equalsIgnoreCase(requestMapping)||"initCSRLogin".equalsIgnoreCase(requestMapping)) {
					flag = true;
					return flag;
				} 
				if("logout".equalsIgnoreCase(requestMapping))
					return true;
				
				HttpSession session = request.getSession();
				UserMstr sessionUserBean = (UserMstr) session.getAttribute("USER_INFO");
				
				if (sessionUserBean== null) {
					//logger.info("Invalid Session. Please Login again.");
					logger.info("Invalid Session. Please Login again.");
					session.invalidate();
					doForward("/timeout.do", request, response);
					flag = false;
				}
				else
				{
				//  Prevent unauthorized module access - redirect to login	
					
				/*	String methodName =(String) request.getParameter("methodName");
					
						
					if (null == methodName)
					{
						if(null != request.getContentType()) 
						{
							String tempContentType = new String(request.getContentType());
							if(tempContentType.contains("multipart/form-data"))
							{
								UserMstr userBean = (UserMstr) (request
										.getSession())
										.getAttribute("USER_INFO");
								if (userBean != null) {
									String actor = userBean.getKmActorId();
									
								// All the excel upload user role to be included ------------ 
									
									if (!(actor.equals("1"))) {
										logger.info("\nUnauthorized URL Access, please Login again. MethodName : "+ methodName);
										session.invalidate();
										doForward("/login.do", request,	response);
										return false;
									}
								}								
							}
						}
					}
					if(null != methodName)
					{
					    boolean isAuthorised = false;							
					    LinkMstrDto kmLinkMstrDto = null;
					    String temp = requestMapping + ".do?methodName=" + methodName;
					    
					    ArrayList<LinkMstrDto> userRoleList =  (ArrayList<LinkMstrDto>) session.getAttribute("USER_ROLE_LIST");
						for (int i = 0; i < userRoleList.size(); i++) {
							kmLinkMstrDto = userRoleList.get(i);
						//	logger.info("RoleList::: [ " + kmLinkMstrDto.getLinkPath()  + "     >>>> "+ session.getId() );
							if(kmLinkMstrDto.getLinkPath().startsWith(temp)) {
								isAuthorised = true;
								break;
							}
						}
						
						UserMstr tempUser = (UserMstr) session.getAttribute("USER_INFO");

						if(!isAuthorised)
						{
							logger.info("\nUnauthorized URL [ "+temp+" ] Access, please Login again.");
							
							FileWriter fw = new FileWriter("/lms_common/LMS/Logs/LMS_Unauthorized_URL_File.txt",true); 
                            fw.write("Unauthorized URL Access :: " + temp+"\n\n");
                            fw.close();
                            
                            
							session.invalidate();
							doForward("/login.do", request, response);
							return false;
						}
					}	
					
					*/
					// unauthorized access control block ends here 
					
					
					
					if (null == session.getAttribute("USER_CHANGEPWD")) {
						flag = true;
					} else {
						if (sessionUserBean.getUserPassword().equals(session.getAttribute("USER_CHANGEPWD"))) {
							flag = true;
						} else {
							doForward("/Logout.do", request, response);
							flag = false;
						}
					}
				}
			} else {
				flag = true;							
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	/*protected ActionForward processActionPerform(HttpServletRequest request,  
            HttpServletResponse response, Action action, ActionForm form,  
            ActionMapping mapping) throws IOException, ServletException {  
		logger.info("*****************************befor********processActionPerform*****************************");
        ActionForward forward = super.processActionPerform(request, response, action, form, mapping); 
        logger.info("*****************************processActionPerform*****************************");
        if(forward == null){  
            //create new forward for error page & put error in perticular scope show it in jsp.  
        }  
        return forward;  
          
    }  */
}