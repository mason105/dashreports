<%@ taglib prefix="s" uri="/struts-tags"%>

		<s:actionerror theme="jquery"/>
		<s:actionmessage theme="jquery"/>

	<s:textfield  name="newKey" value="%{encryptionKey}" readonly="true"  cssClass="textbox" />			