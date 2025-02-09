package com.pgmanagement.service;

import com.pgmanagement.entity.BookingPayment;

public interface BookingPaymentService {

	BookingPayment add(BookingPayment bookingPayment);

	BookingPayment update(BookingPayment bookingPayment);

	BookingPayment getById(int bookingPaymentId);

}
