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

	<div class="smallLabel">Job Name <s:property
			value="%{jobName}" /></div>
			
	<div class="smallLabel">Group Name <s:property
			value="%{groupName}" /></div>
	
	<sx:tabbedpanel id="viewer">
	<s:iterator value="results" status="rowstatus">
		<sx:div id="reportTab_%{key}" label="%{key}">
			<a href="downloadReport.action?id=<s:property value="%{value.outputId}"/>&jobName=<s:property value="%{jobName}"/>&groupName=<s:property value="%{groupName}"/>" target="_blank">Download</a>	
			<display:table name="value.rowSet.rows"  pagesize="25" requestURI="viewJobOutput.action" export="false">	
			</display:table>
		</sx:div>
	</s:iterator>
	</sx:tabbedpanel>
</body>
</html>
	