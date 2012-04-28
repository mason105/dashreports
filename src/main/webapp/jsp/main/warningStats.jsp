<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<html>
<head>
<sx:head />
 	<sj:head locale="en" jqueryui="true" jquerytheme="%{themeName}"/>
<link href="<s:url value='/styles/displaytag.css'/>" rel="stylesheet"
	type="text/css" media="all" />
</head>
<body>


	<div class="formGroupWide">
		<div class="formGroupWideHeader">
			<img src="<s:url value='/images/v2/nav/groupsblue.png'/>"
				align="absmiddle" />&nbsp;Audit Log
		</div>
		<s:form name="getAuditInfo" id="getAuditInfo">

			<s:select label="Module" name="module" list="modules"
				cssClass="textbox"
				onchange="dojo.event.topic.publish('refresh_info');" />
			
			<sj:datepicker name="fromDate" showAnim="slideDown" displayFormat="dd/mm/yy"  timepickerFormat="hh:mm:ss" timepicker="true" onchange="dojo.event.topic.publish('refresh_info');"></sj:datepicker>
			<sj:datepicker name="toDate" showAnim="slideDown"  displayFormat="dd/mm/yy"  timepickerFormat="hh:mm:ss" timepicker="true" onchange="dojo.event.topic.publish('refresh_info');"></sj:datepicker>

		</s:form>
	</div>



	<div class="formGroupWide">
		<div class="formGroupWideHeader">Log</div>
		<s:url id="logUrl" action="getAuditHistory" />
		<sx:div showLoadingText="true" loadingText="Loading data..."
			id="logData" href="%{logUrl}" theme="ajax"
			listenTopics="refresh_info" formId="getAuditInfo"
			executeScripts="true">
		</sx:div>
	</div>
	<div class="formBottomEmpty"></div>
</body>
</html>
