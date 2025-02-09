package com.pgmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgmanagement.entity.PropertyRoomDetail;

@Repository
public interface PropertyRoomDetailDao extends JpaRepository<PropertyRoomDetail, Integer> {

}
