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
	response.setHeader("content-Disposition","attachment;filename=Fids Report.xls");

%>
</head>
<body>
	<table width="75%" class="mTop30" align="center" border="1">
	
		<tr class="text white" >
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" bgcolor="yellow" ><span class="mLeft5 mTop5">FID</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" bgcolor="yellow"  ><span class="mLeft5 mTop5">PAGE_URL</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%" bgcolor="yellow" ><span class="mLeft5 mTop5">Status</span></th>
		</tr>
		<logic:present name="fidsList" scope="request">		
		<logic:empty  scope="request" name="fidsList">
			<TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message
				
					key="assignmentDownload.NotFound" /></TD>
				
			</TR>
		</logic:empty>
		</logic:present>
		<logic:present name="fidsList" scope="request">	
		<logic:notEmpty name="fidsList" scope="request">
			
			<logic:iterate id="report"  name="fidsList" scope="request">
				<TR class="lightBg">
					<TD class="txt" align="center"><bean:write name="report"property="fid" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="pageUrl" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="status" /></TD>
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	</table>
</body>
</html>