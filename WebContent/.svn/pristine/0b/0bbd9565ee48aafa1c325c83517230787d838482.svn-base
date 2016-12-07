<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@page import="com.ibm.lms.dto.UserMstr" %>
<script language="javascript"  src="<%=request.getContextPath()+"/jScripts/ajaxCall.js"%>"></script>
 <script language="javascript">
 var A_TCALCONF = {
	'cssprefix'  : 'tcal',
	'months'     : ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
	'weekdays'   : ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
	'longwdays'  : ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
	'yearscroll' : true, // show year scroller
	'weekstart'  : 0, // first day of week: 0-Su or 1-Mo
	'prevyear'   : 'Previous Year',
	'nextyear'   : 'Next Year',
	'prevmonth'  : 'Previous Month',
	'nextmonth'  : 'Next Month',
	'format'     : 'd-m-Y' // 'd-m-Y', Y-m-d', 'l, F jS Y'
};
// adding by pratap to populate all against of pin code
var pinFlag=false;
var cityzonecode=" ";
var cityzoneText=" ";
var CityCode=" ";
var cityText=" ";
var zoneCodeAndName =" ";
var zonecode=" ";
var zonename=" ";
 
function getAll()
{
 // adding by pratap for populating all data from backend as pincode has been entered
 var PinCodeText=document.forms[0].pinCode;
 var PinCode=PinCodeText.value;
 var circleIDCombo=document.forms[0].circleMstrId;
 var circleMstriD= circleIDCombo.options[circleIDCombo.options.selectedIndex].value;
 if(document.forms[0].pinCode.value != "")
	{
	if(document.forms[0].pinCode.value.length < 6 || !isInteger(document.forms[0].pinCode) )
		{
		  alert("Please enter valid 6 digit PIN.");
		  document.forms[0].pinCode.focus();
		  return false;
		}
	}
	if(circleMstriD!=""){
 if(PinCode != "")
	{
	// populating all things as pincode is entered or giving alert that pin code entered is wrong
	getDataForPinCode(PinCode,circleMstriD);
	}
	if(PinCode != "")
	if(pinFlag == false)
	{
	alert("Not a valid PIN code");
	document.forms[0].pinCode.focus();
	return false;
	}
	}
 // end of adding by pratap fro populating all data from backedn as pincode has been entered
}


function getDataForPinCode(pinCode,circleMstrId)
{
	var data = "mt=getDataForPinCode&pinCode=" + pinCode+"&circleMstrId="+circleMstrId;
	//doAjax("leadRegistration.do", "GET", true, getDataForPinCodee, data);
	doSyncAjax("ajaxSupport.do", "GET", getDataForPinCodee, data);
}

var getDataForPinCodee = function()
{
var details;
	if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
  	{
		var responseValue = xmlHttpRequest.responseText;
		if(responseValue == "") 
		{
		pinFlag=false;
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
		pinFlag=true;
		//alert("response taken as ,cityzonecode :"+cityzonecode+" cityzoneText :"+cityzoneText+" CityCode :"+CityCode+" cityText:"+cityText+" zonecode :"+zonecode+"  zonename :"+zonename+" pinFlag :"+pinFlag);
		var cizonecodeDD=document.forms[0].cityZoneCode;
		var cityDD=document.forms[0].cityCode;
		var zoneDD=document.forms[0].zoneCode;
		for(var i=cizonecodeDD.length-1; i>=0; i--)
  		 {
	         cizonecodeDD.options[i] = null;
   		}
			for(var j=cityDD.length-1; j>=0; j--)
  		 {
	         cityDD.options[j] = null;
   		}
   			for(var k=zoneDD.length-1; k>=0; k--)
  		 {
	         zoneDD.options[k] = null;
   		}
		cizonecodeDD.options[cizonecodeDD.options.length] = new Option(cityzoneText,cityzonecode);
		cityDD.options[cityDD.options.length] = new Option(cityText,CityCode);
		zoneDD.options[zoneDD.options.length] = new Option(zonename,zonecode);
		}
	}
}  
// end of adding by pratap

//zone populated by circle

      
 function populateZoneBasedOnCircleForNew()
{
	/*var circleMstrId = document.assignmentMatrixFormBean.circleMstrId.value;
	var data = "mt=getZoneBasedOnCircleFromZoneMasterNew&circleMstrId="+circleMstrId;
	doSyncAjax("ajaxSupport.do", "GET", returnJsonZoneNew, data)*/
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }  
	    req.onreadystatechange = returnJsonZoneNew;
	    var circleMstrId = document.assignmentMatrixFormBean.circleMstrId.value;
	    var url= 'ajaxSupport.do?mt=getZoneBasedOnCircleFromZoneMasterNew&circleMstrId='+circleMstrId; 
	    //alert("url :"+url);
	    req.open("GET", url, false);
	    req.send(null);
}

// called on populate zone List 
    function returnJsonZoneNew() {
    if (req.readyState == 4) {
        if (req.status == 200) {      
            var json = eval('(' + req.responseText + ')');      
			var elements = json.elements;
		cleanSelectBox("zoneStyleId");
		if (elements.length!= -1)
		 {		//alert("elements.length :"+elements.length+" elements[1].zoneCode :"+elements[1].zoneCode);
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}           	            	
	            var selectDropDown = document.getElementById("zoneStyleId");		   	
	            cleanSelectBox("zoneStyleId");	  	                      	
	            var opt1 = "Select Zone";
				addOption("",opt1, selectDropDown);       	           
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].zoneCode, elements[i].zoneName, selectDropDown);
		    
		        }	
		        return true;	        
		    }	    }	}
        }   
  

//city populated by zone

 function populateAllCityForCircle()
{
		
/*		var circleMstrId = document.assignmentMatrixFormBean.circleMstrId.value;
	var data = "mt=getCityBasedOnZoneNew&circleMstrId="+circleMstrId;
	doSyncAjax("ajaxSupport.do", "GET", returnJsonCityNew, data)*/
	
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }  
	       
	    req.onreadystatechange = returnJsonCityNew;
	    var circleMstrId = document.assignmentMatrixFormBean.circleMstrId.value;
	   
	    var url= 'ajaxSupport.do?mt=getCityBasedOnZoneNew&circleMstrId='+circleMstrId;   
	     
	    req.open("GET", url, false);
	    req.send(null);
}

// called on populate City List 
    function returnJsonCityNew() {
      
    if (req.readyState == 4) {
        if (req.status == 200) {      
            var json = eval('(' + req.responseText + ')');      
			var elements = json.elements;
			
		cleanSelectBox("cityStyleId");
		if (elements.length!= -1)
		 {	
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}           	            	
	            var selectDropDown = document.getElementById("cityStyleId");		   	
	            cleanSelectBox("cityStyleId");	  	                      	
	            var opt1 = "Select City";
				addOption("",opt1, selectDropDown);       	           
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].cityCode, elements[i].cityName, selectDropDown);
		    
		        }	
		        return true;	        
		    }	    }	}
        }   
        
        //CITY ZONE CODE POPULATED
        
        
    function populateCityZoneBasedOnCityNew()
	{
	/*var circleMstrId = document.assignmentMatrixFormBean.circleMstrId.value;
	var data = "mt=getCityZoneBasedOnCityNew&circleMstrId="+circleMstrId;
	doSyncAjax("ajaxSupport.do", "GET", returnJsonCityZoneNew, data)*/
	
		document.forms[0].pinCode.value="";
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }  
	    req.onreadystatechange = returnJsonCityZoneNew;
	    var circleMstrId = document.assignmentMatrixFormBean.circleMstrId.value;
	    var url= 'ajaxSupport.do?mt=getCityZoneBasedOnCityNew&circleMstrId='+circleMstrId;    
	    req.open("GET", url, false);
	    req.send(null);
}

// called on populate City Zone List 

    function returnJsonCityZoneNew() {
    if (req.readyState == 4) {
        if (req.status == 200) {      
            var json = eval('(' + req.responseText + ')');      
			var elements = json.elements;
			
		cleanSelectBox("cityZoneStyleId");
		if (elements.length!= -1)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}           	            	
	            var selectDropDown = document.getElementById("cityZoneStyleId");		   	
	            cleanSelectBox("cityZoneStyleId");	  	                      	
	            var opt1 = "Select City Zone";
				addOption("",opt1, selectDropDown);       	           
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].cityZoneCode, elements[i].cityZoneName, selectDropDown);
		    
		        }	
		        return true;	        
		  }	    }	}
        }


function populateZoneCityCityZoneBasedOnCircle()
{
	document.forms[0].pinCode.value="";
 	var circleMstrId = document.assignmentMatrixFormBean.circleMstrId.value;
	 if(circleMstrId!="")
	 {
	var data = "mt=populateZoneCityCityZoneBasedOnCircle&"+"circleMstrId="+circleMstrId;
	doSyncAjax("ajaxSupport.do", "GET", populateZoneCityCityZoneBasedOnCircleHandler, data);
	}
}

var populateZoneCityCityZoneBasedOnCircleHandler = function()
{
var details;
var detailsArr;
var cizonecodeDD=document.forms[0].cityZoneCode;
		var cityDD=document.forms[0].cityCode;
		var zoneDD=document.forms[0].zoneCode;
	if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
  	{
		var responseValue = xmlHttpRequest.responseText;
		if(responseValue != "") 
		{
		for(var i=cizonecodeDD.length-1; i>=0; i--)
  		 {
	         cizonecodeDD.options[i] = null;
   		}
			for(var j=cityDD.length-1; j>=0; j--)
  		 {
	         cityDD.options[j] = null;
   		}
   			for(var k=zoneDD.length-1; k>=0; k--)
  		 {
	         zoneDD.options[k] = null;
   		}
   		detailsArr=responseValue.split("=");
   		cizonecodeDD.options[cizonecodeDD.options.length] = new Option("Select CityZone","");
		cityDD.options[cityDD.options.length] = new Option("Select City","");
		zoneDD.options[zoneDD.options.length] = new Option("Select Zone","");
   		for(var i=0;i< detailsArr.length;i++ )
   		{
   		details =detailsArr[i].split("#");
		cityzonecode=details[0];
		cityzoneText=details[1];
		CityCode=details[2];
		cityText=details[3];
		zoneCodeAndName=details[4]+"#"+details[5];
		zonecode=details[4];
		zonename=details[5];
		cizonecodeDD.options[cizonecodeDD.options.length] = new Option(cityzoneText,cityzonecode);
		cityDD.options[cityDD.options.length] = new Option(cityText,CityCode);
		zoneDD.options[zoneDD.options.length] = new Option(zonename,zonecode);
   		}
		//alert("response taken as ,cityzonecode :"+cityzonecode+" cityzoneText :"+cityzoneText+" CityCode :"+CityCode+" cityText:"+cityText+" zonecode :"+zonecode+"  zonename :"+zonename+" pinFlag :"+pinFlag);
		}
		else
		{
			for(var i=cizonecodeDD.length-1; i>=0; i--)
	  		 {
		         cizonecodeDD.options[i] = null;
	   		}
				for(var j=cityDD.length-1; j>=0; j--)
	  		 {
		         cityDD.options[j] = null;
	   		}
	   			for(var k=zoneDD.length-1; k>=0; k--)
	  		 {
		         zoneDD.options[k] = null;
	   		}
			cizonecodeDD.options[cizonecodeDD.options.length] = new Option("Select CityZone","");
			cityDD.options[cityDD.options.length] = new Option("Select City","");
			zoneDD.options[zoneDD.options.length] = new Option("Select Zone","");
		}
	}
}  
 
 
 function newXMLHttpRequest1() {

    var xmlreq1 = false;

    if (window.XMLHttpRequest) {
        // Create XMLHttpRequest object in non-Microsoft browsers
        xmlreq1 = new XMLHttpRequest();

    } else if (window.ActiveXObject) {

        // Create XMLHttpRequest via MS ActiveX
        try {
            // Try to create XMLHttpRequest in later versions
            // of Internet Explorer

            xmlreq1 = new ActiveXObject("Msxml2.XMLHTTP");

        } catch (e1) {

            // Failed to create required ActiveXObject

            try {
                // Try version supported by older versions
                // of Internet Explorer

                xmlreq1 = new ActiveXObject("Microsoft.XMLHTTP");

            } catch (e2) {

                // Unable to create an XMLHttpRequest with ActiveX
            }
        }
    }

    return xmlreq1;
}

 function newXMLHttpRequest2() {

    var xmlreq2 = false;

    if (window.XMLHttpRequest) {
        // Create XMLHttpRequest object in non-Microsoft browsers
        xmlreq2 = new XMLHttpRequest();

    } else if (window.ActiveXObject) {

        // Create XMLHttpRequest via MS ActiveX
        try {
            // Try to create XMLHttpRequest in later versions
            // of Internet Explorer

            xmlreq2 = new ActiveXObject("Msxml2.XMLHTTP");

        } catch (e1) {

            // Failed to create required ActiveXObject

            try {
                // Try version supported by older versions
                // of Internet Explorer

                xmlreq2 = new ActiveXObject("Microsoft.XMLHTTP");

            } catch (e2) {

                // Unable to create an XMLHttpRequest with ActiveX
            }
        }
    }

    return xmlreq2;
}
   function cleanSelectBox(selectBox)
  	{
  	var obj = document.getElementById(selectBox);
    if (obj == null) return;
	if (obj.options == null) return;
	while (obj.options.length > 0) {
		obj.remove(0);
	}
  }

 function validate()
 {
	
	if(document.forms[0].productLobID.value == "")
	{
	 	alert("Please select Product Lob.");
	 	document.forms[0].productLobID.focus();
		return false;	
	}
	if(document.forms[0].circleMstrId.value == "")
	{
	 	alert("Please select Circle.");
	 	document.forms[0].circleMstrId.focus();
		return false;	
	}	
	if( document.forms[0].productLobID.value == "2"  || document.forms[0].productLobID.value == "5")
	{
		if(document.forms[0].cityCode.value == "" && document.forms[0].requestType.value == "normal")
	{
	 	alert("Please select City.");
	 	document.forms[0].cityCode.focus();
		return false;	
	}	
	if(document.forms[0].productLobID.value == "5"){
	if(document.forms[0].pinCode.value == "")
	{
	 	alert("Please select Pin Code.");
	 	document.forms[0].pinCode.focus();
		return false;	
	}	
	}	
	}
	if(document.forms[0].requestType.value == "")
	{
		alert("Please select User Type.");
	 	document.forms[0].requestType.focus();
		return false;
	}
 	if(!isEmpty(document.forms[0].olmID))
	{
	if(document.forms[0].olmID.value.length <8 )
		{
		  alert("Please enter valid 8 digit OLM ID.");
		  document.forms[0].olmID.focus();
		  return false;
		}
	}
	else
	{
		alert("Please enter valid 8 digit OLM ID.");
		  document.forms[0].olmID.focus();
		  return false;
	}	
	if(document.forms[0].productLobID.value != "2")		
	{
		if(document.forms[0].requestType.value == "GIS")
		{
			alert("GIS User can be created for Telemedia Product only!");
		  document.forms[0].requestType.focus();
		  return false;
		}
	}
    return true;
}
function submitForm()
 { 
   if(validate())
   {
    document.forms[0].methodName.value ="insertRecord";
   	document.forms[0].submit();
   }
   return false;
 }
 
 function clearForm()
 {
    document.forms[0].reset();
	
    return true;
  }
var getCityOnCircleChange = function()
	{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.assignmentMatrixFormBean.cityCode;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select City", "");
			for (var i = 0; i < optionValues.length; i++)
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
		}
	}

    // End of returnJson
		var done = false;
	function hideOrShowField()
	{
		var lobId = document.assignmentMatrixFormBean.productLobID;
		var prodLob =  lobId.options[lobId.options.selectedIndex].value;
		if(prodLob == 2)
		{
			document.getElementById("rsuLabel").style.display= "block";
			document.getElementById("rsuField").style.display= "block";
		}
		else
		{
			document.getElementById("rsuLabel").style.display= "none";
			document.getElementById("rsuField").style.display= "none";
		}
		if(prodLob == 2 || prodLob == 5)
		{
			 document.getElementById("mandatoryCity").innerHTML = "<font color=red>*</font>";
			 if(prodLob == 5)
			 	document.getElementById("mandatoryPinCode").innerHTML = "<font color=red>*</font>";
   			 else
   			  document.getElementById("mandatoryPinCode").innerHTML = "";
		}
		else
		{
           document.getElementById("mandatoryCity").innerHTML = "";
           document.getElementById("mandatoryPinCode").innerHTML = "";
		}
		
	}
	
	
	
function loadProductDropdown()
{		
	
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Add Files module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }	    
	    req.onreadystatechange = returnJsonProduct;
	    var selectedProductLobId = document.assignmentMatrixFormBean.productLobID.value;
	    var url= 'ajaxSupport.do?mt=getProductBasedOnLob&selectedProductLobId='+selectedProductLobId;	    
	    req.open("GET", url, true);
	    req.send(null);		
}

// called on  populate product 
function returnJsonProduct() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            var json = eval('(' + req.responseText + ')');          
			var elements = json.elements;
		cleanSelectBox("styleProductId");
		if (elements.length!=-1)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}
	            var selectDropDown = document.getElementById("styleProductId");	       	            
	            cleanSelectBox("styleProductId");
	            var opt1 = "Select Product";
				addOption("",opt1, selectDropDown);
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].productId, elements[i].productName, selectDropDown);
		        } 
		        done = true;
		        return true;		 		        
		    }		    
        }    }      }  
    // End of returnJson
      
      var req1=null; 
      
  function loadCircleDropdown()
{	
		var url;
	    request1 = newXMLHttpRequest1();
	    if (!request1) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }  
	    request1.onreadystatechange = returnJson1;
	    var selectedLobId = document.assignmentMatrixFormBean.productLobID.value;
	   
	     <% UserMstr userBean= (UserMstr) request.getAttribute("userBean");%>
	var actorId = <%= userBean.getKmActorId() %>;
	var loginId = '<%= userBean.getUserLoginId() %>';
	
	if(actorId == '3')
	{
	
	url='ajaxSupport.do?mt=getCircleBasedOnLobForCo&selectedLobId='+selectedLobId+"&loginId="+ loginId;
	
	}
	else
	{
	url= 'ajaxSupport.do?mt=getCircleBasedOnLob&selectedLobId='+selectedLobId;
	}  
	
	    request1.open("GET", url, true);
	    request1.send(null);
}

	// called on populate circle List 
    function returnJson1() {
   if (request1.readyState == 4) {
        if (request1.status == 200) {      
            var json = eval('(' + request1.responseText + ')');      
			var elements = json.elements;
		cleanSelectBox("createSingle");
	
		if (elements.length!= -1)
		 {
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}           	            	
	            var selectDropDown = document.getElementById("createSingle");		   	
	            cleanSelectBox("createSingle");	  
	                      	
	            var opt1 = "Select Circle";
				addOption("",opt1, selectDropDown);       	           
				for (var i = 0; i < elements.length; i++) {
				
		            addOption(elements[i].circleMstrId, elements[i].circleName, selectDropDown);
		    
		        }	
		        return true;	        
		    }	    }	}
        } 
     
        
 function populateZoneBasedOnCircle()
{
		document.forms[0].pinCode.value="";
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }  
	    req.onreadystatechange = returnJsonZone;
	    var circleMstrId = document.assignmentMatrixFormBean.circleMstrId.value;
	    var url= 'ajaxSupport.do?mt=getZoneBasedOnCircleFromZoneMaster&circleMstrId='+circleMstrId; 
	    //alert("url :"+url);   
	    req.open("GET", url, true);
	    req.send(null);
}

// called on populate zone List 
    function returnJsonZone() {
    if (req.readyState == 4) {
        if (req.status == 200) {      
            var json = eval('(' + req.responseText + ')');      
			var elements = json.elements;
		cleanSelectBox("zoneStyleId");
		if (elements.length!= -1)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}           	            	
	            var selectDropDown = document.getElementById("zoneStyleId");		   	
	            cleanSelectBox("zoneStyleId");	  	                      	
	            var opt1 = "Select Zone";
				addOption("",opt1, selectDropDown);       	           
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].zoneCode, elements[i].zoneName, selectDropDown);
		    
		        }	
		        return true;	        
		    }	    }	}
        }   
  

 function populateCityBasedOnZone()
{
			
		document.forms[0].pinCode.value="";
		/*
		var cizonecodeDD=document.forms[0].cityZoneCode;
		for(var i=cizonecodeDD.length-1; i>=0; i--)
	  		 {
		         cizonecodeDD.options[i] = null;
	   		}
	   		cizonecodeDD.options[cizonecodeDD.options.length] = new Option("Select CityZone","");
	   		*/
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }  
	    req.onreadystatechange = returnJsonCity;
	    var zoneCode = document.assignmentMatrixFormBean.zoneCode.value;
	    var url= 'ajaxSupport.do?mt=getCityBasedOnZone&zoneCode='+zoneCode;    
	    req.open("GET", url, true);
	    req.send(null);
}

// called on populate City List 
    function returnJsonCity() {
    if (req.readyState == 4) {
        if (req.status == 200) {      
            var json = eval('(' + req.responseText + ')');      
			var elements = json.elements;
			
		cleanSelectBox("cityStyleId");
		if (elements.length!= -1)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}           	            	
	            var selectDropDown = document.getElementById("cityStyleId");		   	
	            cleanSelectBox("cityStyleId");	  	                      	
	            var opt1 = "Select City";
				addOption("",opt1, selectDropDown);       	           
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].cityCode, elements[i].cityName, selectDropDown);
		          
		    
		        }	
		        return true;	        
		    }	    }	}
        }   
        
        
        //Multiple city zone population
        
        function populateCityZoneBasedOnZone()
{

		document.forms[0].pinCode.value="";
		/*
		var cizonecodeDD=document.forms[0].cityZoneCode;
		for(var i=cizonecodeDD.length-1; i>=0; i--)
	  		 {
		         cizonecodeDD.options[i] = null;
	   		}
	   		cizonecodeDD.options[cizonecodeDD.options.length] = new Option("Select CityZone",""); */
	    req1 = newXMLHttpRequest1();
	    if (!req1) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }  
	    req1.onreadystatechange = returnJsonCityZone;
	    var zoneCode = document.assignmentMatrixFormBean.zoneCode.value;
	    var url= 'ajaxSupport.do?mt=getCityZoneBasedOnZone&zoneCode='+zoneCode;    
	    req1.open("GET", url, true);
	    req1.send(null);
}

// called on populate City List 

    function returnJsonCityZone() {
    if (req1.readyState == 4) {
        if (req1.status == 200) {      
            var json = eval('(' + req1.responseText + ')');      
			var elements = json.elements;
			
		cleanSelectBox("cityStyleId");
		if (elements.length!= -1)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}           	            	
	            var selectDropDown = document.getElementById("cityStyleId");		   	
	            cleanSelectBox("cityStyleId");	  	                      	
	            var opt1 = "Select City";
				addOption("",opt1, selectDropDown);       	           
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].cityZoneCode, elements[i].cityZoneName, selectDropDown);
		    
		        }	
		        return true;	        
		    }	    }	}
        }   
        
        
    function populateCityZoneBasedOnCity()
	{
		document.forms[0].pinCode.value="";
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }  
	    req.onreadystatechange = returnJsonCityZone;
	    var cityCode = document.assignmentMatrixFormBean.cityCode.value;
	    var url= 'ajaxSupport.do?mt=getCityZoneBasedOnCity&cityCode='+cityCode;    
	    req.open("GET", url, true);
	    req.send(null);
}

// called on populate City Zone List 
    function returnJsonCityZone() {
    if (req.readyState == 4) {
        if (req.status == 200) {      
            var json = eval('(' + req.responseText + ')');      
			var elements = json.elements;
			
		cleanSelectBox("cityZoneStyleId");
		if (elements.length!= -1)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}           	            	
	            var selectDropDown = document.getElementById("cityZoneStyleId");		   	
	            cleanSelectBox("cityZoneStyleId");	  	                      	
	            var opt1 = "Select City Zone";
				addOption("",opt1, selectDropDown);       	           
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].cityZoneCode, elements[i].cityZoneName, selectDropDown);
		    
		        }	
		        return true;	        
		  }	    }	}
        }
        
        /*
       function populatePincodeRsuBasedOnCityZone(){
        populatePincodeBasedOnCityZone();
        populateRsuBasedOnCityZone();
        
        }
        */
	        
    function populatePincodeBasedOnCityZone()
	{
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }  
	    req.onreadystatechange = returnJsonPin;
	    var cityZoneCode = document.assignmentMatrixFormBean.cityZoneCode.value;
	    var url= 'ajaxSupport.do?mt=getPincodeBasedOnCityZone&cityZoneCode='+cityZoneCode;    
	    req.open("GET", url, true);
	    req.send(null);
}

// called on populate Pincode List 
    function returnJsonPin() {
    if (req.readyState == 4) {
        if (req.status == 200) {      
            var json = eval('(' + req.responseText + ')');      
			var elements = json.elements;
		cleanSelectBox("pinCodeId");	
		if (elements.length!= -1)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}           	            	
	            var selectDropDown = document.getElementById("pinCodeId");			   	
	            cleanSelectBox("pinCodeId");	 	  	                      	
	            var opt1 = "Select PinCode";
				addOption("",opt1, selectDropDown);              	           
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].pinCode, elements[i].pinCode, selectDropDown);
		    
		        }	
		        return true;	        
		  }	    }	}
        }
        
 function populateRsuBasedOnCityZone()
	{
	    req2 = newXMLHttpRequest2();	    
	    if (!req2) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }  
	    req2.onreadystatechange = returnJsonRsu;
	    var cityZoneCode = document.assignmentMatrixFormBean.cityZoneCode.value;
	    var url= 'ajaxSupport.do?mt=getRsuBasedOnCityZone&cityZoneCode='+cityZoneCode;    
	    req2.open("GET", url, true);
	    req2.send(null);
}

// called on populate Rsu List 
    function returnJsonRsu() {
    if (req2.readyState == 4) {
        if (req2.status == 200) {      
            var json = eval('(' + req2.responseText + ')');      
			var elements = json.elements;	
		cleanSelectBox("rsuStyleId");
		if (elements.length!= -1)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}           	            	
	            var selectDropDown = document.getElementById("rsuStyleId");			   		 
	            cleanSelectBox("rsuStyleId");	  	                      	
	            var opt1 = "Select RSU";
				addOption("",opt1, selectDropDown);            	           
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].rsuCode, elements[i].rsuCode, selectDropDown);		    
		        }	
		        return true;	        
		  }	    }	}
        }
        
          // Added By Bhaskar
      
        function loadRequestCategoryDropdownLob()
	{		
	
		var url;
	    req1 = newXMLHttpRequest1();
	    if (!req1) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }  
	    req1.onreadystatechange = returnJson4;
	     var productLobID = document.assignmentMatrixFormBean.productLobID.value;
	      var selectedProductId = document.assignmentMatrixFormBean.selectedProductId.value;
	
	    if(productLobID !="" && selectedProductId == "")
	  	{
	  
	  	url='ajaxSupport.do?mt=getRequestCategoryBasedOnProductLob&productLobID='+productLobID;
	  	}
	 	
	    req1.open("GET", url, true);
	    req1.send(null);
}

	// called on populate request List 
    function returnJson4() {
   alert("req1.readyState :" + req1.readyState);
  
    if (req1.readyState == 4) {
        if (req1.status == 200) {      
            var json = eval('(' + req1.responseText + ')');      
			var elements = json.elements;
			 alert("elements :" + elements);
		cleanSelectBox("requestCatStyleId");
	
		if (elements.length!= -1)
		 {
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}           	            	
	            var selectDropDown = document.getElementById("requestCatStyleId");	
	            cleanSelectBox("requestCatStyleId");	  
	                      	
	            var opt1 = "Select RequestCategory";
				addOption("",opt1, selectDropDown);       	           
				for (var i = 0; i < elements.length; i++) {
				
		            addOption(elements[i].requestCategoryId, elements[i].requestCategoryName, selectDropDown);
		    
		        }	
		        return true;	        
		    }	    }	}
        } 
     
     
      function loadRequestCategoryDropdownProduct()
	{	
	
		var url;
	    req1 = newXMLHttpRequest1();
	    if (!req1) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }  
	    req1.onreadystatechange = returnJson4;
	      var productLobID = document.assignmentMatrixFormBean.productLobID.value;
	     
	    var selectedProductId = document.assignmentMatrixFormBean.selectedProductId.value;
	    //alert(selectedProductId);
	    
	 	if(selectedProductId !=""){
	 	 	
	  		url='ajaxSupport.do?mt=getRequestCategoryBasedOnProduct&selectedProductId='+selectedProductId+'&productLobID='+productLobID;
	  		}
	  	 
	    req1.open("GET", url, true);
	    req1.send(null);
}

	// called on populate request List 
    function returnJson4() {
   
  
    if (req1.readyState == 4) {
        if (req1.status == 200) {      
            var json = eval('(' + req1.responseText + ')');      
			var elements = json.elements;
		cleanSelectBox("requestCatStyleId");
	
		if (elements.length!= -1)
		 {
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}           	            	
	            var selectDropDown = document.getElementById("requestCatStyleId");	
	            cleanSelectBox("requestCatStyleId");	  
	                      	
	            var opt1 = "Select RequestCategory";
				addOption("",opt1, selectDropDown);       	           
				for (var i = 0; i < elements.length; i++) {
				
		            addOption(elements[i].requestCategoryId, elements[i].requestCategoryName, selectDropDown);
		    
		        }	
		        return true;	        
		    }	    }	}
        } 
     
      //End by Bhaskar
      
      
	</script>
	
	<html:form action="/assignmentMatrix">
	<html:hidden property="methodName" value=""/>
	<html:hidden property="createdBy" styleId="createdBy"/>	
	<html:hidden property="assignment" value="false"/>	
	
      <div class="box2">
        <div class="content-upload">
          <h1>Assignment Matrix Creation Form</h1> 
			 <center> <strong><FONT color="red"><html:errors/></FONT>
			 <FONT color="green"><html:messages id="msg" message="true"><bean:write name="msg"/></html:messages></FONT></strong></center>
			  <tr>
				<td colspan="2" align="left" class="error">
					<strong> 
          			<logic:present name = "assignmentMatrixFormBean" property="msg"> 
          			<logic:notEmpty  name = "assignmentMatrixFormBean" property="msg">               		
          			<bean:write property="msg" name="assignmentMatrixFormBean"/>  <br>                          
             		</logic:notEmpty> 
             		</logic:present>
             		
            		</strong>
            	</td>
		</tr>
		 <ul class="list2 form1"> 
		 
		
		 
        <li class="clearfix">
          	<span class="text2 fll width160"><strong>Olm ID<font color=red>*</font> </strong> </span>
          	<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner">
          		<html:text property="olmID" styleClass="textbox10" maxlength="12" /></span></span></p>
          		
			 
		    </li>
          	
            <li class="clearfix alt">
          <span class="text2 fll width160"><strong>Product Lob</strong><font color=red>*</font>  </span>
			 <p class="clearfix fll" style="width: 232px"> 
			 	<html:select property="productLobID" name="assignmentMatrixFormBean" styleClass="select1" onchange="Javascript: hideOrShowField();loadRequestCategoryDropdownLob();loadProductDropdown();loadCircleDropdown();">
					<html:option value="">Select Lob</html:option>
					<logic:notEmpty name="productLobList" scope="request">
						<bean:define id="lobs" 	name="productLobList" scope="request"/> 
						<html:options labelProperty="productLobName" property="productLobID" collection="lobs" />
					</logic:notEmpty>
				</html:select>
			</p>
			
			          
          	<span class="text2 fll width120"><strong>Other IDs<font color=red></font></strong> </span>
			 <p class="clearfix fll"> <span class="textbox6"> <span class="textbox6-inner">
			<html:text property="otherIDs"  styleClass="textbox10" /></span></span></p>

		</li>
          <li class="clearfix">
						
			<span class="text2 fll width160"><strong>Product</strong></span>
			<p class="clearfix fll" style="width: 232px"> 
				<html:select property="selectedProductId" name="assignmentMatrixFormBean" styleId="styleProductId" onchange="" styleClass="select1" onchange="loadRequestCategoryDropdownProduct();">
						<html:option value="">Select Product </html:option>
						<logic:notEmpty name="assignmentMatrixFormBean" property="productNameList" >
									<bean:define id="elementsProduct" name="assignmentMatrixFormBean" property="productNameList" /> 
										<html:options labelProperty="productName" property="productId"  collection="elementsProduct" />
						</logic:notEmpty>
				</html:select>
			</p>
			 	
          <span class="text2 fll width120"><strong>Circle</strong><font color=red>*</font>  </span>
			 <p class="clearfix fll" > 
			 	<html:select property="circleMstrId" name="assignmentMatrixFormBean" styleId="createSingle"  styleClass="select1" onchange="populateZoneBasedOnCircleForNew();populateAllCityForCircle();populateCityZoneBasedOnCityNew()">
					<html:option value="">Select Circle</html:option>
					<logic:notEmpty name="circleList" scope="request">
						<bean:define id="circles" 	name="circleList" scope="request"/> 
						<html:options labelProperty="circleName" property="circleMstrId" collection="circles" />
					</logic:notEmpty>
				</html:select>
			</p>   
			 </li>  
          
          <li class="clearfix  alt">     	
          
            <span class="text2 fll width160"><strong>Zone</strong> </span>
          	<p class="clearfix fll" style="width: 232px"> 
          		<html:select property="zoneCode" name="assignmentMatrixFormBean" styleId="zoneStyleId" styleClass="select1" onchange="populateCityBasedOnZone();populateCityZoneBasedOnZone()">
					<html:option value="">Select Zone</html:option>
					<logic:present name="zoneList" scope="request">
					<logic:notEmpty  name="zoneList" scope="request">
						<bean:define id="zones" 	name="zoneList" scope="request"/> 
						<html:options labelProperty="zoneName" property="zoneCode" collection="zones" />
					</logic:notEmpty>
					</logic:present>
				</html:select>
			</p>
          
              <span class="text2 fll width120"><table cellpadding="0"><tr><td><strong>City</strong></td><td><div id="mandatoryCity"></div></td></tr></table></span>
          	<p class="clearfix fll" > 
          		<html:select property="cityCode" name="assignmentMatrixFormBean" styleId="cityStyleId" styleClass="select1" onchange="populateCityZoneBasedOnCity()">
					<html:option value="">Select City</html:option>
					<logic:present name="cityList" scope="request">
					<logic:notEmpty  name="cityList" scope="request">
						<bean:define id="cities" 	name="cityList" scope="request"/> 
						<html:options labelProperty="cityName" property="cityCode" collection="cities" />
					</logic:notEmpty>
					</logic:present>
				</html:select>
			</p>  	

          </li>  
          
          <li class="clearfix ">
                  
            <span class="text2 fll width160"><strong>City Zone</strong> </span>
          	<p class="clearfix fll" style="width: 232px"> 
          		<html:select property="cityZoneCode" name="assignmentMatrixFormBean" styleId="cityZoneStyleId" styleClass="select1" onchange="populateRsuBasedOnCityZone()">
					<html:option value="">Select CityZone</html:option>
					<logic:present name="cityZoneList" scope="request">
					<logic:notEmpty  name="cityZoneList" scope="request">
						<bean:define id="zones" 	name="cityZoneList" scope="request"/> 
						<html:options labelProperty="cityZoneName" property="cityZoneCode" collection="zones" />
					</logic:notEmpty>
					</logic:present>
				</html:select>
			</p>
			
          	<span class="text2 fll width120"><table cellpadding="0"><tr><td><strong>PIN Code</strong></td><td><div id="mandatoryPinCode"></div></td></tr></table></span>
         	<p class="clearfix fll"> <span class="textbox6"> <span class="textbox6-inner">
			 	<html:text property="pinCode"  styleClass="textbox10" maxlength="6"  onblur="getAll()"/></span></span></p>     
   
   		</li>
        <li class="clearfix alt">     	
          	<span class="text2 fll width160"><strong>User Type <font color=red>*</font></strong> </span>
			 <p class="clearfix fll" style="width: 232px"> 
			 
			 	<html:select property="requestType" name="assignmentMatrixFormBean" styleClass="select1" >
					<html:option value="">Select</html:option>
					<html:option value="normal">normal</html:option>
					<html:option value="GIS">GIS</html:option>
				</html:select>
				
		   </p>
		
          	<span class="text2 fll width120" id="rsuLabel" style="display:none"><strong>RSU</strong> </span>
          		<p class="clearfix fll" id="rsuField" style="display:none"> 
			 	<html:select property="rsuCode" name="assignmentMatrixFormBean" styleId="rsuStyleId" styleClass="select1" >
					<html:option value="">Select </html:option>
					<logic:present name="rsuList" scope="request">
					<logic:notEmpty  name="rsuList" scope="request">
						<bean:define id="leadStatus"	name="rsuList" scope="request"/> 
						<html:options labelProperty="rsuCode" property="rsuCode" collection="leadStatus" />
					</logic:notEmpty>
					</logic:present>
				</html:select>
			</p>
			</li>
			
			  <li class="clearfix alt">     	
          	<span class="text2 fll width160"><strong>Request Category</strong> </span>
			 <p class="clearfix fll" style="width: 232px"> 
			 
			 	<html:select property="requestCategoryId" name="assignmentMatrixFormBean" styleId="requestCatStyleId" styleClass="select1" >
					<html:option value="">Select RequestCategory</html:option>
					<logic:present name="requestCategoryList" scope="request">
					<logic:notEmpty  name="requestCategoryList" scope="request">
						<bean:define id="leadStatus"	name="requestCategoryList" scope="request"/> 
						<html:options labelProperty="requestCategoryName" property="requestCategoryId" collection="leadStatus" />
					</logic:notEmpty>
					</logic:present>
				</html:select>
				
		   </p></li>
			
		<li class="clearfix "> 
			
				<span class="text2 fll width160" ><strong>Level1 CC</strong> </span>
			 <p class="clearfix fll"> <span class="textbox6"> <span class="textbox6-inner">
			<html:text property="levellCC"  styleClass="textbox10" /></span></span></p>

      <span class="text2 fll width120" ><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Level2 CC</strong> </span>
			 <p class="clearfix fll"> <span class="textbox6"> <span class="textbox6-inner">
			<html:text property="level2CC"  styleClass="textbox10" /></span></span></p>
			</li>
       
       <li class="clearfix alt ">  
       <span class="text2 fll width160" ><strong>Level3 CC</strong> </span>
			<p class="clearfix fll"> <span class="textbox6"> <span class="textbox6-inner">
			 <html:text property="level3CC"  styleClass="textbox10" /></span></span></p>
       
        
       <span class="text2 fll width120" ><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Level4 CC</strong> </span>
			 <p class="clearfix fll"> <span class="textbox6"> <span class="textbox6-inner">
			<html:text property="level4CC"  styleClass="textbox10" /></span></span></p>
       </li>
       </ul>
    
        </div>
       
      </div>
      <div class="button-area">
            <div class="button"><a class="red-btn" onclick="return submitForm()"><b>submit</b></a></div>
            <div class="button"><a class="red-btn" onclick="clearForm()"><b>clear</b></a></div>
          </div>    
</html:form>
