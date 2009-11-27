<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>
<span class="pageTitle"><img src="<s:url value='/images/icons/chart_bar.png'/>" align="absmiddle" />Dashboard Alerts</span>

<table border="0" width="100%">
	<tr class="rowHeader"> 
		
		<td colspan="4" class="rowHeader"><a href="setupEditAlert.action?groupName=%{groupName}"><img src="<s:url value='/images/icons/add.png'/>" align="absmiddle" />Add
		New Alert</a></td>
	</tr>
	<tr class="rowHeader"><td colspan="3"><s:property value="%{groupName}"/></td></tr>
<s:if test="alerts.size>0">					
		<s:iterator value="alerts" >		
		<%
			boolean rowOdd = true;
		%>
		
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
					<s:property value="alertName" />
				</td>		
				<td width="16"><s:a href="setupEditAlert.action?id=%{id}&groupName=%{groupName}">
					<img src="<s:url value='/images/icons/pencil.png'/>" align="absmiddle" alt="Edit" /></s:a>
				</td>
              <td width="16">	
				 <s:a href="invokeAlert.action?alertId=%{id}"><img src="<s:url value='/images/icons/cog.png'/>" alt="Invoke Now"
							align="absmiddle" /></s:a>			
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
