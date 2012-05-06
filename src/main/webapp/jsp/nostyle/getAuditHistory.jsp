<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<html>
<head>
<sj:head locale="en" jqueryui="true" jquerytheme="smoothness" />
</head>
<body>
	<div>

		<img
			src="<s:url value="getAuditChart.action">
		<s:param name="module" value="module"/>
		<s:param name="fromDate" value="fromDate.time"/>
		<s:param name="toDate" value="toDate.time"/>
		<s:param name="random" value="random"/>
	</s:url>
	" />
	</div>
	<Div>
		<sj:tabbedpanel id="stats" animate="true">


			<sj:tab id="failTab" label="Latest Failures" target="failDiv" />
			<div id="failDiv">
				<div style="overflow-x: auto; overflow-y: auto; overflow: auto;">
					<table>
						<tr>
							<th>Method</th>
							<th>Time-stamp</th>
							<th>Arguments</th>
							<th>User Name</th>
							<th>Run Time (ms)</th>
							<th>Error Text</th>
						</tr>
						<s:iterator value="latestFailEvents">
							<tr>
								<td><s:property value="method" /></td>
								<td><s:property value="timeStamp" /></td>
								<td><s:property value="arguments" /></td>
								<td><s:property value="userName" /></td>
								<td><s:property value="runTime" /></td>
								<td><s:property value="errorText" /></td>
							</tr>
						</s:iterator>
					</table>
				</div>
			</div>
			<s:if test="showLongest">
				<sj:tab id="longTab" label="Longest Running" target="longDiv" />
				<div id="longDiv">
					<div style="overflow-x: auto; overflow-y: auto; overflow: auto;">
						<table>
							<tr>
								<th>Success</th>
								<th>Method</th>
								<th>Time-stamp</th>
								<th>Arguments</th>
								<th>User&nbsp;Name</th>
								<th>Run&nbsp;Time&nbsp;(ms)</th>
								<th>Error&nbsp;Text</th>
							</tr>
							<s:iterator value="longestEvents">
								<tr>
									<td><s:if test="status.toString()=='SUCCESS'">
											<img src="<s:url value="/images/v2/icons/flag_green.png"/>" />
										</s:if>
										<s:else>
											<img src="<s:url value="/images/v2/icons/flag_red.png"/>" />
										</s:else>								
									</td>
									<td><s:property value="method" /></td>
									<td><s:property value="timeStamp" /></td>
									<td><s:property value="arguments" /></td>
									<td><s:property value="userName" /></td>
									<td><s:property value="runTime" /></td>
									<td><s:property value="errorText" /></td>
								</tr>
							</s:iterator>
						</table>
					</div>
				</div>
			</s:if>
		</sj:tabbedpanel>
	</Div>
</body>
</html>