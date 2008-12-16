<%@ taglib prefix="s" uri="/struts-tags" %>  
<html>
  <head>  
    <s:head/>  
  </head>  
  <body>    
      <h1>Groups</h1>  
      <s:if test="groups.size > 0">  
        <table border="1">  
          <s:iterator value="groups">  
            <tr>  
              <td>  
                <s:a href="listJobs.action?groupName=%{groupName}'"><s:property value="groupName" /></s:a>  
              </td>  
              <td>  
                <s:property value="groupDescription" />  
              </td>  
            </tr>  
          </s:iterator>  
        </table>  
      </s:if>    
  </body>  
</html>  