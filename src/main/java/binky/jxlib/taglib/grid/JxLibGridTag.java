package binky.jxlib.taglib.grid;

import javax.servlet.jsp.JspException;

import binky.jxlib.components.grid.Grid;
import binky.jxlib.taglib.JxLibTag;

public class JxLibGridTag extends JxLibTag {

	private Grid gridObject;

	public int doEndTag() throws JspException {

		String functionHeader = "function drawGrid_" + gridObject.getId()
				+ "() {";

		StringBuilder functionBody = new StringBuilder();

		functionBody.append("grid_" + gridObject.getId() + " = new Jx.Grid({");
		functionBody.append("\n");
		functionBody.append("alternateRowColors:"
				+ (gridObject.isAlternateRowColors() ? "true" : "else"));
		functionBody.append("\n");
		functionBody.append("rowHeaders:"
				+ (gridObject.isRowHeaders() ? "true" : "else"));
		functionBody.append("\n");
		functionBody.append("columnHeaders:"
				+ (gridObject.isColumnHeaders() ? "true" : "else"));
		functionBody.append("\n");
		functionBody.append("rowPrelight:"
				+ (gridObject.isRowPrelight() ? "true" : "else"));
		functionBody.append("\n");
		functionBody.append("columnPrelight:"
				+ (gridObject.isColumnPrelight() ? "true" : "else"));
		functionBody.append("\n");
		functionBody.append("rowHeaderPrelight:"
				+ (gridObject.isRowHeaderPrelight() ? "true" : "else"));
		functionBody.append("\n");
		functionBody.append("columnHeaderPrelight:"
				+ (gridObject.isColumnHeaderPrelight() ? "true" : "else"));
		functionBody.append("\n");
		functionBody.append("cellPrelight:"
				+ (gridObject.isCellPrelight() ? "true" : "else"));
		functionBody.append("\n");
		functionBody.append("rowSelection:"
				+ (gridObject.isRowSelection() ? "true" : "else"));
		functionBody.append("\n");
		functionBody.append("columnSelection:"
				+ (gridObject.isColumnSelection() ? "true" : "else"));
		functionBody.append("\n");
		functionBody.append("cellSelection:"
				+ (gridObject.isCellSelection() ? "true" : "else"));
		functionBody.append("\n");

		functionBody.append("}).addTo('" + gridObject.getContainingDiv()
				+ "');");

		boolean b;
		// col headers
		functionBody.append("\n");
		functionBody.append("var colHeaders = [");

		b = false;
		for (String s : gridObject.getData().getColumnHeaders()) {
			if (b) {
				functionBody.append(",");
			} else {
				b = true;
			}
			functionBody.append("'");
			functionBody.append(s);
			functionBody.append("'");
		}
		functionBody.append("\n");
		functionBody.append("];");
		// row headers
		functionBody.append("\n");
		functionBody.append("var rowHeaders = [");
		b = false;
		for (String s : gridObject.getData().getRowHeaders()) {
			if (b) {
				functionBody.append(",");
			} else {
				b = true;
			}
			functionBody.append("'");
			functionBody.append(s);
			functionBody.append("'");
		}
		functionBody.append("\n");
		functionBody.append("];");
		// data
		functionBody.append("\n");
		functionBody.append("var data = [");
		b = false;
		for (String s : gridObject.getData().getData()) {
			if (b) {
				functionBody.append(",");
			} else {
				b = true;
			}
			functionBody.append("'");
			functionBody.append(s);
			functionBody.append("'");
		}
		functionBody.append("\n");
		functionBody.append("];");

		// actual grid
		functionBody.append("var model_" + gridObject.getId()
				+ " = new Jx.Grid.Model(data," + "{"
				+ "rowHeaders: rowHeaders," + "columnHeaders: colHeaders, "
				+ "rowHeaderWidth: " + gridObject.getRowHeaderWidth() + ", "
				+ "colWidth: " + gridObject.getColWidth() + "});");

		functionBody.append("grid_" + gridObject.getId() + ".setModel(model_"
				+ gridObject.getId() + ");");

		String functionFooter = "}";

		StringBuilder function = new StringBuilder();
		function.append(functionHeader);
		function.append("\n");
		function.append(functionBody);
		function.append("\n");
		function.append(functionFooter);
		// write out the contents of the function

		try {
			pageContext.getOut().write(function.toString());
		} catch (java.io.IOException e) {
			throw new JspException("IO Error: " + e.getMessage());
		}
		return EVAL_PAGE;
	}

	public Grid getGridObject() {
		return gridObject;
	}

	public void setGridObject(Grid gridObject) {
		this.gridObject = gridObject;
	}

}
