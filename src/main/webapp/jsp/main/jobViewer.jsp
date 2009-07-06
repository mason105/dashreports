<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html>
	<head>
		<sx:head parseContent="true" />
		<link href="<s:url value='/styles/displaytag.css'/>" rel="stylesheet" type="text/css" media="all" />
	</head>
<body>
<span class="pageTitle"><img
	src="<s:url value="/images/icons/application_form_magnify.png"/>" align="absmiddle" />View Report -  <s:property
			value="%{jobName}" /> in Group <s:property
			value="%{groupName}" /></span>	

	<s:iterator value="results" status="rowstatus">
			<img src="<s:url value="/images/icons/attach.png"/>" align="absmiddle"/><a href="downloadReport.action?id=<s:property value="%{value.outputId}"/>&jobName=<s:property value="%{jobName}"/>&groupName=<s:property value="%{groupName}"/>" target="_blank"><s:property value="%{key}"/></a>	

	</s:iterator>

</body>
</html>
	