<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<html>
<head>
<sx:head parseContent="true" />
	<link href="<s:url value='/styles/displaytag.css'/>" rel="stylesheet" type="text/css" media="all" />

</head>
<body>

	<sx:tabbedpanel id="groups">
		<s:iterator value="dashboardBeans" status="rowstatus">
			<sx:div id="group_%{key}" label="%{key}">
			
				<s:iterator value="value" status="rowstatus">
				<s:if test="(chart==true)">
					<div class="chartBox">
						<a href="downloadChart.action?id=<s:property value="chartUID"/>_large">
							<img src="downloadChart.action?id=<s:property value="chartUID"/>_small"/>
						</a>
					</div>
				</s:if><s:else>
					<div class="dataBox">
						<display:table name="data.rows"  pagesize="25" requestURI="index.action" export="false">	
						</display:table>
					</div>
				</s:else>
				</s:iterator>
			</sx:div>
		</s:iterator>
	</sx:tabbedpanel>

</body>
</html>
	