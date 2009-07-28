<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<link href="<s:url value='/styles/displaytag.css'/>" rel="stylesheet" type="text/css" media="all" />
<display:table name="currentDataset.rows"  requestURI="index.action" export="false">	
</display:table>
<!--<s:property value="gridData" escape="false" />-->