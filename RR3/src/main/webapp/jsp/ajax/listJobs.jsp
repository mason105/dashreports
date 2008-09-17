<%@ taglib prefix="s" uri="/struts-tags" %>  
<h1>Job List</h1>
<s:if test="jobs.size > 0">
          <s:iterator value="jobs">  
            <tr>  
              <td>  
                <s:property value="pk.jobName" />  
              </td>  
              <td>  
                <s:property value="description" />  
              </td>  
            </tr>  
          </s:iterator>  
</s:if>      