package binky.reportrunner.ui.actions.job.beans;

public final class QuartzCronSchedule {
	
	private int[] seconds;
	private boolean allSeconds;

	private int[] minutes;
	private boolean allMinutes;
	
	private int[] hours;
	private boolean allHours;
	
	private int[] daysOfMonth;
	private boolean allDaysOfMonth; //?
	
	private int[] daysOfWeek;
	private boolean allDaysOfWeek; //?
	
	private int[] months;
	private boolean allMonths;
	
	/*
		Field Name Mandatory Allowed Values Allowed Special Characters 
		Seconds YES 0-59 , - * / 
		Minutes YES 0-59 , - * / 
		Hours YES 0-23 , - * / 
		Day of month YES 1-31 , - * ? / L W		 
		Month YES 1-12 or JAN-DEC , - * / 
		Day of week YES 1-7 or SUN-SAT , - * ? / L # 
		Year NO empty, 1970-2099 , - * / 
	 */
	
	@Override
	public String toString() {
		StringBuilder cron = new StringBuilder();
		
		//seconds
		cron.append(getCronSegment(allSeconds,"*",seconds));
		//minutes
		cron.append(getCronSegment(allMinutes,"*",minutes));
		//hours
		cron.append(getCronSegment(allHours,"*",hours));
		//day of month
		cron.append(getCronSegment(allDaysOfMonth,"?",daysOfMonth));
		//month
		cron.append(getCronSegment(allMonths,"*",months));
		//day of month
		cron.append(getCronSegment(allDaysOfWeek,"?",daysOfWeek));
		
		return cron.toString();
	}

	private String getCronSegment(boolean all, String allString, int[] nums) {
		StringBuilder cronSegment = new StringBuilder();
		if (all) {
			cronSegment.append(allString);
			cronSegment.append(" ");
		} else {
			if ((nums==null)||(nums.length==0)) {
				cronSegment.append("0 ");
			} else {				
				for (int i=0;i<nums.length;i++) {
					if (i>0) cronSegment.append(",");
					cronSegment.append(""+nums[i]);
				}
			}
		}
		
		return cronSegment.toString();
	}
	
	public int[] getSeconds() {
		return seconds;
	}

	public void setSeconds(int[] seconds) {
		this.seconds = seconds;
	}

	public boolean isAllSeconds() {
		return allSeconds;
	}

	public void setAllSeconds(boolean allSeconds) {
		this.allSeconds = allSeconds;
	}

	public int[] getMinutes() {
		return minutes;
	}

	public void setMinutes(int[] minutes) {
		this.minutes = minutes;
	}

	public boolean isAllMinutes() {
		return allMinutes;
	}

	public void setAllMinutes(boolean allMinutes) {
		this.allMinutes = allMinutes;
	}

	public int[] getHours() {
		return hours;
	}

	public void setHours(int[] hours) {
		this.hours = hours;
	}

	public boolean isAllHours() {
		return allHours;
	}

	public void setAllHours(boolean allHours) {
		this.allHours = allHours;
	}

	public int[] getDaysOfMonth() {
		return daysOfMonth;
	}

	public void setDaysOfMonth(int[] daysOfMonth) {
		this.daysOfMonth = daysOfMonth;
	}

	public boolean isAllDaysOfMonth() {
		return allDaysOfMonth;
	}

	public void setAllDaysOfMonth(boolean allDaysOfMonth) {
		this.allDaysOfMonth = allDaysOfMonth;
	}

	public int[] getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setDaysOfWeek(int[] daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	public boolean isAllDaysOfWeek() {
		return allDaysOfWeek;
	}

	public void setAllDaysOfWeek(boolean allDaysOfWeek) {
		this.allDaysOfWeek = allDaysOfWeek;
	}

	public int[] getMonths() {
		return months;
	}

	public void setMonths(int[] months) {
		this.months = months;
	}

	public boolean isAllMonths() {
		return allMonths;
	}

	public void setAllMonths(boolean allMonths) {
		this.allMonths = allMonths;
	}
	
	

}
