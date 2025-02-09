package com.pgmanagement.utility;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Helper {

	public static String generateBookingId() {

		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		StringBuilder sb = new StringBuilder(16);
		sb.append("B-");

		for (int i = 0; i < 14; i++) {

			int index = (int) (AlphaNumericString.length() * Math.random());

			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString().toUpperCase();
	}

	public static String generateBookingPaymentId() {

		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		StringBuilder sb = new StringBuilder(16);
		sb.append("P-");

		for (int i = 0; i < 14; i++) {

			int index = (int) (AlphaNumericString.length() * Math.random());

			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString().toUpperCase();
	}

	public static LocalDateTime convertToDate(String monthName, int year, boolean isFirstDate) {
		// Convert month name to Month enum
		Month month = Month.valueOf(monthName.toUpperCase());

		// Get the first date of the month
		LocalDate firstDate = LocalDate.of(year, month, 1);

		if (isFirstDate) {
			// Return the first date of the month with time set to 12:00 AM
			return firstDate.atStartOfDay();
		} else {
			// Get the last day of the month
			int lastDay = YearMonth.of(year, month).lengthOfMonth();
			LocalDate lastDate = LocalDate.of(year, month, lastDay);

			// Return the last date of the month with time set to 11:59:59 PM
			return lastDate.atTime(LocalTime.MAX);
		}
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

	public static List<String> getMonthsBetween(String startMillis, String endMillis) {
		List<String> months = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");

		// Convert String millis to LocalDateTime
		LocalDateTime startDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(startMillis)),
				ZoneId.systemDefault());
		LocalDateTime endDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(endMillis)),
				ZoneId.systemDefault());

		LocalDateTime current = startDateTime.withDayOfMonth(1); // Start at the beginning of the month
		endDateTime = endDateTime.withDayOfMonth(1); // Normalize endDateTime to the first day of the month

		while (!current.isAfter(endDateTime)) {
			months.add(current.format(formatter));
			current = current.plusMonths(1);
		}

		return months;
	}
	
	public static List<String> getMonthsBetweenWithTime(String startMillis, String endMillis) {
	    List<String> months = new ArrayList<>();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");

	    // Convert String millis to LocalDateTime
	    LocalDateTime startDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(startMillis)),
	            ZoneId.systemDefault());
	    LocalDateTime endDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(endMillis)),
	            ZoneId.systemDefault());

	    // Set current to startDateTime and normalize endDateTime to the last day of its month
	    LocalDateTime current = startDateTime.withDayOfMonth(1); // Beginning of the first month
	    endDateTime = endDateTime.withDayOfMonth(endDateTime.toLocalDate().lengthOfMonth()); // Last day of the last month

	    while (!current.isAfter(endDateTime)) {
	        months.add(current.format(formatter));
	        current = current.plusMonths(1);
	    }

	    return months;
	}

}
