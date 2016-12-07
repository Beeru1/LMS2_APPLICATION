<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

	<HEAD>
		<LINK href="theme/text.css" rel="stylesheet" type="text/css">
		<TITLE>Form Id Creation</TITLE>

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
		
			function submitForm()
			{
				var url = document.forms[0].pageUrl.value;
				if(url.trim() == '')
				{
					alert("Please enter valid URL.");
					return false;
				}
				
				if(isNaN(url))
				{	
					if(isValidUrl(url))
					{	
						document.forms[0].methodName.value = "createFormId";
						document.forms[0].submit();
						return true;
					}
					else
					{
						alert("'" + url  + "' is not a valid URL.");
						return false;
					}
				}
				else
				{
					alert("'" + url  + "' is not a valid URL.");
					return false;
				}	
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
					<table width="100%" align="center" cellspacing="0" cellpadding="0">		
						<tr>
							<td colspan="4" class="content-upload">
								<h1><bean:message key="fidCreation.heading" /></h1>
							</td>
						</tr>
					</table> 
								
	       			<ul class="list2 form1 ">
						
						<li class="clearfix">
							<li>
								<span class="text2 fll width100">
									<strong>Page URL</strong> <font color=red>*</font>
								</span>
								
								<p class="clearfix fll">
									<span class="textbox6">
										<span class="textbox6-inner"> 
											<html:text property="pageUrl" styleClass="textbox10" maxlength="100"  />
										</span>
										<span>
										&nbsp;
										</span>
										<span>
											<div class="button">
												<a class="red-btn" onclick="return submitForm();"><b>Create</b>
												</a>
											</div>
										</span>
										
										<br><br><br>
										Fid no. = <font color="green"><bean:write name="formIdBean" property="fid" /></font>
									</span>
								</p>
								<br><br><br><br><br>
								<p class="fll">
									<font size="2" color="grey">
									Page URL: means the URL of the page on which the form is live. E.g www.airtel.in/myplan
									</font>
								</p>
							</li>
						</li>
		     	</ul></div></div>
		</html:form>
	</body>