package binky.reportrunner.ui.util;

import java.text.ParseException;

import org.quartz.CronTrigger;

import junit.framework.TestCase;

public class QuartzCronScheduleTest extends TestCase {

	public void testToString() {

		QuartzCronSchedule c = new QuartzCronSchedule();

		check(c);

		c.setAllSeconds(false);
		c.setSeconds(new int[] { 0 });
		c.setAllMinutes(true);
		c.setAllHours(true);
		c.setAllDaysOfWeek(true);
		c.setAllDaysOfMonth(true);
		c.setAllMonths(true);

		check(c);
		c.setAllSeconds(false);
		c.setSeconds(new int[] { 0, 1, 2, 3, 4 });

		check(c);

		c.setAllDaysOfWeek(false);
		c.setDaysOfWeek(new int[] { 1, 2, 3 });

		check(c);

		c.setAllDaysOfMonth(false);
		c.setDaysOfMonth(new int[] { 1, 2, 3 });

		check(c);

		c.setAllMinutes(false);
		c.setMinutes(new int[] { 15, 30, 45 });
		check(c);
		c.setAllHours(false);
		c.setHours(new int[] { 8, 16, 22 });
		check(c);
		c.setAllMonths(false);
		c.setMonths(new int[] { 1, 3, 4, 5, 6, 7, 8, 9, 10 });
		check(c);
	}

	public void testConstructor() {

		String[] schedules = { "* * * ? * *", "0 * * ? * *",
				"0,1,2,3,4 * * ? * *", "0,1,2,3,4 * * ? * 1,2,3",
				"0,1,2,3,4 * * 1,2,3 * 1,2,3",
				"0,1,2,3,4 15,30,45 * 1,2,3 * 1,2,3",
				"0,1,2,3,4 15,30,45 8,16,22 1,2,3 * 1,2,3",
				"0,1,2,3,4 15,30,45 8,16,22 1,2,3 1,3,4,5,6,7,8,9,10 1,2,3", };

		for (String s:schedules) {
			QuartzCronSchedule q = new QuartzCronSchedule(s);
			System.out.println(s+ "    :   " + q.toString());			
			assertTrue(q.toString().equals(s));
			check(q);
		}
		
		QuartzCronSchedule c = new QuartzCronSchedule("0 * * * * ?");
		System.out.println("{"+ c + "}");
		System.out.println("{"+ c.isAllDaysOfMonth() + "}");
	}

	private void check(QuartzCronSchedule c) {
		System.out.println("["+ c + "]");

		try {
			new CronTrigger("test", "test", c.toString());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

}
