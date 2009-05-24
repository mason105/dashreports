package binky.jxlib.components;

import java.io.Serializable;

public abstract class JxLibComponent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	public JxLibComponent(String id) {
		this.id=id;
	}
	
	public final String getId() {
		return id;
	}
	
}
