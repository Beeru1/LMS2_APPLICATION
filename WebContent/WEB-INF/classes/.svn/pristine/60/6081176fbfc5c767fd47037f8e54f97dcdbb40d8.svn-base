package com.ibm.km.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ibm.lms.actions.LeadRegistrationAction;
import com.ibm.lms.engine.util.ServerPropertyReader;

/**
 * Servlet implementation class LoadPropertyServlet
 */
public class LoadPropertyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger;
	static {
		logger = Logger.getLogger(LoadPropertyServlet.class);
	}

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadPropertyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.info("***********start loading****************************");
		ServerPropertyReader.loadPropertyServer();
		logger.info("***********success loaded****************************");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
