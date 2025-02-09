package com.pgmanagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgmanagement.dao.PropertyRoomDetailDao;
import com.pgmanagement.entity.PropertyRoomDetail;

@Service
public class PropertyRoomDetailServiceImpl implements PropertyRoomDetailService {

	@Autowired
	private PropertyRoomDetailDao propertyRoomDetailDao;

	@Override
	public PropertyRoomDetail addRoom(PropertyRoomDetail roomDetail) {
		// TODO Auto-generated method stub
		return propertyRoomDetailDao.save(roomDetail);
	}

	@Override
	public PropertyRoomDetail getRoomById(int roomId) {

		Optional<PropertyRoomDetail> optional = this.propertyRoomDetailDao.findById(roomId);

		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}

	}

}
