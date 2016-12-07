<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


<link href="./jsp/theme/airtel.css" rel="stylesheet" type="text/css" />
<head>
<script>



function isInteger(x) 
{
	return x % 1 === 0;
}


function validate()
{
   // document.forms[0].flag.value="false";
   // alert("document.forms[0].OTPCode.value"+document.forms[0].OTPCode.value);
	if(document.forms[0].OTPCode.value=="")
	{	alert("Please enter OTP Code");
		return false;
		
	}
	
  if(document.forms[0].OTPCode.value !="" )
		{	
		//alert("blockkkk");
		if(document.forms[0].OTPCode.value.length <8 || !isInteger(document.forms[0].OTPCode.value))
		{
		alert("Please enter valid 8 digit OTP Code");
		return false;
		}
		
	}
	//alert("byeeeeee");
	document.forms[0].submit();
	return true;
}


function OTPSubmit()
{
	//alert("hi");
	
	
	//alert("hi"+document.forms[0].flag.value);
	//document.getElementById("getOTP").style.display="none";
	document.getElementById("myForm").submit();
	
	
}
function reset()
{
	//alert("hiiiii");
	document.forms[0].OTPCode.value='';
}
</script>
</head>
<body onload="reset();">
<html:form action="/OTPlogin" focus="OTPCode" method='post'>

<html:hidden property="flag" value=""/>

<table width="778" border="0" align="center" cellpadding="0" cellspacing="0" style="background-image:url(./images/bg_1.jpg); background-repeat:repeat-y; background-position:center top;">
  <tr>
    
    <td width="470">&nbsp;</td>
     <td width="131"><img src="./images/airtel_logo.jpg" alt="Airtel" width="107" height="80" /></td>
  </tr>
  <tr>
    <td colspan="3" align="center"><img src="common/images/BannerImage.png" alt="login-banner" width="770" height="127" /></td>
  </tr>
  <tr>
    <td height="36" colspan="3"><table width="770" border="0" align="center" cellpadding="0" cellspacing="0">
 
</table></td>
  </tr>
  <tr>
    <td height="265" colspan="3" align="center"><table width="400" border="0" cellpadding="0" cellspacing="0" bgcolor="#D9EEF6">
      <tr>
        <td width="9" height="4" ><img src="./images/box_cov-left-top.jpg" alt="" width="6" height="4" /></td>
        <td colspan="2"></td>
        
        <td width="10" align="right"><img src="./images/box_cov-right-top.jpg" alt="" width="6" height="4" /></td>
      </tr>
      <tr>
        <td height="19">&nbsp;</td>
        <td width="20" height="19" valign="top"><span class="login_heading"><img src="./images/lock.jpg" alt="" width="15" height="17" /></span><br /> </td>
        <td width="361" align="left" valign="top" style="padding-top:3px;"><span class="login_heading"> LMS Portal </span></td>
        <td width="10">&nbsp;</td>
      </tr>
      <tr>
				<td colspan="4" class="error">
				<strong> <html:errors  /></strong></td>
		</tr>
		<tr>
				<td colspan="4" class="text">
				<strong> <bean:write name="loginForm" property="message" /></strong></td>
		</tr> 
      <tr>
        <td>&nbsp;</td>
        <td colspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          
          <tr>
            <td width="127" height="24" align="right" class="aria_11_black"><bean:message key="login.otp" /></td>
            <td width="8">&nbsp;</td> 
            <td width="246" align="left"><html:password name="loginForm"  property="OTPCode" style="WIDTH: 60%" maxlength="8" /></td>
          </tr>
          
          <tr>
            <td width="127" height="5" align="right"></td>
            <td width="8" height="5" align="left"></td>
            <td width="246" height="5" align="left"></td>
          </tr>
		  <tr>
            <td width="127" height="27" align="right">&nbsp;</td>
            <td width="8">   </td>
            <td width="246" align="left">
            <html:image align="center"  property="submit"  onclick="return validate();" src="./images/submit_button.jpg" /></td>
            
            </tr>
        </table>
        </br>
        
          <A HREF='#' id="getOTP" onclick='OTPSubmit();'><u><b><font size="2">OTP not received? Click to resend.</font></b></u></A>
          
        </td>
        <td width="10">&nbsp;</td>
      </tr>
      
      <tr>
        <td height="4"><img src="./images/box_cov-left-bottom.jpg" alt="" width="6" height="4" /></td>
        <td colspan="2"></td>
        <td width="10" align="right"><img src="./images/box_cov-right-bottom.jpg" alt="" width="6" height="4" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="55" colspan="3" align="center"><table width="771" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="55" align="left" valign="top" class="whttext" style="background-image:url(./images/bottom_bg.jpg); background-repeat:repeat-x; background-position:left top; padding-left:8px; padding-top:8px;">&copy; 2008 IBM Bharti, All Right Reserved.</td>
  </tr>
</table></td>
  </tr>
</table>
</html:form>
 <html:form  method='post' action="OTPlogin.do" styleId="myForm">	
 <html:hidden property="flag" value="true"/>
        </html:form>
</body>