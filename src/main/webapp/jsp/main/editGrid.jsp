<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
    <sx:head/>
 	<sj:head locale="en" jqueryui="true" jquerytheme="%{themeName}"/>
</head>
<body>
<div id="jobEditForm">
<s:form action="saveGrid">
	<div class="jobHeader"><img src="<s:url value='/images/v2/nav/groupsblue.png'/>" align="absmiddle" />&nbsp;<s:a href="showGroup.action?groupName=%{groupName}"><s:property value="groupName"/></s:a> > Edit Grid</div>
<sj:tabbedpanel id="report" animate="true"  useSelectedTabCookie="true">	
	
		
		
		<s:hidden name="item.itemId" value="%{item.itemId}"/>
		<sj:tab id="detailsTab" target="detailsDiv" label="Details"/>
		<div id="detailsDiv" >
			<div class="formGroup">
				<div class="formGroupHeader">Details</div>
			
				<s:textfield label="Item Name" size="64" value="%{item.itemName}" name="item.itemName" cssClass="textbox" required="true">
				</s:textfield>
				<s:hidden name="item.group.groupName" value="%{groupName}"/>
				<s:select label="Select Data Source"
					name="dataSourceName" value="%{item.datasource.dataSourceName}"
					list="runnerDataSources" cssClass="textbox">
				</s:select>
				
				<s:textarea label="Query" cols="30" rows="20"
					value="%{item.alertQuery}" name="itemQuery" cssClass="textbox" required="true" onchange="dojo.event.topic.publish('refresh_fields');">
				</s:textarea>	
			
				<s:url id="validateUrl" action="validateItemQuery" /> 
				<sx:div showLoadingText="true" loadingText="Testing query..." id="validateQuery" href="%{validateUrl}" theme="ajax"  listenTopics="refresh_fields" formId="saveGrid">
				</sx:div>
				
			</div>	
			<div class="formBottomEmpty"></div>
		</div>
		<sj:tab id="scheduleTab" target="scheduleDiv" label="Schedule"/>
		<div id="scheduleDiv" >
		
			<div class="formGroup">
				<div class="formGroupHeader">Schedule</div>	
						
				<s:checkbox label="All Seconds" name="simpleCron.allSeconds" cssClass="checkbox">
				</s:checkbox>		
				
				<s:select label="Seconds" 
				onclick="document.getElementById('editJob_simpleCron_allSeconds').checked=false;"
				name="simpleCron.seconds" multiple="true" list="#{0:0,1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23,24:24,25:25,26:26,27:27,28:28,29:29,30:30,31:31,32:32,33:33,34:34,35:35,36:36,37:37,38:38,39:39,40:40,41:41,42:42,43:43,44:44,45:45,46:46,47:47,48:48,49:49,50:50,51:51,52:52,53:53,54:54,55:55,56:56,57:57,58:58,59:59}"></s:select>
				
				<s:checkbox label="All Minutes" name="simpleCron.allMinutes" cssClass="checkbox">
				</s:checkbox>		
				
				<s:select label="Minutes" 
				onclick="document.getElementById('editJob_simpleCron_allMinutes').checked=false;"
				name="simpleCron.minutes" multiple="true" list="#{0:0,1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23,24:24,25:25,26:26,27:27,28:28,29:29,30:30,31:31,32:32,33:33,34:34,35:35,36:36,37:37,38:38,39:39,40:40,41:41,42:42,43:43,44:44,45:45,46:46,47:47,48:48,49:49,50:50,51:51,52:52,53:53,54:54,55:55,56:56,57:57,58:58,59:59}"></s:select>
				
				<s:checkbox label="All Hours" name="simpleCron.allHours" cssClass="checkbox">
				</s:checkbox>		
				
				
				<s:select label="Hours" 
				onclick="document.getElementById('editJob_simpleCron_allHours').checked=false;"
				name="simpleCron.hours" multiple="true" list="#{0:0,1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23}"></s:select>
	
				<s:checkbox label="All Days of The Month" name="simpleCron.allDaysOfMonth" cssClass="checkbox">
				</s:checkbox>		
				
				
				<s:select label="Days of Month" 
				onclick="document.getElementById('editJob_simpleCron_allDaysOfMonth').checked=false;"
				name="simpleCron.daysOfMonth" multiple="true" list="#{0:0,1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23,24:24,25:25,26:26,27:27,28:28,29:29,30:30,31:31}"></s:select>
			
				<s:checkbox label="All Months" name="simpleCron.allMonths" cssClass="checkbox">
				</s:checkbox>		
				
				<s:select label="Month" 
				onclick="document.getElementById('editJob_simpleCron_allMonths').checked=false;"
				name="simpleCron.months" multiple="true" list='#{1:"Jan",2:"Feb",3:"March",4:"April",5:"May",6:"June",7:"July",8:"Aug",9:"Sept",10:"Oct",11:"Nov",12:"Dec"}'></s:select>
	
				
				<s:checkbox label="All Days of The Week" name="simpleCron.allDaysOfWeek" cssClass="checkbox">
				</s:checkbox>		
				
				<s:select label="Days of Week" 
				onclick="document.getElementById('editJob_simpleCron_allDaysOfWeek').checked=false;"
				name="simpleCron.daysOfWeek" multiple="true" list='#{1:"Monday",2:"Tuesday",3:"Wednesday",4:"Thursday",5:"Friday",6:"Saturday",7:"Sunday"}'></s:select>
				
			</div>
				
			<div class="formBottomEmpty"></div>
		
		</div>		
	<sj:tab id="layoutTab" target="layoutDiv" label="Layout"/>
		<div id="layoutDiv" >
			<div class="formGroup">
				<div class="formGroupHeader">Layout</div>
				<div style="float:left;padding-right:10px;">
				 <sj:spinner 
			    	name="item.displayColumn" 
			    	id="item.displayColumn" 
			    	min="1" 
			    	max="50" 
			    	step="1" 
			    	value="%{item.displayColumn}"
			    	label="Display Column"
			    	
			    	/>
</div>
<div style="float:left;padding-right:10px;">
		 <sj:spinner 
			    	name="item.displayRow" 
			    	id="item.displayRow" 
			    	min="1" 
			    	max="50" 
			    	step="1" 
			    	value="%{item.displayRow}"
			    	label="Display Row"
			    	
			    	/>
	</div>				
			<div>		
				<sj:spinner 
			    	name="item.rowsToDisplay" 
			    	id="item.rowsToDisplay" 
			    	min="0" 
			    	max="10000" 
			    	step="10" 
			    	value="%{item.rowsToDisplay}"
			    	label="Rows to Display (0=all)"
			    	
			    />	
					</div>
				<s:select label="Width" name="item.width" list="widths"
						listKey="name" listValue="displayName" cssClass="textbox"></s:select>
						
				<s:select label="Height" name="item.height" list="heights"
						listKey="name" listValue="displayName" cssClass="textbox"></s:select>
					
			</div>
				<div class="formBottomEmpty"></div>
		</div>
		
	<div class="formBottom">
	<div class="formFooterText">* required field
	
	<s:actionerror  theme="jquery" />
		<s:actionmessage  theme="jquery"/>
		</div>
	<s:submit value="Save" align="left" /></div>
	
</sj:tabbedpanel>
</s:form>
</div>
</body>
</html>
	