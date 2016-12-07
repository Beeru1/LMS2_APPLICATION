<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<LINK href="theme/text.css" rel="stylesheet" type="text/css">
	
<script language="JavaScript" type="text/javascript">
function validate()
{
	if(document.kmUserMstrFormBean.userLoginId.value == ""){
	alert("Olm ID is required!");
	return false;
	}
	if(document.kmUserMstrFormBean.selectedLobId.value == ""){
	alert("Lob ID is required!");
	return false;
	}
	document.kmUserMstrFormBean.methodName.value="searchUser";
	return true;
}
var xmlhttp;

function getLob(){

document.getElementById("lobDiv").style.visibility="visible";
document.getElementById("search").style.visibility="visible";

var olmId = document.forms[0].userLoginId.value;

 req = newXMLHttpRequest();
	    var url;
	    if (!req) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }
	    
	    req.onreadystatechange = returnJson;
	    	
		url= 'ajaxSupport.do?mt=getLobForUser&olmId='+olmId;
	   
	    
	    req.open("GET", url, true);
	    req.send(null);
		

}

// called on populate LOB List 
    function returnJson() {
    if (req.readyState == 4) {
        if (req.status == 200) {
        
            var json = eval('(' + req.responseText + ')');
            
                
			var elements = json.elements;

		cleanSelectBox("initialSelectBox");
		

		if (elements.length!= -1)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}           
	            	
	            var selectDropDown = document.getElementById("initialSelectBox");	           	
	            
	            cleanSelectBox("initialSelectBox");		            	  
	                      	
	            var opt1 = "Select Lob";
				addOption("",opt1, selectDropDown);
				       	           
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].lobId, elements[i].lobName, selectDropDown);
		            
		        }	
		        return true;	        
		    }	    
        }
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

<html:form action="/kmUserMstr">
<html:hidden name="kmUserMstrFormBean" property="userId" />
<html:hidden name="kmUserMstrFormBean" property="methodName" value="searchUser" /> 
<html:hidden name="kmUserMstrFormBean" property="selectedUserId" value=""/>

<div class="box2">
 <div class="content-upload">
  <h1><bean:message key="viewUser.viewUser" /></h1>
  <strong style="margin-left: 10px"> <html:errors /></strong>
  
  <br><br>
	 <ul class="list2 form1" style="height: 100px">   
		 <li class="clearfix ">
          		<span class="text2 fll width100"><strong>Olm ID<font color=red>*</font> </strong> </span>
          	<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner">
          		<html:text property="userLoginId" styleClass="textbox77" maxlength="12" /></span></span></p>
          		<span class="text2 fll width50"  >&nbsp;</span><div class="button" >
			<input type = "button" styleClass="red-btn"  value="Go" onclick="return getLob()"/></div>
			<div id = "lobDiv" style="visibility: hidden;" >
          		 <span class="text2 fll  width100"><strong>Lob Id<font color=red>*</font></strong></span>
					<td ><html:select property="selectedLobId" styleId="initialSelectBox" name="kmUserMstrFormBean" styleClass="select1" >
								<html:option value=""><bean:message key="createUser.select" /></html:option>
								<logic:notEmpty name="kmUserMstrFormBean" property="lobList" >
									<bean:define id="lobs" name="kmUserMstrFormBean" property="lobList" /> 
										<html:options labelProperty="lobName" property="lobId"  collection="lobs" />
								</logic:notEmpty>
					</html:select>
			</div>
			</li>
				 <li class="clearfix " id="search" style="visibility: hidden;">
			<span class="text2 fll width160"  >&nbsp;</span><div class="button" >
			<html:submit styleClass="red-btn"  value="Search" onclick="return validate()"/></div>
			</li>
</ul> 
 
<logic:notEmpty name="kmUserMstrFormBean" property="userFname">
<h1 style="height:26px">User Detail</h1>
	<ul class="list2 form1 ">
	
		<li class="clearfix alt" id="actor" >
				<span class="text2 fll width160"><strong><bean:message
				key="updateUser.FirstName" /></strong></span>
			<p class="clearfix fll margin-r20" style="width: 232px"><bean:write name="kmUserMstrFormBean" property="userFname" />
		</p>
				<span class="text2 fll width120"><strong><bean:message key="updateUser.LastName" /></strong></span>
			<p class="clearfix fll"> 
			<bean:write name="kmUserMstrFormBean" property="userLname" /></p>
		</li>
		<li class="clearfix" id="actor" >
				<span class="text2 fll width160"><strong><bean:message key="updateUser.E-Mail" /></strong></span>
			<p class="clearfix fll margin-r20" style="width: 232px"> 
			<bean:write name="kmUserMstrFormBean" property="userEmailid" /></p>
			<span class="text2 fll width120"><strong><bean:message key="createUser.Mobile" /></strong></span>
					<p class="clearfix fll"> 
					<bean:write name="kmUserMstrFormBean" property="userMobileNumber" /></p> 
		</li>
		
		<li class="clearfix alt" id="actor" >
				<span class="text2 fll width160"><strong>Role</strong></span>
			<p class="clearfix fll margin-r20"  style="width: 232px"> 
			<bean:write name="kmUserMstrFormBean" property="role" /></p>			
		</li>
		<li class="clearfix " id="actor" >
				<span class="text2 fll width160"><strong>Partner Name</strong></span>
			<p class="clearfix fll margin-r20" > <bean:write name="kmUserMstrFormBean" property="partnerName" />	</p>		
				
		</li>
		
		<li class="clearfix alt" id="actor" >
				<span class="text2 fll width160"><strong>Lob Name</strong></span>
			<p class="clearfix fll margin-r20" > <bean:write name="kmUserMstrFormBean" property="lobId" />	</p>		
				
		</li>
		
		<li class="clearfix " id="actor" >				
				<span class="text2 fll width160"><strong>Circle Name</strong></span>
				<p class="clearfix fll margin-r20" > 
				<bean:write name="kmUserMstrFormBean" property="circleName" /></p>
		</li>
	
		<li class="clearfix alt" id="actor" >
				<span class="text2 fll width160"><strong>Zone Name</strong></span>
			<p class="clearfix fll margin-r20"  style="width: 232px"> 
			<bean:write name="kmUserMstrFormBean" property="zoneName" /></p>			
				
		</li>
		
			<li class="clearfix " id="actor" >						
			<span class="text2 fll width160"><strong>City Zone Name</strong></span>
			<p class="clearfix fll margin-r20"> 
			<bean:write name="kmUserMstrFormBean" property="cityZoneName" /></p>
				
		</li>
		
			<li class="clearfix alt " id="actor" >
				<span class="text2 fll width160"><strong><bean:message key="updateUser.Status" /></strong></span>
				<p class="clearfix fll margin-r20" style="width: 232px"> 
				<bean:write name="kmUserMstrFormBean" property="status" /></p>
		</li>
			<li class="clearfix alt" id="actor" >
				<span class="text2 fll width160"><strong><bean:message key="updateUser.createdBy"/></strong></span>
			<p class="clearfix fll margin-r20">
			<bean:write name="kmUserMstrFormBean" property="createdBy" /></p>
		<logic:present name="kmUserMstrFormBean" property="createdBy" >
			<span class="text2 fll width100"><strong>On</strong></span> 
			<p>  <bean:write name="kmUserMstrFormBean" property="createdDt" /> </p>
			  </logic:present>
		</li>
	
			<li class="clearfix" id="actor" >
				<span class="text2 fll width160"><strong><bean:message
				key="updateUser.updatedBy" /></strong></span>
			<p class="clearfix fll margin-r20">
			<bean:write name="kmUserMstrFormBean" property="updatedBy" /></p>
			<logic:present name="kmUserMstrFormBean" property="updatedBy" >
			<span class="text2 fll width100"><strong>On</strong></span> 
			  <p><bean:write name="kmUserMstrFormBean" property="updatedDt" /> </p>
			  </logic:present>
		</li>
	
		<logic:equal value="Y" name="kmUserMstrFormBean" property="levelCheck">
		<li class="clearfix ">
				<span class="text2 fll width200"><strong></strong></span>
			<p class="clearfix fll margin-r20">
			<html:submit styleClass="red-btn" value="Edit" onclick="document.kmUserMstrFormBean.methodName.value='initEdit';document.kmUserMstrFormBean.selectedUserId.value= document.kmUserMstrFormBean.userId.value"/>
		</li>
		</logic:equal>
		
 	  </ul>
 	  </logic:notEmpty>
	</div>
  </div>
</html:form>