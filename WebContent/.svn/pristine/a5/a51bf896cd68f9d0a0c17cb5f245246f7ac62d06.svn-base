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
 
  
  var req = false;

    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();

    } else if (window.ActiveXObject) {

        try {
            req = new ActiveXObject("Msxml2.XMLHTTP");

        } catch (e1) {
            try {
                req = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e2) {
            }
        }
    }
  
  function loadCirclesMapped(){

 		
	  //  req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Add Files module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }
	    
	    req.onreadystatechange = returnJson1;
	    var selectedAgencyId = document.agencyMappingForm.selectedAgencyId.value;
	    var url= 'ajaxSupport.do?mt=getCirclesMappedWithAgency&selectedAgencyId='+selectedAgencyId;
	    
	    req.open("GET", url, true);
	    req.send(null);
	   
  
  }
  
    function loadCirclesNotMapped(){

	  //  req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Add Files module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }
	    
	    req.onreadystatechange = returnJson2;
	    var url= 'ajaxSupport.do?mt=getCirclesNotMappedWithAgency';	    
	    req.open("GET", url, true);
	    req.send(null);
  }
  
      function loadAgencyDescription(){
	
	  //  req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Add Files module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }
	    
	    req.onreadystatechange = returnJson3;
	    var selectedAgencyId = document.agencyMappingForm.selectedAgencyId.value;
	    var url= 'ajaxSupport.do?mt=getAgencyDescriptionDetails&selectedAgencyId='+selectedAgencyId;
	    
	    req.open("GET", url, true);
	    req.send(null);
  }
  
    function returnJson1() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            var json1 = eval('(' + req.responseText + ')');
            
			var elements1 = json1.elements1;
			
		if (elements1.length!=0)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}
	                
	            var selectDropDown = document.getElementById("createMultiple");	       
	            
	            cleanSelectBox("createMultiple");
	            
				for (var i = 0; i < elements1.length; i++) {
		            addOption(elements1[i].mappedCircleId, elements1[i].mappedCircleName, selectDropDown);
		        }		        
		    }
		    
		    
		    loadCirclesNotMapped();
        }
       }
        
    }
    
    function returnJson2() {
    
    if (req.readyState == 4) {
        if (req.status == 200) {
            var json2 = eval('(' + req.responseText + ')');
            
			var elements2 = json2.elements2;
			
		if (elements2.length!=0)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}
	                
	            var selectDropDown = document.getElementById("createMultiple1");	       
	            
	            cleanSelectBox("createMultiple1");
	            
				for (var i = 0; i < elements2.length; i++) {
		            addOption(elements2[i].circleId, elements2[i].circleName, selectDropDown);
		        }		        
		    }
		    
		    
		      loadAgencyDescription();
		    
        }
       }
        
    }
    
    function returnJson3() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            var json = eval('(' + req.responseText + ')');
			var desc = json.desc;
			var path = json.path;	
			var classname = json.classname;	
			var username = json.username;	
			var password = json.password;
			var isDefault = json.isDefault;		
			
			document.getElementById("agencyDescription").value = desc; 
			document.getElementById("agencyPath").value = path; 
			document.getElementById("agencyClass").value = classname; 
			document.getElementById("username").value = username; 
			document.getElementById("password").value = password; 
			document.getElementById("defaultCheck").value = isDefault; 
			document.getElementById("confirmPassword").value = password; 
			
			//alert("is default"+ isDefault);
			
			if(document.getElementById("defaultCheck").value== "Y"){
				document.getElementById("defaultCheck").checked = true;
			
			}
			else if(document.getElementById("defaultCheck").value== "N"){
				document.getElementById("defaultCheck").checked = false;			
			}
	    
        }
    }
}

    function returnJson4() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            var json1 = eval('(' + req.responseText + ')');
            
			var elements1 = json1.elements1;
			
		if (elements1.length!=0)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}
	                
	            var selectDropDown = document.getElementById("createMultiple");	       
	            
	            cleanSelectBox("createMultiple");
	            
				for (var i = 0; i < elements1.length; i++) {
		            addOption(elements1[i].mappedCircleId, elements1[i].mappedCircleName, selectDropDown);
		        }		        
		    }
		    
		    removefromList("createMultiple1");
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
  
    function returnJson5() {
    
    if (req.readyState == 4) {
        if (req.status == 200) {
        
        var json2 = eval('(' + req.responseText + ')');            
			var elements2 = json2.elements2;
						
		if (elements2.length!=0)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}
	                
	            var selectDropDown = document.getElementById("createMultiple1");	       	            
	            cleanSelectBox("createMultiple1");
	            
				for (var i = 0; i < elements2.length; i++) {
		            addOption(elements2[i].circleId, elements2[i].circleName, selectDropDown);
		        }		        
		    }
		    removefromList("createMultiple");
		    
        }
       }
        
    }
  
  function addCircle(){  
  
  url= addItem('createMultiple1');
		  if(url==null)
		 {
		  alert("Select a Circle From Other Circle(s) List");
		 }
		  else{
		  	if(confirm("Do you want to add Circle(s)."))
		   	{
    
	    if (!req) {
	        alert("Your browser does not support AJAX! Add Files module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }
	    
	    req.onreadystatechange = returnJson4;
	    var selectedAgencyId = document.agencyMappingForm.selectedAgencyId.value;
	    url= url+"&selectedAgencyId="+selectedAgencyId;
	    
	    req.open("GET", url, true);
	    req.send(null);
	    }
	    }
  }
  
  function removeCircle(){
  
  url= removeItem('createMultiple');

		  if(url==null)
		 {
		  alert("Select a Circle From Mapped Circle(s) List");
		 }
		  else{
		  	if(confirm("Do you want to remove Circle(s)."))
		   	{
    
	    if (!req) {
	        alert("Your browser does not support AJAX! Add Files module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }
	    
	    req.onreadystatechange = returnJson5;
	    var selectedAgencyId = document.agencyMappingForm.selectedAgencyId.value;
	    url= url+"&selectedAgencyId="+selectedAgencyId;
	    
	    req.open("GET", url, true);
	    req.send(null);
	    }
	    }
  
  }
  // To call ajax for removing an already mapped circle
  
	function removeItem(selectbox)mappedCircleId
		{ c=0;
		var i;
		var obj = document.getElementById(selectbox);
		var url = 'ajaxSupport.do?mt=removeCircleMapping';
		for(i=obj.options.length-1;i>=0;i--)
		{ 
			if(obj.options[i].selected){
			var circleId = obj.options[i].value;
			 url=url+"&circleId="+circleId;		
			 c++;
			}
		
		}		
		 if(c==0) url=null;
		
		 return(url);
		}
		
  // To call ajax for adding a new circle to mapping	
	function addItem(selectbox)
		{ c=0;
		var i;
		var obj = document.getElementById(selectbox);
		var url = 'ajaxSupport.do?mt=addCircleMapping';
		for(i=obj.options.length-1;i>=0;i--)
		{ 
			if(obj.options[i].selected){
			var mappedCircleId = obj.options[i].value;
			 url=url+"&mappedCircleId="+mappedCircleId;		
			 c++;
			}
		
		}		
		 if(c==0) url=null;
		
		 return(url);
		}
		
	function removefromList(selectbox){
	var obj = document.getElementById(selectbox);	
 		for(var i= obj.options.length-1; i>=0; i--)
		{ 
			if(obj.options[i].selected){
 			obj.remove(i);

			}

		}
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
  
  function validateData(){

		if(isEmpty(document.agencyMappingForm.agencyDescription)){
		alert("Please Enter Agency Description"); 
		return false;
		}
		
		if(isEmpty(document.agencyMappingForm.agencyPath)){
		alert("Please Enter URL Path"); 
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

		if(document.agencyMappingForm.password.value != document.agencyMappingForm.confirmPassword.value)
		{
			alert("The Password and Confirm password do not match!");
			document.agencyMappingForm.password.value="";
			document.agencyMappingForm.confirmPassword.value="";			
			return false;
		} 
		
	 	document.agencyMappingForm.methodName.value="update";	
		return true;
  }
</script>

<html:form action="/editAgencyMapping">
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
				key="createAgency.edit" /></h1></td>
		</tr>		
		</table>  	
		 <ul class="list2 form1 ">
		 <li class="clearfix">
		 	<strong><font color="red"><bean:write name="agencyMappingForm" property="message" /></font></strong>
		 </li>
		
		<li class="clearfix">
				<span class="text2 fll width160"><strong><bean:message key="createAgency.selectAgency" /><FONT color="red" size="1">*</FONT></strong></span>
				<html:select property="selectedAgencyId" name="agencyMappingForm" styleId="selectedAgencyId" onchange="loadCirclesMapped()" styleClass="select1">
						<option value="-1" >--Select Agency--</option>
						<logic:notEmpty name="agencyMappingForm" property="agencyList" >
									<bean:define id="elements" name="agencyMappingForm" property="agencyList" /> 
										<html:options labelProperty="agencyName" property="agencyId"  collection="elements" />
						</logic:notEmpty>
				</html:select>
		</li>
		
		<li class="clearfix alt">
			<span class="text2 fll width160"><strong><bean:message key="createAgency.agencyDescription"/><FONT color="red" size="1">*</FONT></strong></span>
			 <p class="clearfix fll margin-r20"> <span class="textbox6-inner">
			 	<html:textarea styleClass="textarea1" styleId="agencyDescription" property="agencyDescription" name="agencyMappingForm" cols="35" rows="3" onkeypress="return handleEnter(this, event)" onkeydown="limitText(this,255);" ></html:textarea>
			 </span> </p>
		</li>
		<li class="clearfix">
				<span class="text2 fll width160"><strong><bean:message key="createAgency.agencyPath" /><FONT color="red" size="1">*</FONT></strong></span>
				<p class="clearfix fll margin-r20"> <span class="textbox6"> 
					<span class="textbox6-inner"><html:text styleClass="textbox7" styleId = "agencyPath" property="agencyPath" name="agencyMappingForm" maxlength="255" />
				</span> </span> </p>
		</li>
		<li class="clearfix alt">
				<span class="text2 fll width160"><strong><bean:message key="createAgency.agencyClass" /><FONT color="red" size="1">*</FONT></strong></span>
				<p class="clearfix fll margin-r20"> <span class="textbox6"> 
					<span class="textbox6-inner"><html:text styleClass="textbox7" property="agencyClass" styleId="agencyClass" name="agencyMappingForm" maxlength="255" />
				</span> </span> </p>
		</li>
		<li class="clearfix">
				<span class="text2 fll width160"><strong><bean:message key="createAgency.agencyUsername" /><FONT color="red" size="1">*</FONT></strong></span>
				<p class="clearfix fll margin-r20"> <span class="textbox6"> 
					<span class="textbox6-inner"><html:text styleClass="textbox77" styleId="username" property="username" maxlength="40" />
				</span> </span> </p>
		</li>
		
		<li class="clearfix alt">
				<span class="text2 fll width160"><strong><bean:message key="createAgency.agencyPassword" /><FONT color="red" size="1">*</FONT></strong></span>
				<p class="clearfix fll margin-r20"> <span class="textbox6"> 
					<span class="textbox6-inner"><html:password styleClass="textbox77" styleId="password" property="password" maxlength="50" />
				</span> </span> </p>
		</li>
		
		<li class="clearfix alt">
				<span class="text2 fll width160"><strong><bean:message key="changePassword.confirmPassword" /></strong></span>
				<p class="clearfix fll margin-r20"> <span class="textbox6"> 
					<span class="textbox6-inner"><html:password styleClass="textbox77" property="confirmPassword"  styleId="confirmPassword" maxlength="50" />
				</span> </span> </p>
		</li>
		
		
		<li class="clearfix">
		
		 <table cellpadding="0" cellspacing="0"><tr><td>
			<span class="text2 fll width160"><strong><bean:message key="editAgency.mappedCircles"/><FONT color="red" size="1"></FONT></strong></span>
			 <p class="clearfix fll margin-r20"> <span class="textbox6-inner">
				<html:select property="createMultiple" styleId="createMultiple" multiple="multiple" size="7" style="width:160px;" onchange="javascript:selectAll();">
					<logic:notEmpty name="agencyMappingForm" property="circleList" >
							<option value="-2" >--Select Circle(s)--</option>
							<option value="-3" >--All Circles--</option>
								<bean:define id="elements1" name="agencyMappingForm" property="mappedCircleList" /> 
								<html:options labelProperty="mappedCircleName" property="mappedCircleId"  collection="elements1" />					
					</logic:notEmpty>
				</html:select>
			 </span> </p>
			<input class="red-btn" onclick="javascript:addCircle();" type="button" value="<%="Add <"%>" />
			&nbsp;&nbsp;&nbsp;<input class="red-btn" onclick="javascript:removeCircle();" type="button" value="Remove >" />
		  </td>
		  <td width="100px">&nbsp;</td> 
		  <td> 
			<span class="text2" ><strong ><bean:message key="editAgency.remainingCircles"/></strong></span>
			 <p class="clearfix margin-r20"> <span class="textbox6-inner">
				<html:select property="createMultiple1" styleId="createMultiple1" multiple="multiple" size="6" style="width:160px;" onchange="javascript:selectAll();">
					<logic:notEmpty name="agencyMappingForm" property="circleList" >
							<option value="-2" >--Select Circle(s)--</option>
							<option value="-3" >--All Circles--</option>
								<bean:define id="elements2" name="agencyMappingForm" property="circleList" /> 
								<html:options labelProperty="circleName" property="circleId"  collection="elements2" />					
					</logic:notEmpty>
				</html:select>
			 </span> </p> 
			</td>
		  </tr>
		 </table> 
		</li>
		
		<li class="clearfix alt">
			<html:checkbox property="defaultCheck" styleId="defaultCheck" onclick="check(this)">&nbsp;&nbsp;<strong><bean:message key="createAgency.isDefault"/></strong></html:checkbox>
		</li>
		
	</ul>
	
		<div class="button-area">
            <div class="button">
              <input class="submit-btn1 red-btn" type="submit" value="" onclick="return validateData()" />
            </div>
	  	</div>	 
	 </div>
	 </div>
	 
</html:form>