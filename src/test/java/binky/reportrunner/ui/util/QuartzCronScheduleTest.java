package binky.reportrunner.ui.util;

import java.text.ParseException;

import org.quartz.CronTrigger;

import junit.framework.TestCase;

public class QuartzCronScheduleTest extends TestCase {

	public void testToString() {

		QuartzCronSchedule c = new QuartzCronSchedule();

		check(c);
		
		c.setAllSeconds(false);
		c.setSeconds(new int[]{0});
		c.setAllMinutes(true);
		c.setAllHours(true);
		c.setAllDaysOfWeek(true);
		c.setAllDaysOfMonth(true);
		c.setAllMonths(true);

		check(c);
		c.setAllSeconds(false);
		c.setSeconds(new int[]{0,1,2,3,4});
		
		check(c);
		
		c.setAllDaysOfWeek(false);
		c.setDaysOfWeek(new int[]{1,2,3});
		
		check(c);
		
		
		c.setAllDaysOfMonth(false);
		c.setDaysOfMonth(new int[]{1,2,3});
		
		check(c);
		
		c.setAllMinutes(false);
		c.setMinutes(new int[]{15,30,45});
		check(c);
		c.setAllHours(false);
		c.setHours(new int[]{8,16,22});
		check(c);
		c.setAllMonths(false);
		c.setMonths(new int[]{1,3,4,5,6,7,8,9,10});
		check(c);
	}

	private void check(QuartzCronSchedule c) {
		System.out.println(c);

		try {
			CronTrigger t = new CronTrigger("test", "test", c.toString());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

}
