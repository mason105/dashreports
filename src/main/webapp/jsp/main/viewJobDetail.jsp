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
<div class="formGroupWide">
<div class="formGroupWideHeader"><img src="<s:url value='/images/v2/icons/jobhistory.png'/>" title="View Execution Detail/History" align="absmiddle" />&nbsp;<s:a href="showGroup.action?groupName=%{groupName}"><s:property value="groupName"/></s:a> > Job Detail - 
			<s:property  value="jobName"/>		</div>

<div class="listing">
	<div class="listingRow">
		<div class="listingCell"><b>Job Name</b></div>
		<div class="listingCell"><s:property value="%{job.jobName}" /></div>
	</div>
	<div class="listingRow">
		<div class="listingCell"><b>Group Name</b></div>
		<div class="listingCell"><s:property value="%{job.groupName}" /></div>
	</div>
	<div class="listingRow">
		<div class="listingCell"><b>Job Description</b></div>
		<div class="listingCell"><s:property value="%{job.description}" /></div>
	</div>
	<div class="listingRow">
		<div class="listingCell"><b>Previous Run Time</b></div>
		<div class="listingCell"><s:property value="%{job.previousRunTime}" />
		</div>
	</div>
	<div class="listingRow">
		<div class="listingCell"><b>Next Run Time</b></div>
		<div class="listingCell"><s:property value="%{job.nexdiv>unTime}" /></div>
	</div>
	<div class="listingRow">
		<div class="listingCell"><b>Schedule Active</b></div>
		<div class="listingCell"><s:property value="%{job.isScheduleActive}" /></div>
	</div>
</div>

</div>
<div class="formGroupWide">
<div class="formGroupWideHeader">Job History</div>
<display:table name="events"  pagesize="25" requestURI="viewJobDetail.action" export="false">    
	<display:column property="eventId" title="ID"  sortable="div>ue" headerClass="sortable" />
	<display:column property="runTime" title="Time (ms)"  sortable="div>ue" headerClass="sortable" />	
	<display:column property="timestamp" title="Timestamp"  sortable="div>ue" headerClass="sortable" />
	<display:column property="success" title="Success"  sortable="div>ue" headerClass="sortable" />
	<display:column property="message" title="Message"  sortable="div>ue" headerClass="sortable" />
</display:table>
	<div class="formBottomEmpty"></div>
</div>
</body>
</html>
