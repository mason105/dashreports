<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<sj:head locale="en" jqueryui="true" jquerytheme="%{themeName}"/>
</head>
<body>      

<div class="formGroup">
<div class="formGroupHeader"><span>About Dash Reports</span></div>
<sj:accordion autoHeight="false">
<sj:accordionItem title="Credits">
<table border="0" cellpadding="2" cellspacing="2" style="border-collapse: collapse;"  width="450"> 
<tr>
	<td width="50%"> 
		<strong>Lead Developer</strong>   
	</td>
	<td>    
		<a href="http://www.matthews-grout.co.uk" target="_blank">Daniel Matthews-Grout</a>
	</td>
</tr>
<tr>
	<td width="50%"> 
		<strong>Web Consultant</strong>   
	</td>
	<td>    
		<a href="mailto:john@johncook.me.uk">John Cook</a>
	</td>
</tr>
<tr>
	<td width="50%"> 
		<strong>Tester</strong>   
	</td>
	<td>    
		<a href="mailto:sam_j_nash@live.co.uk">Sam Nash</a>
	</td>
</tr>

<tr>
	<td width="50%"> 
		<strong>Icon Design</strong>   
	</td>
	<td>    
		<a href="http://www.doublejdesign.co.uk/" target="_blank">Double-J Design</a>
	</td>
</tr>
</table>
</sj:accordionItem>
<s:if test="sessionUser.isAdmin">
<sj:accordionItem title="System Information">
<table border="1" cellpadding="2" cellspacing="2" style="border-collapse: collapse;"  width="100%"> 
<tr>
<td colspan="2" align="center" bgcolor="#ccc"><strong>Server Properties</strong></td>
</tr>
<tr><td width="200"> 
<strong>Dash Reports Version</strong></td><td><s:property value="versionId"/></td>
</tr>
<tr>
<td width="200"> <strong>Java Version</strong></td><td><s:property value="javaVersion"/></td>
</tr>
<tr>
<td width="200"> <strong>Available Processors</strong></td><td><s:property value="processors"/></td>
</tr>
<tr>
<td width="200"> <strong>Total Memory (mb)</strong></td><td><s:property value="totalMem"/></td>
</tr>
<tr>
<td width="200"> <strong>Max Memory (mb)</strong></td><td><s:property value="maxMem"/></td>
</tr>
<tr>
<td width="200"> <strong>Free Memory (mb)</strong></td><td><s:property value="freeMem"/></td>
</tr>
<tr>
<td colspan="2" align="center" bgcolor="#ccc"><strong>Database Properties</strong></td>
</tr>
<tr>
<td><strong>Schema Name</strong></td>
<td><s:property value="databaseSchemaName"/></td>
</tr>

<s:iterator value="databaseProperties">
	<tr>
		<td><strong><s:property value="key"/></strong></td>
		<td><s:property value="value"/></td>
	</tr>
</s:iterator>

</table>
</sj:accordionItem>
</s:if>
<sj:accordionItem title="Third Party Modules">
<table border="0" cellpadding="2" cellspacing="2" style="border-collapse: collapse;" width="450" align="center">
	<tr> 
	<td width="33%" align="center"><a href="http://struts.apache.org/2.x/" target="_blank"><img src="<s:url value="/images/vendor/struts2.png"/>" border="0" align="middle"/></a></td>
	<td width="34%" align="center"><a href="http://maven.apache.org/" target="_blank"><img src="<s:url value="/images/vendor/maven-feather.png"/>" border="0" align="middle"/></a></td>
	<td width="33%" align="center"><a href="https://www.hibernate.org/" target="_blank"><img src="<s:url value="/images/vendor/site_logo.gif"/>" border="0" align="middle"/></a></td>
	</tr><tr>
	<td align="center"><a href="http://www.springsource.org/" target="_blank"><img src="<s:url value="/images/vendor/spring.png"/>" border="0" align="middle"/></a></td>
	<td align="center"><a href="http://displaytag.sourceforge.net/1.2/" target="_blank"><img src="<s:url value="/images/vendor/displaytag.png"/>" border="0" align="middle"/></a></td>
<td align="center"><a href="http://jasperforge.org/projects/jasperreports" target="_blank"><img src="<s:url value="/images/vendor/jasperreports.gif"/>" border="0" align="middle"/></a></td>
	</tr><tr>
	<td align="center"><a href="http://commons.apache.org/vfs/" target="_blank"><img src="<s:url value="/images/vendor/vfs-logo-white.png"/>" border="0" align="middle"/></a></td>
	<td align="center"><a href="http://www.jfree.org/jfreechart/index.html" target="_blank">JFree Chart</a></td>
	<td align="center" rowspan="2"><a href="http://code.google.com/p/struts2-jquery/" target="_blank"><img src="<s:url value="/images/vendor/s2jq.png"/>" border="0" align="middle" /></a></td>
	
	</tr>
	<tr>
	<td align="center"><a href="http://jquery.com/" target="_blank"><img src="<s:url value="/images/vendor/jquery.png"/>" border="0" align="middle"/></a></td>
	<td align="center"></td>
	
	</tr>
</table>	
</sj:accordionItem>

<sj:accordionItem title="Dash Reports License">
<pre>
    Copyright (C) 2009-2012  Daniel Matthews-Grout (dangrout@googlemail.com)

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
</sj:accordionItem>

<sj:accordionItem title="Third Party Licenses (Generated by Maven)">
<p><b>Public Domain:</b> AOP alliance</p>
<BR/>
<p><b>The Apache Software License, Version 2.0:</b> Ehcache Spring Annotations - Core, Quartz Enterprise Job Scheduler,Apache Log4j, Apache POI, Apache Velocity, Collections, Commons BeanUtils, Commons DBCP, Commons Digester, Commons Email, Commons IO, Commons Logging, Commons Pool, Commons VFS, Ehcache Core, FileUpload, Guava (Google Common Libraries), Lang, OGNL - Object Graph Navigation Library, StAX API, Streaming API for XML (STAX API 1.0), Struts 2 Core, Struts 2 Dojo Plugin, Struts 2 JFreeChart Plugin, Struts 2 Sitemesh Plugin, Struts 2 Spring Plugin, Struts 2 jQuery Plugin, XWork: Core, XmlBeans, spring-security-config, spring-security-core, spring-security-crypto, spring-security-web, Javassist</p>
<BR/>
<p><b>Unknown:</b> Castor, Javassist, dom4j, itext, jdtcore, jsp-api, jstl, jta, oro, servlet-api, spring-aop, spring-asm, spring-beans, spring-context, spring-context-support, spring-core, spring-expression, spring-jdbc, spring-orm, spring-test, spring-tx, spring-web, standard, xml-apis</p>
<BR/>
<p><b>BSD-style license:</b> FreeMarker</p>
<BR/>
<p><b>Eclipse Public License - v 1.0:</b> AspectJ runtime, AspectJ weaver</p>
<BR/>
<p><b>GNU General Public License (GPL) v3:</b> Dan Utils, Dash Reports</p>
<BR/>
<p><b>MPL 1.1:</b> Javassist</p>
<BR/>
<p><b>GNU Lesser General Public License:</b> A Hibernate Core Module, JasperReports</p>
<BR/>
<p><b>BSD License:</b> AntLR Parser Generator, PostgreSQL JDBC Driver</p>
<BR/>
<p><b>Common Public License Version 1.0:</b> JUnit</p>
<BR/>
<p><b>Common Development and Distribution License (CDDL) v1.0:</b> JavaBeans Activation Framework (JAF), JavaMail API</p>
<BR/>
<p><b>The GNU General Public License, Version 2:</b> MySQL java connector</p>
<BR/>
<p><b>GNU LESSER GENERAL PUBLIC LICENSE:</b> Hibernate Commons Annotations</p>
<BR/>
<p><b>The Apache Software License, Version 1.1:</b> Sitemesh</p>
<BR/>
<p><b>BSD:</b> ASM Commons, ASM Core, ASM Tree</p>
<BR/>
<p><b>LGPL 2.1:</b> Javassist</p>
<BR/>
<p><b>GNU Lesser General Public License, version 2.1:</b> JBoss Logging 3, Transaction 1.1 API, jcommon, jfreechart</p>
<BR/>
<p><b>Artistic License:</b> Display excel export module, Display tag library</p>
<BR/>
<p><b>MIT License:</b> SLF4J API Module, SLF4J LOG4J-12 Binding</p>
<BR/>
<p><b>GNU Lesser General Public License (LGPL), Version 2.1:</b> Data Mapper for Jackson, Jackson</p>
</sj:accordionItem>
</sj:accordion>
</div>
	<div class="formBottomEmpty"></div>
</body>
</html>	
    