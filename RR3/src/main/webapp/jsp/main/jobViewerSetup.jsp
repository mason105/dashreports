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

	
	<s:iterator value="parameters" status="rowstatus">
		<div class="formSectionInner">
		<div class="smallLabel">Parameter Index <s:property
			value="%{pk.parameterIdx}" /></div>
		<s:hidden value="%{key.pk.parameterIdx}"
			name="parameters[%{#rowstatus.index}].pk.parameterIdx" /> 

		<div class="smallLabel">Description <s:property
			value="%{pk.description}" /></div>
		
		<s:if test="key.parameterBurstColumn == null">
	

			<s:textfield
				label="Value" name="parameters[%{#rowstatus.index}].parameterValue"
				value="%{key.parameterValue}">
			</s:textfield> 

		</s:if> <s:else> 
		<!-- going to shelve this for a bit 
			<s:select
				label="Value" name="parameters[%{#rowstatus.index}].parameterValue"
				value="%{key.parameterValue}"  list="value">
			</s:select> 

		-->						
		</s:else> 
		
		<s:hidden
		name="parameters[%{#rowstatus.index}].parameterBurstColumn"
		value="%{key.parameterBurstColumn}" />
		
		<s:hidden value="%{key.parameterType}"
				name="parameters[%{#rowstatus.index}].parameterType" /> 
			</div>
	
	</s:iterator>
	
	<div class="formSubmit" id="save">
		<s:submit name="dispatchRunButton" value="Get Report" align="none" />
	</div>
</s:form>

</body>
</html>
	