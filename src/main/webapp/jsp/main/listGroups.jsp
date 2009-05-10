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
	<table border="0" width="100%">
		<s:if test="sessionUser.isAdmin == true">
		<tr class="rowHeader"> 	
			<td colspan="4" class="rowHeader"><a href="setupEditGroup.action"><img
				src="<s:url value='/images/add_small.png'/>" align="absmiddle" />Add
			Group</a></td>
		</tr>
		</s:if>
		<s:if test="groups.size > 0">
			<%
				boolean rowOdd = true;
			%>
			<s:iterator value="groups">
				<%
					if (rowOdd) {
								rowOdd = false;
				%>
					<tr class="rowOdd">
				<%
					} else {
								rowOdd = true;
				%>			
					<tr class="rowEven">
				<%
					}
				%>
					<td><s:a href="listJobs.action?groupName=%{groupName}">
						<s:property value="groupName" />
					</s:a></td>
					<td><s:property value="groupDescription" /></td>				
					<s:if test="sessionUser.isAdmin == true">
						<td width="24"><s:a href="setupEditGroup.action?groupName=%{groupName}">
							<img src="<s:url value='/images/edit_small.png'/>" align="absmiddle" alt="Edit" />
						</s:a></td>
						<td width="24"><s:a href="deleteGroup.action?groupName=%{groupName}"  onClick="return confirm('Really delete this group and all jobs within it?');">
							<img src="<s:url value='/images/delete_small.png'/>" alt="Delete"
								align="absmiddle" />
						</s:a></td>
					</s:if>
					<s:else>
						<td></td>
						<td></td>
					</s:else>
				</tr>
			</s:iterator>
		</s:if>
	</table>
</div>
</body>
</html>
