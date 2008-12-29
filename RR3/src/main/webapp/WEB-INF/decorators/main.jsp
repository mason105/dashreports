<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><decorator:title default="Struts Starter"/></title>
    <link href="<s:url value='/styles/main.css'/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<s:url value='/struts/niftycorners/niftyCorners.css'/>" rel="stylesheet" type="text/css"/>
    <link href="<s:url value='/struts/niftycorners/niftyPrint.css'/>" rel="stylesheet" type="text/css" media="print"/>
    <script language="JavaScript" type="text/javascript" src="<s:url value='/struts/niftycorners/nifty.js'/>"></script>
	<script language="JavaScript" type="text/javascript">
        window.onload = function(){
            if(!NiftyCheck()) {
                return;
            }
            // perform niftycorners rounding
            // eg.
            // Rounded("blockquote","tr bl","#ECF1F9","#CDFFAA","smooth border #88D84F");
        }
    </script>
    <decorator:head/>
</head>
<body id="page-home">
    <div id="page">
        <div id="header" class="clearfix">
        	Report Runner 3.0
            <hr />
        </div>
        
        <div id="content" class="clearfix">
            <div id="main">         
            	<decorator:body/>
                <hr />
            </div>
            
            <div id="sub">
            	<h3>Sub Content</h3>
            </div>         
            <div id="local">
            </div>
            <div id="nav">
                <div class="wrapper">
                <h3>Nav. bar</h3>
                <ul class="clearfix">
                     <li><a href="index.action">Home</a></li>
                     <li><a href="listGroups.action">My Groups</a></li>
                     <li><a href="manageServer.action">Manage Server</a></li>
                     <li class="last"><a href="logout.action">Logout</a></li>                     
                </ul>
                </div>
                <hr />
            </div>
        </div>
        
        <div id="footer" class="clearfix">
            &copy; 2008 Daniel Grout
        </div>
        
    </div>
    
    <div id="extra1">&nbsp;</div>
    <div id="extra2">&nbsp;</div>
</body>
</html>
