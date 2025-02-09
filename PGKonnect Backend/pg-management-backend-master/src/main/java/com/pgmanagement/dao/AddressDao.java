package com.pgmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgmanagement.entity.Address;

@Repository
public interface AddressDao extends JpaRepository<Address, Integer> {

}
