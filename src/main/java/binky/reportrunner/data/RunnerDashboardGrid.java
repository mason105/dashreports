package binky.reportrunner.data;

import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "T_GRID")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RunnerDashboardGrid extends RunnerDashboardItem {


	private static final long serialVersionUID = 1L;

	private int rowsToDisplay;

	public int getRowsToDisplay() {
		return rowsToDisplay;
	}

	public void setRowsToDisplay(int rowsToDisplay) {
		this.rowsToDisplay = rowsToDisplay;
	}

	@Override
	public ItemType getItemType() {
		return ItemType.Grid;
	}
	
}
