<%@ taglib prefix="s" uri="/struts-tags" %>  
<html>
  <head>  
    <s:head/> 
<script language="javascript">
		var maxParamRow=<s:property value="paramCount" />;
		function addParameter() {
			var ni = document.getElementById('parameterArea');
			var newdiv = document.createElement('div');	
			maxParamRow++;
			var divIdName = 'my'+maxParamRow;
			newdiv.setAttribute('id',divIdName);
			var innerHtml='';
				innerHtml=innerHtml+'<tr>';
				innerHtml=innerHtml+'<td valign="middle"  class="label">Index</td><td valign="middle"  class="input">  <input readonly="true" type=\"text\" name=\"parameterList[' + maxParamRow + '].pk.parameterIdx\" value=\"' + maxParamRow + '\"></td>';
				innerHtml=innerHtml+'<td valign="middle"  class="label">Value</td><td valign="middle"  class="input">  <input type=\"text\" name=\"parameterList[' + maxParamRow + '].parameterValue\" value=\"\"></td>';
				innerHtml=innerHtml+'<td valign="middle"  class="label">Burst Column</td><td valign="middle"  class="input">  <input type=\"text\" name=\"parameterList[' + maxParamRow + '].parameterBurstColumn\" value=\"\"></td>';
				innerHtml=innerHtml+'<td valign="middle"  class="label">Data Type</td><td valign="middle"  class="input">';  
				innerHtml=innerHtml+'<select '; 
				innerHtml=innerHtml+'name=\"parameterList[' + maxParamRow + '].parameterType\">'; 
				innerHtml=innerHtml+'<option value=\"1\">String</option><option value=\"2\">Date</option><option value=\"3\">Boolean</option><option value=\"4\">Integer</option><option value=\"5\">Long</option><option value=\"6\">Double</option>';
				innerHtml=innerHtml+'value=\"\"></select>';
				innerHtml=innerHtml+'</td><td></td>';				
				innerHtml=innerHtml+'</tr>';
			newdiv.innerHTML=innerHtml;
			ni.appendChild(newdiv);
	
		}
		function removeParameter(paramNum) {
  			var d = document.getElementById('parameterArea');
  			var olddiv = document.getElementById('param'+paramNum);
  			d.removeChild(olddiv);		
		}
	</script>
  </head>  
  <body>  
<s:actionerror />
<s:fielderror />  
  	<s:form action="saveJob" method="post" enctype="multipart/form-data">
  		<table>
  			<tr>
				<td valign="middle" class="label">Job Name</td>  			
  				<td valign="middle"  class="input">
  					<s:if test="job.pk.jobName != null">
  					<s:textfield size="32" value="%{job.pk.jobName}" name="jobName" readonly="true"/>
  					</s:if>
  					<s:else>
  					<s:textfield size="32" value="%{job.pk.jobName}" name="jobName"/>
  					</s:else>
  					<s:hidden value="%{groupName}" name="groupName"/>
  				</td>
  			</tr>
  			<tr>
				<td valign="middle"  class="label">Description</td>  			
  				<td valign="middle"  class="input">
  					<s:textfield size="128" value="%{job.description}" name="description"/>
  				</td>
  			</tr>
  			<tr>
				<td valign="middle"  class="label">Output URL</td>  			
  				<td valign="middle"  class="input">
  					<s:textfield size="128" value="%{job.outputUrl}" name="outputUrl"/>
  				</td>
  			</tr>
  			<tr>
				<td valign="middle"  class="label">Select Data Source</td>  			
  				<td valign="middle"  class="input">
  					  <s:select
    					name="dataSourceName" 
    					headerKey="0"
    					headerValue="-- Please Select --"
    					value="%{job.dataSource.dataSourceName}"
    					list="dataSources"
    				   />
  				</td>
  			</tr>
  			
  			<tr>
				<td valign="middle"  class="label">Report Query</td>  			
  				<td valign="middle"  class="input">
  					<s:textarea cols="20" rows="20" value="%{job.query}" name="query"/>
  				</td>
  			</tr>
  			
  			<tr>
				<td valign="middle"  class="label">Start Date Time</td>  			
  				<td valign="middle"  class="input">
  					<s:datetimepicker value="%{job.startDate}" name="startDate" displayFormat="dd-MM-yyyy HH:mm:ss" />
  				</td>
  			</tr>
  			
  			<tr>
				<td valign="middle"  class="label">End Date/Time</td>  			
  				<td valign="middle"  class="input">
  					<s:datetimepicker value="%{job.endDate}" name="endDate" displayFormat="dd-MM-yyyy HH:mm:ss" />
  				</td>
  			</tr>
  
  			<tr>
				<td valign="middle"  class="label">Cron String</td>  			
  				<td valign="middle"  class="input">
  					<s:textfield size="32" value="%{job.cronString}" name="cronString"/>
  				</td>
  			</tr>

  			<tr>
				<td valign="middle"  class="label"><Is Bursted Report/td>  			
  				<td valign="middle"  class="input">
  					<s:checkbox value="%{job.isBurst}" name="isBurst"/>
  				</td>
  			</tr>
  			
  			<tr>
				<td valign="middle"  class="label">Burst Query</td>  			
  				<td valign="middle"  class="input">
  					<s:textarea cols="20" rows="20" value="%{job.burstQuery}" name="burstQuery"/>
  				</td>
  			</tr>
  			<tr>
				<td valign="middle"  class="label">File Name from Burst Column</td>  			
  				<td valign="middle"  class="input">
  					<s:textfield size="32" value="%{job.burstFileNameParameterName}" name="burstFileNameParameterName"/>
  				</td>
  			</tr>
  				<tr>
  				<td valign="middle"  colspan=2 clas="label">
  					Parameters
  				</td>
  			</tr>
  			<tr>
  				<td valign="middle"  colspan=2>
  					<a href="#" onClick="addParameter();">Add Parameter</a>
  				</td>
  			</tr>
  			<tr>
  			<td valign="middle"  colspan=2>
  			<div id="parameterArea">
			<s:iterator value="job.parameters" status="rowstatus">				
				<div id="param%{#rowstatus.index}">
				<table>
				<tr>																
					<td valign="middle"  class="label">Index</td><td valign="middle"  class="input">  <s:textfield readonly="true" name="parameterList[%{#rowstatus.index}].pk.parameterIdx" value="%{pk.parameterIdx}"/></td>
					<td valign="middle"  class="label">Value</td><td valign="middle"  class="input">  <s:textfield name="parameterList[%{#rowstatus.index}].parameterValue" value="%{parameterValue}"/></td>
					<td valign="middle"  class="label">Burst Column</td><td valign="middle"  class="input">  <s:textfield name="parameterList[%{#rowstatus.index}].parameterBurstColumn" value="%{parameterBurstColumn}"/></td>
					<td valign="middle"  class="label">Data Type</td><td valign="middle"  class="input">  
					<s:select label="Select Data Type" 
				    name="parameterList[%{#rowstatus.index}].parameterType" 
				    headerKey="1"
				    headerValue="-- Please Select --"
					list="#{'1':'String','2':'Date':'3':'Boolean,'4':'Integer','5':'Long','6':'Double'}"
					value="%{parameterType}"/>				    
					</td>
					<td valign="middle"  class="label"><a href="#" onClick="removeParameter('%{#rowstatus.index}');">Delete Parameter</a></td>
				</tr>	
				<table>	
				</div>		
			</s:iterator>
			</div>		
			</td>
			</tr>
			<tr>
				<td valign="middle"  class="label">Jasper Template File</td>
				<td class="input" valign="middle" ><s:file name="upload"/></td>
			</tr>	
  			<tr>
				<td valign="middle"  class="label">Success/Fail Alert Email Address(es)</td>  			
  				<td valign="middle"  class="input">
  					<s:textarea cols="20" rows="20" value="%{job.alertEmailAddress}" name="alertEmailAddress"/>
  				</td>
  			</tr>
  			<tr>
				<td valign="middle"  class="label">Distribution Email Address(es)</td>  			
  				<td valign="middle"  class="input">
  					<s:textarea cols="20" rows="20" value="%{job.targetEmailAddress}" name="targetEmailAddress"/>
  				</td>
  			</tr>
  			<tr>
  				<td colspan=2>
  					<s:submit />
  				</td>
  			</tr>
  		</table>
  	</s:form>
  </body>  
</html>  
