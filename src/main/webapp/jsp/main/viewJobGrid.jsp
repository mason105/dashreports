<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>
<link href="<s:url value='/styles/displaytag.css'/>" rel="stylesheet" type="text/css" media="all" />
 	<sj:head locale="en" jqueryui="true" jquerytheme="%{themeName}"/>

</head>
<body >
<div class="jobViewHeader"><img src="<s:url value='/images/v2/nav/groupsblue.png'/>" align="absmiddle" />&nbsp;<s:a href="showGroup.action?groupName=%{groupName}"><s:property value="groupName"/></s:a> > <s:a href="setupViewJob.action?groupName=%{groupName}&jobName=%{jobName}"><s:property value="jobName"/></s:a> 
</div>			
    
    <sj:tabbedpanel id="viewTabs">
    	<s:iterator value="gridResults"  status="rowstatus">
    	<sj:tab id="result%{#rowstatus.index}"  target="tab%{#rowstatus.index}" label="%{key}"/>
    	<div id="tab<s:property value="#rowstatus.index"/>">
    		<display:table name="value.rows"  export="false" >
			</display:table>
    	</div>
    	</s:iterator>
    </sj:tabbedpanel>
    
</body>
</html>
