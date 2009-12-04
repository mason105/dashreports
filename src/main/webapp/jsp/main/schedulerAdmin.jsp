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

<span class="pageTitle"><img src="<s:url value='/images/icons/clock.png'/>" align="absmiddle" />Scheduler Administration</span>
       

<div class="formGroup">
<div class="formGroupHeader">Summary</div>
<pre><s:property value="summary"/></pre>
</div>

<div class="formGroup">
<div class="formGroupHeader">Scheduler Infomation</div>

<table border="0" cellpadding="2" cellspacing="5" width="100%"> 
<tr>
	<td><strong>Job Count</strong></td>
	<td><s:property value="jobCount"/></td>
</tr>
<tr>
	<td><strong>Jobs Executed</strong></td>
	<td><s:property value="jobsExectuted"/></td>
</tr>
<tr>
	<td><strong>Running Since</strong></td>
	<td><s:date name="runningSince"/></td>
</tr>

</table>

</div>

<div class="formGroup">
<div class="formGroupHeader">Scheduler Status</div>

<s:form action="schedulerStateChange" method="post" enctype="multipart/form-data" validate="false">
	<s:select label="Scheduler Status" name="schedulerState" value="%{schedulerState}" list="#{0:'Shutdown',2:'Started'}"></s:select>	
	<s:submit/>		
</s:form>

</div>

</body>
</html>	
    