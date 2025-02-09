package com.pgmanagement.utility;

public class Constants {

	public enum UserRole {
		ROLE_GUEST("Guest"), ROLE_ADMIN("Admin"), ROLE_OWNER("Owner");

		private String role;

		private UserRole(String role) {
			this.role = role;
		}

		public String value() {
			return this.role;
		}
	}

	public enum ActiveStatus {
		ACTIVE("Active"), DEACTIVATED("Deactivated");

		private String status;

		private ActiveStatus(String status) {
			this.status = status;
		}

		public String value() {
			return this.status;
		}
	}

	public enum PropertyStatus {
		ACTIVE("Active"), DEACTIVATED("Deactivated"), PENDNING("Pending"), REJECTED("Rejected");

		private String status;

		private PropertyStatus(String status) {
			this.status = status;
		}

		public String value() {
			return this.status;
		}
	}

	public enum BookingStatus {
		PENDING("Pending"), APPROVED("APPROVED"), REJECT("REJECTED"), CANCELLED("CANCELLED");

		private String status;

		private BookingStatus(String status) {
			this.status = status;
		}

		public String value() {
			return this.status;
		}
	}

	public enum BookingPaymentStatus {
		PAID("Paid"), PENDING("Pending");

		private String status;

		private BookingPaymentStatus(String status) {
			this.status = status;
		}

		public String value() {
			return this.status;
		}
	}

	public enum UserProfession {
		STUDENT("Student"), SERVICE("Private Service"), SELF_EMPLOYED("Self-Employed");

		private String profession;

		private UserProfession(String profession) {
			this.profession = profession;
		}

		public String value() {
			return this.profession;
		}
	}

	public enum PropertyRoomFor {
		BOYS("Boys"), GIRLS("Girls"), BOTH("Both");

		private String roomFor;

		private PropertyRoomFor(String roomFor) {
			this.roomFor = roomFor;
		}

		public String value() {
			return this.roomFor;
		}
	}

	public enum PropertyRoomType {
		SHARED("Shared"), PRIVATE("Private");

		private String type;

		private PropertyRoomType(String type) {
			this.type = type;
		}

		public String value() {
			return this.type;
		}
	}

	public enum PaymentGatewayTxnType {
		CREATE_ORDER("Create Order"), PAYMENT("Payment");

		private String type;

		private PaymentGatewayTxnType(String type) {
			this.type = type;
		}

		public String value() {
			return this.type;
		}
	}

	public enum PaymentGatewayTxnStatus {
		SUCCESS("Success"), FAILED("Failed");

		private String type;

		private PaymentGatewayTxnStatus(String type) {
			this.type = type;
		}

		public String value() {
			return this.type;
		}
	}

	public enum BookingPaymentType {
		WALLET("Wallet"), DIRECT("Direct");

		private String type;

		private BookingPaymentType(String type) {
			this.type = type;
		}

		public String value() {
			return this.type;
		}
	}

	public enum MonthEnum {
		JANUARY("January"), FEBRUARY("February"), MARCH("March"), APRIL("April"), MAY("May"), JUNE("June"),
		JULY("July"), AUGUST("August"), SEPTEMBER("September"), OCTOBER("October"), NOVEMBER("November"),
		DECEMBER("December");

		private final String monthName;

		MonthEnum(String monthName) {
			this.monthName = monthName;
		}

		public String getMonthName() {
			return monthName;
		}

		public static MonthEnum fromMonthName(String monthName) {
			for (MonthEnum monthEnum : values()) {
				if (monthEnum.getMonthName().equalsIgnoreCase(monthName)) {
					return monthEnum;
				}
			}
			throw new IllegalArgumentException("Invalid month name: " + monthName);
		}
	}

}
