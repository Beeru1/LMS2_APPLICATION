<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<LINK href="./theme/css.css" rel="stylesheet" type="text/css">
<LINK href=./jsp/theme/css.css rel="stylesheet" type="text/css">

<script language="javascript">
	var path = '<%=request.getContextPath()%>';
	var port = '<%= request.getServerPort()%>';
	var serverName = '<%=request.getServerName() %>';
</script>

<script language="javascript"><!--


function newXMLHttpRequest() {

    var xmlreq = false;

    if (window.XMLHttpRequest) {
        xmlreq = new XMLHttpRequest();

    } else if (window.ActiveXObject) {

        try {
            xmlreq = new ActiveXObject("Msxml2.XMLHTTP");

        } catch (e1) {
            try {
                xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e2) {
            }
        }
    }
    return xmlreq;
}

function loadDropdown()
{
		document.circleManagementForm.circleName.value=="";
		document.circleManagementForm.circleDesc.value=="";
				
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Add Files module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }
	    document.circleManagementForm.methodName.value="view";
	    document.circleManagementForm.submit();
	
}

function editCircle(index)
{
	//alert("here");
	var button=document.getElementById("updateButton");
	//alert("button"+button);
	document.getElementById("updateButton").style.display='block';
	document.getElementById("circleName").focus();
	
	document.circleManagementForm.circleName.value = document.getElementById('cirName'+index).innerHTML;
	document.circleManagementForm.circleDesc.value = document.getElementById('cirDesc'+index).innerHTML;
	document.forms[0].circleIdToDelete.value=document.forms[0].circleId[index].value;
}

function update()
{
  		
  		 if(document.circleManagementForm.selectedLobId.value=="-2" || document.circleManagementForm.selectedLobId.value==""){
		alert("Please Select LOB "); 
		return false;
		}
		
		if(document.circleManagementForm.circleName.value==""){
		alert("Please Enter Circle Name"); 
		return false;
		}
		
		if(document.circleManagementForm.circleDesc.value==""){
		alert("Please Enter Circle Description "); 
		return false;
		}
  			 
			  document.forms[0].methodName.value = "edit";
			  document.forms[0].submit();
			
}
function deleteCircle(index)
{
	
	if(confirm("Do you want to delete the circle permanently?"))
	{
	  document.forms[0].circleIdToDelete.value=document.forms[0].circleId[index].value;
	  //alert(circleIdToDelete.value);
	  document.forms[0].methodName.value = "delete";
	  document.forms[0].submit();
	}
	return false;	
}
  function validateData()
{	
		if(document.circleManagementForm.selectedLobId.value=="-2" || document.circleManagementForm.selectedLobId.value==""){
		alert("Please Select LOB "); 
		return false;
		}
		
		if(document.circleManagementForm.circleName.value==""){
		alert("Please Enter Circle Name"); 
		return false;
		}
		
		if(document.circleManagementForm.circleDesc.value==""){
		alert("Please Enter Circle Description "); 
		return false;
		}
		
	document.circleManagementForm.methodName.value="insert";	
	document.circleManagementForm.submit();
}


--></script>

<html:form action="/circleManagement">

	<html:hidden name="circleManagementForm" property="methodName" />
	<html:hidden name="circleManagementForm" property="message" />
	<html:hidden name="circleManagementForm" property="circleIdToDelete" />
	<html:hidden name="circleManagementForm" property="circleIdToEdit" />
	
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
			<td colspan="4" class="content-upload"><h1><bean:message key="createCircle.cedCircle"/></h1></td>
		</tr>		
		</table>  	
		 <ul class="list2 form1 ">
		 <li class="clearfix">
		 	<strong><font color="green">
		 	<div id="createStatusMsg" /><bean:write name="circleManagementForm" property="message" /></font></strong>
		 </li>
		
		<li class="clearfix">
				<span class="text2 fll width160"><strong><bean:message key="createCircle.lobType" /><FONT color="red">*</FONT></strong></span>
				<html:select property="selectedLobId" name="circleManagementForm" styleId="selectedLobId" onchange="javascript:loadDropdown()" styleClass="select1">
						<option value="-2" >Select LOB Type</option>
						<logic:notEmpty name="circleManagementForm" property="lobList" >
									<bean:define id="elements" name="circleManagementForm" property="lobList" /> 
										<html:options labelProperty="lobName" property="lobId"  collection="elements" />
						</logic:notEmpty>
						
				</html:select>
		</li>
		<li class="clearfix">
				<span class="text2 fll width180"><strong><bean:message key="createCircle.circleName" /><FONT color="red">*</FONT></strong></span>
				<html:text property="circleName" name="circleManagementForm" styleId="circleName">
				</html:text>
		</li>
		
		<li class="clearfix">
				<span class="text2 fll width180"><strong><bean:message key="createCircle.circleDesc" /><FONT color="red">*</FONT></strong></span>
				<html:text  property="circleDesc" name="circleManagementForm" styleId="circleDesc">
				</html:text>
		</li>
		
		<li>
		<div class="button-area">
            <div class="button" align="left"><a class="red-btn"  onclick="javascript:validateData();"><b>create</b></a></div>
            <div class="button" id="updateButton" style="display: none;" align="left"><a class="red-btn"  onclick="update()"><b>update</b></a></div>
          </div>
          </li>
	</ul>
	  	
	 
	 </div>
	 </div>
	 <div> <br/> </div>
	  
	<div class="box2">               
	 <div class="content-upload" style="margin-bottom: 0px;">
       <h1><bean:message key="createCircle.viewCircle" /></h1>
     </div>
         
     <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
        <tr class="textwhite">
        	
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="20%"><font size="2"><span class="mTop5">&nbsp;&nbsp;Circle Name</span></font></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="40%"><font size="2"><span class="mTop5">&nbsp;&nbsp;Circle Description</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="40%"><font size="2"><span class="mTop5">&nbsp;&nbsp;Action</span></td>
		</tr>
        
		<logic:empty name="circleManagementForm" property="circleList">
			<TR class="lightBg">
				<TD class="text" align="center" colspan="6"><bean:message key="viewAllUser.NotFound" /></TD>
			</TR>
		</logic:empty>
		
		<logic:notEmpty name="circleManagementForm" property="circleList">
		<bean:define id="circleList" name="circleManagementForm" property="circleList" type="java.util.ArrayList" />
		  <logic:notEmpty name="circleList"> 
		  <logic:iterate name="circleList" id="circle" indexId="i" type="com.ibm.lms.dto.CircleMstrDto">
		  <TR>
		  <TD  id="cirName<%=i%>" class="txt" align="left" width="40%"><bean:write name="circle" property="circleName" /></TD>
		   <TD id="cirDesc<%=i%>" class="txt" align="left" width="40%"><bean:write name="circle" property="circleDesc"/></TD>
		  <TD width="20%">	
			<html:hidden  property="circleId" value='<%=circle.getCircleId()%>' />
			<FONT color="red" size="2"><a onclick="editCircle(<%=i%>)">Edit</a><a onclick="deleteCircle(<%=i%>)">&nbsp;&nbsp;&nbsp;Delete</a></FONT>
          </TD>
		  </TR>
		 
		  </logic:iterate>
		  </logic:notEmpty>
		
			
		</logic:notEmpty>
	
	</table>	<br>
 </div>
	 
</html:form>