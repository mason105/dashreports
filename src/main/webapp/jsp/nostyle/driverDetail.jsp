<%@ taglib prefix="s" uri="/struts-tags"%>

<s:textfield label="JDBC Driver" size="60"
			value="%{jdbcClass}" name="dataSource.jdbcClass"  cssClass="textbox"/>

<s:textfield label="JDBC URL" size="60" value="%{jdbcUrl}"
			name="dataSource.jdbcUrl"  cssClass="textbox"/>
			