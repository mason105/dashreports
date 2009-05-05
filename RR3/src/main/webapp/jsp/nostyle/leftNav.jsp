<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><decorator:title default="Report Runner" /></title>
<link href="<s:url value='/styles/main.css'/>" rel="stylesheet"
	type="text/css" media="all" />
	<sx:head parseContent="true" />
 <!-- include the jx library and the delicious skin -->
  <script src="<s:url value='/jx/jxlib.js" type="text/javascript'/>" charset="utf-8"></script>
  <link rel="stylesheet" href="<s:url value='/jx/themes/delicious/jxtheme.css'/>" type="text/css" media="screen" charset="utf-8"/>
  <!-- IE specific style sheets -->
  <!--[if IE lte 6]>
  <link rel="stylesheet" href="<s:url value='/jx/themes/delicious/ie6.css'/>" type="text/css" media="screen" charset="utf-8"/>
  <![endif]-->
  <!--[if IE 7]>
  <link rel="stylesheet" href="<s:url value='/jx/themes/delicious/ie7.css'/>" type="text/css" media="screen" charset="utf-8"/>
  <![endif]-->	
<script id="treescript" language="javascript">
	
window.onload=function(){
 /* var nodes =dojo.widget.manager.getWidgetsByType('struts:StrutsTreeNode');
                for( var i=0; i < nodes.length; i++){ 
					nodes[i].expand();
                }*/
	tree();
} 	

function tree() {
  
    var cm = new Jx.Menu.Context();
    var mi = new Jx.Menu.Item({label:'test'});
    cm.add(mi);
    
    /* the tree object goes in a div */
    var tree = new Jx.Tree({parent: 'treeArea'});

    var root = new Jx.TreeItem({
        label: 'Home',
        image: '<s:url value="/images/icons/house.png"/>',
        onClick: function() {
            parent.window.location='index.action';
        },
        contextMenu: cm
    });
    tree.append(root);
    
    /* add groups folder to the tree */
    var groups = new Jx.TreeFolder({label: 'Groups'});

    tree.append(groups);
    
    <s:iterator value="groups">
	    /* put items into the folder */
	    groups.append(new Jx.TreeItem({
        image: '<s:url value="/images/icons/report.png"/>',
	        label: '<s:property value="%{groupName}"/>',
	        onClick: function() {
	        	parent.window.location='listJobs.action?groupName=<s:property value="%{groupName}"/>';
	        }
    	}));
    </s:iterator>
    <s:if test="sessionUser.isAdmin == true">
	    var manager = new Jx.TreeFolder({label: 'Manage Server'});
	    tree.append(manager);
	    manager.append(new Jx.TreeItem({
	        label: 'Users',
	        image: '<s:url value="/images/icons/user.png"/>',
	        onClick: function() {
	            parent.window.location='listUsers.action';
	        },
	        contextMenu: cm
	    }));
	       
	    manager.append(new Jx.TreeItem({
	        label: 'Groups',
	        image: '<s:url value="/images/icons/group.png"/>',
	        onClick: function() {
	            parent.window.location='listGroups.action';
	        },
	        contextMenu: cm
	    }));
	       
	    manager.append(new Jx.TreeItem({
	        label: 'Dashboard Items',
	        image: '<s:url value="/images/icons/chart_bar.png"/>',
	        onClick: function() {
	            parent.window.location='listAlerts.action';
	        },
	        contextMenu: cm
	    }));
	
	    manager.append(new Jx.TreeItem({
	        label: 'Data Sources',
	        image: '<s:url value="/images/icons/database_link.png"/>',
	        onClick: function() {
	            parent.window.location='listDataSources.action';
	        },
	        contextMenu: cm
	    }));
	       
	    manager.append(new Jx.TreeItem({
	        label: 'Executing Jobs',
	        image: '<s:url value="/images/icons/application_lightning.png"/>',
	        onClick: function() {
	            parent.window.location='listCurrentExecutingJobs.action';
	        },
	        contextMenu: cm
	    }));
	       
	    manager.append(new Jx.TreeItem({
	        label: 'Server Stats',
	        image: '<s:url value="/images/icons/chart_curve.png"/>',
	        onClick: function() {
	            parent.window.location='jobStatistics.action';
	        },
	        contextMenu: cm
	    }));
    </s:if>   

    tree.append(new Jx.TreeItem({
        label: 'Change Password',
        image: '<s:url value="/images/icons/key.png"/>',
        onClick: function() {
            parent.window.location='setupChangePassword.action';
        },
        contextMenu: cm
    }));
       
    tree.append(new Jx.TreeItem({
        label: 'Help',
        image: '<s:url value="/images/icons/help.png"/>',
        onClick: function() {
            parent.window.location='https://sourceforge.net/forum/forum.php?forum_id=848787';
        },
        contextMenu: cm
    }));
       
    tree.append(new Jx.TreeItem({
        label: 'Logout',
        image: '<s:url value="/images/icons/door_out.png"/>',
        onClick: function() {
            parent.window.location='logout.action';
        },
        contextMenu: cm
    }));
       
    
};

</script>
</head>
<body>
 <!--<sx:tree id="reportrunner" label="<a href='index.action' target='_top'>Report Runner</a>" showRootGrid="true">

  <sx:treenode id="groups" label="Groups" >
  	  <s:iterator value="groups">
      	<sx:treenode id="group_%{groupName}" label="<a href='listJobs.action?groupName=%{groupName}' target='_top'>%{groupName}</a>" />
      </s:iterator>
  </sx:treenode>
  <s:if test="sessionUser.isAdmin == true">
  	<sx:treenode id="manage" label="Manage Server">
		<sx:treenode id="mange_" label="<a href='listUsers.action' target='_top'>Users</a>" />  			
		<sx:treenode id="mange_" label="<a href='listGroups.action' target='_top'>Groups</a>" />  			
		<sx:treenode id="mange_" label="<a href='listAlerts.action' target='_top'>Dashboard Items</a>" />  			
		<sx:treenode id="mange_" label="<a href='listDataSources.action' target='_top'>Data Sources</a>" />  			
		<sx:treenode id="mange_" label="<a href='listCurrentExecutingJobs.action' target='_top'>Executing Jobs</a>" />  			
		<sx:treenode id="mange_" label="<a href='jobStatistics.action' target='_top'>Server Stats</a>" />  			
  	</sx:treenode>
  </s:if>
  <sx:treenode id="changepassword" label="<a href='setupChangePassword.action' target='_top'>Change Password</a>" />
  <sx:treenode id="help" label="<a href='https://sourceforge.net/forum/forum.php?forum_id=848787' target='_top'>Help</a>" />
  <sx:treenode id="logout" label="<a href='logout.action' target='_top'>Logout</a>" />    
 </sx:tree>-->
 
<div id="treeArea" class="treeBox"></div>
 
</body>
</html>