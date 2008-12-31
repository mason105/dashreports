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
      <td colspan=5>
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
                <s:property value="jobName" />  
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
                <s:property value="isScheduled" />  
              </td>                                            
              <td>
				 <s:a href="viewJobDetail.action?jobName=%{jobName}&groupName=%{groupName}">View Job</s:a>&nbsp;
				 <s:a href="setupEditJob.action?jobName=%{jobName}&groupName=%{groupName}">Edit Job</s:a>&nbsp;
				 <s:a href="deleteJob.action?jobName=%{jobName}&groupName=%{groupName}">Delete Job</s:a>				
              </td>
            </tr>  
          </s:iterator>
	  </s:if>             
     </table>  

  </body>  
</html>     