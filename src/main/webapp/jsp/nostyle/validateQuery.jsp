<%@ taglib prefix="s" uri="/struts-tags"%>

<s:actionerror />
<s:actionmessage/>

<s:if test="isValid">
<img src="<s:url value="/images/v2/icons/tick.png"/>"/>
</s:if>
<s:else>
<img src="<s:url value="/images/v2/icons/cross.png"/>"/>
</s:else>
