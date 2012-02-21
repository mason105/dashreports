<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>
<sx:head parseContent="true" />

<sj:head locale="en" jqueryui="true" jquerytheme="smoothness" />

</head>
<body>
	<div class="formGroup">
		<div class="formGroupHeader">Change Encryption Key</div>

		<p>
			Key was changed to: <strong><s:property value="newKey" /></strong>
		</p>
		<p>Now you must copy this key into the dashreports.properties file
			and restart the server. Failure to do this will render the
			data-sources unusable as they will be encrypted under a different key</p>

	</div>
	<div class="formBottom"></div>
</body>

</html>