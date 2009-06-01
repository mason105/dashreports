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
 * Module: GridData.java
 ******************************************************************************/
package binky.jxlib.components.grid;

import java.io.Serializable;

public class GridData implements Serializable {

	private static final long serialVersionUID = 7577460737757322785L;

	/**
	 * @param columnHeaders
	 * @param rowHeaders
	 * @param data
	 */
	public GridData(String[] columnHeaders, String[] rowHeaders, String[] data) {
		super();
		
		if (columnHeaders==null) {
			this.columnHeaders=new String[0];
		} else {
			this.columnHeaders = columnHeaders;
		}
		
		if (rowHeaders==null) {
			this.rowHeaders=new String[0];
		} else {
			this.rowHeaders = rowHeaders;
		}
		this.data = data;
	}

	private String[] columnHeaders;
	
	private String[] rowHeaders;
	
	private String[] data;

	public String[] getColumnHeaders() {
		return columnHeaders;
	}

	public void setColumnHeaders(String[] columnHeaders) {
		this.columnHeaders = columnHeaders;
	}

	public String[] getRowHeaders() {
		return rowHeaders;
	}

	public void setRowHeaders(String[] rowHeaders) {
		this.rowHeaders = rowHeaders;
	}

	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}
	
}
