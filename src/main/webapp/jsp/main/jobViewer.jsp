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

<span class="pageTitle"><img
	src="<s:url value="/images/icons/application_form_magnify.png"/>" align="absmiddle" />View Report</span>	
<s:form action="viewJobOutput" method="post" enctype="multipart/form-data"
	validate="true">


	
	<s:hidden value="%{jobName}" name="jobName" />
	<s:hidden value="%{groupName}" name="groupName" />

<div class="formGroup">
	<div class="formGroupHeader"><s:property value="%{jobName}" />(<s:property value="%{groupName}" />)</div>
	
	<s:iterator value="parameters" status="rowstatus">

	<s:if test="(key.parameterBurstColumn == null)||(key.parameterBurstColumn.isEmpty())">					
			<s:textfield
				label="%{key.description}" name="parameters[%{#rowstatus.index}].parameterValue"
				value="%{key.parameterValue}">
			</s:textfield> 	
		
	</s:if> <s:else> 
			<s:select
				label="%{key.description}" name="parameters[%{#rowstatus.index}].parameterValue"
				value="%{key.parameterValue}"  list="value">
			</s:select> 
	</s:else>

		<s:hidden value="%{key.pk.parameterIdx}"
			name="parameters[%{#rowstatus.index}].pk.parameterIdx" /> 
		
		<s:hidden
		name="parameters[%{#rowstatus.index}].parameterBurstColumn"
		value="%{key.parameterBurstColumn}" />
		
		<s:hidden value="%{key.parameterType}"
				name="parameters[%{#rowstatus.index}].parameterType" /> 			
	 
	</s:iterator>
		

</div>


	<div id="submit_button"></div>

	<script language="javascript">
		new Jx.Button({label: 'Get Report',
			image:'<s:url value="/images/icons/report.png"/>',
			onClick: function() {
				document.viewJobOutput.submit();
			}
		}).addTo('submit_button');
	</script>
	

</s:form>
</body>
</html>
	