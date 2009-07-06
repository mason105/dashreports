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
 * Module: AbstractRenderer.java
 ******************************************************************************/
package binky.reportrunner.engine.renderers;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import binky.reportrunner.exceptions.RenderException;

public abstract class AbstractRenderer {
	public abstract void generateReport(ResultSet resultSet, OutputStream outputStream,
			String extension) throws RenderException, SQLException;
}
