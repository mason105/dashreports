package binky.reportrunner.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name="T_D_DATA")
public class DashboardData extends DatabaseObject<Long> {

	private static final long serialVersionUID = -5699427071162312552L;

	public DashboardData() {}
	
	public DashboardData(RunnerDashboardItem item, Date timeStored,
			String columnName,String value, int dataType, int rowNumber) {
		this.item = item;
		this.timeStored = timeStored;
		this.columnName = columnName;
		this.value = value;
		this.dataType = dataType;
		this.rowNumber=rowNumber;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private RunnerDashboardItem item;

	private Date timeStored;
	private int rowNumber;
	private String columnName;
	private String value;	
	
	//java.sql.Types
	private int dataType;
	@Override
	public Long getId() {
		return id;
	}

	public RunnerDashboardItem getItem() {
		return item;
	}

	public void setItem(RunnerDashboardItem item) {
		this.item = item;
	}

	public Date getTimeStored() {
		return timeStored;
	}

	public void setTimeStored(Date timeStored) {
		this.timeStored = timeStored;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

}
