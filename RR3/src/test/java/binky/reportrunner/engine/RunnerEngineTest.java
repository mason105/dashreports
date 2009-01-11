package binky.reportrunner.engine;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.apache.commons.vfs.FileObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.dao.impl.RunnerGroupDaoImplTest;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJobParameter_pk;
import binky.reportrunner.data.RunnerJob_pk;
import binky.reportrunner.data.RunnerJob.FileFormat;
import binky.reportrunner.data.RunnerJobParameter.DataType;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class RunnerEngineTest extends TestCase {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	DataSource ds;
	RunnerEngine engine;
	RunnerGroup testGroup;

	@Override
	protected void setUp() throws Exception {
		this.ds = (ComboPooledDataSource) ctx.getBean("dataSource");
		engine = new RunnerEngine();
		engine.ds = ds;
		RunnerGroupDao groupDao = (RunnerGroupDao) ctx
				.getBean("runnerGroupDao");
		testGroup = RunnerGroupDaoImplTest.getDemoGroup("test"
				+ Calendar.getInstance().getTimeInMillis());
		groupDao.saveUpdateGroup(testGroup);
	}

	protected void tearDown() throws Exception {
		RunnerGroupDao groupDao = (RunnerGroupDao) ctx
				.getBean("runnerGroupDao");
		groupDao.deleteGroup(testGroup.getGroupName());
	}

	private RunnerJob getTestJob(String jobName, String url) {
		RunnerJob_pk pk = new RunnerJob_pk();
		pk.setGroup(testGroup);
		pk.setJobName(jobName);
		RunnerJob job = new RunnerJob();
		job.setPk(pk);
		job.setOutputUrl(url);
		job.setFileFormat(FileFormat.CSV);
		return job;
	}

	public void testProcessSingleReport() {
		try {

			RunnerJob job = getTestJob("testSingle", "file://"
					+ System.getProperty("java.io.tmpdir")+"/test");
			List<RunnerJobParameter> params = new LinkedList<RunnerJobParameter>();
			job.setParameters(params);
			job.setQuery("select * from runneruser");

			String url = engine.processSingleReport(job);

			FileSystemHandler fsh = new FileSystemHandler();
			FileObject fo = fsh.getFileObjectForUrl(url);

			assertTrue(fo.exists());
			assertTrue(fo.isReadable());
			fsh.deleteFile(url);

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());			
		}
	}

	public void testProcessBurstedReport() {
		try {
			RunnerJob job = getTestJob("testBurst", "file://"
					+ System.getProperty("java.io.tmpdir")+"/test");
			job.setBurstFileNameParameterName("userName");
			String sql = "select userName from runnerUser";
			job.setBurstQuery(sql);
			job.setQuery("select * from runneruser where userName=?");
			List<RunnerJobParameter> params = new LinkedList<RunnerJobParameter>();
			RunnerJobParameter param = new RunnerJobParameter();
			RunnerJobParameter_pk pk = new RunnerJobParameter_pk();
			pk.setRunnerJob_pk(job.getPk());
			pk.setParameterIdx(1);
			param.setPk(pk);
			param.setParameterType(DataType.STRING);
			param.setParameterBurstColumn("userName");
			params.add(param);
			job.setParameters(params);
			List<String> urls = engine.processBurstedReport(job);
			assertNotNull(urls);
			int rowCount = 0;
			ResultSet res = ds.getConnection().createStatement().executeQuery(
					sql);
			assertTrue(res.last());
			rowCount = res.getRow();

			assertEquals(urls.size(), rowCount);

			for (String url : urls) {
				FileSystemHandler fsh = new FileSystemHandler();
				FileObject fo = fsh.getFileObjectForUrl(url);

				assertTrue(fo.exists());
				assertTrue(fo.isReadable());
				fsh.deleteFile(url);
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());			
		}
	}

}