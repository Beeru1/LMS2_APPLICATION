
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


<link href="./jsp/theme/airtel.css" rel="stylesheet" type="text/css" />
<script language="javascript"> 
  
function callOnload()
{
	document.userBean.userLoginId.focus();
}

	function setIP(ip) {
		document.loginForm.ipaddress.value=ip;
	}

<%@ include file="/jScripts/capLock.js" %>
</script>
<APPLET code="IP" width="0" height="2" archive="./IP.jar">
</APPLET>
<html:form action="/forgotPassword" focus="userName">
<input type="hidden" value="FALSE" name="CSR">
<input type="hidden" name="ipaddress" value=""/>
<html:hidden property="kmforgot" value="true"/>
<table width="778" border="0" align="center" cellpadding="0" cellspacing="0" style="background-image:url(./images/bg_1.jpg); background-repeat:repeat-y; background-position:center top;">
  <tr>
    <td width="177" height="91" align="right"><img src="common/images/lms_logo.png" width="158" height="44" alt="iportal"/><!--
    <img src="./images/iportel_animation.gif" alt="iPortal" width="158" height="44" />-->
    </td>
    <td width="470">&nbsp;</td>
    <td width="131"><img src="./images/airtel_logo.jpg" alt="Airtel" width="107" height="80" /></td>
  </tr>
  <tr>
    <td colspan="3" align="center"><img src="./images/master_head.jpg" alt="" width="770" height="127" /></td>
  </tr>
  <tr>
    <td height="36" colspan="3"><table width="770" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="36" align="center" style="background-image:url(./images/top_heading_heading.jpg); background-repeat:repeat-x; background-position:left top;">
    <!--<img src="./images/welcome_to_iportal.jpg" alt="" width="155" height="16" />
    --><h3 style="color: red;font:bold; ">Welcome to iLMS</h3></td>
  </tr>
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
        <td width="361" align="left" valign="top" style="padding-top:3px;"><span class="login_heading"> LMS </span></td>
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
            <td width="127" height="24" align="right" class="aria_11_black"><bean:message key="login.loginId" /></td>
            <td width="8">&nbsp;</td> 
            <td width="246" align="left"><html:text name="loginForm"  property="userName" style="WIDTH: 60%" maxlength="25" styleClass="textbox7"/></td>
          </tr>
          
          <tr>
            <td width="127" height="5" align="right"></td>
            <td width="8" height="5" align="left"></td>
            <td width="246" height="5" align="left"></td>
          </tr>
          
		  <tr>
            <td width="127" height="27" align="right">&nbsp;</td>
            <td width="8">   </td>
            <td width="246" align="left"><html:image align="center"  property="submit"  src="./images/submit.jpg" /></td>
          </tr>
          
        </table></td>
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

