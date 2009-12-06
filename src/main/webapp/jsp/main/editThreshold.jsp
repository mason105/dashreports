<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<script language="javascript">
window.onload=function(){ 	
	tree();
	drawTabs();	
	setupColour();
}


function drawTabs() {
	
	
	var tb = new Jx.Toolbar();
	var tbButt = new Jx.Toolbar();
	
	var tabSet = new Jx.TabSet('tabContentArea');
	
		
	var tabDetails = (new Jx.Button.Tab({
        		image: '<s:url value="/images/icons/report.png"/>',
           		label: 'Details',            
           		content: 'details'            	
       		}));
       		
	var tabLayout = (new Jx.Button.Tab({
        		image: '<s:url value="/images/icons/table_lightning.png"/>',
           		label: 'Layout',            
           		content: 'layout'            	
       		}));
       		
	var tabThreshold = (new Jx.Button.Tab({
        		image: '<s:url value='/images/icons/flag_green.png'/>',
           		label: 'Threshold',            
           		content: 'Threshold'            	
       		}));
    tb.add(tabDetails); 
    tabSet.add(tabDetails);  
    tb.add(tabLayout); 
    tabSet.add(tabLayout);1
    tb.add(tabThreshold); 
    tabSet.add(tabThreshold);


    
	var submit=new Jx.Button({label: 'Save',
            	image: '<s:url value="/images/icons/disk.png"/>',
				onClick: function() {					
					document.saveAlert.submit();
				}
			});
	
   tbButt.add(new Jx.Toolbar.Separator());
   
   tbButt.add(submit);
   
   tbButt.add(new Jx.Toolbar.Separator());
   
   
   var cancel=new Jx.Button({label: 'Cancel',
			onClick: function() {
				window.location='<s:url value="listAlerts.action"/>';
			}
		});
   
   tbButt.add(cancel);
   
   var tbc=new Jx.Toolbar.Container().addTo('toolbar1');
   tbc.add(tb);
   tbc.add(tbButt);
   
   
}



</script>
<sx:head  />
</head>
<body>
<div id="toolbar1"></div>

<div id="tabContain">
</div>
<div id="toolbarButtons"></div>
<s:form action="saveThreshold">
<div id="tabContentArea"></div>
<div id="formBody" style="position:absolute;top:150px;">
	
		<s:actionerror />
		<s:actionmessage/>
		
		<s:hidden name="threshold.id" value="%{threshold.id}"/>
		
		<div id="details">
			<div class="formGroup">
				<div class="formGroupHeader">Details</div>
			
				<s:textfield label="Item Name" size="64" value="%{threshold.alertName}" name="threshold.alertName" cssClass="textbox" required="true">
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
		</div>
		
		<div id="layout">
			<div class="formGroup">
				<div class="formGroupHeader">Layout/Type</div>
				
				<s:textfield label="Display Column" size="64" value="%{threshold.displayColumn}" name="threshold.displayColumn" cssClass="textbox">
				</s:textfield>
				
				<s:textfield label="Display Row" size="64" value="%{threshold.displayRow}" name="threshold.displayRow" cssClass="textbox">
				</s:textfield>
						
				<s:select label="Width" name="threshold.width" list="widths"
						listKey="name" listValue="displayName" cssClass="textbox"></s:select>
						
				<s:select label="Height" name="threshold.height" list="heights"
						listKey="name" listValue="displayName" cssClass="textbox"></s:select>
					
			</div>
		</div>
		
		<div id="Threshold">
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
		</div>
	
</div>
</s:form>
</body>
</html>
	