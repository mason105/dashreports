<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>
<span class="pageTitle"><img
	src="<s:url value='/images/icons/chart_bar.png'/>" align="absmiddle" />Dashboard Alerts</span>

<table border="0" width="100%">
	<tr class="rowHeader"> 
		
		<td colspan="5" class="rowHeader"><a href="setupEditAlert.action"><img
			src="<s:url value='/images/icons/add.png'/>" align="absmiddle" />Add
		New Alert</a></td>
	</tr>
	<s:if test="alerts.size > 0">
		<%
			boolean rowOdd = true;
		%>
		<s:iterator value="alerts">
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
					<s:property value="id" />
				</td>
				<td>
					<s:property value="alertName" />
				</td>
				<td><s:property value="group.groupName" /></td>				
				<td width="16"><s:a href="setupEditAlert.action?id=%{id}">
					<img src="<s:url value='/images/icons/pencil.png'/>" align="absmiddle" alt="Edit" /></s:a>
				</td>
				<td width="16"><s:a href="deleteAlert.action?id=%{id}"  onClick="return confirm('Really delete this alert?');">
						<img src="<s:url value='/images/icons/delete.png'/>" alt="Delete"
							align="absmiddle" /></s:a>
				</td>
			</tr>
		</s:iterator>
	</s:if>
</table>

</body>
</html>
