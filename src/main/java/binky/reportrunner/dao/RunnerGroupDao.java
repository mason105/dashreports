package binky.reportrunner.dao;

import java.util.List;

import binky.reportrunner.data.RunnerGroup;

public interface RunnerGroupDao {

	public void saveUpdateGroup(RunnerGroup group);
	public void deleteGroup(String groupName);
	public List<RunnerGroup> listGroups();
	public RunnerGroup getGroup(String groupName);
	
}
