package binky.reportrunner.engine.renderers.exporters;

public class ExportException extends Exception {

	private static final long serialVersionUID = -8598613745537792061L;

	public ExportException(String message, Exception e) {
		super(message, e);
	}

}
