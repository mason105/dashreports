
<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<sx:head />
 	<sj:head locale="en" jqueryui="true" jquerytheme="%{themeName}"/>
</head>
<body>
	<div class="formGroup">

		<div class="formGroupHeader">
			<img src="<s:url value='/images/v2/nav/groupsblue.png'/>"
				align="absmiddle" />&nbsp;
			<s:a href="showGroup.action?groupName=%{groupName}">
				<s:property value="groupName" />
			</s:a>
			> View Job -
			<s:property value="jobName" />
		</div>

	

		<div class="formGroup">
	
				<p></p>
				<p> Error whilst processing request.</p>
<p></p>
		</div>
			<div class="formBottom">
			<s:actionerror theme="jquery" />
			<s:actionmessage theme="jquery" />
		</div>
	</div>

</body>
</html>
