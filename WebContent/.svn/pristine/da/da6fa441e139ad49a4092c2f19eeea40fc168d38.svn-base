<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html>

	<HEAD>
		<LINK href="theme/text.css" rel="stylesheet" type="text/css">
		<TITLE>Edit/Download FID</TITLE>
		<script>
			function validate()
			{
				var fid = document.forms[0].fid.value;
				if(fid.trim() == '')
				{
					alert("Please enter valid Form Id.");
					return false;
				}
				if(isNaN(fid))
				{
					alert('Please enter numeric value only.')
					return false;
				}
				return true;		
			}
		
			function submitFormToEditValues()
			{
				if(validate())
				{
					document.forms[0].methodName.value = "editFidData";
					document.forms[0].submit();
					return true;
				}
				return false;
			}
			
			function submitFormToDownloadExcel()
			{
				document.forms[0].methodName.value = "downloadExcel";
				document.forms[0].submit();
				return true;
			}
			
			function submitFormToViewValues()
			{
				if(validate())
				{
					document.forms[0].methodName.value = "getFidData";
					document.forms[0].submit();
					return true;				
				}
				return false;	
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
			<html:hidden property="methodName" value="" />
			<div class="box2">
	       		<div class="content-upload" style="height:250px ">
	       			
	       			<h1>Edit/Download FID</h1>
	       
	       			<ul class="list2 form1 ">
						<li class="clearfix ">
							<li>
								<span class="text2 fll width100">
									<strong>Form Id</strong> <font color=red>*</font>
								</span>
								<p class="clearfix fll">
									<span class="textbox6">
										<span class="textbox6-inner"> 
											<html:text name="formIdBean" property="fid" styleClass="textbox10" maxlength="100" />
										</span>
										<span>
											&nbsp;
										</span>
											<div class="button">
												<a class="red-btn" onclick="submitFormToViewValues()"><b>View</b>
												</a>
											</div>
											<div class="button">
												<a class="red-btn" onclick="submitFormToEditValues()"><b>Edit</b>
												</a>
											</div>
											<div class="button">
												<a class="red-btn" onclick="return submitFormToDownloadExcel();"><b>Download</b>
												</a>
											</div>
										</span>
									</span>
								</p>
								<%
									Object o = request.getAttribute("HIDE_FIELDS");
									if(o == null)
									{								
								%>		
								<br><br><br><br><br>
								<p class="fll">
									<font size="3" color="red">
										<bean:write name="formIdBean" property="message" />
									</font>
								</p>
				 					
				 				<%
				 					}
				 					else if(o.toString().equals("false"))
				 					{
				 				 %>
										<li  class="clearfix ">
										<li id="pageurllabel">
											<span  class="text2 fll width100">
												<strong>Page URL</strong>
											</span>									
											<p class="clearfix fll">
												<span class="textbox6">
													<span  id="pageurltext" class="textbox6-inner">
														<html:text name="formIdBean" property="pageUrl" styleClass="textbox10" readonly="true"></html:text>
																								
													</span>
												</span>
											</p>
										</li>
									</li>
									<li class="clearfix">
										<li>
											<span id="statuslabel" class="text2 fll width100">
												<strong>Status</strong>
											</span>
											<p  id="statustext" class="clearfix fll">
												<bean:write name="formIdBean" property="status" />
											</p>
										</li>
								
								<br><br><br><br>
								<p class="fll">
									<font size="2" color="grey">
									&nbsp;&nbsp;Page URL: means the URL of the page on which the form is live. E.g www.airtel.in/myplan
									</font>
								</p>
								
								<% 
								  	}
								%>
							
						</li>
					</ul>
				</div>
			</div>
		</html:form>
	</body>
</html:html>