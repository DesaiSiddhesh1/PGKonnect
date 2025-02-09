package com.pgmanagement.resource;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

import com.pgmanagement.dto.AddPropertyFacilityRequest;
import com.pgmanagement.dto.AddPropertyRequestDto;
import com.pgmanagement.dto.AddPropertyRoomDetailRequest;
import com.pgmanagement.dto.CommonApiResponse;
import com.pgmanagement.dto.PropertyResponse;
import com.pgmanagement.entity.FacilityDetail;
import com.pgmanagement.entity.Location;
import com.pgmanagement.entity.Property;
import com.pgmanagement.entity.PropertyRoomDetail;
import com.pgmanagement.entity.PropertySharedRoomBedDetail;
import com.pgmanagement.entity.User;
import com.pgmanagement.service.FacilityDetailService;
import com.pgmanagement.service.LocationService;
import com.pgmanagement.service.PropertyRoomDetailService;
import com.pgmanagement.service.PropertyService;
import com.pgmanagement.service.PropertySharedRoomBedDetailService;
import com.pgmanagement.service.StorageService;
import com.pgmanagement.service.UserService;
import com.pgmanagement.utility.Constants.ActiveStatus;
import com.pgmanagement.utility.Constants.PropertyRoomType;
import com.pgmanagement.utility.Constants.PropertyStatus;
import com.pgmanagement.utility.Constants.UserRole;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PropertyResource {

	private final Logger LOG = LoggerFactory.getLogger(PropertyResource.class);

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private UserService userService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private FacilityDetailService facilityDetailService;

	@Autowired
	private PropertyRoomDetailService propertyRoomDetailService;

	@Autowired
	private PropertySharedRoomBedDetailService propertySharedRoomBedDetailService;

	public ResponseEntity<CommonApiResponse> addProperty(AddPropertyRequestDto request) {

		LOG.info("Request received for adding the property ");

		CommonApiResponse response = new CommonApiResponse();

		if (request == null) {
			response.setResponseMessage("request is null");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getOwnerId() == 0) {
			response.setResponseMessage("missing owner id");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getLocationId() == 0) {
			response.setResponseMessage("missing location id");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Location location = this.locationService.getById(request.getLocationId());

		if (location == null) {
			response.setResponseMessage("location not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User user = this.userService.getUserById(request.getOwnerId());

		if (user == null || !user.getRole().equals(UserRole.ROLE_OWNER.value())) {
			response.setResponseMessage("Owner not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		String propertyImageFileName = this.storageService.store(request.getImage());

		Property property = new Property();
		property.setName(request.getName());
		property.setDescription(request.getDescription());
		property.setImage(propertyImageFileName);
		property.setAddress(request.getAddress());
		property.setOwner(user);
		property.setLocation(location);
		property.setStatus(PropertyStatus.PENDNING.value());

		Property addedProperty = this.propertyService.addProperty(property);

		if (addedProperty == null) {
			response.setResponseMessage("Failed to add the Property!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("Property Added successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<PropertyResponse> updatePropertyFacility(AddPropertyFacilityRequest request) {

		LOG.info("Request received for updating the property facility");

		PropertyResponse response = new PropertyResponse();

		if (request == null) {
			response.setResponseMessage("request is null");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getPropertyId() == 0) {
			response.setResponseMessage("missing property id");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getName() == null || request.getDescription() == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Property property = this.propertyService.getById(request.getPropertyId());

		if (property == null) {
			response.setResponseMessage("property not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		FacilityDetail facilityDetail = new FacilityDetail();
		facilityDetail.setName(request.getName());
		facilityDetail.setDescription(request.getDescription());
		facilityDetail.setProperty(property);

		FacilityDetail savedFacilityDetail = facilityDetailService.addDetail(facilityDetail);

		if (savedFacilityDetail == null) {
			response.setProperties(Arrays.asList(property));
			response.setResponseMessage("Failed to add the facility!!!");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Property updatedProperty = this.propertyService.getById(request.getPropertyId());

		response.setProperties(Arrays.asList(updatedProperty));
		response.setResponseMessage("Property Facilites Added Successful!!");
		response.setSuccess(true);

		return new ResponseEntity<PropertyResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<PropertyResponse> addPropertyRoom(AddPropertyRoomDetailRequest request) {

		LOG.info("Request received for adding the room detail");

		PropertyResponse response = new PropertyResponse();

		if (request == null) {
			response.setResponseMessage("request is null");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getPropertyId() == 0) {
			response.setResponseMessage("missing property id");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getType() == null || request.getRoomNo() == null || request.getRoomFor() == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getType().equals(PropertyRoomType.PRIVATE.value())) {
			if (request.getPrivateRoomPrice() == null) {
				response.setResponseMessage("Private Room price is missing!!!");
				response.setSuccess(false);

				return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
			}
		} else if (request.getType().equals(PropertyRoomType.SHARED.value())) {
			if (request.getSharedRoomOneBedPrice() == null || request.getSharedBedsNos() == null
					|| request.getTotalBeds() == 0) {
				response.setResponseMessage("missing input for shared room!!!");
				response.setSuccess(false);

				return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
			}

			if (request.getSharedRoomOneBedPrice().compareTo(BigDecimal.ZERO) <= 0) {
				response.setResponseMessage("missing shared bed room price!!!");
				response.setSuccess(false);

				return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}

		Property property = this.propertyService.getById(request.getPropertyId());

		if (property == null) {
			response.setResponseMessage("property not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		PropertyRoomDetail roomDetail = AddPropertyRoomDetailRequest.toEntity(request);
		roomDetail.setProperty(property);

		PropertyRoomDetail savedRoomDetail = this.propertyRoomDetailService.addRoom(roomDetail);

		if (savedRoomDetail == null) {
			response.setResponseMessage("Failed to add the room detail!!!");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getType().equals(PropertyRoomType.SHARED.value())) {

			String[] bedNos = request.getSharedBedsNos().split(",");

			for (int i = 0; i < bedNos.length; i++) {
				String bedNo = bedNos[i];

				PropertySharedRoomBedDetail bedDetail = new PropertySharedRoomBedDetail();
				bedDetail.setBedNo(bedNo);
				bedDetail.setRoomDetail(savedRoomDetail);

				propertySharedRoomBedDetailService.addSharedBed(bedDetail);

			}

		}

		Property updatedProperty = this.propertyService.getById(request.getPropertyId());

		response.setProperties(Arrays.asList(updatedProperty));
		response.setResponseMessage("Property Room Detail Added Successful!!");
		response.setSuccess(true);

		return new ResponseEntity<PropertyResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<PropertyResponse> fetchAllPropertiesByStatus(String status) {

		LOG.info("Request received for fetching all properties by status");

		PropertyResponse response = new PropertyResponse();

		if (status == null) {
			response.setResponseMessage("missing status");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		List<Property> properties = new ArrayList<>();

		properties = this.propertyService.getByStatus(status);

		if (CollectionUtils.isEmpty(properties)) {
			response.setResponseMessage("No properties found");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.OK);
		}

		response.setProperties(properties);
		response.setResponseMessage("Properties fetched successful");
		response.setSuccess(true);

		return new ResponseEntity<PropertyResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<PropertyResponse> fetchAllPropertiesByLocationAndStatus(Integer locationId, String status) {

		LOG.info("Request received for fetching all properties by location and status");

		PropertyResponse response = new PropertyResponse();

		if (status == null || locationId == 0) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		List<Property> properties = new ArrayList<>();

		Location location = this.locationService.getById(locationId);

		if (location == null) {
			response.setResponseMessage("location not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		properties = this.propertyService.getByLocationAndStatus(location, status);

		if (CollectionUtils.isEmpty(properties)) {
			response.setResponseMessage("No properties found");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.OK);
		}

		response.setProperties(properties);
		response.setResponseMessage("Properties fetched successful");
		response.setSuccess(true);

		return new ResponseEntity<PropertyResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<PropertyResponse> fetchAllPropertiesByOwner(Integer ownerId, String status) {

		LOG.info("Request received for fetching all properties by owner and status");

		PropertyResponse response = new PropertyResponse();

		if (status == null || ownerId == 0) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		List<Property> properties = new ArrayList<>();

		User owner = this.userService.getUserById(ownerId);

		if (owner == null) {
			response.setResponseMessage("owner not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		properties = this.propertyService.getByOwnerAndStatus(owner, status);

		if (CollectionUtils.isEmpty(properties)) {
			response.setResponseMessage("No properties found");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.OK);
		}

		response.setProperties(properties);
		response.setResponseMessage("Properties fetched successful");
		response.setSuccess(true);

		return new ResponseEntity<PropertyResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<PropertyResponse> fetchPropertyById(Integer propertyId) {

		LOG.info("Request received for fetching the property using id");

		PropertyResponse response = new PropertyResponse();

		if (propertyId == 0) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Property property = this.propertyService.getById(propertyId);

		if (property == null) {
			response.setResponseMessage("property not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setProperties(Arrays.asList(property));
		response.setResponseMessage("Properties fetched successful");
		response.setSuccess(true);

		return new ResponseEntity<PropertyResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> deleteProperty(Integer propertyId) {

		LOG.info("Request received for deleting the property using id");

		CommonApiResponse response = new CommonApiResponse();

		if (propertyId == 0) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Property property = this.propertyService.getById(propertyId);

		if (property == null) {
			response.setResponseMessage("property not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		property.setStatus(ActiveStatus.DEACTIVATED.value());

		Property updatedProperty = this.propertyService.updateProperty(property);

		if (updatedProperty == null) {
			response.setResponseMessage("Failed to delete the property!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.setResponseMessage("Properties Deleted successful");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> updatePropertyStatus(int propertyId, String status) {

		LOG.info("Request received for updating the property status");

		CommonApiResponse response = new CommonApiResponse();

		if (propertyId == 0) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Property property = this.propertyService.getById(propertyId);

		if (property == null) {
			response.setResponseMessage("property not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		property.setStatus(status);

		Property updatedProperty = this.propertyService.updateProperty(property);

		if (updatedProperty == null) {
			response.setResponseMessage("Failed to update the property status!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.setResponseMessage("Property updated successful");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<PropertyResponse> fetchAllPropertiesByNameAndStatus(String name, String status) {

		LOG.info("Request received for fetching all properties by name and status");

		PropertyResponse response = new PropertyResponse();

		if (status == null || name == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.BAD_REQUEST);
		}

		List<Property> properties = new ArrayList<>();

		properties = this.propertyService.getByNameAndStatus(name, status);

		if (CollectionUtils.isEmpty(properties)) {
			response.setResponseMessage("No properties found");
			response.setSuccess(false);

			return new ResponseEntity<PropertyResponse>(response, HttpStatus.OK);
		}

		response.setProperties(properties);
		response.setResponseMessage("Properties fetched successful");
		response.setSuccess(true);

		return new ResponseEntity<PropertyResponse>(response, HttpStatus.OK);
	}

	public void fetchPropertyImage(String propertyImageName, HttpServletResponse resp) {
		Resource resource = storageService.load(propertyImageName);
		if (resource != null) {
			try (InputStream in = resource.getInputStream()) {
				ServletOutputStream out = resp.getOutputStream();
				FileCopyUtils.copy(in, out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
