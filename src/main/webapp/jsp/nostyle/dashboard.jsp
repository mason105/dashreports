<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<html>
<head>
	<title>Report Runner - <s:property value="%{groupName}"/></title>
	
	<sx:head />

<link href="<s:url value='/styles/main.css'/>" rel="stylesheet"
	type="text/css" media="all" />

<body>
	<div class="toolBar">
		<s:a href="setupEditChart.action?groupName=%{groupName}"><img align="absmiddle" src="<s:url value="/images/v2/icons/chart_add.png"/>"/>Add Chart</s:a>&nbsp;|
		<s:a href="setupEditGrid.action?groupName=%{groupName}"><img align="absmiddle" src="<s:url value="/images/v2/icons/grid_add.png"/>"/>Add Data Grid</s:a>&nbsp;|
		<s:a href="setupEditThreshold.action?groupName=%{groupName}"><img align="absmiddle" src="<s:url value="/images/v2/icons/threshold_add.png"/>"/>Add Threshold</s:a>
	</div>					
<s:if test="items.size>0">		
	<div class="dashboard">							
		<s:iterator value="items" status="rowstatus">
			<s:if test="(displayRow!=#currentRow)">
				<div class="clearFix"></div>
				<s:set name="currentRow" value="%{displayRow}"/>							
			</s:if>						
			<sx:div theme="ajax" href="dashboardWidget.action?itemId=%{itemId}" updateFreq="%{visualRefreshTime}">
				<sx:div theme="ajax" href="dashboardWidget.action?itemId=%{itemId}"/>										
			</sx:div>	
		</s:iterator>
	</div>
</s:if>
</body>
</html>