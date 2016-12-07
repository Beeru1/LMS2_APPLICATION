<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<script> 
function searchUser()
{ 
    var userLoginId = trimAll(document.forms[0].userLoginId.value);
    document.forms[0].userLoginId.value = userLoginId;
    var userFname = trimAll(document.forms[0].userFname.value);
    document.forms[0].userFname.value = userFname;
    
    
    if(userLoginId=="" && userFname=="")
	{	
		alert("Please enter User Id or UserFname.");
		document.forms[0].userLoginId.focus();
		document.forms[0].userFname.focus();
		return false;
	}
	else
	{
		document.forms[0].methodName.value="initSearchUser";
		document.forms[0].submit();
	}	
}

function submitSearch(event) {
    if (event.keyCode == 13)
    {
        searchUser();        
    }
     return true;
}

</SCRIPT> 
<html:form action="/kmUserMstr">
	<html:hidden property="methodName" value=""/>
      <div class="box2">
      
        <div class="content-upload">
          <h1>Search User</h1> 
               <ul class="list2 form1" >
              <li class="clearfix" style="margin-top: 18px;" >	
             	<p class="clearfix fll width235 margin-r64" style="margin-left: 50px;">
             	 <span class="text2 fll margin-r20"><strong>User Id</strong> </span>	
             	  <html:text style="width:125px;" tabindex="1" property="userLoginId" styleId="keyword" name="kmUserMstrFormBean" maxlength="40" onkeypress="return submitSearch(event);" 
      	               	 style="height:18px; width: 125px; border-color: #000000; border-width:1px; padding-left:3px; padding-top:3px; font-size:14px; font-weight:bold; color:#2f2f2f;"/>	            	 
             	 
             	 </p>  <script>document.forms[0].userLoginId.select();</SCRIPT> 
             	 <span class="text2 fll margin-r20"><strong>User Fname</strong> </span>	
             	 <p> <html:text style="width:125px;" tabindex="1" property="userFname" styleId="keyword" name="kmUserMstrFormBean" maxlength="40" onkeypress="return submitSearch(event);" 
      	               	 style="height:18px; width: 125px; border-color: #000000; border-width:1px; padding-left:3px; padding-top:3px; font-size:14px; font-weight:bold; color:#2f2f2f;"/></p>
		     </li>
   	         <li class="clearfix" style="margin-top: 18px;">	
					<center><a class="red-btn" tabindex="3" onclick="return searchUser();"><b>Search</b></a></center>
		     </li>      
            </ul>
       </div>
       <br>
     </div>
      		 <FONT color="red" style="margin-left: 10px;"><html:errors/></FONT>           
</html:form>