<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html>
<HEAD>
<%@ page language="java" import="com.ibm.lms.dto.UserMstr" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="cal/tcal.css" />
<script type="text/javascript" src="cal/tcal.js"></script> 
<script language="JavaScript" src="jScripts/LmsValidations.js" type="text/javascript"> </script>

<link href="common/css/style.css" type="text/css" rel="stylesheet" />
<LINK href="theme/text.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="common/source/ckeditor.js"></script>
<script src="common/source/sample.js" type="text/javascript"></script>
<link href="common/source/sample.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="common/js/stayConnect.js"></script>
 <script src="common/js/jquery-1.8.2.min.js" type="text/javascript"></script>
<link  href="common/css/jquery.jscrollpane.css" rel="stylesheet" type="text/css" media="all" />

<link href="common/css/tipsy.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="common/js/jquery.tools.min.js"></script>
<script type="text/javascript" src="common/js/jquery.mousewheel.js"></script> 
<script type="text/javascript" src="common/js/jquery.jscrollpane.min.js"></script> 
<TITLE><tiles:getAsString name="title" /></TITLE>

<%
response.setHeader("expires","0");
response.setHeader("expires","now");
response.setHeader("Pragma","no-cache");
response.setHeader("Cache-Control","no-cache");
%>

</HEAD>
<body>

<div class="wrapper">
  		<tiles:insert attribute="header" />
  
	  
	<div class="content clearfix">
	   	<div class="left-side clearfix" style="border-top:1px solid #cccccc; margin-left: 5px">
	   	
			<tiles:insert attribute="menu" />
		</div>
	   	<div class="right-side clearfix" >
			
			<tiles:insert attribute="body" />
		</div>
	</div>
		
		
	<div class="footer">
		<tiles:insert attribute="footer" />
	</div>
</div>
	
</body>
</html:html>