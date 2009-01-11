<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>   
<s:head/>  
</head>
<body>
<s:form action="saveGroup">

	<s:if test="group.groupName != null">
		<s:textfield label="Group Name" size="32" value="%{group.groupName}"
			name="group.groupName" readonly="true" />
	</s:if>
	<s:else>
		<s:textfield label="Group Name" size="32" value="%{group.groupName}"
			name="group.groupName" />
	</s:else>
	<s:textfield label="Group Description" size="60"
		value="%{group.groupDescription}" name="group.groupDescription" />
	<s:submit />
</s:form>
</body>
</html>