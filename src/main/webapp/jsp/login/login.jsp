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
	color: #000000;
	font-weight: bold;
	font-family:Verdana, Arial, Helvetica, sans-serif;
	font-size: 10px;
}

.blurb,a {
	font-size: 9px;
	color: #000000;
}

input {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	border: 1px solid #333333;
}
* {margin:0;padding:0}
/* mac hide \*/
html,body{height:100%;width:100%;}
/* end hide */
body { 
	text-align:center;
}
#xouter{
	height:100%;
	width:100%;
	display:table;
	vertical-align:middle;
}
#xcontainer {
	text-align: center;
	position:relative;
	vertical-align:middle;
	display:table-cell;
}	
#xinner {
	background:#fff;
	text-align: center;
	margin-left:auto;
	margin-right:auto;
	width:500px;
}
/* not required for demo */
p,h1{margin-bottom:1em}
#header{margin-right:0}
.maintxt{text-align:left;margin:1em;}
/* - */
</style>
<!--[if lte IE 8]>
<style type="text/css">
#xouter{display:block}
#xcontainer{top:50%;display:block}
#xinner{top:-50%;position:relative}
</style>
<![endif]-->

<!--[if IE 7]>
<style type="text/css">
#xouter{
position:relative;
overflow:hidden;
}
</style>
<![endif]-->


</head>

<body>
<div id="xouter">
	<div id="xcontainer">
		<div id="xinner">

<s:form namespace="/" action="index.action"
	method="post">
	<s:hidden name="loginAttempt" value="%{'1'}" />
	<table width="500" border="0" align="center" cellpadding="2" cellspacing="4" class="tableBorder" bgcolor=#FFFFFF>
		<tr>
		<td  colspan="2" valign="top" align="center"><img
				src="images/logo.png" alt="report runner"  /></td>
		</tr>
		<tr>
 			<td class="txt" align="right">Username</td>
		  <td  width=400><input type="text" name="userName" value=""
				id="index_action_userName" style="width: 400px;" /></td>
		</tr>
		<tr>
		  <td class="txt" align="right">Password</td>
			<td width=400><input type="password" name="password"
				id="index_action_password" style="width: 400px;" /></td>
		</tr>
		<tr>
		<td align="middle" colspan=2><p><s:actionerror /></p></td>
		</tr>
		<tr>
		  <td></td>
		  <td  width=400 align="left" valign="top"><input type="submit"
				id="index_action_0" value="Login" style="width: 75px;" /></td>
		</tr>
	</table>
</s:form>
		</div>
	</div>
</div>

</body>
</html>