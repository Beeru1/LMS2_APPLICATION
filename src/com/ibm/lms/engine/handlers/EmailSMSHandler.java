package com.ibm.lms.engine.handlers;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibm.lms.common.Constants;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.common.SendSmsEmailLog;
import com.ibm.lms.dto.BulkAssignmentDto;
import com.ibm.lms.engine.AsyncTaskManager;
import com.ibm.lms.engine.LMSHandler;
import com.ibm.lms.engine.exception.LmsException;
import com.ibm.lms.exception.DAOException;

public class EmailSMSHandler extends LMSHandler
{

	private BulkAssignmentDto bulkAssignmentDto;
	SendSmsEmailLog sendSmsEmailLog= new SendSmsEmailLog();
	String msg="";
	private static Logger lmsLogger=Logger.getLogger(EmailSMSHandler.class);
	
	public EmailSMSHandler(BulkAssignmentDto bulkAssignmentDto)
	{
		this.bulkAssignmentDto=bulkAssignmentDto;
	}
	
	
	public Boolean call()  {
		try {
			//logger.info("inside call method******************************");
			lmsLogger.info("LMSHandler::call method****************aaaaaaaaaa************"+this.bulkAssignmentDto.getFlagSmsEmail());
		
			process();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Boolean(true);
	}
	
	
	protected void process() throws LmsException
	{
		//logger.info("inside process method******************************");
		if(bulkAssignmentDto.getFlagSmsEmail()==Constants.SMSFLAG || bulkAssignmentDto.getFlagSmsEmail()==Constants.BOTH )
		{
			//logger.info("inside process method*****111111111111111*************************");
			try {
				sendSmsEmailLog.SendSMSAndLog(bulkAssignmentDto);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(bulkAssignmentDto.getFlagSmsEmail()==Constants.EMAILFLAG || bulkAssignmentDto.getFlagSmsEmail()==Constants.BOTH )
		{
			//logger.info("inside process method***********22222222222*******************");
			try {
				msg = sendSmsEmailLog.SendEmailAndLog(bulkAssignmentDto);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Connection connection;
		try {
			connection = DBConnection.getDBConnection();
			sendSmsEmailLog.createLogs(connection,bulkAssignmentDto,msg);
			connection.close();
		    }
		catch (DAOException e1) 
			{
			e1.printStackTrace();
			} catch (SQLException e) 
			  {
			   e.printStackTrace();
		      }
		AsyncTaskManager.getInstance().shutDownAll();
	      }
}
