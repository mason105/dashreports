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
 * Module: ViewerResults.java
 ******************************************************************************/
package binky.reportrunner.engine;

import java.io.Serializable;

import org.apache.commons.beanutils.RowSetDynaClass;

public class ViewerResults implements Serializable {
	
	
	private static final long serialVersionUID = -2161910706155557952L;
	
	private RowSetDynaClass rowSet;
	private String outputId;
	public String getOutputId() {
		return outputId;
	}
	public void setOutputId(String outputId) {
		this.outputId = outputId;
	}
	public RowSetDynaClass getRowSet() {
		return rowSet;
	}
	public void setRowSet(RowSetDynaClass rowSet) {
		this.rowSet = rowSet;
	}
	public ViewerResults(RowSetDynaClass rowSet, String outputId) {
		this.rowSet = rowSet;
		this.outputId = outputId;
	}
	
	

}
