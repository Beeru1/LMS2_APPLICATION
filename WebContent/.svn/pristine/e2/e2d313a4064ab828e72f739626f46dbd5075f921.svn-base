<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<link href="common/css/style.css" type="text/css" rel="stylesheet" />
<script src="jScripts/reportConfiguration.js" type="text/javascript" language="JavaScript"> </script>
<body onload="hidecontrol(); unselectBoxes();">
	<html:form name="ReportConfigurationBean" action="/reportConfiguration" method="post" type="com.ibm.lms.forms.ReportConfigurationBean" >
		<html:hidden property="methodName" value=""/>
			<div class="box2">
	       		<div class="content-upload" style="height:1200px ">
					<table width="100%" align="center" cellspacing="0" cellpadding="0">		
						<tr>
							<td colspan="4" class="content-upload">
								<h1><bean:message key="reportConfiguration.heading" /></h1>
							</td>
						</tr>
					</table>
					
				 <div align="center">
				 	<font color="red">
				 		<bean:write name="ReportConfigurationBean" property="message" />
				 	</font>
				 </div>
				<table>
					<%
						boolean flag = (Boolean)request.getAttribute("SHOW_ACTION");
						if(flag)
						{
					%>
					<tr>
						<td></td>
						<td>
							<label id="radiolbl1">	
								<html:radio property="actionType" value="create" onchange="submitFormToCreateReport()"/>&nbsp;Create	
							</label>
							&nbsp;&nbsp;
							<label id="radiolbl2">
								<html:radio property="actionType" value="modify" onchange="submitFormToModifyReport()"/>&nbsp;Modify
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<br />
						</td>
					</tr>
					<%
						}
						flag = (Boolean)request.getAttribute("SHOW_REPORT");
						if(flag)
						{
					%>
					<tr>
						<td>Select Report: </td>
						<td id="reportBox">
							<html:select property="selectedReport" name="ReportConfigurationBean"  onchange="submitForm();" styleClass="select1">
								<html:option value="-1">Select Report</html:option>
								<logic:notEmpty name="ReportConfigurationBean" property="reports" >
									<bean:define id="elements" name="ReportConfigurationBean" property="reports" /> 
										<html:options labelProperty="reportName" property="reportId"  collection="elements" />
								</logic:notEmpty>
							</html:select>
						</td>
					</tr>
					<tr>
						<td>
							<br />
						</td>
					</tr>
					<%
						}
						flag = (Boolean)request.getAttribute("EDIT_REPORT_NAME");
						if(flag)
						{
					%>
					<tr>
						<td> Report Name: </td> 
						<td id="reportTd">
							<html:text name="ReportConfigurationBean" property="reportName" onblur="verifyTextContents(this);"></html:text>
						</td>
					</tr>
					<tr>
						<td>
							<br />
						</td>
					</tr>
					<%
						}
						flag = (Boolean)request.getAttribute("SHOW_REPORT_TYPES");
						if(flag)
						{
					%>
					<tr>
						<td>Select Report Type: </td>
						<td>
							<label id="radiolbl1">
								<html:radio property="reportType" value="fidwise" onchange="submitForm();" />&nbsp;FID wise
							</label>
							&nbsp;&nbsp;
							<label id="radiolbl2">
								<html:radio property="reportType" value="productwise" onchange="submitForm();" />&nbsp;Product wise
							</label>
						</td>
					</tr>
					<%
						}
						flag = (Boolean)request.getAttribute("SHOW_FIDS");
						if(flag)
						{
					%>
					<tr>
						<td>
							Select FIDs: 
						</td>
						<td>
							<table>
								<tr>
									<td id="leftFidBox">
										<html:select property="unselectedFids" multiple="true" size="10">
											<logic:notEmpty name="ReportConfigurationBean" property="inVisibleFids" >
												<bean:define id="elements" name="ReportConfigurationBean" property="inVisibleFids" /> 
												<html:options labelProperty="fid" property="fid"  collection="elements" />
											</logic:notEmpty>
										</html:select>
									</td>
									<td>
										<input type="button" value=">>" onclick="addFidToRight();"/><br />
										<input type="button" value="&lt;&lt;" onclick="addFidToLeft();"/>
									</td>
									<td id="rightFidBox">
										<html:select property="selectedFids" multiple="true" size="10">
											<logic:notEmpty name="ReportConfigurationBean" property="visibleFids" >
												<bean:define id="elements" name="ReportConfigurationBean" property="visibleFids" /> 
												<html:options labelProperty="fid" property="fid"  collection="elements" />
											</logic:notEmpty>
										</html:select>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
					<%
						}
						flag = (Boolean)request.getAttribute("SHOW_ROLES");
						if(flag)
						{
					%>
					<tr id="roleRow">
						<td>Select Role: </td>
						<td id="actorBox">
							<html:select property="selectedActor" name="ReportConfigurationBean" onchange="submitFormBasedOnRole();" styleClass="select1">
								<html:option value="-1">Select UserRole</html:option>
								<logic:notEmpty name="ReportConfigurationBean" property="actors" >
									<bean:define id="elements" name="ReportConfigurationBean" property="actors" /> 
										<html:options labelProperty="actorName" property="actorId"  collection="elements" />
								</logic:notEmpty>
							</html:select>
						</td>
					</tr>
					
					<%
						}
						flag = (Boolean)request.getAttribute("SHOW_LOBS");
						if(flag)
						{
					%>
					<tr>
						<td>
							<br />
						</td>
					</tr>
					<tr id="lobRow">
						<td>Select LOB: </td>
						<td id="lobBox">
							<html:select property="selectedLob" name="ReportConfigurationBean" onchange="submitForm();" styleClass="select1">
								<html:option value="-1">Select LOB</html:option>
								<logic:notEmpty name="ReportConfigurationBean" property="lobs" >
									<bean:define id="elements" name="ReportConfigurationBean" property="lobs" /> 
										<html:options labelProperty="lobName" property="lobId"  collection="elements" />
								</logic:notEmpty>
							</html:select>
						</td>
					</tr>
					<%
						}
						flag = (Boolean)request.getAttribute("SHOW_PRODUCTS");
						if(flag)
						{
					%>
					<tr>
						<td>
							<br />
						</td>
					</tr>
					<tr id="productRow">
						<td>Select Product(s): </td>
						<td id="productBox">
							<html:select property="selectedProducts" name="ReportConfigurationBean" multiple="true" size="10" styleClass="select1">
								<logic:notEmpty name="ReportConfigurationBean" property="products" >
									<bean:define id="elements" name="ReportConfigurationBean" property="products" /> 
										<html:options labelProperty="productName" property="productId"  collection="elements" />
								</logic:notEmpty>
							</html:select>
						</td>
					</tr>
					<% 
						}
						flag = (Boolean)request.getAttribute("SHOW_FREQUENCY");
						if(flag)
						{
					%>
					<tr>
						<td>
							<br />
						</td>
					</tr>
					<tr>
						<td> Frequency: </td> 
						<td id="frequencyTd">
							<html:text name="ReportConfigurationBean" property="frequency" onfocus="verifyFrequency(this)" onchange="verifyFrequency(this)" onkeyup="verifyFrequency(this)"> </html:text>
						</td>
					</tr>
					<tr>
						<td>
							<br />
						</td>
					</tr>
					<% 
						}
						flag = (Boolean)request.getAttribute("SHOW_TIMINGS");
						if(flag)
						{
					%>
					<tr id="timingsRow">
						<td> Timings: </td> 
						<td id="checkboxTd">
							<label id="lbl1">
								<html:multibox name="ReportConfigurationBean"  property="timings" value="0" onclick="verifyWithFrequency(this)"/>12 AM
							</label>
							<label id="lbl2">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="1" onclick="verifyWithFrequency(this)"/>1 AM
							</label>
							<label id="lbl3">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="2" onclick="verifyWithFrequency(this)"/>2 AM
							</label>
							<label id="lbl4">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="3" onclick="verifyWithFrequency(this)"/>3 AM
							</label>
							<label id="lbl5">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="4" onclick="verifyWithFrequency(this)"/>4 AM
							</label>
							<label id="lbl6">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="5" onclick="verifyWithFrequency(this)"/>5 AM
							</label>
							<br>
							<label id="lbl7">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="6" onclick="verifyWithFrequency(this)"/>6 AM
							</label>
							<label id="lbl8">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="7" onclick="verifyWithFrequency(this)"/>7 AM
							</label>
							<label id="lbl9">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="8" onclick="verifyWithFrequency(this)"/>8 AM
							</label>
							<label id="lbl10">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="9" onclick="verifyWithFrequency(this)"/>9 AM
							</label>
							<label id="lbl11">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="10" onclick="verifyWithFrequency(this)"/>10 AM
							</label>
							<label id="lbl12">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="11" onclick="verifyWithFrequency(this)"/>11 AM
							</label>
							<br>
							<label id="lbl13">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="12" onclick="verifyWithFrequency(this)"/>12 PM
							</label>
							<label id="lbl14">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="13" onclick="verifyWithFrequency(this)"/>1 PM
							</label>
							<label id="lbl15">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="14" onclick="verifyWithFrequency(this)"/>2 PM
							</label>
							<label id="lbl16">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="15" onclick="verifyWithFrequency(this)"/>3 PM
							</label>
							<label id="lbl17">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="16" onclick="verifyWithFrequency(this)"/>4 PM
							</label>
							<label id="lbl18">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="17" onclick="verifyWithFrequency(this)"/>5 PM
							</label>
							<br>
							<label id="lbl19">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="18" onclick="verifyWithFrequency(this)"/>6 PM
							</label>
							<label id="lbl20">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="19" onclick="verifyWithFrequency(this)"/>7 PM
							</label>
							<label id="lbl21">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="20" onclick="verifyWithFrequency(this)"/>8 PM
							</label>
							<label id="lbl22">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="21" onclick="verifyWithFrequency(this)"/>9 PM
							</label>
							<label id="lbl23">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="22" onclick="verifyWithFrequency(this)"/>10 PM
							</label>
							<label id="lbl24">
								<html:multibox  name="ReportConfigurationBean"  property="timings" value="23" onclick="verifyWithFrequency(this)"/>11 PM
							</label>
						</td>
					</tr>
					<%
						}
					%>
					<tr>
						<td>
							<br />
						</td>
					</tr>
					<% 
						flag = (Boolean)request.getAttribute("SHOW_EMAILS");
						if(flag)
						{
					%>
					<tr id="toRecipientsRow">
						<td>Recipients (To): </td>
						<td id="toTD">
							<html:text name="ReportConfigurationBean" property="toRecipients" > </html:text>
						</td>
					</tr>
					<tr>
						<td>
							<br />
						</td>
					</tr>
					<tr id="ccRecipientsRow">
						<td>Recipients (Cc): </td>
						<td id="ccTD">
							<html:text name="ReportConfigurationBean" property="ccRecipients" > </html:text>
						</td>
					</tr>
					<tr>
						<td>
							<br />
						</td>
					</tr>
					<tr id="subjectRow">
						<td>Subject: </td>
						<td id="subjectTD">
							<html:text name="ReportConfigurationBean" property="subject" > </html:text>
						</td>
					</tr>
					<tr>
						<td>
							<br />
						</td>
					</tr>
					<% 
						}
						flag = (Boolean)request.getAttribute("SHOW_COLUMNS");
						if(flag)
						{
					%>
					<tr>
						<td>Select Columns: </td>
						<td>
							<table>
								<tr>
									<td id="leftBox">
										<html:select property="unselectedColumns" multiple="true" size="20">
											<logic:notEmpty name="ReportConfigurationBean" property="inVisibleColumns" >
												<bean:define id="elements" name="ReportConfigurationBean" property="inVisibleColumns" /> 
												<html:options labelProperty="columnName" property="columnId"  collection="elements" />
											</logic:notEmpty>
										</html:select>
									</td>
									<td>
										<input type="button" value=">>" onclick="addToRight();"/><br />
										<input type="button" value="&lt;&lt;" onclick="addToLeft();"/>
									</td>
									<td id="rightBox">
										<html:select property="selectedColumns" multiple="true" size="20" onchange="showDisplayNameOnSelect();">
											<logic:notEmpty name="ReportConfigurationBean" property="visibleColumns" >
												<bean:define id="elements" name="ReportConfigurationBean" property="visibleColumns" /> 
												<html:options labelProperty="columnName" property="columnId"  collection="elements" />
											</logic:notEmpty>
										</html:select>
									</td>
									<td>
										configure display name <br />
										<input id="displayName" type="text" maxlength="50" onblur="verifyTextContents(this); saveDisplayNameOnBlur();">
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<br>
						</td>
					</tr>
					<tr align="center">
						<table align="center">
							<tr>
								<td align="right">
									<input type="button" value="Save & Live" onclick="fillHiddenFidSelect(); fillHiddenSelect(); saveDisplayNames(); moveDisplayNamesToMap(); submitFormToSaveData();">
								</td>
								<td align="center">
									<input type="button" value="Hold" onclick="fillHiddenFidSelect(); fillHiddenSelect(); saveDisplayNames(); moveDisplayNamesToMap(); submitFormToHoldData();">
								</td>
								<% 
									flag = (Boolean)request.getAttribute("SHOW_REPORT");
									if(flag)
									{
								%>
								<td align="left">
									<input type="button" value="Delete" onclick="deleteSelectedConfiguration();">
								</td>
								<%
									}
								 %>
							<tr>
						</table>
					</tr>
					<%
						}
					%>
				</table>
			</div>
		</div>
		<div id="hiddenDiv">
			<html:select property="updatedColumns" multiple="true" size="20">
				<logic:notEmpty name="ReportConfigurationBean" property="visibleColumns" >
					<bean:define id="elements" name="ReportConfigurationBean" property="visibleColumns" /> 
					<html:options labelProperty="columnName" property="columnId"  collection="elements" />
				</logic:notEmpty>
			</html:select>
			<html:select property="displayNames" multiple="true" size="20">
				<logic:notEmpty name="ReportConfigurationBean" property="displayNamesList" >
					<bean:define id="elements" name="ReportConfigurationBean" property="displayNamesList" /> 
					<html:options labelProperty="displayName" property="columnId"  collection="elements" />
				</logic:notEmpty>
			</html:select>
			<html:select property="displayNamesMap" multiple="true" size="20">
				<logic:notEmpty name="ReportConfigurationBean" property="displayNamesList" >
					<bean:define id="elements" name="ReportConfigurationBean" property="displayNamesList" /> 
					<html:options labelProperty="displayName" property="columnId"  collection="elements" />
				</logic:notEmpty>
			</html:select>
			<html:select property="updatedFids" multiple="true" size="20">
				<logic:notEmpty name="ReportConfigurationBean" property="selectedFids" >
					<bean:define id="elements" name="ReportConfigurationBean" property="selectedFids" /> 
					<html:options labelProperty="fid" property="fid"  collection="elements" />
				</logic:notEmpty>
			</html:select>
		</div>
	</html:form>
</body>