<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<LINK href="theme/css.css" rel="stylesheet" type="text/css">
<LINK href=./jsp/theme/css.css rel="stylesheet" type="text/css">
<script language="javascript">
function handleEnter (field, event) {
		var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
		if (keyCode == 13) {
			var i;
			for (i = 0; i < field.form.elements.length; i++)
				if (field == field.form.elements[i])
					break;
			i = (i + 1) % field.form.elements.length;
			return false;
		} 
		else
		return true;
} 

function limitText(textArea, length) {
	if (textArea.value.length > length) {
		alert ("Please limit comments length to "+length+" characters.");
	textArea.value = textArea.value.substr(0,length);
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
  }
  
  
  function validateData(){
  
  
  		if(isEmpty(document.agencyMappingForm.agencyName)){
		alert("Please Enter Agency Name"); 
		return false;
		}

		if(isEmpty(document.agencyMappingForm.agencyDescription)){
		alert("Please Enter Agency Description"); 
		return false;
		}
		
		if(isEmpty(document.agencyMappingForm.agencyPath)){
		alert("Please Enter Agency Path"); 
		return false;
		}
		
		if(isEmpty(document.agencyMappingForm.agencyClass)){
		alert("Please Enter Agency Class"); 
		return false;
		}
		
		if(isEmpty(document.agencyMappingForm.username)){
		alert("Please Enter Username"); 
		return false;
		}
		
		if(isEmpty(document.agencyMappingForm.password)){
		alert("Please Enter Password"); 
		return false;
		}
		
		if(isEmpty(document.agencyMappingForm.confirmPassword)){
		alert("Please Enter Confirm Password"); 
		return false;
		}
		if(document.getElementById('createMultiple').options[document.getElementById('createMultiple').selectedIndex].value=="-2"){
		alert("Please Select Circle(s)"); 
		return false;
		}
		if(document.agencyMappingForm.password.value != document.agencyMappingForm.confirmPassword.value)
		{
			alert("The Password and Confirm password do not match!");
			document.agencyMappingForm.password.value="";
			document.agencyMappingForm.confirmPassword.value="";			
			return false;
		} 
	 	document.agencyMappingForm.methodName.value="insert";	
	 	document.agencyMappingForm.submit();

  }
  
  function check(element) {

        //alert(element.checked); 
        if(element.checked){
        	document.agencyMappingForm.defaultCheck.value="Yes";
        	//alert(document.agencyMappingForm.defaultCheck.value);
        }
        else{
        	document.agencyMappingForm.defaultCheck.value="No";
        	//alert(document.agencyMappingForm.defaultCheck.value);
        }
} 

</script>


<html:form action="/createAgencyMapping">

	<html:hidden name="agencyMappingForm" property="methodName" />
	<html:hidden name="agencyMappingForm" property="message" />
	<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%= session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY) %>">
	
 <div class="box2">
  <div class="content-upload">
	<table width="100%" align="center" cellspacing="0" cellpadding="0">		
		<tr>
				<td colspan="2" align="center" class="error">
					<strong> 
          			<html:messages id="msg" message="true">
                 		<bean:write name="msg"/>  <br>                          
             		</html:messages>
            		</strong>
            	</td>
		</tr>
		<tr >
			<td colspan="4" class="error" align="center"><strong><html:errors/></strong></td>
		</tr>

		<tr>
			<td colspan="4" class="content-upload"><h1><bean:message
				key="createAgency.create" /></h1></td>
		</tr>		
		</table>  	
		 <ul class="list2 form1 ">
		 <li class="clearfix">
		 	<strong><font color="red"><bean:write name="agencyMappingForm" property="message" /></font></strong>
		 </li>

		<li class="clearfix">
				<span class="text2 fll width160"><strong><bean:message key="createAgency.agencyName" /><FONT color="red" size="1">*</FONT></strong></span>
				<p class="clearfix fll margin-r20"> <span class="textbox6"> 
					<span class="textbox6-inner"><html:text styleClass="textbox77" property="agencyName" maxlength="50" />
				</span> </span> </p>
		</li>
		
		<li class="clearfix alt">
			<span class="text2 fll width160"><strong><bean:message key="createAgency.agencyDescription"/><FONT color="red" size="1">*</FONT></strong></span>
			 <p class="clearfix fll margin-r20"> <span class="textbox6-inner">
			 	<html:textarea styleClass="textarea1" property="agencyDescription" name="agencyMappingForm" cols="35" rows="3" onkeypress="return handleEnter(this, event)" onkeydown="limitText(this,255);"></html:textarea>
			 </span> </p>
		</li>
		
		<li class="clearfix">
				<span class="text2 fll width160"><strong><bean:message key="createAgency.agencyPath" /><FONT color="red" size="1">*</FONT></strong></span>
				<p class="clearfix fll margin-r20"> <span class="textbox6"> 
					<span class="textbox6-inner"><html:text styleClass="textbox7" property="agencyPath" name="agencyMappingForm" maxlength="255" />
				</span> </span> </p>
		</li>
		
		<li class="clearfix alt">
				<span class="text2 fll width160"><strong><bean:message key="createAgency.agencyClass" /><FONT color="red" size="1">*</FONT></strong></span>
				<p class="clearfix fll margin-r20"> <span class="textbox6"> 
					<span class="textbox6-inner"><html:text styleClass="textbox7" property="agencyClass" name="agencyMappingForm" maxlength="255" />
				</span> </span> </p>
		</li>
		
		
	
		
		<li class="clearfix">
				<span class="text2 fll width160"><strong><bean:message key="createAgency.agencyUsername" /><FONT color="red" size="1">*</FONT></strong></span>
				<p class="clearfix fll margin-r20"> <span class="textbox6"> 
					<span class="textbox6-inner"><html:text styleClass="textbox77" property="username" maxlength="40" />
				</span> </span> </p>
		</li>
		
		<li class="clearfix alt">
				<span class="text2 fll width160"><strong><bean:message key="createAgency.agencyPassword" /><FONT color="red" size="1">*</FONT></strong></span>
				<p class="clearfix fll margin-r20"> <span class="textbox6"> 
					<span class="textbox6-inner"><html:password styleClass="textbox77" property="password" maxlength="50" />
				</span> </span> </p>
		</li>
		
		<li class="clearfix alt">
				<span class="text2 fll width160"><strong><bean:message key="changePassword.confirmPassword" /></strong></span>
				<p class="clearfix fll margin-r20"> <span class="textbox6"> 
					<span class="textbox6-inner"><html:password styleClass="textbox77" property="confirmPassword" maxlength="50" />
				</span> </span> </p>
		</li>
		
		<li class="clearfix">
			<span class="text2 fll width160"><strong><bean:message key="createAgency.selectCircles"/><FONT color="red" size="1">*</FONT></strong></span>
			 <p class="clearfix fll margin-r20"> <span class="textbox6-inner">
				<html:select property="createMultiple" styleId="createMultiple" multiple="multiple" size="6" style="width:160px;" onchange="javascript:selectAll();">
				<option value="-2" selected="selected">--Select Circle(s)--</option>
				<option value="-3" >--All Circles--</option>
					<logic:notEmpty name="agencyMappingForm" property="circleList" >
								<bean:define id="elements" name="agencyMappingForm" property="circleList" /> 
								<html:options labelProperty="circleName" property="circleId"  collection="elements" />					
					</logic:notEmpty>
				</html:select>
			 </span> </p>
		</li>
		<li class="clearfix alt">
			<html:checkbox property="defaultCheck" styleId="defaultCheck" onclick="check(this)">&nbsp;&nbsp;<strong><bean:message key="createAgency.isDefault"/></strong></html:checkbox>
		</li>
		
	</ul>
	  	
	  <div class="button-area">
            <div class="button"><a class="red-btn" onclick="validateData()"><b>submit</b></a></div>
      </div> 

	 
	 </div>
	 </div>
</html:form>