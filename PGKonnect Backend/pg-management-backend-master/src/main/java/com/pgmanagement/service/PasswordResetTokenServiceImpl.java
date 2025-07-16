package com.pgmanagement.service;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    @Override
    public void saveToken(String email, String token) {
        tokenStore.put(token, email);
    }

    @Override
    public String getEmailByToken(String token) {
        return tokenStore.get(token);
    }

    @Override
    public void removeToken(String token) {
        tokenStore.remove(token);
    }
}
