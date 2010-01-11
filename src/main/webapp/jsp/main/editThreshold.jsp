<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head  />
</head>
<body>
<s:form action="saveChart">

<sx:tabbedpanel id="report">
	
		<s:actionerror />
		<s:actionmessage/>
		
		<s:hidden name="threshold.itemId" value="%{threshold.itemId}"/>
		
		<sx:div id="details" label="Details">
			<div class="formGroup">
				<div class="formGroupHeader">Details</div>
			
				<s:textfield label="Item Name" size="64" value="%{threshold.itemName}" name="threshold.itemName" cssClass="textbox" required="true">
				</s:textfield>
				<s:hidden name="threshold.group.groupName" value="%{groupName}"/>
				<s:select label="Select Data Source"
					name="threshold.datasource.dataSourceName" value="%{threshold.datasource}"
					list="runnerDataSources" listKey="dataSourceName" listValue="dataSourceName" cssClass="textbox">
				</s:select>
				
				<s:textarea label="Query" cols="30" rows="20"
					value="%{threshold.alertQuery}" name="threshold.alertQuery" cssClass="textbox" required="true">
				</s:textarea>	
			
				<s:textfield label="Cron String" size="32" value="%{threshold.cronTab}" name="threshold.cronTab" cssClass="textbox" required="true">
				</s:textfield>
				
			</div>	
			<div class="formFooterText">* required field</div>
		</sx:div>
		
		<sx:div id="layout" label="Layout">
			<div class="formGroup">
				<div class="formGroupHeader">Layout</div>
				

				<s:textfield label="Display Column" size="64" value="%{threshold.displayColumn}" name="threshold.displayColumn" cssClass="textbox">
				</s:textfield>
				
				<s:textfield label="Display Row" size="64" value="%{threshold.displayRow}" name="threshold.displayRow" cssClass="textbox">
				</s:textfield>
						
				<s:select label="Width" name="threshold.width" list="widths"
						listKey="name" listValue="displayName" cssClass="textbox"></s:select>
						
				<s:select label="Height" name="threshold.height" list="heights"
						listKey="name" listValue="displayName" cssClass="textbox"></s:select>
					
			</div>
		</sx:div>
		
		<sx:div id="chart" label="Chart Details">
			<div class="formGroup">
				<div class="formGroupHeader">Threshold Configuration</div>
				
			
			
				<s:select label="Threshold Type" name="threshold.type" list="thresholdTypes"
				listKey="name" listValue="displayName"></s:select>
		
			
			 	<s:textfield label="Label Column Name" size="64" value="%{threshold.labelColumn}" name="threshold.labelColumn" cssClass="textbox">
				</s:textfield>
				
					
				<s:textfield label="Value Column Name" size="32" value="%{threshold.valueColumn}" name="threshold.valueColumn" cssClass="textbox">
				</s:textfield>
				
				<s:textfield label="Upper Value" size="32" value="%{threshold.upperValue}" name="threshold.upperValue" cssClass="textbox">
				</s:textfield>
			
				<s:textfield label="Lower Value" size="32" value="%{threshold.lowerValue}" name="threshold.lowerValue" cssClass="textbox">
				</s:textfield>
			</div>		
	</sx:div>
<s:submit value="Save"/>
</sx:tabbedpanel>
</s:form>
</body>
</html>	