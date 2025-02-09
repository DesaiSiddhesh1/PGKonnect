package com.pgmanagement.dto;

import lombok.Data;

@Data
public class AddPropertyFacilityRequest {

	private int id; // for updating we can use it

	private String name;

	private String description;

	private int propertyId;

}
