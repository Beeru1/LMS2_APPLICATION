package com.ibm.km.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ibm.km.dto.KmAlertMstr;
import com.ibm.km.services.impl.KmAlertMstrServiceImpl;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.services.UserMstrService;
import com.ibm.lms.services.impl.UserMstrServiceImpl;

/**
 * Servlet implementation class for Servlet: SMSAlertServlet
 *
 */
 public class SMSAlertServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	 private static Logger logger = Logger.getLogger(SMSAlertServlet.class.getName());
	 
	 public SMSAlertServlet() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			
			
			logger.info("***SMS ALERT***");
			String mobileNumber = request.getParameter("mobileNumber");
			String message = request.getParameter("message");
			//out.println(message);
			KmAlertMstr alertDto= null;
			String keyword = PropertyReader.getAppValue("FH.KEYWORD");
			
			logger.info("Request for Mobile Number   "+  mobileNumber); 
			logger.info("Alert Message  "+  message);
			if (null != mobileNumber || !(mobileNumber.equals(""))) {

				if(message==null || message.trim().equals("") || message.length() <= keyword.length()  ){
					out.print("No Alert Message Found in the Request");
					return;
				}
				else{
					message = message.substring(keyword.length()+1).trim();
					if(message.equals("")){
						out.print("No Alert Message Found in the Request");
						return;
					}else if(message.length()>250){
						out.print("Message Length Exceeds Maximum Limit");
					}
					mobileNumber=mobileNumber.trim();
					if(mobileNumber.startsWith("91")){
						mobileNumber=mobileNumber.substring(2, mobileNumber.length());
					}else if(mobileNumber.startsWith("0")){
						mobileNumber=mobileNumber.substring(1, mobileNumber.length());
					}
					UserMstrService userService = new UserMstrServiceImpl();
					UserMstr userDto=userService.userIdFromMobile(mobileNumber);
					if(userDto!=null){
						
							alertDto = new KmAlertMstr();
							alertDto.setCreatedBy(userDto.getUserId());
							alertDto.setUpdatedBy(userDto.getUserId());
							alertDto.setCircleId(userDto.getElementId());
							alertDto.setMessage(message);
							alertDto.setAlertSource(mobileNumber);
							alertDto.setStatus("A");
							KmAlertMstrServiceImpl service = new KmAlertMstrServiceImpl();
							service.createAlert(alertDto);
							StringBuffer responseMessage = new StringBuffer("");
							
							responseMessage.append("Hi ").append(userDto.getUserFname()).append(", An alert Message is created. \nMessage : ").append(message);
							out.print(responseMessage.toString());
					}	
					
					else{
						out.print("User Account Information Not Found.");
					}
				}				
				
				
			}
			else {
				logger.info("Mobile Number not obtained");
				out.print("Mobile Number cannot  be obtained");
				
			}

		}catch(Exception e){
			logger.info("Exception occured while creating alert : "+e);
			out.print("Some problems occured while creating the alert message. Please contact customer care");
		}
		
	}
 	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			
			
			logger.info("***SMS ALERT***");
			String mobileNumber = request.getParameter("mobileNumber");
			String message = request.getParameter("message");
			//out.println(message);
			KmAlertMstr alertDto= null;
			String keyword = PropertyReader.getAppValue("FH.KEYWORD");
			
			logger.info("Request for Mobile Number   "+  mobileNumber); 
			logger.info("Alert Message  "+  message);
			if (null != mobileNumber || !(mobileNumber.equals(""))) {

				if(message==null || message.trim().equals("") || message.length() <= keyword.length()  ){
					out.print("No Alert Message Found in the Request");
					return;
				}
				else{
					message = message.substring(keyword.length()+1).trim();
					if(message.equals("")){
						out.print("No Alert Message Found in the Request");
						return;
					}else if(message.length()>250){
						out.print("Message Length Exceeds Maximum Limit");
					}
					mobileNumber=mobileNumber.trim();
					if(mobileNumber.startsWith("91")){
						mobileNumber=mobileNumber.substring(2, mobileNumber.length());
					}else if(mobileNumber.startsWith("0")){
						mobileNumber=mobileNumber.substring(1, mobileNumber.length());
					}
					UserMstrService userService = new UserMstrServiceImpl();
					UserMstr userDto=userService.userIdFromMobile(mobileNumber);
					if(userDto!=null){
						
							alertDto = new KmAlertMstr();
							alertDto.setCreatedBy(userDto.getUserId());
							alertDto.setUpdatedBy(userDto.getUserId());
							alertDto.setCircleId(userDto.getElementId());
							alertDto.setMessage(message);
							alertDto.setAlertSource(mobileNumber);
							alertDto.setStatus("A");
							KmAlertMstrServiceImpl service = new KmAlertMstrServiceImpl();
							service.createAlert(alertDto);
							StringBuffer responseMessage = new StringBuffer("");
							
							responseMessage.append("Hi ").append(userDto.getUserFname()).append(", An alert Message is created. \nMessage : ").append(message);
							out.print(responseMessage.toString());
					}	
					
					else{
						out.print("User Account Information Not Found.");
					}
				}				
				
				
			}
			else {
				logger.info("Mobile Number not obtained");
				out.print("Mobile Number cannot  be obtained");
				
			}

		}catch(Exception e){
			logger.info("Exception occured while creating alert : "+e);
			out.print("Some problems occured while creating the alert message. Please contact customer care");
		}
		
	}
	
	
}