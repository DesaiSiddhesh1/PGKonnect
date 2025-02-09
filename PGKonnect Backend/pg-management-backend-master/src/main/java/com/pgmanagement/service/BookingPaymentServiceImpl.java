package com.pgmanagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgmanagement.dao.BookingPaymentDao;
import com.pgmanagement.entity.BookingPayment;

@Service
public class BookingPaymentServiceImpl implements BookingPaymentService {

	@Autowired
	private BookingPaymentDao bookingPaymentDao;

	@Override
	public BookingPayment add(BookingPayment bookingPayment) {
		// TODO Auto-generated method stub
		return bookingPaymentDao.save(bookingPayment);
	}

	@Override
	public BookingPayment update(BookingPayment bookingPayment) {
		// TODO Auto-generated method stub
		return bookingPaymentDao.save(bookingPayment);
	}

	@Override
	public BookingPayment getById(int bookingPaymentId) {

		Optional<BookingPayment> optional = this.bookingPaymentDao.findById(bookingPaymentId);

		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}

	}

}
