<%@ taglib prefix="s" uri="/struts-tags" %>  
<html>
  <head>  
    <s:head/>  
  </head>  
  <body>  
  	<s:form action="saveGroup">
  		<table>
  			<tr>
  				<td>
  					<s:if test="group.groupName != null">
  					Group Name:<s:textfield size="32" value="%{group.groupName}" name="groupName"  readonly="true"/>
  					</s:if>
  					<s:else>
  					Group Name:<s:textfield size="32" value="%{group.groupName}" name="groupName"/>
  					</s:else>  					
  				</td>
  			</tr>
  			<tr>
  				<td>
  					Group Description:<s:textfield size="60" value="%{group.groupDescription}" name="groupDescription"/>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					<s:submit />
  				</td>
  			</tr>  			
  		</table>
  	 </s:form>
  	</body>
</html>