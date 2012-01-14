<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>

<div class="formGroup">
	<div class="formGroupHeader">
		<img src="<s:url value='/images/v2/nav/groupsblue.png'/>" align="absmiddle" />&nbsp;Manage Groups
	</div>
	<div class="listingHeader"> 	
		<div class="listingIcon"><img src="<s:url value='/images/v2/icons/add.png'/>" align="absmiddle" /></div><a href="setupEditGroup.action">Add a Group</a>
	</div>

	<div class="listing">
	
	<div class="listingHeaderRow">		
		<div class="listingIconCell">&nbsp;</div>
		<div class="listingIconCell">&nbsp;</div>		
		<div class="listingCell">Group Name</div>
		<div class="listingCell">Description</div>
	</div>
	<s:iterator value="groups">
	<div class="listingRow">
		<div class="listingIconCell"><s:a href="setupEditGroup.action?groupName=%{groupName}"><img src="<s:url value='/images/v2/icons/edit.png'/>" align="absmiddle" alt="Edit" /></s:a></div>
		<div class="listingIconCell"><s:a href="deleteGroup.action?groupName=%{groupName}"  onClick="return confirm('Really delete this group and all jobs within it?');"><img src="<s:url value='/images/v2/icons/delete.png'/>" alt="Delete" align="absmiddle" /></s:a></div>		
		<div class="listingCell"><s:property value="groupName" /></div>
		<div class="listingCell"><s:property value="groupDescription" /></div>								
	</div>
	</s:iterator>	
	</div>
</div>
<span class="pageTitle"><img src="<s:url value='/images/v2/icons/group.png'/>" align="absmiddle" />Groups</span>

<table border="0" width="100%">
	<s:if test="sessionUser.isAdmin == true">
	<tr class="rowHeader"> 
		
		<td colspan="4" class="rowHeader"><a href="setupEditGroup.action"><img src="<s:url value='/images/icons/add.png'/>" align="absmiddle" />Add
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
				<td><s:a href="group.action?groupName=%{groupName}">
					<s:property value="groupName" />
				</s:a></td>
				<td><s:property value="groupDescription" /></td>				
				<s:if test="sessionUser.isAdmin == true">
					<td width="16"><s:a href="setupEditGroup.action?groupName=%{groupName}">
						<img src="<s:url value='/images/icons/pencil.png'/>" align="absmiddle" alt="Edit" />
					</s:a></td>
					<td width="16"><s:a href="deleteGroup.action?groupName=%{groupName}"  onClick="return confirm('Really delete this group and all jobs within it?');">
						<img src="<s:url value='/images/icons/delete.png'/>" alt="Delete" align="absmiddle" />
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
