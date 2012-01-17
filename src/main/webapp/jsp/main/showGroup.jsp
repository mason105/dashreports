<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
 	<sj:head locale="en" jqueryui="true" jquerytheme="smoothness"/>
	<sx:head parseContent="false" />
</head>
<body>
<sj:tabbedpanel id="groupTabs"  animate="true" collapsible="true" useSelectedTabCookie="true">						
	<sj:tab id="dashboardTab" label="Dashboard" target="dashDiv"/>
		<div id="dashDiv" style="overflow:auto">
		<div class="toolBar">
			<s:a href="setupEditChart.action?groupName=%{groupName}"><img align="absmiddle" src="<s:url value="/images/v2/icons/chart_add.png"/>"/>Add Chart</s:a>&nbsp;|
			<s:a href="setupEditGrid.action?groupName=%{groupName}"><img align="absmiddle" src="<s:url value="/images/v2/icons/grid_add.png"/>"/>Add Data Grid</s:a>&nbsp;|
			<s:a href="setupEditThreshold.action?groupName=%{groupName}"><img align="absmiddle" src="<s:url value="/images/v2/icons/threshold_add.png"/>"/>Add Threshold</s:a>
		</div>					
		<s:if test="items.size>0">		
			<div class="dashboard">							
				<s:iterator value="items" status="rowstatus">
					<s:if test="(displayRow!=#currentRow)">
						<div class="clearFix"></div>
						<s:set name="currentRow" value="%{displayRow}"/>							
					</s:if>						
					<sx:div theme="ajax" href="dashboardWidget.action?itemId=%{itemId}" updateFreq="%{visualRefreshTime}">
						<sx:div theme="ajax" href="dashboardWidget.action?itemId=%{itemId}"/>										
					</sx:div>	
				</s:iterator>
			</div>
		</s:if>
		</div>
	<sj:tab id="reportsTab" label="Reports" target="reportsDiv"/>	
	<div id="reportsDiv"  style="overflow:auto">
	
<div class="formGroup">
	<div class="formGroupHeader">
		<img src="<s:url value='/images/v2/nav/reportsblue.png'/>" align="absmiddle" />&nbsp;Reports for <s:property value="groupName"/>
	</div>
	<div class="listingHeader"> 	
		<s:if test="!sessionUser.isReadOnly||sessionUser.isAdmin">
		<div class="listingIcon"><img src="<s:url value='/images/v2/icons/add.png'/>" align="absmiddle" /></div>		      	
		<a href="setupEditJob.action?groupName=<s:property value="groupName" />">Add Report</a>
		</s:if>				
	</div>

	<div class="listing">
	
	<div class="listingHeaderRow">		
	 	<s:if test="!sessionUser.isReadOnly||sessionUser.isAdmin">
		<div class="listingIconCell">&nbsp;</div>		
		<div class="listingIconCell">&nbsp;</div>		
		<div class="listingIconCell">&nbsp;</div>
		<div class="listingIconCell">&nbsp;</div>
		<div class="listingIconCell">&nbsp;</div>					
		</s:if>
		<div class="listingCell">Report</div>
		<div class="listingCell">Description</div>
	</div>
		<s:iterator value="jobs">    
		<div class="listingRow">
			<s:if test="!sessionUser.isReadOnly||sessionUser.isAdmin">
			<div class="listingIconCell">
			<s:a href="viewJobDetail.action?jobName=%{jobName}&groupName=%{groupName}"><img src="<s:url value='/images/icons/magnifier.png'/>" alt="View Detail/History" align="absmiddle" /></s:a>
			</div>
			<div class="listingIconCell">
			<s:a href="invokeJob.action?jobName=%{jobName}&groupName=%{groupName}"><img src="<s:url value='/images/icons/cog.png'/>" alt="Invoke Now" align="absmiddle" /></s:a>
			</div>
			<div class="listingIconCell">
			<s:a href="setupEditJob.action?jobName=%{jobName}&groupName=%{groupName}"><img src="<s:url value='/images/icons/pencil.png'/>" align="absmiddle" alt="Edit" /></s:a>
			</div>
			<div class="listingIconCell">
			      <s:if test="isScheduled"><s:if test="isScheduleActive">
			      <s:a href="setJobStatus.action?jobName=%{jobName}&groupName=%{groupName}&jobStatus=false"><img src="<s:url value='/images/icons/clock_pause.png'/>" alt="Pause" align="absmiddle" /></s:a>
			      </s:if><s:else>
			      <s:a href="setJobStatus.action?jobName=%{jobName}&groupName=%{groupName}&jobStatus=true"><img src="<s:url value='/images/icons/clock_play.png'/>" alt="Resume" align="absmiddle" /></s:a>				 	
			      </s:else></s:if>
			</div>
			<div class="listingIconCell">
				<a href="deleteJob.action?jobName=<s:property value="jobName" />&groupName=<s:property value="groupName" />" onClick="return confirm('Really delete this job?');"><img src="<s:url value='/images/icons/delete.png'/>" alt="Delete" align="absmiddle" /></a>
			</div>
			</s:if>			  
			<div class="listingCell">
				<img src="<s:url value="/images/icons/report.png"/>" align="absmiddle"/>&nbsp;<s:a href="setupViewJob.action?jobName=%{jobName}&groupName=%{groupName}"><s:property value="jobName" /></s:a>
			</div>
			<div class="listingCell">
				<s:property value="description" />
			</div>
		</div>  
	</s:iterator>
	</div>
	</div>
</sj:tabbedpanel>
</body>
</html>