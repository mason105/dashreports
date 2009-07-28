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

  <!-- include the jx library and the delicious skin -->
  <script src="<s:url value='/jx/jxlib.js" type="text/javascript'/>" charset="utf-8"></script>
  <link rel="stylesheet" href="<s:url value='/jx/themes/delicios/jxtheme.css'/>" type="text/css" media="screen" charset="utf-8">
  <!-- IE specific style sheets -->
  <!--[if  lte IE 6]>
  <link rel="stylesheet" href="<s:url value='/jx/themes/delicios/ie6.css'/>" type="text/css" media="screen" charset="utf-8">
  <![endif]-->
  <!--[if gt IE 6]>
  <link rel="stylesheet" href="<s:url value='/jx/themes/delicios/ie7.css'/>" type="text/css" media="screen" charset="utf-8">
  <![endif]-->

<script language="javascript">

function pageY(elem) {
    return elem.offsetParent ? (elem.offsetTop + pageY(elem.offsetParent)) : elem.offsetTop;
}
var buffer = 20; //scroll bar buffer
function resizeIframe() {
    var height = document.documentElement.clientHeight;
    height = pageY(document.getElementById('leftNav'))+ buffer ;
    height = (height < 0) ? 0 : height;
    document.getElementById('leftNav').style.height = height + 'px';
    resizeDebug();
}

</script>	
	
<decorator:head />
</head>
<body id="page-home">

<div id="container">
<div id="top">
		<div class="appLogo"><img src="<s:url value='/images/logo.png'/>" /></div>
</div>
<div id="leftnav">		
			 <iframe id="leftNav" src="<s:url value="/leftNav.action?actionName="/><s:property value="actionName"/>" scrolling="auto" class="leftNavFrame" frameborder="0"></iframe>
</div>
<div id="content">
				<decorator:body />
</div>
<div id="footer">
<strong>Current User:</strong><s:property value="sessionUser.fullName"/>
</div>
</div>

</body>
</html>
