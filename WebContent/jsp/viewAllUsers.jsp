
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<LINK href=./jsp/theme/css.css rel="stylesheet" type="text/css">

<SCRIPT language="JavaScript" type="text/javascript">

function importExcel()
{


document.forms[0].methodName.value= "excelImport";
document.forms[0].submit();

}
function formSubmit(userId)
{

document.forms[0].selectedUserId.value=userId;
document.forms[0].methodName.value= "initEdit";
document.forms[0].submit();
return false;
}

function test()
{
	alert("Hello world");
}
</SCRIPT>
<html:form action="/kmUserMstr" >
<html:hidden property="methodName" />
<html:hidden property="selectedUserId" />
  
 
		
	<div style="position: absolute; z-index: 100; margin-left: 700px;">	<img  src="images/excel.gif" width="39" height="35" border="0" alt="Download Report" onclick="importExcel();" /></div>
      <div class="box2" style="height:500px; overflow:auto; ">               
		<div class="content-upload" style="margin-bottom: 0px;">
          <h1><bean:message key="viewAllUser.ViewUsers" /></h1>
        </div>
        <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
		
		
		
		<tr align="left">
 		 <td colspan="10"><strong><bean:write property="message" name="kmUserMstrFormBean" /></strong></td>
		 </tr>
		<tr>
		 <td colspan="10" align="center" class="error">
		  <strong><html:messages id="msg" message="true"><bean:write name="msg"/></html:messages></strong>
         </td>
		</tr>
        		        
        <tr class="textwhite">
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="5%"><span class="mTop5">
			&nbsp;SL.</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5"><bean:message
				key="viewAllUser.UserLoginId" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5"><bean:message
				key="viewAllUser.FirstName" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5"><bean:message
				key="viewAllUser.LastName" /></span></td>
		<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5">
			  Mobile No.</span></td> 
			 <td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5">
			  Circle</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x; " height="35" width="15%"><span class="mTop5"> &nbsp;&nbsp;&nbsp;&nbsp; <bean:message
				key="viewAllUser.EmailId" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="5%"><span class="mTop5">Partner</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="5%"><span class="mTop5"><bean:message
				key="viewAllUser.UserType" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="5%"><span class="mTop5"><bean:message
				key="viewAllUser.Status" /></span>&nbsp;</td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="5%"><span class="mTop5">Action</span></td>
		
		</tr>
        

            
		<logic:empty name="kmUserMstrFormBean" property="userList">
			<TR class="lightBg">
				<TD class="text" align="center"><bean:message
					key="viewAllUser.NotFound" /></TD>
			</TR>
		</logic:empty>
		
		<logic:notEmpty name="kmUserMstrFormBean" property="userList">
			<logic:iterate name="kmUserMstrFormBean" id="report" indexId="i" property="userList">
				<%
				String cssName = "";				
				if( i%2==1)
				{			
				cssName = "alt";
				}	
				%>
				
				<tr class="<%=cssName %>" >
					<TD class="txt" align="left"  width="5%"> &nbsp;<%=(i.intValue() + 1)%>.&nbsp;</TD>
					<TD class="txt" align="left" width="15%"><bean:write name="report"
						property="userLoginId" /></TD>
					<TD class="txt" align="left" width="15%"><bean:write name="report"
						property="userFname" /></TD>
					<TD class="txt" align="left" width="15%"><bean:write name="report"
						property="userLname" /></TD>
			<TD class="txt" align="left" width="15%"><bean:write name="report"
						property="userMobileNumber" /></TD>
					<TD class="txt" align="left" width="15%"><bean:write name="report"
						property="circleName" /></TD>
					<TD class="txt" align="left" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="report"
						property="userEmailid" /></TD>
					<TD class="txt" align="left" width="5%" ><bean:write name="report"
						property="partnerName" /></TD>
					<TD class="txt" align="left" width="5%"><bean:write name="report"
						property="userType" /></TD>
					<TD class="txt" align="left" width="5%"><bean:write name="report"
						property="status" /></TD>
					<TD class="txt" align="left" width="5%"><A
						href="javascript: formSubmit('<bean:write name="report" property="userId"/>');" ><FONT color="red"><bean:message
						key="viewAllUser.edit" /></FONT></A></TD>
				
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	
	</table>
	<br>
 </div>
</html:form>

