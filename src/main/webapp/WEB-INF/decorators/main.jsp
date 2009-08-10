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

  <!-- include the jx library and the delicious skin -->
  <script src="<s:url value='/jx/jxlib.js" type="text/javascript'/>" charset="utf-8"></script>
  <link rel="stylesheet" href="<s:url value='/jx/themes/delicious/jxtheme.css'/>" type="text/css" media="screen" charset="utf-8">
  <!-- IE specific style sheets -->
  <!--[if  lte IE 6]>
  <link rel="stylesheet" href="<s:url value='/jx/themes/delicious/ie6.css'/>" type="text/css" media="screen" charset="utf-8">
  <![endif]-->
  <!--[if gt IE 6]>
  <link rel="stylesheet" href="<s:url value='/jx/themes/delicious/ie7.css'/>" type="text/css" media="screen" charset="utf-8">
  <![endif]-->

<script id="treescript" language="javascript">

window.onload=function(){
	tree();	 	
}

var hidden=false;

function getElement(element) {
	var elem;
	if( document.getElementById ) // this is the way the standards work
	  elem = document.getElementById( element );
	 else if( document.all ) // this is the way old msie versions work
	  elem = document.all[element];
	 else if( document.layers ) // this is the way nn4 works
	   elem = document.layers[element];
	   
    return elem;
}						 

function drawHideButton() {

    new Jx.Toolbar.Container().addTo('treeTool').add(
        	new Jx.Toolbar().add(
            	new Jx.Button({label: '',
            	image: '<s:url value="/images/icons/tag.png"/>',
				onClick: function() {
					/* hide the tool bar */
					if (hidden) {
						
						var leftNav=getElement('leftNav');
						var treeArea=getElement('treeArea');
						var content=getElement('content');
						
						treeArea.style.visibility = 'visible'; 
						leftNav.style.width='150px';
						content.style.left='151px';
						content.style.right='0px';
						/* set var to true to switch mode*/
						hidden=false;
					} else {
																												
						var leftNav=getElement('leftNav');
						var treeArea=getElement('treeArea');
						var content=getElement('content');
						
						treeArea.style.visibility = 'hidden'; 
						leftNav.style.width='25px';
						content.style.left='26px';
						content.style.right='0px';
						/* set var to true to switch mode*/
						hidden=true;											
						
					}
					
				}			            
            })
        )
    );
    
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
    var groups = new Jx.TreeFolder({label: 'Report Groups'});

    tree.append(groups);
    
    <s:iterator value="groups">
	    /* put items into the folder */
	    groups.append(new Jx.TreeItem({
        image: '<s:url value="/images/icons/group.png"/>',
	        label: '<s:property value="%{groupName}"/>',
	        onClick: function() {
	        	parent.window.location='<s:url value="listJobs.action?groupName=%{groupName}"/>';
	        }
    	}));
    </s:iterator>

	<s:if test="expandGroups==true">
		groups.expand();
	</s:if>


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
	
	<s:if test="expandAdmin==true">
		manager.expand();
	</s:if>
	    
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
            parent.window.location='http://code.google.com/p/reportrunner/w/list';
        },
        contextMenu: cm
    }));
    
     tree.append(new Jx.TreeItem({
        label: 'About',
        image: '<s:url value="/images/icons/information.png"/>',
        onClick: function() {
            parent.window.location='about.action';
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
   
}

</script>

<decorator:head />
</head>
<body id="page-home">
<div id="container">
<div id="top">
		<div class="appLogo"><img src="<s:url value='/images/logo.png'/>" /></div>
</div>
		<div id="leftNav"  class="leftnav">
			<div id="treeTool" class="treeTool"></div>
			<div id="treeArea" class="treeBox"></div>
		</div>
<div id="content">
				<decorator:body />
</div>
<div id="footer">
<strong>Current User:</strong><s:property value="sessionUser.fullName"/>
</div>
</div>

</body>
</html>
