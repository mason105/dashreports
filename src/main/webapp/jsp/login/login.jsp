<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="<s:url value="/styles/main.css"/>" rel="stylesheet"
	type="text/css" media="all" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Report Runner - Login</title>
</head>

<body>
<div id="top">
		<div class="appLogo"><img src="<s:url value='/images/v2/top_bar_logo.png'/>" /></div>
</div>
<s:form namespace="/" action="index.action" method="post" autocomplete="off">
<div class="formGroup" style="margin:0 auto;background-color:#ffffff;width:350px;">
		<div class="formGroupHeader" >Login to Report Runner</div>
		<s:hidden name="loginAttempt" value="%{'1'}" />
		<div style="float:right;margin-top:10px;">
<s:actionerror /></div>
		<s:textfield name="userName" label="Username"  cssClass="textbox"></s:textfield>
		<s:password label="Password" name="password"  cssClass="textbox"></s:password><s:submit value="Login"/>
</div>
</s:form>
</body>
</html>