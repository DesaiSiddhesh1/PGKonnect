package com.pgmanagement.service;

import java.util.List;

import com.pgmanagement.entity.Location;
import com.pgmanagement.entity.Property;
import com.pgmanagement.entity.User;

public interface PropertyService {

	Property addProperty(Property property);

	Property updateProperty(Property property);

	Property getById(int propertyId);

	List<Property> getByOwnerId(User owner);

	List<Property> getByStatus(String status);

	List<Property> getByLocationAndStatus(Location location, String status);
	
	List<Property> getByOwnerAndStatus(User owner, String status);

	List<Property> getByNameAndStatus(String name, String status);

}
