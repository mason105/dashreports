<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
<script language="javascript" type="text/javascript">
		var maxParamRow=<s:property value="paramCount" />;
		function addParameter() {
			var ni = document.getElementById('parameterArea');
			var newdiv = document.createElement('div');				
			var divIdName = 'my'+maxParamRow;
			newdiv.setAttribute('id',divIdName);
			var innerHtml='';
				innerHtml=innerHtml+'';
				innerHtml=innerHtml+'Index  <input readonly="true" type=\"text\" name=\"parameters[' + maxParamRow + '].pk.parameterIdx\" value=\"' + maxParamRow + '\">';
				innerHtml=innerHtml+'Value  <input type=\"text\" name=\"parameters[' + maxParamRow + '].parameterValue\" value=\"\">';
				innerHtml=innerHtml+'Burst Column  <input type=\"text\" name=\"parameters[' + maxParamRow + '].parameterBurstColumn\" value=\"\">';
				innerHtml=innerHtml+'Data Type';  
				innerHtml=innerHtml+'<select '; 
				innerHtml=innerHtml+'name=\"parameters[' + maxParamRow + '].parameterType\">'; 
				innerHtml=innerHtml+'<option value=\"1\">String</option><option value=\"2\">Date</option><option value=\"3\">Boolean</option><option value=\"4\">Integer</option><option value=\"5\">Long</option><option value=\"6\">Double</option>';
				innerHtml=innerHtml+'value=\"\"></select>';
				innerHtml=innerHtml+'<td>';				
				innerHtml=innerHtml+'';
			newdiv.innerHTML=innerHtml;
			ni.appendChild(newdiv);
			maxParamRow++;
		}
		function removeParameter(paramNum) {
  			var d = document.getElementById('parameterArea');
  			var olddiv = document.getElementById('param'+paramNum);
  			d.removeChild(olddiv);		
		}
	</script>
</head>
<body>
<s:actionerror />
<s:fielderror />

<s:form action="saveJob" method="post" enctype="multipart/form-data"
	validate="true">
	<s:if test="job.pk.jobName != null">
		<s:textfield size="32" label="Job Name" value="%{job.pk.jobName}"
			name="job.pk.jobName" readonly="true">

		</s:textfield>
	</s:if>
	<s:else>
		<s:textfield size="32" label="Job Name" value="%{job.pk.jobName}"
			name="job.pk.jobName">

		</s:textfield>
	</s:else>
	<s:hidden value="%{groupName}" name="job.pk.group.groupName" />

	<s:textfield label="Description" size="128" value="%{job.description}"
		name="job.description">

	</s:textfield>

	<s:textfield label="Output URL" size="128" value="%{job.outputUrl}"
		name="job.outputUrl">

	</s:textfield>

	<s:select label="File Format" name="fileFormat" list="fileFormats"
		listKey="name" listValue="displayName">

	</s:select>

	<s:select label="Select Data Source"
		name="job.datasource.dataSourceName" value="%{job.dataSource}"
		list="dataSources" listKey="dataSourceName" listValue="dataSourceName">
	</s:select>

	<s:textarea label="Report Query" cols="20" rows="20"
		value="%{job.query}" name="job.query">
	</s:textarea>

	<sx:datetimepicker label="Start Date Time" value="%{job.startDate}"
		name="job.startDate" displayFormat="dd-MM-yyyy HH:mm:ss">
	</sx:datetimepicker>

	<sx:datetimepicker label="End Date/Time" value="%{job.endDate}"
		name="job.endDate" displayFormat="dd-MM-yyyy HH:mm:ss">
	</sx:datetimepicker>

	<s:textfield label="Cron String" size="32" value="%{job.cronString}"
		name="job.cronString">
	</s:textfield>

	<s:checkbox label="Is Bursted Report" value="%{job.isBurst}"
		name="job.isBurst">
	</s:checkbox>

	<s:textarea label="Burst Query" cols="20" rows="20"
		value="%{job.burstQuery}" name="job.burstQuery">
	</s:textarea>

	<s:textfield label="File Name from Burst Column" size="32"
		value="%{job.burstFileNameParameterName}"
		name="job.burstFileNameParameterName">
	</s:textfield>

	<tr>
		<td colspan=2>
		<h3>Parameters</h3>
		</td>
	</tr>

	<tr>
		<td><s:a href="#" onClick="addParameter();">Add Parameter</s:a></td>
	</tr>

	<div id="parameterArea"><s:iterator value="job.parameters"
		status="rowstatus">
		<div id="params<s:property value="#rowstatus.index" />">
		<tr>
			<td colspan=2>
			<h3>Parameter Index <s:property value="#rowstatus.index" /></h3>
			</td>
		</tr>
		<s:hidden value="%{pk.parameterIdx}" name="pk.parameterIdx" />
		<s:textfield label="Value"
			name="parameters[%{#rowstatus.index}].parameterValue"
			value="%{parameterValue}">

		</s:textfield> <s:textfield label="Burst Column"
			name="parameters[%{#rowstatus.index}].parameterBurstColumn"
			value="%{parameterBurstColumn}">

		</s:textfield> <s:select label="Data Type"
			name="parameters[%{#rowstatus.index}].parameterType" list="dataTypes"
			listKey="name" listValue="displayName">

		</s:select>
		<td><a href="#"
			onClick="removeParameter(<s:property value="#rowstatus.index" />);">Delete
		Parameter</a></td>
		<td>&nbsp;</td>
		</div>
	</s:iterator></div>



	<s:file label="Jasper Template File" name="job.upload">

	</s:file>
	<s:textarea label="Success/Fail Alert Email Address(es)" cols="20"
		rows="20" value="%{job.alertEmailAddress}"
		name="job.alertEmailAddress">

	</s:textarea>
	<s:textarea label="Distribution Email Address(es)" cols="20" rows="20"
		value="%{job.targetEmailAddress}" name="job.targetEmailAddress">

	</s:textarea>

	<s:submit />
</s:form>
</body>
</html>
