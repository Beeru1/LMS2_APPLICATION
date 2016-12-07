		<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<script language="javascript"
	src="<%=request.getContextPath() + "/jScripts/ajaxCall.js"%>"></script>
<head>
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
var confirmValue=" ";
var enableButtons=" ";
 </script>
</head>
<script language="javascript">

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

	// Search through string's characters one by one
  	// until we find a non-whitespace character.
	// When we do, return false; if we don't, return true.
	for (var i = 0; i < theLength ; i++)
  	{
    	// Check that current character isn't whitespace.
    	var theChar = theInput.charAt(i);

    	if (whitespace.indexOf(theChar) == -1)
    	{
	    	return (false);
    	}
  	}//for loop ends

  	// All characters are whitespace.
  	return (true);
}// function isEmpty ends


/**
 * Removes the leading and trailing white spaces from the value in the given text object.
 * The trimmed string is set as the value of the given object and the same is returned by
 * the function.
 *
 * @param	objInput	The input text object.
 * @return 	The trimmed string.
 */
function trimValue(objInput)
{
	var strValue = objInput.value;

	var leftIndex;
	var rigthIndex;

	//get the location of the first character that is not a white space.
	for(var i = 0; i < strValue.length; i++)
	{
		if(strValue.charAt(i) != " ")
		{
			leftIndex = i;
			break;
		}
	}
	//get the location of the last character that is not a white space.
	for(var i = strValue.length; i > 0; i--)
	{
		if(strValue.charAt(i - 1) != " ")
		{
			//not i - 1 as the last place is not included while
			// performing a substring
			rigthIndex = i;
			break;
		}
	}
	var strReturn = strValue.substring(leftIndex, rigthIndex);
	//set the same value to the input object
	objInput.value = strReturn;
	//return the trimmed string
	return strReturn;
}
//---------------------------- added by pratap
var rowNumber=0;
 function validate()
 {
 	document.forms[0].customerName.value = trimAll(document.forms[0].customerName.value);
 	if(isEmpty(document.forms[0].customerName))
	{
	 	alert("Please enter Customer Name.");
	 	document.forms[0].customerName.focus();
	 	return false;
	}
	else
	if(!isName(document.forms[0].customerName))
	{
	 alert("Only Alphabets and spaces are allowed in Customer Name.");
	 document.forms[0].customerName.select();
	 //Nancy
	 document.forms[0].customerName.disabled= false;
	//document.forms[0].contactNo.disabled= false;
	 return false;	
    }
	
	
	if(isEmpty(document.forms[0].contactNo))
	{
	 	alert("Please enter Mobile No.");
	 	document.forms[0].contactNo.select();
		return false;	
	}
	//added by Nancy
	if(isEmpty(document.forms[0].alternateContactNo))
	{
	 	alert("Please enter alternate ContactNo No.");
	 	document.forms[0].alternateContactNo.select();
		return false;	
	}
	
	if(isEmpty(document.forms[0].udid))
	{
	 	alert("Please enter the UdId.");
	 	document.forms[0].udid.select();
		return false;	
	}
	
	// adding by pratap-------------
	else if(document.forms[0].contactNo.value == 0)
		{
		alert("Please enter valid 10 digit Mobile No.");
		return false;
		}
		else if(document.forms[0].contactNo.value.substring(0,1)== '0')
		{
		alert("Mobile No. Can't start with Zero!");
		return false;
		}
		else if(document.forms[0].alternateContactNo.value.substring(0,1)== '0')
		{
		alert("Alternate Mobile No. Can't start with Zero!");
		return false;
		}
		else if(document.forms[0].alternateContactNo.value == 0)
		{
		alert("Please enter valid 10 digit Alternate Mobile No.");
		return false;
		}
		else if(!(isEmpty(document.forms[0].landlineNo)) && document.forms[0].landlineNo.value.length <11)
		{
		alert("Landline number should be of 11 digits.");
		return false;
		}
		else if(!(isEmpty(document.forms[0].landlineNo)) && !isInteger(document.forms[0].landlineNo))
		{
		alert("Please enter valid landline number.");
		return false;
		}
		else if(document.forms[0].alternateContactNo.value == document.forms[0].contactNo.value)
		{
		alert("Contact number and alternate contact number should not be same.");
		return false;
		}
		else if(document.forms[0].alternateContactNo.value.substring(0,1)== '0')
		{
		alert("Alternate Mobile No. Can't start with Zero!");
		return false;
		}
		//----- end of adding by pratap
	else
	if(document.forms[0].contactNo.value.length <10 || !isInteger(document.forms[0].contactNo) )
		{
		  alert("Please enter valid 10 digit Mobile No.");
		  document.forms[0].contactNo.select();
		  document.forms[0].contactNo.disabled= false;
		  return false;
		}
	else if(!isEmpty(document.forms[0].extraParam1))
	{
	 if(document.forms[0].extraParam1.value.length<10 || !isInteger(document.forms[0].extraParam1))
		{
		 alert("Please enter valid 10 digit sales Executive No.");
		  document.forms[0].extraParam1.select();
		  return false;
		}
		}	
		// checking for alternate mobile no by pratap
	if(!isEmpty(document.forms[0].alternateContactNo))
	{
	
		if(document.forms[0].alternateContactNo.value.length <10 || !isInteger(document.forms[0].alternateContactNo) )
		{
		  alert("Please enter valid 10 digit alternate mobile No.");
		  document.forms[0].alternateContactNo.select();
		  return false;
		}
	}
	// end of checking for alternate mobile no by pratap
	
	// checking for preffered language
	if(!isEmpty(document.forms[0].language))
	{
		if(isInteger(document.forms[0].language) )
		{
		  alert("Please enter valid language");
		  document.forms[0].language.select();
		  return false;
		}
		if(!isName(document.forms[0].language))
		{
		  alert("Please enter valid language");
		  document.forms[0].language.select();
		  return false;
		}
	}
	
	if(document.forms[0].productLobID.value == ""){
		 alert("Please select LOB");
		  document.forms[0].language.select();
		  return false;
	}
	if(document.forms[0].productId.value == "-1"){
		 alert("Please select Product");
		  document.forms[0].language.select();
		  return false;
	}
	
		
	if(document.forms[0].circleId.value == "-1")
	{
	 	alert("Please select Circle.");
	 	document.forms[0].circleId.focus();
		return false;	
	}
	
	
	if(isPincodeMandatory)
	{
	
	if(document.forms[0].cityCode.value == "-1")
		{
		 	alert("Please select City");
			return false;	
		}	
		if(isEmpty(document.forms[0].pinCode))
		{
		 	alert("Please enter Pincode");
		 	document.forms[0].pinCode.focus();
		 	return false;
		}
		
		
	}
		
	
	 if(isAddressMandatory)
	{
		 var leadStatus=document.forms[0].leadStatus.value;
		if(isEmpty(document.forms[0].address1))
		{
		 	alert("Please enter Address");
		 	document.forms[0].address1.focus();
		 	return false;
		}
		
		if(document.forms[0].cityCode.value == "-1" && leadStatus==="false")
		{
		 	alert("Please select City");
			return false;	
		}	
	
		
	}
	
	if(!isEmpty(document.forms[0].language))
	{
		if(!isName(document.forms[0].customerName))
		{
		  alert("Only Alphabets and spaces are allowed in Language Name.");
		  document.forms[0].language.select();
		  return false;
		  
		}
	}
	
	
	
	if(document.forms[0].email.value != "")
	{
	
		
		if(!isEmailAddress(document.forms[0].email))
		{
		  alert("Please enter valid E-mail Id.");
		  document.forms[0].email.select();
		  return false;
		}
	}
	
	
	if(!isEmpty(document.forms[0].extraParam1))
	{
	
		if(!isInteger(document.forms[0].extraParam1) )
		{
		  alert("Please enter valid Sales Executive Number No.");
		  document.forms[0].extraParam1.select();
		  return false;
		}
	}
	//Appoint 
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;
	var yyyy = today.getFullYear();
	var hh = today.getHours();
	var min = today.getMinutes();
	
	if(dd<10){dd='0'+dd}
	if(mm<10){mm='0'+mm}
	var curr_dt=dd+'-'+mm+'-'+yyyy;


	 
	 if(document.forms[0].appointmentDate.value != "")
	 {
	 	var formDate  = document.forms[0].appointmentDate.value;
		
       if(process(formDate) < process(curr_dt))
		{
			alert("Appointment Date cannot be a past Date.");
			return false;			
		}
		
		if(document.forms[0].appointmentMinute.value == "" && document.forms[0].appointmentHour.value == "")
			{
				alert("Please select Appointment Hour and Minute.");
				return false;			
			}
			if(document.forms[0].appointmentMinute.value == "" && document.forms[0].appointmentHour.value != "")
			{
				alert("Please select Appointment Minute.");
				return false;			
			}
			
		if(document.forms[0].appointmentMinute.value != "" && document.forms[0].appointmentHour.value == "")
			{
				alert("Please select Appointment Hour.");
				return false;			
			}	
			
		if(document.forms[0].appointmentDate.value == curr_dt)
		{
		   alert(document.forms[0].appointmentHour.value+" , hh : "+hh);
			
			if(document.forms[0].appointmentHour.value != "")
			{
			   if(document.forms[0].appointmentHour.value < hh)
			   {
				alert("Appointment Hour cannot be a past time.");
				return false;
				}			
			}
		alert(document.forms[0].appointmentMinute.value+" , min : "+min);
			
			if(document.forms[0].appointmentMinute.value != "")
			{
				if(document.forms[0].appointmentHour.value == hh )
				{
				 	if(document.forms[0].appointmentMinute.value < min)
			    	{
					alert("Appointment Minute cannot be a past time.");
					return false;
					}
				}			
			}
		}
	 }	
	if(document.forms[0].appointmentDate.value == "")
	{
	if(document.forms[0].appointmentMinute.value != "" || document.forms[0].appointmentHour.value != "")
			{
				alert("Please select Appointment date.");
				return false;			
			}
	}
	
 	/* if(!isEmpty(document.forms[0].pinCode.value)) commentd by pratap  its not needed now
	{
	if(document.forms[0].pinCode.value.length <6 || !isInteger(document.forms[0].pinCode.value) )
		{
		  alert("Please enter valid 6 digit PIN.");
		  document.forms[0].pinCode.focus();
		  return false;
		}
	} */
	
	// return emailvalidation();			
    return true;
}
function emailvalidation()
{
 if(document.forms[0].email.value != "")
	{
		if(!isEmailAddress(document.forms[0].email))
		{
		  alert("Please enter valid E-mail Id.");
		  document.forms[0].email.select();
		  return false;
		}
	}
		
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1;
		var yyyy = today.getFullYear();
		var hh = today.getHours();
		var min = today.getMinutes();
		
		if(dd<10){dd='0'+dd}
		if(mm<10){mm='0'+mm}
		var curr_dt=dd+'-'+mm+'-'+yyyy;
		
		
	 if(document.forms[0].appointmentDate.value != "")
	 {
        if(document.forms[0].appointmentDate.value < curr_dt)
		{
			alert("Appointment Date cannot be a past Date.");
			return false;			
		}
		
		if(document.forms[0].appointmentMinute.value != "" && document.forms[0].appointmentHour.value == "")
		{
			alert("Please select Appointment Hour.");
			return false;			
		}
			
			
		if(document.forms[0].appointmentDate.value == curr_dt)
		{
		    alert(document.forms[0].appointmentHour.value+" , hh : "+hh);
			
			if(document.forms[0].appointmentHour.value != "")
			{
			   if(document.forms[0].appointmentHour.value < hh)
			   {
				alert("Appointment Hour cannot be a past time.");
				return false;
				}			
			}
			alert(document.forms[0].appointmentMinute.value+" , min : "+min);
			if(document.forms[0].appointmentMinute.value != "")
			{
				if(document.forms[0].appointmentHour.value == hh )
				{
				 	if(document.forms[0].appointmentMinute.value < min)
			    	{
					alert("Appointment Minute cannot be a past time.");
					return false;
					}
				}			
			}
		}
	 }
}



function submitForm()
 { 

 var totalTableValues="";
 if(enableButtons=="false")return;
   if(validate())
   {
   // added by Nancy Agrawal.
document.forms[0].customerName.disabled =false;
	document.forms[0].contactNo.disabled= false;
	
	
  	// adding by pratap start
  	 var dataTable=document.getElementById("dataTable");
	var Tablerows=document.getElementById("dataTable").rows;
	for(rowcount=1;rowcount<Tablerows.length ;rowcount++)
	{
	//totalTableValues=totalTableValues+Tablerows[rowcount].cells[7].innerText+"=";
/********************************** pratap *********************/
	if (window.ActiveXObject) // code for IE6, IE5
	{
	totalTableValues=totalTableValues+Tablerows[rowcount].cells[7].innerText+"=";
	}
	else if (window.XMLHttpRequest) // code for IE7+, Firefox, Chrome, Opera, Safari
	{
	totalTableValues=totalTableValues+Tablerows[rowcount].cells[7].innerHTML+"=";
	}
	else if (window.XDomainRequest) // code for IE8
	{
		totalTableValues=totalTableValues+Tablerows[rowcount].cells[7].innerText+"=";
	}
/****************************** end ****************************/
	}
	
  	// end adding by pratap
  	if(totalTableValues == "")
  	{
  	
  	alert("Please Add products");
  	return;
  	}
  	
	document.forms[0].tableValues.value = totalTableValues;
  	 //alert("form  final values  :"+document.getElementById("tableValues").value);
  	 
    document.forms[0].methodName.value ="insertRecord";
   	document.forms[0].submit();
   }
   
 }	

 function clearForm()
 {
 
 if(enableButtons == "false")
 {
 return false;
 }
 else
 {
    document.forms[0].reset();
    document.forms[0].customerName.disabled =false;
	document.forms[0].contactNo.disabled= false;
	return true;
    }
  
  }
 function cleanTime()
 {
 	document.forms[0].appointmentDate.value="";
	document.forms[0].appointmentHour.value="";
	document.forms[0].appointmentMinute.value="";
 }  
var getCityOnCircleChange = function()
	{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.leadRegistrationFormBean.cityCode;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select City", "-1");
			for (var i = 0; i < optionValues.length; i++)
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
		}
	}
	
function getcityForCircle()
	{
		removeAllOptions(document.leadRegistrationFormBean.cityCode,new Option("Select City","-1"));
		removeAllOptions(document.leadRegistrationFormBean.cityZoneCode,new Option("Select CityZone","-1"));
		document.leadRegistrationFormBean.pinCode.value="";
		var circleID = document.leadRegistrationFormBean.circleId;
		var circleMstriD = circleID.options[circleID.options.selectedIndex].value.split("#")[2];// changed done by pratap on 28-11-13
		var data = "methodName=getCityOnCircleChange&circleMstriD=" + circleMstriD;
		doAjax("leadRegistration.do", "GET", false, getCityOnCircleChange, data);
	}
	
	
	//Added By Bhaskar
	
	function getRsuForCityCode()
	{
	
		//removeAllOptions(document.leadRegistrationFormBean.cityCode,new Option("Select City","-1"));
		//removeAllOptions(document.leadRegistrationFormBean.cityZoneCode,new Option("Select CityZone","-1"));
		var circleID = document.leadRegistrationFormBean.circleId;
		


		var circleMstriD = circleID.options[circleID.options.selectedIndex].value.split("#")[2];// changed done by pratap on 28-11-13
		var cityCode = document.leadRegistrationFormBean.cityCode;
		alert(cityCode);
		var CityCode = cityCode.options[cityCode.options.selectedIndex].value.split("#")[1];
		
		var data = "methodName=getRsuForCityChange&circleMstriD=" + circleMstriD + "&CityCode="+ CityCode;
		doAjax("leadRegistration.do", "GET", false, getRsuForCityChange, data);
		//doSyncAjax("leadRegistration.do", "GET", getRsuForCityChange, data);
	}
	var getRsuForCityChange = function()
	{
	
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.leadRegistrationFormBean.rsuCode;
			
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select Rsu", "-1");
			for (var i = 0; i < optionValues.length; i++)
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
		}
	}
	 
	 
	 
	 function getRsuForCityZoneCode()
	{
	
		//removeAllOptions(document.leadRegistrationFormBean.cityCode,new Option("Select City","-1"));
		//removeAllOptions(document.leadRegistrationFormBean.cityZoneCode,new Option("Select CityZone","-1"));
		var circleID = document.leadRegistrationFormBean.circleId;
		

		var circleMstriD = circleID.options[circleID.options.selectedIndex].value.split("#")[2];// changed done by pratap on 28-11-13
		//alert("circleMstriD"+circleMstriD);
	
	
		var cityCode = document.leadRegistrationFormBean.cityCode;
		var CityCode = cityCode.options[cityCode.options.selectedIndex].value.split("#")[1];
		//alert("cityCode"+CityCode);
		cityZoneCode = document.leadRegistrationFormBean.cityZoneCode;
		cityzonecode = cityZoneCode.options[cityZoneCode.options.selectedIndex].value
		//alert("cityzonecode"+cityzonecode);
		var data = "methodName=getRsuForCityZoneCodeChange&circleMstriD=" + circleMstriD + "&CityCode="+ CityCode +"&cityzonecode="+cityzonecode;
	doAjax("leadRegistration.do", "GET", false, getRsuForCityZoneCodeChange, data);
		//doSyncAjax("leadRegistration.do", "GET", getRsuForCityChange, data);
	}
	var getRsuForCityZoneCodeChange = function()
	{
	
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{//alert("Inside request one");
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.leadRegistrationFormBean.rsuCode;
			//alert("selectObj"+selectObj);
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select Rsu", "");
			for (var i = 0; i < optionValues.length; i++)
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"));
		}
	}
	//End By Bhaskar

var getPINOnCityChange = function()
	{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.leadRegistrationFormBean.pinCode;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select PIN", "-1");
			for (var i = 0; i < optionValues.length; i++)
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
		}
	}
	
function getPinCodeForCity()
	{
		removeAllOptions(document.leadRegistrationFormBean.pinCode);
		var cityCode = document.leadRegistrationFormBean.cityCode;
		var CityCode = cityCode.options[cityCode.options.selectedIndex].value;
		var data = "methodName=getPINOnCityChange&CityCode=" + CityCode;
		doAjax("leadRegistration.do", "GET", false, getPINOnCityChange, data);
	}


function getDataforCity()
{
		getPinCodeForCity();
}


function makeAddressFieldsMandatory()
{ 

  var makeMandatory = false;

  var productId=document.forms[0].productLobID.value;
  var leadStatus=document.forms[0].leadStatus.value;
 	   
  if (productId!=null && productId.length >0)
   {
      isAddressMandatory = true;
      makeMandatory = true;		
   }
 	
 	   if(makeMandatory)
 	   {
 	   		 document.getElementById("mandatoryAddress1").innerHTML = "<font color=red>*</font>";
 	   		 if(leadStatus==="false"){
			 document.getElementById("mandatoryCity").innerHTML = "<font color=red>*</font>";}
 	   }
 	   else
 	   {
 	       document.getElementById("mandatoryAddress1").innerHTML = "";
         	document.getElementById("mandatoryCity").innerHTML = "";
 	       isAddressMandatory = false;
 	   }
 	   
 }

 function makePincodeFieldsMandatory()
{ 

  var makeMandatory = false;

  var productId=document.forms[0].productLobID.value;
  var leadStatus=document.forms[0].leadStatus.value;
 	   
 	 if (productId!=null&&productId!=2&&leadStatus==="false")
   {
      isPincodeMandatory = true;
      makeMandatory = true;		
   }
 	
 	   if(makeMandatory)
 	   {
 	   		 document.getElementById("mandatoryPincode").innerHTML ="<font color=red>*</font>";
			 //document.getElementById("mandatoryCity").innerHTML = "<font color=red>*</font>";
			 
 	   }
 	   else
 	   {
 	       document.getElementById("mandatoryPincode").innerHTML = "";
         	//document.getElementById("mandatoryCity").innerHTML = "";
 	       isPincodeMandatory = false;
 	   }
 	   
 }
 


   function getCircleForProduct()
	{
	
		removeAllOptions(document.leadRegistrationFormBean.circleId,new Option("Select Circle","-1"));
		removeAllOptions(document.leadRegistrationFormBean.cityCode,new Option("Select City","-1"));
		removeAllOptions(document.leadRegistrationFormBean.cityZoneCode,new Option("Select CityZone","-1"));
		document.leadRegistrationFormBean.pinCode.value="";
		document.forms[0].pinCode.value="";
	
		  	productLobId=document.forms[0].productLobID.value;
		  
   var data = "methodName=getCircleOnProductChange&productLobId=" + productLobId;
  
		doAjax("leadRegistration.do", "GET", false, getCircleOnProductChange, data);
	}
var getCircleOnProductChange = function()
	{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.leadRegistrationFormBean.circleId;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select Circle", "-1");
			for (var i = 0; i < optionValues.length; i++){
			selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
				}
		}
	}
	
	function getZoneForCircle()
	{
		removeAllOptions(document.leadRegistrationFormBean.cityZoneCode,new Option("Select CityZone","-1"));
		removeAllOptions(document.leadRegistrationFormBean.cityCode,new Option("Select City","-1"));
		removeAllOptions(document.leadRegistrationFormBean.pinCode,new Option("Select PIN","-1"));
		var circleID = document.leadRegistrationFormBean.circleId;
		var circleiD = circleID.options[circleID.options.selectedIndex].value;
		var data = "methodName=getZoneOnCircleChange&circleID=" + circleiD;
		doAjax("leadRegistration.do", "GET", false, getZoneOnCircleChange, data);
	}
	var getZoneOnCircleChange = function()
	{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.leadRegistrationFormBean.zoneCode;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select zone", "-1");
			for (var i = 0; i < optionValues.length; i++){
			selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
			}
		}
	}
	

	
function getcityForZone()
	{
		removeAllOptions(document.leadRegistrationFormBean.cityCode,new Option("Select City","-1"));
		removeAllOptions(document.leadRegistrationFormBean.pinCode,new Option("Select PIN","-1"));
		var zoneCode = document.leadRegistrationFormBean.zoneCode;
		var citycode = zoneCode.options[zoneCode.options.selectedIndex].value.split("#")[0];
		var data = "methodName=getCityOnZoneChange&cityCode=" + citycode;
		doAjax("leadRegistration.do", "GET", false, getCityOnZoneChange, data);
	}
		
	var getCityOnZoneChange = function()
	{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.leadRegistrationFormBean.cityCode;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select City", "-1");
			for (var i = 0; i < optionValues.length; i++)
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
		}
	}
	
	function getCityZoneOnCityChange()
	{
		removeAllOptions(document.leadRegistrationFormBean.cityZoneCode,new Option("Select CityZone","-1"));
		document.leadRegistrationFormBean.pinCode.value="";
		var cityCode = document.leadRegistrationFormBean.cityCode;
		var citycode = cityCode.options[cityCode.options.selectedIndex].value.split("#")[1];
		var data = "methodName=getCityZoneOnCityChange&cityCode=" + citycode;
		//doAjax("leadRegistration.do", "GET", true, getCityZoneForCity, data);
		doSyncAjax("leadRegistration.do", "GET", getCityZoneForCity, data);
		
		var cityCodeeeee = document.leadRegistrationFormBean.cityCode;
		var cityCodeeeeeval = cityCodeeeee.options[cityCodeeeee.options.selectedIndex].value.split("#")[0];
		getZoneCode(cityCodeeeeeval);
		
	}
		
	var getCityZoneForCity = function()
	{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.leadRegistrationFormBean.cityZoneCode;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select CityZone", "-1");
			for (var i = 0; i < optionValues.length; i++)
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
		}

	}

	
	// getting city for pincode
	function getCityForPinCode()
	{
		var PinCode = document.leadRegistrationFormBean.pinCode.value;
		if(document.leadRegistrationFormBean.pinCode.value.length < 6) 
		{
		alert("Please enter valid 6 digit PIN.");
		document.leadRegistrationFormBean.pinCode.select();
		return false;
		}
		var data = "methodName=getCityForPinCode&pinCode=" + document.leadRegistrationFormBean.pinCode.value;
		return doAjax("leadRegistration.do", "GET", false, getCityForPinCodee, data);
	
	}
		
	var getCityForPinCodee = function()
	{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var responseValue = xmlHttpRequest.responseText;
			if(responseValue =="") 
			{
			alert("Not a valid PIN code");
			document.leadRegistrationFormBean.pinCode.focus();
			return false;
			}
			
			CityCode=responseValue.split("#")[0];
			cityText=responseValue.split("#")[1];
			return true;
		}
	}
	// end of getting city for pincode
	
function Add()
{
	
	cityCode = " ";
	CityCode = " ";
	cityText =" ";
	circleID=" ";
	circleiD=" ";
	circleText=" ";
	cityZoneCode=" " ;
	cityzonecode=" ";
	cityzoneText=" ";
	zoneCodeArr=" ";
	zonecode=" ";
	zoneText=" ";
	hiddenTD=" ";
	circleMstrId=" ";
	document.forms[0].autoZoneCode.value=" ";
	// getting product id and product lob id
	var PinCode = document.forms[0].pinCode.value;
			
			    var productLobId;
			    var productId;
			    var productName;
	 
				productLobId=document.forms[0].productLobID.value;
		        productID = document.leadRegistrationFormBean.productId;
		       
		        productiD=productID.options[productID.options.selectedIndex].value;
		        productId=productID.options[productID.options.selectedIndex].value.split("#")[0];
		        
		        productName=productID.options[productID.options.selectedIndex].value.split("#")[1];
		       
		       
	// getting circle id 
		 circleID = document.leadRegistrationFormBean.circleId;
		 circleiD = circleID.options[circleID.options.selectedIndex].value;
		if(circleiD == '-1')
		{
		alert("Please select Circle");
		return;
		}
		circleiD = circleID.options[circleID.options.selectedIndex].value.split("#")[0];
		circleText = circleID.options[circleID.options.selectedIndex].text;
		circleMstrId=circleID.options[circleID.options.selectedIndex].value.split("#")[2];
	// end 
	
	// getting zone id and text
		/*	var zoneCode = document.leadRegistrationFormBean.zoneCode;
			var cityAndZone = zoneCode.options[zoneCode.options.selectedIndex].value;
		    
			if(cityAndZone == -1)
			{
			alert("Select Zone");
			return;
			}
			var zoneText= zoneCode.options[zoneCode.options.selectedIndex].text;
			zoneCode=zoneCode.options[zoneCode.options.selectedIndex].value.split("#")[1];
			alert("city #zonecode  :"+cityAndZone +"  zonecode :"+zoneCode+"  zoneTxt :"+zoneText);
			*/
	// end 
	
	// getting city code and text
	
	//  checking for duplicate addition of product in grid by pratap 30 nov 2013( grid values has been changed please cummit/compare that also
	var rowsOfDataTable=document.getElementById("dataTable").rows;
	
	var rowcountOfrowsOfDataTable="";
	var totalIds="";
	var prodcID="";
	var cirID="";
	for(rowcountOfrowsOfDataTable=1;rowcountOfrowsOfDataTable<rowsOfDataTable.length ;rowcountOfrowsOfDataTable++)
	{
	/********************************** pratap *********************/
	
		if (window.ActiveXObject) // code for IE6, IE5
		{
		prodcID=rowsOfDataTable[rowcountOfrowsOfDataTable].cells[7].innerText.split("#")[0];
		cirID=rowsOfDataTable[rowcountOfrowsOfDataTable].cells[7].innerText.split("#")[1];
		}
		else if (window.XMLHttpRequest) // code for IE7+, Firefox, Chrome, Opera, Safari
		{
		prodcID=rowsOfDataTable[rowcountOfrowsOfDataTable].cells[7].innerHTML.split("#")[0];
		cirID=rowsOfDataTable[rowcountOfrowsOfDataTable].cells[7].innerHTML.split("#")[1];
		}
		else if (window.XDomainRequest) // code for IE8
		{
		prodcID=rowsOfDataTable[rowcountOfrowsOfDataTable].cells[7].innerText.split("#")[0];
		cirID=rowsOfDataTable[rowcountOfrowsOfDataTable].cells[7].innerText.split("#")[1];
		}
	/****************************** end by pratap****************************/
	if(productId == prodcID && circleiD == cirID)
	{
	alert("Lead for this Product has already been added for the Customer");
	return;
	}
	}
	// checking for telemedia mandatory fields
	//commentd By Bhaskar
	
	
	  var leadStatus=document.forms[0].leadStatus.value;
if(document.forms[0].cityCode.value == "-1" && leadStatus==="false" )
			{
			 	alert("Please select City");
			 	document.forms[0].cityCode.focus();
				return;	
			}	
	
	if(isPincodeMandatory)
		{
		
		if(isEmpty(document.forms[0].pinCode))
			{
			 	alert("Please enter Pincode");
			 	document.forms[0].pinCode.focus();
			 	return;
			}
					
		}
	 if(isAddressMandatory)
		{
		 
		 var leadStatus=document.forms[0].leadStatus.value;
			if(isEmpty(document.forms[0].address1))
			{
			 	alert("Please enter Address");
			 	document.forms[0].address1.focus();
			 	return;
			}
			
			if(document.forms[0].cityCode.value == "-1" && leadStatus==="false")
			{
			 	alert("Please select City");
			 	document.forms[0].cityCode.focus();
				return;	
			}	
		
			
		} 
	// end of checking for telemedia mandatory fields
	// End of checking for duplicate addition of product in grid
	
	// checking for valid pin code by pratap
	 	if(document.forms[0].pinCode.value != "")
		{
		if(document.forms[0].pinCode.value.length < 6 || !isInteger(document.forms[0].pinCode) )
			{
			  alert("Please enter valid 6 digit PIN.");
			  document.forms[0].pinCode.focus();
			  return false;
			}
		}
			
	// getting pin code and text
	 cityCode = document.leadRegistrationFormBean.cityCode;
	 CityCode = cityCode.options[cityCode.options.selectedIndex].value;
	 cityText = cityCode.options[cityCode.options.selectedIndex].text;
	if(PinCode != "")
	{
		getDataForPinCode(PinCode,circleMstrId);
		if( pinFlag == " ")
		{
		return;
		}
		if(pinFlag == "false")
		{
		alert("Not a valid PIN code");
		document.leadRegistrationFormBean.pinCode.focus();
		return;
		}
		
	// end of populating all things as pincode is entered or giving alert that pin code entered is wrong
	}
	else if(CityCode != '-1')
	{
	// populating zonecode here 
	//getZoneCode(CityCode.split("#")[0]);
	CityCode=CityCode.split("#")[1];
	// getting cityzone code and text
					cityZoneCode = document.leadRegistrationFormBean.cityZoneCode;
					cityzonecode = cityZoneCode.options[cityZoneCode.options.selectedIndex].value;
					cityzoneText = cityZoneCode.options[cityZoneCode.options.selectedIndex].text;
					if(cityzonecode == '-1')
					{
					cityzoneText=" ";
					cityzonecode=" ";
					}
			// end 
	PinCode=" ";
	// end of populating zonecode here
	}
		else
		{
			// getting all things from front end as entered by user
			PinCode=" ";
		// populating city code
			
					 
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
			// end 
	// end of getting all things from front end as entered by user
		}
	// end
	// adding to data table ====
	var dataTable=document.getElementById("dataTable");
	rowNumber++;
	var row=dataTable.insertRow(-1);
	var product=row.insertCell(0);
	var circle=row.insertCell(1);
	var zone=row.insertCell(2);
	var city=row.insertCell(3);
	var cityzone=row.insertCell(4);
	var pincode=row.insertCell(5);
	var remove=row.insertCell(6);
	var hidden=row.insertCell(7);
	product.innerHTML="<a id="+productId+" name="+productId+">"+productName+"</a>";
	circle.innerHTML="<a id="+circleiD+" name="+circleiD+">"+circleText+"</a>";
	zoneCodeArr=" ";
	zonecode=" ";
	zoneText=" ";
	//alert("zoneCodeAndName :"+zoneCodeAndName );
	//alert("zoneCodeAndName hidden variable:"+document.forms[0].zoneCodeAndName.value );
	
		//alert("document.forms[0].zoneCodeAndName.value  2222:"+document.forms[0].zoneCodeAndName.value);
	if(zoneCodeAndName == " ")
	{
	zonecode=" ";
	zoneText=" ";
	}
	else
	{
	zoneCodeArr=zoneCodeAndName.split("#");
	zonecode=zoneCodeArr[0];
	zoneText=zoneCodeArr[1];
	}
	zone.innerHTML="<a id="+zonecode+" name="+zonecode+">"+zoneText+"</a>";//changes done here
	city.innerHTML="<a id="+CityCode+" name="+CityCode+">"+cityText+"</a>";
	cityzone.innerHTML="<a id="+cityzonecode+" name="+cityzonecode+">"+cityzoneText+"</a>";
	pincode.innerHTML="<a id="+PinCode+" name="+PinCode+">"+PinCode+"</a>";
	remove.innerHTML="<label id='rmCal"+rowNumber+"' name='rmCal"+rowNumber+" ' value='false' "+" onclick='deleteRow(this.id) ' >"+"<b><a href='#' style='text-align: center;text-decoration: underline; color:#ff0000;'>Remove</a></b></label>";
	hidden.style.display="none";
	//hidden.visibility="none";
	//hiddenTD="<label id='hidden"+rowNumber+"' name="+"'hidden"+rowNumber+"' style='display: none;'>"+productId+"#"+circleiD+"#"+zonecode+"#"+CityCode+"#"+cityzonecode+"#"+PinCode+"#"+"</label>";
	//hidden.innerHTML=hiddenTD;
	/********************************** pratap *********************/
		if (window.ActiveXObject) // code for IE6, IE5
		{
		hiddenTD="<label id='hidden"+rowNumber+"' name="+"'hidden"+rowNumber+"' style='display: none;'>"+productId+"#"+circleiD+"#"+zonecode+"#"+CityCode+"#"+cityzonecode+"#"+PinCode+"#"+"</label>";
		hidden.innerHTML=hiddenTD;
		}
		else if (window.XMLHttpRequest) // code for IE7+, Firefox, Chrome, Opera, Safari
		{
		hiddenTD=productId+"#"+circleiD+"#"+zonecode+"#"+CityCode+"#"+cityzonecode+"#"+PinCode+"#";
		hidden.innerHTML=hiddenTD;
		}
		else if (window.XDomainRequest) // code for IE8
		{
			hiddenTD="<label id='hidden"+rowNumber+"' name="+"'hidden"+rowNumber+"' style='display: none;'>"+productId+"#"+circleiD+"#"+zonecode+"#"+CityCode+"#"+cityzonecode+"#"+PinCode+"#"+"</label>";
			hidden.innerHTML=hiddenTD;
		}
	/****************************** end by pratap****************************/
	
	// end of adding to data table
	
	
	// extra code for trial  just remove it
	var tableValArray= new Array(1);
	//alert("Arrays length  :"+tableValArray.length);
	var val=0;
	for(i=0;i<tableValArray.length;i++)
	{
		tableValArray[i]=new Array(1);
	// putting some values in two dimentional array
		for(k=0;k < tableValArray[i].length;k++)
		{
		tableValArray[i][0] = productId;
		//alert("tableValArray[i][0]  :"+tableValArray[i][0] );
		tableValArray[i][1] = circleiD;
		//alert("tableValArray[i][1]  :"+tableValArray[i][1] );
		tableValArray[i][2] = zonecode;
		//alert("tableValArray[i][2]  :"+tableValArray[i][2] );
		tableValArray[i][3] = CityCode;
		//alert("tableValArray[i][3]  :"+tableValArray[i][3] );
		tableValArray[i][4] = cityzonecode;
		//alert("tableValArray[i][4]  :"+tableValArray[i][4] );
		tableValArray[i][5] = PinCode;
		//alert("tableValArray[i][5]  :"+tableValArray[i][5] );
		}
	}

	document.forms[0].tableValArray.value=tableValArray;
	// end of trial codes
	productId=" "; 
	productName=" ";
	circleiD=" ";
	circleText=" ";
	document.forms[0].autoZoneCode.value =" ";
	CityCode=" ";
	cityText=" ";
	cityzonecode=" ";
	cityzoneText=" ";
	PinCode=" ";
	
	//added by Nancy.
	
	//document.forms[0].customerName.disabled =true;
	//document.forms[0].contactNo.disabled= true;
	//alert(document.forms[0].contactNo.value);
	
}

function deleteRow(removeBtnId)
{
	
document.getElementById(removeBtnId).innerHTML='';
document.getElementById(removeBtnId).innerHTML='true';
var dataTable=document.getElementById("dataTable");
var Tablerows=document.getElementById("dataTable").rows;
for(rowcount=0;rowcount<Tablerows.length ;rowcount++)
{
//===============================
if (window.ActiveXObject) // code for IE6, IE5
	{
	//alert("its IE 6");
	if(Tablerows[rowcount].cells[6].innerText == 'true')
			{
			//alert("hidden values  :"+Tablerows[rowcount].cells[7].innerText);
			dataTable.deleteRow(rowcount);
			}
	}
	else if (window.XMLHttpRequest) // code for IE7+, Firefox, Chrome, Opera, Safari
	{
	//alert("its mozila  :"+Tablerows[rowcount].cells[6].innerHTML);
		if(Tablerows[rowcount].cells[6].innerHTML.search('true') > 0 )
		{
		//alert("hidden values  :"+Tablerows[rowcount].cells[7].innerText);
		dataTable.deleteRow(rowcount);
		}
	}
	else if (window.XDomainRequest) // code for IE8
	{
	//alert("its IE 8");
		if(Tablerows[rowcount].cells[6].innerText == 'true')
		{
		//alert("hidden values  :"+Tablerows[rowcount].cells[7].innerText);
		dataTable.deleteRow(rowcount);
		}
	}


//=============================
/*if(Tablerows[rowcount].cells[6].innerText == 'true')
{
//alert("hidden values  :"+Tablerows[rowcount].cells[7].innerText);
dataTable.deleteRow(rowcount);
}
}
dataTable.deleteRow(removeBtnId);*/
}
}
// getting zoncode,city , zone for pincode
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
  		var el = document.getElementById("confirm");
		var responseValue = xmlHttpRequest.responseText;
		var productId;
	    var productName;
	    
		productLobId=document.forms[0].productLobID.value;
        productID = document.leadRegistrationFormBean.productId;
       
        productiD=productID.options[productID.options.selectedIndex].value;
        productId=productID.options[productID.options.selectedIndex].value.split("#")[0];
        
        productName=productID.options[productID.options.selectedIndex].value.split("#")[1];
       
		
		if(responseValue == "") 
		{
		//alert("Not a valid PIN code setting flag as false response value is blank ");
		//document.leadRegistrationFormBean.pinCode.focus();
		pinFlag="false";
		}
		else if(productLobId == 1 && responseValue == "NON_SERVICEABLE_PINCODE")// this block is for checking pincode is serviceable or not
		{
		//if(confirmValue=confirm("Not a Serviceable pin code, Want to save it ?"))
		disableControls();
		el.style.visibility ="visible"; 
		pinFlag=" ";
		}
		else
		{
		el.style.visibility ="hidden";
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

// populating zonecode 

function getZoneCode(zonecode)
	{
	//alert("zonecode :"+zonecode);
		var data = "methodName=getZoneCode&zonecode=" + zonecode;
		//alert("111111111111111");
		//doSyncAjax("leadRegistration.do", "GET",false, getZoneCodeForCity, data);
		doSyncAjax("leadRegistration.do", "GET", getZoneCodeForCity, data);
		
	}
		
	var getZoneCodeForCity = function()
	{
	//alert("in zonecode handler funciton ::::");
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var responseValue = xmlHttpRequest.responseText;
			//alert("responseValue :"+responseValue);
			if(responseValue == " ") 
			{
			zoneCodeAndName=" ";
			document.forms[0].zoneCodeAndName.value=" ";
			}
			else
			{
			//alert("responseValue :"+responseValue);
			//alert("responsevalue  :"+responseValue);
			zoneCodeAndName = responseValue;
			document.forms[0].zoneCodeAndName.value=responseValue;
			//alert("responseValue assigned to zoneCodeAndName:"+zoneCodeAndName);
			}
			//alert("document.forms[0].zoneCodeAndName.value 1111 :"+document.forms[0].zoneCodeAndName.value);
		}
	}
	// end of populating zonecode
// end of getting zoncode,city , zone for pincode
// saving dirty lead   starts here


//Added By Bhaskar

function hideOrShowField()
	{
		
		/* for(ii=0; ii < document.forms[0].productIds.length; ii++)
	      	 {
	        if (document.forms[0].productIds[ii].checked)
	        {
	        productIdAndProductLobId=document.forms[0].productIds[ii].value;
	        productId= productIdAndProductLobId.split("#")[0];
	        productLobId = productIdAndProductLobId.split("#")[1];
	        productName=productIdAndProductLobId.split("#")[2];
	        checked=true;
	        }
	        } */
	        
	        productLobId=document.forms[0].productLobID.value;  
	       
		if(productLobId == 2)
		{
		
			document.getElementById("rsuLine").style.display= "block";
			document.getElementById("rsuLabel").style.display= "block";
			document.getElementById("rsuField").style.display= "block";
			//document.getElementById("forQualifiedLead2").style.display= "none";
			document.getElementById("forQualifiedLead1").style.display= "none";
		}
		else
		{
			document.getElementById("rsuLabel").style.display= "none";
			document.getElementById("rsuField").style.display= "none";
			document.getElementById("rsuField").style.display= "none";
			//document.getElementById("forQualifiedLead2").style.display= "none";
		}
		}
function saveDirtyLead()
{
var productIdAndProductLobIdForDirtyLead;
		    var productLobIdForDirtyLead;
		    var productIdForDirtyLead;
		    var productNameForDirtyLead;

	    
		    document.forms[0].customerName.value = trimAll(document.forms[0].customerName.value);
 	if(isEmpty(document.forms[0].customerName))
	{
	 	alert("Please enter Customer Name.");
	 	document.forms[0].customerName.focus();
	 	return false;
	}
	else
	if(!isName(document.forms[0].customerName))
	{
	 alert("Only Alphabets and spaces are allowed in Customer Name.");
	 document.forms[0].customerName.select();
	 return false;	
    }
	
	
	else
	if(isEmpty(document.forms[0].contactNo))
	{
	 	alert("Please enter Mobile No.");
	 	document.forms[0].contactNo.select();
		return false;	
	}
	
	else if(document.forms[0].contactNo.value == 0)
		{
		alert("Please enter valid 10 digit Mobile No.");
		return false;
		}
		else if(document.forms[0].contactNo.value.substring(0,1)== '0')
		{
		alert("Mobile No. Can't start with Zero!");
		return false;
		}
	else
	if(document.forms[0].contactNo.value.length <10 || !isInteger(document.forms[0].contactNo) )
		{
		  alert("Please enter valid 10 digit Mobile No.");
		  document.forms[0].contactNo.select();
		  return false;
		}
		else
	if(isEmpty(document.forms[0].alternateContactNo))
	{
	 	alert("Please enter Alternate Mobile No.");
	 	document.forms[0].alternateContactNo.select();
		return false;	
	}
	
	else if(document.forms[0].alternateContactNo.value == 0)
		{
		alert("Please enter valid 10 digit Alternate Mobile No.");
		return false;
		}
		else if(document.forms[0].alternateContactNo.value.substring(0,1)== '0')
		{
		alert("Mobile No. Can't start with Zero!");
		return false;
		}
	else
	if(document.forms[0].alternateContactNo.value.length <10 || !isInteger(document.forms[0].alternateContactNo) )
		{
		  alert("Please enter valid 10 digit Mobile No.");
		  document.forms[0].alternateContactNo.select();
		  return false;
		}
		
		
		
		else
		{
			
			productLobIdForDirtyLead=document.forms[0].productLobID.value;
	        productID = document.leadRegistrationFormBean.productId;
	       
	        productiD=productID.options[productID.options.selectedIndex].value;
	        productIdForDirtyLead=productID.options[productID.options.selectedIndex].value.split("#")[0];
	        
	        productNameForDirtyLead=productID.options[productID.options.selectedIndex].value.split("#")[1];
	       
			
			
		   /*  for(ii=0; ii < document.forms[0].productIds.length; ii++)
	      	 {
	        if (document.forms[0].productIds[ii].checked)
	        {
	        productIdAndProductLobIdForDirtyLead=document.forms[0].productIds[ii].value;
	        productIdForDirtyLead= productIdAndProductLobIdForDirtyLead.split("#")[0];
	        productLobIdForDirtyLead = productIdAndProductLobIdForDirtyLead.split("#")[1];
	        productNameForDirtyLead=productIdAndProductLobIdForDirtyLead.split("#")[2];
	        }
	        } */
	        var circleIDForDirtyLead = document.leadRegistrationFormBean.circleId;
	        var circleiDForDirtyLead = circleIDForDirtyLead.options[circleIDForDirtyLead.options.selectedIndex].value.split("#")[0];
	        var cityCodeForDirtyLead = document.leadRegistrationFormBean.cityCode;
 			var CityCodeForDirtyLead = cityCodeForDirtyLead.options[cityCodeForDirtyLead.options.selectedIndex].value;
 			if(CityCodeForDirtyLead == '-1')
				{ 
				CityCodeForDirtyLead=" ";
				}
				else
				{
				CityCodeForDirtyLead=CityCodeForDirtyLead.split("#")[1];
				// end of populating citycode			
				// populating zonecode
				//getZoneCode(CityCodeForDirtyLead.split("#")[0]);
				// end of populating zonecode
				}
				var cityZoneCodeForDirtyLead = document.leadRegistrationFormBean.cityZoneCode;
				var cityzonecodeForDirtyLead = cityZoneCodeForDirtyLead.options[cityZoneCodeForDirtyLead.options.selectedIndex].value;
				if(cityzonecodeForDirtyLead == '-1')
				{
				cityzonecodeForDirtyLead=" ";
				}
 		var PinCodeForDirtyLead = document.forms[0].pinCode.value;
 		//alert("productIdForDirtyLead :"+productIdForDirtyLead+"  circleiDForDirtyLead:"+circleiDForDirtyLead+" CityCodeForDirtyLead :"+CityCodeForDirtyLead+" cityzonecodeForDirtyLead :"+cityzonecodeForDirtyLead+" PinCodeForDirtyLead :"+PinCodeForDirtyLead);
var allValues=productIdForDirtyLead+"#"+circleiDForDirtyLead+"#"+" "+"#"+CityCodeForDirtyLead+"#"+cityzonecodeForDirtyLead+"#"+PinCodeForDirtyLead+"=";
//alert("allValues:"+allValues);
	document.forms[0].tableValues.value = allValues;
  	// alert("form  final values  :"+document.getElementById("tableValues").value);
  	 //alert("form  final values  :"+document.forms[0].tableValues.value);
  	 //alert("dirtyLead value on form 1 :"+document.forms[0].dirtyLead.value);
    document.forms[0].methodName.value ="insertRecord";
    document.forms[0].dirtyLead.value =true;
    //alert("dirtyLead value on form 222 :"+document.forms[0].dirtyLead.value);
   	document.forms[0].submit();	
   	clearForm();	
 }		
}
function confirmYesNo(confirm)
{
enableControls();
var el = document.getElementById("confirm");
el.style.visibility ="hidden";
pinFlag=" ";
if(confirm == "true")
	{
	saveDirtyLead();
	}
	else
	{
	document.forms[0].pinCode.focus();
	}
}

function enableDisableFields()
{

	if(undefined != document.forms[0].leadStatus)
	  	 {
		    for(ii=0; ii < document.forms[0].leadStatus.length; ii++)
	      	 {
		        if (document.forms[0].leadStatus[ii].checked)
		        {
			        if(document.forms[0].leadStatus[ii].value == "false")// it is for qualified lead
			        {
			        document.getElementById("forQualifiedLead1").style.display="block";
			        document.getElementById("forQualifiedLead2").style.display="block";
			        document.forms[0].qualifiedLeadStatus.value=true;
			        }
			        else // it is for open lead
			        {
			        document.getElementById("forQualifiedLead1").style.display="none";
			        document.getElementById("forQualifiedLead2").style.display="none";
			        document.forms[0].qualifiedLeadStatus.value=false;
			        }
	          }
	        }
	  	}
}
function disableControls()
{
/* if(undefined != document.forms[0].productIds)
	  	 {
		    for(ii=0; ii < document.forms[0].productIds.length; ii++)
	      	 {
		 	 document.forms[0].productIds[ii].disabled=true;
	        }
	  	} */
document.forms[0].customerName.disabled=true;
document.forms[0].contactNo.disabled=true;
document.forms[0].address1.disabled=true;
document.forms[0].address2.disabled=true;
document.forms[0].circleId.disabled=true;
document.forms[0].cityCode.disabled=true;
document.forms[0].cityZoneCode.disabled=true;
document.forms[0].pinCode.disabled=true;
document.forms[0].alternateContactNo.disabled=true;
document.forms[0].language.disabled=true;
enableButtons="false";
}
function enableControls()
{
/* if(undefined != document.forms[0].productIds)
	  	 {
		    for(ii=0; ii < document.forms[0].productIds.length; ii++)
	      	 {
		 	 document.forms[0].productIds[ii].disabled=false;
	        }
	  	} */
document.forms[0].customerName.disabled=false;
document.forms[0].contactNo.disabled=false;
document.forms[0].address1.disabled=false;
document.forms[0].address2.disabled=false;
document.forms[0].circleId.disabled=false;
document.forms[0].cityCode.disabled=false;
document.forms[0].cityZoneCode.disabled=false;
document.forms[0].pinCode.disabled=false;
document.forms[0].alternateContactNo.disabled=false;
document.forms[0].language.disabled=false;
enableButtons="true";
}


function cleanTime()
{
	document.forms[0].appointmentDate.value="";
	document.forms[0].appointmentHour.value="";
	document.forms[0].appointmentMinute.value="";
} 


	
//Added By Bhaskar

function loadProductDropdown()
{		
	    var leadStatus=document.leadRegistrationFormBean.leadStatus.value;
	    var selectedProductLobId = document.leadRegistrationFormBean.productLobID.value;
	    
	 
	   // var url= 'ajaxSupport.do?mt=getProductBasedOnLob&selectedProductLobId='+selectedProductLobId;	
	    var data = "methodName=getProductonLobChange&selectedProductLobId=" + selectedProductLobId;
	    doSyncAjax("leadRegistration.do", "GET", getProductonLobChange, data);
	    var cityCodeeeee = document.leadRegistrationFormBean.productId;
		var cityCodeeeeeval = cityCodeeeee.options[cityCodeeeee.options.selectedIndex].value.split("#")[0];	
}


var getProductonLobChange = function()
{
	
	if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
  	{
		var xmldoc = xmlHttpRequest.responseXML.documentElement;
		if (xmldoc == null) return;
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = document.leadRegistrationFormBean.productId;
		
		selectObj.options.length = 0;
		selectObj.options[selectObj.options.length] = new Option("Select Product", "-1");
		for (var i = 0; i < optionValues.length; i++){
		selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
			}
	}
}


</script>
<style>
fieldset.field_set_main {
	border-bottom-width: 2px;
	border-top-width: 2px;
	border-left-width: 2px;
	border-right-width: 2px;
	margin: 10px;
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
  	 border:1px solid black;
}
#confirm div {
     width:250px;
     margin: 100px auto;
     background-color: #04b2e4;
     border:3px solid #ed1c24;
     padding:15px;
     text-align:center;
     font-size:2en;
}
</style>
<html:form action="/leadRegistration">
	<html:hidden property="methodName" value="" />
	<html:hidden property="createdBy" styleId="createdBy" />
	<html:hidden property="tableValues" value="" />
	<html:hidden property="autoZoneCode" value="" />
	<html:hidden property="tableValArray" value="" />
	<html:hidden property="zoneCodeAndName" value="" />
	<html:hidden property="dirtyLead" value="false" />
	<html:hidden property="qualifiedLeadStatus" value="false" />
	<html:hidden property="fourGflag" value="false"/>
	<div class="box2">
		<div class="content-upload">
			<h1>Lead Registration Form</h1>
			<center>
				<strong><FONT color="red"><html:errors />
				</FONT> <FONT color="red"><html:messages id="msg" message="true">
							<bean:write name="msg" />
						</html:messages>
				</FONT>
				</strong>
			</center>

			<%
				if (null != request.getAttribute("insertStatus")) {
						String insertStatus = request.getAttribute("insertStatus")
								.toString();
						if (!"null".equals(insertStatus)) {
							out.print("<center><h4><FONT color=\"green\">"
									+ insertStatus + "</FONT></h4></center>");
						}
					}
			%>
			<ul class="list2 form1">
			<li class="clearfix alt">
			<html:radio property="leadStatus" value='true' onclick="enableDisableFields();"/><strong>Open Lead</strong> 
			<html:radio property="leadStatus" value='false' onclick="enableDisableFields();" /><strong>Qualified Lead</strong>
			</li>
			
				<li class="clearfix"><span class="text2 fll width160"><strong>Customer Name<font color=red>*</font> </strong> </span>
				<div id="mandatoryCustomerName"></div>
					<p class="clearfix fll margin-r20">
						<span class="textbox6"> <span class="textbox6-inner">
						
								<html:text property="customerName" styleClass="textbox10" maxlength="50" />
						</span>
						</span>
					</p> <script>document.forms[0].customerName.focus();</script> <span
					class="text2 fll width120"><strong>Mobile No.<font color=red>*</font></strong> </span>
					<div id="mandatoryMobileNo."></div>
					<p class="clearfix fll">
						<span class="textbox6"> <span class="textbox6-inner">
								<html:text property="contactNo" styleClass="textbox10"  maxlength="10" />
						</span>
						</span>
					</p></li>
					
					  <li class="clearfix alt">
          <span class="text2 fll width160"><strong>Product Lob</strong><font color=red>*</font>  </span>
			 <p class="clearfix fll" style="width: 232px"> 
			 	<html:select property="productLobID" name="leadRegistrationFormBean" styleClass="select1" onchange="loadProductDropdown();">
					<html:option value="">Select Lob</html:option>
					<logic:notEmpty name="productLobList" scope="request">
						<bean:define id="lobs" 	name="productLobList" scope="request"/> 
						<html:options labelProperty="productLobName" property="productLobID" collection="lobs" />
					</logic:notEmpty>
				</html:select>
			</p>
			
						
			<span class="text2 fll width120"><strong>Product</strong> <font color=red>*</font></span>
			<p class="clearfix fll"> 
				<html:select property="productId" name="leadRegistrationFormBean" styleId="styleProductId"  styleClass="select1" onchange="makeAddressFieldsMandatory();makePincodeFieldsMandatory();Javascript: hideOrShowField();getCircleForProduct();">
						<html:option value="-1">Select Product </html:option>
						
				</html:select>
			</p>
				</li>
				
			
					
			<%-- 	<li class="clearfix alt"><strong>Product<font
							color=red>*</font> </strong>  
													
							<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Mobility</font></legend><bean:define id="productList"
						name="leadRegistrationFormBean" property="mobilityProductList"
						type="java.util.ArrayList" /> <logic:notEmpty name="productList">
						<table>
							<tr>
								<logic:iterate name="productList" id="productMobility" indexId="i"
									type="com.ibm.lms.dto.ProductDTO">
									<script type="text/javascript"> var counter=<%=i.intValue() + 1%></script>
									<tr ><html:radio property="productIds"
											value='<%=productMobility.getProductId()+"#"+productMobility.getProductLobId()+"#"+productMobility.getProductName()%>'
											onclick="makeAddressFieldsMandatory();Javascript: hideOrShowField();getCircleForProduct()" />
										<!-- makeAddressFieldsMandatory(); --> <%=productMobility.getProductName()%>
									
									<%
										if (((i.intValue() + 1) % 4) == 0) {
									%>
								
							</tr>
								<%
									}
								%>
							
								</logic:iterate>
							</tr>
						</table>
						</logic:notEmpty> </fieldset>
				<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Telemedia</font></legend>
					<bean:define id="telemediaProductList" name="leadRegistrationFormBean" property="telemediaProductList"
						type="java.util.ArrayList" /> <logic:notEmpty
						name="telemediaProductList">
						
						<table>
							<tr>
							<script type="text/javascript"> var telemediaProductArray = new Array(); </script>
						<logic:iterate name="telemediaProductList" id="product" indexId="i"
									type="com.ibm.lms.dto.ProductDTO">
									
									<script type="text/javascript"> telemediaProductArray[<%=i%>] = <%=product.getProductId()%></script>
									<script type="text/javascript"> var counter=<%=i.intValue() + 1%></script>
									<tr>
									<html:radio property="productIds" value='<%=product.getProductId()+"#"+product.getProductLobId()+"#"+product.getProductName()%>'
											onclick="makeAddressFieldsMandatory();Javascript: hideOrShowField();getCircleForProduct()" />
										<!-- makeAddressFieldsMandatory(); --> <%=product.getProductName()%>
									
									<%
										if (((i.intValue() + 1) % 4) == 0) {
									%>
								</tr>
							
								<%
									}
								%>
								
								</logic:iterate>
				</tr>
						</table>
					</logic:notEmpty>
					</fieldset>
					
					<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Others</font></legend>
					<bean:define id="otherProductsList" name="leadRegistrationFormBean" property="otherProductsList"
						type="java.util.ArrayList" /> <logic:notEmpty
						name="otherProductsList">
						
			<table>
							<tr>
						<logic:iterate name="otherProductsList" id="product" indexId="i"
									type="com.ibm.lms.dto.ProductDTO">
									<script type="text/javascript"> var counter=<%=i.intValue() + 1%></script>
								<tr>
									<html:radio property="productIds" value='<%=product.getProductId()+"#"+product.getProductLobId()+"#"+product.getProductName()%>'
											onclick="makeAddressFieldsMandatory();Javascript: hideOrShowField();getCircleForProduct()" />
										<!-- makeAddressFieldsMandatory(); --> <%=product.getProductName()%>
								
									<%
										if (((i.intValue() + 1) % 4) == 0) {
									%>
								</tr>
								<%
									}
								%>
								
								</logic:iterate></tr></table>

					</logic:notEmpty></table></li></fieldset>
					<li>
					
					<div id="confirm">
     					<div>
          					<p>Can not Register the Lead for this Pincode. Press 'Yes' to Proceed and 'No' if you want to change the pincode</p>
          					<a href='#' value="true"onclick='confirmYesNo(this.value)'>Yes</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          					<a href='#' value="false" onclick='confirmYesNo(this.value)'>No</a>
     					</div>
     				</li>
 --%>   
 					<li>
					
					<div id="confirm">
     					<div>
          					<p>Can not Register the Lead for this Pincode. Press 'Yes' to Proceed and 'No' if you want to change the pincode</p>
          					<a href='#' value="true"onclick='confirmYesNo(this.value)'>Yes</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          					<a href='#' value="false" onclick='confirmYesNo(this.value)'>No</a>
     					</div>
     				</li>  				
					<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Address</font></legend>
				<li class="clearfix"><span class="text2 fll width160"><table cellpadding="0">
							<tr>
								<td><strong>Address 1</strong>
								</td>
								<td><div id="mandatoryAddress1"></div>
								</td>
							</tr>
						</table>
				</span>
					<p class="clearfix fll margin-r20">
						<span class="textbox6"> <span class="textbox6-inner">
								<html:text property="address1" styleClass="textbox10"
									maxlength="200" />
						</span>
						</span>
					</p> <span class="text2 fll width100"><strong>Address 2</strong>
				</span>
					<p class="clearfix fll">
						<span class="textbox6"> <span class="textbox6-inner">
								<html:text property="address2" styleClass="textbox10"
									maxlength="200" />
						</span>
						</span>
					</p></li>
					<li class="clearfix"><span class="text2 fll width160">
						<table cellpadding="0">
							<tr>
								<td><strong>E-Mail</strong>
								</td>
								<td><div id="mandatoryEmail"></div>
								</td>
							</tr>
						</table>
				</span>
					<p class="clearfix fll margin-r20">
						<span class="textbox6"> <span class="textbox6-inner">
								<html:text property="email" styleClass="textbox10"
									maxlength="100" />
						</span>
						</span>
						</p>
						
						<span class="text2 fll width100"><strong>UDID<font color=red>*</font></strong> </span>
						<p class="clearfix fll">
						<span class="textbox6"> <span class="textbox6-inner">
								<html:text property="udid" styleClass="textbox10" maxlength="15" />
						</span>
						</span>
					</p>
					
					</li>
					
						<div id="forQualifiedLead1" style="display: none;">
						<li class="clearfix"><span class="text2 fll width160">
						<table
							cellpadding="0">
							<tr>
								<td><strong>LandLine No</strong>
								</td>
								
							</tr>
						</table>
				</span>
					<p class="clearfix fll margin-r20">
						<span class="textbox6"> <span class="textbox6-inner">
								<html:text property="landlineNo" styleClass="textbox10"
									maxlength="11" />
						</span>
						</span>
						</p></li>
						
						</div>
					</fieldset>
				<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Location</font></legend>	
				<li class="clearfix alt"><span class="text2 fll width160"><strong>Circle</strong><font
						color=red>*</font> </span>
					<p class="clearfix fll" style="width: 232px">
						<html:select property="circleId" name="leadRegistrationFormBean"
							styleClass="select1" onchange="getcityForCircle();">
							<html:option value="-1">Select Circle</html:option>
						</html:select>
					</p> <span class="text2 fll width120"><table cellpadding="0">
							<tr>
								<td><strong>City</strong>
								</td>
								<td><div id="mandatoryCity"></div>
								</td>
							</tr>
						</table>
				</span>
					<p class="clearfix fll">
						<html:select property="cityCode" name="leadRegistrationFormBean"
							styleClass="select1" onchange="getCityZoneOnCityChange();">
							<html:option value="">Select City</html:option>
						</html:select>
					</p></li>
				<li class="clearfix alt"><span class="text2 fll width160"><table
							cellpadding="0">
							<tr>
								<td><strong>City Zone</strong>
								</td>
								<td><div id="mandatoryZone"></div>
								</td>
							</tr>
						</table>
				</span>
					<p class="clearfix fll" style="width: 232px">
						<html:select property="cityZoneCode"
							name="leadRegistrationFormBean" styleClass="select1" onchange="getRsuForCityZoneCode();">
							<html:option value="">Select CityZone</html:option>
						</html:select>
					</p> <span class="text2 fll width100"><table cellpadding="0">
					<tr>
					<td><strong>PIN Code</strong></td>
					<td><div id="mandatoryPincode"></div></td>
					</tr>
					</table>
					</span>
					<p class="clearfix fll">
						<span class="textbox6"> <span class="textbox6-inner">
								<html:text property="pinCode" styleClass="textbox10"
									maxlength="6" />
						</span>
						</span>
					</p></li>
						
					<li class="clearfix alt" id="rsuLine" style="display:none" >
					<span class="text2 fll width160" id="rsuLabel" style="display:none"><strong>Rsu</strong></span>
					<p class="clearfix fll" style="width: 232px" id="rsuField" style="display:none">
						<html:select property="rsuCode"name="leadRegistrationFormBean" styleClass="select1">
							<html:option value="">Select</html:option>
						</html:select>
					</p></li>
						
						</fieldset>
					<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Other Details</font></legend>
				<li class="clearfix alt"><span class="text2 fll width160"><strong>Preferred
							Language</strong> </span>
					<p class="clearfix fll margin-r20">
						<span class="textbox6"> <span class="textbox6-inner">
								<html:text property="language" styleClass="textbox10"
									maxlength="25" />
						</span>
						</span>
						
					</p> <span class="text2 fll width100"><strong>Alternate Mobile No.<font color=red>*</font></strong> </span>
					<div id="mandatoryAlternateNo"></div>
					<p class="clearfix fll">
						<span class="textbox6"> <span class="textbox6-inner">
								<html:text property="alternateContactNo" styleClass="textbox10"
									maxlength="10" />
						</span>
						</span>
					</p>
					<script>document.forms[0].alternateContactNo.focus();</script>
					</li>
					
					<div name="forQualifiedLead2" id="forQualifiedLead2" style="display:none;">
					<li class="clearfix"><span class="text2 fll width160"><strong>Campaign</strong> </span>
					<p class="clearfix fll margin-r20">
						<span class="textbox6"> <span class="textbox6-inner">
								<html:text property="campaign" styleClass="textbox10"
									maxlength="25" />
						</span>
						</span>
					</p> <span class="text2 fll width120"><strong>Existing Customer</strong> </span>
					<p class="clearfix fll">
						<html:select property="existingCustomer"
							name="leadRegistrationFormBean" styleClass="select1">
							<html:option value="">--Select--</html:option>
							<html:option value="Y">Yes</html:option>
							<html:option value="N">No</html:option>
						</html:select>
						</span>
					</p>
					
					</li>
					<li class="clearfix alt"><span class="text2 fll width160">
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
				
				
				<li class="clearfix"><span class="text2 fll width160"><strong>Caf Number</strong> </span>
					<p class="clearfix fll margin-r20">
						<span class="textbox6"> <span class="textbox6-inner">
								<html:text property="caf" styleClass="textbox10"
									maxlength="25" />
						</span>
						</span>
					</p> 
					<span class="text2 fll width100"><strong>Sales Executive Number</strong> </span>
					<p class="clearfix fll">
						<span class="textbox6"> <span class="textbox6-inner">
								<html:text property="extraParam1" styleClass="textbox10"
									maxlength="10" />
						</span>
						</span>
					</p>
					</li>
					
					</li lass="clearfix"><span class="text2 fll width160"><strong><font size="2">Sales Channel Code</font></strong> </span>
					<p class="clearfix fll margin-r20">
						<span class="textbox6"> <span class="textbox6-inner">
								<html:text property="salesChannelCode" styleClass="textbox10" maxlength="15" />
						</span>
						</span>
					</p>
				
					<span class="text2 fll width120"><strong><font size="2">Appointment Time</font></strong> </span> <html:text property="appointmentDate" styleClass="tcal calender2 fll" readonly="true" />
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

						</html:select></td>	<td><img src="images/delete_icon.gif" onclick="cleanTime()"></td>
						</tr></table>
				
				</li>
				
				</div>
						<div name="forQualifiedLead1" id="forQualifiedLead1" style="display:none;">
					
				
				
					</div>
					</fieldset>
			</ul>
		</div>
		<jsp:include page="Disclaminer.jsp"></jsp:include>
	</div>
	<ul class="list2 form1">
		<li class="clearfix"><font color=green size="3">Step 1:<font color=red>"Add"</font> Products, Step 2: Click <font color=red>"Submit All"</font>.</font></li>
	</ul>
	<div class="button-area">
		<div class="button">
			<a class="red-btn" onclick="Add()"><b>Add</b>
			</a>
		</div>
		<div class="button">
			<a class="red-btn" onclick="clearForm()"><b>clear</b>
			</a>
		</div>
	</div>
	<div class="box2" >
		<div class="content-upload" id="tableData" style="display:block">
			<table id="dataTable" name="dataTable" border="1">
				<tr bgcolor="#6699FF" color="#FF00">
					<th><span class="text2 fll width100">Product</span>
					</th>
					<th><span class="text2 fll width100">Circle</span>
					</th>
					<th><span class="text2 fll width100">Zone</span>
					</th>
					<th><span class="text2 fll width100">City</span>
					</th>
					<th><span class="text2 fll width100">City Zone</span>
					</th>
					<th><span class="text2 fll width100">Pin Code</span>
					</th>
					<th><span class="text2 fll width100">&nbsp</span>
					</th>
					<th bgcolor="white"></th>
				</tr>
			</table>
		</div>
	</div>
	<div class="button-area">
		<div class="button">
			<a class="red-btn" onclick="submitForm();"><b>Submit All</b>
			</a>
		</div>
	</div>
</html:form>
