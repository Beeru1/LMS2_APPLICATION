<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="com.ibm.lms.dto.UserMstr" %>

<html:html><HEAD>

<LINK href="theme/text.css" rel="stylesheet" type="text/css">

<script language="javascript"  src="<%=request.getContextPath()+"/jScripts/ajaxCall.js"%>"></script>
<TITLE>User Master Download</TITLE>


<script>


function validate()
{


	if( document.userMasterDownloadFormBean.circleId.value == "")
	  {
	   alert("Please select Circle Type");
	   return false;
	  }
	  
	
		document.userMasterDownloadFormBean.methodName.value = "userMasterDownload";
		
		document.forms[0].submit();
	 	return true;
}



function loadCircleDropdown()
{		

		var url;
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }  
	    req.onreadystatechange = returnJson1;
	    var selectedLobId = document.userMasterDownloadFormBean.selectedproductlobId.value;
	   
	     <% UserMstr userBean= (UserMstr) request.getAttribute("userBean");%>
	var actorId = <%= userBean.getKmActorId() %>;
	var loginId = '<%= userBean.getUserLoginId() %>';
	
	
	if(actorId == '3')
	{

	
	url='ajaxSupport.do?mt=getCircleBasedOnLobForCoUser&selectedLobId='+selectedLobId+"&loginId="+ loginId;
	
	}
	else
	{
	
	
	url= 'ajaxSupport.do?mt=getCircleBasedOnLob&selectedLobId='+selectedLobId;
	}  
	
	    req.open("GET", url, true);
	    req.send(null);
}

	// called on populate circle List 
    function returnJson1() {
  
 	
    if (req.readyState == 4) {
        if (req.status == 200) {      
            var json = eval('(' + req.responseText + ')');      
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
	            var opt2 ="All"
				addOption("",opt1, selectDropDown);  
			addOption("-2",opt2, selectDropDown);  
				 	           
				for (var i = 0; i < elements.length; i++) {
				
		            addOption(elements[i].circleId, elements[i].circleName, selectDropDown);
		    
		        }	
		        return true;	        
		    }	    }	}
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

</HEAD>
  					

<html:form name="userMasterDownloadFormBean" type="com.ibm.lms.forms.UserMasterDownloadFormBean" action="/userMasterDownload" enctype="multipart/form-data" method="post">
	
	<html:hidden name="userMasterDownloadFormBean" property="methodName" />
	<html:hidden name="userMasterDownloadFormBean" property="msg" />
	
	
	  <div class="box2">
        <div class="content-upload" style="height:250px ">
        <h1><bean:message key="userMasterDownload.heading" /></h1>
        
        	<center><font color="#FF0000"><strong>
			<html:messages id="msg" message="true">
					<bean:write name="msg"/>  
			</html:messages></strong></font></center>

		<ul class="list2 form1 ">
			<li class="clearfix">
		 	<font color="red"><bean:write name="userMasterDownloadFormBean" property="msg" /></font>
		 
               <li class="clearfix alt" style="height: 40px;">
				<span class="text2 fll width160"><strong><bean:message key="assignmentDownload.lobName" /><FONT color="red" size="1">*</FONT></strong></span>
				<html:select property="selectedproductlobId" name="userMasterDownloadFormBean"  styleClass="select1" onchange="Javascript:loadCircleDropdown();" >
						<option value="" >Select LOB </option>
						
						<logic:notEmpty name="productLobList" scope="request">
						<bean:define id="lobs" 	name="productLobList" scope="request"/> 
						<html:options labelProperty="productLobName" property="productLobID" collection="lobs" />
						<option value="-2" > All </option>
					</logic:notEmpty>
				</html:select>
			</li> 
			
			 <li class="clearfix" style="height: 40px;">
				<span class="text2 fll width160"><strong><bean:message key="assignmentDownload.name" /><FONT color="red" size="1">*</FONT></strong></span>
				<html:select property="circleId" name="userMasterDownloadFormBean" styleId="createSingle" styleClass="select1" >
						<option value="" >Select Circle</option>
						<option value="-2" > All </option>
						
						<logic:notEmpty name="circleList" scope="request">
						<bean:define id="circles" 	name="circleList" scope="request"/> 
						<html:options labelProperty="circleName" property="circleId" collection="circles" />
					</logic:notEmpty>
				</html:select>
			</li> 
			
			<li class="clearfix alt" style="height: 40px;"> 
			<span class="text2 fll width160"><strong><bean:message key="createUser.UserType" /></strong></span>
					<html:select property="kmActorId" styleId="selectedActorId" name="userMasterDownloadFormBean" styleClass="select1" >
								<html:option value=""><bean:message key="createUser.select" /></html:option>
								<logic:notEmpty name="userMasterDownloadFormBean" property="actorList" >
									<bean:define id="elements" name="userMasterDownloadFormBean" property="actorList" /> 
										<html:options labelProperty="kmActorName" property="kmActorId"  collection="elements" />
								</logic:notEmpty>
					</html:select>
			</li>		
			
			
		<li class="clearfix" style="padding-left:10px;">	
		<span class="text2 fll">&nbsp;</span>
					<center><a class="red-btn" style="margin-right:10px;" tabindex="3" onclick="return validate();"><b>Download</b></a></center>
		     </li>  
         </ul>
         
            
  </div>
  </div>
	
	
	
			
</html:form>

</html:html>

