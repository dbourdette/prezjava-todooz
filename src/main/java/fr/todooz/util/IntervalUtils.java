package fr.todooz.util;

import org.joda.time.DateMidnight;
import org.joda.time.Interval;

public class IntervalUtils {
	public static Interval todayInterval() {
		DateMidnight today = new DateMidnight();
		
		return new Interval(today, today.plusDays(1));
	}
	
	public static Interval tomorrowInterval() {
		DateMidnight today = new DateMidnight();
		
		return new Interval(today.plusDays(1), today.plusDays(2));
	}
}
