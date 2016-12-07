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
	response.setHeader("content-Disposition","attachment;filename=EmailLogs.xlsx");

%>
</head>
<body>
	<table width="75%" class="mTop30" align="center" border="1">
	
		<tr class="text white" >
		
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="10%" ><span class="mLeft5 mTop5">User MailId</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%"  ><span class="mLeft5 mTop5">Email MessageContent</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Email SubjectMessage</span></th> 
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"width="10%" ><span class="mLeft5 mTop5">SentMail Date</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="10%" ><span class="mTop5">Response Status</span></th>
							
		</tr>
		<logic:present name="logsEmailList" scope="request">		
		<logic:empty  scope="request" name="logsEmailList">
			<TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message
				
					key="Emaillogs.notFound" /></TD>
				
			</TR>
		</logic:empty>
		</logic:present>
		<logic:present name="logsEmailList" scope="request">	
		<logic:notEmpty name="logsEmailList" scope="request">
			
			<logic:iterate id="logs"  name="logsEmailList" scope="request"   >
				
				<TR class="lightBg">

					<TD class="txt" align="center"><bean:write name="logs" property="emailId" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="emailMessage" /></TD>
				    <TD class="txt" align="center"><bean:write name="logs" property="emailSubject" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="sentDate" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="responseStatus" /></TD>
						
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	</table>
</body>
</html>
