<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>
<span class="pageTitle"><img
	src="<s:url value='/images/icons/group.png'/>" align="absmiddle" />Groups</span>

<table border="0" width="100%">
	<s:if test="sessionUser.isAdmin == true">
	<tr class="rowHeader"> 
		
		<td colspan="4" class="rowHeader"><a href="setupEditGroup.action"><img
			src="<s:url value='/images/icons/add.png'/>" align="absmiddle" />Add
		Group</a></td>
	</tr>
	</s:if>
	<s:if test="groups.size > 0">
		<%
			boolean rowOdd = true;
		%>
		<s:iterator value="groups">
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
				<td><s:a href="listJobs.action?groupName=%{groupName}">
					<s:property value="groupName" />
				</s:a></td>
				<td><s:property value="groupDescription" /></td>				
				<s:if test="sessionUser.isAdmin == true">
					<td width="16"><s:a href="setupEditGroup.action?groupName=%{groupName}">
						<img src="<s:url value='/images/icons/pencil.png'/>" align="absmiddle" alt="Edit" />
					</s:a></td>
					<td width="16"><s:a href="deleteGroup.action?groupName=%{groupName}"  onClick="return confirm('Really delete this group and all jobs within it?');">
						<img src="<s:url value='/images/icons/delete.png'/>" alt="Delete"
							align="absmiddle" />
					</s:a></td>
				</s:if>
				<s:else>
					<td></td>
					<td></td>
				</s:else>
			</tr>
		</s:iterator>
	</s:if>
</table>

</body>
</html>
