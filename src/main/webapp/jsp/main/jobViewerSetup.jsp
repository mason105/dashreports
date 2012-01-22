<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
</head>
<body>


	<s:form action="viewJobOutput" method="post"
		enctype="multipart/form-data" validate="true">



		<s:hidden value="%{jobName}" name="jobName" />
		<s:hidden value="%{groupName}" name="groupName" />

		<div class="formGroup">
		<div class="formGroupHeader"><img src="<s:url value='/images/v2/nav/groupsblue.png'/>" align="absmiddle" />&nbsp;<s:a href="showGroup.action?groupName=%{groupName}"><s:property value="groupName"/></s:a> > View Job - 
			<s:property  value="jobName"/>			
</div>


			<s:if test="parameters.size>0">
			<div class="formGroupHeader">
				Set Parameters
			</div>

			<s:iterator value="parameters" status="rowstatus">

				<s:if
					test="(key.parameterBurstColumn == null)||(key.parameterBurstColumn.isEmpty())">
					<s:textfield label="%{key.description}"
						name="parameters[%{#rowstatus.index}].parameterValue"
						value="%{key.parameterValue}">
					</s:textfield>

				</s:if>
				<s:else>
					<s:select label="%{key.description}"
						headerKey="**********"
  						headerValue="-- All --"
						name="parameters[%{#rowstatus.index}].parameterValue"
						value="%{key.parameterValue}" list="value">
					</s:select>
				</s:else>

				<s:hidden value="%{key.pk.parameterIdx}"
					name="parameters[%{#rowstatus.index}].pk.parameterIdx" />

				<s:hidden
					name="parameters[%{#rowstatus.index}].parameterBurstColumn"
					value="%{key.parameterBurstColumn}" />

				<s:hidden value="%{key.parameterType}"
					name="parameters[%{#rowstatus.index}].parameterType" />

			</s:iterator>
			</s:if>
			<s:submit value="View Report" align="left" />

		</div>
<div class="formBottomEmpty"></div>

	</s:form>
</body>
</html>
