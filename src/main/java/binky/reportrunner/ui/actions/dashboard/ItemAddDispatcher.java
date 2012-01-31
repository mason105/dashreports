package binky.reportrunner.ui.actions.dashboard;

import java.util.Arrays;
import java.util.List;

import binky.reportrunner.data.RunnerDashboardItem.ItemType;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ItemAddDispatcher extends StandardRunnerAction {

	private static final long serialVersionUID = 2643417065013583027L;
	private ItemType itemType;
	
	@Override
	public String execute() throws Exception {
		return itemType.getDisplayName();
	}
	public final List<ItemType> getItemTypes() {
		return Arrays.asList(ItemType.values());
	}

	public ItemType getItemType() {
		return itemType;
	}
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

}
