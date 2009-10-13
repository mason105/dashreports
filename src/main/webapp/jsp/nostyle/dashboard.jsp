<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<html>
<head>
	<title><s:property value="%{groupName}"/></title>
	<sx:head />

<link href="<s:url value='/styles/main.css'/>" rel="stylesheet"
	type="text/css" media="all" />

<body>
<s:if test="alerts.size>0">							
	<div class="dashboard">							
		<s:iterator value="alerts" status="rowstatus">
			<s:if test="(displayRow!=#currentRow)">
				<div class="clearFix"></div>
				<s:set name="currentRow" value="%{displayRow}"/>							
			</s:if>						
			<sx:div theme="ajax" href="dashboardWidget.action?alertId=%{id}" updateFreq="60000">
				<sx:div theme="ajax" href="dashboardWidget.action?alertId=%{id}"/>										
			</sx:div>	
		</s:iterator>
	</div>
</s:if>
</body>
</html>