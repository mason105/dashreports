<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
	<sj:head locale="en" jqueryui="true" jquerytheme="smoothness"/>

<script language="javascript">

	function populateGrids() {
	
	<s:iterator value="results" status="rowstatus">
	
		var mydata<s:property value="rowstatus"/> = [ 
		<s:iterator value="value.rows" var="rowItem">
			<s:if test="rowstatus>0">,</s:if>{
			<s:iterator value="columns.get(key)" var="colName">
				<s:property value="colName"/>:"<s:property value="rowItem.get(colName)"/>"
			</s:iterator>
		}
		</s:iterator> 	
		 ]; 

		for(var i=0;i<=mydata.length;i++) jQuery("#grid<s:property value="rowstatus"/>").jqGrid('addRowData',i+1,mydata<s:property value="rowstatus"/>[i]);
	
	</s:iterator>
	
	}

</script>
</head>
<body onload="populateGrids();">
<sj:tabbedpanel id="grids">
	<s:iterator value="results" status="rowstatus">
		<sj:tab id="gridTab%{rowstatus}" target="gridDiv%{rowstatus}"/>
		<div id="gridDiv<s:property value="rowstatus"/>">
			<sjg:grid id="#grid%{rowstatus}" dataType="local" caption="%{jobName}" gridModel="">
				<s:iterator value="columns" var="colName">
					<sjg:gridColumn name="%{colName}" index="%{colName}" title="%{colName}" sortable="false"/>
				</s:iterator>    	
			</sjg:grid>
    	</div>
	</s:iterator>
</sj:tabbedpanel>
</body>
</html>
