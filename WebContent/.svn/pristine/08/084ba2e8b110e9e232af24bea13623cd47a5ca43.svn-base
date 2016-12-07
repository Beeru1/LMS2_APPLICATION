<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="kmUserBean" name="USER_INFO"  type="com.ibm.lms.dto.UserMstr" scope="session" />
<bean:define id="loginActorId" name="LOGIN_USER_ACTOR_ID" type="java.lang.String" scope="session" />

<LINK href="theme/css.css" rel="stylesheet" type="text/css">
<LINK href=./jsp/theme/css.css rel="stylesheet" type="text/css">

<script language="javascript">
	var path = '<%=request.getContextPath()%>';
	var port = '<%= request.getServerPort()%>';
	var serverName = '<%=request.getServerName() %>';
</script>
	
<script language="JavaScript" type="text/javascript">

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
  }

function validateData(){

	if(document.kmUserMstrFormBean.selectedLobId.value=="")
	{
		alert("Please Select A LOB");
		return false;
	}

	if(document.kmUserMstrFormBean.kmActorId.value=="")
	{
		alert("Please Select A User Type");
		return false;
	}
	
		if(document.kmUserMstrFormBean.kmActorId.value != "2"
		 && document.kmUserMstrFormBean.kmActorId.value != "3"
		 && document.kmUserMstrFormBean.circleMstrId.value==""){
			alert("Please Select Circle"); 
			return false;
		
		}
		
		if(document.kmUserMstrFormBean.kmActorId.value == "3" && document.getElementById('createMultiple').options[document.getElementById('createMultiple').selectedIndex].value==""){		
			alert("Please Select Circle(s)"); 
			return false;
		
		}
		
	

	var mob=document.kmUserMstrFormBean.userMobileNumber.value;
	var len=mob.length;
	var searchChars="`~!$^&*()=+><{}[]+|=?':;\\\"-&-,@ ";
	var searchChars1="`~!$^_&*()=+><{}[]+|=?':;\\\"-&-,@ ";
	var reg=/^[0-9a-zA-Z_]*$/;
	var regmob=/^[0-9]*$/;
	var names=/^[a-zA-Z_]*$/;

	if(isEmpty(document.kmUserMstrFormBean.userLoginId)){
			alert("Please enter User Login Id");
			document.kmUserMstrFormBean.userLoginId.focus();
			return false;
	}
	if(document.kmUserMstrFormBean.userLoginId.value.length < 8){
			alert("User Login Id should not be less then 8 characters");
			document.kmUserMstrFormBean.userLoginId.focus();
			return false;
	}
	if(!reg.test(document.forms[0].userLoginId.value)){
		alert("Special characters except underscore not allowed in User Login Id");
		document.forms[0].userLoginId.value="";
		document.forms[0].userLoginId.focus();
		return false;	
	}

	if(isEmpty(document.kmUserMstrFormBean.userFname)){
			alert("Please enter User First Name");
			document.kmUserMstrFormBean.userFname.focus();
			return false;
	}
	if(!names.test(document.forms[0].userFname.value)){
		alert("Please Enter only Characters for First name");
		document.forms[0].userFname.value="";
		document.forms[0].userFname.focus();
		return false;	
	}
	
	if(isEmpty(document.kmUserMstrFormBean.userLname)){
			alert("Please enter User Last Name");
			document.kmUserMstrFormBean.userLname.focus();
			return false;
	}  
	if(!names.test(document.forms[0].userLname.value)){
		alert("Please Enter only Characters for Last name");
		document.forms[0].userLname.value="";
		document.forms[0].userLname.focus();
		return false;	
	}
	if(!isEmpty(document.forms[0].userMname))
	{
		if(!names.test(document.kmUserMstrFormBean.userMname.value)){
			alert("Special Characters are not Allowed in Middle Name");
			document.kmUserMstrFormBean.userMname.focus();
			document.kmUserMstrFormBean.userMname.value="";
			return false;	
		}
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
	  	

  	 if(confirm("Do you want to create a new user?"))
  	 {
  	 
		document.kmUserMstrFormBean.roleId.value =	document.forms[0].kmActorId.value;
  	 	document.forms[0].methodName.value="insert";
		return true;
     }
      else{
        clearFields();
        return false;
      } 	
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


function clearFields(){
	
	var form=document.forms[0];
	
	form.userLoginId.value="";
	form.userFname.value="";
	form.userMname.value="";
	form.userLname.value="";
	form.userMobileNumber.value="";
	form.userEmailid.value="";
	form.kmActorId.value="";
	
	form.userLoginId.focus();
	
	return true;
	
}   
function focusSet()
{

	document.kmUserMstrFormBean.userLoginId.focus();
}

function disableFields()
{

	if(document.kmUserMstrFormBean.kmActorId.value == "3"){	
		//alert("Circle Coordinator:: multiple circle");
		document.getElementById("multipleCircleId").style.visibility="visible";
		document.getElementById("singleCircleId").style.visibility="hidden";
		document.getElementById("cordinatorTypeId").style.visibility="hidden";	
		document.getElementById("singleZoneId").style.visibility="hidden";
		
	}
	else if(document.kmUserMstrFormBean.kmActorId.value == "15" || document.kmUserMstrFormBean.kmActorId.value == "4" || document.kmUserMstrFormBean.kmActorId.value == "6"){
		//alert("Zonal Coordinator: single circle");
		document.getElementById("multipleCircleId").style.visibility="hidden";
		document.getElementById("singleCircleId").style.visibility="visible";
		document.getElementById("cordinatorTypeId").style.visibility="visible";		
	}
	else if (document.kmUserMstrFormBean.kmActorId.value == "2"){
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
	
	/*
	if(document.kmUserMstrFormBean.kmActorId.value == "2"){	
		document.kmUserMstrFormBean.circleMstrId.value = "0";
		document.kmUserMstrFormBean.selectedCircleId.value = document.kmUserMstrFormBean.circleMstrId.value;
		document.getElementById("initialSelectBox").disabled = true;
	}
	else{
		document.getElementById("initialSelectBox").disabled = false;	
	}
	
	*/
	
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
        
            var json = eval('(' + req.responseText + ')');
            
            //alert("json "+json);        
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
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].zoneId, elements[i].zoneName, selectDropDown);
		        }	
		        return true;	        
		    }	    
        }
       }
        
    }

</script>


<script language="JavaScript" type="text/JavaScript">
function limitText(textArea, length) {
if (textArea.value.length > length) {
alert ("Please limit comments length to "+length+" characters.");
textArea.value = textArea.value.substr(0,length);
}
}

var req=null; 

function $id(id) {
	return document.getElementById(id);
	
}



</script>

<html:form action="/kmUserMstr">

	<html:hidden name="kmUserMstrFormBean" property="methodName" />
	<html:hidden name="kmUserMstrFormBean" property="roleId" />
	<html:hidden name="kmUserMstrFormBean" property="selectedCircleId" />

	<html:hidden property="kmLoginActorId"/>
	
 <div class="box2">
  <div class="content-upload">
	<table width="100%" align="center" cellspacing="0"
		cellpadding="0">		
		
			<td colspan="4" class="content-upload"><h1><bean:message
				key="createUser.NewUser" /></h1></td>
		</tr>	
		
		<tr>
				<td colspan="2" align="left" class="error">
					<strong style="margin-left: 10px"> 
          			<html:messages id="msg" message="true">
                 		<bean:write name="msg"/> <bean:write name="kmUserMstrFormBean" property="lobIdMsg"/> <br>                          
             		</html:messages>
            		</strong>
            	</td>
		</tr>
		<tr >
			<td colspan="4" class="error" align="left"><strong><html:errors/></strong></td>
		</tr>
		
		<tr>
		
		</table>  	
		 <ul class="list2 form1 ">
		 
		 <!-- Added By Parnika for LMS Phase2 -->	
		 		
		<li class="clearfix">
		<table id="table_0">
			<tr>
			 <td height="30px">
			 <span class="text2 fll width160"><strong><bean:message key="createUser.LobType" /></strong></span>
					<td ><html:select property="selectedLobId" styleId="initialSelectBox" name="kmUserMstrFormBean" styleClass="select1" onchange="javascript:loadCircleDropdown();">
								<html:option value=""><bean:message key="createUser.select" /></html:option>
								<logic:notEmpty name="kmUserMstrFormBean" property="lobList" >
									<bean:define id="lobs" name="kmUserMstrFormBean" property="lobList" /> 
										<html:options labelProperty="lobName" property="lobId"  collection="lobs" />
								</logic:notEmpty>
					</html:select></td>
			</tr>
		 </table>					
		</li>
		
		<li class="clearfix alt">
          	<span class="text2 fll width160"><strong><bean:message
				key="createUser.UserLoginId" /></strong> </span>
			<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77" property="userLoginId" maxlength="20"  /></span> </span> </p>
		</li>
		<li class="clearfix">
          	<span class="text2 fll width160"><strong><bean:message
				key="createUser.FirstName" /></strong> </span>
			<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77" property="userFname" maxlength="40" /></span> </span> </p>
		</li>
		<li class="clearfix alt">
          	<span class="text2 fll width160"><strong><bean:message
				key="createUser.MiddleName" /></strong></span>
		<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77" property="userMname" maxlength="40" /></span> </span> </p>
			</li>
		<li class="clearfix">
          	<span class="text2 fll width160"><strong><bean:message
				key="createUser.LastName" /></strong></span>
		<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77"
				property="userLname" maxlength="40" /></span> </span> </p>
			</li>
		<li class="clearfix alt">
          	<span class="text2 fll width160"><strong><bean:message key="createUser.E-Mail" /></strong></span>
		<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77"
				property="userEmailid" maxlength="40" /></span> </span> </p>
			</li>
		<li class="clearfix">
          	<span class="text2 fll width160"><strong><bean:message key="createUser.Mobile" /></strong></span>
		<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77"
				property="userMobileNumber" maxlength="10" /></span></span> </p>
			</li>
			
		<li class="clearfix alt" id="partner" >
          	<span class="text2 fll width160"><strong><bean:message key="createUser.Partner" /></strong></span>
			<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77"
				property="partner" maxlength="11" /></span> </span> </p>
		</li>


		<li class="clearfix" id="actor"> 
		<table id="table_0">
			<tr>
			 <td height="30px">
			<span class="text2 fll width160"><strong><bean:message key="createUser.UserType" /></strong></span>
					<td ><html:select property="kmActorId" styleId="selectedActorId" name="kmUserMstrFormBean" styleClass="select1" onchange="disableFields()">
								<html:option value=""><bean:message key="createUser.select" /></html:option>
								<logic:notEmpty name="kmUserMstrFormBean" property="actorList" >
									<bean:define id="actors" name="kmUserMstrFormBean" property="actorList" /> 
										<html:options labelProperty="kmActorName" property="kmActorId"  collection="actors" />
								</logic:notEmpty>
					</html:select></td>
			</tr>
		 </table>
		</li>		
	 <li class="clearfix" id="singleCircleId" style="visibility: hidden;">
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
		
		<li class="clearfix" id="multipleCircleId" style="visibility: hidden;">
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
		
		<li class="clearfix alt" id="cordinatorTypeId" style="visibility: hidden;">
		<table id="table_0">
			<tr>
			 <td height="30px">
			 <span class="text2 fll width160"><strong><bean:message key="createUser.CoordinatorType" /></strong></span>
					<td ><html:select property="selectedTypeId" styleId="coordinatorType" name="kmUserMstrFormBean" styleClass="select1" onchange="javascript:loadZoneDropdown();">
								<html:option value=""><bean:message key="createUser.select" /></html:option>
								<html:option value="1">Circle-Zone Coordinator</html:option>
								<html:option value="2">City-Zone Coordinator</html:option>
					</html:select></td>
			</tr>
		 </table>					
		</li>
		
		<li class="clearfix" id="singleZoneId" style="visibility: hidden;">
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
					
	<!-- End of changes By Parnika for LMS Pase2 -->	
		
	 </ul>
	 
	 <div class="button-area">
            <div class="button">
              <input class="submit-btn1 red-btn" type="submit" value="" onclick="return validateData();" />
            </div>
            <div class="button"> <input class="red-btn" onclick="return clearFields();" type="button" value="clear" />  </div>
          </div>
	  </div>
	  	<jsp:include page="Disclaminer.jsp"></jsp:include>
	  </div>
</html:form>
