package com.pgmanagement.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pgmanagement.dto.CommonApiResponse;
import com.pgmanagement.entity.BookingPayment;
import com.pgmanagement.entity.PropertyBooking;
import com.pgmanagement.entity.User;
import com.pgmanagement.service.BookingPaymentService;
import com.pgmanagement.service.PropertyBookingService;
import com.pgmanagement.service.UserService;
import com.pgmanagement.utility.Constants.BookingPaymentStatus;
import com.pgmanagement.utility.Constants.UserRole;
import com.pgmanagement.utility.Helper;

@Component
public class BookingPaymentResource {

	private final Logger LOG = LoggerFactory.getLogger(BookingPaymentResource.class);

	@Autowired
	private PropertyBookingService propertyBookingService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookingPaymentService bookingPaymentService;

	public ResponseEntity<CommonApiResponse> payForTheBooking(Integer userId, Integer bookingId, Integer paymentId) {

		LOG.info("Request received for making the booking payment for the month");

		CommonApiResponse response = new CommonApiResponse();

		if (userId == 0 || bookingId == 0 || paymentId == 0) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User user = this.userService.getUserById(userId);

		if (user == null || !user.getRole().equals(UserRole.ROLE_GUEST.value())) {
			response.setResponseMessage("Guest not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		PropertyBooking booking = this.propertyBookingService.getById(bookingId);

		if (booking == null) {
			response.setResponseMessage("PG Booking not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (booking.getGuest().getId() != userId) {
			response.setResponseMessage("PG Booking not belongs to Guest!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User owner = userService.getUserById(booking.getProperty().getOwner().getId());

		List<BookingPayment> payments = booking.getPayments();

		if (CollectionUtils.isEmpty(payments)) {
			response.setResponseMessage("PG Booking payments not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		boolean isSelectedPaymentEntryPresent = false;

		BookingPayment bookingPayment = null;

		for (BookingPayment payment : payments) {
			if (paymentId == payment.getId()) {
				isSelectedPaymentEntryPresent = true;
				bookingPayment = payment;
				break;
			}
		}

		if (!isSelectedPaymentEntryPresent) {
			response.setResponseMessage("PG Booking payment entry not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (user.getWalletAmount().compareTo(booking.getAmountToPayPerMonth()) < 0) {
			response.setResponseMessage("Insufficient Fund in Wallet!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		bookingPayment.setAmountPaid(booking.getAmountToPayPerMonth());
		bookingPayment.setStatus(BookingPaymentStatus.PAID.value());
		bookingPayment.setPaymentId(Helper.generateBookingPaymentId());

		user.setWalletAmount(user.getWalletAmount().subtract(booking.getAmountToPayPerMonth()));
		owner.setWalletAmount(owner.getWalletAmount().add(booking.getAmountToPayPerMonth()));

		this.userService.updateUser(user);
		this.userService.updateUser(owner);

		BookingPayment updatedPayment = this.bookingPaymentService.add(bookingPayment);

		if (updatedPayment == null) {
			response.setResponseMessage("Failed to make the Payment for the Month " + bookingPayment.getForMonth());
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("Payment for the month " + bookingPayment.getForMonth() + " is Successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

}
