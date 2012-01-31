<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
    <sx:head/>
 	<sj:head locale="en" jqueryui="true" jquerytheme="smoothness"/>
</head>
<body>
<s:form action="saveChart"  method="post"  id="saveChart"  validate="true">
<div id="jobEditForm">
	<div class="jobHeader"><img src="<s:url value='/images/v2/nav/groupsblue.png'/>" align="absmiddle" />&nbsp;<s:a href="showGroup.action?groupName=%{groupName}"><s:property value="groupName"/></s:a> > Edit Chart</div>
<sj:tabbedpanel id="report" animate="true"  useSelectedTabCookie="true">	
	
		
		
		<s:hidden name="item.itemId" value="%{item.itemId}"/>
		<sj:tab id="detailsTab" target="detailsDiv" label="Details"/>
		<div id="detailsDiv" >
			<div class="formGroup">
				<div class="formGroupHeader">Details</div>
			
				<s:textfield label="Item Name" size="64" value="%{item.itemName}" name="item.itemName" cssClass="textbox" required="true">
				</s:textfield>
				<s:hidden name="item.group.groupName" value="%{groupName}"/>
				<s:select label="Select Data Source"
					name="dataSourceName" value="%{item.datasource.dataSourceName}"
					list="runnerDataSources" cssClass="textbox" onchange="dojo.event.topic.publish('refresh_fields');">
				</s:select>
				
				<s:textarea label="Query" cols="30" rows="20"
					value="%{item.alertQuery}" name="itemQuery" cssClass="textbox" required="true" onchange="dojo.event.topic.publish('refresh_fields');">
				</s:textarea>	
				
				<s:url id="validateUrl" action="validateItemQuery" /> 
				<sx:div showLoadingText="true" loadingText="Testing query..." id="validateQuery" href="%{validateUrl}" theme="ajax"  listenTopics="refresh_fields" formId="saveChart">
				</sx:div>
							
			</div>	
			<div class="formBottomEmpty"></div>
		</div>
		<sj:tab id="scheduleTab" target="scheduleDiv" label="Schedule"/>
		<div id="scheduleDiv" >
		
			<div class="formGroup">
				<div class="formGroupHeader">Schedule</div>	
						
				<s:checkbox label="All Seconds" name="simpleCron.allSeconds" cssClass="checkbox">
				</s:checkbox>		
				
				<s:select label="Seconds" 
				onclick="document.getElementById('editJob_simpleCron_allSeconds').checked=false;"
				name="simpleCron.seconds" multiple="true" list="#{0:0,1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23,24:24,25:25,26:26,27:27,28:28,29:29,30:30,31:31,32:32,33:33,34:34,35:35,36:36,37:37,38:38,39:39,40:40,41:41,42:42,43:43,44:44,45:45,46:46,47:47,48:48,49:49,50:50,51:51,52:52,53:53,54:54,55:55,56:56,57:57,58:58,59:59}"></s:select>
				
				<s:checkbox label="All Minutes" name="simpleCron.allMinutes" cssClass="checkbox">
				</s:checkbox>		
				
				<s:select label="Minutes" 
				onclick="document.getElementById('editJob_simpleCron_allMinutes').checked=false;"
				name="simpleCron.minutes" multiple="true" list="#{0:0,1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23,24:24,25:25,26:26,27:27,28:28,29:29,30:30,31:31,32:32,33:33,34:34,35:35,36:36,37:37,38:38,39:39,40:40,41:41,42:42,43:43,44:44,45:45,46:46,47:47,48:48,49:49,50:50,51:51,52:52,53:53,54:54,55:55,56:56,57:57,58:58,59:59}"></s:select>
				
				<s:checkbox label="All Hours" name="simpleCron.allHours" cssClass="checkbox">
				</s:checkbox>		
				
				
				<s:select label="Hours" 
				onclick="document.getElementById('editJob_simpleCron_allHours').checked=false;"
				name="simpleCron.hours" multiple="true" list="#{0:0,1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23}"></s:select>
	
				<s:checkbox label="All Days of The Month" name="simpleCron.allDaysOfMonth" cssClass="checkbox">
				</s:checkbox>		
				
				
				<s:select label="Days of Month" 
				onclick="document.getElementById('editJob_simpleCron_allDaysOfMonth').checked=false;"
				name="simpleCron.daysOfMonth" multiple="true" list="#{0:0,1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20,21:21,22:22,23:23,24:24,25:25,26:26,27:27,28:28,29:29,30:30,31:31}"></s:select>
			
				<s:checkbox label="All Months" name="simpleCron.allMonths" cssClass="checkbox">
				</s:checkbox>		
				
				<s:select label="Month" 
				onclick="document.getElementById('editJob_simpleCron_allMonths').checked=false;"
				name="simpleCron.months" multiple="true" list='#{1:"Jan",2:"Feb",3:"March",4:"April",5:"May",6:"June",7:"July",8:"Aug",9:"Sept",10:"Oct",11:"Nov",12:"Dec"}'></s:select>
	
				
				<s:checkbox label="All Days of The Week" name="simpleCron.allDaysOfWeek" cssClass="checkbox">
				</s:checkbox>		
				
				<s:select label="Days of Week" 
				onclick="document.getElementById('editJob_simpleCron_allDaysOfWeek').checked=false;"
				name="simpleCron.daysOfWeek" multiple="true" list='#{1:"Monday",2:"Tuesday",3:"Wednesday",4:"Thursday",5:"Friday",6:"Saturday",7:"Sunday"}'></s:select>
				
			</div>
				
			<div class="formBottomEmpty"></div>
		
		</div>		
		<sj:tab id="layoutTab" target="layoutDiv" label="Layout"/>
		<div id="layoutDiv" >
			<div class="formGroup">
				<div class="formGroupHeader">Layout</div>

				<div style="float:left;padding-right:10px;">
				 <sj:spinner 
			    	name="item.displayColumn" 
			    	id="item.displayColumn" 
			    	min="1" 
			    	max="50" 
			    	step="1" 
			    	value="%{item.displayColumn}"
			    	label="Display Column"
			    	
			    	/>
</div>
<div>
		 <sj:spinner 
			    	name="item.displayRow" 
			    	id="item.displayRow" 
			    	min="1" 
			    	max="50" 
			    	step="1" 
			    	value="%{item.displayRow}"
			    	label="Display Row"
			    	
			    	/>
	</div>					
				<s:select label="Width" name="item.width" list="widths"
						listKey="name" listValue="displayName" cssClass="textbox"></s:select>
						
				<s:select label="Height" name="item.height" list="heights"
						listKey="name" listValue="displayName" cssClass="textbox"></s:select>
					
			</div>
			<div class="formBottomEmpty"></div>
		</div>
			
		<sj:tab id="chartTab" target="chartDiv" label="Chart Details"/>
		<div id="chartDiv" >
			<div class="formGroup">
				<div class="formGroupHeader">Chart Configuration</div>
				
				<s:select label="Chart Type" name="item.chartType" list="chartTypes"
				listKey="name" listValue="displayName" cssClass="textbox"></s:select>			
				
				<s:select label="Orientation" name="item.orientation" list="orientations"
				listKey="name" listValue="displayName" cssClass="textbox"></s:select>						
				
				<s:checkbox name="item.gridLines" label="Show Grid Lines"/>
				
				
				 <s:select label="Chart Background Colour" 
				    name="item.backGroundColour" 
				    list="#{'#FFFFFF':'White',
				    '#000000':'Black',
					'#150517':'Gray0',
					'#250517':'Gray18',
					'#2B1B17':'Gray21',
					'#302217':'Gray23',
					'#302226':'Gray24',
					'#342826':'Gray25',
					'#34282C':'Gray26',
					'#382D2C':'Gray27',
					'#3b3131':'Gray28',
					'#3E3535':'Gray29',
					'#413839':'Gray30',
					'#41383C':'Gray31',
					'#463E3F':'Gray32',
					'#4A4344':'Gray34',
					'#4C4646':'Gray35',
					'#4E4848':'Gray36',
					'#504A4B':'Gray37',
					'#544E4F':'Gray38',
					'#565051':'Gray39',
					'#595454':'Gray40',
					'#5C5858':'Gray41',
					'#5F5A59':'Gray42',
					'#625D5D':'Gray43',
					'#646060':'Gray44',
					'#666362':'Gray45',
					'#696565':'Gray46',
					'#6D6968':'Gray47',
					'#6E6A6B':'Gray48',
					'#726E6D':'Gray49',
					'#747170':'Gray50',
					'#736F6E':'Gray',
					'#616D7E':'Slate Gray4',
					'#657383':'Slate Gray',
					'#646D7E':'Light Steel Blue4',
					'#6D7B8D':'Light Slate Gray',
					'#4C787E':'Cadet Blue4',
					'#4C7D7E':'Dark Slate Gray4',
					'#806D7E':'Thistle4',
					'#5E5A80':'Medium Slate Blue',
					'#4E387E':'Medium Purple4',
					'#151B54':'Midnight Blue',
					'#2B3856':'Dark Slate Blue',
					'#25383C':'Dark Slate Gray',
					'#463E41':'Dim Gray',
					'#151B8D':'Cornflower Blue',
					'#15317E':'Royal Blue4',
					'#342D7E':'Slate Blue4',
					'#2B60DE':'Royal Blue',
					'#306EFF':'Royal Blue1',
					'#2B65EC':'Royal Blue2',
					'#2554C7':'Royal Blue3',
					'#3BB9FF':'Deep Sky Blue',
					'#38ACEC':'Deep Sky Blue2',
					'#357EC7':'Slate Blue',
					'#3090C7':'Deep Sky Blue3',
					'#25587E':'Deep Sky Blue4',
					'#1589FF':'Dodger Blue',
					'#157DEC':'Dodger Blue2',
					'#1569C7':'Dodger Blue3',
					'#153E7E':'Dodger Blue4',
					'#2B547E':'Steel Blue4',
					'#4863A0':'Steel Blue',
					'#6960EC':'Slate Blue2',
					'#8D38C9':'Violet',
					'#7A5DC7':'Medium Purple3',
					'#8467D7':'Medium Purple',
					'#9172EC':'Medium Purple2',
					'#9E7BFF':'Medium Purple1',
					'#728FCE':'Light Steel Blue',
					'#488AC7':'Steel Blue3',
					'#56A5EC':'Steel Blue2',
					'#5CB3FF':'Steel Blue1',
					'#659EC7':'Sky Blue3',
					'#41627E':'Sky Blue4',
					'#737CA1':'Slate Blue',
					'#737CA1':'Slate Blue',
					'#98AFC7':'Slate Gray3',
					'#F6358A':'Violet Red',
					'#F6358A':'Violet Red1',
					'#E4317F':'Violet Red2',
					'#F52887':'Deep Pink',
					'#E4287C':'Deep Pink2',
					'#C12267':'Deep Pink3',
					'#7D053F':'Deep Pink4',
					'#CA226B':'Medium Violet Red',
					'#C12869':'Violet Red3',
					'#800517':'Firebrick',
					'#7D0541':'Violet Red4',
					'#7D0552':'Maroon4',
					'#810541':'Maroon',
					'#C12283':'Maroon3',
					'#E3319D':'Maroon2',
					'#F535AA':'Maroon1',
					'#FF00FF':'Magenta',
					'#F433FF':'Magenta1',
					'#E238EC':'Magenta2',
					'#C031C7':'Magenta3',
					'#B048B5':'Medium Orchid',
					'#D462FF':'Medium Orchid1',
					'#C45AEC':'Medium Orchid2',
					'#A74AC7':'Medium Orchid3',
					'#6A287E':'Medium Orchid4',
					'#8E35EF':'Purple',
					'#893BFF':'Purple1',
					'#7F38EC':'Purple2',
					'#6C2DC7':'Purple3',
					'#461B7E':'Purple4',
					'#571B7e':'Dark Orchid4',
					'#7D1B7E':'Dark Orchid',
					'#842DCE':'Dark Violet',
					'#8B31C7':'Dark Orchid3',
					'#A23BEC':'Dark Orchid2',
					'#B041FF':'Dark Orchid1',
					'#7E587E':'Plum4',
					'#D16587':'Pale Violet Red',
					'#F778A1':'Pale Violet Red1',
					'#E56E94':'Pale Violet Red2',
					'#C25A7C':'Pale Violet Red3',
					'#7E354D':'Pale Violet Red4',
					'#B93B8F':'Plum',
					'#F9B7FF':'Plum1',
					'#E6A9EC':'Plum2',
					'#C38EC7':'Plum3',
					'#D2B9D3':'Thistle',
					'#C6AEC7':'Thistle3',
					'#EBDDE2':'Lavender Blush2',
					'#C8BBBE':'Lavender Blush3',
					'#E9CFEC':'Thistle2',
					'#FCDFFF':'Thistle1',
					'#E3E4FA':'Lavender',
					'#FDEEF4':'Lavender Blush',
					'#C6DEFF':'Light Steel Blue1',
					'#ADDFFF':'Light Blue',
					'#BDEDFF':'Light Blue1',
					'#E0FFFF':'Light Cyan',
					'#C2DFFF':'Slate Gray1',
					'#B4CFEC':'Slate Gray2',
					'#B7CEEC':'Light Steel Blue2',
					'#52F3FF':'Turquoise1',
					'#00FFFF':'Cyan',
					'#57FEFF':'Cyan1',
					'#50EBEC':'Cyan2',
					'#4EE2EC':'Turquoise2',
					'#48CCCD':'Medium Turquoise',
					'#43C6DB':'Turquoise',
					'#9AFEFF':'Dark Slate Gray1',
					'#8EEBEC':'Dark slate Gray2',
					'#78c7c7':'Dark Slate Gray3',
					'#46C7C7':'Cyan3',
					'#43BFC7':'Turquoise3',
					'#77BFC7':'Cadet Blue3',
					'#92C7C7':'Pale Turquoise3',
					'#AFDCEC':'Light Blue2',
					'#3B9C9C':'Dark Turquoise',
					'#307D7E':'Cyan4',
					'#3EA99F':'Light Sea Green',
					'#82CAFA':'Light Sky Blue',
					'#A0CFEC':'Light Sky Blue2',
					'#87AFC7':'Light Sky Blue3',
					'#82CAFF':'Sky Blue',
					'#79BAEC':'Sky Blue2',
					'#566D7E':'Light Sky Blue4',
					'#6698FF':'Sky Blue',
					'#736AFF':'Light Slate Blue',
					'#CFECEC':'Light Cyan2',
					'#AFC7C7':'Light Cyan3',
					'#717D7D':'Light Cyan4',
					'#95B9C7':'Light Blue3',
					'#5E767E':'Light Blue4',
					'#5E7D7E':'Pale Turquoise4',
					'#617C58':'Dark Sea Green4',
					'#348781':'Medium Aquamarine',
					'#306754':'Medium Sea Green',
					'#4E8975':'Sea Green',
					'#254117':'Dark Green',
					'#387C44':'Sea Green4',
					'#4E9258':'Forest Green',
					'#347235':'Medium Forest Green',
					'#347C2C':'Spring Green4',
					'#667C26':'Dark Olive Green4',
					'#437C17':'Chartreuse4',
					'#347C17':'Green4',
					'#348017':'Medium Spring Green',
					'#4AA02C':'Spring Green',
					'#41A317':'Lime Green',
					'#4AA02C':'Spring Green',
					'#8BB381':'Dark Sea Green',
					'#99C68E':'Dark Sea Green3',
					'#4CC417':'Green3',
					'#6CC417':'Chartreuse3',
					'#52D017':'Yellow Green',
					'#4CC552':'Spring Green3',
					'#54C571':'Sea Green3',
					'#57E964':'Spring Green2',
					'#5EFB6E':'Spring Green1',
					'#64E986':'Sea Green2',
					'#6AFB92':'Sea Green1',
					'#B5EAAA':'Dark Sea Green2',
					'#C3FDB8':'Dark Sea Green1',
					'#00FF00':'Green',
					'#87F717':'Lawn Green',
					'#5FFB17':'Green1',
					'#59E817':'Green2',
					'#7FE817':'Chartreuse2',
					'#8AFB17':'Chartreuse',
					'#B1FB17':'Green Yellow',
					'#CCFB5D':'Dark Olive Green1',
					'#BCE954':'Dark Olive Green2',
					'#A0C544':'Dark Olive Green3',
					'#FFFF00':'Yellow',
					'#FFFC17':'Yellow1',
					'#FFF380':'Khaki1',
					'#EDE275':'Khaki2',
					'#EDDA74':'Goldenrod',
					'#EAC117':'Gold2',
					'#FDD017':'Gold1',
					'#FBB917':'Goldenrod1',
					'#E9AB17':'Goldenrod2',
					'#D4A017':'Gold',
					'#C7A317':'Gold3',
					'#C68E17':'Goldenrod3',
					'#AF7817':'Dark Goldenrod',
					'#ADA96E':'Khaki',
					'#C9BE62':'Khaki3',
					'#827839':'Khaki4',
					'#FBB117':'Dark Goldenrod1',
					'#E8A317':'Dark Goldenrod2',
					'#C58917':'Dark Goldenrod3',
					'#F87431':'Sienna1',
					'#E66C2C':'Sienna2',
					'#F88017':'Dark Orange',
					'#F87217':'Dark Orange1',
					'#E56717':'Dark Orange2',
					'#C35617':'Dark Orange3',
					'#C35817':'Sienna3',
					'#8A4117':'Sienna',
					'#7E3517':'Sienna4',
					'#7E2217':'Indian Red4',
					'#7E3117':'Dark Orange3',
					'#7E3817':'Salmon4',
					'#7F5217':'Dark Goldenrod4',
					'#806517':'Gold4',
					'#805817':'Goldenrod4',
					'#7F462C':'Light Salmon4',
					'#C85A17':'Chocolate',
					'#C34A2C':'Coral3',
					'#E55B3C':'Coral2',
					'#F76541':'Coral',
					'#E18B6B':'Dark Salmon',
					'#F88158':'Pale Turquoise4',
					'#E67451':'Salmon2',
					'#C36241':'Salmon3',
					'#C47451':'Light Salmon3',
					'#E78A61':'Light Salmon2',
					'#F9966B':'Light Salmon',
					'#EE9A4D':'Sandy Brown',
					'#F660AB':'Hot Pink',
					'#F665AB':'Hot Pink1',
					'#E45E9D':'Hot Pink2',
					'#C25283':'Hot Pink3',
					'#7D2252':'Hot Pink4',
					'#E77471':'Light Coral',
					'#F75D59':'Indian Red1',
					'#E55451':'Indian Red2',
					'#C24641':'Indian Red3',
					'#FF0000':'Red',
					'#F62217':'Red1',
					'#E41B17':'Red2',
					'#F62817':'Firebrick1',
					'#E42217':'Firebrick2',
					'#C11B17':'Firebrick3',
					'#FAAFBE':'Pink',
					'#FBBBB9':'Rosy Brown1',
					'#E8ADAA':'Rosy Brown2',
					'#E7A1B0':'Pink2',
					'#FAAFBA':'Light Pink',
					'#F9A7B0':'Light Pink1',
					'#E799A3':'Light Pink2',
					'#C48793':'Pink3',
					'#C5908E':'Rosy Brown3',
					'#B38481':'Rosy Brown',
					'#C48189':'Light Pink3',
					'#7F5A58':'Rosy Brown4',
					'#7F4E52':'Light Pink4',
					'#7F525D':'Pink4',
					'#817679':'Lavendar Blush4',
					'#817339':'Light Goldenrod4',
					'#827B60':'Lemon Chiffon4',
					'#C9C299':'Lemon Chiffon3',
					'#C8B560':'Light Goldenrod3',
					'#ECD672':'Light Golden2',
					'#ECD872':'Light Goldenrod',
					'#FFE87C':'Light Goldenrod1',
					'#ECE5B6':'Lemon Chiffon2',
					'#FFF8C6':'Lemon Chiffon',
					'#FAF8CC':'Light Goldenrod Yellow'}"
				    cssClass="textbox" />
				
			
			 	<s:textfield label="Y-Axis Label" size="64" value="%{item.yLabel}" name="item.yLabel" cssClass="textbox">
				</s:textfield>
											
				<s:hidden name="xaxisColumnValue" value="%{item.xaxisColumn}"/>
				<s:hidden name="valueColumnValue" value="%{item.valueColumn}"/>
				<s:hidden name="seriesNameColumnValue" value="%{item.seriesNameColumn}"/>
				
				<s:url id="fieldsUrl" action="getChartColumns" /> 
				<sx:div  showLoadingText="true" loadingText="Getting fields..." id="refreshFields" href="%{fieldsUrl}" theme="ajax"  listenTopics="refresh_fields" formId="saveChart">
				</sx:div>
			
			<div class="formBottomEmpty"></div>
			</div>		
				
	</div>
	<div class="formBottom">
		<s:actionerror  theme="jquery" />
		<s:actionmessage  theme="jquery"/>
		<div class="formFooterText">* required field
		</div>
	<s:submit value="Save" align="left" /></div>
	
</sj:tabbedpanel>
</div>
</s:form>
</body>
</html>
	