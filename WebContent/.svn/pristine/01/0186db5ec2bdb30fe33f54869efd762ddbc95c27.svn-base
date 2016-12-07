<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<html:form action="/kmUserMstr">
<html:hidden name="kmUserMstrFormBean" property="userId" />
<html:hidden name="kmUserMstrFormBean" property="userLoginId" />
<html:hidden name="kmUserMstrFormBean" property="methodName"  /> 
<div class="box2">
 <div class="content-upload">
  <h1><bean:message key="viewUser.viewUser" /></h1>
	<table align="center" cellpadding="0" cellspacing="0">
		<tr><td align="left"><br><strong> <html:errors /></strong></td>		</tr>
	</table>
	<ul class="list2 form1 ">
		
		<li class="clearfix" id="actor" >
				<span class="text2 fll width160"><strong><bean:message key="updateUser.UserLoginId" /></strong></span>
			<p class="clearfix fll margin-r20"> 
			<bean:write name="kmUserMstrFormBean" property="userLoginId" /> </p>
		</li>
		<li class="clearfix alt" id="actor" >
				<span class="text2 fll width160"><strong>Role</strong></span>
			<p class="clearfix fll margin-r20"> 			<bean:write name="kmUserMstrFormBean" property="role" />
		</li>
		
		<li class="clearfix  " id="actor" >
				<span class="text2 fll width160"><strong>Lob Name</strong></span>
			<p class="clearfix fll margin-r20"> 			<bean:write name="kmUserMstrFormBean" property="lobId" />
		</li>
		<li class="clearfix alt" id="actor" >
				<span class="text2 fll width160"><strong>Circle Name</strong></span>
			<p class="clearfix fll margin-r20"> 			<bean:write name="kmUserMstrFormBean" property="circleName" />
		</li>
		<li class="clearfix  " id="actor" >
				<span class="text2 fll width160"><strong>Zone Name</strong></span>
			<p class="clearfix fll margin-r20"> 			<bean:write name="kmUserMstrFormBean" property="zoneName" />
		</li>
		<li class="clearfix alt" id="actor" >
				<span class="text2 fll width160"><strong>City Zone Name</strong></span>
			<p class="clearfix fll margin-r20"> 			<bean:write name="kmUserMstrFormBean" property="cityZoneName" />
		</li>
		
		<li class="clearfix " id="actor" >
				<span class="text2 fll width160"><strong><bean:message
				key="updateUser.FirstName" /></strong></span>
			<p class="clearfix fll margin-r20"> 			<bean:write name="kmUserMstrFormBean" property="userFname" />
		</li>
		<li class="clearfix alt" id="actor" >
				<span class="text2 fll width160"><strong><bean:message key="updateUser.MiddleName" /></strong></span>
			<p class="clearfix fll margin-r20"> 
			<bean:write name="kmUserMstrFormBean" property="userMname" />
		</li>
		<li class="clearfix " id="actor" >
				<span class="text2 fll width160"><strong><bean:message key="updateUser.LastName" /></strong></span>
			<p class="clearfix fll margin-r20"> 
			<bean:write name="kmUserMstrFormBean" property="userLname" />
		</li>
		<li class="clearfix alt" id="actor" >
				<span class="text2 fll width160"><strong><bean:message key="updateUser.E-Mail" /></strong></span>
			<p class="clearfix fll margin-r20"> 
			<bean:write name="kmUserMstrFormBean" property="userEmailid" />
		</li>
		<li class="clearfix " id="actor" >
			<span class="text2 fll width160"><strong><bean:message key="createUser.Mobile" /></strong></span>
					<p class="clearfix fll margin-r20"> 
					<bean:write name="kmUserMstrFormBean" property="userMobileNumber" />
	 	</li>
		<li class="clearfix alt" id="actor" >
				<span class="text2 fll width160"><strong>Partner Name</strong></span>
			<p class="clearfix fll margin-r20"> 
			<bean:write name="kmUserMstrFormBean" property="partnerName" />
		</li>
		<li class="clearfix " id="actor" >
				<span class="text2 fll width160"><strong><bean:message key="updateUser.Status" /></strong></span>
				<bean:write name="kmUserMstrFormBean" property="status" />
		</li>
		
		<li class="clearfix alt" id="actor" >
				<span class="text2 fll width160"><strong><bean:message key="updateUser.createdBy"/></strong></span>
			<p class="clearfix fll margin-r20">
			<bean:write name="kmUserMstrFormBean" property="createdBy" /></p>
		<logic:present name="kmUserMstrFormBean" property="createdBy" >
			<span class="text2 fll width100"><strong>On</strong></span> 
			<p>  <bean:write name="kmUserMstrFormBean" property="createdDt" /> </p>
			  </logic:present>
		</li>
	
			<li class="clearfix" id="actor" >
				<span class="text2 fll width160"><strong><bean:message
				key="updateUser.updatedBy" /></strong></span>
			<p class="clearfix fll margin-r20">
			<bean:write name="kmUserMstrFormBean" property="updatedBy" /></p>
			<logic:present name="kmUserMstrFormBean" property="updatedBy" >
			<span class="text2 fll width100"><strong>On</strong></span> 
			  <p><bean:write name="kmUserMstrFormBean" property="updatedDt" /> </p>
			  </logic:present>
		</li>
	
 	  </ul>
	</div>
  </div>
</html:form>