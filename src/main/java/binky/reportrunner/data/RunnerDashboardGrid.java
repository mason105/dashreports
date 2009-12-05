package binky.reportrunner.data;

import javax.persistence.Entity;

@Entity
public class RunnerDashboardGrid extends RunnerDashboardItem {


	private static final long serialVersionUID = 1L;

	private int rowsToDisplay;

	public int getRowsToDisplay() {
		return rowsToDisplay;
	}

	public void setRowsToDisplay(int rowsToDisplay) {
		this.rowsToDisplay = rowsToDisplay;
	}
	
}
