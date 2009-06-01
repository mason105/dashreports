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
 * Module: JxLibTag.java
 ******************************************************************************/
package binky.jxlib.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

public abstract class JxLibTag implements Tag {

	protected PageContext pageContext;
	protected Tag parent;

	public final void setPageContext(
			final javax.servlet.jsp.PageContext pageContext) {
		this.pageContext = pageContext;
	}

	public final void setParent(final javax.servlet.jsp.tagext.Tag parent) {
		this.parent = parent;
	}
	public final Tag getParent() {
		return parent;
	}
	
	public final void release() {
		
	}

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
}
