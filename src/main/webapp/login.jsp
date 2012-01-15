<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="<s:url value='/styles/forms.css'/>" rel="stylesheet"
	type="text/css" media="all" />
<link href="<s:url value='/styles/typo.css'/>" rel="stylesheet"
	type="text/css" media="all" />	
<style>
	.errorText {
		padding-top:13px;
		float:right;
		font-weight:bold;
		font-style:italic;
		color:#ff0000;
	}
	.loginBox {
		width:440px;
	}
	.loginBoxTop {
		background-color:#333333;
		border-right: 1px solid gray;
		border-left: 1px solid gray;
		border-top: 1px solid gray;
		padding-top:5px;
		padding-left:15px;
		padding-bottom:5px;
		color:#ffffff;
		font-weight:bold;
		height:35px;
		line-height:35px;
		font-size:12px;
	}
	.loginBoxMiddle {
		background-color:#ffffff;
		border: 1px solid gray;
		padding:10px;
	}
	.loginBoxBottom {
		background-color:#eeeeee;
		border-right: 1px solid gray;
		border-left: 1px solid gray;
		border-bottom: 1px solid gray;		
		padding-left:15px;
		padding-top:10px;
		padding-bottom:10px;
		padding-right:20px;
	}
	body {
		background-image: url(<s:url value="/images/v2/background.png"/>);
		text-align: center;
}

div#container
{
	margin-left: auto;
	margin-right: auto;
	width: 50em;
	text-align: left;

}
input.textbox {
	width:400px;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Login</title>
</head>
<body>
<form action="j_spring_security_check" method="post" autocomplete="off">
	<div id="container">
	<div class="loginBox">
	<div class="loginBoxTop">
		Security Login
	</div>
	<div class="loginBoxMiddle">
	
		<s:textfield name="j_username" label="Username"  cssClass="textbox"/>
		<s:password label="Password" name="j_password"  cssClass="textbox"/>
	</div>
	<div class="loginBoxBottom">
			<c:if test="${not empty param.login_error}">    
			<div class="errorText">Your login attempt was not successful</div>
		</c:if><s:submit value="Login" align="left"/>
	</div>
	</div>
	</div>
</form>
</body>
</html>