package binky.reportrunner.ui.actions.admin;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.data.RunnerHistoryEvent.Module;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class WarningsStats extends StandardRunnerAction {


	private static final long serialVersionUID = -7476318388046063816L;
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {
		return SUCCESS;
	}
	public final List<Module> getModules() {
		return Arrays.asList(Module.values());
	}
}
