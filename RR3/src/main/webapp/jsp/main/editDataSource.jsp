<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
  
<html>
  <head>  
    <sx:head parseContent="true"/>  
  </head>  
  <body>  
  	<s:form action="saveDataSource">
  		<table>
  			<tr>
  				<td>
  					<s:if test="dataSource.dataSourceName != null">
  					Data Source Name:<s:textfield size="32" value="%{dataSource.dataSourceName}" name="dataSource.dataSourceName"  readonly="true"/>
  					</s:if>
  					<s:else>
  					Data Source Name:<s:textfield size="32" value="%{dataSource.dataSourceName}" name="dataSource.dataSourceName"/>
  					</s:else>  					
  				</td>
  			</tr>
  			<tr>
  				<td>
  					JNDI Name:<s:textfield size="60" value="%{dataSource.jndiName}" name="dataSource.jndiName"/>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					OR
  				</td>
  			</tr>
  			<tr>
  				<td>
  					JDBC Driver:<s:textfield size="60" value="%{dataSource.jdbcClass}" name="dataSource.jdbcClass"/>
  				</td>
  			</tr>  			
  			
  			<tr>
  				<td>
  					JDBC URL:<s:textfield size="60" value="%{dataSource.jdbcUrl}" name="dataSource.jdbcUrl"/>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					Username:<s:textfield size="60" value="%{dataSource.username}" name="dataSource.username"/>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					Password:<s:textfield size="60" value="%{dataSource.password}" name="dataSource.password"/>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					Initial Pool Size:<s:textfield size="10" value="%{dataSource.initialPoolSize}" name="dataSource.initialPoolSize"/>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					Min Pool Size:<s:textfield size="10" value="%{dataSource.minPoolSize}" name="dataSource.minPoolSize"/>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					Max Pool Size:<s:textfield size="10" value="%{dataSource.maxPoolSize}" name="dataSource.maxPoolSize"/>
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