package com.pgmanagement.dto;

import java.util.ArrayList;
import java.util.List;

import com.pgmanagement.entity.Location;

import lombok.Data;

@Data
public class LocationResponseDto extends CommonApiResponse {

	private List<Location> locations = new ArrayList<>();
	
}
