<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<LINK href="./theme/css.css" rel="stylesheet" type="text/css">
<LINK href="./theme/text.css" rel="stylesheet" type="text/css">
<LINK href="./jsp/theme/css.css" rel="stylesheet" type="text/css">

<LINK href="theme/text.css" rel="stylesheet" type="text/css">
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
function deleteAssignmentMatrix()
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
		if (confirm("Please confirm matrix deletion."))
		{
		var oForm = document.forms[0];
		oForm.methodName.value = "DeleteAssignmentMatrix";
		oForm.submit();
		}
	}	
	return false;
}
</script>


<html:form action="assignmentMatrix.do" >
	<html:hidden name="assignmentMatrixFormBean" property="methodName" value="viewAssignmentMatrix"/>
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
					
  <div class="box2">               
   <div class="content-upload" style="margin-bottom: 0px;">
         <h1>Search Assignment Matrix</h1>
   </div>
      
   <div class="content-upload">
    <ul class="list2 form1" >   
		 <li class="clearfix alt">
          		<span class="text2 fll width160"><strong>Olm ID<font color=red>*</font> </strong> </span>
          	<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner">
          		<html:text property="olmID" styleClass="textbox77" maxlength="12" /></span></span></p>
          		<html:hidden  property="URL" value="valid"/>
			<span class="text2 fll" >&nbsp;</span><div class="button" >
			<html:submit styleClass="red-btn" value="Search" />
		</li>
    </ul> 
   </div>
  </div>
   <br/><br/><br/>
   
  <div class="box2">               
	  <div class="content-upload" style="margin-bottom: 0px;">
          <h1>List Assignment Matrix</h1>
      </div>
        
      <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
        <tr class="textwhite">
      		  <td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" width="5%"  height="20"><input type="button" value="Delete" onclick="javascript:return deleteAssignmentMatrix()"/></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="5%"><span class="mTop5">
			&nbsp;SL.</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5"><bean:message
				key="view.primaryUser" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5">
			  OLM ID</span></td>
			 <td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5">
			  Product LOB</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5"><bean:message
				key="view.productName" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5">Circle</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5">
			  Zone</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5">
			  City</span></td>
			 <td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5">
			  City Zone</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5">
			 Pin Code</span></td>
			 <td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="20" width="5%"><span class="mTop5">
			Primary Auth</span></td>
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
				</td>	<TD class="txt" align="left"  width="5%"  height="20"> &nbsp;<%=(i.intValue() + 1)%>.&nbsp;</TD>
					<TD class="txt" align="left" width="15%" height="20"><bean:write name="report"
						property="userType" />
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
					<TD class="txt" align="left" width="15%" height="20"><bean:write name="report"
						property="cityZoneCode" /></TD>
						<TD class="txt" align="left" width="10%" height="20"><bean:write name="report"
						property="pincode" /></TD>
						<TD class="txt" align="left" width="5%" height="20"><bean:write name="report"
						property="primaryAuth" /></TD>
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	
	</table>
	<br>
 </div>
</html:form>
