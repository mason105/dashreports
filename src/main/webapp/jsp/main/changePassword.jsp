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

<div class="formGroup">
	<div class="formGroupHeader">Change Password</div>
<s:form action="changePassword" autocomplete="off">	
	
	<s:actionerror />
	<s:actionmessage/>
	
	<s:password label="Old Password" name="oldPassword"  cssClass="textbox"/>	
	<s:password label="New Password" name="newPassword1"  cssClass="textbox"/>	
	<s:password label="Confirm New Password" name="newPassword2"  cssClass="textbox"/>	
<div class="formBottom">
	<div class="formFooterText">
	
	<s:actionerror  theme="jquery" />
		<s:actionmessage  theme="jquery"/>
		</div>
	<s:submit value="Save" align="left"/>
	</div>
</s:form>
</div>
</body>
</html>
	