<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%><script language="javascript"  src="<%=request.getContextPath()+"/jScripts/ajaxCall.js"%>"></script>
<script language="javascript">

function showField()
{
document.getElementById("hist").style.display= "block";
}	
</script>	

<style>
fieldset.field_set_main {
	border-bottom-width: 2px;
	border-top-width: 2px;
	border-left-width: 2px;
	border-right-width: 2px;
	margin: auto;
	padding: 0px 0px 0px 0px;
	border-color: CornflowerBlue;
}
</style>

<html:form action="/leadRegistration">
	<html:hidden property="methodName" value=""/>
	<html:hidden property="leadId" />
     
  <logic:notEqual name="leadRegistrationFormBean" property="initStatus" value="true">
	   
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
			 <br/>
			 <logic:iterate name="leadDetailsList" type="com.ibm.lms.dto.LeadDetailsDTO" id="report">
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
					
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
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">Preferred Language </span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="language" /></TD>
					
					<TD align="left" valign="top" class="label0">Nationality</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="nationality" /></TD>
						</TR>
						
						<TR class="alt">
						<TD align="left" valign="top" class="label0">Gender</TD>
						<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="gender" /></TD>
						<TD align="left" valign="top" class="label0">Relation Name</TD>
						<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="relationName" /></TD>
						</TR>

						<TR>	
					<TD align="left" valign="top" class="label0">Identity Proof Id</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="identityProofId" /></TD>
					<TD align="left" valign="top" class="label0">Identity Proof Type</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="identityProofType" /></TD>
					
				</TR>
						
						
				</table>
				
				
					<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Change Request Type</font>   </legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
				
				<TR class="alt">	
					<TD align="left" valign="top" class="label0">Request Type </TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="requestTypeDesc" /></TD>
					
				</TR>
				
			
				</table></fieldset>
							
				<logic:equal property="productLobId" name="report"  value="24">
				<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Plan and Transaction Details</font>   </legend>
					<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">

				<TR class="alt">
					<TD align="left" valign="top" class="label0">PlanId</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="planId" /></TD>
					<TD align="left" valign="top" class="label0">Plan</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="plan" /></TD>
					</TR>
					
					<TR>
					<TD align="left" valign="top" class="label0">Plan Type</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="planType" /></TD>
					<TD align="left" valign="top" class="label0">Previous Operator</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="previousOperator" /></TD>
					</TR>	
					
					<TR class="alt">
					<TD align="left" valign="top" class="label0">Rental</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="rental" /></TD>
					<TD align="left" valign="top" class="label0">RentalType</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="rentalType" /></TD>
				</TR>	
					<TR>
					<TD align="left" valign="top" class="label0">OnlineCafNo</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="onlineCafNo" /></TD>
					<TD align="left" valign="top" class="label0">Payment Amount</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="payment" /></TD>
				</TR>
				<TR class="alt">	
					<TD align="left" valign="top" class="label0">TransactionRefNo</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="transactionRefNo" /></TD>
					<TD align="left" valign="top" class="label0">Offer</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="offer" /></TD>
				</TR>	
				
				<TR>
				<TD align="left" valign="top" class="label0">Company</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="company" /></TD>
						
					<!--<TD align="left" valign="top" class="label0">DownloadLimit</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="downloadLimit" /></TD>-->
					<TD align="left" valign="top" class="label0">DeviceMrp</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="deviceMrp" /></TD>
					
				</TR>
				
				<TR class="alt">
					<!--<TD align="left" valign="top" class="label0">PkgDuration</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="pkgDuration" /></TD>-->
					<TD align="left" valign="top" class="label0">Benefit</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="benefit" /></TD>
					<TD align="left" valign="top" class="label0">State</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="state" /></TD>
					
				</TR>
				<TR>
				<TD align="left" valign="top" class="label0">DeviceTaken</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="devicetaken" /></TD>
					
					</TR>
						
		</table>
							</fieldset>	</logic:equal>
				
				
				
				<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Location Details </font>   </legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
				
				<tr class="alt">
		
					<TD align="left" valign="top" class="label0">Previous Circle</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="previousCircle" /></TD>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width160">Circle</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="circleName" /></TD>
					</tr>
			<TR>	
					
					<TD align="left" valign="top" class="label0"><span class="text2 fll width120">City</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="cityName" /></TD>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width160">City Zone Code</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="cityZoneName" /></TD>
				</TR>
				
				<TR class="alt">	
					<TD align="left" valign="top" class="label0"><span class="text2 fll">Address 1</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="address1" /></TD>
					
				</TR>
				<TR>	
					
					<TD align="left" valign="top" class="label0"><span class="text2 fll width120">Address 2</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="address2" /></TD>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width120">PinCode</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="pinCode" /></TD>
				</TR>
				
				</table>
				</fieldset>
				
				<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Contact Details </font>   </legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
				<TR class="alt">	
					<TD align="left" valign="top" class="label0"><span class="text2 fll width160">Land Line No. </span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="landlineNo" /></TD>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width120">Email Id</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="email" /></TD>
				</TR>
				<tr>
				
<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
				<TD align="left" valign="top" class="label0"><span class="text2 fll ">Alternate Mobile Number </span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="alternateContactNo" /></TD>
			<%} %>
				</tr>
				</table>
				</fieldset>
				
				<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Lead Info</font>   </legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
				
				<TR class="alt">	
					<TD align="left" valign="top" class="label0">Request Registered</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="oppertunityTime" /></TD>
					<TD align="left" valign="top" class="label0">Status </TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="leadStatusName" /></TD>
				</TR>
				<TR>
				    <TD align="left" valign="top" class="label0">Lead LifeCycle Status</TD>
					<TD align="left" valign="top" class="labelValue"><strong><bean:write name="report" property="requestCategoryName" /></strong></TD>
					<TD align="left" valign="top" class="label0">IP Address</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="ipAddress" /></TD>
			
						</TR>
					
					<TR class="alt">
					<TD align="left" valign="top" class="label0">Preferred Appointment Start Time</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="appointmentTime" /></TD>
				    <TD align="left" valign="top" class="label0">Preferred Appointment End Time</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="appointmentEndTime" /></TD>
				
					</TR>
			</table></fieldset>
			
				
				<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Source Details </font>   </legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
				<TR>	
					<TD align="left" valign="top" class="label0" >Source</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="sourceName" /></TD>
					<TD align="left" valign="top" class="label0">Sub Source</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="subSourceName" /></TD>
				</TR>
				<TR class="alt">	
				 <TD align="left" valign="top" class="label0">Campaign</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="campaign" /></TD>
						<!-- <TD align="left" valign="top" class="label0">Refer Page </TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="referPage" /></TD>-->
				</TR>
				</table>
				</fieldset>
				
				
			 <fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Other Lead Info</font>   </legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
				
				
				<TR>	
				<!--<TD align="left" valign="top" class="label0">UtmLabels</TD>
				<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="utmLabels" /></TD>
				<TD align="left" valign="top" class="label0">Campaign</TD>
				<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="campaign" /></TD>-->
					<!-- <TD align="left" valign="top" class="label0">Refer Page </TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="referPage" /></TD> -->
				</TR>
				
				<TR class="alt">
					<!--<TD align="left" valign="top" class="label0">QualLeadParam</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="qualLeadParam" /></TD>
					<TD align="left" valign="top" class="label0">GeoIpCity</TD>-->
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="geoIpCity" /></TD>
				</TR>
				<TR>	
					<TD align="left" valign="top" class="label0">LeadSubmitTime</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="leadSubmitTime" /></TD>
					<TD align="left" valign="top" class="label0">Keyword</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="keyWord" /></TD>
				
					<!--<TD align="left" valign="top" class="label0">Tid </TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="tid" /></TD>
				</TR>
				
				<TR class="alt">
					<!--<TD align="left" valign="top" class="label0">Fid</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="fid" /></TD>
					</TR>-->
				<TR class="alt">	
					<TD align="left" valign="top" class="label0">fromPage</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="fromPage" /></TD>
					<TD align="left" valign="top" class="label0">service </TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="service" /></TD>
				</TR>
				
				<TR>
					<TD align="left" valign="top" class="label0">FeasibilityParam</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="feasibilityParam" /></TD>
					<TD align="left" valign="top" class="label0">Refer Page </TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="referPage" /></TD></TR>
				<TR class="alt">	
					<TD align="left" valign="top" class="label0">NdncDisclaimer</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="ndncDisclaimer" /></TD>
					<TD align="left" valign="top" class="label0">ReferUrl</TD>
					<TD align="left" valign="top" class="label0"><textarea onclick="this.style.height='200px';" onblur="this.style.height='60px';" readonly><bean:write name="report" property="referUrl" /></textarea></TD>
				</TR>
				
				
				<TR>
					<!--<TD align="left" valign="top" class="label0">Flag</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="flag" /></TD>-->
					<TD align="left" valign="top" class="label0">Company</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="company" /></TD>
					<TD align="left" valign="top" class="label0">HlrNo</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="hlrNo" /></TD>
					
				
				</TR>
				<TR class="alt">
				    <TD align="left" valign="top" class="label0">StartTask Time</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="taskStartTime" /></TD>
					<TD align="left" valign="top" class="label0">Task End Time</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="taskEndTime" /></TD>
				
					
				</TR>
				<TR>
				<TD align="left" valign="top" class="label0">Latitude</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="extraParam3" /></TD>
					<TD align="left" valign="top" class="label0">Longitude</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="extraParam4" /></TD>
					
					
				</TR>
				<TR class="alt">
				<TD align="left" valign="top" class="label0">Total Amount Due</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="totalDue" /></TD>
					<TD align="left" valign="top" class="label0">Sales Channel Code</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="salesChannelCode" /></TD>
					
				</TR>
				
				<TR>	
					<TD align="left" valign="top" class="label0">UPC</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="upc" /></TD>
					<TD align="left" valign="top" class="label0">UPC Generation Date</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="upcGenDate" /></TD>
					
				</TR>	
				
					<TR class="alt">	
					<TD align="left" valign="top" class="label0">Existing Part</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="existingPart" /></TD>	
					<TD align="left" valign="top" class="label0">Customer Segment</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="customerSegment" /></TD>
						
					</TR>	
					<TR>	
					<TD align="left" valign="top" class="label0">MNP Status</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="mnpStatus" /></TD>	
					<TD align="left" valign="top" class="label0">Document Collected</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="documentCollectedFlag" /></TD>
					
				</TR>	
						
				<TR class="alt">	
					<TD align="left" valign="top" class="label0">Sim Number</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="simNumber" /></TD>
					
					<TD align="left" valign="top" class="label0">Plan Type</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="planType" /></TD>
									
				</TR>
				
				</table>
				
		
				</fieldset>
			
			</br>	
			
			<logic:equal name="report" property="productflag" value="true">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<A HREF='leadRegistration.do?methodName=editLeadDetails&leadId=<%=report.getLeadId()%>' ><u><b>Get Appointment</b></u></A>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
	        </logic:equal>
	        
	        <logic:equal name="report" property="productflag" value="4Gdifferent">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<A HREF='leadRegistration.do?methodName=editLeadDetails&leadId=<%=report.getLeadId()%>' ><u><b>Update Details</b></u></A>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
	        </logic:equal>
		
		
		<a href="#" onclick="showField()"><u><b>Show History</b></u></a>
		
			
				</logic:iterate>  
	
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
			<tr ><td height="15px" bgcolor="#04b2e4" colspan="7"><h4><font color="white">Transaction History</font></h4></td>
			</tr>
		 <tr class="textwhite">
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Transaction Time</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Assigned User</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Expected Closer</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Closer Time</span></th>
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
	
	</br>
	<div id="hist" style="display:none">
	<logic:notEmpty name="LEAD_HIST_DETAILS">
			<bean:define id="leadHstDetailsList" name="LEAD_HIST_DETAILS" type="java.util.ArrayList" scope="request"  />
		</logic:notEmpty>		
		
		<logic:notEmpty name="leadHstDetailsList" >
		 <div class="boxt2" >
	      <div class="content-upload clearfix">
	       <div class="content-table-type2 clearfix" style="overflow-x:auto; overflow-y: none;">
			<table  border="0" cellspacing="0" cellpadding="2" class="form1" width="100%">
			<tr ><td height="15px" bgcolor="#04b2e4" colspan="7"><h4><font color="white">Details History</font></h4></td>
			</tr>
		 <tr class="textwhite">
		    <th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Serial number</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Customer Name</span></th>
			
<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Mobile Number</span></th>
			<%} %>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">EmailId</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Request Category</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Appointment Start time</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Appointment End time</</span></th>
			<!--
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x; " height="15" ><span class="mTop5">Plan</span></th>
			
		--></tr>
 		<logic:iterate name="leadHstDetailsList" type="com.ibm.lms.dto.LeadDetailsDTO" id="history" indexId="ii">
				<%
				String cssName = "";				
				if( (ii.intValue()%2)==1)
				{			
				cssName = "alt";
				}	
				%>
				<tr class="<%=cssName %>" >
				
				    <TD class="labelText" align="left" nowrap="nowrap" ><%=(ii.intValue() + 1)%>.</TD></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="history" property="customerName"  /></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="history" property="contact" /></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="history" property="email" /></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="history" property="requestCategory" /></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="history" property="appointmentTime" /></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="history" property="appointmentEndTime" /></TD>
					
					<!--<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="history" property="plan" /></TD>
					
					
				--></TR>
			</logic:iterate>  
		</table>	
	   </div>
	  </div>
     </div>
	</logic:notEmpty>
	</div>
 </logic:notEqual>
</html:form>