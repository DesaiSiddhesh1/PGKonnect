package com.pgmanagement.dto;

import java.util.ArrayList;
import java.util.List;

import com.pgmanagement.entity.Property;

import lombok.Data;

@Data
public class PropertyResponse extends CommonApiResponse {

	private List<Property> properties = new ArrayList<>();

}
