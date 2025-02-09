package com.pgmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgmanagement.dao.PropertyBookingDao;
import com.pgmanagement.entity.Property;
import com.pgmanagement.entity.PropertyBooking;
import com.pgmanagement.entity.PropertyRoomDetail;
import com.pgmanagement.entity.PropertySharedRoomBedDetail;
import com.pgmanagement.entity.User;

@Service
public class PropertyBookingServiceImpl implements PropertyBookingService {

	@Autowired
	private PropertyBookingDao propertyBookingDao;

	@Override
	public PropertyBooking addBooking(PropertyBooking booking) {
		// TODO Auto-generated method stub
		return propertyBookingDao.save(booking);
	}

	@Override
	public PropertyBooking getById(int bookingId) {

		Optional<PropertyBooking> optionalPropertybooking = this.propertyBookingDao.findById(bookingId);

		if (optionalPropertybooking.isPresent()) {
			return optionalPropertybooking.get();
		} else {
			return null;
		}

	}

	@Override
	public List<PropertyBooking> getByPropertyAndRoomDetailAndRoomTypeAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
			Property property, PropertyRoomDetail roomDetail, String roomType, String status, String endDate,
			String startDate) {
		// TODO Auto-generated method stub
		return this.propertyBookingDao
				.findByPropertyAndRoomDetailAndRoomTypeAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
						property, roomDetail, roomType, status, endDate, startDate);
	}

	@Override
	public List<PropertyBooking> getByPropertyAndRoomDetailAndRoomTypeAndBedDetailAndStatusAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
			Property property, PropertyRoomDetail roomDetail, String roomType, PropertySharedRoomBedDetail bedDetail,
			String status, String endDate, String startDate) {
		// TODO Auto-generated method stub
		return this.propertyBookingDao
				.findByPropertyAndRoomDetailAndRoomTypeAndBedDetailAndStatusAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
						property, roomDetail, roomType, bedDetail, status, endDate, startDate);
	}

	@Override
	public List<PropertyBooking> findByStatusOrderByIdDesc(String status) {
		// TODO Auto-generated method stub
		return this.propertyBookingDao.findByStatusOrderByIdDesc(status);
	}

	@Override
	public List<PropertyBooking> findByOwnerAndStatusOrderByIdDesc(User owner, String status) {
		// TODO Auto-generated method stub
		return this.propertyBookingDao.findByOwnerAndStatusOrderByIdDesc(owner, status);
	}

	@Override
	public List<PropertyBooking> findByGuestAndStatusOrderByIdDesc(User owner, String status) {
		// TODO Auto-generated method stub
		return this.propertyBookingDao.findByGuestAndStatusOrderByIdDesc(owner, status);
	}

}
