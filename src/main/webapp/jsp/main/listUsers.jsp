<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>
<div class="formGroup">
	<div class="formGroupHeader">
		<img src="<s:url value='/images/v2/nav/usersblue.png'/>" align="absmiddle" />&nbsp;Manage Users
	</div>
	<div class="listingHeader"> 	
		<div class="listingIcon"><img src="<s:url value='/images/v2/icons/add.png'/>" align="absmiddle" /></div><a href="setupEditUser.action">Add a User</a>
	</div>

	<div class="listing">
	
	<div class="listingHeaderRow">		
		<div class="listingIconCell">&nbsp;</div>
		<div class="listingIconCell">&nbsp;</div>		
		<div class="listingCell">User Name</div>
		<div class="listingCell">Full Name</div>
	</div>
	<s:iterator value="users">
	<div class="listingRow">
		<div class="listingIconCell"><s:a href="setupEditUser.action?userName=%{userName}"><img src="<s:url value='/images/v2/icons/edit.png'/>" align="absmiddle" alt="Edit" /></s:a></div>
		<div class="listingIconCell"><s:a href="deleteUser.action?userName=%{userName}"  onClick="return confirm('Really delete this user?');"><img src="<s:url value='/images/v2/icons/delete.png'/>" alt="Delete" align="absmiddle" /></s:a></div>		
		<div class="listingCell"><s:property value="userName" /></div>
		<div class="listingCell"><s:property value="fullName" /></div>								
	</div>
	</s:iterator>	
	</div>
</div>
</body>
</html>
