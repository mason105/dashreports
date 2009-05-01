<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" uri="/connext" %>
<html>
<head>
	<title><s:property value="%{alertName}"/></title>
</head>
<body>
	<m:graph id="chart_%{id}" width="100%" height="100%" align="middle" bgcolor="#FFFFFF"
									  url="/getDashboardChartData.action?alertId=%{alertId}"
									/>
</body>
</html>