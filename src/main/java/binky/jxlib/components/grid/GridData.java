package binky.jxlib.components.grid;

import java.io.Serializable;

public class GridData implements Serializable {

	private static final long serialVersionUID = 7577460737757322785L;

	/**
	 * @param columnHeaders
	 * @param rowHeaders
	 * @param data
	 */
	public GridData(String[] columnHeaders, String[] rowHeaders, String[] data) {
		super();
		
		if (columnHeaders==null) {
			this.columnHeaders=new String[0];
		} else {
			this.columnHeaders = columnHeaders;
		}
		
		if (rowHeaders==null) {
			this.rowHeaders=new String[0];
		} else {
			this.rowHeaders = rowHeaders;
		}
		this.data = data;
	}

	private String[] columnHeaders;
	
	private String[] rowHeaders;
	
	private String[] data;

	public String[] getColumnHeaders() {
		return columnHeaders;
	}

	public void setColumnHeaders(String[] columnHeaders) {
		this.columnHeaders = columnHeaders;
	}

	public String[] getRowHeaders() {
		return rowHeaders;
	}

	public void setRowHeaders(String[] rowHeaders) {
		this.rowHeaders = rowHeaders;
	}

	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}
	
}
