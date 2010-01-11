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
		
		<s:hidden name="grid.itemId" value="%{grid.itemId}"/>
		
		<sx:div id="details" label="Details">
			<div class="formGroup">
				<div class="formGroupHeader">Details</div>
			
				<s:textfield label="Item Name" size="64" value="%{grid.itemName}" name="grid.itemName" cssClass="textbox" required="true">
				</s:textfield>
				<s:hidden name="grid.group.groupName" value="%{groupName}"/>
				<s:select label="Select Data Source"
					name="dashboardAlert.datasource.dataSourceName" value="%{grid.datasource}"
					list="runnerDataSources" listKey="dataSourceName" listValue="dataSourceName" cssClass="textbox">
				</s:select>
				
				<s:textarea label="Query" cols="30" rows="20"
					value="%{grid.alertQuery}" name="grid.alertQuery" cssClass="textbox" required="true">
				</s:textarea>	
			
				<s:textfield label="Cron String" size="32" value="%{grid.cronTab}" name="grid.cronTab" cssClass="textbox" required="true">
				</s:textfield>
				
			</div>	
			<div class="formFooterText">* required field</div>
		</sx:div>
		
		<sx:div id="layout" label="Layout">
			<div class="formGroup">
				<div class="formGroupHeader">Layout</div>
		<s:textfield label="Display Column" size="10" value="%{grid.displayColumn}" name="grid.displayColumn" cssClass="textbox">
				</s:textfield>
				
				<s:textfield label="Display Row" size="10" value="%{grid.displayRow}" name="grid.displayRow" cssClass="textbox">
				</s:textfield>
						
				<s:textfield label="Max Rows" size="19" value="%{grid.rowsToDisplay}" name="grid.rowsToDisplay" cssClass="textbox">
				</s:textfield>
						
						
				<s:select label="Width" name="grid.width" list="widths"
						listKey="name" listValue="displayName" cssClass="textbox"></s:select>
						
				<s:select label="Height" name="grid.height" list="heights"
						listKey="name" listValue="displayName" cssClass="textbox"></s:select>
					
			</div>
		</sx:div>
		
<s:submit value="Save"/>
</sx:tabbedpanel>
</s:form>
</body>
</html>
	