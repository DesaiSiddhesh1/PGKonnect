package com.pgmanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgmanagement.entity.Location;

@Repository
public interface LocationDao extends JpaRepository<Location, Integer> {

	List<Location> findByStatus(String status);

}
