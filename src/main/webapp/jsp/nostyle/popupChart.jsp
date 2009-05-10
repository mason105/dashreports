<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" uri="/connext" %>
<html>
<head>
	<title><s:property value="%{alertName}"/></title>
<script type="text/javascript">
	window.onload=function(){
		reload();
	}
		function reload()
		{
		  tmp = findSWF("chart");
			  x = tmp.reload();		}
		
		function findSWF(movieName) {
		  if (navigator.appName.indexOf("Microsoft")!= -1) {
		    return window["ie_" + movieName];
		  } else {
		    return document[movieName];
		  }
		}
	
	</script>
</head>
<body>
	<m:graph id="chart" width="800" height="600" align="middle" bgcolor="#FFFFFF"  url="getDashboardChartData.action?alertId=%{alertId}"/>
</body>
</html>