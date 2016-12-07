<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%@page import="com.ibm.lms.wf.forms.AssignmentMatrixFormBean"%><LINK href="./theme/css.css" rel="stylesheet" type="text/css">
<LINK href="./theme/text.css" rel="stylesheet" type="text/css">
<LINK href="./jsp/theme/css.css" rel="stylesheet" type="text/css">

<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
    document.getElementById('textarea-id').setAttribute('maxlength', '512');
</script>

<script language="JavaScript" type="text/javascript">


function importExcel()
{
document.assignmentMatrixFormBean.methodName.value= "excelImport";

document.assignmentMatrixFormBean.submit();
}
function uploadExcel()
{
document.assignmentMatrixFormBean.methodName.value= "uploadExcel";
document.assignmentMatrixFormBean.submit();
return false;
}
function approveAssignmentMatrix()
{
	var chks = document.forms[0].ids;
	if (chks){ //checkbox(s) exists
		var checked = false;
		if (chks.length){ //multiple checkboxes
			var len = chks.length;
			for (var i=0; i<len; i++){
				if (chks[i].checked){
					checked = true;
					break;
				}
			}
		}
		else{ //single checkbox only
			checked = chks.checked;
		}
		if (!checked){
			alert("Please Check atleast one Row.");
			return false;
		}
		else 
		if (confirm("Please confirm matrix approval."))
		{
		var oForm = document.forms[0];
		oForm.flag.value="true";
		if(oForm.remarks.value == "")
		{
		alert("Comments are required!");oForm.remarks.focus(); 
		return false;
		}

		oForm.methodName.value = "rejectAssignmentMatrix";
		oForm.submit();
		}
	}	
	return false;
}

function rejectAssignmentMatrix()
{
	var chks = document.forms[0].ids;
	if (chks){ //checkbox(s) exists
		var checked = false;
		if (chks.length){ //multiple checkboxes
			var len = chks.length;
			for (var i=0; i<len; i++){
				if (chks[i].checked){
					checked = true;
					break;
				}
			}
		}
		else{ //single checkbox only
			checked = chks.checked;
		}
		if (!checked){
			alert("Please Check atleast one Row.");
			return false;
		}
		else 
		if (confirm("Please confirm matrix rejection."))
		{
		var oForm = document.forms[0];
		oForm.flag.value="false";
		if(oForm.remarks.value == "")
		{
		alert("Comments are required!");oForm.remarks.focus(); 
		return false;
		}
		oForm.methodName.value = "rejectAssignmentMatrix";
		oForm.submit();
		}
	}	
	return false;
}

</script>


<html:form action="assignmentApprovals.do" >
	<html:hidden name="assignmentMatrixFormBean" property="methodName" value=""/>
	<html:hidden name="assignmentMatrixFormBean" property="flag" value=""/>
	 <FONT color="red" style="font-size: small">
		 <tr>
				<td colspan="2" align="left" class="error">
					<strong> 
          		  <logic:present name = "assignmentMatrixFormBean" property="msg"> 
          			<logic:notEmpty  name = "assignmentMatrixFormBean" property="msg">               		
          			<bean:write property="msg" name="assignmentMatrixFormBean"/>  <br>                          
             		</logic:notEmpty> 
             		</logic:present>
             	
            		</strong>
            	</td>
		</tr>
				<tr>
				<td colspan="10" align="center" class="error">
					<strong> 
          		  	<html:messages id="msg" message="true">
                 		<bean:write name="msg"/>  
                          
             		</html:messages>
             		
            		</strong>
            	</td>
		</tr>					
			<logic:equal name="assignmentMatrixFormBean" property="isError" value="true">
			   <a href="Docs/bulkFeasibilityErrorLog.csv" target="_new">click here</a>
		    </logic:equal>
		    </FONT> 
					
     <br/><br/><br/>
   
  <div class="box2">               
	  <div class="content-upload" style="margin-bottom: 0px;">
          <h1>List Assignment Matrix</h1>
      </div>
        
      <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
        <tr class="textwhite">
      		<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" width="5%"  height="20"><input type="button" value="Approve" onclick="javascript:return approveAssignmentMatrix();"/></td>
      		<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" width="5%"  height="20"><input type="button" value="Reject" onclick="javascript:return rejectAssignmentMatrix();"/></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="5%"><span class="mTop5">&nbsp;SL.</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5"><bean:message key="view.primaryUser" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5">OLM ID</span></td>
			 <td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5">Product LOB</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5"><bean:message key="view.productName" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5">Circle</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5"> Zone</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5">City</span></td>
			 
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5">Pin Code</span></td>
			 </tr>
        

            
		<logic:empty name="assignmentMatrixList" scope="request">
			<TR class="lightBg">
				<TD class="text" align="center" colspan="8"><bean:message
					key="viewAllUser.NotFound" /></TD>
			</TR>
		</logic:empty>
		
		<logic:notEmpty name="assignmentMatrixList" scope="request">
			<logic:iterate name="assignmentMatrixList" id="report" indexId="i" scope="request">
				<%
				String cssName = "";				
				if(( i.intValue()%2)==1)
				{			
				cssName = "alt";
				}	
				%>
				
				<tr class="<%=cssName %>" >
				<td width="5%"  height="20"><input type="checkbox" name="ids" id="ids" value="<bean:write name="report"	property="olmId" />,<bean:write name="report" property="assignmentKey" />,<bean:write name="report"	property="primaryAuth" />">
				</td>
				<td></td>	
				<TD class="txt" align="left"  width="5%"  height="20"> &nbsp;<%=(i.intValue() + 1)%>.&nbsp;</TD>
					<TD class="txt" align="left" width="15%" height="20"><bean:write name="report" property="userType" />
					</TD>
					<TD class="txt" align="left" width="10%" height="20"><bean:write name="report"
						property="olmId" /></TD>
					<TD class="txt" align="left" width="10%" height="20"><bean:write name="report"
						property="productLobId" /></TD>
					<TD class="txt" align="left" width="10%" height="20"><bean:write name="report"
						property="productName" /></TD>
					<TD class="txt" align="left" width="10%" height="20"><bean:write name="report"
						property="circle" /></TD>
					<TD class="txt" align="left" width="15%" height="20"><bean:write name="report"
						property="cityZone" /></TD>
					<TD class="txt" align="left" width="10%" height="20"><bean:write name="report"
						property="city" /></TD>
					
						<TD class="txt" align="left" width="10%" height="20"><bean:write name="report"
						property="pincode" /></TD>
						
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	
	</table>
	<br>
	<fieldset>
							<legend><h5>Remarks for Rejection/Approval</h5></legend>
							<table border="0" width="100%" cellpadding="1" cellspacing="1">
							
							<tr>
								<td class="text" align="right" valign="top" style="padding-top:3px;"><font color="#4b0013"><b>Comments</b></font></td>
							    <td class="pTop3 pLeft5"><html:text styleClass="textbox" property="remarks"  value="" maxlength="60" size="50" ></html:text></td>
							</tr>
												
							</table>
						</fieldset>
 </div>
</html:form>
