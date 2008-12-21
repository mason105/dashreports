<%@ taglib prefix="s" uri="/struts-tags" %>  
<html>
  <head>  
    <s:head/>  
  </head>  
  <body>  <%@ taglib prefix="s" uri="/struts-tags" %>  
<h1>Job List for Group: <s:property value="job.pk.group.groupName" /></h1>
<s:if test="jobs.size > 0">
	    <table>
	      <tr>
	      <td colspan=3>
	      <a href="setupEditJob.action">Add Job</a>
	      </td>
	      </tr>	    
          <s:iterator value="jobs">  
            <tr>  
              <td>  
                <s:property value="pk.jobName" />  
              </td>  
              <td>  
                <s:property value="description" />  
              </td>  
              <td>
				<a href="viewJobDetail.action?jobName=%{pk.jobName}">View Job</a>&nbsp;
				<a href="setupEditJob.action?jobName=%{pk.jobName}">Edit Job</a>&nbsp;
				<a href="deleteJob.action?jobName=%{pk.jobName}">Delete Job</a>				
              </td>
            </tr>  
          </s:iterator>
         </table>  
	</s:if>   
  </body>  
</html>     