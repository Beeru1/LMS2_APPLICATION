<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<LINK href="theme/css.css" rel="stylesheet" type="text/css">
<LINK href=./jsp/theme/css.css rel="stylesheet" type="text/css">
<script type="text/javascript">
function importExcel(){
	window.location.href="userReport.do?methodName=lockedUserReportExcel";
}
</SCRIPT>
   <p align="right">   <img  src="images/excel.gif" width="59" height="35" border="0" onclick="importExcel();" />    </p>
	
	
	<div class="box2">
        <div class="content-upload">
       		<h1><bean:message key="lockedUser.title" /></h1>
			
	<table width="100%" align="center" cellpadding="0" cellspacing="0">
		<tr align="right" >
				<td width="147" class="pLeft10"></td>
				<td width="636"  colspan="5"></td>
        </tr>	

				<tr class="text white">

					<th width="5%" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span >&nbsp;S.No</span>
					</th>
					<th width="25%" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span >User LoginId</span></th>
					<th width="30%" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span >First Name</span>
					</th>
					
					<th width="20%" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span >Last Name</span></th>
					<th width="20%" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span >Lock Type</span></th>
					<!--<th class="darkRedBg height19"><span >Last LogIn Time</span></th>-->
					

				</tr>
              	 <logic:notEmpty name="LOCKED_USER_LIST">
              	 <bean:define id="userList" name="LOCKED_USER_LIST" type="java.util.ArrayList" scope="request" />
				<logic:iterate name="userList" id="user" indexId="i" type="com.ibm.lms.dto.UserMstr">
					<%
					String cssName = "";				
					if( i%2==1)
					{			
					cssName = "alt";
					}	
					%>
					<tr class="<%=cssName%>">

						<TD height="20px" class="text" align="left">&nbsp;<%=(i.intValue() + 1)%>.&nbsp;</TD>
						<TD class="text" align="left"><bean:write name="user"
							property="userId" /></TD>
						<TD class="text" align="left"><bean:write name="user"
							property="firstName" /></TD>
						<TD class="text" align="left"><bean:write name="user"
							property="lastName" /></TD>
						<TD class="text" align="left"><bean:write name="user"
							property="lockType" /></TD>
					</TR>
				</logic:iterate>
		</logic:notEmpty>
			<logic:empty name="LOCKED_USER_LIST"  >
				<TR class="lightBg">
				<TD colspan="2" class="error" align="left"><FONT color="red"><bean:message
					key="viewAllFiles.NotFound" /></FONT></TD>
			</TR>
			</logic:empty>
		</table>
  </div>
</div>