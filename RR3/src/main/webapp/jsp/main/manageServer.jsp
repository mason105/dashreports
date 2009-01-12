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
<span class="pageTitle"><img src="<s:url value='/images/manage.png'/>" align="absmiddle" />Manage Server</span>
<br/><br/>
<table width="100%">
	<tr>
		<td width="24" class="rowOdd"><img src="<s:url value='/images/user_small.png'/>"/></td>
		<td class="rowOdd"><a href="#">User Management</a></td>
	</tr>
	<tr>
		<td width="24" class="rowOdd"><img src="<s:url value='/images/dashboard_small.png'/>"/></td>
		<td class="rowEven"><a href="#">Dashboard Alerts</a></td>
	</tr>	
	<tr>
		<td width="24" class="rowEven"><img src="<s:url value='/images/datasource_small.png'/>"/></td>	
		<td class="rowOdd"><a href="listDataSources.action">Datasource Management</a></td>
	</tr>
	<tr>
		<td width="24" class="rowOdd"><img src="<s:url value='/images/executing_small.png'/>"/></td>
		<td class="rowEven"><a href="#">Current Executing Jobs</a></td>
	</tr>
	<tr>
		<td width="24" class="rowEven"><img src="<s:url value='/images/stats_small.png'/>"/></td>
		<td class="rowOdd"><a href="#">Statistics and Warnings</a></td>
	</tr>	
</table>

</body>
</html>
