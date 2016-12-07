package com.ibm.lms.common;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.FileDataSource;
import javax.activation.DataHandler;
import org.apache.log4j.Logger;
import com.ibm.lms.dao.impl.BulkAssigmentDaoImpl;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.DAOException;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class SendMail 
{
		
		private static final String MAIL_MSG_ERRO  ="Messaging Error";
		private static final String MAIL_MSG_SUCC  ="OK";
		private static final String MAIL_MSG_ADD  ="Address Problem";
		private static final String MAIL_EXCEP  ="Not sent";
		private static Logger logger = Logger.getLogger(SendMail.class.getName());
		private static final String INSERT_EMAIL_HISTORY = "INSERT INTO EMAIL_SENT_HISTORY(EMAIL_MSG, SUBJECT, EMAIL_ID, SENT_ON, RESPONSE) VALUES(?,?,?, current timestamp,?)";
		private static final String SQL_GET_ALERT_CONFIG = "select SUBJECT_TEMPLATE,MSG_TEMPLATE from ALERT_EMAIL_CONFIG where status='A' and ALERT_ID=? with ur";
		private static final String GET_EMAILS="SELECT USER_EMAILID FROM USER_MSTR WHERE USER_LOGIN_ID in (SELECT distinct APPROVER_L1 FROM OLM_APPROVER WHERE OLM_ID=? union all SELECT distinct APPROVER_L2 FROM OLM_APPROVER WHERE OLM_ID=? union all SELECT distinct olm_id FROM OLM_APPROVER WHERE OLM_ID = ?) WITH UR";
		private static String fromEmail = PropertyReader.getAppValue("email.from");
		private static String strHost = PropertyReader.getAppValue("mail.smtp.host");	
		

		
		public void sendMail(String filePath,String emailId) 
	    {
		  
	      boolean debug = false;
	      Properties props = System.getProperties();
	      props.put("mail.smtp.host",strHost);

	      try {
	    	  
	      Session mailSession = Session.getDefaultInstance(props, null);
	      mailSession.setDebug(debug);
	      
	      MimeMessage message = new MimeMessage(mailSession);
	    
		  message.setSubject("LMS Assignment matrix Upload Report");
	      MimeBodyPart textPart = new MimeBodyPart();
	      
	      
	      StringBuffer sbMessage = new StringBuffer();

			String txtMessage = null;

			sbMessage.append("Hi <br> <br> Please find attached the report.");

			sbMessage.append("<br> <br> Regards ");
			sbMessage.append("<br> LMS System Administrator ");
			sbMessage
					.append("<br><br>/** This is an Auto generated email.Please do not reply to this.**/");
			txtMessage = sbMessage.toString();
	      
		  textPart.setContent(txtMessage, "text/html");

	      MimeBodyPart attachFilePart = new MimeBodyPart();
	      FileDataSource fds = new FileDataSource(filePath);
	      attachFilePart.setDataHandler(new DataHandler(fds));
	      attachFilePart.setFileName(fds.getName());

	      Multipart mp = new MimeMultipart();
	      mp.addBodyPart(textPart);
	      mp.addBodyPart(attachFilePart);

	      message.setContent(mp);
	      message.setFrom(new InternetAddress("LMS_Administrator"));
	      message.addRecipient(Message.RecipientType.TO,new InternetAddress(emailId));

	      Transport.send(message);
	      
	      } catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		
			
	
	    

		public static String sendingMail(String filePath,String emailId ,String emailId_cc , String messageTemplate , String subjectTemplate ,String sender ,String host) 
	    {
		  
	      boolean debug = false;
	      Properties props = System.getProperties();
	      props.put("mail.smtp.host",PropertyReader.getAppValue("mail.smtp.host"));

	      try {    	
	       MimeBodyPart attachFilePart	 = null;
	      Session mailSession = Session.getDefaultInstance(props, null);
	      mailSession.setDebug(debug);      
	      MimeMessage message = new MimeMessage(mailSession);
	    
		  message.setSubject(subjectTemplate);
		  
	      MimeBodyPart textPart = new MimeBodyPart();      
	      messageTemplate.concat("<br><br>/** This is an Auto generated email.Please do not reply to this.**/");
		  textPart.setContent(messageTemplate, "text/html");
		 
		  if(filePath !=null) {
		   attachFilePart = new MimeBodyPart();
	      FileDataSource fds = new FileDataSource(filePath);
	      attachFilePart.setDataHandler(new DataHandler(fds));
	      attachFilePart.setFileName(fds.getName());
		  }
	      Multipart mp = new MimeMultipart();
	      mp.addBodyPart(textPart);
	      if(filePath !=null) {
	    	  mp.addBodyPart(attachFilePart);
	      }

	      message.setContent(mp);   
	      message.setFrom(new InternetAddress(sender));
	      if(emailId!=null){
	      message.addRecipient(Message.RecipientType.TO,  new InternetAddress(emailId));
	      }
	      if(emailId_cc!=null){
	      message.addRecipients(Message.RecipientType.CC, emailId_cc);
	      }
	      Transport.send(message);
	      return MAIL_MSG_SUCC;
	      } catch (AddressException ae) {
	    	  ae.printStackTrace();
	    	  return MAIL_MSG_ADD;
	      }catch (MessagingException me) {
	    	  me.printStackTrace();
	    	  return MAIL_MSG_ERRO;
	      }
	      catch (Exception e) {
				e.printStackTrace();
				return MAIL_EXCEP;
			}
	    }
		
		
		public String sendMailReport(String filePath,String emailId ,String emailId_cc , String messageTemplate , String subjectTemplate ,String sender ,String host) 
	    {
		  
	      boolean debug = false;
	      Properties props = System.getProperties();
	      props.put("mail.smtp.host",host);

	      try {    	
	       MimeBodyPart attachFilePart	 = null;
	       String[] mailId ,mailId_cc= null; 
	      Session mailSession = Session.getDefaultInstance(props, null);
	      mailSession.setDebug(debug);      
	      MimeMessage message = new MimeMessage(mailSession);
	    
		  message.setSubject(subjectTemplate);
		  
	      MimeBodyPart textPart = new MimeBodyPart();      
	      messageTemplate.concat("<br><br>/** This is an Auto generated email.Please do not reply to this.**/");
		  textPart.setContent(messageTemplate, "text/html");
		 
		  if(filePath !=null) {
		   attachFilePart = new MimeBodyPart();
	      FileDataSource fds = new FileDataSource(filePath);
	      attachFilePart.setDataHandler(new DataHandler(fds));
	      attachFilePart.setFileName(fds.getName());
		  }
	      Multipart mp = new MimeMultipart();
	      mp.addBodyPart(textPart);
	      if(filePath !=null) {
	    	  mp.addBodyPart(attachFilePart);
	      }

	      message.setContent(mp);   
	      message.setFrom(new InternetAddress(sender));
	      if(emailId!=null){
	    	  mailId= emailId.split(",");
	    	  InternetAddress[] emails= new InternetAddress[mailId.length];
	    	  for(int i=0; i<mailId.length; i++){
	    		  emails[i]= new InternetAddress(mailId[i]);
	    	  }
	      message.addRecipients(Message.RecipientType.TO, emailId);
	      }
	      if(emailId_cc!=null){
	    	  mailId_cc= emailId_cc.split(",");
	    	  InternetAddress[] emails_cc= new InternetAddress[mailId_cc.length];
	    	  for(int i=0; i<mailId_cc.length; i++){
	    		  emails_cc[i]= new InternetAddress(mailId_cc[i]);
	    	  }
	      message.addRecipients(Message.RecipientType.CC, emailId_cc);
	      }
	      Transport.send(message);
	      return MAIL_MSG_SUCC;
	      } catch (AddressException ae) {
	    	  return MAIL_MSG_ADD;
	      }catch (MessagingException me) {
	    	  return MAIL_MSG_ERRO;
	      }
	      catch (Exception e) {
				e.printStackTrace();
				return MAIL_EXCEP;
			}
	    }
	

		public  static String getGeneralMessage(String messageBody) {

			StringBuffer compMessage = new StringBuffer();
			
			compMessage.append("<B><FONT FACE='ARIAL' SIZE='2'color='BLUE'>Dear User" +
					 ",<BR><BR>");
			compMessage.append("\n\n<FONT FACE='ARIAL' SIZE='2'>" + messageBody + "<BR><BR>");
			
			compMessage.append("\n\n<FONT FACE='ARIAL' SIZE='2'>Regards<BR>");
			compMessage
					.append("\n<FONT FACE='ARIAL' SIZE='2'>iLMS Administration</FONT></B><BR><BR>");
			compMessage
					.append("\n\n  /** This is an Auto generated email.Please do not reply to this.**/");
			compMessage.append("<BR><BR>");

			return compMessage.toString();
		}
		
		/*public String getLatestFile(String dirName,String repName) throws IOException {
			String fName=null;
			logger.info("dirname*******"+dirName+"*******"+repName+"**********");
			File readDir = new File(dirName);
			File[] dirList = readDir.listFiles(new FileFilter()
			{
			public boolean accept(File file)
			{
			return file.isFile();
			}
			});
			
			logger.info("dirList.length***********"+dirList.length);
			for(int i=0;i<dirList.length;i++){
				
				if(dirList[i].getName().contains(repName)){
				
				 fName = dirList[i].getName();}
				}
				return fName;
		}
		*/
		
		
}
