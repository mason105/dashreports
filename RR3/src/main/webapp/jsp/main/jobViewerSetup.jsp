<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>

<s:form action="viewJobOutput" method="post" enctype="multipart/form-data"
	validate="true">
	
	<s:hidden value="%{jobName}" name="jobName" />
	<s:hidden value="%{groupName}" name="groupName" />

	<div class="smallLabel">Job Name <s:property
			value="%{jobName}" /></div>
	<div class="smallLabel">Group Name <s:property
			value="%{groupName}" /></div>

	
	<s:iterator value="job.parameters" status="rowstatus">
		<div class="formSectionInner">
		<div class="smallLabel">Parameter Index <s:property
			value="%{pk.parameterIdx}" /></div>
		<s:hidden value="%{pk.parameterIdx}"
			name="parameters[%{#rowstatus.index}].pk.parameterIdx" /> 
		
		<s:if test="parameters[%{#rowstatus.index}].parameterBurstColumn == null">
			<s:textfield
				label="Value" name="parameters[%{#rowstatus.index}].parameterValue"
				value="%{parameterValue}">
			</s:textfield> 
		</s:if> <s:else>
			<s:textfield
				label="Value" name="parameters[%{#rowstatus.index}].parameterValue"
				value="%{parameterValue}"  readonly="true" cssClass="readOnly">
			</s:textfield> 
		</s:else> 
			
		<s:textfield label="Burst Column"
			name="parameters[%{#rowstatus.index}].parameterBurstColumn"
			value="%{parameterBurstColumn}" readonly="true" cssClass="readOnly"></s:textfield> 

		 <s:select label="Data Type"
			name="parameters[%{#rowstatus.index}].parameterType" list="dataTypes"
			listKey="name" listValue="displayName" readonly="true" cssClass="readOnly"></s:select> 
		</div>
	</s:iterator>
	
	<div class="formSubmit" id="save">
		<s:submit name="dispatchRunButton" value="Get Report" align="none" />
	</div>
</s:form>

</body>
</html>
	