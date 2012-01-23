<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<html>
	<head>
			<sj:head locale="en" jqueryui="true" jquerytheme="smoothness"/>
		<link href="<s:url value='/styles/displaytag.css'/>" rel="stylesheet" type="text/css" media="all" />
	</head>
<body>
	<div class="formGroupWide">
	<div class="formGroupWideHeader"><img align="absmiddle"  src="<s:url value="/images/v2/icons/stats.png"/>"/>&nbsp;Statistics and Warnings</div>
	<sj:tabbedpanel id="stats">
		<sj:tab id="successTab" label="Latest Success" target="successDiv"/>
		<div id="successDiv">		
			<div style="overflow-x:auto; overflow-y:auto; overflow:auto;">
				<display:table name="latestSuccessEvents"  pagesize="20" requestURI="jobStatistics.action" export="false">
				</display:table>		
			</div>
		</div>		
		<sj:tab id="failTab" label="Latest Failures" target="failDiv"/>		
		<div id="failDiv">
			<div style="overflow-x:auto; overflow-y:auto; overflow:auto;">				
				<display:table name="latestFailEvents"  pagesize="20" requestURI="jobStatistics.action" export="false">
				</display:table>		
			</div>
		</div>		
		<sj:tab id="longTab" label="Longest Running" target="longDiv"/>
		<div id="longDiv">	
			<div style="overflow-x:auto; overflow-y:auto; overflow:auto;">	
				<display:table name="longestEvents"  pagesize="20" requestURI="jobStatistics.action" export="false">
				</display:table>		
			</div>
		</div>
	</sj:tabbedpanel>
	</div>
</body>
</html>
	