package binky.reportrunner.engine;

import java.io.Serializable;

import org.apache.commons.beanutils.RowSetDynaClass;

public class ViewerResults implements Serializable {
	
	
	private static final long serialVersionUID = -2161910706155557952L;
	
	private RowSetDynaClass rowSet;
	private String outputId;
	public String getOutputId() {
		return outputId;
	}
	public void setOutputId(String outputId) {
		this.outputId = outputId;
	}
	public RowSetDynaClass getRowSet() {
		return rowSet;
	}
	public void setRowSet(RowSetDynaClass rowSet) {
		this.rowSet = rowSet;
	}
	public ViewerResults(RowSetDynaClass rowSet, String outputId) {
		this.rowSet = rowSet;
		this.outputId = outputId;
	}
	
	

}
