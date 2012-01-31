<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
	<sj:head locale="en" jqueryui="true" jquerytheme="smoothness"/>
</head>
<body>

<s:form action="saveUser" method="get" validate="true" autocomplete="off">
<div class="formGroup">
	<div class="formGroupHeader">User Details</div>
	
		<s:actionerror theme="jquery"/>
		<s:actionmessage theme="jquery"/>
		<s:if test="runnerUser.userName != null">
			<s:textfield label="User Name" size="32" value="%{runnerUser.userName}"
				name="runnerUser.userName" readonly="true" cssClass="readOnly, textbox" required="true"/>
		</s:if>
		<s:else>
			<s:textfield label="User Name" size="32" value="%{runnerUser.userName}"
				name="runnerUser.userName"  cssClass="textbox" required="true"/>
		</s:else>
	
			<s:textfield label="Full Name" size="60"
			value="%{runnerUser.fullName}" name="runnerUser.fullName"  cssClass="textbox"/>
	
		<s:if test="runnerUser.userName != null">
				<s:password label="Password" name="runnerUser.password" cssClass="textbox"  />	
		</s:if>
		<s:else>	
			<s:password label="Password" name="runnerUser.password" cssClass="textbox"  required="true"/>	
		</s:else>
		<s:checkbox label="Administrator" value="%{runnerUser.isAdmin}"
			name="runnerUser.isAdmin" cssClass="checkbox">
		</s:checkbox>

		<s:checkbox label="Read Only" value="%{runnerUser.isReadOnly}"
			name="runnerUser.isReadOnly" cssClass="checkbox">
		</s:checkbox>		


		<s:checkbox label="Locked" value="%{runnerUser.isLocked}"
			name="runnerUser.isLocked" cssClass="checkbox">
		</s:checkbox>		

		<s:select label="Groups"
	       name="userGroups"
	       list="groups"
	       multiple="true"
	       listKey="groupName" 
	       listValue="groupName"  cssClass="textbox"	       
		/>
		<div class="formBottom">
		<div class="formFooterText">* required field</div>
		<s:submit value="Save" align="left"/>
		</div>
	
</div>
</s:form>	
</body>
</html>