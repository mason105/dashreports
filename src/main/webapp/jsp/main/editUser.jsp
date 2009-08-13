<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>
<div class="formGroup">
	<div class="formGroupHeader">User Details</div>
	
<s:form action="saveUser">
	<s:actionerror />
	<s:actionmessage/>
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

	<s:checkbox label="Is User Read Only" value="%{runnerUser.isReadOnly}"
		name="runnerUser.isReadOnly">
	</s:checkbox>		

	
	<s:checkbox label="Is Account Locked" value="%{runnerUser.isLocked}"
		name="runnerUser.isLocked">
	</s:checkbox>		
	<hr/>
	 <s:checkboxlist name="groupNames" list="groups" />
	<hr/>
	
</s:form>
</div>
	
	<div id="submit_button"></div>

	<script language="javascript">
		new Jx.Button({label: 'Save',
			image:'<s:url value="/images/icons/disk.png"/>',
			onClick: function() {
				document.saveUser.submit();
			}
		}).addTo('submit_button');
	</script>
</body>
</html>