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
<link href="<s:url value='/styles/login.css'/>" rel="stylesheet"
	type="text/css" media="all" />	

<title>Dash Reports</title>

<script type="text/javascript">
//hacky as feck hack to force ajax divs to reload.
var pageName = window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);

if (pageName!='login.jsp') {
	location.reload(true);
}

</script>


</head>
<body>



<div id="MainBar">
	<div id="TopLinks">
	</div>
	<div id="LogoWrapper">
		<div id="Logo"></div>
	</div>
</div>
<div id="ShadeBar">
	<div id="NavBar">

	</div>
</div>
<div id="BottomBar">
<form action="j_spring_security_check" method="post" autocomplete="off">
	<div id="container">
	<div class="loginBox">
	<div class="loginBoxTop">
		Application Login
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
</div>

</body>
</html>