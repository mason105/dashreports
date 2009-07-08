<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />

</head>
<body>




<s:form action="saveJob" method="post" enctype="multipart/form-data"
	validate="true">

	<s:actionerror />
	<s:actionmessage/>
	
	
	<sx:tabbedpanel id="edit">
	<sx:div id="report" label="Report"labelposition="top" theme="ajax" >

	<s:if test="job.pk.jobName != null">
		<s:textfield size="32" label="Job Name" value="%{job.pk.jobName}"
			name="job.pk.jobName" readonly="true" cssClass="readOnly">

		</s:textfield>
	</s:if> <s:else>
		<s:textfield size="32" label="Job Name" value="%{job.pk.jobName}"
			name="job.pk.jobName">

		</s:textfield>
	</s:else> <s:hidden value="%{groupName}" name="job.pk.group.groupName" /> <s:textfield
		label="Description" size="50" value="%{job.description}"
		name="job.description">

	</s:textfield>
	
	 <s:select label="Select Data Source"
		name="job.datasource.dataSourceName" value="%{job.dataSource}"
		list="dataSources" listKey="dataSourceName" listValue="dataSourceName">
	</s:select> 
	
	<s:textarea label="Report Query" cols="120" rows="30"
		value="%{job.query}" name="job.query"></s:textarea>
		
	<s:checkbox label="Is Bursted Report" value="%{job.isBurst}"
		name="job.isBurst">
	</s:checkbox>

	<s:textarea label="Burst Query" cols="120" rows="5"
		value="%{job.burstQuery}" name="job.burstQuery">
	</s:textarea>		


	</sx:div>
	
	<sx:div id="parameters" label="Parameters">	
	<s:submit name="dispatchSaveButton" value="Add Parameter" align="none" />
	<s:iterator value="job.parameters" status="rowstatus">
		<div class="formSectionInner">
		<div class="smallLabel">Parameter Index <s:property
			value="%{pk.parameterIdx}" /></div>
		<s:hidden value="%{pk.parameterIdx}"
			name="parameters[%{#rowstatus.index}].pk.parameterIdx" />
		
		<s:textfield
			label="Description" name="parameters[%{#rowstatus.index}].description"
			value="%{description}">
		</s:textfield> 
					
		<s:textfield
			label="Value" name="parameters[%{#rowstatus.index}].parameterValue"
			value="%{parameterValue}">
		</s:textfield> 
		
		<s:textfield label="Burst Column Name"
			name="parameters[%{#rowstatus.index}].parameterBurstColumn"
			value="%{parameterBurstColumn}">
		</s:textfield>
		
		 <s:select label="Data Type"
			name="parameters[%{#rowstatus.index}].parameterType" list="dataTypes"
			listKey="name" listValue="displayName">

		</s:select> 
		<s:submit align="none" name="dispatchSaveButton"
			value="Delete Parameter %{pk.parameterIdx}" /></div>
	</s:iterator>
	</sx:div>

	<sx:div id="schedule" label="Schedule">	
		<div class="wwgrp">
			<div class="dateTimePicker">
					<sx:datetimepicker label="Start Date Time" value="%{job.startDate}"
						name="job.startDate">
					</sx:datetimepicker>
			</div>
		</div>
		<div class="wwgrp">
			<div class="dateTimePicker">
				<table width="100%">
					<sx:datetimepicker label="End Date/Time" value="%{job.endDate}"
						name="job.endDate">
					</sx:datetimepicker>
				</table>
			</div>
		</div>
		<s:textfield label="Cron String" size="32" value="%{job.cronString}" name="job.cronString">
		</s:textfield>
	</sx:div>
	
	<sx:div id="output" label="Output">		
		<s:select label="Template Type" name="job.templateType" list="templateTypes"
			listKey="name" listValue="displayName"></s:select>
		<s:file label="Template File" name="template"></s:file> 
		<s:select label="File Format" name="job.fileFormat" list="fileFormats"
			listKey="name" listValue="displayName"></s:select>

		<s:textfield label="Output URL" size="50" value="%{job.outputUrl}"
			name="job.outputUrl">
		</s:textfield>
		<s:textarea label="Distribution Email Address(es)" cols="30" rows="20"
			value="%{job.targetEmailAddress}" name="job.targetEmailAddress">
		</s:textarea>

	</sx:div>

	<sx:div id="misc" label="Misc">		
		<s:textarea label="Success/Fail Alert Email Address(es)" cols="30"
			rows="20" value="%{job.alertEmailAddress}"
			name="job.alertEmailAddress">
		</s:textarea>
	</sx:div>
	</sx:tabbedpanel>
	<div class="formSubmit" id="save">
		<s:submit name="dispatchSaveButton" value="Save" align="none" />
	</div>
</s:form>

<script language="javascript">
	changeTab('<s:property value="activeTab" />');
</script>
</body>
</html>
