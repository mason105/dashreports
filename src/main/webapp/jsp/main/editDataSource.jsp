<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>

<div class="formGroup">
	<div class="formGroupHeader">Data Source Details</div>
	
	<s:form action="saveDataSource">
		<s:actionerror />
		<s:actionmessage/>
		<s:if test="dataSource.dataSourceName != null">
			<s:textfield label="Data Source Name" size="32"
				value="%{dataSource.dataSourceName}" name="dataSource.dataSourceName"  required="true"
				readonly="true"  cssClass="readonly, textbox" />
		</s:if>
		<s:else>
			<s:textfield label="Data Source Name" size="32"
				value="%{dataSource.dataSourceName}" name="dataSource.dataSourceName" />
		</s:else>
		<s:textfield label="JNDI Name" size="60" value="%{dataSource.jndiName}"	name="dataSource.jndiName"  cssClass="textbox"/>
			
			<table align="center">
			<tr><td>
				<div class="wwlbl">
					<div id="wwlbl_or" class="wwlbl">
						OR
					</div>
				</div>
			</td></tr>
			</table>
	
		<s:textfield label="JDBC Driver" size="60"
			value="%{dataSource.jdbcClass}" name="dataSource.jdbcClass"  cssClass="textbox"/>
		<s:textfield label="JDBC URL" size="60" value="%{dataSource.jdbcUrl}"
			name="dataSource.jdbcUrl"  cssClass="textbox"/>
		<s:textfield label="Username" size="60" value="%{dataSource.username}"
			name="dataSource.username"  cssClass="textbox"/>
		<s:textfield label="Password" size="60" value="%{dataSource.password}"
			name="dataSource.password"  cssClass="textbox"/>
		<s:textfield label="Initial Pool Size" size="10"
			value="%{dataSource.initialPoolSize}"
			name="dataSource.initialPoolSize"/>
		<s:textfield label="Min Pool Size" size="5"
			value="%{dataSource.minPoolSize}" name="dataSource.minPoolSize" />
		<s:textfield label="Max Pool Size" size="5"
			value="%{dataSource.maxPoolSize}" name="dataSource.maxPoolSize" />
	<s:submit value="Save"/>
	</s:form>
</div>
<div class="formFooterText">* required field</div>

</body>
</html>