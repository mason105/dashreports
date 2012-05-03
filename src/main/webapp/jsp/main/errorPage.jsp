<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>
<sx:head parseContent="true" />

<sj:head locale="en" jqueryui="true" jquerytheme="%{themeName}" />

</head>
<body>

	<div class="formGroup">
		<div class="formGroupHeader"><span>An Error has Occurred</span></div>


		<s:actionerror theme="jquery" />
		<s:actionmessage theme="jquery" />
		<br/><br/>
		<div class="formBottom"><strong>Please consult the log files if you require further
			information</strong></div>
	</div>

</body>

</html>