<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%>
<%@page import="com.ibm.lms.dto.UserMstr"%>
<%@page import="com.ibm.lms.common.PropertyReader"%><script> 
function searchTxnId()
{ 
  
    var tid = trimAll(document.forms[0].tid.value);
    document.forms[0].tid.value = tid;
    
    if(tid=="")
	{	
		alert("Please enter  Tid.");
		document.forms[0].tid.focus();
		return false;
	}
	else
	{
		
		if(tid !="" )
		{	
		   if( tid.length <3 || !isInteger(document.forms[0].tid) )
			{
			  alert("Please enter valid numeric Tid.");
			  document.forms[0].leadId.select();
			  return false;
			}
		}
	
	
	document.forms[0].methodName.value="SearchLeadCreationwithTxnId";
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
	<html:hidden property="pageStatus" value=""/>
      <div class="box2">
      
        <div class="content-upload">
          <h1>TransactionId Search</h1> 
             <center> <strong><FONT color="red"><html:errors/></FONT>
			 <FONT color="green"><html:messages id="msg" message="true"><bean:write name="msg"/></html:messages></FONT></strong></center>
             <ul class="list2 form1" >
            
		     <li class="clearfix alt" style="margin-top: 18px;" >	
             	<p class="clearfix fll width235 margin-r64" style="margin-left: 80px;">
             	 <span class="text2 fll margin-r20"><strong>Tid.</strong> </span>	
             	  <html:text style="width:150px;" tabindex="1" property="tid"  styleId="keyword" name="leadRegistrationFormBean" maxlength="10" onkeypress="return submitSearch(event);" 
      	               	 style="height:18px; width: 150px; border-color: #000000; border-width:1px; padding-left:3px; padding-top:3px; font-size:14px; font-weight:bold; color:#2f2f2f;"/>	
             	 </p>  </li>
   	         <li class="clearfix " style="margin-top: 18px;">	
					<center><a class="red-btn" tabindex="3" onclick="return searchTxnId();"><b>search</b></a></center>
		     </li>      
            </ul>
       </div>
       <br>
     </div>
     
     
  <logic:notEqual name="leadRegistrationFormBean" property="validTxnId" value="0">
  
  
     <logic:empty  scope="request" name="LeadList">
     
     
      <div class="boxt2">
	      <div class="content-upload clearfix">
	       <div class="content-table-type2 clearfix">
	       <h1 >Not a Valid TXN ID</h1>
	       <table width="100%" border="0" cellspacing="0" cellpadding="2">
     
     
     <TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message
				
					key="assignmentDownload.NotFound" /></TD>
				
			</TR>
     
     </table>
	       
	       </div>
	       </div>
	       </div>
	       
     
			
		</logic:empty>
      
      </logic:notEqual>
      
      
  <logic:notEqual name="leadRegistrationFormBean" property="validLeadStatus" value="0">
  
  
		 <div class="boxt2">
	      <div class="content-upload clearfix">
	       <div class="content-table-type2 clearfix">
	       <h1 >Valid Lead created</h1>
			
				<table width="100%" border="0" cellspacing="0" cellpadding="2">
			<TR >
               
                
                <TH width="30%" align="center" valign="top">Lead Id</TH>
                
                <TH width="18%" align="center" valign="top"> Circle</TH>
                <TH width="18%" align="center" valign="top"> Product Name</TH>
                
			</TR>
			
		<logic:empty  scope="request" name="LeadList">
			<TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message
				
					key="assignmentDownload.NotFound" /></TD>
				
			</TR>
		</logic:empty>
		
		<logic:present name="LeadList" scope="request">	
		<logic:notEmpty name="LeadList" scope="request">
		<logic:iterate id="report"  name="LeadList" scope="request"  >
				<TR >	
					
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="leadId" /></TD>
				
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="circleName" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="productName" /></TD>
					
				</TR>
			</logic:iterate>  
			</logic:notEmpty>
	</logic:present>
		</table>
			
			
			
	   </div>
	  </div>
     </div>
	</logic:notEqual>  
     
      <logic:notEqual name="leadRegistrationFormBean" property="duplicateLeadStatus" value="0">
  
  
		 <div class="boxt2">
	      <div class="content-upload clearfix">
	       <div class="content-table-type2 clearfix">
	       <h1 >Duplicate Lead created </h1>
			
			
				<table width="100%" border="0" cellspacing="0" cellpadding="2">
			<TR >
              
              	
                <TH width="30%" align="center" valign="top">Mobile Number</TH>
                 <TH width="18%" align="center" valign="top"> Circle Name</TH>
                  <TH width="18%" align="center" valign="top"> Product Name</TH>
                
               
            
			</TR>
			
		<logic:empty  scope="request" name="LeadList">
			<TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message
				
					key="assignmentDownload.NotFound" /></TD>
				
			</TR>
		</logic:empty>
		
		<logic:present name="LeadList" scope="request">	
		<logic:notEmpty name="LeadList" scope="request">
		<logic:iterate id="report"  name="LeadList" scope="request"  >
				<TR >	
					
					
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="mobileNo" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="circleName" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="productName" /></TD>
				
					
				</TR>
			</logic:iterate>  
			</logic:notEmpty>
	</logic:present>
		</table>
			
			
			
			
			
	   </div>
	  </div>
     </div>
	</logic:notEqual>  
	<logic:notEqual name="leadRegistrationFormBean" property="dirtyLeadStatus" value="0">
  
  
		 <div class="boxt2">
	      <div class="content-upload clearfix">
	       <div class="content-table-type2 clearfix">
	       <h1 > Dirty Lead created</h1>
			
					<table width="100%" border="0" cellspacing="0" cellpadding="2">
			<TR >
             
              	 
                <TH width="30%" align="center" valign="top">Mobile Number</TH>
                <TH width="18%" align="center" valign="top"> Circle Name</TH>
              	<TH width="18%" align="center" valign="top"> Product Name</TH>
                <TH width="18%" align="center" valign="top"> errorMessage</TH>
            
			</TR>
			
		<logic:empty  scope="request" name="LeadList">
			<TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message
				
					key="assignmentDownload.NotFound" /></TD>
				
			</TR>
		</logic:empty>
		
		<logic:present name="LeadList" scope="request">	
		<logic:notEmpty name="LeadList" scope="request">
		<logic:iterate id="report"  name="LeadList" scope="request"  >
				<TR >	
					
				
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="mobileNo" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="circleName" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="productName" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="errorMessage" /></TD>
				
					
				</TR>
			</logic:iterate>  
			</logic:notEmpty>
	</logic:present>
		</table>
			
			
			
			
	   </div>
	  </div>
     </div>
	</logic:notEqual>  
	<logic:notEqual name="leadRegistrationFormBean" property="validTxnId" value="0">
	
	  <h1 > Not A Valid Transaction Id</h1>
	
	</logic:notEqual>
     


 
</html:form>