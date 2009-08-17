<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
	<head>
		<sx:head parseContent="true" />
	</head>
<body>
	<span class="pageTitle"><img
	src="<s:url value="/images/icons/application_form_magnify.png"/>" align="absmiddle" />View Report -  <s:property
			value="%{jobName}" /> in Group <s:property
			value="%{groupName}" /></span>	

	<s:iterator value="results" status="rowstatus">
		<div class="formGroup">
			<div class="formGroupHeader">Results (click to download)</div>
			<img src="<s:url value="/images/icons/attach.png"/>" align="absmiddle"/>
			<s:a href="downloadReport.action?id=%{value.outputId}&jobName=%{jobName}&groupName=%{groupName}" target="_blank">
			<s:property value="%{key}"/></s:a>
		</div>
	</s:iterator>

</body>
</html>
	