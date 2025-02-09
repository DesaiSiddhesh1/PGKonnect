package com.pgmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgmanagement.dao.LocationDao;
import com.pgmanagement.entity.Location;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationDao locationDao;

	@Override
	public Location add(Location location) {
		// TODO Auto-generated method stub
		return locationDao.save(location);
	}

	@Override
	public Location update(Location location) {
		// TODO Auto-generated method stub
		return locationDao.save(location);
	}

	@Override
	public Location getById(int id) {

		Optional<Location> optionalLocation = this.locationDao.findById(id);

		if (optionalLocation.isEmpty()) {
			return null;
		}

		return optionalLocation.get();
	}

	@Override
	public List<Location> getAllByStatus(String status) {
		// TODO Auto-generated method stub
		return this.locationDao.findByStatus(status);
	}

}
