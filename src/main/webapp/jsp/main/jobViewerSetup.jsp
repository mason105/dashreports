<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />

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
  
<script language="JavaScript" type="text/javascript">

	window.onload=function(){
		panel();
	}
	
	function panel() {
		new Jx.Panel({
			 image: '<s:url value="/images/icons/group.png"/>',
	        label: 'Groups',
	        content: 'mainContent',
	        minHeight: 400,
	        right:0
	    }).addTo('mainPanel');
    	
	}
</script>


</head>
<body>

<div id="mainPanel"></div>
<div id="mainContent">
<table><tr><Td>
<s:form action="viewJobOutput" method="post" enctype="multipart/form-data"
	validate="true">
	
	<s:hidden value="%{jobName}" name="jobName" />
	<s:hidden value="%{groupName}" name="groupName" />

	<div class="smallLabel">Job Name <s:property
			value="%{jobName}" /></div>
	<div class="smallLabel">Group Name <s:property
			value="%{groupName}" /></div>
	
	<s:iterator value="parameters" status="rowstatus">
	<div class="formSectionInner">
		<div class="smallLabel">Parameter Index <s:property
			value="%{pk.parameterIdx}" /></div>

		<div class="smallLabel"><s:property
			value="%{description}" /></div>
	
	<s:if test="(key.parameterBurstColumn == null)||(key.parameterBurstColumn.isEmpty())">					
			<s:textfield
				label="Value" name="parameters[%{#rowstatus.index}].parameterValue"
				value="%{key.parameterValue}">
			</s:textfield> 	
		
	</s:if> <s:else> 
			<s:select
				label="Value" name="parameters[%{#rowstatus.index}].parameterValue"
				value="%{key.parameterValue}"  list="value">
			</s:select> 
	</s:else>

		<s:hidden value="%{key.pk.parameterIdx}"
			name="parameters[%{#rowstatus.index}].pk.parameterIdx" /> 
		
		<s:hidden
		name="parameters[%{#rowstatus.index}].parameterBurstColumn"
		value="%{key.parameterBurstColumn}" />
		
		<s:hidden value="%{key.parameterType}"
				name="parameters[%{#rowstatus.index}].parameterType" /> 
			</div>
	 
	</s:iterator>
	
	<s:submit name="dispatchRunButton" value="Get Report" align="none" />
</s:form>
</td></tr></table>
</div>
</body>
</html>
	