<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>
<span class="pageTitle"><img
	src="<s:url value='/images/folders.png'/>" align="middle" />Edit Group</span>

<s:form action="saveGroup">
	<s:actionerror />
	<s:actionmessage/>
	<s:if test="group.groupName != null">
		<s:textfield label="Group Name" size="32" value="%{group.groupName}"
			name="group.groupName" readonly="true" cssClass="readOnly"/>
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