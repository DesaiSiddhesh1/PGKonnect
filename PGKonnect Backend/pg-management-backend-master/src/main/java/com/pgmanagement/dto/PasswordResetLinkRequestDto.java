package com.pgmanagement.dto;

import lombok.Data;

@Data
public class PasswordResetLinkRequestDto {
	private String token;
    private String newPassword;
}
