<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<html>
<head>
<sx:head parseContent="true" />
<link href="<s:url value='/styles/displaytag.css'/>" rel="stylesheet"
	type="text/css" media="all" />
</head>
<body>
<span class="pageTitle"><img
	src="<s:url value='/images/view.png'/>" align="middle" />View Job
Detail</span>
<br /><br />
[<s:a href="listJobs.action?groupName=%{job.groupName}">Back</s:a>]
<br />
<table width="100%">
	<tr>
		<td class="rowHeader"><b>Job Name</b></td>
		<td class="rowHeader"><s:property value="%{job.jobName}" /></td>
	</tr>
	<tr>
		<td class="rowHeader"><b>Group Name</b></td>
		<td class="rowHeader"><s:property value="%{job.groupName}" /></td>
	</tr>
	<tr>
		<td class="rowHeader"><b>Job Description</b></td>
		<td class="rowHeader"><s:property value="%{job.description}" /></td>
	</tr>
	<tr>
		<td class="rowHeader"><b>Previous Run Time</b></td>
		<td class="rowHeader"><s:property value="%{job.previousRunTime}" />
		</td>
	</tr>
	<tr>
		<td class="rowHeader"><b>Next Run Time</b></td>
		<td class="rowHeader"><s:property value="%{job.nextRunTime}" /></td>
	</tr>
	<tr>
		<td class="rowHeader"><b>Schedule Active</b></td>
		<td class="rowHeader"><s:property value="%{job.isScheduleActive}" />
		</td>
	</tr>
</table>
<display:table name="events"  pagesize="25" requestURI="viewJobDetail.action" export="true">
    <display:setProperty name="export.rtf.filename" value="history.rtf"/>
    <display:setProperty name="export.csv.filename" value="history.csv"/>
    <display:setProperty name="export.xls" value="false" />
    <display:setProperty name="export.xml.filename" value="history.xml"/>
    <display:setProperty name="export.pdf.filename" value="history.pdf"/>
	<display:column property="eventId" title="ID"  sortable="true" headerClass="sortable" />
	<display:column property="timestamp" title="Timestamp"  sortable="true" headerClass="sortable" />
	<display:column property="success" title="Success"  sortable="true" headerClass="sortable" />
	<display:column property="message" title="Message"  sortable="true" headerClass="sortable" />
</display:table>
</body>
</html>
