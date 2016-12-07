package com.ibm.lms.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * @version 	1.0
 * @author
 */
public class LoginFilter implements Filter {
	
	private static final Logger logger;

	static {

		logger = Logger.getLogger(LoginFilter.class);
	}
	/**
	* @see javax.servlet.Filter#void ()
	*/
	private String onErrorUrl;
	public void destroy() {
		//logger.info("Filter destroyed");

	}

	/**
	* @see javax.servlet.Filter#void (javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	*/
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
		throws ServletException, IOException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		String requestedURI = request.getRequestURI();
		//StringBuffer requestedURL=request.getRequestURL();
		logger.info("Request URI: "+requestedURI);
		logger.info("uri____________"+requestedURI);
		//if(HttpServletResponse.SC_FOUND==300){
		if (requestedURI.lastIndexOf(".do") != requestedURI.length()-3 ) {
			session.invalidate();
			response.sendRedirect((request.getContextPath()) + onErrorUrl);
		} else {
			if (session.getAttribute("USER_INFO") == null) {
				session.invalidate();
				response.sendRedirect((request.getContextPath()) + onErrorUrl);
			} else {
				chain.doFilter(request, response);
				//logger.info("Filter destroyed");
			}

		}
		//}
		}




	/**
	* Method init.
	* @param config
	* @throws javax.servlet.ServletException
	*/
	public void init(FilterConfig config) throws ServletException {
		onErrorUrl = config.getInitParameter("onError");
			
logger.info("Heloo-------------------");
	} 

}
