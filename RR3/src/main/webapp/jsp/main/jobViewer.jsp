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

	<div class="smallLabel">Job Name <s:property
			value="%{jobName}" /></div>
			
	<div class="smallLabel">Group Name <s:property
			value="%{groupName}" /></div>
	
	<sx:tabbedpanel id="edit">
	<s:iterator value="results" status="rowstatus">
		<sx:div id="reportTab_%{key}" label="%{key}">	
			<display:table name="value.rows"  pagesize="25" requestURI="viewJobOutput.action" export="true">
			    <display:setProperty name="export.rtf.filename" value="history.rtf"/>
			    <display:setProperty name="export.csv.filename" value="history.csv"/>
			    <display:setProperty name="export.xls" value="false" />
			    <display:setProperty name="export.xml.filename" value="history.xml"/>
			    <display:setProperty name="export.pdf.filename" value="history.pdf"/>		
			</display:table>
		</sx:div>
	</s:iterator>
	</sx:tabbedpanel>
</body>
</html>
	