package binky.reportrunner.engine.renderers.exporters;

import java.io.File;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.engine.FileSystemHandler;
import binky.reportrunner.engine.SQLProcessor;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import junit.framework.TestCase;

public class XLSExporterTest extends TestCase {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	public void testExport() {
		try {
			AbstractExporter exp = new XLSExporter();
			FileSystemHandler fs = new FileSystemHandler();
			OutputStream os = fs.getOutputStreamForUrl("file://"
					+ System.getProperty("java.io.tmpdir") + "/test.file");
			SQLProcessor proc = new SQLProcessor();
			ComboPooledDataSource ds = (ComboPooledDataSource) ctx
					.getBean("dataSource");
			Connection connection = ds.getConnection();
			String sql = "select * from runneruser";
			ResultSet res = proc.getResults(connection, sql);
			exp.export(res, os);
			os.close();
			File test = new File(System.getProperty("java.io.tmpdir")
					+ File.separatorChar + "test.file");
			System.out.println(test.getAbsolutePath());
			assertTrue(test.exists());
			assertTrue(test.isFile());
			assertTrue(test.length() > 0);
			test.delete();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
