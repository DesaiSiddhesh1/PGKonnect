package com.pgmanagement.dto;

import lombok.Data;

@Data
public class BookPgRoomRequest {

	private Integer guestId; // userId from user table

	private Integer propertyId;

	private Integer roomId;

	private String type;

	private Integer bedId; // if shared room

	private String startDate; // January 2024

	private String endDate; // December 2024

}
