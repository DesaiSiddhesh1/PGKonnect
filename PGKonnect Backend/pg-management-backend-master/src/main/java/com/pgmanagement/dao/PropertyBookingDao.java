package com.pgmanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pgmanagement.entity.Property;
import com.pgmanagement.entity.PropertyBooking;
import com.pgmanagement.entity.PropertyRoomDetail;
import com.pgmanagement.entity.PropertySharedRoomBedDetail;
import com.pgmanagement.entity.User;

@Repository
public interface PropertyBookingDao extends JpaRepository<PropertyBooking, Integer> {

	List<PropertyBooking> findByPropertyAndRoomDetailAndRoomTypeAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
			Property property, PropertyRoomDetail roomDetail, String roomType, String status, String endDate,
			String startDate);

	List<PropertyBooking> findByPropertyAndRoomDetailAndRoomTypeAndBedDetailAndStatusAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
			Property property, PropertyRoomDetail roomDetail, String roomType, PropertySharedRoomBedDetail bedDetail,
			String status, String endDate, String startDate);

	List<PropertyBooking> findByStatusOrderByIdDesc(String status);

	@Query("SELECT b FROM PropertyBooking b WHERE b.property.owner =:owner and b.status =:status")
	List<PropertyBooking> findByOwnerAndStatusOrderByIdDesc(User owner, String status);

	List<PropertyBooking> findByGuestAndStatusOrderByIdDesc(User guest, String status);

}
