<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%><script language="javascript"  src="<%=request.getContextPath()+"/jScripts/ajaxCall.js"%>"></script>


<script language="javascript">
	var cityCode = " ";
	var CityCode =" ";
	var cityText = " ";
	var circleID=" ";
	var circleiD=" ";
	var circleText=" ";
	var cityZoneCode=" ";
	var cityzonecode=" ";
	var cityzoneText=" ";
	var zoneCodeArr=" ";
	var zonecode=" ";
	var zoneText=" ";
	var hiddenTD=" ";
	var circleMstrId=" ";
	var zoneCodeAndName=" ";
	var pinFlag=" ";
	var GpinFlag=" ";
	var confirmValue=" ";
	var enableButtons=" ";
	
	
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;
	var yyyy = today.getFullYear();
	var hh = today.getHours();
	var min = today.getMinutes();
	
	var curr_dt=dd+'-'+mm+'-'+yyyy;
	
	;

 </script>
<script language="javascript"><!--


function process(date){
	   var parts = date.split("-");
	   var dates = new Date(parts[1] + "-" + parts[0] + "-" + parts[2]);
	   return dates.getTime();
	}
function isEmpty(objInput)
{
	//whitespace characters
 	var whitespace = " \b\t\n\r";
 	
	
	var theInput = trimValue(objInput);
	var theLength = theInput.length;

	// Is the text field empty?
  	if((theInput == null) || (theLength == 0))
	{
    	return (true);
  	}


	for (var i = 0; i < theLength ; i++)
  	{
    	
    	var theChar = theInput.charAt(i);

    	if (whitespace.indexOf(theChar) == -1)
    	{
	    	return (false);
    	}
  	}
  	return (true);
}

function getAppointmentdate()
	{
	
var oRadio = document.forms[0].elements["checkslot2"];
 
   for(var i = 0; i < oRadio.length; i++)
   {
      if(oRadio[i].checked)
      {
        // alert(document.getElementById("checkslotId").value=oRadio[i].value);
         document.getElementById("checkslotId").value=oRadio[i].value;
      }
   }
	 
						
	}
	
	function getCancel()
	{
	  
		//document.getElementById("showHideAppointment").style.display="none";
		document.forms[0].appointmentStatus.value=false ;
		//alert(document.forms[0].appointmentStatus.value);
		
					
	}
	
	var get4GDataForPinCodeeData = function() 
{
var details;
if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
  	{
  		var el = document.getElementById("confirm");
		var responseValue = xmlHttpRequest.responseText;
		if(responseValue == "") 
		{
		document.forms[0].pinCode.focus();
		pinFlag="false";
		}
		else 
		{
		details =responseValue.split("#");
		cityzonecode=details[0];
		cityzoneText=details[1];
		CityCode=details[2];
		cityText=details[3];
		zoneCodeAndName=details[4]+"#"+details[5];
		pinFlag="true";
		}
	}
}

function get4GDataForPinCodee(pinCode,productLobId,circleMstrId)
{
	var data = "methodName=getDataForPinCode&pinCode=" + pinCode+"&productLobId="+productLobId+"&circleMstrId="+circleMstrId;
    doSyncAjax("leadRegistration.do", "GET", get4GDataForPinCodeeData, data);
}


function get4GDataForPinCodeeValidate() {
	
	if(document.forms[0].pinCode==null || document.forms[0].pinCode.value == "" )
		{
			  alert("Please enter Pincode.");
			  document.forms[0].pinCode.focus();
			  return false;
		}
		
	
	else if(document.forms[0].pinCode.value.length < 6 || !isInteger(document.forms[0].pinCode) )
			{
			  alert("Please enter valid 6 digit Pincode.");
			  document.forms[0].pinCode.focus();
			  return false;
			}
		
		var PinCode = document.forms[0].pinCode.value;
		var productLobId  = document.forms[0].productLobId.value;
		var circleID = document.leadRegistrationFormBean.circleId;
		circleiD = circleID.options[circleID.options.selectedIndex].value.split("#")[0];
		circleText = circleID.options[circleID.options.selectedIndex].text;
		circleMstrId=circleID.options[circleID.options.selectedIndex].value.split("#")[2];
		cityCode = document.forms[0].cityCode;
		CityCode = cityCode.options[cityCode.options.selectedIndex].value;
		
	    cityText = cityCode.options[cityCode.options.selectedIndex].text;
	 	
	 	
	 	
	  	get4GDataForPinCodee(PinCode,productLobId,circleMstrId);
	 	
	 	if( pinFlag == " ")
		{
		return false;
		}
		if(pinFlag == "false")
		{
		alert("Not a valid PIN code");
		document.leadRegistrationFormBean.pinCode.focus();
		return false ;
		}
		else if(CityCode != '-1')
	{
   					 citycode = document.leadRegistrationFormBean.cityCode;
	                 citycode.options[citycode.options.selectedIndex].value=CityCode;
	                 citycode.options[citycode.options.selectedIndex].text=cityText;
	                 
	                 
	                cityZoneCode = document.leadRegistrationFormBean.cityZoneCode;
	               	cityZoneCode.options[cityZoneCode.options.selectedIndex].value=cityzonecode ;
					cityZoneCode.options[cityZoneCode.options.selectedIndex].text=cityzoneText;
					
					
					if(cityzonecode == '-1')
					{
					
					cityzoneText=" ";
					cityzonecode=" ";
					}
			
	PinCode=" ";
	
	}
	
	else
		{
		
			
			PinCode=" ";
		
			
					 
					 if(CityCode == '-1')
					{ 
					cityText=" ";
					CityCode=" ";
					}
					else
					{
					CityCode=CityCode.split("#")[1];
					// end of populating citycode			
					// populating zonecode
					getZoneCode(CityCode.split("#")[0]);
					// end of populating zonecode
					}
			// getting cityzone code and text
					cityZoneCode = document.leadRegistrationFormBean.cityZoneCode;
					cityzonecode = cityZoneCode.options[cityZoneCode.options.selectedIndex].value;
					cityzoneText = cityZoneCode.options[cityZoneCode.options.selectedIndex].text;
					if(cityzonecode == '-1')
					{
					cityzoneText=" ";
					cityzonecode=" ";
					}
	}
	 return true;
	}
	
function validate()
{

if(document.forms[0].circleId.value == "")
	{
	 	alert("Please select Circle.");
		return false;	
	}

if(document.forms[0].cityCode.value == "")
	{
	 	alert("Please select City.");
		return false;	
	}
	if(document.forms[0].address1.value == "")
	{
	 	alert("Please enter the  Address");
		return false;	
	}
return get4GDataForPinCodeeValidate();
}
	
function submitForm()
{
document.forms[0].pageStatus.value="edit";
  if(validate())
 {

 	document.forms[0].methodName.value ="updateRecord";
   	    document.forms[0].submit();
}
}


 var getDataForPinCode = function()
	   {		
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
  	      {
  	    var xmldoc = xmlHttpRequest.responseXML.documentElement;
		if (xmldoc == null)
		{
		 return false;
		}
		
		else
		return true;
	  }
	 return true;
	  }	


var getCityOnCircleChange = function()
	{
	
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.forms[0].cityCode;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select City", "");
			for (var i = 0; i < optionValues.length; i++)
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
		}
	}
	
function getcityForCircle()
	{
	
		var circleID = document.forms[0].circleId;
		var circleMstriD = circleID.options[circleID.options.selectedIndex].value.split("#")[2];// changed done by pratap on 28-11-13
		removeAllOptions(document.leadRegistrationFormBean.cityCode,new Option("Select City","-1"));
		removeAllOptions(document.leadRegistrationFormBean.cityZoneCode,new Option("Select CityZone","-1"));
		document.leadRegistrationFormBean.pinCode.value="";
		document.forms[0].pinCode.value="";
   		var data = "methodName=getCityOnCircleChange&circleMstriD=" + circleMstriD;
		doAjax("leadRegistration.do", "GET", false, getCityOnCircleChange, data);
		
	}
	
function getCityZoneOnCityChange()
	{
        removeAllOptions(document.forms[0].cityZoneCode,new Option("Select CityZone",""));
		var cityCode = document.forms[0].cityCode;
		
		var citycode = cityCode.options[cityCode.options.selectedIndex].value.split("#")[1];
		
        var data = "methodName=getCityZoneOnCityChange&cityCode=" + citycode;
		doAjax("leadRegistration.do", "GET", false, getCityZoneForCity, data);
	}
	
	var getCityZoneForCity = function()
	{

		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.forms[0].cityZoneCode;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select cityzone", "");
			for (var i = 0; i < optionValues.length; i++)
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
		}
	}
	
var getAppointmentData = function()
	{
	 document.getElementById("getAppointmentId").style.display="";
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
	  	var xmldoc = xmlHttpRequest.responseXML.documentElement;
	  	if (xmldoc == null) {
		return;
		}else {
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = document.leadRegistrationFormBean.checkslot;
		
		selectObj.options.length = 0;
		selectObj.options[selectObj.options.length] = new Option(" Select slot", "-1");
		for (var i = 0; i < optionValues.length; i++){
		selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
		}
		}
		document.getElementById("displayId").style.display="";
		document.getElementById("displayId1").style.display="";
		}
	
	}	
	
	function appointment()
	{
	document.forms[0].appointmentStatus.value=true ;
	
	document.leadRegistrationFormBean.appointmentStatus.value="true";
	circleID = document.forms[0].circleId;
	
	circleiD = circleID.options[circleID.options.selectedIndex].value.split("#")[0];
	circleText = circleID.options[circleID.options.selectedIndex].text;
	circleMstrId=circleID.options[circleID.options.selectedIndex].value.split("#")[2];
	
	productLobId=document.forms[0].productLobId.value;
	
	taskType=document.forms[0].taskType.value;
	
	 pinCode=document.forms[0].pinCode.value;

	 taskType=document.forms[0].taskType.value;
		if(taskType ==null || taskType=="" )
		 {
		document.forms[0].taskType.focus();
		alert("Please enter valid taskType");
		return false;
		}
		
		if(document.forms[0].pinCode.value=="")
		{
		alert("Please enter Pincode ");
		return false;
		}
		
		if(document.forms[0].pinCode.value != "")
		{
		if(document.forms[0].pinCode.value.length < 6 || !isInteger(document.forms[0].pinCode) )
			{
			  alert("Please enter valid 6 digit PIN.");
			  document.forms[0].pinCode.focus();
			  return false;
			}
		}
		
		get4GDataForPinCodeeValidate();
		
		
  	 var formDate  = document.forms[0].appointmentStartDate.value;
	 var formDate1  = document.forms[0].appointmentEndDate.value;
	 var startHour= document.forms[0].appointmentStartHour.value;
	 var startMinute= document.forms[0].appointmentStartMinute.value;
	 var endHour= document.forms[0].appointmentEndHour.value;
	 var endMinute= document.forms[0].appointmentEndMinute.value;
	
	 appointmentStart=formDate+" "+startHour+":"+startMinute;
	 appointmentEnd=formDate1+" "+endHour+":"+endMinute;
	 			
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;
	var yyyy = today.getFullYear();
	var hh = today.getHours();
	var min = today.getMinutes();
	if(dd<10){dd='0'+dd}
	if(mm<10){mm='0'+mm}
			
	var curr_dt=dd+'-'+mm+'-'+yyyy;
	
     if(formDate == "" || formDate1 == "")
     {
     alert("Select appointment start and end dates!");
     return false;
     }
	   if(process(formDate) < process(curr_dt))
		{
			alert("Appointment Start Date cannot be a past Date.");
			return false;			
		}
		
		if(startHour == "")
			{
				alert("Please select Appointment Start Hour.");
				return false;			
			}
			
		if(startMinute == "")
			{
				alert("Please select Appointment Start Minute.");
				return false;			
			}
			
			
				
			if(process(formDate1) < process(curr_dt)|| (process(formDate1) < process(formDate)) )
		{
			alert("Appointment End Date cannot be a past Date.");
			return false;			
		}
		
			if(endHour == "")
			{
				alert("Please select Appointment End Hour ");
				return false;			
			}
						
			if(endMinute == "")
			{
				alert("Please select Appointment End Minute.");
				return false;			
			}
		if(formDate == curr_dt)
		{
		   
			if(startHour != "")
			{
			   if(startHour < hh)
			   {
				alert("Appointment Start Hour cannot be a past time.");
				return false;
				}			
			}
		
			
			if(startMinute != "")
			{
				if(startHour == hh )
				{
					if(startMinute < min)
					{
					alert("Appointment Minute cannot be a past time.");
					return false;
					}
				}			
			}
		}
		//if start and end date are equal:			 
			if(process(formDate) == process(formDate1))
			{	 
				   
					if(endHour != "")
					{
					   if(endHour < startHour)
					   {
						alert("Appointment End Hour cannot be a past time.");
						return false;
						}			
					}
				
					
					if(endMinute!= "")
					{
						if(endHour == startHour )
						{
						 	if(endMinute < startMinute)
					    	{
							alert("Appointment Minute cannot be a past time.");
							return false;
							}
						}			
					}
				}
				
		document.getElementById("getAppointmentId").style.display="none";
		var data = "methodName=getAppointment&productLobId="+productLobId+"&pinCode="+pinCode+"&taskType="+taskType+"&appointmentStart="+appointmentStart+"&appointmentEnd="+appointmentEnd;
		doAjax("leadRegistration.do", "GET", false, getAppointmentData, data);
		
		}
	

	function getZoneCode(zonecode)
	{
	
		var data = "methodName=getZoneCode&zonecode=" + zonecode;
		doSyncAjax("leadRegistration.do", "GET", getZoneCodeForCity, data);
		
	}
		
	var getZoneCodeForCity = function()
	{
	
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var responseValue = xmlHttpRequest.responseText;
			
			if(responseValue == " ") 
			{
			zoneCodeAndName=" ";
			document.forms[0].zoneCodeAndName.value=" ";
			}
			else
			{
				zoneCodeAndName = responseValue;
			document.forms[0].zoneCodeAndName.value=responseValue;
			}
			}
	}		

 function cleanStartTime(){
	 
	 	
	 	document.forms[0].appointmentStartDate.value="";
		document.forms[0].appointmentStartHour.value="";
		document.forms[0].appointmentStartMinute.value="";
 }
 function cleanEndTime(){
	 
	 	document.forms[0].appointmentEndDate.value="";
		document.forms[0].appointmentEndHour.value="";
		document.forms[0].appointmentEndMinute.value="";
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

#confirm {

	 visibility: hidden;
     position: fixed;
     left: 0px;
     top: 0px;
     width:100%;
     height:100%;
     text-align:center;
     z-index: 1000;
}
#confirm div {
     width:250px;
     margin: 100px auto;
     background-color: lightblue;
     border:1px solid #000;
     padding:15px;
     text-align:center;
     }
#crossSale {

	 visibility: hidden;
     position: fixed;
     left: 0px;
     top: 0px;
     width:100%;
     height:100%;
     text-align:center;
     z-index: 1000;
}
#crossSale div {
     width:250px;
     margin: 100px auto;
     background-color: lightblue;
     border:1px solid #000;
     padding:15px;
     text-align:center;
     }
</style>

<style type="text/css">
 .row { vertical-align: top; height:auto !important; }
 .list {display:none; }
 .show {display: none; }
 .hide:target + .show {display: inline; }
 .hide:target {display: none; }
 .hide:target ~ .list {display:inline; }
 @media print { .hide, .show { display: none; } }
 </style>
 

<html:form action="/leadRegistration">
	<html:hidden property="methodName" value="editLeadDetails"/>
	<html:hidden property="leadId" />
		<html:hidden property="pageStatus" value=""/>
	
		<html:hidden property="appointmentStatus" value="" />
	
     
  <logic:notEqual name="leadRegistrationFormBean" property="initStatus" value="true">
	    <html:hidden	property="productLobId" name="leadRegistrationFormBean" /></p>
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
			 
			 	<%
				if (null != request.getAttribute("updateStatus")) {
								String updateStatus = request.getAttribute("updateStatus").toString();
								if (!"null".equals(updateStatus)) {
									out.print(updateStatus);
								}
							}
			%>
			
			<ul class="list2 form1">
			
			<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
					
				<TR>	
					<TD align="left" valign="top" class="label0"><span class="text2 fll width120">Lead Id </span></TD>
					<TD align="left" valign="top" class="labelValue"><b><bean:write name="leadRegistrationFormBean" property="leadId" /></b></TD>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width120">Product Name</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="leadRegistrationFormBean" property="productName" /></TD>
				</TR>
				<TR class="alt">	
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">Customer Name </span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="leadRegistrationFormBean" property="customerName" /></TD>
					<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">Mobile Number</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="leadRegistrationFormBean" property="contactNo" /></TD>
					<%} %>
				</TR>
					
				<TR class="alt">	
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">Email Id</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="leadRegistrationFormBean" property="email" /></TD>
					<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
					<TD align="left" valign="top" class="label0"><span class="text2 fll ">Alternate Mobile Number</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="leadRegistrationFormBean" property="alternateContactNo" /></TD>
					<%} %>
						</TR>
				</table>
				
				 
					<li class="clearfix alt">
				<span class="text2 fll width160"><strong>Lead LifeCycle Status</strong> </span>
				<p class="clearfix fll" style="width: 208px">
				<html:select property="requestCategoryId" name="leadRegistrationFormBean"	styleClass="select1">
					<html:option value="">Select Status</html:option>
					<logic:notEmpty name="leadRegistrationFormBean"	property="categoryForStatusList">
						<bean:define id="requestCategories" name="leadRegistrationFormBean"	property="categoryForStatusList" />
						<html:options labelProperty="requestCategoryName" property="requestCategoryId" collection="requestCategories" />
					</logic:notEmpty>
				</html:select></p>
				
					
				
				<span class="text2 fll width60"><strong>Feasibility Param &nbsp;&nbsp;</strong></span>
				<p class="clearfix fll" style="width: 232px">
				<html:select styleId="feasibilityParam" property="feasibilityParam" name="leadRegistrationFormBean" styleClass="select1" >
				<html:option value=""><bean:write name="leadRegistrationFormBean" property="feasibilityParam" /></html:option>
				<html:option value="Y">Y</html:option>
				<html:option value="N">N</html:option>
				</html:select></p>
				</li>
								
				
					<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Plan and Transaction Details</font></legend>
					<li class="clearfix"><span class="text2 fll width100"><strong>Rental</strong></span>
				<p class="clearfix fll" align="justify" style="margin-top: 5px; width: 300px"> 
				<bean:write property="rental" name="leadRegistrationFormBean" /></p>
				
					<span class="text2 fll width100"><strong>RentalType</strong></span>
				<p class="clearfix fll" align="justify" style="margin-top: 5px; width: 150px"> <bean:write
					property="rentalType" name="leadRegistrationFormBean" /></p>
				</li>
						
				
					
				<li class="clearfix"><span class="text2 fll width100"><strong>Offer</strong></span>
				<p class="clearfix fll" align="justify" style="margin-top: 5px; width: 300px"> 
				<bean:write property="offer" name="leadRegistrationFormBean" /></p>
				
				<span class="text2 fll width100"><strong>Company</strong></span>
				<p class="clearfix fll" align="justify" style="margin-top: 5px; width: 150px"> <bean:write
					property="company" name="leadRegistrationFormBean" /></p>
				</li>
				
				<li class="clearfix"><span class="text2 fll width100"><strong>DeviceMrp</strong></span>
				<p class="clearfix fll" align="justify" style="margin-top: 5px; width: 300px"> <bean:write
					property="deviceMrp" name="leadRegistrationFormBean" /></p>
				
				
				<span class="text2 fll width100"><strong>OnlineCafNo</strong></span>
				<p class="clearfix fll" align="justify" style="margin-top: 5px; width: 150px"> 
				<bean:write property="onlineCafNo" name="leadRegistrationFormBean" /></p>
		
				</li>	
				
				</fieldset>	
				
					
					<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Location Details</font></legend>
				<li class="clearfix alt"><span class="text2 fll width160"><strong>Circle</strong><font color="red">*</font></span>
				<p class="clearfix fll" style="width: 238px">
				<html:select
					property="circleId" name="leadRegistrationFormBean"
					styleClass="select1" onchange="getcityForCircle()">
					<html:option value="">Select Circle</html:option>
					<logic:notEmpty name="leadRegistrationFormBean" property="circleForProductList">
						<bean:define id="circles" name="leadRegistrationFormBean" property="circleForProductList" />
						<html:options labelProperty="circleName"
							property="circleIdLobIdCircleMstrId" collection="circles" />
					</logic:notEmpty>
				</html:select></p>
				
				<span class="text2 fll width100"><strong>City</strong> <font color="red">*</font></span>
				<p class="clearfix fll" style="width: 232px"><html:select property="cityCode" name="leadRegistrationFormBean" styleClass="select1" onchange="getCityZoneOnCityChange()">
					<html:option value="">Select City</html:option>
					<logic:notEmpty name="leadRegistrationFormBean" property="cityList">
						<bean:define id="cities" name="leadRegistrationFormBean" property="cityList" />
						<html:options labelProperty="cityName" property="cityCode" collection="cities" />
					</logic:notEmpty>
				</html:select></p>
				</li>

				<li class="clearfix alt"><span class="text2 fll width160"><strong>City Zone</strong> </span>
				<p class="clearfix fll" style="width: 232px"><html:select styleId="cityZoneCode" property="cityZoneCode" name="leadRegistrationFormBean" styleClass="select1" >
					<html:option value="">Select </html:option>
					<logic:notEmpty name="leadRegistrationFormBean" property="cityZoneList">
						<bean:define id="zones" name="leadRegistrationFormBean" property="cityZoneList" />
						<html:options labelProperty="cityZoneName" property="cityZoneCode" 	collection="zones" />
					</logic:notEmpty>
				</html:select></p>
				
				
				<class="clearfix alt"> 
				<span class="text2 fll width100"><strong>PinCode</strong> <font color="red">*</font></span>
				<p class="clearfix fll" style="width: 232px"><span class="textbox6"> <span class="textbox6-inner"> <html:text property="pinCode" name="leadRegistrationFormBean" styleClass="textbox10" maxlength="6" /></span></span></p>
			    </li>
				
				
				<li class="clearfix alt"><span class="text2 fll width160"><strong>Address 1</strong> <font color="red">*</font></span>
				<p class="clearfix fll" align="justify" style="width: 232px"><html:textarea  property="address1" name="leadRegistrationFormBean" onclick="this.style.height='200px';" onblur="this.style.height='';"></html:textarea></p>
				
				
				<span class="text2 fll width100"><strong>Address 2</strong></span>
				<p class="clearfix fll" align="justify" style="width: 232px"><html:textarea  property="address2" name="leadRegistrationFormBean" onclick="this.style.height='200px';" onblur="this.style.height='';"></html:textarea></p>
			
			</li>
				
			</fieldset>
			
			<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Source Details </font>   </legend>
				
				<table width="100%" border="0" cellspacing="0" cellpadding="2" class="form1">
					
				<TR>	
					<TD align="left" valign="top" class="label0"><span class="text2 fll width160">Source</span></TD>
					<TD align="left" valign="top" class="labelValue"><b><bean:write name="leadRegistrationFormBean" property="sourceName" /></b></TD>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width120">SubSource</span></TD>
					<TD align="left" valign="top" class="labelValue"><bean:write name="leadRegistrationFormBean" property="subSourceName" /></TD>
				</TR>
				
				<TR>	
					<TD align="left" valign="top" class="label0"><span class="text2 fll width160">Campaign</span></TD>
					<TD align="left" valign="top" class="labelValue"><b><bean:write name="leadRegistrationFormBean" property="campaign" /></b></TD>
					</TR>
				
				</table>
				</fieldset>
				
				
			<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Payment Details</font></legend>	
			
			
				<li class="clearfix alt"> 
				<span class="text2 fll width120"><strong>Plan</strong></span>
				<p class="clearfix fll" style="width: 232px"><span class="textbox6"> <span class="textbox6-inner"> <html:text property="plan" name="leadRegistrationFormBean" styleClass="textbox10" maxlength="25" /></span></span></p>
			    
			    <span class="text2 fll width120"><strong>Payment Amount</strong></span>
				<p class="clearfix fll" style="width: 232px"><span class="textbox6"> <span class="textbox6-inner"> <html:text property="payment" name="leadRegistrationFormBean" styleClass="textbox10" maxlength="25" /></span></span></p>
			   
			    </li>
			    
			    <li class="clearfix alt"> 
				<span class="text2 fll width120"><strong>Payment TxnId</strong></span>
				<p class="clearfix fll" style="width: 232px"><span class="textbox6"> <span class="textbox6-inner"> <html:text property="transactionRefNo" name="leadRegistrationFormBean" styleClass="textbox10" maxlength="25" /></span></span></p>
			    <span class="text2 fll width120"><strong>Service Status</strong></span>
				<p class="clearfix fll" style="width: 232px"><span class="textbox6"> <span class="textbox6-inner"> <html:text property="feasibilityParam" name="leadRegistrationFormBean" styleClass="textbox10" maxlength="25" /></span></span></p>
			    </li>
			    
			     <li class="clearfix alt"> 
				<span class="text2 fll width120"><strong>Device</strong></span>
				<p class="clearfix fll" style="width: 232px"><span class="textbox6"> <span class="textbox6-inner"> <html:text property="devicetaken" name="leadRegistrationFormBean" styleClass="textbox10" maxlength="25" /></span></span></p>
			    <span class="text2 fll width100"><strong>Payment Type</strong></span>
				<p class="clearfix fll" style="width: 232px"><font size="2">Cash On Delivery</font></p>
				</li>
			    
			   <!--   <logic:equal name="leadRegistrationFormBean" property="productflag" value="true"> 	    
				<li class="clearfix alt"> 
				<span class="text2 fll width120"><strong>Preferred Appointment start Time</strong></span>
				<p class="clearfix fll" style="width: 232px"><b><bean:write property="appointmentTime" name="leadRegistrationFormBean"></bean:write></b></p>
			    <span class="text2 fll width120"><strong>Preferred Appointment end Time</strong></span>
				<p class="clearfix fll" style="width: 232px"><b><bean:write property="appointmentEndTime" name="leadRegistrationFormBean" /></b></p>
			    </li>-->
			    
			    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="form1">
					
				<TR>	
					<TD align="left" valign="top" class="label0"><span class="text2 fll width160"><strong>Appointment Start Time</strong></span></TD>
					<TD align="left" valign="top" class="labelValue"><b><bean:write name="leadRegistrationFormBean" property="appointmentTime" /></b></TD>
					<TD align="left" valign="top" class="label0"><span class="text2 fll width120"><strong>Appointment End Time</strong></span></TD>
					<TD align="left" valign="top" class="labelValue"><b><bean:write name="leadRegistrationFormBean" property="appointmentEndTime" /></b></TD>
				</TR>
				</table>
				<!--</logic:equal>  --> 
				
				
				<li class="clearfix alt"> 
				<span class="text2 fll width120"><strong>Payment Status</strong></span>
				<p class="clearfix fll" style="width: 232px"><span class="textbox6"> <span class="textbox6-inner"> <html:text property="extraParam6" name="leadRegistrationFormBean" styleClass="textbox10" maxlength="25" /></span></span></p>
			   </li>
				</fieldset>
				<logic:equal name="leadRegistrationFormBean" property="productflag" value="true">
	    				
     				<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Get Appointment Details</font></legend>
									
					<li class="clearfix alt">
					<span class="text2 fll width120"><strong>Task Type<font color=red>*</font></strong> </span>
					<p class="clearfix fll margin-r20">
						<span class="textbox6"> <span class="textbox6-inner">
								<html:text property="taskType" styleClass="textbox10"
									maxlength="25"  value="4G ODC Installation" readonly="true"/>
						</span>
						</span>
					</p> 
					</li>
				
					
				<table cellpadding="0" cellspacing="2">
				<tr>
				<td class="text2 fll width260"><strong><font size="2">Prefered Appointment Start Time</font></strong><font color="red" >*</font> &nbsp;&nbsp;</td>
				<td>	<html:text property="appointmentStartDate" styleClass="tcal calender2 fll" readonly="true"/>
					</td>
					
				
						<td><html:select property="appointmentStartHour" name="leadRegistrationFormBean" styleClass="select0">
							<html:option value="">HH</html:option>
							<%
								String hh = "00";
												for (int ii = 0; ii <= 24; ii++) 
												{
													if (ii < 10)
														hh = "0" + ii;
													else
														hh = "" + ii;
							%>
							<html:option value="<%=hh%>"><%=hh%></html:option>
							<%
								}
							%>
						</html:select></td>
						<td><html:select property="appointmentStartMinute" name="leadRegistrationFormBean" styleClass="select0">
							<html:option value="">MM</html:option>
							<%
								String mm = "00";
												for (int ii = 0; ii < 60; ii++) {
													if (ii < 10)
														mm = "0" + ii;
													else
														mm = "" + ii;
							%>
							<html:option value="<%=mm%>"><%=mm%></html:option>
							<%
								}
							%>

						</html:select></td>	<td><img src="images/delete_icon.gif" onclick="cleanStartTime()"></td>
						</tr>
				</table>
				
				
				
				
				
				<table cellpadding="0" cellspacing="2">
				<tr>
			<td class="text1 fll width260"><strong><font size="2">Prefered Appointment End Time</font><font color="red">*</font></strong>&nbsp;&nbsp;&nbsp;&nbsp; </td>
				<td>	 <html:text property="appointmentEndDate" styleClass="tcal calender2 fll" readonly="true"/></td>
					
						<td><html:select property="appointmentEndHour" name="leadRegistrationFormBean" styleClass="select0">
							<html:option value="">HH</html:option>
							<%
								String hh = "00";
												for (int ii = 0; ii <= 24; ii++) {
													if (ii < 10)
														hh = "0" + ii;
													else
														hh = "" + ii;
							%>
							<html:option value="<%=hh%>"><%=hh%></html:option>
							<%
								}
							%>
						</html:select></td>
						<td><html:select property="appointmentEndMinute"
							name="leadRegistrationFormBean" styleClass="select0">
							<html:option value="">MM</html:option>
							<%
								String mm = "00";
												for (int ii = 0; ii < 60; ii++) {
													if (ii < 10)
														mm = "0" + ii;
													else
														mm = "" + ii;
							%>
							<html:option value="<%=mm%>"><%=mm%></html:option>
							<%
								}
							%>



						</html:select></td>	<td><img src="images/delete_icon.gif" onclick="cleanEndTime()"></td>
						</tr>
					</table>
					
				
				
			<table>
		<tr>
		<td>
			<a class="red-btn" style="width:60px;height:30px" onclick="appointment();" id ="getAppointmentId"><b>Get Appointment</b>
			</a>
		</td>
		<td>
		<a class="red-btn" style="width:60px;height:30px" onclick="getCancel();" name=><b>Cancel</b></a> 
		<!--<html:radio property="appointmentStatus" value='false' onclick= "getCancel();"/><strong>Cancel</strong>-->
		</td>
		<td  id="displayId" style="display:none">&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<strong class="text2">Time Slots Available</strong></td><td style="display:none" id="displayId1"><html:select property="checkslot" name="leadRegistrationFormBean" styleClass="select1"  style="font: bold;width: 257px;"><html:option value="-1">Please Select Time Slot</html:option></html:select></td>	
		</tr></table>
					
					</fieldset>
				
					</logic:equal>
				</ul>
				
       </div>
       
	  </div>
	  
     </div>
	</logic:notEmpty>
	
	
	
	<br>
	<logic:equal name="leadRegistrationFormBean" property="productflag" value="true">
	<div class="button-area">
		<div class="button">
			<a class="red-btn" onclick="submitForm();"><b>Submit All</b></a>
		</div>
	</div>	
		</logic:equal>
		
	<logic:equal name="leadRegistrationFormBean" property="productflag" value="false">
	
	<div class="button-area">
		<div class="button">
			<a class="red-btn" onclick="submitForm();"><b>Update</b></a>
		</div>
	</div>	
	</logic:equal>
 </logic:notEqual>
</html:form>