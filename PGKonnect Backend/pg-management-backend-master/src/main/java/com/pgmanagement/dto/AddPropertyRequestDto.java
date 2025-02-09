package com.pgmanagement.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddPropertyRequestDto {

	private int id;

	private String name;

	private String description;

	private String address;

	private Integer locationId;

	private MultipartFile image;

	private Integer ownerId;

}
