package com.pgmanagement.service;

import java.util.List;

import com.pgmanagement.entity.Property;
import com.pgmanagement.entity.PropertyBooking;
import com.pgmanagement.entity.PropertyRoomDetail;
import com.pgmanagement.entity.PropertySharedRoomBedDetail;
import com.pgmanagement.entity.User;

public interface PropertyBookingService {

	PropertyBooking addBooking(PropertyBooking booking);

	PropertyBooking getById(int bookingId);

	List<PropertyBooking> getByPropertyAndRoomDetailAndRoomTypeAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
			Property property, PropertyRoomDetail roomDetail, String roomType, String status, String endDate,
			String startDate);

	List<PropertyBooking> getByPropertyAndRoomDetailAndRoomTypeAndBedDetailAndStatusAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
			Property property, PropertyRoomDetail roomDetail, String roomType, PropertySharedRoomBedDetail bedDetail,
			String status, String endDate, String startDate);

	List<PropertyBooking> findByStatusOrderByIdDesc(String status);

	List<PropertyBooking> findByOwnerAndStatusOrderByIdDesc(User owner, String status);

	List<PropertyBooking> findByGuestAndStatusOrderByIdDesc(User owner, String status);

}
