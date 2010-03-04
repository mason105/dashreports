<%@ taglib prefix="s" uri="/struts-tags"%>

<s:actionerror />
<s:actionmessage/>

				<s:select label="Label Name Column Name"
					name="item.labelColumn"  value="labelColumnValue"
					list="columnNames"  cssClass="textbox">
				</s:select>

				<s:select label="Value Column Name"
					name="item.valueColumn" value="valueColumnValue"
					list="columnNames"  cssClass="textbox">
				</s:select>
