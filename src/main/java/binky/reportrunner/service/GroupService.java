package binky.reportrunner.service;

import java.util.List;

import binky.reportrunner.data.RunnerGroup;

public interface GroupService {

	public List<RunnerGroup> getAll();
	public void saveOrUpdate(RunnerGroup group);
	public void delete(String groupName);
	public RunnerGroup getGroup(String groupName);
	
}
