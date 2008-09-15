package binky.reportrunner.engine.renderers;

public class RenderException extends Exception {

	
	private static final long serialVersionUID = -9220136437676619198L;

	public RenderException (String message, Exception e) {
		super(message,e);
	}
}
