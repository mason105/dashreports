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

   <!-- include the jx library and the delicious skin -->
  <script src="<s:url value='/jx/jxlib.js" type="text/javascript'/>" charset="utf-8"></script>
  <link rel="stylesheet" href="<s:url value='/jx/themes/delicious/jxtheme.css'/>" type="text/css" media="screen" charset="utf-8"/>
  <!-- IE specific style sheets -->
  <!--[if IE lte 6]>
  <link rel="stylesheet" href="<s:url value='/jx/themes/delicious/ie6.css'/>" type="text/css" media="screen" charset="utf-8"/>
  <![endif]-->
  <!--[if IE 7]>
  <link rel="stylesheet" href="<s:url value='/jx/themes/delicious/ie7.css'/>" type="text/css" media="screen" charset="utf-8"/>
  <![endif]-->	

<script type="text/javascript">
window.onload=function(){
	drawPanels();
}
function pop(id)
{
  NewWindow=window.open('<s:url value="/popupChart.action?alertId="/>'+id,'newWin','width=400,height=300,left=0,top=0,toolbar=No,location=No,scrollbars=No,status=No,resizable=Yes,fullscreen=No');
  NewWindow.focus();
}

function drawPanels() {
<s:if test="alerts.size>0">
	<s:iterator value="alerts" status="rowstatus">
		<s:iterator value="value" status="rowstatus">
			<s:if test="(displayType.name=='CHART')">
			
			
			  var popUp_<s:property value="%{id}"/> = new Jx.Dialog({
			        label: '<s:property value="%{alertName}"/>',
			        image: '<s:url value="/images/icons/chart_bar.png"/>',
			        modal: true, 
			        horizontal: '200 left', 
			        vertical: '50 top', 
					width: 850,
			        height: 700,			       
			        content: 'popUpDiv_<s:property value="%{id}"/>',
			        onOpen: function() { 
			        	popUp_<s:property value="%{id}"/>.toggleCollapse();
			        	popUp_<s:property value="%{id}"/>.toggleCollapse();
			           } , 
			        resize: false			        
			    });
				
				
				var tbTop_<s:property value="%{id}"/> = new Jx.Toolbar({position: 'top'}).add(	        	        			
        				new Jx.Button({label:'Zoom',
        				image: '<s:url value="/images/icons/zoom.png"/>',
        				onClick: popUp_<s:property value="%{id}"/>.open.bind(popUp_<s:property value="%{id}"/>)        				       			
        				})   		
	    		);
	    	  
	    		 /*
	    		 
	    		 */
				
	    			    	
			</s:if><s:else>
 
				/* possibilty implement jxGrid but it looks hard - might write a tag lib */
							
			</s:else>
		    new Jx.Panel({		    	
		        label: '<s:property value="%{alertName}"/>',
		        content: 'alert_<s:property value="%{id}"/>',		       
		        <s:if test="(chartSize.name=='Small')">			
		       		 height: 345,
		       		 width:340	       		 
		        </s:if><s:else>
		        	<s:if test="(chartSize.name=='Medium')">
						height: 495,		        
		        		width:530
		        	</s:if><s:else>
						height: 695,		        
		        		width:830	
		        	</s:else>
		        </s:else>
		        <s:if test="(displayType.name=='CHART')">
		        	,toolbars:[tbTop_<s:property value="%{id}"/>]
		        	,image: '<s:url value="/images/icons/chart_bar.png"/>',
		        </s:if><s:else>
		        	 ,image: '<s:url value="/images/icons/page_white_text.png"/>'
		        </s:else> 		       
		    }).addTo('panel_<s:property value="%{id}"/>');
		</s:iterator>
	</s:iterator>
</s:if>	
}

</script>
</head>
<body>

<s:if test="alerts.size>0">
	<sx:tabbedpanel id="groups">				
		<s:iterator value="alerts" status="rowstatus">
			<s:if test="value.size>0">		
				<sx:div id="group_%{key}" label="%{key}">						
					<div class="dashboard">							
						<s:iterator value="value" status="rowstatus">
							<s:if test="(displayRow!=#currentRow)">
								<div class="clearFix"></div>
								<s:set name="currentRow" value="%{displayRow}"/>							
							</s:if>			
							<div class="widgetContainer">
								<div id="panel_<s:property value="%{id}"/>"></div>
							</div>			
							 
							<s:if test="(displayType.name=='CHART')">	
								
								<div id="popUpDiv_<s:property value="%{id}"/>">
									<m:graph id="pop_chart_%{id}" width="800" height="600" align="middle" bgcolor="#FFFFFF"  url="getDashboardChartData.action?alertId=%{id}"/>
								</div>
												
								<div id="alert_<s:property value="%{id}"/>" class="alertBox">
							        <s:if test="(chartSize.name=='Small')">			
										<m:graph id="chart_%{id}" width="300" height="250" align="middle" bgcolor="#FFFFFF"
									 	url="/getDashboardChartData.action?alertId=%{id}" />							       		 	       		 
							        </s:if><s:else>
							        	<s:if test="(chartSize.name=='Medium')">
											<m:graph id="chart_%{id}" width="500" height="400" align="middle" bgcolor="#FFFFFF"
									 		url="/getDashboardChartData.action?alertId=%{id}" />											
							        	</s:if><s:else>
											<m:graph id="chart_%{id}" width="800" height="600" align="middle" bgcolor="#FFFFFF"
									 		url="/getDashboardChartData.action?alertId=%{id}" />							        	
							        	</s:else>
							        </s:else>											
								</div>
								
							</s:if><s:else>					 
								<div id="alert_<s:property value="%{id}"/>" class="alertBox">								
									<display:table name="currentDataset.rows"  requestURI="index.action" export="false">	
									</display:table>					
								</div>
							</s:else>
						</s:iterator>
					</div>
				</sx:div>
			</s:if>
		</s:iterator>
	</sx:tabbedpanel>
</s:if>

</body>
</html>
	