<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%><html:form action="/feasibleLeads">
	<html:hidden property="methodName" value=""/>
	<%-- <html:hidden property="leadId" />  --%>
     
  <logic:notEqual name="LeadForm" property="initStatus" value="true">
	   
	   <logic:notEmpty name="LEAD_DETAILS">
			<bean:define id="leadDetailsList" name="LEAD_DETAILS" type="java.util.ArrayList" scope="request"  />
		</logic:notEmpty>
		<logic:empty name="leadDetailsList" >
			<FONT color="red" style="margin-left: 15px;"><bean:message key="lead.not.found" /></FONT>
		</logic:empty>
		
		<logic:notEmpty name="leadDetailsList" >
		 <div class="boxt2">
	      <div class="content-upload clearfix">
	       <div class="content-table-type2 clearfix">
	       <h1>Lead Details</h1>
	       
	       <center> <strong><FONT color="red"><html:errors/></FONT>
			 <FONT color="green"><html:messages id="msg" message="true"><bean:write name="msg"/></html:messages></FONT></strong></center>
			 
			 
			<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
			<logic:iterate name="leadDetailsList" type="com.ibm.lms.dto.LeadDetailsDTO" id="report">
				<TR>	
					<TD align="left" valign="top" class="label0"><span class="text2 fll width160">Lead Id </span></TD>
					<TD align="left" valign="top" class="labelValue"><b><bean:write name="report" property="leadId" /></b></TD>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width120">Product Name</span></TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="productName" /></TD>
				</TR>
				<TR class="alt">	
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">Customer Name </span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="customerName" /></TD>
				
<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
								 <TD align="left" valign="top" class="label0"><span class="text2 fll ">Mobile Number </span></TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="contactNo" /></TD>
					<%} %>
				</TR>
						
				<TR>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width160">Circle  </span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="circleName" /></TD>	
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">City </span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="cityName" /></TD>
				</TR>
				<TR class="alt">	
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">PIN</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="pinCode" /></TD>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width120">RSU</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="rsuCode" /></TD>
				</TR>
				
				<TR>					
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">Address 1</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="address1" /></TD>
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">Address 2</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="address2" /></TD>
					
				</TR>
					
				<TR class="alt">	
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">Preferred Language </span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="language" /></TD>
					
<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">Alternate Mobile Number </span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="alternateContactNo" /></TD>
					<%} %>
				</TR>
				
				<TR>	
				
<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width160">Land Line No. </span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="landlineNo" /></TD>
					<%} %>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width120">Email Id</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="email" /></TD>
				</TR>
				<TR class="alt">	
					<TD align="left" valign="top" class="label0">Request Type </TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="requestTypeDesc" /></TD>
					<TD align="left" valign="top" class="label0">  </TD>
					<TD align="left" valign="top" class="labelValue"> </TD>
				</TR>
				<TR >	
				<!-- 
					<TD align="left" valign="top" class="label0"><span class="text2 fll width160">Sub Zone</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="subZoneName" /></TD>   -->
					<TD align="left" valign="top" class="label0"><span class="text2 fll width120">Zone</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="zoneName" /></TD>
						<!--  Adding by pratap for city zone code 13 Dec 2013 -->
					<TD align="left" valign="top" class="label0">City Zone </TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="cityZoneName" /></TD>
						<!-- End of  Adding by pratap for city zone code  -->
				</TR> 
			
					
				<TR class="alt">	
					<TD align="left" valign="top" class="label0" >Source</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="sourceName" /></TD>
					<TD align="left" valign="top" class="label0">Sub Source</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="subSourceName" /></TD>
				</TR>
				<TR>	
					<TD align="left" valign="top" class="label0">Request Registered</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="oppertunityTime" /></TD>
					<TD align="left" valign="top" class="label0">Status </TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="leadStatusName" /></TD>
				</TR>
				<TR class="alt">
					<TD align="left" valign="top" class="label0">appointment Time</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="appointmentTime" /></TD>
					<TD align="left" valign="top" class="label0">Remarks</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="remarks" /></TD>
				</TR>
			</logic:iterate>  
		</table>
	   </div>
	  </div>
     </div>
	</logic:notEmpty>
	
	<br>
	
		<logic:notEmpty name="LEAD_TRNS_DETAILS">
			<bean:define id="leadTrnsDetailsList" name="LEAD_TRNS_DETAILS" type="java.util.ArrayList" scope="request"  />
		</logic:notEmpty>		
		
		<logic:notEmpty name="leadTrnsDetailsList" >
		 <div class="boxt2" >
	      <div class="content-upload clearfix">
	       <div class="content-table-type2 clearfix" style="overflow-x:auto; overflow-y: none;">
			<table  border="0" cellspacing="0" cellpadding="2" class="form1" width="100%">
			<tr ><td height="15px" bgcolor="#04b2e4" colspan="7"><h4><font color="white">Transaction Details</font></h4></td>
			</tr>
		 <tr class="textwhite">
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Transaction Time</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Assigned User</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Expected Closure</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Closure Time</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Lead Status</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x; " height="15" ><span class="mTop5">Sub Status</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Remarks</span></th>
		</tr>
 		<logic:iterate name="leadTrnsDetailsList" type="com.ibm.lms.dto.LeadDetailsDTO" id="report" indexId="ii">
				<%
				String cssName = "";				
				if( (ii.intValue()%2)==1)
				{			
				cssName = "alt";
				}	
				%>
				<tr class="<%=cssName %>" >
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="report"	property="transactionTime"  /></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="report" property="assignedPrimaryUser" /></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="report" property="expectedCloserDate" /></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="report" property="closerTime" /></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="report" property="leadStatusName" /></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="report" property="leadSubStatusName" /></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="report" property="remarks" /></TD>
				</TR>
			</logic:iterate>  
		</table>
	   </div>
	  </div>
     </div>
	</logic:notEmpty>
	
 </logic:notEqual>
</html:form>