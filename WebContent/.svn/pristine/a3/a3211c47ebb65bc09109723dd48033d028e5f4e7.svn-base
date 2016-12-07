
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="loginActorId" name="LOGIN_USER_ACTOR_ID" type="java.lang.String" scope="session" />
<bean:define id="kmUserBean" name="USER_INFO"  type="com.ibm.lms.dto.UserMstr" scope="session" />

<LINK href="theme/text.css" rel="stylesheet" type="text/css">

<script language="JavaScript" src="jScripts/KmValidations.js"
	type="text/javascript">
	</script>
<script language="JavaScript" type="text/javascript">


function validateData(){

		var reg=/^[0-9]*$/;
	var mob=document.kmUserMstrFormBean.userMobileNumber.value;
	
	var names=/^[a-zA-Z]*$/;
	var len=mob.length;
	
	
	var searchChars="`~!$^&*()=+><{}[]+|=?':;\\\"-&-,@ ";
	var searchChars1="`~!$^_&*()=+><{}[]+|=?':;\\\"-&-,@ ";
	
	var regmob=/^[0-9]*$/;
	
	
	
	if(isEmpty(document.forms[0].userFname))
	{
			alert("Please enter User First Name");
			document.kmUserMstrFormBean.userFname.focus();
			return false;
	}
	if(isEmpty(document.forms[0].userLname))
	{
			alert("Please enter User Last Name");
			document.kmUserMstrFormBean.userLname.focus();
			return false;
	}
	if(isEmpty(document.kmUserMstrFormBean.userEmailid))
			{
				alert("Please enter Email-ID");
				document.kmUserMstrFormBean.userEmailid.focus();
				return false;
			}
	else
	{
		if(!checkEmail(document.kmUserMstrFormBean.userEmailid.value))
				{
					alert("Please enter Valid Email-ID");
					document.kmUserMstrFormBean.userEmailid.focus();
					return false;
				}
	}			
	
	
	if(isEmpty(document.kmUserMstrFormBean.userMobileNumber))
	{
		alert("Please enter Mobile Number");
		document.kmUserMstrFormBean.userMobileNumber.focus();
		return false;
	}	

	if(!regmob.test(document.kmUserMstrFormBean.userMobileNumber.value)){
		alert("Please enter Valid User Mobile Number");
		document.kmUserMstrFormBean.userMobileNumber.select();		
		return false;	
	}
	//Bug resolved MASDB00064245
	if(mob=='00000000000' || mob=='0000000000'){
		alert("Please enter Valid User Mobile Number");
		document.kmUserMstrFormBean.userMobileNumber.select();		
		return false;	
	}
	if(len<10||len>11)
	{		if(!isEmpty(document.kmUserMstrFormBean.userMobileNumber))
			{	
				
				alert("Please enter Valid User Mobile Number");
				document.kmUserMstrFormBean.userMobileNumber.focus();
				return false;
			}
	
	} 
	
	if(!names.test(document.kmUserMstrFormBean.userFname.value)){
		alert("Special Characters are not Allowed in First Name");
		document.kmUserMstrFormBean.userFname.focus();
		document.kmUserMstrFormBean.userFname.value="";
		return false;	
	}
	//Bug resolved MASDB00064782
	if(!isEmpty(document.forms[0].userMname))
	{
		if(!names.test(document.kmUserMstrFormBean.userMname.value)){
			alert("Special Characters are not Allowed in Middle Name");
			document.kmUserMstrFormBean.userMname.focus();
			document.kmUserMstrFormBean.userMname.value="";
			return false;	
		}
	}
	
	if(!names.test(document.kmUserMstrFormBean.userLname.value)){
		alert("Special Characters are not Allowed in Last Name");
		document.kmUserMstrFormBean.userLname.focus();
		document.kmUserMstrFormBean.userLname.value="";
		return false;	
	}
	if(!isEmailAddress(document.kmUserMstrFormBean.userEmailid)){
			if(!isEmpty(document.kmUserMstrFormBean.userEmailid))
			{
				alert("Please enter Valid Email-ID");
				document.kmUserMstrFormBean.userEmailid.focus();
				return false;
			}	
	} 
	
	if(!reg.test(document.kmUserMstrFormBean.userMobileNumber.value)){
		alert("Please enter Valid User Mobile Number");
		document.kmUserMstrFormBean.userMobileNumber.focus();
		document.kmUserMstrFormBean.userMobileNumber.value="";
		return false;	
	}
	if(len<10||len>11)
	{		if(!isEmpty(document.kmUserMstrFormBean.userMobileNumber))
			{	
				
				alert("Please enter Valid User Mobile Number");
				document.kmUserMstrFormBean.userMobileNumber.focus();
				return false;
			}
	
	} 

	if(document.kmUserMstrFormBean.selectedActorId.value == "-1"){
		alert("Please select User Type");
		return false;	
	}
	
	 /* if(document.kmUserMstrFormBean.circleMstrId.value==""){
		alert("Please Select Circle"); 
		return false;
	} */
	
	if(document.kmUserMstrFormBean.selectedActorId.value == "15" 
	|| document.kmUserMstrFormBean.selectedActorId.value == "4" 
	|| document.kmUserMstrFormBean.selectedActorId.value == "6"){
	
	 if(document.kmUserMstrFormBean.selectedTypeId.value==""){
		alert("Please Select Zone Type"); 
		return false;
	}
	if(document.kmUserMstrFormBean.selectedZoneId.value==""){
		alert("Please Select Zone"); 
		return false;
	}
	
	
	}
	
	document.forms[0].methodName.value="editUser";
	document.forms[0].submit();	
	
}
function checkEmail(email) 
	{
		var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (!filter.test(email)) 
		{
			return false;
		}
		return true;
	}

function disableFields()
{

if(document.kmUserMstrFormBean.selectedActorId.value == "3"){	
		//alert("Circle Coordinator:: multiple circle");
		document.getElementById("multipleCircleId").style.visibility="visible";
		document.getElementById("singleCircleId").style.visibility="hidden";
		document.getElementById("cordinatorTypeId").style.visibility="hidden";	
		document.getElementById("singleZoneId").style.visibility="hidden";
		
	}
	else if(document.kmUserMstrFormBean.selectedActorId.value == "15" || document.kmUserMstrFormBean.selectedActorId.value == "4" || document.kmUserMstrFormBean.selectedActorId.value == "6"){
		//alert("Zonal Coordinator: single circle");
		document.getElementById("multipleCircleId").style.visibility="hidden";
		document.getElementById("singleCircleId").style.visibility="visible";
		document.getElementById("cordinatorTypeId").style.visibility="visible";		
	}
	else if (document.kmUserMstrFormBean.selectedActorId.value == "2"){
		//alert("Other actor: No circle");
		document.getElementById("multipleCircleId").style.visibility="hidden";
		document.getElementById("singleCircleId").style.visibility="hidden";
		document.getElementById("cordinatorTypeId").style.visibility="hidden";		
		document.getElementById("singleZoneId").style.visibility="hidden";
		document.kmUserMstrFormBean.circleMstrId.value = "0";
		document.kmUserMstrFormBean.selectedCircleId.value = document.kmUserMstrFormBean.circleMstrId.value;
	}		else{
		//alert("Other actor: No circle");
		document.getElementById("multipleCircleId").style.visibility="hidden";
		document.getElementById("singleCircleId").style.visibility="visible";
		document.getElementById("cordinatorTypeId").style.visibility="hidden";		
		document.getElementById("singleZoneId").style.visibility="hidden";
		//document.kmUserMstrFormBean.circleMstrId.value = "0";
		//document.kmUserMstrFormBean.selectedCircleId.value = document.kmUserMstrFormBean.circleMstrId.value;
	}
	

	if(document.kmUserMstrFormBean.selectedActorId.value == "2"){	
		document.kmUserMstrFormBean.circleId.value = "0";
		document.kmUserMstrFormBean.selectedCircleId.value = document.kmUserMstrFormBean.circleId.value;
		document.getElementById("initialSelectBox").disabled = true;
	}
	else{
		document.getElementById("initialSelectBox").disabled = false;	
	}
	
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
function loadCircleDropdown()
{
	//alert("loadCircleDropdown");
	    req = newXMLHttpRequest();
	    var url;
	    if (!req) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }
	    
	    req.onreadystatechange = returnJson;
	    var selectedLobId = document.kmUserMstrFormBean.selectedLobId.value;
		//alert("selectedlob id :::::"+selectedLobId);

		var actorId = <%= kmUserBean.getKmActorId() %>;
		var loginId = '<%= kmUserBean.getUserLoginId() %>';
	
		if(actorId == '3'){
			url='ajaxSupport.do?mt=getCircleBasedOnLobForCo&selectedLobId='+selectedLobId+"&loginId="+ loginId;
		}
		else
		{
	    	url= 'ajaxSupport.do?mt=getCircleBasedOnLob&selectedLobId='+selectedLobId;
	    }
	    
	    req.open("GET", url, true);
	    req.send(null);
		

}

// called on populate circle List 
    function returnJson() {
    if (req.readyState == 4) {
        if (req.status == 200) {
        
            var json = eval('(' + req.responseText + ')');
            
            //alert("json "+json);        
			var elements = json.elements;

		cleanSelectBox("createMultiple");
		cleanSelectBox("createSingle");

		if (elements.length!= -1)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}           
	            	
	            var selectDropDown = document.getElementById("createMultiple");	
	            var selectDropDown1 = document.getElementById("createSingle");	
	            
	            cleanSelectBox("createMultiple");	
	            cleanSelectBox("createSingle");	  
	                      	
	            var opt1 = "Select Circles";
				addOption("",opt1, selectDropDown);  
				addOption("",opt1, selectDropDown1);       	           
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].circleMstrId, elements[i].circleName, selectDropDown);
		            addOption(elements[i].circleMstrId, elements[i].circleName, selectDropDown1);
		        }	
		        return true;	        
		    }	    
        }
       }
        
    }   
    
    
     function selectAll()
  	{
 
    var obj = document.getElementById('createMultiple');
     
  	if(obj.value == "-3")
  	{	
  		obj.options[0].selected = false;	
  	 	for (var i = 1; i < obj.options.length; i++) 
  	 	{ 
           obj.options[i].selected = true; 
        }
     }  
     
  else{
  
  	obj.options[0].selected = false;  	 
  var selLength = obj.length;
  var selectedText = new Array();
  var selectedValues = new Array();
  var selectedCount = 0;
  var selectedText1 = new Array();
  var selectedValues1 = new Array();
  var selectedCount1 = 0;

  var i;

  // Find the selected Options in reverse order
  // and delete them from the 'from' Select.
  for(i=selLength-1; i>=0; i--)
  { 
    if(obj.options[i].selected)
    {
   
      selectedText[selectedCount] = obj.options[i].text;
      selectedValues[selectedCount] = obj.options[i].value;
      //deleteOption(obj, i);
      selectedCount++;
      
    }
    else{
     selectedText1[selectedCount1] = obj.options[i].text;
      selectedValues1[selectedCount1] = obj.options[i].value;
      //deleteOption(obj, i);
      selectedCount1++;
    
    }
  }
   var str =[];
  // Add the selected text/values in reverse order.
  // This will add the Options to the 'to' Select
  // in the same order as they were in the 'from' Select.
  for(i=selectedCount-1; i>=0; i--)  {
       
    //addOption(document.getElementById('saveMultiple'), selectedText[i], selectedValues[i]);
  
    str.push(selectedText[i]);
  
    
  }
  document.kmUserMstrFormBean.selectedvalues.value = str;
 
  
  }
  
  }
  
 

    function loadZoneDropdown()
{
		//alert("loadZoneDropdown");	
		if(document.kmUserMstrFormBean.selectedTypeId.value!="" ){
			document.getElementById("singleZoneId").style.visibility="visible";
		}		
				
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }
	    
	    req.onreadystatechange = returnJson1;
	    var selectedTypeId = document.kmUserMstrFormBean.selectedTypeId.value;
	    var circleMstrId = document.kmUserMstrFormBean.circleMstrId.value;
		//alert("selectedTypeId id :::::"+selectedTypeId);
		//alert("circle Mstr Id id :::::"+circleMstrId);
	    var url= 'ajaxSupport.do?mt=getZoneBasedOnCircle&selectedTypeId='+selectedTypeId+'&circleMstrId='+circleMstrId;
	    
	    req.open("GET", url, true);
	    req.send(null);
		

}

// called on populate Zone List 
    function returnJson1() {
    if (req.readyState == 4) {
        if (req.status == 200) {
         //alert("json ");  
            var json = eval('(' + req.responseText + ')');
            
           // alert("json "+json);        
			var elements = json.elements;

		cleanSelectBox("createSingleZone");
		if (elements.length!= -1)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	     }           
	            	
	            var selectDropDown = document.getElementById("createSingleZone");	
	            
	            cleanSelectBox("createSingleZone");	 
	                      	
	            var opt1 = "Select Zone";
				addOption("-2",opt1, selectDropDown);         	           
				for (var i = 1; i < elements.length; i++) {
		            addOption(elements[i].zoneId, elements[i].zoneName, selectDropDown);
		        }	
		        return true;	        
		    }	    
        }
       }
        
    }
    
   function enableFields(){
   
   	 var obj = document.getElementById('createMultiple');
  obj.options[0].selected = false;  	 
  var selLength = obj.length;
  var selectedText = new Array();
  var selectedValues = new Array();
  var selectedCount = 0;
  var selectedText1 = new Array();
  var selectedValues1 = new Array();
  var selectedCount1 = 0;

  var i;

  // Find the selected Options in reverse order
  // and delete them from the 'from' Select.
  for(i=selLength-1; i>=0; i--)
  { 
    if(obj.options[i].selected)
    {
   
      selectedText[selectedCount] = obj.options[i].text;
      selectedValues[selectedCount] = obj.options[i].value;
      //deleteOption(obj, i);
      selectedCount++;
      
    }
    }
     var str =[];
     
  // Add the selected text/values in reverse order.
  // This will add the Options to the 'to' Select
  // in the same order as they were in the 'from' Select.
  for(i=selectedCount-1; i>=0; i--)  {       
    //addOption(document.getElementById('saveMultiple'), selectedText[i], selectedValues[i]);  
    str.push(selectedText[i]);  
    
  }
  document.kmUserMstrFormBean.selectedvalues.value = str;
    
   // alert(document.kmUserMstrFormBean.selectedActorId.value);
    if(document.kmUserMstrFormBean.selectedActorId.value == "15" 
    || document.kmUserMstrFormBean.selectedActorId.value == "4" 
    || document.kmUserMstrFormBean.selectedActorId.value == "6"){
		//alert("Zonal Coordinator: single circle");
		//document.getElementById("multipleCircleId").style.visibility="hidden";
		document.getElementById("singleCircleId").style.visibility="visible";
		document.getElementById("cordinatorTypeId").style.visibility="visible";	
		document.getElementById("singleZoneId").style.visibility="visible";	
	}
	 else if(document.kmUserMstrFormBean.selectedActorId.value == "2"  || document.kmUserMstrFormBean.selectedActorId.value == "3"){
		//alert("Zonal Coordinator: single circle");
		//document.getElementById("multipleCircleId").style.visibility="hidden";
		document.getElementById("singleCircleId").style.visibility="hidden";
		
	}
	else{	
	document.getElementById("singleCircleId").style.visibility="visible";
	}
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
</script>
<body onload="enableFields();">
<html:form action="/kmUserMstr"  >

	<html:hidden name="kmUserMstrFormBean" property="userId" />
	<html:hidden name="kmUserMstrFormBean" property="userLoginId" />
	<html:hidden name="kmUserMstrFormBean" property="methodName"  /> 
	<html:hidden name="kmUserMstrFormBean" property="selectedCircleId" />
	<html:hidden name="kmUserMstrFormBean" property="selectedLobId" />
	<html:hidden name="kmUserMstrFormBean" property="selectedvalues" />
	<div class="box2">
        <div class="content-upload">

			<h1><bean:message key="updateUser.updateUser" /></h1>
	<table align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left"><br>
			<strong> <html:errors /></strong></td>
		</tr>
		<tr>
			<td align="center"><br>
			<logic:notEmpty name="kmUserMstrFormBean" property="userStatus">
			<FONT color="red"><bean:write name="kmUserMstrFormBean" property="userStatus" /></FONT>
		</logic:notEmpty></td>
		</tr>
		<tr> <td align="center"><logic:notEmpty name="kmUserMstrFormBean" property="userStatus">
			<FONT color="red"><bean:write name="kmUserMstrFormBean" property="userStatus" /></FONT>
		</logic:notEmpty></TD>
		</tr>
	</table>
	<ul class="list2 form1 "  >
	
			<li class="clearfix alt">	
			 <span class="text2 fll width160"><strong>Lob Name</strong></span>
					<td >
					<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text property="lobName"
				name="kmUserMstrFormBean" disabled="true" styleClass="textbox77" /></span> </span> </p>
								
					</td>
		</li>
		<li class="clearfix" id="actor" >
				<span class="text2 fll width160"><strong><bean:message
				key="updateUser.UserLoginId" /></strong></span>
			<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text property="userLoginId"
				name="kmUserMstrFormBean" disabled="true" styleClass="textbox77" /></span> </span> </p>
		</li>
		<li class="clearfix alt" id="actor" >
				<span class="text2 fll width160"><strong><bean:message
				key="updateUser.FirstName" /></strong></span>
			<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="width100"
				property="userFname" maxlength="40" styleClass="textbox77" /></span> </span> </p>
		</li>
		<li class="clearfix" id="actor" >
				<span class="text2 fll width160"><strong><bean:message
				key="updateUser.MiddleName" /></strong></span>
			<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="width100"
				property="userMname" maxlength="40" styleClass="textbox77" /></span> </span> </p>
		</li>
		<li class="clearfix alt" id="actor" >
				<span class="text2 fll width160"><strong><bean:message
				key="updateUser.LastName" /></strong></span>
			<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="width100"
				property="userLname" maxlength="40" styleClass="textbox77" /></span> </span> </p>
		</li>
		<li class="clearfix" id="actor" >
				<span class="text2 fll width160"><strong><bean:message key="updateUser.E-Mail" /></strong></span>
			<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="width100"
				property="userEmailid" maxlength="40" styleClass="textbox77" /></span> </span> </p>
		</li>
		<li class="clearfix alt" id="actor" >
				<span class="text2 fll width160"><strong><bean:message key="createUser.Mobile" /></strong></span>
					<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77"
				property="userMobileNumber" maxlength="10" /></span></span> </p>
				
		</li>
		
		
		<li class="clearfix " id="actor" >
				<span class="text2 fll width160"><strong>Partner Name</strong></span>
			<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="width100"
				property="partnerName" maxlength="11" styleClass="textbox77"/></span> </span> </p>
		</li>
		<li class="clearfix alt" id="actor" >
				<span class="text2 fll width160"><strong><bean:message key="updateUser.Status" /></strong></span>
			<html:select property="status" styleClass="select1">
				<html:option value="A">
					<bean:message key="updateUser.Active" />
				</html:option>
				<html:option value="D">
					<bean:message key="updateUser.Deactive" />
				</html:option>
			</html:select></li>
			
		
		<logic:notEqual name="loginActorId"  value="8">
		 <li class="clearfix ">
				<span class="text2 fll width160"><strong>User Type<FONT color="red" size="1">*</FONT></strong></span>
				<html:select property="selectedActorId" name="kmUserMstrFormBean" styleId="selectedActorId" styleClass="select1" onchange="disableFields()">
						<option value="-1" >Select User Type</option>
						<logic:notEmpty name="kmUserMstrFormBean" property="actorList" >
									<bean:define id="elements" name="kmUserMstrFormBean" property="actorList" /> 
										<html:options labelProperty="actorName" property="actorId"  collection="elements" />
						</logic:notEmpty>
				</html:select>
		</li> 
		</logic:notEqual>
		<logic:equal  name="loginActorId"  value="8">
			<html:hidden name="kmUserMstrFormBean" property="selectedActorId" />
			<li class="clearfix  " id="actor" >
				<span class="text2 fll width160"><strong>User Type</strong></span>
					<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77"
				property="role" maxlength="15" readonly="true"/></span></span> </p>
		
		</logic:equal>
		<logic:equal  name="kmUserMstrFormBean"  property="selectedActorId"  value="3">
			<li class="clearfix alt" id="multipleCircleId" >
			
			<span class="text2 fll width160"><strong><bean:message key="createUser.selectCircles"/></strong></span>
			  <p class="clearfix fll margin-r20"> <span class="textbox6-inner">   
				<html:select property="createMultiple" styleId="createMultiple" multiple="multiple" size="6" style="width:160px;"  onchange="javascript:selectAll();">
				<option value="" selected="selected">--Select Circle(s)--</option>
					<option value="-3" >--All Circles--</option> 
					<logic:notEmpty name="kmUserMstrFormBean" property="circleList" >
								<bean:define id="elements" name="kmUserMstrFormBean" property="circleList" /> 
								<html:options labelProperty="circleName" property="circleMstrId"  collection="elements" />					
					</logic:notEmpty>
				</html:select>
			  </span> </p> 
		</li>
		</logic:equal>
	
		 <li class="clearfix alt" id="singleCircleId" style="visibility: hidden;">
		<table id="table_0">
			<tr>
			 <td height="30px">
			 <span class="text2 fll" style="width: 155px;">Select Circle <FONT color="red" size="1">*</FONT></span></td>
					<td ><html:select property="circleMstrId" styleId="createSingle" name="kmUserMstrFormBean" styleClass="select1">
								<html:option value="">Select Circle</html:option>
								<logic:notEmpty name="kmUserMstrFormBean" property="circleList" >
									<bean:define id="circles" name="kmUserMstrFormBean" property="circleList" /> 
										<html:options labelProperty="circleName" property="circleMstrId"  collection="circles" />
								</logic:notEmpty>
					</html:select></td>
			</tr>
		 </table>					
		</li>
		
			
			<li class="clearfix alt" id="multipleCircleId" style="visibility: hidden;">
			<span class="text2 fll width160"><strong><bean:message key="createUser.selectCircles"/></strong></span>
			  <p class="clearfix fll margin-r20"> <span class="textbox6-inner">   
				<html:select property="createMultiple" styleId="createMultiple" multiple="multiple" size="6" style="width:160px;" onchange="javascript:selectAll();">
				<option value="" selected="selected">--Select Circle(s)--</option>
				<!--  <option value="-3" >--All Circles--</option>  -->
					<logic:notEmpty name="kmUserMstrFormBean" property="circleList" >
								<bean:define id="elements" name="kmUserMstrFormBean" property="circleList" /> 
								<html:options labelProperty="circleName" property="circleMstrId"  collection="elements" />					
					</logic:notEmpty>
				</html:select>
			  </span> </p> 
		</li>	
	
	
		
<%-- 		<logic:notEqual name="loginActorId"  value="8">
		<li class="clearfix alt">
				<span class="text2 fll width160"><strong>Circle Name<FONT color="red" size="1">*</FONT></strong></span>
				<html:select property="circleId" name="kmUserMstrFormBean" styleId="initialSelectBox" styleClass="select1">
						<option value="" >Select Circle</option>
						<logic:notEmpty name="kmUserMstrFormBean" property="circleList" >
									<bean:define id="circles" name="kmUserMstrFormBean" property="circleList" /> 
										<html:options labelProperty="circleName" property="circleId"  collection="circles" />
						</logic:notEmpty>
				</html:select>
		</li> 
		</logic:notEqual>
		
		<logic:equal  name="loginActorId"  value="8">
			<html:hidden name="kmUserMstrFormBean" property="circleId" />
			<li class="clearfix " id="circles" >
				<span class="text2 fll width160"><strong>Circle Name</strong></span>
					<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77"
				property="circleName" maxlength="15" readonly="true"/></span></span> </p>
		
		</logic:equal> --%>
		<li class="clearfix " id="cordinatorTypeId" style="visibility: hidden;">
		<table id="table_0">
			<tr>
			 <td height="30px">
			 <span class="text2 fll width160"><strong><bean:message key="createUser.CoordinatorType" /></strong></span>
					<td ><html:select property="selectedTypeId" styleId="coordinatorType" name="kmUserMstrFormBean" styleClass="select1" onchange="javascript:loadZoneDropdown();">
								<html:option value="" ><bean:message key="createUser.select" /></html:option>
								<html:option value="1">Circle-Zone Coordinator</html:option>
								<html:option value="2">City-Zone Coordinator</html:option>
					</html:select></td>
			</tr>
		 </table>					
		</li>
		
		<li class="clearfix alt" id="singleZoneId" style="visibility: hidden;">
		<table id="table_0">
			<tr>
			 <td height="30px">
			 <span class="text2 fll" style="width: 155px;">Select Zone <FONT color="red" size="1">*</FONT></span></td>
					<td ><html:select property="selectedZoneId" styleId="createSingleZone" name="kmUserMstrFormBean" styleClass="select1">
								<html:option value="">Select Zone</html:option>
								<logic:notEmpty name="kmUserMstrFormBean" property="zoneList" >
									<bean:define id="zones" name="kmUserMstrFormBean" property="zoneList" /> 
										<html:options labelProperty="zoneName" property="zoneId"  collection="zones" />
								</logic:notEmpty>
					</html:select></td>
			</tr>
		 </table>					
		</li>
			

		<li class="clearfix " style="padding-left:170px;">
			<span class="text2 fll">&nbsp;</span>
		    <input class="red-btn fll"  style="margin-right:10px;" value="Update" alt="Update" onclick="validateData();"/>		
		</li>
		
		</ul>
	
	</div>
	</div>
</html:form>
</body>

