<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" uri="/connext" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>


<html>
<head>
<sx:head parseContent="true" />
	<link href="<s:url value='/styles/displaytag.css'/>" rel="stylesheet" type="text/css" media="all" />

   <!-- include the jx library and the delicious skin -->
  <script src="<s:url value='/jx/jxlib.js" type="text/javascript'/>" charset="utf-8"></script>
  <link rel="stylesheet" href="<s:url value='/jx/themes/delicious/jxtheme.css'/>" type="text/css" media="screen" charset="utf-8"/>
  <!-- IE specific style sheets -->
  <!--[if lte IE 6]>
  <link rel="stylesheet" href="<s:url value='/jx/themes/delicious/ie6.css'/>" type="text/css" media="screen" charset="utf-8"/>
  <![endif]-->
  <!--[if gte IE 6]>
  <link rel="stylesheet" href="<s:url value='/jx/themes/delicious/ie7.css'/>" type="text/css" media="screen" charset="utf-8"/>
  <![endif]-->	

<script type="text/javascript">

	var timerID = 0;
	var reloadInterval=60000;
	var tryXML=true;
	
	
	function reload()
	{
		
		if (timerID)
		{
			clearTimeout(timerID);
		}
		<s:if test="alerts.size>0">
			<s:iterator value="alerts" status="rowstatus"> 
				<s:iterator value="value" status="rowstatus">
					<s:if test="(displayType.name=='CHART')">
						try {
							tmp = findSWF("chart_<s:property value="%{id}"/>");
							if (tmp != null) {
								x = tmp.reload("<s:url value="/getDashboardChartData.action?alertId=%{id}"/>", false);
							}
						} catch(err) {
						}			
					</s:if>
				</s:iterator>
			</s:iterator>
		</s:if>				
 		timerID = setTimeout("reload()", reloadInterval);
	}


	function findSWF(movieName)
	{
		if (navigator.appName.indexOf("Microsoft")!= -1) {
			return window[movieName];
		} else {
			return document[movieName];
		}
	}

	timerID  = setTimeout("reload()", reloadInterval);

window.onresize=function() {
	window.location=window.location;
}
window.onload=function(){
	drawPanels();	 
	drawTabs();	
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
					width: 1024,
			        height: 768,			       
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

			</s:if><s:else>
 
				/* possibilty implement jxGrid but it looks hard - might write a tag lib */
							
			</s:else>
		   var chart_<s:property value="%{id}"/>= new Jx.Panel({		    	
		        label: '<s:property value="%{alertName}"/>',
		        content: 'alert_<s:property value="%{id}"/>',		       
		        <s:if test="(width.name=='Small')">			
		       		 width:340,	       		 
		        </s:if><s:else>
		        	<s:if test="(width.name=='Medium')">		        
		        		width:662,
		        	</s:if><s:else>        
		        		width:1024,	
		        	</s:else>
		        </s:else>
		        <s:if test="(height.name=='Small')">			
		       		 height: 345		       		 
		        </s:if><s:else>
		        	<s:if test="(height.name=='Medium')">		        
		        		height: 500
		        	</s:if><s:else>        
		        		height: 768	
		        	</s:else>
		        </s:else>		        
		         
		        <s:if test="(displayType.name=='CHART')">
		        	,toolbars:[tbTop_<s:property value="%{id}"/>]
		        	,image: '<s:url value="/images/icons/chart_bar.png"/>'
		        </s:if><s:else>
		        	 ,image: '<s:url value="/images/icons/page_white_text.png"/>'
		        </s:else> 		       
		    });
		    
		    chart_<s:property value="%{id}"/>.addTo('panel_<s:property value="%{id}"/>');		    
		    chart_<s:property value="%{id}"/>.toggleCollapse();
		    chart_<s:property value="%{id}"/>.toggleCollapse();
		    chart_<s:property value="%{id}"/>.maximize();
		</s:iterator>
	</s:iterator>
</s:if>	
}

function drawTabs() {
var tb = new Jx.Toolbar({parent: 'toolbar'});
var tabSet = new Jx.TabSet('tabContentArea');

<s:if test="alerts.size>0">
	var i=0;
	var tab = new Array();
	<s:iterator value="alerts" status="rowstatus">
			<s:if test="value.size>0">
				i++;	
				tab[i]= (new Jx.Button.Tab({
            		image: '<s:url value="/images/icons/database_table.png"/>',
            		label: '<s:property value="%{key}"/>',            
            		content: 'group_<s:property value="%{key}"/>',            	
        		}));
        		tb.add(tab[i]); 
        		tabSet.add(tab[i]);
        		
			</s:if>
	</s:iterator>		
</s:if>			
}

</script>
</head>
<body>

<!-- containing divs for jxlib -->
<div id="toolbar"></div>
<div id="tabContain">
<div id="tabContentArea"></div>
</div>

<s:if test="alerts.size>0">
					
		<s:iterator value="alerts" status="rowstatus">
			<s:if test="value.size>0">		
				<div id="group_<s:property value="%{key}"/>">		
					<img src="<s:url value="/images/icons/shape_move_forwards.png"/>" align="absmiddle"/><a href="<s:url value="/popUpDashboard.action?groupName="/><s:property value="%{key}"/>" target="_blank">Pop Out</a>				
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
									<m:graph id="pop_chart_%{id}" width="995" height="720" align="middle" bgcolor="#FFFFFF"  url="getDashboardChartData.action?alertId=%{id}"/>
								</div>
												
								<div id="alert_<s:property value="%{id}"/>" class="alertBox">
							        <s:if test="(width.name=='Small')">			
							        	<s:set name="x" value="340"/>				       		 	       		 
							        </s:if><s:else>
							        	<s:if test="(width.name=='Medium')">
											<s:set name="x" value="655"/>			
							        	</s:if><s:else>
						        			<s:set name="x" value="1020"/>
							        	</s:else>
							        </s:else>		
						      	   <s:if test="(height.name=='Small')">			
							        	<s:set name="y" value="295"/>															       		 	       		 
							        </s:if><s:else>
							        	<s:if test="(height.name=='Medium')">
										 	<s:set name="y" value="450"/>						
							        	</s:if><s:else>
						        			<s:set name="y" value="600"/>	
							        	</s:else>
							        </s:else>								        
							        <m:graph id="chart_%{id}" width="%{x}" height="%{y}" align="middle" bgcolor="#FFFFFF"
									 	url="/getDashboardChartData.action?alertId=%{id}" />											
								</div>
								
							</s:if><s:else>					 
								<div id="alert_<s:property value="%{id}"/>" class="alertBox">								
									<sx:div theme="ajax" href="getDashboardGridData.action?alertId=%{id}" updateFreq="30000">
										<sx:div theme="ajax" href="getDashboardGridData.action?alertId=%{id}"/>										
									</sx:div>									
								</div>
							</s:else>
						</s:iterator>
					</div>
				</div>
			</s:if>
		</s:iterator>
	
</s:if>

</body>
</html>
	