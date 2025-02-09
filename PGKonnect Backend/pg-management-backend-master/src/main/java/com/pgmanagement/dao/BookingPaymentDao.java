package com.pgmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgmanagement.entity.BookingPayment;

@Repository
public interface BookingPaymentDao extends JpaRepository<BookingPayment, Integer> {

}
