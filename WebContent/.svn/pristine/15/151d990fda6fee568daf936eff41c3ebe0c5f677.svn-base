<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="com.ibm.lms.dto.UserMstr" %>

<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%><script>
 
function limitdays(date1 , date2){

     var parts = date1.split("-");
   var dates = new Date(parts[1] + "-" + parts[0] + "-" + parts[2]); 
   var parts1 = date2.split("-");
   var dates1 = new Date(parts1[1] + "-" + parts1[0] + "-" + parts1[2]); 
    // The number of milliseconds in one day
    var ONE_DAY = 1000 * 60 * 60 * 24;
    // Convert both dates to milliseconds
    var date1_ms = dates.getTime();
    var date2_ms = dates1.getTime();
    // Calculate the difference in milliseconds
    var difference_ms = Math.abs(date1_ms - date2_ms);    
    // Convert back to days and return    
   
    return Math.round(difference_ms/ONE_DAY);
}
function searchLead()
{  
    var leadId = trimAll(document.forms[0].leadId.value);
    
    document.forms[0].leadId.value = leadId;
    
    var olmId = trimAll(document.forms[0].olmId.value);
    document.forms[0].olmId.value = olmId;
    
    var startDate = trimAll(document.forms[0].startDate.value);
    document.forms[0].startDate.value = startDate;
    
    var selectedLob = trimAll(document.forms[0].selectedProductLobId.value);
    document.forms[0].selectedProductLobId.value = selectedLob;
   
   
   //added by bhaskar
   
    var selectedCircle = trimAll(document.forms[0].selectedCircleId.value);
    document.forms[0].selectedCircleId.value = selectedCircle;
    
    var endDate = trimAll(document.forms[0].endDate.value);
    document.forms[0].endDate.value = endDate;
   
   if(startDate > endDate){
		alert("Start Date cannot be greater than End date");
		return false;
	}
    
    if(selectedLob == "-2" && selectedCircle == "" && leadId == "" && olmId == "" && startDate == "" && endDate == "")
    {
    alert("Please select search criterion");
	return false;
    }
  	if(leadId == "" && olmId == "" &&  selectedLob != "-2" && startDate == "" && endDate == "")
  	{
  	 alert("StartDate and EndDate.");
	return false;
	}
	if(leadId == "" && olmId == "" &&  selectedLob != "-2"  && selectedCircle != "" && startDate == "" && endDate == "")
  	{
  	 alert("StartDate and EndDate.");
	return false;
	}
	if(leadId == "" && olmId != ""  && startDate == "" && endDate == "")
  	{
  	 alert("StartDate and EndDate.");
	return false;
	}
	
  
    if(leadId=="" && startDate=="" && endDate=="" ){	
	alert("Please enter Lead Id or StartDate and EndDate.");
	document.forms[0].leadId.focus();
	return false;
	}
	else
	{
	if(leadId !=""  && startDate=="" && endDate==""){	
		
	if( leadId.length <14 || !isInteger(document.forms[0].leadId))
	{
 	 alert("Please enter 14 digit numeric Lead Id.");
 	 document.forms[0].leadId.select();
 	 return false;
		}
				
		document.forms[0].methodName.value="searchLeadFinalStatus";
		document.forms[0].submit();
	}
		else {
			if(startDate==""){
				alert("Please Enter StartDate.");
				 return false;
			}
			if(endDate==""){
				alert("Please Enter EndDate.");
			 	return false;
			}
		}
	var noOfDays = limitdays(startDate , endDate);
	 	 var days = document.forms[0].param.value;
        if (noOfDays > days)
    {
    	alert("Difference between start date and end date cannot exceed " + days +" days");
		return false;
    }
	document.forms[0].methodName.value="searchLeadFinalStatus";
	document.forms[0].submit();
	
	return true;
	}	
}

//Added By Bhaskar
function importExcel()
{

document.leadRegistrationFormBean.methodName.value= "excelImport";
document.leadRegistrationFormBean.submit();
}

function uploadExcel()
{
	
 	if(document.getElementById("newFile").value==""){
		alert("Please Select the file");
		return false;
	}
	else{  
		document.leadRegistrationFormBean.methodName.value= "uploadExcel";
		document.leadRegistrationFormBean.submit();
		//return true;
	}
	
}

function submitSearch(event) {
    if (event.keyCode == 13)
    {
       return searchLead();        
    }
     return true;
}

function loadCircleDropdown()
{	
var selectedProductLobId=document.forms[0].selectedProductLobId.value;

		var url;
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }  
	    req.onreadystatechange = returnJson1;
	    var selectedLobId = trimAll(document.forms[0].selectedProductLobId.value);
	   
	     <% UserMstr userBean= (UserMstr) session.getAttribute("USER_INFO");%>
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
				addOption("",opt1, selectDropDown);   
				 	           
				for (var i = 0; i < elements.length; i++) {
				
		            addOption(elements[i].circleId, elements[i].circleName, selectDropDown);
		    
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

</SCRIPT> 
<html:form action="/leadRegistration" enctype="multipart/form-data" method="post" >
	<html:hidden property="methodName" value=""/>
      <div class="box2">
      <html:hidden name="leadRegistrationFormBean" property="param" />
        <div class="content-upload">
          <h1>Lead Search</h1> 
             <logic:present name="msg" scope="request">
             <bean:define id="msg" name="msg" type="java.lang.String" scope="request"  />
             <FONT color="green"><%=msg%></FONT>
             <script>document.forms[0].leadId</script>
             </logic:present>
             
             <FONT color="red" style="font-size: small; margin-left: 10px;">
		 	<html:messages id="msg" message="true">
            <bean:write name="msg"/>  
            </html:messages>			
            <logic:equal name="leadRegistrationFormBean" property="isError" value="true">
			  <a href="./leadRegistration.do?methodName=openErrLog" target="_new">click here</a>
		    </logic:equal>
            </FONT> 
             
             <ul class="list2 form1" >
             <li class="clearfix" style="margin-top: 18px;">
				<span class="text2 fll width120"><strong><bean:message key="createProductMapping.selectproductLob" /></strong></span>
				<p class="clearfix fll" > 
					<html:select property="selectedProductLobId" name="leadRegistrationFormBean" styleId="selectedProductLobId"  styleClass="select1" onchange="Javascript:loadCircleDropdown();">
							<option value="-2" >Select Product LOB</option>
							<logic:notEmpty name="leadRegistrationFormBean" property="productList" >
										<bean:define id="elements" name="leadRegistrationFormBean" property="productList" /> 
											<html:options labelProperty="productLobName" property="productLobId"  collection="elements" />
							</logic:notEmpty>
					</html:select>
				</p> 	
			</li>
              <li class="clearfix alt" style="height: 18px;">
				<span class="text2 fll width120"><strong>Circle</strong></span>
				<html:select property="selectedCircleId" name="leadRegistrationFormBean" styleId="createSingle" styleClass="select1" >
						<option value="" >Select Circle</option>
						
						<logic:notEmpty name="circleList" scope="request">
						<bean:define id="circles" 	name="circleList" scope="request"/> 
						<html:options labelProperty="circleName" property="selectedCircleId" collection="circles" />
					</logic:notEmpty>
				</html:select>
			</li> 
             
              <li class="clearfix" style="margin-top: 18px;" >	
             
             	 <span class="text2 fll width120"><strong>Lead Id</strong> </span>	
             	  <html:text style="width:150px;" tabindex="1" property="leadId" styleId="keyword" name="leadRegistrationFormBean" maxlength="14" onkeypress="return submitSearch(event);" 
      	               	 style="height:18px; width: 150px; border-color: #000000; border-width:1px; padding-left:3px; padding-top:3px; font-size:14px; font-weight:bold; color:#2f2f2f;"/>
             	
             	
		     </li>
		     
		        <li class="clearfix alt" style="margin-top: 18px;" >	
             	
      	         <span class="text2 fll width120"><strong>OLM Id</strong> </span>
      	         <html:text style="width:150px;" tabindex="1" property="olmId" styleId="keyword" name="leadRegistrationFormBean" maxlength="14" onkeypress="return submitSearch(event);" 
      	               	 style="height:18px; width: 150px; border-color: #000000; border-width:1px; padding-left:3px; padding-top:3px; font-size:14px; font-weight:bold; color:#2f2f2f;"/>	
             	 
             	
		     </li>
   	         <li class="clearfix ">
          	<span class="text2 fll width160 rightAlign"><strong>Start Date &nbsp;&nbsp;&nbsp;<font color=red></font> </strong> </span>
          	<input type="text" name="startDate"  readonly="readonly" class="tcal calender2 fll" value="<bean:write name="leadRegistrationFormBean" property="startDate"/>">
			<span class="text2 fll width160 rightAlign" ><strong>End Date &nbsp;&nbsp;&nbsp;<font color=red></font></strong> </span>
			<input type="text" name="endDate"  readonly="readonly" class="tcal calender2 fll" value="<bean:write name="leadRegistrationFormBean" property="endDate"/>">
			<span class="text2 fll" >&nbsp;</span><div class="button" >
			
			</li> 
		
			 <li class="clearfix alt" >
				<span class="text2 fll width160"><strong>Select File: </strong> </span>	
				  <p class="clearfix fll margin-r20"> 
				  <input type="file" value="Browse" id="newFile" name="newFile"/></p><span class="text2 fll">&nbsp;</span>
				<input type="image" src="images/submit.jpg" style="margin-right:10px;" onclick="return uploadExcel();" styleClass="red-btn fll"/>&nbsp;&nbsp;&nbsp;&nbsp;
				 <img  src="images/excel.gif" width="39" height="35" border="0" onclick="importExcel();" /> </li>
	
   	         <li class="clearfix alt" style="margin-top: 18px;">	
					<center><a class="red-btn" tabindex="3" onclick="return searchLead();"><b>search</b></a></center>
		     </li>      
            </ul>
            
            
       </div>
       <br>
     </div>
     
  <logic:notEqual name="leadRegistrationFormBean" property="initStatus" value="true">
	   
	   <logic:notEmpty name="LEAD_LIST">
			<bean:define id="leadList" name="LEAD_LIST" type="java.util.ArrayList" scope="request"  />
		</logic:notEmpty>
		<logic:notPresent name="msg" scope="request">
		<logic:empty name="leadList" >
			<FONT color="red" style="margin-left: 15px;"><bean:message key="lead.not.found" /></FONT>
		</logic:empty>
		</logic:notPresent>
		<logic:notEmpty name="leadList" >
		 <div class="boxt2">
	      <div class="content-upload clearfix">
	       <div class="content-table-type2 clearfix">
	       <h1 >Search Result </h1>
			<table width="100%" border="0" cellspacing="0" cellpadding="2">
			<TR >
                <TH width="1%"  align="left" valign="top">SL#</TH>
                <TH width="10%" align="left" valign="top">Lead Id</TH>
                <TH width="18%" align="left" valign="top">Product Name</TH>
				<TH width="20%" align="left" valign="top">Customer Name</TH>
				<%
								  if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								 %>
				<TH width="10%" align="left" valign="top">Contact No.</TH>
				<% }%>
                <TH width="10%" align="left" valign="top">Circle</TH>
				<TH width="23%" align="left" valign="top">Opportunity Time</TH>
				<TH width="13%" align="left" valign="top">Status</TH>
			</TR>
		
			<logic:iterate name="leadList" type="com.ibm.lms.dto.LeadDetailsDTO" id="report" indexId="i">
			 <!--  String cssName = ""; if( i%2==1){cssName = "alt";}%> -->
				<TR ><!-- class="=cssName%>"> -->	
					<TD align="left" valign="top" style="font-size:12px;"><%=(i.intValue() + 1)%>.</TD>
					<TD align="left" valign="top" style="font-size:12px;">
					<A HREF='leadRegistration.do?methodName=viewFinalLeadStatusDetail&leadId=<%=report.getLeadId()%>' class="Red11" ><bean:write name="report" property="leadId" /></A></TD>
					<TD align="left" valign="top" style="font-size:12px;" ><bean:write name="report" property="productName"  /></TD>
					<TD align="left" valign="top" style="font-size:12px;"><bean:write name="report" property="customerName" /></TD>
					<%if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){ %>
					<TD align="left" valign="top" style="font-size:12px;"><bean:write name="report" property="contactNo" /></TD>
					<%} %>
					<TD align="left" valign="top" style="font-size:12px;"><bean:write name="report" property="circleName" /></TD>
					<TD align="left" valign="top" style="font-size:12px;"><bean:write name="report" property="oppertunityTime" /></TD>
					<TD align="left" valign="top" style="font-size:12px;"><bean:write name="report" property="leadStatusName" /></TD>
				</TR>
			</logic:iterate>  
		</table>
	   </div>
	  </div>
     </div>
	</logic:notEmpty>
   
 </logic:notEqual>
 
 <script>
   if(document.forms[0].leadId.value=="")
   {
   	  document.forms[0].leadId.select();
   }
   
 </SCRIPT> 
 
 
</html:form>