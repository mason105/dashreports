package binky.reportrunner.dao;

import java.util.List;

import binky.reportrunner.data.RunnerUser;

public interface RunnerUserDao {
	
	public void saveUpdateUser(RunnerUser user);
	public void deleteUser(String userName);
	public RunnerUser getUser(String userName);
	public List<RunnerUser> listUsers();

}
