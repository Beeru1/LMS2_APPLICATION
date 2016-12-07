<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page session="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>iLMS</title>
<link href="common/css/style.css" type="text/css" rel="stylesheet" />
<script language="JavaScript" src="jScripts/LmsValidations.js" type="text/javascript"> </script>
<script type="text/javascript" src="jScripts/jquery-1.4.2.min.js"></script>
<!-- the jScrollPane script -->
<script language="javascript">
<%@ include file="/jScripts/capLock.js" %>
<!--//
function myPopup(url,windowname,w,h,x,y){
//window.open(url,'_blank',"resizable=yes,toolbar=no,scrollbars=yes,menubar=no,status=no,directories=no,width="+w+",height="+h+",left="+x+",top="+y+"");
	if (window.showModalDialog) {
		window.open(url,'dummydate',"resizable=yes,toolbar=no,scrollbars=yes,menubar=no,status=no,directories=no,modal=yes,location=no,width="+w+",height="+h+",left=5,top=5");
		//window.showModalDialog(url,'dummydate',"dialogWidth:1000px;dialogHeight:1000px");
	} else {
		window.showModalDialog(url,'dummydate',"dialogWidth:1000px;dialogHeight:1000px");
		//window.open(url,'dummydate',"resizable=yes,toolbar=no,scrollbars=yes,menubar=no,status=no,directories=no,modal=yes,location=no,width="+w+",height="+h+",left=5,top=5");
	}
}
//-->
</script>
<script><!-- 
 
function callOnload()
{
	document.loginForm.userId.focus();
}
function validate()
{
   document.getElementById("userId").value=trimAll(document.getElementById("userId").value);
   document.getElementById("password").value=trimAll(document.getElementById("password").value);
   

    if(document.getElementById("userId").value=="Login Id" || document.getElementById("userId").value==0)
 	{
	 	alert("Please Enter Login Id");
	 	return false;
  	}
  	else
  	if(document.getElementById("password").value=="Password" || document.getElementById("password").value==0)
 	{
	 	alert("Please Enter Password");
	 	return false;
  	}	
  	else
  	{  	  
  	  return true;
 	}
}

	/*function setIP(ip) {
		document.loginForm.ipaddress.value=ip;
		alert(ip);
	}*/
</script>

</head>
<body>
<APPLET code="IP" width="0" height="2" archive="./IP.jar">
</APPLET>
<html:form action="/Altlogin" >
<input type="hidden" value="FALSE" name="CSR"/>
<input type="hidden" name="ipaddress" value=""/>
<div class="wrapper">
  <div class="logo-area">
    <div class="logo_iportal"><img style="margin-top:10px " src="common/images/lms_logo.png" width="158" height="44" alt="iportal" /></div>
    <div class="logo_airtel"><a href="http://www.airtel.in/wps/wcm/connect/airtel.in/airtel.in/home/"><img src="common/images/logo-airtel.jpg" alt="airtel" width="106" height="28" border="0" /></a></div>
  </div>
  <div class="navigation">
    <div class="navigation-left"></div>
    <div class="navigation-middle clearfix"> </div>
    <div class="navigation-right"></div>
  </div>
   <div class="two-column-layout3 clearfix">
    <div class="column1"> 
    	<img src="common/images/BannerImage.png" alt="login-banner" height="328px" width="665px"/> </div>
     <div class="column2">
      <h1><img src="common/images/welcome-iportal.png" /></h1>
       <div class="inner">
        <ul class="list16">
		 <li>
				<span>
				<strong> <html:errors  /></strong></span>
		 </li>
		 <li>
				<span>
				<strong> <html:errors name="errors.login.invalid_id"  /></strong></span>
		 </li>
		 <li>
				<span>
				<strong> <bean:write name="loginForm" property="message" /></strong></span>
		 </li>         
         <li>               
          <span class="textbox12">
             <html:text name="loginForm" property="userId" styleId="userId" maxlength="50" styleClass="textbox12-inner" value="Login Id" onblur="if(this.value=='') this.value='Login Id';" onfocus="if(this.value=='Login Id') this.value='';" ></html:text>
            </span>
          </li>
          <li>         
           <span class="textbox12" >
            <html:password name="loginForm"  styleId="password" maxlength="50" property="password"   redisplay="false" styleClass="textbox12-inner" value="Password" onblur="if(this.value=='') this.value='Password';" onfocus="if(this.value=='Password') this.value='';"></html:password>
           </span>
          </li>
          <li>
           	<html:submit styleClass="sign-in" style="font-size:0px;" onclick="return validate();" title="Click to Login" ></html:submit>
          </li>
            <li><a href="initForgotPassword.do?KmForgot=true">Forgot password</a></li>
        </ul>
        
        
      </div>
    </div>
  </div>
  <div class="footer">© airtel India 2013, All Rights Reserved.</div>
</div>

</html:form>
 
<script>
$(document).ready(function(){
    $(document).keydown(function(event) { 
        if (event.ctrlKey==true && (event.which == '118' || event.which == '86')) {
            alert('Do not Paste!');
            event.preventDefault();
         }
    });
});
</script>
</body>
</html>
