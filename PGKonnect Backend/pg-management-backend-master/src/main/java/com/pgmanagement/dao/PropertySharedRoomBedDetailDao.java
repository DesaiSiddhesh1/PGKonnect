package com.pgmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgmanagement.entity.PropertySharedRoomBedDetail;

@Repository
public interface PropertySharedRoomBedDetailDao extends JpaRepository<PropertySharedRoomBedDetail, Integer> {

}
