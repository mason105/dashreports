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

<div id="groupPanel">
<div class="groupHeader"><img src="<s:url value='/images/v2/nav/groupsblue.png'/>" align="absmiddle" />&nbsp;<s:property value="groupName"/> </div>
<sj:tabbedpanel id="groupTabs"   name="groupTabs" animate="true" collapsible="false" useSelectedTabCookie="true">						
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
	
	<div class="jobList">
	<div class="jobListHeader">
		<img src="<s:url value='/images/v2/nav/reportsblue.png'/>" align="absmiddle" />&nbsp;Reports for <s:property value="groupName"/>
	</div>
	<div class="listingHeaderWide"> 	
		<s:if test="!sessionUser.isReadOnly||sessionUser.isAdmin">
		<div class="listingIcon"><img src="<s:url value='/images/v2/icons/add.png'/>" align="absmiddle" /></div>		      	
		<a href="setupEditJob.action?groupName=<s:property value="groupName" />">Add Report</a>
		</s:if>				
	</div>

	<div class="listing">
	
	<div class="listingHeaderRow">		
	 	<s:if test="!sessionUser.isReadOnly||sessionUser.isAdmin">
		<div class="listingIconCell" style="padding-left:5px;width:90px;border-right: 1px solid #cccccc;">Schedule</div>
		<div class="listingIconCell">&nbsp;</div>		
		<div class="listingIconCell">&nbsp;</div>					
		</s:if>
		<div class="listingCell">Report</div>
		<div class="listingCell">Description</div>
	</div>
		<s:iterator value="jobs">    
		<div class="listingRow">
			<s:if test="!sessionUser.isReadOnly||sessionUser.isAdmin">
			 <s:if test="isScheduled">
			<div class="listingIconCell"  style="padding-left:5px;width:90px;border-right: 1px solid #cccccc;background-color:#eeeeee">
			<s:a href="viewJobDetail.action?jobName=%{jobName}&groupName=%{groupName}"><img src="<s:url value='/images/v2/icons/jobhistory.png'/>" title="View Execution Detail/History" align="absmiddle" /></s:a>
			
			<s:a href="invokeJob.action?jobName=%{jobName}&groupName=%{groupName}"><img src="<s:url value='/images/v2/icons/execute.png'/>" title="Invoke Now" align="absmiddle" /></s:a>
		
			
			     <s:if test="isScheduleActive">
			      <s:a href="setJobStatus.action?jobName=%{jobName}&groupName=%{groupName}&jobStatus=false"><img src="<s:url value='/images/v2/icons/pause.png'/>" title="Pause Schedule" align="absmiddle" /></s:a>
			      </s:if><s:else>
			      <s:a href="setJobStatus.action?jobName=%{jobName}&groupName=%{groupName}&jobStatus=true"><img src="<s:url value='/images/v2/icons/play.png'/>" title="Resume Schedule" align="absmiddle" /></s:a>				 	
			      </s:else>
			</div>
			</s:if>
			<div class="listingIconCell">
				<a href="deleteJob.action?jobName=<s:property value="jobName" />&groupName=<s:property value="groupName" />" onClick="return confirm('Really delete this job?');"><img src="<s:url value='/images/v2/icons/delete.png'/>" title="Delete" align="absmiddle" /></a>
			</div>		
			<div class="listingIconCell">
			<s:a href="setupEditJob.action?jobName=%{jobName}&groupName=%{groupName}"><img src="<s:url value='/images/v2/icons/edit.png'/>" align="absmiddle" title="Edit" /></s:a>			
			</div>
			</s:if>			  
			<div class="listingCell">
				<s:a href="setupViewJob.action?jobName=%{jobName}&groupName=%{groupName}"><s:property value="jobName" /></s:a>
			</div>
			<div class="listingCell">
				<s:property value="description" />
			</div>
		</div>  
	</s:iterator>
	</div>
	</div>
	</div>
</sj:tabbedpanel>
</div>
</body>
</html>