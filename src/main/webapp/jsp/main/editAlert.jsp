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
<span class="pageTitle"><img src="<s:url value='/images/icons/chart_bar.png'/>" align="absmiddle" />Edit Dasboard Alert</span>

<s:form action="saveAlert">
	<s:actionerror />
	<s:actionmessage/>
	
	<s:hidden name="dashboardAlert.id" value="%{dashboardAlert.id}"/>
	
	<s:textfield label="Name/Title" size="64" value="%{dashboardAlert.alertName}" name="dashboardAlert.alertName">
	</s:textfield>
	
	<s:select label="Select Group"
		name="dashboardAlert.group.groupName" value="%{dashboardAlert.group.groupName}"
		list="groups" listKey="groupName" listValue="groupName">
	</s:select>
	
	<s:textfield label="Display Column" size="64" value="%{dashboardAlert.displayColumn}" name="dashboardAlert.displayColumn">
	</s:textfield>
	
	<s:textfield label="Display Row" size="64" value="%{dashboardAlert.displayRow}" name="dashboardAlert.displayRow">
	</s:textfield>
	
	
	<s:select label="Display Type" name="dashboardAlert.displayType" list="displayTypes"
			listKey="name" listValue="displayName"></s:select>
			
	<s:select label="Width" name="dashboardAlert.width" list="widths"
			listKey="name" listValue="displayName"></s:select>
			
	<s:select label="Height" name="dashboardAlert.height" list="heights"
			listKey="name" listValue="displayName"></s:select>
			
	<s:select label="Chart Type" name="dashboardAlert.chartType" list="chartTypes"
			listKey="name" listValue="displayName"></s:select>


	<s:select label="X Axis Step Size" name="dashboardAlert.xAxisStep" list="xAxisSteps"
			listKey="name" listValue="displayName"></s:select>

	<s:select label="Select Data Source"
		name="dashboardAlert.datasource.dataSourceName" value="%{dashboardAlert.datasource}"
		list="runnerDataSources" listKey="dataSourceName" listValue="dataSourceName">
	</s:select>
	
	<s:textarea label="Query" cols="30" rows="20"
		value="%{dashboardAlert.alertQuery}" name="dashboardAlert.alertQuery">
	</s:textarea>	
	
 <s:textfield label="Y-Axis Label" size="64" value="%{dashboardAlert.yLabel}" name="dashboardAlert.yLabel">
	</s:textfield>
	
	<s:textfield label="X-Axis Column Name" size="32" value="%{dashboardAlert.xaxisColumn}" name="dashboardAlert.xaxisColumn">
	</s:textfield>
	
	
	<s:textfield label="Value Column Name" size="32" value="%{dashboardAlert.valueColumn}" name="dashboardAlert.valueColumn">
	</s:textfield>
	
	<s:textfield label="Series Name Column Name" size="32" value="%{dashboardAlert.seriesNameColumn}" name="dashboardAlert.seriesNameColumn">
	</s:textfield>
	
	<s:textfield label="Cron String" size="32" value="%{dashboardAlert.cronTab}" name="dashboardAlert.cronTab">
	</s:textfield>
	
	<s:submit />
</s:form>

</body>
</html>
	