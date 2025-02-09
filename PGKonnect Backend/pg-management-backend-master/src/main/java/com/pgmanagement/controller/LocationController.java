package com.pgmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pgmanagement.dto.CommonApiResponse;
import com.pgmanagement.dto.LocationResponseDto;
import com.pgmanagement.entity.Location;
import com.pgmanagement.resource.LocationResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/location")
@CrossOrigin(origins = "http://localhost:3000")
public class LocationController {
	
	@Autowired
	private LocationResource locationResource;
	
	@PostMapping("/add")
	public ResponseEntity<CommonApiResponse> addLocation(@RequestBody Location location) {
		return locationResource.addLocation(location);
	}
	
	@PutMapping("/update")
	public ResponseEntity<CommonApiResponse> updateLocation(@RequestBody Location location) {
		return locationResource.updateLocation(location);
	}
	
	@GetMapping("/fetch/all")
	public ResponseEntity<LocationResponseDto> fetchAllLocation() {
		return locationResource.fetchAllLocation();
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<CommonApiResponse> deleteLocation(@RequestParam("locationId") int locationId) {
		return locationResource.deleteLocation(locationId);
	}

}
