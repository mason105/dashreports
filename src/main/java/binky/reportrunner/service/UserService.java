package binky.reportrunner.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;

public interface UserService extends Auditable {

	public void saveOrUpdate(RunnerUser user);

	public void deleteUser(String userName);;

	public RunnerUser getUser(String userName);

	public List<RunnerUser> getAll();
	
	public List<RunnerGroup> getGroupsForUser(String userName);

	public boolean changePassword(String userName,String oldPasswordHash,String newPassword) throws NoSuchAlgorithmException ;
	
}
