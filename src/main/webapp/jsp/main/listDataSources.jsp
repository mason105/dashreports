<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
  </head>  
  <body>    
<span class="pageTitle"><img
	src="<s:url value='/images/datasource.png'/>" align="middle" />Datasources</span>
       
	 <table border="0" width="100%">
		<tr class="rowHeader"> 
			<td colspan="4" class="rowHeader"><s:a href="setupEditDataSource.action"><img
				src="<s:url value='/images/add_small.png'/>" align="absmiddle" />Add Datasource</s:a></td>
		 </tr>

	      <s:if test="dataSources.size > 0">	            
	<%
			boolean rowOdd = true;
		%>
		 <s:iterator value="dataSources">  
			<%
				if (rowOdd) {
							rowOdd = false;
			%>
				<tr class="rowOdd">
			<%
				} else {
							rowOdd = true;
			%>			
				<tr class="rowEven">
			<%
				}
			%>         
         
              <td>  
                <s:property value="dataSourceName" />
              </td>               
              <td width="24">  
                <s:a href="setupEditDataSource.action?dataSourceName=%{dataSourceName}"><img src="<s:url value='/images/edit_small.png'/>" align="absmiddle" alt="Edit" /></s:a>  
              </td>  
              <td width="24">  
                <s:a href="setupEditDataSource.action?dataSourceName=%{dataSourceName}"><img src="<s:url value='/images/delete_small.png'/>" alt="Delete"
							align="absmiddle" /></s:a>  
              </td>  
            </tr>  
          </s:iterator>
          </s:if>  
        </table>  
          
  </body>  
</html>  