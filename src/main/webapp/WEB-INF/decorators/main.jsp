<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html lang="en">
<head>
<title><decorator:title default="Dash Reports" /></title>
<script language="JavaScript" type="text/javascript">

	function hideNav(navName) {
		document.getElementById(navName).style.visibility='hidden';	
	}

	function showNav(navName) {
		hideAllNav();
		document.getElementById(navName).style.visibility='visible';	
	}

	function hideAllNav() {
		document.getElementById('manageNavPane').style.visibility='hidden';	
		document.getElementById('groupNavPane').style.visibility='hidden';	
	}
	
</script>
<decorator:head />
<link href="<s:url value='/styles/main.css'/>" rel="stylesheet"
	type="text/css" media="all" />

</head>
<body id="page-home">
<div id="top">
		<div class="appLogo"><img src="<s:url value='/images/v2/top_bar_logo.png'/>" /><div class="vendorLogo"><img src="<s:url value='getLogo.action?rand=%{randomNumber}'/>" /></div></div>
		<div id="userName"><img src="<s:url value='/images/v2/nav/user.png'/>" align="absmiddle" style="padding-right:5px;"/><s:property value="sessionUser.fullName"/></div>
</div>
<div id="container">
<div id="leftNav"  class="leftnav" >
		<div class="navBody" >	
			<div class="navItemTop"><div class="navIcon"><img src="<s:url value='/images/v2/nav/home.png'/>" /></div><s:a href="index.action">Home</s:a></div>
			<div class="navItem"><div class="navIcon"><img src="<s:url value='/images/v2/nav/groups.png'/>" /></div><a href="#" onclick="showNav('groupNavPane');">My Groups</a></div>
			<s:if test="sessionUser.isAdmin">
				<div class="navItem"><div class="navIcon"><img src="<s:url value='/images/v2/nav/manage.png'/>" /></div>
				<a href="#" onclick="showNav('manageNavPane');">Manage</a></div>
			</s:if>
			<div class="navItem"><div class="navIcon"><img src="<s:url value='/images/v2/nav/about.png'/>" /></div><s:a href="about.action">About</s:a></div>
			<div class="navItem"><div class="navIcon"><img src="<s:url value='/images/v2/nav/help.png'/>" /></div><s:a href="index.action">Help</s:a></div>
			<div class="navItem"><div class="navIcon"><img src="<s:url value='/images/v2/nav/password.png'/>" /></div><s:a href="setupChangePassword.action">Password</s:a></div>
			<div class="navItem"><div class="navIcon"><img src="<s:url value='/images/v2/nav/logout.png'/>" /></div><s:a href="j_spring_security_logout">Logout</s:a></div>
		</div>
	</div>
<div id="content">
<decorator:body />
</div>
</div>
<div id="manageNavPane" class="navPaneSec" onclick="hideAllNav();">
	<div class="navItemSecTop"><div class="navIcon"><img src="<s:url value='/images/v2/nav/config.png'/>" /></div><s:a href="editConfiguration.action">Configuration</s:a></div>
	<div class="navItemSec"><div class="navIcon"><img src="<s:url value='/images/v2/nav/users.png'/>" /></div><s:a href="listUsers.action">Users</s:a></div>
	<div class="navItemSec"><div class="navIcon"><img src="<s:url value='/images/v2/nav/managegroups.png'/>" /></div><s:a href="listGroups.action">Groups</s:a></div>
	<div class="navItemSec"><div class="navIcon"><img src="<s:url value='/images/v2/nav/datasource.png'/>" /></div><s:a href="listDataSources.action">Datasources</s:a></div>
	<!-- <div class="navItemSec"><div class="navIcon"><img src="<s:url value='/images/v2/nav/executingJobs.png'/>" /></div><s:a href="listCurrentExecutingJobs.action">Current Executing Jobs</s:a></div> -->
	<div class="navItemSec"><div class="navIcon"><img src="<s:url value='/images/v2/nav/stats.png'/>" /></div><s:a href="warningStats.action">Audit Log</s:a></div>
	<div class="navItemSec"><div class="navIcon"><img src="<s:url value='/images/v2/nav/scheduler.png'/>" /></div><s:a href="schedulerAdmin.action">Scheduler</s:a></div>
</div>

<div id="groupNavPane" class="navPaneSec" onclick="hideAllNav();">

	<% boolean firstDone=false; %>
	<s:iterator value="groups">
		<% if (!firstDone) { %>
		<div class="navItemSecTop"><s:a href="showGroup.action?groupName=%{groupName}"><s:property value="%{groupName}" /></s:a></div>
		<% firstDone=true;
		   } else { %>
		<div class="navItemSec"><s:a href="showGroup.action?groupName=%{groupName}"><s:property value="%{groupName}" /></s:a></div>
		<%}%>
	</s:iterator>
</div>

</body>
</html>
