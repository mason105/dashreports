<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="m" uri="/connext" %>
<link href="<s:url value='/styles/displaytag.css'/>" rel="stylesheet" type="text/css" media="all" />

<!--<s:property value="gridData" escape="false" />-->
	
	<div class="widgetLabel"><s:property value="%{alert.alertName}"/></div>
	
	<s:if test="(alert.displayType.name=='CHART')">	
		
		<s:if test="(alert.width.name=='Small')">			
			<s:set name="x" value="340"/>				       		 	       		 
		</s:if><s:else>
			<s:if test="(alert.width.name=='Medium')">
					<s:set name="x" value="705"/>			
			</s:if><s:else>
				<s:set name="x" value="1070"/>
			</s:else>
		</s:else>		
	   
	   <s:if test="(alert.height.name=='Small')">			
			<s:set name="y" value="295"/>															       		 	       		 
		</s:if><s:else>
			<s:if test="(alert.height.name=='Medium')">
					<s:set name="y" value="450"/>						
			</s:if><s:else>
				<s:set name="y" value="600"/>	
			</s:else>
		</s:else>								        
	
		<div id="alert_<s:property value="%{alert.id}"/>" class="alertBox_<s:property value="%{alert.width}"/>_<s:property value="%{alert.height}"/>">
		<m:graph id="chart_%{id}" width="%{x}" height="%{y}" align="middle" bgcolor="#FFFFFF" url="/getDashboardChartData.action?alertId=%{alert.id}" />											
		</div>

	</s:if><s:else>			
				 
		<div id="alert_<s:property value="%{alert.id}"/>" class="alertBox_<s:property value="%{alert.width}"/>_<s:property value="%{alert.height}"/>">								
			<display:table name="alert.currentDataset.rows"  requestURI="index.action" export="false">
			</display:table>
		</div>						
	</s:else>
