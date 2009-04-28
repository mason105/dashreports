<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><decorator:title default="Report Runner" /></title>
<link href="<s:url value='/styles/main.css'/>" rel="stylesheet"
	type="text/css" media="all" />
	<sx:head parseContent="true" />
<script language="javascript">
	
window.onload=function(){
  var nodes =dojo.widget.manager.getWidgetsByType('struts:StrutsTreeNode');
               /* for( var i=0; i < nodes.length; i++){ */
                 /*   nodes[i].expand();*/
                /*}*/
                nodes[0].expand();
} 	
</script>
</head>
<body>
 <sx:tree id="reportrunner" label="<a href='index.action' target='_top'>Report Runner</a>" showRootGrid="true">

  <sx:treenode id="groups" label="Groups" >
  	  <s:iterator value="groups">
      	<sx:treenode id="group_%{groupName}" label="<a href='listJobs.action?groupName=%{groupName}' target='_top'>%{groupName}</a>" />
      </s:iterator>
  </sx:treenode>
  <s:if test="sessionUser.isAdmin == true">
  	<sx:treenode id="manage" label="Manage Server">
		<sx:treenode id="mange_" label="<a href='listUsers.action' target='_top'>Users</a>" />  			
		<sx:treenode id="mange_" label="<a href='listGroups.action' target='_top'>Groups</a>" />  			
		<sx:treenode id="mange_" label="<a href='listAlerts.action' target='_top'>Dashboard Items</a>" />  			
		<sx:treenode id="mange_" label="<a href='listDataSources.action' target='_top'>Data Sources</a>" />  			
		<sx:treenode id="mange_" label="<a href='listCurrentExecutingJobs.action' target='_top'>Executing Jobs</a>" />  			
		<sx:treenode id="mange_" label="<a href='jobStatistics.action' target='_top'>Server Stats</a>" />  			
  	</sx:treenode>
  </s:if>
  <sx:treenode id="changepassword" label="<a href='setupChangePassword.action' target='_top'>Change Password</a>" />
  <sx:treenode id="help" label="<a href='https://sourceforge.net/forum/forum.php?forum_id=848787' target='_top'>Help</a>" />
  <sx:treenode id="logout" label="<a href='logout.action' target='_top'>Logout</a>" />    
 </sx:tree>
</body>
</html>