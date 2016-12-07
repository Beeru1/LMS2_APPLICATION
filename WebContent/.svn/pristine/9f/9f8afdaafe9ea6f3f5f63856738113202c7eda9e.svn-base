<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<script type="text/javascript">

function formSubmit()
	{
		if(document.forms[0].flag.value == 0)
		{
			alert("Please Select Inactive Type");
			document.forms[0].flag.focus();
			return false;
		}
		else
		{
			document.forms[0].methodName.value = "getExpiredLocked";
			document.forms[0].submit();
			return true;
		}
	}
	function activateUsers()
	{   
	  var counter = 0;
	  for (var i=0;i < document.activateUserAccountBean.elements.length;i++)
	   {
		var e = document.activateUserAccountBean.elements[i];
		if (e.type == "checkbox" && e.checked)
		{
			counter++;
		}
	   }
	if(counter < 1) {
	  alert("Please select atleast one element.");
	  return false;
	}
	 else {
		document.forms[0].methodName.value = "updateExpired";
		document.forms[0].submit();
		}
	}	
	function checkSelected()
	{
	var counter = 0;
	for (var i=0;i < document.activateUserAccountBean.elements.length;i++)
	{
		var e = document.activateUserAccountBean.elements[i];
		if (e.type == "checkbox" && e.checked)
		{
			counter++;
		}
	}
	if(counter < 1) {
	  alert("Please select atleast one element.");
	  return false;
	}
	}
	</script>
	
	<style>
	.scroll{
	display:block;
	border: 1px  ;
	padding:5px;
	margin-top:5px;
	width:750px;
	height:300px;
	overflow:scroll;
	}
	</style>

<html:form action="/activateUserAccount">
  <html:hidden property="methodName"/>
    <div class="box2">
     <div class="content-upload" style="height: 200px;">
		<H1>Unlock User</H1>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		    <tr>
			  <td>&nbsp;</td>
		      <td ><FONT color="red"><STRONG><html:errors/></STRONG></FONT></td>
		 	</tr>	
		</table>	

		<ul class="list2 form1">
			<li class="clearfix" id="element" style="margin-bottom: 40px;margin-top: 10px;">
			<span class="text2 fll width160" ><strong>Inactive Type<font color="red" size="2">* </font></strong></span>
                  <html:select property="flag" styleClass="box" onchange="javascript:return formSubmit();" styleClass="select1">
                  	<html:option value="0">Select</html:option>
                  	<html:option value="Locked">Locked</html:option>
                  	<html:option value="Expired">Expired</html:option>                     
                  </html:select>
			</li> 
		</ul>
	</div>
	</div>
		<logic:notEmpty name="activateUserAccountBean" property="expiredUserList">
		 <div class="scroll">
		  <div class="box2">
     		<div class="content-upload">
		     <center><h1>User List</h1></center>
		     	
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <th width="20%" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span class="mLeft5 mTop5">&nbsp;User Id</span></th>
					<th width="40%" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span class=" mTop5">User Name</span></th>
					<th width="30%" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span class=" mTop5">User Login Id</span></th>
					<th width="10%" align="left" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span class=" mTop5">Activate</span></th>
                 </tr>
				 
				 
				
				<logic:iterate id="expiredUserList" indexId="ii" name="activateUserAccountBean" property="expiredUserList">
				 <%	String cssName = ""; if( (ii.intValue()%2)==1){cssName = "alt";}%>
				 <tr class="<%=cssName%>">
                    <td  height="25">&nbsp;<bean:write name="expiredUserList" property="USER_ID" /></td>
                    <td> <bean:write name="expiredUserList" property="USER_FNAME" /></td>
                    <td><bean:write name="expiredUserList" property="USER_LOGIN_ID" /></td>
                    <td align="center"><INPUT type="checkbox" value='<bean:write name="expiredUserList" property="USER_ID" />'	name="submittedIds"></td>                    	
				</tr>
				</logic:iterate>
			
			</table>
			</div>
		  </div>
        </div>	
        

		<div class="button-area" >
       	 <div class="button"><input class="submit-btn1 red-btn" name="" value="" onclick="javascript:return activateUsers();" />       </div>
	    </div>
	</logic:notEmpty>
	</div>
 
 </div>
</html:form>