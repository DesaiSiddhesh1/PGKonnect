package com.pgmanagement.service;

public interface PasswordResetTokenService {
	void saveToken(String email, String token);
    String getEmailByToken(String token);
    void removeToken(String token);
}
