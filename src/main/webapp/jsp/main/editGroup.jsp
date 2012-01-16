<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>
<s:form action="saveGroup">
	<div class="formGroup">
	<div class="formGroupHeader">Group Details</div>


	<s:actionerror /> <s:actionmessage /> <s:if
		test="group.groupName != null">
		<s:textfield label="Group Name" size="14" value="%{group.groupName}"
			name="group.groupName" readonly="true" cssClass="readOnly, textbox"
			required="true" />
	</s:if> <s:else>
		<s:textfield label="Group Name" size="32" value="%{group.groupName}"
			name="group.groupName" cssClass="textbox" required="true" />
	</s:else> <s:textfield label="Group Description" size="60"
		value="%{group.groupDescription}" name="group.groupDescription"
		cssClass="textbox" />

	<div class="formBottom">
	<div class="formFooterText">* required field</div>
	<s:submit value="Save" align="left" /></div>
	</div>
</s:form>
</body>
</html>