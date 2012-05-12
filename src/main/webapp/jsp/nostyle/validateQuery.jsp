<%@ taglib prefix="s" uri="/struts-tags"%>



<s:if test="isValid">
<img src="<s:url value="/images/v2/icons/tick.png"/>"/>
</s:if>
<s:else>
<img src="<s:url value="/images/v2/icons/cross.png"/>"/>
</s:else>
Time to Execute: <s:property value="runTime"/> (ms)
		<s:actionerror theme="jquery"/>
		<s:actionmessage theme="jquery"/>