/*******************************************************************************
 * Copyright (c) 2009 Daniel Grout.
 * 
 * GNU GENERAL PUBLIC LICENSE - Version 3
 * 
 * This file is part of Report Runner (http://code.google.com/p/reportrunner).
 * 
 * Report Runner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Runner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Runner. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Module: Grid.java
 ******************************************************************************/
package binky.jxlib.components.grid;

import binky.jxlib.components.JxLibComponent;

public class Grid extends JxLibComponent {


	private static final long serialVersionUID = 5272700098984333797L;

	private boolean alternateRowColors;

	private boolean rowHeaders=false;
	private boolean columnHeaders=false;
	private boolean rowPrelight=false;
	private boolean columnPrelight=false;
	private boolean rowHeaderPrelight=false;
	private boolean columnHeaderPrelight;
	private boolean cellPrelight=false;
	private boolean rowSelection=false;
	private boolean columnSelection=false;
	private boolean cellSelection=false;
	private GridData data;
	private String containingDiv;
	private int rowHeaderWidth=150;
	private int colWidth=75;
	
	/**
	 * @param id
	 * @param data
	 */
	public Grid(String id, GridData data) {
		super(id);
		this.data=data;
	}

	public GridData getData() {
		return this.data;
	}

	public boolean isAlternateRowColors() {
		return this.alternateRowColors;
	}

	public boolean isCellPrelight() {
		return this.cellPrelight;
	}

	public boolean isCellSelection() {
		return this.cellSelection;
	}

	public boolean isColumnHeaderPrelight() {
		return this.columnHeaderPrelight;
	}

	public boolean isColumnHeaders() {
		return this.columnHeaders;
	}

	public boolean isColumnPrelight() {
		return this.columnPrelight;
	}

	public boolean isColumnSelection() {
		return this.columnSelection;
	}

	public boolean isRowHeaderPrelight() {
		return this.rowHeaderPrelight;
	}

	public boolean isRowHeaders() {
		return this.rowHeaders;
	}

	public boolean isRowPrelight() {
		return this.rowPrelight;
	}

	public boolean isRowSelection() {
		return this.rowSelection;
	}

	public void setAlternateRowColors(boolean alternateRowColors) {
		this.alternateRowColors = alternateRowColors;
	}

	public void setCellPrelight(boolean cellPrelight) {
		this.cellPrelight = cellPrelight;
	}

	public void setCellSelection(boolean cellSelection) {
		this.cellSelection = cellSelection;
	}

	public void setColumnHeaderPrelight(boolean columnHeaderPrelight) {
		this.columnHeaderPrelight = columnHeaderPrelight;
	}

	public void setColumnHeaders(boolean columnHeaders) {
		this.columnHeaders = columnHeaders;
	}

	public void setColumnPrelight(boolean columnPrelight) {
		this.columnPrelight = columnPrelight;
	}

	public void setColumnSelection(boolean columnSelection) {
		this.columnSelection = columnSelection;
	}

	public void setData(GridData data) {
		this.data = data;
	}

	public void setRowHeaderPrelight(boolean rowHeaderPrelight) {
		this.rowHeaderPrelight = rowHeaderPrelight;
	}

	public void setRowHeaders(boolean rowHeaders) {
		this.rowHeaders = rowHeaders;
	}

	public void setRowPrelight(boolean rowPrelight) {
		this.rowPrelight = rowPrelight;
	}

	public void setRowSelection(boolean rowSelection) {
		this.rowSelection = rowSelection;
	}

	public String getContainingDiv() {
		return containingDiv;
	}

	public void setContainingDiv(String containingDiv) {
		this.containingDiv = containingDiv;
	}

	public int getRowHeaderWidth() {
		return rowHeaderWidth;
	}

	public void setRowHeaderWidth(int rowHeaderWidth) {
		this.rowHeaderWidth = rowHeaderWidth;
	}

	public int getColWidth() {
		return colWidth;
	}

	public void setColWidth(int colWidth) {
		this.colWidth = colWidth;
	}

}
