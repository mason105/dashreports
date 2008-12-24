<%@ taglib prefix="s" uri="/struts-tags" %>  
<html>
  <head>  
    <s:head/>  
  </head>  
  <body>    
      <h1>Groups</h1>  
  
        <table border="1">  
	      <tr>
	      <td colspan=3>
	      <a href="setupEditGroup.action">Add Group</a>
	      </td>
	      </tr>
	      <s:if test="groups.size > 0">	            
          <s:iterator value="groups">  
            <tr>  
              <td>  
                <s:a href="listJobs.action?groupName=%{groupName}"><s:property value="groupName" /></s:a>  
              </td>  
              <td>  
                <s:property value="groupDescription" />  
              </td>  
              <td>  
                <s:a href="setupEditGroup.action?groupName=%{groupName}">Edit</s:a>  
              </td>  
              <td>  
                <s:a href="deleteGroup.action?groupName=%{groupName}">Delete</s:a>  
              </td>  
            </tr>  
          </s:iterator>
          </s:if>  
        </table>  
          
  </body>  
</html>  