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
	<span class="pageTitle"><img
	src="<s:url value='/images/icons/chart_curve.png'/>" align="absmiddle" />Statistics and Warnings</span>
	
	<sx:tabbedpanel id="stats">
		<sx:div id="success" label="Latest Success">		
			<div style="overflow-x:auto; overflow-y:auto; overflow:auto;">
				<display:table name="latestSuccessEvents"  pagesize="20" requestURI="jobStatistics.action" export="false">
				</display:table>		
			</div>
		</sx:div>		
		<sx:div id="fail" label="Latest Failures">		
			<div style="overflow-x:auto; overflow-y:auto; overflow:auto;">				
				<display:table name="latestFailEvents"  pagesize="20" requestURI="jobStatistics.action" export="false">
				</display:table>		
			</div>
		</sx:div>		
		<sx:div id="long" label="Longest Running">	
			<div style="overflow-x:auto; overflow-y:auto; overflow:auto;">	
				<display:table name="longestEvents"  pagesize="20" requestURI="jobStatistics.action" export="false">
				</display:table>		
			</div>
		</sx:div>
	</sx:tabbedpanel>
				
</body>
</html>
	