package com.pgmanagement.dto;

import lombok.Data;

@Data
public class UserProfileDataUpdateRequest {

	private String fullName;

	private String age;

	private String profession;

	private String permanentAddress;

	private Integer userId;

}
