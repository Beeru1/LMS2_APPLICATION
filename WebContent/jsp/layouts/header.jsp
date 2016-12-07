<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page 
language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
import="com.ibm.lms.dto.UserMstr"
%>
<%@page import="java.util.ArrayList" %>
<%@page import="com.ibm.lms.dto.LinkMstrDto" %>

<script type="text/javascript" src="jScripts/DateTime.js"></script>
<style type="text/css"> 
		.HL {background: #ffff99; 
		color: #000000;}  
</style> 
<style>
body {
    font-family:Calibri;
}
#noti_Container {
    position:relative;
    border:1px solid blue;
    width:16px;
    height:16px;
}
.noti_bubble {

height: 10px;
    -moz-border-radius: 20px;
    border-radius: 20px;
    width: 20px;
    padding: 3px;
    display: inline-block;
    color: #fff;
    text-shadow: 0px 1px 1px #707070;
    font-weight: bold;
    font-family: comic sans;
    text-align: center;
    font-size: 10px;
    top: -13px;
    right: 10px;
    position: absolute;
    position:absolute;
    top: -4px;
    right:-4px;
    padding:1px 1px 1px 1px;
    background-color:red;
    color:white;
    font-weight:bold;
    font-size:0.55em;
    
    border-radius:10px;
    box-shadow:1px 1px 1px gray;
}
</style>
<script language="javascript">
window.history.forward(1);

function clickLink(url){
		window.open(url,'_blank',"resizable=yes,location=yes,toolbar=no,scrollbars=yes,menubar=no,status=no,directories=no,width=750,height=300,left=10,top=10");
}

function highlight(container,what,spanClass) {
		var content = container.innerHTML;
		var	pattern = new RegExp('(>[^<.]*)(' + what + ')([^<.]*)','g');
		var replaceWith = '$1<span ' + ( spanClass ? 'class="' + spanClass + '"' : '' ) + '>$2</span>$3';
		var highlighted = content.replace(pattern,replaceWith);
    return (container.innerHTML = highlighted) !== content;
}
</script>
<bean:define id="kmUserBean" name="USER_INFO"  type="UserMstr" scope="session" />
	<div class="logo-area">
	    <div class="logo_iportal"><img style="margin-top:10px " src="common/images/lms_logo.png" width="158" height="44" alt="iLMS"/></div>
	    <div class="logo_airtel"><img src="images/logo-airtel.jpg" alt="airtel" width="106" height="28" border="0" /></div><br><br><br>
	</div>
    
    <div class="admin clearfix">
     <div class="admin-content" >
      <h6>welcome <span><bean:write name='kmUserBean' property="userFname" /></span>&nbsp;(<bean:write name='kmUserBean' property="kmActorName" />)
      &nbsp;| last login : 
         <bean:write name='kmUserBean' property="lastLoginTime" />
       </h6>
     </div>
      
     <ul class="flr right-nav clearfix" style="margin-bottom: 0px; padding: 0px;">
       <li style="border:0px;height:15px"  ><a href="javascript:clickLink('http://www.airtel.in');">Airtel Home</a></li>
       <li class="last"  ><a href="./Logout.do"><img src="common/images/exit.png" alt="Log Out" title="Close" height="25" width="25" border="0"  /></a></li>
     </ul>      
  </div>

<div class="navigation">
	<div class="navigation-left"></div>
	<div class="navigation-middle">
	<ul>
		<li><a href="./home.do">Home</a></li>
		<li><a href="./help.do">Help</a></li>
		
		<%  if(session.getAttribute("chpassword")==null){%>
		 <li><a href="./kmChangePassword.do?methodName=init">Change Password</a></li>
		<% } %>  
		<!--<li>
		<div id="noti_Container">
		    <img src="http://l-stat.livejournal.com/img/facebook-profile.gif" alt="facebook-profile" />
		    <div class="noti_bubble"><%=request.getAttribute("total")%></div>
		</div>
		</li>
		
		
	--></ul>
	</div>
<div class="navigation-right"></div>
</div>



