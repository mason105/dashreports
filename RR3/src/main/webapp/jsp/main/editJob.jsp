<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />

<script language="javascript">
	function changeTab(tabName) {
		var reportButton=document.getElementById('reportButton');
		var paramsButton=document.getElementById('paramsButton');
		var scheduleButton=document.getElementById('scheduleButton');
		var outputButton=document.getElementById('outputButton');
		var miscButton=document.getElementById('miscButton');
		reportButton.className='tabButton';
		paramsButton.className='tabButton';
		scheduleButton.className='tabButton';
		outputButton.className='tabButton';
		miscButton.className='tabButton';
		var button = document.getElementById(tabName+'Button');
		button.className='tabButtonSelected';

		var report=document.getElementById('report');
		var params=document.getElementById('params');
		var schedule=document.getElementById('schedule');
		var output=document.getElementById('output');
		var misc=document.getElementById('misc');

		report.style.visibility='hidden';
		params.style.visibility='hidden';
		schedule.style.visibility='hidden';
		output.style.visibility='hidden';
		misc.style.visibility='hidden';

		var tab = document.getElementById(tabName);
		tab.style.visibility='visible';
	}
</script>

</head>
<body>




<s:form action="saveJob" method="post" enctype="multipart/form-data"
	validate="true">

	<s:actionerror />
	<s:fielderror />

	<div class="tabButtonSelected" id="reportButton" onClick="changeTab('report')">
		Report
	</div>
	
	<div class="tabButton" id="paramsButton" onClick="changeTab('params')">
		Parameters
	</div>
	
	<div class="tabButton" id="scheduleButton" onClick="changeTab('schedule')">
		Schedule
	</div>
	
	<div class="tabButton" id="outputButton" onClick="changeTab('output')">
		Output
	</div>
	
	<div class="tabButton" id="miscButton" onClick="changeTab('misc')">
		Misc
	</div>
	
	<div class="formSection" id="report">
	<div class="midLabel">Report Details</div>

	<s:if test="job.pk.jobName != null">
		<s:textfield size="32" label="Job Name" value="%{job.pk.jobName}"
			name="job.pk.jobName" readonly="true" cssClass="readOnly">

		</s:textfield>
	</s:if> <s:else>
		<s:textfield size="32" label="Job Name" value="%{job.pk.jobName}"
			name="job.pk.jobName">

		</s:textfield>
	</s:else> <s:hidden value="%{groupName}" name="job.pk.group.groupName" /> <s:textfield
		label="Description" size="64" value="%{job.description}"
		name="job.description">

	</s:textfield> <s:select label="Select Data Source"
		name="job.datasource.dataSourceName" value="%{job.dataSource}"
		list="dataSources" listKey="dataSourceName" listValue="dataSourceName">
	</s:select> <s:textarea label="Report Query" cols="30" rows="20"
		value="%{job.query}" name="job.query">
<s:checkbox label="Is Bursted Report" value="%{job.isBurst}"
		name="job.isBurst">
	</s:checkbox>

	<s:textarea label="Burst Query" cols="30" rows="20"
		value="%{job.burstQuery}" name="job.burstQuery">
	</s:textarea>		
	</s:textarea> 
	</div>
	
	<div class="formSection" id="params">
	<div class="midLabel">Parameters</div>
	<s:submit name="dispatchSaveButton" value="Add Parameter" align="none" />
	<s:iterator value="job.parameters" status="rowstatus">
		<div class="formSectionInner">
		<div class="smallLabel">Parameter Index <s:property
			value="%{pk.parameterIdx}" /></div>
		<s:hidden value="%{pk.parameterIdx}"
			name="parameters[%{#rowstatus.index}].pk.parameterIdx" /> <s:textfield
			label="Value" name="parameters[%{#rowstatus.index}].parameterValue"
			value="%{parameterValue}">
		</s:textfield> <s:textfield label="Burst Column"
			name="parameters[%{#rowstatus.index}].parameterBurstColumn"
			value="%{parameterBurstColumn}">
		</s:textfield> <s:select label="Data Type"
			name="parameters[%{#rowstatus.index}].parameterType" list="dataTypes"
			listKey="name" listValue="displayName">

		</s:select> <s:submit align="none" name="dispatchSaveButton"
			value="Delete Parameter %{pk.parameterIdx}" /></div>
	</s:iterator>
	</div>

	<div class="formSection" id="schedule">
		<div class="midLabel">Schedule Details</div>	
		<div class="wwgrp">
			<div class="dateTimePicker">
				<table width="100%">
					<sx:datetimepicker label="Start Date Time" value="%{job.startDate}"
						name="job.startDate" displayFormat="dd-MM-yyyy HH:mm:ss">
					</sx:datetimepicker>
				</table>
			</div>
		</div>
		<div class="wwgrp">
			<div class="dateTimePicker">
				<table width="100%">
					<sx:datetimepicker label="End Date/Time" value="%{job.endDate}"
						name="job.endDate" displayFormat="dd-MM-yyyy HH:mm:ss">
					</sx:datetimepicker>
				</table>
			</div>
		</div>
		<s:textfield label="Cron String" size="32" value="%{job.cronString}" name="job.cronString">
		</s:textfield>
	</div>
	
	<div class="formSection" id="output">
		<div class="midLabel">Output Details</div>
		<s:file label="Jasper Template File" name="job.upload"></s:file> 
		<s:select label="File Format" name="job.fileFormat" list="fileFormats"
			listKey="name" listValue="displayName"></s:select>
		<s:textfield label="File Name from Burst Column" size="32"
			value="%{job.burstFileNameParameterName}"
			name="job.burstFileNameParameterName">
		</s:textfield>
		<s:textfield label="Output URL" size="64" value="%{job.outputUrl}"
			name="job.outputUrl">
		</s:textfield>
		<s:textarea label="Distribution Email Address(es)" cols="30" rows="20"
			value="%{job.targetEmailAddress}" name="job.targetEmailAddress">
		</s:textarea>
	</div>

	<div class="formSection" id="misc">
		<div class="midLabel">Misc Details</div>
		<s:textarea label="Success/Fail Alert Email Address(es)" cols="30"
			rows="20" value="%{job.alertEmailAddress}"
			name="job.alertEmailAddress">
		</s:textarea>
	</div>

	<div class="formSubmit" id="save">
		<s:submit name="dispatchSaveButton" value="Save" align="none" />
	</div>
</s:form>

<script language="javascript">
	changeTab('<s:property value="activeTab" />');
</script>
</body>
</html>
