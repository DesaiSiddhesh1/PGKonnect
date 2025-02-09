package com.pgmanagement.resource;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pgmanagement.dto.CommonApiResponse;
import com.pgmanagement.dto.LocationResponseDto;
import com.pgmanagement.entity.Location;
import com.pgmanagement.entity.Property;
import com.pgmanagement.exception.LocationSaveFailedException;
import com.pgmanagement.service.LocationService;
import com.pgmanagement.service.PropertyService;
import com.pgmanagement.utility.Constants.ActiveStatus;

@Component
public class LocationResource {

	@Autowired
	private LocationService locationService;

	@Autowired
	private PropertyService propertyService;

	private final Logger LOG = LoggerFactory.getLogger(LocationResource.class);

	public ResponseEntity<CommonApiResponse> addLocation(Location location) {

		LOG.info("Request received for add location");

		CommonApiResponse response = new CommonApiResponse();

		if (location == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		location.setStatus(ActiveStatus.ACTIVE.value());

		Location savedLocation = this.locationService.add(location);

		if (savedLocation == null) {
			throw new LocationSaveFailedException("Failed to add location");
		}

		response.setResponseMessage("Location Added Successful");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<CommonApiResponse> updateLocation(Location location) {

		LOG.info("Request received for add location");

		CommonApiResponse response = new CommonApiResponse();

		if (location == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (location.getId() == 0) {
			response.setResponseMessage("missing location Id");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		location.setStatus(ActiveStatus.ACTIVE.value());
		Location savedLocation = this.locationService.update(location);

		if (savedLocation == null) {
			throw new LocationSaveFailedException("Failed to update location");
		}

		response.setResponseMessage("Location Updated Successful");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<LocationResponseDto> fetchAllLocation() {

		LOG.info("Request received for fetching all locations");

		LocationResponseDto response = new LocationResponseDto();

		List<Location> locations = new ArrayList<>();

		locations = this.locationService.getAllByStatus(ActiveStatus.ACTIVE.value());

		if (CollectionUtils.isEmpty(locations)) {
			response.setResponseMessage("No Locations found");
			response.setSuccess(false);

			return new ResponseEntity<LocationResponseDto>(response, HttpStatus.OK);
		}

		response.setLocations(locations);
		response.setResponseMessage("Location fetched successful");
		response.setSuccess(true);

		return new ResponseEntity<LocationResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> deleteLocation(int locationId) {

		LOG.info("Request received for deleting location");

		CommonApiResponse response = new CommonApiResponse();

		if (locationId == 0) {
			response.setResponseMessage("missing location Id");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Location location = this.locationService.getById(locationId);

		if (location == null) {
			response.setResponseMessage("location not found");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		location.setStatus(ActiveStatus.DEACTIVATED.value());
		Location updatedLocation = this.locationService.update(location);

		if (updatedLocation == null) {
			throw new LocationSaveFailedException("Failed to delete the Location");
		}

		List<Property> properties = this.propertyService.getByLocationAndStatus(updatedLocation,
				ActiveStatus.ACTIVE.value());

		for (Property property : properties) {
			property.setStatus(ActiveStatus.DEACTIVATED.value());

			this.propertyService.updateProperty(property);
		}

		response.setResponseMessage("Location Deleted Successful");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

}
