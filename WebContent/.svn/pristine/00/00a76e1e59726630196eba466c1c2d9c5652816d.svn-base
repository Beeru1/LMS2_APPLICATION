<%@page import="com.ibm.lms.forms.LeadRegistrationFormBean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>


<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%>
<%@page import="com.ibm.websphere.management.repository.Document"%><script
	language="javascript"
	src="<%=request.getContextPath() + "/jScripts/ajaxCall.js"%>">
	</script>
<script type="text/javascript" src="common/js/jquery.modal_box.js"
	charset="utf-8"></script>
<script type="text/javascript">

</script>

<script language="javascript"><!--
 
	
	
	 var isAddressMandatory = false;
	 var isPINMandatory = true;
	 var isRSUMandatory=false;
	 var isRemarksMandatory = false;
	var pinFlag=" ";
	var cityzonecode=" ";
	var cityzoneText=" ";
	var CityCode=" ";
	var cityText=" ";
	var zoneCodeAndName =" ";
	var zonecode=" ";
	var zonename=" ";
 function process(date){
   var parts = date.split("-");
   var dates = new Date(parts[1] + "-" + parts[0] + "-" + parts[2]);
   return dates.getTime();
}

 function valid(productId)
 {
	 var productId= dialerUpdateForm.productid.value;
	 if(productId == "1" || productId== "173")
		 {
		 return true;
		 }
	 else
		{
		 return false;
		}
 }
function getAll()
{
//alert("====in getall funtion ::::");
 // adding by pratap for populating all data from backend as pincode has been entered
 var PinCode=dialerUpdateForm.pinCode.value;
 var circleID=document.forms[1].circleId;
 var circleMstriD = circleID.options[circleID.options.selectedIndex].value.split("#")[2];
 if(PinCode != "")
	{
	// populating all things as pincode is entered or giving alert that pin code entered is wrong
	var leadId = trimAll(dialerSearchForm.leadId.value);
	
	getDataForPinCode(PinCode,circleMstriD);
	}
	if(PinCode != "")
	if(pinFlag == "false")
	{
	alert("Not a valid PIN code");
	dialerUpdateForm.pinCode.value="";
	dialerUpdateForm.pinCode.focus();
	return false;
	}
	else
	{
	//alert("========populating all status ==================:"+pinFlag);
	//changeLeadStatus("&nonServiceAble=false&leadId="+leadId);// populating lead status again
	//dialerUpdateForm.leadStatusId.focus();
	}
 // end of adding by pratap fro populating all data from backedn as pincode has been entered
}
function getDataForPinCode(pinCode,circleMstrId)
{
	var data = "methodName=getDataForPinCode&pinCode=" + pinCode+"&circleMstrId="+circleMstrId;
	//doAjax("leadRegistration.do", "GET", true, getDataForPinCodee, data);
	doSyncAjax("leadRegistration.do", "GET", getDataForPinCodee, data);
}

var getDataForPinCodee = function()
{
var details;
	if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
  	{
		var responseValue = xmlHttpRequest.responseText;
		if(responseValue == "") 
		{
		pinFlag="false"; // for not a valid pin code, no record found in DB
		}
		else if(responseValue == "NON_SERVICEABLE_PINCODE")// this block is for checking pincode is serviceable or not
		{ // pin code not found in serviceable_pincode_mstr table 
		var el = document.getElementById("confirm");
		el.style.visibility ="visible";
		}
		else
		{
		details =responseValue.split("#");
		cityzonecode=details[0];
		cityzoneText=details[1];
		CityCode=details[2];
		cityText=details[3];
		zoneCodeAndName=details[4]+"#"+details[5];
		zonecode=details[4];
		zonename=details[5];
		pinFlag="true";
		//alert("response taken as ,cityzonecode :"+cityzonecode+" cityzoneText :"+cityzoneText+" CityCode :"+CityCode+" cityText:"+cityText+" zonecode :"+zonecode+"  zonename :"+zonename+" pinFlag :"+pinFlag);
		var cizonecodeDD=document.forms[1].cityZoneCode;
		var cityDD=document.forms[1].cityCode;
		for(var i=cizonecodeDD.length-1; i>=0; i--)
  		 {
	         cizonecodeDD.options[i] = null;
   		}
			for(var j=cityDD.length-1; j>=0; j--)
  		 {
	         cityDD.options[j] = null;
   		}
		cizonecodeDD.options[cizonecodeDD.options.length] = new Option(cityzoneText,cityzonecode);
		cityDD.options[cityDD.options.length] = new Option(cityText,"#"+CityCode);
		document.forms[1].zoneCode.value =zonecode;
		//changeLeadStatus("&nonServiceAble=false");// populating lead status again 
		
		}
	}
}

function changeLeadStatus(attri)
{
//var data = "methodName=getLeadStatusIfLeadNotServiceAble"+attri;

	//doSyncAjax("leadRegistration.do", "GET", changeLeadStatusHandler, data);
	//doAjax("leadRegistration.do", "GET", false, changeLeadStatusHandler, data);
	
		var data = "methodName=getLeadStatusIfLeadNotServiceAble" + attri;
		//alert("data :"+data);
		//doAjax("leadRegistration.do", "GET", false, changeLeadStatusHandler, data);
		doSyncAjax("leadRegistration.do", "GET", changeLeadStatusHandler, data);
}

function changeLeadStatusHandler()
{
var details;
var codeAndName;
var codeAndNameArr;
	if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
  	{
  	
  	var leadStatusIdDD=dialerUpdateForm.leadStatusId;
  		for(var i=leadStatusIdDD.length-1; i>=0; i--)
  		 {
	         leadStatusIdDD.options[i] = null;
   		}
  	var responseValue = xmlHttpRequest.responseText;
  	// alert("responseValue  :"+responseValue);
  	leadStatusIdDD.options[leadStatusIdDD.options.length] = new Option("Select","");
  	codeAndNameArr=responseValue.split(","); 
  	if(codeAndNameArr.length==1)codeAndNameArr.length=2;  
  	for(var a=0;a < codeAndNameArr.length-1;a++)
  	{
  	codeAndName=codeAndNameArr[a].split("=");//
  	leadStatusIdDD.options[leadStatusIdDD.options.length] = new Option(codeAndName[1],codeAndName[0]);
  	}
  	}
}

	function validate()
 	{
		//alert("hiiiiii");
		<%boolean isCrossell = false;
			if (request.getAttribute("fromUpdate") != null) {
				if (request.getAttribute("fromUpdate").equals("fromUpdate"))
					isCrossell = true;
			}%>
		//alert("stop #1");
		var crosssell = <%=isCrossell%>	
		//alert("stop #2");
		if (crosssell == true)
		{
	 		var sell = dialerUpdateForm.productIds;
			if (sell)
			{
				//checkbox(s) exists
				var checked = false;
				if (sell.length)
				{
					//multiple checkboxes
					var len = sell.length;
					for (var i=0; i<len; i++)
					{
						if (sell[i].checked && sell[i].disabled)
						{
							checked = true;
							break;
						}
					}
				}
				else
				{
					//single checkbox only
					checked = sell.checked;
				}
				if (checked)
				{
					return false;
				}
			}
		}
 		//alert("stop #8");
//alert('code'+dialerUpdateForm.alternateContactNo); 		
		dialerUpdateForm.customerName.value = trimAll(dialerUpdateForm.customerName.value);
		if (isEmpty(dialerUpdateForm.customerName))
		{
		 	alert("Please enter Customer Name.");
		 	dialerUpdateForm.customerName.focus();
		 	return false;
		}
		else if(!isName(dialerUpdateForm.customerName))
		{
			alert("Only Alphabets and spaces are allowed in Customer Name.");
			dialerUpdateForm.customerName.select();
			return false;	
		}
		else if(!(isEmpty(dialerUpdateForm.landlineNo)) && dialerUpdateForm.landlineNo.value.length <11)
		{
			alert("Landline number should be of 11 digits.");
			return false;
		}
		else if(!(isEmpty(dialerUpdateForm.landlineNo)) && !isInteger(dialerUpdateForm.landlineNo))
		{
			alert("Please enter valid landline number.");
			return false;
		}
/*	else if(isEmpty(dialerUpdateForm.contactNo))
	{
	 	alert("Please enter Mobile No.");
	 	dialerUpdateForm.contactNo.select();
		return false;	
	}*/

		else if(dialerUpdateForm.alternateContactNo && isEmpty(dialerUpdateForm.alternateContactNo))
		{
		 	alert("Please enter alternate Contact No.");
		 	document.forms[0].alternateContactNo.select();
			return false;	
		}
/*	else if(dialerUpdateForm.contactNo.value.substring(0,1)== '0')
		{
		alert("Mobile No. Can't start with Zero!");
		return false;
		}*/
		else if(dialerUpdateForm.alternateContactNo && dialerUpdateForm.alternateContactNo.value.substring(0,1)== '0')
		{
			alert("Alternate Mobile No. Can't start with Zero!");
			return false;
		}
/*		else if(dialerUpdateForm.contactNo.value == 0)
		{
		alert("Please enter valid 10 digit Alternate Mobile No.");
		return false;
		}
		else if(dialerUpdateForm.alternateContactNo.value == dialerUpdateForm.contactNo.value)
		{
		alert("Contect number and alternate contact number should not be same.");
		return false;
		}*/
		else if(dialerUpdateForm.alternateContactNo && dialerUpdateForm.alternateContactNo.value == 0)
		{
			alert("Please enter valid 10 digit Alternate Mobile No.");
			return false;
		}
		else if(dialerUpdateForm.alternateContactNo && !isEmpty(dialerUpdateForm.alternateContactNo))
		{
			if(dialerUpdateForm.alternateContactNo.value.length <10 || !isInteger(dialerUpdateForm.alternateContactNo) )
			{
				alert("Please enter valid 10 digit alternate mobile No.");
				dialerUpdateForm.alternateContactNo.select();
				return false;
			}
		}
		else if(dialerUpdateForm.contactNo.value.length < 10 || !isInteger(dialerUpdateForm.contactNo) )
		{
/*		  alert("Please enter valid 10 digit Mobile No.");
		  dialerUpdateForm.contactNo.select();
		  return false;*/
		}
		var chks = dialerUpdateForm.productIds;
		//alert("stop #9");
		if (chks)
		{
			//checkbox(s) exists
			var checked = false;
			if (chks.length)
			{ //multiple checkboxes
				var len = chks.length;
				for (var i=0; i<len; i++)
				{
					if (chks[i].checked)
					{
						checked = true;
						break;
					}
				}
			}
			else
			{
				//single checkbox only
				checked = chks.checked;
			}
			if (!checked)
			{
				alert("Please Check atleast one Product.");
				return false;
			}
		}	
		if(dialerUpdateForm.circleId.value == "")
		{
			//alert(dialerUpdateForm.productId.value);
		 	alert("Please select Circle.");
			return false;
		}
		if (dialerUpdateForm.cityCode.value == "")
		{
		 	alert("Please select City");
			return false;	
		}	
		if((dialerUpdateForm.address1.value==""))
		{
		 	alert("Please enter Address");
		 	dialerUpdateForm.address1.focus();
		 	return false;
		}	
		if(isAddressMandatory)
		{
			if(isEmpty(dialerUpdateForm.address1))
			{
			 	alert("Please enter Address");
			 	dialerUpdateForm.address1.focus();
			 	return false;
			}
		}
	
		if (isPINMandatory)
		{
			if(dialerUpdateForm.pinCode.value == "")
			{
			 	alert("Please enter PIN Code");
		 		dialerUpdateForm.pinCode.focus();
				return false;	
			}
			else
			{
				var PinCode = dialerUpdateForm.pinCode.value;
		 		var circleID = document.forms[1].circleId;
		 		var circleMstriD = circleID.options[circleID.options.selectedIndex].value.split("#")[2];
		 		if (PinCode != "")
				{
					var leadId = trimAll(dialerSearchForm.leadId.value);
					getDataForPinCode(PinCode,circleMstriD);
				}
				if (pinFlag == "false")
				{
					alert("Not a valid PIN code");
					dialerUpdateForm.pinCode.value = "";
					dialerUpdateForm.pinCode.focus();
					return false;
				}
			}
		}
	
	
	if(dialerUpdateForm.leadStatusId.disabled)
	{
		dialerUpdateForm.leadStatusId.value="";
		return true;	
	}
	else 
	{
	if(dialerUpdateForm.allStatusId.value != "")
	{
	}
	else
	{
	if (dialerUpdateForm.leadStatusId.value == "" )
	{
	
	alert("Please select Lead Status.");	
	return false;	
	}
	}
	}
	if(dialerUpdateForm.alternateContactNo && !isEmpty(dialerUpdateForm.alternateContactNo))
	{
		if(dialerUpdateForm.alternateContactNo.value.length <6 || !isInteger(dialerUpdateForm.alternateContactNo) )
		{
			alert("Please enter valid 10 digit Alternate Mobile No.");
			dialerUpdateForm.alternateContactNo.select();
			return false;
		}
	}
	if (dialerUpdateForm.email.value != "")
	{
		if(!isEmailAddress(dialerUpdateForm.email))
		{
			alert("Please enter valid E-mail Id.");
			dialerUpdateForm.email.select();
			return false;
		}
	}
	if (isRemarksMandatory)
	{
		if(dialerUpdateForm.remarks.value == "")
		{
		 	alert("Please enter Remarks.");
			return false;	
		}	
	}
	if(dialerUpdateForm.remarks.value.length >1000)
	{
		alert("Remarks can not be grater than 1000"); 
		return false;
	}
	//alert("stop #11");
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1;
		var yyyy = today.getFullYear();
		var hh = today.getHours();
		var min = today.getMinutes();
		
		var productFlag='<%=request.getAttribute("productFlag")%>';
		//alert("stop #12");
		if (dd<10){
			dd = '0' + dd;
		}
		//alert("stop #13");
		if (mm<10)
		{
			mm = '0'+mm;
		}
		//alert("stop #14");
		var curr_dt = dd + '-' + mm + '-' + yyyy;
		//alert("stop #15");
		
		if(productFlag==true){
		var formDate = dialerUpdateForm.appointmentDate.value;
		//alert("stop #16");
		//alert(dialerUpdateForm.appointmentEndDate);
			var formDate1 = dialerUpdateForm.appointmentEndDate.value;
			var endHour = dialerUpdateForm.appointmentEndHour.value;
			var endMinute = dialerUpdateForm.appointmentEndMinute.value;
		//alert("stop #17"); 
		var startHour = dialerUpdateForm.appointmentHour.value;
		//alert("stop #18"); 
		var startMinute = dialerUpdateForm.appointmentMinute.value;
		//alert("stop #19");
		 appointmentStart=formDate+" "+startHour+":"+startMinute;
		//alert("stop #20");
		 appointmentEnd=formDate1+" "+endHour+":"+endMinute;
		///alert("stop #21");
		//alert(appointmentEnd);
/* 	 if(dialerUpdateForm.appointmentDate.value != "")
	 {
	 	var formDate  = dialerUpdateForm.appointmentDate.value;
		
        if(process(formDate) < process(curr_dt))
		{
			alert("Appointment Date cannot be a past Date.");
			return false;			
		}
		
		if(dialerUpdateForm.appointmentMinute.value != "" && dialerUpdateForm.appointmentHour.value == "")
			{
				alert("Please select Appointment Hour.");
				return false;			
			}
			
			
		if(dialerUpdateForm.appointmentDate.value == curr_dt)
		{
		    //alert(dialerUpdateForm.appointmentHour.value+" , hh : "+hh);
			
			if(dialerUpdateForm.appointmentHour.value != "")
			{
			   if(dialerUpdateForm.appointmentHour.value < hh)
			   {
				alert("Appointment Hour cannot be a past time.");
				return false;
				}			
			}
			//alert(dialerUpdateForm.appointmentMinute.value+" , min : "+min);
			
			if(dialerUpdateForm.appointmentMinute.value != "")
			{
				if(dialerUpdateForm.appointmentHour.value == hh )
				{
				 	if(dialerUpdateForm.appointmentMinute.value < min)
			    	{
					alert("Appointment Minute cannot be a past time.");
					return false;
					}
				}			
			}
		}
	 }	  
	 
	 
	 
	 */
	 
	//alert("stop #22");

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
			
		
      //if start date is equal to current date:			
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
		}
	//// alert("yrururuururu");
    return true;
}
var retVal;
function submitForm()
 {    
 //alert('here');

  if(validate())
   		
   		{
 	//alert("hiiiiiiiiiijuejei");
 	//retVal = confirm("Do you want to crossSell for this customer ?");
 	var el = document.getElementById("crossSale");
		el.style.visibility ="visible"; 
  	
  //	alert("hiiiiiiiiii");
   /* 
     alert("inside validate");
    //dialerUpdateForm.methodName.value ="updateLeadDetailsDialler"; 
    dialerUpdateForm.methodName.value = "updateLeadDetailsDialler" ; 
  	//window.confirm("do you want sssss");
   	dialerUpdateForm.submit();   
   	return true;	
   		}    */
   		
   		
	
   
  }
 }

 
 
 
 function clearForm()
 {
    dialerUpdateForm.reset();
	
    return true;
  }
 function cleanTime()
 {

 	dialerUpdateForm.appointmentDate.value="";
	dialerUpdateForm.appointmentHour.value="";
	dialerUpdateForm.appointmentMinute.value="";
 }  

function searchLead()
{ 
    var leadId = trimAll(dialerSearchForm.leadId.value);
    dialerSearchForm.leadId.value = leadId;
    
    if(leadId==""  )
	{	
		alert("Please enter Lead Id.");
		dialerSearchForm.leadId.focus();
		return false;
	}
	else
	{
		if(leadId !="" )
		{	
		   if( leadId.length <14 || !isInteger(dialerSearchForm.leadId) )
			{
			  alert("Please enter 14 digit numeric Lead Id.");
			  dialerSearchForm.leadId.select();
			  return false;
			}
		}
	
		dialerSearchForm.methodName.value="searchLeadDialler";
		dialerSearchForm.submit();
		return true;
	}
	return false;	
}

function submitSearch(event) {
    if (event.keyCode == 13)
    {
      return searchLead();        
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
			var selectObj = document.forms[1].cityCode;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select City", "");
			for (var i = 0; i < optionValues.length; i++)
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
		}
	}
	
function getcityForCircle()
	{
	//	alert(document.forms[1].cityCode);
		removeAllOptions(document.forms[1].cityCode,new Option("Select city",""));
		removeAllOptions(document.forms[1].cityZoneCode,new Option("Select cityZone",""));
		document.forms[1].pinCode.value="";
		//removeAllOptions(document.leadRegistrationFormBean.pinCode,new Option("Select PIN","-1"));
		var circleID = document.forms[1].circleId;
			//alert(circleID);
			//alert(circleID.options[circleID.options.selectedIndex].value);
		var circleMstriD = circleID.options[circleID.options.selectedIndex].value.split("#")[2];// changed done by pratap on 28-11-13
		//alert(circleMstriD);
		var data = "methodName=getCityOnCircleChange&circleMstriD=" + circleMstriD;
		doAjax("leadRegistration.do", "GET", false, getCityOnCircleChange, data);
	}

	
function getCityZoneOnCityChange()
	{

		removeAllOptions(document.forms[1].cityZoneCode,new Option("Select CityZone",""));
		//removeAllOptions(document.leadRegistrationFormBean.pinCode,new Option("Select PIN","-1"));
		var cityCode = document.forms[1].cityCode;
		document.forms[1].pinCode.value="";
		//alert(cityCode);
		var citycode = cityCode.options[cityCode.options.selectedIndex].value.split("#")[1];
		//alert("city code  :"+citycode);
		//var data = "methodName=getCityOnCircleChange&circleID=" + circleiD;
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
			var selectObj = document.forms[1].cityZoneCode;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select cityzone", "");
			for (var i = 0; i < optionValues.length; i++)
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
		}
	}
	
		
var getZoneOnCityChange = function()
	{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			optionValues = xmldoc.getElementsByTagName("zoneOption");
			var selectObj = dialerUpdateForm.zoneCode;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select", "");
			for (var i = 0; i < optionValues.length; i++)
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
			optionValues1 = xmldoc.getElementsByTagName("pinCodeOption");
			var selectObj2 = dialerUpdateForm.pinCode;
			selectObj2.options.length = 0;
			selectObj2.options[selectObj2.options.length] = new Option("Select PIN", "");
			for (var j = 0; j < optionValues1.length; j++)
				selectObj2.options[selectObj2.options.length] = new Option(optionValues1[j].getAttribute("text"), optionValues1[j].getAttribute("value"));
	
		
		}
	}
	
function getZonePinCodeForCity()
	{
		removeAllOptions(dialerUpdateForm.pinCode);
		removeAllOptions(dialerUpdateForm.zoneCode);
		var cityCode = dialerUpdateForm.cityCode;
		var CityCode = cityCode.options[cityCode.options.selectedIndex].value;
		var data = "methodName=getZoneOnCityChange&CityCode=" + CityCode;
		doAjax("leadRegistration.do", "GET", false, getZoneOnCityChange, data);
	}

function markMandatory()
{
	var makeMandatory = false;
	var oForm = dialerUpdateForm;
	var statusID = oForm.leadStatusId[oForm.leadStatusId.selectedIndex].value;
	var substatusID = oForm.allStatusId[oForm.allStatusId.selectedIndex].value;
	var status = statusID.split("#");
	
		if((statusID !="" ) || (substatusID != ""))
	{
		makeMandatory = true;
		isRemarksMandatory = true;
	}
	
	 if(makeMandatory)
   	   {
   	   		 document.getElementById("mandatoryRemarks").innerHTML = "<font color=red>*</font>";
   	   }
   	   else
   	   {
   	   	 document.getElementById("mandatoryRemarks").innerHTML = "";
   	   	 isRemarksMandatory = false;
   	   }
}

function getCircleForProduct(obj)
	{
	//alert("getCircleForProduct")
		removeAllOptions(document.forms[1].circleId,new Option("Select Circle",""));
		removeAllOptions(document.forms[1].cityCode,new Option("Select City",""));
		removeAllOptions(document.forms[1].cityZoneCode,new Option("Select CityZone",""));
		document.forms[1].zoneCode.value = "";
		//removeAllOptions(document.leadRegistrationFormBean.pinCode,new Option("Select PIN","-1"));
		document.forms[1].pinCode.value="";
		/**if(undefined != document.forms[1].productIds)
	  	 {
		    var makeMandatory = false;
		    var productIdAndProductLobId;
		    var productLobId;
		    var productId;
		    for(ii=0; ii < document.forms[1].productIds.length; ii++)
	      	 {
	        if (document.forms[1].productIds[ii].checked)
	        {
	        productId=document.forms[1].productIds[ii].value;
	        //alert()
	       // alert(document.forms[1].productIds[ii].value);
	       // productId= productIdAndProductLobId.split("#")[0];
	       // productLobId = productIdAndProductLobId.split("#")[1];
	        }
   	   }
    }*/



			


		/*	
			for(ii=0; ii < dialerUpdateForm.productIds.length; ii++)
       {
	        if (obj==null)
	        {
	        
	          for(jj=0; jj < telemediaProductArray.length; jj++)
		       {
		        if (dialerUpdateForm.productIds[ii].value == telemediaProductArray[jj])
			        {
			        document.getElementById("rsuLine").style.display= "block";
			document.getElementById("rsuLabel").style.display= "block";
			document.getElementById("rsuField").style.display= "block";
			           
			        }
		   	   }  
		   	    for(kk=0; kk < dthProductArray.length; kk++)
		       {
			        if (dialerUpdateForm.productIds[ii].value == dthProductArray[kk])
			        {
			          document.getElementById("rsuLine").style.display= "none";
			document.getElementById("rsuLabel").style.display= "none";
			document.getElementById("rsuField").style.display= "none";			          
			        }
		   	   } 
		   	   
	        }
	     //From here Added by srrikant for Cross sale product,first time yes   
	      if(obj!=null&&obj.value!=0)
	      {
	       //alert("Second time");
	          for(ll=0;ll<telemediaProductArray.length;ll++)
	          {
	          	if(obj==telemediaProductArray[ll])
	          	{
	          		document.getElementById("rsuLine").style.display= "block";
			document.getElementById("rsuLabel").style.display= "block";
			document.getElementById("rsuField").style.display= "block";
			           
			           //alert("makePINMandatory="+makePINMandatory);
	          	}
	          }
	          
	          }
	          }  
*/




    	var data = "methodName=getCircleOnProductChange&productId=" + obj;
    	//alert("data :"+data);
		doAjax("leadRegistration.do", "GET", false, getCircleOnProductChange, data);
	}
	
	var getCircleOnProductChange = function()
	{
	//alert("in  getCircleOnProductChange :://////::");
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			optionValues = xmldoc.getElementsByTagName("option");
			//alert("option  :"+optionValues);
			var selectObj = document.forms[1].circleId;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select Circle", "");
			for (var i = 0; i < optionValues.length; i++){
		//	alert("text  :"+optionValues[i].getAttribute("text"));
			//alert("value  :"+optionValues[i].getAttribute("value"));
			
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
				}
		}
	}
// added by pratap 3 dec 2013

//added by Bhaskar

function cleanSelectBox(selectBox)
  	{
 
  	var obj = document.getElementById(selectBox);
    if (obj == null) return;
	if (obj.options == null) return;
	while (obj.options.length > 0) {
		obj.remove(0);
	}
  }

 function getLeadSubStatusDropDown()
{
	
	var leadStatus  = document.forms[1].leadStatusId;
	var leadId=document.forms[0].leadId.value;
	
		var leadStatusId=leadStatus.options[leadStatus.options.selectedIndex].value.split("#")[1];
	
	   
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }  
	    req.onreadystatechange = returnJsonLeadSubStatus;
	    
	    var url= 'ajaxSupport.do?mt=getLeadSubSubStatus1&leadStatusId='+leadStatusId+"&leadId="+leadId; 
	    req.open("GET", url, true);
	    req.send(null);
}

// called on populate Lead Sub Status List  

    function returnJsonLeadSubStatus() {
   
   
    if (req.readyState == 4) {
        if (req.status == 200) {  
           
            var json = eval('(' + req.responseText + ')');    
            
			var elements = json.elements;
			
//			cleanSelectBox("leadSubSubStatusid");
		removeAllOptions(document.forms[1].leadSubSubStatusid,new Option("Select Sub Status","-1"));
		
		if (elements.length!= -1)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	     }    
	                  	
	            var selectDropDown = document.forms[1].leadSubSubStatusid;	
	                        	
	            cleanSelectBox("leadSubSubStatusid");	  	                      	
	           /* var opt1 = "Select";
				addOption("",opt1, selectDropDown);   		*/
				           
				for (var i = 0; i < elements.length; i++) {
				
		            addOption( elements[i].leadSubSubStatusId,elements[i].leadSubStatus, selectDropDown);
		    
		        }	
		        return true;	        
		    }	   
		     }	
		     }
        }   


//ended by Bhaskar


function confirmYesNo(confirm)
{
if(confirm == true)
	{
	
		changeLeadStatus("&nonServiceAble=true");
	}
	else
	{
		dialerUpdateForm.pinCode.focus();
	}
	pinFlag=" ";
	var el = document.getElementById("confirm");
	el.style.visibility ="hidden";
	pinFlag=" ";
}

function getRsuForCityZoneCode()
	{
	
		var circleID = dialerUpdateForm.circleId;
		var circleMstriD = circleID.options[circleID.options.selectedIndex].value.split("#")[2];// changed done by pratap on 28-11-13
		var cityCode = dialerUpdateForm.cityCode;
		var CityCode = cityCode.options[cityCode.options.selectedIndex].value.split("#")[1];
		
		cityZoneCode = dialerUpdateForm.cityZoneCode;
		cityzonecode = cityZoneCode.options[cityZoneCode.options.selectedIndex].value;
		
		var data = "methodName=getRsuForCityZoneCodeChange&circleMstriD=" + circleMstriD + "&CityCode="+ CityCode +"&cityzonecode="+cityzonecode;
     	doAjax("leadRegistration.do", "GET", false, getRsuForCityZoneCodeChange, data);
  }
	var getRsuForCityZoneCodeChange = function()
	{
	
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = dialerUpdateForm.rsuCode;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select Rsu", "");
			for (var i = 0; i < optionValues.length; i++)
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"));
		}
	}
function onCityZoneChange()
{
document.forms[1].pinCode.value="";
}


function crossSaleFirst(confirm)
{
if(confirm == true)
	{
		flag12="yes";
		return true;
	}
	else
	{
	    flag12="no";
	    document.getElementById('productIds').value='0';
		retVal=false;
		dialerUpdateForm.methodName.value = "updateLeadDetailsDialler" ; 
  	  	dialerUpdateForm.flag.value=retVal;
  	  	retVal=" ";
  		dialerUpdateForm.submit(); 
	 	 return false;
	}
	var el = document.getElementById("confirm");
	el.style.visibility ="hidden";
}

function crossSaleYesNo(confirm)
{

if(confirm == true )  // first time yes second time yes----create new lead
	{
	
		retVal=true;
		dialerUpdateForm.methodName.value = "updateLeadDetailsDialler" ; 
  	  	dialerUpdateForm.flag.value=retVal;
  	  	dialerUpdateForm.submit();
		  return true;
	}
	else if(confirm != true && flag12=="yes")  //first time yes second time no----dont update the lead location
	{
	
		document.getElementById('productIds').value='0';
		var finalFlag="yes";
		retVal=false;
		dialerUpdateForm.methodName.value = "updateLeadDetailsDialler" ; 
  	  	dialerUpdateForm.flag.value=retVal;
  	  	dialerUpdateForm.flag12.value=finalFlag;
  	  	retVal=" ";
  	  	dialerUpdateForm.submit(); 
      	return false;
	}
	
}


 function submitFeasibilForm() {
 		if(isEmpty(dialerUpdateForm.address1))
		{
		 	alert("Please enter Address");
		 	dialerUpdateForm.address1.focus();
		 	return false;
		}else {
			document.getElementById('addrId').value =dialerUpdateForm.address1.value;
			document.getElementById('gisFormId').submit(); 
		}
 }
 
 
 ///////%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  var count_circle = 1;
  var count_relation = 1;
  var count_docu = 1;
 function removeFirstChildCircle(){
 //alert("in removeFirstChildCircle");

 var sel = document.getElementById("previouscircle");
 if(count_circle <=1){
 sel.removeChild(sel.childNodes[0]);
 count_circle = count_circle +1;
 }
 }
 
  function removeFirstChildRelation(){
 //alert("in removeFirstChildRelation");
 var sel = document.getElementById("rlnName");
 if(count_relation <=1){
 sel.removeChild(sel.childNodes[0]);
 count_relation = count_circle +1;
 }
 }
 
   function removeFirstChildDocument(){
 //alert("in removeFirstChildDocument");
 var sel = document.getElementById("docCollectedFlag");
 if(count_docu <=1){
 sel.removeChild(sel.childNodes[0]);
 count_docu = count_circle +1;
 }
}
--></script>

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
	width: 100%;
	height: 100%;
	text-align: center;
	z-index: 1000;
}

#confirm div {
	width: 250px;
	margin: 100px auto;
	background-color: lightblue;
	border: 1px solid #000;
	padding: 15px;
	text-align: center;
}

#crossSale {
	visibility: hidden;
	position: fixed;
	left: 0px;
	top: 0px;
	width: 100%;
	height: 100%;
	text-align: center;
	z-index: 1000;
}

#crossSale div {
	width: 250px;
	margin: 100px auto;
	background-color: lightblue;
	border: 1px solid #000;
	padding: 15px;
	text-align: center;
}
</style>


<style type="text/css">
.row {
	vertical-align: top;
	height: auto !important;
}

.list {
	display: none;
}

.show {
	display: none;
}

.hide:target+.show {
	display: inline;
}

.hide:target {
	display: none;
}

.hide:target  ~ .list {
	display: inline;
}

@media print {
	.hide,.show {
		display: none;
	}
}
</style>


<html:form action="/leadRegistration">
	<input type="hidden" name="methodName" value="searchLeadDialler" />
	<div class="box2">
	<div class="content-upload">
	<h1>Lead Search</h1>
	<center><strong><FONT color="red"><html:errors /></FONT>
	<FONT color="green"><html:messages id="msg" message="true">
		<bean:write name="msg" />
	</html:messages></FONT></strong></center>
	<ul class="list2 form1">
		<li class="clearfix" style="margin-top: 5px;">
		<p class="clearfix fll width235 " style="margin-left: 50px;"><span
			class="text2 fll margin-r10"><strong>Lead Id.</strong> </span> <html:text
			style="width:150px;" tabindex="1" property="leadId" styleId="keyword"
			name="leadRegistrationFormBean" maxlength="14"
			onkeypress="return submitSearch(event);"
			style="height:18px; width: 150px; border-color: #000000; border-width:1px; padding-left:3px; padding-top:3px; font-size:14px; font-weight:bold; color:#2f2f2f;" />
		</p>
		<img src="common/images/go.jpg" onclick="return searchLead();"
			width="37" height="25" border="0" /></li>
	</ul>
	</div>
	<br>
	</div>
</html:form>

<html:form action="/leadRegistration">
	<input type="hidden" name="methodName" value="updateLeadDetailsDialler" />
	<html:hidden property="leadProspectId" />
	<html:hidden property="prospectId" />
	<html:hidden property="sourceId" />
	<html:hidden property="zoneCode" />
	<html:hidden property="productId" />
	<html:hidden property="leadId" />
	<html:hidden property="flag" styleId="flag" />
	<html:hidden property="flag12" value="no" />


	<html:hidden property="isSecondCall" />

	<logic:notEqual name="leadRegistrationFormBean" property="initStatus"
		value="true">

		<logic:notEmpty name="LEAD_DETAILS">
			<bean:define id="leadList" name="LEAD_DETAILS"
				type="java.util.ArrayList" scope="request" />
		</logic:notEmpty>
		<logic:empty name="leadList">
			<FONT color="red" style="margin-left: 15px;"><bean:message
				key="lead.not.found" /></FONT>
		</logic:empty>

		<logic:notEmpty name="leadList">
			
			<html:hidden property="methodName" value="" />
			<html:hidden property="createdBy" styleId="createdBy" />
			<div class="box2">
			<div class="content-upload">
			<h1>Lead Details</h1>
			<logic:present name="leadDetailsPopUpFlag">
				<logic:equal name="leadDetailsPopUpFlag" value="true">
				<script>
					//alert("hii");
					var lead=document.getElementById("keyword").value;
					//alert(lead);
					var url="leadRegistration.do?methodName=getLeadDetailsByContact&leadId="+lead;
					window.open(url,"PopUp","width=850,height=400,scrollbars=yes,toolbar=no");
				</script>
				</logic:equal>
			
				
			</logic:present>
			<center><strong><FONT color="red"><html:errors /></FONT>
			<FONT color="green"><html:messages id="msg" message="true">
				<bean:write name="msg" />
			</html:messages></FONT></strong></center>

			<%
				if (null != request.getAttribute("insertStatus")) {
								String insertStatus = request.getAttribute(
										"insertStatus").toString();
								if (!"null".equals(insertStatus)) {
									out.print(insertStatus);
								}
							}
			%>

			<ul class="list2 form1">
				<li class="clearfix"><span class="text2 fll width160"><strong>Customer
				Name<font color=red>*</font> </strong> </span>
				<p class="clearfix fll margin-r20"><span class="textbox6">
				<span class="textbox6-inner"> <html:text
					property="customerName" styleClass="textbox10" maxlength="50" /></span></span></p>
				<%
					if ("N".equalsIgnoreCase(request.getAttribute(
										"mobile_flag").toString())) {
				%><span>
				<%
					} else {
				%> <span style="display: none">
				<%
					}
				%> <span class="text2 fll width120"><strong>Mobile
				No .<font color=red>*</font></strong> </span>
				<p class="clearfix fll"><span class="textbox6"> <span
					class="textbox6-inner"> <html:text property="contactNo"
					styleClass="textbox10" maxlength="10" readonly="true" /></span></span>
				</span>
				</p></li>


				<!-- <li class="clearfix"><span class="text2 fll width160"><strong>Lob</strong>
				</span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="productLobName" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong>Product</strong></span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="productName" name="leadRegistrationFormBean" /></STRONG></p>
				
				</li> -->
				<li class="clearfix"><span class="text2 fll width100"><strong>Lob</strong>
				</span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 150px"><bean:write
					property="productLobName" name="leadRegistrationFormBean" /></p>
				<span class="text2 fll width100"><strong>Product</strong></span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 150px"><bean:write
					property="productName" name="leadRegistrationFormBean" /></p>
				<!-- <span class="text2 fll width100"><strong>Lead</strong></span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 3px; width: 50px"><STRONG> <bean:write
					property="productName" name="leadRegistrationFormBean" /></STRONG></p> -->



				<span class="text2 fll width100"><strong>Lead
				Priority</strong></span>
				<p class="clearfix fll" align="justify" style="margin-top: 5px;">
				<STRONG> <logic:equal value="HIGH" property="leadPriority"
					name="leadRegistrationFormBean">
					<font color="red"> <bean:write property="leadPriority"
						name="leadRegistrationFormBean" /> </font>
				</logic:equal> <logic:equal value="MEDIUM" property="leadPriority"
					name="leadRegistrationFormBean">
					<font color="blue"> <bean:write property="leadPriority"
						name="leadRegistrationFormBean" /> </font>
				</logic:equal> <logic:equal value="LOW" property="leadPriority"
					name="leadRegistrationFormBean">
					<font color="green"> <bean:write property="leadPriority"
						name="leadRegistrationFormBean" /> </font>
				</logic:equal> </STRONG></p>
				</li>

				<li><span id="SelectedProductId"><strong
					style="font-size: 12px; color: #000;">Selected Product</strong>&nbsp;&nbsp;&nbsp;&nbsp;<span
					id="checkedProductId"></span> <input type="hidden"
					name="productIds" id="productIds" value="" class="SelectedProduct">

				</span></li>
				<div id="allProducts" style="display: none">
				<li class="clearfix"><strong>Product<font color=red>*</font>
				</strong>

				<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Mobility</font></legend> <bean:define id="productList"
					name="leadRegistrationFormBean" property="mobilityProductList"
					type="java.util.ArrayList" /> <bean:define
					id="restrictedProductList" name="leadRegistrationFormBean"
					property="restrictedProductList" type="java.util.ArrayList" /> <logic:notEmpty
					name="productList">
					<table>
						<tr>
							<logic:iterate name="productList" id="product" indexId="m"
								type="com.ibm.lms.dto.ProductDTO">
								<script type="text/javascript"> var counter=<%=(m.intValue() + 1)%></script>
								<bean:define id="leadRegFormBean"
									name="leadRegistrationFormBean" property="productIdsOpen"
									type="java.lang.String[]" />
								<%
									boolean disableStatus = true;

																if (request.getAttribute("fromUpdate") != null) {
																	if (request.getAttribute(
																			"fromUpdate").equals(
																			"fromUpdate")) {

																		String[] productIdsOpen = new String[0];
																		productIdsOpen = leadRegFormBean;

																		disableStatus = false;

																		for (int ij = 0; ij < productIdsOpen.length; ij++) {
																			if (productIdsOpen[ij]
																					.equals(product
																							.getProductId()
																							+ ""))
																				disableStatus = true;
																		}
																		//product.setProductId(0);

																	}
																}
								%>

								<td class="width160"><logic:notEmpty
									name="restrictedProductList">
									<logic:iterate name="restrictedProductList"
										id="restrictedProduct" indexId="m" type="java.lang.Integer">
										<%
											if (restrictedProduct
																								.intValue() != product
																								.getProductId()) {
										%>
										<html:radio property="productIds"
											value='<%=product
																					.getProductId()
																			+ ""%>'
											disabled='<%=disableStatus%>'
											onclick="getCircleForProduct(this.value);makeAddressFieldsMandatory(this.value)"
											styleId="productIds" title='<%=product
																					.getProductName()%>' />
										<%=product
																					.getProductName()%>
										<%
											}
										%>
									</logic:iterate>
								</logic:notEmpty></td>

								<%
									if (((m.intValue() + 1) % 4) == 0) {
								%>
							
						</tr>
						<tr>
							<%
								}
							%>
							</logic:iterate>
						</tr>
					</table>
				</logic:notEmpty></fieldset>


				<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Telemedia</font></legend> <bean:define id="productList"
					name="leadRegistrationFormBean" property="telemediaProductList"
					type="java.util.ArrayList" /> <bean:define
					id="restrictedProductList" name="leadRegistrationFormBean"
					property="restrictedProductList" type="java.util.ArrayList" /> <logic:notEmpty
					name="productList">
					<table>
						<tr>
							<logic:iterate name="productList" id="product" indexId="j"
								type="com.ibm.lms.dto.ProductDTO">
								<script type="text/javascript"> var counter=<%=(j.intValue() + 1)%></script>
								<bean:define id="leadRegFormBean"
									name="leadRegistrationFormBean" property="productIdsOpen"
									type="java.lang.String[]" />
								<%
									boolean disableStatus = true;

																if (request.getAttribute("fromUpdate") != null) {
																	if (request.getAttribute(
																			"fromUpdate").equals(
																			"fromUpdate")) {

																		String[] productIdsOpen = new String[0];
																		productIdsOpen = leadRegFormBean;

																		disableStatus = false;

																		for (int ij = 0; ij < productIdsOpen.length; ij++) {
																			if (productIdsOpen[ij]
																					.equals(product
																							.getProductId()
																							+ ""))
																				disableStatus = true;
																		}
																		//product.setProductId(0);

																	}
																}
								%>

								<td class="width160"><logic:notEmpty
									name="restrictedProductList">
									<logic:iterate name="restrictedProductList"
										id="restrictedProduct" indexId="j" type="java.lang.Integer">
										<%
											if (restrictedProduct
																								.intValue() != product
																								.getProductId()) {
										%>
										<html:radio property="productIds"
											value='<%=product
																					.getProductId()
																			+ ""%>'
											disabled='<%=disableStatus%>'
											onclick="getCircleForProduct(this.value);makeAddressFieldsMandatory(this.value)"
											styleId="productIds" title='<%=product
																					.getProductName()%>' />
										<%=product
																					.getProductName()%>
										<%
											}
										%>
									</logic:iterate>
								</logic:notEmpty></td>

								<%
									if (((j.intValue() + 1) % 4) == 0) {
								%>
							
						</tr>
						<tr>
							<%
								}
							%>
							</logic:iterate>
						</tr>
					</table>
				</logic:notEmpty></fieldset>

				<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Others</font></legend> <bean:define id="productList"
					name="leadRegistrationFormBean" property="otherProductsList"
					type="java.util.ArrayList" /> <bean:define
					id="restrictedProductList" name="leadRegistrationFormBean"
					property="restrictedProductList" type="java.util.ArrayList" /> <logic:notEmpty
					name="productList">
					<table>
						<tr>
							<logic:iterate name="productList" id="product" indexId="k"
								type="com.ibm.lms.dto.ProductDTO">
								<script type="text/javascript"> var counter=<%=(k.intValue() + 1)%></script>
								<bean:define id="leadRegFormBean"
									name="leadRegistrationFormBean" property="productIdsOpen"
									type="java.lang.String[]" />
								<%
									boolean disableStatus = true;

																if (request.getAttribute("fromUpdate") != null) {
																	if (request.getAttribute(
																			"fromUpdate").equals(
																			"fromUpdate")) {

																		String[] productIdsOpen = new String[0];
																		productIdsOpen = leadRegFormBean;

																		disableStatus = false;

																		for (int ij = 0; ij < productIdsOpen.length; ij++) {
																			if (productIdsOpen[ij]
																					.equals(product
																							.getProductId()
																							+ ""))
																				disableStatus = true;
																		}
																		//product.setProductId(0);

																	}
																}
								%>

								<td class="width160"><logic:notEmpty
									name="restrictedProductList">
									<logic:iterate name="restrictedProductList"
										id="restrictedProduct" indexId="k" type="java.lang.Integer">
										<%
											if (restrictedProduct
																								.intValue() != product
																								.getProductId()) {
										%>
										<html:radio property="productIds"
											value='<%=product
																					.getProductId()
																			+ ""%>'
											disabled='<%=disableStatus%>'
											onclick="getCircleForProduct(this.value);makeAddressFieldsMandatory(this.value)"
											styleId="productIds" title='<%=product
																					.getProductName()%>' />
										<%=product
																					.getProductName()%>
										<%
											}
										%>
									</logic:iterate>
								</logic:notEmpty></td>

								<%
									if (((k.intValue() + 1) % 4) == 0) {
								%>
							
						</tr>
						<tr>
							<%
								}
							%>
							</logic:iterate>
						</tr>
					</table>
				</logic:notEmpty></fieldset>
				</div>

				<bean:define id="telemediaProductList"
					name="leadRegistrationFormBean" property="telemediaProductList"
					type="java.util.ArrayList" />
				<logic:notEmpty name="telemediaProductList">


					<script type="text/javascript"> var telemediaProductArray = new Array(); </script>

					<logic:iterate name="telemediaProductList" id="telemediaProduct"
						indexId="i" type="com.ibm.lms.dto.ProductDTO">
						<script type="text/javascript"> telemediaProductArray[<%=i%>] = <%=telemediaProduct
														.getProductId()%></script>
					</logic:iterate>
				</logic:notEmpty>
				<bean:define id="dthProductList" name="leadRegistrationFormBean"
					property="dthProductList" type="java.util.ArrayList" />
				<logic:notEmpty name="dthProductList">
					<script type="text/javascript"> var dthProductArray = new Array(); </script>

					<logic:iterate name="dthProductList" id="dthProduct" indexId="j"
						type="com.ibm.lms.dto.ProductDTO">
						<script type="text/javascript"> dthProductArray[<%=j%>] = <%=dthProduct.getProductId()%></script>
					</logic:iterate>
				</logic:notEmpty>
				</li>
				<li>
				<div id="confirm">
				<div>
				<p style=""><b>Not a Serviceable pin code, Want to continue.
				?</p>
				<a href='#' value="true" onclick='confirmYesNo(true)'>Yes</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href='#' value="false" onclick='confirmYesNo(false)'>No</b></a></div>
				</div>
				</li>
				<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Change Request Type</font></legend>

				<li class="clearfix alt"><span class="text2 fll width160"><strong>Request
				Type</strong> </span>
				<p class="clearfix fll" style="width: 232px"><html:select
					property="requestType" name="leadRegistrationFormBean"
					styleClass="select1">
					<html:option value="">Select</html:option>
					<logic:notEmpty name="leadRegistrationFormBean"
						property="productList">
						<bean:define id="productTypes" name="leadRegistrationFormBean"
							property="productList" />
						<html:options labelProperty="productName" property="productId"
							collection="productTypes" />
					</logic:notEmpty>
				</html:select></p>

				<!--  <span class="text2 fll width120"><strong>Lead
				Priority</strong></span>
				<p class="clearfix fll" align="justify"	style="margin-top: 5px;">
					<STRONG>
						<logic:equal value="HIGH" property="leadPriority" name="leadRegistrationFormBean" >
							<font color="red"  >
							
								<bean:write	property="leadPriority" name="leadRegistrationFormBean"  />
							
							</font>
						</logic:equal>
						<logic:equal value="MEDIUM" property="leadPriority" name="leadRegistrationFormBean" >
							<font color="blue" >
							
								<bean:write	property="leadPriority" name="leadRegistrationFormBean" />
								
							</font>
						</logic:equal>
						<logic:equal value="LOW" property="leadPriority" name="leadRegistrationFormBean" >
							<font color="green">
						
								<bean:write	property="leadPriority" name="leadRegistrationFormBean"  />
								
							</font>
						</logic:equal>
					</STRONG>
				</p>
				--></li>
				</fieldset>

				<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Location Details</font></legend>
				<li class="clearfix alt"><span class="text2 fll width160"><strong>Circle</strong><font
					color=red>*</font> </span>
				<p class="clearfix fll" style="width: 232px"><html:select property="circleId" name="leadRegistrationFormBean" styleClass="select1" onchange="getcityForCircle()">
					<html:option value="">Select Circle</html:option>
					<logic:notEmpty name="leadRegistrationFormBean" property="circleForProductList">
						<bean:define id="circles" name="leadRegistrationFormBean" property="circleForProductList" />
						<html:options labelProperty="circleName" property="circleIdLobIdCircleMstrId" collection="circles" />
					</logic:notEmpty>
				</html:select></p>
				<span class="text2 fll width120">
				<table cellpadding="0">
					<tr>
						<td><strong>City</strong> <font color="red">*</font></td>
						<td>
						<div id="mandatoryCity"></div>
						</td>
					</tr>
				</table>
				</span>
				<p class="clearfix fll"><html:select property="cityCode"
					name="leadRegistrationFormBean" styleClass="select1"
					onchange="getCityZoneOnCityChange()">
					<html:option value="">Select City</html:option>
					<logic:notEmpty name="leadRegistrationFormBean" property="cityList">
						<bean:define id="cities" name="leadRegistrationFormBean"
							property="cityList" />
						<html:options labelProperty="cityName" property="cityCode"
							collection="cities" />
					</logic:notEmpty>
				</html:select></p>
				</li>


				<li class="clearfix alt"><span class="text2 fll width160"><strong>City
				Zone</strong> </span>
				<p class="clearfix fll" style="width: 232px"><html:select
					styleId="cityZoneCode" property="cityZoneCode"
					name="leadRegistrationFormBean" styleClass="select1"
					onchange="onCityZoneChange();getRsuForCityZoneCode()">
					<html:option value="">Select </html:option>
					<logic:notEmpty name="leadRegistrationFormBean"
						property="cityZoneList">
						<bean:define id="zones" name="leadRegistrationFormBean"
							property="cityZoneList" />
						<html:options labelProperty="cityZoneName" property="cityZoneCode"
							collection="zones" />
					</logic:notEmpty>
				</html:select></p>


				<class="clearfix"><span class="text2 fll width120">
				<table cellpadding="0">
					<tr>
						<td><strong>PIN Code</strong></td>
						<td>
						<div id="mandatoryPIN"></div>
						</td>
					</tr>
				</table>
				</span>
				<p class="clearfix fll" style="width: 208px"><span
					class="textbox6"> <span class="textbox6-inner"> <html:text
					property="pinCode" styleClass="textbox10" maxlength="6" /></span></span></p>
				<li class="clearfix alt" id="rsuLine" style="display: none"><span
					class="text2 fll width160" id="rsuLabel" style="display: none"><strong>RSU
				Code</strong></span>
				<p class="clearfix fll" style="width: 232px" id="rsuField"
					style="display:none"><html:select property="rsuCode"
					name="leadRegistrationFormBean" styleClass="select1">
					<html:option value="">Select RSU</html:option>
					<logic:notEmpty name="leadRegistrationFormBean" property="rsuList">
						<bean:define id="rsus" name="leadRegistrationFormBean"
							property="rsuList" />
						<html:options labelProperty="rsuCode" property="rsuCode"
							collection="rsus" />
					</logic:notEmpty>
				</html:select></p>
				</li>




				<li class="clearfix"><span class="text2 fll width160">
				<table cellpadding="0">

					<tr>
						<td><span class="text2 fll" style="width: 70px;"><strong>Address1</strong><font
							color="red">*</font></span></td>
						<td>
						<div id="mandatoryAddress1"></div>
						</td>

						<td>
						<p class="clearfix fll" align="justify" style="margin-left: 83px; width: 190px;">
						<html:textarea property="address1" onclick="this.style.height='200px';" onblur="this.style.height='';"></html:textarea>
						</td>
						<td><span class="text2 fll width160"><strong>Address2</strong></span></td>
						<td>
						<div id="mandatoryAddress1"></div>

						<p class="clearfix fll" align="justify"
							style="margin-top: 5px; width: 232px"><html:textarea
							property="address2" onclick="this.style.height='200px';"
							onblur="this.style.height='';">
						</html:textarea>
						</td>
					</tr>
				</table>
				</span></li>

				<li class="clearfix alt"><span class="text2 fll width160"><strong>Latitude</strong>
					</span>
					<p class="clearfix fll" align="justify" style="margin-top: 5px; width: 232px"><STRONG> 
					<bean:write property="extraParam3" name="leadRegistrationFormBean" /></STRONG></p>

					<span class="text2 fll width100"><strong>Longitude</strong></span>
					<p class="clearfix fll" align="justify" style="margin-top: 5px; width: 232px"><STRONG> 
					<bean:write property="extraParam4" name="leadRegistrationFormBean" /></STRONG></p>

					</li>

				</fieldset>


				<!-- For Mobility LOB -->

				<div class="row"><logic:equal name="leadRegistrationFormBean"
					property="productLobId" value="1">
					<fieldset class="field_set_main"><legend class="header"><font
						size=2 color=#ff0000>Plan and Transaction Details</font></legend><a
						href="#hide1" class="hide" id="hide1"><font size="4">[+]</font></a><a
						href="#show1" class="show" id="show1"><font size="4">[-]</font></a>
					<div class="list">


					<li class="clearfix"><span class="text2 fll width160"><strong>Request
					Category</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="requestCategory" name="leadRegistrationFormBean" /></p>
					<span class="text2 fll width100"><strong>Plan Id</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="planId" name="leadRegistrationFormBean" /></p>

					</li>

					<li class="clearfix alt"><span class="text2 fll width160"><strong>Plan</strong>
					</span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="plan" name="leadRegistrationFormBean" /></p>
					<span class="text2 fll width100"><strong>Rental</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="rental" name="leadRegistrationFormBean" /></p>

					</li>


					<li class="clearfix"><span class="text2 fll width160"><strong>Freebie
					Count</strong></span>
					</td>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="freebieCount" name="leadRegistrationFormBean" /></p>


					<span class="text2 fll width100"><strong>Booster
					Count</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="boosterCount" name="leadRegistrationFormBean" /></p>

					</li>


					<li class="clearfix alt"><span class="text2 fll width160"><strong>Freebie
					Taken</strong> </span>

					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><textarea
						onclick="this.style.height='200px';"
						onblur="this.style.height='60px';" readonly><bean:write
						property="freebieTaken" name="leadRegistrationFormBean" />
               </textarea></p>
					<!-- <span class="text2 fll width100"><strong>Booster Count</strong></span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><bean:write
					property="boosterCount" name="leadRegistrationFormBean" /></p> -->


					<span class="text2 fll width100"><strong>Booster
					Taken</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><textarea
						onclick="this.style.height='200px';"
						onblur="this.style.height='60px';" readonly><bean:write
						property="boosterTaken" name="leadRegistrationFormBean" />
					</textarea></p>


					</li>
					<li class="clearfix"><span class="text2 fll width160"><strong>Selected
					Postpaid Number</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="allocatedNo" name="leadRegistrationFormBean" /></p>
					<span class="text2 fll width100"><strong>Prepaid
					Number </strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="prepaidNumber" name="leadRegistrationFormBean" /></p>

					</li>

					<li class="clearfix alt"><span class="text2 fll width160"><strong>Online
					CafNo</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="onlineCafNo" name="leadRegistrationFormBean" /></p>
					<span class="text2 fll width100"><strong>Payment
					Amount</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="payment" name="leadRegistrationFormBean" /></p>

					</li>
					<li class="clearfix"><span class="text2 fll width160"><strong>Company</strong>
					</span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="company" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong>Transaction
					Reference Number</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="transactionRefNo" name="leadRegistrationFormBean" /></STRONG></p>


					</li>
					</fieldset>
				</logic:equal></div>
				<!-- Ended By Mobility -->

				<!-- For Telemedia LOB -->

				<div class="row"><logic:equal name="leadRegistrationFormBean"
					property="productLobId" value="2">
					<fieldset class="field_set_main"><legend class="header"><font
						size=2 color=#ff0000>Plan and Transaction Details</font></legend><a
						href="#hide1" class="hide" id="hide1"><font size="4">[+]</font></a><a
						href="#show1" class="show" id="show1"><font size="4">[-]</font></a>
					<div class="list">

					<li class="clearfix"><span class="text2 fll width160"><strong>
					Request Category </strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="requestCategory" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong>Plan Id</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="planId" name="leadRegistrationFormBean" /></p>

					</li>

					<li class="clearfix alt"><span class="text2 fll width160"><strong>Plan</strong>
					</span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="plan" name="leadRegistrationFormBean" /></p>
					<span class="text2 fll width100"><strong>Rental</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="rental" name="leadRegistrationFormBean" /></p>

					</li>



					<li class="clearfix"><span class="text2 fll width160"><strong>Rental
					Type</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="rentalType" name="leadRegistrationFormBean" /></p>
					<span class="text2 fll width100"><strong>Offer</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="offer" name="leadRegistrationFormBean" /></p>

					</li>
					<li class="clearfix alt"><span class="text2 fll width160"><strong>Download
					Limit</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="downloadLimit" name="leadRegistrationFormBean" /></p>
					<span class="text2 fll width160"><strong>Voice
					Benefit</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="voiceBenefit" name="leadRegistrationFormBean" /></p>
					</li>

					<li class="clearfix "><span class="text2 fll width160"><strong>Data
					Quota</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="dataQuota" name="leadRegistrationFormBean" /></p>
					<span class="text2 fll width160"><strong>Benefit</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="benefit" name="leadRegistrationFormBean" /></p>

					</li>

					<li class="clearfix alt"><span class="text2 fll width160"><strong>Package
					Duration</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="pkgDuration" name="leadRegistrationFormBean" /></p>
					<span class="text2 fll width160"><strong>Online
					CafNo</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="onlineCafNo" name="leadRegistrationFormBean" /></p>

					</li>
					<li class="clearfix"><span class="text2 fll width160"><strong>Payment
					Amount </strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="pytAmt" name="leadRegistrationFormBean" /></p>
					<span class="text2 fll width160"><strong>Company</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><bean:write
						property="company" name="leadRegistrationFormBean" /></p>
					</li>
					<li class="clearfix alt"><span class="text2 fll width100"><strong>Transaction
					Reference Number</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="tranRefno" name="leadRegistrationFormBean" /></STRONG></p>

					</li>
					</fieldset>
				</logic:equal></div>
				<!-- Ended By Telemedia -->
				<!-- For DTH LOB -->

				<div class="row"><logic:equal name="leadRegistrationFormBean"
					property="productLobId" value="5">
					<fieldset class="field_set_main"><legend class="header"><font
						size=2 color=#ff0000>Plan and Transaction Details</font></legend><a
						href="#hide1" class="hide" id="hide1"><font size="4">[+]</font></a><a
						href="#show1" class="show" id="show1"><font size="4">[-]</font></a>
					<div class="list">

					<li class="clearfix"><span class="text2 fll width160"><strong>Request
					Category</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="requestCategory" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong> Plan Id</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="planId" name="leadRegistrationFormBean" /></STRONG></p>

					</li>

					<li class="clearfix alt"><span class="text2 fll width160"><strong>
					Plan</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="plan" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong> Rental</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="rental" name="leadRegistrationFormBean" /></STRONG></p>

					</li>



					<li class="clearfix"><span class="text2 fll width160"><strong>DeviceMrp</strong>
					</span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="deviceMrp" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong>UserType </strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="userType" name="leadRegistrationFormBean" /></STRONG></p>

					</li>

					<li class="clearfix alt"><span class="text2 fll width160"><strong>DeviceTaken</strong>
					</span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="devicetaken" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong>Benefit </strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="benefit" name="leadRegistrationFormBean" /></STRONG></p>

					</li>

					<li class="clearfix"><span class="text2 fll width160"><strong>Package
					Duration</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="pkgDuration" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width160"><strong>Payment
					Amount </strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="pytAmt" name="leadRegistrationFormBean" /></STRONG></p>

					</li>
					<li class="clearfix alt"><span class="text2 fll width160"><strong>Online
					CafNo</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="onlineCafNo" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width160"><strong>Company</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="company" name="leadRegistrationFormBean" /></STRONG></p>


					</li>
					<li class="clearfix"><span class="text2 fll width100"><strong>Transaction
					Reference Number </strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="tranRefno" name="leadRegistrationFormBean" /></STRONG></p>

					</li>
					</fieldset>
				</logic:equal></div>
				<!-- Ended By DTH -->

				<!-- For 3g LOB -->
				<div class="row"><logic:equal name="leadRegistrationFormBean"
					property="productLobId" value="23">
					<fieldset class="field_set_main"><legend class="header"><font
						size=2 color=#ff0000>Plan and Transaction Details</font></legend><a
						href="#hide1" class="hide" id="hide1"><font size="4">[+]</font></a><a
						href="#show1" class="show" id="show1"><font size="4">[-]</font></a>
					<div class="list">

					<li class="clearfix"><span class="text2 fll width160"><strong>Request
					Category</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="requestCategory" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong> Plan Id</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="planId" name="leadRegistrationFormBean" /></STRONG></p>

					</li>

					<li class="clearfix alt"><span class="text2 fll width160"><strong>
					Plan</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="plan" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong> Rental</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="rental" name="leadRegistrationFormBean" /></STRONG></p>

					</li>

					<li class="clearfix "><span class="text2 fll width160"><strong>Rental
					Type</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="rentalType" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong>Offer</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="offer" name="leadRegistrationFormBean" /></STRONG></p>

					</li>

					<li class="clearfix alt"><span class="text2 fll width160"><strong>Download
					Limit</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="downloadLimit" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong>Device Mrp</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="deviceMrp" name="leadRegistrationFormBean" /></STRONG></p>

					</li>

					<li class="clearfix "><span class="text2 fll width160"><strong>Data
					Quota</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="dataQuota" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong>Device
					Taken</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="devicetaken" name="leadRegistrationFormBean" /></STRONG></p>

					</li>
					<li class="clearfix alt"><span class="text2 fll width160"><strong>Benefit</strong>
					</span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="benefit" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong>Package
					Duration</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="pkgDuration" name="leadRegistrationFormBean" /></STRONG></p>

					</li>

					<li class="clearfix"><span class="text2 fll width160"><strong>Online
					CafNo</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="onlineCafNo" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong>Payment
					Amount</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="pytAmt" name="leadRegistrationFormBean" /></STRONG></p>

					</li>

					<li class="clearfix alt"><span class="text2 fll width160"><strong>Company</strong>
					</span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="company" name="leadRegistrationFormBean" /></STRONG></p>

					<span class="text2 fll width100"><strong>Transaction
					Reference Number</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="tranRefno" name="leadRegistrationFormBean" /></STRONG></p>

					</li>
					</fieldset>
				</logic:equal></div>
				<!-- Ended By 3g -->

				<!-- For 4g LOB -->

				<div class="row"><logic:equal name="leadRegistrationFormBean"
					property="productLobId" value="24">
					<fieldset class="field_set_main"><legend class="header"><font
						size=2 color=#ff0000>Plan and Transaction Details</font></legend><a
						href="#hide1" class="hide" id="hide1"><font size="4">[+]</font></a><a
						href="#show1" class="show" id="show1"><font size="4">[-]</font></a>
					<div class="list">

					<li class="clearfix"><span class="text2 fll width160"><strong>Request
					Category</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="requestCategory" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong> Plan Id</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="planId" name="leadRegistrationFormBean" /></STRONG></p>

					</li>

					<li class="clearfix alt"><span class="text2 fll width160"><strong>
					Plan</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="plan" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong> Rental</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="rental" name="leadRegistrationFormBean" /></STRONG></p>

					</li>

					<li class="clearfix "><span class="text2 fll width160"><strong>Rental
					Type</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="rentalType" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong>Offer</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="offer" name="leadRegistrationFormBean" /></STRONG></p>

					</li>

					<li class="clearfix alt"><span class="text2 fll width160"><strong>Download
					Limit</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="downloadLimit" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong>Device Mrp</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="deviceMrp" name="leadRegistrationFormBean" /></STRONG></p>

					</li>

					<li class="clearfix "><span class="text2 fll width160"><strong>Data
					Quota</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="dataQuota" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong>Device
					Taken</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="devicetaken" name="leadRegistrationFormBean" /></STRONG></p>

					</li>
					<li class="clearfix alt"><span class="text2 fll width160"><strong>Benefit</strong>
					</span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="benefit" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong>Package
					Duration</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="pkgDuration" name="leadRegistrationFormBean" /></STRONG></p>

					</li>

					<li class="clearfix"><span class="text2 fll width160"><strong>Online
					CafNo</strong> </span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="onlineCafNo" name="leadRegistrationFormBean" /></STRONG></p>
					<span class="text2 fll width100"><strong>Payment
					Amount</strong></span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="pytAmt" name="leadRegistrationFormBean" /></STRONG></p>

					</li>

					<li class="clearfix alt"><span class="text2 fll width160"><strong>Company</strong>
					</span>
					<p class="clearfix fll" align="justify"
						style="margin-top: 5px; width: 232px"><STRONG> <bean:write
						property="company" name="leadRegistrationFormBean" /></STRONG></p>

					<span class="text2 fll width100"><strong>Transaction Reference Number</strong></span>
					<p class="clearfix fll" align="justify" style="margin-top: 5px; width: 232px"><STRONG> 
					<bean:write property="tranRefno" name="leadRegistrationFormBean" /></STRONG></p>

					</li>
					</fieldset>
				</logic:equal></div>
				<!-- Ended By 4g -->


				<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Contact Details</font></legend>
				<li class="clearfix alt"><span class="text2 fll width160"><strong>Email
				Id</strong> </span>
				<p class="clearfix fll margin-r20"><span class="textbox6">
				<span class="textbox6-inner"> <html:text property="email"
					styleClass="textbox10" /></span></span></p>

				<logic:empty name="leadRegistrationFormBean"
					property="alternateContactNo">

					<span class="text2 fll width120"> <strong>Alternate
					Mobile No.<font color=red>*</font></strong> </span>
					<p class="clearfix fll"><span class="textbox6"> <span
						class="textbox6-inner"> <html:text
						property="alternateContactNo" styleClass="textbox10"
						maxlength="10" /></span></span></p>

				</logic:empty></li>


				<li class="clearfix"><span class="text2 fll width160"><strong>Landline
				No.</strong> </span>
				<p class="clearfix fll margin-r20"><span class="textbox6">
				<span class="textbox6-inner"> <html:text
					property="landlineNo" styleClass="textbox10" maxlength="11" /></span></span></p>
				</li>

				</fieldset>



				<li>
				<div id="crossSale">
				<div>
				<p><b>Do you want to crossSell for this customer ?</p>
				<span id="updateLeadId" style="display: none;"> <a href='#'
					value='true' onclick='crossSaleYesNo(true)'>Yes</a>

				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href='#' value="false" onclick='crossSaleYesNo(false)'>No</b></a> </span> <span
					id="updateLeadIdModule"> <a href='#' class="paulund_modal_5"
					onclick='crossSaleFirst(true)'>Yes</a>

				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href='#' value="false" onclick='crossSaleFirst(false)'>No</b></a> </span></div>
				</div>
				</li>

				</fieldset>




				<%
					if (request.getAttribute("productFlag") != null
										&& request.getAttribute("productFlag").equals(
												true)) {
				%>
				<!--<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Newly added fields editable</font></legend>
					 ------------------------------&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& 
					<li class="clearfix alt"><span class="text2 fll width160"><strong>Previous Circle</strong></span>
					
				<p class="clearfix fll" style="width: 200px"><html:select styleId="previouscircle" property="previouscircle" name="leadRegistrationFormBean" styleClass="select1" onchange="removeFirstChildCircle();">
					
					<html:option value="-1"><bean:write property="previouscircle" name="leadRegistrationFormBean" /></html:option>
					<html:option value="">Select Circle</html:option>
					<logic:notEmpty name="leadRegistrationFormBean" property="circleForProductList">
						<bean:define id="circles" name="leadRegistrationFormBean" property="circleForProductList" />
						<html:options labelProperty="circleName" 	property="circleIdLobIdCircleMstrId" collection="circles" />
					</logic:notEmpty>
				</html:select></p>
				
				
				<span  class="text2 fll width160"><strong>Document Collected </strong></span>
				<p class="clearfix fll" style="width: 200px"><html:select styleId="docCollectedFlag"
					property="documentCollectedFlag" name="leadRegistrationFormBean"
					styleClass="select1" onchange="removeFirstChildDocument();">
					<html:option value="-1"><bean:write property="documentCollectedFlag" name="leadRegistrationFormBean" /></html:option>
					<html:option value="">Select</html:option>
					<html:option value="YES">YES</html:option>
					<html:option value="no">NO</html:option>
					
				</html:select></p>
				
				</li>
				
					<li class="clearfix alt"><span class="text2 fll width160"><strong>Relation Name</strong></span>
				<p class="clearfix fll" style="width: 200px"><html:select styleId="rlnName" property="relationname" name="leadRegistrationFormBean" styleClass="select1" onchange="removeFirstChildRelation();" >
					<html:option value="-1"><bean:write property="relationname" name="leadRegistrationFormBean" /></html:option>
					<html:option value="">Select</html:option>
					<html:option value="father">FATHER</html:option>
					<html:option value="mother">MOTHER</html:option>
					<html:option value="husband">HUSBAND</html:option>
					</html:select></p>
				
				
				<span class="text2 fll width160"><strong>Previous Operator</strong></span>
				<p class="clearfix fll" style="width: 200px"><html:select property="previousoperatorname" name="leadRegistrationFormBean" styleClass="select1">
					<html:option value="">Select previous operator</html:option>
					<logic:notEmpty name="previousoperatorList" scope="request">
						<bean:define id="lobs" 	name="previousoperatorList" scope="request"/> 
						<c:catch>
						<html:options labelProperty="previousoperatorname" property="previousoperatorname" collection="lobs" />
						</c:catch>
					</logic:notEmpty>
				</html:select></p>
				
				</li>
			
					</fieldset>
				
						
				-->

				<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>CAF Details</font></legend>

				<li class="clearfix"><span class="text2 fll width160"><strong>Sim
				No.</strong> </span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="simNumber" name="leadRegistrationFormBean" /></STRONG></p>

				<span class="text2 fll width100"><strong>Customer
				Segment</strong></span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="customerSegment" name="leadRegistrationFormBean" /></STRONG></p>

				</li>

				<li class="clearfix alt"><span class="text2 fll width160"><strong>Nationality</strong>
				</span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="nationality" name="leadRegistrationFormBean" /></STRONG></p>
				<span class="text2 fll width100"><strong>ID Type</strong></span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="identityProofType" name="leadRegistrationFormBean" /></STRONG></p>

				</li>

				<li class="clearfix "><span class="text2 fll width160"><strong>ID
				Proof</strong> </span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="identityProofId" name="leadRegistrationFormBean" /></STRONG></p>
				<span class="text2 fll width100"><strong>Gender</strong></span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="gender" name="leadRegistrationFormBean" /></STRONG></p>

				</li>


				<li class="clearfix alt"><span class="text2 fll width160"><strong>Plan
				Type</strong> </span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="planType" name="leadRegistrationFormBean" /></STRONG></p>
				<span class="text2 fll width100"><strong>Payment
				Date</strong></span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="paymentDate" name="leadRegistrationFormBean" /></STRONG></p>

				</li>

				<li class="clearfix "><span class="text2 fll width160"><strong>UPC</strong>
				</span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="upc" name="leadRegistrationFormBean" /></STRONG></p>
				<span class="text2 fll width100"><strong>UPC GenDate</strong></span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="upcGenDate" name="leadRegistrationFormBean" /></STRONG></p>

				</li>
				<li class="clearfix alt"><span class="text2 fll width160"><strong>Existing
				Part</strong> </span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="existingPart" name="leadRegistrationFormBean" /></STRONG></p>
				<span class="text2 fll width100"><strong>MNP status</strong></span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="mnpStatus" name="leadRegistrationFormBean" /></STRONG></p>

				</li>

				<li class="clearfix"><span class="text2 fll width160"><strong>Previous
				Circle</strong> </span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="previousCircle" name="leadRegistrationFormBean" /></STRONG></p>

				<span class="text2 fll width100"><strong>Previous
				Operator</strong></span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="previousOperator" name="leadRegistrationFormBean" /></STRONG></p>

				</li>


				<li class="clearfix"><span class="text2 fll width160"><strong>Relation
				Name</strong> </span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="relationName" name="leadRegistrationFormBean" /></STRONG></p>

				<span class="text2 fll width100"><strong>Document
				Collected</strong></span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="documentCollectedFlag" name="leadRegistrationFormBean" /></STRONG></p>

				</li>



				</fieldset>


				<%
					}
				%>
				<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Source Details</font></legend>
				<li class="clearfix"><span class="text2 fll width160"><strong>Source</strong>
				</span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG><bean:write
					property="sourceName" name="leadRegistrationFormBean" /></STRONG></p>


				<span class="text2 fll width120"><strong>Sub Source</strong>
				</span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 200px"><STRONG> <bean:write
					property="subSourceName" name="leadRegistrationFormBean" /></STRONG></p>
				</li>



				<li lass="clearfix"><span class="text2 fll width160"><strong>Sales
				Channel Code</strong> </span>
				<p class="clearfix fll margin-r20"><span class="textbox6">
				<span class="textbox6-inner"> <html:text
					property="salesChannelCode" styleClass="textbox10" maxlength="15" />
				</span> </span></p>
				<span class="text2 fll width100"><strong>Campaign</strong></span>
				<p class="clearfix fll" align="justify"
					style="margin-top: 5px; width: 232px"><STRONG> <bean:write
					property="campaign" name="leadRegistrationFormBean" /></STRONG></p>
				</li>
				</br>
				</br>
				</fieldset>
				<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Lead Status</font></legend>
				<li class="clearfix"><span class="text2 fll width160"><strong>Existing
				Lead Status</strong> </span>
				<p class="clearfix fll width235" align="justify"
					style="margin-top: 5px;"><STRONG><bean:write
					property="leadStatusName" name="leadRegistrationFormBean" /></STRONG></p>


				<span class="text2 fll width120"><strong>Lead Status</strong><font color=red>*</font></span>
				<p class="clearfix fll">
				<%
					boolean disable = false;
								if (request.getAttribute("fromUpdate") != null) {
									if (request.getAttribute("fromUpdate").equals("fromUpdate")) {
										disable = true;

									}
								}
				%> <html:select property="leadStatusId"
					name="leadRegistrationFormBean" disabled='<%=disable%>'
					styleClass="select2"
					onchange="markMandatory();getLeadSubStatusDropDown()">
					<html:option value="">Select </html:option>
					<logic:notEmpty name="leadRegistrationFormBean" property="leadStatusList">
						<bean:define id="leasStatus" name="leadRegistrationFormBean" property="leadStatusList" />
						<html:options labelProperty="leadStatus" property="leadStatusSubStatusId" collection="leasStatus" />
					</logic:notEmpty>
					<%-- <html:option value="qualified">qualified</html:option> --%>

				</html:select></p>
				</li>
				
				<li class="clearfix alt"><span class="text2 fll width120"><strong>Appointment
				Time</strong> </span> <html:text property="appointmentDate"
					styleClass="tcal calender2 fll" readonly="true" />
				<table cellpadding="0" cellspacing="2">
					<tr>
						<td><html:select property="appointmentHour"
							name="leadRegistrationFormBean" styleClass="select0">
							<html:option value="">HH</html:option>
							<%
								String hh = "00";
												for (int ii = 0; ii < 24; ii++) {
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
						<td><html:select property="appointmentMinute"
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

						</html:select></td>
						<td><img src="images/delete_icon.gif" onclick="cleanTime()"></td>







						<td><span class="text2 fll width120">&nbsp;&nbsp;&nbsp;&nbsp;<strong>Lead Sub Status</strong> </span>
						<p class="clearfix fll" style="width: 200px"><html:select property="leadSubSubStatusid" name="leadRegistrationFormBean" styleClass="select1">
							<html:option value="">Select Sub Status</html:option>
							<logic:present name="leadSubStatusList" scope="request">
								<logic:notEmpty name="leadSubStatusList" scope="request">
									<bean:define id="leadIds" name="leadSubStatusList"
										scope="request" />
									<html:options labelProperty="leadSubStatus"
										property="leadSubSubStatusid" collection="leadIds" />
								</logic:notEmpty>
							</logic:present>

						</html:select></p>
						</td>

					</tr>
                      
                      
                
		
				</table>


				</li>

<li>

<table>

<tr>
                      <td><span class="text2 fll width120"><strong>Lead Sub Sub Status</strong> </span>
						<p class="clearfix fll" style="width: 232px"><html:select property="allStatusId" name="leadRegistrationFormBean" styleClass="select1" onchange="markMandatory()">
					<html:option value="">Lead Sub Sub Status</html:option>
					<logic:notEmpty name="leadRegistrationFormBean" property="leadSubSubStatusList">
						<bean:define id="subSubStatus" name="leadRegistrationFormBean" property="leadSubSubStatusList" />
						<html:options labelProperty="allStatusName" property="allStatusId" collection="subSubStatus" />
					</logic:notEmpty>
				</html:select></p>
						
				</tr>	

</table>

</li>	


				<!-- Added by satish --> <%
 	if (request.getAttribute("productFlag") != null
 						&& request.getAttribute("productFlag").equals(
 								true)) {
 %>
				<li class="clearfix alt"><span class="text2 fll width160"><strong>Appointment
				End Time</strong> </span> <html:text property="appointmentEndDate"
					styleClass="tcal calender2 fll" readonly="true" />
				<table cellpadding="0" cellspacing="2">
					<tr>
						<td><html:select property="appointmentEndHour"
							name="leadRegistrationFormBean" styleClass="select0">
							<html:option value="">HH</html:option>
							<%
								String hh = "00";
													for (int ii = 0; ii < 24; ii++) {
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

						</html:select></td>
						<td><img src="images/delete_icon.gif" onclick="cleanTime()"></td>
					</tr>

				</table>




				</li>

				<%
					}
				%> <!-- Added by satish -->


				<li><span class="text2 fll width120">
				<table cellpadding="0">
					<tr>
						<td><strong>Remarks</strong></td>
						<td>
						<div id="mandatoryRemarks"></div>
						</td>
					</tr>
				</table>
				</span> <html:textarea styleClass="textarea2 fll" property="remarks"></html:textarea>
				</li>
				</fieldset>




			</ul>


			<jsp:include page="Disclaminer.jsp"></jsp:include></div>
			<logic:equal name="leadRegistrationFormBean" property="GISFlag"
				value="true">
				<logic:equal name="leadRegistrationFormBean" property="productLobId"
					value="2">
					<div class="button" style="padding: 19px;"><a class="red-btn"
						onclick="return submitFeasibilForm();"><b>Feasibility</b></a></div>
				</logic:equal>
			</logic:equal>
			<div class="button-area" id="updatebutton">
			<div class="button"><a class="red-btn"
				onclick="return submitForm();"><b>update</b></a></div>
			<div class="button"><a class="red-btn" onclick="clearForm()"><b>clear</b></a></div>
		</logic:notEmpty>
	</logic:notEqual>
	<script><!--
  var dialerSearchForm = document.forms[0];
  var dialerUpdateForm = document.forms[1];
  
  dialerSearchForm.leadId.select();
  function makeAddressFieldsMandatory(pid)
  {    
  //alert("here");
  //alert("dialerUpdateForm.productIds="+dialerUpdateForm.productIds);
   if(undefined != dialerUpdateForm.productIds)
   {
    var makeMandatory = false;
    var makePINMandatory = true;
    var makeRSUMandatory =false;
    document.getElementById("rsuLine").style.display= "none";
			document.getElementById("rsuLabel").style.display= "none";
			document.getElementById("rsuField").style.display= "none";
    //var isRSUMandatory=false;
    for(ii=0; ii < dialerUpdateForm.productIds.length; ii++)
       {
	        if (dialerUpdateForm.productIds[ii].checked&&pid==null)
	        {
	        
	          for(jj=0; jj < telemediaProductArray.length; jj++)
		       {
		        if (dialerUpdateForm.productIds[ii].value == telemediaProductArray[jj])
			        {
			           isAddressMandatory = true;
			           makeMandatory = true;	
			           makePINMandatory = false;
			           makeRSUVisible=true;	
			           isRSUVisible=true;
			           document.getElementById("rsuLine").style.display= "block";
			document.getElementById("rsuLabel").style.display= "block";
			document.getElementById("rsuField").style.display= "block";
			        }
		   	   }  
		   	    for(kk=0; kk < dthProductArray.length; kk++)
		       {
			        if (dialerUpdateForm.productIds[ii].value == dthProductArray[kk])
			        {
			           isPINMandatory = true;
			           makePINMandatory = true;	
			           document.getElementById("rsuLine").style.display= "none";
			document.getElementById("rsuLabel").style.display= "none";
			document.getElementById("rsuField").style.display= "none";			          
			        }
		   	   } 
		   	   
	        }
	     //From here Added by srrikant for Cross sale product,first time yes   
	      if(pid!=null&&pid.value!=0)
	      {
	       //alert("Second time");
	          for(ll=0;ll<telemediaProductArray.length;ll++)
	          {
	          	if(pid==telemediaProductArray[ll])
	          	{
	          		isAddressMandatory = true;
			           makeMandatory = true;	
			           makePINMandatory = false;
			           makeRSUVisible=true;	
			           isRSUVisible=true;
			           document.getElementById("rsuLine").style.display= "block";
			document.getElementById("rsuLabel").style.display= "block";
			document.getElementById("rsuField").style.display= "block";
			           //alert("makePINMandatory="+makePINMandatory);
			          // alert("hiiiiiiiiiii"+pid);
	          	}
	          	/*else
	          	{
	          
	          	document.getElementById("rsuLine").style.display= "none";
			document.getElementById("rsuLabel").style.display= "none";
			document.getElementById("rsuField").style.display= "none";
			//alert("Alernate");
	          	}*/
	          }
	          
	          }  
	        
	        
   	   }
   	   
   	 //  if(makeMandatory)
   	   //{
   	   	//	 document.getElementById("mandatoryAddress1").innerHTML = "<font color=red>*</font>";
			// document.getElementById("mandatoryCity").innerHTML = "<font color=red>*</font>";
   	//   }
   	  // else
   	   //{
   	     //  document.getElementById("mandatoryAddress1").innerHTML = "";
          // document.getElementById("mandatoryCity").innerHTML = "";
   	       //isAddressMandatory = false;
   	   //}
   	   if(makePINMandatory)
   	   {
   	   		 document.getElementById("mandatoryPIN").innerHTML = "<font color=red>*</font>";
   	   		 isPINMandatory = true;
   	   }
   	   else
   	   {
   	   	 document.getElementById("mandatoryPIN").innerHTML = "";
   	   	 isPINMandatory = false;
   	   	 
   	   }
   	  /*if(makeRSUMandatory)
   	  {
   	   		 //document.getElementById("VisibleRSU").innerHTML.visible =true;
   	   		 document.getElementById("getRSU").style.display="";
   	   }
   	   else
   	   {
   	   	 //document.getElementById("VisibleRSU").innerHTML.visible =false;
   	   	document.getElementById("getRSU").style.display="none";
   	   	 //isRSUVisible=false;
   	   	 
   	   	 
   	   }*/
    } 
    }  
    makeAddressFieldsMandatory();
    
      jQuery(document).ready(function(){
//jQuery("input[type='radio']").removeAttr('checked');

jQuery(".paulund_modal_5").click(function(){
 //window.showModalDialog('<strong>Select Product<font	color=red>*</font> </strong> ', '', 'status:1; resizable:1; dialogWidth:900px; dialogHeight:500px; dialogTop=50px; dialogLeft:100px')
  var el = document.getElementById("crossSale");
	el.style.visibility ="hidden";
		
});
$('.paulund_modal_5').paulund_modal_box({
	title:'<strong>Select Product<font	color=red>*</font> </strong> ',
	description:'<br>'+jQuery('#allProducts').html(),
	height: '500',
	width: '700'	
});
});
  --></script>

</html:form>
<form
	action='<bean:write  property="GISUrlAddress" name="leadRegistrationFormBean" />'
	target="_blank" id="gisFormId"><html:hidden property="leadId"
	name="leadRegistrationFormBean" /> <html:hidden property="password"
	name="leadRegistrationFormBean" /> <html:hidden property="username"
	name="leadRegistrationFormBean" /> <input type="hidden" name="addr"
	value="" id="addrId"></form>
