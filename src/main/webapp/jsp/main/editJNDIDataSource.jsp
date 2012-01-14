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
		
	<s:select label="JNDI Name"
					value="%{dataSource.jndiName}" name="dataSource.jndiName" 
					list="jndiNames" cssClass="textbox">
		</s:select>			

		<input type="button" value="Test" onclick="dojo.event.topic.publish('testButton');">

		<s:url id="validateUrl" action="testDataSource" /> 
		<sx:div showLoadingText="true" loadingText="Testing datasource..." id="validateQuery" href="%{validateUrl}" theme="ajax"  listenTopics="testButton" formId="saveDataSource" preload="false">
		Please click test
		</sx:div>
						
			
	<s:submit value="Save"/>
	</s:form>
</div>

</body>
</html>