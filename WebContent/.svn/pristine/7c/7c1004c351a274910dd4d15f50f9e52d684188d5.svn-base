<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<LINK href="theme/css.css" rel="stylesheet" type="text/css">
<LINK href=./jsp/theme/css.css rel="stylesheet" type="text/css">
<script language="JavaScript" src="jScripts/KmValidations.js" type="text/javascript"> </script>
<logic:notEmpty name="FILE_LIST" scope="request">
<bean:define id="fileList" name="FILE_LIST" type="java.util.ArrayList" scope="request"></bean:define>
</logic:notEmpty>
<script type="text/javascript">
function formValidate()
{
	var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1;//January is 0!
		var yyyy = today.getFullYear();
		if(dd<10){dd='0'+dd}
		if(mm<10){mm='0'+mm}
		var curr_dt=yyyy+'-'+mm+'-'+dd;
		if(document.getElementById("date_selector").value > curr_dt)
		{
		alert("PLease select the past date ")
		return false;
		}
		if(document.getElementById("date_selector").value == "")
		{
		alert("PLease select the date ")
		return false;
		}
	else
	{
	document.forms[0].methodName.value = "viewBulkUploadFiles";
	return true;
	}
}
function openFile(filePath)
{

window.location.href='userBulkUpload.do?methodName=openFile&filePath='+filePath;

}
function openLogFile(filePath)
{

window.location.href='userBulkUpload.do?methodName=openFile&isLog=true&filePath='+filePath;

}

</script>

<html:form action="/userBulkUpload" onsubmit="return formValidate();" >
<html:hidden property="methodName"/>
	<div class="box2">
     <div class="content-upload">

		<ul class="list2 form1">
		 	<li  >
		 	<span class="content-upload"><h1><bean:message key="vb.heading" /></h1></span>
		 	</li>
		 	 	<li class="clearfix" >
		 	 	<font color="#0B8E7C">
		 	 	<strong>
					<html:messages id="msg" message="true">	<bean:write name="msg"/>  
                          
             							</html:messages></strong></font>
		 	 	</li>
		     	<li class="clearfix alt" >
			<span class="text2 fll" style="width:165px"><strong> <bean:message key="bv.date" /> </strong></span>							
				<span class="text2 fll width150">
				<input type="text" class="tcal calender2 fll" id="date_selector" readonly="readonly" name="date" value="<bean:write property='date' name='bulkUserUploadFormBean'/>"/> </span>
                	
       	</li>
       	<li class="clearfix" >
       	<input type="image" src="images/submit.jpg"/ >	
       	</li>
		</ul>
			
	
	<logic:notEqual name="bulkUserUploadFormBean" property="initStatus" value="init">
		<table  width="100%" align="center" border="0" cellpadding="1" cellspacing="1">
		

			<tr class="textwhite">
				<td
					style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="5%"><span class="mTop5"><bean:message
					key="file.SNO" /></span></td>

				<td
					style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5"><bean:message
					key="file.fileName" /></span></td>

				<td
					style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5"><bean:message
					key="file.fileType" /></span></td>
				<td
					style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5"><bean:message
					key="file.fileStatus" /></span></td>
				<td
					style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5"><bean:message
					key="file.uploadedBy" /></span></td>
				<td
					style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5"><bean:message
					key="file.total" /></span></td>

				<td
					style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5"><bean:message
					key="user.created" /></span></td>
				<td
					style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5"><bean:message
					key="user.updated" /></span></td>
				<td
					style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="10%"><span class="mTop5"><bean:message
					key="user.deleted" /></span></td>

			</tr>


			<logic:notEmpty name="FILE_LIST">
				<logic:iterate name="fileList" type="com.ibm.lms.dto.FileDto" id="report" indexId="i">
					<%
				String cssName = "";				
				if( i%2==1)
				{			
				cssName = "alt";
				}	
				%>
					<tr class="<%=cssName %>">

						<TD class="txt" align="left"  width="5%"><%=(i.intValue() + 1)%>.</TD>
						<TD class="txt" align="left"  width="15%"><A Href="#" onclick="javascript: openFile('<bean:write name="report" property="filePath"/>');"><FONT color ="red" ><bean:write
							name="report" property="fileName" /></FONT></a></TD>
						<TD class="txt" align="left"  width="10%"><bean:write
							name="report" property="fileType" /></TD>
						<TD class="txt" align="left"  width="10%"><bean:write name="report"
							property="status" /></TD>
						<TD class="txt" align="left"  width="10%"><bean:write name="report"
							property="uploadedBy" /></TD>
						<TD class="txt" align="left"  width="10%"><bean:write name="report"
							property="totalUsers" /></TD>
						<TD class="txt" align="left"  width="10%"><bean:write name="report"
							property="usersCreated" /></TD>
						<TD class="txt" align="left"  width="10%"><bean:write name="report"
							property="usersUpdated" /></TD>
						<TD class="txt" align="left"  width="10%"><bean:write name="report"
							property="usersDeleted" /></TD>
						<TD class="txt" align="left"  width="10%"> <logic:equal name ="report" property="status"   value="Processed"><A Href="#" onclick="javascript: openLogFile('<bean:write name="report" property="filePath"/>');"><FONT color ="red" >Log</FONT></A> </logic:equal> </TD>	

					</TR>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty name="fileList">
				<TR class="lightBg">
					<TD colspan="2" class="error" align="left"><FONT color="red"><bean:message
						key="viewAllFiles.NotFound" /></FONT></TD>
				</TR>
			</logic:empty>

		</table>
	</logic:notEqual>
	</div>		
	</div>
</html:form>

