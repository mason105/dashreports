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
<s:form action="changePassword">	
	
	<s:actionerror />
	<s:actionmessage/>
	
	<s:password label="Old Password" name="oldPassword"  cssClass="textbox"/>	
	<s:password label="New Password" name="newPassword1"  cssClass="textbox"/>	
	<s:password label="Confirm New Password" name="newPassword2"  cssClass="textbox"/>	
	
</s:form>
</div>
	
	<div id="submit_button"></div>

	<script language="javascript">
		new Jx.Button({label: 'Save',
			image:'<s:url value="/images/icons/disk.png"/>',
			onClick: function() {
				document.changePassword.submit();
			}
		}).addTo('submit_button');
	</script>
</body>
</html>
	