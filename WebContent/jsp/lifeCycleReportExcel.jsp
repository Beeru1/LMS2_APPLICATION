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
	response.setHeader("content-Disposition","attachment;filename=LifeCycleReport.xls");

%>
</head>
<body>
	<table width="75%" class="mTop30" align="center" border="1">
	
		<tr class="text white" >																				

			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Lead Id</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%"  ><span class="mLeft5 mTop5">Lob Name</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%" ><span class="mLeft5 mTop5">Insert Date</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"width="5%" ><span class="mLeft5 mTop5">Verification Date </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">Assigned to Center Date & Time</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">Contact Date & Time</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"   width="5%" ><span class="mTop5">Qualified Date & Time</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">Assigned Date & Time</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Reassigned</span></th>			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Closure</span></th>
			
			
		</tr>
		<logic:present name="reportList" scope="request">		
		<logic:empty  scope="request" name="reportList">
			<TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message
				
					key="assignmentDownload.NotFound" /></TD>
				
			</TR>
		</logic:empty>
		</logic:present>
		<logic:present name="reportList" scope="request">	
		<logic:notEmpty name="reportList" scope="request">
			
			<logic:iterate id="report"  name="reportList" scope="request"   >
				
				<TR class="lightBg">
				
					<TD class="txt" align="center"><bean:write name="report" property="leadId" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="lobName" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="insertDate" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="veriDate" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="assignToCenDate" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="contactDate" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="qualifiedDate" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="assognedDate" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="reassignedDate" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="closuredate" /></TD>
					
				
				
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	</table>
</body>
</html>
