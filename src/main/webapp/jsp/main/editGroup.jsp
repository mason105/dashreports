<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
 	<sj:head locale="en" jqueryui="true" jquerytheme="%{themeName}"/>

</head>
<body>
<s:form action="saveGroup" validate="true">
	<div class="formGroup">
	<div class="formGroupHeader">Group Details</div>

		<s:actionerror theme="jquery"/>
		<s:actionmessage theme="jquery"/>
		 <s:if
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