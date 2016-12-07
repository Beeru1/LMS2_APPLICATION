<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%><script> 
function searchLead()
{ 
    var leadId = trimAll(document.forms[0].leadId.value);
    document.forms[0].leadId.value = leadId;
    
    var contactNo = trimAll(document.forms[0].contactNo.value);
    document.forms[0].contactNo.value = contactNo;
    
    var tid = trimAll(document.forms[0].tid.value);
    document.forms[0].tid.value = tid;
    
     
    if(leadId=="" && contactNo=="" && tid=="")
	{	
		alert("Please enter Lead Id or Mobile No Or Tid.");
		document.forms[0].leadId.focus();
		return false;
	}
	else
	{
		if(leadId !="" )
		{	
		   if( leadId.length <14 || !isInteger(document.forms[0].leadId) )
			{
			  alert("Please enter 14 digit numeric Lead Id.");
			  document.forms[0].leadId.select();
			  return false;
			}
		}
		
		if(contactNo !="" )
		{	
		   if(contactNo.length <10 || !isInteger(document.forms[0].contactNo) )
			{
			  alert("Please enter 10 digit Mobile No.");
			  document.forms[0].contactNo.select();
			  return false;
			}
		}
	
	if(tid !="" )
		{	
		   if(tid.length <3 || !isInteger(document.forms[0].tid) )
			{
			  alert("Please enter valid Transaction Id!");
			  document.forms[0].tid.select();
			  return false;
			}
		}
	
	
	document.forms[0].methodName.value="editSearchLead";
	document.forms[0].submit();
	return true;
	}	
}

function submitSearch(event) {
    if (event.keyCode == 13)
    {
       return searchLead();        
    }
     return true;
}


</SCRIPT> 
<html:form action="/leadRegistration">
	<html:hidden property="methodName" value=""/>
	<html:hidden property="productLobId" value="" />
		<html:hidden property="pageStatus" value=""/>
		
      <div class="box2">
      
        <div class="content-upload">
          <h1>Lead Search</h1> 
             <center> <strong><FONT color="red"><html:errors/></FONT>
			 <FONT color="green"><html:messages id="msg" message="true"><bean:write name="msg"/></html:messages></FONT></strong></center>
             <ul class="list2 form1" >
              <li class="clearfix" style="margin-top: 18px;" >	
             	<p class="clearfix fll width235 margin-r64" style="margin-left: 50px;">
             	 <span class="text2 fll margin-r20"><strong>Lead Id.</strong> </span>	
             	  <html:text style="width:150px;" tabindex="1" property="leadId" styleId="keyword" name="leadRegistrationFormBean" maxlength="14" onkeypress="return submitSearch(event);" 
      	               	 style="height:18px; width: 150px; border-color: #000000; border-width:1px; padding-left:3px; padding-top:3px; font-size:14px; font-weight:bold; color:#2f2f2f;"/>	
             	 </p>  
             	 <p class="clearfix fll" >
             
      	         <span class="text2 fll margin-r20" style="margin-left: 100px;"><strong>Mobile No.</strong> </span>    	 
               	  <html:text style="width:150px;" tabindex="2" property="contactNo" styleId="contactNo" name="leadRegistrationFormBean" maxlength="10" onkeypress="return submitSearch(event);" 
               	 		style="height:18px; width: 150px; border-color: #000000; border-width:1px; padding-left:3px; padding-top:3px; font-size:14px; font-weight:bold;  color:#2f2f2f;"/>
             	 <% %>
             	 </p>
		     </li>
		     <li class="clearfix alt" style="margin-top: 18px;" >	
             	<p class="clearfix fll width235 margin-r64" style="margin-left: 80px;">
             	 <span class="text2 fll margin-r20"><strong>Tid.</strong> </span>	
             	  <html:text style="width:150px;" tabindex="1" property="tid"  styleId="keyword" name="leadRegistrationFormBean" maxlength="14" onkeypress="return submitSearch(event);" 
      	               	 style="height:18px; width: 150px; border-color: #000000; border-width:1px; padding-left:3px; padding-top:3px; font-size:14px; font-weight:bold; color:#2f2f2f;"/>	
             	 </p>  </li>
   	         <li class="clearfix " style="margin-top: 18px;">	
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
		</br>
		<logic:empty name="leadList" >
			<FONT color="red" style="margin-left: 15px;"><bean:message key="lead.not.4G" /></FONT>
		</logic:empty>
		
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
				<%} %>
                <TH width="10%" align="left" valign="top">Circle</TH>
				<TH width="23%" align="left" valign="top">Opportunity Time</TH>
				<TH width="13%" align="left" valign="top">Status</TH>
				<TH width="13%" align="left" valign="top">Get Appointment</TH>
			</TR>
		
			<logic:iterate name="leadList" type="com.ibm.lms.dto.LeadDetailsDTO" id="report" indexId="i">
			 <!--  String cssName = ""; if( i%2==1){cssName = "alt";}%> -->
				<TR ><!-- class="=cssName%>"> -->	
					<TD align="left" valign="top" style="font-size:12px;"><%=(i.intValue() + 1)%>.</TD>
					<TD align="left" valign="top" style="font-size:12px;">
						<A HREF='leadRegistration.do?methodName=viewLeadDetails&leadId=<%=report.getLeadId()%>&pageStatus=edit' class="Red11" ><bean:write name="report" property="leadId" /></A></TD>
					<TD align="left" valign="top" style="font-size:12px;" ><bean:write name="report" property="productName"  /></TD>
					<TD align="left" valign="top" style="font-size:12px;"><bean:write name="report" property="customerName" /></TD>
					<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
					<TD align="left" valign="top" style="font-size:12px;"><bean:write name="report" property="contactNo" /></TD>
					<%} %>
					<TD align="left" valign="top" style="font-size:12px;"><bean:write name="report" property="circleName" /></TD>
					<TD align="left" valign="top" style="font-size:12px;"><bean:write name="report" property="oppertunityTime" /></TD>
					<TD align="left" valign="top" style="font-size:12px;"><bean:write name="report" property="leadStatusName" /></TD>
				
					<logic:equal name="report" property="productflag" value="true">
					<TD align="left" valign="top" style="font-size:12px;">
					<A HREF='leadRegistration.do?methodName=editLeadDetails&leadId=<%=report.getLeadId()%>' class="Red11" >Get Appointment</A></TD>
					</logic:equal>
					
					<logic:equal name="report" property="productflag" value="4Gdifferent">
					<TD align="left" valign="top" style="font-size:12px;">
					<A HREF='leadRegistration.do?methodName=editLeadDetails&leadId=<%=report.getLeadId()%>' class="Red11" >Update Details</A></TD>
					</logic:equal>
					
					
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
   	   if(document.forms[0].contactNo.value=="")
   	   {
	    document.forms[0].leadId.select();
	   }
	   else
	   {
	     document.forms[0].contactNo.select();
	   }
   }
   else
   {
     document.forms[0].leadId.select();
   }
 </SCRIPT> 
 
 
</html:form>