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

<script language="javascript">

function pageY(elem) {
    return elem.offsetParent ? (elem.offsetTop + pageY(elem.offsetParent)) : elem.offsetTop;
}
var buffer = 20; //scroll bar buffer
function resizeIframe() {
    var height = document.documentElement.clientHeight;
    height -= pageY(document.getElementById('leftNav'))+ buffer ;
    height = (height < 0) ? 0 : height;
    document.getElementById('leftNav').style.height = height + 'px';
    resizeDebug();
}
document.getElementById('leftNav').onload=resizeIframe;
window.onresize = resizeIframe;


</script>	
	
<decorator:head />
</head>
<body id="page-home">

<div id="container">
<div id="top">
		<div class="appLogo"><img src="<s:url value='/images/logo.png'/>" /></div>
</div>
<div id="leftnav">
			<!--<li><a href="index.action">Home</a></li>
			<li><a href="listGroups.action">My Groups</a></li>
			<s:if test="sessionUser.isAdmin == true">
				<li><a href="manageServer.action">Manage Server</a></li>
			</s:if>
			<li><a href="setupChangePassword.action">Change Password</a></li>	
			<li><a href="https://sourceforge.net/forum/forum.php?forum_id=848787">Help</a></li>
			<li class="last"><a href="logout.action">Logout</a></li></ul>-->
			
			 <iframe id="leftNav" src="<s:url value='/leftNav.action'/>" scrolling="auto" width="199" height="100%" frameborder="0"></iframe>
</div>
<div id="content">
		<div class="body"><decorator:body /></div>
</div>
<div id="footer">
Footer
</div>
</div>

</body>
</html>
