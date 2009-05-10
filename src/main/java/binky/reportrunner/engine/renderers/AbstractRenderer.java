package binky.reportrunner.engine.renderers;

import java.io.OutputStream;
import java.sql.ResultSet;

import binky.reportrunner.exceptions.RenderException;

public abstract class AbstractRenderer {
	public abstract void generateReport(ResultSet resultSet, OutputStream outputStream,
			String extension) throws RenderException;
}
