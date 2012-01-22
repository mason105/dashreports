<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>

<sj:head/>


<link rel="stylesheet" type="text/css" media="screen" href="<s:url value="/js/jqGrid/css/ui.jqgrid.css"/>" />


<script src="<s:url value="/js/jqGrid/js/i18n/grid.locale-en.js"/>" type="text/javascript"></script>
<script src="<s:url value="/js/jqGrid/js/jquery.jqGrid.min.js"/>" type="text/javascript"></script>

<script type='text/javascript'>

	

	function populateGrids() {
	
	<s:iterator value="gridResults"  status="rowstatusTab">
		var mydata<s:property value="#rowstatusTab.index"/> = [ 
		<s:iterator value="value" var="row"  status="rowstatusRow">
			<s:if test="#rowstatusRow.index>0">,</s:if>{			
				<s:iterator value="row" status="rowStatusCol">
				<s:if test="#rowStatusCol.index>0">,</s:if>
					<s:property value="key"/>:"<s:property value="value"/>"
				</s:iterator>
		}
		</s:iterator> 	
		 ]; 

		for(var i=0;i<=mydata<s:property value="#rowstatusTab.index"/>.length;i++) jQuery('#mygrid<s:property value="#rowstatusTab.index"/>').jqGrid('addRowData',i+1,mydata<s:property value="#rowstatusTab.index"/>[i]);	
	</s:iterator>
	}

</script>
</head>
<body >
	<s:iterator value="columns" status="rowstatus">

    	
    	<table  id="mygrid<s:property value="#rowstatus.index"/>" class="scroll" ></table>
    	<div id="mygridpager<s:property value="#rowstatus.index"/>"></div>
    	<script type='text/javascript'>
    	
    			jQuery("#mygrid<s:property value="#rowstatus.index"/>").jqGrid({ datatype: "local", 
    				
    				colNames:[
					<s:iterator value="value" var="colName" status="rowstatusCol">	
					<s:if test="#rowstatusCol.index>0">,</s:if>
					'<s:property value="colName"/>'
    				</s:iterator>
    				], 
    				colModel:[ 
					<s:iterator value="value" var="colName" status="rowstatusCol">	
					<s:if test="#rowstatusCol.index>0">,</s:if>
    				{name:'<s:property value="colName"/>',index:'<s:property value="colName"/>'}  
    				</s:iterator>
    				]
    			, multiselect: false,rowList:[10,20,30],pager: '#mygridpager<s:property value="#rowstatus.index"/>',viewrecords: true
    			,autowidth:true
    			,caption: "<s:property value="key"/>"}) ;
    	    
    			jQuery("#mygrid<s:property value="#rowstatus.index"/>").jqGrid('navGrid','#mygridpager<s:property value="#rowstatus.index"/>',{edit:false,add:false,del:false});
    	</script>
	</s:iterator>
	<script type="text/javascript">
		populateGrids();
	</script>
</body>
</html>
