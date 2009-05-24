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
