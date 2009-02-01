<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>
<s:form action="changePassword">
	<s:actionerror />
	<s:actionmessage/>
	
	<s:password label="Old Password" name="oldPassword"/>	
	<s:password label="New Password" name="newPassword1"/>	
	<s:password label="Confirm New Password" name="newPassword2"/>	
	
	<s:submit />
	
</s:form>
</body>
</html>
	