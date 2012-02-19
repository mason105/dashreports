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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + rowsToDisplay;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RunnerDashboardGrid other = (RunnerDashboardGrid) obj;
		if (rowsToDisplay != other.rowsToDisplay)
			return false;
		return true;
	}
	
}
