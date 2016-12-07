<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">

<%
	response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	response.setHeader("content-Disposition","attachment;filename=SMSLogs.xlsx");

%>
</head>
<body>
	<table width="75%" class="mTop30" align="center" border="1">
	
		<tr class="text white" >
		
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="10%" ><span class="mLeft5 mTop5">Mobile No</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%"  ><span class="mLeft5 mTop5">Message Content</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">SentSMS Time</span></th> 
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"width="10%" ><span class="mLeft5 mTop5">Response Status</span></th>
		</tr>
		<logic:present name="logsSMSList" scope="request">		
		<logic:empty  scope="request" name="logsSMSList">
			<TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message
				
					key="SMSlogs.notFound" /></TD>
				
			</TR>
		</logic:empty>
		</logic:present>
		<logic:present name="logsSMSList" scope="request">	
		<logic:notEmpty name="logsSMSList" scope="request">
			
			<logic:iterate id="logs"  name="logsSMSList" scope="request"   >
				
				<TR class="lightBg">

					<TD class="txt" align="center"><bean:write name="logs" property="mobileNo" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="messageContent" /></TD>
				    <TD class="txt" align="center"><bean:write name="logs" property="sentDate" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="responseStatus" /></TD>
						
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	</table>
</body>
</html>
