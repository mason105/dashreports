<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>
<sx:head parseContent="true" />

<sj:head locale="en" jqueryui="true" jquerytheme="smoothness" />

</head>
<body>
	<s:form action="changeEncryptionKey">
		<div class="formGroup">
			<div class="formGroupHeader">Change Encryption Key</div>


			<s:actionerror theme="jquery" />
			<s:actionmessage theme="jquery" />

			<p>This function will re-encrypt the data-source passwords stored
				in the Dash Reports database. Please use it with care.</p>
			<div class="ui-state-error ui-corner-all"
				style="padding: 0.3em 0.7em; margin-top: 20px;">
				<p>
					<span class="ui-icon ui-icon-alert"
						style="float: left; margin-right: 0.3em;"></span> <span>WARNING:
						Please copy the key before clicking save. You will then need to
						add this to the properties file and restart the server. If you do
						not follow these steps, the passwords for the data-sources will be
						encrypted under a different key and will be unusable.</span>
				</p>
			</div>


			<input type="button" value="Generate Key" class="testButton"
				onclick="dojo.event.topic.publish('generateButton');">
			<s:url id="keyUrl" action="generateEncryptionKey" />
			<sx:div showLoadingText="true" loadingText="Generating Key..."
				id="keyGeneration" href="%{keyUrl}" theme="ajax"
				listenTopics="generateButton" formId="changeEncryptionKey"
				preload="false">
		Please generate a key
		</sx:div>
		</div>

		<div class="formBottom">

			<s:submit value="Save" align="left" />
		</div>

	</s:form>
</body>

</html>