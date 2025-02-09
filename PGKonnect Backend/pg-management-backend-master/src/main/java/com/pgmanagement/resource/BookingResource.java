package com.pgmanagement.resource;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pgmanagement.dto.BookPgRoomRequest;
import com.pgmanagement.dto.CommonApiResponse;
import com.pgmanagement.dto.PropertyBookingResponse;
import com.pgmanagement.entity.BookingPayment;
import com.pgmanagement.entity.Property;
import com.pgmanagement.entity.PropertyBooking;
import com.pgmanagement.entity.PropertyRoomDetail;
import com.pgmanagement.entity.PropertySharedRoomBedDetail;
import com.pgmanagement.entity.User;
import com.pgmanagement.service.BookingPaymentService;
import com.pgmanagement.service.PropertyBookingService;
import com.pgmanagement.service.PropertyRoomDetailService;
import com.pgmanagement.service.PropertyService;
import com.pgmanagement.service.UserService;
import com.pgmanagement.utility.Constants.BookingPaymentStatus;
import com.pgmanagement.utility.Constants.BookingPaymentType;
import com.pgmanagement.utility.Constants.BookingStatus;
import com.pgmanagement.utility.Constants.PropertyRoomType;
import com.pgmanagement.utility.Constants.UserRole;
import com.pgmanagement.utility.Helper;

@Component
public class BookingResource {

	private final Logger LOG = LoggerFactory.getLogger(BookingResource.class);

	@Autowired
	private PropertyBookingService propertyBookingService;

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private UserService userService;

	@Autowired
	private PropertyRoomDetailService propertyRoomDetailService;

	@Autowired
	private BookingPaymentService bookingPaymentService;

	public ResponseEntity<CommonApiResponse> bookPgRoom(BookPgRoomRequest request) {

		LOG.info("Request received for booking the room");

		CommonApiResponse response = new CommonApiResponse();

		String currentTime = String
				.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

		if (request == null) {
			response.setResponseMessage("request is null");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getGuestId() == 0) {
			response.setResponseMessage("missing guest id");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getPropertyId() == 0) {
			response.setResponseMessage("missing property id");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getRoomId() == 0) {
			response.setResponseMessage("missing room id");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Property property = this.propertyService.getById(request.getPropertyId());

		if (property == null) {
			response.setResponseMessage("property not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User guest = this.userService.getUserById(request.getGuestId());

		if (guest == null || !guest.getRole().equals(UserRole.ROLE_GUEST.value())) {
			response.setResponseMessage("Guest Not Found");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		PropertyRoomDetail room = propertyRoomDetailService.getRoomById(request.getRoomId());

		if (room == null) {
			response.setResponseMessage("Property Room not found!!!");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (!request.getType().equals(room.getType())) {
			response.setResponseMessage("Room Type " + request.getType() + " not avaiable for this room");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getType().equals(PropertyRoomType.SHARED.value()) && request.getBedId() == 0) {
			response.setResponseMessage("Bed Id missing for Shared room!!!");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		List<PropertySharedRoomBedDetail> sharedRoomBeds = null;

		if (request.getType().equals(PropertyRoomType.SHARED.value())) {
			sharedRoomBeds = room.getSharedRoomBeds();

			if (CollectionUtils.isEmpty(sharedRoomBeds)) {
				response.setResponseMessage("Shared Beds not found in the Room!!!");
				response.setSuccess(false);
				return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}

		String startMonth = request.getStartDate().split(" ")[0];
		Integer startYear = Integer.parseInt(request.getStartDate().split(" ")[1]);

		String endMonth = request.getEndDate().split(" ")[0];
		Integer endYear = Integer.parseInt(request.getEndDate().split(" ")[1]);

		LocalDateTime startDateTime = Helper.convertToDate(startMonth, startYear, true);
		LocalDateTime endDateTime = Helper.convertToDate(endMonth, endYear, false);

		String startDateTimeInMillis = Helper.convertToMillisString(startDateTime);
		String endDateTimeInMillis = Helper.convertToMillisString(endDateTime);

		PropertySharedRoomBedDetail bedDetail = null;

		if (request.getType().equals(PropertyRoomType.SHARED.value())) {

			boolean isSharedBedPresent = false;

			for (PropertySharedRoomBedDetail bed : sharedRoomBeds) {
				if (bed.getId() == request.getBedId()) {
					isSharedBedPresent = true;
					bedDetail = bed;
					break;
				}
			}

			if (!isSharedBedPresent) {
				response.setResponseMessage("Shared bed no not found in room!!!");
				response.setSuccess(false);
				return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
			}

		}

		if (request.getType().equals(PropertyRoomType.PRIVATE.value())) {
			List<PropertyBooking> existingBookings = this.propertyBookingService
					.getByPropertyAndRoomDetailAndRoomTypeAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
							property, room, request.getType(), BookingStatus.APPROVED.value(), endDateTimeInMillis,
							startDateTimeInMillis);

			if (!CollectionUtils.isEmpty(existingBookings)) {
				response.setResponseMessage("Room " + room.getRoomNo()
						+ " Already booked in selected Range, you can check for other months!!!");
				response.setSuccess(false);
				return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
			}
		} else {
			List<PropertyBooking> existingBookings = this.propertyBookingService
					.getByPropertyAndRoomDetailAndRoomTypeAndBedDetailAndStatusAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
							property, room, request.getType(), bedDetail, BookingStatus.APPROVED.value(),
							endDateTimeInMillis, startDateTimeInMillis);

			if (!CollectionUtils.isEmpty(existingBookings)) {
				response.setResponseMessage("Room " + room.getRoomNo() + " - Bed No " + bedDetail.getBedNo()
						+ " Already booked in selected Range, you can check for other months!!!");
				response.setSuccess(false);
				return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}

		PropertyBooking booking = new PropertyBooking();
		booking.setAmountToPayPerMonth(
				request.getType().equals(PropertyRoomType.PRIVATE.value()) ? room.getPrivateRoomPrice()
						: room.getSharedRoomOneBedPrice());
		booking.setBedDetail(request.getType().equals(PropertyRoomType.PRIVATE.value()) ? null : bedDetail);
		booking.setBookingId(Helper.generateBookingId());
		booking.setBookingTime(currentTime);
		booking.setGuest(guest);
		booking.setProperty(property);
		booking.setRoomDetail(room);
		booking.setStartDate(startDateTimeInMillis);
		booking.setEndDate(endDateTimeInMillis);
		booking.setRoomType(request.getType());
		booking.setTotalStayMonth(Helper.getMonthsBetween(startDateTimeInMillis, endDateTimeInMillis).size());

		booking.setStatus(BookingStatus.PENDING.value());

		PropertyBooking updatedBooking = this.propertyBookingService.addBooking(booking);

		if (updatedBooking == null) {
			response.setResponseMessage("Failed to book the room!!!");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		List<String> bookedMonths = Helper.getMonthsBetweenWithTime(startDateTimeInMillis, endDateTimeInMillis);

		for (int i = 0; i < Helper.getMonthsBetween(startDateTimeInMillis, endDateTimeInMillis).size(); i++) {

			BookingPayment payment = new BookingPayment();
			payment.setAmountPaid(
					request.getType().equals(PropertyRoomType.PRIVATE.value()) ? room.getPrivateRoomPrice()
							: room.getSharedRoomOneBedPrice());
			payment.setBooking(updatedBooking);
			payment.setForMonth(bookedMonths.get(i));
			payment.setPaymentId("");
			payment.setPaymentType(BookingPaymentType.WALLET.value());
			payment.setStatus(BookingPaymentStatus.PENDING.value());
			payment.setPaymentTime(currentTime);

			bookingPaymentService.add(payment);

		}

		response.setResponseMessage("Property Added successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> updatePropertyBookingStatus(int bookingId, String status) {

		LOG.info("Request received for updating the property booking status");

		CommonApiResponse response = new CommonApiResponse();

		if (bookingId == 0) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		PropertyBooking booking = this.propertyBookingService.getById(bookingId);

		if (booking == null) {
			response.setResponseMessage("property booking not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		booking.setStatus(status);

		PropertyBooking updatedBooking = this.propertyBookingService.addBooking(booking);

		if (updatedBooking == null) {
			response.setResponseMessage("Failed to update the booking status");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.setResponseMessage("Booking updated successful");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> checkRoomAvailability(int propertyId, int roomId, int bedId,
			String roomType, String startDate, String endDate) {

		LOG.info("Request received for check the room availability!!!");

		CommonApiResponse response = new CommonApiResponse();

		if (propertyId == 0 || roomId == 0 || roomType == null || startDate == null || endDate == null) {
			response.setResponseMessage("request is null");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (roomType.equals(PropertyRoomType.SHARED.value()) && bedId == 0) {
			response.setResponseMessage("Please select the shared room's bed no. to check availability!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Property property = this.propertyService.getById(propertyId);

		if (property == null) {
			response.setResponseMessage("property not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		PropertyRoomDetail room = propertyRoomDetailService.getRoomById(roomId);

		if (room == null) {
			response.setResponseMessage("Property Room not found!!!");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		List<PropertySharedRoomBedDetail> sharedRoomBeds = null;

		if (roomType.equals(PropertyRoomType.SHARED.value())) {
			sharedRoomBeds = room.getSharedRoomBeds();

			if (CollectionUtils.isEmpty(sharedRoomBeds)) {
				response.setResponseMessage("Shared Beds not found in the Room!!!");
				response.setSuccess(false);
				return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}

		String startMonth = startDate.split(" ")[0];
		Integer startYear = Integer.parseInt(startDate.split(" ")[1]);

		String endMonth = endDate.split(" ")[0];
		Integer endYear = Integer.parseInt(endDate.split(" ")[1]);

		LocalDateTime startDateTime = Helper.convertToDate(startMonth, startYear, true);
		LocalDateTime endDateTime = Helper.convertToDate(endMonth, endYear, false);

		String startDateTimeInMillis = Helper.convertToMillisString(startDateTime);
		String endDateTimeInMillis = Helper.convertToMillisString(endDateTime);

		PropertySharedRoomBedDetail bedDetail = null;

		if (roomType.equals(PropertyRoomType.SHARED.value())) {

			boolean isSharedBedPresent = false;

			for (PropertySharedRoomBedDetail bed : sharedRoomBeds) {
				if (bed.getId() == bedId) {
					isSharedBedPresent = true;
					bedDetail = bed;
					break;
				}
			}

			if (!isSharedBedPresent) {
				response.setResponseMessage("Shared bed no not found in room!!!");
				response.setSuccess(false);
				return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
			}

		}

		if (roomType.equals(PropertyRoomType.PRIVATE.value())) {
			List<PropertyBooking> existingBookings = this.propertyBookingService
					.getByPropertyAndRoomDetailAndRoomTypeAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
							property, room, roomType, BookingStatus.APPROVED.value(), endDateTimeInMillis,
							startDateTimeInMillis);

			if (!CollectionUtils.isEmpty(existingBookings)) {
				response.setResponseMessage("Room " + room.getRoomNo()
						+ " Already booked in selected Range, you can check for other months!!!");
				response.setSuccess(false);
				return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
			}
		} else {
			List<PropertyBooking> existingBookings = this.propertyBookingService
					.getByPropertyAndRoomDetailAndRoomTypeAndBedDetailAndStatusAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
							property, room, roomType, bedDetail, BookingStatus.APPROVED.value(), endDateTimeInMillis,
							startDateTimeInMillis);

			if (!CollectionUtils.isEmpty(existingBookings)) {
				response.setResponseMessage("Room " + room.getRoomNo() + " - Bed No " + bedDetail.getBedNo()
						+ " Already booked in selected Range, you can check for other months!!!");
				response.setSuccess(false);
				return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}

		if (roomType.equals(PropertyRoomType.PRIVATE.value())) {

			response.setResponseMessage(
					"Congralutions!!! Room " + room.getRoomNo() + " is Available in selected Range!!!");
			response.setSuccess(true);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

		} else {

			response.setResponseMessage("Congralutions!!! Room " + room.getRoomNo() + " - Bed No "
					+ bedDetail.getBedNo() + " is Available in selected Range!!!!!!");
			response.setSuccess(true);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		}

	}

	public ResponseEntity<PropertyBookingResponse> fetchAllPropertyBookingByStatus(String status) {

		LOG.info("Request received for fetching all property bookings by status");

		PropertyBookingResponse response = new PropertyBookingResponse();

		if (status == null) {
			response.setResponseMessage("missing status");
			response.setSuccess(false);

			return new ResponseEntity<PropertyBookingResponse>(response, HttpStatus.BAD_REQUEST);
		}

		List<PropertyBooking> bookings = new ArrayList<>();

		bookings = this.propertyBookingService.findByStatusOrderByIdDesc(status);

		if (CollectionUtils.isEmpty(bookings)) {
			response.setResponseMessage("No bookings found");
			response.setSuccess(false);

			return new ResponseEntity<PropertyBookingResponse>(response, HttpStatus.OK);
		}

		response.setBookings(bookings);
		response.setResponseMessage("Bookings fetched successful");
		response.setSuccess(true);

		return new ResponseEntity<PropertyBookingResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<PropertyBookingResponse> fetchAllPropertyBookingByOwner(Integer ownerId, String status) {

		LOG.info("Request received for fetching all property bookings by ownwer");

		PropertyBookingResponse response = new PropertyBookingResponse();

		if (status == null || ownerId == 0) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<PropertyBookingResponse>(response, HttpStatus.BAD_REQUEST);
		}

		List<PropertyBooking> bookings = new ArrayList<>();

		User owner = this.userService.getUserById(ownerId);

		if (owner == null || !owner.getRole().equals(UserRole.ROLE_OWNER.value())) {
			response.setResponseMessage("Owner Not Found");
			response.setSuccess(false);
			return new ResponseEntity<PropertyBookingResponse>(response, HttpStatus.BAD_REQUEST);
		}

		bookings = this.propertyBookingService.findByOwnerAndStatusOrderByIdDesc(owner, status);

		if (CollectionUtils.isEmpty(bookings)) {
			response.setResponseMessage("No bookings found");
			response.setSuccess(false);

			return new ResponseEntity<PropertyBookingResponse>(response, HttpStatus.OK);
		}

		response.setBookings(bookings);
		response.setResponseMessage("Bookings fetched successful");
		response.setSuccess(true);

		return new ResponseEntity<PropertyBookingResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<PropertyBookingResponse> fetchAllPropertyBookingByGuest(Integer guestId, String status) {

		LOG.info("Request received for fetching all property bookings by guest");

		PropertyBookingResponse response = new PropertyBookingResponse();

		if (status == null || guestId == 0) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<PropertyBookingResponse>(response, HttpStatus.BAD_REQUEST);
		}

		List<PropertyBooking> bookings = new ArrayList<>();

		User guest = this.userService.getUserById(guestId);

		if (guest == null || !guest.getRole().equals(UserRole.ROLE_GUEST.value())) {
			response.setResponseMessage("Guest Not Found");
			response.setSuccess(false);
			return new ResponseEntity<PropertyBookingResponse>(response, HttpStatus.BAD_REQUEST);
		}

		bookings = this.propertyBookingService.findByGuestAndStatusOrderByIdDesc(guest, status);

		if (CollectionUtils.isEmpty(bookings)) {
			response.setResponseMessage("No bookings found");
			response.setSuccess(false);

			return new ResponseEntity<PropertyBookingResponse>(response, HttpStatus.OK);
		}

		response.setBookings(bookings);
		response.setResponseMessage("Bookings fetched successful");
		response.setSuccess(true);

		return new ResponseEntity<PropertyBookingResponse>(response, HttpStatus.OK);
	}

}
