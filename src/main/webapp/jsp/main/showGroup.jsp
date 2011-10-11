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
		<table border="0" width="100%">
			<tr class="rowHeader">
		      		<td colspan='7'  class="rowHeader">
		      		<s:if test="!sessionUser.isReadOnly||sessionUser.isAdmin">
		      			<a href="setupEditJob.action?groupName=<s:property value="groupName" />"><img src="<s:url value='/images/icons/add.png'/>" align="absmiddle" />Add Job</a>
				</s:if>				
			      </td>
		      	</tr>	    
	        	<tr>
		      		<td class="headerCell">Job Name</td>
		      <td class="headerCell">Description</td>
		      <s:if test="!sessionUser.isReadOnly||sessionUser.isAdmin">
		      	<td class="headerCell" colspan="5">&nbsp;</td>
		      </s:if>
		      </tr>
			  <s:if test="jobs.size > 0">	
			  <s:iterator value="jobs">      
			     <tr>				
			      <td>  
				<img src="<s:url value="/images/icons/report.png"/>" align="absmiddle"/>&nbsp;<s:a href="setupViewJob.action?jobName=%{jobName}&groupName=%{groupName}"><s:property value="jobName" /></s:a>
			      </td>  
			      <td>  
				<s:property value="description" />  
			      </td>
			      <s:if test="!sessionUser.isReadOnly||sessionUser.isAdmin">
			      <td width="16">
			      <s:a href="viewJobDetail.action?jobName=%{jobName}&groupName=%{groupName}"><img src="<s:url value='/images/icons/magnifier.png'/>" alt="View Detail/History" align="absmiddle" /></s:a>
			      </td>
			      <td width="16">	
			      <s:a href="invokeJob.action?jobName=%{jobName}&groupName=%{groupName}"><img src="<s:url value='/images/icons/cog.png'/>" alt="Invoke Now" align="absmiddle" /></s:a>			
			      </td>
			      <td  width="16">				 
			      <s:a href="setupEditJob.action?jobName=%{jobName}&groupName=%{groupName}"><img src="<s:url value='/images/icons/pencil.png'/>" align="absmiddle" alt="Edit" /></s:a>
			      </td>
			      <td width="16">
			      <s:if test="isScheduled"><s:if test="isScheduleActive">
			      <s:a href="setJobStatus.action?jobName=%{jobName}&groupName=%{groupName}&jobStatus=false"><img src="<s:url value='/images/icons/clock_pause.png'/>" alt="Pause" align="absmiddle" /></s:a>
			      </s:if><s:else>
			      <s:a href="setJobStatus.action?jobName=%{jobName}&groupName=%{groupName}&jobStatus=true"><img src="<s:url value='/images/icons/clock_play.png'/>" alt="Resume" align="absmiddle" /></s:a>				 	
			      </s:else></s:if>
			      </td>			  
			      <td width="16">
			      <a href="deleteJob.action?jobName=<s:property value="jobName" />&groupName=<s:property value="groupName" />" onClick="return confirm('Really delete this job?');"><img src="<s:url value='/images/icons/delete.png'/>" alt="Delete" align="absmiddle" /></a>
			      </td>
			    </s:if>             
			    </tr>  
			  </s:iterator>
			  </s:if>             
		     </table>  
	</div>
</sj:tabbedpanel>
</body>
</html>