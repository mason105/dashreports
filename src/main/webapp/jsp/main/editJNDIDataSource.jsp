<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>
<s:form action="saveDataSource">
<div class="formGroup">
	<div class="formGroupHeader">Data Source Details</div>
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

				
		<s:select label="Groups"
	       name="dataSourceGroups"
	       list="groups"
	       multiple="true"
	       listKey="groupName" 
	       listValue="groupName"  cssClass="textbox"	       
		/>
		
		<input type="button" value="Test" onclick="dojo.event.topic.publish('testButton');">

		<s:url id="validateUrl" action="testDataSource" /> 
		<sx:div showLoadingText="true" loadingText="Testing datasource..." id="validateQuery" href="%{validateUrl}" theme="ajax"  listenTopics="testButton" formId="saveDataSource" preload="false">
		Please click test
		</sx:div>
				
		<div class="formBottom">
		<div class="formFooterText">* required field</div>
		<s:submit value="Save" align="left"/>
		</div>
</div>
</s:form>
</body>
</html>