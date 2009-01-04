<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Login to Report Runner</title>
<style>
div.wwgrp {
	clear: both;
	padding-top: 2px;
}

div.wwgrp div.wwlbl {
	float: left;
	width: 100px;
	text-align: right;
}

div.wwgrp div.wwctrl {
	float: left;
	/*width: 375px;*/
	text-align: left;
}

div.wwgrp { /*width: 550px;*/
	
}

div.wwgrp br {
	display: none;
}

body {
	margin: 0px;
	width: 100%;
	height: 100%;
	background-color: #CCCCCC;
	text-align: left;
	font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;
	font-size: 76%;
	line-height: 1em;
	color: #333;
}

div.midLabel { /*width:549px;*/
	background-color: #333333;
	font-size: 14px;
	color: #FFFFFF;
	height: 14px;
	font-weight: bold;
	padding: 2px;
}

div.formSection {
	clear: both;
	border-style: solid;
	border-width: 1px;
	border-color: #000000;
	/*padding-top: 5px;*/
	background-color: #FFFFFF;
	margin: 0 auto;
	/*width:500px;*/
	text-align: left;
}

/* column container */
.colmask {
	position: relative; /* This fixes the IE7 overflow hidden bug */
	clear: both;
	float: left;
	width: 100%; /* width of whole page */
	overflow: hidden; /* This chops off any overhanging divs */
}

/* common column settings */
.colright,.colmid,.colleft {
	float: left;
	width: 100%; /* width of page */
	position: relative;
}

.col1,.col2,.col3 {
	float: left;
	position: relative;
	padding: 0 0 1em 0;
	/* no left and right padding on columns, we just make them narrower instead 
						only padding top and bottom is included here, make it whatever value you need */
	overflow: hidden;
}

/* 3 Column settings */
.threecol {
	
}

.threecol .colmid {
	right: 25%; /* width of the right column */
}

.threecol .colleft {
	right: 50%; /* width of the middle column */
}

.threecol .col1 {
	width: 46%;
	/* width of center column content (column width minus padding on either side) */
	left: 102%; /* 100% plus left padding of center column */
}

.threecol .col2 {
	width: 21%;
	/* Width of left column content (column width minus padding on either side) */
	left: 31%;
	/* width of (right column) plus (center column left and right padding) plus (left column left padding) */
}

.threecol .col3 {
	width: 21%;
	/* Width of right column content (column width minus padding on either side) */
	left: 85%; /* Please make note of the brackets here:
						(100% - left column width) plus (center column left and right padding) plus (left column left and right padding) plus (right column left padding) */
}
</style>
</head>
<body>
<div>&nbsp;</div>
<div class="colmask threecol">
<div class="colmid">
<div class="colleft">
<div class="col1">

<div class="formSection"><s:form namespace="/"
	action="index.action" method="post">
	<div class="midLabel">Login to Report Runner</div>
	<s:actionerror />
	<s:hidden name="loginAttempt" value="%{'1'}" />
	<s:textfield name="userName" label="Username" />
	<s:password name="password" label="Password" />
	<s:submit value="Login" align="center" />
</s:form></div>
</div>
<div class="col2"></div>
<div class="col3"></div>
</div>

</div>
</div>
</body>
</html>
