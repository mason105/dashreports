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
<sx:tabbedpanel id="grouptabs" selectedTab="%{activeTab}">						
				<sx:div id="dashboard" label="Dashboard">		
					<sx:div href="dashboard.action?groupName=%{groupName}" separateScripts="true"  executeScripts="true"/>
				</sx:div>
				<sx:div id="reports" label="Reports">		
					<sx:div href="listJobs.action?groupName=%{groupName}" separateScripts="true"  executeScripts="true"/>
				</sx:div>				
</sx:tabbedpanel>
</body>
</html>