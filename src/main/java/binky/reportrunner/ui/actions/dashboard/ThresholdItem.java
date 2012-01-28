package binky.reportrunner.ui.actions.dashboard;

public class ThresholdItem {

	private Object value;
	private int status;
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public ThresholdItem(Object value, int status) {		
		this.value = value;
		this.status = status;
	}
	
	public ThresholdItem() {
		
	}
	
}
