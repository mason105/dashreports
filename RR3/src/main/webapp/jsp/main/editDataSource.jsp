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
  					<s:if test="dataSource.dataSourceName != null">
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
  					JDBC Driver:<s:textfield size="60" value="%{dataSource.jdbcClass}" name="jdbcClass"/>
  				</td>
  			</tr>  			
  			
  			<tr>
  				<td>
  					JDBC URL:<s:textfield size="60" value="%{dataSource.jdbcUrl}" name="jdbcUrl"/>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					Username:<s:textfield size="60" value="%{dataSource.username}" name="username"/>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					Password:<s:textfield size="60" value="%{dataSource.password}" name="password"/>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					Initial Pool Size:<s:textfield size="10" value="%{dataSource.jndiName}" name="initialPoolSize"/>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					Min Pool Size:<s:textfield size="10" value="%{dataSource.jndiName}" name="minPoolSize"/>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					Max Pool Size:<s:textfield size="10" value="%{dataSource.maxPoolSize}" name="maxPoolSize"/>
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