<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
  
<html>
  <head>  
    <sx:head parseContent="true"/>  
  </head>  
  <body>  
  	<s:form action="saveDataSource">

  					<s:if test="dataSource.dataSourceName != null">
  					<s:textfield label="Data Source Name" size="32" value="%{dataSource.dataSourceName}" name="dataSource.dataSourceName"  readonly="true"/>
  					</s:if>
  					<s:else>
  					<s:textfield label="Data Source Name"  size="32" value="%{dataSource.dataSourceName}" name="dataSource.dataSourceName"/>
  					</s:else>  					
  					<s:textfield label="JNDI Name"  size="60" value="%{dataSource.jndiName}" name="dataSource.jndiName"/>
  			<tr>
  				<td>
  					OR
  				</td>
  			</tr>
  					<s:textfield label="JDBC Driver"  size="60" value="%{dataSource.jdbcClass}" name="dataSource.jdbcClass"/>
  					<s:textfield label="JDBC URL"  size="60" value="%{dataSource.jdbcUrl}" name="dataSource.jdbcUrl"/>
  					<s:textfield label="Username"  size="60" value="%{dataSource.username}" name="dataSource.username"/>
  					<s:textfield label="Password"  size="60" value="%{dataSource.password}" name="dataSource.password"/>
  					<s:textfield label="Initial Pool Size"  size="10" value="%{dataSource.initialPoolSize}" name="dataSource.initialPoolSize"/>
  					<s:textfield label="Min Pool Size"  size="10" value="%{dataSource.minPoolSize}" name="dataSource.minPoolSize"/>
  					<s:textfield label="Max Pool Size"  size="10" value="%{dataSource.maxPoolSize}" name="dataSource.maxPoolSize"/>
  					<s:submit />
  	 </s:form>
  	</body>
</html>