<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>

<s:form action="saveUser" validate="true">
<div class="formGroup">
	<div class="formGroupHeader">User Details</div>
	
		<s:actionerror />
		<s:actionmessage/>
		<s:if test="runnerUser.userName != null">
			<s:textfield label="User Name" size="32" value="%{runnerUser.userName}"
				name="runnerUser.userName" readonly="true" cssClass="readOnly" cssClass="textbox" required="true"/>
		</s:if>
		<s:else>
			<s:textfield label="User Name" size="32" value="%{runnerUser.userName}"
				name="runnerUser.userName"  cssClass="textbox" required="true"/>
		</s:else>
		<s:textfield label="Full Name" size="60"
			value="%{runnerUser.fullName}" name="runnerUser.fullName"  cssClass="textbox"/>

		<s:password label="Password" value="%{runnerUser.password}" name="runnerUser.password" cssClass="textbox"  required="true"/>	

		<s:checkbox label="Is User an Administrator" value="%{runnerUser.isAdmin}"
			name="runnerUser.isAdmin" cssClass="checkbox">
		</s:checkbox>

		<s:checkbox label="Is User Read Only" value="%{runnerUser.isReadOnly}"
			name="runnerUser.isReadOnly" cssClass="checkbox">
		</s:checkbox>		


		<s:checkbox label="Is Account Locked" value="%{runnerUser.isLocked}"
			name="runnerUser.isLocked" cssClass="checkbox">
		</s:checkbox>		

		<s:select label="Groups"
	       name="userGroups"
	       list="groups"
	       multiple="true"
	       listKey="groupName" 
	       listValue="groupName"	       
		/>

	
</div>
<div class="formFooterText">* required field</div>
</s:form>	
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