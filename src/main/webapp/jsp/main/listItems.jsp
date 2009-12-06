<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>
<span class="pageTitle"><img src="<s:url value='/images/icons/chart_bar.png'/>" align="absmiddle" />Dashboard Items for Group <s:property value="groupName"/></span>

<table border="0" width="100%">
	<tr class="rowHeader"> 
		<td colspan="4" class="rowHeader"><a href="setupEditChart.action?groupName=%{groupName}"><img src="<s:url value='/images/icons/add.png'/>" align="absmiddle" />Add New Chart</a></td>
	</tr>
	<tr class="rowHeader"> 
		<td colspan="4" class="rowHeader"><a href="setupEditGrid.action?groupName=%{groupName}"><img src="<s:url value='/images/icons/add.png'/>" align="absmiddle" />Add New Data Grid</a></td>
	</tr>
	<tr class="rowHeader"> 
		<td colspan="4" class="rowHeader"><a href="setupEditThreshold.action?groupName=%{groupName}"><img src="<s:url value='/images/icons/add.png'/>" align="absmiddle" />Add New Threshold Alert</a></td>
	</tr>
<s:if test="items.size>0">					
		<s:iterator value="items" >		
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
					<s:property value="itemName" />
				</td>		
				<td width="16">
				<s:if test="itemType='Chart'">
					<s:a href="setupEditChart.action?itemId=%{itemId}&groupName=%{groupName}"><img src="<s:url value='/images/icons/pencil.png'/>" align="absmiddle" alt="Edit" /></s:a>
				</s:if>
				<s:if test="itemType='Grid'">
					<s:a href="setupEditGrid.action?itemId=%{itemId}&groupName=%{groupName}"><img src="<s:url value='/images/icons/pencil.png'/>" align="absmiddle" alt="Edit" /></s:a>
				</s:if>
				<s:if test="itemType='Threshold'">
					<s:a href="setupEditThreshold.action?itemId=%{itemId}&groupName=%{groupName}"><img src="<s:url value='/images/icons/pencil.png'/>" align="absmiddle" alt="Edit" /></s:a>
				</s:if>
				</td>
              <td width="16">	
				 <s:a href="invokeItem.action?alertId=%{itemId}"><img src="<s:url value='/images/icons/cog.png'/>" alt="Invoke Now"
							align="absmiddle" /></s:a>			
              </td>				
				<td width="16"><s:a href="deleteItem.action?id=%{itemId}"  onClick="return confirm('Really delete this alert?');">
						<img src="<s:url value='/images/icons/delete.png'/>" alt="Delete"
							align="absmiddle" /></s:a>
				</td>
			</tr>
	</s:iterator>
</s:if>
</table>

</body>
</html>
