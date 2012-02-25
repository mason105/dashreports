<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>
<sx:head parseContent="true" />

<sj:head locale="en" jqueryui="true" jquerytheme="smoothness" />

</head>
<body>
	<s:form action="saveConfiguration" method="post"
		enctype="multipart/form-data">
		<div class="formGroup">
			<div class="formGroupHeader">Configuration</div>


			<s:actionerror theme="jquery" />
			<s:actionmessage theme="jquery" />


			<s:iterator value="configurations" status="rowstatus">
				<s:hidden value="%{type}"
					name="configurations[%{#rowstatus.index}].type" />

				<s:if test="type.dataType==1">
					<s:textfield label="%{type.displayName}"
						name="configurations[%{#rowstatus.index}].value" value="%{value}"
						cssClass="textbox" />
				</s:if>
				<s:if test="type.dataType==2">
					<sj:spinner label="%{type.displayName}"
						name="configurations[%{#rowstatus.index}].value" value="%{value}"
						step="1" />

				</s:if>
				<s:if test="type.dataType==3">
					<s:file label="%{type.displayName}"
						name="files[%{#rowstatus.index}]" value="" cssClass="textbox" />
				</s:if>
			</s:iterator>



			<div class="formBottom">
				<div class="formFooterText">* required field</div>
				<s:submit value="Save" align="left" />
			</div>
		</div>
	</s:form>
</body>

</html>