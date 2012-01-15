<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>  
<body>    
  
<div class="formGroup">
	<div class="formGroupHeader">
		<img src="<s:url value='/images/v2/nav/datasourceblue.png'/>" align="absmiddle" />&nbsp;Manage Data Sources
	</div>
	<div class="listingHeader"> 	
		<div class="listingIcon"><img src="<s:url value='/images/v2/icons/add.png'/>" align="absmiddle" /></div>Add a <s:a href="setupEditDataSource.action">JDBC</s:a> or 
		<s:a href="setupEditJNDIDataSource.action">JNDI</s:a> Data Source
	</div>

	<div class="listing">
	
	<div class="listingHeaderRow">		
		<div class="listingIconCell">&nbsp;</div>
		<div class="listingIconCell">&nbsp;</div>		
		<div class="listingIconCell">&nbsp;</div>		
		<div class="listingCell">Data Source Name</div>
	</div>
	<s:iterator value="dataSources">
	<div class="listingRow">
		<div class="listingIconCell">
			<s:if test="jndiName==null || jndiName==''">
				<s:a href="setupEditDataSource.action?dataSourceName=%{dataSourceName}"><img src="<s:url value='/images/v2/icons/edit.png'/>" align="absmiddle" alt="Edit" /></s:a>
			</s:if>
			<s:else>
				<s:a href="setupEditJNDIDataSource.action?dataSourceName=%{dataSourceName}"><img src="<s:url value='/images/v2/icons/edit.png'/>" align="absmiddle" alt="Edit" /></s:a>
			</s:else>
		</div>
		<div class="listingIconCell"> <s:a href="purgeConnections.action?dataSourceName=%{dataSourceName}" onClick="return confirm('Really purge all connections for this datasource?');" ><img src="<s:url value='/images/v2/icons/purge.png'/>" align="absmiddle" alt="Edit" alt="Purge connections" /></s:a></div>
		<div class="listingIconCell"><s:a href="deleteDataSource.action?dataSourceName=%{dataSourceName}" onClick="return confirm('Really delete this datasource?');"><img src="<s:url value='/images/v2/icons/delete.png'/>" alt="Delete" align="absmiddle" /></s:a></div>		
		<div class="listingCell"><s:property value="dataSourceName" /></div>								
	</div>
	</s:iterator>	
	</div>
</div>  
</body>  
</html>  