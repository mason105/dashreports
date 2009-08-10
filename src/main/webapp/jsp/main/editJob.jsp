<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />

<script id="treescript" language="javascript">
window.onresize=function() {
	window.location=window.location;
}

window.onload=function(){ 
	tree();
	drawTabs();	
}

function drawTabs() {
	
	
	var tb = new Jx.Toolbar();
	var tbButt = new Jx.Toolbar();
	
	var tabSet = new Jx.TabSet('tabContentArea');
	
		
	var tabReport = (new Jx.Button.Tab({
        		image: '<s:url value="/images/icons/report.png"/>',
           		label: 'Report',            
           		content: 'report',            	
       		}));
       		
	var tabParameters = (new Jx.Button.Tab({
        		image: '<s:url value="/images/icons/table_lightning.png"/>',
           		label: 'Parameters',            
           		content: 'parameters',            	
       		}));
       		
	var tabSchedule = (new Jx.Button.Tab({
        		image: '<s:url value="/images/icons/calendar.png"/>',
           		label: 'Schedule',            
           		content: 'schedule',            	
       		}));
       		
	var tabOutput = (new Jx.Button.Tab({
        		image: '<s:url value="/images/icons/folder_page.png"/>',
           		label: 'Output',            
           		content: 'output',            	
       		}));
       		
	var tabMisc = (new Jx.Button.Tab({
        		image: '<s:url value="/images/icons/application.png"/>',
           		label: 'Misc',            
           		content: 'misc',            	
       		}));
       		
       		
    tb.add(tabReport); 
    tabSet.add(tabReport);
    tb.add(tabParameters); 
    tabSet.add(tabParameters);
    tb.add(tabSchedule); 
    tabSet.add(tabSchedule);
    tb.add(tabOutput); 
    tabSet.add(tabOutput);
    tb.add(tabMisc); 
    tabSet.add(tabMisc);
    
    
	var submit=new Jx.Button({label: 'Save',
            	image: '<s:url value="/images/icons/disk.png"/>',
				onClick: function() {
					document.saveJob.submit();
				}
			});
	
   tbButt.add(submit);
   
   tbButt.add(new Jx.Toolbar.Separator());
   
   
   var cancel=new Jx.Button({label: 'Cancel',
			onClick: function() {
				window.location='<s:url value="listJobs.action?groupName=%{groupName}"/>';
			}
		});
   
   tbButt.add(cancel);
   
   new Jx.Toolbar.Container().addTo('toolbar1').add(tbButt);
   new Jx.Toolbar.Container().addTo('toolbar2').add(tb);
   
}
</script>
</head>
<body>

<div id="toolbar1"></div>

<div id="tabContain">
<div id="toolbar2"></div>
<div id="tabContentArea"></div>
</div>
<div id="toolbarButtons"></div>
<div id="formBody" style="position:absolute;top:150px;>
<s:form action="saveJob" method="post" enctype="multipart/form-data"
	validate="true">

	<s:actionerror />
	<s:actionmessage/>
	
	
	
	<div id="report">
		<div class="fromGroup">
		<div class="formGroupHeader">Job Details</div>
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
			name="job.datasource.dataSourceName" value="%{job.datasource}"
			list="dataSources" listKey="dataSourceName" listValue="dataSourceName">
		</s:select> 
		</div>
		
		<div class="fromGroup">
		<div class="formGroupHeader">Definition</div>

		<s:textarea label="Report Query" cols="80" rows="30"
			value="%{job.query}" name="job.query"></s:textarea>

		<s:checkbox label="Is Bursted Report" value="%{job.isBurst}"
			name="job.isBurst">
		</s:checkbox>

		<s:textarea label="Burst Query" cols="80" rows="5"
			value="%{job.burstQuery}" name="job.burstQuery">
		</s:textarea>		
		</div>

	</div>
	
	<div id="parameters">	
		<s:submit name="dispatchSaveButton" value="Add Parameter" align="none" />
		<s:iterator value="job.parameters" status="rowstatus">

			<div class="formGroup">
 				<div class="formGroupHeader">Parameter Index <s:property
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
					value="Delete Parameter %{pk.parameterIdx}" />
			</div>
		</s:iterator>
	</div>

	<div id="schedule">	
		<div class="formGroup">
			<div class="formGroupHeader">Schedule Details</div>
			<sx:datetimepicker label="Start Date Time" value="%{job.startDate}"
				name="job.startDate">
			</sx:datetimepicker>

			<!-- temp hack-->
			<br/>

			<sx:datetimepicker label="End Date/Time" value="%{job.endDate}"
				name="job.endDate">
			</sx:datetimepicker>

			<s:textfield label="Cron String" size="32" value="%{job.cronString}" name="job.cronString">
			</s:textfield>
		</div>
	</div>
	
	<div id="output">	
	
		<div class="formGroup">
		<div class="formGroupHeader">File</div>
		
			<s:textfield label="Output URL" size="50" value="%{job.outputUrl}"
					name="job.outputUrl">
			</s:textfield>
		</div>
		<div class="formGroup">
		<div class="formGroupHeader">Template</div>				
		
			<s:select label="File Format" name="job.fileFormat" list="fileFormats"
			listKey="name" listValue="displayName"></s:select>

		
			<s:select label="Template Type" name="job.templateType" list="templateTypes"
			listKey="name" listValue="displayName"></s:select>
			
			<s:if test="job.templateFileName!=null">
			<s:textfield size="40" label="Template File Name" value="%{job.templateFileName}"
			name="job.templateFileName" readonly="true" cssClass="readOnly">		</s:textfield>
			</s:if>	
		
			<s:file label="Template File" name="template"></s:file> 

		</div>
		
		<div class="formGroup">
			<div class="formGroupHeader">Email Distribution</div>				
		
			<s:textarea label="Distribution Email Address(es)" cols="30" rows="20"
				value="%{job.targetEmailAddress}" name="job.targetEmailAddress">
			</s:textarea>
		</div>

	</div>

	<div id="misc" >	
		<div class="formGroup">
			<div class="formGroupHeader">Alerting</div>				
			
			<s:textarea label="Success/Fail Alert Email Address(es)" cols="30"
			rows="20" value="%{job.alertEmailAddress}"
			name="job.alertEmailAddress">
			</s:textarea>
		</div>
	</div>

	
</s:form>
</div>

</body>
</html>
