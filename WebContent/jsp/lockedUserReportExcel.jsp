<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >

<%@ page errorPage="ExceptionHandler.jsp"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<html:html>

<HEAD>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition",
			"attachment;filename=LockedUserReport.xls");

	
	
%>
</HEAD>
<body>
<table width="100%" class="mTop30" align="center">
      <TR align="center">
	           			<td width="100%" colspan="6" height="28"align="center"><font size="3"><B><bean:message
					key="lockedUser.title" /></B></font></td>
	</TR>
  <tr></tr>
   
    
    <tr></tr>
			
			<tr class="text white">
			
			<td style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="35"><span class="mLeft5 mTop5">S.No</span>
					</td>
					<td style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="35"><span class="mLeft5 mTop5">User LoginId</span></td>
					<td style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="35"><span class="mLeft5 mTop5">First Name</span>
					</td>
					
					<td style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="35"><span class="mLeft5 mTop5">Last Name</span></td>
					<td style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="35"><span class="mLeft5 mTop5">Lock Type</span></td>
			</tr>
			<tr></tr>

    
                  	 <logic:notEmpty name="LOCKED_USER_LIST">
              	 <bean:define id="userList" name="LOCKED_USER_LIST" type="java.util.ArrayList" scope="request" />
				<logic:iterate name="userList" id="user" indexId="i" type="com.ibm.lms.dto.UserMstr">
			
				

					<TR class="lightBg">

						<TD class="text" align="left"><%=(i.intValue() + 1)%>.</TD>
						<TD class="text" align="left"><bean:write name="user"
							property="userId" /></TD>
						<TD class="text" align="left"><bean:write name="user"
							property="firstName" /></TD>
						<TD class="text" align="left"><bean:write name="user"
							property="lastName" /></TD>
						<TD class="text" align="left"><bean:write name="user"
							property="lockType" /></TD>

						
						
					
					</TR>
				</logic:iterate>
						
		</logic:notEmpty>	
   <logic:empty name="LOCKED_USER_LIST">
			<TR class="lightBg">
				<TD colspan="2" class="error" align="left"><FONT color="red"><bean:message
					key="viewAllFiles.NotFound" /></FONT></TD>
			</TR>
	</logic:empty>
	
	
</table>
</body>
</html:html>
