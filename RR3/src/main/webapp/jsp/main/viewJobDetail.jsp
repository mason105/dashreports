<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
  
<html>
  <head>  
    <sx:head parseContent="true"/> 
  </head>  
  <body>  
	Job Name: <s:property value="%{job.jobName}" /><br/>
	Group Name: <s:property value="%{job.groupName}"/><br/>
	Job Description: <s:property value="%{job.description}"/><br/>
	Previous Run Time: <s:property value="%{job.previousRunTime}"/><br/>
	Next Run Time: <s:property value="%{job.nextRunTime}"/><br/>
	Schedule Active: <s:property value="%{job.isScheduleActive}"/><br/>
	<table>
		<tr>			
			<td>Event Id</td>
			<td>Time Stamp</td>
			<td>Message</td>
		</tr>
	<s:iterator value="events">
		 <tr>
		 	<td><s:property value="%{eventId}"/></td>
		 	<td><s:property value="%{timestamp}"/></td>
		 	<td><s:property value="%{message}"/></td>
		 </tr>
	</s:iterator>
	</table>
  </body>  
</html>  
