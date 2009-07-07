<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>
<span class="pageTitle"><img
	src="<s:url value='/images/icons/user.png'/>" align="absmiddle" />Users</span>

<table border="0" width="100%">
	<s:if test="sessionUser.isAdmin == true">
	<tr class="rowHeader"> 
		
		<td colspan="4" class="rowHeader"><a href="setupEditUser.action"><img
			src="<s:url value='/images/icons/add.png'/>" align="absmiddle" />Add
		User</a></td>
	</tr>
	</s:if>
	<s:if test="users.size > 0">
		<%
			boolean rowOdd = true;
		%>
		<s:iterator value="users">
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
					<s:property value="userName" />
				</td>
				<td><s:property value="fullName" /></td>				
				<s:if test="sessionUser.isAdmin == true">
					<td width="16"><s:a href="setupEditUser.action?userName=%{userName}">
						<img src="<s:url value='/images/icons/pencil.png'/>" align="absmiddle" alt="Edit" />
					</s:a></td>
					<td width="16"><s:a href="deleteUser.action?userName=%{userName}"  onClick="return confirm('Really delete this user?');">
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
