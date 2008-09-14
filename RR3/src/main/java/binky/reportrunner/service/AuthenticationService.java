package binky.reportrunner.service;

import binky.reportrunner.data.RunnerUser;

public interface AuthenticationService {

	public RunnerUser authUser(String userName, String password);
	
}
