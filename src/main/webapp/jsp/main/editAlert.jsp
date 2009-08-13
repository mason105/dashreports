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
}

function drawTabs() {
	
	
	var tb = new Jx.Toolbar();
	var tbButt = new Jx.Toolbar();
	
	var tabSet = new Jx.TabSet('tabContentArea');
	
		
	var tabDetails = (new Jx.Button.Tab({
        		image: '<s:url value="/images/icons/report.png"/>',
           		label: 'Details',            
           		content: 'details',            	
       		}));
       		
	var tabLayout = (new Jx.Button.Tab({
        		image: '<s:url value="/images/icons/table_lightning.png"/>',
           		label: 'Layout',            
           		content: 'layout',            	
       		}));
       		
	var tabChart = (new Jx.Button.Tab({
        		image: '<s:url value='/images/icons/chart_bar.png'/>',
           		label: 'Chart',            
           		content: 'chart',            	
       		}));
    tb.add(tabDetails); 
    tabSet.add(tabDetails);  
    tb.add(tabLayout); 
    tabSet.add(tabLayout);1
    tb.add(tabChart); 
    tabSet.add(tabChart);


    
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
<div id="tabContentArea"></div>
<div id="formBody" style="position:absolute;top:150px;">
	<s:form action="saveAlert">
		<s:actionerror />
		<s:actionmessage/>
		
		<s:hidden name="dashboardAlert.id" value="%{dashboardAlert.id}"/>
		
		<div id="details">
			<div class="formGroup">
				<div class="formGroupHeader">Details</div>
			
				<s:textfield label="Item Name" size="64" value="%{dashboardAlert.alertName}" name="dashboardAlert.alertName">
				</s:textfield>
				<s:select label="Select Group"
					name="dashboardAlert.group.groupName" value="%{dashboardAlert.group.groupName}"
					list="groups" listKey="groupName" listValue="groupName">
				</s:select>
				
				<s:select label="Select Data Source"
					name="dashboardAlert.datasource.dataSourceName" value="%{dashboardAlert.datasource}"
					list="runnerDataSources" listKey="dataSourceName" listValue="dataSourceName">
				</s:select>
				
				<s:textarea label="Query" cols="30" rows="20"
					value="%{dashboardAlert.alertQuery}" name="dashboardAlert.alertQuery">
				</s:textarea>	
			
				<s:textfield label="Cron String" size="32" value="%{dashboardAlert.cronTab}" name="dashboardAlert.cronTab">
				</s:textfield>
				
			</div>	
		</div>
		
		<div id="layout">
			<div class="formGroup">
				<div class="formGroupHeader">Layout/Type</div>
				
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
		
			</div>
		</div>
		
		<div id="chart">
			<div class="formGroup">
				<div class="formGroupHeader">Chart Configuration</div>
				
				<s:select label="X Axis Step Size" name="dashboardAlert.xAxisStep" list="xAxisSteps"
				listKey="name" listValue="displayName"></s:select>
			
			 	<s:textfield label="Y-Axis Label" size="64" value="%{dashboardAlert.yLabel}" name="dashboardAlert.yLabel">
				</s:textfield>
				
				<s:textfield label="X-Axis Column Name" size="32" value="%{dashboardAlert.xaxisColumn}" name="dashboardAlert.xaxisColumn">
				</s:textfield>
				
				
				<s:textfield label="Value Column Name" size="32" value="%{dashboardAlert.valueColumn}" name="dashboardAlert.valueColumn">
				</s:textfield>
				
				<s:textfield label="Series Name Column Name" size="32" value="%{dashboardAlert.seriesNameColumn}" name="dashboardAlert.seriesNameColumn">
				</s:textfield>
			
			</div>
		</div>
	</s:form>
</div>
</body>
</html>
	