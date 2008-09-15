package binky.reportrunner.engine.renderers;

import java.io.OutputStream;
import java.sql.ResultSet;

public abstract class AbstractRenderer {
	public abstract void generateReport(ResultSet resultSet, OutputStream outputStream,
			String extension) throws RenderException;
}
