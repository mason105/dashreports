<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
    <sx:head/>
 	<sj:head locale="en" jqueryui="true" jquerytheme="smoothness"/>
</head>
<body>
<div id="jobEditForm">
<s:form  action="editJob" method="post" enctype="multipart/form-data" validate="true" id="editJob">	

<sj:tabbedpanel id="job"  animate="true" collapsible="true" useSelectedTabCookie="true">						
	<sj:tab id="report" label="Report" target="reportDiv"/>
	
	<div id="reportDiv">
	
		<s:actionerror />
		<s:actionmessage/>


		<div class="formGroup">
			<div class="formGroupHeader">Job Details</div>
			<s:if test="job.pk.jobName">				
				<s:textfield size="32" label="Job Name" value="%{job.pk.jobName}"
					name="job.pk.jobName" readonly="true" cssClass="readOnly, textBox" required="true" >
				</s:textfield>
			</s:if><s:else>				
				<s:textfield size="32" label="Job Name" value="%{job.pk.jobName}"
					name="job.pk.jobName"								
					required="true" cssClass="textbox">
				</s:textfield>
				
			</s:else> 
					<s:hidden value="%{groupName}" name="groupName" />
			<s:hidden value="%{groupName}" name="job.pk.group.groupName" /> 
			<s:textfield label="Description" size="50" value="%{job.description}"
				name="job.description"
				cssClass="textbox">

			</s:textfield>
				<s:select label="Select Data Source"
					name="dsName" 
					list="dataSources" cssClass="textbox" >
				</s:select>

		
		</div>

		<div class="formGroup">
			<div class="formGroupHeader">Definition</div>
			<s:textarea label="Report Query" cols="80" rows="20"
				value="%{job.query}" name="job.query" 
  			    required="true" cssClass="textbox" onchange="validateJobQuery()">
				</s:textarea>
			
			<div id="jobQueryValidation">
			</div>

			<s:checkbox label="Is Bursted Report" value="%{job.isBurst}"
				name="job.isBurst" cssClass="checkbox"   onchange="validateBurstQuery()">
			</s:checkbox>
		
			<s:textarea label="Burst Query" cols="80" rows="5"
				value="%{job.burstQuery}" name="job.burstQuery"
				cssClass="textbox"  onchange="validateBurstQuery()">

			</s:textarea>

			<div id="burstQueryValidation">
			</div>
	
		</div>
		<div class="formBottomEmpty"></div>

	</div>
	
		
	<sj:tab id="parameters" label="Parameters" target="parametersDiv"/>
	<div id="parametersDiv">	
			<s:iterator value="job.parameters" status="rowstatus">

			<div class="formGroup">
 				<div class="formGroupHeader">Parameter Index <s:property value="%{#rowstatus.index}" /></div>
				
				<s:hidden value="%{#rowstatus.index}"
					name="parameters[%{#rowstatus.index}].pk.parameterIdx" />
				
				<s:textfield
					label="Description" name="parameters[%{#rowstatus.index}].description"
					value="%{description}" cssClass="textbox">
				</s:textfield> 

			
				<s:textfield
					label="Value" name="parameters[%{#rowstatus.index}].parameterValue"
					value="%{parameterValue}"
					onfocus="document.getElementById('jobValueTip%{#rowstatus.index}').style.visibility='visible';" 
					onblur="document.getElementById('jobValueTip%{#rowstatus.index}').style.visibility='hidden';" cssClass="textbox">

				</s:textfield> 
			
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
				<input type="radio" name="parameterId" value="<s:property value="%{#rowstatus.index}"/>" checked="unchecked"/>	Select				
			</div>
		</s:iterator>
		<s:if test="job.parameters.size>0">
			<div class="formBottomEmpty"></div>
		</s:if>		
		<s:submit name="addParameter" value="Add Parameter" align="left" cssStyle="float:left;"/>
		<s:submit name="deleteParameters" value="Delete Parameter" align="left"/>			
	</div>

	<sj:tab id="schedule" label="Schedule" target="scheduleDiv"/>
	<div id="scheduleDiv">
		<div class="formGroup">
			<div class="formGroupHeader">Start and End</div>
			<sj:datepicker label="Start Date Time" value="%{job.startDate}"
				name="job.startDate" showAnim="slideDown"  displayFormat="dd/mm/yy">
			</sj:datepicker>
			<sj:datepicker label="End Date/Time" value="%{job.endDate}"
				name="job.endDate" showAnim="slideDown" displayFormat="dd/mm/yy">
			</sj:datepicker>			
		</div>
		
		<!-- <div class="formGroup">
			<div class="formGroupHeader">Cron Schedule</div>						
			<s:textfield label="Cron String" size="32" value="%{job.cronString}" name="job.cronString" cssClass="textbox">
			</s:textfield>
		</div> -->
				
		<div class="formGroup">
			<div class="formGroupHeader">Cron Schedule Builder</div>	
					
			<s:checkbox label="All Seconds" name="simpleCron.allSeconds" cssClass="checkbox">
			</s:checkbox>		
			
			<s:select label="Seconds" 
			onClick="document.getElementById('editJob_simpleCron_allSeconds').checked=false;"
			name="simpleCron.seconds" multiple="true" list="#{0:0,1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23,24:24,25:25,26:26,27:27,28:28,29:29,30:30,31:31,32:32,33:33,34:34,35:35,36:36,37:37,38:38,39:39,40:40,41:41,42:42,43:43,44:44,45:45,46:46,47:47,48:48,49:49,50:50,51:51,52:52,53:53,54:54,55:55,56:56,57:57,58:58,59:59}"></s:select>
			
			<s:checkbox label="All Minutes" name="simpleCron.allMinutes" cssClass="checkbox">
			</s:checkbox>		
			
			<s:select label="Minutes" 
			onClick="document.getElementById('editJob_simpleCron_allMinutes').checked=false;"
			name="simpleCron.minutes" multiple="true" list="#{0:0,1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23,24:24,25:25,26:26,27:27,28:28,29:29,30:30,31:31,32:32,33:33,34:34,35:35,36:36,37:37,38:38,39:39,40:40,41:41,42:42,43:43,44:44,45:45,46:46,47:47,48:48,49:49,50:50,51:51,52:52,53:53,54:54,55:55,56:56,57:57,58:58,59:59}"></s:select>
			
			<s:checkbox label="All Hours" name="simpleCron.allHours" cssClass="checkbox">
			</s:checkbox>		
			
			
			<s:select label="Hours" 
			onClick="document.getElementById('editJob_simpleCron_allHours').checked=false;"
			name="simpleCron.hours" multiple="true" list="#{0:0,1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23}"></s:select>

			<s:checkbox label="All Days of The Month" name="simpleCron.allDaysOfMonth" cssClass="checkbox">
			</s:checkbox>		
			
			
			<s:select label="Days of Month" 
			onClick="document.getElementById('editJob_simpleCron_allDaysOfMonth').checked=false;"
			name="simpleCron.daysOfMonth" multiple="true" list="#{0:0,1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23,24:24,25:25,26:26,27:27,28:28,29:29,30:30,31:31}"></s:select>
		
			<s:checkbox label="All Months" name="simpleCron.allMonths" cssClass="checkbox">
			</s:checkbox>		
			
			<s:select label="Month" 
			onClick="document.getElementById('editJob_simpleCron_allMonths').checked=false;"
			name="simpleCron.months" multiple="true" list='#{1:"Jan",2:"Feb",3:"March",4:"April",5:"May",6:"June",7:"July",8:"Aug",9:"Sept",10:"Oct",11:"Nov",12:"Dec"}'></s:select>

			
			<s:checkbox label="All Days of The Week" name="simpleCron.allDaysOfWeek" cssClass="checkbox">
			</s:checkbox>		
			
			<s:select label="Days of Week" 
			onClick="document.getElementById('editJob_simpleCron_allDaysOfWeek').checked=false;"
			name="simpleCron.daysOfWeek" multiple="true" list='#{1:"Monday",2:"Tuesday",3:"Wednesday",4:"Thursday",5:"Friday",6:"Saturday",7:"Sunday"}'></s:select>
			
		</div>
			
		<div class="formBottomEmpty"></div>
	</div>
	
	<sj:tab id="output" label="Output" target="outputDiv"/>
	<div id="outputDiv">	
		<div class="formGroup">
		<div class="formGroupHeader">File</div>		
			<s:textfield label="Output URL" size="50" value="%{job.outputUrl}" name="job.outputUrl" cssClass="textbox">			
			</s:textfield>
			<s:select label="File Format" name="job.fileFormat" list="fileFormats"
			listKey="name" listValue="displayName"></s:select>
		</div>
		<div class="formGroup">
			<div class="formGroupHeader">Template</div>				
			<s:select label="Template Type" name="job.templateType" list="templateTypes"
			listKey="name" listValue="displayName"></s:select>
			
			<s:if test="job.templateFileName!=null">
			<s:textfield size="40" label="Template File Name" value="%{job.templateFileName}"
			name="job.templateFileName" readonly="true" cssClass="readOnly,textbox"></s:textfield>
			</s:if>	
		
			<s:file label="Template File" name="template"></s:file> 

		</div>
		
		<div class="formGroup">
			<div class="formGroupHeader">Email Distribution</div>				
			<s:textarea label="Distribution Email Address(es)" cols="30" rows="20"
				value="%{job.targetEmailAddress}" name="job.targetEmailAddress" cssClass="textbox">							
			</s:textarea>
		</div>
		<div class="formBottomEmpty"></div>
	</div>
	<sj:tab id="miscstuff" label="Misc" target="miscDiv"/>
	<div id="miscDiv">	
		<div class="formGroup">
			<div class="formGroupHeader">Alerting</div>							
			<s:textarea label="Success/Fail Alert Email Address(es)" cols="30"
			rows="20" value="%{job.alertEmailAddress}"
			name="job.alertEmailAddress"
			cssClass="textbox">				
			</s:textarea>
		</div>
		<div class="formBottomEmpty"></div>
	</div>
	<div class="formBottom">
	<div class="formFooterText">* required field</div>
<s:submit name="saveJob" value="Save" align="left" cssStyle="margin-left:15px"/>
</div>	
</sj:tabbedpanel>



</s:form>
</div>		
</body>
</html>
