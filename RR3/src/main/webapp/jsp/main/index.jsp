<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" uri="/connext" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<html>
<head>
<sx:head parseContent="true" />
	<link href="<s:url value='/styles/displaytag.css'/>" rel="stylesheet" type="text/css" media="all" />


<script type="text/javascript">

function pop(id)
{
  NewWindow=window.open('<s:url value="/popupChart.action?alertId="/>'+id,'newWin','width=400,height=300,left=0,top=0,toolbar=No,location=No,scrollbars=No,status=No,resizable=Yes,fullscreen=No');
  NewWindow.focus();
}

</script>
</head>
<body>

<s:if test="alerts.size>0">
	<sx:tabbedpanel id="groups">				
		<s:iterator value="alerts" status="rowstatus">
			<s:if test="value.size>0">		
			<sx:div id="group_%{key}" label="%{key}">			
				
				<s:set name="currRow" value="%{0}"/>
				
				<s:iterator value="value" status="rowstatus">
				<s:if test="(currRow!=displayRow)">	
					<s:set name="currRow" value="%{displayRow}" />
					<div class="clearFix"></div>
				</s:if>
				<s:if test="(displayType.name=='CHART')">				
					<s:if test="(chartSize.name=='Small')">		
						<div class="chartBoxSmall" id="alertBox_<s:property value="%{id}"/>">
								<div class="chartBorder">						
								<m:graph id="chart_%{id}" width="300" height="300" align="middle" bgcolor="#FFFFFF"
								  url="/getDashboardChartData.action?alertId=%{id}"
								/></div><div class="popUpBox"><span class="popUpText"><a href="javascript:pop(<s:property value="%{id}"/>);">pop up</a></span></div>					
						</div>											
					</s:if><s:else>
						<s:if test="(chartSize.name=='Medium')">
							<div class="chartBoxMedium" id="alertBox_<s:property value="%{id}"/>">
									<div class="chartBorder">
									<m:graph id="chart_%{id}" width="450" height="350" align="middle" bgcolor="#FFFFFF"
									  url="/getDashboardChartData.action?alertId=%{id}"
									/></div><div class="popUpBox"><span class="popUpText"><a href="javascript:pop(<s:property value="%{id}"/>);">pop up</a></span></div>					
							</div>						
														
						</s:if><s:else>							
							<div class="chartBoxLarge" id="alertBox_<s:property value="%{id}"/>">
									<div class="chartBorder">
									<m:graph id="chart_%{id}" width="800" height="600" align="middle" bgcolor="#FFFFFF"
									  url="/getDashboardChartData.action?alertId=%{id}"
									/></div>
									<div class="popUpBox"><span class="popUpText"><a href="javascript:pop(<s:property value="%{id}"/>);">pop up</a></span></div>
							</div>
														
						</s:else>
					</s:else>	
				</s:if><s:else>					 
					<div class="dataBox" id="alertBox_<s:property value="%{id}"/>">
						<span class="dataBoxHeader"><s:property value="%{alertName}"/></span>
						<display:table name="currentDataset.rows"  requestURI="index.action" export="false">	
						</display:table>					
					</div>
				</s:else>
				</s:iterator>
			</sx:div>
			</s:if>
		</s:iterator>
	</sx:tabbedpanel>
</s:if>

</body>
</html>
	