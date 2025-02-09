package com.pgmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgmanagement.dao.PropertyDao;
import com.pgmanagement.entity.Location;
import com.pgmanagement.entity.Property;
import com.pgmanagement.entity.User;

@Service
public class PropertyServiceImpl implements PropertyService {

	@Autowired
	private PropertyDao propertyDao;

	@Override
	public Property addProperty(Property property) {
		// TODO Auto-generated method stub
		return propertyDao.save(property);
	}

	@Override
	public Property updateProperty(Property property) {
		// TODO Auto-generated method stub
		return propertyDao.save(property);
	}

	@Override
	public Property getById(int propertyId) {

		Optional<Property> optionalProperty = this.propertyDao.findById(propertyId);

		if (optionalProperty.isPresent()) {
			return optionalProperty.get();
		} else {
			return null;
		}

	}

	@Override
	public List<Property> getByOwnerId(User owner) {
		// TODO Auto-generated method stub
		return propertyDao.findByOwner(owner);
	}

	@Override
	public List<Property> getByStatus(String status) {
		// TODO Auto-generated method stub
		return propertyDao.findByStatus(status);
	}

	@Override
	public List<Property> getByLocationAndStatus(Location location, String status) {
		// TODO Auto-generated method stub
		return propertyDao.findByLocationAndStatus(location, status);
	}

	@Override
	public List<Property> getByOwnerAndStatus(User owner, String status) {
		// TODO Auto-generated method stub
		return propertyDao.findByOwnerAndStatus(owner, status);
	}

	@Override
	public List<Property> getByNameAndStatus(String name, String status) {
		// TODO Auto-generated method stub
		return propertyDao.findByStatusAndNameContainingIgnoreCase(status, name);
	}

}
