<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
<head>
<title>viewUsersExcel</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">

<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition",
			"attachment;filename=UserReport.xls");

	
	
%>
</head>
<body>
	<table width="75%" class="mTop30" align="center">

		<tr align="center">
			<td colspan="9"><span class="heading"><bean:message
				key="viewAllUser.ViewUsers" /></span></td>
		</tr>
		<tr align="left">
			<td colspan="12"><strong><bean:write property="message"
				name="kmUserMstrFormBean" /></strong></td>
		</tr>
		<tr>
				<td colspan="6" align="center" class="error">
					<strong> 
          			<html:messages id="msg" message="true">
                 		<bean:write name="msg"/>  
                          
             		</html:messages>
            		</strong>
            	</td>
			</tr>
		
		<tr class="text white">
			<td style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="35"><span class="mLeft5 mTop5"><bean:message
			    key="viewAllUser.SNO" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="35"><span class="mLeft5 mTop5"><bean:message
				key="viewAllUser.UserLoginId" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="35"><span class="mLeft5 mTop5"><bean:message
				key="viewAllUser.FirstName" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="35"><span class="mLeft5 mTop5"><bean:message
				key="viewAllUser.LastName" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="35"><span class="mLeft5 mTop5"><bean:message
				key="viewAllUser.MobileNumber" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="35"><span class="mLeft5 mTop5"><bean:message
				key="viewAllUser.EmailId" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="35"><span class="mLeft5 mTop5"><bean:write 
			    name="kmUserMstrFormBean"	property="elementType" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="35"><span class="mLeft5 mTop5"><bean:message
				key="viewAllUser.UserType" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="35"><span class="mLeft5 mTop5"><bean:message
				key="viewAllUser.Status" /></span></td>
		<!--	<td bgcolor="a90000" align="center">&nbsp;</td>
			<td bgcolor="a90000" align="center">&nbsp;</td>  -->
		</tr>
		<logic:empty name="kmUserMstrFormBean" property="userList">
			<TR class="lightBg">
				<TD class="text" align="center"><bean:message
					key="viewAllUser.NotFound" /></TD>
			</TR>
		</logic:empty>
		<logic:notEmpty name="kmUserMstrFormBean" property="userList">
			<logic:iterate name="kmUserMstrFormBean" id="report" indexId="i"
				property="userList">
				
				<TR class="lightBg">
					<TD class="text" align="center"><%=(i.intValue() + 1)%>.</TD>
					<TD class="txt" align="center"><bean:write name="report"
						property="userLoginId" /></TD>
					<TD class="txt" align="center"><bean:write name="report"
						property="userFname" /></TD>
					<TD class="txt" align="center"><bean:write name="report"
						property="userLname" /></TD>
				<TD class="txt" align="center"><bean:write name="report"
						property="userMobileNumber" /></TD> 
					<TD class="txt" align="center"><bean:write name="report"
						property="userEmailid" /></TD>
					<TD class="txt" align="center"><bean:write name="report"
						property="elementName" /></TD>
					<TD class="txt" align="center"><bean:write name="report"
						property="userType" /></TD>
					<TD class="txt" align="center"><bean:write name="report"
						property="status" /></TD>
					
				
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	
	</table>
</body>
</html>
