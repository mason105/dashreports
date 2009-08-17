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

<span class="pageTitle"><img src="<s:url value='/images/icons/information.png'/>" align="absmiddle" />About Report Runner</span>
       

<div class="formGroup">
<div class="formGroupHeader">Version</div>
<s:property value="versionId"/>
</div>

<div class="formGroup">
<div class="formGroupHeader">Credits</div>
<table border="0" cellpadding="2" cellspacing="2" style="border-collapse: collapse;"  width="450"> 
<tr>
	<td width="50%"> 
		<strong>Lead Developer</strong>   
	</td>
	<td>    
		<a href="mailto:daniel@daniel-grout.co.uk">Daniel Grout</a>
	</td>
</tr>
<tr>
	<td> 
		<strong>Website Design</strong>   
	</td>
		<td>    
		<a href="mailto:john@johncook.me.uk">John Cook</a>
	</td>
</tr>
<tr>
	<td> 
		<strong>Graphic Design</strong>   
	</td>
		<td>    
		<a href="mailto:geord@hotmail.co.uk">Andrew Hall</a>
	</td>
</tr>
</table>
</div>

<div class="formGroup">
<div class="formGroupHeader">Report Runner Uses the Following Technologies and Frameworks</div>
<table border="1" cellpadding="2" cellspacing="2" style="border-collapse: collapse;" width="450">
	<tr> 
	<td width="33%" align="center"><a href="http://struts.apache.org/2.x/" target="_blank"><img src="<s:url value="/images/vendor/struts2.png"/>" border="0" align="middle"/></a></td>
	<td width="34%" align="center"><a href="http://maven.apache.org/" target="_blank"><img src="<s:url value="/images/vendor/maven-feather.png"/>" border="0" align="middle"/></a></td>
	<td width="33%" align="center"><a href="https://www.hibernate.org/" target="_blank"><img src="<s:url value="/images/vendor/site_logo.gif"/>" border="0" align="middle"/></a></td>
	</tr><tr>
	<td align="center"><a href="http://www.springsource.org/" target="_blank"><img src="<s:url value="/images/vendor/spring.png"/>" border="0" align="middle"/></a></td>
	<td align="center"><a href="http://displaytag.sourceforge.net/1.2/" target="_blank"><img src="<s:url value="/images/vendor/displaytag.png"/>" border="0" align="middle"/></a></td>
	<td align="center"><h4><a href="http://www.jxlib.org/" target="_blank">JxLib</a></h4></td>
	</tr><tr>
	<td align="center"><a href="http://commons.apache.org/vfs/" target="_blank"><img src="<s:url value="/images/vendor/vfs-logo-white.png"/>" border="0" align="middle"/></a></td>
	<td align="center"><a href="http://teethgrinder.co.uk/open-flash-chart/" target="_blank"><img src="<s:url value="/images/vendor/openflashchart.png"/>" border="0" align="middle"/></a></td>
	<td align="center"><a href="" target="_blank"><img src="<s:url value="/images/vendor/jasperreports.gif"/>" border="0" align="middle"/></a></td>
	</tr>
</table>	
</div>

<div class="formGroup">
<div class="formGroupHeader">License</div>
<pre>
    Copyright (C) 2009  Daniel Grout (daniel@daniel-grout.co.uk)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see http://www.gnu.org/licenses/.
</pre>
</div>


</body>
</html>	
    