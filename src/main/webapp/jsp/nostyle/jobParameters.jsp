<%@ taglib prefix="s" uri="/struts-tags"%>
<s:iterator value="job.parameters" status="rowstatus">

			<div class="formGroup">
 				<div class="formGroupHeader">Parameter Index <s:property value="%{pk.parameterIdx}" /></div>
				
				<s:hidden value="%{pk.parameterIdx}"
					name="parameters[%{#rowstatus.index}].pk.parameterIdx" />
				
				<s:textfield
					label="Description" name="parameters[%{#rowstatus.index}].description"
					value="%{description}" cssClass="textbox">
				</s:textfield> 

				<div id="jobValueTip<s:property value="%{#rowstatus.index}"/>" class="tipText">Use this field to hard code the value. </div>
				<s:textfield
					label="Value" name="parameters[%{#rowstatus.index}].parameterValue"
					value="%{parameterValue}"
					onfocus="document.getElementById('jobValueTip%{#rowstatus.index}').style.visibility='visible';" 
					onblur="document.getElementById('jobValueTip%{#rowstatus.index}').style.visibility='hidden';" cssClass="textbox">

				</s:textfield> 
				<div id="jobBurstColTip<s:property value="%{#rowstatus.index}"/>" class="tipText">Entry should match a column in the burst query.</div>
				<s:textfield label="Burst Column"
					name="parameters[%{#rowstatus.index}].parameterBurstColumn"
					value="%{parameterBurstColumn}"	
					onfocus="document.getElementById('jobBurstColTip%{#rowstatus.index}').style.visibility='visible';" 
					onblur="document.getElementById('jobBurstColTip%{#rowstatus.index}').style.visibility='hidden';" cssClass="textbox">

				</s:textfield>

				 <s:select label="Data Type"
					name="parameters[%{#rowstatus.index}].parameterType" list="dataTypes"
					listKey="name" listValue="displayName">

				</s:select> 
				<input type="button" name="dispatchSaveButtom" value="Delete Parameter %{pk.parameterIdx}"/>
							
			</div>
		</s:iterator>