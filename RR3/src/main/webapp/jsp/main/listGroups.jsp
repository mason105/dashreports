<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<s:head/>
</head>
<body>
<span class="pageTitle"><img
	src="<s:url value='/images/folders.png'/>" align="middle" />Groups</span>

<table border="0" width="100%">
	<tr class="rowHeader"> 
		<td colspan="4" class="rowHeader"><a href="setupEditGroup.action"><img
			src="<s:url value='/images/add_small.png'/>" align="absmiddle" />Add
		Group</a></td>
	</tr>
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
				<s:if test="user.isAdmin == true">
					<td width="24"><s:a href="setupEditGroup.action?groupName=%{groupName}">
						<img src="<s:url value='/images/edit_small.png'/>" align="absmiddle" alt="Edit" />
					</s:a></td>
					<td width="24"><a href="deleteGroup.action?groupName= <s:property value="groupName" />"  onClick="return confirm('Really delete this group and all jobs within it?');">
						<img src="<s:url value='/images/delete_small.png'/>" alt="Delete"
							align="absmiddle" />
					</a></td>
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
