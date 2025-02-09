package com.pgmanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgmanagement.entity.Location;
import com.pgmanagement.entity.Property;
import com.pgmanagement.entity.User;

@Repository
public interface PropertyDao extends JpaRepository<Property, Integer> {

	List<Property> findByOwner(User owner);

	List<Property> findByStatus(String status);

	List<Property> findByOwnerAndStatus(User owner, String status);

	List<Property> findByLocationAndStatus(Location location, String status);

	List<Property> findByStatusAndNameContainingIgnoreCase(String status, String name);

}
