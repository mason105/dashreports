<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Report Runner - Login</title>
<style type="text/css">
<!--
body {
	background-color: #999999;
	font-family:Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
}

.errorMessage {
	color: #FF0000;
	font-weight: bold;
}

.tableBorder {
	border-color: #000000;
	border-style: solid;
	border-width: 1px;
}

.txt {
	color: #FFFFFF;
	font-weight: bold;
	font-family:Verdana, Arial, Helvetica, sans-serif;
	font-size: 10px;
}

.blurb,a {
	font-size: 9px;
	color: #FFFFFF;
}

input {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	border: 1px solid #333333;
}
-->
</style>
</head>

<body>
<div id="wrap">
<div id="content"><s:form namespace="/" action="index.action"
	method="post">
	<s:hidden name="loginAttempt" value="%{'1'}" />
	<table width="500" height="250" border="0" align="center" cellpadding="2" cellspacing="0"
		background="images/login_bg.png" class="tableBorder">
		<tr>
			<td height="50" colspan="3" align="left"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="250" height="75" align="left" valign="middle"><img
				src="images/logo.png" alt="report runner" width="250" height="50" /></td>
                <td align="right" valign="middle"><!--<img src="images/binky_logo.png" alt="binky software" width="200" height="75" />--></td>
              </tr>
            </table></td>
		</tr>
		<tr>
			<td width="200" rowspan="4" valign="middle" align="center"><img
				src="images/Keys.png" width="128" height="128" /></td>
			<td colspan="2" class="blurb">
			<p>Welcome to Report Runner - a java powered report generator,
			scheduler and distrubution system.</p>
			<p><s:actionerror /></p>
			</td>
		</tr>
		<tr>
		  <td width="75" height="15" class="txt">Username</td>
		  <td height="15"><input type="text" name="userName" value=""
				id="index_action_userName" style="width: 225px;" /></td>
		</tr>
		<tr>
		  <td width="75" height="15" class="txt">Password</td>
			<td height="15"><input type="password" name="password"
				id="index_action_password" style="width: 225px;" /></td>
		</tr>
		<tr>
			<td height="15" align="right">&nbsp;</td>
		  <td height="40" align="right" valign="top"><input type="submit"
				id="index_action_0" value="Login" style="width: 75px;" /></td>
		</tr>
		<tr>
			<td height="15" colspan="3" align="center" class="blurb"><a
				href="http://reportrunner.binkysoftware.co.uk" target="_blank">http://reportrunner.binkysoftware.co.uk</a></td>
		</tr>
	</table>
</s:form></div>
</div>
</body>
</html>