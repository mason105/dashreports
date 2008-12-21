<%@ taglib prefix="s" uri="/struts-tags" %>  
<html>
  <head>  
    <s:head/>  
  </head>  
  <body>  
  	<s:form action="saveJob">
  		<table>
  			<tr>
  				<td>
  					Job Name:<s:textfield size="32" value="%{job.pk.jobName}" name="jobName"/>
  					<s:hidden value="%{groupName}" name="groupName"/>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					Description:<s:textfield size="128" value="%{job.description}" name="description"/>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					Output URL:<s:textfield size="128" value="%{job.outputUrl}" name="outputUrl"/>
  				</td>
  			</tr>
  			<tr>
  				<td>
  					  <s:select label="Select Data Source" 
    					name="dataSourceNaem" 
    					headerKey="1"
    					headervalue="-- Please Select --"
    					value="%{job.dataSource.dataSourceName}"
    					list="dataSources"
    				   />
  				</td>
  			</tr>
  			
  			<tr>
  				<td>
  					<s:textarea label="Report Query" cols="20" rows="20" value="%{job.query}" name="query"/>
  				</td>
  			</tr>
  			
  			<tr>
  				<td>
  					<s:datetimepicker value="%{job.startDate}" name="startDate" label="Start Date Time" displayFormat="dd-MM-yyyy HH:mm:ss" />
  				</td>
  			</tr>
  			
  			<tr>
  				<td>
  					<s:datetimepicker value="%{job.endDate}" name="endDate" label="End Date Time" displayFormat="dd-MM-yyyy HH:mm:ss" />
  				</td>
  			</tr>
  
  			<tr>
  				<td>
  					Cron String:<s:textfield size="32" value="%{job.cronString}" name="cronString"/>
  				</td>
  			</tr>

  			<tr>
  				<td>
  					<s:checkbox label="Is Bursted Report" value="%{job.isBurst}" name="isBurst"/>
  				</td>
  			</tr>
  			
  			<tr>
  				<td>
  					<s:textarea label="Burst Query" cols="20" rows="20" value="%{job.burstQuery}" name="burstQuery"/>
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
