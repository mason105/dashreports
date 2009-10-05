<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
  </head>  
  <body>    
<span class="pageTitle"><img src="<s:url value='/images/icons/error.png'/>" align="absmiddle" />Error!</span>
       	<p>
       	<s:actionerror />
		<s:actionmessage/>
        </p>
        
        <input type=button value="Back" onClick="history.go(-1)">
  </body>  
</html>  