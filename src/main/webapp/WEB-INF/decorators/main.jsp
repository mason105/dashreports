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

<decorator:head />
<link href="<s:url value='/styles/main.css'/>" rel="stylesheet"
	type="text/css" media="all" />

</head>
<body id="page-home">
<div id="top">
		<div class="appLogo"><img src="<s:url value='/images/v2/top_bar_logo.png'/>" /></div>
		<div id="userName"><img src="<s:url value='/images/v2/icons/user.png'/>" align="absmiddle" style="padding-right:5px;"/><s:property value="sessionUser.fullName"/></div>
</div>
<div id="navbar">
<img src="<s:url value='/images/v2/icons/house.png'/>"  align="absmiddle" style="padding-right:5px;"/><s:a href="index.action">Home</s:a>&nbsp;|&nbsp;
<s:if test="sessionUser.isAdmin">
	<img src="<s:url value='/images/v2/icons/wrench.png'/>"  align="absmiddle" style="padding-right:5px;"/>
	<s:a href="manageServer.action">Manage</s:a>&nbsp;|&nbsp;
</s:if>
<img src="<s:url value='/images/v2/icons/key.png'/>"  align="absmiddle" style="padding-right:5px;"/><s:a href="setupChangePassword.action">Password</s:a>&nbsp;|&nbsp;
<img src="<s:url value='/images/v2/icons/page.png'/>"  align="absmiddle" style="padding-right:5px;"/><s:a href="about.action">About</s:a>&nbsp;|&nbsp;
<img src="<s:url value='/images/v2/icons/help.png'/>"  align="absmiddle" style="padding-right:5px;"/><s:a href="index.action">Help</s:a>&nbsp;|&nbsp;
<img src="<s:url value='/images/v2/icons/door_out.png'/>"  align="absmiddle" style="padding-right:5px;"/><s:a href="logout.action">Logout</s:a>
</DIV>	
<div id="container">
	<div id="leftNav"  class="leftnav">
			<div class="navHeader">Groups</div>	
			<div class="navBody">	
			<s:iterator value="groups">
				<div class="groupButton" onClick="parent.location='group.action?groupName=<s:property value="%{groupName}" />'"><s:property value="%{groupName}" /></div>
	   		</s:iterator>
			</div>
			<div class="navFooter"></div>
		</div>
<div id="content">
<decorator:body />
</div>
</div>

</body>
</html>
