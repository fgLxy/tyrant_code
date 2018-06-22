package org.tyrant.core.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

	public static String currentTimeString() {
		LocalDateTime datetime = LocalDateTime.now();
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(datetime);
	}

	public static long currentDateTimestamp() {
		LocalDate date = LocalDate.now();
		return date.atTime(0, 0, 0).toInstant(ZoneOffset.of("+8")).toEpochMilli();
	}

	public static String currentDateTime(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}

}
