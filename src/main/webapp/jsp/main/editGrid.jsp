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
           		content: 'details'            	
       		}));
       		
	var tabLayout = (new Jx.Button.Tab({
        		image: '<s:url value="/images/icons/table_lightning.png"/>',
           		label: 'Layout',            
           		content: 'layout'            	
       		}));
       		
    tb.add(tabDetails); 
    tabSet.add(tabDetails);  
    tb.add(tabLayout); 
    tabSet.add(tabLayout);1
    
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
<s:form action="saveGrid">
<div id="tabContentArea"></div>
<div id="formBody" style="position:absolute;top:150px;">
	
		<s:actionerror />
		<s:actionmessage/>
		
		<s:hidden name="gird.id" value="%{gird.id}"/>
		
		<div id="details">
			<div class="formGroup">
				<div class="formGroupHeader">Details</div>
			
				<s:textfield label="Item Name" size="64" value="%{gird.alertName}" name="gird.alertName" cssClass="textbox" required="true">
				</s:textfield>
				<s:hidden name="gird.group.groupName" value="%{groupName}"/>
				<s:select label="Select Data Source"
					name="dashboardAlert.datasource.dataSourceName" value="%{gird.datasource}"
					list="runnerDataSources" listKey="dataSourceName" listValue="dataSourceName" cssClass="textbox">
				</s:select>
				
				<s:textarea label="Query" cols="30" rows="20"
					value="%{gird.alertQuery}" name="gird.alertQuery" cssClass="textbox" required="true">
				</s:textarea>	
			
				<s:textfield label="Cron String" size="32" value="%{gird.cronTab}" name="gird.cronTab" cssClass="textbox" required="true">
				</s:textfield>
				
			</div>	
			<div class="formFooterText">* required field</div>
		</div>
		
		<div id="layout">
			<div class="formGroup">
				<div class="formGroupHeader">Layout/Type</div>
				
				<s:textfield label="Display Column" size="10" value="%{gird.displayColumn}" name="gird.displayColumn" cssClass="textbox">
				</s:textfield>
				
				<s:textfield label="Display Row" size="10" value="%{gird.displayRow}" name="gird.displayRow" cssClass="textbox">
				</s:textfield>
						
				<s:textfield label="Max Rows" size="19" value="%{gird.rowsToDisplay}" name="gird.rowsToDisplay" cssClass="textbox">
				</s:textfield>
						
						
				<s:select label="Width" name="gird.width" list="widths"
						listKey="name" listValue="displayName" cssClass="textbox"></s:select>
						
				<s:select label="Height" name="gird.height" list="heights"
						listKey="name" listValue="displayName" cssClass="textbox"></s:select>
					
			</div>
		</div>
		
	
</div>
</s:form>
</body>
</html>
	