package com.pgmanagement.utility;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

	public static String getProperDateTimeFormatFromEpochTime(String epochTimeString) {

		long epochTimeMillis = Long.parseLong(epochTimeString);

		Instant instant = Instant.ofEpochMilli(epochTimeMillis);
		ZoneId zoneId = ZoneId.systemDefault();

		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);

		// Define the desired date format
		String dateFormatPattern = "yyyy-MM-dd HH:mm:ss";

		// Format LocalDateTime to a specific date format
		String formattedDateTime = formatLocalDateTime(localDateTime, dateFormatPattern);

		// Print the formatted date and time
		System.out.println("Formatted DateTime: " + formattedDateTime);

		return formattedDateTime;
	}

	private static String formatLocalDateTime(LocalDateTime dateTime, String dateFormatPattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatPattern);
		return dateTime.format(formatter);
	}

	// Method to convert LocalDateTime to milliseconds as String
	public static String convertToMillisString(LocalDateTime localDateTime) {
		// Convert LocalDateTime to ZonedDateTime with system default time zone
		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

		// Convert ZonedDateTime to milliseconds
		long millis = zonedDateTime.toInstant().toEpochMilli();

		// Return milliseconds as String
		return String.valueOf(millis);
	}

}
