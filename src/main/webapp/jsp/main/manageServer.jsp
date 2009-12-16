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
<span class="pageTitle"><img src="<s:url value='/images/icons/server.png'/>" align="absmiddle" />Manage Server</span>
<br/><br/>
<table width="100%">
	<tr>
		<td width="24" class="rowOdd"><img src="<s:url value='/images/v2/icons/user.png'/>"/></td>
		<td class="rowOdd"><a href="listUsers.action">User Management</a></td>
	</tr>
	<tr>
		<td width="24" class="rowEven"><img src="<s:url value='/images/v2/icons/database.png'/>"/></td>	
		<td class="rowEven"><a href="listDataSources.action">Datasource Management</a></td>
	</tr>
	<tr>
		<td width="24" class="rowOdd"><img src="<s:url value='/images/v2/icons/lightning.png'/>"/></td>
		<td class="rowOdd"><a href="listCurrentExecutingJobs.action">Current Executing Jobs</a></td>
	</tr>
	<tr>
		<td width="24" class="rowEven"><img src="<s:url value='/images/v2/icons/chart_curve.png'/>"/></td>
		<td class="rowEven"><a href="jobStatistics.action">Statistics and Warnings</a></td>
	</tr>	
	<tr>
		<td width="24" class="rowOdd"><img src="<s:url value='/images/v2/icons/clock.png'/>"/></td>
		<td class="rowOdd"><a href="schedulerAdmin.action">Manage Scheduler</a></td>
	</tr>	
</table>

</body>
</html>
