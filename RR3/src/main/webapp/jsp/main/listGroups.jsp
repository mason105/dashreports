<%@ taglib prefix="s" uri="/struts-tags" %>  
<html><!-- InstanceBegin template="/Templates/MainTemplate.dwt.jsp" codeOutsideHTMLIsLocked="false" -->  
  <head>  
    <s:head theme="ajax"/>  
  </head>  
  <!-- InstanceBeginEditable name="head" -->  
  <script>  
    function show_job_list(groupName) {   
      document.frm_groups.groupName.value = groupName;   
      dojo.event.topic.publish("show_jobs");   
    }   
  </script>       
  <!-- InstanceEndEditable -->       
  <body>  
  <!-- InstanceBeginEditable name="Body" -->  
    <s:form id="frm_groups" name="frm_groups">  
      <h1>Job</h1>  
      <s:if test="groups.size > 0">  
        <table border="1">  
          <s:iterator value="groups">  
            <tr>  
              <td>  
                <s:a href="#" onclick="javascript:show_job_list('%{groupName}');return false;"><s:property value="groupName" /></s:a>  
              </td>  
              <td>  
                <s:property value="groupName" />  
              </td>  
              <td>  
                <s:property value="groupDescription" />  
              </td>  
            </tr>  
          </s:iterator>  
        </table>  
      </s:if>    
      <s:hidden name="groupName"/>  
      <s:url id="d_url" action="listJobs" />  
      <s:div  id="job_list" href="%{d_url}" theme="ajax" listenTopics="show_jobs" formId="frm_groups" >  
      </s:div>  
    </s:form>  
  <!-- InstanceEndEditable -->
  </body>  
<!-- InstanceEnd --></html>  