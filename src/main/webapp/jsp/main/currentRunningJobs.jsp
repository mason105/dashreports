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
	src="<s:url value='/images/icons/application_lightning.png'/>" align="absmiddle" />Current Executing Jobs</span>

<table border="0" width="100%">

	<s:if test="jobs.size > 0">
		<%
			boolean rowOdd = true;
		%>
		<s:iterator value="jobs">
			<%
				if (rowOdd) {
							rowOdd = false;
			%>
				<tr class="rowOdd">
			<%
				} else {
							rowOdd = true;
			%>			
				<tr class="rowEven">
			<%
				}
			%>
				<td>
					<s:property value="pk.jobName" />
				</td>
				<td><s:property value="pk.group.groupName" /></td>				
					<td width="24"><s:a href="interruptCurrentExecutingJob.action?jobName=%{pk.jobName}groupName=%{pk.group.groupName}">
						<img src="<s:url value='/images/delete_small.png'/>" align="absmiddle" alt="Halt Execution" />
					</s:a></td>
			</tr>
		</s:iterator>
	</s:if>
</table>
</body>
</html>
	