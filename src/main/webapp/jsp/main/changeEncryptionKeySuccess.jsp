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

		<div id="saveConfiguration_" class="ui-widget actionMessage">
			<div class="ui-state-highlight ui-corner-all"
				style="padding: 0.3em 0.7em; margin-top: 20px;">
				<p>
					<span class="ui-icon ui-icon-info"
						style="float: left; margin-right: 0.3em;"></span> <span>Key
						was changed to: <strong><s:property value="newKey" /></strong>
					</span>
				</p>
			</div>
		</div>
		<div class="ui-state-error ui-corner-all"
			style="padding: 0.3em 0.7em; margin-top: 20px;">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin-right: 0.3em;"></span> <span>Now
					you must copy this key into the dashreports.properties file and
					restart the server. Failure to do this will render the data-sources
					unusable as they will be encrypted under a different key </span>
			</p>
		</div>

	</div>
	<div class="formBottom"></div>
</body>

</html>