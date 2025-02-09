package com.pgmanagement.service;

import java.util.List;

import com.pgmanagement.entity.Location;

public interface LocationService {

	Location add(Location location);

	Location update(Location location);

	Location getById(int id);

	List<Location> getAllByStatus(String status);

}
