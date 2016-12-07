
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE></TITLE>
<logic:notEmpty name="LOGGED_IN_USER_LIST">
<bean:define id="userList" name="LOGGED_IN_USER_LIST" type="java.util.ArrayList" scope="request" />
</logic:notEmpty>

<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition",
			"attachment;filename=userLoginExcelReport.xls");

	
	
%>
</HEAD>

<BODY>
      
<table width="100%" class="mTop30" align="center">
<TR align="center">
	           			<td width="100%" colspan="6" height="28"align="center"><font size="3"><B><bean:message
					key="userLoginStatus.title" /></B></font></td>
	</TR>
  <tr></tr>
  <tr class="lightBg">
		<td  colspan="10" align="left" width="90%"><FONT size="2"><B>Search Location :</B></FONT>&nbsp;&nbsp;<bean:write name="userReportForm" property="elementPath" /></td>
	</tr>
	<tr></tr>
	
				<tr class="text white">

					<td class="darkRedBg height19"><span class="mLeft5 mTop5"><font size="2"><B>S.No</B></font></span>
					</td>
					<td class="darkRedBg height19"><span class="mLeft5 mTop5"><font size="2"><B>User LoginId</B></font></span></td>
					<td class="darkRedBg height19"><span class="mLeft5 mTop5"><font size="2"><B>First Name</B></font></span>
					</td>
					<td class="darkRedBg height19"><span class="mLeft5 mTop5"><font size="2"><B>Last Name</B></font></span></td>
					<td class="darkRedBg height19"><span class="mLeft5 mTop5"><font size="2"><B>Login Time</B></font></span></td>
					<td class="darkRedBg height19"><span class="mLeft5 mTop5"><font size="2"><B>Partner Name</B></font></span></td>
					<td class="darkRedBg height19"><span class="mLeft5 mTop5"><font size="2"><B>Circle</B></font></span></td>
					<td class="darkRedBg height19"><span class="mLeft5 mTop5"><font size="2"><B>LOB</B></font></span></td>
					<td class="darkRedBg height19"><span class="mLeft5 mTop5"><font size="2"><B>Role</B></font></span></td>
					
					
					<!--<td class="darkRedBg height19"><span class="mLeft5 mTop5">Last LogIn Time</span></td>-->
					

				</tr><tr></tr>
					
 	 <logic:notEmpty name="LOGGED_IN_USER_LIST">
 	 <logic:iterate name="userList" id="file" indexId="i" type="com.ibm.lms.dto.UserMstr">

 
		
                        <TR class="lightBg">
						<TD class="text" align="left"><%=(i.intValue() + 1)%>.</TD>
						<TD class="text" align="left"><bean:write name="file"
							property="userId" /></TD>
						<TD class="text" align="left"><bean:write name="file"
							property="userFname" /></TD>
						<TD class="text" align="left"><bean:write name="file"
							property="userLname" /></TD>
						<TD class="text" align="left"><bean:write name="file"
							property="loginTime" /></TD>
						<TD class="text" align="left"><bean:write name="file"
							property="partnerName" /></TD>
						<TD class="text" align="left"><bean:write name="file"
							property="circle" /></TD>
						<TD class="text" align="left"><bean:write name="file"
							property="lob" /></TD>
						<TD class="text" align="left"><bean:write name="file"
							property="role" /></TD>
						
					
					</TR>
				</logic:iterate>
		</logic:notEmpty>
					<logic:empty name="LOGGED_IN_USER_LIST">
					<TR align="center">
					<TD height="27" align="center" colspan="14" class="txt"><font
						color="red">No Records found</font></TD>
			</TR>
			</logic:empty>
			
		</table>
       
</BODY>
</html:html>
