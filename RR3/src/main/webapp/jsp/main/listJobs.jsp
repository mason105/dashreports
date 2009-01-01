<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
  
<html>
  <head>  
    <sx:head parseContent="true"/> 
  </head>  
  <body>  <%@ taglib prefix="s" uri="/struts-tags" %>  
	<h1>Job List for Group: <s:property value="groupName" /></h1>
    <table>
      <tr>
      <td colspan='5'>
      <a href="setupEditJob.action?groupName=<s:property value="groupName" />">Add Job</a>
      </td>
      </tr>	    
      <tr>
      <td class="headerCell">
      	Job Name
      </td>
      <td class="headerCell">
      	Description
      </td>
      <td class="headerCell">
      	Next Run Time
      </td>
      <td class="headerCell">
      	Previous Run Time
      </td>
      <td class="headerCell">
      	Is Scheduled
      </td>
      </tr>
	  <s:if test="jobs.size > 0">	      
          <s:iterator value="jobs">  
            <tr>  
              <td>  
                <s:a href="viewJobDetail.action?jobName=%{jobName}&groupName=%{groupName}"><s:property value="jobName" /></s:a>  
              </td>  
              <td>  
                <s:property value="description" />  
              </td>  
              <td>  
                <s:property value="nextRunTime" />  
              </td>  
              <td>  
                <s:property value="previousRunTime" />  
              </td>  
              <td>  
                <s:property value="isScheduleActive" />  
              </td>                                            
              <td>				 
				 <s:a href="setupEditJob.action?jobName=%{jobName}&groupName=%{groupName}">Edit</s:a>&nbsp;
				 <s:a href="deleteJob.action?jobName=%{jobName}&groupName=%{groupName}" onClick="return confirm('Really delete this job?');">Delete</s:a>
				 <s:if test="isScheduled">				
				 	<s:if test="isScheduleActive">
				 		<s:a href="setJobStatus.action?jobName=%{jobName}&groupName=%{groupName}&jobStatus=false">Pause</s:a>
				 	</s:if>
				 	<s:else>
				 		<s:a href="setJobStatus.action?jobName=%{jobName}&groupName=%{groupName}&jobStatus=true">Resume</s:a>				 	
				 	</s:else>
				 </s:if>	
				 <s:a href="invokeJob.action?jobName=%{jobName}&groupName=%{groupName}">Invoke</s:a>&nbsp;			
              </td>
            </tr>  
          </s:iterator>
	  </s:if>             
     </table>  

  </body>  
</html>     