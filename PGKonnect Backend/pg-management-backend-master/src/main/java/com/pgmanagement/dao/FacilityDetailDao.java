package com.pgmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgmanagement.entity.FacilityDetail;

@Repository
public interface FacilityDetailDao extends JpaRepository<FacilityDetail, Integer> {

}
