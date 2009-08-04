<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<html>
<head>
<sx:head parseContent="true" />
<script type="text/javascript">

window.onresize=function() {
	window.location=window.location;
}
window.onload=function(){ 
	tree();
	drawTabs();	
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
        		
			    new Jx.Toolbar.Container().addTo(
			        'bar_<s:property value="%{key}"/>'
			    ).add(
			        new Jx.Toolbar().add(
			            new Jx.Button({label: 'Open in New Page',
			            image: '<s:url value="/images/icons/page_go.png"/>',
 						onClick: function() {
					        window.open('<s:url value="/dashboard.action?groupName=%{key}"/>');
    					}			            
			            })
			        )
			    );
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
					<div id="bar_<s:property value="%{key}"/>" class="tabBar"></div>
					<!--<iframe src="<s:url value="dashboard.action?groupName=%{key}"/>" frameborder="0" class="dashFrame"></iframe>-->
					<sx:div href="dashboard.action?groupName=%{key}" separateScripts="true"  executeScripts="true"/>
				</div>
			</s:if>
		</s:iterator>
	
</s:if>

</body>
</html>
	