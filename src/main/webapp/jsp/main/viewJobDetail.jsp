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
	src="<s:url value='/images/icons/magnifier.png'/>" align="absmiddle" />View Job
Detail</span>
<br /><br />
[<s:a href="listJobs.action?groupName=%{job.groupName}">Back</s:a>]
<br /><br />
<div class="formGroup">
<div class="formGroupHeader">Job Detail</div>
<table width="100%">
	<tr class="rowEven">
		<td ><b>Job Name</b></td>
		<td><s:property value="%{job.jobName}" /></td>
	</tr>
	<tr  class="rowOdd">
		<td><b>Group Name</b></td>
		<td><s:property value="%{job.groupName}" /></td>
	</tr>
	<tr class="rowEven"
		<td><b>Job Description</b></td>
		<td><s:property value="%{job.description}" /></td>
	</tr>
	<tr  class="rowOdd">
		<td><b>Previous Run Time</b></td>
		<td><s:property value="%{job.previousRunTime}" />
		</td>
	</tr>
	<tr class="rowEven">
		<td><b>Next Run Time</b></td>
		<td><s:property value="%{job.nextRunTime}" /></td>
	</tr>
	<tr  class="rowOdd">
		<td><b>Schedule Active</b></td>
		<td><s:property value="%{job.isScheduleActive}" />
		</td>
	</tr>
</table>
</div>
<div class="formGroupFluid">
<div class="formGroupHeader">Job History</div>
<display:table name="events"  pagesize="25" requestURI="viewJobDetail.action" export="false">    
	<display:column property="eventId" title="ID"  sortable="true" headerClass="sortable" />
	<display:column property="runTime" title="Time to Execute (ms)"  sortable="true" headerClass="sortable" />	
	<display:column property="timestamp" title="Timestamp"  sortable="true" headerClass="sortable" />
	<display:column property="success" title="Success"  sortable="true" headerClass="sortable" />
	<display:column property="message" title="Message"  sortable="true" headerClass="sortable" />
</display:table>
</div>
</body>
</html>
