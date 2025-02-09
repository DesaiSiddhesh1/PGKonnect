package com.pgmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgmanagement.entity.PgTransaction;

@Repository
public interface PgTransactionDao extends JpaRepository<PgTransaction, Integer> {

	PgTransaction findByTypeAndOrderId(String value, String razorpayOrderId);

}
