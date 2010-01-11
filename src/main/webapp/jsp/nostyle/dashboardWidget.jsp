<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="o" uri="/ofc2" %>
<link href="<s:url value='/styles/displaytag.css'/>" rel="stylesheet" type="text/css" media="all" />

<!--<s:property value="gridData" escape="false" />-->

	<s:if test="(item.itemType.name=='Chart')">	
		
		<s:if test="(item.width.name=='Small')">			
			<s:set name="x" value="353"/>				       		 	       		 
		</s:if><s:else>
			<s:if test="(item.width.name=='Medium')">
					<s:set name="x" value="714"/>			
			</s:if><s:else>
				<s:set name="x" value="1075"/>
			</s:else>
		</s:else>		
	   
	   <s:if test="(item.height.name=='Small')">			
			<s:set name="y" value="293"/>															       		 	       		 
		</s:if><s:else>
			<s:if test="(item.height.name=='Medium')">
					<s:set name="y" value="594"/>						
			</s:if><s:else>
				<s:set name="y" value="895"/>	
			</s:else>
		</s:else>								        
	
		<div id="alert_<s:property value="%{item.itemId}"/>" class="alertBox_<s:property value="%{item.width}"/>_<s:property value="%{item.height}"/>">
			<img src="<s:url value="/images/icons/chart_bar.png"/>" style="float:left;padding-right:5px;" title="(Last Updated: <s:date name="%{alert.lastUpdated}" format="yyyy-MM-dd HH:mm:ss" />)"/> 
			<div class="widgetLabel"><s:property value="%{item.itemName}"/></div>
			<div class="widgetToolbar">
 				<s:if test="!sessionUser.isReadOnly||sessionUser.isAdmin">
 					 <s:a href="setupEditChart.action?itemId=%{itemId}&groupName=%{groupName}"><img src="<s:url value='/images/icons/pencil.png'/>" align="absmiddle" alt="Edit" /></s:a>
					 <s:a href="invokeItem.action?itemId=%{itemId}&groupName=%{groupName}"><img src="<s:url value='/images/icons/cog.png'/>" alt="Invoke Now" align="absmiddle" /></s:a> 					 
 					 <s:a href="deleteItem.action?itemId=%{itemId}&groupName=%{groupName}" onClick="return confirm('Really delete this item?');"><img src="<s:url value='/images/icons/delete.png'/>" alt="Delete" align="absmiddle" /></s:a>	
 				</s:if>		
			</div>	
			<o:graph id="chart_%{itemId}" width="%{x}" height="%{y}" dataUrl="/getDashboardChartData.action?itemId=%{item.itemId}" />											
		</div>

	</s:if>		
	<s:if test="(item.itemType.name=='Grid')">				 
		<div id="alert_<s:property value="%{item.itemId}"/>" class="alertBox_<s:property value="%{item.width}"/>_<s:property value="%{item.height}"/>">								
			<img src="<s:url value="/images/icons/script.png"/>" style="float:left;padding-right:5px;" title="(Last Updated: <s:date name="%{item.lastUpdated}" format="yyyy-MM-dd HH:mm:ss" />)"/> 
			<div class="widgetLabel"><s:property value="%{item.itemName}"/></div>
			<div class="widgetToolbar">
 				<s:if test="!sessionUser.isReadOnly||sessionUser.isAdmin">
 					 <s:a href="setupEditGrid.action?itemId=%{itemId}&groupName=%{groupName}"><img src="<s:url value='/images/icons/pencil.png'/>" align="absmiddle" alt="Edit" /></s:a>
					 <s:a href="invokeItem.action?itemId=%{itemId}&groupName=%{groupName}"><img src="<s:url value='/images/icons/cog.png'/>" alt="Invoke Now" align="absmiddle" /></s:a> 					 
 					 <s:a href="deleteItem.action?itemId=%{itemId}&groupName=%{groupName}" onClick="return confirm('Really delete this item?');"><img src="<s:url value='/images/icons/delete.png'/>" alt="Delete" align="absmiddle" /></s:a>	
 				</s:if>		
			</div>				
			<display:table name="item.currentDataset.rows"  requestURI="index.action" export="false">
			</display:table>
		</div>						
	</s:if>
	<s:if test="(item.itemType.name=='Threshold')">				 
		<div id="alert_<s:property value="%{item.itemId}"/>" class="alertBox_<s:property value="%{item.width}"/>_<s:property value="%{item.height}"/>">								
			<img src="<s:url value="/images/icons/script.png"/>" style="float:left;padding-right:5px;" title="(Last Updated: <s:date name="%{item.lastUpdated}" format="yyyy-MM-dd HH:mm:ss" />)"/> 
			<div class="widgetLabel"><s:property value="%{item.itemName}"/></div>
			<div class="widgetToolbar">
 				<s:if test="!sessionUser.isReadOnly||sessionUser.isAdmin">
 					 <s:a href="setupEditThreshold.action?itemId=%{itemId}&groupName=%{groupName}"><img src="<s:url value='/images/icons/pencil.png'/>" align="absmiddle" alt="Edit" /></s:a>
					 <s:a href="invokeItem.action?itemId=%{itemId}&groupName=%{groupName}"><img src="<s:url value='/images/icons/cog.png'/>" alt="Invoke Now" align="absmiddle" /></s:a> 					 
 					 <s:a href="deleteItem.action?itemId=%{itemId}&groupName=%{groupName}" onClick="return confirm('Really delete this item?');"><img src="<s:url value='/images/icons/delete.png'/>" alt="Delete" align="absmiddle" /></s:a>	
 				</s:if>		
			</div>				
			<display:table name="item.currentDataset.rows"  requestURI="index.action" export="false">
			</display:table>
		</div>								
	</s:if>