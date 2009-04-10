<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<sx:head parseContent="true" />
  </head>  
  <body>  <%@ taglib prefix="s" uri="/struts-tags" %>  
  <span class="pageTitle"><img
	src="<s:url value='/images/documentnotes.png'/>" align="absmiddle" />Job List for Group: <s:property value="groupName" /></span>	
    <table border="0" width="100%">
      <tr class="rowHeader">
      <td colspan='7'  class="rowHeader">
      <a href="setupEditJob.action?groupName=<s:property value="groupName" />"><img
			src="<s:url value='/images/add_small.png'/>" align="absmiddle" />Add Job</a>
      </td>
      </tr>	    
      <tr>
      <td class="headerCell">
      	Job Name
      </td>
      <td class="headerCell">
      	Description
      </td>
      <td class="headerCell" colspan="5">&nbsp;</td>
      </tr>
	  <s:if test="jobs.size > 0">	      
          
	<%
			boolean rowOdd = true;
		%>
		  <s:iterator value="jobs">
			<%
				if (rowOdd) {
							rowOdd = false;
			%>
				<tr class="rowOdd">
			<%
				} else {
							rowOdd = true;
			%>			
				<tr class="rowEven">
			<%
				}
			%>              
              <td>  
                <s:a href="setupViewJob.action?jobName=%{jobName}&groupName=%{groupName}"><s:property value="jobName" /></s:a>
              </td>  
              <td>  
                <s:property value="description" />  
              </td>  
              <td width="24">
              <s:a href="viewJobDetail.action?jobName=%{jobName}&groupName=%{groupName}"><img src="<s:url value='/images/view_small.png'/>" alt="View Detail/History"
							align="absmiddle" /></s:a>
              </td>
              <td width="24">	
				 <s:a href="invokeJob.action?jobName=%{jobName}&groupName=%{groupName}"><img src="<s:url value='/images/invoke_small.png'/>" alt="Invoke Now"
							align="absmiddle" /></s:a>&nbsp;			
              </td>
              <td  width="24">				 
				 <s:a href="setupEditJob.action?jobName=%{jobName}&groupName=%{groupName}"><img src="<s:url value='/images/edit_small.png'/>" align="absmiddle" alt="Edit" /></s:a>
			  </td>
			  <td width="24">
				 <s:if test="isScheduled">				
				 	<s:if test="isScheduleActive">
				 		<s:a href="setJobStatus.action?jobName=%{jobName}&groupName=%{groupName}&jobStatus=false"><img src="<s:url value='/images/pause_small.png'/>" alt="Pause"
							align="absmiddle" /></s:a>
				 	</s:if>
				 	<s:else>
				 		<s:a href="setJobStatus.action?jobName=%{jobName}&groupName=%{groupName}&jobStatus=true"><img src="<s:url value='/images/resume_small.png'/>" alt="Resume"
							align="absmiddle" /></s:a>				 	
				 	</s:else>
				 </s:if>
			  </td>			  
			  <td width="24">
				 <a href="deleteJob.action?jobName=<s:property value="jobName" />&groupName=<s:property value="groupName" />" onClick="return confirm('Really delete this job?');"><img src="<s:url value='/images/delete_small.png'/>" alt="Delete"
							align="absmiddle" /></a>
			  </td>
            </tr>  
          </s:iterator>
	  </s:if>             
     </table>  

  </body>  
</html>     