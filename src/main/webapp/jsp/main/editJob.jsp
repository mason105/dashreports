<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>


<script language="javascript">
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
					document.saveJob.dispatchSaveButton.value='save';
					document.saveJob.submit();
				}
			});
	
   tbButt.add(new Jx.Toolbar.Separator());
   
   tbButt.add(submit);
   
   tbButt.add(new Jx.Toolbar.Separator());
   
   
   var cancel=new Jx.Button({label: 'Cancel',
			onClick: function() {
				window.location='<s:url value="listJobs.action?groupName=%{groupName}"/>';
			}
		});
   
   tbButt.add(cancel);
   
   var tbc=new Jx.Toolbar.Container().addTo('toolbar1');
   tbc.add(tb);
   tbc.add(tbButt);
   
   
}



</script>
<sx:head />
</head>
<body>

<div id="toolbar1"></div>

<div id="tabContain">
</div>
<div id="toolbarButtons"></div>



<s:form action="saveJob" method="post" enctype="multipart/form-data" validate="true">	

<div id="tabContentArea"></div>
<div id="formBody" style="position:absolute;top:150px;">

	<s:actionerror />
	<s:actionmessage/>
	
		
	<div id="report">
		<div class="formGroup">
			<div class="formGroupHeader">Job Details</div>
			<s:if test="job.pk.jobName != null">
				<s:textfield size="32" label="Job Name" value="%{job.pk.jobName}"
					name="job.pk.jobName" readonly="true" cssClass="readOnly">

				</s:textfield>
			</s:if><s:else>
				<div id="jobNameTip" class="tipText">Please enter a unique name for this job</div>
				<s:textfield size="32" label="Job Name *" value="%{job.pk.jobName}"
					name="job.pk.jobName"
					onfocus="document.getElementById('jobNameTip').style.visibility='visible';" onblur="document.getElementById('jobNameTip').style.visibility='hidden';">
				</s:textfield>
				
			</s:else> 
			
			<s:hidden value="%{groupName}" name="job.pk.group.groupName" /> 
			<div id="jobDescriptionTip" class="tipText">Please enter a short description of this job</div>
			<s:textfield label="Description" size="50" value="%{job.description}"
				name="job.description"
				onfocus="document.getElementById('jobDescriptionTip').style.visibility='visible';" onblur="document.getElementById('jobDescriptionTip').style.visibility='hidden';">

			</s:textfield>

			 <s:select label="Select Data Source"
				name="job.datasource.dataSourceName" value="%{job.datasource}"
				list="dataSources" listKey="dataSourceName" listValue="dataSourceName">
			</s:select> 
		</div>

		<div class="formGroup">
			<div class="formGroupHeader">Definition</div>
			<div id="jobQueryTip" class="tipText">Please input the query to be used for this job.  This should be valid SQL to be used against the datasource selected above.</div>
			<s:textarea label="Report Query *" cols="80" rows="20"
				value="%{job.query}" name="job.query" 
				onfocus="document.getElementById('jobQueryTip').style.visibility='visible';" 
				onblur="document.getElementById('jobQueryTip').style.visibility='hidden';">
				</s:textarea>

			<s:checkbox label="Is Bursted Report" value="%{job.isBurst}"
				name="job.isBurst">
			</s:checkbox>
			<div id="jobBurstQueryTip" class="tipText">Please enter an SQL query to be used to populate parameters to burst the report.  The above check box must be enabled for this query to be used.</div>
			<s:textarea label="Burst Query" cols="80" rows="5"
				value="%{job.burstQuery}" name="job.burstQuery"
				onfocus="document.getElementById('jobBurstQueryTip').style.visibility='visible';" 
				onblur="document.getElementById('jobBurstQueryTip').style.visibility='hidden';">

			</s:textarea>		
		</div>
		<div class="formFooterText">* required field</div>

	</div>
	
	
	<div id="parameters">	
		
		<div style="margin: 0 auto;"><div id="param_button_add" style="margin:0 auto;"></div></div>
		
		<script language="javascript">
			new Jx.Button({label: 'Add Parameter',
				onClick: function() {
					document.saveJob.dispatchSaveButton.value='Add Parameter';
					document.saveJob.submit();
				}
			}).addTo('param_button_add');
		</script>
		
				
		<s:iterator value="job.parameters" status="rowstatus">

			<div class="formGroup">
 				<div class="formGroupHeader">Parameter Index <s:property value="%{pk.parameterIdx}" /></div>
				
				<s:hidden value="%{pk.parameterIdx}"
					name="parameters[%{#rowstatus.index}].pk.parameterIdx" />

				<s:textfield
					label="Description" name="parameters[%{#rowstatus.index}].description"
					value="%{description}">
				</s:textfield> 

				<div id="jobValueTip<s:property value="%{#rowstatus.index}"/>" class="tipText">Use this field to hard code the value. </div>
				<s:textfield
					label="Value" name="parameters[%{#rowstatus.index}].parameterValue"
					value="%{parameterValue}"
					onfocus="document.getElementById('jobValueTip%{#rowstatus.index}').style.visibility='visible';" 
					onblur="document.getElementById('jobValueTip%{#rowstatus.index}').style.visibility='hidden';">

				</s:textfield> 
				<div id="jobBurstColTip<s:property value="%{#rowstatus.index}"/>" class="tipText">Entry should match a column in the burst query.</div>
				<s:textfield label="Burst Column"
					name="parameters[%{#rowstatus.index}].parameterBurstColumn"
					value="%{parameterBurstColumn}"	
					onfocus="document.getElementById('jobBurstColTip%{#rowstatus.index}').style.visibility='visible';" 
					onblur="document.getElementById('jobBurstColTip%{#rowstatus.index}').style.visibility='hidden';">

				</s:textfield>

				 <s:select label="Data Type"
					name="parameters[%{#rowstatus.index}].parameterType" list="dataTypes"
					listKey="name" listValue="displayName">

				</s:select> 
				<div id="param_button_<s:property value="%{pk.parameterIdx}"/>"></div>
				<script language="javascript">
					new Jx.Button({label: 'Delete Parameter <s:property value="%{pk.parameterIdx}"/>',
						onClick: function() {
							document.saveJob.dispatchSaveButton.value='Delete Parameter <s:property value="%{pk.parameterIdx}"/>';
							document.saveJob.submit();	
						}
					}).addTo('param_button_<s:property value="%{pk.parameterIdx}"/>');
				</script>
				
			</div>
		</s:iterator>
	</div>

	<div id="schedule">	
		<div class="formGroup">
			<div class="formGroupHeader">Schedule Details</div>
			<sx:datetimepicker label="Start Date Time" value="%{job.startDate}"
				name="job.startDate">
			</sx:datetimepicker>

			<sx:datetimepicker label="End Date/Time" value="%{job.endDate}"
				name="job.endDate">
			</sx:datetimepicker>
			<div id="jobCronTip" class="tipText">Please enter a valid cron string (see help).</div>
			<s:textfield label="Cron String" size="32" value="%{job.cronString}" name="job.cronString"
			onfocus="document.getElementById('jobCronTip').style.visibility='visible';" 
			onblur="document.getElementById('jobCronTip').style.visibility='hidden';">
			</s:textfield>
		</div>
	</div>
	
	<div id="output">	
	
		<div class="formGroup">
		<div class="formGroupHeader">File</div>
			<div id="jobOutputTip" class="tipText">Please enter an output URL (see help).</div>
			<s:textfield label="Output URL" size="50" value="%{job.outputUrl}" name="job.outputUrl"
			onfocus="document.getElementById('jobOutputTip').style.visibility='visible';" 
			onblur="document.getElementById('jobOutputTip').style.visibility='hidden';">			
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
			name="job.templateFileName" readonly="true" cssClass="readOnly"></s:textfield>
			</s:if>	
		
			<s:file label="Template File" name="template"></s:file> 

		</div>
		
		<div class="formGroup">
			<div class="formGroupHeader">Email Distribution</div>				
			<div id="jobEmailTip" class="tipText">Please enter a comma seperated list of email addresses to send output files to.</div>
			<s:textarea label="Distribution Email Address(es)" cols="30" rows="20"
				value="%{job.targetEmailAddress}" name="job.targetEmailAddress"
				onfocus="document.getElementById('jobEmailTip').style.visibility='visible';" 
				onblur="document.getElementById('jobEmailTip').style.visibility='hidden';">							
			</s:textarea>
		</div>

	</div>

	<div id="misc" >	
		<div class="formGroup">
			<div class="formGroupHeader">Alerting</div>				
			<div id="jobAlertEmailTip" class="tipText">Please enter a comma seperated list of email addresses to send alert emails to.</div>
			<s:textarea label="Success/Fail Alert Email Address(es)" cols="30"
			rows="20" value="%{job.alertEmailAddress}"
			name="job.alertEmailAddress"
			onfocus="document.getElementById('jobAlertEmailTip').style.visibility='visible';" 
			onblur="document.getElementById('jobAlertEmailTip').style.visibility='hidden';">				
			</s:textarea>
		</div>
	</div>

	<s:hidden name="dispatchSaveButton" value=""/>
</div>	

</s:form>
		


</body>
</html>
