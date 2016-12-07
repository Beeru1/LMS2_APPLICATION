<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html><HEAD>

<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<TITLE>Bulk Assignment</TITLE>


<script>

function formsubmit(){
      
     if(document.bulkAssigmentFormBean.newFile.value==""){
		alert("Please Select the file");
		return false;
	}else if(!(/^.*\.xls$/.test(document.bulkAssigmentFormBean.newFile.value)) && !(/^.*\.xlsx$/.test(document.bulkAssigmentFormBean.newFile.value)) ) {
			alert("Please select a xls or xlsx file only.");
			return false;
				
	}else if(navigator.userAgent.indexOf("MSIE") != -1) 
	      {
		   if(document.bulkAssigmentFormBean.newFile.value.indexOf(":\\") == -1 ) {
			alert("Please Select a proper file.")
			return false;
		   }
		}else{  
			document.forms[0].methodName.value='uploadExcel';
			return true;
		}
	
}

</script>
</HEAD>
  					

<html:form name="bulkAssigmentFormBean" type="com.ibm.lms.forms.BulkAssigmentFormBean" action="/bulkAssignment" enctype="multipart/form-data">
	
	<html:hidden name="bulkAssigmentFormBean" property="methodName" value="uploadExcel"/>
	
			
		<div class="box2">
        <div class="content-upload">
        
        <H1>Bulk Assignment Matrix Creation</H1>
        
			
		 <FONT color="red" style="font-size: small; margin-left: 10px;">
		 
			<html:messages id="msg" message="true">
            <bean:write name="msg"/>  
            </html:messages>			
            
            <logic:equal name="bulkAssigmentFormBean" property="isError" value="true">
			   <a href="./bulkAssignment.do?methodName=openErrLog" target="_new">click here</a>
		    </logic:equal>
            			
		    </FONT> 
					
			 
			 <ul class="list2 form1 "> 
			 
			 <li class="clearfix" > 
			 
			 <logic:equal name="bulkAssigmentFormBean" property="downloadTemplate" value="true">
			 <u> <a class="flr" href="./bulkAssignment.do?methodName=downloadTemplate" target="_new" >Click here to download template</a> </u>   
		    </logic:equal>
		    
		    </li>
			 
			 <li class="clearfix alt" >
				<span class="text2 fll width160"><strong>Select File: </strong> </span>	

				<p class="clearfix fll margin-r20"> 
				   <html:file property="newFile"/> 
				</p>		
			  </li>
			   
	   <li class="clearfix" style="padding-left:170px;">
			<span class="text2 fll">&nbsp;</span>
			<input type="image" src="images/submit.jpg" style="margin-right:10px;" onclick="return formsubmit();" styleClass="red-btn fll"/>&nbsp;&nbsp;&nbsp;&nbsp;	
		</li>
		
		</br></br>
		<li>
		<a href='bulkAssignment.do?methodName=viewAssignmentMatrixStatus'><u><b>View Pending/Rejected Assignments</b></u></a>
		</li>
				
		</ul>
		
	   <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
        <tr class="textwhite">
   
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="30" width="5%"><span class="mTop5">&nbsp;<font size="2">SL.</font></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5"><font size="2">OLM ID</font></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="1%"><span class="mTop5"><font size="2">LOB</font></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5"><font size="2"><bean:message key="view.productName" /></font></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5"><font size="2">Circle</font></span></td><!--
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5"><font size="2">PinCode</font></span></td>
			--><td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5"><font size="2">Pending with</font></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5"><font size="2">Rejected By</font></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5"><font size="2">Rejection Remarks</font></span></td>
			
			 </tr>
        

            
		<logic:empty name="masterList" scope="request">
			<TR class="lightBg">
				<TD class="text" align="center" colspan="8"><bean:message key="viewAllUser.NotFound" /></TD>
			</TR>
		</logic:empty>
		
		<logic:notEmpty name="masterList" scope="request">
			<logic:iterate name="masterList" id="report" indexId="i" scope="request">
				<%
				String cssName = "";				
				if(( i.intValue()%2)==1)
				{			
				cssName = "alt";
				}	
				%>
				
				<tr class="<%=cssName %>" >
					<TD class="txt" align="left"  width="5%"  height="20"> <font size="2">&nbsp;<%=(i.intValue() + 1)%>.&nbsp;</font></TD>
					<TD class="txt" align="left" width="10%" height="20"><font size="2"><bean:write name="report" property="olmId" /></font></TD>
					<TD class="txt" align="left" width="10%" height="20"><font size="2"><bean:write name="report" property="productLobId" /></font></TD>
					<TD class="txt" align="left" width="10%" height="20"><font size="2"><bean:write name="report" property="productName" /></font></TD>
					<TD class="txt" align="left" width="10%" height="20"><font size="2"><bean:write name="report" property="circle" /></font></TD>
					<!--<TD class="txt" align="left" width="10%" height="20"><font size="2"><bean:write name="report" property="city" /></font></TD>
					<TD class="txt" align="left" width="10%" height="20"><font size="2"><bean:write name="report" property="pincode" /></font></TD>-->
					<TD class="txt" align="left" width="15%" height="20"><font size="2"><bean:write name="report" property="pendingwith" /></font></TD>
					<TD class="txt" align="left" width="10%" height="20"><font size="2"><bean:write name="report" property="rejectedBy" /></font></TD>
					<TD class="txt" align="left" width="10%" height="20"><font size="2"><html:textarea name="report" property="remarks"  readonly="true" rows="2"/></font></TD>
						
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	
	</table>		
			
	</div>
	</div>		

			
</html:form>

</html:html>

