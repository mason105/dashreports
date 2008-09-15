package binky.reportrunner.engine.renderers.exporters;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractExporter {
	public abstract void export(ResultSet resultSet, OutputStream outputStream) throws IOException, SQLException;
}
