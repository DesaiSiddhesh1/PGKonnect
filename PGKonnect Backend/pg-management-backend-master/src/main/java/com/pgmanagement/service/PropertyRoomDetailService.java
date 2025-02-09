package com.pgmanagement.service;

import com.pgmanagement.entity.PropertyRoomDetail;

public interface PropertyRoomDetailService {

	PropertyRoomDetail addRoom(PropertyRoomDetail roomDetail);

	PropertyRoomDetail getRoomById(int roomId);

}
