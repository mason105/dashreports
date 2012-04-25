<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<link href="<s:url value='/styles/displaytag.css'/>" rel="stylesheet"
	type="text/css" media="all" />
<sj:head jquerytheme="smoothness" />
</head>
<body>
<div class="formGroupWide">
<div class="formGroupWideHeader">
		<img src="<s:url value='/images/v2/nav/groupsblue.png'/>"
			align="absmiddle" />&nbsp;
		<s:a href="showGroup.action?groupName=%{groupName}">
			<s:property value="groupName" />
		</s:a>
		>
		<s:a
			href="setupViewJob.action?groupName=%{groupName}&jobName=%{jobName}">
			<s:property value="jobName" />
		</s:a>
	</div>
		<div class="formGroupWideHeader">Results (click to download)</div>
		<s:iterator value="downloadResults" status="rowstatus">
			<div class="fileDownloadRow">
			<img src="<s:url value="/images/icons/attach.png"/>"
				align="absmiddle" />
			<s:a
				href="downloadReport.action?id=%{value.outputId}&jobName=%{jobName}&groupName=%{groupName}"
				target="_blank">
				<s:property value="%{key}" />
			</s:a>
	</div>
		</s:iterator>
	
	<div class="formBottomEmpty"></div>
</div>

</body>
</html>
