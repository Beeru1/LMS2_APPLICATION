<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@page import="com.ibm.lms.dto.UserMstr" %>
<script language="javascript"  src="<%=request.getContextPath()+"/jScripts/ajaxCall.js"%>"></script>
<script language="javascript">
	var currentTime= (new Date()).getTime();  
function selectAlertType()
{
		var alertId=document.forms[0].alertId.value;
		var source=document.forms[0].source.value;
		//alert("source is.........."+ source);
	 
		var data = "methodName=selectDetailsAlertType&alertId="+alertId+"&source="+source+"&currentTime="+currentTime;
		doSyncAjax("alertsAction.do", "GET", selectDetailsAlertType, data);
}

var selectDetailsAlertType = function()
{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
	  		var xmlDoc=xmlHttpRequest.responseText.toLowerCase().trim();
	  		if((xmlDoc=="BOTH".toLowerCase().trim()))
		   	{
		   	document.forms[0].alert_type.value='both';
		 	}
		 	 if((xmlDoc=="SMS".toLowerCase().trim()))
		 	{
		  	document.forms[0].alert_type.value='sms';
		  	}
		 	if((xmlDoc=="email".toLowerCase().trim()))
		  	{
		 	document.forms[0].alert_type.value='email';
			}
			getDataFields();
		}
}
	 
//For SMS and Email 
function getDataFields()
{
		
			var alertType=document.forms[0].alert_type.value;
			var alertId=document.forms[0].alertId.value;
			//alert("getDataFields"+alertType);
			if(alertType == 'sms' && alertId!=7 && alertId!=9) // 7 is channel partner login alert no email id and sms numbers to be shown
         	{
         	//alert("getDataFields"+alertType);
			document.getElementById("toRecipientsRow1").style.display= "block"; //display email id's
			document.getElementById("message1").style.display= "block"; //display message template
			document.getElementById("toRecipientsRow").style.display= "none"; //not display sms numbers
			document.getElementById("message").style.display= "none";//not display msg template
			document.getElementById("emailSubject").style.display="none";
			document.forms[0].message.value="";
			document.forms[0].email.value="";
			document.forms[0].subjectEmail.value="";
			selectMessageSMS();
			
			selectSms();
			}
			else if(alertType=='email' && alertId!=7 && alertId!=9)
			{
			//alert("getDataFields"+alertType);
			document.getElementById("toRecipientsRow").style.display= "block"; //display sms numbers
			document.getElementById("message").style.display= "block";//display msg template
			document.getElementById("emailSubject").style.display="block";
			document.getElementById("toRecipientsRow1").style.display= "none"; //not display email id's
			document.getElementById("message1").style.display= "none"; //not display message template
			document.forms[0].sms.value="";
			document.forms[0].message1.value="";
			selectMessage();
			selectEmail();
			selectSubject();
			}
			
			else if(alertType =='both' && alertId!=7 && alertId!=9)
		    {
		    //alert("getDataFields"+alertType);
		    document.getElementById("toRecipientsRow1").style.display= "block"; //display email id's
			document.getElementById("message1").style.display= "block"; //display message template
		    document.getElementById("toRecipientsRow").style.display= "block"; //display sms numbers
			document.getElementById("message").style.display= "block";//display msg template
			document.getElementById("emailSubject").style.display="block";
			selectMessage();
			selectEmail();
			selectSubject();
			selectMessageSMS();
			
			selectSms();    
		    }
		    else if(alertType =='')
		    {
		  // alert("getDataFields"+alertType);
		    document.getElementById("message").style.display= "none"; //msg
			document.forms[0].message.value="";
			document.getElementById("toRecipientsRow1").style.display= "none"; //email
			document.forms[0].email.value="";
			document.getElementById("emailSubject").style.display= "none"; //subject
			document.forms[0].subjectEmail.value="";
			document.getElementById("message1").style.display= "none"; //sms
			document.forms[0].message1.value="";
			document.getElementById("toRecipientsRow").style.display= "none"; //sms rec
			document.forms[0].sms.value="";
		    }
		    else if(alertType =='both' && (alertId==7 || alertId==9 ))
		    {
		    document.getElementById("message").style.display= "block"; //msg
			//document.forms[0].message.value="";
			document.getElementById("toRecipientsRow1").style.display= "none"; //email
			document.forms[0].email.value="";
			document.getElementById("emailSubject").style.display= "block"; //subject
			//document.forms[0].subjectEmail.value="";
			document.getElementById("message1").style.display= "block"; //sms
			//document.forms[0].message1.value="";
			document.getElementById("toRecipientsRow").style.display= "none"; //sms rec
			document.forms[0].sms.value="";
		    selectMessage();
			selectSubject();
			selectMessageSMS();
			
		    }
		      else if(alertType =='sms' && (alertId==7 || alertId==9 ))
		    {
		    document.getElementById("message").style.display= "none"; //msg
			document.forms[0].message.value="";
			document.getElementById("toRecipientsRow1").style.display= "none"; //email
			document.forms[0].email.value="";
			document.getElementById("emailSubject").style.display= "none"; //subject
			document.forms[0].subjectEmail.value="";
			document.getElementById("message1").style.display= "block"; //sms
			//document.forms[0].message1.value="";
			document.getElementById("toRecipientsRow").style.display= "none"; //sms rec
			document.forms[0].sms.value="";
		    
			selectMessageSMS();
			
		    }
		      else if(alertType =='email' && (alertId==7 || alertId==9 ))
		    {
		    document.getElementById("message").style.display= "block"; //msg
			//document.forms[0].message.value="";
			document.getElementById("toRecipientsRow1").style.display= "none"; //email
			document.forms[0].email.value="";
			document.getElementById("emailSubject").style.display= "block"; //subject
			//document.forms[0].subjectEmail.value="";
			document.getElementById("message1").style.display= "none"; //sms
			document.forms[0].message1.value="";
			document.getElementById("toRecipientsRow").style.display= "none"; //sms rec
			document.forms[0].sms.value="";
		    selectMessage();
			selectSubject();
			
			
		    }
		    
 }
function selectMessage()
{
		var alertId=document.forms[0].alertId.value;
		var source=document.forms[0].source.value;
        var data = "methodName=selectDetails&alertId="+alertId+ "&source="+source+"&type=email"+"&currentTime="+currentTime;
		doSyncAjax("alertsAction.do", "GET", selectDetails, data);
}
var selectDetails = function()
{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
	  	var xmlDoc=xmlHttpRequest.responseText;
	
		document.forms[0].message.value=xmlDoc; //email message
		}
}
function selectMessageSMS()
{
		var alertId=document.forms[0].alertId.value;
		var source=document.forms[0].source.value;
        var data = "methodName=selectDetails&alertId="+alertId+ "&source="+source+"&type=sms"+"&currentTime="+currentTime;
		doSyncAjax("alertsAction.do", "GET", selectDetailssms, data);
}
var selectDetailssms = function()
{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
	  	var xmlDoc=xmlHttpRequest.responseText;
	
		document.forms[0].message1.value=xmlDoc; //sms message
		}
}

function selectEmail()
{
		var alertId=document.forms[0].alertId.value;
		var source=document.forms[0].source.value;
		var data = "methodName=selectDetailsEmail&alertId="+alertId+  "&source="+source+"&currentTime="+currentTime;
		doSyncAjax("alertsAction.do", "GET",  selectDetailsEmail, data);
}
var selectDetailsEmail = function()
{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
	  	var xmlDoc=xmlHttpRequest.responseText;
		document.forms[0].email.value=xmlDoc;
		}
		
}

function selectSubject()
{
		var alertId=document.forms[0].alertId.value;
		var source=document.forms[0].source.value;
		var data = "methodName=selectDetailsSubject&alertId="+alertId+ "&source="+source+"&currentTime="+currentTime;
		doSyncAjax("alertsAction.do", "GET",  selectDetailsSubject, data);
}

var selectDetailsSubject = function()
{
	//alert("selectDetailsSubject");
	if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
	  	var xmlDoc=xmlHttpRequest.responseText;
	 // 	alert("subject .."+xmlDoc);
		document.forms[0].subjectEmail.value=xmlDoc;
			
		}
}	
function selectSms()
{		
		var alertId=document.forms[0].alertId.value;
		var source=document.forms[0].source.value;
		var data = "methodName=selectDetailsSms&alertId="+alertId+ "&source="+source+"&currentTime="+currentTime;
		doSyncAjax("alertsAction.do", "GET",  selectDetailsSms, data);
}  
var selectDetailsSms = function()
{
      	if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
	  	var xmlDoc=xmlHttpRequest.responseText;
		document.forms[0].sms.value=xmlDoc;
		}
}

function selectThresholdCount()
{
		var alertId=document.forms[0].alertId.value;
		var source=document.forms[0].source.value;
		//alert("selectThresholdCount"+source);
		var data = "methodName=selectDetailsThresholdCount&alertId="+alertId+ "&source="+source+"&currentTime="+currentTime;
		doSyncAjax("alertsAction.do", "GET",  selectDetailsThresholdCount, data);
}
var selectDetailsThresholdCount = function()
{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
	  	var xmlDoc=xmlHttpRequest.responseText;
		document.forms[0].threshold_count.value=xmlDoc;
			
		}
}
		
function selectThresholdPeriod()
{
		var alertId=document.forms[0].alertId.value;
		var source=document.forms[0].source.value;
		var data = "methodName=selectDetailsThresholdPeriod&alertId="+alertId +"&source="+source+"&currentTime="+currentTime;
		doSyncAjax("alertsAction.do", "GET",  selectDetailsThresholdPeriod, data);
}
var selectDetailsThresholdPeriod = function()
{
	if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
	  	var xmlDoc=xmlHttpRequest.responseText;
	  	var alertId=document.forms[0].alertId.value;
	  	if(alertId==7)
		document.forms[0].threshold_period1.value=xmlDoc;
		else if(alertId==8)
		document.forms[0].threshold_period2.value=xmlDoc;
		else
		document.forms[0].threshold_period.value=xmlDoc;
			
		}
}
function selectStatus()
{
		var source=document.forms[0].source.value;
		var alertId=document.forms[0].alertId.value;
	//		alert(source);
		var data = "methodName=selectDetailsStatus&alertId="+alertId+ "&source="+source+"&currentTime="+currentTime;
		doSyncAjax("alertsAction.do", "GET", selectDetailsStatus, data);
}
		
			
var selectDetailsStatus = function()
{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
	  	var xmlDoc=xmlHttpRequest.responseText.toUpperCase().trim();
	  	if(xmlDoc=="A".toUpperCase().trim())
	  	{
	  	document.forms[0].status.value='A';
		}
		if(xmlDoc=="I".toUpperCase().trim())
		{
		document.forms[0].status.value='I';
		}
		}
}
	
		 
function hideOrShowField()
{
		
		var alertId = document.forms[0].alertId;
		var alert_Id =  alertId.options[alertId.options.selectedIndex].value;
		if(alert_Id!=3) // no need of source
		{
			document.getElementById("sourceLabel").style.display= "none"; //let the user select source
			document.forms[0].source.value="";
			document.getElementById("statusLabel").style.display="block"; // status to be shown for all
			document.forms[0].status.value="";
			document.getElementById("alertTypeLabel").style.display="block"; // alert type to be shown for all
			document.forms[0].alert_type.value="";
			document.forms[0].message.value="";
			document.forms[0].email.value="";
			document.forms[0].subjectEmail.value="";
			document.forms[0].message1.value="";
			document.forms[0].sms.value="";
			document.forms[0].threshold_period1.value="";
			document.forms[0].threshold_period.value="";
			document.forms[0].threshold_period2.value="";
			document.forms[0].threshold_count.value="";
			selectStatus();
			selectAlertType();
			//selectMessage();
			//selectMessageSMS();
			//selectEmail();
			//selectSubject();
			//selectSms();
			selectThresholdCount();
			selectThresholdPeriod();
			
			
		}
		if(alert_Id == 3) // show source
		{
			document.getElementById("sourceLabel").style.display= "block"; //let the user select source
			document.forms[0].source.value="";
			document.getElementById("statusLabel").style.display= "none"; //status
			document.forms[0].status.value="";
			document.getElementById("alertTypeLabel").style.display= "none"; //alert type
			document.forms[0].alert_type.value="";
			
			document.getElementById("message").style.display= "none"; //msg
			document.forms[0].message.value="";
			document.getElementById("toRecipientsRow1").style.display= "none"; //email
			document.forms[0].email.value="";
			document.getElementById("emailSubject").style.display= "none"; //subject
			document.forms[0].subjectEmail.value="";
			document.getElementById("message1").style.display= "none"; //sms
			document.forms[0].message1.value="";
			document.getElementById("toRecipientsRow").style.display= "none"; //sms rec
			document.forms[0].sms.value="";
			
			document.getElementById("threshold_period1").style.display= "none";
			document.forms[0].threshold_period1.value="";
			document.getElementById("threshold_period").style.display= "none";
			document.forms[0].threshold_period.value="";
			document.getElementById("threshold_period2").style.display= "none";
			document.forms[0].threshold_period2.value="";
			document.getElementById("threshold_count").style.display= "none";
			document.forms[0].threshold_count.value="";
		}
		
		if(alert_Id == 7) // channel partner login days no need of count
		{
		document.getElementById("threshold_period1").style.display= "block";
		document.getElementById("threshold_period2").style.display= "none";
		document.getElementById("threshold_period").style.display= "none";
		document.getElementById("threshold_count").style.display= "none";
		}
		else if(alert_Id == 8) // application slow seconds no need of count
		{
		document.getElementById("threshold_period2").style.display= "block";
		document.getElementById("threshold_period1").style.display= "none";
		document.getElementById("threshold_count").style.display= "none";
		document.getElementById("threshold_period").style.display= "none";
		
		}
		else if(alert_Id == 9)
		{
			document.getElementById("threshold_period1").style.display= "none";
			document.forms[0].threshold_period1.value="";
			document.getElementById("threshold_period").style.display= "none";
			document.forms[0].threshold_period.value="";
			document.getElementById("threshold_period2").style.display= "none";
			document.forms[0].threshold_period2.value="";
			document.getElementById("threshold_count").style.display= "none";
			document.forms[0].threshold_count.value="";
		}
		else if(alert_Id != 3)// other hours
		{
		document.getElementById("threshold_count").style.display= "block";
	    document.getElementById("threshold_period").style.display= "block";
	    document.getElementById("threshold_period1").style.display= "none";
		document.getElementById("threshold_period2").style.display= "none";
		}
		
		
			
}

function selectSource()
{
			
			document.forms[0].alert_type.value="";
			document.forms[0].status.value="";
			document.forms[0].message.value="";
			document.forms[0].email.value="";
			document.forms[0].subjectEmail.value="";
			document.forms[0].message1.value="";
			document.forms[0].sms.value="";
			document.forms[0].threshold_period1.value="";
			
			document.forms[0].threshold_period.value="";
			document.forms[0].threshold_period2.value="";
			document.forms[0].threshold_count.value="";
			selectStatus();
			selectAlertType();
			//selectMessage();
			//selectMessageSMS();
			//selectEmail();
			//selectSubject();
			//selectSms();
			selectThresholdCount();
			selectThresholdPeriod();
			document.getElementById("statusLabel").style.display="block"; // status to be shown for all
			document.getElementById("alertTypeLabel").style.display="block"; // alert type to be shown for all
			document.getElementById("threshold_count").style.display= "block";
	    	document.getElementById("threshold_period").style.display= "block";
	
}

function verifyEmail(val)
	{
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

		return reg.test(val);
    }	

    
function verifyContentsEmail(textBox)
	  {
  			 if(textBox.value == '')
			return false;
			var array = textBox.value.split(',');
			for(var indx=0;indx<array.length;indx++)
			{
			if(!verifyEmail(array[indx]))
			{
				return false;		
			}
			}
		return true;
	}
 

function verifyEmailTextbox()
  {
    var td = document.forms[0].email;
    var email =  td.value;
    var alertid=document.forms[0].alertId.value;
    if(email.length>1000)
    {
    alert("Please enter lesser number of email id's");
    return false;
    }
	if(email.trim() == "" && alertid!=7 && alertid!=9)
		{
			alert('To recipients can not be empty');
			return false;
		}
		
		if(!verifyContentsEmail(td) && alertid!=7 && alertid!=9)
		{
			alert("Incorrect emails are provided in TO Recipients");
			return false;
         }
         return true;
  }
  
  //sms validation:
  
function verifySms(val)
{
		var reg =  /^([0-9]{2})\)?[-. ]?([0-9]{4})[-. ]?([0-9]{4})$/; 	
		return reg.test(val);
}
  
function verifyContentsSms(textBox)
{
		if(textBox.value == '')
		return false;
		var array = textBox.value.split(',');
		for(var indx=0;indx<array.length;indx++)
		{
		if(!verifySms(array[indx]))
			{
			return false;		
			}
		}
		return true;
}
	
function verifySmsTextbox()
{
   
    var td1 = document.forms[0].sms;
    var alertid=document.forms[0].alertId.value;
	var sms =  td1.value;
	if(sms.length>500)
	{
	alert("Please enter less numbers");
	return false;
	}
    if(sms.trim() == "" && alertid!=7 && alertid!=9)
		{
			alert("sms recipients can not be empty");
			return false;
		}
		
		if(!verifyContentsSms(td1) && alertid!=7  && alertid!=9)
		{
			alert("Incorrect numbers are provided in TO Recipients");
			return false;
        }
      return true;
}

function isInteger(x) 
{
		//alert(x);
        return x % 1 === 0;
}
function clearForm()
{
   		alertsFormBean.reset();
    	return true;
}      
function validate()
{ 
  var alertType=document.forms[0].alert_type.value; 
  var status=document.forms[0].status.value;
  var alertid=document.forms[0].alertId.value;
  var msg_temp=document.forms[0].message.value;
  var subjectEmail=document.forms[0].subjectEmail.value;
  var sms_temp=document.forms[0].message1.value;
	//alert(alertid);
	if(document.forms[0].alertId.value == 0)
	{
	alert("Please Select Alert."+document.forms[0].alert_type.value);
	return false;
	}
	
	if(alertType == "")
	{
	alert("Please Select Alert Type.");
	return false;
	}
	if(status == "")
	{
	alert("Please Select Status Field Value.");
	return false;
	}
	if(document.forms[0].alertId.value == '3' && document.forms[0].source.value=="")
	{
	alert("Please select the source");
	return false;
	}
	if(alertType=='both')
	{
	if(!verifyEmailTextbox() || !verifySmsTextbox() || !verifyEmailTextbox()&& !verifySmsTextbox())
	{
	return false;
	}
	}
	if(alertType=='email' && !verifyEmailTextbox())
		{
		  return false;
		}
	if(alertType=='sms'&& !verifySmsTextbox())
		{
		  return false;
		}
	//alert(alertid+"nan "+isInteger(document.forms[0].threshold_count.value));
	
	if(alertid!='7' && alertid!='8' && alertid!='9')
	{
	if(document.forms[0].threshold_count.value==0 || !isInteger(document.forms[0].threshold_count.value)||document.forms[0].threshold_count.value.length>6)
	{
	alert("Please enter valid value in threshold count");
	return false;
	}
	}
	//alert("nan "+isInteger(document.forms[0].threshold_period.value));
	if(alertid == '7')
	{
	if(document.forms[0].threshold_period1.value==0 || !isInteger(document.forms[0].threshold_period1.value)||document.forms[0].threshold_period1.value.length>6)
	{
	alert("Please enter valid value in threshold period");
	return false;
	}
	}
	else if(alertid == '8')
	{
	if(document.forms[0].threshold_period2.value==0 || !isInteger(document.forms[0].threshold_period2.value))
	{
	alert("Please enter valid value in threshold period");
	return false;
	}
	}
	else if(alertid != '9')
	{
	if(document.forms[0].threshold_period.value==0 || !isInteger(document.forms[0].threshold_period.value))
	{
	alert("Please enter valid value in threshold period");
	return false;
	}
	}
	if(alertid=='8')
	{
	if(document.forms[0].threshold_period2.value<3)
	{
	alert("Please enter value greater than 3 seconds in threshold period");
	return false;
	}
	}
	
	if(msg_temp.length>1000)
	{
	alert("message template too long");return false;
	}
	
	if((alertType=='email' && subjectEmail=="") || (alertType=='both' && subjectEmail=="" ))
	{
	alert("Please enter email subject");
	return false;
	}
	
	if(alertType=='email' || alertType=='both')
	{
	if(msg_temp.trim().length=="")
	{
	alert("Please enter email message");
	return false;
	}
	}
	
	if(alertType=='sms' || alertType=='both')
	{
	if(sms_temp.trim().length=="")
	{
	alert("Please enter SMS message");
	return false;
	}
	}
	
	if(sms_temp.length>500)
	{
	alert("Message template too long!");
	return false;
	}
	
	document.forms[0].methodName.value ="insertRecord";
   	document.forms[0].submit();
   	return true;
   	}


</script>
	
	<html:form action="/alertsAction">
	<html:hidden property="methodName" value=""/>
	
      <div class="box2">
        <div class="content-upload" style="height:650px"> 
        <h1><bean:message key="submitReport.heading" /></h1>
        <center><font color="#FF0000"><strong>
			<html:messages id="msg" message="true">
					<bean:write name="msg"/>  
			</html:messages></strong></font></center>

	
   <ul class="list2 form1"> 
		    <li class="clearfix alt">
          <span class="text2 fll width160">SELECT ALERT<font color=red>*</font>  </span>
			
			 	<html:select property="alertId" name="alertsFormBean" styleClass="select1" onchange="Javascript: hideOrShowField();">
					<html:option value="0">Select Alert</html:option>
					<logic:present name="alertList" scope="request">
					<logic:notEmpty name="alertList" scope="request">
						<bean:define id="alerts" 	name="alertList" scope="request"/> 
						<html:options labelProperty="alertName" property="alertId" collection="alerts" />
					</logic:notEmpty>
					</logic:present>
				</html:select></li>
			
		<li class="clearfix " style="display:none" id="sourceLabel"> <span class="text2 fll width160">SELECT SOURCE<font color=red>*</font> </span>
          		 
			 	<html:select property="source" name="alertsFormBean" styleId="rsuStyleId" styleClass="select1" onchange="javascript: selectSource();">
					<html:option value="">Select Source </html:option>
					<logic:present name="sourceList" scope="request">
					<logic:notEmpty  name="sourceList" scope="request">
						<bean:define id="source" name="sourceList" scope="request"/> 
						<html:options labelProperty="sourceName" property="sourceName" collection="source" />
					</logic:notEmpty>
					</logic:present>
				</html:select>
			</li>
			
						<li class="clearfix alt" style="display:none" id="statusLabel"> <span class="text2 fll width160">SELECT STATUS<font color=red>*</font> </span>
				
						<html:select property="status" 	name="alertsFormBean" styleClass="select1">
							<html:option value="">Select Status</html:option>
							<html:option value="A">Active</html:option>
							<html:option value="I">Inactive</html:option>
						</html:select>
						</span>
					</li>
					
					  
		  <li class="clearfix alt" style="display:none" id="alertTypeLabel"><span class="text2 fll width160">SELECT ALERT TYPE<font color=red>*</font> </span>
				
						<html:select property="alert_type" 	name="alertsFormBean" styleClass="select1" onchange="Javascript: getDataFields();">
							<html:option value="">Select Alert Type</html:option>
							<html:option value="sms">SMS</html:option>
							<html:option value="email">E-mail</html:option>
							<html:option value="both">Both</html:option>
							
						</html:select>
						</span>
					</li>
					
					
					<li class="clearfix alt" style=display:none id=message>
          	<span class="text2 fll width160">EMAIL MESSAGE TEMPLATE<font color=red>*</font></span>
		<p class="clearfix fll margin-r20"> <html:textarea styleClass="textarea2 fll" property="message"></html:textarea></p>
			</li>
			
			
			
					<li class="clearfix" style="display:none" id="toRecipientsRow">
          	<span class="text2 fll width160">EMAIL RECIPIENTS (TO):<font color=red>*</font> </span>
          	<td id="toTD">
			<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77" property="email" maxlength="1000"></html:text>
			
		</span> </span> </p>
		</td>
			</li>
			
			<li class="clearfix" style="display:none" id="emailSubject">
          	<span class="text2 fll width160">EMAIL SUBJECT:<font color=red>*</font> </span>
          	<td id="toTD">
			<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77" property="subjectEmail" maxlength="200"></html:text>
			
		</span> </span> </p>
		</td>
			</li>
			
			<li class="clearfix alt" style="display:none" id="message1">
          	<span class="text2 fll width160">SMS TEMPLATE<font color=red>*</font> </span>
		<p class="clearfix fll margin-r20"> <html:textarea styleClass="textarea2 fll" property="message1"></html:textarea></p>
			</li>	
				
			<li class="clearfix alt" style="display:none" id="toRecipientsRow1">
          	<span class="text2 fll width160">SMS RECIPIENTS (TO):<font color=red>*</font> </span>
          	<td id="toTD1">
			<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77" property="sms" maxlength="500"></html:text>
			</span> </span> </p>
			</li>
				
					<li class="clearfix" style=display:none id=threshold_count>
          	<span class="text2 fll width160">THRESHOLD COUNT<font color=red>*</font> </span>
		<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77"
				property="threshold_count" maxlength="6" /></span> </span> </p>
			</li>
			
					
					<li class="clearfix alt" style=display:none id=threshold_period>
          	<span class="text2 fll width160">THRESHOLD PERIOD<font color=red>*</font> </span>
		<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77"
				property="threshold_period" maxlength="6" /></span> </span> </p><class="text2 fll width160"/>hours
			</li>
			
			
			<li class="clearfix alt" style=display:none id=threshold_period1>
          	<span class="text2 fll width160">THRESHOLD PERIOD<font color=red>*</font> </span>
		<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77"
				property="threshold_period1" maxlength="6" /></span> </span> </p><class="text2 fll width160"/>days
			</li>
			
			
			<li class="clearfix alt" style=display:none id=threshold_period2>
          	<span class="text2 fll width160">THRESHOLD PERIOD<font color=red>*</font> </span>
		<p class="clearfix fll margin-r20"> <span class="textbox6"> <span class="textbox6-inner"><html:text styleClass="textbox77"
				property="threshold_period2" maxlength="6" /></span> </span> </p><class="text2 fll width160"/>seconds
			</li>
			
			</br>
		 <li class="clearfix alt" style=display:block id=note>
          	<span class="text2 fll width560"><font size="2" color="red">Note: The variable names in Message Template including threshold count, source, olmids, prefix and threshold period will be replaced dynamically. Please do not change these variable names.</font> </span>
          		</li>
          	
          	<div class="button-area">
		<div class="button">
			<a class="red-btn" onclick="return validate()"><b>Submit</b>
			</a>
		</div>
		<div class="button">
			<a class="red-btn" onclick="clearForm()"><b>clear</b>
			</a>
		</div>
	</div>	
          </ul>
    
        </div>
       
      </div>
    
</html:form>
