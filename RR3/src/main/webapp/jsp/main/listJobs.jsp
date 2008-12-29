<%@ taglib prefix="s" uri="/struts-tags" %>  
<html>
  <head>  
    <s:head/>  
  </head>  
  <body>  <%@ taglib prefix="s" uri="/struts-tags" %>  
	<h1>Job List for Group: <s:property value="groupName" /></h1>
    <table>
      <tr>
      <td colspan=3>
      <a href="setupEditJob.action?groupName=<s:property value="groupName" />">Add Job</a>
      </td>
      </tr>	    
	  <s:if test="jobs.size > 0">	      
          <s:iterator value="jobs">  
            <tr>  
              <td>  
                <s:property value="pk.jobName" />  
              </td>  
              <td>  
                <s:property value="description" />  
              </td>  
              <td>
				<a href="viewJobDetail.action?jobName=%{pk.jobName}&groupName=%{groupName}">View Job</a>&nbsp;
				<a href="setupEditJob.action?jobName=%{pk.jobName}&groupName=%{groupName}">Edit Job</a>&nbsp;
				<a href="deleteJob.action?jobName=%{pk.jobName}&groupName=%{groupName}">Delete Job</a>				
              </td>
            </tr>  
          </s:iterator>
	  </s:if>             
     </table>  

  </body>  
</html>     