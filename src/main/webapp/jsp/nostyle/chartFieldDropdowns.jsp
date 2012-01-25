<%@ taglib prefix="s" uri="/struts-tags"%>

<s:actionerror />
<s:actionmessage/>

				<s:select label="X-Axis Column Name"
					name="item.xaxisColumn" value="xaxisColumnValue"
					list="columnNames"  cssClass="textbox">
				</s:select>

				<s:select label="Value Column Name"
					name="item.valueColumn" value="valueColumnValue"
					list="columnNames"  cssClass="textbox">
				</s:select>

				<s:select label="Series Name Column Name"
					name="item.seriesNameColumn"  value="seriesNameColumnValue"
					list="columnNames"  cssClass="textbox">
				</s:select>