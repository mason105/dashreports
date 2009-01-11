<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
  <head>  
    <s:head/>  
  </head>  
  <body>    
      <h1>Data Sources</h1>  
  
        <table border="1">  
	      <tr>
	      <td colspan=3>
	      <a href="setupEditDataSource.action">Add Data Source</a>
	      </td>
	      </tr>
	      <s:if test="dataSources.size > 0">	            
          <s:iterator value="dataSources">  
            <tr>  
              <td>  
                <s:property value="dataSourceName" />
              </td>               
              <td>  
                <s:a href="setupEditDataSource.action?dataSourceName=%{dataSourceName}">Edit</s:a>  
              </td>  
              <td>  
                <s:a href="setupEditDataSource.action?dataSourceName=%{dataSourceName}">Delete</s:a>  
              </td>  
            </tr>  
          </s:iterator>
          </s:if>  
        </table>  
          
  </body>  
</html>  