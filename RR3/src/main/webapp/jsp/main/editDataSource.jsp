<%@ taglib prefix="s" uri="/struts-tags" %>  
<html>
  <head>  
    <s:head/>  
  </head>  
  <body>  
  	<s:form action="saveDataSource">
  		<table>
  			<tr>
  				<td>
  					<s:if test="dataSource.dataSourceName != ''">
  					Data Source Name:<s:textfield size="32" value="%{dataSource.dataSourceName}" name="dataSourceName"  readonly="true"/>
  					</s:if>
  					<s:else>
  					Data Source Name:<s:textfield size="32" value="%{dataSource.dataSourceName}" name="dataSourceName"/>
  					</s:else>  					
  				</td>
  			</tr>
  			<tr>
  				<td>
  					JNDI Name:<s:textfield size="60" value="%{dataSource.jndiName}" name="jndiName"/>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					OR
  				</td>
  			</tr>
  			<tr>
  				<td>
  					  <s:select label="Select JDBC Driver" 
    					name="jdbcClass" 
    					headerKey="1"
    					headervalue="-- Please Select --"
    					value="%{dataSource.jdbcClass}"
    					list="jdbcDriverNames"
    				   />
  				</td>
  			</tr>  			
  			<tr>
  				<td>
  					<s:submit />
  				</td>
  			</tr>  			
  		</table>
  	 </s:form>
  	</body>
</html>