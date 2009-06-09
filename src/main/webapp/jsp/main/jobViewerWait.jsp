<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<html>
<head>
<sx:head parseContent="true" />
 <meta http-equiv="refresh" content="2;url=<s:url includeParams="all" />"/>
</head>
<body>
 <h3> Please wait while we process your request.</h3> 
     <p>Click <a href="<s:url includeParams="all" />">here</a> if this page does not reload automatically.</p>
</body>
</html>	