package binky.reportrunner.service;

import java.util.List;

import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;

public interface UserService {

	public void saveOrUpdate(RunnerUser user);

	public void deleteUser(String userName);;

	public RunnerUser getUser(String userName);

	public List<RunnerUser> getAll();
	
	public List<RunnerGroup> getGroupsForUser(String userName);

}
