<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="m" uri="/connext" %>

<html>
<head>
<sx:head parseContent="true" />
	<link href="<s:url value='/styles/displaytag.css'/>" rel="stylesheet" type="text/css" media="all" />

</head>
<body>

	<sx:tabbedpanel id="groups">
		<s:iterator value="dashboardBeans" status="rowstatus">
			<sx:div id="group_%{key}" label="%{key}">
			
				<s:bean name="binky.reportrunner.ui.actions.dashboard.RowBean" id="rowStatus">
					<s:param name="currRow">0</s:param>
				</s:bean>
			
				<s:iterator value="value" status="rowstatus">
				<s:if test="(rowStatus.currRow!=displayRow)">	
					<s:set name="rowStatus.currRow" value="%{displayRow}" />
					<div class="clearFix"></div>
				</s:if>
				<s:if test="(chart==true)">				
					<div class="chartBox">

							<m:graph
							  id="%{alertId}"
							  width="300"
							  height="300"
							  align="middle"
							  bgcolor="#FFFFFF"
							  url="/getDashboardChartData.action?alertId=%{alertId}"
							/>
					
					</div>
				</s:if><s:else>
					<div class="dataBox">
						<span class="dataBoxHeader"><s:property value="%{alertName}"/></span>
						<display:table name="data.rows"  requestURI="index.action" export="false">	
						</display:table>
					</div>
				</s:else>
				</s:iterator>
			</sx:div>
		</s:iterator>
	</sx:tabbedpanel>

</body>
</html>
	