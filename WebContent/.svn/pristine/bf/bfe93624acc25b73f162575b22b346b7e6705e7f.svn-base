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
	response.setHeader("content-Disposition","attachment;filename=LeadDashboardReport.xls");

%>
</head>
<body>
	<table width="75%" class="mTop30" align="center" border="1">
	<TR class="text white">
            <TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">SL.</TH> 
             <TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">Duration</TH> 
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">Total Leads Submitted</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">Total Leads Created</TH>
                <TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">Total Leads Sent to Dialler</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">Total Leads Qualified</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">Total Leads in Feasibility</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">Total Assigned Leads</TH>
				
			</TR>
		<logic:present name="assignedList" scope="request">		
		<logic:empty  scope="request" name="assignedList">
			<TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message
				
					key="assignmentDownload.NotFound" /></TD>
				
			</TR>
		</logic:empty>
		</logic:present>
		<logic:present name="assignedList" scope="request">	
		<logic:notEmpty name="assignedList" scope="request">
			
			<logic:iterate id="report" indexId="i" name="assignedList" scope="request"   >
				
				<%
				
				String cssName = "";				
				if(( i.intValue()%2)==1)
				{			
			
				cssName = "alt";
				}	
				%>
				<TR class="lightBg" class="<%=cssName %>">				
					
					<TD class="txt" align="left"  width="5%"> &nbsp;<%=(i.intValue() + 1)%>.&nbsp;</TD>
					
					<TD class="txt" align="center"><bean:write name="report" property="param" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="submittedLead" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="createdLead" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="senttoDialerLeads" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="qualifiedLeads" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="feasibilityLeads" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="assignedLeads" /></TD>
					
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	</table>
</body>
</html>
