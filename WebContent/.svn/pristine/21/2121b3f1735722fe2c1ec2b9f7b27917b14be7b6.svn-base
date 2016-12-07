<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

	<HEAD>
		<LINK href="theme/text.css" rel="stylesheet" type="text/css">
		<TITLE>Edit Form Id</TITLE>
		<script>
		
			function isValidUrl(url) 
			{
  				var pattern = new RegExp('^(https?:\\/\\/)?'+ // protocol
  										'((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|'+ // domain name
  										'((\\d{1,3}\\.){3}\\d{1,3}))'+ // OR ip (v4) address
  										'(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*'+ // port and path
  										'(\\?[;&a-z\\d%_.~+=-]*)?'+ // query string
  										'(\\#[-a-z\\d_]*)?$','i'); // fragment locator
  				if(!pattern.test(url)) 
  				{
    				return false;
  				} 
  				else 
  				{
    				return true;
  				}
			}
		
			function submitFormToUpdate()
			{
				var url = document.forms[0].pageUrl.value;
				if(url.trim() == '')
				{
					alert("Page URL can't be empty");
					return false;
				}
				
				if(!isValidUrl(url))
				{
					alert("Please enter a valid URL.");
					return false;
				}
				
				document.forms[0].methodName.value = "updateFidData";
				document.forms[0].submit();
				return true;
			}
			
			function handleEnterKey(e)
			{
				// Enter key
				if (e.keyCode == 13)
				{
					return false;
				}
				return true;
			}
			
		</script>
	</HEAD>

	<body onkeydown="return handleEnterKey(event);">
		<html:form name="formIdBean" type="com.ibm.lms.wf.forms.FormIdBean" action="/manageFormId" method="post" >
			<html:hidden property="methodName" value=""/>
			<div class="box2">
	       		<div class="content-upload" style="height:250px ">
	       			<h1>Edit FID</h1>
	       
	       			<ul class="list2 form1 ">
						
						<li class="clearfix alt">
							<li>
								<span class="text2 fll width120">
									<strong>Form Id</strong> <font color=red>*</font>
								</span>
								<p class="clearfix fll">
									<span class="textbox6">
										<span class="textbox6-inner"> 
											<html:text property="fid" name="formIdBean" styleClass="textbox10" maxlength="100" readonly="true"
											/>
										</span>
									</span>
								</p>
							</li>
						</li>
						<li class="clearfix alt">
							<li>
								<span class="text2 fll width120">
									<strong>Page URL</strong> <font color=red>*</font>
								</span>
								<p class="clearfix fll">
									<span class="textbox6">
										<span class="textbox6-inner"> 
											
											<html:text property="pageUrl" name="formIdBean" styleClass="textbox10" maxlength="100" 
														
											/>
										</span>
									</span>
								</p>
							</li>
						</li>
						<li class="clearfix alt">
							<li>
								<span class="text2 fll width120">
									<strong>Status</strong> <font color=red>*</font>
								</span>
								<p class="clearfix fll">
									<span class="textbox6">
										<span class="textbox6-inner"> 
											<html:select name="formIdBean" property="status">
        										<html:options name="formIdBean" property="statusList"/>
    										</html:select>
										</span>
									</span>
								</p>
							</li>
						</li>
						<li class="clearfix" style="padding-left:10px;">	
							<span class="text2 fll">&nbsp;</span>
							<center>
								<a class="red-btn" style="margin-right:10px;" onclick="return submitFormToUpdate();">
									<b>Update</b>
								</a>
							</center>
		     			</li>  
					</ul>
				</div>
			</div>
		</html:form>