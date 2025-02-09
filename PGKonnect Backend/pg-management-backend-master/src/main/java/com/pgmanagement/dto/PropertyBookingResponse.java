package com.pgmanagement.dto;

import java.util.ArrayList;
import java.util.List;

import com.pgmanagement.entity.PropertyBooking;

import lombok.Data;

@Data
public class PropertyBookingResponse extends CommonApiResponse {

	private List<PropertyBooking> bookings = new ArrayList<>();

}
