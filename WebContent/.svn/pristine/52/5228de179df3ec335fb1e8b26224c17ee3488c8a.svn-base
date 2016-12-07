<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ibm.lms.wf.dto.LeadDetailDTO"%>

<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%>
<%@page import="com.ibm.lms.dto.UserMstr"%>
<%@page import="com.ibm.lms.common.PropertyReader"%><LINK href="./jsp/theme/css.css" rel="stylesheet" type="text/css">
<script language="javascript"  src="<%=request.getContextPath()+"/jScripts/ajaxCall.js"%>"></script>
<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript">
function leadClosure(obj)
{

	var oForm = document.LeadForm;
	
	oForm.methodName.value = "closeTheLead";
	if(oForm.actionType[oForm.actionType.selectedIndex].value == ""){alert("Action Type is required!");oForm.actionType.focus();return false;}
	
	if(oForm.actionType[oForm.actionType.selectedIndex].value == 500)
	{
		if(oForm.cafNumber.value == ""){alert("Caf Number is required!");oForm.cafNumber.focus();return false;	}
	}
	
	if(oForm.closureComments.value == ""){alert("Comments are required!"); oForm.closureComments.focus(); return false;}
	if(oForm.closureComments.value.length >1000){alert("Comments can not be grater than 1000!"); oForm.closureComments.focus(); return false;}
	
	oForm.submit();
	obj.disabled=true;
	
	return false;
}
function leadForward()
{
	var oForm = document.LeadForm;
	oForm.methodName.value = "forwardTheLead";
	if(oForm.forwardTo[oForm.forwardTo.selectedIndex].value == ""){alert("User Name is required!");oForm.forwardTo.focus();return false;}
	if(oForm.forwardComments.value == ""){alert("Comments are required!"); oForm.forwardComments.focus(); return false;}
	if(oForm.forwardComments.value.length >1000){alert("Comments can not be grater than 1000!"); oForm.forwardComments.focus(); return false;}
	oForm.submit();
	return false;
}

var getSubStatusOnStatusChange = function()
	{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
	  		var status = document.LeadForm.actionType;
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			var withholdFlag=0;
			
			if(status.options[status.options.selectedIndex].innerHTML=="ON HOLD")
			 {
			  withholdFlag=1;
			 }
			else if(status.options[status.options.selectedIndex].innerHTML=="WIP")
			 {
		  		withholdFlag=2;
			 }
			
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.LeadForm.subStatusID;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select", "");
		//	alert(withholdFlag);
			for (var i = 0; i < optionValues.length; i++){
//				alert(optionValues[i].getAttribute("value"));
			     if(withholdFlag==1 && optionValues[i].getAttribute("value")!=469  && optionValues[i].getAttribute("value")!=464 && optionValues[i].getAttribute("value")!=487 &&optionValues[i].getAttribute("value")!=488)
			       continue;
			     if(withholdFlag==2 && (optionValues[i].getAttribute("value")==469  || optionValues[i].getAttribute("value")==464 || optionValues[i].getAttribute("value")==487 || optionValues[i].getAttribute("value")==488))
			       continue;
				  selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
				}
			
		}
	}
	
function getSubStatus()
	{
	var productCheck = '<%=request.getAttribute("cafStatus")%>'
	
		removeAllOptions(document.LeadForm.subStatusID);
		var status = document.LeadForm.actionType;
		var product_name=document.forms[0].product_name.value;
		var leadID=document.forms[0].leadID.value;
		var leadStatus=status.options[status.options.selectedIndex].value;
		if (status.options[status.options.selectedIndex].value == "") return false;
		var data = "methodName=getFeasibilitySubStatusList&status="+leadStatus+ "&leadID=" +leadID;
		doAjax("feasibleLeads.do", "GET", false, getSubStatusOnStatusChange, data);
		//var status = document.LeadForm.actionType;
		var statusID = status.options[status.options.selectedIndex].value;
		if (statusID == "") return false;
		if(statusID == 500)
		{
			document.getElementById("cafDisplay").style.display= "";
			document.getElementById("subStatusDisplay").style.display="";
		}
		else if(statusID == 600)
		{
			document.getElementById("subStatusDisplay").style.display="";
			document.getElementById("cafDisplay").style.display= "none";
			
		}
		else if((statusID==400 || statusID==401 ) && productCheck=="true"){
			document.getElementById("subStatusDisplay").style.display="";
			document.getElementById("cafDisplay").style.display= "none";
		}
		else
		{
		document.getElementById("subStatusDisplay").style.display="none";
		document.getElementById("cafDisplay").style.display= "none";
		}
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

legend.header {
	text-align: right;
}
</style>

<html:form action="qualifiedLeads.do">
	<html:hidden name="LeadForm" property="methodName" />
	<html:hidden name="LeadForm" property="leadID" />
	<div class="box2">                            
		<div class="content-upload" style="margin-bottom: 0px;">
          <h1><bean:message key="view.leadDetail" /></h1>
        </div>
         <br> 
				<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Lead Detail</font></legend>
							<logic:present name="detail" scope="request">
							<logic:notEmpty name="detail" scope="request">
							<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
							<tr>
								<td width="25%" class="text height12" align="right"><font color="#4b0013"><b>Product Name &nbsp;&nbsp;</td>
								<td width="25%" align="left" class="height12" colspan="3"><bean:write name="detail"	property="product_name" />
								<html:hidden name="detail" property="product_name"/></td>
								<td width="25%" class="text height12" align="right"><font color="#4b0013"><b>Customer Name &nbsp;&nbsp;</td>
								<td width="25%" align="left" class="height12"><bean:write name="detail"	property="customer_name" /></td>
							</tr>

							<tr>
								<td width="25%" class="text height12" align="right"><font color="#4b0013"><b>Lead ID &nbsp;&nbsp;</td>
								<td width="25%" align="left" class="height12"><bean:write name="LeadForm"	property="leadID" /></td>
								
								
							</tr>
							</table>
							</fieldset>
							<fieldset class="field_set_main"><legend class="header"><font
								size=2 color=#ff0000>Location</font></legend>
								<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Circle Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="circle_name" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>State Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="state_name" /></td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Zone Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="zone_name" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>City Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="city_name" /></td>

							</tr>

							<tr>
								
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>City Zone Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="cityZoneName" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Email ID &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="email" /></td>
								
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Pin code &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="pincode" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>RSU ID &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="rsu_id" /></td>
							</tr>
							
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Latitude &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="latitude" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Longitude &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="longitude" /></td>
							</tr>
							
						</table>
						</fieldset>
						<fieldset class="field_set_main"><legend class="header"><font
								size=2 color=#ff0000>Contact Details</font></legend>
								<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
							 <% UserMstr userBean= (UserMstr) session.getAttribute("USER_INFO");
			 					String actorId =userBean.getKmActorId();
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString()) || PropertyReader.getAppValue("channel.partner").equalsIgnoreCase(actorId)){
								%>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Landline Number &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="landline_number" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Alternate Contact Number &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="alternate_contact_number" /></td>
							</tr>
							<%} %>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Address &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="address1" /></td>
								
								<%  if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())|| PropertyReader.getAppValue("channel.partner").equalsIgnoreCase(actorId)){
								 %>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Mobile Number &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="prospect_mobile_number" /></td>
								<td width="25%" align="left">&nbsp;</td>
								<%} %>
							</tr>
						</table>
						</fieldset>
							<fieldset class="field_set_main"><legend class="header"><font
								size=2 color=#ff0000>Other Lead Details</font></legend>
							<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Source &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="source" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Subsource &nbsp;&nbsp;</td>
								<td width="25%"  class="text" align="left"><bean:write name="detail"	property="subSource" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Request Type &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="request_type" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b> Campaign&nbsp;&nbsp;</td>
								<td width="25%"  class="text" align="left"><bean:write name="detail"	property="compaign" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>GeoIpCity &nbsp;&nbsp;</td>
								<td width="25%"  class="text" align="left"><bean:write name="detail" property="geoIpCity" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Fid &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="fid" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Cid &nbsp;&nbsp;</td>
								<td width="25%"  class="text" align="left"><bean:write name="detail" property="cid" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Lead Submit Time &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="leadSubmitTime" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Keyword &nbsp;&nbsp;</td>
								<td width="25%"  class="text" align="left"><bean:write name="detail" property="keyword" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Service &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="service" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>From Page &nbsp;&nbsp;</td>
								<td width="25%"  class="text" align="left"><bean:write name="detail" property="fromPage" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>	
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Flag&nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="flag" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Address 2 &nbsp;&nbsp;</td>
								<td width="25%"  class="text" align="left"><bean:write name="detail" property="address2" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>	
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>HLR No&nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="hlrNo" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>State &nbsp;&nbsp;</td>
								<td width="25%"  class="text" align="left"><bean:write name="detail" property="state" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>	
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>appointment time &nbsp;&nbsp;</td>
								<td width="25%"  class="text" align="left"><bean:write name="detail" property="appointmentTime" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>QualLead Param &nbsp;&nbsp;</td>
								<td width="25%"  class="text" align="left"><bean:write name="detail" property="qualLeadParam" /></td>
								
							</tr>

							<TR>
								<logic:equal value="1" name="detail" property="lobId">
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Security Deposit &nbsp;&nbsp;</td>
									<td width="25%"  class="text" align="left"><bean:write name="detail"	property="paymentAmt" /></td>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Allocated Number &nbsp;&nbsp;</td>
									<td width="25%" class="text" align="left"><bean:write name="detail"	property="allocatedNumber" /></td>
								</logic:equal>
								<logic:equal value="2" name="detail" property="lobId">
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Activation Fee &nbsp;&nbsp;</td>
									<td width="25%"  class="text" align="left"><bean:write name="detail"	property="paymentAmt" /></td>
								
								</logic:equal>
								<logic:equal value="23" name="detail" property="lobId">
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Dongle MRP: &nbsp;&nbsp;</td>
									<td width="25%"  class="text" align="left"><bean:write name="detail"	property="paymentAmt" /></td>
								</logic:equal>
								<logic:equal value="24" name="detail" property="lobId">
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Dongle MRP: &nbsp;&nbsp;</td>
									<td width="25%"  class="text" align="left"><bean:write name="detail"	property="paymentAmt" /></td>
								</logic:equal>
								<logic:notEqual value="1" name="detail" property="lobId">
									<logic:notEqual value="2" name="detail" property="lobId">
										<logic:notEqual value="23" name="detail" property="lobId">
											<logic:notEqual value="24" name="detail" property="lobId">
												
													<td width="25%" class="text" align="right"><font color="#4b0013"><b>Payment Details: &nbsp;&nbsp;</td>
													<td width="25%"  class="text" align="left"><bean:write name="detail"	property="paymentAmt" /></td>
												
											</logic:notEqual>
										</logic:notEqual>
									</logic:notEqual>
								</logic:notEqual>
							</TR>
							<TR>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Product Type&nbsp;&nbsp;</td>
								<td width="25%"  class="text" align="left"><bean:write name="detail"	property="productType" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Lead Priority &nbsp;&nbsp;</td>
								<td width="25%" class="text" align="left">
									<logic:equal value="HIGH" property="leadPriority" name="detail" >
										<font color="red">
											<bean:write	property="leadPriority" name="detail" />
										</font>
									</logic:equal>
										<logic:equal value="MEDIUM" property="leadPriority" name="detail" >
										<font color="blue">
											<bean:write	property="leadPriority" name="detail" />
										</font>
									</logic:equal>
										<logic:equal value="LOW" property="leadPriority" name="detail" >
										<font color="green">
											<bean:write	property="leadPriority" name="detail" />
										</font>
									</logic:equal>

							</TR>
							<TR>
															<td width="25%" class="text" align="right"><font color="#4b0013"><b>Plan ID &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="myOpId" /></td>
							</TR>	
							<TR>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Remarks Verification &nbsp;&nbsp;</td>
								<td width="25%" colspan="3" class="text" align="left"><bean:write name="detail"	property="remarks" /></td>
							</TR>
							<TR>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Remarks Latest &nbsp;&nbsp;</td>
								<td width="25%" colspan="3" class="text" align="left"><bean:write name="detail"	property="remarksLatest" /></td>
							</TR>
							
							</table>
						</fieldset>
							</table>
							</logic:notEmpty>
							</logic:present>
						</fieldset><br/>
						
						
						<!-- Mobility  -->
						<logic:equal property="lobId" name="detail"  value="1">
						
						<fieldset class="field_set_main"><legend class="header"><font
								size=2 color=#ff0000>LOB Info</font></legend>
								<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Plan Id&nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="planID" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Plan&nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="plan" /></td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Rental &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="rental" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Freebie Count &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="freebieCount" /></td>
								</tr>
							<tr>
								
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Booster Count &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="boosterCount" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Freebie Taken &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="freebieTaken" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>
							<tr>
								
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Booster Taken &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="boosterTaken" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Allocated No. &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="allocatedNumber" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>
							<tr>
								
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Prepaid No. &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="prepaidNo" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Online Caf No. &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="onlineCafNumber" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>
							<tr>
								
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Payment Amount &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="paymentAmt" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Trans Ref No. &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="transRefNo" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>
							
							<tr>		
							    <td width="25%" class="text" align="right"><font color="#4b0013"><b>Benefit &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="benefit" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Pkg Duration &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="pkgDuration" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>
							
							<tr>
							<td width="25%" class="text" align="right"><font color="#4b0013"><b>Company &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="company" /></td>
							</tr>
							
						</table>
						</fieldset>

						</logic:equal>
						
						<!-- Telemedia  -->
						<logic:equal property="lobId" name="detail"  value="2">
						
							<fieldset class="field_set_main"><legend class="header"><font
								size=2 color=#ff0000>LOB Details</font></legend>
								<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
									<tr>
										<td width="25%" class="text" align="right"><font color="#4b0013"><b>Plan Id&nbsp;&nbsp;</td>
										<td width="25%" align="left"><bean:write name="detail"	property="planID" /></td>
										<td width="25%" class="text" align="right"><font color="#4b0013"><b>Plan&nbsp;&nbsp;</td>
										<td width="25%" align="left"><bean:write name="detail"	property="plan" /></td>
									</tr>
									<tr>
										<td width="25%" class="text" align="right"><font color="#4b0013"><b>Rental &nbsp;&nbsp;</td>
										<td width="25%" align="left"><bean:write name="detail"	property="rental" /></td>
										<td width="25%" class="text" align="right"><font color="#4b0013"><b>Rental Type &nbsp;&nbsp;</td>
										<td width="25%" align="left"><bean:write name="detail"	property="rental_type" /></td>
									</tr>
									<tr>
										<td width="25%" class="text" align="right"><font color="#4b0013"><b>Online Caf No. &nbsp;&nbsp;</td>
										<td width="25%" align="left"><bean:write name="detail"	property="onlineCafNumber" /></td>
										<td width="25%" class="text" align="right"><font color="#4b0013"><b>Payment Amount &nbsp;&nbsp;</td>
										<td width="25%" align="left"><bean:write name="detail"	property="paymentAmt" /></td>
										<td width="25%" align="left">&nbsp;</td>
									</tr>
									<tr>
										<td width="25%" class="text" align="right"><font color="#4b0013"><b>Trans Ref No. &nbsp;&nbsp;</td>
										<td width="25%" align="left"><bean:write name="detail"	property="transRefNo" /></td>
										<td width="25%" class="text" align="right"><font color="#4b0013"><b>Offer &nbsp;&nbsp;</td>
										<td width="25%" align="left"><bean:write name="detail"	property="offer" /></td>
										<td width="25%" align="left">&nbsp;</td>
									</tr>
									<tr>
										<td width="25%" class="text" align="right"><font color="#4b0013"><b>Download Limit &nbsp;&nbsp;</td>
										<td width="25%" align="left"><bean:write name="detail"	property="downoadLimit" /></td>
										<td width="25%" class="text" align="right"><font color="#4b0013"><b>Device MRP &nbsp;&nbsp;</td>
										<td width="25%" align="left"><bean:write name="detail"	property="deviceMrp" /></td>
										<td width="25%" align="left">&nbsp;</td>
									</tr>
									<tr>
										<td width="25%" class="text" align="right"><font color="#4b0013"><b>Voice Benefit &nbsp;&nbsp;</td>
										<td width="25%" align="left"><bean:write name="detail"	property="voiceBenefit" /></td>
										<td width="25%" class="text" align="right"><font color="#4b0013"><b>Data Quota &nbsp;&nbsp;</td>
										<td width="25%" align="left"><bean:write name="detail"	property="dataQuota" /></td>
										<td width="25%" align="left">&nbsp;</td>
									</tr>
									<tr>
										<td width="25%" class="text" align="right"><font color="#4b0013"><b>Benefit &nbsp;&nbsp;</td>
										<td width="25%" align="left"><bean:write name="detail"	property="benefit" /></td>
										<td width="25%" class="text" align="right"><font color="#4b0013"><b>Pkg Duration &nbsp;&nbsp;</td>
										<td width="25%" align="left"><bean:write name="detail"	property="pkgDuration" /></td>
										<td width="25%" align="left">&nbsp;</td>
									</tr>
									<tr>
							<td width="25%" class="text" align="right"><font color="#4b0013"><b>Company &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="company" /></td>
							</tr>
									
								</table>
							</fieldset>
						</logic:equal>
						
						<!-- 3G -->
						<logic:equal property="lobId" name="detail"  value="23">
						
							<fieldset class="field_set_main"><legend class="header"><font
								size=2 color=#ff0000>LOB Details</font></legend>
								<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
								<tr>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Plan Id&nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="planID" /></td>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Plan&nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="plan" /></td>
								</tr>
								<tr>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Rental &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="rental" /></td>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Rental Type &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="rental_type" /></td>
								</tr>
								<tr>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Online Caf No. &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="onlineCafNumber" /></td>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Payment Amount &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="paymentAmt" /></td>
									<td width="25%" align="left">&nbsp;</td>
								</tr>
								<tr>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Trans Ref No. &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="transRefNo" /></td>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Offer &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="offer" /></td>
									<td width="25%" align="left">&nbsp;</td>
								</tr>
								<tr>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Download Limit &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="downoadLimit" /></td>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Device MRP &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="deviceMrp" /></td>
									<td width="25%" align="left">&nbsp;</td>
								</tr>
								<tr>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Data Quota  &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="dataQuota" /></td>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Device Taken&nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="deviceTaken" /></td>
									<td width="25%" align="left">&nbsp;</td>
								</tr>
								<tr>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Benefit &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="benefit" /></td>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Pkg Duration &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="pkgDuration" /></td>
									<td width="25%" align="left">&nbsp;</td>
								</tr>
								
								<tr>
							<td width="25%" class="text" align="right"><font color="#4b0013"><b>Company &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="company" /></td>
							</tr>
								
							</table>
						</fieldset>
						
						</logic:equal>
						
						<!-- 4G -->
						<logic:equal property="lobId" name="detail"  value="24">
						
							<fieldset class="field_set_main"><legend class="header"><font
								size=2 color=#ff0000>LOB Details</font></legend>
								<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
								<tr>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Plan Id&nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="planID" /></td>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Plan&nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="plan" /></td>
								</tr>
								<tr>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Rental &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="rental" /></td>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Rental Type &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="rental_type" /></td>
								</tr>
								<tr>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Online Caf No. &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="onlineCafNumber" /></td>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Payment Amount &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="paymentAmt" /></td>
									<td width="25%" align="left">&nbsp;</td>
								</tr>
								<tr>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Trans Ref No. &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="transRefNo" /></td>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Offer &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="offer" /></td>
									<td width="25%" align="left">&nbsp;</td>
								</tr>
								<tr>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Download Limit &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="downoadLimit" /></td>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Device MRP &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="deviceMrp" /></td>
									<td width="25%" align="left">&nbsp;</td>
								</tr>
								<tr>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Data Quota  &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="dataQuota" /></td>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Device Taken&nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="deviceTaken" /></td>
									<td width="25%" align="left">&nbsp;</td>
								</tr>
								<tr>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Benefit &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="benefit" /></td>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Pkg Duration &nbsp;&nbsp;</td>
									<td width="25%" align="left"><bean:write name="detail"	property="pkgDuration" /></td>
									<td width="25%" align="left">&nbsp;</td>
								</tr>
								
								<tr>
							<td width="25%" class="text" align="right"><font color="#4b0013"><b>Company &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="company" /></td>
							</tr>
								
							</table>
						</fieldset>
						
						</logic:equal>
						
						<!-- dth -->
						<logic:equal property="lobId" name="detail"  value="5">
						
						
						<fieldset class="field_set_main"><legend class="header"><font
								size=2 color=#ff0000>LOB Details</font></legend>
							<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Plan Id&nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="planID" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Plan&nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="plan" /></td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Rental &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="rental" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Online Caf No.&nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="onlineCafNumber" /></td>
							</tr>
							
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Trans Ref No.  &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="transRefNo" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Payment Amount &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="paymentAmt" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Trans Ref No. &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="transRefNo" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Offer &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="offer" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b> &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Device MRP &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="deviceMrp" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Device Taken &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="deviceTaken" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Benefit &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="benefit" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Pkg Duration &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="pkgDuration" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>
							<tr>
							<td width="25%" class="text" align="right"><font color="#4b0013"><b>Company &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="company" /></td>
								<td>
							</tr>
							
						</table>
						</fieldset>
							
						</logic:equal>
					
						<fieldset>
							<legend><b>Action</b></legend>
							<table border="0" width="100%" cellpadding="1" cellspacing="1">
							<tr>
								<td width="35%" class="text" align="right"><font color="#4b0013"><b>Activity / Action Type <font color="#ff0000">*</font>&nbsp;:</b></font></td>
								<td width="65%" class="pTop3 pLeft5 text">
									<html:select styleClass="width180" property="actionType" onchange="getSubStatus()">
										<html:option value="">Select</html:option>
										<html:optionsCollection name="actionList" value="ID" label="keyValue"/>
									</html:select>
								</td>
							</tr>
							
							<tr style="display:none" id="subStatusDisplay">
								<td width="35%" class="text" align="right"><font color="#4b0013"><b>Sub status <font color="#ff0000"></font>&nbsp;:</b></font></td>
								<td width="65%" class="pTop3 pLeft5 text">
									<html:select styleClass="width180" property="subStatusID">
										<html:option value="">Select</html:option>
										<logic:present name="subStatusList" scope="request">
										<logic:notEmpty name="subStatusList" scope="request">
										<html:optionsCollection name="subStatusList" value="ID" label="keyValue"/>
										</logic:notEmpty>
										</logic:present>
									</html:select>
								</td>
							</tr>
							
								<tr style="display:none" id="cafDisplay">
								<td class="text" align="center" valign="top" style="padding-top:3px;"><font color="#4b0013"><b>CAF Number/Customer ID <font color="#ff0000">*</font>&nbsp;:</b></font></td>
								
								<% if(request.getAttribute("cafStatus") !=null && "true".equalsIgnoreCase(request.getAttribute("cafStatus").toString())) {%>
								 <td class="pTop3 pLeft5">
								  <html:select styleClass="width180" property="cafNumber">
								  	<html:option value="">Select</html:option>
								   	<html:options name="cafList"/>
								  </html:select>
								</td>
								 <%}else { %>
								 <td class="pTop3 pLeft5"><html:text styleClass="width180" property="cafNumber" maxlength="20"></html:text></td>
								<%} %>
							</tr>
							<tr>
								<td class="text" align="right" valign="top" style="padding-top:3px;"><font color="#4b0013"><b>Comments <font color="#ff0000">*</font>&nbsp;:</b></font></td>
								<td class="pTop3 pLeft5"><html:textarea styleClass="width300" property="closureComments" rows="7"></html:textarea></td>
							</tr>
							<tr>
							<td colspan="2" align="center"><html:submit styleClass="red-btn" value="Submit" onclick="javascript:return leadClosure(this)"/></td></tr>
							</table>
						</fieldset><br/>
						<fieldset>
							<legend><h5>Forward</h5></legend>
							<table border="0" width="100%" cellpadding="1" cellspacing="1">
							<tr>
								<td width="35%" class="text" align="right"><font color="#4b0013"><b>Forward To <font color="#ff0000">*</font>&nbsp;:</b></font></td>
								<td width="65%" class="pTop3 pLeft5 text">
									<html:select styleClass="width180" property="forwardTo" >
										<html:option value="">Select</html:option>
										<html:optionsCollection name="userList" value="userLoginId" label="userFname"/>
									</html:select>
								</td>
							</tr>
							<tr>
								<td class="text" align="right" valign="top" style="padding-top:3px;"><font color="#4b0013"><b>Comments <font color="#ff0000">*</font>&nbsp;:</b></font></td>
								<td class="pTop3 pLeft5"><html:textarea styleClass="width300" property="forwardComments" rows="7"></html:textarea></td>
							</tr>
							<tr>
							<td colspan="2" align="center"><html:submit styleClass="red-btn" value="Forward" onclick="javascript:return leadForward()"/></td></tr>
						
							</table>
						</fieldset>
 </div>
</html:form>
