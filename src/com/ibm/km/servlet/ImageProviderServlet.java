package com.ibm.km.servlet;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.ibm.km.services.KmEmployeeAppreciationService;
import com.ibm.lms.common.Utility;


 public class ImageProviderServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ImageProviderServlet.class.getName());
	 
	 public ImageProviderServlet() {
		super();
	}   	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			// put logger comments here
			
			String reqType = request.getParameter("requestType");
			if(reqType == null)
				reqType="";
				if(reqType.equals("xmlFile")) {
					String docPath = request.getParameter("docPath");
					String tagId = request.getParameter("tagId");
					int tagIndex = Integer.parseInt(tagId);
					String tagName = request.getParameter("tagName");
					String content = getContent(docPath, tagName, tagIndex);
					ServletOutputStream out = response.getOutputStream();
					out.print(content);
					out.close();
				} else 
					if(reqType.equals("productImage")) {
					String path = request.getParameter("imagePath");
					 FileInputStream fstream = new FileInputStream(path);
					InputStream in = fstream;
					if(in != null) {
					response.setContentType("image/jpeg");   
					ServletOutputStream out = response.getOutputStream();   
					int size = in.available();   
					byte[] content = new byte[size]; 
					in.read(content);   
					out.write(content);   
					in.close();   
					out.close();  
					}
				}
					

		}catch(Exception e){
			e.printStackTrace();
			logger.info("Exception occured while writing image : "+e);
		}
		
	}
 	
	private String getContent(String docPath, String tagName, int tagId) {
		
		String htmlContent = "";
		  String documentPath =docPath;
		  
		  try {
		  FileInputStream fstream = new FileInputStream(documentPath);
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  StringBuffer sb = new StringBuffer();
		  String strLine="";
			
		  while ((strLine = br.readLine()) != null)   {
			  sb.append(strLine);
		  }
		  in.close();
		  
		    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			
			is.setCharacterStream(new StringReader(sb.toString()));
			Document doc = db.parse(is);                
			doc.getDocumentElement().normalize();
			
			NodeList nodeLst =  doc.getElementsByTagName(tagName);
							
			int contentLength = nodeLst.getLength();
			String content[] = new String[contentLength];

			for (int s = 0; s < contentLength; s++) {
				content[s] = nodeLst.item(s).getTextContent();
			}

			if(tagId < content.length) 
				htmlContent = Utility.decodeContent(content[tagId]);
			
		  } catch (Exception e) {
				e.printStackTrace();
				logger.info("Exception occured while getting content : "+ e + " Document: " + docPath);
		  }
		
		return htmlContent;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			String imagePath = request.getParameter("imagePath");
			//logger.info("imagePath : "+imagePath);
			response.setContentType("image/jpeg");   
			ServletOutputStream out = response.getOutputStream();   
			
			FileInputStream in = new FileInputStream(imagePath);   
			int size = in.available();   
			byte[] content = new byte[size]; 
			in.read(content);   
			out.write(content);   
			in.close();   
			out.close();  

		}catch(Exception e){
			e.printStackTrace();
			logger.info("Exception occured while writing image : "+e);
		}
		
	}
 	
}