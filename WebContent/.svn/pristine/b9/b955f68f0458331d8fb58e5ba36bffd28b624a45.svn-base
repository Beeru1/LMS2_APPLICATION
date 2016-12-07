<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%>
<%@page import="com.ibm.lms.dto.UserMstr"%><style>
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
			 <% UserMstr userBean= (UserMstr) session.getAttribute("USER_INFO");
			 					String actorId =userBean.getKmActorId();
			 					Boolean channelPartnerFlag=(Boolean)request.getAttribute("channel_flag");

								  if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString()) || ("10".equalsIgnoreCase(actorId)&& channelPartnerFlag)){ %>
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">Mobile Number </span></TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="contactNo" /></TD><%} %>
				</TR>
					
				<TR class="alt">	
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">Preferred Language </span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="language" /></TD>
						</TR>
				</table>
				
				
					<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Change Request Type</font>   </legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
				
				<TR class="alt">	
					<TD align="left" valign="top" class="label0">Request Type </TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="requestTypeDesc" /></TD>
					
				</TR>
				
			
				</table></fieldset>
							
				<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Location Details </font>   </legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
				<TR>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width160">Circle  </span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="circleName" /></TD>	
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">City </span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="cityName" /></TD>
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
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">PIN</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="pinCode" /></TD>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width120">RSU</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="rsuCode" /></TD>
				</TR>
				
				<TR>
				<TD align="left" valign="top" class="label0"><span class="text2 fll ">Address 1</span></TD>
     			
     				
     			<TD align="left" valign="top" class="labelValue"><textarea onclick="this.style.height='200px';" onblur="this.style.height='60px';" readonly><bean:write name="report" property="address1" />
               </textarea></TD>
               <TD align="left" valign="top" class="label0"><span class="text2 fll ">Address 2</span></TD>
     			
     				
     			<TD align="left" valign="top" class="labelValue"><textarea onclick="this.style.height='200px';" onblur="this.style.height='60px';" readonly><bean:write name="report" property="address2" />
               </textarea></TD>
              
               </TR>
               
               <TR>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width160">Latitude</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="extraParam3" /></TD>	
					<TD align="left" valign="top" class="label0"><span class="text2 fll width160">Longitude</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="extraParam4" /></TD>
				</TR>
     				
				</table></fieldset>
				
				
				
				
				
				<!-- Postpaid Lob -->
				<logic:equal property="productLobId" name="report"  value="1">
				
					<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Plan and Transaction Details</font>   </legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
							
				<TR class="alt">
					<TD align="left" valign="top" class="label0">PlanId</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="planId" /></TD>
					<TD align="left" valign="top" class="label0">Plan</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="plan" /></TD>
					</TR>
					<TR class="alt">
					<TD align="left" valign="top" class="label0">Rental</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="rental" /></TD>
					<TD align="left" valign="top" class="label0">FreebieCount</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="freebieCount" /></TD>
				</TR>	
					<TR class="alt">
					
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">Booster Taken</span></TD>
     			
     				
     			<TD align="left" valign="top" class="labelValue"><textarea onclick="this.style.height='200px';" onblur="this.style.height='60px';" readonly><bean:write name="report" property="boosterTaken" />
               </textarea></TD>
							
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">Freebie Taken</span></TD>
     			
     				
     			<TD align="left" valign="top" class="labelValue"><textarea onclick="this.style.height='200px';" onblur="this.style.height='60px';" readonly><bean:write name="report" property="freebieTaken" />
               </textarea></TD>
					</TR>
					
					
					
				<!--  	<TD align="left" valign="top" class="label0">FreebieTaken</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="freebieTaken" /></TD>
				</TR>	-->
				<TR class="alt">
								
					<TD align="left" valign="top" class="label0">BoosterCount</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="boosterCount" /></TD>
					
					
					
					<TD align="left" valign="top" class="label0">AllocatedNo</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="allocatedNo" /></TD>
				</TR>
					<TR class="alt">
					<TD align="left" valign="top" class="label0">Prepaid Number</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="prepaidNumber" /></TD>
					<TD align="left" valign="top" class="label0">Online CafNo</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="onlineCafNo" /></TD>
				</TR>
				<TR>	
					<TD align="left" valign="top" class="label0">Transaction Reference No</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="transactionRefNo" /></TD>
					<TD align="left" valign="top" class="label0">Total Payment Amount</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="payment" /></TD>
				</TR>	
				<TR>	
					<TD align="left" valign="top" class="label0">Company</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="company" /></TD>
					<TD align="left" valign="top" class="label0">Request Category</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="requestCategory" /></TD>
				</TR>	
				</table>
							</fieldset></logic:equal>
				
				<!-- End of Postpaid Lob -->
				
				<!-- Telemedia Lob -->
				<logic:equal property="productLobId" name="report"  value="2">
				 <fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Plan and Transaction Details</font>   </legend>
					<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
				<TR class="alt">
					<TD align="left" valign="top" class="label0">PlanId</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="planId" /></TD>
					<TD align="left" valign="top" class="label0">Plan</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="plan" /></TD>
					</TR>
					<TR class="alt">
					<TD align="left" valign="top" class="label0">Rental</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="rental" /></TD>
					<TD align="left" valign="top" class="label0">RentalType</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="rentalType" /></TD>
				</TR>	
					<TR class="alt">
					<TD align="left" valign="top" class="label0">Online CafNo</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="onlineCafNo" /></TD>
					<TD align="left" valign="top" class="label0">Total Payment Amount</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="payment" /></TD>
				</TR>
				<TR>	
					<TD align="left" valign="top" class="label0">Transaction Referenece No</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="transactionRefNo" /></TD>
					<TD align="left" valign="top" class="label0">Offer</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="offer" /></TD>
				</TR>	
				
				<TR>	
					<TD align="left" valign="top" class="label0">DownloadLimit</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="downloadLimit" /></TD>
					<TD align="left" valign="top" class="label0">DeviceMrp</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="deviceMrp" /></TD>
				</TR>
				
				<TR>	
					<TD align="left" valign="top" class="label0">VoiceBenefit</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="voiceBenefit" /></TD>
					<TD align="left" valign="top" class="label0">DataQuota</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="dataQuota" /></TD>
				</TR>
				<TR class="alt">
					<TD align="left" valign="top" class="label0">Package Duration</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="pkgDuration" /></TD>
					<TD align="left" valign="top" class="label0">Benefit</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="benefit" /></TD>
				</TR>
				<TR>	
					<TD align="left" valign="top" class="label0">Company</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="company" /></TD>
					<TD align="left" valign="top" class="label0">Request Category</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="requestCategoryName" /></TD>
				</TR>	
				</table>
							</fieldset></logic:equal>
				
				<!-- End of Telemedia Lob -->
				
				
				<!-- 3g Lob -->
				<logic:equal property="productLobId" name="report"  value="23">
				 <fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Plan and Transaction Details</font>   </legend>
					<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
								
				<TR class="alt">
					<TD align="left" valign="top" class="label0">PlanId</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="planId" /></TD>
					<TD align="left" valign="top" class="label0">Plan</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="plan" /></TD>
					</TR>
					<TR class="alt">
					<TD align="left" valign="top" class="label0">Rental</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="rental" /></TD>
					<TD align="left" valign="top" class="label0">RentalType</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="rentalType" /></TD>
				</TR>	
					<TR class="alt">
					<TD align="left" valign="top" class="label0">Online CafNo</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="onlineCafNo" /></TD>
					<TD align="left" valign="top" class="label0">Total Payment Amount</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="payment" /></TD>
				</TR>
				<TR>	
					<TD align="left" valign="top" class="label0">Transaction Reference No</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="transactionRefNo" /></TD>
					<TD align="left" valign="top" class="label0">Offer</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="offer" /></TD>
				</TR>	
				
				<TR>	
					<TD align="left" valign="top" class="label0">DownloadLimit</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="downloadLimit" /></TD>
					<TD align="left" valign="top" class="label0">DeviceMrp</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="deviceMrp" /></TD>
				</TR>
				
				<TR>	
					<TD align="left" valign="top" class="label0">DeviceTaken</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="deviceTaken" /></TD>
					<TD align="left" valign="top" class="label0">DataQuota</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="dataQuota" /></TD>
				</TR>
				<TR class="alt">
					<TD align="left" valign="top" class="label0">Package Duration</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="pkgDuration" /></TD>
					<TD align="left" valign="top" class="label0">Benefit</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="benefit" /></TD>
				</TR>
				<TR>	
					<TD align="left" valign="top" class="label0">Company</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="company" /></TD>
					<TD align="left" valign="top" class="label0">Request Category</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="requestCategory" /></TD>
				</TR>	
				</table>
							</fieldset></logic:equal>
				
				<!-- End of 3g Lob -->
				
				<!-- 4g Lob -->
				<logic:equal property="productLobId" name="report"  value="24">
				<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Plan and Transaction Details</font>   </legend>
					<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
							
				<TR class="alt">
					<TD align="left" valign="top" class="label0">PlanId</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="planId" /></TD>
					<TD align="left" valign="top" class="label0">Plan</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="plan" /></TD>
					</TR>
					<TR class="alt">
					<TD align="left" valign="top" class="label0">Rental</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="rental" /></TD>
					<TD align="left" valign="top" class="label0">RentalType</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="rentalType" /></TD>
				</TR>	
					<TR class="alt">
					<TD align="left" valign="top" class="label0">OnlineCafNo</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="onlineCafNo" /></TD>
					<TD align="left" valign="top" class="label0">Total Payment Amount</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="payment" /></TD>
				</TR>
				<TR>	
					<TD align="left" valign="top" class="label0">TransactionRefNo</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="transactionRefNo" /></TD>
					<TD align="left" valign="top" class="label0">Offer</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="offer" /></TD>
				</TR>	
				
				<TR>	
					<TD align="left" valign="top" class="label0">DownloadLimit</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="downloadLimit" /></TD>
					<TD align="left" valign="top" class="label0">DeviceMrp</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="deviceMrp" /></TD>
				</TR>
				
				<TR>	
					<TD align="left" valign="top" class="label0">DeviceTaken</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="devicetaken" /></TD>
					<TD align="left" valign="top" class="label0">DataQuota</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="dataQuota" /></TD>
				</TR>
				<TR class="alt">
					<TD align="left" valign="top" class="label0">PkgDuration</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="pkgDuration" /></TD>
					<TD align="left" valign="top" class="label0">Benefit</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="benefit" /></TD>
				</TR>
				<TR class="alt">
					<TD align="left" valign="top" class="label0">State</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="state" /></TD>
					<TD align="left" valign="top" class="label0">Company</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="company" /></TD>
					</TR>
					<TR>	
					
					<TD align="left" valign="top" class="label0">Request Category</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="requestCategory" /></TD>
					
					<TD align="left" valign="top" class="label0">Total Amount Due</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="totalDue" /></TD>
				
				</TR>	
		</table>
							</fieldset>	</logic:equal>
				
				<!-- End of 4g Lob -->
				
				
				<!-- Dth Lob -->
				
				<logic:equal property="productLobId" name="report"  value="5">
				<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Plan and Transaction details</font>   </legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">		
				<TR class="alt">
					<TD align="left" valign="top" class="label0">PlanId</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="planId" /></TD>
					<TD align="left" valign="top" class="label0">Plan</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="plan" /></TD>
					</TR>
					<TR class="alt">
					<TD align="left" valign="top" class="label0">Rental</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="rental" /></TD>
					<TD align="left" valign="top" class="label0">OnlineCafNo</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="onlineCafNo" /></TD>
				</TR>	
					<TR class="alt">
					<TD align="left" valign="top" class="label0">TransactionRefNo</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="transactionRefNo" /></TD>
					<TD align="left" valign="top" class="label0">Total Payment Amount</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="payment" /></TD>
				</TR>
				<TR>	
					<TD align="left" valign="top" class="label0">DeviceMrp</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="transactionRefNo" /></TD>
					<TD align="left" valign="top" class="label0">UserType</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="userType" /></TD>
				</TR>	
				
				<TR>	
					<TD align="left" valign="top" class="label0">DeviceTaken</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="downloadLimit" /></TD>
					<TD align="left" valign="top" class="label0">DataQuota</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="dataQuota" /></TD>
				</TR>
				
				<TR class="alt">
					<TD align="left" valign="top" class="label0">PkgDuration</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="pkgDuration" /></TD>
				<TD align="left" valign="top" class="label0">Benefit</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="benefit" /></TD>
				
				<TR>	
					<TD align="left" valign="top" class="label0">Company</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="company" /></TD>
					<TD align="left" valign="top" class="label0">Request Category</TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="requestCategory" /></TD>
				</TR>
				</TR></table></fieldset>
				</logic:equal>
				
				<!-- End of Dth Lob -->
				
				<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Contact Details </font>   </legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
				<TR>	
				<% 
				 if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())|| ("10".equalsIgnoreCase(actorId)&& channelPartnerFlag)){
								 %>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width160">Land Line No. </span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="landlineNo" /></TD>
					<%} %>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width120">Email Id</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="email" /></TD>
				</TR>
				<tr>
				<% 
				
								  if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())|| ("10".equalsIgnoreCase(actorId)&& channelPartnerFlag)){
								 %><TD align="left" valign="top" class="label0"><span class="text2 fll ">Alternate Mobile Number </span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="alternateContactNo" /></TD><%} %>
				
				</tr>
				</table>
				</fieldset>
				
				<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Lead Info</font>   </legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
				
				<TR>	
					<TD align="left" valign="top" class="label0">Request Registered</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="oppertunityTime" /></TD>
					<TD align="left" valign="top" class="label0">Status </TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="leadStatusName" /></TD>
				</TR>
				<TR class="alt">
					<TD align="left" valign="top" class="label0">appointment Time</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="appointmentTime" /></TD>
					<TD align="left" valign="top" class="label0">IP Address</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="ipAddress" /></TD>
			</TR>
			</table></fieldset>
			
				
				<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Source Details </font>   </legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
				<TR class="alt">	
					<TD align="left" valign="top" class="label0" >Source</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="sourceName" /></TD>
					<TD align="left" valign="top" class="label0">Sub Source</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="subSourceName" /></TD>
				</TR>
				<TR>	
				 <TD align="left" valign="top" class="label0">Campaign</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="campaign" /></TD>
						<!-- <TD align="left" valign="top" class="label0">Refer Page </TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="referPage" /></TD>-->
				</TR>
				</table>
				</fieldset>
				
				<!-- Added By Bhaskar  -->	
			 <fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Other Lead Info</font>   </legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
				
				
				<TR>
				<TD align="left" valign="top" class="label0"><span class="text2 fll ">UtmLabels</span></TD>
     			<TD align="left" valign="top" class="labelValue"><textarea onclick="this.style.height='200px';" onblur="this.style.height='60px';" readonly><bean:write name="report" property="utmLabels" />
               </textarea></TD>
               	<TD align="left" valign="top" class="label0">Campaign</TD>
				<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="campaign" /></TD>
				</TR>
				
				<TR class="alt">
					<TD align="left" valign="top" class="label0">QualLeadParam</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="qualLeadParam" /></TD>
					<TD align="left" valign="top" class="label0">GeoIpCity</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="geoIpCity" /></TD>
				</TR>
				<TR>	
					<TD align="left" valign="top" class="label0">LeadSubmitTime</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="leadSubmitTime" /></TD>
					<TD align="left" valign="top" class="label0">Tid </TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="tid" /></TD>
				</TR>
				
				<TR class="alt">
					<TD align="left" valign="top" class="label0">Fid</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="fid" /></TD>
					<TD align="left" valign="top" class="label0">Keyword</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="keyWord" /></TD>
				</TR>
				<TR>	
					<TD align="left" valign="top" class="label0">fromPage</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="fromPage" /></TD>
					<TD align="left" valign="top" class="label0">service </TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="service" /></TD>
				</TR>
				
				<TR class="alt">
					<TD align="left" valign="top" class="label0">FeasibilityParam</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="feasibilityParam" /></TD>
					<TD align="left" valign="top" class="label0">Refer Page </TD>
					<TD align="left" valign="top" class="label0"><bean:write name="report" property="referPage" /></TD></TR>
				<TR>	
					<TD align="left" valign="top" class="label0">NdncDisclaimer</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="ndncDisclaimer" /></TD>
					<TD align="left" valign="top" class="label0">ReferUrl</TD>
					<TD align="left" valign="top" class="label0"><textarea onclick="this.style.height='200px';" onblur="this.style.height='60px';" readonly><bean:write name="report" property="referUrl" /></textarea></TD>
				</TR>
				
				
				<TR class="alt">
					<TD align="left" valign="top" class="label0">Flag</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="flag" /></TD>
					<TD align="left" valign="top" class="label0">Company</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="company" /></TD>
				</TR>
				<TR>	
					<TD align="left" valign="top" class="label0">HlrNo</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="hlrNo" /></TD>
					<TD align="left" valign="top" class="label0">Sales Channel Code</TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="report" property="salesChannelCode" /></TD>
					
					</TR>
					
				</table>
				</fieldset>
				
				
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
			<tr ><td height="15px" bgcolor="#04b2e4" colspan="8"><h4><font color="white">Transaction Details</font></h4></td>
			</tr>
		 <tr class="textwhite">
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Transaction Time</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Assigned User</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Channel Partner Name</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Expected Closer</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Closer Time</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">Lead Status</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x; " height="15" ><span class="mTop5">Sub Status</span></th>
			<th class="labelText" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x; " height="15" ><span class="mTop5">Sub Sub Status</span></th>
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
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="report" property="userFname" /></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="report" property="expectedCloserDate" /></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="report" property="closerTime" /></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="report" property="leadStatusName" /></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="report" property="leadSubStatusName" /></TD>
					<TD class="labelText" align="left" nowrap="nowrap" ><bean:write name="report" property="allStatusName" /></TD>
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