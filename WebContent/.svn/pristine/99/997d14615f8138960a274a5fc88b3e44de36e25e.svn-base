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
	response.setHeader("content-Disposition","attachment;filename=UserMstrHistLogs.xlsx");

%>
</head>
<body>
	<table width="75%" class="mTop30" align="center" border="1">
	
		<tr class="text white" >
		
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="10%" ><span class="mLeft5 mTop5">User LoginId</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%"  ><span class="mLeft5 mTop5">First Name</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Middle Name</span></th> 
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"width="10%" ><span class="mLeft5 mTop5">Last Name</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="10%" ><span class="mLeft5 mTop5">User Mobile no</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%"  ><span class="mLeft5 mTop5">User EMailId</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%"  ><span class="mLeft5 mTop5">User Password</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%"  ><span class="mLeft5 mTop5">User Password ExpiryDate</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Created date</span></th> 
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="10%" ><span class="mLeft5 mTop5">Created By</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%"  ><span class="mLeft5 mTop5">Updated Date</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Updated By</span></th> 
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Status</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Group Id</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Actor Id</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Owner Id</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Login Attempted</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Last LoginTime</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">UserLogin Status</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">FAV categoryId</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">ElementId</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">User Alerts</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Alert Id</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Partner Name</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Password ResetTime</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Alert Count</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">OLMId</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Role</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Business Segment</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Process</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Activity</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Partner</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Location</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">EndLogin Time</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Total LoginTime</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">PBX Id</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">Business Segment</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%"  ><span class="mLeft5 mTop5">History Date</span></th>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="10%" ><span class="mLeft5 mTop5">History Action</span></th> 
							
		</tr>
		<logic:present name="logsUserMstrHistList" scope="request">		
		<logic:empty  scope="request" name="logsUserMstrHistList">
			<TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message
				
					key="Usermstrhistlogs.notFound" /></TD>
				
			</TR>
		</logic:empty>
		</logic:present>
		<logic:present name="logsUserMstrHistList" scope="request">	
		<logic:notEmpty name="logsUserMstrHistList" scope="request">
			
			<logic:iterate id="logs"  name="logsUserMstrHistList" scope="request"   >
				
				<TR class="lightBg">

					<TD class="txt" align="center"><bean:write name="logs" property="userLoginId" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="firstName" /></TD>
				    <TD class="txt" align="center"><bean:write name="logs" property="middleName" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="lastName" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="mobileNo" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="emailId" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="userPassword" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="userPasswordExpDate" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="createdDate" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="createdBy" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="updatedDate" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="updatedBy" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="status" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="groupId" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="actorId" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="ownerId" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="loginAttempt" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="lastLoginTime" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="login_status" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="catogoryId" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="elementId" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="user_alert" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="alertId" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="partnerName" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="passwordResetTime" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="alertCount" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="olmId" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="role" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="bussinessSegment" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="process" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="activity" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="partner" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="location" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="endLoginTime" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="totalLogintime" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="pbxId" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="historyDate" /></TD>
					<TD class="txt" align="center"><bean:write name="logs" property="historyAction" /></TD>	
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	</table>
</body>
</html>
