<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head/>


<script language="JavaScript">


window.onload= validateQuery;

function validateQuery(){


   dojo.require("dojo.io.IframeIO");

   var bindArgs = {
        transport: "IframeTransport",
	url: "<s:url value="/validateQuery.action" />",
        mimetype: "text/html",
	formNode: dojo.byId("saveJob"),
        load: function(type, data, evt){
            document.getElementById("queryValidation").innerHTML=data.firstChild.innerHTML;
        }
    };
    var request = dojo.io.bind(bindArgs);
}


</script>

</head>
<body>

<s:form action="saveJob" method="post" enctype="multipart/form-data" validate="true" id="saveJob">	

<sx:tabbedpanel id="job">		

	<sx:div id="report" label="Report">

		<s:actionerror />
		<s:actionmessage/>


		<div class="formGroup">
			<div class="formGroupHeader">Job Details</div>
			<s:if test="job.pk.jobName">
				<s:textfield size="32" label="Job Name" value="%{job.pk.jobName}"
					name="job.pk.jobName" readonly="true" cssClass="readOnly" required="true" cssClass="textbox">

				</s:textfield>
			</s:if><s:else>
				<div id="jobNameTip" class="tipText">Please enter a unique name for this job</div>
				<s:textfield size="32" label="Job Name" value="%{job.pk.jobName}"
					name="job.pk.jobName"
					onfocus="document.getElementById('jobNameTip').style.visibility='visible';" 
					onblur="document.getElementById('jobNameTip').style.visibility='hidden';"
					required="true" cssClass="textbox">
				</s:textfield>
				
			</s:else> 
			
			<s:hidden value="%{groupName}" name="job.pk.group.groupName" /> 
			<div id="jobDescriptionTip" class="tipText">Please enter a short description of this job</div>
			<s:textfield label="Description" size="50" value="%{job.description}"
				name="job.description"
				onfocus="document.getElementById('jobDescriptionTip').style.visibility='visible';" onblur="document.getElementById('jobDescriptionTip').style.visibility='hidden';" cssClass="textbox">

			</s:textfield>

				<s:select label="Select Data Source"
					name="dataSourceName" value="%{job.datasource.dataSourceName}"
					list="dataSources" cssClass="textbox" onchange="validateQuery();">
				</s:select>
		</div>

		<div class="formGroup">
			<div class="formGroupHeader">Definition</div>
			<div id="jobQueryTip" class="tipText">Please input the query to be used for this job.  This should be valid SQL to be used against the datasource selected above.</div>
			<s:textarea label="Report Query" cols="80" rows="20"
				value="%{job.query}" name="itemQuery" 
				onfocus="document.getElementById('jobQueryTip').style.visibility='visible';" 
				onblur="document.getElementById('jobQueryTip').style.visibility='hidden';"
				 required="true" cssClass="textbox" onchange="validateQuery()">
				</s:textarea>

				
				<div id="queryValidation">
				</div>

			<s:checkbox label="Is Bursted Report" value="%{job.isBurst}"
				name="job.isBurst" cssClass="checkbox">
			</s:checkbox>
			<div id="jobBurstQueryTip" class="tipText">Please enter an SQL query to be used to populate parameters to burst the report.  The above check box must be enabled for this query to be used.</div>
			<s:textarea label="Burst Query" cols="80" rows="5"
				value="%{job.burstQuery}" name="job.burstQuery"
				onfocus="document.getElementById('jobBurstQueryTip').style.visibility='visible';" 
				onblur="document.getElementById('jobBurstQueryTip').style.visibility='hidden';" cssClass="textbox">

			</s:textarea>		
		</div>
		<div class="formFooterText">* required field</div>

	</sx:div>
	
	
	<sx:div id="parameters" label="Parameters">	
		
		<div style="margin: 0 auto;"><div id="param_button_add" style="margin:0 auto;"></div></div>
		
				
		<s:iterator value="job.parameters" status="rowstatus">

			<div class="formGroup">
 				<div class="formGroupHeader">Parameter Index <s:property value="%{pk.parameterIdx}" /></div>
				
				<s:hidden value="%{pk.parameterIdx}"
					name="parameters[%{#rowstatus.index}].pk.parameterIdx" />

				<s:textfield
					label="Description" name="parameters[%{#rowstatus.index}].description"
					value="%{description}" cssClass="textbox">
				</s:textfield> 

				<div id="jobValueTip<s:property value="%{#rowstatus.index}"/>" class="tipText">Use this field to hard code the value. </div>
				<s:textfield
					label="Value" name="parameters[%{#rowstatus.index}].parameterValue"
					value="%{parameterValue}"
					onfocus="document.getElementById('jobValueTip%{#rowstatus.index}').style.visibility='visible';" 
					onblur="document.getElementById('jobValueTip%{#rowstatus.index}').style.visibility='hidden';" cssClass="textbox">

				</s:textfield> 
				<div id="jobBurstColTip<s:property value="%{#rowstatus.index}"/>" class="tipText">Entry should match a column in the burst query.</div>
				<s:textfield label="Burst Column"
					name="parameters[%{#rowstatus.index}].parameterBurstColumn"
					value="%{parameterBurstColumn}"	
					onfocus="document.getElementById('jobBurstColTip%{#rowstatus.index}').style.visibility='visible';" 
					onblur="document.getElementById('jobBurstColTip%{#rowstatus.index}').style.visibility='hidden';" cssClass="textbox">

				</s:textfield>

				 <s:select label="Data Type"
					name="parameters[%{#rowstatus.index}].parameterType" list="dataTypes"
					listKey="name" listValue="displayName">

				</s:select> 
				<s:submit name="dispatchSaveButtom" value="Delete Parameter %{pk.parameterIdx}"/>			
			</div>
		</s:iterator>
	</sx:div>

	<sx:div id="schedule" label="Schedule">	
		<div class="formGroup">
			<div class="formGroupHeader">Start and End</div>
			<sx:datetimepicker label="Start Date Time" value="%{job.startDate}"
				name="job.startDate">
			</sx:datetimepicker>

			<sx:datetimepicker label="End Date/Time" value="%{job.endDate}"
				name="job.endDate">
			</sx:datetimepicker>
			
		</div>
		<div class="formGroup">
			<div class="formGroupHeader">Cron Schedule</div>			
			<div id="jobCronTip" class="tipText">Please enter a valid cron string or use the builder below.</div>
			<s:textfield label="Cron String" size="32" value="%{job.cronString}" name="job.cronString"
			onfocus="document.getElementById('jobCronTip').style.visibility='visible';" 
			onblur="document.getElementById('jobCronTip').style.visibility='hidden';" cssClass="textbox">
			</s:textfield>
		</div>
				
		<!--<div class="formGroup">
			<div class="formGroupHeader">Cron Schedule Builder</div>	
					
			<s:checkbox label="All Seconds" name="simpleCron.allSeconds" cssClass="checkbox" value="#{false}">
			</s:checkbox>		
			
			<s:select label="Seconds" name="simpleCron.seconds" multiple="true" list="#{0:0,1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23,24:24,25:25,26:26,27:27,28:28,29:29,30:30,31:31,32:32,33:33,34:34,35:35,36:36,37:37,38:38,39:39,40:40,41:41,42:42,43:43,44:44,45:45,46:46,47:47,48:48,49:49,50:50,51:51,52:52,53:53,54:54,55:55,56:56,57:57,58:58,59:59}"></s:select>
			
			<s:checkbox label="All Minutes" name="simpleCron.allMinutes" cssClass="checkbox" value="#{false}">
			</s:checkbox>		
			
			<s:select label="Minutes" name="simpleCron.minutes" multiple="true" list="#{0:0,1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23,24:24,25:25,26:26,27:27,28:28,29:29,30:30,31:31,32:32,33:33,34:34,35:35,36:36,37:37,38:38,39:39,40:40,41:41,42:42,43:43,44:44,45:45,46:46,47:47,48:48,49:49,50:50,51:51,52:52,53:53,54:54,55:55,56:56,57:57,58:58,59:59}"></s:select>
			
			<s:checkbox label="All Hours" name="simpleCron.allHours" cssClass="checkbox" value="#{false}">
			</s:checkbox>		
			
			
			<s:select label="Hours" name="simpleCron.hours" multiple="true" list="#{0:0,1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23}"></s:select>

			<s:checkbox label="All Days of The Month" name="simpleCron.allDaysMonth" cssClass="checkbox" value="#{true}">
			</s:checkbox>		
			
			
			<s:select label="Days of Month" name="simpleCron.daysOfMonth" multiple="true" list="#{0:0,1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23,24:24,25:25,26:26,27:27,28:28,29:29,30:30,31:31}"></s:select>
		
			<s:checkbox label="All Months" name="simpleCron.allMonths" cssClass="checkbox" value="#{true}">
			</s:checkbox>		
			
			<s:select label="Month" name="simpleCron.months" multiple="true" list='#{1:"Jan",2:"Feb",3:"March",4:"April",5:"May",6:"June",7:"July",8:"Aug",9:"Sept",10:"Oct",11:"Nov",12:"Dec"}'></s:select>

			
			<s:checkbox label="All Days of The Week" name="simpleCron.allDaysWeek" cssClass="checkbox" value="#{true}">
			</s:checkbox>		
			
			<s:select label="Days of Week" name="simpleCron.daysOfWeek" multiple="true" list='#{1:"Monday",2:"Tuesday",3:"Wednesday",4:"Thursday",5:"Friday",6:"Saturday",7:"Sunday"}'></s:select>
			
		</div>-->
			

	</sx:div>
	
	<sx:div id="output" label="Output">	
	
		<div class="formGroup">
		<div class="formGroupHeader">File</div>
			<div id="jobOutputTip" class="tipText">Please enter an output URL (see help).</div>
			<s:textfield label="Output URL" size="50" value="%{job.outputUrl}" name="job.outputUrl"
			onfocus="document.getElementById('jobOutputTip').style.visibility='visible';" 
			onblur="document.getElementById('jobOutputTip').style.visibility='hidden';" cssClass="textbox">			
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
			name="job.templateFileName" readonly="true" cssClass="readOnly" cssClass="textbox"></s:textfield>
			</s:if>	
		
			<s:file label="Template File" name="template"></s:file> 

		</div>
		
		<div class="formGroup">
			<div class="formGroupHeader">Email Distribution</div>				
			<div id="jobEmailTip" class="tipText">Please enter a comma seperated list of email addresses to send output files to.</div>
			<s:textarea label="Distribution Email Address(es)" cols="30" rows="20"
				value="%{job.targetEmailAddress}" name="job.targetEmailAddress"
				onfocus="document.getElementById('jobEmailTip').style.visibility='visible';" 
				onblur="document.getElementById('jobEmailTip').style.visibility='hidden';" cssClass="textbox">							
			</s:textarea>
		</div>

	</sx:div>

	<sx:div id="misc" label="Misc" >	
		<div class="formGroup">
			<div class="formGroupHeader">Alerting</div>				
			<div id="jobAlertEmailTip" class="tipText">Please enter a comma seperated list of email addresses to send alert emails to.</div>
			<s:textarea label="Success/Fail Alert Email Address(es)" cols="30"
			rows="20" value="%{job.alertEmailAddress}"
			name="job.alertEmailAddress"
			onfocus="document.getElementById('jobAlertEmailTip').style.visibility='visible';" 
			onblur="document.getElementById('jobAlertEmailTip').style.visibility='hidden';" cssClass="textbox">				
			</s:textarea>
		</div>
	</sx:div>

	<s:submit name="dispatchSaveButton" value="Save"/>
	
</sx:tabbedpanel>

</s:form>
		
</body>
</html>
