<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><decorator:title default="Report Runner" /></title>
<link href="<s:url value='/styles/main.css'/>" rel="stylesheet"
	type="text/css" media="all" />
<decorator:head />
</head>
<body id="page-home">
<div id="page">
<div id="header" class="clearfix">
<div class="appLogo"><img src="<s:url value='/images/logo.png'/>" /></div>
</div>

<div id="content" class="clearfix">
<div id="main"><decorator:body /></div>

<div id="sub"></div>
<div id="local"></div>
<div id="nav">
<div class="wrapper">
<ul class="clearfix">
	<li><a href="index.action">Home</a></li>
	<li><a href="listGroups.action">My Groups</a></li>
	<s:if test="user.isAdmin == true">
		<li><a href="manageServer.action">Manage Server</a></li>#
	</s:if>
	<li><a href="setupChangePassword.action">Change Password</a></li>	
	<li class="last"><a href="logout.action">Logout</a></li>
</ul>
</div>
</div>
</div>

</div>

</body>
</html>
