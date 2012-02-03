package binky.reportrunner.ui.util;

import org.apache.commons.lang.StringUtils;

public final class QuartzCronSchedule {

	public QuartzCronSchedule() {
		this.parseSchedule("0 * * ? * *");
	}

	public QuartzCronSchedule(String cronString) {
		if (StringUtils.isEmpty(cronString)) cronString="0 * * ? * *";
		this.parseSchedule(cronString);
	}
	private void parseSchedule (String cronString) {
		String[] split = cronString.split(" ");

		// 1 2 3 4 5 6
		// 0 * * * * ?

		if (split.length == 6) {

			allSeconds = (split[0].equals("*"));
			allMinutes = (split[1].equals("*"));
			allHours =  split[2].equals("*");
			allDaysOfMonth = split[3].equals("?") ||split[3].equals("*");			
			allMonths = (split[4].equals("*"));
			allDaysOfWeek = (split[5].equals("?") || split[5].equals("*"));
			
			if (!allSeconds) this.seconds=parseSegment(split[0]); 
			if (!allMinutes) this.minutes=parseSegment(split[1]); 
			if (!allHours) this.hours=parseSegment(split[2]); 
			if (!allDaysOfMonth) this.daysOfMonth=parseSegment(split[3]); 
			if (!allMonths) this.months=parseSegment(split[4]); 
			if (!allDaysOfWeek) this.daysOfWeek=parseSegment(split[5]);
		}

	}
	
	private int[] parseSegment(String segment) {
		String[] split = segment.split(",");
		
		int[] ret= new int[split.length];
		int x =0;
		for (String s:split) {
			int i=0;
			try {
				i=Integer.parseInt(s);
			} catch (NumberFormatException e) {
				
			}
			ret[x++]=i;
		}		
		return ret;
	}

	private int[] seconds;
	private boolean allSeconds = true;

	private int[] minutes;
	private boolean allMinutes = true;

	private int[] hours;
	private boolean allHours = true;

	private int[] daysOfMonth;
	private boolean allDaysOfMonth = true; // ?

	private int[] daysOfWeek;
	private boolean allDaysOfWeek = true; // ?

	private int[] months;
	private boolean allMonths = true;

	/*
	 * Field Name Mandatory Allowed Values Allowed Special Characters Seconds
	 * YES 0-59 , - * / Minutes YES 0-59 , - * / Hours YES 0-23 , - * / Day of
	 * month YES 1-31 , - * ? / L W Month YES 1-12 or JAN-DEC , - * / Day of
	 * week YES 1-7 or SUN-SAT , - * ? / L # Year NO empty, 1970-2099 , - * /
	 */

	@Override
	public String toString() {
		StringBuilder cron = new StringBuilder();

		// seconds
		cron.append(getCronSegment(allSeconds, "*", seconds));
		cron.append(" ");
		// minutes
		cron.append(getCronSegment(allMinutes, "*", minutes));
		cron.append(" ");
		// hours
		cron.append(getCronSegment(allHours, "*", hours));
		cron.append(" ");
		// day of month
		cron.append(getCronSegment(allDaysOfMonth, "?", daysOfMonth));
		cron.append(" ");
		// month
		cron.append(getCronSegment(allMonths, "*", months));
		cron.append(" ");
		// day of month
		if (allDaysOfMonth) {
			cron.append(getCronSegment(allDaysOfWeek, "*", daysOfWeek));
		} else {
			cron.append(getCronSegment(allDaysOfWeek, "?", daysOfWeek));
		}

		return cron.toString();
	}

	private String getCronSegment(boolean all, String allString, int[] nums) {
		StringBuilder cronSegment = new StringBuilder();
		if (all) {
			cronSegment.append(allString);
		} else {
			if ((nums == null) || (nums.length == 0)) {
				cronSegment.append("0");
			} else {
				for (int i = 0; i < nums.length; i++) {
					if (i > 0)
						cronSegment.append(",");
					cronSegment.append(nums[i]);
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
