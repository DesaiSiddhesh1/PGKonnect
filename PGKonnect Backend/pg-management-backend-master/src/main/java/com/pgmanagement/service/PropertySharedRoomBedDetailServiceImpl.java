package com.pgmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgmanagement.dao.PropertySharedRoomBedDetailDao;
import com.pgmanagement.entity.PropertySharedRoomBedDetail;

@Service
public class PropertySharedRoomBedDetailServiceImpl implements PropertySharedRoomBedDetailService {

	@Autowired
	private PropertySharedRoomBedDetailDao propertySharedRoomBedDetailDao;

	@Override
	public PropertySharedRoomBedDetail addSharedBed(PropertySharedRoomBedDetail propertySharedRoomBedDetail) {
		// TODO Auto-generated method stub
		return propertySharedRoomBedDetailDao.save(propertySharedRoomBedDetail);
	}

}
