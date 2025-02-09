package com.pgmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pgmanagement.dto.CommonApiResponse;
import com.pgmanagement.resource.BookingPaymentResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/booking/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingPaymentController {

	@Autowired
	private BookingPaymentResource bookingPaymentResource;

	@GetMapping
	public ResponseEntity<CommonApiResponse> payForTheBooking(@RequestParam("userId") int userId,
			@RequestParam("bookingId") int bookingId, @RequestParam("paymentId") int paymentId) {
		return this.bookingPaymentResource.payForTheBooking(userId, bookingId, paymentId);
	}

}
