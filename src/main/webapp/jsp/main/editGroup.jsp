<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>

<div class="formGroup">
	<div class="formGroupHeader">Group Details</div>
	
	<s:form action="saveGroup">
		<s:actionerror />
		<s:actionmessage/>
		<s:if test="group.groupName != null">
			<s:textfield label="Group Name" size="32" value="%{group.groupName}"
				name="group.groupName" readonly="true" cssClass="readOnly, textbox" required="true"/>
		</s:if>
		<s:else>
			<s:textfield label="Group Name" size="32" value="%{group.groupName}"
				name="group.groupName"  cssClass="textbox" required="true"/>
		</s:else>
		<s:textfield label="Group Description" size="60"
			value="%{group.groupDescription}" name="group.groupDescription"  cssClass="textbox" />

	</s:form>
</div>
<div class="formFooterText">* required field</div>
	<div id="submit_button"></div>

	<script language="javascript">
		new Jx.Button({label: 'Save',
			image:'<s:url value="/images/icons/disk.png"/>',
			onClick: function() {
				document.saveGroup.submit();
			}
		}).addTo('submit_button');
	</script>
	
</body>
</html>