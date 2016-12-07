<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">

<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition","attachment;filename=UserMasterDownload.xls");

%>
</head>
<body>
	<table width="75%" class="mTop30" align="center" border="1">
	
		<tr class="text white" >
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" bgcolor="yellow" ><span class="mLeft5 mTop5">USER_LOGIN_ID</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%" bgcolor="yellow" ><span class="mLeft5 mTop5">CIRCLE_ID</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%" bgcolor="yellow" ><span class="mLeft5 mTop5">USER_FNAME</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"width="5%" ><span class="mLeft5 mTop5">USER_MNAME </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">USER_LNAME</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">USER_ROLE</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"   width="5%" ><span class="mTop5">EMAIL_ID</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">MOBILE_NO</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">PARTNER</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">CREATED_DT</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">CREATED_BY</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">UPDATED_DT</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">UPDATED_BY</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">STATUS</span></th>
			
			
			
			
		</tr>
		<logic:present name="assignmentList" scope="request">		
		<logic:empty  scope="request" name="assignmentList">
			<TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message
				
					key="assignmentDownload.NotFound" /></TD>
				
			</TR>
		</logic:empty>
		</logic:present>
		<logic:present name="assignmentList" scope="request">	
		<logic:notEmpty name="assignmentList" scope="request">
			
			<logic:iterate id="report"  name="assignmentList" scope="request"   >
				
				<TR class="lightBg">
					
					<TD class="txt" align="center"><bean:write name="report" property="userLoginId" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="circleId" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="userFName" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="userMName" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="userLName" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="userRole" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="emailId" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="mobileNo" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="partner" /></TD>
					
					<TD class="txt" align="center"><bean:write name="report"property="created" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="createdBy" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="updated" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="updatedBy" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="status" /></TD>
					
				
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	</table>
</body>
</html>
