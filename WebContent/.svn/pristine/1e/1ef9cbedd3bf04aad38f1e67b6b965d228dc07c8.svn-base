<html>
<head>
<title>LMS :: Change Password</title>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<bean:define id="kmUserBean" name="USER_INFO"  type="UserMstr" scope="session" />
<%@ page 
language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
import="com.ibm.lms.dto.UserMstr"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<bean:define id="kmUserBean" name="USER_INFO"  type="UserMstr" scope="session" />

<link href="common/css/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jScripts/DateTime.js"></script>
<script src="common/js/jquery-1.8.2.min.js" type="text/javascript"></script>

<script type="text/javascript" src="common/js/jquery.mousewheel.js"></script>
<script type="text/javascript" src="common/js/jquery.jscrollpane.min.js"></script>


<script type="text/javascript">
	$(function()
	{
		$('.scroll-pane').jScrollPane(
		);
	});
</script>

<script type="text/javascript">
 window.history.forward(1);
 </script>
<SCRIPT>

function csrLogout()
{
opener.location.href='./Logout.do';
self.close();

}
function viewCSRHome()
{
	if (confirm("I have read and understood the briefing for the day and will abide by it. Thanks."))
	{
		window.location.href="csrAction.do?methodName=viewCSRHome";
	}
	else
	{
		return false;
	}
}


var searchReq = getXmlHttpRequestObject();
var logoutRequest = getXmlHttpRequestObject();

//Gets the browser specific XmlHttpRequest Object
function getXmlHttpRequestObject() {
	if (window.XMLHttpRequest) {
		return new XMLHttpRequest();
	} else if(window.ActiveXObject) {
		return new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		alert("Browser Doesn't Support AJAX");
	}
}

function handleOnClose() {
   if((window.event.clientX<0) ||(window.event.clientY<0)){
		logoutonBrowserClose();
		
   }
}

function logoutonBrowserClose() 
{
	opener.location.href="./Logout.do";
	if(window.XmlHttpRequest) {
		logoutRequest = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		logoutRequest=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(logoutRequest==null) {
		alert("Browser Doesn't Support AJAX");
		return;
	}
		url="Logout.do";
		searchReq.open("GET",url,true);
		searchReq.send();
}


function timer1(){
	
	window.setTimeout('showSessionAlert()',1505000);
	
	
}
function showSessionAlert(){

	if(confirm("Your session will be expired within 5 minutes. You want to open a new session ?")){
		window.location.href = window.location;
	}	
	else{
			try{
					window.opener.location.href = "Logout.do"
					self.close();
				}
				catch(e){
					self.close();
				}
	}
		
}

timer1();


</SCRIPT>
<script language="JavaScript" src="jScripts/KmValidations.js" type="text/javascript"> </script>
<script language="JavaScript" type="text/javascript">
function validateData(){
 
 	if(isEmpty(document.kmChangePasswordForm.oldPassword)){
			alert("Please enter Old Password");
			document.kmChangePasswordForm.oldPassword.focus();
			return false;
	}
	var reg=/[\s]+/;
	
	if(reg.test(document.kmChangePasswordForm.newPassword.value)||reg.test(document.kmChangePasswordForm.confirmPassword.value)){
		alert("Space is not allowed in the Password");
		document.kmChangePasswordForm.newPassword.value="";
		document.kmChangePasswordForm.confirmPassword.value="";
		document.kmChangePasswordForm.newPassword.focus();
		return false;
	}	
	if(isEmpty(document.kmChangePasswordForm.newPassword)){
			alert("Please enter New Password");
			document.kmChangePasswordForm.newPassword.focus();
			return false;
	}
	if(isEmpty(document.kmChangePasswordForm.confirmPassword)){
			alert("Please enter Confirm Password");
			document.kmChangePasswordForm.confirmPassword.focus();
			return false;
	}
	if(document.kmChangePasswordForm.newPassword.value != document.kmChangePasswordForm.confirmPassword.value)
		{
			alert("The Password and Confirm password do not match!");
			document.kmChangePasswordForm.newPassword.focus();
			document.kmChangePasswordForm.newPassword.value="";
			document.kmChangePasswordForm.confirmPassword.value="";			
			return false;
		} 
	document.kmChangePasswordForm.methodName.value="changePassword";
	//document.kmChangePasswordForm.submit();	
	
}
function setFocus()
{
	document.kmChangePasswordForm.oldPassword.focus();
	
}


</script>
</head>

<body>
                   	
<html:form action="/kmChangePassword"> 
<html:hidden property="methodName" />
 <% String csrflag = (String)request.getAttribute("csrFirstLogin") ; 
	String memberFlag = (String)request.getAttribute("FirstLogin") ; 
       if(csrflag==null) {
       	 	csrflag="false" ; 
       	 } 
       	  if(memberFlag==null) {
       	 	memberFlag="false" ; 
       	 }
       	
%> 	      
<input type="hidden"  name="flagLogin" value="<%=csrflag %>" />
<input type="hidden"  name="memberLogin" value="<%=memberFlag %>" />   
<div align="center">
  <div class="wrapper" align="left">   	
  <div class="logo-area">
   <div class="logo_iportal"><img src="common/images/lms_logo.png" width="158" height="44" alt="iLMS" /></div>
   <div class="logo_airtel"><a href="http://www.airtel.in/wps/wcm/connect/airtel.in/airtel.in/home/"><img src="common/images/logo-airtel.jpg" alt="airtel" width="106" height="28" border="0" /></a></div>
  </div>
  
  <div class="admin">
   <div class="admin-content"><h6>welcome <span><bean:write name='kmUserBean' property="userFname" /></span></h6>&nbsp;&nbsp;
	<span class="white10bold">Time :&nbsp;</span><span class="white10" id="clock"><SCRIPT language="javascript" src="./jScripts/DateTime.js"></SCRIPT> <SCRIPT>getthedate();dateTime();</SCRIPT> </span>
   </div>
   <div  align="right" style="margin-right: 10px">
   <a href="./Logout.do" style=""><img src="common/images/logout.png" alt="Log Out" title="Log Out" height="25" width="25" border="0"  /></a>
   </div>
  </div>
	  <div class="box2">
       <div class="content-upload">
         <h1><bean:message key="changePassword.changepassword" /></h1>
	    
			<strong style="margin-left: 10px"> 
          	  <html:messages id="msg" message="true">
                 <bean:write name="msg"/>  <a href="./Logout.do"><font color="blue">click...</font></a>              
              </html:messages>
            </strong><br> 
            <strong style="margin-left: 10px"> <html:errors /></strong>
        
	   <ul class="list2 form1">		
		 <li class="clearfix">
          	<span class="text2 fll width160"><strong><bean:message key="changePassword.oldPassword" /></strong></span>
			<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner">
				<html:password styleClass="textbox7" property="oldPassword" maxlength="250"  /></span> </span> </p>
		 </li>
		 <li class="clearfix">
          	<span class="text2 fll alt width160"><strong><bean:message	key="changePassword.newPassword" /></strong></span>
			<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner">
				<html:password styleClass="textbox7" property="newPassword" maxlength="255" /></span> </span> </p>
		 </li>
		 <li class="clearfix">
          	<span class="text2 fll width160"><strong><bean:message key="changePassword.confirmPassword" /></strong></span>
			<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner">
				<html:password styleClass="textbox7" property="confirmPassword" maxlength="255" /></span> </span> </p>
		 </li>
	  	 <li class="clearfix alt" style="padding-left:170px;">
			<span class="text2 fll">&nbsp;</span>
			<input type="image" src="images/submit.jpg" class="btnActive" onclick="return validateData();" />			
		</li>
		<li class="text2 fll"  >
		 <b>Password Selection Criteria: </b><br>
 		  <p style="margin-left: 20px">
			1. Contain at least 8 characters.<br>
			2. Contain at least one alphabetic and one non-alphabetic.<br>
			3. Contain at least one upper case character.<br>
			4. Non-numeric in first and last position.<br>
			5. Maximum consecutive identical character from any position in the previous password must be 3.<br>
			6. Maximum identical consecutive characters 2.<br>
			7. Must not contain the UserID as part of the password.<br>
			8. Number of password changes for which the password cannot be reused is 4. </p>		
			<br>
			<b>Notes:</b><BR>
		  <p style="margin-left: 20px">
			1. Password will expire after 45 days.<br>
			2. After 3 login attempts with invalid passwords the user account will be locked.
		 </p>
		</li>
	   </ul>
	  </div> 
	 <BR>
		
	  	<div class="footer">© airtel India 2013, All Rights Reserved.</div>
	</div>

</div> 
 
 </html:form>
 
<script>
$(document).ready(function(){
    $(document).keydown(function(event) {
        if (event.ctrlKey==true && (event.which == '118' || event.which == '86')) {
            alert('Do not PASTE!');
            event.preventDefault();
         }
    });
});
setFocus();
</script>
</body>
</html>