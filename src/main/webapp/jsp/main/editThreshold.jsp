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
		
		<s:hidden name="item.itemId" value="%{item.itemId}"/>
		
		<sx:div id="details" label="Details">
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
			
				<s:textfield label="Cron String" size="32" value="%{item.cronTab}" name="item.cronTab" cssClass="textbox" required="true">
				</s:textfield>
				
			</div>	
			<div class="formFooterText">* required field</div>
		</sx:div>
		
		<sx:div id="layout" label="Layout">
			<div class="formGroup">
				<div class="formGroupHeader">Layout</div>
				

				<s:textfield label="Display Column" size="64" value="%{item.displayColumn}" name="item.displayColumn" cssClass="textbox">
				</s:textfield>
				
				<s:textfield label="Display Row" size="64" value="%{item.displayRow}" name="item.displayRow" cssClass="textbox">
				</s:textfield>
						
				<s:select label="Width" name="item.width" list="widths"
						listKey="name" listValue="displayName" cssClass="textbox"></s:select>
						
				<s:select label="Height" name="item.height" list="heights"
						listKey="name" listValue="displayName" cssClass="textbox"></s:select>
					
			</div>
		</sx:div>
		
		<sx:div id="chart" label="Chart Details">
			<div class="formGroup">
				<div class="formGroupHeader">Threshold Configuration</div>
				
			
			
				<s:select label="Threshold Type" name="item.type" list="thresholdTypes"
				listKey="name" listValue="displayName"></s:select>
		
			
			 	<s:textfield label="Label Column Name" size="64" value="%{item.labelColumn}" name="item.labelColumn" cssClass="textbox">
				</s:textfield>
				
					
				<s:textfield label="Value Column Name" size="32" value="%{item.valueColumn}" name="item.valueColumn" cssClass="textbox">
				</s:textfield>
				
				<s:textfield label="Upper Value" size="32" value="%{item.upperValue}" name="item.upperValue" cssClass="textbox">
				</s:textfield>
			
				<s:textfield label="Lower Value" size="32" value="%{item.lowerValue}" name="item.lowerValue" cssClass="textbox">
				</s:textfield>
			</div>		
	</sx:div>
<s:submit value="Save"/>
</sx:tabbedpanel>
</s:form>
</body>
</html>	