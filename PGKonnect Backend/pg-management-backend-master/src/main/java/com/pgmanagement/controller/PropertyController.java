package com.pgmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pgmanagement.dto.AddPropertyFacilityRequest;
import com.pgmanagement.dto.AddPropertyRequestDto;
import com.pgmanagement.dto.AddPropertyRoomDetailRequest;
import com.pgmanagement.dto.CommonApiResponse;
import com.pgmanagement.dto.PropertyResponse;
import com.pgmanagement.resource.PropertyResource;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/property")
@CrossOrigin(origins = "http://localhost:3000")
public class PropertyController {

	@Autowired
	private PropertyResource propertyResource;

	@PostMapping("/add")
	public ResponseEntity<CommonApiResponse> addProperty(AddPropertyRequestDto request) {
		return this.propertyResource.addProperty(request);
	}

	@PutMapping("/update/status")
	public ResponseEntity<CommonApiResponse> updatePropertyStatus(@RequestParam("propertyId") int propertyId,
			@RequestParam("status") String status) {
		return this.propertyResource.updatePropertyStatus(propertyId, status);
	}

	@PutMapping("/facility/add")
	public ResponseEntity<PropertyResponse> updatePropertyFacility(@RequestBody AddPropertyFacilityRequest request) {
		return this.propertyResource.updatePropertyFacility(request);
	}

	@PutMapping("/room/add")
	public ResponseEntity<PropertyResponse> addPropertyRoom(@RequestBody AddPropertyRoomDetailRequest request) {
		return this.propertyResource.addPropertyRoom(request);
	}

	@GetMapping("/fetch/all")
	public ResponseEntity<PropertyResponse> fetchAllProperties(@RequestParam("status") String status) {
		return this.propertyResource.fetchAllPropertiesByStatus(status);
	}

	@GetMapping("/fetch/location-wise")
	public ResponseEntity<PropertyResponse> fetchAllPropertiesByLocation(@RequestParam("locationId") Integer locationId,
			@RequestParam("status") String status) {
		return this.propertyResource.fetchAllPropertiesByLocationAndStatus(locationId, status);
	}
	
	@GetMapping("/fetch/name-wise")
	public ResponseEntity<PropertyResponse> fetchAllPropertiesByName(@RequestParam("name") String name,
			@RequestParam("status") String status) {
		return this.propertyResource.fetchAllPropertiesByNameAndStatus(name, status);
	}

	@GetMapping("/fetch/owner-wise")
	public ResponseEntity<PropertyResponse> fetchAllPropertiesByOwner(@RequestParam("ownerId") Integer ownerId,
			@RequestParam("status") String status) {
		return this.propertyResource.fetchAllPropertiesByOwner(ownerId, status);
	}

	@GetMapping("/fetch/id-wise")
	public ResponseEntity<PropertyResponse> fetchPropertyById(@RequestParam("propertyId") Integer propertyId) {
		return this.propertyResource.fetchPropertyById(propertyId);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<CommonApiResponse> deleteProperty(@RequestParam("propertyId") Integer propertyId) {
		return this.propertyResource.deleteProperty(propertyId);
	}
	
	@GetMapping(value = "/{propertyImageName}", produces = "image/*")
	public void fetchTourImage(@PathVariable("propertyImageName") String propertyImageName, HttpServletResponse resp) {
		this.propertyResource.fetchPropertyImage(propertyImageName, resp);
	}

}
