package binky.reportrunner.engine.renderers.exporters;

import java.io.OutputStream;
import java.sql.ResultSet;

public abstract class AbstractExporter {
	public abstract void export(ResultSet resultSet, OutputStream outputStream) throws ExportException;
}
