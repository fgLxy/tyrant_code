package org.tyrant.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {

	public static String currentTimeString() {
		return currentDateTime("yyyy-MM-dd HH:mm:ss");
	}
	
	public static String currentDateTime(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}

	public static int subDayByDay(String beginTime, String endTime, String pattern, LocalTime daySplit) {
		daySplit = daySplit == null ? LocalTime.MIN : daySplit;
		LocalDateTime begin = LocalDateTime.parse(beginTime, DateTimeFormatter.ofPattern(pattern));
		LocalDateTime end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern(pattern));
		begin = begin.minusNanos(daySplit.toNanoOfDay());
		end = end.minusNanos(daySplit.toNanoOfDay());
		begin = begin.withHour(0).withMinute(0).withSecond(0).withNano(0);
		end = end.withHour(0).withMinute(0).withSecond(0).withNano(0);
		return (int) begin.until(end, ChronoUnit.DAYS);
	}

	public static int subDayByDay(String beginTime, String endTime, String pattern) {
		return subDayByDay(beginTime, endTime, pattern, null);
	}

	public static int subHourByTimestamp(String beginTime, String endTime, String pattern) {
		LocalDateTime begin = LocalDateTime.parse(beginTime, DateTimeFormatter.ofPattern(pattern));
		LocalDateTime end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern(pattern));
		return (int) begin.until(end, ChronoUnit.HOURS);
	}
	
	public static int subMinutesByTimestamp(String beginTime, String endTime, String pattern) {
		LocalDateTime begin = LocalDateTime.parse(beginTime, DateTimeFormatter.ofPattern(pattern));
		LocalDateTime end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern(pattern));
		return (int) begin.until(end, ChronoUnit.MINUTES);
	}
	
	public static Date parseDate(String dateStr, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isPassHalfHour(String dateStr, String pattern) {
		LocalDateTime time = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
		return time.getMinute() > 30;
	}

	public static boolean isPassHalfDay(String dateStr, String pattern) {
		LocalDateTime time = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
		return time.getHour() > 12;
	}

}
