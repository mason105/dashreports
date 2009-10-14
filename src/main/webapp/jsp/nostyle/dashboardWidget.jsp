<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="m" uri="/connext" %>
<link href="<s:url value='/styles/displaytag.css'/>" rel="stylesheet" type="text/css" media="all" />

<!--<s:property value="gridData" escape="false" />-->
	
	
	
	<s:if test="(alert.displayType.name=='CHART')">	
		
		<s:if test="(alert.width.name=='Small')">			
			<s:set name="x" value="353"/>				       		 	       		 
		</s:if><s:else>
			<s:if test="(alert.width.name=='Medium')">
					<s:set name="x" value="714"/>			
			</s:if><s:else>
				<s:set name="x" value="1075"/>
			</s:else>
		</s:else>		
	   
	   <s:if test="(alert.height.name=='Small')">			
			<s:set name="y" value="293"/>															       		 	       		 
		</s:if><s:else>
			<s:if test="(alert.height.name=='Medium')">
					<s:set name="y" value="594"/>						
			</s:if><s:else>
				<s:set name="y" value="895"/>	
			</s:else>
		</s:else>								        
	
		<div id="alert_<s:property value="%{alert.id}"/>" class="alertBox_<s:property value="%{alert.width}"/>_<s:property value="%{alert.height}"/>">
			<img src="<s:url value="/images/icons/chart_bar.png"/>" style="float:left;padding-right:5px;" title="(Last Updated: <s:date name="%{alert.lastUpdated}" format="yyyy-MM-dd hh:mm:ss" />)"/> 
			<div class="widgetLabel"><s:property value="%{alert.alertName}"/></div>
			<m:graph id="chart_%{id}" width="%{x}" height="%{y}" align="middle" bgcolor="#FFFFFF" url="/getDashboardChartData.action?alertId=%{alert.id}" />											
		</div>

	</s:if><s:else>			
				 
		<div id="alert_<s:property value="%{alert.id}"/>" class="alertBox_<s:property value="%{alert.width}"/>_<s:property value="%{alert.height}"/>">								
			<img src="<s:url value="/images/icons/script.png"/>" style="float:left;padding-right:5px;" title="(Last Updated: <s:date name="%{alert.lastUpdated}" format="yyyy-MM-dd hh:mm:ss" />)"/> 
			<div class="widgetLabel"><s:property value="%{alert.alertName}"/></div>
			<display:table name="alert.currentDataset.rows"  requestURI="index.action" export="false">
			</display:table>
		</div>						
	</s:else>
