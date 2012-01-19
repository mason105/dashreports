<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
	<sj:head locale="en" jqueryui="true" jquerytheme="smoothness"/>
</head>
<body>

        <sjg:grid
    	id="gridtable"
    	dataType="local"
 		caption="%{jobName}">
    	<s:iterator value="columns" var="colName">
    		<sjg:gridColumn name="%{colName}" index="%{colName}" title="%{colName}" sortable="false"/>
    	</s:iterator>    	
       </sjg:grid>

</body>
</html>
