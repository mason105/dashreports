<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>
<span class="pageTitle"><img
	src="<s:url value='/images/user.png'/>" align="middle" />Edit User</span>

<s:form action="saveUser">

	<s:if test="runnerUser.userName != null">
		<s:textfield label="User Name" size="32" value="%{runnerUser.userName}"
			name="runnerUser.userName" readonly="true" cssClass="readOnly"/>
	</s:if>
	<s:else>
		<s:textfield label="User Name" size="32" value="%{runnerUser.userName}"
			name="runnerUser.userName" />
	</s:else>
	<s:textfield label="Full Name" size="60"
		value="%{runnerUser.fullName}" name="runnerUser.fullName" />
	
	<s:password label="Password" value="%{runnerUser.password}" name="runnerUser.password"/>	
		
	<s:checkbox label="Is User an Administrator" value="%{runnerUser.isAdmin}"
		name="runnerUser.isAdmin">
	</s:checkbox>
	
	<s:checkbox label="Is Account Locked" value="%{runnerUser.isLocked}"
		name="runnerUser.isLocked">
	</s:checkbox>		
	
	 <s:checkboxlist name="groupNames" list="groups" />
	
	<s:submit />
</s:form>
</body>
</html>