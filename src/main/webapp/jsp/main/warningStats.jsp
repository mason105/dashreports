<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<html>
<head>
<sx:head />
<sj:head locale="en" jqueryui="true" jquerytheme="smoothness" />
<link href="<s:url value='/styles/displaytag.css'/>" rel="stylesheet"
	type="text/css" media="all" />
</head>
<body>


	<div class="formGroup">
		<div class="formGroupHeader">
			<img src="<s:url value='/images/v2/nav/groupsblue.png'/>"
				align="absmiddle" />&nbsp;Audit Log
		</div>
		<s:form name="getAuditInfo" id="getAuditInfo">

			<s:select label="Module" name="module" list="modules" listKey="name"
				listValue="displayName" cssClass="textbox"
				onchange="dojo.event.topic.publish('refresh_info');" />
			<sj:spinner name="returnCount" id="returnCount" min="10" max="500"
				step="10" value="20" label="Count"
				onchange="dojo.event.topic.publish('refresh_info');" />
		</s:form>
	</div>

	<div class="formGroup">
	<div class="formGroupHeader">Chart</div>
	</div>
	
	<div class="formGroup">
	<div class="formGroupHeader">Log</div>
	<s:url id="logUrl" action="getAuditHistory" />
	<sx:div showLoadingText="true" loadingText="Loading data..."
		id="logData" href="%{logUrl}" theme="ajax" listenTopics="refresh_info"
		formId="getAuditInfo" executeScripts="true">
	</sx:div>
	</div>
	<div class="formBottomEmpty"></div>
</body>
</html>
