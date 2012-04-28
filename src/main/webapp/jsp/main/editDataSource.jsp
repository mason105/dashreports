<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>
<sx:head parseContent="true" />

 	<sj:head locale="en" jqueryui="true" jquerytheme="%{themeName}"/>

</head>
<body>
<s:form action="saveDataSource">
	<div class="formGroup">
	<div class="formGroupHeader">Data Source Details</div>


		<s:actionerror theme="jquery"/>
		<s:actionmessage theme="jquery"/> <s:if
		test="dataSource.dataSourceName != null">
		<s:textfield label="Data Source Name" size="32"
			value="%{dataSource.dataSourceName}" name="dataSource.dataSourceName"
			required="true" readonly="true" cssClass="readonly, textbox" />
	</s:if> <s:else>
		<s:textfield label="Data Source Name" size="32"
			value="%{dataSource.dataSourceName}" name="dataSource.dataSourceName"  cssClass="textbox" />
	</s:else> <s:select
		label="Select Driver (please ensure the relevant JAR is on the classpath)"
		name="jdbcDriver" value="%{currentDriver}" list="drivers"
		cssClass="textbox"
		onchange="dojo.event.topic.publish('updateDriver');">
	</s:select> <s:url id="driverDetail" action="driverDetail" /> <sx:div
		showLoadingText="true" loadingText="Getting details..."
		id="validateQuery" href="%{driverDetail}" theme="ajax"
		listenTopics="updateDriver" formId="saveDataSource">
	</sx:div> <s:textfield label="Username" size="60" value="%{dataSource.username}"
		name="dataSource.username" cssClass="textbox" /> <s:password
		label="Password" size="60" name="dataSource.password"
		cssClass="textbox" /> <s:textfield label="Initial Pool Size" size="10"
		value="%{dataSource.initialPoolSize}"
		name="dataSource.initialPoolSize" /> <!--	<s:textfield label="Min Pool Size" size="5"
			value="%{dataSource.minPoolSize}" name="dataSource.minPoolSize" />-->
	<s:textfield label="Max Pool Size" size="5"
		value="%{dataSource.maxPoolSize}" name="dataSource.maxPoolSize" /> <s:select
		label="Groups" name="dataSourceGroups" list="groups" multiple="true"
		listKey="groupName" listValue="groupName" cssClass="textbox" />

	<div class="testBox">
	<input type="button" value="Test" class="testButton"
		onclick="dojo.event.topic.publish('testButton');"> <s:url
		id="validateUrl" action="testDataSource" /> <sx:div
		showLoadingText="true" loadingText="Testing datasource..."
		id="validateQuery" href="%{validateUrl}" theme="ajax"
		listenTopics="testButton" formId="saveDataSource" preload="false">
		Please click test
		</sx:div>
	</div>
	
	<div class="formBottom">
	<div class="formFooterText">* required field</div>
	<s:submit value="Save" align="left" /></div>
	</div>
</s:form>
</body>

</html>