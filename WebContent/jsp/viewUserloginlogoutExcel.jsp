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
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition","attachment;filename=Login/Logut.xls");

%>
</head>
<body>
	<table width="75%" class="mTop30" align="center" border="1">
	
		<tr class="text white" >
		
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="10%" ><span class="mLeft5 mTop5">User LoginId</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%"  ><span class="mLeft5 mTop5">Login Time</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Element Id</span></th> 
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"width="10%" ><span class="mLeft5 mTop5">Fav Category Id</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="10%" ><span class="mLeft5 mTop5">User First Name</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%"  ><span class="mLeft5 mTop5">User Last Name</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Login Date</span></th> 
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="10%" ><span class="mLeft5 mTop5">Logout Time</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Client Ip</span></th> 			
		</tr>
		<logic:present name="logsUserMstrHistList" scope="request">		
		<logic:empty  scope="request" name="logsUserMstrHistList">
			<TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message
				
					key="login/logout.notFound" /></TD>
				
			</TR>
		</logic:empty>
		</logic:present>
		<logic:present name="logsUserMstrHistList" scope="request">	
		<logic:notEmpty name="logsUserMstrHistList" scope="request">
			
			<logic:iterate id="logs"  name="logsUserMstrHistList" scope="request"   >
				
				<TR class="lightBg">

					<TD class="txt" align="center"><bean:write name="logs" property="userLoginId" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="lastLoginTime" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="elementID" /></TD>
				    <TD class="txt" align="center"><bean:write name="logs" property="favID" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="firstName" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="lastName" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="loginDate" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="logoutTime" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="clientIp" /></TD>				</TR>
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	</table>
</body>
</html>
